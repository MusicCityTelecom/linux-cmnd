/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.spnego;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.model.support.spnego.SpnegoAuthenticationProperties;
import org.apereo.cas.configuration.model.support.spnego.SpnegoLdapProperties;
import org.apereo.cas.configuration.model.support.spnego.SpnegoSystemProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-spnego-webflow")
@JsonFilter(value="SpnegoProperties")
public class SpnegoProperties
implements Serializable {
    private static final long serialVersionUID = 8084143496524446970L;
    private final SpnegoSystemProperties system = new SpnegoSystemProperties();
    private final List<SpnegoAuthenticationProperties> properties = new ArrayList<SpnegoAuthenticationProperties>(0);
    private boolean principalWithDomainName;
    private boolean ntlmAllowed = true;
    private boolean send401OnAuthenticationFailure = true;
    private String hostNameClientActionStrategy = "hostnameSpnegoClientAction";
    @NestedConfigurationProperty
    private SpnegoLdapProperties ldap = new SpnegoLdapProperties();
    @DurationCapable
    private String dnsTimeout = "PT2S";
    private String hostNamePatternString = ".+";
    private String ipsToCheckPattern = "127.+";
    private String alternativeRemoteHostAttribute = "alternateRemoteHeader";
    private String spnegoAttributeName = "distinguishedName";
    private boolean ntlm;
    private boolean mixedModeAuthentication;
    private String supportedBrowsers = "MSIE,Trident,Firefox,AppleWebKit";
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @NestedConfigurationProperty
    private PersonDirectoryPrincipalResolverProperties principal = new PersonDirectoryPrincipalResolverProperties();
    private String name;
    private int order = Integer.MAX_VALUE;
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties webflow = new WebflowAutoConfigurationProperties().setOrder(100);
    private int poolSize = 10;
    @DurationCapable
    private String poolTimeout = "PT2S";

    @Generated
    public SpnegoSystemProperties getSystem() {
        return this.system;
    }

    @Generated
    public List<SpnegoAuthenticationProperties> getProperties() {
        return this.properties;
    }

    @Generated
    public boolean isPrincipalWithDomainName() {
        return this.principalWithDomainName;
    }

    @Generated
    public boolean isNtlmAllowed() {
        return this.ntlmAllowed;
    }

    @Generated
    public boolean isSend401OnAuthenticationFailure() {
        return this.send401OnAuthenticationFailure;
    }

    @Generated
    public String getHostNameClientActionStrategy() {
        return this.hostNameClientActionStrategy;
    }

    @Generated
    public SpnegoLdapProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public String getDnsTimeout() {
        return this.dnsTimeout;
    }

    @Generated
    public String getHostNamePatternString() {
        return this.hostNamePatternString;
    }

    @Generated
    public String getIpsToCheckPattern() {
        return this.ipsToCheckPattern;
    }

    @Generated
    public String getAlternativeRemoteHostAttribute() {
        return this.alternativeRemoteHostAttribute;
    }

    @Generated
    public String getSpnegoAttributeName() {
        return this.spnegoAttributeName;
    }

    @Generated
    public boolean isNtlm() {
        return this.ntlm;
    }

    @Generated
    public boolean isMixedModeAuthentication() {
        return this.mixedModeAuthentication;
    }

    @Generated
    public String getSupportedBrowsers() {
        return this.supportedBrowsers;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties getPrincipal() {
        return this.principal;
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
    public WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public int getPoolSize() {
        return this.poolSize;
    }

    @Generated
    public String getPoolTimeout() {
        return this.poolTimeout;
    }

    @Generated
    public SpnegoProperties setPrincipalWithDomainName(boolean principalWithDomainName) {
        this.principalWithDomainName = principalWithDomainName;
        return this;
    }

    @Generated
    public SpnegoProperties setNtlmAllowed(boolean ntlmAllowed) {
        this.ntlmAllowed = ntlmAllowed;
        return this;
    }

    @Generated
    public SpnegoProperties setSend401OnAuthenticationFailure(boolean send401OnAuthenticationFailure) {
        this.send401OnAuthenticationFailure = send401OnAuthenticationFailure;
        return this;
    }

    @Generated
    public SpnegoProperties setHostNameClientActionStrategy(String hostNameClientActionStrategy) {
        this.hostNameClientActionStrategy = hostNameClientActionStrategy;
        return this;
    }

    @Generated
    public SpnegoProperties setLdap(SpnegoLdapProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public SpnegoProperties setDnsTimeout(String dnsTimeout) {
        this.dnsTimeout = dnsTimeout;
        return this;
    }

    @Generated
    public SpnegoProperties setHostNamePatternString(String hostNamePatternString) {
        this.hostNamePatternString = hostNamePatternString;
        return this;
    }

    @Generated
    public SpnegoProperties setIpsToCheckPattern(String ipsToCheckPattern) {
        this.ipsToCheckPattern = ipsToCheckPattern;
        return this;
    }

    @Generated
    public SpnegoProperties setAlternativeRemoteHostAttribute(String alternativeRemoteHostAttribute) {
        this.alternativeRemoteHostAttribute = alternativeRemoteHostAttribute;
        return this;
    }

    @Generated
    public SpnegoProperties setSpnegoAttributeName(String spnegoAttributeName) {
        this.spnegoAttributeName = spnegoAttributeName;
        return this;
    }

    @Generated
    public SpnegoProperties setNtlm(boolean ntlm) {
        this.ntlm = ntlm;
        return this;
    }

    @Generated
    public SpnegoProperties setMixedModeAuthentication(boolean mixedModeAuthentication) {
        this.mixedModeAuthentication = mixedModeAuthentication;
        return this;
    }

    @Generated
    public SpnegoProperties setSupportedBrowsers(String supportedBrowsers) {
        this.supportedBrowsers = supportedBrowsers;
        return this;
    }

    @Generated
    public SpnegoProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public SpnegoProperties setPrincipal(PersonDirectoryPrincipalResolverProperties principal) {
        this.principal = principal;
        return this;
    }

    @Generated
    public SpnegoProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public SpnegoProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public SpnegoProperties setWebflow(WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }

    @Generated
    public SpnegoProperties setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    @Generated
    public SpnegoProperties setPoolTimeout(String poolTimeout) {
        this.poolTimeout = poolTimeout;
        return this;
    }
}

