# -*- coding: utf-8 -*-
import re
from collections import Counter

text = open(r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac3.txt", encoding="gbk", errors="replace").read()
blocks = re.split(r"(?=^C:\\Users\\[^\n]+\.java:\d+:)", text, flags=re.M)
methods = Counter()
fields = Counter()
other = Counter()
files = Counter()
for block in blocks:
    m = re.match(r"C:\\Users\\.*?\\java\\(.+?\.java):\d+: 错误: ([^\r\n]+)", block)
    if not m:
        continue
    path, kind = m.group(1), m.group(2)
    files[path.split("\\")[0] if False else path] += 1
    if "找不到符号" in kind:
        sym = re.search(r"符号:\s+(\S+)\s+([^\r\n]+)", block)
        loc = re.search(r"位置:\s+(?:类|接口)\s+([^\r\n]+)", block)
        if sym and loc:
            what, name, where = sym.group(1), sym.group(2).strip(), loc.group(1).strip()
            if what == "方法":
                methods[(where, name)] += 1
            else:
                fields[(where, what, name)] += 1
        else:
            other[kind] += 1
    else:
        other[kind] += 1
print("files", len(files), "errors", sum(files.values()))
print("--- other ---")
for k, v in other.most_common(20):
    print(v, k)
print("--- methods", len(methods))
for (where, name), v in list(methods.most_common(30)):
    print(v, where, name)
print("--- fields", len(fields))
for key, v in list(fields.most_common(20)):
    print(v, key)
