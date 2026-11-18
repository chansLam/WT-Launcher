package com.wt.statistics.net;

/* loaded from: classes.dex */
public class NetThread {
    private static final java.lang.String LOGTAG = "NetThread";
    public static final int MAX_EVENT_CACHE = 1000;
    public static final int MSG_ACC_OFF_EVENT = 10;
    public static final int MSG_EVENT_CACHE_EVENT = 9;
    public static final int MSG_GET_CONFIG = 3;
    public static final int MSG_GET_TOKEN = 1;
    public static final int MSG_REFRESH_TOKEN = 2;
    public static final int MSG_SAVE_TO_DB_EVENT = 7;
    public static final int MSG_TIMER_SEND_EVENT = 8;
    public static final int MSG_UPLOAD_DB_EVENT = 6;
    public static final int MSG_UPLOAD_EVENT = 4;
    public static final int MSG_UPLOAD_EXCEP_EVENT = 5;
    private static final int SEND_TIME_INTERVAL = 7200000;
    private android.content.Context mContext;
    private com.wt.statistics.persistentdata.EventBeans mEventCaches = new com.wt.statistics.persistentdata.EventBeans();
    private android.os.Handler mNetHandler;
    private com.wt.statistics.persistentdata.IPersistent mPersistent;

    public NetThread(android.content.Context cxt, com.wt.statistics.persistentdata.IPersistent persistent) {
        this.mContext = cxt.getApplicationContext();
        this.mPersistent = persistent;
        android.os.HandlerThread thread = new android.os.HandlerThread("com.wt.statistics.net.NetThread");
        thread.start();
        this.mNetHandler = new com.wt.statistics.net.NetThread.NetThreadHandler(thread.getLooper());
        makeTimerSendRequest(7200000L);
    }

    public void sendAccOffEvent(long delay, boolean accOff) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 10;
        msg.obj = java.lang.Boolean.valueOf(accOff);
        runMessage(msg, delay);
    }

    public void makeEventCacheMsg(long delay, com.wt.statistics.persistentdata.EventBeans.Events events) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 9;
        msg.obj = events;
        runMessage(msg, delay);
    }

    public void makeTimerSendRequest(long delay) {
        this.mNetHandler.removeMessages(8);
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 8;
        runMessage(msg, delay);
    }

    public void makeTokenRequest(long delay) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 1;
        msg.arg1 = 1;
        runMessage(msg, delay);
    }

    public void makeRefreshTokenRequest(long delay) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 2;
        runMessage(msg, delay);
    }

    public void makeConfigRequest(long delay) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 3;
        runMessage(msg, delay);
    }

    public void makeUploadEventRequest(long delay, com.wt.statistics.persistentdata.EventBeans sendData) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 4;
        msg.obj = sendData;
        runMessage(msg, delay);
    }

    public void makeUploadExceptionRequest(long delay) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 5;
        runMessage(msg, delay);
    }

    public void makeUploadDbEventRequest(long delay) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 6;
        runMessage(msg, delay);
    }

    public void saveDataToDb(long delay, com.wt.statistics.persistentdata.EventBeans sendData) {
        android.os.Message msg = this.mNetHandler.obtainMessage();
        msg.what = 7;
        msg.obj = sendData;
        runMessage(msg, delay);
    }

    public void timerSendEvents(long delay) {
    }

    private void runMessage(android.os.Message msg) {
        runMessage(msg, 0L);
    }

    private void runMessage(android.os.Message msg, long delay) {
        if (this.mNetHandler != null) {
            this.mNetHandler.sendMessageDelayed(msg, delay);
        }
    }

    private class NetThreadHandler extends android.os.Handler {
        public NetThreadHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    com.wt.statistics.net.NetThread.this.doGetTokenRequest(msg);
                    break;
                case 2:
                    com.wt.statistics.net.NetThread.this.doRefreshTokenRequest(msg);
                    break;
                case 3:
                    com.wt.statistics.net.NetThread.this.doGetConfigRequest(msg);
                    break;
                case 4:
                    com.wt.statistics.net.NetThread.this.doUploadEventRequest(msg);
                    break;
                case 5:
                    com.wt.statistics.net.NetThread.this.doUploadExcepEventRequest(msg);
                    break;
                case 6:
                    com.wt.statistics.net.NetThread.this.doUploadDbEventRequest(msg);
                    break;
                case 7:
                    com.wt.statistics.net.NetThread.this.doSaveToDbEventRequest(msg);
                    break;
                case 8:
                    com.wt.statistics.net.NetThread.this.doTimerSendEvent(msg);
                    break;
                case 9:
                    com.wt.statistics.net.NetThread.this.doEventCache(msg);
                    break;
                case 10:
                    com.wt.statistics.net.NetThread.this.doAccOffEvent(msg);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAccOffEvent(android.os.Message msg) {
        boolean accOff = ((java.lang.Boolean) msg.obj).booleanValue();
        if (accOff) {
            com.wt.statistics.StatisticsManager.getInstance(this.mContext).getServerConfig();
            boolean need = needUploadEvent(this.mContext, 0);
            if (need) {
                makeUploadEventRequest(0L, this.mEventCaches);
                makeUploadDbEventRequest(0L);
                return;
            }
            boolean need_send_accoff = needUploadEvent(this.mContext, 1);
            if (need_send_accoff) {
                makeUploadEventRequest(0L, this.mEventCaches);
                makeUploadDbEventRequest(0L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doEventCache(android.os.Message msg) {
        com.wt.statistics.persistentdata.EventBeans.Events events = (com.wt.statistics.persistentdata.EventBeans.Events) msg.obj;
        this.mEventCaches.normal_events.add(events);
        com.wt.statistics.utils.SLog.i(LOGTAG, "handleNormalEvent mEventCaches.normal_events.size() = " + this.mEventCaches.normal_events.size());
        if (this.mEventCaches.normal_events.size() > 1000) {
            makeUploadEventRequest(0L, this.mEventCaches);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doTimerSendEvent(android.os.Message msg) {
        if (!this.mEventCaches.isEmptyData()) {
            boolean need = needUploadEvent(this.mContext, 0);
            if (need) {
                msg.obj = this.mEventCaches;
                doUploadEventRequest(msg);
            }
        }
        makeTimerSendRequest(7200000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSaveToDbEventRequest(android.os.Message msg) {
        com.wt.statistics.persistentdata.EventBeans sendData = (com.wt.statistics.persistentdata.EventBeans) msg.obj;
        persistData(sendData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUploadExcepEventRequest(android.os.Message msg) {
        long timestamp = java.lang.System.currentTimeMillis();
        com.wt.statistics.persistentdata.ExceptionBean bean = this.mPersistent.readExceptionEvent(timestamp);
        if (bean.infos.size() <= 0) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "doUploadExcepEventRequest have no exceptions");
            return;
        }
        com.wt.statistics.net.Result result = sendExceptionRequest(bean);
        com.wt.statistics.utils.SLog.i(LOGTAG, "sendExceptionRequest result.code=" + result.code + ";message" + result.message + ";data=" + result.data + ";sign=" + result.sign);
        if (com.wt.statistics.net.UrlConstants.RET_OK.equals(result.code)) {
            this.mPersistent.deleteExceptionData(timestamp);
            return;
        }
        if (com.wt.statistics.net.UrlConstants.E0005.equals(result.code)) {
            makeRefreshTokenRequest(0L);
        } else if (com.wt.statistics.net.UrlConstants.E0000.equals(result.code) || com.wt.statistics.net.UrlConstants.E0001.equals(result.code) || com.wt.statistics.net.UrlConstants.E0002.equals(result.code) || com.wt.statistics.net.UrlConstants.E0003.equals(result.code)) {
            makeTokenRequest(0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doGetTokenRequest(android.os.Message msg) {
        com.wt.statistics.persistentdata.CommonBean commonBean = com.wt.common.WTConfigure.getCommonBean();
        com.wt.statistics.net.Result result = com.wt.statistics.net.ApiUtils.getTokenRequest(commonBean);
        if (com.wt.statistics.net.UrlConstants.RET_OK.equals(result.code)) {
            com.wt.statistics.net.TokenBean bean = null;
            try {
                bean = (com.wt.statistics.net.TokenBean) result.data;
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            if (bean == null) {
                com.wt.statistics.utils.SLog.i(LOGTAG, "doGetTokenRequest bean is null ");
                return;
            }
            commonBean.tokenBean = bean;
            com.wt.statistics.persistentdata.CommonDataPreference.getInstance(this.mContext).pushToPref(commonBean);
            com.wt.common.WTConfigure.setCommonBean(commonBean);
            com.wt.statistics.utils.SLog.i(LOGTAG, "doGetTokenRequest token" + bean.token + ";time=" + bean.expirationDate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRefreshTokenRequest(android.os.Message msg) {
        com.wt.statistics.net.TokenBean bean = com.wt.common.WTConfigure.getCommonBean().tokenBean;
        if (bean != null) {
            java.lang.String badToken = bean.token;
            com.wt.statistics.net.Result result = com.wt.statistics.net.ApiUtils.refreshTokenRequest(badToken);
            if (com.wt.statistics.net.UrlConstants.RET_OK.equals(result.code)) {
                com.wt.statistics.net.TokenBean newToken = null;
                try {
                    newToken = (com.wt.statistics.net.TokenBean) result.data;
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
                if (newToken == null) {
                    com.wt.statistics.utils.SLog.i(LOGTAG, "doRefreshTokenRequest bean is null ");
                    return;
                }
                com.wt.statistics.persistentdata.CommonBean commonBean = com.wt.common.WTConfigure.getCommonBean();
                commonBean.tokenBean = newToken;
                com.wt.statistics.persistentdata.CommonDataPreference.getInstance(this.mContext).pushToPref(commonBean);
                com.wt.common.WTConfigure.setCommonBean(commonBean);
                return;
            }
            if (com.wt.statistics.net.UrlConstants.E0000.equals(result.code) || com.wt.statistics.net.UrlConstants.E0001.equals(result.code) || com.wt.statistics.net.UrlConstants.E0002.equals(result.code) || com.wt.statistics.net.UrlConstants.E0003.equals(result.code)) {
                makeTokenRequest(0L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doGetConfigRequest(android.os.Message msg) {
        com.wt.statistics.persistentdata.CommonBean commonBean = com.wt.common.WTConfigure.getCommonBean();
        if (commonBean != null) {
            com.wt.statistics.net.Result result = com.wt.statistics.net.ApiUtils.getConfigRequest(commonBean, commonBean.tokenBean);
            com.wt.statistics.utils.SLog.i(LOGTAG, "doGetConfigRequest result.code =" + result.code);
            if (com.wt.statistics.net.UrlConstants.RET_OK.equals(result.code)) {
                try {
                    com.wt.statistics.persistentdata.ServerConfigs.getInstance().setConfigBeans(this.mContext, (java.util.ArrayList) result.data);
                    return;
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (com.wt.statistics.net.UrlConstants.E0005.equals(result.code)) {
                makeRefreshTokenRequest(0L);
                return;
            }
            if (com.wt.statistics.net.UrlConstants.E0000.equals(result.code) || com.wt.statistics.net.UrlConstants.E0001.equals(result.code) || com.wt.statistics.net.UrlConstants.E0002.equals(result.code) || com.wt.statistics.net.UrlConstants.E0003.equals(result.code)) {
                makeTokenRequest(0L);
            } else {
                this.mNetHandler.removeMessages(3);
            }
        }
    }

    public static boolean needUploadEvent(android.content.Context cxt, int scene) {
        boolean networkAvailable = com.wt.statistics.utils.WTUtils.isNetworkAvailable(cxt);
        com.wt.statistics.utils.SLog.i(LOGTAG, "needUploadEvent networkAvailable =" + networkAvailable);
        if (!networkAvailable) {
            return false;
        }
        com.wt.statistics.persistentdata.ServerConfigs.getInstance();
        com.wt.statistics.persistentdata.ConfigBean bean = com.wt.statistics.persistentdata.ServerConfigs.readServerConfig(cxt);
        if (bean == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "needUploadEvent bean is null");
            com.wt.statistics.StatisticsManager.getInstance(cxt).getServerConfig();
            return false;
        }
        int strategy = bean.getReportStrategy();
        com.wt.statistics.utils.SLog.i(LOGTAG, "needUploadEvent strategy =" + strategy + ";scene=" + scene);
        return strategy != -1 && strategy == scene;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUploadEventRequest(android.os.Message msg) {
        boolean need = needUploadEvent(this.mContext, 0);
        com.wt.statistics.persistentdata.EventBeans eventCaches = (com.wt.statistics.persistentdata.EventBeans) msg.obj;
        if (!need) {
            saveDataToDb(0L, eventCaches);
            return;
        }
        long timestamp = java.lang.System.currentTimeMillis();
        com.wt.statistics.utils.SLog.i(LOGTAG, "doUploadEventRequest bean.isEmptyData() = " + eventCaches.isEmptyData());
        if (!eventCaches.isEmptyData()) {
            realUpLoadData(eventCaches, timestamp);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUploadDbEventRequest(android.os.Message msg) {
        long timestamp = java.lang.System.currentTimeMillis();
        com.wt.statistics.persistentdata.EventBeans beans = this.mPersistent.readEvent(timestamp);
        if (!beans.isEmptyData()) {
            realUpLoadDbData(beans, timestamp);
        }
    }

    private void realUpLoadData(com.wt.statistics.persistentdata.EventBeans sendDatas, long timestamp) {
        com.wt.statistics.net.Result result = sendRequest(this.mEventCaches);
        com.wt.statistics.utils.SLog.i(LOGTAG, "realUpLoadData result.code=" + result.code + ";message" + result.message + ";data=" + result.data + ";sign=" + result.sign);
        if (com.wt.statistics.net.UrlConstants.RET_OK.equals(result.code)) {
            makeUploadDbEventRequest(0L);
            this.mEventCaches.cleanData();
            return;
        }
        if (com.wt.statistics.net.UrlConstants.E0005.equals(result.code)) {
            makeRefreshTokenRequest(0L);
        } else if (com.wt.statistics.net.UrlConstants.E0000.equals(result.code) || com.wt.statistics.net.UrlConstants.E0001.equals(result.code) || com.wt.statistics.net.UrlConstants.E0002.equals(result.code) || com.wt.statistics.net.UrlConstants.E0003.equals(result.code)) {
            makeTokenRequest(0L);
        }
        persistData(sendDatas);
    }

    private void realUpLoadDbData(com.wt.statistics.persistentdata.EventBeans sendDatas, long timestamp) {
        com.wt.statistics.net.Result result = sendRequest(sendDatas);
        com.wt.statistics.utils.SLog.i(LOGTAG, "realUpLoadData result.code=" + result.code + ";message" + result.message + ";data=" + result.data + ";sign=" + result.sign);
        if (com.wt.statistics.net.UrlConstants.RET_OK.equals(result.code)) {
            this.mPersistent.deleteEventData(timestamp);
            return;
        }
        if (com.wt.statistics.net.UrlConstants.E0005.equals(result.code)) {
            makeRefreshTokenRequest(0L);
        } else if (com.wt.statistics.net.UrlConstants.E0000.equals(result.code) || com.wt.statistics.net.UrlConstants.E0001.equals(result.code) || com.wt.statistics.net.UrlConstants.E0002.equals(result.code) || com.wt.statistics.net.UrlConstants.E0003.equals(result.code)) {
            makeTokenRequest(0L);
        }
    }

    private void persistData(com.wt.statistics.persistentdata.EventBeans sendDatas) {
        this.mPersistent.saveActivityEvent(this.mEventCaches.activity_events);
        this.mPersistent.saveNormalEvent(this.mEventCaches.normal_events);
        this.mPersistent.saveFragmentEvent(this.mEventCaches.fragment_events);
        this.mEventCaches.cleanData();
    }

    private com.wt.statistics.net.Result sendRequest(com.wt.statistics.persistentdata.EventBeans sendDatas) {
        com.wt.statistics.persistentdata.CommonBean bean = com.wt.common.WTConfigure.getCommonBean();
        sendDatas.baseBean.channel_id = bean.channel_id;
        sendDatas.baseBean.app_id = bean.app_id;
        sendDatas.baseBean.vin = bean.vin;
        java.lang.String carSeries = bean.car_type;
        if (android.text.TextUtils.isEmpty(carSeries)) {
            carSeries = com.wt.statistics.utils.WTUtils.getCurCarType();
        }
        sendDatas.baseBean.carSeries = carSeries;
        sendDatas.baseBean.user_id = "";
        java.lang.String jsonString = sendDatas.toJsonObject().toString();
        return com.wt.statistics.net.ApiUtils.collectRequest(jsonString, bean.tokenBean);
    }

    private com.wt.statistics.net.Result sendExceptionRequest(com.wt.statistics.persistentdata.ExceptionBean beans) {
        com.wt.statistics.persistentdata.CommonBean bean = com.wt.common.WTConfigure.getCommonBean();
        beans.baseBean.vin = bean.vin;
        beans.baseBean.app_id = bean.app_id;
        beans.baseBean.channel_id = bean.channel_id;
        java.lang.String carSeries = bean.car_type;
        if (android.text.TextUtils.isEmpty(carSeries)) {
            carSeries = com.wt.statistics.utils.WTUtils.getCurCarType();
        }
        beans.baseBean.carSeries = carSeries;
        beans.baseBean.user_id = "";
        java.lang.String jsonString = beans.toJsonObject().toString();
        com.wt.statistics.utils.SLog.i(LOGTAG, "sendExceptionRequest jsonString =" + jsonString);
        com.wt.statistics.net.Result result = com.wt.statistics.net.ApiUtils.collectExceptionRequest(jsonString, bean.tokenBean);
        return result;
    }
}
