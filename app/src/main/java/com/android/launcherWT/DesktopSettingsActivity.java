package com.android.launcherWT;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.launcherWT.core.LaunchableApp;
import com.android.launcherWT.core.WallpaperMode;
import com.android.launcherWT.home.InstalledApps;
import com.android.launcherWT.store.DesktopDb;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DesktopSettingsActivity extends Activity {
    private static final int REQUEST_CUSTOM_IMAGE = 1;

    private DesktopDb database;
    private RadioGroup wallpaperMode;
    private ImageView preview;
    private boolean adjustingRadios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        database = new DesktopDb(this);
        wallpaperMode = (RadioGroup) findViewById(R.id.wallpaper_mode);
        preview = (ImageView) findViewById(R.id.wallpaper_preview);
        bindApps();
        selectStoredMode();
        wallpaperMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (adjustingRadios) {
                    return;
                }
                if (checkedId == R.id.wallpaper_custom) {
                    pickCustomImage();
                } else if (checkedId == R.id.wallpaper_daily) {
                    database.setWallpaperMode(WallpaperMode.DAILY);
                    showPreview();
                }
            }
        });
        showPreview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CUSTOM_IMAGE) {
            return;
        }
        if (resultCode == RESULT_CANCELED) {
            selectDailyRadio();
            return;
        }
        Uri uri = data == null ? null : data.getData();
        if (resultCode != RESULT_OK || uri == null || !copyCustom(uri)) {
            selectDailyRadio();
            Toast.makeText(this, R.string.wallpaper_unusable, Toast.LENGTH_SHORT).show();
            return;
        }
        File saved = customFile();
        database.setCustomPath(saved.getAbsolutePath());
        database.setWallpaperMode(WallpaperMode.CUSTOM);
        adjustingRadios = true;
        wallpaperMode.check(R.id.wallpaper_custom);
        adjustingRadios = false;
        showPreview();
    }

    private void bindApps() {
        LinearLayout list = (LinearLayout) findViewById(R.id.app_list);
        list.removeAllViews();
        InstalledApps installed = InstalledApps.load(getPackageManager());
        List<LaunchableApp> apps = installed.apps();
        for (int i = 0; i < apps.size(); i++) {
            LaunchableApp app = apps.get(i);
            list.addView(appRow(app, installed.icon(app)));
        }
    }

    private View appRow(final LaunchableApp app, Drawable icon) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        int pad = dp(8);
        row.setPadding(pad, pad, pad, pad);

        ImageView image = new ImageView(this);
        image.setImageDrawable(icon);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int size = dp(40);
        row.addView(image, new LinearLayout.LayoutParams(size, size));

        TextView name = new TextView(this);
        name.setText(app.label);
        name.setTextColor(0xFFFFFFFF);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        name.setSingleLine(true);
        name.setEllipsize(TextUtils.TruncateAt.END);
        name.setPadding(dp(12), 0, dp(12), 0);
        row.addView(name, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        Switch visible = new Switch(this);
        visible.setText(R.string.app_visible);
        visible.setTextColor(0xFFFFFFFF);
        visible.setChecked(!database.isHidden(app.key()));
        visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                database.setHidden(app.key(), !isChecked);
            }
        });
        row.addView(visible, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private void selectStoredMode() {
        adjustingRadios = true;
        if (database.wallpaperMode() == WallpaperMode.CUSTOM) {
            wallpaperMode.check(R.id.wallpaper_custom);
        } else {
            wallpaperMode.check(R.id.wallpaper_daily);
        }
        adjustingRadios = false;
    }

    private void selectDailyRadio() {
        adjustingRadios = true;
        wallpaperMode.check(R.id.wallpaper_daily);
        adjustingRadios = false;
        showPreview();
    }

    private void pickCustomImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(intent, REQUEST_CUSTOM_IMAGE);
        } catch (ActivityNotFoundException ex) {
            selectDailyRadio();
            Toast.makeText(this, R.string.wallpaper_unusable, Toast.LENGTH_SHORT).show();
        }
    }

    private void showPreview() {
        preview.setImageDrawable(null);
        File file = database.wallpaperMode() == WallpaperMode.CUSTOM ? customFile() : dailyFile();
        if (!file.isFile() || file.length() <= 0L) {
            return;
        }
        Bitmap bitmap = decodePreview(file);
        if (bitmap != null) {
            preview.setImageBitmap(bitmap);
        }
    }

    private Bitmap decodePreview(File file) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bounds);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize(bounds.outWidth, bounds.outHeight, dp(480), dp(120));
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    private boolean copyCustom(Uri uri) {
        File dir = new File(getFilesDir(), "wallpaper");
        if (!dir.isDirectory() && !dir.mkdirs()) {
            return false;
        }
        File temp = new File(dir, "custom.jpg.tmp");
        File dest = customFile();
        File backup = new File(dir, "custom.jpg.bak");
        if (!writeTemp(uri, temp)) {
            temp.delete();
            return false;
        }
        if (!readableBitmap(temp)) {
            temp.delete();
            restoreCustom(dest, backup);
            return false;
        }
        if (backup.exists() && !backup.delete()) {
            temp.delete();
            return false;
        }
        if (dest.exists() && !dest.renameTo(backup)) {
            temp.delete();
            return false;
        }
        if (!temp.renameTo(dest)) {
            restoreCustom(dest, backup);
            temp.delete();
            return false;
        }
        if (backup.exists()) {
            backup.delete();
        }
        return dest.isFile() && dest.length() > 0L;
    }

    private boolean readableBitmap(File file) {
        Bitmap bitmap = null;
        try {
            bitmap = decodePreview(file);
            return bitmap != null;
        } catch (Exception ex) {
            return false;
        } catch (OutOfMemoryError error) {
            return false;
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    private void restoreCustom(File dest, File backup) {
        if (!backup.exists() || dest.exists()) {
            return;
        }
        backup.renameTo(dest);
    }

    private boolean writeTemp(Uri uri, File temp) {
        InputStream input = null;
        FileOutputStream output = null;
        try {
            input = getContentResolver().openInputStream(uri);
            if (input == null) {
                return false;
            }
            output = new FileOutputStream(temp);
            byte[] buffer = new byte[8192];
            int count;
            long total = 0L;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
                total += count;
            }
            output.flush();
            return total > 0L;
        } catch (Exception ex) {
            return false;
        } finally {
            close(output);
            close(input);
        }
    }

    private File customFile() {
        return new File(getFilesDir(), "wallpaper/custom.jpg");
    }

    private File dailyFile() {
        return new File(getFilesDir(), "wallpaper/daily.jpg");
    }

    private static int sampleSize(int width, int height, int targetWidth, int targetHeight) {
        int sample = 1;
        if (width <= 0 || height <= 0 || targetWidth <= 0 || targetHeight <= 0) {
            return sample;
        }
        while (width / (sample * 2) >= targetWidth && height / (sample * 2) >= targetHeight) {
            sample *= 2;
        }
        return sample;
    }

    private static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ex) {
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
