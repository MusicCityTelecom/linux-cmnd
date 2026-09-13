/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatAjpProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatApachePortableRuntimeProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatBasicAuthenticationProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatClusteringProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatCsrfProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatExtendedAccessLogProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatHttpProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatHttpProxyProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatRemoteAddressProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatRewriteValveProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatSocketProperties;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatSslValveProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatProperties")
public class CasEmbeddedApacheTomcatProperties
implements Serializable {
    private static final long serialVersionUID = -99143821503580896L;
    private String serverName = "Apereo CAS";
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatSocketProperties socket = new CasEmbeddedApacheTomcatSocketProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatAjpProperties ajp = new CasEmbeddedApacheTomcatAjpProperties();
    private List<CasEmbeddedApacheTomcatHttpProperties> http = new ArrayList<CasEmbeddedApacheTomcatHttpProperties>();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatClusteringProperties clustering = new CasEmbeddedApacheTomcatClusteringProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatApachePortableRuntimeProperties apr = new CasEmbeddedApacheTomcatApachePortableRuntimeProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatHttpProxyProperties httpProxy = new CasEmbeddedApacheTomcatHttpProxyProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatSslValveProperties sslValve = new CasEmbeddedApacheTomcatSslValveProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatRewriteValveProperties rewriteValve = new CasEmbeddedApacheTomcatRewriteValveProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatExtendedAccessLogProperties extAccessLog = new CasEmbeddedApacheTomcatExtendedAccessLogProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatRemoteAddressProperties remoteAddr = new CasEmbeddedApacheTomcatRemoteAddressProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatCsrfProperties csrf = new CasEmbeddedApacheTomcatCsrfProperties();
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatBasicAuthenticationProperties basicAuthn = new CasEmbeddedApacheTomcatBasicAuthenticationProperties();

    @Generated
    public String getServerName() {
        return this.serverName;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties getSocket() {
        return this.socket;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties getAjp() {
        return this.ajp;
    }

    @Generated
    public List<CasEmbeddedApacheTomcatHttpProperties> getHttp() {
        return this.http;
    }

    @Generated
    public CasEmbeddedApacheTomcatClusteringProperties getClustering() {
        return this.clustering;
    }

    @Generated
    public CasEmbeddedApacheTomcatApachePortableRuntimeProperties getApr() {
        return this.apr;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties getHttpProxy() {
        return this.httpProxy;
    }

    @Generated
    public CasEmbeddedApacheTomcatSslValveProperties getSslValve() {
        return this.sslValve;
    }

    @Generated
    public CasEmbeddedApacheTomcatRewriteValveProperties getRewriteValve() {
        return this.rewriteValve;
    }

    @Generated
    public CasEmbeddedApacheTomcatExtendedAccessLogProperties getExtAccessLog() {
        return this.extAccessLog;
    }

    @Generated
    public CasEmbeddedApacheTomcatRemoteAddressProperties getRemoteAddr() {
        return this.remoteAddr;
    }

    @Generated
    public CasEmbeddedApacheTomcatCsrfProperties getCsrf() {
        return this.csrf;
    }

    @Generated
    public CasEmbeddedApacheTomcatBasicAuthenticationProperties getBasicAuthn() {
        return this.basicAuthn;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setSocket(CasEmbeddedApacheTomcatSocketProperties socket) {
        this.socket = socket;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setAjp(CasEmbeddedApacheTomcatAjpProperties ajp) {
        this.ajp = ajp;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setHttp(List<CasEmbeddedApacheTomcatHttpProperties> http) {
        this.http = http;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setClustering(CasEmbeddedApacheTomcatClusteringProperties clustering) {
        this.clustering = clustering;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setApr(CasEmbeddedApacheTomcatApachePortableRuntimeProperties apr) {
        this.apr = apr;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setHttpProxy(CasEmbeddedApacheTomcatHttpProxyProperties httpProxy) {
        this.httpProxy = httpProxy;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setSslValve(CasEmbeddedApacheTomcatSslValveProperties sslValve) {
        this.sslValve = sslValve;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setRewriteValve(CasEmbeddedApacheTomcatRewriteValveProperties rewriteValve) {
        this.rewriteValve = rewriteValve;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setExtAccessLog(CasEmbeddedApacheTomcatExtendedAccessLogProperties extAccessLog) {
        this.extAccessLog = extAccessLog;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setRemoteAddr(CasEmbeddedApacheTomcatRemoteAddressProperties remoteAddr) {
        this.remoteAddr = remoteAddr;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setCsrf(CasEmbeddedApacheTomcatCsrfProperties csrf) {
        this.csrf = csrf;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties setBasicAuthn(CasEmbeddedApacheTomcatBasicAuthenticationProperties basicAuthn) {
        this.basicAuthn = basicAuthn;
        return this;
    }
}

