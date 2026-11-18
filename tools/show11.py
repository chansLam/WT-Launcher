# -*- coding: utf-8 -*-
text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac11.txt", encoding="gbk", errors="replace").read()
start = text.find("错误:")
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\javac11.txt", "w", encoding="utf-8").write(text[max(0, start - 400):])
