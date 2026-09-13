/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth")
@JsonFilter(value="OAuthGrantsProperties")
public class OAuthGrantsProperties
implements Serializable {
    private static final long serialVersionUID = -2246860215082703251L;
    private ResourceOwner resourceOwner = new ResourceOwner();

    @Generated
    public ResourceOwner getResourceOwner() {
        return this.resourceOwner;
    }

    @Generated
    public OAuthGrantsProperties setResourceOwner(ResourceOwner resourceOwner) {
        this.resourceOwner = resourceOwner;
        return this;
    }

    @RequiresModule(name="cas-server-support-oauth")
    public static class ResourceOwner
    implements Serializable {
        private static final long serialVersionUID = 3171206304518294330L;
        private boolean requireServiceHeader;

        @Generated
        public boolean isRequireServiceHeader() {
            return this.requireServiceHeader;
        }

        @Generated
        public ResourceOwner setRequireServiceHeader(boolean requireServiceHeader) {
            this.requireServiceHeader = requireServiceHeader;
            return this;
        }
    }
}

