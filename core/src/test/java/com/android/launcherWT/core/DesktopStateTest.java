package com.android.launcherWT.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DesktopStateTest {
    @Test
    public void memoryStartsDailyAndTogglesHiddenAndMode() {
        DesktopState state = DesktopState.memory();
        assertEquals(WallpaperMode.DAILY, state.wallpaperMode());

        String key = new LaunchableApp("com.example", "com.example.Main", "Example").key();
        assertFalse(state.isHidden(key));

        state.setHidden(key, true);
        assertTrue(state.isHidden(key));

        state.setHidden(key, false);
        assertFalse(state.isHidden(key));

        state.setWallpaperMode(WallpaperMode.CUSTOM);
        assertEquals(WallpaperMode.CUSTOM, state.wallpaperMode());
    }
}
