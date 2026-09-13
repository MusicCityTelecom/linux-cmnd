/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.rest.factory;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.rest.factory.TicketGrantingTicketResourceEntityResponseFactory;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.inspektr.audit.annotation.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class DefaultTicketGrantingTicketResourceEntityResponseFactory
implements TicketGrantingTicketResourceEntityResponseFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTicketGrantingTicketResourceEntityResponseFactory.class);
    private static final String DOCTYPE_AND_TITLE = "<!DOCTYPE HTML PUBLIC \\\"-//IETF//DTD HTML 2.0//EN\\\"><html><head><title>";
    private static final String CLOSE_TITLE_AND_OPEN_FORM = "</title></head><body><h1>TGT Created</h1><form action=\"";
    private static final String TGT_CREATED_TITLE_CONTENT = HttpStatus.CREATED.toString();
    private static final String DOCTYPE_AND_OPENING_FORM = "<!DOCTYPE HTML PUBLIC \\\"-//IETF//DTD HTML 2.0//EN\\\"><html><head><title>" + TGT_CREATED_TITLE_CONTENT + "</title></head><body><h1>TGT Created</h1><form action=\"";
    private static final String REST_OF_THE_FORM_AND_CLOSING_TAGS = "\" method=\"POST\">Service:<input type=\"text\" name=\"service\" value=\"\"><br><input type=\"submit\" value=\"Submit\"></form></body></html>";

    private static String getResponse(TicketGrantingTicket ticketGrantingTicket, HttpServletRequest request, URI ticketReference, HttpHeaders headers) {
        if (DefaultTicketGrantingTicketResourceEntityResponseFactory.isDefaultContentType(request)) {
            headers.setContentType(MediaType.TEXT_HTML);
            String tgtUrl = ticketReference.toString();
            return DOCTYPE_AND_OPENING_FORM + tgtUrl + REST_OF_THE_FORM_AND_CLOSING_TAGS;
        }
        return ticketGrantingTicket.getId();
    }

    private static boolean isDefaultContentType(HttpServletRequest request) {
        String header = request.getHeader("Accept");
        String accept = StringUtils.defaultString((String)header);
        return StringUtils.isBlank((CharSequence)accept) || accept.startsWith("*/*") || accept.startsWith("text/html");
    }

    @Override
    @Audit(action="REST_API_TICKET_GRANTING_TICKET", actionResolverName="REST_API_TICKET_GRANTING_TICKET_ACTION_RESOLVER", resourceResolverName="REST_API_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER")
    public ResponseEntity<String> build(TicketGrantingTicket ticketGrantingTicket, HttpServletRequest request) throws Exception {
        URI ticketReference = new URI(request.getRequestURL().toString() + "/" + ticketGrantingTicket.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ticketReference);
        String response = DefaultTicketGrantingTicketResourceEntityResponseFactory.getResponse(ticketGrantingTicket, request, ticketReference, headers);
        ResponseEntity entity = new ResponseEntity((Object)response, (MultiValueMap)headers, HttpStatus.CREATED);
        LOGGER.debug("Created response entity [{}]", (Object)entity);
        return entity;
    }
}

