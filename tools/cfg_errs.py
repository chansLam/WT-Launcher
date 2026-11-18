# -*- coding: utf-8 -*-
import re
from collections import Counter

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac6.txt", encoding="gbk", errors="replace").read()
syms = Counter()
for block in re.split(r"(?=^C:\\Users\\[^\n]+ConfigManager\.java:\d+:)", text, flags=re.M):
    if "ConfigManager.java" not in block[:200]:
        continue
    m = re.search(r"符号:\s+(\S+)\s+([^\r\n]+)", block)
    if m:
        syms[m.group(1) + " " + m.group(2)[:60]] += 1
    else:
        head = re.search(r"错误: ([^\r\n]+)", block)
        if head:
            syms["OTHER " + head.group(1)[:60]] += 1
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\cfg.txt", "w", encoding="utf-8").write(
    "\n".join("%4d %s" % (v, k) for k, v in syms.most_common(30))
)
