/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Profiles
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.context.config;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.config.ConfigDataActivationContext;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationBindHandler;
import org.springframework.boot.context.config.InvalidConfigDataPropertyException;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.util.ObjectUtils;

class ConfigDataProperties {
    private static final ConfigurationPropertyName NAME = ConfigurationPropertyName.of("spring.config");
    private static final ConfigurationPropertyName LEGACY_PROFILES_NAME = ConfigurationPropertyName.of("spring.profiles");
    private static final Bindable<ConfigDataProperties> BINDABLE_PROPERTIES = Bindable.of(ConfigDataProperties.class);
    private static final Bindable<String[]> BINDABLE_STRING_ARRAY = Bindable.of(String[].class);
    private final List<ConfigDataLocation> imports;
    private final Activate activate;

    ConfigDataProperties(@Name(value="import") List<ConfigDataLocation> imports, Activate activate) {
        this.imports = imports != null ? imports : Collections.emptyList();
        this.activate = activate;
    }

    List<ConfigDataLocation> getImports() {
        return this.imports;
    }

    boolean isActive(ConfigDataActivationContext activationContext) {
        return this.activate == null || this.activate.isActive(activationContext);
    }

    ConfigDataProperties withoutImports() {
        return new ConfigDataProperties(null, this.activate);
    }

    ConfigDataProperties withLegacyProfiles(String[] legacyProfiles, ConfigurationProperty property) {
        if (this.activate != null && !ObjectUtils.isEmpty((Object[])this.activate.onProfile)) {
            throw new InvalidConfigDataPropertyException(property, false, NAME.append("activate.on-profile"), null);
        }
        return new ConfigDataProperties(this.imports, new Activate(this.activate.onCloudPlatform, legacyProfiles));
    }

    static ConfigDataProperties get(Binder binder) {
        LegacyProfilesBindHandler legacyProfilesBindHandler = new LegacyProfilesBindHandler();
        Object[] legacyProfiles = binder.bind(LEGACY_PROFILES_NAME, BINDABLE_STRING_ARRAY, (BindHandler)legacyProfilesBindHandler).orElse(null);
        ConfigDataProperties properties = binder.bind(NAME, BINDABLE_PROPERTIES, (BindHandler)new ConfigDataLocationBindHandler()).orElse(null);
        if (!ObjectUtils.isEmpty((Object[])legacyProfiles)) {
            properties = properties != null ? properties.withLegacyProfiles((String[])legacyProfiles, legacyProfilesBindHandler.getProperty()) : new ConfigDataProperties(null, new Activate(null, (String[])legacyProfiles));
        }
        return properties;
    }

    static class Activate {
        private final CloudPlatform onCloudPlatform;
        private final String[] onProfile;

        Activate(CloudPlatform onCloudPlatform, String[] onProfile) {
            this.onProfile = onProfile;
            this.onCloudPlatform = onCloudPlatform;
        }

        boolean isActive(ConfigDataActivationContext activationContext) {
            if (activationContext == null) {
                return false;
            }
            boolean activate = true;
            activate = activate && this.isActive(activationContext.getCloudPlatform());
            activate = activate && this.isActive(activationContext.getProfiles());
            return activate;
        }

        private boolean isActive(CloudPlatform cloudPlatform) {
            return this.onCloudPlatform == null || this.onCloudPlatform == cloudPlatform;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean isActive(Profiles profiles) {
            if (ObjectUtils.isEmpty((Object[])this.onProfile)) return true;
            if (profiles == null) return false;
            if (!this.matchesActiveProfiles(profiles::isAccepted)) return false;
            return true;
        }

        private boolean matchesActiveProfiles(Predicate<String> activeProfiles) {
            return org.springframework.core.env.Profiles.of((String[])this.onProfile).matches(activeProfiles);
        }
    }

    private static class LegacyProfilesBindHandler
    implements BindHandler {
        private ConfigurationProperty property;

        private LegacyProfilesBindHandler() {
        }

        @Override
        public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
            this.property = context.getConfigurationProperty();
            return result;
        }

        ConfigurationProperty getProperty() {
            return this.property;
        }
    }
}

