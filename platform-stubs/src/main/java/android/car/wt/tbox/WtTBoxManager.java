package android.car.wt.tbox;

public class WtTBoxManager {
    public WtTBoxManager() {}
    public static WtTBoxManager getInstance() { return null; }
    public static class ISignalListener {
        public ISignalListener() {}
        public static ISignalListener getInstance() { return null; }
    }
    public void registerSignalListener(ISignalListener a0) { }
    public void unregisterSignalListener(ISignalListener a0) { }
}
