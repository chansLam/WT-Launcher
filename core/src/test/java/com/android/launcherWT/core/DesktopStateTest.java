package com.android.launcherWT.core;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DesktopStateTest {
    @Test
    public void memoryStartsDailyAndTogglesHiddenAndMode() {
        DesktopState state = DesktopState.memory();
        assertEquals(WallpaperMode.DEFAULT, state.wallpaperMode());

        String key = new LaunchableApp("com.example", "com.example.Main", "Example").key();
        assertFalse(state.isHidden(key));

        state.setHidden(key, true);
        assertTrue(state.isHidden(key));

        state.setHidden(key, false);
        assertFalse(state.isHidden(key));

        state.setWallpaperMode(WallpaperMode.CUSTOM);
        assertEquals(WallpaperMode.CUSTOM, state.wallpaperMode());
    }

    @Test
    public void hidingKeepsSavedOrder() {
        DesktopState state = DesktopState.memory();
        String kept = "com.example/com.example.Kept";
        String hidden = "com.example/com.example.Hidden";
        state.setDisplayOrder(Arrays.asList(kept, hidden));

        state.setHidden(hidden, true);

        assertEquals(Arrays.asList(kept, hidden), state.displayOrder());
        state.setHidden(hidden, false);
        assertEquals(Arrays.asList(kept, hidden), state.displayOrder());
    }
}
