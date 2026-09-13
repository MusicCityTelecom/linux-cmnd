/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectory;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.validation.AutoValue_TextureCompressionFormatParityValidator_SupportedTextureCompressionFormats;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;

public class TextureCompressionFormatParityValidator
extends SubValidator {
    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        BundleModule referentialModule = null;
        Object referentialTextureCompressionFormats = null;
        for (BundleModule module : modules) {
            SupportedTextureCompressionFormats moduleTextureCompressionFormats = TextureCompressionFormatParityValidator.getSupportedTextureCompressionFormats(module);
            if (moduleTextureCompressionFormats.getFormats().isEmpty()) continue;
            if (referentialTextureCompressionFormats == null) {
                referentialModule = module;
                referentialTextureCompressionFormats = moduleTextureCompressionFormats;
                continue;
            }
            if (referentialTextureCompressionFormats.equals(moduleTextureCompressionFormats)) continue;
            throw ValidationException.builder().withMessage("All modules with targeted textures must have the same set of texture formats, but module '%s' has formats %s and module '%s' has formats %s.", referentialModule.getName(), referentialTextureCompressionFormats, module.getName(), moduleTextureCompressionFormats).build();
        }
    }

    private static SupportedTextureCompressionFormats getSupportedTextureCompressionFormats(BundleModule module) {
        ImmutableSet<TargetedDirectory> targetedDirectories = TargetingUtils.extractAssetsTargetedDirectories(module);
        ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> formats = TargetingUtils.extractTextureCompressionFormats(targetedDirectories);
        boolean hasFallback = targetedDirectories.stream().anyMatch(directory -> {
            Optional<Targeting.AssetsDirectoryTargeting> targeting = directory.getTargeting(TargetingDimension.TEXTURE_COMPRESSION_FORMAT);
            if (targeting.isPresent()) {
                TargetedDirectory siblingFallbackDirectory = directory.removeTargeting(TargetingDimension.TEXTURE_COMPRESSION_FORMAT);
                return module.findEntriesUnderPath(siblingFallbackDirectory.toZipPath()).findAny().isPresent();
            }
            return false;
        });
        return SupportedTextureCompressionFormats.create(formats, hasFallback);
    }

    @AutoValue
    public static abstract class SupportedTextureCompressionFormats {
        public static SupportedTextureCompressionFormats create(ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> formats, boolean hasFallback) {
            return new AutoValue_TextureCompressionFormatParityValidator_SupportedTextureCompressionFormats(formats, hasFallback);
        }

        public abstract ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> getFormats();

        public abstract boolean getHasFallback();

        public final String toString() {
            return this.getFormats().toString() + (this.getHasFallback() ? " (with fallback directories)" : " (without fallback directories)");
        }
    }
}

