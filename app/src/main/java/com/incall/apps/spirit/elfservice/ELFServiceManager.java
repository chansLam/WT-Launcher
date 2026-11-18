package com.incall.apps.spirit.elfservice;

/* loaded from: classes.dex */
public class ELFServiceManager implements com.incall.apps.spirit.elfservice.IELFManager {
    private static final java.lang.String TAG = "ELFServiceManager";
    private static com.incall.apps.spirit.elfservice.ELFServiceManager mAIServiceManager;
    private com.incall.apps.spirit.elfservice.IELFService mIELFService;
    private java.lang.Object lock = new java.lang.Object();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.spirit.elfservice.ELFServiceManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.spirit.elfservice.ELFServiceManager.this.mIELFService == null) {
                        com.incall.apps.service.util.LogUtil.i(com.incall.apps.spirit.elfservice.ELFServiceManager.TAG, "run init getService");
                        com.incall.apps.spirit.elfservice.ELFServiceManager.this.init();
                    }
                    if (com.incall.apps.spirit.elfservice.ELFServiceManager.this.mIELFService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.spirit.elfservice.ELFServiceManager.this.checkService();
                        synchronized (com.incall.apps.spirit.elfservice.ELFServiceManager.this.lock) {
                            com.incall.apps.spirit.elfservice.ELFServiceManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.spirit.elfservice.ELFServiceManager.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    public static com.incall.apps.spirit.elfservice.ELFServiceManager getInstance() {
        if (mAIServiceManager == null) {
            synchronized (com.incall.apps.spirit.elfservice.ELFServiceManager.class) {
                if (mAIServiceManager == null) {
                    mAIServiceManager = new com.incall.apps.spirit.elfservice.ELFServiceManager();
                }
            }
        }
        return mAIServiceManager;
    }

    private ELFServiceManager() {
        init();
        this.mMotion.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder aiService = android.os.ServiceManager.getService(com.incall.apps.common.utils.CommonUtil.ELF_SERVICE_NAME);
        if (aiService != null) {
            if (this.mIELFService == null) {
                this.mIELFService = com.incall.apps.spirit.elfservice.IELFService.Stub.asInterface(aiService);
                return;
            }
            return;
        }
        android.util.Log.e(TAG, "init aiService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(TAG, "checkService success");
        try {
            this.mIELFService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.spirit.elfservice.ELFServiceManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.spirit.elfservice.ELFServiceManager.TAG, "checkService mIELFService binderDied");
                    com.incall.apps.spirit.elfservice.ELFServiceManager.this.mIELFService = null;
                    synchronized (com.incall.apps.spirit.elfservice.ELFServiceManager.this.lock) {
                        com.incall.apps.spirit.elfservice.ELFServiceManager.this.lock.notifyAll();
                    }
                }
            }, 0);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    @Override // com.incall.apps.spirit.elfservice.IELFManager
    public void showELFDialog(com.incall.apps.spirit.elfservice.option.BaseDialogOption option, com.incall.apps.spirit.elfservice.callback.IELFDialogCallback callback) {
        if (this.mIELFService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "showAIDialog mIELFService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "showAIDialog option = " + option);
            this.mIELFService.showELFDialog(option, callback);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "showAIDialog e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.spirit.elfservice.IELFManager
    public void cancelELFDialog(java.lang.String title) {
        if (this.mIELFService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "cancelAIDialog mIELFService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "cancelAIDialog title = " + title);
            this.mIELFService.cancelELFDialog(title);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "cancelAIDialog e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.spirit.elfservice.IELFManager
    public void updateELFDialog(java.lang.String title, com.incall.apps.spirit.elfservice.option.BaseDialogOption option) {
        if (this.mIELFService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "updateELFDialog mIELFService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "updateELFDialog title = " + title);
            this.mIELFService.updateELFDialog(title, option);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "updateELFDialog e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.spirit.elfservice.IELFManager
    public void updateElFDialogByKey(java.lang.String title, com.incall.apps.spirit.elfservice.option.BaseDialogOption option, java.lang.String key) {
        if (this.mIELFService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "updateElFDialogByKey mIELFService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "updateElFDialogByKey title = " + title);
            this.mIELFService.updateElFDialogByKey(title, option, key);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "updateElFDialogByKey e = " + e);
            e.printStackTrace();
        }
    }
}
