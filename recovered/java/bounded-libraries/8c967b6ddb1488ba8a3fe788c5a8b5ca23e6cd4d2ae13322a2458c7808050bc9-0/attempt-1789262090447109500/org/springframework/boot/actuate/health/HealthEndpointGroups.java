/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.actuate.health.AdditionalHealthEndpointPath;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.util.Assert;

public interface HealthEndpointGroups {
    public HealthEndpointGroup getPrimary();

    public Set<String> getNames();

    public HealthEndpointGroup get(String var1);

    default public HealthEndpointGroup get(AdditionalHealthEndpointPath path) {
        Assert.notNull((Object)path, (String)"Path must not be null");
        for (String name : this.getNames()) {
            HealthEndpointGroup group = this.get(name);
            if (!path.equals(group.getAdditionalPath())) continue;
            return group;
        }
        return null;
    }

    default public Set<HealthEndpointGroup> getAllWithAdditionalPath(WebServerNamespace namespace) {
        Assert.notNull((Object)namespace, (String)"Namespace must not be null");
        LinkedHashSet<HealthEndpointGroup> filteredGroups = new LinkedHashSet<HealthEndpointGroup>();
        this.getNames().stream().map(this::get).filter(group -> group.getAdditionalPath() != null && group.getAdditionalPath().hasNamespace(namespace)).forEach(filteredGroups::add);
        return filteredGroups;
    }

    public static HealthEndpointGroups of(final HealthEndpointGroup primary, final Map<String, HealthEndpointGroup> additional) {
        Assert.notNull((Object)primary, (String)"Primary must not be null");
        Assert.notNull(additional, (String)"Additional must not be null");
        return new HealthEndpointGroups(){

            @Override
            public HealthEndpointGroup getPrimary() {
                return primary;
            }

            @Override
            public Set<String> getNames() {
                return additional.keySet();
            }

            @Override
            public HealthEndpointGroup get(String name) {
                return (HealthEndpointGroup)additional.get(name);
            }
        };
    }
}

