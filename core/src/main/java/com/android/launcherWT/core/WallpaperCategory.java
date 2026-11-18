package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.List;

/**
 * One row of the recommended wallpapers. Every category carries several keywords
 * so the row can pull a full batch: UPX8 returns a JSON list per keyword,
 * Wallhaven filters by category bitmask (general 100 / anime 010 / people 001),
 * Bing image search takes one wide keyword.
 */
public final class WallpaperCategory {
    public final String id;
    /** Wallhaven category bitmask, empty when every category is wanted. */
    public final String wallhavenCategories;
    /** Wallhaven keyword, empty for a plain toplist. */
    public final String wallhavenQuery;
    /** Keyword for Bing image search. */
    public final String bingQuery;
    /** Keywords for the UPX8 JSON feed; every one is fetched. */
    public final String[] keywords;

    public WallpaperCategory(String id, String wallhavenCategories, String wallhavenQuery,
            String bingQuery, String[] keywords) {
        this.id = id;
        this.wallhavenCategories = wallhavenCategories == null ? "" : wallhavenCategories;
        this.wallhavenQuery = wallhavenQuery == null ? "" : wallhavenQuery;
        this.bingQuery = bingQuery == null ? "" : bingQuery;
        this.keywords = keywords == null ? new String[0] : keywords;
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
                "4k ultrawide wallpaper landscape nature",
                new String[] { "风景", "壁纸", "星空", "山川" }));
        list.add(new WallpaperCategory(LANDSCAPE, "100", "",
                "nature landscape wallpaper 4k wide",
                new String[] { "风景", "山水", "草原", "森林" }));
        list.add(new WallpaperCategory(CITY, "100", "city",
                "city skyline wallpaper 4k wide",
                new String[] { "城市", "都市", "建筑", "街景" }));
        list.add(new WallpaperCategory(NIGHT, "100", "night",
                "night city wallpaper 4k ultrawide",
                new String[] { "夜景", "星空", "极光", "夜色" }));
        list.add(new WallpaperCategory(ANIME, "010", "",
                "anime wallpaper 4k wide",
                new String[] { "二次元", "动漫", "动漫少女", "插画" }));
        list.add(new WallpaperCategory(CAR, "100", "car",
                "car wallpaper 4k wide",
                new String[] { "汽车", "跑车", "机车", "豪车" }));
        list.add(new WallpaperCategory(ABSTRACT, "100", "abstract",
                "abstract wallpaper 4k ultrawide",
                new String[] { "抽象", "几何", "渐变", "极简" }));
        return list;
    }
}
