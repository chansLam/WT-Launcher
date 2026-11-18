# -*- coding: utf-8 -*-
import re

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac3.txt", encoding="gbk", errors="replace").read()
blocks = re.split(r"(?=^C:\\Users\\[^\n]+\.java:\d+:)", text, flags=re.M)
out = []
n = 0
for block in blocks:
    if "找不到符号" not in block:
        continue
    if "符号:" in block and "位置:" in block:
        continue
    out.append(block[:400])
    n += 1
    if n >= 8:
        break
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\err-sample.txt", "w", encoding="utf-8").write("\n----\n".join(out))
print("samples", n)
