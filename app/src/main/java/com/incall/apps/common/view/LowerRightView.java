package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class LowerRightView extends android.widget.RelativeLayout {
    private java.lang.String TAG;
    private com.incall.proxy.voice.VoiceManager mVoiceManager;
    private boolean mfullscreen;

    public LowerRightView(android.content.Context context) {
        this(context, null);
    }

    public LowerRightView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LowerRightView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.TAG = "LowerRightView";
        this.mfullscreen = false;
        android.content.res.TypedArray ta = context.obtainStyledAttributes(attrs, com.android.launcherWT.R.styleable.LowerRightView);
        this.mfullscreen = ta.getBoolean(com.android.launcherWT.R.styleable.LowerRightView_fullsrceen, false);
        ta.recycle();
        initLayout(context);
    }

    private void initLayout(final android.content.Context context) {
        this.mVoiceManager = com.incall.proxy.voice.VoiceManager.getInstance();
        android.view.LayoutInflater.from(context).inflate(com.android.launcherWT.R.layout.lowerright_layout, this);
        setOnClickListener(new android.view.View.OnClickListener() { // from class: com.incall.apps.common.view.LowerRightView.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View v) {
                if (com.incall.proxy.bt.BtPhoneManager.getInstance().getOnGoingContacts().size() > 0) {
                    com.incall.apps.common.utils.DialogUtils.getInstance().showToast(context, context.getResources().getString(com.android.launcherWT.R.string.voice_fuction_is_not_supported_in_the_call), android.support.graphics.drawable.PathInterpolatorCompat.MAX_NUM_POINTS);
                    return;
                }
                boolean isS302TxCar = com.incall.proxy.setting.SettingManager.getInstance().isTencentUi();
                if (isS302TxCar) {
                    android.util.Log.e(com.incall.apps.common.view.LowerRightView.this.TAG, "isS302TxCar");
                    android.content.Intent intent = com.incall.apps.common.view.LowerRightView.this.getContext().getPackageManager().getLaunchIntentForPackage("com.tencent.wecarspeech");
                    if (intent != null) {
                        com.incall.apps.common.view.LowerRightView.this.getContext().startActivity(intent);
                        return;
                    }
                    return;
                }
                if (com.incall.apps.common.view.LowerRightView.this.mfullscreen) {
                    android.util.Log.e(com.incall.apps.common.view.LowerRightView.this.TAG, "mfullscreen is true");
                    com.incall.apps.common.view.LowerRightView.this.mVoiceManager.startSpeechClient(0, context.getApplicationContext().getPackageName());
                } else {
                    android.util.Log.e(com.incall.apps.common.view.LowerRightView.this.TAG, "mfullscreen is false");
                    com.incall.apps.common.view.LowerRightView.this.mVoiceManager.startSpeechClient(1, context.getApplicationContext().getPackageName());
                }
            }
        });
    }
}
