package com.android.launcherWT.core;

import java.util.List;

public final class DailyFeeds {
    public static final String BING_ZH = "bing-zh";
    public static final String BING_EN = "bing-en";
    public static final String WALLHAVEN = "wallhaven";
    public static final String ZZZMH = "zzzmh";
    public static final String PEAPIX = "peapix";
    public static final String TODAYBING = "todaybing";
    public static final String UPX8 = "upx8";
    public static final String YUMUS = "yumus";
    public static final String VSAA = "vsaa";

    private DailyFeeds() {
    }

    public static DailyPick fromBing(String json, String source) {
        BingDailyImage image = BingDailyImage.parse(json);
        if (image == null || source == null) {
            return null;
        }
        return new DailyPick(source, source + ":" + image.startdate, image.imageUrl);
    }

    public static DailyPick fromWallhaven(String json) {
        List<ImageSearch.Hit> hits = ImageSearch.parseWallhaven(json);
        if (hits.isEmpty()) {
            return null;
        }
        String full = hits.get(0).fullUrl;
        String id = idBefore(json, full);
        return new DailyPick(WALLHAVEN, "wallhaven:" + (id == null ? full : id), full);
    }

    public static DailyPick fromZzzmh(String id, String imageUrl) {
        if (id == null || id.length() == 0 || imageUrl == null || imageUrl.length() == 0) {
            return null;
        }
        return new DailyPick(ZZZMH, "zzzmh:" + id, imageUrl);
    }

    /** Prefix stored on a downloaded image, such as {@code bing-zh} from {@code bing-zh:20261112}. */
    public static String sourceOf(String key) {
        if (key == null) {
            return null;
        }
        int colon = key.indexOf(':');
        if (colon <= 0) {
            return null;
        }
        return key.substring(0, colon);
    }

    /** The saved source when it is present, otherwise the source for this day. */
    public static DailyPick select(List<DailyPick> picks, String source, int dayOfYear) {
        if (picks == null || picks.isEmpty()) {
            return null;
        }
        if (source != null && source.length() > 0) {
            for (int i = 0; i < picks.size(); i++) {
                DailyPick pick = picks.get(i);
                if (pick != null && source.equals(pick.source)) {
                    return pick;
                }
            }
            return null;
        }
        return picks.get(start(picks.size(), dayOfYear));
    }

    /** Stable index for this day, so the same date keeps one source and the next day moves on. */
    public static int start(int count, int dayOfYear) {
        if (count <= 0) {
            return 0;
        }
        int day = dayOfYear % count;
        return day < 0 ? day + count : day;
    }

    private static String idBefore(String json, String fullUrl) {
        if (json == null || fullUrl == null) {
            return null;
        }
        String escaped = fullUrl.replace("/", "\\/");
        int path = json.indexOf(escaped);
        if (path < 0) {
            path = json.indexOf(fullUrl);
        }
        if (path < 0) {
            return null;
        }
        int idAt = json.lastIndexOf("\"id\":\"", path);
        if (idAt < 0 || path - idAt > 500) {
            return null;
        }
        int start = idAt + 6;
        int end = json.indexOf('"', start);
        if (end < 0) {
            return null;
        }
        return json.substring(start, end);
    }

    /** Peapix bing feed: 1920px jpg plus date, title and copyright in JSON. */
    public static DailyPick fromPeapix(String json) {
        String full = quotedValue(json, "fullUrl");
        String date = quotedValue(json, "date");
        if (full == null || date == null) {
            return null;
        }
        return new DailyPick(PEAPIX, PEAPIX + ":" + date, unescape(full));
    }

    /** TodayBing /api/v1/today?formate=json wraps the image in a data object. */
    public static DailyPick fromTodayBing(String json) {
        String image = quotedValue(json, "image");
        String date = quotedValue(json, "date");
        if (image == null || date == null) {
            return null;
        }
        return new DailyPick(TODAYBING, TODAYBING + ":" + date, unescape(image));
    }

    /** UP.UPX8 picks a random picture, so the key is the day to keep one cache entry. */
    public static DailyPick fromUpx8(String json, String day) {
        String url = quotedValue(json, "url");
        if (url == null || day == null) {
            return null;
        }
        return new DailyPick(UPX8, UPX8 + ":" + day, unescape(url));
    }

    /** Yumus returns the image directly, so the pick only carries the day. */
    public static DailyPick fromYumus(String imageUrl, String day) {
        if (imageUrl == null || day == null) {
            return null;
        }
        return new DailyPick(YUMUS, YUMUS + ":" + day, imageUrl);
    }

    /** VSAA toolbox wallpaper: {"code":1,"data":{"url":"https://..."}}. */
    public static DailyPick fromVsaa(String json, String day) {
        if (json == null || day == null || json.indexOf("\"code\":1") < 0) {
            return null;
        }
        String url = quotedValue(json, "url");
        if (url == null) {
            return null;
        }
        return new DailyPick(VSAA, VSAA + ":" + day, unescape(url));
    }

    private static String quotedValue(String json, String field) {
        if (json == null) {
            return null;
        }
        String marker = "\"" + field + "\":\"";
        int at = json.indexOf(marker);
        if (at < 0) {
            return null;
        }
        int start = at + marker.length();
        int end = json.indexOf(34, start);
        if (end < 0) {
            return null;
        }
        return json.substring(start, end);
    }

    private static String unescape(String url) {
        if (url == null) {
            return null;
        }
        return url.replace("\\/", "/");
    }
}
