/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.NamedContributorsMapAdapter;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;

class CompositeReactiveHealthContributorMapAdapter<V>
extends NamedContributorsMapAdapter<V, ReactiveHealthContributor>
implements CompositeReactiveHealthContributor {
    CompositeReactiveHealthContributorMapAdapter(Map<String, V> map, Function<V, ? extends ReactiveHealthContributor> valueAdapter) {
        super(map, valueAdapter);
    }
}

