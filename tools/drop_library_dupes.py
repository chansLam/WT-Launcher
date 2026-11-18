# -*- coding: utf-8 -*-
"""Remove project resources that the support libraries already ship."""
import os
import xml.etree.ElementTree as ET

RES = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res"
MAP = r"c:\Users\v_xyzvzhang\wt-launcher\app\build\intermediates\incremental\debug\mergeDebugResources\compile-file-map.properties"

ANDROID = "http://schemas.android.com/apk/res/android"
ET.register_namespace("android", ANDROID)

STYLE_PREFIXES = (
    "Base.TextAppearance",
    "Base.Theme",
    "Base.Widget",
    "Base.V7",
    "Base.V21",
    "Base.V22",
    "Base.V23",
    "Base.V26",
    "Base.DialogWindowTitle",
    "Platform.",
    "Rtl",
    "TextAppearance.AppCompat",
    "TextAppearance.Compat",
    "TextAppearance.Widget",
    "Theme.AppCompat",
    "ThemeOverlay.",
    "Widget.AppCompat",
    "Widget.Compat",
    "Widget.Support",
)


def library_paths():
    found = set()
    for line in open(MAP, encoding="utf-8"):
        if "=" not in line or line.startswith("#"):
            continue
        key = line.split("=", 1)[0]
        if "app-main-" in key:
            continue
        # key ends with \:/relative/path
        marker = "\\:/"
        i = key.find(marker)
        if i < 0:
            continue
        found.add(key[i + len(marker):].replace("\\", "/"))
    return found


def delete_library_files(paths):
    removed = 0
    for rel in paths:
        dest = os.path.join(RES, rel.replace("/", os.sep))
        if os.path.isfile(dest):
            os.remove(dest)
            removed += 1
    return removed


def drop_name(name):
    if not name:
        return False
    if name.startswith("abc_") or name.startswith("notification_"):
        return True
    return any(name.startswith(p) for p in STYLE_PREFIXES)


def strip_values():
    removed = 0
    deleted_files = 0
    for dp, ds, fs in os.walk(RES):
        if not os.path.basename(dp).startswith("values"):
            continue
        for f in fs:
            if not f.endswith(".xml"):
                continue
            if f == "strings.xml" and os.path.basename(dp) == "values":
                continue
            path = os.path.join(dp, f)
            tree = ET.parse(path)
            root = tree.getroot()
            n = 0
            for el in list(root):
                if drop_name(el.attrib.get("name", "")):
                    root.remove(el)
                    n += 1
            if n == 0:
                continue
            removed += n
            if len(list(root)) == 0:
                os.remove(path)
                deleted_files += 1
            else:
                tree.write(path, encoding="utf-8", xml_declaration=True)
    return removed, deleted_files


def main():
    attrs = os.path.join(RES, "values", "attrs.xml")
    if os.path.exists(attrs):
        os.remove(attrs)
        print("deleted attrs.xml")
    paths = library_paths()
    print("library paths", len(paths))
    print("deleted overlapping files", delete_library_files(paths))
    entries, files = strip_values()
    print("stripped values", entries, "emptied files", files)


if __name__ == "__main__":
    main()
