/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Config;
import com.android.bundle.Files;
import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectory;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectorySegment;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.model.utils.TargetingProtoUtils;
import com.android.tools.build.bundletool.model.utils.files.FileUtils;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TargetingGenerator {
    private static final String ASSETS_DIR = "assets/";
    private static final String LIB_DIR = "lib/";

    public Files.Assets generateTargetingForAssets(Collection<ZipPath> assetDirectories) {
        for (ZipPath zipPath : assetDirectories) {
            TargetingGenerator.checkRootDirectoryName(ASSETS_DIR, zipPath + "/");
        }
        HashMultimap<String, Targeting.AssetsDirectoryTargeting> targetingByBaseName = HashMultimap.create();
        for (ZipPath assetDirectory : FileUtils.toPathWalkingOrder(assetDirectories)) {
            TargetedDirectory targetedDirectory = TargetedDirectory.parse(assetDirectory);
            targetingByBaseName.put((Object)targetedDirectory.getPathBaseName(), (Object)targetedDirectory.getLastSegment().getTargeting());
        }
        this.validateDimensions(targetingByBaseName);
        Files.Assets.Builder builder2 = Files.Assets.newBuilder();
        for (ZipPath assetDirectory : assetDirectories) {
            Targeting.AssetsDirectoryTargeting.Builder targeting = Targeting.AssetsDirectoryTargeting.newBuilder();
            TargetedDirectory targetedDirectory = TargetedDirectory.parse(assetDirectory);
            for (int i2 = 0; i2 < targetedDirectory.getPathSegments().size(); ++i2) {
                TargetedDirectorySegment segment = (TargetedDirectorySegment)targetedDirectory.getPathSegments().get(i2);
                targeting.mergeFrom(segment.getTargeting());
                if (segment.getTargeting().hasLanguage()) continue;
                targeting.mergeFrom(Sets.difference(targetingByBaseName.get((Object)targetedDirectory.getSubPathBaseName(i2)), ImmutableSet.of(segment.getTargeting())).stream().map(TargetingProtoUtils::toAlternativeTargeting).reduce(Targeting.AssetsDirectoryTargeting.newBuilder(), (builder, targetingValue) -> builder.mergeFrom((Targeting.AssetsDirectoryTargeting)targetingValue), (builderA, builderB) -> builderA.mergeFrom(builderB.build())).build());
            }
            builder2.addDirectory(Files.TargetedAssetsDirectory.newBuilder().setPath(assetDirectory.toString()).setTargeting(targeting));
        }
        return builder2.build();
    }

    private void validateDimensions(Multimap<String, Targeting.AssetsDirectoryTargeting> targetingMultimap) {
        for (String baseName : targetingMultimap.keySet()) {
            ImmutableList<TargetingDimension> distinctDimensions = targetingMultimap.get(baseName).stream().map(TargetingUtils::getTargetingDimensions).flatMap(Collection::stream).distinct().collect(ImmutableList.toImmutableList());
            if (distinctDimensions.size() <= 1) continue;
            throw ValidationException.builder().withMessage("Expected at most one dimension type used for targeting of '%s'. However, the following dimensions were used: %s.", baseName, TargetingGenerator.joinDimensions(distinctDimensions)).build();
        }
    }

    private static String joinDimensions(ImmutableList<TargetingDimension> dimensions) {
        return dimensions.stream().map(dimension -> String.format("'%s'", dimension)).sorted().collect(Collectors.joining(", "));
    }

    public Files.NativeLibraries generateTargetingForNativeLibraries(Collection<String> libDirectories) {
        Files.NativeLibraries.Builder nativeLibraries = Files.NativeLibraries.newBuilder();
        for (String directory : libDirectories) {
            TargetingGenerator.checkRootDirectoryName(LIB_DIR, directory);
            String subDirName = directory.substring(LIB_DIR.length());
            Targeting.Abi abi = TargetingGenerator.checkAbiName(subDirName, directory);
            Targeting.NativeDirectoryTargeting.Builder nativeBuilder = Targeting.NativeDirectoryTargeting.newBuilder().setAbi(abi);
            if (subDirName.equals("arm64-v8a-hwasan")) {
                nativeBuilder.setSanitizer(Targeting.Sanitizer.newBuilder().setAlias(Targeting.Sanitizer.SanitizerAlias.HWADDRESS));
            }
            nativeLibraries.addDirectory(Files.TargetedNativeDirectory.newBuilder().setPath(directory).setTargeting(nativeBuilder).build());
        }
        return nativeLibraries.build();
    }

    public Files.ApexImages generateTargetingForApexImages(Config.ApexConfig apexConfig, Collection<ZipPath> apexImageFiles, boolean hasBuildInfo) {
        ImmutableMap<ZipPath, Targeting.MultiAbi> targetingByPath = Maps.toMap(apexImageFiles, path -> TargetingGenerator.buildMultiAbi(path.getFileName().toString()));
        Files.ApexImages.Builder apexImages = Files.ApexImages.newBuilder().addAllApexEmbeddedApkConfig(apexConfig.getApexEmbeddedApkConfigList());
        ImmutableSet allTargeting = ImmutableSet.copyOf(targetingByPath.values());
        targetingByPath.forEach((imagePath, targeting) -> apexImages.addImage(Files.TargetedApexImage.newBuilder().setPath(imagePath.toString()).setBuildInfoPath(hasBuildInfo ? imagePath.toString().replace("img", "build_info.pb") : "").setTargeting(TargetingGenerator.buildApexTargetingWithAlternatives(targeting, allTargeting))));
        return apexImages.build();
    }

    private static Targeting.MultiAbi buildMultiAbi(String fileName) {
        ImmutableList<String> tokens = ImmutableList.copyOf(BundleModule.ABI_SPLITTER.splitToList(fileName));
        int nAbis = tokens.size() - 1;
        Preconditions.checkState(((String)tokens.get(nAbis)).equals("img"), "File under 'apex/' does not have suffix 'img'");
        return Targeting.MultiAbi.newBuilder().addAllAbi(tokens.stream().limit(nAbis).map(token -> TargetingGenerator.checkAbiName(token, fileName)).collect(ImmutableList.toImmutableList())).build();
    }

    private static Targeting.ApexImageTargeting buildApexTargetingWithAlternatives(Targeting.MultiAbi targeting, Set<Targeting.MultiAbi> allTargeting) {
        return Targeting.ApexImageTargeting.newBuilder().setMultiAbi(Targeting.MultiAbiTargeting.newBuilder().addValue(targeting).addAllAlternatives(Sets.difference(allTargeting, ImmutableSet.of(targeting)).immutableCopy())).build();
    }

    private static String checkRootDirectoryName(String rootName, String forDirectory) {
        Preconditions.checkArgument(rootName.endsWith("/"), "'%s' does not end with '/'.", (Object)rootName);
        Preconditions.checkArgument(forDirectory.startsWith(rootName), "Directory '%s' must start with '%s'.", (Object)forDirectory, (Object)rootName);
        return rootName;
    }

    private static Targeting.Abi checkAbiName(String token, String forFileOrDirectory) {
        Optional<AbiName> abiName = AbiName.fromLibSubDirName(token);
        if (!abiName.isPresent()) {
            Optional<AbiName> abiNameLowerCase = AbiName.fromLibSubDirName(token.toLowerCase());
            if (abiNameLowerCase.isPresent()) {
                throw ValidationException.builder().withMessage("Expecting ABI name in file or directory '%s', but found '%s' which is not recognized. Did you mean '%s'?", forFileOrDirectory, token, Ascii.toLowerCase(token)).build();
            }
            throw ValidationException.builder().withMessage("Expecting ABI name in file or directory '%s', but found '%s' which is not recognized.", forFileOrDirectory, token).build();
        }
        return Targeting.Abi.newBuilder().setAlias(abiName.get().toProto()).build();
    }
}

