package com.android.launcherWT.wallpaper;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * 壁纸站点清单 + 下载入口。真正的网络实现搬到了 {@link WallpaperHttp}
 * （conscrypt TLS + 现代 CA 包 + IPv4 优先），这里只保留各接口地址与一次重试。
 *
 * <p>注意：这里列出的地址与 75Play 现场可用的一致，改地址前先确认车机能不能连。</p>
 */
public final class BingClient {
    public static final String DAILY_URL =
            "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    public static final String DAILY_URL_EN =
            "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
    public static final String DAILY_WALLHAVEN =
            "https://wallhaven.cc/api/v1/search?sorting=toplist&topRange=1d&purity=100"
                    + "&atleast=1920x720&ratios=16x9,21x9";
    public static final String PEAPIX_FEED = "https://peapix.com/bing/feed?country=cn&n=1";
    public static final String TODAYBING_TODAY =
            "https://www.todaybing.com/api/v1/today?formate=json&size=fhd";
    public static final String VSAA_DAILY =
            "https://api.vsaa.cn/api/toolbox/wallpaper/wallpaper.php?n=1&idx=0&uhd=0&mkt=zh-CN";
    public static final String UPX8_RANDOM =
            "https://wp.upx8.com/api.php?format=json&resolution=1920x1080&count=3";
    public static final String YUMUS_BING = "https://www.yumus.cn/api/?brand=bing&ua=pc";

    private static final String TAG = "BingClient";

    private BingClient() {
    }

    public static String fetchJson(Context context, String url) {
        return fetchText(context, url);
    }

    /** 一次 TLS 抖动不该让整张壁纸失败，重试一次。 */
    public static String fetchText(Context context, String url) {
        String body = WallpaperHttp.text(context, url);
        if (body != null && body.length() > 0) {
            return body;
        }
        Log.w(TAG, "retry " + url);
        return WallpaperHttp.text(context, url);
    }

    public static boolean download(Context context, String url, File destination) {
        if (WallpaperHttp.download(context, url, destination)) {
            return true;
        }
        Log.w(TAG, "retry download " + url);
        return WallpaperHttp.download(context, url, destination);
    }

    /** 车机网络自检：逐个探一遍壁纸相关站点，返回多行结果。 */
    public static String probeAll(Context context) {
        StringBuilder out = new StringBuilder();
        appendProbe(out, context, "必应(www)", DAILY_URL);
        appendProbe(out, context, "必应(cn)", "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN");
        appendProbe(out, context, "VSAA", VSAA_DAILY);
        appendProbe(out, context, "UPX8", UPX8_RANDOM);
        appendProbe(out, context, "语幕", YUMUS_BING);
        appendProbe(out, context, "TodayBing", TODAYBING_TODAY);
        appendProbe(out, context, "站酷", "https://bz.zzzmh.cn/gallery?r=ultra");
        appendProbe(out, context, "Peapix", PEAPIX_FEED);
        appendProbe(out, context, "Wallhaven", DAILY_WALLHAVEN);
        return out.toString();
    }

    private static void appendProbe(StringBuilder out, Context context, String label, String url) {
        String line = WallpaperHttp.probe(context, label, url);
        Log.i(TAG, "probe " + line);
        if (out.length() > 0) {
            out.append('\n');
        }
        out.append(line);
    }
}
