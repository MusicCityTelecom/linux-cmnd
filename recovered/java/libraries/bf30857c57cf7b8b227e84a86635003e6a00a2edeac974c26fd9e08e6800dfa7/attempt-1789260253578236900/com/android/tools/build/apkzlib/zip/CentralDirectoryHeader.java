/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.CentralDirectoryHeaderCompressInfo;
import com.android.tools.build.apkzlib.zip.ExtraField;
import com.android.tools.build.apkzlib.zip.GPFlags;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.utils.MsDosDateTimeUtils;
import com.google.common.base.Verify;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CentralDirectoryHeader
implements Cloneable {
    private static final int DEFAULT_VERSION_MADE_BY = 24;
    private final String name;
    private long crc32;
    private long uncompressedSize;
    private long madeBy;
    private GPFlags gpBit;
    private long lastModTime;
    private long lastModDate;
    private ExtraField extraField;
    private byte[] comment;
    private long internalAttributes;
    private long externalAttributes;
    private long offset;
    private byte[] encodedFileName;
    private final Future<CentralDirectoryHeaderCompressInfo> compressInfo;
    private final ZFile file;

    CentralDirectoryHeader(String name, byte[] encodedFileName, long uncompressedSize, Future<CentralDirectoryHeaderCompressInfo> compressInfo, GPFlags flags, ZFile zFile) {
        this.name = name;
        this.uncompressedSize = uncompressedSize;
        this.crc32 = 0L;
        this.madeBy = 24L;
        this.gpBit = flags;
        this.lastModTime = MsDosDateTimeUtils.packCurrentTime();
        this.lastModDate = MsDosDateTimeUtils.packCurrentDate();
        this.extraField = new ExtraField();
        this.comment = new byte[0];
        this.internalAttributes = 0L;
        this.externalAttributes = 0L;
        this.offset = -1L;
        this.encodedFileName = encodedFileName;
        this.compressInfo = compressInfo;
        this.file = zFile;
    }

    public String getName() {
        return this.name;
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getCrc32() {
        return this.crc32;
    }

    void setCrc32(long crc32) {
        this.crc32 = crc32;
    }

    public long getMadeBy() {
        return this.madeBy;
    }

    void setMadeBy(long madeBy) {
        this.madeBy = madeBy;
    }

    public GPFlags getGpBit() {
        return this.gpBit;
    }

    public long getLastModTime() {
        return this.lastModTime;
    }

    void setLastModTime(long lastModTime) {
        this.lastModTime = lastModTime;
    }

    public long getLastModDate() {
        return this.lastModDate;
    }

    void setLastModDate(long lastModDate) {
        this.lastModDate = lastModDate;
    }

    public ExtraField getExtraField() {
        return this.extraField;
    }

    public void setExtraField(ExtraField extraField) {
        this.setExtraFieldNoNotify(extraField);
        this.file.centralDirectoryChanged();
    }

    void setExtraFieldNoNotify(ExtraField extraField) {
        this.extraField = extraField;
    }

    public byte[] getComment() {
        return this.comment;
    }

    void setComment(byte[] comment) {
        this.comment = comment;
    }

    public long getInternalAttributes() {
        return this.internalAttributes;
    }

    void setInternalAttributes(long internalAttributes) {
        this.internalAttributes = internalAttributes;
    }

    public long getExternalAttributes() {
        return this.externalAttributes;
    }

    void setExternalAttributes(long externalAttributes) {
        this.externalAttributes = externalAttributes;
    }

    public long getOffset() {
        return this.offset;
    }

    void setOffset(long offset) {
        this.offset = offset;
    }

    public byte[] getEncodedFileName() {
        return this.encodedFileName;
    }

    void resetDeferredCrc() {
        this.gpBit = GPFlags.make(this.gpBit.isUtf8FileName());
    }

    protected CentralDirectoryHeader clone() throws CloneNotSupportedException {
        CentralDirectoryHeader cdr = (CentralDirectoryHeader)super.clone();
        cdr.extraField = this.extraField;
        cdr.comment = Arrays.copyOf(this.comment, this.comment.length);
        cdr.encodedFileName = Arrays.copyOf(this.encodedFileName, this.encodedFileName.length);
        return cdr;
    }

    public Future<CentralDirectoryHeaderCompressInfo> getCompressionInfo() {
        return this.compressInfo;
    }

    public CentralDirectoryHeaderCompressInfo getCompressionInfoWithWait() throws IOException {
        try {
            CentralDirectoryHeaderCompressInfo info = this.getCompressionInfo().get();
            Verify.verifyNotNull(info, "info == null", new Object[0]);
            return info;
        }
        catch (InterruptedException e2) {
            throw new IOException("Interrupted while waiting for compression information.", e2);
        }
        catch (ExecutionException e3) {
            throw new IOException("Execution of compression failed.", e3);
        }
    }
}

