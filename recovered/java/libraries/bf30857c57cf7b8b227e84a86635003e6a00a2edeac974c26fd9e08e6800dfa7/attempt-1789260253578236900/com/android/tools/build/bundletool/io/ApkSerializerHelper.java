/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.apksig.ApkSigner;
import com.android.apksig.apk.ApkFormatException;
import com.android.bundle.Config;
import com.android.tools.build.apkzlib.zfile.ZFiles;
import com.android.tools.build.apkzlib.zip.AlignmentRule;
import com.android.tools.build.apkzlib.zip.AlignmentRules;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.ZFileOptions;
import com.android.tools.build.bundletool.io.TempDirectory;
import com.android.tools.build.bundletool.io.ZipBuilder;
import com.android.tools.build.bundletool.model.Aapt2Command;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.WearApkLocator;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.PathMatcher;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.build.bundletool.model.utils.files.FileUtils;
import com.android.tools.build.bundletool.model.version.Version;
import com.android.tools.build.bundletool.model.version.VersionGuardedFeature;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.ByteStreams;
import com.google.protobuf.MessageLite;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

final class ApkSerializerHelper {
    private static final String NATIVE_LIBRARIES_SUFFIX = ".so";
    private static final Pattern NATIVE_LIBRARIES_PATTERN = Pattern.compile("lib/[^/]+/[^/]+\\.so");
    private static final String SIGNER_CONFIG_NAME = "BNDLTOOL";
    static final AlignmentRule APK_ALIGNMENT_RULE = AlignmentRules.compose(AlignmentRules.constantForSuffix(".so", 4096), AlignmentRules.constant(4));
    private static final Predicate<ZipPath> FILES_FOR_AAPT2 = path -> path.startsWith("res") || path.equals(BundleModule.SpecialModuleEntry.RESOURCE_TABLE.getPath()) || path.equals(ZipPath.create("AndroidManifest.xml"));
    private static final String BUILT_BY = "BundleTool";
    private static final String CREATED_BY = "BundleTool";
    private static final ImmutableSet<String> NO_COMPRESSION_EXTENSIONS = ImmutableSet.of("3g2", "3gp", "3gpp", "3gpp2", "aac", "amr", new String[]{"awb", "gif", "imy", "jet", "jpeg", "jpg", "m4a", "m4v", "mid", "midi", "mkv", "mp2", "mp3", "mp4", "mpeg", "mpg", "ogg", "png", "rtttl", "smf", "wav", "webm", "wma", "wmv", "xmf"});
    private final Aapt2Command aapt2Command;
    private final Version bundleVersion;
    private final Optional<SigningConfiguration> signingConfig;
    private final Optional<SigningConfiguration> stampSigningConfig;
    private final ImmutableList<PathMatcher> uncompressedPathMatchers;

    ApkSerializerHelper(Aapt2Command aapt2Command, Optional<SigningConfiguration> signingConfig, Optional<SigningConfiguration> stampSigningConfig, Version bundleVersion, Config.Compression compression) {
        this.aapt2Command = aapt2Command;
        this.bundleVersion = bundleVersion;
        this.signingConfig = signingConfig;
        this.stampSigningConfig = stampSigningConfig;
        this.uncompressedPathMatchers = compression.getUncompressedGlobList().stream().map(PathMatcher::createFromGlob).collect(ImmutableList.toImmutableList());
    }

    Path writeToZipFile(ModuleSplit split, Path outputPath) {
        try (TempDirectory tempDirectory = new TempDirectory();){
            this.writeToZipFile(split, outputPath, tempDirectory.getPath());
        }
        return outputPath;
    }

    private void writeToZipFile(ModuleSplit split, Path outputPath, Path tempDir) {
        FilePreconditions.checkFileDoesNotExist(outputPath);
        FileUtils.createParentDirectories(outputPath);
        Path partialProtoApk = tempDir.resolve("proto.apk");
        this.writeProtoApk(split, partialProtoApk, tempDir);
        Path binaryApk = tempDir.resolve("binary.apk");
        this.aapt2Command.convertApkProtoToBinary(partialProtoApk, binaryApk);
        Preconditions.checkState(Files.exists(binaryApk, new LinkOption[0]), "No APK created by aapt2 convert command.");
        Path unsignedApkPath = tempDir.resolve("apk-unsigned.apk");
        try (ZFile zOutputApk = ZFiles.apk(unsignedApkPath.toFile(), ApkSerializerHelper.createZFileOptions(tempDir).setAlignmentRule(APK_ALIGNMENT_RULE).setCoverEmptySpaceUsingExtraField(true).setNoTimestamps(true), com.google.common.base.Optional.absent(), "BundleTool", "BundleTool");
             ZFile zAapt2Files = new ZFile(binaryApk.toFile(), ApkSerializerHelper.createZFileOptions(tempDir), true);){
            zOutputApk.mergeFrom(zAapt2Files, Predicates.alwaysFalse());
            this.addNonAapt2Files(zOutputApk, split);
            zOutputApk.sortZipContents();
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Failed to write APK file '%s'.", unsignedApkPath), e2);
        }
        boolean signWithV1 = split.getAndroidManifest().getEffectiveMinSdkVersion() < 24 || !VersionGuardedFeature.NO_V1_SIGNING_WHEN_POSSIBLE.enabledForVersion(this.bundleVersion);
        int minSdkVersion = split.getAndroidManifest().getEffectiveMinSdkVersion();
        if (this.signingConfig.isPresent()) {
            ApkSerializerHelper.signApk(unsignedApkPath, outputPath, this.signingConfig.get(), this.stampSigningConfig, signWithV1, minSdkVersion);
        } else {
            try {
                Files.move(unsignedApkPath, outputPath, new CopyOption[0]);
            }
            catch (IOException e3) {
                throw new UncheckedIOException(String.format("Failed to write APK file '%s'.", outputPath), e3);
            }
        }
    }

    Path writeCompressedApkToZipFile(ModuleSplit split, Path outputPath) {
        try (TempDirectory tempDirectory = new TempDirectory();){
            Path tempApkOutputPath = tempDirectory.getPath().resolve("output.apk");
            this.writeToZipFile(split, tempApkOutputPath, tempDirectory.getPath());
            this.writeCompressedApkToZipFile(tempApkOutputPath, outputPath);
        }
        return outputPath;
    }

    private void writeCompressedApkToZipFile(Path apkPath, Path outputApkGzipPath) {
        FilePreconditions.checkFileDoesNotExist(outputApkGzipPath);
        FileUtils.createParentDirectories(outputApkGzipPath);
        try (FileInputStream fileInputStream = new FileInputStream(apkPath.toFile());
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(outputApkGzipPath.toFile()));){
            ByteStreams.copy(fileInputStream, gzipOutputStream);
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Failed to write APK file '%s' to compressed APK file '%s'.", apkPath, outputApkGzipPath), e2);
        }
    }

    private Path writeProtoApk(ModuleSplit split, Path outputPath, Path tempDir) {
        boolean extractNativeLibs = split.getAndroidManifest().getExtractNativeLibsValue().orElse(true);
        Optional<ZipPath> wear1ApkPath = WearApkLocator.findEmbeddedWearApkPath(split);
        ZipBuilder zipBuilder = new ZipBuilder();
        for (ModuleEntry entry : split.getEntries()) {
            ZipPath pathInApk = this.toApkEntryPath(entry.getPath());
            if (!FILES_FOR_AAPT2.apply(pathInApk)) continue;
            ZipBuilder.EntryOption[] entryOptions = this.entryOptionForPath(pathInApk, !extractNativeLibs, entry.getShouldCompress());
            if (this.signingConfig.isPresent() && wear1ApkPath.isPresent() && wear1ApkPath.get().equals(pathInApk)) {
                Path signedWearApk = ApkSerializerHelper.signWearApk(entry, this.signingConfig.get(), tempDir);
                zipBuilder.addFileFromDisk(pathInApk, signedWearApk.toFile(), entryOptions);
                continue;
            }
            zipBuilder.addFile(pathInApk, entry.getContentSupplier(), entryOptions);
        }
        split.getResourceTable().ifPresent(resourceTable -> zipBuilder.addFileWithProtoContent(BundleModule.SpecialModuleEntry.RESOURCE_TABLE.getPath(), (MessageLite)resourceTable, new ZipBuilder.EntryOption[0]));
        zipBuilder.addFileWithProtoContent(ZipPath.create("AndroidManifest.xml"), split.getAndroidManifest().getManifestRoot().getProto(), new ZipBuilder.EntryOption[0]);
        try {
            zipBuilder.writeTo(outputPath);
        }
        catch (IOException e2) {
            throw new UncheckedIOException(String.format("Error while writing APK to file '%s'.", outputPath), e2);
        }
        return outputPath;
    }

    private ZipBuilder.EntryOption[] entryOptionForPath(ZipPath path, boolean uncompressNativeLibs, boolean entryShouldCompress) {
        if (this.shouldCompress(path, uncompressNativeLibs, entryShouldCompress)) {
            return new ZipBuilder.EntryOption[0];
        }
        return new ZipBuilder.EntryOption[]{ZipBuilder.EntryOption.UNCOMPRESSED};
    }

    private boolean shouldCompress(ZipPath path, boolean uncompressNativeLibs, boolean entryShouldCompress) {
        if (this.uncompressedPathMatchers.stream().anyMatch(pathMatcher -> pathMatcher.matches(path.toString()))) {
            return false;
        }
        if (!entryShouldCompress) {
            return false;
        }
        if (!VersionGuardedFeature.NO_DEFAULT_UNCOMPRESS_EXTENSIONS.enabledForVersion(this.bundleVersion) && NO_COMPRESSION_EXTENSIONS.contains(FileUtils.getFileExtension(path))) {
            return false;
        }
        return !uncompressNativeLibs || !NATIVE_LIBRARIES_PATTERN.matcher(path.toString()).matches();
    }

    private void addNonAapt2Files(ZFile zFile, ModuleSplit split) throws IOException {
        boolean extractNativeLibs = split.getAndroidManifest().getExtractNativeLibsValue().orElse(true);
        for (ModuleEntry entry : split.getEntries()) {
            ZipPath pathInApk = this.toApkEntryPath(entry.getPath());
            if (FILES_FOR_AAPT2.apply(pathInApk)) continue;
            InputStream entryInputStream = entry.getContent();
            Throwable throwable = null;
            try {
                zFile.add(pathInApk.toString(), entryInputStream, this.shouldCompress(pathInApk, !extractNativeLibs, entry.getShouldCompress()));
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (entryInputStream == null) continue;
                if (throwable != null) {
                    try {
                        entryInputStream.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                entryInputStream.close();
            }
        }
    }

    private ZipPath toApkEntryPath(ZipPath pathInModule) {
        if (pathInModule.startsWith(BundleModule.DEX_DIRECTORY)) {
            Preconditions.checkArgument(pathInModule.getNameCount() == 2, "Only files directly in the dex directory are supported but found: %s.", (Object)pathInModule);
            FilePreconditions.checkFileHasExtension("File under dex/ directory", pathInModule, ".dex");
            return pathInModule.getFileName();
        }
        if (pathInModule.startsWith(BundleModule.ROOT_DIRECTORY)) {
            Preconditions.checkArgument(pathInModule.getNameCount() >= 2, "Only files inside the root directory are supported but found: %s", (Object)pathInModule);
            return pathInModule.subpath(1, pathInModule.getNameCount());
        }
        if (pathInModule.startsWith(BundleModule.APEX_DIRECTORY)) {
            Preconditions.checkArgument(pathInModule.getNameCount() >= 2, "Only files inside the apex directory are supported but found: %s", (Object)pathInModule);
            Preconditions.checkArgument(pathInModule.toString().endsWith("img") || pathInModule.toString().endsWith("build_info.pb"), "Unexpected filename in apex directory: %s", (Object)pathInModule);
            if (pathInModule.toString().endsWith("img")) {
                return ZipPath.create("apex_payload.img");
            }
            return ZipPath.create("apex_build_info.pb");
        }
        return pathInModule;
    }

    private static Path signWearApk(ModuleEntry wearApkEntry, SigningConfiguration signingConfig, Path tempDir) {
        try {
            ApkSigner.SignerConfig signerConfig = new ApkSigner.SignerConfig.Builder(SIGNER_CONFIG_NAME, signingConfig.getPrivateKey(), signingConfig.getCertificates()).build();
            Path unsignedApk = tempDir.resolve("wear-unsigned.apk");
            try (InputStream entryContent = wearApkEntry.getContent();){
                Files.copy(entryContent, unsignedApk, new CopyOption[0]);
            }
            Path signedApk = tempDir.resolve("wear-signed.apk");
            ApkSigner apkSigner = new ApkSigner.Builder(ImmutableList.of(signerConfig)).setInputApk(unsignedApk.toFile()).setOutputApk(signedApk.toFile()).build();
            apkSigner.sign();
            return signedApk;
        }
        catch (ApkFormatException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e2) {
            throw new ValidationException("Unable to sign the embedded Wear APK.", e2);
        }
        catch (IOException e3) {
            throw new UncheckedIOException("Unable to sign the embedded Wear APK.", e3);
        }
    }

    private static ZFileOptions createZFileOptions(Path tempDir) {
        ZFileOptions options = new ZFileOptions();
        return options;
    }

    private static void signApk(Path inputApkPath, Path outputApkPath, SigningConfiguration signingConfiguration, Optional<SigningConfiguration> stampSigningConfiguration, boolean signWithV1, int minSdkVersion) {
        try {
            ApkSigner.SignerConfig signerConfig = new ApkSigner.SignerConfig.Builder(SIGNER_CONFIG_NAME, signingConfiguration.getPrivateKey(), signingConfiguration.getCertificates()).build();
            ApkSigner.Builder apkSigner = new ApkSigner.Builder(ImmutableList.of(signerConfig)).setInputApk(inputApkPath.toFile()).setV1SigningEnabled(signWithV1).setV2SigningEnabled(true).setOtherSignersSignaturesPreserved(false).setMinSdkVersion(minSdkVersion).setOutputApk(outputApkPath.toFile());
            apkSigner.build().sign();
        }
        catch (ApkFormatException | IOException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e2) {
            throw new ValidationException("Unable to sign APK.", e2);
        }
    }
}

