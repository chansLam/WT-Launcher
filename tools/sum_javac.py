# -*- coding: utf-8 -*-
import re
from collections import Counter

p = r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac2.txt"
text = open(p, encoding="gbk", errors="replace").read()
pkgs = Counter(re.findall(r"程序包([\w.]+)不存在", text))
print("missing packages", len(pkgs))
for k, v in pkgs.most_common(40):
    print(f"{v:4} {k}")
symbols = Counter(re.findall(r"找不到符号\s*符号:\s*(\S+)", text))
print("symbol lines", sum(symbols.values()))
print("error count", text.count("错误:"))
