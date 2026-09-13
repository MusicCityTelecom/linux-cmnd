/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.validation.TextureCompressionFormatParityValidator;
import com.google.common.collect.ImmutableSet;

final class AutoValue_TextureCompressionFormatParityValidator_SupportedTextureCompressionFormats
extends TextureCompressionFormatParityValidator.SupportedTextureCompressionFormats {
    private final ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> formats;
    private final boolean hasFallback;

    AutoValue_TextureCompressionFormatParityValidator_SupportedTextureCompressionFormats(ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> formats, boolean hasFallback) {
        if (formats == null) {
            throw new NullPointerException("Null formats");
        }
        this.formats = formats;
        this.hasFallback = hasFallback;
    }

    @Override
    public ImmutableSet<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> getFormats() {
        return this.formats;
    }

    @Override
    public boolean getHasFallback() {
        return this.hasFallback;
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof TextureCompressionFormatParityValidator.SupportedTextureCompressionFormats) {
            TextureCompressionFormatParityValidator.SupportedTextureCompressionFormats that = (TextureCompressionFormatParityValidator.SupportedTextureCompressionFormats)o3;
            return this.formats.equals(that.getFormats()) && this.hasFallback == that.getHasFallback();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.formats.hashCode();
        h$ *= 1000003;
        return h$ ^= this.hasFallback ? 1231 : 1237;
    }
}

