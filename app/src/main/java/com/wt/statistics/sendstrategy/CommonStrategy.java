package com.wt.statistics.sendstrategy;

/* loaded from: classes.dex */
public class CommonStrategy {
    public static final java.lang.String ACC_ACTION = "com.coagent.intent.action.ACC_CHANGED";
    private static final java.lang.String LOGTAG = "CommonStrategy";
    private static com.wt.statistics.sendstrategy.CommonStrategy sInstance;
    private android.content.BroadcastReceiver mAccReceiver;
    private android.content.Context mContext;

    private CommonStrategy(android.content.Context cxt) {
        this.mContext = cxt.getApplicationContext();
    }

    public static com.wt.statistics.sendstrategy.CommonStrategy getInstance(android.content.Context cxt) {
        if (sInstance == null) {
            synchronized (com.wt.statistics.sendstrategy.CommonStrategy.class) {
                if (sInstance == null) {
                    sInstance = new com.wt.statistics.sendstrategy.CommonStrategy(cxt);
                }
            }
        }
        return sInstance;
    }

    public void registerACCReceiver(final com.wt.statistics.net.NetThread netThread) {
        if (this.mAccReceiver == null) {
            this.mAccReceiver = new android.content.BroadcastReceiver() { // from class: com.wt.statistics.sendstrategy.CommonStrategy.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    boolean acc = intent.getExtras().getBoolean("acc_state");
                    com.wt.statistics.utils.SLog.i(com.wt.statistics.sendstrategy.CommonStrategy.LOGTAG, "registerACCReceiver acc=" + acc);
                    netThread.sendAccOffEvent(0L, acc);
                }
            };
            com.wt.statistics.utils.SLog.i(LOGTAG, "registerACCReceiver register");
            this.mContext.registerReceiver(this.mAccReceiver, new android.content.IntentFilter(ACC_ACTION));
        }
    }

    public void unRegisterACCReceiver() {
        if (this.mAccReceiver != null) {
            this.mContext.unregisterReceiver(this.mAccReceiver);
            this.mAccReceiver = null;
        }
    }
}
