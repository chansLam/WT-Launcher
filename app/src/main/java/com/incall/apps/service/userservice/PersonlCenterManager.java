package com.incall.apps.service.userservice;

/* loaded from: classes.dex */
public class PersonlCenterManager {
    private static com.incall.apps.service.userservice.PersonlCenterManager mPersonlCenterManager;
    private com.incall.apps.service.userservice.IPersonalService mIPersonalService;
    private java.lang.String TAG = "PersonlCenterManager";
    private java.lang.Object lock = new java.lang.Object();
    private java.util.concurrent.CopyOnWriteArrayList<com.incall.apps.service.userservice.AccountChangeListener> mAccountChangeList = new java.util.concurrent.CopyOnWriteArrayList<>();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.userservice.PersonlCenterManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.service.userservice.PersonlCenterManager.this.mIPersonalService == null) {
                        com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.userservice.PersonlCenterManager.this.TAG, "run init getService");
                        com.incall.apps.service.userservice.PersonlCenterManager.this.init();
                    }
                    if (com.incall.apps.service.userservice.PersonlCenterManager.this.mIPersonalService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.service.userservice.PersonlCenterManager.this.checkService();
                        synchronized (com.incall.apps.service.userservice.PersonlCenterManager.this.lock) {
                            com.incall.apps.service.userservice.PersonlCenterManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.userservice.PersonlCenterManager.this.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    private PersonlCenterManager() {
        init();
        this.mMotion.start();
    }

    public static synchronized com.incall.apps.service.userservice.PersonlCenterManager getInstance() {
        com.incall.apps.service.userservice.PersonlCenterManager personlCenterManager;
        synchronized (com.incall.apps.service.userservice.PersonlCenterManager.class) {
            if (mPersonlCenterManager == null) {
                mPersonlCenterManager = new com.incall.apps.service.userservice.PersonlCenterManager();
            }
            personlCenterManager = mPersonlCenterManager;
        }
        return personlCenterManager;
    }

    public boolean saveUserInfo(com.incall.apps.service.userservice.beans.User user) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "saveUserInfo : mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.saveUserInfo(user);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "saveUserInfo: RemoteException= " + e);
            return false;
        }
    }

    public boolean saveAppsInfo(com.incall.apps.service.userservice.beans.Apps app) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "saveAppsInfo : mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.saveAppsInfo(app);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "saveAppsInfo: RemoteException= " + e);
            return false;
        }
    }

    public boolean savekeysInfo(com.incall.apps.service.userservice.beans.Keys key) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "savekeysInfo : mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.savekeysInfo(key);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "savekeysInfo: RemoteException= " + e);
            return false;
        }
    }

    public boolean deleteUser(java.lang.String userId) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "deleteUser : mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.deleteUser(userId);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "deleteUser: RemoteException= " + e);
            return false;
        }
    }

    public java.util.List<com.incall.apps.service.userservice.beans.User> getAllUser() {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getAllUser : mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.getAllUser();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getAllUser: RemoteException= " + e);
            return null;
        }
    }

    public com.incall.apps.service.userservice.beans.Apps queryAppsInfo(java.lang.String userId) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "queryAppsInfo : mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.queryAppsInfo(userId);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "queryAppsInfo: RemoteException= " + e);
            return null;
        }
    }

    public com.incall.apps.service.userservice.beans.Keys queryKeysInfo(java.lang.String userId) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "queryKeysInfo : mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.queryKeysInfo(userId);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "queryKeysInfo: RemoteException= " + e);
            return null;
        }
    }

    public com.incall.apps.service.userservice.beans.User queryUserInfo(java.lang.String userId) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "queryUserInfo : mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.queryUserInfo(userId);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "queryUserInfo: RemoteException= " + e);
            return null;
        }
    }

    public boolean setUserLogin(com.incall.apps.service.userservice.beans.UserLogin loginUser) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "setUserState : mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.setUserLogin(loginUser);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "setUserLogin: RemoteException= " + e);
            return false;
        }
    }

    public com.incall.apps.service.userservice.beans.UserLogin getUserLogin() {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getUserState: mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.getUserLogin();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getUserLogin: RemoteException= " + e);
            return null;
        }
    }

    public void setUserState(int loginState) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "setUserState: mIPersonalService null");
            return;
        }
        try {
            this.mIPersonalService.setUserState(loginState);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "setUserState: RemoteException= " + e);
        }
    }

    public boolean saveOtherAppInfo(int mode, java.lang.String keys, java.lang.String values) {
        if (!isModeLegal(mode)) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "saveOtherAppInfo: " + mode + " illegality");
            return false;
        }
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "saveOtherAppInfo: mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.saveOtherAppInfo(mode, keys, values);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "saveOtherAppInfo: RemoteException= " + e);
            return false;
        }
    }

    public java.lang.String getOtherAppInfo(int mode, java.lang.String key) {
        if (!isModeLegal(mode)) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getOtherAppInfo: " + mode + " illegality");
            return null;
        }
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getOtherAppInfo: mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.getOtherAppInfo(mode, key);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getOtherAppInfo: RemoteException= " + e);
            return null;
        }
    }

    public boolean deleteOtherAppInfo(int mode, java.lang.String key) {
        if (!isModeLegal(mode)) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "deleteOtherAppInfo: " + mode + " illegality");
            return false;
        }
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "deleteOtherAppInfo: mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.deleteOtherAppInfo(mode, key);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "deleteOtherAppInfo: RemoteException= " + e);
            return false;
        }
    }

    public java.util.Map<java.lang.String, java.lang.String> getAllOtherAppInfo(int mode) {
        if (!isModeLegal(mode)) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getAllOtherAppInfo: " + mode + " illegality");
            return null;
        }
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getAllOtherAppInfo: mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.getAllOtherAppInfo(mode);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getAllOtherAppInfo: RemoteException= " + e);
            return null;
        }
    }

    public com.incall.apps.service.userservice.beans.Seats getSeatAdjustment(java.lang.String userId) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "getSeatAdjustment: mIPersonalService null");
            return null;
        }
        try {
            return this.mIPersonalService.getSeatAdjustment(userId);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getSeatAdjustment: RemoteException= " + e);
            return null;
        }
    }

    public boolean saveSeatAdjustment(com.incall.apps.service.userservice.beans.Seats seats) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "saveSeatAdjustment : mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.saveSeatAdjustment(seats);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "saveSeatAdjustment: RemoteException= " + e);
            return false;
        }
    }

    public void addAccountChangelistener(com.incall.apps.service.userservice.AccountChangeListener callback) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "addAccountChangelistenner: mIPersonalService null");
            return;
        }
        try {
            this.mIPersonalService.addAccountChangelistener(callback);
            this.mAccountChangeList.add(callback);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "addAccountChangelistenner: RemoteException= " + e);
        }
    }

    public void removeAccountChangelistener(com.incall.apps.service.userservice.AccountChangeListener callback) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "removeAccountChangelistener: mIPersonalService null");
            return;
        }
        try {
            this.mIPersonalService.removeAccountChangelistener(callback);
            this.mAccountChangeList.remove(callback);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "removeAccountChangelistener: RemoteException= " + e);
        }
    }

    public boolean isCarRunning() {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "isCarRunning: mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.isCarRunning();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "isCarRunning: RemoteException= " + e);
            return false;
        }
    }

    public boolean isOnline() {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.i(this.TAG, "isOnline: mIPersonalService null");
            return false;
        }
        try {
            return this.mIPersonalService.isOnline();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "isOnline: RemoteException= " + e);
            return false;
        }
    }

    public void uploadata(java.lang.String dtValue, java.lang.String dataStr) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "collectRadio null");
            return;
        }
        try {
            this.mIPersonalService.uploadbehavior(dtValue, dataStr);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "collectRadio e = " + e);
        }
    }

    public boolean collectRadio(com.incall.apps.service.userservice.beans.RadioCollect radio) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "collectRadio null");
            return false;
        }
        try {
            return this.mIPersonalService.collectRadio(radio);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "collectRadio e = " + e);
            return false;
        }
    }

    public boolean cancelCollectRadio(int frequency, int type) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "cancelCollectRadio null");
            return false;
        }
        try {
            return this.mIPersonalService.cancelCollectRadio(frequency, type);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "cancelCollectRadio e = " + e);
            return false;
        }
    }

    public boolean isRadioCollected(int frequency, int type) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "isRadioCollected null");
            return false;
        }
        try {
            return this.mIPersonalService.isRadioCollected(frequency, type);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "isRadioCollected e = " + e);
            return false;
        }
    }

    public java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> getRadioCollectList(int type, boolean isAsc) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getRadioCollectList null");
            return null;
        }
        try {
            return this.mIPersonalService.getRadioCollectList(type, isAsc);
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getRadioCollectList e = " + e);
            return null;
        }
    }

    public boolean collectRadios(java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> radios) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "collectRadios mIPersonalService = null");
            return false;
        }
        try {
            return this.mIPersonalService.collectRadios(radios);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "collectRadios e = " + e.getMessage());
            return false;
        }
    }

    public boolean cancelCollectRadios(java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> radios) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "cancelCollectRadios mIPersonalService = null");
            return false;
        }
        try {
            return this.mIPersonalService.cancelCollectRadios(radios);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "cancelCollectRadios e = " + e.getMessage());
            return false;
        }
    }

    public int getCollectSizeByType(int type) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getCollectSizeByType mIPersonalService = null");
            return 0;
        }
        try {
            return this.mIPersonalService.getCollectSizeByType(type);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getCollectSizeByType e = " + e.getMessage());
            return 0;
        }
    }

    public boolean deleteRadioCollects(int type) {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "deleteRadioCollects mIPersonalService = null");
            return false;
        }
        try {
            return this.mIPersonalService.deleteRadioCollects(type);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "deleteRadioCollects e = " + e.getMessage());
            return false;
        }
    }

    public java.lang.String getTuid() {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getTuid null");
            return null;
        }
        try {
            return this.mIPersonalService.getTuid();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getTuid e = " + e);
            return null;
        }
    }

    public java.lang.String getUuid() {
        if (this.mIPersonalService == null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getUuid null");
            return null;
        }
        try {
            return this.mIPersonalService.getUuid();
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, "getUuid e = " + e);
            return null;
        }
    }

    private boolean isModeLegal(int mode) {
        java.util.List<java.lang.Integer> list = java.util.Arrays.asList(com.incall.apps.service.userservice.UserCenterConstants.ModesType.MODE_LIST);
        return list.contains(java.lang.Integer.valueOf(mode));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder personalService = android.os.ServiceManager.getService("incall.user.service");
        if (personalService != null) {
            com.incall.apps.service.util.LogUtil.e(this.TAG, java.lang.String.valueOf(this.TAG) + " personalService != null");
            if (this.mIPersonalService == null) {
                this.mIPersonalService = com.incall.apps.service.userservice.IPersonalService.Stub.asInterface(personalService);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(this.TAG, java.lang.String.valueOf(this.TAG) + " personalService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(this.TAG, "checkService success");
        try {
            this.mIPersonalService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.userservice.PersonlCenterManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.userservice.PersonlCenterManager.this.TAG, "checkService mIPersonalService binderDied");
                    com.incall.apps.service.userservice.PersonlCenterManager.this.mIPersonalService = null;
                    synchronized (com.incall.apps.service.userservice.PersonlCenterManager.this.lock) {
                        com.incall.apps.service.userservice.PersonlCenterManager.this.lock.notifyAll();
                    }
                }
            }, 0);
            com.incall.apps.service.util.LogUtil.i(this.TAG, "checkService addAccountChangelistener size = " + this.mAccountChangeList.size());
            java.util.Iterator<com.incall.apps.service.userservice.AccountChangeListener> it = this.mAccountChangeList.iterator();
            while (it.hasNext()) {
                com.incall.apps.service.userservice.AccountChangeListener listener = it.next();
                com.incall.apps.service.util.LogUtil.i(this.TAG, "checkService addAccountChangelistener");
                addAccountChangelistener(listener);
            }
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
            com.incall.apps.service.util.LogUtil.e(this.TAG, "checkService RemoteException = ", e.getMessage());
        } catch (java.util.ConcurrentModificationException e2) {
            e2.printStackTrace();
            com.incall.apps.service.util.LogUtil.e(this.TAG, "checkService ConcurrentModificationException = ", e2.getMessage());
        }
    }
}
