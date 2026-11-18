package com.incall.apps.service.can;

/* loaded from: classes.dex */
public class CarStatusManager {
    private static final java.lang.String TAG = "CarStatusManager";
    private final java.lang.Object lock;
    private java.lang.Thread mMotion;
    private com.incall.apps.service.can.ICarStatusService mService;

    private CarStatusManager() {
        this.lock = new java.lang.Object();
        this.mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.can.CarStatusManager.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (true) {
                    try {
                        if (com.incall.apps.service.can.CarStatusManager.this.mService == null) {
                            sleep(1000L);
                            com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.can.CarStatusManager.TAG, "run init getService");
                            com.incall.apps.service.can.CarStatusManager.this.init();
                        } else {
                            com.incall.apps.service.can.CarStatusManager.this.checkService();
                            synchronized (com.incall.apps.service.can.CarStatusManager.this.lock) {
                                com.incall.apps.service.can.CarStatusManager.this.lock.wait();
                            }
                        }
                    } catch (java.lang.InterruptedException e) {
                        com.incall.apps.service.can.CarStatusManager.this.loge(com.incall.apps.service.can.CarStatusManager.TAG, "mMotion InterruptedException = " + e.getMessage());
                        return;
                    }
                }
            }
        };
        init();
        this.mMotion.start();
    }

    /* synthetic */ CarStatusManager(com.incall.apps.service.can.CarStatusManager carStatusManager) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder settingService = android.os.ServiceManager.getService(com.incall.apps.common.utils.CommonUtil.CAR_STATUS_SERVICE_NAME);
        if (settingService != null) {
            if (this.mService == null) {
                this.mService = com.incall.apps.service.can.ICarStatusService.Stub.asInterface(settingService);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(TAG, " mService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        logi(TAG, "checkService success" + this.mService.getClass().toString());
        try {
            this.mService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.can.CarStatusManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.can.CarStatusManager.this.loge(com.incall.apps.service.can.CarStatusManager.TAG, "checkService mCallService binderDied");
                    com.incall.apps.service.can.CarStatusManager.this.mService = null;
                    synchronized (com.incall.apps.service.can.CarStatusManager.this.lock) {
                        com.incall.apps.service.can.CarStatusManager.this.lock.notifyAll();
                    }
                }
            }, 0);
        } catch (android.os.RemoteException e) {
            loge(TAG, "checkService RemoteException = " + e.getMessage());
        }
    }

    public void addCarStatusListener(com.incall.apps.service.can.CarStatusListener arg0) {
        if (this.mService == null) {
            loge(TAG, "addCarStatusListener mService = null");
            return;
        }
        try {
            this.mService.addCarStatusListener(arg0);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeCarStatusListener(com.incall.apps.service.can.CarStatusListener arg0) {
        if (this.mService == null) {
            loge(TAG, "removeCarStatusListener mService = null");
            return;
        }
        try {
            this.mService.removeCarStatusListener(arg0);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loge(java.lang.String tag, java.lang.String msg) {
        android.util.Log.e(tag, msg);
    }

    private void logi(java.lang.String tag, java.lang.String msg) {
        android.util.Log.i(tag, msg);
    }

    public void openLog() {
        if (this.mService == null) {
            loge(TAG, "removeCarStatusListener mService = null");
            return;
        }
        try {
            this.mService.openLog();
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void closeLog() {
        if (this.mService == null) {
            loge(TAG, "removeCarStatusListener mService = null");
            return;
        }
        try {
            this.mService.closeLog();
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public static com.incall.apps.service.can.CarStatusManager getInstance() {
        return com.incall.apps.service.can.CarStatusManager.Holder.MANAGER;
    }

    private static class Holder {
        private static final com.incall.apps.service.can.CarStatusManager MANAGER = new com.incall.apps.service.can.CarStatusManager(null);

        private Holder() {
        }
    }
}
