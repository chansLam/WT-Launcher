package com.android.launcherWT.core;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class NewWallpaperSourcesTest {
    @Test
    public void parsesPeapixFeed() {
        String json = "[{\"title\":\"Owls\",\"copyright\":\"(c) Getty\","
                + "\"fullUrl\":\"https://img.peapix.com/abc_1920.jpg\","
                + "\"thumbUrl\":\"https://img.peapix.com/abc_640.jpg\","
                + "\"date\":\"2026-11-18\"}]";
        DailyPick pick = DailyFeeds.fromPeapix(json);
        assertEquals(DailyFeeds.PEAPIX, pick.source);
        assertEquals("peapix:2026-11-18", pick.key);
        assertEquals("https://img.peapix.com/abc_1920.jpg", pick.imageUrl);
    }

    @Test
    public void parsesTodayBingJson() {
        String json = "{\"data\":{\"title\":\"从黄昏狩猎到黎明\",\"copyright\":\"(c) Zahoor\","
                + "\"date\":\"2026-11-18\",\"image\":\"https://webp.todaybing.com/a.webp\",\"size\":\"fhd\"}}";
        DailyPick pick = DailyFeeds.fromTodayBing(json);
        assertEquals("todaybing:2026-11-18", pick.key);
        assertEquals("https://webp.todaybing.com/a.webp", pick.imageUrl);
    }

    @Test
    public void parsesUpx8JsonWithEscapedSlashes() {
        String json = "{\"code\":200,\"data\":[{\"url\":\"http:\\/\\/cdn.example\\/a.jpg\","
                + "\"width\":0,\"title\":\"\",\"source\":\"birdpaper\"}]}";
        DailyPick pick = DailyFeeds.fromUpx8(json, "20261118");
        assertEquals("upx8:20261118", pick.key);
        assertEquals("http://cdn.example/a.jpg", pick.imageUrl);
    }

    @Test
    public void upx8SearchReturnsHits() {
        String json = "{\"code\":200,\"data\":[{\"url\":\"http:\\/\\/cdn.example\\/a.jpg\"},"
                + "{\"url\":\"http:\\/\\/cdn.example\\/b.jpg\"}]}";
        List<ImageSearch.Hit> hits = ImageSearch.parseUpx8(json);
        assertEquals(2, hits.size());
        assertEquals("http://cdn.example/a.jpg", hits.get(0).fullUrl);
    }

    @Test
    public void yumusCarriesOnlyTheDay() {
        DailyPick pick = DailyFeeds.fromYumus("https://www.yumus.cn/api/?brand=bing&ua=pc", "20261118");
        assertEquals("yumus:20261118", pick.key);
    }

    @Test
    public void rejectsBrokenPayloads() {
        assertNull(DailyFeeds.fromPeapix(null));
        assertNull(DailyFeeds.fromTodayBing("{}"));
        assertNull(DailyFeeds.fromUpx8(null, "20261118"));
        assertTrue(ImageSearch.parseUpx8("{}").isEmpty());
    }

    @Test
    public void cropsWideImageToScreenRatio() {
        int[] rect = AspectCrop.rect(3840, 2160, 1920, 720);
        assertEquals(0, rect[0]);
        assertEquals(360, rect[1]);
        assertEquals(3840, rect[2]);
        assertEquals(1440, rect[3]);
    }

    @Test
    public void keepsMatchingRatio() {
        int[] rect = AspectCrop.rect(1920, 720, 1920, 720);
        assertEquals(0, rect[0]);
        assertEquals(0, rect[1]);
        assertEquals(1920, rect[2]);
        assertEquals(720, rect[3]);
    }

    @Test
    public void cropsTallImageHorizontally() {
        int[] rect = AspectCrop.rect(1080, 1920, 1920, 720);
        assertEquals(1080, rect[2]);
        assertEquals(405, rect[3]);
        assertEquals(0, rect[0]);
        assertEquals(757, rect[1]);
    }
}
