/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.CoreAuthenticationUtils
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.web.cookie.CasCookieBuilder
 *  org.apereo.cas.web.cookie.CookieGenerationContext
 *  org.apereo.cas.web.cookie.CookieSameSitePolicy
 *  org.apereo.cas.web.cookie.CookieValueManager
 *  org.apereo.cas.web.support.WebUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.util.CookieGenerator
 *  org.springframework.web.util.WebUtils
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.web.support.gen;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.web.cookie.CasCookieBuilder;
import org.apereo.cas.web.cookie.CookieGenerationContext;
import org.apereo.cas.web.cookie.CookieSameSitePolicy;
import org.apereo.cas.web.cookie.CookieValueManager;
import org.apereo.cas.web.support.InvalidCookieException;
import org.apereo.cas.web.support.WebUtils;
import org.apereo.cas.web.support.mgmr.NoOpCookieValueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.CookieGenerator;
import org.springframework.webflow.execution.RequestContext;

public class CookieRetrievingCookieGenerator
extends CookieGenerator
implements Serializable,
CasCookieBuilder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieRetrievingCookieGenerator.class);
    private static final long serialVersionUID = -4926982428809856313L;
    private final CookieValueManager casCookieValueManager;
    private final CookieGenerationContext cookieGenerationContext;

    public CookieRetrievingCookieGenerator(CookieGenerationContext context) {
        this(context, NoOpCookieValueManager.INSTANCE);
    }

    public CookieRetrievingCookieGenerator(CookieGenerationContext context, CookieValueManager casCookieValueManager) {
        super.setCookieName(context.getName());
        super.setCookiePath(context.getPath());
        super.setCookieMaxAge(Integer.valueOf(context.getMaxAge()));
        super.setCookieSecure(context.isSecure());
        super.setCookieHttpOnly(context.isHttpOnly());
        this.setCookieDomain(context.getDomain());
        this.cookieGenerationContext = context;
        this.casCookieValueManager = casCookieValueManager;
    }

    public static Boolean isRememberMeAuthentication(RequestContext requestContext) {
        if (CookieRetrievingCookieGenerator.isRememberMeProvidedInRequest(requestContext)) {
            LOGGER.debug("This request is from a remember-me authentication event");
            return Boolean.TRUE;
        }
        Authentication authn = WebUtils.getAuthentication((RequestContext)requestContext);
        if (CoreAuthenticationUtils.isRememberMeAuthentication((Authentication)authn).booleanValue()) {
            LOGGER.debug("The recorded authentication is from a remember-me request");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static boolean isRememberMeProvidedInRequest(RequestContext requestContext) {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext((RequestContext)requestContext);
        String value = request.getParameter("rememberMe");
        LOGGER.trace("Locating request parameter [{}] with value [{}]", (Object)"rememberMe", (Object)value);
        return StringUtils.isNotBlank((CharSequence)value) && WebUtils.isRememberMeAuthenticationEnabled((RequestContext)requestContext) != false;
    }

    private String cleanCookiePath(String givenPath) {
        return (String)FunctionUtils.doIf((boolean)StringUtils.isBlank((CharSequence)this.cookieGenerationContext.getPath()), () -> {
            String path = StringUtils.removeEndIgnoreCase((String)((String)StringUtils.defaultIfBlank((CharSequence)givenPath, (CharSequence)"/")), (String)"/");
            return (String)StringUtils.defaultIfBlank((CharSequence)path, (CharSequence)"/");
        }, () -> givenPath).get();
    }

    public void setCookieDomain(String cookieDomain) {
        super.setCookieDomain((String)StringUtils.defaultIfEmpty((CharSequence)cookieDomain, null));
    }

    public Cookie addCookie(HttpServletRequest request, HttpServletResponse response, boolean rememberMe, String cookieValue) {
        String theCookieValue = this.casCookieValueManager.buildCookieValue(cookieValue, request);
        Cookie cookie = this.createCookie(theCookieValue);
        if (rememberMe) {
            LOGGER.trace("Creating CAS cookie [{}] for remember-me authentication", (Object)this.getCookieName());
            cookie.setMaxAge(this.cookieGenerationContext.getRememberMeMaxAge());
            cookie.setComment(String.format("%s Remember-Me", this.cookieGenerationContext.getComment()));
        } else {
            LOGGER.trace("Creating CAS cookie [{}]", (Object)this.getCookieName());
            if (this.getCookieMaxAge() != null) {
                cookie.setMaxAge(this.getCookieMaxAge().intValue());
            }
        }
        cookie.setSecure(this.isCookieSecure());
        cookie.setHttpOnly(this.isCookieHttpOnly());
        return this.addCookieHeaderToResponse(cookie, request, response);
    }

    public Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String cookieValue) {
        return this.addCookie(request, response, false, cookieValue);
    }

    public String retrieveCookieValue(HttpServletRequest request) {
        try {
            String cookieValue;
            if (StringUtils.isBlank((CharSequence)this.getCookieName())) {
                throw new InvalidCookieException("Cookie name is undefined");
            }
            Cookie cookie = org.springframework.web.util.WebUtils.getCookie((HttpServletRequest)request, (String)Objects.requireNonNull(this.getCookieName()));
            if (cookie == null && StringUtils.isNotBlank((CharSequence)(cookieValue = request.getHeader(this.getCookieName())))) {
                LOGGER.trace("Found cookie [{}] under header name [{}]", (Object)cookieValue, (Object)this.getCookieName());
                cookie = this.createCookie(cookieValue);
            }
            if (cookie == null && StringUtils.isNotBlank((CharSequence)(cookieValue = request.getParameter(this.getCookieName())))) {
                LOGGER.trace("Found cookie [{}] under request parameter name [{}]", (Object)cookieValue, (Object)this.getCookieName());
                cookie = this.createCookie(cookieValue);
            }
            return Optional.ofNullable(cookie).map(ck -> this.casCookieValueManager.obtainCookieValue(ck, request)).orElse(null);
        }
        catch (Exception e) {
            LoggingUtils.warn((Logger)LOGGER, (Throwable)e);
            return null;
        }
    }

    public void removeAll(HttpServletRequest request, HttpServletResponse response) {
        Optional.ofNullable(request.getCookies()).ifPresent(cookies -> Arrays.stream(cookies).filter(c -> StringUtils.equalsIgnoreCase((CharSequence)c.getName(), (CharSequence)this.getCookieName())).forEach(c -> Stream.of("/", this.getCookiePath(), StringUtils.appendIfMissing((String)this.getCookiePath(), (CharSequence)"/", (CharSequence[])new CharSequence[0])).forEach(path -> {
            c.setMaxAge(0);
            c.setPath(path);
            c.setSecure(this.isCookieSecure());
            c.setHttpOnly(this.isCookieHttpOnly());
            c.setComment(this.cookieGenerationContext.getComment());
            LOGGER.debug("Removing cookie [{}] with path [{}]", (Object)c.getName(), (Object)c.getPath());
            response.addCookie(c);
        })));
    }

    protected Cookie createCookie(@NonNull String cookieValue) {
        if (cookieValue == null) {
            throw new NullPointerException("cookieValue is marked non-null but is null");
        }
        Cookie c = super.createCookie(cookieValue);
        c.setComment(this.cookieGenerationContext.getComment());
        c.setPath(this.cleanCookiePath(c.getPath()));
        return c;
    }

    protected Cookie addCookieHeaderToResponse(Cookie cookie, HttpServletRequest request, HttpServletResponse response) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s=%s;", cookie.getName(), cookie.getValue()));
        if (cookie.getMaxAge() > -1) {
            builder.append(String.format(" Max-Age=%s;", cookie.getMaxAge()));
        }
        if (StringUtils.isNotBlank((CharSequence)cookie.getDomain())) {
            builder.append(String.format(" Domain=%s;", cookie.getDomain()));
        }
        String path = this.cleanCookiePath(cookie.getPath());
        builder.append(String.format(" Path=%s;", path));
        Optional sameSiteResult = CookieSameSitePolicy.of((CookieGenerationContext)this.cookieGenerationContext).build(request, response);
        sameSiteResult.ifPresent(result -> builder.append(String.format(" %s", result)));
        String sameSitePolicy = this.cookieGenerationContext.getSameSitePolicy().toLowerCase();
        if (cookie.getSecure() || sameSiteResult.isPresent() && StringUtils.equalsIgnoreCase((CharSequence)((CharSequence)sameSiteResult.get()), (CharSequence)"none")) {
            builder.append(" Secure;");
            LOGGER.trace("Marked cookie [{}] as secure as indicated by cookie configuration or the configured same-site policy set to [{}]", (Object)cookie.getName(), (Object)sameSitePolicy);
        }
        if (cookie.isHttpOnly()) {
            builder.append(" HttpOnly;");
        }
        String value = StringUtils.removeEndIgnoreCase((String)builder.toString(), (String)";");
        LOGGER.trace("Adding cookie header as [{}]", (Object)value);
        Collection setCookieHeaders = response.getHeaders("Set-Cookie");
        response.setHeader("Set-Cookie", value);
        setCookieHeaders.stream().filter(header -> !header.startsWith(cookie.getName() + "=")).forEach(header -> response.addHeader("Set-Cookie", header));
        return cookie;
    }

    @Generated
    public CookieValueManager getCasCookieValueManager() {
        return this.casCookieValueManager;
    }

    @Generated
    public CookieGenerationContext getCookieGenerationContext() {
        return this.cookieGenerationContext;
    }
}

