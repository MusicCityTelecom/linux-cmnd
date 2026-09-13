/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NativeStackCallInfo {
    private static final Pattern SOURCE_NAME_PATTERN = Pattern.compile("^(.+):(\\d+)(\\s+\\(discriminator\\s+\\d+\\))?$");
    private long mAddress;
    private String mLibrary;
    private String mMethod;
    private String mSourceFile;
    private int mLineNumber = -1;

    public NativeStackCallInfo(long address, String lib, String method, String sourceFile) {
        this.mAddress = address;
        this.mLibrary = lib;
        this.mMethod = method;
        Matcher m3 = SOURCE_NAME_PATTERN.matcher(sourceFile);
        if (m3.matches()) {
            this.mSourceFile = m3.group(1);
            try {
                this.mLineNumber = Integer.parseInt(m3.group(2));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            if (m3.groupCount() == 3 && m3.group(3) != null) {
                this.mSourceFile = this.mSourceFile + m3.group(3);
            }
        } else {
            this.mSourceFile = sourceFile;
        }
    }

    public long getAddress() {
        return this.mAddress;
    }

    public String getLibraryName() {
        return this.mLibrary;
    }

    public String getMethodName() {
        return this.mMethod;
    }

    public String getSourceFile() {
        return this.mSourceFile;
    }

    public int getLineNumber() {
        return this.mLineNumber;
    }

    public String toString() {
        return String.format("\t%1$08x\t%2$s --- %3$s --- %4$s:%5$d", this.getAddress(), this.getLibraryName(), this.getMethodName(), this.getSourceFile(), this.getLineNumber());
    }
}

