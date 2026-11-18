package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Applies a saved drag order without moving items the user has never arranged.
 * Unsaved keys stay where the source list put them. Saved keys are reordered
 * only among the slots those saved keys already occupy.
 */
public final class DisplayOrder {
    private DisplayOrder() {
    }

    public static List<String> merge(List<String> sourceOrder, final List<String> savedOrder) {
        List<String> base = new ArrayList<String>();
        if (sourceOrder != null) {
            base.addAll(sourceOrder);
        }
        if (savedOrder == null || savedOrder.isEmpty() || base.isEmpty()) {
            return base;
        }
        final Map<String, Integer> rank = new HashMap<String, Integer>();
        for (int i = 0; i < savedOrder.size(); i++) {
            if (!rank.containsKey(savedOrder.get(i))) {
                rank.put(savedOrder.get(i), Integer.valueOf(i));
            }
        }
        List<Integer> slots = new ArrayList<Integer>();
        List<String> ranked = new ArrayList<String>();
        for (int i = 0; i < base.size(); i++) {
            if (rank.containsKey(base.get(i))) {
                slots.add(Integer.valueOf(i));
                ranked.add(base.get(i));
            }
        }
        Collections.sort(ranked, new Comparator<String>() {
            @Override
            public int compare(String left, String right) {
                return rank.get(left).intValue() - rank.get(right).intValue();
            }
        });
        for (int i = 0; i < slots.size(); i++) {
            base.set(slots.get(i).intValue(), ranked.get(i));
        }
        return base;
    }
}
