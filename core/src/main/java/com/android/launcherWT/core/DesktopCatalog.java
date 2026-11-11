package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class DesktopCatalog {
    private DesktopCatalog() {
    }

    public static List<LaunchableApp> visible(List<LaunchableApp> installed, Set<String> hiddenKeys) {
        List<LaunchableApp> visible = new ArrayList<LaunchableApp>();
        for (LaunchableApp app : installed) {
            if (!hiddenKeys.contains(app.key())) {
                visible.add(app);
            }
        }
        Collections.sort(visible, new Comparator<LaunchableApp>() {
            @Override
            public int compare(LaunchableApp left, LaunchableApp right) {
                int byRank = PriorityOrder.rank(left.packageName, left.className)
                        - PriorityOrder.rank(right.packageName, right.className);
                if (byRank != 0) {
                    return byRank;
                }
                return left.label.compareToIgnoreCase(right.label);
            }
        });
        return visible;
    }
}
