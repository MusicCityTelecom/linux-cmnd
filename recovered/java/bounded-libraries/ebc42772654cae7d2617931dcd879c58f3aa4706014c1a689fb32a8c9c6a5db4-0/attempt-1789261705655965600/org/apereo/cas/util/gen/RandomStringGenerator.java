/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.gen;

public interface RandomStringGenerator {
    public static final int DEFAULT_LENGTH = 36;

    public long getDefaultLength();

    public String getAlgorithm();

    public String getNewString(int var1);

    public String getNewString();

    public byte[] getNewStringAsBytes(int var1);
}

