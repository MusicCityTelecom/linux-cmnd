/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.config.standalone;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-configuration", automated=true)
@JsonFilter(value="StandaloneConfigurationSecurityProperties")
public class StandaloneConfigurationSecurityProperties
implements Serializable {
    private static final long serialVersionUID = 8571848605614437022L;
    private String alg;
    private String provider;
    private long iteration;
    private String psw;
    private Boolean initializationVector;

    @Generated
    public String getAlg() {
        return this.alg;
    }

    @Generated
    public String getProvider() {
        return this.provider;
    }

    @Generated
    public long getIteration() {
        return this.iteration;
    }

    @Generated
    public String getPsw() {
        return this.psw;
    }

    @Generated
    public Boolean getInitializationVector() {
        return this.initializationVector;
    }

    @Generated
    public StandaloneConfigurationSecurityProperties setAlg(String alg) {
        this.alg = alg;
        return this;
    }

    @Generated
    public StandaloneConfigurationSecurityProperties setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    @Generated
    public StandaloneConfigurationSecurityProperties setIteration(long iteration) {
        this.iteration = iteration;
        return this;
    }

    @Generated
    public StandaloneConfigurationSecurityProperties setPsw(String psw) {
        this.psw = psw;
        return this;
    }

    @Generated
    public StandaloneConfigurationSecurityProperties setInitializationVector(Boolean initializationVector) {
        this.initializationVector = initializationVector;
        return this;
    }
}

