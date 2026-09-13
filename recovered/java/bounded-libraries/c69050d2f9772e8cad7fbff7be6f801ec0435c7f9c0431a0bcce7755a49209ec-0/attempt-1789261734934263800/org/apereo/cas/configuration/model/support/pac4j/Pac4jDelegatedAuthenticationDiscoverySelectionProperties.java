/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jDelegatedAuthenticationDiscoverySelectionProperties")
public class Pac4jDelegatedAuthenticationDiscoverySelectionProperties
implements Serializable {
    private static final long serialVersionUID = -2561947621312270068L;
    @RequiredProperty
    private Pac4jDelegatedAuthenticationSelectionTypes selectionType = Pac4jDelegatedAuthenticationSelectionTypes.MENU;
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties json = new Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties();

    @Generated
    public Pac4jDelegatedAuthenticationSelectionTypes getSelectionType() {
        return this.selectionType;
    }

    @Generated
    public Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties getJson() {
        return this.json;
    }

    @Generated
    public Pac4jDelegatedAuthenticationDiscoverySelectionProperties setSelectionType(Pac4jDelegatedAuthenticationSelectionTypes selectionType) {
        this.selectionType = selectionType;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationDiscoverySelectionProperties setJson(Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties json) {
        this.json = json;
        return this;
    }

    public static enum Pac4jDelegatedAuthenticationSelectionTypes {
        MENU(false),
        DYNAMIC(true);

        private final boolean dynamic;

        @Generated
        public boolean isDynamic() {
            return this.dynamic;
        }

        @Generated
        private Pac4jDelegatedAuthenticationSelectionTypes(boolean dynamic) {
            this.dynamic = dynamic;
        }
    }
}

