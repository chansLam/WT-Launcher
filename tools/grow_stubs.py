# -*- coding: utf-8 -*-
"""Add methods and fields reported missing by the last javac run."""
import os
import re

LOG = r"C:\Users\v_xyzvzhang\AppData\Local\Temp\wt-javac4.txt"
ROOT = r"c:\Users\v_xyzvzhang\wt-launcher\platform-stubs\src\main\java"
text = open(LOG, encoding="gbk", errors="replace").read()
blocks = re.split(r"(?=^C:\\Users\\[^\n]+\.java:\d+:)", text, flags=re.M)

added = 0
missing_types = []


def class_file(simple):
    hits = []
    for dp, ds, fs in os.walk(ROOT):
        for f in fs:
            if f == simple + ".java":
                hits.append(os.path.join(dp, f))
    return hits


def insert(path, simple, line):
    global added
    src = open(path, encoding="utf-8").read()
    if line.strip() in src:
        return
    # Insert before the closing brace of the class declaration that matches the simple name.
    pattern = re.compile(r"(public(?: static)? (?:class|interface) %s\b[^{]*\{)" % re.escape(simple))
    m = pattern.search(src)
    if not m:
        return
    # Find the matching close by scanning braces from the opening.
    i = m.end() - 1
    depth = 0
    end = None
    for k in range(i, len(src)):
        if src[k] == "{":
            depth += 1
        elif src[k] == "}":
            depth -= 1
            if depth == 0:
                end = k
                break
    if end is None:
        return
    src = src[:end] + "    " + line + "\n" + src[end:]
    open(path, "w", encoding="utf-8", newline="\n").write(src)
    added += 1


for block in blocks:
    if "找不到符号" not in block:
        continue
    sym = re.search(r"符号:\s+(方法|变量|类)\s+([^\r\n]+)", block)
    loc = re.search(r"位置:\s+(?:类|接口)\s+(\w+)|位置:\s+类型为(\w+)的变量", block)
    if not sym:
        continue
    kind, name = sym.group(1), sym.group(2).strip()
    if kind == "类":
        missing_types.append(name)
        continue
    if not loc:
        continue
    owner = loc.group(1) or loc.group(2)
    hits = class_file(owner)
    if len(hits) != 1:
        continue
    if kind == "变量":
        insert(hits[0], owner, "public Object %s;" % name.split()[0])
    else:
        mname, args = re.match(r"(\w+)\((.*)\)", name).groups()
        params = [a.strip() for a in args.split(",") if a.strip() and a.strip() != "<空值>"]
        # Drop generic noise.
        params = [re.sub(r"<[^>]*>", "", p).split(".")[-1] for p in params]
        formals = ", ".join("%s a%d" % (p, i) for i, p in enumerate(params))
        if mname == "values":
            body = "public static %s[] values() { return new %s[0]; }" % (owner, owner)
        else:
            ret = "void" if mname.startswith(("add", "remove", "set", "register", "unregister")) else "Object"
            body = "public %s %s(%s) { %s}" % (ret, mname, formals, "" if ret == "void" else "return null;")
        insert(hits[0], owner, body)

print("added", added)
print("missing types", missing_types)
