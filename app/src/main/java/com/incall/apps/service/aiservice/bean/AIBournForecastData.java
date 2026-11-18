package com.incall.apps.service.aiservice.bean;

/* loaded from: classes.dex */
public class AIBournForecastData {
    public static final int DESTINATION_TYPE_COMPANY = 1;
    public static final int DESTINATION_TYPE_HOME = 0;
    public static final int DEV_TYPE_MAP = 0;
    public static final int DEV_TYPE_NATIVE = 1;
    private java.lang.String destinationLat;
    private java.lang.String destinationLng;
    private java.lang.String destinationName;
    private com.incall.apps.service.aiservice.bean.RouteInfo destinationRouteInfo;
    private int destinationType;
    private int devType;
    private int icon;
    private boolean isBournForecast;
    private boolean isNavigation;
    private java.lang.String nextRoadName;
    private int roundAboutNum;
    private int routeRemainDis;
    private int routeRemainTime;
    private int routeUpdateTime;
    private int segRemainDis;

    public boolean isNavigation() {
        return this.isNavigation;
    }

    public void setNavigation(boolean isNavigation) {
        this.isNavigation = isNavigation;
    }

    public java.lang.String getNextRoadName() {
        return this.nextRoadName;
    }

    public void setNextRoadName(java.lang.String nextRoadName) {
        this.nextRoadName = nextRoadName;
    }

    public int getSegRemainDis() {
        return this.segRemainDis;
    }

    public void setSegRemainDis(int segRemainDis) {
        this.segRemainDis = segRemainDis;
    }

    public int getRouteRemainDis() {
        return this.routeRemainDis;
    }

    public void setRouteRemainDis(int routeRemainDis) {
        this.routeRemainDis = routeRemainDis;
    }

    public int getRouteRemainTime() {
        return this.routeRemainTime;
    }

    public void setRouteRemainTime(int routeRemainTime) {
        this.routeRemainTime = routeRemainTime;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getRoundAboutNum() {
        return this.roundAboutNum;
    }

    public void setRoundAboutNum(int roundAboutNum) {
        this.roundAboutNum = roundAboutNum;
    }

    public boolean isBournForecast() {
        return this.isBournForecast;
    }

    public void setBournForecast(boolean isBournForecast) {
        this.isBournForecast = isBournForecast;
    }

    public int getDestinationType() {
        return this.destinationType;
    }

    public void setDestinationType(int destinationType) {
        this.destinationType = destinationType;
    }

    public java.lang.String getDestinationName() {
        return this.destinationName;
    }

    public void setDestinationName(java.lang.String destinationName) {
        this.destinationName = destinationName;
    }

    public java.lang.String getDestinationLat() {
        return this.destinationLat;
    }

    public void setDestinationLat(java.lang.String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public java.lang.String getDestinationLng() {
        return this.destinationLng;
    }

    public void setDestinationLng(java.lang.String destinationLng) {
        this.destinationLng = destinationLng;
    }

    public com.incall.apps.service.aiservice.bean.RouteInfo getDestinationRouteInfo() {
        return this.destinationRouteInfo;
    }

    public void setDestinationRouteInfo(com.incall.apps.service.aiservice.bean.RouteInfo destinationRouteInfo) {
        this.destinationRouteInfo = destinationRouteInfo;
    }

    public int getDevType() {
        return this.devType;
    }

    public void setDevType(int devType) {
        this.devType = devType;
    }

    public int getRouteUpdateTime() {
        return this.routeUpdateTime;
    }

    public void setRouteUpdateTime(int routeUpdateTime) {
        this.routeUpdateTime = routeUpdateTime;
    }

    public java.lang.String toString() {
        return "AIBournForecastData [isNavigation=" + this.isNavigation + ", nextRoadName=" + this.nextRoadName + ", segRemainDis=" + this.segRemainDis + ", routeRemainDis=" + this.routeRemainDis + ", routeRemainTime=" + this.routeRemainTime + ", icon=" + this.icon + ", roundAboutNum=" + this.roundAboutNum + ", isBournForecast=" + this.isBournForecast + ", destinationType=" + this.destinationType + ", destinationName=" + this.destinationName + ", destinationLng=" + this.destinationLng + ", destinationLat=" + this.destinationLat + ", destinationRouteInfo=" + this.destinationRouteInfo + ", devType=" + this.routeUpdateTime + ", routeUpdateTime=" + this.devType + "]";
    }
}
