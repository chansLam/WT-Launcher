package com.incall.apps.service.aiservice.bean;

/* loaded from: classes.dex */
public class RouteInfo {
    private int[] degreeDatas;
    private float[] distance;
    private int routeRemainDis;
    private int routeRemainTime;

    public static class RoadStatus {
        public static final int AMBLE_INTEGER = 2;
        public static final java.lang.String AMBLE_STRING = "缓行";
        public static final int SEVERE_TRAFFIC_INTEGER = 4;
        public static final java.lang.String SEVERE_TRAFFIC_STRING = "严重拥堵";
        public static final int TRAFFIC_INTEGER = 3;
        public static final java.lang.String TRAFFIC_STRING = "拥堵";
        public static final int UNBLOCKED_INTEGER = 1;
        public static final java.lang.String UNBLOCKED_STRING = "畅通";
        public static final int UNKNOWN_INTEGER = 0;
        public static final java.lang.String UNKNOWN_STRING = "未知";
    }

    public RouteInfo(int routeRemainDis, int routeRemainTime, int[] degreeDatas, float[] distance) {
        this.routeRemainDis = routeRemainDis;
        this.routeRemainTime = routeRemainTime;
        this.degreeDatas = degreeDatas;
        this.distance = distance;
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

    public int[] getDegreeDatas() {
        return this.degreeDatas;
    }

    public void setDegreeDatas(int[] degreeDatas) {
        this.degreeDatas = degreeDatas;
    }

    public float[] getDistance() {
        return this.distance;
    }

    public void setDistance(float[] distance) {
        this.distance = distance;
    }

    public java.lang.String toString() {
        return "RouteInfo [routeRemainDis=" + this.routeRemainDis + ", routeRemainTime=" + this.routeRemainTime + ", degreeDatas=" + java.util.Arrays.toString(this.degreeDatas) + ", distance=" + java.util.Arrays.toString(this.distance) + "]";
    }
}
