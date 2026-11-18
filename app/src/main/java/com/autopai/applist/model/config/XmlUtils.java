package com.autopai.applist.model.config;

/* loaded from: classes.dex */
public class XmlUtils {
    private static final java.lang.String TAG = "XmlUtils";
    private static final int VALUE_TYPE_BACKGROUND = 3;
    private static final int VALUE_TYPE_ICON = 2;
    private static final int VALUE_TYPE_NO = -1;
    private static final int VALUE_TYPE_PACKAGE = 1;

    public static void parseXml(android.content.res.XmlResourceParser xmlParser) {
        int mValueType = -1;
        try {
            int event = xmlParser.getEventType();
            while (event != 1) {
                switch (event) {
                    case 0:
                        android.util.Log.i(TAG, "parseXml: START_DOCUMENT");
                        com.autopai.applist.model.config.AppsConfig.listIcon.clear();
                        com.autopai.applist.model.config.AppsConfig.listPackage.clear();
                        break;
                    case 2:
                        if (xmlParser.getName().equals("icon")) {
                            mValueType = 2;
                            android.util.Log.i(TAG, "parseXml: icon");
                            break;
                        } else if (xmlParser.getName().equals("package")) {
                            mValueType = 1;
                            android.util.Log.i(TAG, "parseXml: package");
                            break;
                        } else if (!xmlParser.getName().equals("background")) {
                            break;
                        } else {
                            mValueType = 3;
                            android.util.Log.i(TAG, "parseXml: background");
                            break;
                        }
                    case 3:
                        mValueType = -1;
                        break;
                    case 4:
                        switch (mValueType) {
                            case 1:
                                com.autopai.applist.model.config.AppsConfig.listPackage.add(xmlParser.getText());
                                android.util.Log.i(TAG, "parseXml: xmlParser.getText()=" + xmlParser.getText());
                                break;
                            case 2:
                                android.util.Log.i(TAG, "parseXml: xmlParser.getText()=" + xmlParser.getText());
                                com.autopai.applist.model.config.AppsConfig.listIcon.add(xmlParser.getText());
                                break;
                            case 3:
                                com.autopai.applist.model.config.AppsConfig.setBackground(xmlParser.getText());
                                android.util.Log.i(TAG, "parseXml: xmlParser.getText()=" + xmlParser.getText());
                                break;
                        }
                }
                event = xmlParser.next();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }
}
