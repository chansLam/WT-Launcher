package com.android.launcherWT.core;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ImageSearchTest {
    @Test
    public void readsEscapedBingImageUrls() {
        String html = "m={&quot;murl&quot;:&quot;https://example.com/a.jpg&quot;,"
                + "&quot;turl&quot;:&quot;https://example.com/a-thumb.jpg&quot;,"
                + "exph=1080&amp;expw=1920}";
        List<ImageSearch.Hit> hits = ImageSearch.parseBing(html);
        assertEquals(1, hits.size());
        assertEquals("https://example.com/a.jpg", hits.get(0).fullUrl);
        assertEquals("https://example.com/a-thumb.jpg", hits.get(0).thumbUrl);
    }

    @Test
    public void keepsQueryParametersEncodedAsAmp() {
        String html = "&quot;murl&quot;:&quot;https://images.example.com/a.jpeg?cs=srgb&amp;fm=jpg&quot;"
                + " exph=720&amp;expw=1920";
        List<ImageSearch.Hit> hits = ImageSearch.parseBing(html);
        assertEquals(1, hits.size());
        assertEquals("https://images.example.com/a.jpeg?cs=srgb&fm=jpg", hits.get(0).fullUrl);
    }

    @Test
    public void rewritesThumbnailOntoWwwBing() {
        String thumb = "https://ts2.mm.bing.net/th?id=OIP.dI22OgiQxCFyDNQ2PaNtvQHaE8&pid=15.1";
        assertEquals(
                "https://www.bing.com/th?id=OIP.dI22OgiQxCFyDNQ2PaNtvQHaE8&pid=15.1&w=240&h=240&c=7&rs=1",
                ImageSearch.bingImage(thumb, 240, 240));
    }

    @Test
    public void keepsOnlyImagesThatCoverTheWideHome() {
        String html = hit("https://example.com/wide.jpg", 1920, 1080)
                + hit("https://example.com/portrait.jpg", 1080, 1920)
                + hit("https://example.com/tiny.jpg", 640, 480)
                + hit("https://example.com/banner.jpg", 3200, 800)
                + hit("https://example.com/screen.jpg", 1920, 720);
        List<ImageSearch.Hit> hits = ImageSearch.parseBing(html);
        assertEquals(2, hits.size());
        assertEquals("https://example.com/wide.jpg", hits.get(0).fullUrl);
        assertEquals("https://example.com/screen.jpg", hits.get(1).fullUrl);
    }

    @Test
    public void readsWallhavenOriginalAndDropsSmallImages() {
        String json = "{\"data\":["
                + item("wide", 3840, 2160, "https:\\/\\/w.wallhaven.cc\\/full\\/l8\\/wide.jpg",
                "https:\\/\\/th.wallhaven.cc\\/small\\/l8\\/wide.jpg")
                + ","
                + item("tiny", 800, 600, "https:\\/\\/w.wallhaven.cc\\/full\\/xx\\/tiny.jpg",
                "https:\\/\\/th.wallhaven.cc\\/small\\/xx\\/tiny.jpg")
                + "]}";
        List<ImageSearch.Hit> hits = ImageSearch.parseWallhaven(json);
        assertEquals(1, hits.size());
        assertEquals("https://w.wallhaven.cc/full/l8/wide.jpg", hits.get(0).fullUrl);
        assertEquals("https://th.wallhaven.cc/small/l8/wide.jpg", hits.get(0).thumbUrl);
    }

    @Test
    public void interleavesBingAndWallhaven() {
        List<ImageSearch.Hit> bing = new java.util.ArrayList<ImageSearch.Hit>();
        bing.add(new ImageSearch.Hit("https://bing.example/a.jpg", null));
        bing.add(new ImageSearch.Hit("https://bing.example/b.jpg", null));
        List<ImageSearch.Hit> haven = new java.util.ArrayList<ImageSearch.Hit>();
        haven.add(new ImageSearch.Hit("https://w.wallhaven.cc/a.jpg", null));
        List<ImageSearch.Hit> hits = ImageSearch.merge(bing, haven);
        assertEquals(3, hits.size());
        assertEquals("https://bing.example/a.jpg", hits.get(0).fullUrl);
        assertEquals("https://w.wallhaven.cc/a.jpg", hits.get(1).fullUrl);
        assertEquals("https://bing.example/b.jpg", hits.get(2).fullUrl);
    }

    @Test
    public void readsZzzmhUltraCardsAndDetail() {
        String html = "<a class=\"card\" href=\"/wallpaper/abc123\" data-pid=\"abc123\">"
                + "<img src=\"https://imageai.zzzmh.cn/wallpaper/ultra/view/small/aa.webp\" width=\"648\" height=\"270\">"
                + "</a><a data-pid=\"tall\"><img src=\"https://imageai.zzzmh.cn/t.webp\" width=\"400\" height=\"640\"></a>";
        List<ImageSearch.ZzzCard> cards = ImageSearch.parseZzzmh(html);
        assertEquals(1, cards.size());
        assertEquals("abc123", cards.get(0).id);
        String detail = "<img src=\"https://imageai.zzzmh.cn/wallpaper/ultra/view/large/bb.webp\" width=\"1920\" height=\"800\">";
        ImageSearch.Hit hit = ImageSearch.parseZzzmhDetail(detail, cards.get(0).thumbUrl);
        assertEquals("https://imageai.zzzmh.cn/wallpaper/ultra/view/large/bb.webp", hit.fullUrl);
        assertEquals("https://imageai.zzzmh.cn/wallpaper/ultra/view/small/aa.webp", hit.thumbUrl);
    }

    private static String item(String id, int width, int height, String path, String thumb) {
        return "{\"id\":\"" + id + "\",\"dimension_x\":" + width + ",\"dimension_y\":" + height
                + ",\"path\":\"" + path + "\",\"thumbs\":{\"small\":\"" + thumb + "\"}}";
    }

    private static String hit(String url, int width, int height) {
        return "&quot;murl&quot;:&quot;" + url + "&quot; exph=" + height + "&amp;expw=" + width + " ";
    }
}
