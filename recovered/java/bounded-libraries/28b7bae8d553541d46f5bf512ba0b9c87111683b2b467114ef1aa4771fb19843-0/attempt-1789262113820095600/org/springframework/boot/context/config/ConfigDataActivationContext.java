/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.style.ToStringCreator
 */
package org.springframework.boot.context.config;

import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.style.ToStringCreator;

class ConfigDataActivationContext {
    private final CloudPlatform cloudPlatform;
    private final Profiles profiles;

    ConfigDataActivationContext(Environment environment, Binder binder) {
        this.cloudPlatform = this.deduceCloudPlatform(environment, binder);
        this.profiles = null;
    }

    ConfigDataActivationContext(CloudPlatform cloudPlatform, Profiles profiles) {
        this.cloudPlatform = cloudPlatform;
        this.profiles = profiles;
    }

    private CloudPlatform deduceCloudPlatform(Environment environment, Binder binder) {
        for (CloudPlatform candidate : CloudPlatform.values()) {
            if (!candidate.isEnforced(binder)) continue;
            return candidate;
        }
        return CloudPlatform.getActive(environment);
    }

    ConfigDataActivationContext withProfiles(Profiles profiles) {
        return new ConfigDataActivationContext(this.cloudPlatform, profiles);
    }

    CloudPlatform getCloudPlatform() {
        return this.cloudPlatform;
    }

    Profiles getProfiles() {
        return this.profiles;
    }

    public String toString() {
        ToStringCreator creator = new ToStringCreator((Object)this);
        creator.append("cloudPlatform", (Object)this.cloudPlatform);
        creator.append("profiles", (Object)this.profiles);
        return creator.toString();
    }
}

