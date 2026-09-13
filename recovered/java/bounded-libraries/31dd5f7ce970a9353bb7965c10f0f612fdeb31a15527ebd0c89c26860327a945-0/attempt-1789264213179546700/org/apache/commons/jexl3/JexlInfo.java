/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Script;

public class JexlInfo {
    private final int line;
    private final int column;
    private final String name;

    public Detail getDetail() {
        return null;
    }

    public JexlInfo(String source, int l, int c) {
        this.name = source;
        this.line = l;
        this.column = c;
    }

    public JexlInfo() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        String cname = this.getClass().getName();
        String pkgname = this.getClass().getPackage().getName();
        StackTraceElement se = null;
        for (int s = 1; s < stack.length; ++s) {
            se = stack[s];
            String className = se.getClassName();
            if (className.equals(cname)) continue;
            if (!className.startsWith(pkgname + ".internal.") && !className.startsWith(pkgname + ".Jexl") && !className.startsWith(pkgname + ".parser")) break;
            cname = className;
        }
        this.name = se != null ? se.getClassName() + "." + se.getMethodName() + ":" + se.getLineNumber() : "?";
        this.line = 0;
        this.column = 0;
    }

    public JexlInfo at(int l, int c) {
        return new JexlInfo(this.name, l, c);
    }

    protected JexlInfo(JexlInfo copy) {
        this.name = copy.getName();
        this.line = copy.getLine();
        this.column = copy.getColumn();
    }

    public String toString() {
        Detail dbg;
        StringBuilder sb = new StringBuilder(this.name != null ? this.name : "");
        if (this.line > 0) {
            sb.append("@");
            sb.append(this.line);
            if (this.column > 0) {
                sb.append(":");
                sb.append(this.column);
            }
        }
        if ((dbg = this.getDetail()) != null) {
            sb.append("![");
            sb.append(dbg.start());
            sb.append(",");
            sb.append(dbg.end());
            sb.append("]: '");
            sb.append(dbg.toString());
            sb.append("'");
        }
        return sb.toString();
    }

    public final String getName() {
        return this.name;
    }

    public final int getLine() {
        return this.line;
    }

    public final int getColumn() {
        return this.column;
    }

    public JexlInfo detach() {
        return this;
    }

    public static JexlInfo from(JexlScript script) {
        return script instanceof Script ? ((Script)script).getInfo() : null;
    }

    public static interface Detail {
        public int start();

        public int end();

        public String toString();
    }
}

