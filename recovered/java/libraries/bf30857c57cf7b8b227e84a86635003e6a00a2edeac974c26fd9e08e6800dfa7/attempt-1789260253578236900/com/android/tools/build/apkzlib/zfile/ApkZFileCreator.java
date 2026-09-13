/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zfile;

import com.android.tools.build.apkzlib.zfile.ApkCreator;
import com.android.tools.build.apkzlib.zfile.ApkCreatorFactory;
import com.android.tools.build.apkzlib.zfile.ZFiles;
import com.android.tools.build.apkzlib.zip.AlignmentRule;
import com.android.tools.build.apkzlib.zip.AlignmentRules;
import com.android.tools.build.apkzlib.zip.StoredEntry;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.ZFileOptions;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.io.Closer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

class ApkZFileCreator
implements ApkCreator {
    private static final String NATIVE_LIBRARIES_SUFFIX = ".so";
    private static final AlignmentRule SO_RULE = AlignmentRules.constantForSuffix(".so", 4096);
    private final ZFile zip;
    private boolean closed;
    private final Predicate<String> noCompressPredicate;

    ApkZFileCreator(ApkCreatorFactory.CreationData creationData, ZFileOptions options) throws IOException {
        switch (creationData.getNativeLibrariesPackagingMode()) {
            case COMPRESSED: {
                this.noCompressPredicate = creationData.getNoCompressPredicate();
                break;
            }
            case UNCOMPRESSED_AND_ALIGNED: {
                Predicate<String> baseNoCompressPredicate = creationData.getNoCompressPredicate();
                this.noCompressPredicate = name -> baseNoCompressPredicate.apply((String)name) || name.endsWith(NATIVE_LIBRARIES_SUFFIX);
                options.setAlignmentRule(AlignmentRules.compose(SO_RULE, options.getAlignmentRule()));
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        this.zip = ZFiles.apk(creationData.getApkPath(), options, creationData.getSigningOptions(), creationData.getBuiltBy(), creationData.getCreatedBy());
        this.closed = false;
    }

    @Override
    public void writeZip(File zip2, @Nullable Function<String, String> transform, @Nullable Predicate<String> isIgnored) throws IOException {
        Preconditions.checkState(!this.closed, "closed == true");
        Preconditions.checkArgument(zip2.isFile(), "!zip.isFile()");
        try (Closer closer = Closer.create();){
            ZFile toMerge = closer.register(ZFile.openReadWrite(zip2));
            Predicate<String> ignorePredicate = isIgnored == null ? s3 -> false : isIgnored;
            Predicate noMergePredicate = v3 -> ignorePredicate.apply((String)v3) || this.noCompressPredicate.apply((String)v3);
            this.zip.mergeFrom(toMerge, noMergePredicate);
            for (StoredEntry toMergeEntry : toMerge.entries()) {
                String path = toMergeEntry.getCentralDirectoryHeader().getName();
                if (!this.noCompressPredicate.apply(path) || ignorePredicate.apply(path)) continue;
                InputStream ignoredData = toMergeEntry.open();
                Throwable throwable = null;
                try {
                    this.zip.add(path, ignoredData, false);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (ignoredData == null) continue;
                    if (throwable != null) {
                        try {
                            ignoredData.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    ignoredData.close();
                }
            }
        }
    }

    @Override
    public void writeFile(File inputFile, String apkPath) throws IOException {
        Preconditions.checkState(!this.closed, "closed == true");
        boolean mayCompress = !this.noCompressPredicate.apply(apkPath);
        try (Closer closer = Closer.create();){
            FileInputStream inputFileStream = closer.register(new FileInputStream(inputFile));
            this.zip.add(apkPath, inputFileStream, mayCompress);
        }
    }

    @Override
    public void deleteFile(String apkPath) throws IOException {
        Preconditions.checkState(!this.closed, "closed == true");
        StoredEntry entry = this.zip.get(apkPath);
        if (entry != null) {
            entry.delete();
        }
    }

    @Override
    public boolean hasPendingChangesWithWait() throws IOException {
        return this.zip.hasPendingChangesWithWait();
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.zip.close();
        this.closed = true;
    }
}

