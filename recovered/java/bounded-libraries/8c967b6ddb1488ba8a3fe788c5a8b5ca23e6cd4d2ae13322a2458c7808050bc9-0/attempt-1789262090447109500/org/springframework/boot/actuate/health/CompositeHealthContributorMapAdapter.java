/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributorsMapAdapter;

class CompositeHealthContributorMapAdapter<V>
extends NamedContributorsMapAdapter<V, HealthContributor>
implements CompositeHealthContributor {
    CompositeHealthContributorMapAdapter(Map<String, V> map, Function<V, ? extends HealthContributor> valueAdapter) {
        super(map, valueAdapter);
    }
}

