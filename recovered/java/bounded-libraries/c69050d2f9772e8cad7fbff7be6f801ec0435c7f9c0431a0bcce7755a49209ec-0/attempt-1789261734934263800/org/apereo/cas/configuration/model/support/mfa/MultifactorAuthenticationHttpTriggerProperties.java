/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="MultifactorAuthenticationHttpTriggerProperties")
public class MultifactorAuthenticationHttpTriggerProperties
implements Serializable {
    private static final long serialVersionUID = 5511521468929733907L;
    private String sessionAttribute = "authn_method";
    private String requestHeader = "authn_method";
    private String requestParameter = "authn_method";

    @Generated
    public String getSessionAttribute() {
        return this.sessionAttribute;
    }

    @Generated
    public String getRequestHeader() {
        return this.requestHeader;
    }

    @Generated
    public String getRequestParameter() {
        return this.requestParameter;
    }

    @Generated
    public MultifactorAuthenticationHttpTriggerProperties setSessionAttribute(String sessionAttribute) {
        this.sessionAttribute = sessionAttribute;
        return this;
    }

    @Generated
    public MultifactorAuthenticationHttpTriggerProperties setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    @Generated
    public MultifactorAuthenticationHttpTriggerProperties setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
        return this;
    }
}

