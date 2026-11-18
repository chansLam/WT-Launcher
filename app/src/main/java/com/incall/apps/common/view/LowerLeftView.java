package com.incall.apps.common.view;

/* loaded from: classes.dex */
public class LowerLeftView extends android.widget.RelativeLayout {
    private android.content.Context mContext;
    com.incall.apps.common.view.LowerLeftView.HomeClickListener mHomeClickListener;

    public interface HomeClickListener {
        void clickListener();
    }

    public LowerLeftView(android.content.Context context) {
        this(context, null);
    }

    public LowerLeftView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LowerLeftView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = null;
        initLayout(context);
        this.mContext = context;
    }

    private void initLayout(android.content.Context context) {
        android.view.LayoutInflater.from(context).inflate(com.android.launcherWT.R.layout.lowercommponent_layout, this);
        setOnClickListener(new android.view.View.OnClickListener() { // from class: com.incall.apps.common.view.LowerLeftView.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View v) {
                if (com.incall.apps.common.view.LowerLeftView.this.mHomeClickListener == null) {
                    android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.HOME");
                    com.incall.apps.common.view.LowerLeftView.this.mContext.startActivity(intent);
                    return;
                }
                com.incall.apps.common.view.LowerLeftView.this.mHomeClickListener.clickListener();
            }
        });
    }

    public void setHomeClickListener(com.incall.apps.common.view.LowerLeftView.HomeClickListener m) {
        this.mHomeClickListener = m;
    }
}
