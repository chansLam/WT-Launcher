package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class DialogUtils {
    private android.app.Dialog dialog;
    private int type;

    private DialogUtils() {
        this.type = 2003;
    }

    /* synthetic */ DialogUtils(com.incall.apps.common.utils.DialogUtils dialogUtils) {
        this();
    }

    private static class InstanceHolder {
        private static final com.incall.apps.common.utils.DialogUtils instance = new com.incall.apps.common.utils.DialogUtils(null);

        private InstanceHolder() {
        }
    }

    public static com.incall.apps.common.utils.DialogUtils getInstance() {
        return com.incall.apps.common.utils.DialogUtils.InstanceHolder.instance;
    }

    public void setWindowType(int type) {
        this.type = type;
    }

    public void showToast(android.content.Context context, java.lang.String msg) {
        showToast(context, msg, 0, 5000);
    }

    public void showToast(android.content.Context context, java.lang.String msg, int duration) {
        showToast(context, msg, 0, duration);
    }

    public void showToastWithIcon(android.content.Context context, java.lang.String msg, int drwable, int duration) {
        showToast(context, msg, drwable, duration);
    }

    public void showToastWithIcon(android.content.Context context, java.lang.String msg, int drwable) {
        showToast(context, msg, drwable, 5000);
    }

    public void showLoading(android.content.Context context, java.lang.String msg) {
        showToast(context, msg, com.android.launcherWT.R.drawable.common_loading, -1);
    }

    public void showConfirmWithOneBtn(android.content.Context context, java.lang.String msg, java.lang.String btnTitle, android.view.View.OnClickListener listener) {
        showConfirmWithTwoBtn(context, msg, btnTitle, listener, null, null);
    }

    public void closeShowingDialog() {
        if (this.dialog != null) {
            try {
                this.dialog.dismiss();
                this.dialog = null;
            } catch (java.lang.Exception e) {
            }
        }
    }

    public void showToast(android.content.Context context, java.lang.String msg, int icon, int duration) {
        closeShowingDialog();
        this.dialog = new android.app.Dialog(context.getApplicationContext(), com.android.launcherWT.R.style.DimDialogTheme);
        this.dialog.setContentView(com.android.launcherWT.R.layout.common_toast_custom);
        this.dialog.getWindow().setType(this.type);
        android.widget.TextView textView = (android.widget.TextView) this.dialog.findViewById(com.android.launcherWT.R.id.textViewToastMsg);
        if (textView != null) {
            if (msg != null) {
                textView.setText(msg);
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
        }
        if (icon > 0) {
            android.widget.ImageView imageView = (android.widget.ImageView) this.dialog.findViewById(com.android.launcherWT.R.id.imageViewIcon);
            imageView.setImageResource(icon);
            imageView.setVisibility(0);
        } else {
            this.dialog.findViewById(com.android.launcherWT.R.id.imageViewIcon).setVisibility(8);
        }
        this.dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
        this.dialog.show();
        if (duration > 0) {
            final android.os.CountDownTimer timer = new android.os.CountDownTimer(duration, 1000L) { // from class: com.incall.apps.common.utils.DialogUtils.1
                @Override // android.os.CountDownTimer
                public void onTick(long arg0) {
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    if (com.incall.apps.common.utils.DialogUtils.this.dialog != null && com.incall.apps.common.utils.DialogUtils.this.dialog.isShowing()) {
                        com.incall.apps.common.utils.DialogUtils.this.closeShowingDialog();
                    }
                }
            };
            timer.start();
            this.dialog.findViewById(com.android.launcherWT.R.id.linearLayoutClickArea).setOnClickListener(new android.view.View.OnClickListener() { // from class: com.incall.apps.common.utils.DialogUtils.2
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View v) {
                    if (com.incall.apps.common.utils.DialogUtils.this.dialog != null && com.incall.apps.common.utils.DialogUtils.this.dialog.isShowing()) {
                        timer.cancel();
                        com.incall.apps.common.utils.DialogUtils.this.closeShowingDialog();
                    }
                }
            });
        }
    }

    public void showConfirmWithTwoBtn(android.content.Context context, java.lang.String msg, java.lang.String leftBtnTitle, android.view.View.OnClickListener leftBtnListener, java.lang.String rightBtnTitle, android.view.View.OnClickListener rightBtnListener) {
        showTipConfirmWithTwoBtn(context, false, msg, leftBtnTitle, leftBtnListener, rightBtnTitle, rightBtnListener);
    }

    public void showTipConfirmWithTwoBtn(android.content.Context context, boolean haveTipIcan, java.lang.String msg, java.lang.String leftBtnTitle, final android.view.View.OnClickListener leftBtnListener, java.lang.String rightBtnTitle, final android.view.View.OnClickListener rightBtnListener) {
        closeShowingDialog();
        this.dialog = new android.app.Dialog(context.getApplicationContext(), com.android.launcherWT.R.style.DimDialogTheme);
        if (haveTipIcan) {
            this.dialog.setContentView(com.android.launcherWT.R.layout.common_dialog_custom_with_ican);
        } else {
            this.dialog.setContentView(com.android.launcherWT.R.layout.common_dialog_custom);
        }
        this.dialog.getWindow().setType(this.type);
        if (msg != null) {
            android.widget.TextView textView = (android.widget.TextView) this.dialog.findViewById(com.android.launcherWT.R.id.textViewMsg);
            textView.setText(msg);
            android.widget.Button buttonDialogLeft = (android.widget.Button) this.dialog.findViewById(com.android.launcherWT.R.id.buttonDialogLeft);
            if (leftBtnTitle != null) {
                buttonDialogLeft.setText(leftBtnTitle);
                buttonDialogLeft.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.incall.apps.common.utils.DialogUtils.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(android.view.View v) {
                        com.incall.apps.common.utils.DialogUtils.this.closeShowingDialog();
                        if (leftBtnListener != null) {
                            leftBtnListener.onClick(v);
                        }
                    }
                });
                buttonDialogLeft.setVisibility(0);
            } else {
                buttonDialogLeft.setVisibility(8);
            }
            android.widget.Button buttonDialogRight = (android.widget.Button) this.dialog.findViewById(com.android.launcherWT.R.id.buttonDialogRight);
            if (rightBtnTitle != null) {
                buttonDialogRight.setText(rightBtnTitle);
                buttonDialogRight.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.incall.apps.common.utils.DialogUtils.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(android.view.View v) {
                        com.incall.apps.common.utils.DialogUtils.this.closeShowingDialog();
                        if (rightBtnListener != null) {
                            rightBtnListener.onClick(v);
                        }
                    }
                });
                buttonDialogRight.setVisibility(0);
            } else {
                buttonDialogRight.setVisibility(8);
            }
            this.dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
            this.dialog.show();
        }
    }

    public void setChildViewEnabled(boolean enable, android.view.ViewGroup viewGroup) {
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            android.view.View child = viewGroup.getChildAt(j);
            try {
                setChildViewEnabled(enable, (android.view.ViewGroup) child);
            } catch (java.lang.Exception e) {
                viewGroup.getChildAt(j).setEnabled(enable);
            }
        }
    }

    public void setBackgroundRes(android.content.Context context, android.view.View view, int viewId) {
        android.graphics.BitmapFactory.Options opt = new android.graphics.BitmapFactory.Options();
        opt.inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        java.io.InputStream is = context.getResources().openRawResource(viewId);
        android.graphics.Bitmap bm = android.graphics.BitmapFactory.decodeStream(is, null, opt);
        android.graphics.drawable.BitmapDrawable bd = new android.graphics.drawable.BitmapDrawable(context.getResources(), bm);
        view.setBackground(bd);
        try {
            is.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(getClass().getName(), "setBackgroundRes e = " + e.getMessage());
        } finally {
            java.lang.System.gc();
        }
    }

    public void toastMessage(android.content.Context context, java.lang.String messages) {
        if (context instanceof android.app.Activity) {
            android.view.LayoutInflater inflater = ((android.app.Activity) context).getLayoutInflater();
            android.view.View view = inflater.inflate(com.android.launcherWT.R.layout.toast_layout, (android.view.ViewGroup) null);
            android.widget.TextView text = (android.widget.TextView) view.findViewById(com.android.launcherWT.R.id.toast_layout_text);
            text.setTextSize(24.0f);
            text.setText(messages);
            android.widget.Toast toast = new android.widget.Toast(context);
            toast.setGravity(17, 0, 0);
            toast.setDuration(0);
            toast.setView(view);
            toast.show();
        }
    }
}
