package be.tpvision.usermanagement.config;

import java.util.Collections;
import java.util.UUID;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.SpringCacheBasedTicketCache;
import org.springframework.security.cas.authentication.StatelessTicketCache;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Profile("production")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Value("${cas.url}")
   private String casUrl;
   @Value("${cas.login-url:${cas.url}/login}")
   private String casLoginUrl;
   @Value("${cas.logout-url:${cas.url}/logout}")
   private String casLogoutUrl;
   @Value("${cas.service-url}")
   private String casServiceUrl;
   @Value("${cas.tickets.cache.name}")
   private String casTicketsCacheName;
   @Value("${server.context-path}")
   private String serverContextPath;

   @Override
   public void configure(final WebSecurity webSecurity) throws Exception {
      webSecurity.ignoring().antMatchers(HttpMethod.GET, "/**/favicon.ico");
   }

   @Override
   protected void configure(final HttpSecurity httpSecurity) throws Exception {
      ((HttpSecurity)httpSecurity.exceptionHandling()
            .authenticationEntryPoint(this.casAuthenticationEntryPoint())
            .and()
            .addFilter(this.casAuthenticationFilter())
            .addFilterBefore(this.singleSignOutFilter(), CasAuthenticationFilter.class)
            .addFilterBefore(this.requestCasGlobalLogoutFilter(), LogoutFilter.class)
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and())
         .logout()
         .logoutUrl("/logout")
         .permitAll()
         .and()
         .csrf()
         .disable();
   }

   @Override
   protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
      authenticationManagerBuilder.authenticationProvider(this.casAuthenticationProvider());
   }

   @Bean
   public ServiceProperties serviceProperties() {
      ServiceProperties serviceProperties = new ServiceProperties();
      serviceProperties.setService(this.casServiceUrl);
      serviceProperties.setAuthenticateAllArtifacts(true);
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
   public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
      return new ChangeSessionIdAuthenticationStrategy();
   }

   @Bean
   public ServiceAuthenticationDetailsSource serviceAuthenticationDetailsSource() {
      return new ServiceAuthenticationDetailsSource(this.serviceProperties());
   }

   @Bean
   public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
      CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
      casAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
      UserManagementSavedRequestAwareAuthenticationSuccessHandler userManagementSavedRequestAwareAuthenticationSuccessHandler = new UserManagementSavedRequestAwareAuthenticationSuccessHandler();
      userManagementSavedRequestAwareAuthenticationSuccessHandler.setContextPath(this.serverContextPath);
      casAuthenticationFilter.setAuthenticationSuccessHandler(userManagementSavedRequestAwareAuthenticationSuccessHandler);
      casAuthenticationFilter.setSessionAuthenticationStrategy(this.sessionAuthenticationStrategy());
      casAuthenticationFilter.setServiceProperties(this.serviceProperties());
      casAuthenticationFilter.setAuthenticationDetailsSource(this.serviceAuthenticationDetailsSource());
      return casAuthenticationFilter;
   }

   @Bean
   public SingleSignOutFilter singleSignOutFilter() {
      SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
      singleSignOutFilter.setLogoutCallbackPath(this.casUrl);
      return singleSignOutFilter;
   }

   @Bean
   public LogoutFilter requestCasGlobalLogoutFilter() {
      String encodedCasServiceUrl = CommonUtils.urlEncode(this.casServiceUrl);
      String logoutSuccessUrl = this.casLogoutUrl + "?service=" + encodedCasServiceUrl;
      LogoutFilter logoutFilter = new LogoutFilter(logoutSuccessUrl, new SecurityContextLogoutHandler());
      logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/logout"));
      return logoutFilter;
   }

   @Bean
   public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener() {
      return new SingleSignOutHttpSessionListener();
   }

   @Bean
   public CasAuthenticationProvider casAuthenticationProvider() {
      CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
      casAuthenticationProvider.setAuthenticationUserDetailsService(
         token -> new User(token.getAssertion().getPrincipal().getName(), UUID.randomUUID().toString(), Collections.emptyList())
      );
      casAuthenticationProvider.setServiceProperties(this.serviceProperties());
      casAuthenticationProvider.setTicketValidator(this.cas20ProxyTicketValidator());
      casAuthenticationProvider.setKey("user-management");
      casAuthenticationProvider.setStatelessTicketCache(this.statelessTicketCache());
      return casAuthenticationProvider;
   }

   @Bean
   public Cas20ProxyTicketValidator cas20ProxyTicketValidator() {
      Cas20ProxyTicketValidator cas20ProxyTicketValidator = new Cas20ProxyTicketValidator(this.casUrl);
      cas20ProxyTicketValidator.setAcceptAnyProxy(true);
      return cas20ProxyTicketValidator;
   }

   @Bean
   public StatelessTicketCache statelessTicketCache() {
      ConcurrentMapCache cache = new ConcurrentMapCache(this.casTicketsCacheName);
      return new SpringCacheBasedTicketCache(cache);
   }
}
