/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.view;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="Cas20ProxyViewProperties")
public class Cas20ProxyViewProperties
implements Serializable {
    private static final long serialVersionUID = 6765987342872282599L;
    private String success = "protocol/2.0/casProxySuccessView";
    private String failure = "protocol/2.0/casProxyFailureView";

    @Generated
    public String getSuccess() {
        return this.success;
    }

    @Generated
    public String getFailure() {
        return this.failure;
    }

    @Generated
    public Cas20ProxyViewProperties setSuccess(String success) {
        this.success = success;
        return this;
    }

    @Generated
    public Cas20ProxyViewProperties setFailure(String failure) {
        this.failure = failure;
        return this;
    }
}

