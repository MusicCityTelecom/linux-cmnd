/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

import java.lang.reflect.Array;
import java.util.Objects;

public class Arrays {
    public static <T> T[] concat(T[] ... arrays) {
        if (null == arrays || 0 == arrays.length) {
            return null;
        }
        int resultLength = java.util.Arrays.stream(arrays).filter(Objects::nonNull).map(e -> ((Object[])e).length).reduce(0, Integer::sum);
        Object[] resultArray = (Object[])Array.newInstance(arrays[0].getClass().getComponentType(), resultLength);
        int n = arrays.length;
        int curr = 0;
        for (int i = 0; i < n; ++i) {
            T[] array = arrays[i];
            if (null == array) continue;
            int length = array.length;
            System.arraycopy(array, 0, resultArray, curr, length);
            curr += length;
        }
        return resultArray;
    }

    private Arrays() {
    }
}

