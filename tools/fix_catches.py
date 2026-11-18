# -*- coding: utf-8 -*-
import os
import re

root = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\java"
n = 0
for dp, ds, fs in os.walk(root):
    for f in fs:
        if not f.endswith(".java"):
            continue
        path = os.path.join(dp, f)
        text = open(path, encoding="utf-8").read()
        updated = re.sub(r"[ \t]*e = e;\r?\n", "", text)
        updated = re.sub(r"([ \t]*)e = e2;\r?\n", r"\1e2.printStackTrace();\n", updated)
        if updated != text:
            open(path, "w", encoding="utf-8", newline="\n").write(updated)
            n += 1
print("files", n)
