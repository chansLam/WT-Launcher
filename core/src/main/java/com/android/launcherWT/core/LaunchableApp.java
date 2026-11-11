package com.android.launcherWT.core;

public final class LaunchableApp {
    public final String packageName;
    public final String className;
    public final String label;

    public LaunchableApp(String packageName, String className, String label) {
        this.packageName = packageName;
        this.className = className;
        this.label = label;
    }

    public String key() {
        return packageName + "/" + className;
    }
}
