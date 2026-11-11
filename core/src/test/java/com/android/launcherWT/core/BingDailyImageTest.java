package com.android.launcherWT.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class BingDailyImageTest {
    private static final String SAMPLE =
            "{\"images\":[{\"startdate\":\"20261111\",\"url\":\"/th?id=OHR.Test\"}]}";

    @Test
    public void parsesStartdateAndAbsoluteImageUrl() {
        BingDailyImage image = BingDailyImage.parse(SAMPLE);
        assertEquals("20261111", image.startdate);
        assertEquals("https://www.bing.com/th?id=OHR.Test", image.imageUrl);
    }

    @Test
    public void emptyJsonReturnsNull() {
        assertNull(BingDailyImage.parse("{}"));
    }

    @Test
    public void sameStartdateDoesNotDownloadAgain() {
        BingDailyImage first = BingDailyImage.parse(SAMPLE);
        BingDailyImage again = BingDailyImage.parse(SAMPLE);
        assertFalse(WallpaperRules.shouldDownload(
                WallpaperMode.DAILY, first.startdate, again.startdate));
    }
}
