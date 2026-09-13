/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.util;

import com.android.apksig.internal.util.ByteBufferDataSource;
import com.android.apksig.internal.util.RandomAccessFileDataSource;
import com.android.apksig.util.DataSource;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class DataSources {
    private DataSources() {
    }

    public static DataSource asDataSource(ByteBuffer buffer) {
        if (buffer == null) {
            throw new NullPointerException();
        }
        return new ByteBufferDataSource(buffer);
    }

    public static DataSource asDataSource(RandomAccessFile file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new RandomAccessFileDataSource(file);
    }

    public static DataSource asDataSource(RandomAccessFile file, long offset, long size) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new RandomAccessFileDataSource(file, offset, size);
    }
}

