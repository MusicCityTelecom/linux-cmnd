/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
import java.util.EnumSet;
import java.util.function.Function;

public final class EnumMapper {
    public static <E1 extends Enum<E1>, E2 extends Enum<E2>> ImmutableBiMap<E1, E2> mapByName(Class<E1> enum1Type, Class<E2> enum2Type) {
        return EnumMapper.mapByName(enum1Type, enum2Type, ImmutableSet.of());
    }

    public static <E1 extends Enum<E1>, E2 extends Enum<E2>> ImmutableBiMap<E1, E2> mapByName(Class<E1> enum1Type, Class<E2> enum2Type, ImmutableSet<E1> ignoreValues) {
        ImmutableBiMap.Builder map = ImmutableBiMap.builder();
        ImmutableSortedMap<String, E1> enum1ValuesByName = EnumMapper.enumValuesByName(enum1Type, ignoreValues);
        ImmutableSortedMap<String, E2> enum2ValuesByName = EnumMapper.enumValuesByName(enum2Type, ImmutableSet.of());
        if (!((ImmutableSet)((ImmutableMap)enum1ValuesByName).keySet()).equals(((ImmutableMap)enum2ValuesByName).keySet())) {
            throw new IllegalArgumentException(String.format("Enum %s does not have the same values as enum %s: %s vs %s", enum1Type.getCanonicalName(), enum2Type.getCanonicalName(), ((ImmutableMap)enum1ValuesByName).keySet(), ((ImmutableMap)enum2ValuesByName).keySet()));
        }
        for (String enumName : ((ImmutableMap)enum1ValuesByName).keySet()) {
            map.put(((ImmutableMap)enum1ValuesByName).get(enumName), ((ImmutableMap)enum2ValuesByName).get(enumName));
        }
        return map.build();
    }

    private static <E extends Enum<E>> ImmutableSortedMap<String, E> enumValuesByName(Class<E> enumType, ImmutableSet<E> ignoreValues) {
        return EnumSet.allOf(enumType).stream().filter(Predicates.not(ignoreValues::contains)).sorted().collect(ImmutableSortedMap.toImmutableSortedMap(Ordering.natural(), Enum::name, Function.identity()));
    }

    private EnumMapper() {
    }
}

