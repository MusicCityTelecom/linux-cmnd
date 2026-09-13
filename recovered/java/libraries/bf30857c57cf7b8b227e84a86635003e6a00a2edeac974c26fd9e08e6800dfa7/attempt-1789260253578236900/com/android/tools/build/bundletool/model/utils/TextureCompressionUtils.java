/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Targeting;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public final class TextureCompressionUtils {
    public static final ImmutableBiMap<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias, String> TEXTURE_COMPRESSION_FORMAT_TO_MANIFEST_VALUE = ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)ImmutableBiMap.builder().put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ASTC, "GL_KHR_texture_compression_astc_ldr")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ATC, "GL_AMD_compressed_ATC_texture")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.DXT1, "GL_EXT_texture_compression_dxt1")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC1_RGB8, "GL_OES_compressed_ETC1_RGB8_texture")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.LATC, "GL_EXT_texture_compression_latc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PALETTED, "GL_OES_compressed_paletted_texture")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PVRTC, "GL_IMG_texture_compression_pvrtc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.S3TC, "GL_EXT_texture_compression_s3tc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.THREE_DC, "GL_AMD_compressed_3DC_texture")).build();
    public static final ImmutableMap<String, Targeting.TextureCompressionFormatTargeting> TEXTURE_TO_TARGETING = new ImmutableMap.Builder<String, Targeting.TextureCompressionFormatTargeting>().put("astc", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ASTC)).put("atc", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ATC)).put("dxt1", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.DXT1)).put("latc", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.LATC)).put("paletted", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PALETTED)).put("pvrtc", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PVRTC)).put("etc1", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC1_RGB8)).put("etc2", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC2)).put("s3tc", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.S3TC)).put("3dc", TextureCompressionUtils.textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.THREE_DC)).build();
    public static final ImmutableBiMap<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias, String> TARGETING_TO_TEXTURE = ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)((ImmutableBiMap.Builder)ImmutableBiMap.builder().put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ASTC, "astc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ATC, "atc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.DXT1, "dxt1")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC1_RGB8, "etc1")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC2, "etc2")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.LATC, "latc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PALETTED, "paletted")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.PVRTC, "pvrtc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.S3TC, "s3tc")).put(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.THREE_DC, "3dc")).build();

    public static Optional<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> textureCompressionFormat(String glExtension) {
        return Optional.ofNullable(((ImmutableMap)((Object)TEXTURE_COMPRESSION_FORMAT_TO_MANIFEST_VALUE.inverse())).get(glExtension));
    }

    private static Targeting.TextureCompressionFormatTargeting textureCompressionFormat(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias alias) {
        return Targeting.TextureCompressionFormatTargeting.newBuilder().addValue(Targeting.TextureCompressionFormat.newBuilder().setAlias(alias)).build();
    }

    public static ImmutableList<Targeting.TextureCompressionFormat.TextureCompressionFormatAlias> textureCompressionFormatsForGl(int glVersion) {
        if (glVersion >= 196608) {
            return ImmutableList.of(Targeting.TextureCompressionFormat.TextureCompressionFormatAlias.ETC2);
        }
        return ImmutableList.of();
    }

    private TextureCompressionUtils() {
    }
}

