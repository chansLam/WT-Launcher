package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DesktopCatalog {
    private static final Comparator<LaunchableApp> RANK_THEN_LABEL = new Comparator<LaunchableApp>() {
        @Override
        public int compare(LaunchableApp left, LaunchableApp right) {
            int byRank = PriorityOrder.rank(left.packageName, left.className)
                    - PriorityOrder.rank(right.packageName, right.className);
            if (byRank != 0) {
                return byRank;
            }
            return left.label.compareToIgnoreCase(right.label);
        }
    };

    private DesktopCatalog() {
    }

    public static List<LaunchableApp> visible(List<LaunchableApp> installed, Set<String> hiddenKeys) {
        return visible(installed, hiddenKeys, Collections.<String>emptyList());
    }

    public static List<LaunchableApp> visible(
            List<LaunchableApp> installed, Set<String> hiddenKeys, List<String> savedOrder) {
        Map<String, LaunchableApp> byKey = new LinkedHashMap<String, LaunchableApp>();
        for (LaunchableApp app : installed) {
            if (app == null || hiddenKeys.contains(app.key())) {
                continue;
            }
            byKey.put(app.key(), app);
        }
        List<LaunchableApp> ordered = new ArrayList<LaunchableApp>();
        Set<String> placed = new HashSet<String>();
        if (savedOrder != null) {
            for (int i = 0; i < savedOrder.size(); i++) {
                String key = savedOrder.get(i);
                LaunchableApp app = byKey.get(key);
                if (app != null && placed.add(key)) {
                    ordered.add(app);
                }
            }
        }
        List<LaunchableApp> newcomers = new ArrayList<LaunchableApp>();
        for (LaunchableApp app : byKey.values()) {
            if (!placed.contains(app.key())) {
                newcomers.add(app);
            }
        }
        Collections.sort(newcomers, RANK_THEN_LABEL);
        ordered.addAll(newcomers);
        return ordered;
    }
}
