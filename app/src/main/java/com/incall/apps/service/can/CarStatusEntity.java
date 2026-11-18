package com.incall.apps.service.can;

/* loaded from: classes.dex */
public class CarStatusEntity implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.incall.apps.service.can.CarStatusEntity> CREATOR = new android.os.Parcelable.Creator<com.incall.apps.service.can.CarStatusEntity>() { // from class: com.incall.apps.service.can.CarStatusEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.can.CarStatusEntity createFromParcel(android.os.Parcel source) {
            return new com.incall.apps.service.can.CarStatusEntity(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.incall.apps.service.can.CarStatusEntity[] newArray(int size) {
            return new com.incall.apps.service.can.CarStatusEntity[size];
        }
    };
    public static final int DEFAULT_VALUE = 999;

    @com.google.gson.annotations.SerializedName("apaSystemFailureFlag")
    public int apaSystemFailureFlag;

    @com.google.gson.annotations.SerializedName("brakeLampError")
    public int brakeLampError;

    @com.google.gson.annotations.SerializedName("buzzerWarningMode")
    public int buzzerWarningMode;

    @com.google.gson.annotations.SerializedName("chmLampError")
    public int chmLampError;

    @com.google.gson.annotations.SerializedName("engineOilStatus")
    public int engineOilStatus;

    @com.google.gson.annotations.SerializedName("engineState")
    public int engineState;

    @com.google.gson.annotations.SerializedName("frontPositionLampError")
    public int frontPositionLampError;

    @com.google.gson.annotations.SerializedName("handBrakeState")
    public int handBrakeState;

    @com.google.gson.annotations.SerializedName("hmaStatus")
    public int hmaStatus;

    @com.google.gson.annotations.SerializedName("keyUndetectedAlarmSignal")
    public int keyUndetectedAlarmSignal;

    @com.google.gson.annotations.SerializedName("leftAfterDoor")
    public int leftAfterDoor;

    @com.google.gson.annotations.SerializedName("leftBeforeDoor")
    public int leftBeforeDoor;

    @com.google.gson.annotations.SerializedName("leftDayLampError")
    public int leftDayLampError;

    @com.google.gson.annotations.SerializedName("leftFrontFoglampError")
    public int leftFrontFoglampError;

    @com.google.gson.annotations.SerializedName("leftLowBeamError")
    public int leftLowBeamError;

    @com.google.gson.annotations.SerializedName("leftPositionLampError")
    public int leftPositionLampError;

    @com.google.gson.annotations.SerializedName("leftRearFoglampError")
    public int leftRearFoglampError;

    @com.google.gson.annotations.SerializedName("lfPressureWarning")
    public int lfPressureWarning;

    @com.google.gson.annotations.SerializedName("lowPowerWarning")
    public int lowPowerWarning;

    @com.google.gson.annotations.SerializedName("lrPressureWarning")
    public int lrPressureWarning;

    @com.google.gson.annotations.SerializedName("luggageDoor")
    public int luggageDoor;

    @com.google.gson.annotations.SerializedName("oilPressureSts")
    public int oilPressureSts;

    @com.google.gson.annotations.SerializedName("pMSLevel")
    public int pMSLevel;

    @com.google.gson.annotations.SerializedName("powerStatusFeedback")
    public int powerStatusFeedback;

    @com.google.gson.annotations.SerializedName("rainSensor")
    public int rainSensor;

    @com.google.gson.annotations.SerializedName("rearPositionLampError")
    public int rearPositionLampError;

    @com.google.gson.annotations.SerializedName("reverseLampError")
    public int reverseLampError;

    @com.google.gson.annotations.SerializedName("rfPressureWarning")
    public int rfPressureWarning;

    @com.google.gson.annotations.SerializedName("rightAfterDoor")
    public int rightAfterDoor;

    @com.google.gson.annotations.SerializedName("rightBeforeDoor")
    public int rightBeforeDoor;

    @com.google.gson.annotations.SerializedName("rightDayLampError")
    public int rightDayLampError;

    @com.google.gson.annotations.SerializedName("rightFrontFoglampError")
    public int rightFrontFoglampError;

    @com.google.gson.annotations.SerializedName("rightLowBeamError")
    public int rightLowBeamError;

    @com.google.gson.annotations.SerializedName("rightPositionLampError")
    public int rightPositionLampError;

    @com.google.gson.annotations.SerializedName("rightRearFoglampError")
    public int rightRearFoglampError;

    @com.google.gson.annotations.SerializedName("rrPressureWarning")
    public int rrPressureWarning;

    @com.google.gson.annotations.SerializedName("rrsSystemFailureFlag")
    public int rrsSystemFailureFlag;

    @com.google.gson.annotations.SerializedName("speedEffective")
    public int speedEffective;

    @com.google.gson.annotations.SerializedName("startIndicatorSignal")
    public int startIndicatorSignal;

    @com.google.gson.annotations.SerializedName("tirePresureSignalStatus")
    public int tirePresureSignalStatus;

    @com.google.gson.annotations.SerializedName("tpmsSystemFailureWarning")
    public int tpmsSystemFailureWarning;

    @com.google.gson.annotations.SerializedName("turnIndicatorLeftError")
    public int turnIndicatorLeftError;

    @com.google.gson.annotations.SerializedName("turnIndicatorRightError")
    public int turnIndicatorRightError;

    @com.google.gson.annotations.SerializedName("vehicleSpeed")
    public int vehicleSpeed;

    public java.lang.String toString() {
        return "CarStatusEntity{pMSLevel=" + this.pMSLevel + ", lowPowerWarning=" + this.lowPowerWarning + ", powerStatusFeedback=" + this.powerStatusFeedback + ", luggageDoor=" + this.luggageDoor + ", tpmsSystemFailureWarning=" + this.tpmsSystemFailureWarning + ", lfPressureWarning=" + this.lfPressureWarning + ", rfPressureWarning=" + this.rfPressureWarning + ", rrPressureWarning=" + this.rrPressureWarning + ", lrPressureWarning=" + this.lrPressureWarning + ", tirePresureSignalStatus=" + this.tirePresureSignalStatus + ", leftBeforeDoor=" + this.leftBeforeDoor + ", rightBeforeDoor=" + this.rightBeforeDoor + ", leftAfterDoor=" + this.leftAfterDoor + ", rightAfterDoor=" + this.rightAfterDoor + ", speedEffective=" + this.speedEffective + ", vehicleSpeed=" + this.vehicleSpeed + ", handBrakeState=" + this.handBrakeState + ", engineState=" + this.engineState + ", engineOilStatus=" + this.engineOilStatus + ", oilPressureSts=" + this.oilPressureSts + ", apaSystemFailureFlag=" + this.apaSystemFailureFlag + ", rrsSystemFailureFlag=" + this.rrsSystemFailureFlag + ", rainSensor=" + this.rainSensor + ", hmaStatus=" + this.hmaStatus + ", frontPositionLampError=" + this.frontPositionLampError + ", rearPositionLampError=" + this.rearPositionLampError + ", leftLowBeamError=" + this.leftLowBeamError + ", rightLowBeamError=" + this.rightLowBeamError + ", leftFrontFoglampError=" + this.leftFrontFoglampError + ", rightFrontFoglampError=" + this.rightFrontFoglampError + ", leftRearFoglampError=" + this.leftRearFoglampError + ", rightRearFoglampError=" + this.rightRearFoglampError + ", turnIndicatorLeftError=" + this.turnIndicatorLeftError + ", turnIndicatorRightError=" + this.turnIndicatorRightError + ", brakeLampError=" + this.brakeLampError + ", chmLampError=" + this.chmLampError + ", reverseLampError=" + this.reverseLampError + ", leftDayLampError=" + this.leftDayLampError + ", rightDayLampError=" + this.rightDayLampError + ", leftPositionLampError=" + this.leftPositionLampError + ", rightPositionLampError=" + this.rightPositionLampError + ", keyUndetectedAlarmSignal=" + this.keyUndetectedAlarmSignal + ", startIndicatorSignal=" + this.startIndicatorSignal + ", buzzerWarningMode=" + this.buzzerWarningMode + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(this.pMSLevel);
        dest.writeInt(this.lowPowerWarning);
        dest.writeInt(this.powerStatusFeedback);
        dest.writeInt(this.luggageDoor);
        dest.writeInt(this.tpmsSystemFailureWarning);
        dest.writeInt(this.lfPressureWarning);
        dest.writeInt(this.rfPressureWarning);
        dest.writeInt(this.rrPressureWarning);
        dest.writeInt(this.lrPressureWarning);
        dest.writeInt(this.tirePresureSignalStatus);
        dest.writeInt(this.leftBeforeDoor);
        dest.writeInt(this.rightBeforeDoor);
        dest.writeInt(this.leftAfterDoor);
        dest.writeInt(this.rightAfterDoor);
        dest.writeInt(this.speedEffective);
        dest.writeInt(this.vehicleSpeed);
        dest.writeInt(this.handBrakeState);
        dest.writeInt(this.engineState);
        dest.writeInt(this.engineOilStatus);
        dest.writeInt(this.oilPressureSts);
        dest.writeInt(this.apaSystemFailureFlag);
        dest.writeInt(this.rrsSystemFailureFlag);
        dest.writeInt(this.rainSensor);
        dest.writeInt(this.hmaStatus);
        dest.writeInt(this.frontPositionLampError);
        dest.writeInt(this.rearPositionLampError);
        dest.writeInt(this.leftLowBeamError);
        dest.writeInt(this.rightLowBeamError);
        dest.writeInt(this.leftFrontFoglampError);
        dest.writeInt(this.rightFrontFoglampError);
        dest.writeInt(this.leftRearFoglampError);
        dest.writeInt(this.rightRearFoglampError);
        dest.writeInt(this.turnIndicatorLeftError);
        dest.writeInt(this.turnIndicatorRightError);
        dest.writeInt(this.brakeLampError);
        dest.writeInt(this.chmLampError);
        dest.writeInt(this.reverseLampError);
        dest.writeInt(this.leftDayLampError);
        dest.writeInt(this.rightDayLampError);
        dest.writeInt(this.leftPositionLampError);
        dest.writeInt(this.rightPositionLampError);
        dest.writeInt(this.keyUndetectedAlarmSignal);
        dest.writeInt(this.startIndicatorSignal);
        dest.writeInt(this.buzzerWarningMode);
    }

    public CarStatusEntity() {
        this.pMSLevel = DEFAULT_VALUE;
        this.lowPowerWarning = DEFAULT_VALUE;
        this.powerStatusFeedback = DEFAULT_VALUE;
        this.luggageDoor = DEFAULT_VALUE;
        this.tpmsSystemFailureWarning = DEFAULT_VALUE;
        this.lfPressureWarning = DEFAULT_VALUE;
        this.rfPressureWarning = DEFAULT_VALUE;
        this.rrPressureWarning = DEFAULT_VALUE;
        this.lrPressureWarning = DEFAULT_VALUE;
        this.tirePresureSignalStatus = DEFAULT_VALUE;
        this.leftBeforeDoor = DEFAULT_VALUE;
        this.rightBeforeDoor = DEFAULT_VALUE;
        this.leftAfterDoor = DEFAULT_VALUE;
        this.rightAfterDoor = DEFAULT_VALUE;
        this.speedEffective = DEFAULT_VALUE;
        this.vehicleSpeed = 0;
        this.handBrakeState = DEFAULT_VALUE;
        this.engineState = DEFAULT_VALUE;
        this.engineOilStatus = DEFAULT_VALUE;
        this.oilPressureSts = DEFAULT_VALUE;
        this.apaSystemFailureFlag = DEFAULT_VALUE;
        this.rrsSystemFailureFlag = DEFAULT_VALUE;
        this.rainSensor = DEFAULT_VALUE;
        this.hmaStatus = DEFAULT_VALUE;
        this.frontPositionLampError = DEFAULT_VALUE;
        this.rearPositionLampError = DEFAULT_VALUE;
        this.leftLowBeamError = DEFAULT_VALUE;
        this.rightLowBeamError = DEFAULT_VALUE;
        this.leftFrontFoglampError = DEFAULT_VALUE;
        this.rightFrontFoglampError = DEFAULT_VALUE;
        this.leftRearFoglampError = DEFAULT_VALUE;
        this.rightRearFoglampError = DEFAULT_VALUE;
        this.turnIndicatorLeftError = DEFAULT_VALUE;
        this.turnIndicatorRightError = DEFAULT_VALUE;
        this.brakeLampError = DEFAULT_VALUE;
        this.chmLampError = DEFAULT_VALUE;
        this.reverseLampError = DEFAULT_VALUE;
        this.leftDayLampError = DEFAULT_VALUE;
        this.rightDayLampError = DEFAULT_VALUE;
        this.leftPositionLampError = DEFAULT_VALUE;
        this.rightPositionLampError = DEFAULT_VALUE;
        this.keyUndetectedAlarmSignal = DEFAULT_VALUE;
        this.startIndicatorSignal = DEFAULT_VALUE;
        this.buzzerWarningMode = DEFAULT_VALUE;
    }

    protected CarStatusEntity(android.os.Parcel in) {
        this.pMSLevel = DEFAULT_VALUE;
        this.lowPowerWarning = DEFAULT_VALUE;
        this.powerStatusFeedback = DEFAULT_VALUE;
        this.luggageDoor = DEFAULT_VALUE;
        this.tpmsSystemFailureWarning = DEFAULT_VALUE;
        this.lfPressureWarning = DEFAULT_VALUE;
        this.rfPressureWarning = DEFAULT_VALUE;
        this.rrPressureWarning = DEFAULT_VALUE;
        this.lrPressureWarning = DEFAULT_VALUE;
        this.tirePresureSignalStatus = DEFAULT_VALUE;
        this.leftBeforeDoor = DEFAULT_VALUE;
        this.rightBeforeDoor = DEFAULT_VALUE;
        this.leftAfterDoor = DEFAULT_VALUE;
        this.rightAfterDoor = DEFAULT_VALUE;
        this.speedEffective = DEFAULT_VALUE;
        this.vehicleSpeed = 0;
        this.handBrakeState = DEFAULT_VALUE;
        this.engineState = DEFAULT_VALUE;
        this.engineOilStatus = DEFAULT_VALUE;
        this.oilPressureSts = DEFAULT_VALUE;
        this.apaSystemFailureFlag = DEFAULT_VALUE;
        this.rrsSystemFailureFlag = DEFAULT_VALUE;
        this.rainSensor = DEFAULT_VALUE;
        this.hmaStatus = DEFAULT_VALUE;
        this.frontPositionLampError = DEFAULT_VALUE;
        this.rearPositionLampError = DEFAULT_VALUE;
        this.leftLowBeamError = DEFAULT_VALUE;
        this.rightLowBeamError = DEFAULT_VALUE;
        this.leftFrontFoglampError = DEFAULT_VALUE;
        this.rightFrontFoglampError = DEFAULT_VALUE;
        this.leftRearFoglampError = DEFAULT_VALUE;
        this.rightRearFoglampError = DEFAULT_VALUE;
        this.turnIndicatorLeftError = DEFAULT_VALUE;
        this.turnIndicatorRightError = DEFAULT_VALUE;
        this.brakeLampError = DEFAULT_VALUE;
        this.chmLampError = DEFAULT_VALUE;
        this.reverseLampError = DEFAULT_VALUE;
        this.leftDayLampError = DEFAULT_VALUE;
        this.rightDayLampError = DEFAULT_VALUE;
        this.leftPositionLampError = DEFAULT_VALUE;
        this.rightPositionLampError = DEFAULT_VALUE;
        this.keyUndetectedAlarmSignal = DEFAULT_VALUE;
        this.startIndicatorSignal = DEFAULT_VALUE;
        this.buzzerWarningMode = DEFAULT_VALUE;
        this.pMSLevel = in.readInt();
        this.lowPowerWarning = in.readInt();
        this.powerStatusFeedback = in.readInt();
        this.luggageDoor = in.readInt();
        this.tpmsSystemFailureWarning = in.readInt();
        this.lfPressureWarning = in.readInt();
        this.rfPressureWarning = in.readInt();
        this.rrPressureWarning = in.readInt();
        this.lrPressureWarning = in.readInt();
        this.tirePresureSignalStatus = in.readInt();
        this.leftBeforeDoor = in.readInt();
        this.rightBeforeDoor = in.readInt();
        this.leftAfterDoor = in.readInt();
        this.rightAfterDoor = in.readInt();
        this.speedEffective = in.readInt();
        this.vehicleSpeed = in.readInt();
        this.handBrakeState = in.readInt();
        this.engineState = in.readInt();
        this.engineOilStatus = in.readInt();
        this.oilPressureSts = in.readInt();
        this.apaSystemFailureFlag = in.readInt();
        this.rrsSystemFailureFlag = in.readInt();
        this.rainSensor = in.readInt();
        this.hmaStatus = in.readInt();
        this.frontPositionLampError = in.readInt();
        this.rearPositionLampError = in.readInt();
        this.leftLowBeamError = in.readInt();
        this.rightLowBeamError = in.readInt();
        this.leftFrontFoglampError = in.readInt();
        this.rightFrontFoglampError = in.readInt();
        this.leftRearFoglampError = in.readInt();
        this.rightRearFoglampError = in.readInt();
        this.turnIndicatorLeftError = in.readInt();
        this.turnIndicatorRightError = in.readInt();
        this.brakeLampError = in.readInt();
        this.chmLampError = in.readInt();
        this.reverseLampError = in.readInt();
        this.leftDayLampError = in.readInt();
        this.rightDayLampError = in.readInt();
        this.leftPositionLampError = in.readInt();
        this.rightPositionLampError = in.readInt();
        this.keyUndetectedAlarmSignal = in.readInt();
        this.startIndicatorSignal = in.readInt();
        this.buzzerWarningMode = in.readInt();
    }
}
