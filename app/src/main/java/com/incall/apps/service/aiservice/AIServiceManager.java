package com.incall.apps.service.aiservice;

/* loaded from: classes.dex */
public class AIServiceManager implements com.incall.apps.service.aiservice.IAIManager {
    private static final java.lang.String TAG = "AIServiceManager";
    private static com.incall.apps.service.aiservice.AIServiceManager mAIServiceManager;
    private com.incall.apps.service.aiservice.IAIService mIAIService;
    private java.lang.Object lock = new java.lang.Object();
    private java.util.concurrent.CopyOnWriteArrayList<com.incall.apps.service.aiservice.callback.IAICallback> mCallbacks = new java.util.concurrent.CopyOnWriteArrayList<>();
    private java.lang.Thread mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.aiservice.AIServiceManager.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    if (com.incall.apps.service.aiservice.AIServiceManager.this.mIAIService == null) {
                        com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.aiservice.AIServiceManager.TAG, "run init getService");
                        com.incall.apps.service.aiservice.AIServiceManager.this.init();
                    }
                    if (com.incall.apps.service.aiservice.AIServiceManager.this.mIAIService == null) {
                        sleep(1000L);
                    } else {
                        com.incall.apps.service.aiservice.AIServiceManager.this.checkService();
                        synchronized (com.incall.apps.service.aiservice.AIServiceManager.this.lock) {
                            com.incall.apps.service.aiservice.AIServiceManager.this.lock.wait();
                        }
                    }
                } catch (java.lang.InterruptedException e) {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.aiservice.AIServiceManager.TAG, "mMotion sleep InterruptedException = ", e.getMessage());
                    return;
                }
            }
        }
    };

    public static com.incall.apps.service.aiservice.AIServiceManager getInstance() {
        if (mAIServiceManager == null) {
            synchronized (com.incall.apps.service.aiservice.AIServiceManager.class) {
                if (mAIServiceManager == null) {
                    mAIServiceManager = new com.incall.apps.service.aiservice.AIServiceManager();
                }
            }
        }
        return mAIServiceManager;
    }

    private AIServiceManager() {
        init();
        this.mMotion.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder aiService = android.os.ServiceManager.getService(com.incall.apps.common.utils.CommonUtil.AI_SERVICE_NAME);
        if (aiService != null) {
            if (this.mIAIService == null) {
                this.mIAIService = com.incall.apps.service.aiservice.IAIService.Stub.asInterface(aiService);
                return;
            }
            return;
        }
        com.incall.apps.service.util.LogUtil.d(TAG, "init aiService = null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkService() {
        com.incall.apps.service.util.LogUtil.i(TAG, "checkService success");
        try {
            this.mIAIService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.aiservice.AIServiceManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.util.LogUtil.e(com.incall.apps.service.aiservice.AIServiceManager.TAG, "checkService mIAIService binderDied");
                    com.incall.apps.service.aiservice.AIServiceManager.this.mIAIService = null;
                    synchronized (com.incall.apps.service.aiservice.AIServiceManager.this.lock) {
                        com.incall.apps.service.aiservice.AIServiceManager.this.lock.notifyAll();
                    }
                }
            }, 0);
            com.incall.apps.service.util.LogUtil.i(TAG, "checkService registerThemeChangeListener size = " + this.mCallbacks.size());
            java.util.Iterator<com.incall.apps.service.aiservice.callback.IAICallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                com.incall.apps.service.aiservice.callback.IAICallback callback = it.next();
                com.incall.apps.service.util.LogUtil.i(TAG, "checkService addCallback callback = " + callback);
                add(callback);
            }
        } catch (android.os.RemoteException e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "checkService RemoteException = ", e.getMessage());
        }
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void addCallback(com.incall.apps.service.aiservice.callback.IAICallback callback) {
        this.mCallbacks.add(callback);
        add(callback);
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void removeCallback(com.incall.apps.service.aiservice.callback.IAICallback callback) {
        if (this.mCallbacks.contains(callback)) {
            this.mCallbacks.remove(callback);
        }
        remove(callback);
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public java.lang.String getBournForecastData() {
        if (this.mIAIService != null) {
            try {
                return this.mIAIService.getBournForecastData();
            } catch (android.os.RemoteException e) {
                e.printStackTrace();
            }
        }
        com.incall.apps.service.util.LogUtil.e(TAG, "getBournForecastData mIAIService null");
        return null;
    }

    private void add(com.incall.apps.service.aiservice.callback.IAICallback callback) {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "addCallback mIAIService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "addCallback callback = " + callback);
            this.mIAIService.addCallback(callback);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "addCallback e = " + e);
            e.printStackTrace();
        }
    }

    private void remove(com.incall.apps.service.aiservice.callback.IAICallback callback) {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "removeCallback mIAIService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "removeCallback callback = " + callback);
            this.mIAIService.removeCallback(callback);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "removeCallback e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void uploadBehavior(java.lang.String type) {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "uploadBehavior mIAIService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "uploadBehavior");
            this.mIAIService.uploadBehavior(type);
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "uploadBehavior e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void clickHome() {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "clickHome mIAIService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "clickHome");
            this.mIAIService.clickHome();
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "clickHome e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void clickCompany() {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "clickCompany mIAIService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "clickCompany");
            this.mIAIService.clickCompany();
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "clickCompany e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void clickBournForecast() {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "clickBournForecast mIAIService null");
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "clickBournForecast");
            this.mIAIService.clickBournForecast();
        } catch (java.lang.Exception e) {
            com.incall.apps.service.util.LogUtil.e(TAG, "clickBournForecast e = " + e);
            e.printStackTrace();
        }
    }

    @Override // com.incall.apps.service.aiservice.IAIManager
    public void requestNetwork(java.lang.String url, java.util.Map<java.lang.String, java.lang.String> params, com.incall.apps.service.aiservice.callback.INetworkCallback callback) {
        if (this.mIAIService == null) {
            com.incall.apps.service.util.LogUtil.e(TAG, "requestNetwork mIAIService null");
            if (callback != null) {
                try {
                    callback.onError(1, "AI_SERVICE_NULL");
                    return;
                } catch (android.os.RemoteException e) {
                    com.incall.apps.service.util.LogUtil.e(TAG, "requestNetwork onError e = " + e);
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        try {
            com.incall.apps.service.util.LogUtil.d(TAG, "requestNetwork");
            this.mIAIService.requestNetwork(url, params, callback);
        } catch (java.lang.Exception e2) {
            com.incall.apps.service.util.LogUtil.e(TAG, "requestNetwork e = " + e2);
            e2.printStackTrace();
        }
    }
}
