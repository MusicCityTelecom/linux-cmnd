/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.internal.util.ByteBufferSink;
import com.android.apksig.internal.util.ChainedDataSource;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class VerityTreeBuilder {
    private static final int CHUNK_SIZE = 4096;
    private static final String JCA_ALGORITHM = "SHA-256";
    private final byte[] mSalt;
    private final MessageDigest mMd;

    public VerityTreeBuilder(byte[] salt) throws NoSuchAlgorithmException {
        this.mSalt = salt;
        this.mMd = MessageDigest.getInstance(JCA_ALGORITHM);
    }

    public byte[] generateVerityTreeRootHash(DataSource beforeApkSigningBlock, DataSource centralDir, DataSource eocd) throws IOException {
        if (beforeApkSigningBlock.size() % 4096L != 0L) {
            throw new IllegalStateException("APK Signing Block size not a multiple of 4096: " + beforeApkSigningBlock.size());
        }
        long centralDirOffsetForDigesting = beforeApkSigningBlock.size();
        ByteBuffer eocdBuf = ByteBuffer.allocate((int)eocd.size());
        eocdBuf.order(ByteOrder.LITTLE_ENDIAN);
        eocd.copyTo(0L, (int)eocd.size(), eocdBuf);
        eocdBuf.flip();
        ZipUtils.setZipEocdCentralDirectoryOffset(eocdBuf, centralDirOffsetForDigesting);
        return this.generateVerityTreeRootHash(new ChainedDataSource(beforeApkSigningBlock, centralDir, DataSources.asDataSource(eocdBuf)));
    }

    byte[] generateVerityTreeRootHash(DataSource fileSource) throws IOException {
        int digestSize = this.mMd.getDigestLength();
        int[] levelOffset = VerityTreeBuilder.calculateLevelOffset(fileSource.size(), digestSize);
        ByteBuffer verityBuffer = ByteBuffer.allocate(levelOffset[levelOffset.length - 1]);
        for (int i2 = levelOffset.length - 2; i2 >= 0; --i2) {
            DataSource src;
            ByteBufferSink middleBufferSink = new ByteBufferSink(VerityTreeBuilder.slice(verityBuffer, levelOffset[i2], levelOffset[i2 + 1]));
            if (i2 == levelOffset.length - 2) {
                src = fileSource;
                this.digestDataByChunks(src, middleBufferSink, false);
            } else {
                src = DataSources.asDataSource(VerityTreeBuilder.slice(verityBuffer.asReadOnlyBuffer(), levelOffset[i2 + 1], levelOffset[i2 + 2]));
                this.digestDataByChunks(src, middleBufferSink, true);
            }
            long totalOutput = VerityTreeBuilder.divideRoundup(src.size(), 4096L) * (long)digestSize;
            int incomplete = (int)(totalOutput % 4096L);
            if (incomplete <= 0) continue;
            byte[] padding = new byte[4096 - incomplete];
            middleBufferSink.consume(padding, 0, padding.length);
        }
        ByteBuffer firstPage = VerityTreeBuilder.slice(verityBuffer.asReadOnlyBuffer(), 0, 4096);
        return this.saltedDigest(firstPage);
    }

    private static int[] calculateLevelOffset(long dataSize, int digestSize) {
        ArrayList<Long> levelSize = new ArrayList<Long>();
        while (true) {
            long chunkCount = VerityTreeBuilder.divideRoundup(dataSize, 4096L);
            long size = 4096L * VerityTreeBuilder.divideRoundup(chunkCount * (long)digestSize, 4096L);
            levelSize.add(size);
            if (chunkCount * (long)digestSize <= 4096L) break;
            dataSize = chunkCount * (long)digestSize;
        }
        int[] levelOffset = new int[levelSize.size() + 1];
        levelOffset[0] = 0;
        for (int i2 = 0; i2 < levelSize.size(); ++i2) {
            levelOffset[i2 + 1] = levelOffset[i2] + Math.toIntExact((Long)levelSize.get(levelSize.size() - i2 - 1));
        }
        return levelOffset;
    }

    private void digestDataByChunks(DataSource dataSource, DataSink dataSink, boolean padLastChunk) throws IOException {
        long size = dataSource.size();
        long offset = 0L;
        while (offset + 4096L <= size) {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            dataSource.copyTo(offset, 4096, buffer);
            buffer.rewind();
            byte[] hash = this.saltedDigest(buffer);
            dataSink.consume(hash, 0, hash.length);
            offset += 4096L;
        }
        int remaining = (int)(size % 4096L);
        if (remaining > 0) {
            ByteBuffer buffer;
            if (padLastChunk) {
                buffer = ByteBuffer.allocate(4096);
                dataSource.copyTo(offset, remaining, buffer);
                buffer.rewind();
            } else {
                buffer = dataSource.getByteBuffer(offset, remaining);
            }
            byte[] hash = this.saltedDigest(buffer);
            dataSink.consume(hash, 0, hash.length);
        }
    }

    private byte[] saltedDigest(ByteBuffer data) {
        this.mMd.reset();
        if (this.mSalt != null) {
            this.mMd.update(this.mSalt);
        }
        this.mMd.update(data);
        return this.mMd.digest();
    }

    private static long divideRoundup(long dividend, long divisor) {
        return (dividend + divisor - 1L) / divisor;
    }

    private static ByteBuffer slice(ByteBuffer buffer, int begin, int end) {
        ByteBuffer b2 = buffer.duplicate();
        b2.position(0);
        b2.limit(end);
        b2.position(begin);
        return b2.slice();
    }
}

