/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.jasig.cas.client.validation.Assertion
 *  org.jasig.cas.client.validation.TicketValidationException
 *  org.jasig.cas.client.validation.TicketValidator
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.support.MessageSourceAccessor
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AccountStatusUserDetailsChecker
 *  org.springframework.security.authentication.AuthenticationProvider
 *  org.springframework.security.authentication.BadCredentialsException
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.SpringSecurityMessageSource
 *  org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
 *  org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
 *  org.springframework.security.core.userdetails.AuthenticationUserDetailsService
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
 *  org.springframework.security.core.userdetails.UserDetailsChecker
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.authentication;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.authentication.NullStatelessTicketCache;
import org.springframework.security.cas.authentication.StatelessTicketCache;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

public class CasAuthenticationProvider
implements AuthenticationProvider,
InitializingBean,
MessageSourceAware {
    private static final Log logger = LogFactory.getLog(CasAuthenticationProvider.class);
    private AuthenticationUserDetailsService<CasAssertionAuthenticationToken> authenticationUserDetailsService;
    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private StatelessTicketCache statelessTicketCache = new NullStatelessTicketCache();
    private String key;
    private TicketValidator ticketValidator;
    private ServiceProperties serviceProperties;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationUserDetailsService, (String)"An authenticationUserDetailsService must be set");
        Assert.notNull((Object)this.ticketValidator, (String)"A ticketValidator must be set");
        Assert.notNull((Object)this.statelessTicketCache, (String)"A statelessTicketCache must be set");
        Assert.hasText((String)this.key, (String)"A Key is required so CasAuthenticationProvider can identify tokens it previously authenticated");
        Assert.notNull((Object)this.messages, (String)"A message source must be set");
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass())) {
            return null;
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken && !"_cas_stateful_".equals(authentication.getPrincipal().toString()) && !"_cas_stateless_".equals(authentication.getPrincipal().toString())) {
            return null;
        }
        if (authentication instanceof CasAuthenticationToken) {
            if (this.key.hashCode() != ((CasAuthenticationToken)authentication).getKeyHash()) {
                throw new BadCredentialsException(this.messages.getMessage("CasAuthenticationProvider.incorrectKey", "The presented CasAuthenticationToken does not contain the expected key"));
            }
            return authentication;
        }
        if (authentication.getCredentials() == null || "".equals(authentication.getCredentials())) {
            throw new BadCredentialsException(this.messages.getMessage("CasAuthenticationProvider.noServiceTicket", "Failed to provide a CAS service ticket to validate"));
        }
        boolean stateless = authentication instanceof UsernamePasswordAuthenticationToken && "_cas_stateless_".equals(authentication.getPrincipal());
        CasAuthenticationToken result = null;
        if (stateless) {
            result = this.statelessTicketCache.getByTicketId(authentication.getCredentials().toString());
        }
        if (result == null) {
            result = this.authenticateNow(authentication);
            result.setDetails(authentication.getDetails());
        }
        if (stateless) {
            this.statelessTicketCache.putTicketInCache(result);
        }
        return result;
    }

    private CasAuthenticationToken authenticateNow(Authentication authentication) throws AuthenticationException {
        try {
            Assertion assertion = this.ticketValidator.validate(authentication.getCredentials().toString(), this.getServiceUrl(authentication));
            UserDetails userDetails = this.loadUserByAssertion(assertion);
            this.userDetailsChecker.check(userDetails);
            return new CasAuthenticationToken(this.key, (Object)userDetails, authentication.getCredentials(), (Collection<? extends GrantedAuthority>)this.authoritiesMapper.mapAuthorities(userDetails.getAuthorities()), userDetails, assertion);
        }
        catch (TicketValidationException ex) {
            throw new BadCredentialsException(ex.getMessage(), (Throwable)ex);
        }
    }

    private String getServiceUrl(Authentication authentication) {
        if (authentication.getDetails() instanceof ServiceAuthenticationDetails) {
            return ((ServiceAuthenticationDetails)authentication.getDetails()).getServiceUrl();
        }
        Assert.state((this.serviceProperties != null ? 1 : 0) != 0, (String)"serviceProperties cannot be null unless Authentication.getDetails() implements ServiceAuthenticationDetails.");
        Assert.state((this.serviceProperties.getService() != null ? 1 : 0) != 0, (String)"serviceProperties.getService() cannot be null unless Authentication.getDetails() implements ServiceAuthenticationDetails.");
        String serviceUrl = this.serviceProperties.getService();
        logger.debug((Object)LogMessage.format((String)"serviceUrl = %s", (Object)serviceUrl));
        return serviceUrl;
    }

    protected UserDetails loadUserByAssertion(Assertion assertion) {
        CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(assertion, "");
        return this.authenticationUserDetailsService.loadUserDetails((Authentication)token);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.authenticationUserDetailsService = new UserDetailsByNameServiceWrapper(userDetailsService);
    }

    public void setAuthenticationUserDetailsService(AuthenticationUserDetailsService<CasAssertionAuthenticationToken> authenticationUserDetailsService) {
        this.authenticationUserDetailsService = authenticationUserDetailsService;
    }

    public void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    protected String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public StatelessTicketCache getStatelessTicketCache() {
        return this.statelessTicketCache;
    }

    protected TicketValidator getTicketValidator() {
        return this.ticketValidator;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setStatelessTicketCache(StatelessTicketCache statelessTicketCache) {
        this.statelessTicketCache = statelessTicketCache;
    }

    public void setTicketValidator(TicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication) || CasAuthenticationToken.class.isAssignableFrom(authentication) || CasAssertionAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

