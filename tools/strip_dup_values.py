# -*- coding: utf-8 -*-
"""Drop values entries that collide with the AppCompat libraries."""
import os
import xml.etree.ElementTree as ET
from collections import Counter

MERGED = r"c:\Users\v_xyzvzhang\wt-launcher\app\build\intermediates\incremental\debug\mergeDebugResources\merged.dir"
SRC = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res"

ANDROID = "http://schemas.android.com/apk/res/android"
ET.register_namespace("android", ANDROID)


def local(tag):
    return tag.split("}", 1)[-1]


def dup_names(path):
    tree = ET.parse(path)
    counts = Counter()
    for el in tree.getroot():
        name = el.attrib.get("name")
        if name:
            counts[(local(el.tag), name)] += 1
    return {key for key, n in counts.items() if n > 1}


def strip_file(path, dups):
    tree = ET.parse(path)
    root = tree.getroot()
    removed = 0
    for el in list(root):
        name = el.attrib.get("name")
        if name and (local(el.tag), name) in dups:
            root.remove(el)
            removed += 1
    if removed == 0:
        return 0
    if len(list(root)) == 0:
        os.remove(path)
        return removed
    tree.write(path, encoding="utf-8", xml_declaration=True)
    return removed


def main():
    total = 0
    for folder in os.listdir(MERGED):
        if not folder.startswith("values"):
            continue
        merged_dir = os.path.join(MERGED, folder)
        xmls = [f for f in os.listdir(merged_dir) if f.endswith(".xml")]
        if not xmls:
            continue
        dups = set()
        for xml in xmls:
            dups |= dup_names(os.path.join(merged_dir, xml))
        if not dups:
            continue
        src_dir = os.path.join(SRC, folder)
        if not os.path.isdir(src_dir):
            continue
        for xml in os.listdir(src_dir):
            if not xml.endswith(".xml"):
                continue
            n = strip_file(os.path.join(src_dir, xml), dups)
            if n:
                print(folder, xml, n)
                total += n
    print("removed", total)


if __name__ == "__main__":
    main()
