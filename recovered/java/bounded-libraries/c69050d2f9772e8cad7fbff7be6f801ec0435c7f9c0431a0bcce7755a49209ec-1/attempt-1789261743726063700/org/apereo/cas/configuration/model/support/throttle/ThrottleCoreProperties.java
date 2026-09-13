/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-throttle")
@JsonFilter(value="ThrottleCoreProperties")
public class ThrottleCoreProperties
implements Serializable {
    private static final String DEFAULT_APPLICATION_CODE = "CAS";
    private static final long serialVersionUID = -1806129199319966518L;
    private String usernameParameter;
    private String appCode = "CAS";

    @Generated
    public String getUsernameParameter() {
        return this.usernameParameter;
    }

    @Generated
    public String getAppCode() {
        return this.appCode;
    }

    @Generated
    public ThrottleCoreProperties setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
        return this;
    }

    @Generated
    public ThrottleCoreProperties setAppCode(String appCode) {
        this.appCode = appCode;
        return this;
    }
}

