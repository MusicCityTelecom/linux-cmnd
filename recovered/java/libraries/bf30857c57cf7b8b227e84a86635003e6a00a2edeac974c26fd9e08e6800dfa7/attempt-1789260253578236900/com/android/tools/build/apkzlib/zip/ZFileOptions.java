/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.bytestorage.ByteStorageFactory;
import com.android.tools.build.apkzlib.bytestorage.ChunkBasedByteStorageFactory;
import com.android.tools.build.apkzlib.bytestorage.OverflowToDiskByteStorageFactory;
import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectory;
import com.android.tools.build.apkzlib.zip.AlignmentRule;
import com.android.tools.build.apkzlib.zip.AlignmentRules;
import com.android.tools.build.apkzlib.zip.Compressor;
import com.android.tools.build.apkzlib.zip.VerifyLog;
import com.android.tools.build.apkzlib.zip.VerifyLogs;
import com.android.tools.build.apkzlib.zip.compress.DeflateExecutionCompressor;
import com.android.tools.build.apkzlib.zip.utils.ByteTracker;
import com.google.common.base.Supplier;

public class ZFileOptions {
    private ByteStorageFactory storageFactory = new ChunkBasedByteStorageFactory(new OverflowToDiskByteStorageFactory(TemporaryDirectory::newSystemTemporaryDirectory));
    private Compressor compressor = new DeflateExecutionCompressor(Runnable::run, -1);
    private boolean noTimestamps;
    private AlignmentRule alignmentRule = AlignmentRules.compose(new AlignmentRule[0]);
    private boolean coverEmptySpaceUsingExtraField = true;
    private boolean autoSortFiles;
    private Supplier<VerifyLog> verifyLogFactory = VerifyLogs::devNull;

    public ByteStorageFactory getStorageFactory() {
        return this.storageFactory;
    }

    @Deprecated
    public ByteTracker getTracker() {
        return new ByteTracker();
    }

    public ZFileOptions setStorageFactory(ByteStorageFactory storage) {
        this.storageFactory = storage;
        return this;
    }

    public Compressor getCompressor() {
        return this.compressor;
    }

    public ZFileOptions setCompressor(Compressor compressor) {
        this.compressor = compressor;
        return this;
    }

    public boolean getNoTimestamps() {
        return this.noTimestamps;
    }

    public ZFileOptions setNoTimestamps(boolean noTimestamps) {
        this.noTimestamps = noTimestamps;
        return this;
    }

    public AlignmentRule getAlignmentRule() {
        return this.alignmentRule;
    }

    public ZFileOptions setAlignmentRule(AlignmentRule alignmentRule) {
        this.alignmentRule = alignmentRule;
        return this;
    }

    public boolean getCoverEmptySpaceUsingExtraField() {
        return this.coverEmptySpaceUsingExtraField;
    }

    public ZFileOptions setCoverEmptySpaceUsingExtraField(boolean coverEmptySpaceUsingExtraField) {
        this.coverEmptySpaceUsingExtraField = coverEmptySpaceUsingExtraField;
        return this;
    }

    public boolean getAutoSortFiles() {
        return this.autoSortFiles;
    }

    public ZFileOptions setAutoSortFiles(boolean autoSortFiles) {
        this.autoSortFiles = autoSortFiles;
        return this;
    }

    public ZFileOptions setVerifyLogFactory(Supplier<VerifyLog> verifyLogFactory) {
        this.verifyLogFactory = verifyLogFactory;
        return this;
    }

    public Supplier<VerifyLog> getVerifyLogFactory() {
        return this.verifyLogFactory;
    }
}

