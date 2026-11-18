# -*- coding: utf-8 -*-
import re
from collections import Counter

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac3.txt", encoding="gbk", errors="replace").read()
kinds = Counter(re.findall(r"符号:\s+(\S+)", text))
locs = Counter(re.findall(r"位置:\s+(.+)", text))
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\syms.txt", "w", encoding="utf-8").write(
    "KINDS\n" + "\n".join("%s %s" % (v, k) for k, v in kinds.most_common())
    + "\nLOCS\n" + "\n".join("%s %s" % (v, k) for k, v in locs.most_common())
)
