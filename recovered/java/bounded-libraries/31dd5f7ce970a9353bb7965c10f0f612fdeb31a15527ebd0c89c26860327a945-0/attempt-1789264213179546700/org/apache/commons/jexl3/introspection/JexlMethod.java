/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.introspection;

import org.apache.commons.jexl3.JexlException;

public interface JexlMethod {
    public Object invoke(Object var1, Object ... var2) throws Exception;

    public Object tryInvoke(String var1, Object var2, Object ... var3) throws JexlException.TryFailed;

    public boolean tryFailed(Object var1);

    public boolean isCacheable();

    public Class<?> getReturnType();
}

