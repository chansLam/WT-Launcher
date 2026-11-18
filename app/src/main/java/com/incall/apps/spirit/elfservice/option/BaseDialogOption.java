package com.incall.apps.spirit.elfservice.option;

/* loaded from: classes.dex */
public class BaseDialogOption implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.spirit.elfservice.option.BaseDialogOption> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.spirit.elfservice.option.BaseDialogOption>() { // from class: com.incall.apps.spirit.elfservice.option.BaseDialogOption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.spirit.elfservice.option.BaseDialogOption[] newArray(int size) {
            return new com.incall.apps.spirit.elfservice.option.BaseDialogOption[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.spirit.elfservice.option.BaseDialogOption createFromParcel(android.os.Parcel in) {
            return new com.incall.apps.spirit.elfservice.option.BaseDialogOption(in);
        }
    };
    private static final java.lang.String EXTRA_SHOW_TYPE = "showType";
    private static final java.lang.String EXTRA_SPEECH = "speech";
    public static final java.lang.String EXTRA_TITLE = "title";
    protected android.os.Bundle bundle;
    protected com.incall.apps.spirit.elfservice.callback.IELFDialogCallback callback;

    public BaseDialogOption() {
        this.bundle = new android.os.Bundle();
    }

    public BaseDialogOption(android.os.Bundle bundle, com.incall.apps.spirit.elfservice.callback.IELFDialogCallback callback) {
        this.bundle = bundle;
        this.callback = callback;
    }

    public java.lang.String getTitle() {
        return this.bundle.getString("title");
    }

    public void setTitle(java.lang.String title) {
        this.bundle.putString("title", title);
    }

    public void setSpeech(java.lang.String speech) {
        this.bundle.putString(EXTRA_SPEECH, speech);
    }

    public java.lang.String getSpeech() {
        return this.bundle.getString(EXTRA_SPEECH);
    }

    public void setShowType(int showType) {
        this.bundle.putInt(EXTRA_SHOW_TYPE, showType);
    }

    public int getShowType() {
        return this.bundle.getInt(EXTRA_SHOW_TYPE, 0);
    }

    public void setBundle(android.os.Bundle bundle) {
        this.bundle = bundle;
    }

    public android.os.Bundle getBundle() {
        return this.bundle;
    }

    public com.incall.apps.spirit.elfservice.callback.IELFDialogCallback getCallback() {
        return this.callback;
    }

    public void setCallback(com.incall.apps.spirit.elfservice.callback.IELFDialogCallback callback) {
        this.callback = callback;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected BaseDialogOption(android.os.Parcel in) {
        this.bundle = in.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int arg1) {
        dest.writeBundle(this.bundle);
    }

    public java.lang.String toString() {
        return "BaseDialogOption title:" + getTitle() + " speech:" + getSpeech() + ", callback:" + this.callback;
    }
}
