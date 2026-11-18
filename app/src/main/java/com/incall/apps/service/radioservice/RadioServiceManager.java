package com.incall.apps.service.radioservice;

/* loaded from: classes.dex */
public class RadioServiceManager {
    private static com.incall.apps.service.radioservice.RadioServiceManager mRadioManager;
    private final android.content.Context mContext;
    private com.incall.apps.service.radioservice.IRadioService mRadioService;
    private java.lang.String TAG = "RadioServiceManager";
    private java.lang.Object lock = new java.lang.Object();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.radioservice.RadioServiceManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                if (com.incall.apps.service.radioservice.RadioServiceManager.this.mRadioService == null) {
                    com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.radioservice.RadioServiceManager.this.TAG, "run init getService");
                    com.incall.apps.service.radioservice.RadioServiceManager.this.init();
                }
                if (com.incall.apps.service.radioservice.RadioServiceManager.this.mRadioService == null) {
                    try {
                        sleep(1000L);
                    } catch (java.lang.InterruptedException e) {
                        com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.radioservice.RadioServiceManager.this.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    }
                } else {
                    com.incall.apps.service.radioservice.RadioServiceManager.this.checkService();
                    synchronized (com.incall.apps.service.radioservice.RadioServiceManager.this.lock) {
                        try {
                            com.incall.apps.service.radioservice.RadioServiceManager.this.lock.wait();
                        } catch (java.lang.InterruptedException e2) {
                            com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.radioservice.RadioServiceManager.this.TAG, "mMotion wait InterruptedException = ", e2.getMessage());
                        }
                    }
                }
            }
        }
    };

    public static synchronized com.incall.apps.service.radioservice.RadioServiceManager getInstance(android.content.Context aContext) {
        com.incall.apps.service.radioservice.RadioServiceManager radioServiceManager;
        synchronized (com.incall.apps.service.radioservice.RadioServiceManager.class) {
            if (mRadioManager == null) {
                mRadioManager = new com.incall.apps.service.radioservice.RadioServiceManager(aContext);
            }
            radioServiceManager = mRadioManager;
        }
        return radioServiceManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(this.TAG, "checkService success");
        try {
            this.mRadioService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.radioservice.RadioServiceManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.radioservice.RadioServiceManager.this.TAG, "checkService mRadioService binderDied");
                    com.incall.apps.service.radioservice.RadioServiceManager.this.mRadioService = null;
                    synchronized (com.incall.apps.service.radioservice.RadioServiceManager.this.lock) {
                        com.incall.apps.service.radioservice.RadioServiceManager.this.lock.notifyAll();
                    }
                }
            }, 0);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    private RadioServiceManager(android.content.Context context) {
        this.mContext = context;
        init();
        this.mMotion.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder radioService = android.os.ServiceManager.getService("incall.radio.service");
        if (radioService != null) {
            if (this.mRadioService == null) {
                this.mRadioService = com.incall.apps.service.radioservice.IRadioService.Stub.asInterface(radioService);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(this.TAG, java.lang.String.valueOf(this.TAG) + " radioService = null");
    }

    public com.incall.apps.service.radioservice.IRadioService getIRadioService() {
        return this.mRadioService;
    }

    public boolean getShowOnlineRadio() throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getShowOnlineRadio();
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean collectLocalRadio(int frequent, int type) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.collectLocalRadio(frequent, type);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public int deleteCollectLocalRadio(int frequent, int type) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.deleteCollectLocalRadio(frequent, type);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return 0;
    }

    public boolean isCollectLocalRadio(int frequent, int type) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.isCollectLocalRadio(frequent, type);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public java.lang.String getLocalCollectBeanList(int type, boolean isAsc) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getLocalCollectBeanList(type, isAsc);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return null;
    }

    public java.lang.String getAllRadioCollectList(boolean isAsc) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getAllRadioCollectList(isAsc);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return null;
    }

    public boolean collectXmlySound(java.lang.String soundString) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.collectXmlySound(soundString);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public java.lang.String getcollectXmlySound(long id) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getcollectXmlySound(id);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return null;
    }

    public int deleteCollectXmlySound(long id) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.deleteCollectXmlySound(id);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return 0;
    }

    public boolean isCollectXmlySound(long id) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.isCollectXmlySound(id);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean isExistXmlyPlayHistory(long id) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.isExistXmlyPlayHistory(id);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean collectXmlyPlayHistory(java.lang.String beanString) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.collectXmlyPlayHistory(beanString);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public int deleteXmlyPlayHistory(long id) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.deleteXmlyPlayHistory(id);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return 0;
    }

    public boolean deleteAllXmlyPlayHistory() throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.deleteAllXmlyPlayHistory();
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public java.lang.String getAllXmlyPlayHistory() throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getAllXmlyPlayHistory();
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return null;
    }

    public boolean isExistXmlySearchHistory(java.lang.String searString) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.isExistXmlySearchHistory(searString);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean updateXmlySearchHistoryTime(java.lang.String searString) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.updateXmlySearchHistoryTime(searString);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean collectXmlySearchHistory(java.lang.String searString) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.collectXmlySearchHistory(searString);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean deleteXmlySearchHistory(java.lang.String searString) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.deleteXmlySearchHistory(searString);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public boolean deleteAllXmlySearchHistory() throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.deleteAllXmlySearchHistory();
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return false;
    }

    public java.lang.String getAllXmlySearchHistory() throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getAllXmlySearchHistory();
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return null;
    }

    public java.lang.String getXmlyCollectBeanList(boolean isAsc) throws android.os.RemoteException {
        if (this.mRadioService != null) {
            return this.mRadioService.getXmlyCollectBeanList(isAsc);
        }
        android.util.Log.i(this.TAG, "mRadioService null");
        return null;
    }

    public boolean updateCollectRadio(int fromFrequency, int toFrequency, int type) throws android.os.RemoteException {
        if (this.mRadioService == null) {
            android.util.Log.i(this.TAG, "updateCollectRadio--mRadioService null");
            return false;
        }
        boolean result = this.mRadioService.updateCollectRadio(fromFrequency, toFrequency, type);
        android.util.Log.i(this.TAG, "updateCollectRadio=" + result);
        return result;
    }

    public boolean collectLocalRadioList(java.lang.String radioString, int type) throws android.os.RemoteException {
        com.incall.apps.service.util.LogUtil.d(this.TAG, "collectLocalRadioList");
        if (this.mRadioService == null) {
            android.util.Log.i(this.TAG, "collectLocalRadioList--mRadioService null");
            return false;
        }
        boolean result = this.mRadioService.collectLocalRadioList(radioString, type);
        android.util.Log.i(this.TAG, "collectLocalRadioList=" + result);
        return result;
    }

    public boolean collectXmlySoundList(java.lang.String radioListStr) throws android.os.RemoteException {
        com.incall.apps.service.util.LogUtil.d(this.TAG, "collectXmlySoundList = " + radioListStr);
        if (this.mRadioService == null) {
            android.util.Log.i(this.TAG, "collectXmlySoundList--mRadioService null");
            return false;
        }
        boolean result = this.mRadioService.collectXmlySoundList(radioListStr);
        android.util.Log.i(this.TAG, "collectXmlySoundList = " + result);
        return result;
    }
}
