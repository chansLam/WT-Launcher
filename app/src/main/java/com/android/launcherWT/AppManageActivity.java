package com.android.launcherWT;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.launcherWT.core.LaunchableApp;
import com.android.launcherWT.home.DesktopEntries;
import com.android.launcherWT.home.FavoriteCatalog;
import com.android.launcherWT.home.InstalledApps;
import com.android.launcherWT.store.DesktopDb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppManageActivity extends Activity {
    private final List<DesktopEntries.Item> items = new ArrayList<DesktopEntries.Item>();
    private DesktopDb database;
    private LinearLayout list;
    private ScrollView scroll;
    private InstalledApps installed;
    private int dragIndex = -1;
    private float lastRawY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manage);
        database = new DesktopDb(this);
        installed = InstalledApps.load(getPackageManager());
        list = (LinearLayout) findViewById(R.id.app_list);
        scroll = (ScrollView) findViewById(R.id.app_scroll);
        List<DesktopEntries.Item> source = DesktopEntries.source(this, FavoriteCatalog.theme(this));
        Set<String> hidden = hiddenKeys(source);
        items.addAll(DesktopEntries.arrange(source, database.displayOrder(), hidden, true));
        bind();
    }

    private Set<String> hiddenKeys(List<DesktopEntries.Item> source) {
        Set<String> hidden = new HashSet<String>();
        for (int i = 0; i < source.size(); i++) {
            String key = source.get(i).key;
            if (database.isHidden(key)) {
                hidden.add(key);
            }
        }
        return hidden;
    }

    private void bind() {
        list.removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            list.addView(row(i));
        }
    }

    private View row(final int index) {
        final DesktopEntries.Item item = items.get(index);
        View row = getLayoutInflater().inflate(R.layout.item_app_row, list, false);
        ImageView icon = (ImageView) row.findViewById(R.id.app_icon);
        icon.setImageDrawable(iconOf(item));
        TextView title = (TextView) row.findViewById(R.id.app_title);
        title.setText(item.title);
        CheckBox visible = (CheckBox) row.findViewById(R.id.app_visible);
        visible.setOnCheckedChangeListener(null);
        visible.setChecked(!database.isHidden(item.key));
        visible.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton button, boolean checked) {
                database.setHidden(item.key, !checked);
            }
        });
        View handle = row.findViewById(R.id.drag_handle);
        handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return drag(row, event);
            }
        });
        return row;
    }

    private boolean drag(View row, MotionEvent event) {
        int index = list.indexOfChild(row);
        if (index < 0) {
            return false;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dragIndex = index;
                lastRawY = event.getRawY();
                row.setBackgroundColor(0x33FFFFFF);
                scroll.requestDisallowInterceptTouchEvent(true);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (dragIndex < 0) {
                    return true;
                }
                float rawY = event.getRawY();
                int target = indexAt(rawY);
                if (target >= 0 && target != dragIndex) {
                    move(dragIndex, target);
                    dragIndex = target;
                }
                lastRawY = rawY;
                autoScroll(rawY);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                row.setBackgroundColor(0x00000000);
                dragIndex = -1;
                saveOrder();
                return true;
            default:
                return false;
        }
    }

    private void move(int from, int to) {
        DesktopEntries.Item item = items.remove(from);
        items.add(to, item);
        View row = list.getChildAt(from);
        list.removeViewAt(from);
        list.addView(row, to);
    }

    private int indexAt(float rawY) {
        int closest = dragIndex;
        float best = Float.MAX_VALUE;
        int[] location = new int[2];
        for (int i = 0; i < list.getChildCount(); i++) {
            View child = list.getChildAt(i);
            child.getLocationOnScreen(location);
            float center = location[1] + child.getHeight() / 2f;
            float distance = Math.abs(rawY - center);
            if (distance < best) {
                best = distance;
                closest = i;
            }
        }
        return closest;
    }

    private void autoScroll(float rawY) {
        int[] location = new int[2];
        scroll.getLocationOnScreen(location);
        if (rawY < location[1] + 80) {
            scroll.scrollBy(0, -24);
        } else if (rawY > location[1] + scroll.getHeight() - 80) {
            scroll.scrollBy(0, 24);
        }
    }

    private void saveOrder() {
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            keys.add(items.get(i).key);
        }
        database.setDisplayOrder(keys);
    }

    @SuppressWarnings("deprecation")
    private Drawable iconOf(DesktopEntries.Item item) {
        if (item.iconRes != 0) {
            return getResources().getDrawable(item.iconRes);
        }
        return installed.icon(new LaunchableApp(item.packageName, item.className, item.title));
    }
}
