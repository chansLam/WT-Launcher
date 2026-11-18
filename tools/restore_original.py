# -*- coding: utf-8 -*-
"""Copy original APK resources and application sources into the Gradle project."""
import os
import re
import shutil
import zipfile
from pathlib import Path

ROOT = Path(r"c:\Users\v_xyzvzhang\wt-launcher")
RES = ROOT / "app" / "src" / "main" / "res"
JADX_RES = ROOT / "docs" / "apk-dump" / "decoded" / "resources" / "res"
JADX_SRC = ROOT / "docs" / "apk-dump" / "decoded" / "sources"
JAVA = ROOT / "app" / "src" / "main" / "java"
JNI = ROOT / "app" / "src" / "main" / "jniLibs"
APK = Path(r"C:\Users\v_xyzvzhang\Downloads\WT_Launcher.apk")

IMAGE_EXT = {".png", ".jpg", ".jpeg", ".webp", ".gif", ".9.png"}
KEEP_PACKAGES = (
    "com/autopai",
    "com/incall",
    "com/wt",
    "me/weishu",
)


def is_image(name: str) -> bool:
    lower = name.lower()
    return lower.endswith(".9.png") or os.path.splitext(lower)[1] in IMAGE_EXT


def existing_files():
    found = set()
    if RES.exists():
        for p in RES.rglob("*"):
            if p.is_file():
                found.add(p.resolve())
    return found


def copy_jadx_resources(preexisting):
    copied = skipped = 0
    for src in JADX_RES.rglob("*"):
        if not src.is_file() or src.name == "public.xml":
            continue
        dest = RES / src.relative_to(JADX_RES)
        if dest.exists():
            skipped += 1
            continue
        dest.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(src, dest)
        copied += 1
    return copied, skipped


def overlay_original_images(preexisting):
    replaced = kept = 0
    with zipfile.ZipFile(APK) as zf:
        for info in zf.infolist():
            name = info.filename
            if not name.startswith("res/") or name.endswith("/") or not is_image(name):
                continue
            dest = RES / Path(name[4:])
            if dest.resolve() in preexisting:
                kept += 1
                continue
            dest.parent.mkdir(parents=True, exist_ok=True)
            with zf.open(info) as src, open(dest, "wb") as out:
                shutil.copyfileobj(src, out)
            replaced += 1
    return replaced, kept


def copy_jni():
    copied = 0
    with zipfile.ZipFile(APK) as zf:
        for info in zf.infolist():
            name = info.filename
            if not name.startswith("lib/") or name.endswith("/"):
                continue
            dest = JNI / Path(name[4:])
            if dest.exists():
                continue
            dest.parent.mkdir(parents=True, exist_ok=True)
            with zf.open(info) as src, open(dest, "wb") as out:
                shutil.copyfileobj(src, out)
            copied += 1
    return copied


NAME_RE = re.compile(r'<(string|style|color|dimen|integer|bool|array|plurals|item|declare-styleable|attr|id)\b[^>]*\bname="([^"]+)"')


def defined_names(path: Path):
    if not path.exists():
        return set()
    text = path.read_text(encoding="utf-8", errors="replace")
    return {m.group(2) for m in NAME_RE.finditer(text)}


def merge_values(filename, preexisting):
    """Add original value entries whose names are not already defined in default values."""
    src = JADX_RES / "values" / filename
    if not src.exists():
        return 0
    ours = RES / "values" / filename
    taken = defined_names(ours)
    # Also names we already merged on a previous run.
    extra = RES / "values" / ("original_" + filename)
    taken |= defined_names(extra)
    text = src.read_text(encoding="utf-8", errors="replace")
    # Drop the xml header and wrapping <resources>.
    body = re.sub(r"^<\?xml[^>]*>\s*", "", text)
    body = re.sub(r"^<resources>\s*", "", body)
    body = re.sub(r"</resources>\s*$", "", body)
    # Split on top-level tags. Good enough for jadx values files.
    chunks = re.findall(r"<(string|style|color|dimen|integer|bool|array|plurals|item|declare-styleable|attr)\b.*?</\1>|<(string|style|color|dimen|integer|bool|item)\b[^>]*/>", body, re.S)
    # The alternation above loses the text. Re-scan properly.
    pieces = []
    for m in re.finditer(
        r"<(string|style|color|dimen|integer|bool|array|plurals|item|declare-styleable|attr)\b[^>]*/>|<(string|style|color|dimen|integer|bool|array|plurals|item|declare-styleable|attr)\b[^>]*>.*?</\2>",
        body,
        re.S,
    ):
        chunk = m.group(0)
        name = re.search(r'\bname="([^"]+)"', chunk)
        if not name or name.group(1) in taken:
            continue
        pieces.append(chunk.strip())
        taken.add(name.group(1))
    if not pieces:
        return 0
    extra.write_text(
        '<?xml version="1.0" encoding="utf-8"?>\n<resources>\n'
        + "\n".join(pieces)
        + "\n</resources>\n",
        encoding="utf-8",
    )
    return len(pieces)


def copy_java():
    copied = skipped = 0
    for rel_root in KEEP_PACKAGES:
        src_root = JADX_SRC / rel_root
        if not src_root.exists():
            continue
        for src in src_root.rglob("*.java"):
            if src.name in ("R.java", "BuildConfig.java"):
                continue
            dest = JAVA / src.relative_to(JADX_SRC)
            if dest.exists():
                skipped += 1
                continue
            dest.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(src, dest)
            copied += 1
    return copied, skipped


def main():
    pre = existing_files()
    print("preexisting res", len(pre))
    c, s = copy_jadx_resources(pre)
    print("jadx res copied", c, "skipped", s)
    r, k = overlay_original_images(pre)
    print("apk images written", r, "kept ours", k)
    print("jni", copy_jni())
    for filename in ("strings.xml", "styles.xml", "colors.xml", "dimens.xml", "bools.xml", "integers.xml", "attrs.xml"):
        n = merge_values(filename, pre)
        print("merged", filename, n)
    j, js = copy_java()
    print("java copied", j, "skipped", js)


if __name__ == "__main__":
    main()
