package com.incall.apps.service.kuwoservice;

/* loaded from: classes.dex */
public class KuwoManager {
    private static com.incall.apps.service.kuwoservice.KuwoManager mKuwoManager;
    private com.incall.apps.service.kuwoservice.IKuwoService mKuwoService;
    private java.lang.String TAG = "KuwoManager";
    private java.lang.Object lock = new java.lang.Object();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.kuwoservice.KuwoManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.service.kuwoservice.KuwoManager.this.mKuwoService == null) {
                        com.incall.apps.service.util.LogUtil.i(com.incall.apps.service.kuwoservice.KuwoManager.this.TAG, "run init getService");
                        com.incall.apps.service.kuwoservice.KuwoManager.this.init();
                    }
                    if (com.incall.apps.service.kuwoservice.KuwoManager.this.mKuwoService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.service.kuwoservice.KuwoManager.this.checkService();
                        synchronized (com.incall.apps.service.kuwoservice.KuwoManager.this.lock) {
                            com.incall.apps.service.kuwoservice.KuwoManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    e.printStackTrace();
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.kuwoservice.KuwoManager.this.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    public static synchronized com.incall.apps.service.kuwoservice.KuwoManager getInstance() {
        com.incall.apps.service.kuwoservice.KuwoManager kuwoManager;
        synchronized (com.incall.apps.service.kuwoservice.KuwoManager.class) {
            if (mKuwoManager == null) {
                mKuwoManager = new com.incall.apps.service.kuwoservice.KuwoManager();
            }
            kuwoManager = mKuwoManager;
        }
        return kuwoManager;
    }

    private KuwoManager() {
        init();
        if (com.incall.apps.common.utils.ConfigManager.getInstance().isShowOnlineMusic()) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "init isShowOnlineMusic = true start");
            this.mMotion.start();
        } else {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "init isShowOnlineMusic = false");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder kuwoService = android.os.ServiceManager.getService("incall.kuwo.service");
        if (kuwoService != null) {
            if (this.mKuwoService == null) {
                this.mKuwoService = com.incall.apps.service.kuwoservice.IKuwoService.Stub.asInterface(kuwoService);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(this.TAG, java.lang.String.valueOf(this.TAG) + " kuwoService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(this.TAG, "checkService success");
        try {
            this.mKuwoService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.kuwoservice.KuwoManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.kuwoservice.KuwoManager.this.TAG, "checkService mKuwoService binderDied");
                    com.incall.apps.service.kuwoservice.KuwoManager.this.mKuwoService = null;
                    synchronized (com.incall.apps.service.kuwoservice.KuwoManager.this.lock) {
                        com.incall.apps.service.kuwoservice.KuwoManager.this.lock.notifyAll();
                    }
                }
            }, 0);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
            com.incall.apps.service.util.LogUtil.e(this.TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    public void openKuwo() throws android.os.RemoteException {
        if (this.mKuwoService == null) {
            android.util.Log.i(this.TAG, "mKuwoService null");
        } else {
            this.mKuwoService.openKuwoMusic();
        }
    }

    public boolean isKuwoPlaying() {
        if (this.mKuwoService == null) {
            android.util.Log.i(this.TAG, "isKuwoPlaying mKuwoService null");
            return false;
        }
        try {
            return this.mKuwoService.isKuwoPlaying();
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
