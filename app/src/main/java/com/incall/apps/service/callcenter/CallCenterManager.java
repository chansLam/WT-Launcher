package com.incall.apps.service.callcenter;

/* loaded from: classes.dex */
public class CallCenterManager {
    private static com.incall.apps.service.callcenter.CallCenterManager mCallManager;
    private com.incall.apps.service.callcenter.ICallService mCallService;
    private java.lang.String TAG = "CallCenterManager";
    private java.lang.Object lock = new java.lang.Object();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.callcenter.CallCenterManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.service.callcenter.CallCenterManager.this.mCallService == null) {
                        com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.callcenter.CallCenterManager.this.TAG, "run init getService");
                        com.incall.apps.service.callcenter.CallCenterManager.this.init();
                    }
                    if (com.incall.apps.service.callcenter.CallCenterManager.this.mCallService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.service.callcenter.CallCenterManager.this.checkService();
                        synchronized (com.incall.apps.service.callcenter.CallCenterManager.this.lock) {
                            com.incall.apps.service.callcenter.CallCenterManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.callcenter.CallCenterManager.this.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    public static synchronized com.incall.apps.service.callcenter.CallCenterManager getInstance() {
        com.incall.apps.service.callcenter.CallCenterManager callCenterManager;
        synchronized (com.incall.apps.service.callcenter.CallCenterManager.class) {
            if (mCallManager == null) {
                mCallManager = new com.incall.apps.service.callcenter.CallCenterManager();
            }
            callCenterManager = mCallManager;
        }
        return callCenterManager;
    }

    private CallCenterManager() {
        init();
        this.mMotion.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        com.incall.apps.service.util.LogUtil.d(this.TAG, "init");
        android.os.IBinder callBinder = android.os.ServiceManager.getService("incall.callcenter.service");
        if (callBinder != null) {
            if (this.mCallService == null) {
                this.mCallService = com.incall.apps.service.callcenter.ICallService.Stub.asInterface(callBinder);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(this.TAG, "init mCallService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(this.TAG, "checkService success");
        try {
            this.mCallService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.callcenter.CallCenterManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.callcenter.CallCenterManager.this.TAG, "checkService mCallService binderDied");
                    com.incall.apps.service.callcenter.CallCenterManager.this.mCallService = null;
                    synchronized (com.incall.apps.service.callcenter.CallCenterManager.this.lock) {
                        com.incall.apps.service.callcenter.CallCenterManager.this.lock.notifyAll();
                    }
                }
            }, 0);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    public void call() {
        if (this.mCallService == null) {
            com.incall.apps.service.util.LogUtil.w(this.TAG, "call null");
            return;
        }
        try {
            this.mCallService.call();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "call e = " + e);
        }
    }

    public void finish() {
        if (this.mCallService == null) {
            com.incall.apps.service.util.LogUtil.w(this.TAG, "finish null");
            return;
        }
        try {
            this.mCallService.finish();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "finish e = " + e);
        }
    }
}
