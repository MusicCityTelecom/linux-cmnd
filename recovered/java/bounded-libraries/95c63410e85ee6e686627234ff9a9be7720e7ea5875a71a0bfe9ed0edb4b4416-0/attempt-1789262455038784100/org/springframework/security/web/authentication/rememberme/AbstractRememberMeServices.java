/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.support.MessageSourceAccessor
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AccountStatusException
 *  org.springframework.security.authentication.AccountStatusUserDetailsChecker
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.authentication.RememberMeAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.SpringSecurityMessageSource
 *  org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
 *  org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsChecker
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.core.userdetails.UsernameNotFoundException
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.authentication.rememberme;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractRememberMeServices
implements RememberMeServices,
InitializingBean,
LogoutHandler,
MessageSourceAware {
    public static final String SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY = "remember-me";
    public static final String DEFAULT_PARAMETER = "remember-me";
    public static final int TWO_WEEKS_S = 1209600;
    private static final String DELIMITER = ":";
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsService userDetailsService;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private String cookieName = "remember-me";
    private String cookieDomain;
    private String parameter = "remember-me";
    private boolean alwaysRemember;
    private String key;
    private int tokenValiditySeconds = 1209600;
    private Boolean useSecureCookie = null;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    protected AbstractRememberMeServices(String key, UserDetailsService userDetailsService) {
        Assert.hasLength((String)key, (String)"key cannot be empty or null");
        Assert.notNull((Object)userDetailsService, (String)"UserDetailsService cannot be null");
        this.key = key;
        this.userDetailsService = userDetailsService;
    }

    public void afterPropertiesSet() {
        Assert.hasLength((String)this.key, (String)"key cannot be empty or null");
        Assert.notNull((Object)this.userDetailsService, (String)"A UserDetailsService is required");
    }

    @Override
    public final Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeCookie = this.extractRememberMeCookie(request);
        if (rememberMeCookie == null) {
            return null;
        }
        this.logger.debug((Object)"Remember-me cookie detected");
        if (rememberMeCookie.length() == 0) {
            this.logger.debug((Object)"Cookie was empty");
            this.cancelCookie(request, response);
            return null;
        }
        try {
            String[] cookieTokens = this.decodeCookie(rememberMeCookie);
            UserDetails user = this.processAutoLoginCookie(cookieTokens, request, response);
            this.userDetailsChecker.check(user);
            this.logger.debug((Object)"Remember-me cookie accepted");
            return this.createSuccessfulAuthentication(request, user);
        }
        catch (CookieTheftException ex) {
            this.cancelCookie(request, response);
            throw ex;
        }
        catch (UsernameNotFoundException ex) {
            this.logger.debug((Object)"Remember-me login was valid but corresponding user not found.", (Throwable)ex);
        }
        catch (InvalidCookieException ex) {
            this.logger.debug((Object)("Invalid remember-me cookie: " + ex.getMessage()));
        }
        catch (AccountStatusException ex) {
            this.logger.debug((Object)("Invalid UserDetails: " + ex.getMessage()));
        }
        catch (RememberMeAuthenticationException ex) {
            this.logger.debug((Object)ex.getMessage());
        }
        this.cancelCookie(request, response);
        return null;
    }

    protected String extractRememberMeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (!this.cookieName.equals(cookie.getName())) continue;
            return cookie.getValue();
        }
        return null;
    }

    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(this.key, (Object)user, this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        auth.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
        return auth;
    }

    protected String[] decodeCookie(String cookieValue) throws InvalidCookieException {
        String cookieAsPlainText;
        for (int j = 0; j < cookieValue.length() % 4; ++j) {
            cookieValue = cookieValue + "=";
        }
        try {
            cookieAsPlainText = new String(Base64.getDecoder().decode(cookieValue.getBytes()));
        }
        catch (IllegalArgumentException ex) {
            throw new InvalidCookieException("Cookie token was not Base64 encoded; value was '" + cookieValue + "'");
        }
        String[] tokens = StringUtils.delimitedListToStringArray((String)cookieAsPlainText, (String)DELIMITER);
        for (int i = 0; i < tokens.length; ++i) {
            try {
                tokens[i] = URLDecoder.decode(tokens[i], StandardCharsets.UTF_8.toString());
                continue;
            }
            catch (UnsupportedEncodingException ex) {
                this.logger.error((Object)ex.getMessage(), (Throwable)ex);
            }
        }
        return tokens;
    }

    protected String encodeCookie(String[] cookieTokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookieTokens.length; ++i) {
            try {
                sb.append(URLEncoder.encode(cookieTokens[i], StandardCharsets.UTF_8.toString()));
            }
            catch (UnsupportedEncodingException ex) {
                this.logger.error((Object)ex.getMessage(), (Throwable)ex);
            }
            if (i >= cookieTokens.length - 1) continue;
            sb.append(DELIMITER);
        }
        String value = sb.toString();
        sb = new StringBuilder(new String(Base64.getEncoder().encode(value.getBytes())));
        while (sb.charAt(sb.length() - 1) == '=') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public final void loginFail(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug((Object)"Interactive login attempt was unsuccessful.");
        this.cancelCookie(request, response);
        this.onLoginFail(request, response);
    }

    protected void onLoginFail(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public final void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        if (!this.rememberMeRequested(request, this.parameter)) {
            this.logger.debug((Object)"Remember-me login not requested.");
            return;
        }
        this.onLoginSuccess(request, response, successfulAuthentication);
    }

    protected abstract void onLoginSuccess(HttpServletRequest var1, HttpServletResponse var2, Authentication var3);

    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        if (this.alwaysRemember) {
            return true;
        }
        String paramValue = request.getParameter(parameter);
        if (paramValue != null && (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on") || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1"))) {
            return true;
        }
        this.logger.debug((Object)LogMessage.format((String)"Did not send remember-me cookie (principal did not set parameter '%s')", (Object)parameter));
        return false;
    }

    protected abstract UserDetails processAutoLoginCookie(String[] var1, HttpServletRequest var2, HttpServletResponse var3) throws RememberMeAuthenticationException, UsernameNotFoundException;

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug((Object)"Cancelling cookie");
        Cookie cookie = new Cookie(this.cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(this.getCookiePath(request));
        if (this.cookieDomain != null) {
            cookie.setDomain(this.cookieDomain);
        }
        cookie.setSecure(this.useSecureCookie != null ? this.useSecureCookie.booleanValue() : request.isSecure());
        response.addCookie(cookie);
    }

    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        String cookieValue = this.encodeCookie(tokens);
        Cookie cookie = new Cookie(this.cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(this.getCookiePath(request));
        if (this.cookieDomain != null) {
            cookie.setDomain(this.cookieDomain);
        }
        if (maxAge < 1) {
            cookie.setVersion(1);
        }
        cookie.setSecure(this.useSecureCookie != null ? this.useSecureCookie.booleanValue() : request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        this.logger.debug((Object)LogMessage.of(() -> "Logout of user " + (authentication != null ? authentication.getName() : "Unknown")));
        this.cancelCookie(request, response);
    }

    public void setCookieName(String cookieName) {
        Assert.hasLength((String)cookieName, (String)"Cookie name cannot be empty or null");
        this.cookieName = cookieName;
    }

    public void setCookieDomain(String cookieDomain) {
        Assert.hasLength((String)cookieDomain, (String)"Cookie domain cannot be empty or null");
        this.cookieDomain = cookieDomain;
    }

    protected String getCookieName() {
        return this.cookieName;
    }

    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

    public void setParameter(String parameter) {
        Assert.hasText((String)parameter, (String)"Parameter name cannot be empty or null");
        this.parameter = parameter;
    }

    public String getParameter() {
        return this.parameter;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public String getKey() {
        return this.key;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    protected int getTokenValiditySeconds() {
        return this.tokenValiditySeconds;
    }

    public void setUseSecureCookie(boolean useSecureCookie) {
        this.useSecureCookie = useSecureCookie;
    }

    protected AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return this.authenticationDetailsSource;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, (String)"AuthenticationDetailsSource cannot be null");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
        this.userDetailsChecker = userDetailsChecker;
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull((Object)messageSource, (String)"messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

