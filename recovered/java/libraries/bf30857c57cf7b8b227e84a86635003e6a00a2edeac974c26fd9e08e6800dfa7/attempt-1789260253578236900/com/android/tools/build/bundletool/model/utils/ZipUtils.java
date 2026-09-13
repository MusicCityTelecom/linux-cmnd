/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;
import com.google.common.io.CountingOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.WillNotClose;

public final class ZipUtils {
    private static final long GZIP_HEADER_SIZE = 10L;

    public static Stream<ZipPath> allFileEntriesPaths(ZipFile zipFile) {
        return ZipUtils.allFileEntries(zipFile).map(zipEntry -> ZipPath.create(zipEntry.getName()));
    }

    public static Stream<? extends ZipEntry> allFileEntries(ZipFile zipFile) {
        return zipFile.stream().filter(Predicates.not(ZipEntry::isDirectory));
    }

    public static ZipFile openZipFile(Path path) {
        FilePreconditions.checkFileExistsAndReadable(path);
        try {
            return new ZipFile(path.toFile());
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error reading zip file '%s'.", path), e2);
        }
    }

    public static long calculateGzipCompressedSize(@WillNotClose InputStream stream) throws IOException {
        CountingOutputStream countingOutputStream = new CountingOutputStream(ByteStreams.nullOutputStream());
        try (GZIPOutputStream compressedStream = new GZIPOutputStream(countingOutputStream);){
            ByteStreams.copy(stream, compressedStream);
        }
        return countingOutputStream.getCount();
    }

    public static ImmutableList<Long> calculateGZipSizeForEntries(ImmutableList<InputStreamSupplier> streams) throws IOException {
        ImmutableList.Builder gzipSizeIncrements = ImmutableList.builder();
        CountingOutputStream countingOutputStream = new CountingOutputStream(ByteStreams.nullOutputStream());
        long lastOffset = 10L;
        try (GZIPOutputStream compressedStream = new GZIPOutputStream((OutputStream)countingOutputStream, true);){
            for (InputStreamSupplier stream : streams) {
                InputStream is = stream.get();
                Throwable throwable = null;
                try {
                    ByteStreams.copy(is, compressedStream);
                    compressedStream.flush();
                    gzipSizeIncrements.add((Object)(countingOutputStream.getCount() - lastOffset));
                    lastOffset = countingOutputStream.getCount();
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (is == null) continue;
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    is.close();
                }
            }
        }
        return gzipSizeIncrements.build();
    }

    public static ZipPath convertBundleToModulePath(ZipPath bundlePath) {
        return bundlePath.subpath(1, bundlePath.getNameCount());
    }

    private ZipUtils() {
    }
}

