/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationDiscoverySelectionProperties;
import org.apereo.cas.configuration.model.support.replication.SessionReplicationProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jDelegatedAuthenticationCoreProperties")
public class Pac4jDelegatedAuthenticationCoreProperties
implements Serializable {
    private static final long serialVersionUID = -3561947621312270068L;
    private boolean typedIdUsed;
    private String principalAttributeId;
    private boolean lazyInit = true;
    private String name;
    private Integer order;
    @DurationCapable
    private String cacheDuration = "PT8H";
    private long cacheSize = 100L;
    @NestedConfigurationProperty
    private SessionReplicationProperties sessionReplication = new SessionReplicationProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovyRedirectionStrategy = new SpringResourceProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovyProviderPostProcessor = new SpringResourceProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovyAuthenticationRequestCustomizer = new SpringResourceProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationDiscoverySelectionProperties discoverySelection = new Pac4jDelegatedAuthenticationDiscoverySelectionProperties();

    @Generated
    public boolean isTypedIdUsed() {
        return this.typedIdUsed;
    }

    @Generated
    public String getPrincipalAttributeId() {
        return this.principalAttributeId;
    }

    @Generated
    public boolean isLazyInit() {
        return this.lazyInit;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public String getCacheDuration() {
        return this.cacheDuration;
    }

    @Generated
    public long getCacheSize() {
        return this.cacheSize;
    }

    @Generated
    public SessionReplicationProperties getSessionReplication() {
        return this.sessionReplication;
    }

    @Generated
    public SpringResourceProperties getGroovyRedirectionStrategy() {
        return this.groovyRedirectionStrategy;
    }

    @Generated
    public SpringResourceProperties getGroovyProviderPostProcessor() {
        return this.groovyProviderPostProcessor;
    }

    @Generated
    public SpringResourceProperties getGroovyAuthenticationRequestCustomizer() {
        return this.groovyAuthenticationRequestCustomizer;
    }

    @Generated
    public Pac4jDelegatedAuthenticationDiscoverySelectionProperties getDiscoverySelection() {
        return this.discoverySelection;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setTypedIdUsed(boolean typedIdUsed) {
        this.typedIdUsed = typedIdUsed;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setPrincipalAttributeId(String principalAttributeId) {
        this.principalAttributeId = principalAttributeId;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setCacheDuration(String cacheDuration) {
        this.cacheDuration = cacheDuration;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setSessionReplication(SessionReplicationProperties sessionReplication) {
        this.sessionReplication = sessionReplication;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setGroovyRedirectionStrategy(SpringResourceProperties groovyRedirectionStrategy) {
        this.groovyRedirectionStrategy = groovyRedirectionStrategy;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setGroovyProviderPostProcessor(SpringResourceProperties groovyProviderPostProcessor) {
        this.groovyProviderPostProcessor = groovyProviderPostProcessor;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setGroovyAuthenticationRequestCustomizer(SpringResourceProperties groovyAuthenticationRequestCustomizer) {
        this.groovyAuthenticationRequestCustomizer = groovyAuthenticationRequestCustomizer;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties setDiscoverySelection(Pac4jDelegatedAuthenticationDiscoverySelectionProperties discoverySelection) {
        this.discoverySelection = discoverySelection;
        return this;
    }
}

