package com.autopai.support.proxy.S311Proxy;

/* loaded from: classes.dex */
public class S311AirConditionImpl implements com.autopai.support.proxy.Interface.IAirConditionProxy {
    private static final java.lang.String LOGTAG = "S311AirConditionImpl";
    private com.incall.proxy.ac.AirConditionManager acMgr;
    private com.incall.proxy.can.CanManager mCanMgr;
    private android.content.Context mContext;
    private com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener mMyListener;
    private com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.AirConditionListener mListener = new com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.AirConditionListener();
    private com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue mValues = new com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue();

    public S311AirConditionImpl(android.content.Context cxt) {
        com.autopai.support.utils.SLog.d(LOGTAG, "S311AirConditionImpl start");
        this.mContext = cxt.getApplicationContext();
        this.acMgr = com.incall.proxy.ac.AirConditionManager.getInstance();
        this.mCanMgr = com.incall.proxy.can.CanManager.getInstance();
        com.autopai.support.utils.SLog.d(LOGTAG, "S311AirConditionImpl acMgr = " + this.acMgr + "; mCanMgr = " + this.mCanMgr);
        if (this.acMgr != null) {
            this.acMgr.addListener(this.mListener);
            com.incall.proxy.can.AirConditionInfo info = this.acMgr.getAirConditionInfo();
            com.autopai.support.utils.SLog.d(LOGTAG, "S311AirConditionImpl info = " + info);
            if (info != null) {
                if (info.windLevel > 0 && info.windLevel != 255) {
                    this.mValues.isOpen = true;
                }
                if (info.acIonizerState != null && com.incall.proxy.can.AirConditionInfo.Switch.ON.index == info.acIonizerState.index) {
                    this.mValues.isPurifierOpen = true;
                }
                this.mValues.fan = info.windLevel;
                this.mValues.leftTmp = info.leftTemperature;
                this.mValues.rightTmp = info.rightTemperature;
                this.mValues.level = info.electricTemperature;
            }
        }
        com.autopai.support.utils.SLog.d(LOGTAG, "S311AirConditionImpl end mValues string = " + this.mValues.toString());
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public void setAirConditionStatusChangeListener(com.autopai.support.proxy.AirConditionImplProxy.IAirConditionStatusChangeListener listener) {
        this.mMyListener = listener;
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public com.autopai.support.proxy.AirConditionImplProxy.AirConditionValue getAirConditionValue() {
        if (this.mCanMgr == null) {
            com.autopai.support.utils.SLog.d(LOGTAG, "getAirConditionValue mCanMgr is null");
            return this.mValues;
        }
        com.incall.proxy.can.AirConditionInfo airConditionInfo = this.mCanMgr.getAirConditionInfo();
        if (airConditionInfo == null) {
            com.autopai.support.utils.SLog.d(LOGTAG, "getAirConditionValue airConditionInfo is null");
            return this.mValues;
        }
        float leftTemp = airConditionInfo.leftTemperature;
        float rightTemp = airConditionInfo.rightTemperature;
        if (leftTemp == -1.0d) {
            leftTemp = 17.5f;
        } else if (leftTemp == -2.0d) {
            leftTemp = 32.5f;
        }
        if (rightTemp == -1.0d) {
            rightTemp = 17.5f;
        } else if (rightTemp == -2.0d) {
            rightTemp = 32.5f;
        }
        this.mValues.leftTmp = leftTemp;
        this.mValues.rightTmp = rightTemp;
        com.autopai.support.utils.SLog.d(LOGTAG, "getAirConditionValue airConditionInfo mValues.leftTmp = " + this.mValues.leftTmp + "; mValues.rightTmp = " + this.mValues.rightTmp);
        return this.mValues;
    }

    @Override // com.autopai.support.proxy.Interface.IAirConditionProxy
    public void destroy() {
        if (this.acMgr != null) {
            this.acMgr.removeListener(this.mListener);
        }
    }

    class AirConditionListener implements com.incall.proxy.ac.AirConditionManager.IAirConditionListener {
        AirConditionListener() {
        }

        public void onAcPm25inCar(int i, boolean b) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onAcPm25inCar start i = " + i + "; b = " + b);
        }

        public void onAcPm25OutCarChanged(int i, boolean b) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onAcPm25OutCarChanged start i = " + i + "; b = " + b);
        }

        public void onAirCleanStateChanged(int windState, int airCleanState, boolean display) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onAirCleanStateChanged start windState = " + windState + "; airCleanState = " + airCleanState + ";display = " + display);
        }

        public void onACPlasmaStateChanged(int state, boolean display) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACPlasmaStateChanged start state = " + state + "; display = " + display);
            boolean isOpen = false;
            if (state == 1) {
                isOpen = true;
            }
            if (com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.isPurifierOpen != isOpen) {
                com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.isPurifierOpen = isOpen;
                if (com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener != null) {
                    com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener.onAirConditionChanged(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues);
                }
            }
        }

        public void onACWindLevelChanged(boolean isFront, int level, boolean display) {
            boolean curOpen;
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACWindLevelChanged isFront = " + isFront + "; level = " + level + "; display = " + display + "; mValues.isOpen = " + com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.isOpen + ";mMyListener = " + com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener);
            if (com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener != null) {
                if (level <= 0) {
                    curOpen = false;
                } else {
                    curOpen = true;
                }
                if (curOpen != com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.isOpen) {
                    com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.isOpen = curOpen;
                }
                com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.fan = level;
                com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener.onAirConditionChanged(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues);
            }
        }

        public void onACWindModeChanged(boolean b, int i, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACWindModeChanged start i = " + i + "; b = " + b + "; b1 = " + b1);
        }

        public void onACTempChanged(int type, float temp, boolean display) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACTempChanged start type = " + type + "; temp = " + temp + "; display = " + display);
            if (com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener != null) {
                if (temp == -1.0d) {
                    temp = 17.5f;
                } else if (temp == -2.0d) {
                    temp = 32.5f;
                }
                if (type == 0) {
                    com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.leftTmp = temp;
                    com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener.onAirConditionChanged(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues);
                } else if (type == 1) {
                    com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.rightTmp = temp;
                    com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener.onAirConditionChanged(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues);
                }
            }
        }

        public void onACStateChanged(boolean on, boolean display) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACStateChanged start on = " + on + "; display = " + display);
        }

        public void onACDEFStateChanged(boolean b, boolean b1, boolean b2) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACDEFStateChanged start b1 = " + b1 + "; b = " + b + "; b2 = " + b2);
        }

        public void onACCircleModeChanged(int i, boolean b) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACCircleModeChanged start i = " + i + "; b = " + b);
        }

        public void onACAQSStateChanged(boolean b, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACAQSStateChanged start b1 = " + b1 + "; b = " + b);
        }

        public void onACAUTOModeChanged(boolean b, boolean b1, boolean b2) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACAUTOModeChanged start b1 = " + b1 + "; b = " + b + "; b2 = " + b2);
        }

        public void onACSeatHeatChanged(boolean b, int i, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACSeatHeatChanged start b1 = " + b1 + "; b = " + b + "; i = " + i);
        }

        public void onACSeatVentilateChanged(boolean b, int i, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACSeatVentilateChanged start b1 = " + b1 + "; b = " + b + "; i = " + i);
        }

        public void onACRearPanelStateChanged(boolean b, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACRearPanelStateChanged start b1 = " + b1 + "; b = " + b);
        }

        public void onACDUALStateChanged(boolean b, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACDUALStateChanged start b1 = " + b1 + "; b = " + b);
        }

        public void onACSYNCStateChanged(boolean b, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACSYNCStateChanged start b1 = " + b1 + "; b = " + b);
        }

        public void onACMaxStateChanged(boolean b, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACMaxStateChanged start b1 = " + b1 + "; b = " + b);
        }

        public void onElectricACTempChanged(boolean b, int i, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onElectricACTempChanged start b1 = " + b1 + "; b = " + b + "; i =" + i);
        }

        public void onElectricACTempChanged(int type, int level, boolean display) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onElectricACTempChanged type = " + type + "; level = " + level + "; display = " + display);
            if (com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener != null && type == 0) {
                com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.level = level;
                com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mMyListener.onAirConditionChanged(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues);
            }
        }

        public void onPowerStateChanged(boolean b, boolean b1, boolean b2) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onPowerStateChanged start b1 = " + b1 + "; b = " + b + "; b2 =" + b2);
        }

        public void onACFloatWindowChanged(boolean b) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACFloatWindowChanged start b = " + b);
        }

        public void onFreshAirSystemChanged(boolean b, boolean b1) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onFreshAirSystemChanged start b1 = " + b1 + "; b =" + b);
        }

        public void onACIonizerStateChanged(boolean on, boolean display) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACIonizerStateChanged start on = " + on + "; display =" + display + "; mValues.isPurifierOpen =" + com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.this.mValues.isPurifierOpen);
        }

        public void onACFrontStateChanged(int i, boolean b) {
            com.autopai.support.utils.SLog.d(com.autopai.support.proxy.S311Proxy.S311AirConditionImpl.LOGTAG, "onACFrontStateChanged start i = " + i + "; b =" + b);
        }
    }
}
