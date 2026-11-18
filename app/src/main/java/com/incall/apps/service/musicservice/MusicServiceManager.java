package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public class MusicServiceManager {
    private static final java.lang.String TAG = "MusicServiceManager";
    private final java.lang.Object lock;
    private java.lang.Thread mMotion;
    private com.incall.apps.service.musicservice.MusicDataListener mMusicDataListener;
    private java.util.concurrent.CopyOnWriteArrayList<com.incall.apps.service.musicservice.MusicDataListener> mMusicDataListenerList;
    private com.incall.apps.service.musicservice.MusicListListener mMusicListListener;
    private java.util.concurrent.CopyOnWriteArrayList<com.incall.apps.service.musicservice.MusicListListener> mMusicListListenerList;
    private com.incall.apps.service.musicservice.MusicPlayerCallback mMusicPlayerCallback;
    private com.incall.apps.service.musicservice.IMusicService mService;

    private MusicServiceManager() {
        this.lock = new java.lang.Object();
        this.mMusicListListenerList = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mMusicDataListenerList = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mMotion = new java.lang.Thread() { // from class: com.incall.apps.service.musicservice.MusicServiceManager.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (true) {
                    try {
                        if (com.incall.apps.service.musicservice.MusicServiceManager.this.mService == null) {
                            sleep(1000L);
                            com.incall.apps.service.util.LogUtil.d(com.incall.apps.service.musicservice.MusicServiceManager.TAG, "run init getService");
                            com.incall.apps.service.musicservice.MusicServiceManager.this.init();
                        } else {
                            com.incall.apps.service.musicservice.MusicServiceManager.this.checkService();
                            synchronized (com.incall.apps.service.musicservice.MusicServiceManager.this.lock) {
                                com.incall.apps.service.musicservice.MusicServiceManager.this.lock.wait();
                            }
                        }
                    } catch (java.lang.Exception e) {
                        com.incall.apps.service.musicservice.MusicServiceManager.this.loge(com.incall.apps.service.musicservice.MusicServiceManager.TAG, "mMotion Exception = " + e.getMessage());
                        return;
                    }
                }
            }
        };
        init();
        this.mMotion.start();
    }

    /* synthetic */ MusicServiceManager(com.incall.apps.service.musicservice.MusicServiceManager musicServiceManager) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        android.os.IBinder settingService = android.os.ServiceManager.getService(com.incall.apps.common.utils.CommonUtil.MUSIC_SERVICE_NAME);
        if (settingService != null) {
            if (this.mService == null) {
                this.mService = com.incall.apps.service.musicservice.IMusicService.Stub.asInterface(settingService);
                if (this.mMusicPlayerCallback != null) {
                    setMusicPlayerCallback(this.mMusicPlayerCallback);
                    return;
                }
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
            this.mService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.incall.apps.service.musicservice.MusicServiceManager.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    com.incall.apps.service.musicservice.MusicServiceManager.this.loge(com.incall.apps.service.musicservice.MusicServiceManager.TAG, "checkService mCallService binderDied");
                    com.incall.apps.service.musicservice.MusicServiceManager.this.mService = null;
                    synchronized (com.incall.apps.service.musicservice.MusicServiceManager.this.lock) {
                        com.incall.apps.service.musicservice.MusicServiceManager.this.lock.notifyAll();
                    }
                }
            }, 0);
            if (this.mMusicListListener != null) {
                setMusicListListener(this.mMusicListListener);
            }
            if (this.mMusicDataListener != null) {
                setMusicDataListener(this.mMusicDataListener);
            }
            java.util.Iterator<com.incall.apps.service.musicservice.MusicListListener> it = this.mMusicListListenerList.iterator();
            while (it.hasNext()) {
                com.incall.apps.service.musicservice.MusicListListener listener = it.next();
                com.incall.apps.service.util.LogUtil.i(TAG, "checkService addMusicListListener");
                addMusicListListener(listener);
            }
            java.util.Iterator<com.incall.apps.service.musicservice.MusicDataListener> it2 = this.mMusicDataListenerList.iterator();
            while (it2.hasNext()) {
                com.incall.apps.service.musicservice.MusicDataListener listener2 = it2.next();
                com.incall.apps.service.util.LogUtil.i(TAG, "checkService addMusicDataListener");
                addMusicDataListener(listener2);
            }
        } catch (android.os.RemoteException e) {
            loge(TAG, "checkService RemoteException = " + e.getMessage());
        }
    }

    @java.lang.Deprecated
    public void addMusicListListener(com.incall.apps.service.musicservice.MusicListListener listener) {
        if (!this.mMusicListListenerList.contains(listener)) {
            this.mMusicListListenerList.add(listener);
        }
        if (this.mService == null) {
            loge(TAG, "addMusicListListener mService = null");
            return;
        }
        try {
            this.mService.addMusicListListener(listener);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    @java.lang.Deprecated
    public void removeMusicListListener(com.incall.apps.service.musicservice.MusicListListener listener) {
        if (this.mMusicListListenerList.contains(listener)) {
            this.mMusicListListenerList.remove(listener);
        }
        if (this.mService == null) {
            loge(TAG, "removeMusicListListener mService = null");
            return;
        }
        try {
            this.mService.removeMusicListListener(listener);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    @java.lang.Deprecated
    public void addMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener listener) {
        if (!this.mMusicDataListenerList.contains(listener)) {
            this.mMusicDataListenerList.add(listener);
        }
        if (this.mService == null) {
            loge(TAG, "addMusicDataListener mService = null");
            return;
        }
        try {
            this.mService.addMusicDataListener(listener);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    @java.lang.Deprecated
    public void removeMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener listener) {
        if (this.mMusicDataListenerList.contains(listener)) {
            this.mMusicDataListenerList.remove(listener);
        }
        if (this.mService == null) {
            loge(TAG, "removeMusicDataListener mService = null");
            return;
        }
        try {
            this.mService.removeMusicDataListener(listener);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMusicListListener(com.incall.apps.service.musicservice.MusicListListener listener) {
        this.mMusicListListener = listener;
        if (this.mService == null) {
            loge(TAG, "setMusicListListener mService = null");
            return;
        }
        try {
            this.mService.setMusicListListener(listener);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener listener) {
        this.mMusicDataListener = listener;
        if (this.mService == null) {
            loge(TAG, "setMusicDataListener mService = null");
            return;
        }
        try {
            this.mService.setMusicDataListener(listener);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateMusicList(java.lang.String json) {
        if (this.mService == null) {
            loge(TAG, "updateMusicList mService = null");
            return;
        }
        try {
            this.mService.updateMusicList(json);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void play(java.lang.String path) {
        if (this.mService == null) {
            loge(TAG, "play mService = null");
            return;
        }
        try {
            this.mService.play(path);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateMusicData(com.incall.apps.service.musicservice.MusicData data) {
        if (this.mService == null) {
            loge(TAG, "updateMusicList mService = null");
            return;
        }
        try {
            this.mService.updateMusicData(data);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMusicPlayerCallback(com.incall.apps.service.musicservice.MusicPlayerCallback callback) {
        this.mMusicPlayerCallback = callback;
        if (this.mService == null) {
            loge(TAG, "setMusicPlayerCallback mService = null");
            return;
        }
        try {
            this.mService.setMusicPlayerCallback(callback);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void playOrPause() {
        if (this.mService == null) {
            loge(TAG, "playOrPause mService = null");
            return;
        }
        try {
            this.mService.playOrPause();
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void next() {
        if (this.mService == null) {
            loge(TAG, "next mService = null");
            return;
        }
        try {
            this.mService.next();
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void prev() {
        if (this.mService == null) {
            loge(TAG, "prev mService = null");
            return;
        }
        try {
            this.mService.prev();
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    public void changeMode() {
        if (this.mService == null) {
            loge(TAG, "changeMode mService = null");
            return;
        }
        try {
            this.mService.changeMode();
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

    public static synchronized com.incall.apps.service.musicservice.MusicServiceManager getInstance() {
        com.incall.apps.service.musicservice.MusicServiceManager musicServiceManager;
        synchronized (com.incall.apps.service.musicservice.MusicServiceManager.class) {
            musicServiceManager = com.incall.apps.service.musicservice.MusicServiceManager.Holder.MANAGER;
        }
        return musicServiceManager;
    }

    private static class Holder {
        private static final com.incall.apps.service.musicservice.MusicServiceManager MANAGER = new com.incall.apps.service.musicservice.MusicServiceManager(null);

        private Holder() {
        }
    }
}
