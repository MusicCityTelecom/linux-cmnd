/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import java.util.Map;
import java.util.stream.Collectors;

public class MapUtilities {
    private MapUtilities() {
    }

    public static <K, V> Map<V, K> inverse(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}

