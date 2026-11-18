# -*- coding: utf-8 -*-
import xml.etree.ElementTree as ET
from collections import Counter

merged = r"c:\Users\v_xyzvzhang\wt-launcher\app\build\intermediates\incremental\debug\mergeDebugResources\merged.dir\values\values.xml"
attrs_path = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res\values\attrs.xml"
ANDROID = "http://schemas.android.com/apk/res/android"
ET.register_namespace("android", ANDROID)


def local(tag):
    return tag.split("}", 1)[-1]


counts = Counter()
for el in ET.parse(merged).getroot().iter():
    if local(el.tag) == "attr" and el.attrib.get("name"):
        counts[el.attrib["name"]] += 1
dups = {name for name, n in counts.items() if n > 1}
print("duplicate attr names", len(dups))

tree = ET.parse(attrs_path)
root = tree.getroot()
removed = 0
for el in list(root):
    if local(el.tag) == "attr" and el.attrib.get("name") in dups:
        root.remove(el)
        removed += 1
tree.write(attrs_path, encoding="utf-8", xml_declaration=True)
print("removed from ours", removed, "kept", len(list(root)))
