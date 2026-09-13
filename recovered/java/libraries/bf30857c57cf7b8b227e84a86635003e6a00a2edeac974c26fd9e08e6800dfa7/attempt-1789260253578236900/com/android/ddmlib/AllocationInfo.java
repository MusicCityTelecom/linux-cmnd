/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IStackTraceInfo;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AllocationInfo
implements IStackTraceInfo {
    private final String mAllocatedClass;
    private final int mAllocNumber;
    private final int mAllocationSize;
    private final short mThreadId;
    private final StackTraceElement[] mStackTrace;

    AllocationInfo(int allocNumber, String allocatedClass, int allocationSize, short threadId, StackTraceElement[] stackTrace) {
        this.mAllocNumber = allocNumber;
        this.mAllocatedClass = allocatedClass;
        this.mAllocationSize = allocationSize;
        this.mThreadId = threadId;
        this.mStackTrace = stackTrace;
    }

    public int getAllocNumber() {
        return this.mAllocNumber;
    }

    public String getAllocatedClass() {
        return this.mAllocatedClass;
    }

    public int getSize() {
        return this.mAllocationSize;
    }

    public short getThreadId() {
        return this.mThreadId;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return this.mStackTrace;
    }

    public int compareTo(AllocationInfo otherAlloc) {
        return otherAlloc.mAllocationSize - this.mAllocationSize;
    }

    public String getAllocationSite() {
        if (this.mStackTrace.length > 0) {
            return this.mStackTrace[0].toString();
        }
        return null;
    }

    public String getFirstTraceClassName() {
        if (this.mStackTrace.length > 0) {
            return this.mStackTrace[0].getClassName();
        }
        return null;
    }

    public String getFirstTraceMethodName() {
        if (this.mStackTrace.length > 0) {
            return this.mStackTrace[0].getMethodName();
        }
        return null;
    }

    public boolean filter(String filter, boolean fullTrace, Locale locale) {
        return this.allocatedClassMatches(filter, locale) || !this.getMatchingStackFrames(filter, fullTrace, locale).isEmpty();
    }

    public boolean allocatedClassMatches(String pattern, Locale locale) {
        return this.mAllocatedClass.toLowerCase(locale).contains(pattern.toLowerCase(locale));
    }

    public List<String> getMatchingStackFrames(String filter, boolean fullTrace, Locale locale) {
        filter = filter.toLowerCase(locale);
        if (this.mStackTrace.length > 0) {
            int length = fullTrace ? this.mStackTrace.length : 1;
            ArrayList<String> matchingFrames = Lists.newArrayListWithExpectedSize(length);
            for (int i2 = 0; i2 < length; ++i2) {
                String frameString = this.mStackTrace[i2].toString();
                if (!frameString.toLowerCase(locale).contains(filter)) continue;
                matchingFrames.add(frameString);
            }
            return matchingFrames;
        }
        return Collections.emptyList();
    }

    public static final class AllocationSorter
    implements Comparator<AllocationInfo> {
        private SortMode mSortMode = SortMode.SIZE;
        private boolean mDescending = true;

        public void setSortMode(SortMode mode) {
            if (this.mSortMode == mode) {
                this.mDescending = !this.mDescending;
            } else {
                this.mSortMode = mode;
            }
        }

        public void setSortMode(SortMode mode, boolean descending) {
            this.mSortMode = mode;
            this.mDescending = descending;
        }

        public SortMode getSortMode() {
            return this.mSortMode;
        }

        public boolean isDescending() {
            return this.mDescending;
        }

        @Override
        public int compare(AllocationInfo o12, AllocationInfo o22) {
            int diff = 0;
            switch (this.mSortMode) {
                case NUMBER: {
                    diff = o12.mAllocNumber - o22.mAllocNumber;
                    break;
                }
                case SIZE: {
                    break;
                }
                case CLASS: {
                    diff = o12.mAllocatedClass.compareTo(o22.mAllocatedClass);
                    break;
                }
                case THREAD: {
                    diff = o12.mThreadId - o22.mThreadId;
                    break;
                }
                case IN_CLASS: {
                    String class1 = o12.getFirstTraceClassName();
                    String class2 = o22.getFirstTraceClassName();
                    diff = AllocationSorter.compareOptionalString(class1, class2);
                    break;
                }
                case IN_METHOD: {
                    String method1 = o12.getFirstTraceMethodName();
                    String method2 = o22.getFirstTraceMethodName();
                    diff = AllocationSorter.compareOptionalString(method1, method2);
                    break;
                }
                case ALLOCATION_SITE: {
                    String desc1 = o12.getAllocationSite();
                    String desc2 = o22.getAllocationSite();
                    diff = AllocationSorter.compareOptionalString(desc1, desc2);
                }
            }
            if (diff == 0) {
                diff = o12.mAllocationSize - o22.mAllocationSize;
            }
            if (this.mDescending) {
                diff = -diff;
            }
            return diff;
        }

        private static int compareOptionalString(String str1, String str2) {
            if (str1 != null) {
                if (str2 == null) {
                    return -1;
                }
                return str1.compareTo(str2);
            }
            if (str2 == null) {
                return 0;
            }
            return 1;
        }
    }

    public static enum SortMode {
        NUMBER,
        SIZE,
        CLASS,
        THREAD,
        ALLOCATION_SITE,
        IN_CLASS,
        IN_METHOD;

    }
}

