/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.support.MessageSourceAccessor
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.authentication.AuthenticationServiceException
 *  org.springframework.security.authentication.BadCredentialsException
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.SpringSecurityMessageSource
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.core.userdetails.UserCache
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.core.userdetails.UsernameNotFoundException
 *  org.springframework.security.core.userdetails.cache.NullUserCache
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.authentication.www;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.DigestAuthUtils;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class DigestAuthenticationFilter
extends GenericFilterBean
implements MessageSourceAware {
    private static final Log logger = LogFactory.getLog(DigestAuthenticationFilter.class);
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private DigestAuthenticationEntryPoint authenticationEntryPoint;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserCache userCache = new NullUserCache();
    private UserDetailsService userDetailsService;
    private boolean passwordAlreadyEncoded = false;
    private boolean createAuthenticatedToken = false;
    private SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.userDetailsService, (String)"A UserDetailsService is required");
        Assert.notNull((Object)this.authenticationEntryPoint, (String)"A DigestAuthenticationEntryPoint is required");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String serverDigestMd5;
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Digest ")) {
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        logger.debug((Object)LogMessage.format((String)"Digest Authorization header received from user agent: %s", (Object)header));
        DigestData digestAuth = new DigestData(header);
        try {
            digestAuth.validateAndDecode(this.authenticationEntryPoint.getKey(), this.authenticationEntryPoint.getRealmName());
        }
        catch (BadCredentialsException ex) {
            this.fail(request, response, (AuthenticationException)((Object)ex));
            return;
        }
        boolean cacheWasUsed = true;
        UserDetails user = this.userCache.getUserFromCache(digestAuth.getUsername());
        try {
            if (user == null) {
                cacheWasUsed = false;
                user = this.userDetailsService.loadUserByUsername(digestAuth.getUsername());
                if (user == null) {
                    throw new AuthenticationServiceException("AuthenticationDao returned null, which is an interface contract violation");
                }
                this.userCache.putUserInCache(user);
            }
            if (!(serverDigestMd5 = digestAuth.calculateServerDigest(user.getPassword(), request.getMethod())).equals(digestAuth.getResponse()) && cacheWasUsed) {
                logger.debug((Object)"Digest comparison failure; trying to refresh user from DAO in case password had changed");
                user = this.userDetailsService.loadUserByUsername(digestAuth.getUsername());
                this.userCache.putUserInCache(user);
                serverDigestMd5 = digestAuth.calculateServerDigest(user.getPassword(), request.getMethod());
            }
        }
        catch (UsernameNotFoundException ex) {
            String message = this.messages.getMessage("DigestAuthenticationFilter.usernameNotFound", new Object[]{digestAuth.getUsername()}, "Username {0} not found");
            this.fail(request, response, (AuthenticationException)((Object)new BadCredentialsException(message)));
            return;
        }
        if (!serverDigestMd5.equals(digestAuth.getResponse())) {
            logger.debug((Object)LogMessage.format((String)"Expected response: '%s' but received: '%s'; is AuthenticationDao returning clear text passwords?", (Object)serverDigestMd5, (Object)digestAuth.getResponse()));
            String message = this.messages.getMessage("DigestAuthenticationFilter.incorrectResponse", "Incorrect response");
            this.fail(request, response, (AuthenticationException)((Object)new BadCredentialsException(message)));
            return;
        }
        if (digestAuth.isNonceExpired()) {
            String message = this.messages.getMessage("DigestAuthenticationFilter.nonceExpired", "Nonce has expired/timed out");
            this.fail(request, response, new NonceExpiredException(message));
            return;
        }
        logger.debug((Object)LogMessage.format((String)"Authentication success for user: '%s' with response: '%s'", (Object)digestAuth.getUsername(), (Object)digestAuth.getResponse()));
        Authentication authentication = this.createSuccessfulAuthentication(request, user);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext((SecurityContext)context);
        this.securityContextRepository.saveContext(context, request, response);
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    private Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
        UsernamePasswordAuthenticationToken authRequest = this.getAuthRequest(user);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
        return authRequest;
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(UserDetails user) {
        if (this.createAuthenticatedToken) {
            return UsernamePasswordAuthenticationToken.authenticated((Object)user, (Object)user.getPassword(), (Collection)user.getAuthorities());
        }
        return UsernamePasswordAuthenticationToken.unauthenticated((Object)user, (Object)user.getPassword());
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext((SecurityContext)context);
        logger.debug((Object)failed);
        this.authenticationEntryPoint.commence(request, response, failed);
    }

    protected final DigestAuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    public UserCache getUserCache() {
        return this.userCache;
    }

    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, (String)"AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setAuthenticationEntryPoint(DigestAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setPasswordAlreadyEncoded(boolean passwordAlreadyEncoded) {
        this.passwordAlreadyEncoded = passwordAlreadyEncoded;
    }

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setCreateAuthenticatedToken(boolean createAuthenticatedToken) {
        this.createAuthenticatedToken = createAuthenticatedToken;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    private class DigestData {
        private final String username;
        private final String realm;
        private final String nonce;
        private final String uri;
        private final String response;
        private final String qop;
        private final String nc;
        private final String cnonce;
        private final String section212response;
        private long nonceExpiryTime;

        DigestData(String header) {
            this.section212response = header.substring(7);
            String[] headerEntries = DigestAuthUtils.splitIgnoringQuotes(this.section212response, ',');
            Map<String, String> headerMap = DigestAuthUtils.splitEachArrayElementAndCreateMap(headerEntries, "=", "\"");
            this.username = headerMap.get("username");
            this.realm = headerMap.get("realm");
            this.nonce = headerMap.get("nonce");
            this.uri = headerMap.get("uri");
            this.response = headerMap.get("response");
            this.qop = headerMap.get("qop");
            this.nc = headerMap.get("nc");
            this.cnonce = headerMap.get("cnonce");
            logger.debug((Object)LogMessage.format((String)"Extracted username: '%s'; realm: '%s'; nonce: '%s'; uri: '%s'; response: '%s'", (Object[])new Object[]{this.username, this.realm, this.nonce, this.uri, this.response}));
        }

        void validateAndDecode(String entryPointKey, String expectedRealm) throws BadCredentialsException {
            byte[] nonceBytes;
            if (this.username == null || this.realm == null || this.nonce == null || this.uri == null || this.response == null) {
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.missingMandatory", new Object[]{this.section212response}, "Missing mandatory digest value; received header {0}"));
            }
            if ("auth".equals(this.qop) && (this.nc == null || this.cnonce == null)) {
                logger.debug((Object)LogMessage.format((String)"extracted nc: '%s'; cnonce: '%s'", (Object)this.nc, (Object)this.cnonce));
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.missingAuth", new Object[]{this.section212response}, "Missing mandatory digest value; received header {0}"));
            }
            if (!expectedRealm.equals(this.realm)) {
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.incorrectRealm", new Object[]{this.realm, expectedRealm}, "Response realm name '{0}' does not match system realm name of '{1}'"));
            }
            try {
                nonceBytes = Base64.getDecoder().decode(this.nonce.getBytes());
            }
            catch (IllegalArgumentException ex) {
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.nonceEncoding", new Object[]{this.nonce}, "Nonce is not encoded in Base64; received nonce {0}"));
            }
            String nonceAsPlainText = new String(nonceBytes);
            String[] nonceTokens = StringUtils.delimitedListToStringArray((String)nonceAsPlainText, (String)":");
            if (nonceTokens.length != 2) {
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.nonceNotTwoTokens", new Object[]{nonceAsPlainText}, "Nonce should have yielded two tokens but was {0}"));
            }
            try {
                this.nonceExpiryTime = new Long(nonceTokens[0]);
            }
            catch (NumberFormatException nfe) {
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.nonceNotNumeric", new Object[]{nonceAsPlainText}, "Nonce token should have yielded a numeric first token, but was {0}"));
            }
            String expectedNonceSignature = DigestAuthUtils.md5Hex(this.nonceExpiryTime + ":" + entryPointKey);
            if (!expectedNonceSignature.equals(nonceTokens[1])) {
                throw new BadCredentialsException(DigestAuthenticationFilter.this.messages.getMessage("DigestAuthenticationFilter.nonceCompromised", new Object[]{nonceAsPlainText}, "Nonce token compromised {0}"));
            }
        }

        String calculateServerDigest(String password, String httpMethod) {
            return DigestAuthUtils.generateDigest(DigestAuthenticationFilter.this.passwordAlreadyEncoded, this.username, this.realm, password, httpMethod, this.uri, this.qop, this.nonce, this.nc, this.cnonce);
        }

        boolean isNonceExpired() {
            long now = System.currentTimeMillis();
            return this.nonceExpiryTime < now;
        }

        String getUsername() {
            return this.username;
        }

        String getResponse() {
            return this.response;
        }
    }
}

