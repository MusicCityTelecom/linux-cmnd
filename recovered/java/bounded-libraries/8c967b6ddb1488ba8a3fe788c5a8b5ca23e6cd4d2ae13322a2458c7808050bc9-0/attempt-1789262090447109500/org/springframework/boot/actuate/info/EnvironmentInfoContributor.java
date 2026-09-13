/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 */
package org.springframework.boot.actuate.info;

import java.util.Map;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

public class EnvironmentInfoContributor
implements InfoContributor {
    private static final Bindable<Map<String, Object>> STRING_OBJECT_MAP = Bindable.mapOf(String.class, Object.class);
    private final ConfigurableEnvironment environment;

    public EnvironmentInfoContributor(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Binder binder = Binder.get((Environment)this.environment);
        binder.bind("info", STRING_OBJECT_MAP).ifBound(builder::withDetails);
    }
}

