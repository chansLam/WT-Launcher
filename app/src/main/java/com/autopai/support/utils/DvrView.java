package com.autopai.support.utils;

/* loaded from: classes.dex */
public class DvrView extends android.widget.ImageView implements com.autopai.support.IChangeTheme {
    private static final java.lang.String LOGTAG = "DvrView";
    private android.content.Context mContext;
    private int mCurResId;
    private com.autopai.support.proxy.DvrImplProxy mDvrProxy;
    private int mDvrStatus;
    private com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener mListener;

    public DvrView(android.content.Context context) {
        this(context, null);
    }

    public DvrView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DvrView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mListener = new com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener() { // from class: com.autopai.support.utils.DvrView.1
            @Override // com.autopai.support.proxy.DvrImplProxy.IDvrChangeListener
            public void onDvrChanged(int status) {
                com.autopai.support.utils.DvrView.this.setDvrIconStatus(status == 1);
            }
        };
        this.mContext = context.getApplicationContext();
        this.mDvrProxy = new com.autopai.support.proxy.DvrImplProxy(this.mContext);
        this.mDvrProxy.setDvrChangeListener(this.mListener);
        initDvrView();
        if (!com.autopai.support.AppGlobal.getInstance(context).hasDvrDevice()) {
            setVisibility(8);
        }
    }

    private void initDvrView() {
        this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_dvr_status_record");
        this.mDvrStatus = this.mDvrProxy.getDvrStatus();
        if (this.mDvrStatus == 1) {
            this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_dvr_status_record");
        } else {
            this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_dvr_status_record_disable");
        }
        changeTheme();
    }

    public void setDvrIconStatus(boolean flag) {
        if (flag) {
            this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_dvr_status_record");
        } else {
            this.mCurResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_dvr_status_record_disable");
        }
        changeTheme();
    }

    public void destroy() {
        this.mDvrProxy.destroy();
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        android.graphics.drawable.Drawable drawable = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mCurResId);
        setImageDrawable(drawable);
    }
}
