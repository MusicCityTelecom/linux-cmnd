/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.io.Serializable;

public abstract class Expression
implements Serializable {
    public abstract String getExpressionString();

    public abstract boolean equals(Object var1);

    public abstract int hashCode();

    public abstract boolean isLiteralText();
}

