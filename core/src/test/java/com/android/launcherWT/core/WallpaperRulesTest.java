package com.android.launcherWT.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WallpaperRulesTest {
    @Test
    public void savedCustomImageSelectsCustom() {
        assertEquals(WallpaperMode.CUSTOM, WallpaperRules.afterCustomImage(true));
    }

    @Test
    public void failedCustomImageKeepsDaily() {
        assertEquals(WallpaperMode.DAILY, WallpaperRules.afterCustomImage(false));
    }

    @Test
    public void dailyDownloadsOnlyWhenDatesDiffer() {
        assertTrue(WallpaperRules.shouldDownload(WallpaperMode.DAILY, "20261110", "20261111"));
        assertFalse(WallpaperRules.shouldDownload(WallpaperMode.DAILY, "20261111", "20261111"));
    }

    @Test
    public void customModeDoesNotDownload() {
        assertFalse(WallpaperRules.shouldDownload(WallpaperMode.CUSTOM, "20261110", "20261111"));
    }

    @Test
    public void missingRemoteDateDoesNotDownload() {
        assertFalse(WallpaperRules.shouldDownload(WallpaperMode.DAILY, "20261110", null));
    }
}
