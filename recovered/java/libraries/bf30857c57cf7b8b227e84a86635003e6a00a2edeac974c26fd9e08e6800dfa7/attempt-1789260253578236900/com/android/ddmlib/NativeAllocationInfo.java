/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.NativeStackCallInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeAllocationInfo {
    public static final String END_STACKTRACE_KW = "EndStacktrace";
    public static final String BEGIN_STACKTRACE_KW = "BeginStacktrace:";
    public static final String TOTAL_SIZE_KW = "TotalSize:";
    public static final String SIZE_KW = "Size:";
    public static final String ALLOCATIONS_KW = "Allocations:";
    private static final int FLAG_ZYGOTE_CHILD = Integer.MIN_VALUE;
    private static final int FLAG_MASK = Integer.MIN_VALUE;
    private static final List<String> FILTERED_LIBRARIES = Arrays.asList("libc.so", "libc_malloc_debug_leak.so");
    private static final List<Pattern> FILTERED_METHOD_NAME_PATTERNS = Arrays.asList(Pattern.compile("malloc", 2), Pattern.compile("calloc", 2), Pattern.compile("realloc", 2), Pattern.compile("operator new", 2), Pattern.compile("memalign", 2));
    private final int mSize;
    private final boolean mIsZygoteChild;
    private int mAllocations;
    private final ArrayList<Long> mStackCallAddresses = new ArrayList();
    private ArrayList<NativeStackCallInfo> mResolvedStackCall = null;
    private boolean mIsStackCallResolved = false;

    public NativeAllocationInfo(int size, int allocations) {
        this.mSize = size & Integer.MAX_VALUE;
        this.mIsZygoteChild = (size & Integer.MIN_VALUE) != 0;
        this.mAllocations = allocations;
    }

    public void addStackCallAddress(long address) {
        this.mStackCallAddresses.add(address);
    }

    public int getSize() {
        return this.mSize;
    }

    public boolean isZygoteChild() {
        return this.mIsZygoteChild;
    }

    public int getAllocationCount() {
        return this.mAllocations;
    }

    public boolean isStackCallResolved() {
        return this.mIsStackCallResolved;
    }

    public List<Long> getStackCallAddresses() {
        return this.mStackCallAddresses;
    }

    public synchronized void setResolvedStackCall(List<NativeStackCallInfo> resolvedStackCall) {
        if (this.mResolvedStackCall == null) {
            this.mResolvedStackCall = new ArrayList();
        } else {
            this.mResolvedStackCall.clear();
        }
        this.mResolvedStackCall.addAll(resolvedStackCall);
        this.mIsStackCallResolved = !this.mResolvedStackCall.isEmpty();
    }

    public synchronized List<NativeStackCallInfo> getResolvedStackCall() {
        if (this.mIsStackCallResolved) {
            return this.mResolvedStackCall;
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NativeAllocationInfo) {
            NativeAllocationInfo mi = (NativeAllocationInfo)obj;
            if (this.mSize != mi.mSize || this.mAllocations != mi.mAllocations) {
                return false;
            }
            return this.stackEquals(mi);
        }
        return false;
    }

    public boolean stackEquals(NativeAllocationInfo mi) {
        if (this.mStackCallAddresses.size() != mi.mStackCallAddresses.size()) {
            return false;
        }
        int count = this.mStackCallAddresses.size();
        for (int i2 = 0; i2 < count; ++i2) {
            long b2;
            long a2 = this.mStackCallAddresses.get(i2);
            if (a2 == (b2 = mi.mStackCallAddresses.get(i2).longValue())) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + this.mSize;
        result = 31 * result + this.mAllocations;
        result = 31 * result + this.mStackCallAddresses.size();
        for (long addr : this.mStackCallAddresses) {
            result = 31 * result + (int)(addr ^ addr >>> 32);
        }
        return result;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(ALLOCATIONS_KW);
        buffer.append(' ');
        buffer.append(this.mAllocations);
        buffer.append('\n');
        buffer.append(SIZE_KW);
        buffer.append(' ');
        buffer.append(this.mSize);
        buffer.append('\n');
        buffer.append(TOTAL_SIZE_KW);
        buffer.append(' ');
        buffer.append(this.mSize * this.mAllocations);
        buffer.append('\n');
        if (this.mResolvedStackCall != null) {
            buffer.append(BEGIN_STACKTRACE_KW);
            buffer.append('\n');
            for (NativeStackCallInfo source : this.mResolvedStackCall) {
                long addr = source.getAddress();
                if (addr == 0L) continue;
                if (source.getLineNumber() != -1) {
                    buffer.append(String.format("\t%1$08x\t%2$s --- %3$s --- %4$s:%5$d\n", addr, source.getLibraryName(), source.getMethodName(), source.getSourceFile(), source.getLineNumber()));
                    continue;
                }
                buffer.append(String.format("\t%1$08x\t%2$s --- %3$s --- %4$s\n", addr, source.getLibraryName(), source.getMethodName(), source.getSourceFile()));
            }
            buffer.append(END_STACKTRACE_KW);
            buffer.append('\n');
        }
        return buffer.toString();
    }

    public synchronized NativeStackCallInfo getRelevantStackCallInfo() {
        if (this.mIsStackCallResolved && this.mResolvedStackCall != null) {
            for (NativeStackCallInfo info : this.mResolvedStackCall) {
                if (!this.isRelevantLibrary(info.getLibraryName()) || !this.isRelevantMethod(info.getMethodName())) continue;
                return info;
            }
            if (!this.mResolvedStackCall.isEmpty()) {
                return this.mResolvedStackCall.get(0);
            }
        }
        return null;
    }

    private boolean isRelevantLibrary(String libPath) {
        for (String l2 : FILTERED_LIBRARIES) {
            if (!libPath.endsWith(l2)) continue;
            return false;
        }
        return true;
    }

    private boolean isRelevantMethod(String methodName) {
        for (Pattern p3 : FILTERED_METHOD_NAME_PATTERNS) {
            Matcher m3 = p3.matcher(methodName);
            if (!m3.find()) continue;
            return false;
        }
        return true;
    }
}

