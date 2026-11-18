package com.android.launcherWT.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DisplayOrderTest {
    @Test
    public void unsavedFavoritesStayAheadOfAPartialSavedOrder() {
        assertEquals(
                Arrays.asList("fav", "b", "a"),
                DisplayOrder.merge(Arrays.asList("fav", "a", "b"), Arrays.asList("b", "a")));
    }

    @Test
    public void aCompleteSavedOrderBecomesTheDesktopOrder() {
        assertEquals(
                Arrays.asList("c", "a", "b"),
                DisplayOrder.merge(Arrays.asList("a", "b", "c"), Arrays.asList("c", "a", "b")));
    }

    @Test
    public void aNewAppKeepsItsPlaceAfterTheSavedItems() {
        assertEquals(
                Arrays.asList("b", "a", "new"),
                DisplayOrder.merge(Arrays.asList("a", "b", "new"), Arrays.asList("b", "a")));
    }

    @Test
    public void emptySavedOrderLeavesTheSourceAlone() {
        assertEquals(
                Arrays.asList("a", "b"),
                DisplayOrder.merge(Arrays.asList("a", "b"), Collections.<String>emptyList()));
    }
}
