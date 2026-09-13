/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.config.AjaxSessionTimeoutRedirectFilter;
import be.tpvision.smartcontrol.config.CasHttpsRequestSupportFilter;
import be.tpvision.smartcontrol.config.EhCacheProxyGrantingTicketStorage;
import be.tpvision.smartcontrol.config.SmartControlSavedRequestAwareAuthenticationSuccessHandler;
import java.util.Collections;
import java.util.UUID;
import javax.servlet.Filter;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@Profile(value={"production"})
public class SecurityConfig
extends WebSecurityConfigurerAdapter {
    @Value(value="${cas.url}")
    private String casUrl;
    @Value(value="${cas.login-url:${cas.url}/login}")
    private String casLoginUrl;
    @Value(value="${cas.logout-url:${cas.url}/logout}")
    private String casLogoutUrl;
    @Value(value="${cas.service-url}")
    private String casServiceUrl;
    @Value(value="${cas.proxy.receptor-url}")
    private String casProxyReceptorUrl;
    @Value(value="${cas.proxy.callback-url}")
    private String casProxyCallbackUrl;
    @Value(value="${cas.tickets.cache.name}")
    private String casTicketsCacheName;
    @Value(value="${security.ignored}")
    private String[] securityIgnored;
    @Value(value="${server.context-path}")
    private String serverContextPath;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(HttpMethod.GET, this.securityIgnored);
        webSecurity.ignoring().antMatchers(HttpMethod.HEAD, this.securityIgnored);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ((HttpSecurity)((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)httpSecurity.exceptionHandling().authenticationEntryPoint(this.casAuthenticationEntryPoint()).and()).addFilter(this.casAuthenticationFilter()).addFilterBefore((Filter)this.singleSignOutFilter(), CasAuthenticationFilter.class)).addFilterBefore((Filter)this.requestCasGlobalLogoutFilter(), LogoutFilter.class)).addFilterAfter((Filter)this.casHttpsRequestSupportFilter(), SecurityContextPersistenceFilter.class)).addFilterAfter((Filter)this.ajaxTimeoutRedirectFilter(), ExceptionTranslationFilter.class)).authorizeRequests().anyRequest()).authenticated().and()).logout().logoutUrl("/logout").permitAll().and()).csrf().disable()).headers().httpStrictTransportSecurity().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(this.casAuthenticationProvider());
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(this.casServiceUrl);
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(this.casLoginUrl);
        casAuthenticationEntryPoint.setServiceProperties(this.serviceProperties());
        return casAuthenticationEntryPoint;
    }

    @Bean
    public CasHttpsRequestSupportFilter casHttpsRequestSupportFilter() {
        return new CasHttpsRequestSupportFilter(this.serviceProperties(), this.casAuthenticationEntryPoint());
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new ChangeSessionIdAuthenticationStrategy();
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        SmartControlSavedRequestAwareAuthenticationSuccessHandler smartControlSavedRequestAwareAuthenticationSuccessHandler = new SmartControlSavedRequestAwareAuthenticationSuccessHandler();
        smartControlSavedRequestAwareAuthenticationSuccessHandler.setContextPath(this.serverContextPath);
        casAuthenticationFilter.setAuthenticationSuccessHandler(smartControlSavedRequestAwareAuthenticationSuccessHandler);
        casAuthenticationFilter.setSessionAuthenticationStrategy(this.sessionAuthenticationStrategy());
        casAuthenticationFilter.setProxyGrantingTicketStorage(this.proxyGrantingTicketStorage());
        casAuthenticationFilter.setProxyReceptorUrl(this.casProxyReceptorUrl);
        return casAuthenticationFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        singleSignOutFilter.setLogoutCallbackPath(this.casUrl);
        return singleSignOutFilter;
    }

    @Bean
    public LogoutFilter requestCasGlobalLogoutFilter() {
        String encodedCasServiceUrl = CommonUtils.urlEncode(this.casServiceUrl);
        String logoutSuccessUrl = this.casLogoutUrl + "?service=" + encodedCasServiceUrl;
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        LogoutFilter logoutFilter = new LogoutFilter(logoutSuccessUrl, securityContextLogoutHandler);
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/logout");
        logoutFilter.setLogoutRequestMatcher(antPathRequestMatcher);
        return logoutFilter;
    }

    @Bean
    public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener() {
        return new SingleSignOutHttpSessionListener();
    }

    @Bean
    public AjaxSessionTimeoutRedirectFilter ajaxTimeoutRedirectFilter() {
        return new AjaxSessionTimeoutRedirectFilter();
    }

    @Bean
    public CacheManager cacheManager() {
        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        CacheConfiguration cacheConfiguration = new CacheConfiguration(this.casTicketsCacheName, 100);
        configuration.addCache(cacheConfiguration);
        return CacheManager.newInstance(configuration);
    }

    @Bean
    public ProxyGrantingTicketStorage proxyGrantingTicketStorage() {
        Ehcache ehcache = this.cacheManager().getEhcache(this.casTicketsCacheName);
        return new EhCacheProxyGrantingTicketStorage(ehcache);
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(token -> new User(token.getAssertion().getPrincipal().getName(), UUID.randomUUID().toString(), Collections.emptyList()));
        casAuthenticationProvider.setServiceProperties(this.serviceProperties());
        casAuthenticationProvider.setTicketValidator(this.cas20ProxyTicketValidator());
        casAuthenticationProvider.setKey("smartcontrol");
        return casAuthenticationProvider;
    }

    @Bean
    public Cas20ProxyTicketValidator cas20ProxyTicketValidator() {
        Cas20ProxyTicketValidator cas20ProxyTicketValidator = new Cas20ProxyTicketValidator(this.casUrl);
        cas20ProxyTicketValidator.setProxyCallbackUrl(this.casProxyCallbackUrl);
        cas20ProxyTicketValidator.setProxyGrantingTicketStorage(this.proxyGrantingTicketStorage());
        return cas20ProxyTicketValidator;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

