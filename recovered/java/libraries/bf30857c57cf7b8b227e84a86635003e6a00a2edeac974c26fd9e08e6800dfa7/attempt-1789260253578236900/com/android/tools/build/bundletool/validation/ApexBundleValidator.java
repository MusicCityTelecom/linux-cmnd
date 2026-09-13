/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.apex.ApexManifestProto;
import com.android.bundle.Files;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;

public class ApexBundleValidator
extends SubValidator {
    private static final ImmutableList<ZipPath> ALLOWED_APEX_FILES_OUTSIDE_APEX_DIRECTORY = ImmutableList.of(BundleModule.APEX_MANIFEST_PATH, BundleModule.APEX_MANIFEST_JSON_PATH, BundleModule.APEX_NOTICE_PATH, BundleModule.APEX_PUBKEY_PATH);
    private static final ImmutableSet<ImmutableSet<ImmutableSet<AbiName>>> REQUIRED_ONE_OF_ABI_SETS = ImmutableSet.of(ImmutableSet.of(ImmutableSet.of(AbiName.X86)), ImmutableSet.of(ImmutableSet.of(AbiName.ARMEABI_V7A)), ImmutableSet.of(ImmutableSet.of(AbiName.X86_64), ImmutableSet.of(AbiName.X86_64, AbiName.X86)), ImmutableSet.of(ImmutableSet.of(AbiName.ARM64_V8A), ImmutableSet.of(AbiName.ARM64_V8A, AbiName.ARMEABI_V7A)));

    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        long numberOfApexModules = modules.stream().map(BundleModule::getApexConfig).filter(Optional::isPresent).count();
        if (numberOfApexModules == 0L) {
            return;
        }
        if (numberOfApexModules > 1L) {
            throw ValidationException.builder().withMessage("Multiple APEX modules are not allowed, found %d.", numberOfApexModules).build();
        }
        if (modules.size() > 1) {
            throw ValidationException.builder().withMessage("APEX bundles must only contain one module, found %d.", modules.size()).build();
        }
    }

    @Override
    public void validateModule(BundleModule module) {
        if (module.findEntriesUnderPath(BundleModule.APEX_DIRECTORY).count() == 0L) {
            return;
        }
        Optional<ModuleEntry> apexManifest = module.getEntry(BundleModule.APEX_MANIFEST_PATH);
        if (apexManifest.isPresent()) {
            ApexBundleValidator.validateApexManifest(apexManifest.get());
        } else {
            apexManifest = module.getEntry(BundleModule.APEX_MANIFEST_JSON_PATH);
            if (!apexManifest.isPresent()) {
                throw ValidationException.builder().withMessage("Missing expected file in APEX bundle: '%s' or '%s'.", BundleModule.APEX_MANIFEST_PATH, BundleModule.APEX_MANIFEST_JSON_PATH).build();
            }
            ApexBundleValidator.validateApexManifestJson(apexManifest.get());
        }
        ImmutableSet.Builder apexImagesBuilder = ImmutableSet.builder();
        ImmutableSet.Builder apexBuildInfosBuilder = ImmutableSet.builder();
        ImmutableSet.Builder apexFileNamesBuilder = ImmutableSet.builder();
        for (ModuleEntry entry : module.getEntries()) {
            ZipPath path = entry.getPath();
            if (path.startsWith(BundleModule.APEX_DIRECTORY)) {
                if (path.getFileName().toString().endsWith("img")) {
                    apexImagesBuilder.add(path.toString());
                    apexFileNamesBuilder.add(path.getFileName().toString());
                    continue;
                }
                if (path.getFileName().toString().endsWith("build_info.pb")) {
                    apexBuildInfosBuilder.add(path.toString());
                    continue;
                }
                throw ValidationException.builder().withMessage("Unexpected file in apex dirctory of bundle: '%s'.", entry.getPath()).build();
            }
            if (ALLOWED_APEX_FILES_OUTSIDE_APEX_DIRECTORY.contains(path)) continue;
            throw ValidationException.builder().withMessage("Unexpected file in APEX bundle: '%s'.", entry.getPath()).build();
        }
        ImmutableCollection apexBuildInfos = apexBuildInfosBuilder.build();
        ImmutableCollection apexImages = apexImagesBuilder.build();
        ImmutableSet allAbiNameSets = apexFileNamesBuilder.build().stream().map(ApexBundleValidator::abiNamesFromFile).collect(ImmutableSet.toImmutableSet());
        if (allAbiNameSets.size() != apexImages.size()) {
            throw ValidationException.builder().withMessage("Every APEX image file must target a unique set of architectures, but found multiple files that target the same set of architectures.").build();
        }
        if (!apexBuildInfos.isEmpty() && !apexBuildInfos.stream().map(f2 -> f2.replace("build_info.pb", "img")).collect(ImmutableSet.toImmutableSet()).equals(apexImages)) {
            throw ValidationException.builder().withMessage("If APEX build info is provided then one must be provided for each APEX image file.").build();
        }
        if (REQUIRED_ONE_OF_ABI_SETS.stream().anyMatch(one_of -> one_of.stream().noneMatch(allAbiNameSets::contains))) {
            throw ValidationException.builder().withMessage("APEX bundle must contain one of %s.", Joiner.on(" and one of ").join(REQUIRED_ONE_OF_ABI_SETS)).build();
        }
        module.getApexConfig().ifPresent(arg_0 -> ApexBundleValidator.lambda$validateModule$2((ImmutableSet)apexImages, arg_0));
    }

    private static ImmutableSet<AbiName> abiNamesFromFile(String fileName) {
        ImmutableList<String> tokens = ImmutableList.copyOf(BundleModule.ABI_SPLITTER.splitToList(fileName));
        return tokens.stream().limit(tokens.size() - 1).map(AbiName::fromPlatformName).map(Optional::get).collect(ImmutableSet.toImmutableSet());
    }

    private static void validateTargeting(ImmutableSet<String> allImages, Files.ApexImages targeting) {
        ImmutableSet targetedImages = targeting.getImageList().stream().map(Files.TargetedApexImage::getPath).collect(ImmutableSet.toImmutableSet());
        ImmutableSet<String> untargetedImages = Sets.difference(allImages, targetedImages).immutableCopy();
        if (!untargetedImages.isEmpty()) {
            throw ValidationException.builder().withMessage("Found APEX image files that are not targeted: %s", untargetedImages).build();
        }
        ImmutableSet missingTargetedImages = Sets.difference(targetedImages, allImages).immutableCopy();
        if (!missingTargetedImages.isEmpty()) {
            throw ValidationException.builder().withMessage("Targeted APEX image files are missing: %s", missingTargetedImages).build();
        }
    }

    private static void validateApexManifest(ModuleEntry entry) {
        try (InputStream inputStream = entry.getContent();){
            ApexManifestProto.ApexManifest apexManifest = ApexManifestProto.ApexManifest.parseFrom(inputStream, (ExtensionRegistryLite)ExtensionRegistry.getEmptyRegistry());
            if (apexManifest.getName().isEmpty()) {
                throw ValidationException.builder().withMessage("APEX manifest must have a package name.").build();
            }
        }
        catch (InvalidProtocolBufferException e2) {
            throw ValidationException.builder().withMessage("Couldn't parse APEX manifest").withCause(e2).build();
        }
        catch (IOException e3) {
            throw new UncheckedIOException("Couldn't read APEX manifest.", e3);
        }
    }

    private static void validateApexManifestJson(ModuleEntry entry) {
        try (InputStream inputStream = entry.getContent();
             BufferedReader reader = BufferedIo.reader(inputStream);){
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            JsonElement element = json.get("name");
            if (element == null || element.getAsString().isEmpty()) {
                throw ValidationException.builder().withMessage("APEX manifest must have a package name.").build();
            }
        }
        catch (IOException e2) {
            throw new UncheckedIOException("Couldn't read APEX manifest.", e2);
        }
    }

    private static /* synthetic */ void lambda$validateModule$2(ImmutableSet apexImages, Files.ApexImages targeting) {
        ApexBundleValidator.validateTargeting(apexImages, targeting);
    }
}

