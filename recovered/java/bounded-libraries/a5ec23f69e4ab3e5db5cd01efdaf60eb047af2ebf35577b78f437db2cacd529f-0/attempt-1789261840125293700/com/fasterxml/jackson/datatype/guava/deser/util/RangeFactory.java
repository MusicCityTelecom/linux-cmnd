/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BoundType
 *  com.google.common.collect.Range
 */
package com.fasterxml.jackson.datatype.guava.deser.util;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

public class RangeFactory {
    public static <C extends Comparable<?>> Range<C> open(C lowerEndpoint, C upperEndpoint) {
        return RangeFactory.range(lowerEndpoint, BoundType.OPEN, upperEndpoint, BoundType.OPEN);
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C lowerEndpoint, C upperEndpoint) {
        return RangeFactory.range(lowerEndpoint, BoundType.OPEN, upperEndpoint, BoundType.CLOSED);
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C lowerEndpoint, C upperEndpoint) {
        return RangeFactory.range(lowerEndpoint, BoundType.CLOSED, upperEndpoint, BoundType.OPEN);
    }

    public static <C extends Comparable<?>> Range<C> closed(C lowerEndpoint, C upperEndpoint) {
        return RangeFactory.range(lowerEndpoint, BoundType.CLOSED, upperEndpoint, BoundType.CLOSED);
    }

    public static <C extends Comparable<?>> Range<C> range(C lowerEndpoint, BoundType lowerBoundType, C upperEndpoint, BoundType upperBoundType) {
        return Range.range(lowerEndpoint, (BoundType)lowerBoundType, upperEndpoint, (BoundType)upperBoundType);
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C lowerEndpoint) {
        return RangeFactory.downTo(lowerEndpoint, BoundType.OPEN);
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C lowerEndpoint) {
        return RangeFactory.downTo(lowerEndpoint, BoundType.CLOSED);
    }

    public static <C extends Comparable<?>> Range<C> downTo(C lowerEndpoint, BoundType lowerBoundType) {
        return Range.downTo(lowerEndpoint, (BoundType)lowerBoundType);
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C upperEndpoint) {
        return RangeFactory.upTo(upperEndpoint, BoundType.OPEN);
    }

    public static <C extends Comparable<?>> Range<C> atMost(C upperEndpoint) {
        return RangeFactory.upTo(upperEndpoint, BoundType.CLOSED);
    }

    public static <C extends Comparable<?>> Range<C> upTo(C upperEndpoint, BoundType upperBoundType) {
        return Range.upTo(upperEndpoint, (BoundType)upperBoundType);
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return Range.all();
    }

    public static <C extends Comparable<?>> Range<C> singleton(C value) {
        return Range.singleton(value);
    }

    private RangeFactory() {
    }
}

