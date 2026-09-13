/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.utils.EnumMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public enum AbiName {
    ARMEABI("armeabi", 32),
    ARMEABI_V7A("armeabi-v7a", 32),
    ARM64_V8A("arm64-v8a", 64),
    X86("x86", 32),
    X86_64("x86_64", 64),
    MIPS("mips", 32),
    MIPS64("mips64", 64);

    private final String platformName;
    private final int bitSize;
    private static final ImmutableBiMap<Targeting.Abi.AbiAlias, AbiName> ABI_ALIAS_TO_ABI_NAME_MAP;
    private static final ImmutableMap<String, AbiName> PLATFORM_NAME_TO_ABI_NAME_MAP;

    private AbiName(String platformName, int bitSize) {
        this.platformName = platformName;
        this.bitSize = bitSize;
    }

    public String getPlatformName() {
        return this.platformName;
    }

    public int getBitSize() {
        return this.bitSize;
    }

    public Targeting.Abi.AbiAlias toProto() {
        return (Targeting.Abi.AbiAlias)((ImmutableMap)((Object)ABI_ALIAS_TO_ABI_NAME_MAP.inverse())).get((Object)this);
    }

    public static AbiName fromProto(Targeting.Abi.AbiAlias abiAlias) {
        return (AbiName)((Object)Preconditions.checkNotNull(ABI_ALIAS_TO_ABI_NAME_MAP.get(abiAlias), "Unrecognized ABI '%s'.", (Object)abiAlias));
    }

    public static Optional<AbiName> fromLibSubDirName(String subDirName) {
        if (subDirName.equals("arm64-v8a-hwasan")) {
            return Optional.of(ARM64_V8A);
        }
        return AbiName.fromPlatformName(subDirName);
    }

    public static Optional<AbiName> fromPlatformName(String platformAbiName) {
        return Optional.ofNullable(PLATFORM_NAME_TO_ABI_NAME_MAP.get(platformAbiName));
    }

    public static ImmutableSet<String> getAllPlatformAbis() {
        return Stream.of(AbiName.values()).map(AbiName::getPlatformName).collect(ImmutableSet.toImmutableSet());
    }

    static {
        ABI_ALIAS_TO_ABI_NAME_MAP = EnumMapper.mapByName(Targeting.Abi.AbiAlias.class, AbiName.class, ImmutableSet.of(Targeting.Abi.AbiAlias.UNRECOGNIZED, Targeting.Abi.AbiAlias.UNSPECIFIED_CPU_ARCHITECTURE));
        PLATFORM_NAME_TO_ABI_NAME_MAP = Arrays.stream(AbiName.values()).collect(ImmutableMap.toImmutableMap(AbiName::getPlatformName, Function.identity()));
    }
}

