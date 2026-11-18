# -*- coding: utf-8 -*-
import re
from collections import Counter

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac8.txt", encoding="gbk", errors="replace").read()
syms = Counter()
for block in re.split(r"(?=^C:\\Users\\[^\n]+\.java:\d+:)", text, flags=re.M):
    mfile = re.search(r"\\java\\(.+?\.java)", block)
    if not mfile:
        continue
    name = mfile.group(1)
    sym = re.search(r"符号:\s+(?:方法|变量|类)\s+([^\r\n]+)", block)
    err = re.search(r"错误: ([^\r\n]+)", block)
    key = (name.split("\\")[-1], (sym.group(1) if sym else (err.group(1) if err else "?"))[:70])
    syms[key] += 1
lines = ["%3d %s %s" % (v, k[0], k[1]) for k, v in syms.most_common(40)]
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\syms8.txt", "w", encoding="utf-8").write("\n".join(lines))
