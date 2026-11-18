# -*- coding: utf-8 -*-
text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac5.txt", encoding="gbk", errors="replace").read()
lines = [ln for ln in text.splitlines() if "错误" in ln or "符号" in ln or ".java" in ln]
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\javac5.txt", "w", encoding="utf-8").write("\n".join(lines))
