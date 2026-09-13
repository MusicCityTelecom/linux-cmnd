/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Commands;
import com.android.tools.build.bundletool.model.utils.ZipUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ApkSizeUtils {
    public static ImmutableMap<String, Long> getCompressedSizeByApkPaths(ImmutableList<Commands.Variant> variants, Path apksArchive) {
        ImmutableList apkPaths = variants.stream().flatMap(variant -> variant.getApkSetList().stream()).flatMap(apkSet -> apkSet.getApkDescriptionList().stream()).map(Commands.ApkDescription::getPath).distinct().collect(ImmutableList.toImmutableList());
        ImmutableMap.Builder<String, Long> sizeByApkPath = ImmutableMap.builder();
        try (ZipFile apksZip = new ZipFile(apksArchive.toFile());){
            for (String apkPath : apkPaths) {
                ZipEntry entry = Preconditions.checkNotNull(apksZip.getEntry(apkPath));
                InputStream inputStream = apksZip.getInputStream(entry);
                Throwable throwable = null;
                try {
                    long size = Math.min(entry.getSize(), ZipUtils.calculateGzipCompressedSize(inputStream));
                    sizeByApkPath.put(apkPath, size);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (inputStream == null) continue;
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    inputStream.close();
                }
            }
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error while processing the APK Set archive '%s'.", apksArchive), e2);
        }
        return sizeByApkPath.build();
    }
}

