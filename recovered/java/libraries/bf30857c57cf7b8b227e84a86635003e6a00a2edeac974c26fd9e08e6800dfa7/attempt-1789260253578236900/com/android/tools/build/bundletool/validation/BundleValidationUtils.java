/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;

public final class BundleValidationUtils {
    public static void checkHasValuesOrAlternatives(Targeting.AbiTargeting targeting, String directoryPath) {
        if (targeting.getValueCount() == 0 && targeting.getAlternativesCount() == 0) {
            throw ValidationException.builder().withMessage("Directory '%s' has set but empty ABI targeting.", directoryPath).build();
        }
    }

    public static void checkHasValuesOrAlternatives(Targeting.GraphicsApiTargeting targeting, String directoryPath) {
        if (targeting.getValueCount() == 0 && targeting.getAlternativesCount() == 0) {
            throw ValidationException.builder().withMessage("Directory '%s' has set but empty Graphics API targeting.", directoryPath).build();
        }
    }

    public static void checkHasValuesOrAlternatives(Targeting.LanguageTargeting targeting, String directoryPath) {
        if (targeting.getValueCount() == 0 && targeting.getAlternativesCount() == 0) {
            throw ValidationException.builder().withMessage("Directory '%s' has set but empty language targeting.", directoryPath).build();
        }
    }

    public static void checkHasValuesOrAlternatives(Targeting.TextureCompressionFormatTargeting targeting, String directoryPath) {
        if (targeting.getValueCount() == 0 && targeting.getAlternativesCount() == 0) {
            throw ValidationException.builder().withMessage("Directory '%s' has set but empty Texture Compression Format targeting.", directoryPath).build();
        }
    }

    public static boolean directoryContainsNoFiles(BundleModule module, ZipPath dir) {
        return module.findEntriesUnderPath(dir).count() == 0L;
    }

    private BundleValidationUtils() {
    }
}

