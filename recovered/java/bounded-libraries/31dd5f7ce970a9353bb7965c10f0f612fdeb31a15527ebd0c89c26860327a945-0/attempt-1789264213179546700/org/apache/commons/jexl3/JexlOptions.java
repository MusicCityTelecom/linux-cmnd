/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.math.MathContext;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.internal.Engine;

public final class JexlOptions {
    private static final int SHARED = 7;
    private static final int SHADE = 6;
    private static final int ANTISH = 5;
    private static final int LEXICAL = 4;
    private static final int SAFE = 3;
    private static final int SILENT = 2;
    private static final int STRICT = 1;
    private static final int CANCELLABLE = 0;
    private static final String[] NAMES = new String[]{"cancellable", "strict", "silent", "safe", "lexical", "antish", "lexicalShade", "sharedInstance"};
    private static int DEFAULT = 43;
    private MathContext mathContext = null;
    private int mathScale = Integer.MIN_VALUE;
    private boolean strictArithmetic = true;
    private int flags = DEFAULT;
    private Map<String, Object> namespaces = Collections.emptyMap();

    private static int set(int ordinal, int mask, boolean value) {
        return value ? mask | 1 << ordinal : mask & ~(1 << ordinal);
    }

    private static boolean isSet(int ordinal, int mask) {
        return (mask & 1 << ordinal) != 0;
    }

    public static void setDefaultFlags(String ... flags) {
        DEFAULT = JexlOptions.parseFlags(DEFAULT, flags);
    }

    public static int parseFlags(int mask, String ... flags) {
        block0: for (String name : flags) {
            boolean b = true;
            if (name.charAt(0) == '+') {
                name = name.substring(1);
            } else if (name.charAt(0) == '-') {
                name = name.substring(1);
                b = false;
            }
            for (int flag = 0; flag < NAMES.length; ++flag) {
                if (!NAMES[flag].equals(name)) continue;
                if (b) {
                    mask |= 1 << flag;
                    continue block0;
                }
                mask &= ~(1 << flag);
                continue block0;
            }
        }
        return mask;
    }

    public void setFlags(String[] opts) {
        this.flags = JexlOptions.parseFlags(this.flags, opts);
    }

    public MathContext getMathContext() {
        return this.mathContext;
    }

    public int getMathScale() {
        return this.mathScale;
    }

    public boolean isAntish() {
        return JexlOptions.isSet(5, this.flags);
    }

    public boolean isCancellable() {
        return JexlOptions.isSet(0, this.flags);
    }

    public boolean isLexical() {
        return JexlOptions.isSet(4, this.flags);
    }

    public boolean isLexicalShade() {
        return JexlOptions.isSet(6, this.flags);
    }

    public boolean isSafe() {
        return JexlOptions.isSet(3, this.flags);
    }

    public boolean isSilent() {
        return JexlOptions.isSet(2, this.flags);
    }

    public boolean isStrict() {
        return JexlOptions.isSet(1, this.flags);
    }

    public boolean isStrictArithmetic() {
        return this.strictArithmetic;
    }

    public void setAntish(boolean flag) {
        this.flags = JexlOptions.set(5, this.flags, flag);
    }

    public void setCancellable(boolean flag) {
        this.flags = JexlOptions.set(0, this.flags, flag);
    }

    public void setLexical(boolean flag) {
        this.flags = JexlOptions.set(4, this.flags, flag);
    }

    public void setLexicalShade(boolean flag) {
        this.flags = JexlOptions.set(6, this.flags, flag);
        if (flag) {
            this.flags = JexlOptions.set(4, this.flags, true);
        }
    }

    public void setMathContext(MathContext mcontext) {
        this.mathContext = mcontext;
    }

    public void setMathScale(int mscale) {
        this.mathScale = mscale;
    }

    public void setSafe(boolean flag) {
        this.flags = JexlOptions.set(3, this.flags, flag);
    }

    public void setSilent(boolean flag) {
        this.flags = JexlOptions.set(2, this.flags, flag);
    }

    public void setStrict(boolean flag) {
        this.flags = JexlOptions.set(1, this.flags, flag);
    }

    public void setStrictArithmetic(boolean stricta) {
        this.strictArithmetic = stricta;
    }

    public void setSharedInstance(boolean flag) {
        this.flags = JexlOptions.set(7, this.flags, flag);
    }

    public boolean isSharedInstance() {
        return JexlOptions.isSet(7, this.flags);
    }

    public JexlOptions set(JexlEngine jexl) {
        if (jexl instanceof Engine) {
            ((Engine)jexl).optionsSet(this);
        }
        return this;
    }

    public JexlOptions set(JexlOptions src) {
        this.mathContext = src.mathContext;
        this.mathScale = src.mathScale;
        this.strictArithmetic = src.strictArithmetic;
        this.flags = src.flags;
        this.namespaces = src.namespaces;
        return this;
    }

    public Map<String, Object> getNamespaces() {
        return this.namespaces;
    }

    public void setNamespaces(Map<String, Object> ns) {
        this.namespaces = ns == null ? Collections.emptyMap() : ns;
    }

    public JexlOptions copy() {
        return new JexlOptions().set(this);
    }
}

