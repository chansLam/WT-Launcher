# -*- coding: utf-8 -*-
import os
import re
from collections import Counter

p = r"c:\Users\v_xyzvzhang\wt-launcher\app\build\intermediates\incremental\debug\mergeDebugResources\merged.dir\values\values.xml"
print("size", os.path.getsize(p))
text = open(p, encoding="utf-8", errors="replace").read()
names = re.findall(
    r"<(string|style|color|dimen|integer|bool|item|attr|declare-styleable|array|plurals|id)\b[^>]*\bname=\"([^\"]+)\"",
    text,
)
c = Counter(names)
dups = [(k, v) for k, v in c.items() if v > 1]
print("dups", len(dups))
for k, v in dups[:40]:
    print(v, k[0], k[1])
