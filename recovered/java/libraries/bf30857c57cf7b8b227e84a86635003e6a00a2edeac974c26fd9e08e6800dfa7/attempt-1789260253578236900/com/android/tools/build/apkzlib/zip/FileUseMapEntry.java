/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import javax.annotation.Nullable;

class FileUseMapEntry<T> {
    public static final Comparator<FileUseMapEntry<?>> COMPARE_BY_START = (o12, o22) -> Ints.saturatedCast(o12.getStart() - o22.getStart());
    public static final Comparator<FileUseMapEntry<?>> COMPARE_BY_SIZE = (o12, o22) -> Ints.saturatedCast(o12.getSize() - o22.getSize());
    private final long start;
    private final long end;
    @Nullable
    private final T store;

    private FileUseMapEntry(long start, long end, @Nullable T store) {
        Preconditions.checkArgument(start >= 0L, "start < 0");
        Preconditions.checkArgument(end > start, "end <= start");
        this.start = start;
        this.end = end;
        this.store = store;
    }

    public static FileUseMapEntry<Object> makeFree(long start, long end) {
        return new FileUseMapEntry<Object>(start, end, null);
    }

    public static <T> FileUseMapEntry<T> makeUsed(long start, long end, T store) {
        Preconditions.checkNotNull(store, "store == null");
        return new FileUseMapEntry<T>(start, end, store);
    }

    long getStart() {
        return this.start;
    }

    long getEnd() {
        return this.end;
    }

    long getSize() {
        return this.end - this.start;
    }

    boolean isFree() {
        return this.store == null;
    }

    @Nullable
    T getStore() {
        return this.store;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("start", this.start).add("end", this.end).add("store", this.store).toString();
    }
}

