# -*- coding: utf-8 -*-
text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac10.txt", encoding="gbk", errors="replace").read()
start = text.find(":app:compileDebugJavaWithJavac")
open(r"c:\Users\v_xyzvzhang\wt-launcher\tools\javac10.txt", "w", encoding="utf-8").write(text[start:])
