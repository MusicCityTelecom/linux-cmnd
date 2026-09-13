/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.CasServerHostProperties;
import org.apereo.cas.configuration.model.core.CasServerProperties;
import org.apereo.cas.configuration.model.core.audit.AuditProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationProperties;
import org.apereo.cas.configuration.model.core.authentication.HttpClientProperties;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authz.AccessStrategyProperties;
import org.apereo.cas.configuration.model.core.config.cloud.SpringCloudConfigurationProperties;
import org.apereo.cas.configuration.model.core.config.standalone.StandaloneConfigurationProperties;
import org.apereo.cas.configuration.model.core.events.EventsProperties;
import org.apereo.cas.configuration.model.core.logging.LoggingProperties;
import org.apereo.cas.configuration.model.core.logout.LogoutProperties;
import org.apereo.cas.configuration.model.core.monitor.MonitorProperties;
import org.apereo.cas.configuration.model.core.rest.RestProperties;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryProperties;
import org.apereo.cas.configuration.model.core.slo.SingleLogOutProperties;
import org.apereo.cas.configuration.model.core.sso.SingleSignOnProperties;
import org.apereo.cas.configuration.model.core.util.TicketProperties;
import org.apereo.cas.configuration.model.core.web.LocaleProperties;
import org.apereo.cas.configuration.model.core.web.MessageBundleProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowProperties;
import org.apereo.cas.configuration.model.core.web.security.HttpRequestProperties;
import org.apereo.cas.configuration.model.core.web.view.ViewProperties;
import org.apereo.cas.configuration.model.support.account.AccountManagementRegistrationProperties;
import org.apereo.cas.configuration.model.support.acme.AcmeProperties;
import org.apereo.cas.configuration.model.support.analytics.GoogleAnalyticsProperties;
import org.apereo.cas.configuration.model.support.aup.AcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aws.AmazonSecurityTokenServiceProperties;
import org.apereo.cas.configuration.model.support.captcha.GoogleRecaptchaProperties;
import org.apereo.cas.configuration.model.support.clearpass.ClearpassProperties;
import org.apereo.cas.configuration.model.support.consent.ConsentProperties;
import org.apereo.cas.configuration.model.support.cookie.TicketGrantingCookieProperties;
import org.apereo.cas.configuration.model.support.cookie.WarningCookieProperties;
import org.apereo.cas.configuration.model.support.custom.CasCustomProperties;
import org.apereo.cas.configuration.model.support.firebase.GoogleFirebaseCloudMessagingProperties;
import org.apereo.cas.configuration.model.support.geo.GeoLocationProperties;
import org.apereo.cas.configuration.model.support.interrupt.InterruptProperties;
import org.apereo.cas.configuration.model.support.jpa.DatabaseProperties;
import org.apereo.cas.configuration.model.support.saml.SamlCoreProperties;
import org.apereo.cas.configuration.model.support.saml.googleapps.GoogleAppsProperties;
import org.apereo.cas.configuration.model.support.saml.mdui.SamlMetadataUIProperties;
import org.apereo.cas.configuration.model.support.saml.sps.SamlServiceProviderProperties;
import org.apereo.cas.configuration.model.support.scim.ScimProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProvidersProperties;
import org.apereo.cas.configuration.model.support.themes.ThemeProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(value="cas")
@JsonFilter(value="CasConfigurationProperties")
@RequiresModule(name="cas-server-core-api", automated=true)
public class CasConfigurationProperties
implements Serializable {
    public static final String PREFIX = "cas";
    private static final long serialVersionUID = -8620267783496071683L;
    private long initializationTime = Instant.now(Clock.systemUTC()).toEpochMilli();
    @NestedConfigurationProperty
    private LoggingProperties logging = new LoggingProperties();
    @NestedConfigurationProperty
    private InterruptProperties interrupt = new InterruptProperties();
    @NestedConfigurationProperty
    private ConsentProperties consent = new ConsentProperties();
    @NestedConfigurationProperty
    private AccessStrategyProperties accessStrategy = new AccessStrategyProperties();
    @NestedConfigurationProperty
    private AcmeProperties acme = new AcmeProperties();
    @NestedConfigurationProperty
    private ScimProperties scim = new ScimProperties();
    @NestedConfigurationProperty
    private AuthenticationProperties authn = new AuthenticationProperties();
    @NestedConfigurationProperty
    private AuditProperties audit = new AuditProperties();
    @NestedConfigurationProperty
    private HttpClientProperties httpClient = new HttpClientProperties();
    @NestedConfigurationProperty
    private PersonDirectoryPrincipalResolverProperties personDirectory = new PersonDirectoryPrincipalResolverProperties();
    @NestedConfigurationProperty
    private EventsProperties events = new EventsProperties();
    @NestedConfigurationProperty
    private MonitorProperties monitor = new MonitorProperties();
    @NestedConfigurationProperty
    private CasServerHostProperties host = new CasServerHostProperties();
    @NestedConfigurationProperty
    private LogoutProperties logout = new LogoutProperties();
    @NestedConfigurationProperty
    private RestProperties rest = new RestProperties();
    @NestedConfigurationProperty
    private CasServerProperties server = new CasServerProperties();
    @NestedConfigurationProperty
    private ServiceRegistryProperties serviceRegistry = new ServiceRegistryProperties();
    @NestedConfigurationProperty
    private SingleLogOutProperties slo = new SingleLogOutProperties();
    @NestedConfigurationProperty
    private SingleSignOnProperties sso = new SingleSignOnProperties();
    @NestedConfigurationProperty
    private TicketProperties ticket = new TicketProperties();
    @NestedConfigurationProperty
    private MessageBundleProperties messageBundle = new MessageBundleProperties();
    @NestedConfigurationProperty
    private HttpRequestProperties httpWebRequest = new HttpRequestProperties();
    @NestedConfigurationProperty
    private ViewProperties view = new ViewProperties();
    @NestedConfigurationProperty
    private GoogleAnalyticsProperties googleAnalytics = new GoogleAnalyticsProperties();
    @NestedConfigurationProperty
    private GoogleFirebaseCloudMessagingProperties googleFirebaseMessaging = new GoogleFirebaseCloudMessagingProperties();
    @NestedConfigurationProperty
    private GoogleRecaptchaProperties googleRecaptcha = new GoogleRecaptchaProperties();
    @NestedConfigurationProperty
    private SmsProvidersProperties smsProvider = new SmsProvidersProperties();
    @NestedConfigurationProperty
    private AcceptableUsagePolicyProperties acceptableUsagePolicy = new AcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private ClearpassProperties clearpass = new ClearpassProperties();
    @NestedConfigurationProperty
    private TicketGrantingCookieProperties tgc = new TicketGrantingCookieProperties();
    @NestedConfigurationProperty
    private WarningCookieProperties warningCookie = new WarningCookieProperties();
    @NestedConfigurationProperty
    private GeoLocationProperties geoLocation = new GeoLocationProperties();
    @NestedConfigurationProperty
    private SamlServiceProviderProperties samlSp = new SamlServiceProviderProperties();
    @NestedConfigurationProperty
    private DatabaseProperties jdbc = new DatabaseProperties();
    @NestedConfigurationProperty
    private GoogleAppsProperties googleApps = new GoogleAppsProperties();
    @NestedConfigurationProperty
    private AmazonSecurityTokenServiceProperties amazonSts = new AmazonSecurityTokenServiceProperties();
    @NestedConfigurationProperty
    private SamlMetadataUIProperties samlMetadataUi = new SamlMetadataUIProperties();
    @NestedConfigurationProperty
    private SamlCoreProperties samlCore = new SamlCoreProperties();
    @NestedConfigurationProperty
    private ThemeProperties theme = new ThemeProperties();
    @NestedConfigurationProperty
    private LocaleProperties locale = new LocaleProperties();
    @NestedConfigurationProperty
    private WebflowProperties webflow = new WebflowProperties();
    @NestedConfigurationProperty
    private CasCustomProperties custom = new CasCustomProperties();
    @NestedConfigurationProperty
    private StandaloneConfigurationProperties standalone = new StandaloneConfigurationProperties();
    @NestedConfigurationProperty
    private SpringCloudConfigurationProperties spring = new SpringCloudConfigurationProperties();
    @NestedConfigurationProperty
    private AccountManagementRegistrationProperties accountRegistration = new AccountManagementRegistrationProperties();

    public Serializable withHolder() {
        return new Holder(this);
    }

    @Generated
    public long getInitializationTime() {
        return this.initializationTime;
    }

    @Generated
    public LoggingProperties getLogging() {
        return this.logging;
    }

    @Generated
    public InterruptProperties getInterrupt() {
        return this.interrupt;
    }

    @Generated
    public ConsentProperties getConsent() {
        return this.consent;
    }

    @Generated
    public AccessStrategyProperties getAccessStrategy() {
        return this.accessStrategy;
    }

    @Generated
    public AcmeProperties getAcme() {
        return this.acme;
    }

    @Generated
    public ScimProperties getScim() {
        return this.scim;
    }

    @Generated
    public AuthenticationProperties getAuthn() {
        return this.authn;
    }

    @Generated
    public AuditProperties getAudit() {
        return this.audit;
    }

    @Generated
    public HttpClientProperties getHttpClient() {
        return this.httpClient;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties getPersonDirectory() {
        return this.personDirectory;
    }

    @Generated
    public EventsProperties getEvents() {
        return this.events;
    }

    @Generated
    public MonitorProperties getMonitor() {
        return this.monitor;
    }

    @Generated
    public CasServerHostProperties getHost() {
        return this.host;
    }

    @Generated
    public LogoutProperties getLogout() {
        return this.logout;
    }

    @Generated
    public RestProperties getRest() {
        return this.rest;
    }

    @Generated
    public CasServerProperties getServer() {
        return this.server;
    }

    @Generated
    public ServiceRegistryProperties getServiceRegistry() {
        return this.serviceRegistry;
    }

    @Generated
    public SingleLogOutProperties getSlo() {
        return this.slo;
    }

    @Generated
    public SingleSignOnProperties getSso() {
        return this.sso;
    }

    @Generated
    public TicketProperties getTicket() {
        return this.ticket;
    }

    @Generated
    public MessageBundleProperties getMessageBundle() {
        return this.messageBundle;
    }

    @Generated
    public HttpRequestProperties getHttpWebRequest() {
        return this.httpWebRequest;
    }

    @Generated
    public ViewProperties getView() {
        return this.view;
    }

    @Generated
    public GoogleAnalyticsProperties getGoogleAnalytics() {
        return this.googleAnalytics;
    }

    @Generated
    public GoogleFirebaseCloudMessagingProperties getGoogleFirebaseMessaging() {
        return this.googleFirebaseMessaging;
    }

    @Generated
    public GoogleRecaptchaProperties getGoogleRecaptcha() {
        return this.googleRecaptcha;
    }

    @Generated
    public SmsProvidersProperties getSmsProvider() {
        return this.smsProvider;
    }

    @Generated
    public AcceptableUsagePolicyProperties getAcceptableUsagePolicy() {
        return this.acceptableUsagePolicy;
    }

    @Generated
    public ClearpassProperties getClearpass() {
        return this.clearpass;
    }

    @Generated
    public TicketGrantingCookieProperties getTgc() {
        return this.tgc;
    }

    @Generated
    public WarningCookieProperties getWarningCookie() {
        return this.warningCookie;
    }

    @Generated
    public GeoLocationProperties getGeoLocation() {
        return this.geoLocation;
    }

    @Generated
    public SamlServiceProviderProperties getSamlSp() {
        return this.samlSp;
    }

    @Generated
    public DatabaseProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public GoogleAppsProperties getGoogleApps() {
        return this.googleApps;
    }

    @Generated
    public AmazonSecurityTokenServiceProperties getAmazonSts() {
        return this.amazonSts;
    }

    @Generated
    public SamlMetadataUIProperties getSamlMetadataUi() {
        return this.samlMetadataUi;
    }

    @Generated
    public SamlCoreProperties getSamlCore() {
        return this.samlCore;
    }

    @Generated
    public ThemeProperties getTheme() {
        return this.theme;
    }

    @Generated
    public LocaleProperties getLocale() {
        return this.locale;
    }

    @Generated
    public WebflowProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public CasCustomProperties getCustom() {
        return this.custom;
    }

    @Generated
    public StandaloneConfigurationProperties getStandalone() {
        return this.standalone;
    }

    @Generated
    public SpringCloudConfigurationProperties getSpring() {
        return this.spring;
    }

    @Generated
    public AccountManagementRegistrationProperties getAccountRegistration() {
        return this.accountRegistration;
    }

    @Generated
    public CasConfigurationProperties setInitializationTime(long initializationTime) {
        this.initializationTime = initializationTime;
        return this;
    }

    @Generated
    public CasConfigurationProperties setLogging(LoggingProperties logging) {
        this.logging = logging;
        return this;
    }

    @Generated
    public CasConfigurationProperties setInterrupt(InterruptProperties interrupt) {
        this.interrupt = interrupt;
        return this;
    }

    @Generated
    public CasConfigurationProperties setConsent(ConsentProperties consent) {
        this.consent = consent;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAccessStrategy(AccessStrategyProperties accessStrategy) {
        this.accessStrategy = accessStrategy;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAcme(AcmeProperties acme) {
        this.acme = acme;
        return this;
    }

    @Generated
    public CasConfigurationProperties setScim(ScimProperties scim) {
        this.scim = scim;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAuthn(AuthenticationProperties authn) {
        this.authn = authn;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAudit(AuditProperties audit) {
        this.audit = audit;
        return this;
    }

    @Generated
    public CasConfigurationProperties setHttpClient(HttpClientProperties httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    @Generated
    public CasConfigurationProperties setPersonDirectory(PersonDirectoryPrincipalResolverProperties personDirectory) {
        this.personDirectory = personDirectory;
        return this;
    }

    @Generated
    public CasConfigurationProperties setEvents(EventsProperties events) {
        this.events = events;
        return this;
    }

    @Generated
    public CasConfigurationProperties setMonitor(MonitorProperties monitor) {
        this.monitor = monitor;
        return this;
    }

    @Generated
    public CasConfigurationProperties setHost(CasServerHostProperties host) {
        this.host = host;
        return this;
    }

    @Generated
    public CasConfigurationProperties setLogout(LogoutProperties logout) {
        this.logout = logout;
        return this;
    }

    @Generated
    public CasConfigurationProperties setRest(RestProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public CasConfigurationProperties setServer(CasServerProperties server) {
        this.server = server;
        return this;
    }

    @Generated
    public CasConfigurationProperties setServiceRegistry(ServiceRegistryProperties serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSlo(SingleLogOutProperties slo) {
        this.slo = slo;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSso(SingleSignOnProperties sso) {
        this.sso = sso;
        return this;
    }

    @Generated
    public CasConfigurationProperties setTicket(TicketProperties ticket) {
        this.ticket = ticket;
        return this;
    }

    @Generated
    public CasConfigurationProperties setMessageBundle(MessageBundleProperties messageBundle) {
        this.messageBundle = messageBundle;
        return this;
    }

    @Generated
    public CasConfigurationProperties setHttpWebRequest(HttpRequestProperties httpWebRequest) {
        this.httpWebRequest = httpWebRequest;
        return this;
    }

    @Generated
    public CasConfigurationProperties setView(ViewProperties view) {
        this.view = view;
        return this;
    }

    @Generated
    public CasConfigurationProperties setGoogleAnalytics(GoogleAnalyticsProperties googleAnalytics) {
        this.googleAnalytics = googleAnalytics;
        return this;
    }

    @Generated
    public CasConfigurationProperties setGoogleFirebaseMessaging(GoogleFirebaseCloudMessagingProperties googleFirebaseMessaging) {
        this.googleFirebaseMessaging = googleFirebaseMessaging;
        return this;
    }

    @Generated
    public CasConfigurationProperties setGoogleRecaptcha(GoogleRecaptchaProperties googleRecaptcha) {
        this.googleRecaptcha = googleRecaptcha;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSmsProvider(SmsProvidersProperties smsProvider) {
        this.smsProvider = smsProvider;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAcceptableUsagePolicy(AcceptableUsagePolicyProperties acceptableUsagePolicy) {
        this.acceptableUsagePolicy = acceptableUsagePolicy;
        return this;
    }

    @Generated
    public CasConfigurationProperties setClearpass(ClearpassProperties clearpass) {
        this.clearpass = clearpass;
        return this;
    }

    @Generated
    public CasConfigurationProperties setTgc(TicketGrantingCookieProperties tgc) {
        this.tgc = tgc;
        return this;
    }

    @Generated
    public CasConfigurationProperties setWarningCookie(WarningCookieProperties warningCookie) {
        this.warningCookie = warningCookie;
        return this;
    }

    @Generated
    public CasConfigurationProperties setGeoLocation(GeoLocationProperties geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSamlSp(SamlServiceProviderProperties samlSp) {
        this.samlSp = samlSp;
        return this;
    }

    @Generated
    public CasConfigurationProperties setJdbc(DatabaseProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public CasConfigurationProperties setGoogleApps(GoogleAppsProperties googleApps) {
        this.googleApps = googleApps;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAmazonSts(AmazonSecurityTokenServiceProperties amazonSts) {
        this.amazonSts = amazonSts;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSamlMetadataUi(SamlMetadataUIProperties samlMetadataUi) {
        this.samlMetadataUi = samlMetadataUi;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSamlCore(SamlCoreProperties samlCore) {
        this.samlCore = samlCore;
        return this;
    }

    @Generated
    public CasConfigurationProperties setTheme(ThemeProperties theme) {
        this.theme = theme;
        return this;
    }

    @Generated
    public CasConfigurationProperties setLocale(LocaleProperties locale) {
        this.locale = locale;
        return this;
    }

    @Generated
    public CasConfigurationProperties setWebflow(WebflowProperties webflow) {
        this.webflow = webflow;
        return this;
    }

    @Generated
    public CasConfigurationProperties setCustom(CasCustomProperties custom) {
        this.custom = custom;
        return this;
    }

    @Generated
    public CasConfigurationProperties setStandalone(StandaloneConfigurationProperties standalone) {
        this.standalone = standalone;
        return this;
    }

    @Generated
    public CasConfigurationProperties setSpring(SpringCloudConfigurationProperties spring) {
        this.spring = spring;
        return this;
    }

    @Generated
    public CasConfigurationProperties setAccountRegistration(AccountManagementRegistrationProperties accountRegistration) {
        this.accountRegistration = accountRegistration;
        return this;
    }

    private static class Holder
    implements Serializable {
        private static final long serialVersionUID = -3129941286238115568L;
        private final CasConfigurationProperties cas;

        @Generated
        public Holder(CasConfigurationProperties cas) {
            this.cas = cas;
        }

        @Generated
        public CasConfigurationProperties getCas() {
            return this.cas;
        }
    }
}

