/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.ResourceLoader
 */
package org.apereo.cas.configuration.api;

import java.util.Optional;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ResourceLoader;

@FunctionalInterface
public interface CasConfigurationPropertiesSourceLocator {
    public static final String PROFILE_STANDALONE = "standalone";
    public static final String PROFILE_EMBEDDED = "embedded";

    public Optional<PropertySource<?>> locate(Environment var1, ResourceLoader var2);
}

