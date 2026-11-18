package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.List;

public final class ImageSearch {
    /** Home is 1920×720. Keep landscape images that can cover it without a heavy upscale. */
    private static final int MIN_WIDTH = 1280;
    private static final int MIN_HEIGHT = 720;
    private static final float MIN_RATIO = 1.5f;
    private static final float MAX_RATIO = 2.85f;

    public static final class Hit {
        public final String fullUrl;
        public final String thumbUrl;

        public Hit(String fullUrl, String thumbUrl) {
            this.fullUrl = fullUrl;
            this.thumbUrl = thumbUrl == null || thumbUrl.length() == 0 ? fullUrl : thumbUrl;
        }
    }

    private ImageSearch() {
    }

    public static List<Hit> parseBing(String html) {
        List<Hit> hits = new ArrayList<Hit>();
        if (html == null || html.length() == 0) {
            return hits;
        }
        int from = 0;
        while (hits.size() < 12) {
            int marker = indexOfMarker(html, "murl", from);
            if (marker < 0) {
                break;
            }
            String full = readUrl(html, marker);
            from = marker + 4;
            if (full == null) {
                continue;
            }
            String thumb = null;
            int thumbMarker = indexOfMarker(html, "turl", marker);
            if (thumbMarker > marker && thumbMarker < marker + 900) {
                thumb = readUrl(html, thumbMarker);
            }
            int windowEnd = Math.min(html.length(), marker + 2500);
            int width = readIntParam(html, "expw", marker, windowEnd);
            int height = readIntParam(html, "exph", marker, windowEnd);
            if (!suitable(width, height)) {
                continue;
            }
            hits.add(new Hit(full, thumb));
        }
        return hits;
    }

    /** Wallhaven search JSON. Original file is `path`; preview is the small thumb. */
    public static List<Hit> parseWallhaven(String json) {
        List<Hit> hits = new ArrayList<Hit>();
        if (json == null || json.length() == 0) {
            return hits;
        }
        int from = 0;
        while (hits.size() < 12) {
            int idAt = json.indexOf("\"id\":\"", from);
            if (idAt < 0) {
                break;
            }
            int windowEnd = Math.min(json.length(), idAt + 1800);
            int width = readJsonInt(json, "dimension_x", idAt, windowEnd);
            int height = readJsonInt(json, "dimension_y", idAt, windowEnd);
            String full = readJsonString(json, "\"path\":\"", idAt, windowEnd);
            String thumb = readJsonString(json, "\"small\":\"", idAt, windowEnd);
            from = idAt + 6;
            if (full == null || !suitable(width, height)) {
                continue;
            }
            hits.add(new Hit(full, thumb));
        }
        return hits;
    }

    public static final class ZzzCard {
        public final String id;
        public final String thumbUrl;

        public ZzzCard(String id, String thumbUrl) {
            this.id = id;
            this.thumbUrl = thumbUrl;
        }
    }

    /**
     * 极简壁纸 ultra (21:9) search HTML. Thumb size is the preview, so only the ratio is checked here.
     * The detail page carries the 1920-wide file.
     */
    public static List<ZzzCard> parseZzzmh(String html) {
        List<ZzzCard> cards = new ArrayList<ZzzCard>();
        if (html == null || html.length() == 0) {
            return cards;
        }
        int from = 0;
        while (cards.size() < 8) {
            int pidAt = html.indexOf("data-pid=\"", from);
            if (pidAt < 0) {
                break;
            }
            int idStart = pidAt + 10;
            int idEnd = html.indexOf('"', idStart);
            from = idStart;
            if (idEnd < 0) {
                break;
            }
            String id = html.substring(idStart, idEnd);
            int windowEnd = Math.min(html.length(), pidAt + 900);
            String thumb = quotedUrl(html, "src=\"", pidAt, windowEnd);
            int width = quotedInt(html, "width=\"", pidAt, windowEnd);
            int height = quotedInt(html, "height=\"", pidAt, windowEnd);
            if (thumb == null || !wideRatio(width, height) || seenZzz(cards, id)) {
                continue;
            }
            cards.add(new ZzzCard(id, thumb));
        }
        return cards;
    }

    /** Large image on a 极简壁纸 detail page. Returns null when it cannot cover the home screen. */
    public static Hit parseZzzmhDetail(String html, String thumbUrl) {
        if (html == null) {
            return null;
        }
        int from = 0;
        while (from < html.length()) {
            int at = html.indexOf("/view/large/", from);
            if (at < 0) {
                return null;
            }
            int http = html.lastIndexOf("http", at);
            if (http < 0 || at - http > 90) {
                from = at + 12;
                continue;
            }
            String full = readPlainUrl(html, http);
            int windowEnd = Math.min(html.length(), at + 350);
            int width = quotedInt(html, "width=\"", at, windowEnd);
            int height = quotedInt(html, "height=\"", at, windowEnd);
            from = at + 12;
            if (full != null && suitable(width, height)) {
                return new Hit(full, thumbUrl);
            }
        }
        return null;
    }

    /** Alternate providers so one host does not fill the whole row. */
    public static List<Hit> merge(List<Hit> first, List<Hit> second) {
        List<List<Hit>> groups = new ArrayList<List<Hit>>();
        groups.add(first);
        groups.add(second);
        return mergeAll(groups);
    }

    public static List<Hit> mergeAll(List<List<Hit>> groups) {
        List<Hit> hits = new ArrayList<Hit>();
        if (groups == null || groups.isEmpty()) {
            return hits;
        }
        int[] index = new int[groups.size()];
        boolean pending = true;
        while (hits.size() < 18 && pending) {
            pending = false;
            for (int g = 0; g < groups.size(); g++) {
                List<Hit> group = groups.get(g);
                if (group == null) {
                    continue;
                }
                if (index[g] < group.size()) {
                    addUnique(hits, group.get(index[g]));
                    index[g]++;
                    pending = true;
                    if (hits.size() >= 18) {
                        break;
                    }
                }
            }
        }
        return hits;
    }

    private static void addUnique(List<Hit> hits, Hit hit) {
        if (hit == null || hit.fullUrl == null) {
            return;
        }
        for (int i = 0; i < hits.size(); i++) {
            if (hit.fullUrl.equals(hits.get(i).fullUrl)) {
                return;
            }
        }
        hits.add(hit);
    }

    /**
     * Bing's own thumbnail host. The mm.bing.net CDN rejects the old TLS stack,
     * while www.bing.com serves the same image id.
     */
    public static String bingImage(String thumbUrl, int width, int height) {
        String id = imageId(thumbUrl);
        if (id == null) {
            return null;
        }
        return "https://www.bing.com/th?id=" + id + "&pid=15.1&w=" + width + "&h=" + height + "&c=7&rs=1";
    }

    static boolean suitable(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            return false;
        }
        return wideRatio(width, height);
    }

    private static boolean wideRatio(int width, int height) {
        if (width <= 0 || height <= 0) {
            return false;
        }
        float ratio = width / (float) height;
        return ratio >= MIN_RATIO && ratio <= MAX_RATIO;
    }

    private static boolean seenZzz(List<ZzzCard> cards, String id) {
        for (int i = 0; i < cards.size(); i++) {
            if (id.equals(cards.get(i).id)) {
                return true;
            }
        }
        return false;
    }

    private static String quotedUrl(String html, String marker, int from, int to) {
        int at = html.indexOf(marker, from);
        if (at < 0 || at > to) {
            return null;
        }
        return readPlainUrl(html, at + marker.length());
    }

    private static int quotedInt(String html, String marker, int from, int to) {
        int at = html.indexOf(marker, from);
        if (at < 0 || at > to) {
            return -1;
        }
        int start = at + marker.length();
        int end = start;
        while (end < html.length() && end < start + 6
                && html.charAt(end) >= '0' && html.charAt(end) <= '9') {
            end++;
        }
        if (end == start) {
            return -1;
        }
        try {
            return Integer.parseInt(html.substring(start, end));
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static String readPlainUrl(String html, int start) {
        if (start < 0 || start >= html.length() || !html.startsWith("http", start)) {
            return null;
        }
        int end = start;
        while (end < html.length()) {
            char c = html.charAt(end);
            if (c == '"' || c == '\'' || c == ' ' || c == ')' || c == '<') {
                break;
            }
            end++;
        }
        if (end == start) {
            return null;
        }
        return html.substring(start, end);
    }

    private static int readJsonInt(String json, String name, int from, int to) {
        int at = json.indexOf("\"" + name + "\":", from);
        if (at < 0 || at > to) {
            return -1;
        }
        int start = at + name.length() + 3;
        int end = start;
        while (end < json.length() && end < start + 6
                && json.charAt(end) >= '0' && json.charAt(end) <= '9') {
            end++;
        }
        if (end == start) {
            return -1;
        }
        try {
            return Integer.parseInt(json.substring(start, end));
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static String readJsonString(String json, String marker, int from, int to) {
        int at = json.indexOf(marker, from);
        if (at < 0 || at > to) {
            return null;
        }
        int start = at + marker.length();
        StringBuilder url = new StringBuilder();
        int end = start;
        while (end < json.length()) {
            char c = json.charAt(end);
            if (c == '"') {
                break;
            }
            if (c == '\\' && end + 1 < json.length() && json.charAt(end + 1) == '/') {
                url.append('/');
                end += 2;
                continue;
            }
            url.append(c);
            end++;
        }
        if (url.length() == 0) {
            return null;
        }
        return url.toString();
    }

    private static int readIntParam(String html, String name, int from, int to) {
        int at = html.indexOf(name + "=", from);
        if (at < 0 || at > to) {
            return -1;
        }
        int start = at + name.length() + 1;
        int end = start;
        while (end < html.length() && end < start + 6 && html.charAt(end) >= '0' && html.charAt(end) <= '9') {
            end++;
        }
        if (end == start) {
            return -1;
        }
        try {
            return Integer.parseInt(html.substring(start, end));
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static String imageId(String url) {
        if (url == null) {
            return null;
        }
        if (url.indexOf("mm.bing.net") < 0 && url.indexOf("bing.com/th") < 0) {
            return null;
        }
        int at = url.indexOf("id=");
        if (at < 0) {
            return null;
        }
        int start = at + 3;
        int end = start;
        while (end < url.length()) {
            char c = url.charAt(end);
            if (c == '&' || c == '#' || c == '"') {
                break;
            }
            end++;
        }
        if (end <= start) {
            return null;
        }
        return url.substring(start, end);
    }

    private static int indexOfMarker(String html, String name, int from) {
        int quoted = html.indexOf("\"" + name + "\"", from);
        int escaped = html.indexOf("&quot;" + name + "&quot;", from);
        if (quoted < 0) {
            return escaped;
        }
        if (escaped < 0) {
            return quoted;
        }
        return Math.min(quoted, escaped);
    }

    private static String readUrl(String html, int marker) {
        int http = html.indexOf("http", marker);
        if (http < 0 || http > marker + 48) {
            return null;
        }
        StringBuilder url = new StringBuilder();
        int end = http;
        while (end < html.length()) {
            if (html.startsWith("&quot;", end) || html.startsWith("&amp;quot;", end)) {
                break;
            }
            if (html.startsWith("&amp;", end)) {
                url.append('&');
                end += 5;
                continue;
            }
            char c = html.charAt(end);
            if (c == '"' || c == '\\' || c == '&' || c == ' ' || c == '<') {
                break;
            }
            url.append(c);
            end++;
        }
        if (url.length() == 0) {
            return null;
        }
        return url.toString().replace("\\u0026", "&");
    }

    /** UP.UPX8 keyword search: flat JSON array with url and title per item. */
    public static List<Hit> parseUpx8(String json) {
        List<Hit> hits = new ArrayList<Hit>();
        if (json == null) {
            return hits;
        }
        int from = 0;
        while (hits.size() < 6) {
            int at = json.indexOf("\"url\":\"", from);
            if (at < 0) {
                break;
            }
            int start = at + 7;
            int end = json.indexOf(34, start);
            if (end < 0) {
                break;
            }
            String url = json.substring(start, end).replace("\\/", "/");
            from = end;
            if (url.startsWith("http")) {
                hits.add(new Hit(url, url));
            }
        }
        return hits;
    }
}
