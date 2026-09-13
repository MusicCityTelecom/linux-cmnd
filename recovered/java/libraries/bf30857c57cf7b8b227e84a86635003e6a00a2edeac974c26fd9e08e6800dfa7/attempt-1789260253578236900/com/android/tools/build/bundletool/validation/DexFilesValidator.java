/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DexFilesValidator
extends SubValidator {
    private static final Pattern CLASSES_DEX_FILE_PATTERN = Pattern.compile("classes(?<number>[0-9]+)?\\.dex");

    @Override
    public void validateModule(BundleModule module) {
        if (module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) {
            return;
        }
        ImmutableList<String> orderedDexFiles = module.findEntriesUnderPath(BundleModule.DEX_DIRECTORY).map(moduleEntry -> moduleEntry.getPath().getFileName().toString()).filter(fileName -> CLASSES_DEX_FILE_PATTERN.matcher((CharSequence)fileName).matches()).sorted(Comparator.comparingInt(DexFilesValidator::getClassesDexIndex)).collect(ImmutableList.toImmutableList());
        DexFilesValidator.validateDexNames(orderedDexFiles);
        DexFilesValidator.validateHasCode(module, orderedDexFiles);
    }

    private static void validateDexNames(ImmutableList<String> orderedDexFiles) {
        int dexIndex = 1;
        for (String dexFileName : orderedDexFiles) {
            String expectedDexFileName;
            if (!dexFileName.equals(expectedDexFileName = DexFilesValidator.dexFileNameForIndex(dexIndex))) {
                throw ValidationException.builder().withMessage("Invalid dex file indices, expecting file '%s' but found '%s'.", expectedDexFileName, dexFileName).build();
            }
            ++dexIndex;
        }
    }

    private static void validateHasCode(BundleModule module, ImmutableList<String> orderedDexFiles) {
        boolean hasCode = module.getAndroidManifest().getEffectiveHasCode();
        boolean isAssetModule = module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE);
        if (orderedDexFiles.isEmpty() && hasCode && !isAssetModule) {
            throw ValidationException.builder().withMessage("Module '%s' has no dex files but the attribute 'hasCode' is not set to false in the AndroidManifest.xml.", module.getName()).build();
        }
    }

    private static int getClassesDexIndex(String filename) {
        Matcher matcher = CLASSES_DEX_FILE_PATTERN.matcher(filename);
        Preconditions.checkState(matcher.matches(), "File name '%s' does not match the expected pattern.", (Object)filename);
        String numberStr = matcher.group("number");
        return Strings.isNullOrEmpty(numberStr) ? 1 : Integer.parseInt(numberStr);
    }

    private static String dexFileNameForIndex(int index) {
        Preconditions.checkArgument(index > 0, "Index must be positive, got %s.", index);
        return index == 1 ? "classes.dex" : String.format("classes%d.dex", index);
    }
}

