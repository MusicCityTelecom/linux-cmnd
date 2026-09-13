/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.CentralDirectoryHeader;
import com.android.tools.build.apkzlib.zip.CompressionMethod;

public class CentralDirectoryHeaderCompressInfo {
    public static final long VERSION_WITH_STORE_FILES_ONLY = 10L;
    public static final long VERSION_WITH_DIRECTORIES_AND_DEFLATE = 20L;
    public static final long VERSION_WITH_ZIP64_EXTENSIONS = 45L;
    public static final long VERSION_WITH_CENTRAL_FILE_ENCRYPTION = 62L;
    private final CompressionMethod method;
    private final long compressedSize;
    private final long versionExtract;

    public CentralDirectoryHeaderCompressInfo(CompressionMethod method, long compressedSize, long versionToExtract) {
        this.method = method;
        this.compressedSize = compressedSize;
        this.versionExtract = versionToExtract;
    }

    public CentralDirectoryHeaderCompressInfo(CentralDirectoryHeader header, CompressionMethod method, long compressedSize) {
        this.method = method;
        this.compressedSize = compressedSize;
        this.versionExtract = header.getName().endsWith("/") || method == CompressionMethod.DEFLATE ? 20L : 10L;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public CompressionMethod getMethod() {
        return this.method;
    }

    long getVersionExtract() {
        return this.versionExtract;
    }
}

