/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.utils.CachedSupplier;
import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import com.android.tools.build.apkzlib.zip.ZipField;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariantNonNegative;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import java.io.IOException;
import java.nio.ByteBuffer;

class Zip64EocdLocator {
    private static final ZipField.F4 F_SIGNATURE = new ZipField.F4(0, 117853008L, "Zip64 EOCD Locator signature");
    private static final ZipField.F4 F_NUMBER_OF_DISK = new ZipField.F4(F_SIGNATURE.endOffset(), 0L, "Number of disk with Zip64 EOCD");
    private static final ZipField.F8 F_Z64_EOCD_OFFSET = new ZipField.F8(F_NUMBER_OF_DISK.endOffset(), "Offset of Zip64 EOCD", new ZipFieldInvariantNonNegative());
    private static final ZipField.F4 F_TOTAL_NUMBER_OF_DISKS = new ZipField.F4(F_Z64_EOCD_OFFSET.endOffset(), 0L, "Total number of disks");
    public static final int LOCATOR_SIZE = F_TOTAL_NUMBER_OF_DISKS.endOffset();
    private final long z64EocdOffset;
    private final CachedSupplier<byte[]> byteSupplier;

    Zip64EocdLocator(ByteBuffer bytes) throws IOException {
        F_SIGNATURE.verify(bytes);
        F_NUMBER_OF_DISK.verify(bytes);
        long z64EocdOffset = F_Z64_EOCD_OFFSET.read(bytes);
        F_TOTAL_NUMBER_OF_DISKS.verify(bytes);
        Verify.verify(z64EocdOffset >= 0L);
        this.z64EocdOffset = z64EocdOffset;
        this.byteSupplier = new CachedSupplier<byte[]>(this::computeByteRepresentation);
    }

    Zip64EocdLocator(long z64EocdOffset) {
        Preconditions.checkArgument(z64EocdOffset >= 0L, "z64EocdOffset < 0");
        this.z64EocdOffset = z64EocdOffset;
        this.byteSupplier = new CachedSupplier<byte[]>(this::computeByteRepresentation);
    }

    long getZ64EocdOffset() {
        return this.z64EocdOffset;
    }

    long getSize() {
        return F_TOTAL_NUMBER_OF_DISKS.endOffset();
    }

    byte[] toBytes() throws IOException {
        return this.byteSupplier.get();
    }

    private byte[] computeByteRepresentation() {
        ByteBuffer out = ByteBuffer.allocate(F_TOTAL_NUMBER_OF_DISKS.endOffset());
        try {
            F_SIGNATURE.write(out);
            F_NUMBER_OF_DISK.write(out);
            F_Z64_EOCD_OFFSET.write(out, this.z64EocdOffset);
            F_TOTAL_NUMBER_OF_DISKS.write(out);
            return out.array();
        }
        catch (IOException e2) {
            throw new IOExceptionWrapper(e2);
        }
    }
}

