package com.autopai.support.utils;

/* loaded from: classes.dex */
public class MediaStatusView extends android.widget.ImageView implements com.autopai.support.IChangeTheme {
    private static final java.lang.String LOGTAG = "MediaStatusView";
    private android.content.Context mContext;
    private int mCurResId;

    public MediaStatusView(android.content.Context context) {
        this(context, null);
    }

    public MediaStatusView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaStatusView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context.getApplicationContext();
        initMediaStatusView();
    }

    private void initMediaStatusView() {
        this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_ic_account");
        changeTheme();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        com.autopai.support.utils.SLog.d(LOGTAG, "onWindowVisibilityChanged visibility = " + visibility);
        if (visibility == 0) {
            setMediaStatusIcon();
        }
    }

    private void setMediaStatusIcon() {
        setVisibility(0);
        com.incall.proxy.constant.SourceConstantsDef.SourceID sourceID = com.incall.proxy.source.SourceManager.getInstance().getCurrentSource();
        com.autopai.support.utils.SLog.d(LOGTAG, "setMediaStatusIcon sourceID = " + sourceID);
        if (sourceID != com.incall.proxy.constant.SourceConstantsDef.SourceID.VIDEO && sourceID != com.incall.proxy.constant.SourceConstantsDef.SourceID.MUSIC_ONLINE && sourceID != com.incall.proxy.constant.SourceConstantsDef.SourceID.TUNER_ONLINE && sourceID != com.incall.proxy.constant.SourceConstantsDef.SourceID.TUNER) {
            setVisibility(8);
        }
    }

    public void destroy() {
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mCurResId);
        setImageDrawable(drawable);
    }
}
