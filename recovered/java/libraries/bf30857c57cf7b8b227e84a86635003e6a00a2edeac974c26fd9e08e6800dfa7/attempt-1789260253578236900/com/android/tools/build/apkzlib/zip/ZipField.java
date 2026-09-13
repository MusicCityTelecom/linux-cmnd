/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.VerifyLog;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariant;
import com.android.tools.build.apkzlib.zip.utils.LittleEndianUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Set;
import javax.annotation.Nullable;

abstract class ZipField {
    private final String name;
    protected final int offset;
    private final int size;
    @Nullable
    private final Long expected;
    private final Set<ZipFieldInvariant> invariants;

    ZipField(int offset, int size, String name, ZipFieldInvariant ... invariants) {
        Preconditions.checkArgument(offset >= 0, "offset >= 0");
        Preconditions.checkArgument(size == 2 || size == 4 || size == 8, "size != 2 && size != 4 && size != 8");
        this.name = name;
        this.offset = offset;
        this.size = size;
        this.expected = null;
        this.invariants = Sets.newHashSet(invariants);
    }

    ZipField(int offset, int size, long expected, String name) {
        Preconditions.checkArgument(offset >= 0, "offset >= 0");
        Preconditions.checkArgument(size == 2 || size == 4 || size == 8, "size != 2 && size != 4 && size != 8");
        this.name = name;
        this.offset = offset;
        this.size = size;
        this.expected = expected;
        this.invariants = Sets.newHashSet();
    }

    private void checkVerifiesInvariants(long value) throws IOException {
        for (ZipFieldInvariant invariant : this.invariants) {
            if (invariant.isValid(value)) continue;
            throw new IOException("Value " + value + " of field " + this.name + " is invalid (fails '" + invariant.getName() + "').");
        }
    }

    void skip(ByteBuffer bytes) throws IOException {
        if (bytes.remaining() < this.size) {
            throw new IOException("Cannot skip field " + this.name + " because only " + bytes.remaining() + " remain in the buffer.");
        }
        bytes.position(bytes.position() + this.size);
    }

    long read(ByteBuffer bytes) throws IOException {
        if (bytes.remaining() < this.size) {
            throw new IOException("Cannot skip field " + this.name + " because only " + bytes.remaining() + " remain in the buffer.");
        }
        bytes.order(ByteOrder.LITTLE_ENDIAN);
        long r3 = this.size == 2 ? (long)LittleEndianUtils.readUnsigned2Le(bytes) : (this.size == 4 ? LittleEndianUtils.readUnsigned4Le(bytes) : LittleEndianUtils.readUnsigned8Le(bytes));
        this.checkVerifiesInvariants(r3);
        return r3;
    }

    void verify(ByteBuffer bytes) throws IOException {
        this.verify(bytes, null);
    }

    void verify(ByteBuffer bytes, @Nullable VerifyLog verifyLog) throws IOException {
        Preconditions.checkState(this.expected != null, "expected == null");
        this.verify(bytes, this.expected, verifyLog);
    }

    void verify(ByteBuffer bytes, long expected) throws IOException {
        this.verify(bytes, expected, null);
    }

    void verify(ByteBuffer bytes, long expected, @Nullable VerifyLog verifyLog) throws IOException {
        this.checkVerifiesInvariants(expected);
        long r3 = this.read(bytes);
        if (r3 != expected) {
            String error = String.format("Incorrect value for field '%s': value is %s but %s expected.", this.name, r3, expected);
            if (verifyLog == null) {
                throw new IOException(error);
            }
            verifyLog.log(error);
        }
    }

    void write(ByteBuffer output, long value) throws IOException {
        this.checkVerifiesInvariants(value);
        Preconditions.checkArgument(value >= 0L, "value (%s) < 0", value);
        if (this.size == 2) {
            Preconditions.checkArgument(value <= 65535L, "value (%s) > 0x0000ffff", value);
            LittleEndianUtils.writeUnsigned2Le(output, Ints.checkedCast(value));
        } else if (this.size == 4) {
            Preconditions.checkArgument(value <= 0xFFFFFFFFL, "value (%s) > 0x00000000ffffffffL", value);
            LittleEndianUtils.writeUnsigned4Le(output, value);
        } else {
            Verify.verify(this.size == 8);
            LittleEndianUtils.writeUnsigned8Le(output, value);
        }
    }

    void write(ByteBuffer output) throws IOException {
        Preconditions.checkState(this.expected != null, "expected == null");
        this.write(output, this.expected);
    }

    int offset() {
        return this.offset;
    }

    int endOffset() {
        return this.offset + this.size;
    }

    static class F8
    extends ZipField {
        F8(int offset, String name, ZipFieldInvariant ... invariants) {
            super(offset, 8, name, invariants);
        }

        F8(int offset, long expected, String name) {
            super(offset, 8, expected, name);
        }
    }

    static class F4
    extends ZipField {
        F4(int offset, String name, ZipFieldInvariant ... invariants) {
            super(offset, 4, name, invariants);
        }

        F4(int offset, long expected, String name) {
            super(offset, 4, expected, name);
        }
    }

    static class F2
    extends ZipField {
        F2(int offset, String name, ZipFieldInvariant ... invariants) {
            super(offset, 2, name, invariants);
        }

        F2(int offset, long expected, String name) {
            super(offset, 2, expected, name);
        }
    }
}

