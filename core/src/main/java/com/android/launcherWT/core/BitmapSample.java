package com.android.launcherWT.core;

public final class BitmapSample {
    private BitmapSample() {
    }

    /** Largest power of two that still leaves both sides at least the target size. */
    public static int of(int width, int height, int targetWidth, int targetHeight) {
        int sample = 1;
        if (width <= 0 || height <= 0 || targetWidth <= 0 || targetHeight <= 0) {
            return sample;
        }
        while (sample <= Integer.MAX_VALUE / 2
                && width / (sample * 2) >= targetWidth
                && height / (sample * 2) >= targetHeight) {
            sample *= 2;
        }
        return sample;
    }
}
