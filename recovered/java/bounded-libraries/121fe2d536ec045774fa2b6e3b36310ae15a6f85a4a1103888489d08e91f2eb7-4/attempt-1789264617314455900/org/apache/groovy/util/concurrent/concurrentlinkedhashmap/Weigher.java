/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.ThreadSafe
 */
package org.apache.groovy.util.concurrent.concurrentlinkedhashmap;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface Weigher<V> {
    public int weightOf(V var1);
}

