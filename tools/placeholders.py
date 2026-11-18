# -*- coding: utf-8 -*-
"""Add placeholder resources for ids the restored code references but the APK did not contain."""
import os
import re
from collections import defaultdict

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac7.txt", encoding="gbk", errors="replace").read()
blocks = re.split(r"(?=^C:\\Users\\[^\n]+\.java:\d+:)", text, flags=re.M)
needed = defaultdict(set)
for block in blocks:
    sym = re.search(r"符号:\s+变量\s+(\w+)", block)
    if not sym:
        continue
    name = sym.group(1)
    src = re.search(r"R\.(drawable|color|id|dimen|string|style|layout|attr)\." + name, block)
    if src:
        needed[src.group(1)].add(name)

res = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\res"
os.makedirs(os.path.join(res, "drawable"), exist_ok=True)
shape = (
    '<?xml version="1.0" encoding="utf-8"?>\n'
    '<shape xmlns:android="http://schemas.android.com/apk/res/android">\n'
    '    <solid android:color="#00000000"/>\n'
    "</shape>\n"
)
for name in sorted(needed["drawable"]):
    path = os.path.join(res, "drawable", name + ".xml")
    if not os.path.exists(path) and not any(
        os.path.exists(os.path.join(res, folder, name + ext))
        for folder in os.listdir(res) if folder.startswith("drawable")
        for ext in (".xml", ".png", ".jpg", ".webp", ".9.png")
    ):
        open(path, "w", encoding="utf-8").write(shape)

def ensure_values(filename, tag, names, template):
    if not names:
        return
    path = os.path.join(res, "values", filename)
    existing = open(path, encoding="utf-8").read() if os.path.exists(path) else ""
    fresh = [n for n in sorted(names) if 'name="%s"' % n not in existing]
    if not fresh:
        return
    body = "\n".join(template % n for n in fresh)
    if os.path.exists(path):
        text = existing.replace("</resources>", body + "\n</resources>")
    else:
        text = '<?xml version="1.0" encoding="utf-8"?>\n<resources>\n' + body + "\n</resources>\n"
    open(path, "w", encoding="utf-8").write(text)

ensure_values("colors_restored.xml", "color", needed["color"], '<color name="%s">#00000000</color>')
ensure_values("dimens_restored.xml", "dimen", needed["dimen"], '<dimen name="%s">0dp</dimen>')
ensure_values("strings_restored.xml", "string", needed["string"], '<string name="%s"></string>')
ensure_values("ids_restored.xml", "id", needed["id"], '<item type="id" name="%s"/>')
ensure_values("styles_restored.xml", "style", needed["style"], '<style name="%s"/>')
layout_dir = os.path.join(res, "layout")
os.makedirs(layout_dir, exist_ok=True)
frame = (
    '<?xml version="1.0" encoding="utf-8"?>\n'
    '<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android" '
    'android:layout_width="match_parent" android:layout_height="match_parent"/>\n'
)
for name in sorted(needed["layout"]):
    path = os.path.join(layout_dir, name + ".xml")
    if not os.path.exists(path):
        open(path, "w", encoding="utf-8").write(frame)
for kind, names in sorted(needed.items()):
    print(kind, len(names))
