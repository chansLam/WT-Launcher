package com.autopai.common.utils.common;

/* loaded from: classes.dex */
public final class Utilities {
    public static final java.lang.String ALLOW_ROTATION_PREFERENCE_KEY = "pref_allowRotation";
    public static final boolean ATLEAST_JB_MR1;
    public static final boolean ATLEAST_JB_MR2;
    public static final boolean ATLEAST_KITKAT;
    public static final boolean ATLEAST_LOLLIPOP;
    public static final boolean ATLEAST_LOLLIPOP_MR1;
    public static final boolean ATLEAST_MARSHMALLOW;
    public static final boolean ATLEAST_O;
    public static final boolean ATLEAST_P;
    public static final android.util.DisplayMetrics DM;
    private static final java.lang.String FORCE_ENABLE_ROTATION_PROPERTY = "launcher_force_rotate";
    private static final java.lang.String TAG = "Launcher.Utilities";
    public static final boolean USE_LOADINGVIEW = false;
    private static float mCornerRadius;
    static int sColorIndex;
    static int[] sColors;
    private static boolean sForceEnableRotation;
    private static final int[] sLoc0;
    private static final int[] sLoc1;
    private static final android.graphics.Rect sOldBounds = new android.graphics.Rect();
    private static final android.graphics.Canvas sCanvas = new android.graphics.Canvas();
    private static final java.util.regex.Pattern sTrimPattern = java.util.regex.Pattern.compile("^[\\s|\\p{javaSpaceChar}]*(.*)[\\s|\\p{javaSpaceChar}]*$");

    static {
        sCanvas.setDrawFilter(new android.graphics.PaintFlagsDrawFilter(4, 2));
        sColors = new int[]{android.support.v4.internal.view.SupportMenu.CATEGORY_MASK, -16711936, -16776961};
        sColorIndex = 0;
        sLoc0 = new int[2];
        sLoc1 = new int[2];
        ATLEAST_MARSHMALLOW = android.os.Build.VERSION.SDK_INT >= 23;
        ATLEAST_LOLLIPOP_MR1 = android.os.Build.VERSION.SDK_INT >= 22;
        ATLEAST_LOLLIPOP = android.os.Build.VERSION.SDK_INT >= 21;
        ATLEAST_KITKAT = android.os.Build.VERSION.SDK_INT >= 19;
        ATLEAST_JB_MR1 = android.os.Build.VERSION.SDK_INT >= 17;
        ATLEAST_JB_MR2 = android.os.Build.VERSION.SDK_INT >= 18;
        ATLEAST_O = android.os.Build.VERSION.SDK_INT >= 26;
        ATLEAST_P = android.os.Build.VERSION.SDK_INT >= 28;
        sForceEnableRotation = isPropertyEnabled(FORCE_ENABLE_ROTATION_PROPERTY);
        DM = new android.util.DisplayMetrics();
        mCornerRadius = 0.0f;
    }

    public static boolean isPropertyEnabled(java.lang.String propertyName) {
        return android.util.Log.isLoggable(propertyName, 2);
    }

    private static android.graphics.Bitmap addBorderForIcons(android.graphics.Bitmap bitmap, int iconSize) {
        android.util.Log.i(TAG, "addBorderForIcons: iconSize ==" + iconSize);
        int halfWidth = iconSize / 2;
        int halfHeight = iconSize / 2;
        android.graphics.Bitmap output = android.graphics.Bitmap.createBitmap(iconSize, iconSize, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(output);
        android.graphics.RectF outerRect = new android.graphics.RectF(0.0f, 0.0f, iconSize, iconSize);
        android.graphics.Paint paint = new android.graphics.Paint(3);
        android.graphics.LinearGradient mLinearGradientClamp = new android.graphics.LinearGradient(0.0f, 0.0f, 0.0f, iconSize, new int[]{getCenterTopColor(bitmap), getCenterBottomColor(bitmap)}, (float[]) null, android.graphics.Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradientClamp);
        canvas.drawRoundRect(outerRect, mCornerRadius, mCornerRadius, paint);
        int sw = (int) (bitmap.getWidth() - mCornerRadius);
        int sh = (int) (bitmap.getHeight() - mCornerRadius);
        android.util.Log.i(TAG, "addBorderForIcons: sw ==" + sw);
        android.util.Log.i(TAG, "addBorderForIcons: sh ==" + sh);
        android.graphics.Rect srcRect = new android.graphics.Rect(0, 0, sw, sh);
        int left = halfWidth - (sw / 2);
        int top = halfHeight - (sw / 2);
        android.graphics.Rect dstRect = new android.graphics.Rect(left, top, left + sw, top + sh);
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
        return output;
    }

    private static int getCenterTopColor(android.graphics.Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int x = (int) (w * 0.5d);
        int c = -1;
        for (int y = h - 1; y >= 0; y--) {
            int pixel = src.getPixel(x, y);
            if (android.graphics.Color.alpha(pixel) == 255) {
                c = pixel;
            }
        }
        return c;
    }

    public static void startActivityForResultSafely(android.app.Activity activity, android.content.Intent intent, int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (android.content.ActivityNotFoundException e) {
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Launcher does not have the permission to launch " + intent + ". Make sure to create a MAIN intent-filter for the corresponding activity or use the exported attribute for this activity.", e2);
        }
    }

    private static int getCenterBottomColor(android.graphics.Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int x = (int) (w * 0.5d);
        int c = -1;
        for (int y = 0; y < h; y++) {
            int pixel = src.getPixel(x, y);
            if (android.graphics.Color.alpha(pixel) == 255) {
                c = pixel;
            }
        }
        return c;
    }

    private static int softColor(int color) {
        int r = (int) (android.graphics.Color.red(color) * 1.3d);
        int b = (int) (android.graphics.Color.blue(color) * 1.3d);
        int g = (int) (android.graphics.Color.green(color) * 1.3d);
        if (b >= 255) {
            b = 255;
        }
        if (r >= 255) {
            r = 255;
        }
        if (g >= 255) {
            g = 255;
        }
        return (-16777216) | (r << 16) | (g << 8) | b;
    }

    public static float getDescendantCoordRelativeToParent(android.view.View descendant, android.view.View root, int[] coord, boolean includeRootScroll) {
        java.util.ArrayList<android.view.View> ancestorChain = new java.util.ArrayList<>();
        float[] pt = {coord[0], coord[1]};
        for (android.view.View v = descendant; v != root && v != null; v = (android.view.View) v.getParent()) {
            ancestorChain.add(v);
        }
        ancestorChain.add(root);
        float scale = 1.0f;
        int count = ancestorChain.size();
        for (int i = 0; i < count; i++) {
            android.view.View v0 = ancestorChain.get(i);
            if (v0 != descendant || includeRootScroll) {
                pt[0] = pt[0] - v0.getScrollX();
                pt[1] = pt[1] - v0.getScrollY();
            }
            v0.getMatrix().mapPoints(pt);
            pt[0] = pt[0] + v0.getLeft();
            pt[1] = pt[1] + v0.getTop();
            scale *= v0.getScaleX();
        }
        coord[0] = java.lang.Math.round(pt[0]);
        coord[1] = java.lang.Math.round(pt[1]);
        return scale;
    }

    public static float mapCoordInSelfToDescendent(android.view.View descendant, android.view.View root, int[] coord) {
        java.util.ArrayList<android.view.View> ancestorChain = new java.util.ArrayList<>();
        float[] pt = {coord[0], coord[1]};
        for (android.view.View v = descendant; v != root; v = (android.view.View) v.getParent()) {
            ancestorChain.add(v);
        }
        ancestorChain.add(root);
        float scale = 1.0f;
        android.graphics.Matrix inverse = new android.graphics.Matrix();
        int count = ancestorChain.size();
        int i = count - 1;
        while (i >= 0) {
            android.view.View ancestor = ancestorChain.get(i);
            android.view.View next = i > 0 ? ancestorChain.get(i - 1) : null;
            pt[0] = pt[0] + ancestor.getScrollX();
            pt[1] = pt[1] + ancestor.getScrollY();
            if (next != null) {
                pt[0] = pt[0] - next.getLeft();
                pt[1] = pt[1] - next.getTop();
                next.getMatrix().invert(inverse);
                inverse.mapPoints(pt);
                scale *= next.getScaleX();
            }
            i--;
        }
        coord[0] = java.lang.Math.round(pt[0]);
        coord[1] = java.lang.Math.round(pt[1]);
        return scale;
    }

    public static boolean pointInView(android.view.View v, float localX, float localY, float slop) {
        return localX >= (-slop) && localY >= (-slop) && localX < ((float) v.getWidth()) + slop && localY < ((float) v.getHeight()) + slop;
    }

    public static void scaleRect(android.graphics.Rect r, float scale) {
        if (scale != 1.0f) {
            r.left = (int) ((r.left * scale) + 0.5f);
            r.top = (int) ((r.top * scale) + 0.5f);
            r.right = (int) ((r.right * scale) + 0.5f);
            r.bottom = (int) ((r.bottom * scale) + 0.5f);
        }
    }

    public static int[] getCenterDeltaInScreenSpace(android.view.View v0, android.view.View v1, int[] delta) {
        v0.getLocationInWindow(sLoc0);
        v1.getLocationInWindow(sLoc1);
        sLoc0[0] = (int) (sLoc0[0] + ((v0.getMeasuredWidth() * v0.getScaleX()) / 2.0f));
        sLoc0[1] = (int) (sLoc0[1] + ((v0.getMeasuredHeight() * v0.getScaleY()) / 2.0f));
        sLoc1[0] = (int) (sLoc1[0] + ((v1.getMeasuredWidth() * v1.getScaleX()) / 2.0f));
        sLoc1[1] = (int) (sLoc1[1] + ((v1.getMeasuredHeight() * v1.getScaleY()) / 2.0f));
        if (delta == null) {
            delta = new int[2];
        }
        delta[0] = sLoc1[0] - sLoc0[0];
        delta[1] = sLoc1[1] - sLoc0[1];
        return delta;
    }

    public static void scaleRectAboutCenter(android.graphics.Rect r, float scale) {
        int cx = r.centerX();
        int cy = r.centerY();
        r.offset(-cx, -cy);
        scaleRect(r, scale);
        r.offset(cx, cy);
    }

    public static boolean isSystemApp(android.content.Context context, android.content.Intent intent) {
        android.content.pm.PackageManager pm = context.getPackageManager();
        android.content.ComponentName cn = intent.getComponent();
        java.lang.String packageName = null;
        if (cn == null) {
            android.content.pm.ResolveInfo info = pm.resolveActivity(intent, 65536);
            if (info != null && info.activityInfo != null) {
                packageName = info.activityInfo.packageName;
            }
        } else {
            packageName = cn.getPackageName();
        }
        if (packageName == null) {
            return false;
        }
        try {
            android.content.pm.PackageInfo info2 = pm.getPackageInfo(packageName, 0);
            if (info2 == null || info2.applicationInfo == null) {
                return false;
            }
            return (info2.applicationInfo.flags & 1) != 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    static int findDominantColorByHue(android.graphics.Bitmap bitmap, int samples) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int sampleStride = (int) java.lang.Math.sqrt((height * width) / samples);
        if (sampleStride < 1) {
            sampleStride = 1;
        }
        float[] hsv = new float[3];
        float[] hueScoreHistogram = new float[360];
        float highScore = -1.0f;
        int bestHue = -1;
        for (int y = 0; y < height; y += sampleStride) {
            for (int x = 0; x < width; x += sampleStride) {
                int argb = bitmap.getPixel(x, y);
                int alpha = (argb >> 24) & 255;
                if (alpha >= 128) {
                    android.graphics.Color.colorToHSV(argb | android.support.v4.view.ViewCompat.MEASURED_STATE_MASK, hsv);
                    int hue = (int) hsv[0];
                    if (hue >= 0 && hue < hueScoreHistogram.length) {
                        hueScoreHistogram[hue] = hueScoreHistogram[hue] + (hsv[1] * hsv[2]);
                        if (hueScoreHistogram[hue] > highScore) {
                            highScore = hueScoreHistogram[hue];
                            bestHue = hue;
                        }
                    }
                }
            }
        }
        android.util.SparseArray<java.lang.Float> rgbScores = new android.util.SparseArray<>();
        int bestColor = android.support.v4.view.ViewCompat.MEASURED_STATE_MASK;
        float highScore2 = -1.0f;
        for (int y2 = 0; y2 < height; y2 += sampleStride) {
            for (int x2 = 0; x2 < width; x2 += sampleStride) {
                int rgb = bitmap.getPixel(x2, y2) | android.support.v4.view.ViewCompat.MEASURED_STATE_MASK;
                android.graphics.Color.colorToHSV(rgb, hsv);
                if (((int) hsv[0]) == bestHue) {
                    float s = hsv[1];
                    float v = hsv[2];
                    int bucket = ((int) (100.0f * s)) + ((int) (10000.0f * v));
                    float score = s * v;
                    java.lang.Float oldTotal = rgbScores.get(bucket);
                    float newTotal = oldTotal == null ? score : oldTotal.floatValue() + score;
                    rgbScores.put(bucket, java.lang.Float.valueOf(newTotal));
                    if (newTotal > highScore2) {
                        highScore2 = newTotal;
                        bestColor = rgb;
                    }
                }
            }
        }
        return bestColor;
    }

    static android.util.Pair<java.lang.String, android.content.res.Resources> findSystemApk(java.lang.String action, android.content.pm.PackageManager pm) {
        android.content.Intent intent = new android.content.Intent(action);
        java.util.Iterator<android.content.pm.ResolveInfo> it = pm.queryBroadcastReceivers(intent, 0).iterator();
        while (it.hasNext()) {
            android.content.pm.ResolveInfo info = it.next();
            if (info.activityInfo != null && (info.activityInfo.applicationInfo.flags & 1) != 0) {
                java.lang.String packageName = info.activityInfo.packageName;
                try {
                    android.content.res.Resources res = pm.getResourcesForApplication(packageName);
                    return android.util.Pair.create(packageName, res);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.w(TAG, "Failed to find resources for " + packageName);
                }
            }
        }
        return null;
    }

    @android.annotation.TargetApi(19)
    public static boolean isViewAttachedToWindow(android.view.View v) {
        if (ATLEAST_KITKAT) {
            return v.isAttachedToWindow();
        }
        return v.getKeyDispatcherState() != null;
    }

    @android.annotation.TargetApi(17)
    public static android.appwidget.AppWidgetProviderInfo getSearchWidgetProvider(android.content.Context context) {
        android.app.SearchManager searchManager = (android.app.SearchManager) context.getSystemService("search");
        android.content.ComponentName searchComponent = searchManager.getGlobalSearchActivity();
        if (searchComponent == null) {
            return null;
        }
        java.lang.String providerPkg = searchComponent.getPackageName();
        android.appwidget.AppWidgetProviderInfo defaultWidgetForSearchPackage = null;
        android.appwidget.AppWidgetManager appWidgetManager = android.appwidget.AppWidgetManager.getInstance(context);
        java.util.Iterator<android.appwidget.AppWidgetProviderInfo> it = appWidgetManager.getInstalledProviders().iterator();
        while (it.hasNext()) {
            android.appwidget.AppWidgetProviderInfo info = it.next();
            if (info.provider.getPackageName().equals(providerPkg)) {
                if (ATLEAST_JB_MR1 && (info.widgetCategory & 4) == 0) {
                    if (defaultWidgetForSearchPackage == null) {
                        defaultWidgetForSearchPackage = info;
                    }
                } else {
                    return info;
                }
            }
        }
        return defaultWidgetForSearchPackage;
    }

    public static byte[] flattenBitmap(android.graphics.Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream(size);
        try {
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Could not write bitmap");
            return null;
        }
    }

    public static boolean findVacantCell(int[] vacant, int spanX, int spanY, int xCount, int yCount, boolean[][] occupied) {
        for (int y = 0; y + spanY <= yCount; y++) {
            for (int x = 0; x + spanX <= xCount; x++) {
                boolean available = !occupied[x][y];
                for (int i = x; i < x + spanX; i++) {
                    for (int j = y; j < y + spanY; j++) {
                        available = available && !occupied[i][j];
                        if (!available) {
                            break;
                        }
                    }
                }
                if (available) {
                    vacant[0] = x;
                    vacant[1] = y;
                    return true;
                }
            }
        }
        return false;
    }

    public static java.lang.String trim(java.lang.CharSequence s) {
        if (s == null) {
            return null;
        }
        java.util.regex.Matcher m = sTrimPattern.matcher(s);
        return m.replaceAll("$1");
    }

    public static float calculateTextHeight(float textSizePx) {
        android.graphics.Paint p = new android.graphics.Paint();
        p.setTextSize(textSizePx);
        android.graphics.Paint.FontMetrics fm = p.getFontMetrics();
        return (-fm.top) + fm.bottom;
    }

    public static void println(java.lang.String key, java.lang.Object... args) {
        java.lang.StringBuilder b = new java.lang.StringBuilder();
        b.append(key);
        b.append(": ");
        boolean isFirstArgument = true;
        for (java.lang.Object arg : args) {
            if (isFirstArgument) {
                isFirstArgument = false;
            } else {
                b.append(", ");
            }
            b.append(arg);
        }
        java.lang.System.out.println(b.toString());
    }

    @android.annotation.TargetApi(17)
    public static boolean isRtl(android.content.res.Resources res) {
        return ATLEAST_JB_MR1 && res.getConfiguration().getLayoutDirection() == 1;
    }

    public static float dpiFromPx(int size, android.util.DisplayMetrics metrics) {
        float densityRatio = metrics.densityDpi / 160.0f;
        return size / densityRatio;
    }

    public static int pxFromDp(float size, android.util.DisplayMetrics metrics) {
        return java.lang.Math.round(android.util.TypedValue.applyDimension(1, size, metrics));
    }

    public static int pxFromSp(float size, android.util.DisplayMetrics metrics) {
        return java.lang.Math.round(android.util.TypedValue.applyDimension(2, size, metrics));
    }

    public static java.lang.String createDbSelectionQuery(java.lang.String columnName, java.lang.Iterable<?> values) {
        return java.lang.String.format(java.util.Locale.ENGLISH, "%s IN (%s)", columnName, android.text.TextUtils.join(", ", values));
    }

    public static int getResourcesID(android.content.Context context, java.lang.String idname, java.lang.String localxy) {
        android.content.res.Resources res = context.getResources();
        return res.getIdentifier(idname, localxy, context.getPackageName());
    }

    public static android.app.Activity getActivity(android.content.Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof android.app.Activity) {
            return (android.app.Activity) context;
        }
        if (context instanceof android.content.ContextWrapper) {
            return getActivity(((android.content.ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
