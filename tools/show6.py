# -*- coding: utf-8 -*-
import re
from collections import Counter

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac8.txt", encoding="gbk", errors="replace").read()
kinds = Counter()
files = Counter()
for m in re.finditer(r"\\java\\(.+?\.java):\d+: 错误: ([^\r\n]+)", text):
    files[m.group(1)] += 1
    kinds[m.group(2)[:80]] += 1
out = ["KINDS"]
for k, v in kinds.most_common(25):
    out.append("%4d %s" % (v, k))
out.append("FILES")
for k, v in files.most_common(30):
    out.append("%4d %s" % (v, k))
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\javac8.txt", "w", encoding="utf-8").write("\n".join(out))
