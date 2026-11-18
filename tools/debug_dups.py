# -*- coding: utf-8 -*-
import os
import xml.etree.ElementTree as ET
from collections import Counter

p = r"c:\Users\v_xyzvzhang\wt-launcher\app\build\intermediates\incremental\debug\mergeDebugResources\merged.dir\values\values.xml"
print("exists", os.path.exists(p), "size", os.path.getsize(p))
tree = ET.parse(p)
root = tree.getroot()
print("root", root.tag, "children", len(list(root)))
counts = Counter()
for el in root:
    name = el.attrib.get("name")
    tag = el.tag.split("}", 1)[-1]
    if name:
        counts[(tag, name)] += 1
dups = [k for k, n in counts.items() if n > 1]
print("dups", len(dups))
print("sample", dups[:5])
src = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res\values"
print("src files", os.listdir(src)[:20])
# how many of those names are in attrs.xml
attrs = ET.parse(os.path.join(src, "attrs.xml")).getroot()
anames = {el.attrib.get("name") for el in attrs}
hit = [k for k in dups if k[1] in anames]
print("attrs hits", len(hit), "attrs count", len(anames))
