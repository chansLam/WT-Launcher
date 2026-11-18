package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.List;

/**
 * One row of the recommended wallpapers. Wallhaven filters by category bitmask
 * (general 100 / anime 010 / people 001), the other hosts take a keyword.
 */
public final class WallpaperCategory {
    public final String id;
    /** Wallhaven category bitmask, empty when every category is wanted. */
    public final String wallhavenCategories;
    /** Wallhaven keyword, empty for a plain toplist. */
    public final String wallhavenQuery;
    /** Keyword for Bing image search. */
    public final String bingQuery;
    /** Keyword for the UPX8 JSON feed. */
    public final String upx8Query;

    public WallpaperCategory(String id, String wallhavenCategories, String wallhavenQuery,
            String bingQuery, String upx8Query) {
        this.id = id;
        this.wallhavenCategories = wallhavenCategories == null ? "" : wallhavenCategories;
        this.wallhavenQuery = wallhavenQuery == null ? "" : wallhavenQuery;
        this.bingQuery = bingQuery == null ? "" : bingQuery;
        this.upx8Query = upx8Query == null ? "" : upx8Query;
    }

    public static final String POPULAR = "popular";
    public static final String LANDSCAPE = "landscape";
    public static final String CITY = "city";
    public static final String NIGHT = "night";
    public static final String ANIME = "anime";
    public static final String CAR = "car";
    public static final String ABSTRACT = "abstract";

    public static List<WallpaperCategory> all() {
        List<WallpaperCategory> list = new ArrayList<WallpaperCategory>();
        list.add(new WallpaperCategory(POPULAR, "", "",
                "4k ultrawide wallpaper landscape nature", "风景"));
        list.add(new WallpaperCategory(LANDSCAPE, "100", "",
                "nature landscape wallpaper 4k wide", "风景"));
        list.add(new WallpaperCategory(CITY, "100", "city",
                "city skyline wallpaper 4k wide", "城市"));
        list.add(new WallpaperCategory(NIGHT, "100", "night",
                "night city wallpaper 4k ultrawide", "夜景"));
        list.add(new WallpaperCategory(ANIME, "010", "",
                "anime wallpaper 4k wide", "二次元"));
        list.add(new WallpaperCategory(CAR, "100", "car",
                "car wallpaper 4k wide", "汽车"));
        list.add(new WallpaperCategory(ABSTRACT, "100", "abstract",
                "abstract wallpaper 4k ultrawide", "抽象"));
        return list;
    }
}
