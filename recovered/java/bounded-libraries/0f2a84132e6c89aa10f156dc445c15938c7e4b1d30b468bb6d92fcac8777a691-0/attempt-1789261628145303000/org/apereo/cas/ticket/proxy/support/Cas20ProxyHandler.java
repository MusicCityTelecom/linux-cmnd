/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.credential.HttpBasedServiceCredential
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.ticket.proxy.ProxyHandler
 *  org.apereo.cas.util.http.HttpClient
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.ticket.proxy.support;

import java.net.URL;
import lombok.Generated;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.credential.HttpBasedServiceCredential;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.proxy.ProxyHandler;
import org.apereo.cas.util.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cas20ProxyHandler
implements ProxyHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(Cas20ProxyHandler.class);
    private static final int BUFFER_LENGTH_ADDITIONAL_CHARGE = 15;
    private final HttpClient httpClient;
    private final UniqueTicketIdGenerator uniqueTicketIdGenerator;

    public String handle(Credential credential, TicketGrantingTicket proxyGrantingTicketId) {
        HttpBasedServiceCredential serviceCredentials = (HttpBasedServiceCredential)credential;
        String proxyIou = this.uniqueTicketIdGenerator.getNewTicketId("PGTIOU");
        URL callbackUrl = serviceCredentials.getCallbackUrl();
        String serviceCredentialsAsString = callbackUrl.toExternalForm();
        int bufferLength = serviceCredentialsAsString.length() + proxyIou.length() + proxyGrantingTicketId.getId().length() + 15;
        StringBuilder stringBuffer = new StringBuilder(bufferLength).append(serviceCredentialsAsString);
        if (callbackUrl.getQuery() != null) {
            stringBuffer.append('&');
        } else {
            stringBuffer.append('?');
        }
        stringBuffer.append("pgtIou").append('=').append(proxyIou).append('&').append("pgtId").append('=').append(proxyGrantingTicketId);
        if (this.httpClient.isValidEndPoint(stringBuffer.toString())) {
            LOGGER.debug("Sent ProxyIou of [{}] for service: [{}]", (Object)proxyIou, (Object)serviceCredentials);
            return proxyIou;
        }
        LOGGER.debug("Failed to send ProxyIou of [{}] for service: [{}]", (Object)proxyIou, (Object)serviceCredentials);
        return null;
    }

    public boolean canHandle(Credential credential) {
        return true;
    }

    @Generated
    public Cas20ProxyHandler(HttpClient httpClient, UniqueTicketIdGenerator uniqueTicketIdGenerator) {
        this.httpClient = httpClient;
        this.uniqueTicketIdGenerator = uniqueTicketIdGenerator;
    }
}

