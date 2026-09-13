/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Streams;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class CollectorUtils {
    public static <T, K extends Comparable<K>> Collector<T, ?, ImmutableListMultimap<K, T>> groupingBySortedKeys(Function<? super T, ? extends K> keyFunction) {
        return CollectorUtils.groupingBySortedKeys(keyFunction, Function.identity());
    }

    public static <T, K extends Comparable<K>, V> Collector<T, ?, ImmutableListMultimap<K, V>> groupingBySortedKeys(Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends V> valueFunction) {
        return Collectors.collectingAndThen(Multimaps.toMultimap(keyFunction, valueFunction, MultimapBuilder.treeKeys().arrayListValues()::build), ImmutableListMultimap::copyOf);
    }

    public static <K, V> ImmutableMap<K, V> combineMaps(Map<K, V> map1, Map<K, V> map2, BinaryOperator<V> mergeFunction) {
        return Streams.concat(map1.entrySet().stream(), map2.entrySet().stream()).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction));
    }

    private CollectorUtils() {
    }
}

