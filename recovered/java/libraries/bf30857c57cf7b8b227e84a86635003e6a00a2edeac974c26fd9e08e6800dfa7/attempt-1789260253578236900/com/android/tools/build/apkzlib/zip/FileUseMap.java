/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.FileUseMapEntry;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Nullable;

class FileUseMap {
    private long size;
    private final TreeSet<FileUseMapEntry<?>> map;
    private final TreeSet<FileUseMapEntry<?>> free;
    private int mMinFreeSize;

    FileUseMap(long size, int minFreeSize) {
        Preconditions.checkArgument(size >= 0L, "size < 0");
        Preconditions.checkArgument(minFreeSize >= 0, "minFreeSize < 0");
        this.size = size;
        this.map = new TreeSet(FileUseMapEntry.COMPARE_BY_START);
        this.free = new TreeSet(FileUseMapEntry.COMPARE_BY_SIZE);
        this.mMinFreeSize = minFreeSize;
        if (size > 0L) {
            this.internalAdd(FileUseMapEntry.makeFree(0L, size));
        }
    }

    private void internalAdd(FileUseMapEntry<?> entry) {
        this.map.add(entry);
        if (entry.isFree()) {
            this.free.add(entry);
        }
    }

    private void internalRemove(FileUseMapEntry<?> entry) {
        boolean wasRemoved = this.map.remove(entry);
        Preconditions.checkState(wasRemoved, "entry not in map");
        if (entry.isFree()) {
            this.free.remove(entry);
        }
    }

    private void add(FileUseMapEntry<?> entry) {
        Preconditions.checkArgument(entry.getStart() < this.size, "entry.getStart() >= size");
        Preconditions.checkArgument(entry.getEnd() <= this.size, "entry.getEnd() > size");
        Preconditions.checkArgument(!entry.isFree(), "entry.isFree()");
        FileUseMapEntry<?> container = this.findContainer(entry);
        Verify.verify(container.isFree(), "!container.isFree()", new Object[0]);
        Set<FileUseMapEntry<?>> replacements = FileUseMap.split(container, entry);
        this.internalRemove(container);
        for (FileUseMapEntry<?> r3 : replacements) {
            this.internalAdd(r3);
        }
    }

    <T> FileUseMapEntry<T> add(long start, long end, T store) {
        Preconditions.checkArgument(start >= 0L, "start < 0");
        Preconditions.checkArgument(end > start, "end < start");
        FileUseMapEntry<T> entry = FileUseMapEntry.makeUsed(start, end, store);
        this.add(entry);
        return entry;
    }

    void remove(FileUseMapEntry<?> entry) {
        Preconditions.checkState(this.map.contains(entry), "!map.contains(entry)");
        Preconditions.checkArgument(!entry.isFree(), "entry.isFree()");
        this.internalRemove(entry);
        FileUseMapEntry<Object> replacement = FileUseMapEntry.makeFree(entry.getStart(), entry.getEnd());
        this.internalAdd(replacement);
        this.coalesce(replacement);
    }

    private FileUseMapEntry<?> findContainer(FileUseMapEntry<?> entry) {
        FileUseMapEntry<?> container = this.map.floor(entry);
        Verify.verifyNotNull(container);
        Verify.verify(container.getStart() <= entry.getStart());
        Verify.verify(container.getEnd() >= entry.getEnd());
        return container;
    }

    private static Set<FileUseMapEntry<?>> split(FileUseMapEntry<?> container, FileUseMapEntry<?> entry) {
        Preconditions.checkArgument(container.isFree(), "!container.isFree()");
        long farStart = container.getStart();
        long start = entry.getStart();
        long end = entry.getEnd();
        long farEnd = container.getEnd();
        Verify.verify(farStart <= start, "farStart > start", new Object[0]);
        Verify.verify(start < end, "start >= end", new Object[0]);
        Verify.verify(farEnd >= end, "farEnd < end", new Object[0]);
        HashSet<FileUseMapEntry<?>> result = Sets.newHashSet();
        if (farStart < start) {
            result.add(FileUseMapEntry.makeFree(farStart, start));
        }
        result.add(entry);
        if (end < farEnd) {
            result.add(FileUseMapEntry.makeFree(end, farEnd));
        }
        return result;
    }

    private void coalesce(FileUseMapEntry<?> entry) {
        Preconditions.checkArgument(entry.isFree(), "!entry.isFree()");
        FileUseMapEntry<Object> prevToMerge = null;
        long start = entry.getStart();
        if (start > 0L) {
            prevToMerge = this.map.floor(FileUseMapEntry.makeFree(start - 1L, start));
            Verify.verifyNotNull(prevToMerge);
            if (!prevToMerge.isFree()) {
                prevToMerge = null;
            }
        }
        FileUseMapEntry<Object> nextToMerge = null;
        long end = entry.getEnd();
        if (end < this.size) {
            nextToMerge = this.map.ceiling(FileUseMapEntry.makeFree(end, end + 1L));
            Verify.verifyNotNull(nextToMerge);
            if (!nextToMerge.isFree()) {
                nextToMerge = null;
            }
        }
        if (prevToMerge == null && nextToMerge == null) {
            return;
        }
        long newStart = start;
        if (prevToMerge != null) {
            newStart = prevToMerge.getStart();
            this.internalRemove(prevToMerge);
        }
        long newEnd = end;
        if (nextToMerge != null) {
            newEnd = nextToMerge.getEnd();
            this.internalRemove(nextToMerge);
        }
        this.internalRemove(entry);
        this.internalAdd(FileUseMapEntry.makeFree(newStart, newEnd));
    }

    void truncate() {
        if (this.size == 0L) {
            return;
        }
        FileUseMapEntry<?> last = this.map.last();
        Verify.verifyNotNull(last, "last == null", new Object[0]);
        if (last.isFree()) {
            this.internalRemove(last);
            this.size = last.getStart();
        }
    }

    long size() {
        return this.size;
    }

    long usedSize() {
        if (this.size == 0L) {
            return 0L;
        }
        FileUseMapEntry<?> last = this.map.last();
        Verify.verifyNotNull(last, "last == null", new Object[0]);
        if (last.isFree()) {
            return last.getStart();
        }
        Verify.verify(last.getEnd() == this.size);
        return this.size;
    }

    void extend(long size) {
        Preconditions.checkArgument(size >= this.size, "size < size");
        if (this.size == size) {
            return;
        }
        FileUseMapEntry<Object> newBlock = FileUseMapEntry.makeFree(this.size, size);
        this.internalAdd(newBlock);
        this.size = size;
        this.coalesce(newBlock);
    }

    long locateFree(long size, long alignOffset, long align, PositionAlgorithm alg) {
        FileUseMapEntry<?> last;
        SortedSet<FileUseMapEntry<Object>> matches;
        Preconditions.checkArgument(size > 0L, "size <= 0");
        FileUseMapEntry<Object> minimumSizedEntry = FileUseMapEntry.makeFree(0L, size);
        switch (alg) {
            case BEST_FIT: {
                matches = this.free.tailSet(minimumSizedEntry);
                break;
            }
            case FIRST_FIT: {
                matches = this.map;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        FileUseMapEntry best = null;
        long bestExtraSize = 0L;
        for (FileUseMapEntry fileUseMapEntry : matches) {
            FileUseMapEntry next;
            long emptySpaceLeft;
            if (!fileUseMapEntry.isFree()) continue;
            long extraSize = align == 0L ? 0L : (align - (fileUseMapEntry.getStart() + alignOffset) % align) % align;
            if (extraSize > 0L && extraSize < (long)this.mMinFreeSize) {
                int addAlignBlocks = Ints.checkedCast(((long)this.mMinFreeSize - extraSize + align - 1L) / align);
                extraSize += (long)addAlignBlocks * align;
            }
            if (fileUseMapEntry.getSize() < size + extraSize || (emptySpaceLeft = fileUseMapEntry.getSize() - (size + extraSize)) > 0L && emptySpaceLeft < (long)this.mMinFreeSize && (next = this.map.higher(fileUseMapEntry)) != null && !next.isFree() || best != null && best.getSize() < fileUseMapEntry.getSize()) continue;
            best = fileUseMapEntry;
            bestExtraSize = extraSize;
            if (alg != PositionAlgorithm.FIRST_FIT) continue;
            break;
        }
        long firstFree = this.size;
        if (best == null && !this.map.isEmpty() && (last = this.map.last()).isFree()) {
            firstFree = last.getStart();
        }
        if (best == null) {
            long extra = (align - (firstFree + alignOffset) % align) % align;
            if (extra > 0L && extra < (long)this.mMinFreeSize) {
                extra += align * (((long)this.mMinFreeSize - extra + (align - 1L)) / align);
            }
            return firstFree + extra;
        }
        return best.getStart() + bestExtraSize;
    }

    List<FileUseMapEntry<?>> getFreeAreas() {
        ArrayList<FileUseMapEntry<?>> freeAreas = Lists.newArrayList();
        for (FileUseMapEntry<?> area : this.map) {
            if (!area.isFree() || area.getEnd() == this.size) continue;
            freeAreas.add(area);
        }
        return freeAreas;
    }

    @Nullable
    FileUseMapEntry<?> before(FileUseMapEntry<?> entry) {
        Preconditions.checkNotNull(entry, "entry == null");
        return this.map.lower(entry);
    }

    @Nullable
    FileUseMapEntry<?> after(FileUseMapEntry<?> entry) {
        Preconditions.checkNotNull(entry, "entry == null");
        return this.map.higher(entry);
    }

    @Nullable
    FileUseMapEntry<?> at(long offset) {
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        Preconditions.checkArgument(offset < this.size, "offset >= size");
        FileUseMapEntry<Object> entry = this.map.floor(FileUseMapEntry.makeFree(offset, offset + 1L));
        if (entry == null) {
            return null;
        }
        Verify.verify(entry.getStart() <= offset);
        Verify.verify(entry.getEnd() > offset);
        return entry;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (FileUseMapEntry<?> entry : this.map) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append(entry.getStart());
            builder.append(" - ");
            builder.append(entry.getEnd());
            builder.append(": ");
            builder.append(entry.getStore());
        }
        return builder.toString();
    }

    public static enum PositionAlgorithm {
        BEST_FIT,
        FIRST_FIT;

    }
}

