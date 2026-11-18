package com.autopai.support.utils;

/* loaded from: classes.dex */
public class AirConditionView extends android.widget.RelativeLayout implements com.autopai.support.IChangeTheme {
    private static final java.lang.String DEGREE = "℃";
    private static final java.lang.String LOGTAG = "AirConditionView";
    private com.autopai.support.view.TopImageView mAirLevelIcon;
    private int mAirLevelResId;
    private int mBgResId;
    private android.content.Context mContext;
    private int mDividerResId;
    private com.autopai.support.view.TopImageView mDividerView;
    private int mFunResId;
    private com.autopai.support.view.TopTextView mFunView;
    private int mIconResId;
    private com.autopai.support.view.TopImageView mIconView;
    private com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener mListener;
    private com.autopai.support.view.TopTextView mOffView;
    private android.widget.LinearLayout mParentLinearLayout;
    private com.autopai.support.proxy.AirConditionImplProxy mProxy;
    private int mPuriferResId;
    private com.autopai.support.view.TopImageView mPuriferView;
    private com.autopai.support.view.TopTextView mTempView;
    private int mTextResId;

    public AirConditionView(android.content.Context context) {
        this(context, null);
    }

    public AirConditionView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirConditionView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mListener = new com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener() { // from class: com.autopai.support.utils.AirConditionView.1
            @Override // com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener
            public void onAirConditionChanged(com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue value) {
                if (value != null) {
                    com.autopai.support.utils.SLog.d(com.autopai.support.utils.AirConditionView.LOGTAG, "onAirConditionChanged open = " + value.isOpen + "; leftTmp = " + value.leftTmp + "; rightTmp = " + value.rightTmp);
                    com.autopai.support.utils.AirConditionView.this.post(new java.lang.Runnable() { // from class: com.autopai.support.utils.AirConditionView.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            com.autopai.support.utils.AirConditionView.this.updatViews();
                        }
                    });
                }
            }
        };
        com.autopai.support.utils.SLog.d(LOGTAG, "AirConditionView start");
        this.mContext = context.getApplicationContext();
        this.mProxy = new com.autopai.support.proxy.AirConditionImplProxy(this.mContext);
        this.mProxy.setAirConditionStatusChangeListener(this.mListener);
        initLayout();
        initData();
    }

    private void initData() {
        updatViews();
    }

    private void initLayout() {
        com.autopai.support.utils.SLog.d(LOGTAG, "initLayout start");
        android.view.LayoutInflater.from(this.mContext).inflate(com.autopai.support.utils.AutoPaiResource.getIdentifierByLayout(this.mContext, "air_condition_layout"), this);
        this.mIconView = (com.autopai.support.view.TopImageView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "iv_aircondition_icon"));
        this.mDividerView = (com.autopai.support.view.TopImageView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "iv_divider"));
        this.mTempView = (com.autopai.support.view.TopTextView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "tv_temp"));
        this.mFunView = (com.autopai.support.view.TopTextView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "tv_fun"));
        this.mPuriferView = (com.autopai.support.view.TopImageView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "iv_purifer"));
        this.mOffView = (com.autopai.support.view.TopTextView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "tv_off"));
        this.mAirLevelIcon = (com.autopai.support.view.TopImageView) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "iv_air_level_icon"));
        this.mParentLinearLayout = (android.widget.LinearLayout) findViewById(com.autopai.support.utils.AutoPaiResource.getIdentifierById(this.mContext, "ll_parent"));
        this.mIconResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_ic_aircondition");
        this.mBgResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "air_condition_background");
        this.mDividerResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_aircondition_line");
        this.mTextResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByColor(this.mContext, "toprightview_clock_text_color");
        this.mPuriferResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "air_condition_purifer");
        this.mFunResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByColor(this.mContext, "toprightview_temp_text_color");
        this.mAirLevelResId = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(this.mContext, "jg_ic_aircondition_level");
        changeTheme();
    }

    public void destroy() {
        this.mProxy.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatViews() {
        com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue value = this.mProxy.getAirConditionValue();
        if (this.mOffView != null) {
            this.mOffView.setVisibility(8);
        }
        if (this.mAirLevelIcon != null) {
            this.mAirLevelIcon.setVisibility(8);
        }
        if (this.mPuriferView != null) {
            this.mPuriferView.setVisibility(8);
        }
        if (this.mDividerView != null) {
            this.mDividerView.setVisibility(8);
        }
        if (this.mTempView != null) {
            this.mTempView.setVisibility(8);
        }
        if (this.mFunView != null) {
            this.mFunView.setVisibility(8);
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "updatViews value.string = " + value.toString());
        if (value.isOpen) {
            if (this.mFunView != null) {
                this.mFunView.setVisibility(0);
                this.mFunView.setText(java.lang.String.valueOf(value.fan));
            }
            if (this.mTempView != null) {
                this.mTempView.setVisibility(0);
            }
            if (this.mDividerView != null) {
                this.mDividerView.setVisibility(0);
            }
            if (com.autopai.support.AppGlobal.getInstance(this.mContext).hasDoubleAirCondition()) {
                if (this.mTempView != null) {
                    this.mTempView.setText(value.leftTmp + com.incall.apps.common.net.Urls.SERVER_URL + value.rightTmp + DEGREE);
                }
                if (this.mPuriferView != null) {
                    if (value.isPurifierOpen) {
                        this.mPuriferView.setVisibility(0);
                        return;
                    } else {
                        this.mPuriferView.setVisibility(8);
                        return;
                    }
                }
                return;
            }
            if (com.autopai.support.AppGlobal.getInstance(this.mContext).isAutoAirCondition()) {
                if (this.mTempView != null) {
                    this.mTempView.setText(value.leftTmp + DEGREE);
                    return;
                }
                return;
            }
            if (this.mAirLevelIcon != null) {
                this.mAirLevelIcon.setVisibility(0);
            }
            if (this.mTempView != null) {
                this.mTempView.setText(value.level + getResources().getString(com.autopai.support.utils.AutoPaiResource.getIdentifierByString(this.mContext, "air_electric_level")));
                return;
            }
            return;
        }
        if (this.mOffView != null) {
            this.mOffView.setVisibility(0);
        }
    }

    @Override // com.autopai.support.IChangeTheme
    public void changeTheme() {
        android.graphics.drawable.Drawable icon = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mIconResId);
        if (this.mIconView != null) {
            this.mIconView.setImageDrawable(icon);
        }
        android.graphics.drawable.Drawable divider = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mDividerResId);
        if (this.mDividerView != null) {
            this.mDividerView.setImageDrawable(divider);
        }
        android.graphics.drawable.Drawable bg = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mBgResId);
        if (this.mParentLinearLayout != null) {
            this.mParentLinearLayout.setBackground(bg);
        }
        if (this.mTempView != null) {
            this.mTempView.setTextColor(com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getColorStateList(this.mTextResId));
        }
        if (this.mOffView != null) {
            this.mOffView.setTextColor(com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getColorStateList(this.mTextResId));
        }
        if (this.mFunView != null) {
            this.mFunView.setTextColor(com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getColorStateList(this.mTextResId));
        }
        android.graphics.drawable.Drawable puriferIcon = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mPuriferResId);
        if (this.mPuriferView != null) {
            this.mPuriferView.setImageDrawable(puriferIcon);
        }
        android.graphics.drawable.Drawable airLevelIcon = com.autopai.support.utils.ResourceUtils.getInstance(this.mContext).getDrawable(this.mAirLevelResId);
        if (this.mAirLevelIcon != null) {
            this.mAirLevelIcon.setImageDrawable(airLevelIcon);
        }
    }
}
