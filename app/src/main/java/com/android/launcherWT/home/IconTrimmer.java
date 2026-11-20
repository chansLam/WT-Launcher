package com.android.launcherWT.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

/**
 * Preset icons ship on a 136px canvas with a wide transparent border (map keeps
 * only 72 of 136 px of artwork), so FIT_CENTER draws them visibly smaller than
 * a third party icon that fills its box. Cropping the empty border makes the
 * artwork fill the same cell box the installed-app icons use.
 */
public final class IconTrimmer {

    private static final SparseArray<Drawable> CACHE = new SparseArray<Drawable>();

    /** Above this coverage the icon already fills its canvas and stays untouched. */
    private static final float FULL_COVERAGE = 0.92f;

    private IconTrimmer() {
    }

    @SuppressWarnings("deprecation")
    public static Drawable preset(Context context, int resId) {
        Drawable cached = CACHE.get(resId);
        if (cached != null) {
            return cached;
        }
        Drawable raw = context.getResources().getDrawable(resId);
        Drawable result = raw;
        try {
            result = trim(context, raw);
        } catch (Throwable ignored) {
            result = raw;
        }
        CACHE.put(resId, result);
        return result;
    }

    private static Drawable trim(Context context, Drawable raw) {
        int width = raw.getIntrinsicWidth();
        int height = raw.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            return raw;
        }
        Bitmap source = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(source);
        raw.setBounds(0, 0, width, height);
        raw.draw(canvas);

        int minX = width;
        int minY = height;
        int maxX = -1;
        int maxY = -1;
        int[] row = new int[width];
        for (int y = 0; y < height; y++) {
            source.getPixels(row, 0, width, 0, y, width, 1);
            for (int x = 0; x < width; x++) {
                if ((row[x] >>> 24) != 0) {
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }
        if (maxX < minX || maxY < minY) {
            source.recycle();
            return raw;
        }
        int artWidth = maxX - minX + 1;
        int artHeight = maxY - minY + 1;
        if (artWidth >= width * FULL_COVERAGE && artHeight >= height * FULL_COVERAGE) {
            source.recycle();
            return raw;
        }
        Bitmap cropped = Bitmap.createBitmap(source, minX, minY, artWidth, artHeight);
        source.recycle();
        BitmapDrawable result = new BitmapDrawable(context.getResources(), cropped);
        result.setBounds(0, 0, artWidth, artHeight);
        return result;
    }
}
