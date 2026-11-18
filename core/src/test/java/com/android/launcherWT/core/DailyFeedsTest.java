package com.android.launcherWT.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DailyFeedsTest {
    @Test
    public void prefixesBingSourceOntoTheDate() {
        DailyPick pick = DailyFeeds.fromBing(
                "{\"images\":[{\"startdate\":\"20261112\",\"url\":\"/th?id=OHR.Test\"}]}",
                "bing-en");
        assertEquals(DailyFeeds.BING_EN, pick.source);
        assertEquals("bing-en:20261112", pick.key);
        assertEquals("https://www.bing.com/th?id=OHR.Test", pick.imageUrl);
    }

    @Test
    public void usesTheFirstWideWallhavenImage() {
        String json = "{\"data\":["
                + "{\"id\":\"tiny\",\"dimension_x\":640,\"dimension_y\":480,"
                + "\"path\":\"https:\\/\\/w.wallhaven.cc\\/full\\/aa\\/tiny.jpg\"},"
                + "{\"id\":\"wide\",\"dimension_x\":3840,\"dimension_y\":2160,"
                + "\"path\":\"https:\\/\\/w.wallhaven.cc\\/full\\/l8\\/wide.jpg\","
                + "\"thumbs\":{\"small\":\"https:\\/\\/th.wallhaven.cc\\/small\\/l8\\/wide.jpg\"}}"
                + "]}";
        DailyPick pick = DailyFeeds.fromWallhaven(json);
        assertEquals(DailyFeeds.WALLHAVEN, pick.source);
        assertEquals("wallhaven:wide", pick.key);
        assertEquals("https://w.wallhaven.cc/full/l8/wide.jpg", pick.imageUrl);
    }

    @Test
    public void emptyWallhavenReturnsNull() {
        assertNull(DailyFeeds.fromWallhaven("{\"data\":[]}"));
    }

    @Test
    public void savedSourceWinsOverTheDayRotation() {
        DailyPick zh = new DailyPick(DailyFeeds.BING_ZH, "bing-zh:1", "http://zh");
        DailyPick en = new DailyPick(DailyFeeds.BING_EN, "bing-en:1", "http://en");
        java.util.List<DailyPick> picks = java.util.Arrays.asList(zh, en);
        assertEquals(en, DailyFeeds.select(picks, DailyFeeds.BING_EN, 0));
        assertEquals(zh, DailyFeeds.select(picks, null, 2));
        assertNull(DailyFeeds.select(picks, DailyFeeds.ZZZMH, 0));
        assertEquals(DailyFeeds.BING_ZH, DailyFeeds.sourceOf("bing-zh:20261112"));
        assertNull(DailyFeeds.sourceOf("20261112"));
    }

    @Test
    public void dayRotatesAcrossSources() {
        assertEquals(0, DailyFeeds.start(3, 3));
        assertEquals(1, DailyFeeds.start(3, 4));
        assertEquals(2, DailyFeeds.start(3, 5));
    }
}
