/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.MultiException;

public interface HK2Loader {
    public Class<?> loadClass(String var1) throws MultiException;
}

