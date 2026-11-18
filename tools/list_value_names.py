# -*- coding: utf-8 -*-
import os
import xml.etree.ElementTree as ET

src = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res\values"
for f in sorted(os.listdir(src)):
    if not f.endswith(".xml"):
        continue
    root = ET.parse(os.path.join(src, f)).getroot()
    names = [el.attrib.get("name", "") for el in root]
    print(f, len(names))
    print(" ", ", ".join(names[:12]))
