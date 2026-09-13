/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.TreeSet;
import javax.annotation.Nullable;

class LruTracker<T> {
    private final BiMap<T, Integer> objectToAccessTime = HashBiMap.create();
    private final TreeSet<Integer> accessTimes = new TreeSet((i02, i12) -> i12 - i02);
    private int currentTime = 1;

    LruTracker() {
    }

    synchronized void track(T object) {
        Preconditions.checkState(!this.objectToAccessTime.containsKey(object));
        this.objectToAccessTime.put(object, this.currentTime);
        this.accessTimes.add(this.currentTime);
        ++this.currentTime;
    }

    synchronized void untrack(T object) {
        Preconditions.checkState(this.objectToAccessTime.containsKey(object));
        this.accessTimes.remove(this.objectToAccessTime.get(object));
        this.objectToAccessTime.remove(object);
    }

    synchronized void access(T object) {
        this.untrack(object);
        this.track(object);
    }

    synchronized int positionOf(T object) {
        Preconditions.checkState(this.objectToAccessTime.containsKey(object));
        int lastAccess = (Integer)this.objectToAccessTime.get(object);
        return this.accessTimes.headSet(lastAccess).size();
    }

    @Nullable
    synchronized T last() {
        if (this.accessTimes.isEmpty()) {
            return null;
        }
        return (T)this.objectToAccessTime.inverse().get(this.accessTimes.last());
    }
}

