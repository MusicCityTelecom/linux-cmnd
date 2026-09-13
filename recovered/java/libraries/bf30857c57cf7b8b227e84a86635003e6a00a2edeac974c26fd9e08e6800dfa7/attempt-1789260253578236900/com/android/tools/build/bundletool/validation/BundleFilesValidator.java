/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.BundleFileTypesException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.regex.Pattern;

public class BundleFilesValidator
extends SubValidator {
    private static final Pattern CLASSES_DEX_PATTERN = Pattern.compile("classes[0-9]*\\.dex");
    private static final ImmutableSet<ZipPath> RESERVED_ROOT_APK_ENTRIES = ImmutableSet.of(BundleModule.LIB_DIRECTORY, BundleModule.RESOURCES_DIRECTORY, ZipPath.create("AndroidManifest.xml"), ZipPath.create("resources.arsc"), ZipPath.create("AndroidManifest.xml"), BundleModule.SpecialModuleEntry.RESOURCE_TABLE.getPath(), new ZipPath[0]);

    @Override
    public void validateModuleFile(ZipPath file) {
        String fileName = file.getFileName().toString();
        if (!file.startsWith(BundleModule.ASSETS_DIRECTORY)) {
            if (file.startsWith(BundleModule.DEX_DIRECTORY)) {
                if (!fileName.endsWith(".dex")) {
                    throw new BundleFileTypesException.InvalidFileExtensionInDirectoryException(BundleModule.DEX_DIRECTORY, ".dex", file);
                }
                if (!CLASSES_DEX_PATTERN.matcher(fileName).matches()) {
                    throw ValidationException.builder().withMessage("Files under %s/ must match the 'classes[0-9]*.dex' pattern, found '%s'.", BundleModule.DEX_DIRECTORY, file).build();
                }
                if (file.getNameCount() != 2) {
                    throw ValidationException.builder().withMessage("The %s/ directory cannot contain directories, found '%s'.", BundleModule.DEX_DIRECTORY, file).build();
                }
            } else if (file.startsWith(BundleModule.LIB_DIRECTORY)) {
                if (file.getNameCount() != 3) {
                    throw new BundleFileTypesException.InvalidNativeLibraryPathException(BundleModule.LIB_DIRECTORY, file);
                }
                if (!fileName.endsWith(".so")) {
                    throw new BundleFileTypesException.InvalidFileExtensionInDirectoryException(BundleModule.LIB_DIRECTORY, ".so", file);
                }
                String subDirName = file.getName(1).toString();
                if (!AbiName.fromLibSubDirName(subDirName).isPresent()) {
                    throw BundleFileTypesException.InvalidNativeArchitectureNameException.createForDirectory(file.subpath(0, 2));
                }
            } else if (file.startsWith(BundleModule.MANIFEST_DIRECTORY)) {
                if (!fileName.equals("AndroidManifest.xml")) {
                    throw new BundleFileTypesException.InvalidFileNameInDirectoryException("AndroidManifest.xml", BundleModule.MANIFEST_DIRECTORY, file);
                }
            } else if (!file.startsWith(BundleModule.RESOURCES_DIRECTORY)) {
                if (file.startsWith(BundleModule.ROOT_DIRECTORY)) {
                    ZipPath nameUnderRoot = file.getName(1);
                    if (BundleFilesValidator.isReservedRootApkEntry(nameUnderRoot)) {
                        throw new BundleFileTypesException.FileUsesReservedNameException(file, nameUnderRoot);
                    }
                } else if (file.startsWith(BundleModule.APEX_DIRECTORY)) {
                    if (file.getNameCount() != 2) {
                        throw new BundleFileTypesException.InvalidApexImagePathException(BundleModule.APEX_DIRECTORY, file);
                    }
                    if (!fileName.endsWith(".img") && !fileName.endsWith(".build_info.pb")) {
                        throw new BundleFileTypesException.InvalidFileExtensionInDirectoryException(BundleModule.APEX_DIRECTORY, ".img", file);
                    }
                    BundleFilesValidator.validateMultiAbiFileName(file);
                } else {
                    throw new BundleFileTypesException.UnknownFileOrDirectoryFoundInModuleException(file);
                }
            }
        }
    }

    private static boolean isReservedRootApkEntry(ZipPath name) {
        return RESERVED_ROOT_APK_ENTRIES.contains(name) || CLASSES_DEX_PATTERN.matcher(name.toString()).matches();
    }

    private static void validateMultiAbiFileName(ZipPath file) {
        if (!file.toString().endsWith(".img")) {
            return;
        }
        ImmutableList<String> tokens = ImmutableList.copyOf(BundleModule.ABI_SPLITTER.splitToList(file.getFileName().toString()));
        int nAbis = tokens.size() - 1;
        ImmutableList abis = tokens.stream().limit(nAbis).map(AbiName::fromPlatformName).collect(ImmutableList.toImmutableList());
        if (!abis.stream().allMatch(Optional::isPresent)) {
            throw BundleFileTypesException.InvalidNativeArchitectureNameException.createForFile(file);
        }
        ImmutableSet uniqueAbis = abis.stream().map(Optional::get).collect(ImmutableSet.toImmutableSet());
        if (uniqueAbis.size() != nAbis) {
            throw ValidationException.builder().withMessage("Repeating architectures in APEX system image file '%s'.", file).build();
        }
    }
}

