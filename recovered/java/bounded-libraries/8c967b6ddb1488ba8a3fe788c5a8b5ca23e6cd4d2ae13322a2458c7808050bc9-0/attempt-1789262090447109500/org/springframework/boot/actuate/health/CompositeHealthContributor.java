/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.CompositeHealthContributorMapAdapter;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributors;

public interface CompositeHealthContributor
extends HealthContributor,
NamedContributors<HealthContributor> {
    public static CompositeHealthContributor fromMap(Map<String, ? extends HealthContributor> map) {
        return CompositeHealthContributor.fromMap(map, Function.identity());
    }

    public static <V> CompositeHealthContributor fromMap(Map<String, V> map, Function<V, ? extends HealthContributor> valueAdapter) {
        return new CompositeHealthContributorMapAdapter<V>(map, valueAdapter);
    }
}

