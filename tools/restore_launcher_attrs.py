# -*- coding: utf-8 -*-
import os
import shutil
import xml.etree.ElementTree as ET

MERGED = r"c:\Users\v_xyzvzhang\wt-launcher\app\build\intermediates\incremental\debug\mergeDebugResources\merged.dir\values\values.xml"
JADX = r"c:\Users\v_xyzvzhang\wt-launcher\docs\apk-dump\decoded\resources\res\values\attrs.xml"
DEST = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res\values\attrs.xml"

ANDROID = "http://schemas.android.com/apk/res/android"
ET.register_namespace("android", ANDROID)


def local(tag):
    return tag.split("}", 1)[-1]


library = set()
for el in ET.parse(MERGED).getroot():
    if local(el.tag) in ("attr", "declare-styleable"):
        name = el.attrib.get("name")
        if name:
            library.add((local(el.tag), name))

shutil.copyfile(JADX, DEST)
tree = ET.parse(DEST)
root = tree.getroot()
removed = 0
for el in list(root):
    kind = local(el.tag)
    name = el.attrib.get("name")
    if (kind, name) in library:
        root.remove(el)
        removed += 1
tree.write(DEST, encoding="utf-8", xml_declaration=True)
print("library decls", len(library), "removed", removed, "kept", len(list(root)))
