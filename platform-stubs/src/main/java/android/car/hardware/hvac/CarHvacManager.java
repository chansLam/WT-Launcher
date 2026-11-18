package android.car.hardware.hvac;

public class CarHvacManager {
    public CarHvacManager() {}
    public static CarHvacManager getInstance() { return null; }
    public static interface CarHvacEventCallback {
    }
    public void registerCallback(Object a0) { }
    public float getFloatProperty(int a0, int a1) { return 0f;}
    public int getIntProperty(int a0, int a1) { return 0;}
    public boolean getBooleanProperty(int a0, int a1) { return false;}
    public void unregisterCallback(Object a0) { }
}
