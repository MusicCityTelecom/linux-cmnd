/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.logout.slo.SingleLogoutMessage
 *  org.apereo.cas.logout.slo.SingleLogoutMessage$SingleLogoutMessageBuilder
 *  org.apereo.cas.logout.slo.SingleLogoutMessageCreator
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.util.CompressionUtils
 *  org.apereo.cas.util.DefaultUniqueTicketIdGenerator
 *  org.apereo.cas.util.ISOStandardDateFormat
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.logout;

import lombok.Generated;
import org.apereo.cas.logout.slo.SingleLogoutMessage;
import org.apereo.cas.logout.slo.SingleLogoutMessageCreator;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.util.CompressionUtils;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.apereo.cas.util.ISOStandardDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSingleLogoutMessageCreator
implements SingleLogoutMessageCreator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSingleLogoutMessageCreator.class);
    private static final UniqueTicketIdGenerator GENERATOR = new DefaultUniqueTicketIdGenerator(18L);

    public SingleLogoutMessage create(SingleLogoutRequestContext request) {
        String logoutRequest = String.format("<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\"%s\" Version=\"2.0\" IssueInstant=\"%s\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">%s</saml:NameID><samlp:SessionIndex>%s</samlp:SessionIndex></samlp:LogoutRequest>", GENERATOR.getNewTicketId("LR"), new ISOStandardDateFormat().getCurrentDateAndTime(), request.getExecutionRequest().getTicketGrantingTicket().getAuthentication().getPrincipal().getId(), request.getTicketId());
        SingleLogoutMessage.SingleLogoutMessageBuilder builder = SingleLogoutMessage.builder();
        if (request.getLogoutType() == RegisteredServiceLogoutType.FRONT_CHANNEL) {
            LOGGER.trace("Attempting to deflate the logout message [{}]", (Object)logoutRequest);
            return builder.payload(CompressionUtils.deflate((String)logoutRequest)).build();
        }
        return builder.payload(logoutRequest).build();
    }
}

