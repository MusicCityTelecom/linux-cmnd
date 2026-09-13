/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionsUtil {
    public static boolean isUnmodifiableMap(Map map) {
        return map != null && map.getClass().getSimpleName().contains("Unmodifiable");
    }

    public static boolean isUnmodifiableCollection(Collection coll) {
        return coll != null && coll.getClass().getSimpleName().contains("Unmodifiable");
    }

    public static <K, V> Map<K, V> safelyWrapAsUnmodifiableMap(Map<K, V> map) {
        return CollectionsUtil.isUnmodifiableMap(map) ? map : Collections.unmodifiableMap(map);
    }

    public static <T> Collection<T> safelyWrapAsUnmodifiableCollection(Collection<T> collection) {
        return CollectionsUtil.isUnmodifiableCollection(collection) ? collection : Collections.unmodifiableCollection(collection);
    }

    public static <T> Set<T> safelyWrapAsUnmodifiableSet(Set<T> set) {
        return CollectionsUtil.isUnmodifiableCollection(set) ? set : Collections.unmodifiableSet(set);
    }

    public static <T> List<T> safelyWrapAsUnmodifiableList(List<T> list) {
        return CollectionsUtil.isUnmodifiableCollection(list) ? list : Collections.unmodifiableList(list);
    }
}

