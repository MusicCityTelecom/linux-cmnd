/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core", automated=true)
@JsonFilter(value="CasServerHostProperties")
public class CasServerHostProperties
implements Serializable {
    private static final long serialVersionUID = 8624916460241033347L;
    private String name;

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public CasServerHostProperties setName(String name) {
        this.name = name;
        return this;
    }
}

