# 签名说明（i.MX6Q 车机平台证书）

`car` flavor 的 release 包必须与车机系统应用同一个签名（`sharedUserId=android.uid.system`），
否则安装时会报 `INSTALL_FAILED_SHARED_USER_INCOMPATIBLE`。

本仓库已把平台证书放在 `.signing/platform.jks`（仅此文件从 `.gitignore` 中豁免）。

| 项 | 值 |
| --- | --- |
| 文件 | `.signing/platform.jks` |
| Store 口令 | `android` |
| Key Alias | `platform` |
| Key 口令 | `android` |
| Owner | `EMAILADDRESS=android@freescale.com, CN=Android, OU=Android, O=Android, L=Mountain View, ST=California, C=US` |
| 有效期 | 2011-07-15 → 2038-11-30 |
| 算法 | SHA1withRSA（Android 4.4 只认 v1 签名，构建脚本已同时开 v1/v2） |

SHA-1 指纹（装包前可用它核对是否同一把证书）：

```
3E:0C:AF:3D:79:9F:BA:EC:56:6F:AC:BE:1A:67:FB:25:0B:DE:FD:93
```

SHA-256：

```
7F:BA:E8:17:B7:DB:24:64:2D:89:59:C3:47:B5:61:C0:03:C6:CF:DE:2C:B1:0F:90:9C:93:2F:21:A4:D6:D8:04
```

`app/build.gradle` 中的引用：

```gradle
signingConfigs {
    platform {
        storeFile rootProject.file(".signing/platform.jks")
        storePassword "android"
        keyAlias "platform"
        keyPassword "android"
        enableV1Signing true
        enableV2Signing true
    }
}
```

## 两个 flavor 的签名差异

- `carRelease` —— 用上面的平台证书，装车机（覆盖原厂 `/system/app/WT_Launcher .apk`）。
- `avdDebug` —— 用默认 debug 签名，装模拟器；用平台证书装 AVD 会
  `INSTALL_FAILED_SHARED_USER_INCOMPATIBLE`。

## 换证书

把新 keystore 覆盖 `.signing/platform.jks`，同步改 `app/build.gradle` 里的口令/alias 即可。
CI 里不做任何密钥注入，证书随仓库走。
