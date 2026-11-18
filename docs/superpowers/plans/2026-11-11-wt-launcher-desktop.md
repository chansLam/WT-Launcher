# WT Launcher 桌面 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 API 19 上做出可安装的车机桌面：应用宫格、桌面设置里的显示开关、自定义图片与每日壁纸二选一。

**Architecture:** 纯 Java 的 `core` 模块负责排序、显隐和壁纸选择，JVM 单测不依赖设备。`app` 模块是 API 19 的 HOME，读取 `PackageManager`，用 SQLite 记住显隐和壁纸，并用 `WallpaperManager` 铺壁纸。拖拽、文件夹、小部件缩放和车辆状态栏不在本计划，分别见同目录的 edit 与 status 计划。

**Tech Stack:** Java、Gradle 8.2.1、Android Gradle Plugin 8.2.2、`minSdk 19`、`targetSdk 19`、`compileSdk 34`、JUnit 4。

## Global Constraints

- `minSdk` 和 `targetSdk` 都是 19。界面用 Java 和 XML。
- 包名 `com.android.launcherWT`。`android:sharedUserId="android.uid.system"`。`com.incall.proxy` 为可选库。
- 调试包去掉 `sharedUserId`，不绑定真实车辆服务。
- HOME 入口是 `MainActivity`，`singleInstance`，横屏。桌面设置是 `DesktopSettingsActivity`，不是 HOME。
- 打开后第一页是应用宫格，左滑第二页是导航、音乐、新闻三块卡片。宫格 5 列 2 行，每页 10 格。
- 宫格列出全部带 `MAIN` / `LAUNCHER` 的已安装应用，不用白名单剔除。图标用应用自己的图标。没有图标时用系统默认应用图标。
- 优先组件按规格中的 12 个组件排序，其余按名称。不为读不到的组件名编造类名。
- 显示开关只在桌面设置里改。关闭不卸载，并从文件夹和快捷栏移除；打开后加到应用页末尾。新安装默认显示。
- 壁纸只有「自定义图片」和「每日自动」。必须且只能选中一个。默认「每日自动」。
- 每日自动地址是 `https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN`。用 `images[0].startdate` 判断是否换图。选中自定义图片期间不下载。
- 取消选图或图片读失败时，单选保持「每日自动」。不关闭证书校验。下载失败保留当前壁纸。
- 主题广播不更换壁纸，也不更换应用图标。
- 不把原 APK 的图片打进新安装包。不拷贝反编译的原厂 Java。
- 权限：`INTERNET`、`SET_WALLPAPER`、`READ_EXTERNAL_STORAGE`。选图用 `ACTION_GET_CONTENT`。

本计划做完后，第二页卡片在小部件提供者缺失时显示应用名，点击打开对应 Activity。长按拖拽、文件夹、小部件缩放、快捷栏更换、车辆状态图标留到后续两份计划。

---

### Task 1: 工程骨架

**Files:**
- Create: `settings.gradle`
- Create: `build.gradle`
- Create: `gradle.properties`
- Create: `app/build.gradle`
- Create: `core/build.gradle`
- Create: `app/src/main/AndroidManifest.xml`
- Create: `app/src/debug/AndroidManifest.xml`
- Create: `app/src/main/java/com/android/launcherWT/MainActivity.java`
- Create: `app/src/main/res/layout/activity_main.xml`
- Create: `app/src/main/res/values/strings.xml`
- Test: `./gradlew.bat :app:assembleDebug`

**Interfaces:**
- Consumes: 无
- Produces: 可安装的空 HOME。后续界面都改 `activity_main.xml` 和 `MainActivity`。

- [ ] **Step 1: 写根工程**

`settings.gradle`

```gradle
rootProject.name = "wt-launcher"
include ":app", ":core"
```

`build.gradle`

```gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:8.2.2"
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```

`gradle.properties`

```properties
android.useAndroidX=false
android.nonTransitiveRClass=false
org.gradle.jvmargs=-Xmx2048m
```

`core/build.gradle`

```gradle
apply plugin: "java"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    testImplementation "junit:junit:4.13.2"
}
```

`app/build.gradle`

```gradle
apply plugin: "com.android.application"

android {
    namespace "com.android.launcherWT"
    compileSdk 34

    defaultConfig {
        applicationId "com.android.launcherWT"
        minSdk 19
        targetSdk 19
        versionCode 1
        versionName "1.0"
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }
}

dependencies {
    implementation project(":core")
}
```

调试清单和主清单都不写 `android:sharedUserId`。只有 release 清单在 Task 8 加上它。

- [ ] **Step 2: 写清单和空主页**

`app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.android.launcherWT">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.SET_WALLPAPER" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

    <uses-library android:name="com.incall.proxy" android:required="false" />

    <application
        android:allowBackup="false"
        android:label="@string/app_name"
        android:hardwareAccelerated="true">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:launchMode="singleInstance"
            android:screenOrientation="landscape"
            android:stateNotNeeded="true"
            android:clearTaskOnLaunch="true">
            <intent-filter android:priority="1">
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.HOME" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

`app/src/debug/AndroidManifest.xml` 不声明 `sharedUserId`。主清单也不声明，避免没有平台证书时装不上。release 签名任务在本计划最后一步用 `android:sharedUserId="android.uid.system"` 加到一份 `app/src/release/AndroidManifest.xml`。

`app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/placeholder"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/app_name" />
</FrameLayout>
```

`app/src/main/res/values/strings.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">LauncherWT</string>
</resources>
```

`app/src/main/java/com/android/launcherWT/MainActivity.java`

```java
package com.android.launcherWT;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

- [ ] **Step 3: 生成 Wrapper 并编译**

在已安装 Gradle 的机器上，于仓库根目录执行：

```bat
gradle wrapper --gradle-version 8.2.1
gradlew.bat :app:assembleDebug
```

Expected: `BUILD SUCCESSFUL`，并生成 `app/build/outputs/apk/debug/app-debug.apk`。调试清单里没有 `android:sharedUserId`。

- [ ] **Step 4: Commit**

```bat
git add settings.gradle build.gradle gradle.properties gradlew gradlew.bat gradle app core
git commit -m "build: add API 19 launcher shell"
```

---

### Task 2: 应用排序

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/LaunchableApp.java`
- Create: `core/src/main/java/com/android/launcherWT/core/PriorityOrder.java`
- Create: `core/src/main/java/com/android/launcherWT/core/DesktopCatalog.java`
- Test: `core/src/test/java/com/android/launcherWT/core/DesktopCatalogTest.java`

**Interfaces:**
- Consumes: 无
- Produces: `LaunchableApp(String packageName, String className, String label)`，`DesktopCatalog.visible(List<LaunchableApp> installed, Set<String> hiddenKeys)` 返回已排序且去掉隐藏项的列表。键是 `packageName + "/" + className`。

- [ ] **Step 1: 写失败测试**

```java
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
```

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.DesktopCatalogTest`

Expected: FAIL，`DesktopCatalog` 不存在。

- [ ] **Step 3: 写实现**

`LaunchableApp.java` 保存 `packageName`、`className`、`label`，并提供 `key()`，返回 `packageName + "/" + className`。

`PriorityOrder.rank(packageName, className)` 按规格表返回 0 到 11，未知组件返回 12。12 个类名必须与规格表一致，包括软件管理的 `com.incall.apps.fota.MainActivity` 和音乐的 `com.tencent.wecarmusic.activity.MainActivity`。

`DesktopCatalog.visible` 去掉 `hiddenKeys` 中的项，再按 rank 升序，rank 相同则按 `label` 忽略大小写排序。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.DesktopCatalogTest`

Expected: PASS

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core core/src/test/java/com/android/launcherWT/core/DesktopCatalogTest.java
git commit -m "feat: order installed apps and hide chosen ones"
```

---

### Task 3: 壁纸单选和必应解析

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/WallpaperMode.java`
- Create: `core/src/main/java/com/android/launcherWT/core/WallpaperRules.java`
- Create: `core/src/main/java/com/android/launcherWT/core/BingDailyImage.java`
- Test: `core/src/test/java/com/android/launcherWT/core/WallpaperRulesTest.java`
- Test: `core/src/test/java/com/android/launcherWT/core/BingDailyImageTest.java`

**Interfaces:**
- Consumes: 无
- Produces:
  - `enum WallpaperMode { DAILY, CUSTOM }`
  - `WallpaperRules.afterCustomImage(boolean saved)`：`saved` 为真返回 `CUSTOM`，否则返回 `DAILY`
  - `WallpaperRules.shouldDownload(WallpaperMode mode, String cachedStartdate, String remoteStartdate)`：仅 `DAILY` 且两个日期不相等时返回真。`remoteStartdate == null` 返回假。
  - `BingDailyImage.parse(String json)`：成功返回对象，字段 `startdate` 与绝对 `imageUrl`；失败返回 `null`。

- [ ] **Step 1: 写失败测试**

`WallpaperRulesTest` 断言：保存成功得到 `CUSTOM`；保存失败得到 `DAILY`；`DAILY` 且缓存日期不同才下载；`CUSTOM` 时不下载；远程日期缺失时不下载。

`BingDailyImageTest` 使用这段 JSON：

```json
{"images":[{"startdate":"20261111","url":"/th?id=OHR.Test"}]}
```

断言 `startdate` 为 `20261111`，`imageUrl` 为 `https://www.bing.com/th?id=OHR.Test`。空 JSON 返回 `null`。同一 `startdate` 再解析一次，`shouldDownload` 为假。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.WallpaperRulesTest --tests com.android.launcherWT.core.BingDailyImageTest`

Expected: FAIL，类不存在。

- [ ] **Step 3: 写实现**

`BingDailyImage.parse` 在 JSON 里找第一个 `"startdate":"..."` 和 `"url":"..."`。`url` 已是 `http` 开头则原样使用，否则前面加 `https://www.bing.com`。任一字段缺失返回 `null`。不要用 Android 的 `org.json`，这样 JVM 测试不用 Android。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.WallpaperRulesTest --tests com.android.launcherWT.core.BingDailyImageTest`

Expected: PASS

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core/WallpaperMode.java core/src/main/java/com/android/launcherWT/core/WallpaperRules.java core/src/main/java/com/android/launcherWT/core/BingDailyImage.java core/src/test/java/com/android/launcherWT/core/WallpaperRulesTest.java core/src/test/java/com/android/launcherWT/core/BingDailyImageTest.java
git commit -m "feat: keep wallpaper choice exclusive and parse Bing date"
```

---

### Task 4: 桌面数据库

**Files:**
- Create: `app/src/main/java/com/android/launcherWT/store/DesktopDb.java`
- Create: `core/src/main/java/com/android/launcherWT/core/DesktopState.java`
- Test: `core/src/test/java/com/android/launcherWT/core/DesktopStateTest.java`

**Interfaces:**
- Consumes: `WallpaperMode`，`LaunchableApp.key()`
- Produces: `DesktopState`。方法：`boolean isHidden(String key)`、`void setHidden(String key, boolean hidden)`、`WallpaperMode wallpaperMode()`、`void setWallpaperMode(WallpaperMode mode)`、`String customPath()`、`void setCustomPath(String path)`、`String dailyStartdate()`、`void setDailyStartdate(String date)`。内存实现 `DesktopState.memory()` 默认模式为 `DAILY`，没有隐藏项。`DesktopDb` 用 SQLite 实现同一组方法，库损坏时删除文件并重建为空状态。

- [ ] **Step 1: 写失败测试**

测试内存状态：新对象的模式是 `DAILY`；`setHidden(key, true)` 后 `isHidden` 为真；再设为假则恢复显示；`setWallpaperMode(CUSTOM)` 后读回 `CUSTOM`。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.DesktopStateTest`

Expected: FAIL

- [ ] **Step 3: 写内存实现和 SQLite**

`DesktopState` 是抽象类，`memory()` 返回内部类。`DesktopDb extends DesktopState`，表 `hidden(component_key TEXT PRIMARY KEY)` 和表 `wallpaper(id INTEGER PRIMARY KEY, mode TEXT, custom_path TEXT, daily_startdate TEXT)`。`id` 固定为 1。打开数据库抛异常时删除该文件并重新 `getWritableDatabase()`。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.DesktopStateTest`

Expected: PASS。SQLite 路径在 Task 8 的模拟器检查里确认重建。

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core/DesktopState.java core/src/test/java/com/android/launcherWT/core/DesktopStateTest.java app/src/main/java/com/android/launcherWT/store/DesktopDb.java
git commit -m "feat: store hidden apps and wallpaper mode"
```

---

### Task 5: 主页两页和快捷栏

**Files:**
- Modify: `app/src/main/res/layout/activity_main.xml`
- Modify: `app/src/main/java/com/android/launcherWT/MainActivity.java`
- Create: `app/src/main/java/com/android/launcherWT/home/AppGridView.java`
- Create: `app/src/main/java/com/android/launcherWT/home/InstalledApps.java`
- Create: `app/src/main/java/com/android/launcherWT/home/CardPageView.java`
- Create: `app/src/main/java/com/android/launcherWT/home/HotseatView.java`
- Modify: `app/src/main/res/values/strings.xml`

**Interfaces:**
- Consumes: `DesktopCatalog.visible`，`DesktopDb`，`LaunchableApp`
- Produces: 主页顶部左侧按钮 `R.id.settings_button` 打开设置。中间 `ViewPager` 第一页是宫格，第二页是三张卡片。底部三个按钮的组件名固定为规格里的导航、音乐、新闻。

- [ ] **Step 1: 查询已安装应用**

`InstalledApps.load(PackageManager)` 查询 `ACTION_MAIN` + `CATEGORY_LAUNCHER`，每个 `ResolveInfo` 变成 `LaunchableApp`。图标用 `resolveInfo.loadIcon`。没有图标时用 `android.R.drawable.sym_def_app_icon`。

- [ ] **Step 2: 画主页**

`activity_main.xml` 用竖直 `LinearLayout`：顶栏、`android.support.v4.view.ViewPager`（若不用 support 库，则用两个页面的 `HorizontalScrollView` 加上自行记录的页码，避免引入 AndroidX）、页点、底栏。本计划不引入 AndroidX。页切换用一个横向 `ViewFlipper` 或两个子布局加左右滑。手势：`GestureDetector` 的 `onFling`，向左显示卡片页，向右显示应用页。

应用页用 `GridLayout`，`columnCount=5`。`GridLayout` 从 API 14 就有。每格是图标在上、名称在下的竖直布局，点击 `startActivity` 对应组件。`ActivityNotFoundException` 时 `Toast`「应用打不开」，Activity 不结束。

卡片页三个等宽按钮，文字为「导航」「音乐」「新闻」。`AppWidgetHost` 在本计划只尝试绑定规格中的三个提供者；`bindAppWidgetIdIfAllowed` 失败或提供者不存在时，按钮保持应用名。点击仍发送对应 Activity 的 Intent。

底栏三个按钮使用和卡片相同的三个组件，点击打开 Activity，不展开小部件。

顶栏左侧按钮文字「桌面设置」，`contentDescription` 同文案。点击启动 `DesktopSettingsActivity`。右侧先放一个显示当前时间的 `TextView`，车辆图标留给 status 计划。

- [ ] **Step 3: 编译**

Run: `gradlew.bat :app:assembleDebug`

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 4: Commit**

```bat
git add app/src/main
git commit -m "feat: show app pages and hotseat launches"
```

---

### Task 6: 桌面设置

**Files:**
- Create: `app/src/main/java/com/android/launcherWT/DesktopSettingsActivity.java`
- Create: `app/src/main/res/layout/activity_settings.xml`
- Modify: `app/src/main/AndroidManifest.xml`
- Modify: `app/src/main/res/values/strings.xml`

**Interfaces:**
- Consumes: `InstalledApps.load`，`DesktopDb.setHidden`，`DesktopDb.wallpaperMode`
- Produces: 设置页列出全部可启动应用。开关关闭调用 `setHidden(key, true)`，打开调用 `setHidden(key, false)`。返回主页后 `MainActivity.onResume` 重新 `DesktopCatalog.visible`。

- [ ] **Step 1: 登记 Activity**

清单中 `DesktopSettingsActivity` 为 `screenOrientation="landscape"`，`exported="false"`，没有 HOME intent-filter。

- [ ] **Step 2: 做列表和单选**

设置页竖直排列：应用列表、两个 `RadioButton`（「每日自动」「自定义图片」）、一个预览 `ImageView`。两个 `RadioButton` 放进同一个 `RadioGroup`，因此不能同时选中，也不能通过再点一次取消。进入页面时按数据库设置选中项，默认 `DAILY`。

每个应用一行：`ImageView`、名称、`Switch`。API 19 有 `Switch`。开关初始值是 `!db.isHidden(key)`。

选「自定义图片」时立刻 `startActivityForResult(ACTION_GET_CONTENT, 1)`，类型 `image/*`。`RESULT_CANCELED` 时把 `RadioGroup` 设回「每日自动」，不改数据库里的模式。`RESULT_OK` 时把 Uri 的字节复制到 `getFilesDir()/wallpaper/custom.jpg`。复制成功才 `setWallpaperMode(CUSTOM)` 和 `setCustomPath`。复制失败则回到「每日自动」，`Toast`「这张图片不能使用」。

预览：模式为 `CUSTOM` 时显示 `custom.jpg`；为 `DAILY` 时显示 `getFilesDir()/wallpaper/daily.jpg`，文件不存在就留空。

- [ ] **Step 3: 编译**

Run: `gradlew.bat :app:assembleDebug`

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 4: Commit**

```bat
git add app/src/main/AndroidManifest.xml app/src/main/java/com/android/launcherWT/DesktopSettingsActivity.java app/src/main/res/layout/activity_settings.xml app/src/main/res/values/strings.xml
git commit -m "feat: toggle desktop apps and wallpaper mode"
```

---

### Task 7: 把选中的壁纸铺到桌面

**Files:**
- Create: `app/src/main/java/com/android/launcherWT/wallpaper/WallpaperApplier.java`
- Create: `app/src/main/java/com/android/launcherWT/wallpaper/BingClient.java`
- Modify: `app/src/main/java/com/android/launcherWT/MainActivity.java`
- Modify: `app/src/main/java/com/android/launcherWT/DesktopSettingsActivity.java`

**Interfaces:**
- Consumes: `BingDailyImage.parse`，`WallpaperRules.shouldDownload`，`DesktopDb`
- Produces: `BingClient.fetchJson(String url)` 返回响应正文，失败返回 `null`。`WallpaperApplier.apply(Context, File)` 调用 `WallpaperManager.setBitmap`。只在模式为 `DAILY` 且 `shouldDownload` 为真时下载。

- [ ] **Step 1: 下载与铺图**

`BingClient.fetchJson` 使用 `HttpsURLConnection`，连接超时和读取超时都是 10000 毫秒。非 200 或异常返回 `null`。不要设置信任所有证书的 `TrustManager`。

`MainActivity.onStart` 以及收到 `ACTION_DATE_CHANGED`、`ACTION_TIME_CHANGED` 时，若模式是 `DAILY`，在后台线程拉取 JSON。`parse` 得到 `null`，或 `shouldDownload` 为假，则结束。需要下载时把图片字节写入 `wallpaper/daily.jpg`，成功后 `setDailyStartdate` 并 `WallpaperApplier.apply`。失败不删除旧的 `daily.jpg`，主线程 `Toast`「今天的壁纸没有更新」。

模式为 `CUSTOM` 时，`onStart` 只对 `custom.jpg` 调用 `apply`，不访问网络。

设置页在成功保存自定义图片后立刻 `apply` 该文件。改回「每日自动」时，若 `dailyStartdate` 已等于刚解析到的日期，直接 `apply(daily.jpg)`；否则走同一下载路径。

- [ ] **Step 2: 编译**

Run: `gradlew.bat :app:assembleDebug :core:test`

Expected: `BUILD SUCCESSFUL`，core 测试全部 PASS。

- [ ] **Step 3: Commit**

```bat
git add app/src/main/java/com/android/launcherWT/wallpaper app/src/main/java/com/android/launcherWT/MainActivity.java app/src/main/java/com/android/launcherWT/DesktopSettingsActivity.java
git commit -m "feat: apply custom or daily wallpaper exclusively"
```

---

### Task 8: 模拟器核对

**Files:**
- Modify: `app/src/release/AndroidManifest.xml`（只给 release 增加 `android:sharedUserId="android.uid.system"`）

**Interfaces:**
- Consumes: 前面全部界面
- Produces: 一份调试 APK。实车签名不在本计划宣称完成。

- [ ] **Step 1: 安装调试包**

用 API 19 模拟器或映像：

```bat
gradlew.bat :app:assembleDebug
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

Expected: 安装成功。按 Home 能进入这个桌面。

- [ ] **Step 2: 按规格核对**

- 已安装应用都在第一页，优先的空调、新闻排在名称排序的应用前面。超过 10 个出现第二格应用页，卡片页仍在最后。
- 桌面设置关掉一个应用后，宫格和底栏都没有它，系统设置里的应用仍在。再打开，它出现在宫格末尾，图标不变。
- 壁纸默认是「每日自动」。选自定义图片并选中一张后桌面更换，取消选图则单选回到「每日自动」。两个单选不会同时点亮。
- 断网时每日更新失败，桌面仍是上一张，并出现「今天的壁纸没有更新」。

- [ ] **Step 3: Commit**

```bat
git add app/src/release/AndroidManifest.xml
git commit -m "build: mark release launcher as a system user"
```

release 包只有在使用车机平台证书签名后才能安装。本计划不把证书放进仓库。
