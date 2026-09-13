/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Set;
import org.apereo.cas.services.RegisteredServiceProxyGrantingTicketExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegisteredServiceProxyTicketExpirationPolicy;
import org.apereo.cas.services.RegisteredServiceServiceTicketExpirationPolicy;
import org.apereo.cas.services.WebBasedRegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface CasModelRegisteredService
extends WebBasedRegisteredService {
    public RegisteredServiceProxyPolicy getProxyPolicy();

    public RegisteredServiceProxyGrantingTicketExpirationPolicy getProxyGrantingTicketExpirationPolicy();

    public RegisteredServiceServiceTicketExpirationPolicy getServiceTicketExpirationPolicy();

    public RegisteredServiceProxyTicketExpirationPolicy getProxyTicketExpirationPolicy();

    public String getRedirectUrl();

    public Set<String> getSupportedProtocols();
}

