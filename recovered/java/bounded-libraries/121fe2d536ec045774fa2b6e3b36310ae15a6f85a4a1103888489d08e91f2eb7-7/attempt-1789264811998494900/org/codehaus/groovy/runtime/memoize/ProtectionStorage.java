/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.memoize;

interface ProtectionStorage<K, V> {
    public void touch(K var1, V var2);
}

