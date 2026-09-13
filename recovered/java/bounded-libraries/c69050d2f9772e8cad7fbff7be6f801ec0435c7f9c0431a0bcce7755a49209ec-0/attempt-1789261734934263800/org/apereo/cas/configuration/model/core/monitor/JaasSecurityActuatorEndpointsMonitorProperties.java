/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-core-monitor", automated=true)
@JsonFilter(value="JaasSecurityActuatorEndpointsMonitorProperties")
public class JaasSecurityActuatorEndpointsMonitorProperties
implements Serializable {
    private static final long serialVersionUID = -3024678577827371641L;
    private transient Resource loginConfig;
    private boolean refreshConfigurationOnStartup = true;
    private String loginContextName;

    @Generated
    public Resource getLoginConfig() {
        return this.loginConfig;
    }

    @Generated
    public boolean isRefreshConfigurationOnStartup() {
        return this.refreshConfigurationOnStartup;
    }

    @Generated
    public String getLoginContextName() {
        return this.loginContextName;
    }

    @Generated
    public JaasSecurityActuatorEndpointsMonitorProperties setLoginConfig(Resource loginConfig) {
        this.loginConfig = loginConfig;
        return this;
    }

    @Generated
    public JaasSecurityActuatorEndpointsMonitorProperties setRefreshConfigurationOnStartup(boolean refreshConfigurationOnStartup) {
        this.refreshConfigurationOnStartup = refreshConfigurationOnStartup;
        return this;
    }

    @Generated
    public JaasSecurityActuatorEndpointsMonitorProperties setLoginContextName(String loginContextName) {
        this.loginContextName = loginContextName;
        return this;
    }
}

