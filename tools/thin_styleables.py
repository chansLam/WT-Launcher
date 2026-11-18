# -*- coding: utf-8 -*-
import xml.etree.ElementTree as ET

path = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res\values\attrs.xml"
ANDROID = "http://schemas.android.com/apk/res/android"
ET.register_namespace("android", ANDROID)


def local(tag):
    return tag.split("}", 1)[-1]


tree = ET.parse(path)
root = tree.getroot()
top = set()
for el in root:
    if local(el.tag) == "attr" and el.attrib.get("name"):
        top.add(el.attrib["name"])

cleared = 0
for el in root:
    if local(el.tag) != "declare-styleable":
        continue
    for child in list(el):
        if local(child.tag) != "attr":
            continue
        name = child.attrib.get("name")
        if name in top and (len(list(child)) > 0 or "format" in child.attrib):
            child.clear()
            child.set("name", name)
            cleared += 1

tree.write(path, encoding="utf-8", xml_declaration=True)
print("cleared styleable attrs", cleared, "top-level", len(top))
