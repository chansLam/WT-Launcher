# WT Launcher 复刻设计

日期：2026-11-11

## 目标

在 API 19 的车机上替换 `WT_Launcher.apk`（`com.android.launcherWT` 1.8.17.23）。只重做这个桌面。空调、导航、音乐、设置等仍是车上已有应用，桌面用原来的包名和服务名打开、绑定它们。

## 已确认的决定

- 设备只跑 API 19，横屏。工程 `minSdk` 和 `targetSdk` 都是 19。界面用 Java 和 XML。
- 包名 `com.android.launcherWT`，`android:sharedUserId="android.uid.system"`。`com.incall.proxy` 为可选库。
- HOME 入口是 `MainActivity`，`singleInstance`。桌面设置是同一个应用里的第二个界面。
- 打开后第一页是应用宫格，左滑第二页是导航、音乐、新闻三块卡片。
- 应用宫格默认列出车上所有可启动的已安装应用，不再按白名单剔除。图标用该应用自己的图标。
- 桌面设置里可以增删要显示的应用。拿掉只是不显示，不卸载。
- 壁纸只有两个单选项：自定义图片、每日自动。同一时间只能选中一个，也不能两个都不选。默认是每日自动。每日自动使用必应每日图片，不需要密钥，接口里的 `startdate` 变了才换。
- 右上角状态栏和底部三个快捷入口常驻。
- 长按可以拖动图标、合成文件夹、添加和缩放小部件。
- 车上的主题不改变壁纸，也不更换应用图标。设置服务只用来读取 `CustomerId`。
- 车辆状态按 `CustomerId` 走 S311 或 ZBH 两套已有协议。
- 桌面内核的拖拽、文件夹、小部件宿主使用可在 API 19 运行的 AOSP Launcher3 源码。车机层按原界面、包名和服务名重写。

## 界面

主页从上到下三块：

1. 状态栏。左侧是「桌面设置」。靠右是空调、USB、行车记录仪、Wi-Fi、蓝牙、静音、移动信号、时钟。某一项的服务不可用时，只隐藏该项。
2. 中间两页，底部有页点。
   - 第一页：应用宫格，5 列、2 行，每页 10 格。只放当前选择显示的应用。超过 10 个时向后加页，新页仍在卡片页前面。
   - 第二页：三块等宽卡片，导航、音乐、新闻。
3. 底部快捷栏三个位置：导航、音乐、新闻。点击打开对应应用，不打开卡片里的小部件。

宫格来源是 `PackageManager` 里所有带 `MAIN` / `LAUNCHER` 的 Activity。已安装的应用都保留，不用下面这张表做过滤。表只决定排序：表中的组件排在前面，其余已安装应用按名称接在后面。图标一律取该应用自己的图标，不换主题图标，也不使用原 APK 里的图片。应用没有图标时用系统默认应用图标。

排序优先的组件：

| 顺序 | 名称 | 组件 |
| --- | --- | --- |
| 1 | 空调 | `com.incall.apps.airconditioner` / `.MainActivity` |
| 2 | 车辆 | `com.incall.apps.vehiclesetting` / `.VehicleActivity` |
| 3 | 电话 | `com.incall.apps.btphone` / `.activity.BtActivity` |
| 4 | 记录仪 | `com.coagent.dvr` / `.DvrActivity` |
| 5 | 客服 | `com.incall.apps.callcenter` / `.activity.CallCenterActivity` |
| 6 | 设置 | `com.incall.apps.setting` / `.MainActivity` |
| 7 | 文件 | `com.incall.apps.filemanager` / `.activity.FileMainActivity` |
| 8 | 用户 | `com.incall.apps.usercenter` / `.MainActivity` |
| 9 | 软件 | `com.incall.apps.softmanager` / `com.incall.apps.fota.MainActivity` |
| 10 | 导航 | `com.tencent.wecarnavi` / `.MainActivity` |
| 11 | 音乐 | `com.tencent.wecarmusicp` / `com.tencent.wecarmusic.activity.MainActivity` |
| 12 | 新闻 | `com.tencent.wecarnews` / `com.tencent.wecarnews.UI.home.NewsMainTab` |

原桌面应用配置里还有这些名字：3D 车控、舒适座舱、亿连、说明书、保养、收音机、小程序、语音，以及语音的更多页。它们若已安装，会作为普通已安装应用出现，并尽量排在上表后面、其他应用前面。读不到组件名时不单开排序项，只要装了就会出现在其余应用里。

三块卡片绑定车上已有小部件：

- `com.tencent.wecarnavi` / `com.tencent.wecarnavi.appwidget.NaviAppWidget`
- `com.tencent.musicwidget` / `com.tencent.musicwidget.MusicConnectAppWidgetProvider`
- `com.tencent.wecarnews.swidget` / `com.tencent.wecarnews.swidget.SWidget`

小部件提供者不在时，卡片显示应用名。该应用的 Activity 在，点击仍打开它。

应用图标始终是各应用自己的图标。壁纸只来自下面的单选，车上的主题广播不更换壁纸，也不更换图标。不把原 APK 的图片打进新安装包。

长按图标开始拖动。拖到另一个图标上松手，合成文件夹。长按空白处进入小部件列表，可以把小部件放到卡片页并缩放。编辑结果写入本机数据库，下次启动仍在。底部三个快捷入口可以换应用，但不能超过 3 个。应用是否出现在桌面上，只在桌面设置里改。

## 桌面设置

状态栏左侧的「桌面设置」打开 `DesktopSettingsActivity`，横屏，不是 HOME。页面三块：

1. 桌面应用。列出全部可启动的已安装应用，每行是应用自己的图标、名称和一个「显示」开关。打开后出现在应用宫格；关闭后从应用页、文件夹和底部快捷栏移除，应用仍留在系统里。新安装的应用默认显示。
2. 壁纸，两个单选项，只能选中一个。
   - 自定义图片：从系统选择一张图片，复制到应用私有目录，再设为壁纸。选这一项时打开图片选择。用户取消选择，则单选仍停在原来的「每日自动」，不改壁纸。
   - 每日自动：向必应每日图片接口取当天的图。地址是 `https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN`，不带密钥。响应用 `images[0].url` 拼成 `https://www.bing.com` 加该路径，用 `images[0].startdate` 作为这一天的标记。
3. 当前壁纸的预览。预览只显示当前选中的那一种。

每日自动只在它被选中、且本地还没有当天 `startdate` 的缓存时下载。桌面启动、日期或时间被系统改动时检查一次。下载在后台线程完成，成功后用 `WallpaperManager` 设为壁纸，并把图片和日期存到应用私有目录。同一天不重复下载。选中「自定义图片」期间不下载、也不再铺上每日图片。

改回「每日自动」后，立刻改用已缓存的当日图片；缓存不是当天的再下载。自定义图片文件可以留在私有目录，但只要单选不是「自定义图片」，就不作为壁纸。

## 模块

- `MainActivity`：横屏 HOME，装上状态栏、两页内容和快捷栏。状态栏左侧进入桌面设置。
- `DesktopSettingsActivity`：增删桌面应用，切换壁纸模式，选择自定义图片。
- 应用页：列出「显示」为开的可启动应用，分页网格。图标用应用自己的图标，点击用该 Activity 启动。
- 卡片页：`AppWidgetHost` 承载三个小部件。
- 快捷栏：三个持久化的启动项，默认导航、音乐、新闻。
- 状态栏：空调、USB、记录仪、Wi-Fi、蓝牙、静音、信号、时钟各自一个视图。
- 车辆代理：六个接口（空调、记录仪、蓝牙、静音、信号、USB）。S311 与 ZBH 各一套实现。`CustomerId` 决定用哪一套。
- 编辑与存储：拖拽、文件夹、小部件跨度、是否显示在桌面，写入 SQLite。字段包括屏幕、格子坐标、容器（桌面、文件夹或快捷栏）、类型、Intent、横向跨度、纵向跨度，以及 `hidden`。`hidden` 为真的应用不出现在桌面。壁纸模式、自定义图片路径、每日图片的 `startdate` 记在同一库里。

## 数据流

应用列表来自 `PackageManager` 的全部可启动应用，不按白名单删减。第一次启动时全部显示，上表里的组件排在前面。之后以数据库为准：`hidden` 的应用不画在桌面上。用户拖动之后，位置以数据库为准。新安装的应用默认显示，追加到应用页末尾。卸载的应用从数据库删除。桌面设置里关闭「显示」把对应记录标为 `hidden`，并从文件夹和快捷栏移除；打开「显示」则加回应用页末尾。

壁纸单选默认是「每日自动」，并且始终保持其中一个被选中。选中「每日自动」时，若缓存的 `startdate` 不是接口返回的当天值，就下载并调用 `WallpaperManager.setBitmap`。选中「自定义图片」时，只用那张私有目录里的图片，每日图片不再盖上去。

状态栏绑定车上已有服务，回调只更新对应图标：

- 空调：`incall.aircondition.service`
- `CustomerId`：`incall.setting.service`
- 车身信号：`incall.canstatus.service`
- 记录仪：S311 走 coagent 的状态广播；ZBH 在系统里存在 `android.car.wt.WtCarDVRManager` 时走该类
- 蓝牙：`android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED` 和 `STATE_CHANGED`
- 状态栏显隐：`com.incall.action.StatusBar.Show` 和 `com.incall.action.StatusBar.Hide`

`CustomerId` 不是 S311 也不是 ZBH 时，不选一套去猜，六个车辆图标全部隐藏，并把读到的 id 记到日志。

点击应用或快捷栏时，用组件名发 `Intent`。目标不存在时弹出短提示，桌面不退出。

## 错误处理

- 某个车辆服务绑定失败或中途断开：隐藏对应图标，按固定间隔重绑。桌面其他部分继续可用。
- 小部件绑定失败：卡片保留应用名，不留空白崩溃页。
- 数据库损坏：删掉该库，按全部已安装应用和默认快捷栏重建。优先组件仍排在前面，没有应用被标为隐藏，壁纸单选回到「每日自动」。
- 每日图片下载失败、解析失败，或 API 19 无法完成 HTTPS 握手：保留当前壁纸，桌面设置里提示「今天的壁纸没有更新」。不关闭证书校验。
- 自定义图片无法读取：单选不改到「自定义图片」，继续使用每日壁纸，提示「这张图片不能使用」。

## 构建与签名

`sharedUserId` 要求用车机平台证书签名，否则装不上。签名文件通过本机 `local.properties` 指向，不放进仓库。没有平台证书时，工程仍能编出未签成系统用户的调试包，用来在 API 19 模拟器上看界面；这个调试包不绑定真实车辆服务。

应用图标使用各应用自己的图标。不把原 APK 里的图片文件打进新安装包。另需权限 `SET_WALLPAPER`。选择自定义图片使用 `ACTION_GET_CONTENT`，并声明 `READ_EXTERNAL_STORAGE`，以便在 API 19 上读到所选图片。`INTERNET` 用于每日壁纸。

## 测试

- 应用列表：模拟器上所有可启动应用默认都出现，不因为不在优先表里被去掉。优先表中的组件排在前面。图标是该应用自己的图标。超过 10 个显示中的应用时出现下一页。
- 桌面设置：关闭某个应用的「显示」后，它从宫格、文件夹和快捷栏消失，系统里仍在，设置列表里开关为关。再打开后出现在应用页末尾，图标不变。新安装的应用默认显示。
- 壁纸单选：默认选中「每日自动」。选中「自定义图片」并成功选图后，桌面换成该图，复制到应用私有目录，每日图片不再盖上。取消选图时，单选仍是「每日自动」。再选回「每日自动」后，自定义图片不再作为壁纸。两个选项不能同时为选中。
- 每日图片：用一份固定的必应 JSON 响应模拟，同一 `startdate` 不重复下载；换一个 `startdate` 后会再下载并更换壁纸。下载失败时桌面仍是上一张图。
- 两页和快捷栏：第一页是宫格，第二页是三张卡片，底部三个入口能发出对应组件的 Intent。
- 代理：用假的 S311、ZBH 服务分别驱动空调和记录仪图标；服务断开后图标消失，桌面进程还在。
- 编辑：拖动、文件夹、小部件跨度写入数据库，进程重启后位置还在。
- 实车清单：平台证书签名后装到车机，确认能成为 HOME，空调、记录仪图标与车上原服务一致，九个原厂应用和腾讯导航、音乐、新闻能打开。模拟器上不宣称这项已通过。

## 不在本工程里做的事

不重做空调、车辆设置、蓝牙电话、导航、音乐、新闻、系统设置这些独立应用。不实现 `com.incall.proxy` 服务端。不把反编译得到的原厂 Java 拷进工程。
