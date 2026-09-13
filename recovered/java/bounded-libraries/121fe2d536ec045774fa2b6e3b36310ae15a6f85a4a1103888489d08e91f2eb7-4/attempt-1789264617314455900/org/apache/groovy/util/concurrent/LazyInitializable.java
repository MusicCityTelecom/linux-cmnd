/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util.concurrent;

public interface LazyInitializable {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    default public void lazyInit() {
        if (this.isInitialized()) {
            return;
        }
        LazyInitializable lazyInitializable = this;
        synchronized (lazyInitializable) {
            if (this.isInitialized()) {
                return;
            }
            this.doInit();
            this.setInitialized(true);
        }
    }

    public void doInit();

    public boolean isInitialized();

    public void setInitialized(boolean var1);
}

