/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.preprocessors;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.files.FileUtils;
import com.android.tools.build.bundletool.preprocessors.AppBundlePreprocessor;
import com.android.utils.ImmutableCollectors;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

public class AppBundleObfuscationPreprocessor
implements AppBundlePreprocessor {
    private static final int RESOURCE_NAME_LENGTH = 6;
    private final Predicate<ZipPath> shouldObfuscate;

    AppBundleObfuscationPreprocessor(Predicate<ZipPath> shouldObfuscate) {
        this.shouldObfuscate = shouldObfuscate;
    }

    @Override
    public AppBundle preprocess(AppBundle originalAppBundle) {
        HashMap<String, String> resourceNameMapping = new HashMap<String, String>();
        AppBundle.Builder newAppBundle = originalAppBundle.toBuilder();
        ImmutableList.Builder obfuscatedBundleModules = ImmutableList.builder();
        for (BundleModule originalModule : AppBundleObfuscationPreprocessor.getSortedBundleModulelist(originalAppBundle)) {
            obfuscatedBundleModules.add(AppBundleObfuscationPreprocessor.obfuscateBundleModule(originalModule, resourceNameMapping, this.shouldObfuscate));
        }
        return newAppBundle.setRawModules(obfuscatedBundleModules.build()).build();
    }

    private static Resources.ResourceTable obfuscateResourceTableEntries(Resources.ResourceTable initialResourceTable, ImmutableMap<String, String> resourceNameMapping) {
        Resources.ResourceTable.Builder modifiedResourceTable = initialResourceTable.toBuilder();
        for (Resources.Package.Builder pkg : modifiedResourceTable.getPackageBuilderList()) {
            for (Resources.Type.Builder type : pkg.getTypeBuilderList()) {
                for (Resources.Entry.Builder entry : type.getEntryBuilderList()) {
                    for (Resources.ConfigValue.Builder config : entry.getConfigValueBuilderList()) {
                        Resources.FileReference.Builder fileReference;
                        String fileReferencePath;
                        Resources.Item.Builder item;
                        Resources.Value.Builder value = config.getValueBuilder();
                        if (value.getValueCase() != Resources.Value.ValueCase.ITEM || (item = value.getItemBuilder()).getValueCase() != Resources.Item.ValueCase.FILE || !resourceNameMapping.containsKey(fileReferencePath = (fileReference = item.getFileBuilder()).getPath())) continue;
                        fileReference.setPath(resourceNameMapping.get(fileReferencePath));
                    }
                }
            }
        }
        return modifiedResourceTable.build();
    }

    private static ImmutableList<ModuleEntry> obfuscateModuleEntries(ImmutableSet<ModuleEntry> toBeObfuscatedEntries, HashMap<String, String> resourceNameMapping) {
        ImmutableList<ModuleEntry> sortedEntries = ImmutableList.sortedCopyOf(Comparator.comparing(ModuleEntry::getPath), toBeObfuscatedEntries);
        ImmutableList.Builder obfuscatedEntries = ImmutableList.builder();
        for (ModuleEntry moduleEntry : sortedEntries) {
            ZipPath newPath = AppBundleObfuscationPreprocessor.obfuscateZipPath(moduleEntry.getPath(), ImmutableMap.copyOf(resourceNameMapping));
            resourceNameMapping.put(moduleEntry.getPath().toString(), newPath.toString());
            ModuleEntry newModuleEntry = moduleEntry.toBuilder().setPath(newPath).build();
            obfuscatedEntries.add(newModuleEntry);
        }
        return obfuscatedEntries.build();
    }

    private static BundleModule obfuscateBundleModule(BundleModule bundleModule, HashMap<String, String> resourceNameMapping, Predicate<ZipPath> shouldObfuscate) {
        ImmutableSet<ModuleEntry> toBeObfuscatedEntries = bundleModule.findEntriesUnderPath(BundleModule.RESOURCES_DIRECTORY).filter(moduleEntry -> shouldObfuscate.test(moduleEntry.getPath())).collect(ImmutableCollectors.toImmutableSet());
        ImmutableList otherEntries = bundleModule.getEntries().stream().filter(moduleEntry -> !toBeObfuscatedEntries.contains(moduleEntry)).collect(ImmutableList.toImmutableList());
        BundleModule.Builder obfuscatedBundleModule = bundleModule.toBuilder().setRawEntries(((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(AppBundleObfuscationPreprocessor.obfuscateModuleEntries(toBeObfuscatedEntries, resourceNameMapping))).addAll((Iterable)otherEntries)).build());
        if (bundleModule.getResourceTable().isPresent()) {
            obfuscatedBundleModule.setResourceTable(AppBundleObfuscationPreprocessor.obfuscateResourceTableEntries(bundleModule.getResourceTable().get(), ImmutableMap.copyOf(resourceNameMapping)));
        }
        return obfuscatedBundleModule.build();
    }

    private static ImmutableList<BundleModule> getSortedBundleModulelist(AppBundle appBundle) {
        return ImmutableList.sortedCopyOf(Comparator.comparing(BundleModule::getName), appBundle.getModules().values());
    }

    private static ZipPath obfuscateZipPath(ZipPath oldZipPath, ImmutableMap<String, String> resourceNameMapping) {
        HashCode hashCode = Hashing.sha256().hashString(oldZipPath.toString(), StandardCharsets.UTF_8);
        String encodedString = Base64.getUrlEncoder().encodeToString(Arrays.copyOf(hashCode.asBytes(), 6));
        while (resourceNameMapping.containsValue("res/" + encodedString)) {
            encodedString = AppBundleObfuscationPreprocessor.handleCollision(hashCode.asBytes());
        }
        String fileExtension = FileUtils.getFileExtension(oldZipPath);
        if (Ascii.equalsIgnoreCase(fileExtension, "xml")) {
            encodedString = encodedString + "." + fileExtension;
        }
        return BundleModule.RESOURCES_DIRECTORY.resolve(encodedString);
    }

    private static String handleCollision(byte[] hashedRepresentation) {
        BigInteger bigInteger = new BigInteger(hashedRepresentation = Arrays.copyOf(hashedRepresentation, 6));
        byte[] newHashedRepresentation = bigInteger.toByteArray();
        if (newHashedRepresentation.length > 6) {
            newHashedRepresentation = new byte[6];
        }
        return Base64.getUrlEncoder().encodeToString(newHashedRepresentation);
    }

    @VisibleForTesting
    String hashFilePath(String stringPath) {
        HashCode hashCode = Hashing.sha256().hashString(stringPath, StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().encodeToString(Arrays.copyOf(hashCode.asBytes(), 6));
    }
}

