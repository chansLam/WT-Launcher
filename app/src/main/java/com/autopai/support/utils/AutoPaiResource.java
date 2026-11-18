package com.autopai.support.utils;

/* loaded from: classes.dex */
public class AutoPaiResource {
    private static final java.lang.String LOGTAG = "AutoPaiResource";

    public static int getIdentifierById(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "id", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierById context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByDrawable(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "drawable", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByDrawable context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByLayout(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "layout", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByLayout context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByAnim(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "anim", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByAnim context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByAttr(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "attr", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByAttr context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByBool(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "bool", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByBool context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByColor(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "color", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByColor context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByDimen(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "dimen", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByDimen context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByString(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "string", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByString context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByStyle(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "style", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByStyle context is null idName = " + idName);
        return 0;
    }

    public static int getIdentifierByInteger(android.content.Context context, java.lang.String idName) {
        if (context != null) {
            return context.getResources().getIdentifier(idName, "integer", context.getPackageName());
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "getIdentifierByInteger context is null idName = " + idName);
        return 0;
    }
}
