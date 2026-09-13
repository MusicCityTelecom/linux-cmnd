/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.introspection;

import org.apache.commons.jexl3.JexlException;

public interface JexlPropertyGet {
    public Object invoke(Object var1) throws Exception;

    public Object tryInvoke(Object var1, Object var2) throws JexlException.TryFailed;

    public boolean tryFailed(Object var1);

    public boolean isCacheable();
}

