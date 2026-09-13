/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.support.cookie.CookieProperties
 *  org.apereo.cas.configuration.model.support.cookie.TicketGrantingCookieProperties
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.cas.ticket.registry.TicketRegistry
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.web.cookie.CasCookieBuilder
 *  org.apereo.cas.web.cookie.CookieGenerationContext
 *  org.apereo.cas.web.cookie.CookieGenerationContext$CookieGenerationContextBuilder
 */
package org.apereo.cas.web.support;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.support.cookie.CookieProperties;
import org.apereo.cas.configuration.model.support.cookie.TicketGrantingCookieProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.web.cookie.CasCookieBuilder;
import org.apereo.cas.web.cookie.CookieGenerationContext;
import org.apereo.cas.web.support.gen.CookieRetrievingCookieGenerator;

public final class CookieUtils {
    public static CasCookieBuilder buildCookieRetrievingGenerator(CookieProperties cookie) {
        CookieGenerationContext context = CookieUtils.buildCookieGenerationContext(cookie);
        return new CookieRetrievingCookieGenerator(context);
    }

    public static TicketGrantingTicket getTicketGrantingTicketFromRequest(CasCookieBuilder ticketGrantingTicketCookieGenerator, TicketRegistry ticketRegistry, HttpServletRequest request) {
        String cookieValue = ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
        if (StringUtils.isNotBlank((CharSequence)cookieValue)) {
            return (TicketGrantingTicket)FunctionUtils.doAndHandle(() -> {
                TicketGrantingTicket state = (TicketGrantingTicket)ticketRegistry.getTicket(cookieValue, TicketGrantingTicket.class);
                return state == null || state.isExpired() ? null : state;
            });
        }
        return null;
    }

    public static Optional<Cookie> getCookieFromRequest(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies).filter(c -> c.getName().equalsIgnoreCase(cookieName)).findFirst();
    }

    public static CookieGenerationContext buildCookieGenerationContext(CookieProperties cookie) {
        return CookieUtils.buildCookieGenerationContextBuilder(cookie).build();
    }

    public static CookieGenerationContext buildCookieGenerationContext(TicketGrantingCookieProperties cookie) {
        int rememberMeMaxAge = (int)Beans.newDuration((String)cookie.getRememberMeMaxAge()).getSeconds();
        CookieGenerationContext.CookieGenerationContextBuilder builder = CookieUtils.buildCookieGenerationContextBuilder((CookieProperties)cookie);
        return builder.rememberMeMaxAge(rememberMeMaxAge).build();
    }

    private static CookieGenerationContext.CookieGenerationContextBuilder buildCookieGenerationContextBuilder(CookieProperties cookie) {
        return CookieGenerationContext.builder().name(cookie.getName()).path(StringUtils.defaultString((String)cookie.getPath(), (String)"/")).maxAge(cookie.getMaxAge()).secure(cookie.isSecure()).domain(cookie.getDomain()).comment(cookie.getComment()).sameSitePolicy(cookie.getSameSitePolicy()).httpOnly(cookie.isHttpOnly());
    }

    @Generated
    private CookieUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

