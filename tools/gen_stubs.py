# -*- coding: utf-8 -*-
"""Rebuild head-unit stubs from how the decompiled code actually uses them."""
import os
import re
import shutil
from collections import defaultdict

JAVA = r"c:\Users\v_xyzvzhang\wt-launcher\app\src\main\java"
OUT = r"c:\Users\v_xyzvzhang\wt-launcher\platform-stubs\src\main\java"
PREFIXES = ("com.incall.proxy", "android.car")

chain_re = re.compile(
    r"\b((?:com\.incall\.proxy|android\.car)(?:\.[A-Za-z_][A-Za-z0-9_]*)+)"
)

texts = []
for dp, ds, fs in os.walk(JAVA):
    for f in fs:
        if f.endswith(".java"):
            texts.append(open(os.path.join(dp, f), encoding="utf-8", errors="replace").read())
blob = "\n".join(texts)

# (package, outer, inners tuple) -> class. Fields: (package, outer, inner or None) -> names
classes = set()
fields = defaultdict(set)


def is_type(full):
    return re.search(
        r"(?:new|implements|extends|catch)\s+" + re.escape(full) + r"\b"
        + r"|" + re.escape(full) + r"\s+[a-zA-Z_]"
        + r"|" + re.escape(full) + r"(?:\[\]|\s*<)",
        blob,
    ) is not None


for text in texts:
    for match in chain_re.finditer(text):
        parts = match.group(1).split(".")
        # Stop at the first method (lowercase) segment.
        cut = len(parts)
        class_at = None
        for i, part in enumerate(parts):
            if part[:1].isupper():
                class_at = i
                break
        if class_at is None:
            continue
        pascal = []
        for part in parts[class_at:]:
            if not part[:1].isupper():
                break
            pascal.append(part)
        if not pascal:
            continue
        package = ".".join(parts[:class_at])
        # Walk from the outside. A segment is a class when that prefix is used as a type,
        # or when a longer class hangs off it. The final segment is a field when it is not a type.
        for n in range(1, len(pascal) + 1):
            full = package + "." + ".".join(pascal[:n])
            followed_by_method = n == len(pascal) and class_at + len(pascal) < len(parts) and parts[class_at + len(pascal)][:1].islower()
            if n < len(pascal) or is_type(full) or followed_by_method:
                classes.add((package, tuple(pascal[:n])))
            elif n > 1:
                owner = (package, tuple(pascal[: n - 1]))
                classes.add(owner)
                fields[owner].add(pascal[-1])

if os.path.isdir(OUT):
    shutil.rmtree(OUT)

tree = defaultdict(dict)


def ensure(package, parts):
    node = tree[(package, parts[0])]
    node.setdefault("fields", set())
    node.setdefault("inners", {})
    cur = node
    for name in parts[1:]:
        cur = cur["inners"].setdefault(name, {"fields": set(), "inners": {}})
    return cur


for package, parts in classes:
    ensure(package, parts).setdefault("fields", set()).update(fields.get((package, parts), ()))

ifaces = set()
for text in texts:
    for m in re.finditer(r"\bimplements\s+((?:com\.incall\.proxy|android\.car)(?:\.[A-Za-z0-9_]+)+)", text):
        ifaces.add(m.group(1))


def emit_class(package, name, node, indent, parent_full):
    full = parent_full + "." + name if parent_full else package + "." + name
    iface = full in ifaces
    kind = "interface" if iface else "class"
    extra = ""
    if name.endswith("Exception") and not iface:
        extra = " extends Exception"
    if name == "TboxCallListener":
        extra = " extends TboxChangedListener"
    lines = ["%spublic static %s %s%s {" % ("    " * indent, kind, name, extra)] if indent else [
        "public %s %s%s {" % (kind, name, extra)
    ]
    pad = "    " * (indent + 1)
    if not iface:
        lines.append("%spublic %s() {}" % (pad, name))
        lines.append("%spublic static %s getInstance() { return null; }" % (pad, name))
    for field in sorted(node.get("fields", ())):
        lines.append("%spublic static final Object %s = null;" % (pad, field))
    for inner, child in sorted(node.get("inners", {}).items(), key=lambda kv: (kv[0] != "TboxChangedListener", kv[0])):
        lines.extend(emit_class(package, inner, child, indent + 1, full))
    lines.append("%s}" % ("    " * indent))
    return lines


count = 0
for (package, outer), node in sorted(tree.items()):
    body = ["package %s;" % package, ""]
    body.extend(emit_class(package, outer, node, 0, ""))
    body.append("")
    dest = os.path.join(OUT, package.replace(".", os.sep))
    os.makedirs(dest, exist_ok=True)
    open(os.path.join(dest, outer + ".java"), "w", encoding="utf-8").write("\n".join(body))
    count += 1
print("classes", len(classes), "files", count, "fields", sum(len(v) for v in fields.values()))
