package com.autopai.support.utils;

/* loaded from: classes.dex */
public class SignalView extends android.widget.ImageView implements com.autopai.support.IChangeTheme {
    private static final java.lang.String LOGTAG = "SignalView";
    private android.content.Context mContext;
    private int mCurResId;
    private android.os.Handler mHandler;
    private com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener mListener;
    private com.autopai.support.utils.SignalUtil mSignalUtils;

    public SignalView(android.content.Context context) {
        this(context, null);
    }

    public SignalView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignalView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mListener = new com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener() { // from class: com.autopai.support.utils.SignalView.1
            @Override // com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener
            public void onTboxMobileSignalChanged(final int type, final int level, final int value) {
                com.autopai.support.utils.SLog.d(com.autopai.support.utils.SignalView.LOGTAG, "onTboxMobileSignalChanged type = " + type + "; level = " + level);
                com.autopai.support.utils.SignalView.this.mHandler.post(new java.lang.Runnable() { // from class: com.autopai.support.utils.SignalView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (com.autopai.support.utils.SignalView.this.mSignalUtils != null) {
                            int id = com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH[0][0];
                            com.autopai.support.utils.SLog.d(com.autopai.support.utils.SignalView.LOGTAG, "id = " + id + "; type = " + type + "; level = " + level + "; value = " + value);
                            if (type > 0 && level >= 0) {
                                if (type < com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH.length && level < com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH[type].length) {
                                    id = com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH[type][level];
                                }
                            } else if (type < 0) {
                                id = com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH[0][0];
                                if (level >= 0 && level < com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH[0].length) {
                                    id = com.autopai.support.utils.SignalView.this.mSignalUtils.JG_SIGNAL_STRENGTH[0][level];
                                }
                            }
                            com.autopai.support.utils.SignalView.this.mCurResId = id;
                            com.autopai.support.utils.SignalView.this.changeTheme();
                            return;
                        }
                        com.autopai.support.utils.SLog.d(com.autopai.support.utils.SignalView.LOGTAG, "initSignalView mSignalUtils is null");
                    }
                });
            }
        };
        this.mContext = context.getApplicationContext();
        initSignalView();
        com.autopai.support.utils.SLog.d(LOGTAG, "SignalView start");
    }

    private void initSignalView() {
        this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_ic_no_signal");
        changeTheme();
        this.mSignalUtils = new com.autopai.support.utils.SignalUtil(this.mContext, this.mListener);
    }

    public void destroy() {
        com.autopai.support.utils.SLog.d(LOGTAG, "destroy mSignalUtils = " + this.mSignalUtils);
        if (this.mSignalUtils != null) {
            this.mSignalUtils.destroy();
            this.mSignalUtils = null;
        }
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mCurResId);
        setImageDrawable(drawable);
    }
}
