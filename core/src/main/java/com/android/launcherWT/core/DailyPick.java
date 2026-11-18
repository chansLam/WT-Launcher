package com.android.launcherWT.core;

/** One day's wallpaper from a named provider. */
public final class DailyPick {
    public final String source;
    public final String key;
    public final String imageUrl;

    public DailyPick(String source, String key, String imageUrl) {
        this.source = source;
        this.key = key;
        this.imageUrl = imageUrl;
    }
}
