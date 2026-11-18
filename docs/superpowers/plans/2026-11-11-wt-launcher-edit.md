# WT Launcher 编辑 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在已经能显示应用的桌面上加入长按拖动、文件夹、小部件缩放，以及最多 3 个的快捷栏更换。

**Architecture:** 位置关系放在 `core` 的纯 Java 模型里，用 JVM 测试覆盖占用、文件夹和快捷栏上限。`app` 只负责把长按和松手翻译成这些模型操作，并写入桌面计划里的 SQLite。不复制 AOSP Launcher3 的整棵源码树；格子、容器和跨度按该桌面的 favorites 模型实现，以便在 API 19 上直接编译。

**Tech Stack:** 与桌面计划相同。本计划在 `2026-11-11-wt-launcher-desktop.md` 完成之后执行。

## Global Constraints

- 长按图标开始拖动。拖到另一个图标上松手，合成文件夹。
- 长按空白处进入小部件列表，可以把小部件放到卡片页并缩放。
- 底部快捷栏最多 3 个，可以换成别的已显示应用。
- 应用是否出现在桌面，仍只由桌面设置决定。隐藏的应用要从文件夹和快捷栏移除。
- 编辑结果写入数据库，进程重启后还在。
- 数据库损坏时按桌面计划重建，不保留拖动位置。
- `minSdk` / `targetSdk` 为 19。不引入 AndroidX。

---

### Task 1: 格子占用

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/edit/Cell.java`
- Create: `core/src/main/java/com/android/launcherWT/core/edit/Placement.java`
- Test: `core/src/test/java/com/android/launcherWT/core/edit/PlacementTest.java`

**Interfaces:**
- Consumes: `LaunchableApp.key()`
- Produces: `Placement`。`move(String key, int page, int cell)` 把应用放到应用页。页从 0 开始，每页 10 格，`cell` 为 0 到 9。目标格已有应用时两者交换。`page` 小于 0 或 `cell` 超出 0 到 9 时不改动。

- [ ] **Step 1: 写失败测试**

断言：把 A 放到第 0 页第 1 格后，读回就是这个位置。再把 B 放到同一格，A 和 B 交换。`cell = 10` 时位置不变。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.edit.PlacementTest`

Expected: FAIL，`Placement` 不存在。

- [ ] **Step 3: 写实现**

`Cell` 保存 `page` 和 `index`。`Placement` 用 `Map<String, Cell>`。`move` 先拒绝非法坐标，再查找占用目标格的另一个 key，把它设为原 key 的旧格子；原 key 没有旧格子时，被挤出的项放到同一页的第一个空格，没有空格就放到下一页第 0 格。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.edit.PlacementTest`

Expected: PASS

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core/edit core/src/test/java/com/android/launcherWT/core/edit/PlacementTest.java
git commit -m "feat: place icons on a ten-cell page"
```

---

### Task 2: 文件夹和快捷栏上限

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/edit/Folders.java`
- Create: `core/src/main/java/com/android/launcherWT/core/edit/Hotseat.java`
- Test: `core/src/test/java/com/android/launcherWT/core/edit/FoldersTest.java`
- Test: `core/src/test/java/com/android/launcherWT/core/edit/HotseatTest.java`

**Interfaces:**
- Consumes: 无
- Produces:
  - `Folders.dropOn(String dragged, String target)` 返回新文件夹 id，两个 key 都在该文件夹里，原来的单图标位置不再保留。
  - `Folders.remove(String folderId, String key)` 后若文件夹不足 2 个成员，则解散，剩下的一个回到应用页。
  - `Hotseat.set(int slot, String key)`：`slot` 只接受 0、1、2。第四次不同写入若试图使用新槽位则拒绝。同一 key 再写入另一个槽时，旧槽清空。

- [ ] **Step 1: 写失败测试**

文件夹：A 拖到 B 上后，两者的文件夹 id 相同，成员数为 2。移除 A 后文件夹不存在，B 不再属于文件夹。

快捷栏：槽 0、1、2 可以分别放入三个 key。`set(3, key)` 返回假并且不增加数量。把槽 0 的 key 再 `set(1, key)` 后，槽 0 为空。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.edit.FoldersTest --tests com.android.launcherWT.core.edit.HotseatTest`

Expected: FAIL

- [ ] **Step 3: 写实现**

`Folders` 用递增整数 id。`Hotseat` 用长度为 3 的数组。隐藏应用时由调用方对每个文件夹调用 `remove`，并对快捷栏里相同 key 的槽写成 `null`。这个调用放在 `DesktopSettingsActivity` 关闭开关的地方。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.edit.FoldersTest --tests com.android.launcherWT.core.edit.HotseatTest`

Expected: PASS

- [ ] **Step 5: Commit**

```bat
git add core/src/main/java/com/android/launcherWT/core/edit core/src/test/java/com/android/launcherWT/core/edit/FoldersTest.java core/src/test/java/com/android/launcherWT/core/edit/HotseatTest.java
git commit -m "feat: group icons into folders and cap the hotseat"
```

---

### Task 3: 小部件跨度

**Files:**
- Create: `core/src/main/java/com/android/launcherWT/core/edit/WidgetSpan.java`
- Test: `core/src/test/java/com/android/launcherWT/core/edit/WidgetSpanTest.java`

**Interfaces:**
- Consumes: 无
- Produces: `WidgetSpan.resize(int spanX, int spanY)`。卡片页一行最多 3 列，`spanX` 为 1 到 3，`spanY` 只能是 1。非法值保持上一次的合法跨度，初始是 1×1。三个小部件的类名固定为规格中的导航、音乐、新闻提供者。

- [ ] **Step 1: 写失败测试**

`resize(2, 1)` 后 `spanX` 为 2、`spanY` 为 1。`resize(2, 2)` 和 `resize(4, 1)` 后仍保持 2×1。

- [ ] **Step 2: 跑测试，确认失败**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.edit.WidgetSpanTest`

Expected: FAIL

- [ ] **Step 3: 写实现**

`spanX` 在 1 到 3 且 `spanY == 1` 时更新。卡片页共 3 列，一块小部件可以占 1 到 3 列，不能占两行。

- [ ] **Step 4: 跑测试，确认通过**

Run: `gradlew.bat :core:test --tests com.android.launcherWT.core.edit.WidgetSpanTest`

Expected: PASS

- [ ] **Step 5: 把模型接到主页**

`MainActivity` 长按图标时给该格加一个拖动阴影，松手时若落在另一格上调用 `Folders.dropOn`，否则调用 `Placement.move`。长按卡片页空白处弹出三个小部件名称，点击后该卡片显示对应提供者；绑定失败时卡片文字保持应用名。把结果写入 `DesktopDb` 新表 `placement(component_key, page, cell, folder_id, hotseat_slot)`。重启后按这张表摆放，没有记录的新应用仍追加在末尾。

- [ ] **Step 6: 编译并提交**

Run: `gradlew.bat :core:test :app:assembleDebug`

Expected: core 测试 PASS，`BUILD SUCCESSFUL`

```bat
git add core/src/main/java/com/android/launcherWT/core/edit core/src/test/java/com/android/launcherWT/core/edit app/src/main/java/com/android/launcherWT
git commit -m "feat: drag icons into folders and keep widget cards"
```
