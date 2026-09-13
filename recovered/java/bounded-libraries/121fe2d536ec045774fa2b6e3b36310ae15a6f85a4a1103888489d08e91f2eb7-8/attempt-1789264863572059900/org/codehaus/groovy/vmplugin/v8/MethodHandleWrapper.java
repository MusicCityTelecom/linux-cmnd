/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v8;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.atomic.AtomicLong;

class MethodHandleWrapper {
    private final MethodHandle cachedMethodHandle;
    private final MethodHandle targetMethodHandle;
    private final boolean canSetTarget;
    private final AtomicLong latestHitCount = new AtomicLong(0L);

    public MethodHandleWrapper(MethodHandle cachedMethodHandle, MethodHandle targetMethodHandle, boolean canSetTarget) {
        this.cachedMethodHandle = cachedMethodHandle;
        this.targetMethodHandle = targetMethodHandle;
        this.canSetTarget = canSetTarget;
    }

    public MethodHandle getCachedMethodHandle() {
        return this.cachedMethodHandle;
    }

    public MethodHandle getTargetMethodHandle() {
        return this.targetMethodHandle;
    }

    public boolean isCanSetTarget() {
        return this.canSetTarget;
    }

    public long incrementLatestHitCount() {
        return this.latestHitCount.incrementAndGet();
    }

    public void resetLatestHitCount() {
        this.latestHitCount.set(0L);
    }

    public long getLatestHitCount() {
        return this.latestHitCount.get();
    }

    public static MethodHandleWrapper getNullMethodHandleWrapper() {
        return NullMethodHandleWrapper.INSTANCE;
    }

    private static class NullMethodHandleWrapper
    extends MethodHandleWrapper {
        public static final NullMethodHandleWrapper INSTANCE = new NullMethodHandleWrapper(null, null, false);

        private NullMethodHandleWrapper(MethodHandle cachedMethodHandle, MethodHandle targetMethodHandle, boolean canSetTarget) {
            super(cachedMethodHandle, targetMethodHandle, canSetTarget);
        }
    }
}

