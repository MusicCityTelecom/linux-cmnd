/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributorMapAdapter;
import org.springframework.boot.actuate.health.NamedContributors;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;

public interface CompositeReactiveHealthContributor
extends ReactiveHealthContributor,
NamedContributors<ReactiveHealthContributor> {
    public static CompositeReactiveHealthContributor fromMap(Map<String, ? extends ReactiveHealthContributor> map) {
        return CompositeReactiveHealthContributor.fromMap(map, Function.identity());
    }

    public static <V> CompositeReactiveHealthContributor fromMap(Map<String, V> map, Function<V, ? extends ReactiveHealthContributor> valueAdapter) {
        return new CompositeReactiveHealthContributorMapAdapter<V>(map, valueAdapter);
    }
}

