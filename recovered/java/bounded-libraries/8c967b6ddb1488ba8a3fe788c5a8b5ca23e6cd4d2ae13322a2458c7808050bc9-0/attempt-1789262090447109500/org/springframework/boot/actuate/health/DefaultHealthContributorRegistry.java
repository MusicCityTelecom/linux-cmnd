/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.DefaultContributorRegistry;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;

public class DefaultHealthContributorRegistry
extends DefaultContributorRegistry<HealthContributor>
implements HealthContributorRegistry {
    public DefaultHealthContributorRegistry() {
    }

    public DefaultHealthContributorRegistry(Map<String, HealthContributor> contributors) {
        super(contributors);
    }

    public DefaultHealthContributorRegistry(Map<String, HealthContributor> contributors, Function<String, String> nameFactory) {
        super(contributors, nameFactory);
    }
}

