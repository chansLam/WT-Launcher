package com.incall.apps.service.aircleaner;

/* loaded from: classes.dex */
public class AirCleanManager implements com.incall.apps.service.aircleaner.IAirManager {
    private static final int INVALID_RECYCLE = 4;
    private static final java.lang.String TAG = "AirCleanManager";
    private static com.incall.apps.service.aircleaner.AirCleanManager mAirManager;
    private com.incall.apps.service.aircleaner.IAirService mAirService;
    private com.incall.apps.service.aircleaner.IDataChange mCallback;
    private java.lang.Object lock = new java.lang.Object();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.aircleaner.AirCleanManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.service.aircleaner.AirCleanManager.this.mAirService == null) {
                        com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.aircleaner.AirCleanManager.TAG, "run init getService");
                        com.incall.apps.service.aircleaner.AirCleanManager.this.init();
                    }
                    if (com.incall.apps.service.aircleaner.AirCleanManager.this.mAirService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.service.aircleaner.AirCleanManager.this.checkService();
                        synchronized (com.incall.apps.service.aircleaner.AirCleanManager.this.lock) {
                            com.incall.apps.service.aircleaner.AirCleanManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.aircleaner.AirCleanManager.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    public static synchronized com.incall.apps.service.aircleaner.AirCleanManager getInstance() {
        com.incall.apps.service.aircleaner.AirCleanManager airCleanManager;
        synchronized (com.incall.apps.service.aircleaner.AirCleanManager.class) {
            if (mAirManager == null) {
                mAirManager = new com.incall.apps.service.aircleaner.AirCleanManager();
            }
            airCleanManager = mAirManager;
        }
        return airCleanManager;
    }

    private AirCleanManager() {
        init();
        if (com.incall.apps.common.utils.ConfigManager.getInstance().isShowAirClean()) {
            com.incall.apps.service.util.LogUtil.i(TAG, "init isShowFloat = true start");
            this.mMotion.start();
        } else {
            com.incall.apps.service.util.LogUtil.i(TAG, "init isShowFloat = false");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder airBinder = android.os.ServiceManager.getService("incall.airclean.service");
        if (airBinder != null) {
            if (this.mAirService == null) {
                this.mAirService = com.incall.apps.service.aircleaner.IAirService.Stub.asInterface(airBinder);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(TAG, "init mAirService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(TAG, "checkService success");
        try {
            this.mAirService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.aircleaner.AirCleanManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.aircleaner.AirCleanManager.TAG, "checkService mUpdateService binderDied");
                    com.incall.apps.service.aircleaner.AirCleanManager.this.mAirService = null;
                    synchronized (com.incall.apps.service.aircleaner.AirCleanManager.this.lock) {
                        com.incall.apps.service.aircleaner.AirCleanManager.this.lock.notifyAll();
                    }
                }
            }, 0);
            if (this.mCallback != null) {
                com.incall.apps.service.util.LogUtil.i(TAG, "checkService addCallback");
                setCallback(this.mCallback);
            }
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public void setCallback(com.incall.apps.service.aircleaner.IDataChange callback) {
        this.mCallback = callback;
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "addCallback null");
            return;
        }
        try {
            this.mAirService.setCallback(callback);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "addCallback e = " + e);
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public void removeCallback() {
        this.mCallback = null;
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "removeCallback null");
            return;
        }
        try {
            this.mAirService.removeCallback();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "removeCallback e = " + e);
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public int getInLevel() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "getInLevel null");
            return -1;
        }
        try {
            return this.mAirService.getInLevel();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "getInLevel e = " + e);
            return -1;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public int getOutLevel() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "getOutLevel null");
            return -1;
        }
        try {
            return this.mAirService.getOutLevel();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "getOutLevel e = " + e);
            return -1;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public boolean isPlasmaOn() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "isPlasmaOn null");
            return false;
        }
        try {
            return this.mAirService.isPlasmaOn();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "isPlasmaOn e = " + e);
            return false;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public int getRecycleType() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "getRecycleType null");
            return 4;
        }
        try {
            return this.mAirService.getRecycleType();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "getRecycleType e = " + e);
            return 4;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public boolean isWindOn() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "isWindOn null");
            return false;
        }
        try {
            return this.mAirService.isWindOn();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "isWindOn e = " + e);
            return false;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public void setIonizerEnable(boolean enable, boolean playVoice) {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "setIonizerEnable null");
            return;
        }
        try {
            this.mAirService.setIonizerEnable(enable, playVoice);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "setIonizerEnable e = " + e);
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public boolean isPowerOn() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "isPowerOn null");
            return false;
        }
        try {
            return this.mAirService.isPowerOn();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "isPowerOn e = " + e);
            return false;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public int getInPM() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "getInPM null");
            return -1;
        }
        try {
            return this.mAirService.getInPM();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "getInPM e = " + e);
            return -1;
        }
    }

    @Override // com.incall.apps.service.aircleaner.IAirManager
    public int getOutPM() {
        if (this.mAirService == null) {
            com.incall.apps.service.util.LogUtil.w(TAG, "getOutPM null");
            return -1;
        }
        try {
            return this.mAirService.getOutPM();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "getOutPM e = " + e);
            return -1;
        }
    }
}
