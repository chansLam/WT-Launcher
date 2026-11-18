package com.autopai.support.proxy.S311Proxy;

/* loaded from: classes.dex */
public class S311SignalImpl implements com.autopai.support.proxy.Interface.ISingalProxy {
    private static final java.lang.String LOGTAG = "S311SignalImpl";
    private com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener mChangeListener;
    private int mCurLevel;
    private int mCurType;
    private boolean isOpen = true;
    private com.incall.proxy.tbox.TboxManager.TboxChangedListener mTboxChangedListener = new com.incall.proxy.tbox.TboxManager.TboxCallListener() { // from class: com.autopai.support.proxy.S311Proxy.S311SignalImpl.1
        public void onTboxCallStateReport(com.incall.proxy.tbox.TboxCommonDefine.TboxCallReportCMD tboxCallReportCMD, java.lang.String s) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "onTboxCallStateReport s = " + s);
        }

        public void onCallStatusChanged(int status, int calltype) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "onCallStatusChanged status = " + status + "; calltype = " + calltype);
        }

        public void onTboxMobileSignalChanged(int type, int level, int value) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "onTboxMobileSignalChanged type = " + type + "; level = " + level + "; value = " + value + ";isOpen =" + com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen);
            com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mCurType = type;
            com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mCurLevel = level;
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null && com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen) {
                com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(type, level, value);
            }
            com.incall.proxy.tbox.TboxManager.getInstance().getMobileState(com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mMobileStateCallbackListener);
        }

        public void onTboxMobileSwitchStateChanged(int status) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "onTboxMobileSwitchStateChanged status = " + status);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null) {
                if (status == 2) {
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = false;
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(-1, 4, 0);
                } else if (status == 1) {
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = true;
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(3, 4, 0);
                }
            }
        }

        public void onTboxWifiStationConnectStatusChanged(int status, java.lang.String ssid) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "onTboxWifiStationConnectStatusChanged status = " + status + "; ssid = " + ssid);
        }
    };
    private com.incall.proxy.tbox.INetworkCallBack mNetCallbackListener = new com.incall.proxy.tbox.INetworkCallBack() { // from class: com.autopai.support.proxy.S311Proxy.S311SignalImpl.2
        public void onException(int e, java.lang.String response) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "mNetCallbackListener onException e = " + e + "; response = " + response);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null) {
                com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(-1, 0, 0);
            }
        }

        public void onCompleted(int type, java.lang.String level) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "mNetCallbackListener onCompleted type = " + type + "; level = " + level + "; mChangeListener = " + com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null && com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen) {
                int signallevel = 0;
                try {
                    signallevel = java.lang.Integer.valueOf(level).intValue();
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
                com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(type, signallevel, 0);
            }
        }
    };
    private com.incall.proxy.tbox.INetworkCallBack mMobileStateCallbackListener = new com.incall.proxy.tbox.INetworkCallBack() { // from class: com.autopai.support.proxy.S311Proxy.S311SignalImpl.3
        public void onException(int e, java.lang.String response) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "mMobileStateCallbackListener INetworkCallBack onException e = " + e + "; response = " + response);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null) {
                com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = false;
                com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(-1, 0, 0);
            }
        }

        public void onCompleted(int type, java.lang.String response) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "mMobileStateCallbackListener onCompleted type = " + type + "; response = " + response + "; mCurType = " + com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mCurType + "; mCurLevel = " + com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mCurLevel);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null) {
                if (type == 0) {
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = false;
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(-1, 4, 0);
                } else {
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = true;
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mCurType, com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mCurLevel, 0);
                }
            }
        }
    };
    private com.incall.proxy.tbox.INetworkCallBack mMobileNetCallbackListener = new com.incall.proxy.tbox.INetworkCallBack() { // from class: com.autopai.support.proxy.S311Proxy.S311SignalImpl.4
        public void onException(int e, java.lang.String response) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "mMobileNetCallbackListener INetworkCallBack onException e = " + e + "; response = " + response);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null) {
                com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(-1, 0, 0);
            }
        }

        public void onCompleted(int type, java.lang.String response) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311SignalImpl.LOGTAG, "mMobileNetCallbackListener onCompleted type = " + type + "; response = " + response + "; mChangeListener" + com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener);
            if (com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener != null) {
                if (type == 1) {
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = true;
                } else if (type == 2) {
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.isOpen = false;
                    com.autopai.support.proxy.S311Proxy.S311SignalImpl.this.mChangeListener.onTboxMobileSignalChanged(-1, 4, 0);
                }
            }
        }
    };

    public S311SignalImpl(com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        this.mChangeListener = listener;
        com.incall.proxy.tbox.TboxManager.getInstance().getWanInfo(this.mNetCallbackListener);
        com.incall.proxy.tbox.TboxManager.getInstance().getMobileNetworkState(this.mMobileNetCallbackListener);
        com.incall.proxy.tbox.TboxManager.getInstance().getMobileState(this.mMobileStateCallbackListener);
        com.incall.proxy.tbox.TboxManager.getInstance().addTBoxChangedListener(this.mTboxChangedListener);
    }

    @Override // com.autopai.support.proxy.Interface.ISingalProxy
    public void destroy() {
        com.incall.proxy.tbox.TboxManager.getInstance().removeTBoxChangedListener(this.mTboxChangedListener);
        this.mChangeListener = null;
        this.mTboxChangedListener = null;
    }

    @Override // com.autopai.support.proxy.Interface.ISingalProxy
    public void setSignalChangeListener(com.autopai.support.proxy.SingalImplProxy.ISignalChangeListener listener) {
        this.mChangeListener = listener;
    }
}
