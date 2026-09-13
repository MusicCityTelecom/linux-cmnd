/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredServiceAccessStrategy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.RegisteredServiceContact;
import org.apereo.cas.services.RegisteredServiceExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceMatchingStrategy;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.apereo.cas.services.RegisteredServiceProperty;
import org.apereo.cas.services.RegisteredServicePublicKey;
import org.apereo.cas.services.RegisteredServiceTicketGrantingTicketExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredService
extends Serializable,
Comparable<RegisteredService> {
    public static final long INITIAL_IDENTIFIER_VALUE = -1L;

    public RegisteredServiceExpirationPolicy getExpirationPolicy();

    public RegisteredServiceAuthenticationPolicy getAuthenticationPolicy();

    public RegisteredServiceMatchingStrategy getMatchingStrategy();

    public RegisteredServicePublicKey getPublicKey();

    public String getServiceId();

    public long getId();

    public void setId(long var1);

    public String getName();

    default public String getDescription() {
        return "";
    }

    public String getResponseType();

    public int getEvaluationOrder();

    public void setEvaluationOrder(int var1);

    public RegisteredServiceUsernameAttributeProvider getUsernameAttributeProvider();

    public RegisteredServiceMultifactorPolicy getMultifactorAuthenticationPolicy();

    public RegisteredServiceTicketGrantingTicketExpirationPolicy getTicketGrantingTicketExpirationPolicy();

    @Deprecated(since="6.2.0")
    public Set<String> getRequiredHandlers();

    public Set<String> getEnvironments();

    public RegisteredServiceAccessStrategy getAccessStrategy();

    public boolean matches(Service var1);

    public boolean matches(String var1);

    public RegisteredServiceAttributeReleasePolicy getAttributeReleasePolicy();

    public Map<String, RegisteredServiceProperty> getProperties();

    public List<RegisteredServiceContact> getContacts();

    @JsonIgnore
    default public int getEvaluationPriority() {
        return Integer.MAX_VALUE;
    }

    @JsonIgnore
    default public String getFriendlyName() {
        return this.getClass().getSimpleName();
    }

    default public void initialize() {
    }
}

