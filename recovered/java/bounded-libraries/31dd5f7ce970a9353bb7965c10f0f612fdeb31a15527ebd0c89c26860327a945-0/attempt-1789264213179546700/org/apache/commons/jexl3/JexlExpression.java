/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.util.concurrent.Callable;
import org.apache.commons.jexl3.JexlContext;

public interface JexlExpression {
    public Object evaluate(JexlContext var1);

    public String getSourceText();

    public String getParsedText();

    public Callable<Object> callable(JexlContext var1);
}

