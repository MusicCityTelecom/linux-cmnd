/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ntlm;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-spnego")
public class NtlmProperties
implements Serializable {
    private static final long serialVersionUID = 1479912148936123469L;
    private String domainController;
    private String includePattern;
    private boolean loadBalance = true;
    private String name;
    private int order = Integer.MAX_VALUE;
    private boolean enabled;

    @Generated
    public String getDomainController() {
        return this.domainController;
    }

    @Generated
    public String getIncludePattern() {
        return this.includePattern;
    }

    @Generated
    public boolean isLoadBalance() {
        return this.loadBalance;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public NtlmProperties setDomainController(String domainController) {
        this.domainController = domainController;
        return this;
    }

    @Generated
    public NtlmProperties setIncludePattern(String includePattern) {
        this.includePattern = includePattern;
        return this;
    }

    @Generated
    public NtlmProperties setLoadBalance(boolean loadBalance) {
        this.loadBalance = loadBalance;
        return this;
    }

    @Generated
    public NtlmProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public NtlmProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public NtlmProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

