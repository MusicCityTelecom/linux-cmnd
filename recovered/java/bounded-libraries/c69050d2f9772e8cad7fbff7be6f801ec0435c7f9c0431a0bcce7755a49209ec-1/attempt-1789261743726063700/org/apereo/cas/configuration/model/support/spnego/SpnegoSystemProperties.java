/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.spnego;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-spnego")
@JsonFilter(value="SpnegoSystemProperties")
public class SpnegoSystemProperties
implements Serializable {
    private static final long serialVersionUID = -7213507143858237596L;
    private String loginConf;
    private String kerberosConf;
    private String kerberosKdc = "172.10.1.10";
    private String kerberosRealm = "EXAMPLE.COM";
    private String kerberosDebug;
    private boolean useSubjectCredsOnly;

    @Generated
    public String getLoginConf() {
        return this.loginConf;
    }

    @Generated
    public String getKerberosConf() {
        return this.kerberosConf;
    }

    @Generated
    public String getKerberosKdc() {
        return this.kerberosKdc;
    }

    @Generated
    public String getKerberosRealm() {
        return this.kerberosRealm;
    }

    @Generated
    public String getKerberosDebug() {
        return this.kerberosDebug;
    }

    @Generated
    public boolean isUseSubjectCredsOnly() {
        return this.useSubjectCredsOnly;
    }

    @Generated
    public SpnegoSystemProperties setLoginConf(String loginConf) {
        this.loginConf = loginConf;
        return this;
    }

    @Generated
    public SpnegoSystemProperties setKerberosConf(String kerberosConf) {
        this.kerberosConf = kerberosConf;
        return this;
    }

    @Generated
    public SpnegoSystemProperties setKerberosKdc(String kerberosKdc) {
        this.kerberosKdc = kerberosKdc;
        return this;
    }

    @Generated
    public SpnegoSystemProperties setKerberosRealm(String kerberosRealm) {
        this.kerberosRealm = kerberosRealm;
        return this;
    }

    @Generated
    public SpnegoSystemProperties setKerberosDebug(String kerberosDebug) {
        this.kerberosDebug = kerberosDebug;
        return this;
    }

    @Generated
    public SpnegoSystemProperties setUseSubjectCredsOnly(boolean useSubjectCredsOnly) {
        this.useSubjectCredsOnly = useSubjectCredsOnly;
        return this;
    }
}

