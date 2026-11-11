package com.android.launcherWT.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DesktopCatalogTest {
    @Test
    public void priorityComponentsComeFirstAndHiddenOnesDropOut() {
        List<LaunchableApp> installed = Arrays.asList(
                new LaunchableApp("com.other", "com.other.Main", "斑马"),
                new LaunchableApp("com.tencent.wecarnews", "com.tencent.wecarnews.UI.home.NewsMainTab", "新闻"),
                new LaunchableApp("com.incall.apps.airconditioner", "com.incall.apps.airconditioner.MainActivity", "空调"),
                new LaunchableApp("com.android.settings", "com.android.settings.Settings", "系统")
        );
        List<LaunchableApp> visible = DesktopCatalog.visible(
                installed,
                new HashSet<String>(Collections.singleton("com.android.settings/com.android.settings.Settings")));
        assertEquals(3, visible.size());
        assertEquals("空调", visible.get(0).label);
        assertEquals("新闻", visible.get(1).label);
        assertEquals("斑马", visible.get(2).label);
    }
}
