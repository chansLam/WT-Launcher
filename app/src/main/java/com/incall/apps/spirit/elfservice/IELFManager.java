package com.incall.apps.spirit.elfservice;

/* loaded from: classes.dex */
public interface IELFManager {
    void cancelELFDialog(java.lang.String str);

    void showELFDialog(com.incall.apps.spirit.elfservice.option.BaseDialogOption baseDialogOption, com.incall.apps.spirit.elfservice.callback.IELFDialogCallback iELFDialogCallback);

    void updateELFDialog(java.lang.String str, com.incall.apps.spirit.elfservice.option.BaseDialogOption baseDialogOption);

    void updateElFDialogByKey(java.lang.String str, com.incall.apps.spirit.elfservice.option.BaseDialogOption baseDialogOption, java.lang.String str2);
}
