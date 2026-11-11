package com.android.launcherWT.core;

public final class BingDailyImage {
    public final String startdate;
    public final String imageUrl;

    public BingDailyImage(String startdate, String imageUrl) {
        this.startdate = startdate;
        this.imageUrl = imageUrl;
    }

    public static BingDailyImage parse(String json) {
        if (json == null) {
            return null;
        }
        String startdate = quotedValue(json, "startdate");
        String url = quotedValue(json, "url");
        if (startdate == null || url == null) {
            return null;
        }
        String imageUrl = url.startsWith("http") ? url : "https://www.bing.com" + url;
        return new BingDailyImage(startdate, imageUrl);
    }

    private static String quotedValue(String json, String field) {
        String marker = "\"" + field + "\":\"";
        int start = json.indexOf(marker);
        if (start < 0) {
            return null;
        }
        int valueStart = start + marker.length();
        int valueEnd = json.indexOf('"', valueStart);
        if (valueEnd < 0) {
            return null;
        }
        return json.substring(valueStart, valueEnd);
    }
}
