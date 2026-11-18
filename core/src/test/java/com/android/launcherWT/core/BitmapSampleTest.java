package com.android.launcherWT.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitmapSampleTest {
    @Test
    public void keepsAPhotoThatIsOnlyALittleLargerThanTheScreen() {
        assertEquals(1, BitmapSample.of(2992, 2000, 1920, 720));
    }

    @Test
    public void halvesAnImageThatIsStillSharpAtHalfSize() {
        assertEquals(2, BitmapSample.of(4000, 3000, 1920, 720));
    }
}
