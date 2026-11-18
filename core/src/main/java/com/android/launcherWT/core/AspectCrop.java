package com.android.launcherWT.core;

/** Center crop a source image so it fills a target box without stretching. */
public final class AspectCrop {
    private AspectCrop() {
    }

    /** Returns {left, top, width, height} inside the source that matches the target ratio. */
    public static int[] rect(int width, int height, int targetWidth, int targetHeight) {
        if (width <= 0 || height <= 0 || targetWidth <= 0 || targetHeight <= 0) {
            return new int[] {0, 0, width, height};
        }
        double sourceRatio = (double) width / (double) height;
        double targetRatio = (double) targetWidth / (double) targetHeight;
        if (sourceRatio > targetRatio) {
            int cropWidth = (int) Math.round(height * targetRatio);
            if (cropWidth <= 0 || cropWidth > width) {
                cropWidth = width;
            }
            return new int[] {(width - cropWidth) / 2, 0, cropWidth, height};
        }
        if (sourceRatio < targetRatio) {
            int cropHeight = (int) Math.round(width / targetRatio);
            if (cropHeight <= 0 || cropHeight > height) {
                cropHeight = height;
            }
            return new int[] {0, (height - cropHeight) / 2, width, cropHeight};
        }
        return new int[] {0, 0, width, height};
    }
}
