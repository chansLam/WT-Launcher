# WT Launcher 状态栏 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 按车上的 `CustomerId` 显示空调、USB、记录仪、Wi-Fi、蓝牙、静音和移动信号；服务不可用时只隐藏对应图标。

**Architecture:** 平台选择和“服务是否显示图标”是纯 Java，用假回调测试。`app` 里的 S311 与 ZBH 各自绑定规格中的服务名或广播。读不到已知车型时六个图标全部隐藏。调试包不绑定这些真实服务。

**Tech Stack:** 与桌面计划相同。在桌面计划的主页顶栏完成之后执行。

## Global Constraints

- 状态栏靠右的顺序：空调、USB、行车记录仪、Wi-Fi、蓝牙、静音、移动信号、时钟。
- 某一项的服务不可用时，只隐藏该项。
- `CustomerId` 来自 `incall.setting.service`。不是 S311 也不是 ZBH 时，六个车辆图标全部隐藏，并把读到的 id 记到 logcat 标签 `LauncherWT`。
- 空调服务名 `incall.aircondition.service`。车身信号服务名 `incall.canstatus.service`。
- S311 记录仪听 coagent 状态广播。ZBH 仅当系统存在 `android.car.wt.WtCarDVRManager` 时使用它。
- 蓝牙听 `android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED` 和 `STATE_CHANGED`。
- 状态栏显隐听 `com.incall.action.StatusBar.Show` 和 `com.incall.action.StatusBar.Hide`。
- 绑定失败或断开后隐藏对应图标，每 5 秒重绑一次。桌面其他部分继续可用。
- 调试包不绑定真实车辆服务。不实现 `com.incall.proxy` 服务端。

---

### Task 1: 车型选择

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/status/VehiclePlatform.java`
- Test: `core/src/test/java/com/android/launcherWT/core/status/VehiclePlatformTest.java`

**Interfaces:**
- Consumes: 无
- Produces: `enum VehiclePlatform { S311, ZBH, UNKNOWN }` 和 `VehiclePlatform.fromCustomerId(String id)`。`id` 为 `S311` 或 `ZBH` 时返回对应值，其余包含 `null` 和空字符串都返回 `UNKNOWN`。

- [ ] **Step 1: 写失败测试**

`fromCustomerId("S311")` 为 `S311`，`"ZBH"` 为 `ZBH`，`"C301"`、`null`、`""` 都为 `UNKNOWN`。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.status.VehiclePlatformTest`

Expected: FAIL

- [ ] **Step 3: 写实现**

只比较这两个精确字符串，不做大小写转换，避免把未知车型猜成其中一种。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.status.VehiclePlatformTest`

Expected: PASS

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core/status/VehiclePlatform.java core/src/test/java/com/android/launcherWT/core/status/VehiclePlatformTest.java
git commit -m "feat: select S311 or ZBH without guessing"
```

---

### Task 2: 单项显隐

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/status/StatusIcons.java`
- Test: `core/src/test/java/com/android/launcherWT/core/status/StatusIconsTest.java`

**Interfaces:**
- Consumes: `VehiclePlatform`
- Produces: `StatusIcons`。方法 `void setAvailable(String name, boolean available)` 和 `boolean visible(String name)`。`name` 只接受 `air`、`usb`、`dvr`、`wifi`、`bluetooth`、`mute`、`signal`。平台为 `UNKNOWN` 时这七项 `visible` 都为假。平台已知时，只有 `setAvailable(name, true)` 的项为真。时钟不在这个类里，顶栏时钟始终显示。

- [ ] **Step 1: 写失败测试**

`UNKNOWN` 时 `air` 为不可见。`S311` 且 `setAvailable("air", true)` 后只有 `air` 可见，`dvr` 仍不可见。`setAvailable("air", false)` 后 `air` 消失。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.status.StatusIconsTest`

Expected: FAIL

- [ ] **Step 3: 写实现**

默认全部不可用。`UNKNOWN` 在 `visible` 里直接返回假。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.status.StatusIconsTest`

Expected: PASS

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core/status core/src/test/java/com/android/launcherWT/core/status/StatusIconsTest.java
git commit -m "feat: hide a status icon when its service is down"
```

---

### Task 3: 接到顶栏

**Files:**
- Create: `app/src/main/java/com/android/launcherWT/status/StatusBarController.java`
- Modify: `app/src/main/java/com/android/launcherWT/MainActivity.java`
- Modify: `app/src/main/res/layout/activity_main.xml`

**Interfaces:**
- Consumes: `VehiclePlatform.fromCustomerId`，`StatusIcons`
- Produces: 顶栏右侧七个 `ImageView` 加时钟。`StatusBarController.bind(Context)` 在 release 构建里绑定服务；`BuildConfig.DEBUG` 为真时不绑定，七个图标保持隐藏。

- [ ] **Step 1: 画七个图标**

顺序与规格一致。初始 `visibility` 为 `gone`。时钟用 `TextClock` 不可用在 API 19，改用 `TextView`，每 30 秒按 `HH:mm` 刷新。

- [ ] **Step 2: 只在 release 绑定**

`StatusBarController` 读取设置服务里的 customer id 字符串。`UNKNOWN` 时 `Log.i("LauncherWT", "customerId=" + id)` 并保持图标隐藏。S311 绑定 `incall.aircondition.service`、`incall.canstatus.service`，并注册 coagent 记录仪广播。ZBH 用 `Class.forName("android.car.wt.WtCarDVRManager")`，找不到类就把记录仪标为不可用。蓝牙注册规格中的两个系统广播。`StatusBar.Hide` 把整个右侧车辆区设为 `gone`，`StatusBar.Show` 再按 `StatusIcons` 恢复。

`ServiceConnection.onServiceDisconnected` 把对应项设为不可用，并用 `Handler.postDelayed` 每 5000 毫秒调用一次原来的绑定方法，直到 `onServiceConnected`。

- [ ] **Step 3: 编译**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.status.VehiclePlatformTest --tests com.android.launcherWT.core.status.StatusIconsTest :app:assembleDebug`

Expected: 测试 PASS，`BUILD SUCCESSFUL`

- [ ] **Step 4: Commit**

```bat
git add app/src/main/java/com/android/launcherWT/status app/src/main/java/com/android/launcherWT/MainActivity.java app/src/main/res/layout/activity_main.xml
git commit -m "feat: show vehicle status only when the service is up"
```

---

## 实际实现（2026-11-18）

原计划是自绘七个 ImageView，实际改回原版控件，视觉与状态和 1.8.17.23 一致。

- 新增 `app/src/main/java/com/android/launcherWT/status/StatusCluster.java`
- 替换 `OriginalHomeView` 里手搓的静态顶栏（固定 `OFF` 文本、固定“录制中”图标、固定“无信号”图标）
- 顺序与 `res/layout/jg_toprightview_layout.xml` 一致：空调、USB、记录仪、WiFi、蓝牙、静音、导航占位、信号、时钟
- 每项单独 try/catch 构造：服务类缺失时只丢弃该项，其余照常工作，不 crash
- 主题跟随 `Configuration.UI_MODE_NIGHT_*`，交给 `ResourceUtils.setCurTheme` 选 `day_` 资源
- 监听 `com.incall.action.StatusBar.Hide/Show`，隐藏时记住各图标状态再恢复
- 导航卡片沿用原布局的 `ViewStub`，保持收起
- `AndroidManifest.xml` 补上原 APK 有的 `READ_PHONE_STATE`

验证：`:core:test` 31 用例通过，`:app:assembleDebug` BUILD SUCCESSFUL。

## 行为对齐（2026-11-18，5 项）

依据原版 `AppsContentView.onItemClick / startAppsActivity`、`AppsManager`、`TopRightView.setThemeType(1)`：

1. 点击统计：`OriginalHomeView` 构造时 `DataStatisticsManager.initSdk(context)`（Throwable 兜底），每次点击先 `sendEvents(context, packageName, null)`。
2. 语音门禁：点击语音项前读 `Settings.System wt_speech_ready`，非 1 弹 `speech_content` 并不拉起；`SettingNotFoundException` 时按原版放行。
3. 主题实时：`onAttachedToWindow` 用 `IOnThemeChange.Stub` 注册 `SettingServiceManager`，回调 `post(reload())` 刷新背景/图标/指示器；`onDetachedFromWindow` 注销。
4. 安全模式：`HomeLaunch` 在 start / startSpeech 前判 `pm.isSafeMode() && !Utilities.isSystemApp()`，命中弹 `safe_mode_error` 并 return。
5. 状态栏：`StatusCluster` 由夜间判断改为固定 `setCurTheme(1)`（等价 `TopRightView.setThemeType(1)`）。

## 车型过滤（2026-11-18）

原版 `AppsManager.loadAndBindAllAppsList` 的过滤条件在此还原：

- `sys.thirdapk.caninstall == 1`（`AppsConfig.showTest()`）时白名单整体失效，桌面显示所有已安装应用
- 否则车型命中 `mDisableCarTypeList`（CARTYPE_1~9、11~14）**且**包命中 `mDisableAppsList`（`com.coagent.dvr`、`com.incall.apps.comfortcockpit`）时两者都不出现
- 车型取 `com.incall.proxy.setting.SettingManager.getCurCarType()`，取不到时退回默认 `CHANGAN_S311_CARTYPE_10`

实现：`app/src/main/java/com/android/launcherWT/home/CarFilters.java`，由 `DesktopEntries.source` 统一过滤（桌面与应用管理页同步生效）。

## 深度对账与补齐（2026-11-18）

- 未安装过滤：`DesktopEntries.source` 只保留 `queryIntentActivities(MAIN/LAUNCHER, setPackage)` 有结果、或显式 component 存在的白名单项，等价原版 `getActivityList` 的"装且可启动"语义。
- 清单对齐：`taskAffinity=""`、`configChanges`（10 项同原版）、`resumeWhilePausing`（隐藏属性，aapt 接受）、`windowSoftInputMode=adjustPan`、`category MONKEY`、`application icon=@mipmap/ic_launcher_home`、`android:name=.LauncherApplication`（空实现，同原版）。
- 应用名样式：`sans-serif-condensed`、`singleLine + marquee`，对齐 `@style/Icon`。
- 点击反馈：cell 按下 alpha 0.65 / 抬起回 1，补 `BubbleTextView` 的按压态。
- 不还原：`AppsConfig.setChangeUI` + `XmlUtils.parseXml` 的外部主题资源包（原版无调用方，死代码）；`TopRightView` 的 `MediaStatusView`（`initMediaStatusView()` 为空）。

## 背景系统：新增壁纸源与比例裁切（2026-11-18）

新增 4 个源（JSON/直链都已实测可用）：

| 源 | 端点 | 返回 | 缓存 key |
|---|---|---|---|
| Peapix | `https://peapix.com/bing/feed` | JSON，`fullUrl` 为 1920 宽 jpg，带 title/copyright/date | `peapix:<date>` |
| TodayBing | `https://www.todaybing.com/api/v1/today?formate=json&size=fhd` | JSON，`data.image`（webp，API19 可解 lossy） | `todaybing:<date>` |
| UPX8 | `https://wp.upx8.com/api.php?format=json&resolution=1920x1080&count=3` | JSON，`data[].url`（http CDN jpg） | `upx8:<yyyyMMdd>`（随机接口按天缓存） |
| 语幕 | `https://www.yumus.cn/api/?brand=bing&ua=pc` | 302 直链，1920×1080 jpg | `yumus:<yyyyMMdd>` |

未接：Bing VSAA（给出的文档地址不可用，真实端点无法确认）、UApiPro（只回 webp 原图、无 JSON 元数据，且与必应重复）。

- 每日轮换默认纳入新源（共 8 个：bing-zh / bing-en / wallhaven / zzzmh / peapix / todaybing / upx8 / yumus），单选面板新增 4 项。
- UPX8 关键词搜索接入自定义搜索聚合（`content=<关键词>&count=3`），`ImageSearch.parseUpx8` 解析。
- 新增 `core/AspectCrop`：按目标比例中心裁切。`HomeBackground.decode` 与 `WallpaperApplier.setBitmap` 都改为裁切后绘制/设置，修掉 1920×1080 图在 1920×720 屏上被拉伸的问题。

## Android 4.4.2 TLS 加固（2026-11-18）

实测四个新源的服务端链（openssl verify 对照 `res/raw/mozilla_cacert.pem`）：

| 主机 | 叶子证书 | 链 | 协议 |
|---|---|---|---|
| peapix.com / img.peapix.com | ECDSA，Google Trust Services WE1 | 3 | TLS1.2 |
| www.todaybing.com / webp.todaybing.com | ECDSA，Let's Encrypt YE1 | 4 | TLS1.2 |
| wp.upx8.com | ECDSA，Let's Encrypt YE2 | 4 | TLS1.2 |
| www.yumus.cn | RSA，TrustAsia DV TLS RSA CA 2025 | 3 | TLS1.2 |

四个域名的链全部能用内置 Mozilla CA 集合验过（GTS R4、ISRG Root X1、TrustAsia G3/G4 都在），说明**系统 CA 库（4.4 没有这些根）不是问题，内置信任库才是关键**。

改动：
- `Tls12SocketFactory` 由"开启所有套件"改为按优先级挑 `ECDHE_AES-GCM → CHACHA20 → ECDHE_CBC`（4.4 不支持 ChaCha20 会自动跳过），协议顺序 TLS1.2 → 1.1 → 1.0。
- 请求失败自动重试一次（4.4 偶发握手/会话复用失败）。
- `logFailure()` 沿 cause 链打印，排查车机上失败原因时看 `BingClient` tag 即可。
- 下载后先 `BitmapFactory` 探边界再采用，避免 4.4 解不了的 WebP 被当成壁纸发布。
- 注意：四个域名的 http:// 全部 301 到 https，没有明文降级可用。

## 交互重构（8 条要求）
- 桌面默认只排列原版白名单（车型过滤后）+ 已添加应用 + 恒有「桌面设置」块；长按菜单（showMenu/dialog_home_menu）整体删除。
- 桌面图标支持按住 380ms 后拖拽，落位为**插入**语义（其余顺移），松手写 displayOrder；设置块不参与拖拽。
- 设置页合并为单页双栏：左「壁纸设置」（默认/本地/搜索/每日 + 每日源 Spinner + 预览），右「应用管理」（可移除、可拖动排序、可从已安装列表添加）。旧 AppManageActivity / WallpaperActivity 及 4 个布局已删除，清单只剩 MainActivity + DesktopSettingsActivity。
- 左上角「返回桌面」按钮，避免车机无返回键被卡住。
- `./gradlew :core:test :app:assembleDebug` 通过（app-debug.apk ≈ 9.6 MB）。

## 桌面栅格可配置
- 设置页新增「桌面设置」区：列数 4~7、行数 2~4、图标大小 64~160（SeekBar），改完返回桌面即生效（MainActivity.onResume → reload）。
- 持久化落在 desktop.db 新表 `layout(id, columns, rows, icon_size)`，DB 版本升到 4，老库 upgrade 建表；DesktopState 抽象出 gridColumns/gridRows/iconSize。
- OriginalHomeView 去掉写死的 5×3/PAGE_SIZE，改为按列数/行数均分 1920×720 有效区（pad 80/80/64/84），图标按设置尺寸居中，文字随图标缩放（0.85~1.5 倍）。

## 构建变体
- 改为 productFlavor 双通道：`car`（applicationId com.android.launcherWT，targetSdk 19，车机用）、`avd`（`.avd` 后缀，targetSdk 33，模拟器用）。
- 原因：Android 13/14 的安装器拒绝 targetSdk < 23 的包（表现为 INSTALL_FAILED_INVALID_APK），AVD 包必须抬高 targetSdk；车机包保持 19。
- 清单补 `QUERY_ALL_PACKAGES`（targetSdk ≥ 30 时查询已安装应用需要，API 19 上可忽略）。
- 构建命令：车机 `./gradlew :core:test :app:assembleCarDebug`；模拟器 `./gradlew :app:assembleAvdDebug`（产物 app/build/outputs/apk/avd/debug/app-avd-debug.apk）。

## 桌面栅格渲染修复
- 症状：桌面只显示第一个图标，其余图标与所有应用名都不可见；`uiautomator dump` 里只有时钟一个 TextView。
- 原因：`OriginalHomeView.onLayout` 里手工 `cell/icon/label.layout()`，与空实现的 `Board`（ViewGroup.onLayout 为空）和子 `LinearLayout` 自身的测量/布局互相打架，只有首个 child 的 frame 生效，其余 child 位置错误或宽高为 0，被当作不可见。
- 修复：`Board` 变成真正的栅格容器——`configure(pageWidth, columns, rows, pages, pads)` 后在 `onMeasure` 用 EXACTLY(cellW/cellH) 测量每个 child，在 `onLayout` 按 页/列/行 摆放；单元格内部改用标准 `LinearLayout.LayoutParams`（图标定宽定高、文字 wrap + topMargin），删除 `layoutCells` 与手工 frame。列行、图标尺寸改动时只需重测。

## 设置页三 Tab + 保存语义 + 图标预览
- 布局：左栏固定导航「桌面布局 / 壁纸设置 / 应用管理」三个独立 Tab（选中高亮+加粗），右侧 FrameLayout 切三块面板；左栏底部「保存并返回桌面」主按钮 + 下方「返回桌面」（放弃改动）。
- 保存语义：桌面布局（列/行/图标）与应用管理（增删/排序）全部走 pending 缓冲（`pendingColumns/pendingRows/pendingIconSize/pendingOrder/pendingHidden`），点保存才一次性写 `DesktopDb`，随后把 `MainActivity` 置顶再 finish，确保一定回到桌面；壁纸仍即时生效（选本地图、搜索结果、每日源当场落盘并预览）。
- 图标预览：桌面布局 Tab 中间预览块，按桌面实际像素 1:1 渲染样例图标（`iconSize * widthPixels/1920`）与文字，底部标注「N 列 × M 行 · 图标 X px」，拖 SeekBar/换行列即时刷新。
- 文字随图标反向缩放 `clamp(96/iconSize, 0.6, 1.15)`，避免大图标下行与行文字互相压盖（桌面 `addCell` 与设置页预览同一规则）。
