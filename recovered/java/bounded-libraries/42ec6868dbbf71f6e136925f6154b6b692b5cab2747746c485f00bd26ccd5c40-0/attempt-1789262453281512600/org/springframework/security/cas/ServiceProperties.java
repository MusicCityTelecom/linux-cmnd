/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class ServiceProperties
implements InitializingBean {
    public static final String DEFAULT_CAS_ARTIFACT_PARAMETER = "ticket";
    public static final String DEFAULT_CAS_SERVICE_PARAMETER = "service";
    private String service;
    private boolean authenticateAllArtifacts;
    private boolean sendRenew = false;
    private String artifactParameter = "ticket";
    private String serviceParameter = "service";

    public void afterPropertiesSet() {
        Assert.hasLength((String)this.service, (String)"service cannot be empty.");
        Assert.hasLength((String)this.artifactParameter, (String)"artifactParameter cannot be empty.");
        Assert.hasLength((String)this.serviceParameter, (String)"serviceParameter cannot be empty.");
    }

    public final String getService() {
        return this.service;
    }

    public final boolean isSendRenew() {
        return this.sendRenew;
    }

    public final void setSendRenew(boolean sendRenew) {
        this.sendRenew = sendRenew;
    }

    public final void setService(String service) {
        this.service = service;
    }

    public final String getArtifactParameter() {
        return this.artifactParameter;
    }

    public final void setArtifactParameter(String artifactParameter) {
        this.artifactParameter = artifactParameter;
    }

    public final String getServiceParameter() {
        return this.serviceParameter;
    }

    public final void setServiceParameter(String serviceParameter) {
        this.serviceParameter = serviceParameter;
    }

    public final boolean isAuthenticateAllArtifacts() {
        return this.authenticateAllArtifacts;
    }

    public final void setAuthenticateAllArtifacts(boolean authenticateAllArtifacts) {
        this.authenticateAllArtifacts = authenticateAllArtifacts;
    }
}

