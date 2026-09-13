/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.DefaultContributorRegistry;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributorRegistry;

public class DefaultReactiveHealthContributorRegistry
extends DefaultContributorRegistry<ReactiveHealthContributor>
implements ReactiveHealthContributorRegistry {
    public DefaultReactiveHealthContributorRegistry() {
    }

    public DefaultReactiveHealthContributorRegistry(Map<String, ReactiveHealthContributor> contributors) {
        super(contributors);
    }

    public DefaultReactiveHealthContributorRegistry(Map<String, ReactiveHealthContributor> contributors, Function<String, String> nameFactory) {
        super(contributors, nameFactory);
    }
}

