/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.Ordered
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AccountStatusUserDetailsChecker
 *  org.springframework.security.authentication.AuthenticationProvider
 *  org.springframework.security.authentication.BadCredentialsException
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.userdetails.AuthenticationUserDetailsService
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsChecker
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.preauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

public class PreAuthenticatedAuthenticationProvider
implements AuthenticationProvider,
InitializingBean,
Ordered {
    private static final Log logger = LogFactory.getLog(PreAuthenticatedAuthenticationProvider.class);
    private AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> preAuthenticatedUserDetailsService;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private boolean throwExceptionWhenTokenRejected;
    private int order = -1;

    public void afterPropertiesSet() {
        Assert.notNull(this.preAuthenticatedUserDetailsService, (String)"An AuthenticationUserDetailsService must be set");
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass())) {
            return null;
        }
        logger.debug((Object)LogMessage.format((String)"PreAuthenticated authentication request: %s", (Object)authentication));
        if (authentication.getPrincipal() == null) {
            logger.debug((Object)"No pre-authenticated principal found in request.");
            if (this.throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException("No pre-authenticated principal found in request.");
            }
            return null;
        }
        if (authentication.getCredentials() == null) {
            logger.debug((Object)"No pre-authenticated credentials found in request.");
            if (this.throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException("No pre-authenticated credentials found in request.");
            }
            return null;
        }
        UserDetails userDetails = this.preAuthenticatedUserDetailsService.loadUserDetails((Authentication)((PreAuthenticatedAuthenticationToken)authentication));
        this.userDetailsChecker.check(userDetails);
        PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    public final boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setPreAuthenticatedUserDetailsService(AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> uds) {
        this.preAuthenticatedUserDetailsService = uds;
    }

    public void setThrowExceptionWhenTokenRejected(boolean throwExceptionWhenTokenRejected) {
        this.throwExceptionWhenTokenRejected = throwExceptionWhenTokenRejected;
    }

    public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
        Assert.notNull((Object)userDetailsChecker, (String)"userDetailsChecker cannot be null");
        this.userDetailsChecker = userDetailsChecker;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }
}

