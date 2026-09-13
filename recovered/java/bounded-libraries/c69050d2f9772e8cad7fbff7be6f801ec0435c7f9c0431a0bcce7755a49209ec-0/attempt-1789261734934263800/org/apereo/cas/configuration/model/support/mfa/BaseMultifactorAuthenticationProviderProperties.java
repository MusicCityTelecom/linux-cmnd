/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication-mfa")
public abstract class BaseMultifactorAuthenticationProviderProperties
implements Serializable {
    private static final long serialVersionUID = -2690281104343633871L;
    private int rank;
    private int order = Integer.MAX_VALUE;
    private String id;
    @NestedConfigurationProperty
    private MultifactorAuthenticationProviderBypassProperties bypass = new MultifactorAuthenticationProviderBypassProperties();
    private String name;
    private MultifactorAuthenticationProviderFailureModes failureMode = MultifactorAuthenticationProviderFailureModes.CLOSED;

    @Generated
    public int getRank() {
        return this.rank;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties getBypass() {
        return this.bypass;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public MultifactorAuthenticationProviderFailureModes getFailureMode() {
        return this.failureMode;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties setRank(int rank) {
        this.rank = rank;
        return this;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties setBypass(MultifactorAuthenticationProviderBypassProperties bypass) {
        this.bypass = bypass;
        return this;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties setFailureMode(MultifactorAuthenticationProviderFailureModes failureMode) {
        this.failureMode = failureMode;
        return this;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseMultifactorAuthenticationProviderProperties)) {
            return false;
        }
        BaseMultifactorAuthenticationProviderProperties other = (BaseMultifactorAuthenticationProviderProperties)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseMultifactorAuthenticationProviderProperties;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public static enum MultifactorAuthenticationProviderFailureModes {
        OPEN,
        CLOSED,
        PHANTOM,
        NONE,
        UNDEFINED;


        public boolean isAllowedToBypass() {
            return this == OPEN || this == PHANTOM;
        }
    }
}

