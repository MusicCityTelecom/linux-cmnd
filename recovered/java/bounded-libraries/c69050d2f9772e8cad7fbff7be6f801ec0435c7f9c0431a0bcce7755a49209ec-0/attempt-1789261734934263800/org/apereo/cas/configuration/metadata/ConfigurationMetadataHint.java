/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.configurationmetadata.ValueHint
 *  org.springframework.boot.configurationmetadata.ValueProvider
 */
package org.apereo.cas.configuration.metadata;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.springframework.boot.configurationmetadata.ValueHint;
import org.springframework.boot.configurationmetadata.ValueProvider;

public class ConfigurationMetadataHint {
    private final List<ValueHint> values = new ArrayList<ValueHint>(0);
    private final List<ValueProvider> providers = new ArrayList<ValueProvider>(0);
    private String name;

    @Generated
    public List<ValueHint> getValues() {
        return this.values;
    }

    @Generated
    public List<ValueProvider> getProviders() {
        return this.providers;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }
}

