/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Response
 *  org.apereo.cas.authentication.principal.Response$ResponseType
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.web.UrlValidator
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.principal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.AbstractWebApplicationServiceResponseBuilder;
import org.apereo.cas.authentication.principal.Response;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.UrlValidator;
import org.springframework.util.StringUtils;

public class WebApplicationServiceResponseBuilder
extends AbstractWebApplicationServiceResponseBuilder {
    private static final long serialVersionUID = -851233878780818494L;
    private int order = Integer.MAX_VALUE;

    public WebApplicationServiceResponseBuilder(ServicesManager servicesManager, UrlValidator urlValidator) {
        super(servicesManager, urlValidator);
    }

    public Response build(WebApplicationService service, String serviceTicketId, Authentication authentication) {
        WebApplicationService finalService;
        Response.ResponseType responseType;
        HashMap<String, String> parameters = new HashMap<String, String>();
        if (StringUtils.hasText((String)serviceTicketId)) {
            parameters.put("ticket", serviceTicketId);
        }
        if ((responseType = this.getWebApplicationServiceResponseType(finalService = this.buildInternal(service, parameters))) == Response.ResponseType.POST) {
            return this.buildPost(finalService, parameters);
        }
        if (responseType == Response.ResponseType.REDIRECT) {
            return this.buildRedirect(finalService, parameters);
        }
        if (responseType == Response.ResponseType.HEADER) {
            return this.buildHeader(finalService, parameters);
        }
        throw new IllegalArgumentException("Response type is invalid. Only " + Arrays.toString(Response.ResponseType.values()) + " are supported");
    }

    protected WebApplicationService buildInternal(WebApplicationService service, Map<String, String> parameters) {
        return service;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof WebApplicationServiceResponseBuilder)) {
            return false;
        }
        WebApplicationServiceResponseBuilder other = (WebApplicationServiceResponseBuilder)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.order == other.order;
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof WebApplicationServiceResponseBuilder;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.order;
        return result;
    }

    @Override
    @Generated
    public int getOrder() {
        return this.order;
    }

    @Override
    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

