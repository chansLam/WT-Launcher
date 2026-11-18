package android.car.wt;

public class WtCarDVRManager {
    public WtCarDVRManager() {}
    public static WtCarDVRManager getInstance() { return null; }
    public static class WtCarDVREventCallback {
        public WtCarDVREventCallback() {}
        public static WtCarDVREventCallback getInstance() { return null; }
    }
    public void registerCallback(WtCarDVREventCallback a0) { }
    public int getIntProperty(int a0, int a1) { return 0;}
    public void unregisterCallback(WtCarDVREventCallback a0) { }
}
