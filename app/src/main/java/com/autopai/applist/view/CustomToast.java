package com.autopai.applist.view;

/* loaded from: classes.dex */
public class CustomToast extends android.widget.Toast {
    private android.widget.TextView mContentView;
    private android.content.Context mContext;

    public CustomToast(android.content.Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // android.widget.Toast
    public void cancel() {
        super.cancel();
    }

    @Override // android.widget.Toast
    public void show() {
        super.show();
    }

    public void initToast(java.lang.String content) {
        android.view.LayoutInflater inflater = (android.view.LayoutInflater) this.mContext.getSystemService("layout_inflater");
        android.view.View layout = inflater.inflate(com.android.launcherWT.R.layout.toast_layout, (android.view.ViewGroup) null);
        this.mContentView = (android.widget.TextView) layout.findViewById(com.android.launcherWT.R.id.toast_text);
        this.mContentView.setText(content);
        setView(layout);
        setGravity(80, 0, 32);
    }

    public void showToast(int time) {
        if (time == 1) {
            setDuration(1);
        } else {
            setDuration(0);
        }
        show();
    }

    @Override // android.widget.Toast
    public void setText(java.lang.CharSequence s) {
        this.mContentView.setText(s);
    }

    public java.lang.String getText() {
        return this.mContentView.getText().toString();
    }
}
