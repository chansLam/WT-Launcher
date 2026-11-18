package android.car;

public class CarNotConnectedException extends RuntimeException {
    public CarNotConnectedException() {}
    public static CarNotConnectedException getInstance() { return null; }
}
