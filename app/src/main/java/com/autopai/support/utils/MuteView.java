package com.autopai.support.utils;

/* loaded from: classes.dex */
public class MuteView extends android.widget.ImageView implements com.autopai.support.IChangeTheme {
    private static final java.lang.String LOGTAG = "MuteView";
    private android.content.Context mContext;
    private int mCurResId;
    private com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener mListener;
    private com.autopai.support.proxy.MuteImplProxy mMuteImplProxy;

    public MuteView(android.content.Context context) {
        this(context, null);
    }

    public MuteView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MuteView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mListener = new com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener() { // from class: com.autopai.support.utils.MuteView.1
            @Override // com.autopai.support.proxy.MuteImplProxy.IMuteChangeListener
            public void onMuteChanged(final boolean muteStatus) {
                com.autopai.support.utils.MuteView.this.post(new java.lang.Runnable() { // from class: com.autopai.support.utils.MuteView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.autopai.support.utils.MuteView.this.showMute(muteStatus);
                    }
                });
            }
        };
        this.mContext = context.getApplicationContext();
        this.mMuteImplProxy = new com.autopai.support.proxy.MuteImplProxy(this.mContext);
        initLayout();
    }

    private void initLayout() {
        this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jp_horn_mute");
        changeTheme();
        showMute(this.mMuteImplProxy.getMuteStatus());
        this.mMuteImplProxy.setMuteChangeListener(this.mListener);
    }

    public void destroy() {
        this.mMuteImplProxy.destroy();
    }

    public void showMute(boolean show) {
        if (show) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mCurResId);
        setImageDrawable(drawable);
    }
}
