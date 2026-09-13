/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package org.apache.groovy.util.concurrent.concurrentlinkedhashmap;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface EvictionListener<K, V> {
    public void onEviction(K var1, V var2);
}

