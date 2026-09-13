/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.bundle.Commands;
import com.android.tools.build.bundletool.io.SplitApkSerializer;
import com.android.tools.build.bundletool.io.StandaloneApkSerializer;
import com.android.tools.build.bundletool.io.ZipBuilder;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Message;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public final class ApkSetBuilderFactory {
    public static ApkSetBuilder createApkSetBuilder(SplitApkSerializer splitApkSerializer, StandaloneApkSerializer standaloneApkSerializer, Path tempDir) {
        return new ApkSetArchiveBuilder(splitApkSerializer, standaloneApkSerializer, tempDir);
    }

    public static ApkSetBuilder createApkSetWithoutArchiveBuilder(SplitApkSerializer splitApkSerializer, StandaloneApkSerializer standaloneApkSerializer, Path outputDir) {
        return new ApkSetWithoutArchiveBuilder(splitApkSerializer, standaloneApkSerializer, outputDir);
    }

    public static class ApkSetWithoutArchiveBuilder
    implements ApkSetBuilder {
        private final SplitApkSerializer splitApkSerializer;
        private final StandaloneApkSerializer standaloneApkSerializer;
        private final Path outputDirectory;

        public ApkSetWithoutArchiveBuilder(SplitApkSerializer splitApkSerializer, StandaloneApkSerializer standaloneApkSerializer, Path outputDirectory) {
            this.outputDirectory = outputDirectory;
            this.splitApkSerializer = splitApkSerializer;
            this.standaloneApkSerializer = standaloneApkSerializer;
        }

        @Override
        public Commands.ApkDescription addInstantApk(ModuleSplit split) {
            return this.splitApkSerializer.writeInstantSplitToDisk(split, this.outputDirectory);
        }

        @Override
        public Commands.ApkDescription addSplitApk(ModuleSplit split) {
            return this.splitApkSerializer.writeSplitToDisk(split, this.outputDirectory);
        }

        @Override
        public Commands.ApkDescription addAssetSliceApk(ModuleSplit split) {
            return this.splitApkSerializer.writeAssetSliceToDisk(split, this.outputDirectory);
        }

        @Override
        public Commands.ApkDescription addStandaloneApk(ModuleSplit split) {
            return this.standaloneApkSerializer.writeToDisk(split, this.outputDirectory);
        }

        @Override
        public Commands.ApkDescription addStandaloneUniversalApk(ModuleSplit split) {
            return this.standaloneApkSerializer.writeToDiskAsUniversal(split, this.outputDirectory);
        }

        @Override
        public Commands.ApkDescription addSystemApk(ModuleSplit split) {
            return this.standaloneApkSerializer.writeSystemApkToDisk(split, this.outputDirectory);
        }

        @Override
        public ImmutableList<Commands.ApkDescription> addCompressedSystemApks(ModuleSplit split) {
            return this.standaloneApkSerializer.writeCompressedSystemApksToDisk(split, this.outputDirectory);
        }

        @Override
        public void setTableOfContentsFile(Commands.BuildApksResult tableOfContentsProto) {
            this.writeProtoFile(tableOfContentsProto, this.outputDirectory.resolve("toc.pb"));
        }

        @Override
        public void writeTo(Path destinationPath) {
        }

        private void writeProtoFile(Message proto, Path outputFile) {
            try (OutputStream outputStream = BufferedIo.outputStream(outputFile);){
                proto.writeTo(outputStream);
            }
            catch (FileNotFoundException e2) {
                throw new UncheckedIOException(String.format("Can't create the output file '%s'.", outputFile), e2);
            }
            catch (IOException e3) {
                throw new UncheckedIOException(String.format("Error while writing the output file '%s'.", outputFile), e3);
            }
        }
    }

    public static class ApkSetArchiveBuilder
    implements ApkSetBuilder {
        private final SplitApkSerializer splitApkSerializer;
        private final StandaloneApkSerializer standaloneApkSerializer;
        private final ZipBuilder apkSetZipBuilder;
        private final Path tempDirectory;

        public ApkSetArchiveBuilder(SplitApkSerializer splitApkSerializer, StandaloneApkSerializer standaloneApkSerializer, Path tempDirectory) {
            this.splitApkSerializer = splitApkSerializer;
            this.standaloneApkSerializer = standaloneApkSerializer;
            this.tempDirectory = tempDirectory;
            this.apkSetZipBuilder = new ZipBuilder();
        }

        @Override
        public Commands.ApkDescription addSplitApk(ModuleSplit split) {
            Commands.ApkDescription apkDescription = this.splitApkSerializer.writeSplitToDisk(split, this.tempDirectory);
            this.addToApkSetArchive(apkDescription.getPath());
            return apkDescription;
        }

        @Override
        public Commands.ApkDescription addInstantApk(ModuleSplit split) {
            Commands.ApkDescription apkDescription = this.splitApkSerializer.writeInstantSplitToDisk(split, this.tempDirectory);
            this.addToApkSetArchive(apkDescription.getPath());
            return apkDescription;
        }

        @Override
        public Commands.ApkDescription addAssetSliceApk(ModuleSplit split) {
            Commands.ApkDescription apkDescription = this.splitApkSerializer.writeAssetSliceToDisk(split, this.tempDirectory);
            this.addToApkSetArchive(apkDescription.getPath());
            return apkDescription;
        }

        @Override
        public Commands.ApkDescription addStandaloneApk(ModuleSplit split) {
            Commands.ApkDescription apkDescription = this.standaloneApkSerializer.writeToDisk(split, this.tempDirectory);
            this.addToApkSetArchive(apkDescription.getPath());
            return apkDescription;
        }

        @Override
        public Commands.ApkDescription addStandaloneUniversalApk(ModuleSplit split) {
            Commands.ApkDescription apkDescription = this.standaloneApkSerializer.writeToDiskAsUniversal(split, this.tempDirectory);
            this.addToApkSetArchive(apkDescription.getPath());
            return apkDescription;
        }

        @Override
        public Commands.ApkDescription addSystemApk(ModuleSplit split) {
            Commands.ApkDescription apkDescription = this.standaloneApkSerializer.writeSystemApkToDisk(split, this.tempDirectory);
            this.addToApkSetArchive(apkDescription.getPath());
            return apkDescription;
        }

        @Override
        public ImmutableList<Commands.ApkDescription> addCompressedSystemApks(ModuleSplit split) {
            ImmutableList<Commands.ApkDescription> apkDescriptions = this.standaloneApkSerializer.writeCompressedSystemApksToDisk(split, this.tempDirectory);
            apkDescriptions.forEach(apkDescription -> this.addToApkSetArchive(apkDescription.getPath()));
            return apkDescriptions;
        }

        private void addToApkSetArchive(String relativeApkPath) {
            Path fullApkPath = this.tempDirectory.resolve(relativeApkPath);
            FilePreconditions.checkFileExistsAndReadable(fullApkPath);
            this.apkSetZipBuilder.addFileFromDisk(ZipPath.create(relativeApkPath), fullApkPath.toFile(), ZipBuilder.EntryOption.UNCOMPRESSED);
        }

        @Override
        public void setTableOfContentsFile(Commands.BuildApksResult tableOfContentsProto) {
            this.apkSetZipBuilder.addFileWithProtoContent(ZipPath.create("toc.pb"), tableOfContentsProto, new ZipBuilder.EntryOption[0]);
        }

        @Override
        public void writeTo(Path destinationPath) {
            try {
                this.apkSetZipBuilder.writeTo(destinationPath);
            }
            catch (IOException e2) {
                throw new UncheckedIOException(String.format("Error while writing the APK Set archive to '%s'.", destinationPath), e2);
            }
        }
    }

    public static interface ApkSetBuilder {
        public Commands.ApkDescription addSplitApk(ModuleSplit var1);

        public Commands.ApkDescription addStandaloneApk(ModuleSplit var1);

        public Commands.ApkDescription addStandaloneUniversalApk(ModuleSplit var1);

        public Commands.ApkDescription addInstantApk(ModuleSplit var1);

        public Commands.ApkDescription addSystemApk(ModuleSplit var1);

        public Commands.ApkDescription addAssetSliceApk(ModuleSplit var1);

        public ImmutableList<Commands.ApkDescription> addCompressedSystemApks(ModuleSplit var1);

        public void setTableOfContentsFile(Commands.BuildApksResult var1);

        public void writeTo(Path var1);
    }
}

