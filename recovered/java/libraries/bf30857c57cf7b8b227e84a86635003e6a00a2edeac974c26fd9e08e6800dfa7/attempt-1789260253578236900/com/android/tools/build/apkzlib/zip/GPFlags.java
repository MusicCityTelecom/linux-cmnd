/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import java.io.IOException;

class GPFlags {
    private static final int BIT_ENCRYPTION = 1;
    private static final int BIT_DEFERRED_CRC = 8;
    private static final int BIT_ENHANCED_DEFLATING = 16;
    private static final int BIT_PATCHED_DATA = 32;
    private static final int BIT_STRONG_ENCRYPTION = 8256;
    private static final int BIT_EFS = 2048;
    private static final int BIT_UNUSED = 51072;
    private final long value;
    private boolean deferredCrc;
    private boolean utf8FileName;

    private GPFlags(long value) {
        this.value = value;
        this.deferredCrc = (value & 8L) != 0L;
        this.utf8FileName = (value & 0x800L) != 0L;
    }

    public long getValue() {
        return this.value;
    }

    public boolean isDeferredCrc() {
        return this.deferredCrc;
    }

    public boolean isUtf8FileName() {
        return this.utf8FileName;
    }

    static GPFlags make(boolean utf8Encoding) {
        long flags = 0L;
        if (utf8Encoding) {
            flags |= 0x800L;
        }
        return new GPFlags(flags);
    }

    static GPFlags from(long bits) throws IOException {
        if ((bits & 1L) != 0L) {
            throw new IOException("Zip files with encrypted of entries not supported.");
        }
        if ((bits & 0x10L) != 0L) {
            throw new IOException("Enhanced deflating not supported.");
        }
        if ((bits & 0x20L) != 0L) {
            throw new IOException("Compressed patched data not supported.");
        }
        if ((bits & 0x2040L) != 0L) {
            throw new IOException("Strong encryption not supported.");
        }
        if ((bits & 0xC780L) != 0L) {
            throw new IOException("Unused bits set in directory entry. Weird. I don't know what's going on.");
        }
        if ((bits & 0xFFFFFFFF00000000L) != 0L) {
            throw new IOException("Unsupported bits after 32.");
        }
        return new GPFlags(bits);
    }
}

