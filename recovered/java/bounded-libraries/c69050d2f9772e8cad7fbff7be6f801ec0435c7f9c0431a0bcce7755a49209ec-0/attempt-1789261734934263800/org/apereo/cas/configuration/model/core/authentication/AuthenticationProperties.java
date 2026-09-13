/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationAttributeReleaseProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationExceptionsProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.CoreAuthenticationProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.passwordsync.PasswordSynchronizationProperties;
import org.apereo.cas.configuration.model.support.azuread.AzureActiveDirectoryAuthenticationProperties;
import org.apereo.cas.configuration.model.support.cassandra.authentication.CassandraAuthenticationProperties;
import org.apereo.cas.configuration.model.support.clouddirectory.AmazonCloudDirectoryProperties;
import org.apereo.cas.configuration.model.support.cognito.AmazonCognitoAuthenticationProperties;
import org.apereo.cas.configuration.model.support.couchbase.authentication.CouchbaseAuthenticationProperties;
import org.apereo.cas.configuration.model.support.couchdb.authentication.CouchDbAuthenticationProperties;
import org.apereo.cas.configuration.model.support.digest.DigestProperties;
import org.apereo.cas.configuration.model.support.fortress.FortressAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.AcceptAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.FileAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.GroovyAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.JsonResourceAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.RejectAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.RemoteAddressAuthenticationProperties;
import org.apereo.cas.configuration.model.support.generic.ShiroAuthenticationProperties;
import org.apereo.cas.configuration.model.support.gua.GraphicalUserAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jaas.JaasAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jdbc.JdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.ldap.LdapAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mongo.MongoDbAuthenticationProperties;
import org.apereo.cas.configuration.model.support.ntlm.NtlmProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcProperties;
import org.apereo.cas.configuration.model.support.okta.OktaAuthenticationProperties;
import org.apereo.cas.configuration.model.support.openid.OpenIdProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationProperties;
import org.apereo.cas.configuration.model.support.passwordless.PasswordlessAuthenticationProperties;
import org.apereo.cas.configuration.model.support.pm.PasswordManagementProperties;
import org.apereo.cas.configuration.model.support.qr.QRAuthenticationProperties;
import org.apereo.cas.configuration.model.support.radius.RadiusProperties;
import org.apereo.cas.configuration.model.support.redis.RedisAuthenticationProperties;
import org.apereo.cas.configuration.model.support.rest.RestAuthenticationProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPProperties;
import org.apereo.cas.configuration.model.support.saml.shibboleth.ShibbolethIdPProperties;
import org.apereo.cas.configuration.model.support.soap.SoapAuthenticationProperties;
import org.apereo.cas.configuration.model.support.spnego.SpnegoProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateAuthenticationProperties;
import org.apereo.cas.configuration.model.support.syncope.SyncopeAuthenticationProperties;
import org.apereo.cas.configuration.model.support.throttle.ThrottleProperties;
import org.apereo.cas.configuration.model.support.token.TokenAuthenticationProperties;
import org.apereo.cas.configuration.model.support.trusted.TrustedAuthenticationProperties;
import org.apereo.cas.configuration.model.support.wsfed.WsFederationDelegationProperties;
import org.apereo.cas.configuration.model.support.wsfed.WsFederationProperties;
import org.apereo.cas.configuration.model.support.x509.X509Properties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="AuthenticationProperties")
public class AuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -1233126985007049516L;
    @NestedConfigurationProperty
    private CoreAuthenticationProperties core = new CoreAuthenticationProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationProperties passwordless = new PasswordlessAuthenticationProperties();
    @NestedConfigurationProperty
    private QRAuthenticationProperties qr = new QRAuthenticationProperties();
    @NestedConfigurationProperty
    private PasswordSynchronizationProperties passwordSync = new PasswordSynchronizationProperties();
    @NestedConfigurationProperty
    private JsonResourceAuthenticationProperties json = new JsonResourceAuthenticationProperties();
    @NestedConfigurationProperty
    private GroovyAuthenticationProperties groovy = new GroovyAuthenticationProperties();
    @NestedConfigurationProperty
    private SyncopeAuthenticationProperties syncope = new SyncopeAuthenticationProperties();
    @NestedConfigurationProperty
    private AzureActiveDirectoryAuthenticationProperties azureActiveDirectory = new AzureActiveDirectoryAuthenticationProperties();
    @NestedConfigurationProperty
    private OktaAuthenticationProperties okta = new OktaAuthenticationProperties();
    @NestedConfigurationProperty
    private CouchbaseAuthenticationProperties couchbase = new CouchbaseAuthenticationProperties();
    @NestedConfigurationProperty
    private RedisAuthenticationProperties redis = new RedisAuthenticationProperties();
    @NestedConfigurationProperty
    private CassandraAuthenticationProperties cassandra = new CassandraAuthenticationProperties();
    @NestedConfigurationProperty
    private AmazonCloudDirectoryProperties cloudDirectory = new AmazonCloudDirectoryProperties();
    @NestedConfigurationProperty
    private AmazonCognitoAuthenticationProperties cognito = new AmazonCognitoAuthenticationProperties();
    @NestedConfigurationProperty
    private SoapAuthenticationProperties soap = new SoapAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateAuthenticationProperties surrogate = new SurrogateAuthenticationProperties();
    @NestedConfigurationProperty
    private GraphicalUserAuthenticationProperties gua = new GraphicalUserAuthenticationProperties();
    @NestedConfigurationProperty
    private PasswordManagementProperties pm = new PasswordManagementProperties();
    @NestedConfigurationProperty
    private AdaptiveAuthenticationProperties adaptive = new AdaptiveAuthenticationProperties();
    @NestedConfigurationProperty
    private PrincipalAttributesProperties attributeRepository = new PrincipalAttributesProperties();
    @NestedConfigurationProperty
    private DigestProperties digest = new DigestProperties();
    @NestedConfigurationProperty
    private RestAuthenticationProperties rest = new RestAuthenticationProperties();
    private List<LdapAuthenticationProperties> ldap = new ArrayList<LdapAuthenticationProperties>(0);
    @NestedConfigurationProperty
    private ThrottleProperties throttle = new ThrottleProperties();
    @NestedConfigurationProperty
    private SamlIdPProperties samlIdp = new SamlIdPProperties();
    @NestedConfigurationProperty
    private AuthenticationExceptionsProperties errors = new AuthenticationExceptionsProperties();
    @NestedConfigurationProperty
    private AuthenticationPolicyProperties policy = new AuthenticationPolicyProperties();
    @NestedConfigurationProperty
    private AcceptAuthenticationProperties accept = new AcceptAuthenticationProperties();
    @NestedConfigurationProperty
    private FileAuthenticationProperties file = new FileAuthenticationProperties();
    @NestedConfigurationProperty
    private RejectAuthenticationProperties reject = new RejectAuthenticationProperties();
    @NestedConfigurationProperty
    private RemoteAddressAuthenticationProperties remoteAddress = new RemoteAddressAuthenticationProperties();
    @NestedConfigurationProperty
    private ShibbolethIdPProperties shibIdp = new ShibbolethIdPProperties();
    @NestedConfigurationProperty
    @Deprecated(since="6.6.0")
    private ShiroAuthenticationProperties shiro = new ShiroAuthenticationProperties();
    @NestedConfigurationProperty
    private TrustedAuthenticationProperties trusted = new TrustedAuthenticationProperties();
    private List<JaasAuthenticationProperties> jaas = new ArrayList<JaasAuthenticationProperties>(0);
    @NestedConfigurationProperty
    private JdbcAuthenticationProperties jdbc = new JdbcAuthenticationProperties();
    @NestedConfigurationProperty
    private MultifactorAuthenticationProperties mfa = new MultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private MongoDbAuthenticationProperties mongo = new MongoDbAuthenticationProperties();
    @NestedConfigurationProperty
    private CouchDbAuthenticationProperties couchDb = new CouchDbAuthenticationProperties();
    @NestedConfigurationProperty
    private NtlmProperties ntlm = new NtlmProperties();
    @NestedConfigurationProperty
    private OAuthProperties oauth = new OAuthProperties();
    @NestedConfigurationProperty
    private OidcProperties oidc = new OidcProperties();
    @NestedConfigurationProperty
    @Deprecated(since="6.2.0")
    private OpenIdProperties openid = new OpenIdProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationProperties pac4j = new Pac4jDelegatedAuthenticationProperties();
    @NestedConfigurationProperty
    private RadiusProperties radius = new RadiusProperties();
    @NestedConfigurationProperty
    private SpnegoProperties spnego = new SpnegoProperties();
    private List<WsFederationDelegationProperties> wsfed = new ArrayList<WsFederationDelegationProperties>(0);
    @NestedConfigurationProperty
    private WsFederationProperties wsfedIdp = new WsFederationProperties();
    @NestedConfigurationProperty
    private X509Properties x509 = new X509Properties();
    @NestedConfigurationProperty
    private TokenAuthenticationProperties token = new TokenAuthenticationProperties();
    @NestedConfigurationProperty
    private FortressAuthenticationProperties fortress = new FortressAuthenticationProperties();
    @NestedConfigurationProperty
    private AuthenticationAttributeReleaseProperties authenticationAttributeRelease = new AuthenticationAttributeReleaseProperties();

    @Generated
    public CoreAuthenticationProperties getCore() {
        return this.core;
    }

    @Generated
    public PasswordlessAuthenticationProperties getPasswordless() {
        return this.passwordless;
    }

    @Generated
    public QRAuthenticationProperties getQr() {
        return this.qr;
    }

    @Generated
    public PasswordSynchronizationProperties getPasswordSync() {
        return this.passwordSync;
    }

    @Generated
    public JsonResourceAuthenticationProperties getJson() {
        return this.json;
    }

    @Generated
    public GroovyAuthenticationProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public SyncopeAuthenticationProperties getSyncope() {
        return this.syncope;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties getAzureActiveDirectory() {
        return this.azureActiveDirectory;
    }

    @Generated
    public OktaAuthenticationProperties getOkta() {
        return this.okta;
    }

    @Generated
    public CouchbaseAuthenticationProperties getCouchbase() {
        return this.couchbase;
    }

    @Generated
    public RedisAuthenticationProperties getRedis() {
        return this.redis;
    }

    @Generated
    public CassandraAuthenticationProperties getCassandra() {
        return this.cassandra;
    }

    @Generated
    public AmazonCloudDirectoryProperties getCloudDirectory() {
        return this.cloudDirectory;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties getCognito() {
        return this.cognito;
    }

    @Generated
    public SoapAuthenticationProperties getSoap() {
        return this.soap;
    }

    @Generated
    public SurrogateAuthenticationProperties getSurrogate() {
        return this.surrogate;
    }

    @Generated
    public GraphicalUserAuthenticationProperties getGua() {
        return this.gua;
    }

    @Generated
    public PasswordManagementProperties getPm() {
        return this.pm;
    }

    @Generated
    public AdaptiveAuthenticationProperties getAdaptive() {
        return this.adaptive;
    }

    @Generated
    public PrincipalAttributesProperties getAttributeRepository() {
        return this.attributeRepository;
    }

    @Generated
    public DigestProperties getDigest() {
        return this.digest;
    }

    @Generated
    public RestAuthenticationProperties getRest() {
        return this.rest;
    }

    @Generated
    public List<LdapAuthenticationProperties> getLdap() {
        return this.ldap;
    }

    @Generated
    public ThrottleProperties getThrottle() {
        return this.throttle;
    }

    @Generated
    public SamlIdPProperties getSamlIdp() {
        return this.samlIdp;
    }

    @Generated
    public AuthenticationExceptionsProperties getErrors() {
        return this.errors;
    }

    @Generated
    public AuthenticationPolicyProperties getPolicy() {
        return this.policy;
    }

    @Generated
    public AcceptAuthenticationProperties getAccept() {
        return this.accept;
    }

    @Generated
    public FileAuthenticationProperties getFile() {
        return this.file;
    }

    @Generated
    public RejectAuthenticationProperties getReject() {
        return this.reject;
    }

    @Generated
    public RemoteAddressAuthenticationProperties getRemoteAddress() {
        return this.remoteAddress;
    }

    @Generated
    public ShibbolethIdPProperties getShibIdp() {
        return this.shibIdp;
    }

    @Deprecated
    @Generated
    public ShiroAuthenticationProperties getShiro() {
        return this.shiro;
    }

    @Generated
    public TrustedAuthenticationProperties getTrusted() {
        return this.trusted;
    }

    @Generated
    public List<JaasAuthenticationProperties> getJaas() {
        return this.jaas;
    }

    @Generated
    public JdbcAuthenticationProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public MultifactorAuthenticationProperties getMfa() {
        return this.mfa;
    }

    @Generated
    public MongoDbAuthenticationProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public CouchDbAuthenticationProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public NtlmProperties getNtlm() {
        return this.ntlm;
    }

    @Generated
    public OAuthProperties getOauth() {
        return this.oauth;
    }

    @Generated
    public OidcProperties getOidc() {
        return this.oidc;
    }

    @Deprecated
    @Generated
    public OpenIdProperties getOpenid() {
        return this.openid;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties getPac4j() {
        return this.pac4j;
    }

    @Generated
    public RadiusProperties getRadius() {
        return this.radius;
    }

    @Generated
    public SpnegoProperties getSpnego() {
        return this.spnego;
    }

    @Generated
    public List<WsFederationDelegationProperties> getWsfed() {
        return this.wsfed;
    }

    @Generated
    public WsFederationProperties getWsfedIdp() {
        return this.wsfedIdp;
    }

    @Generated
    public X509Properties getX509() {
        return this.x509;
    }

    @Generated
    public TokenAuthenticationProperties getToken() {
        return this.token;
    }

    @Generated
    public FortressAuthenticationProperties getFortress() {
        return this.fortress;
    }

    @Generated
    public AuthenticationAttributeReleaseProperties getAuthenticationAttributeRelease() {
        return this.authenticationAttributeRelease;
    }

    @Generated
    public AuthenticationProperties setCore(CoreAuthenticationProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public AuthenticationProperties setPasswordless(PasswordlessAuthenticationProperties passwordless) {
        this.passwordless = passwordless;
        return this;
    }

    @Generated
    public AuthenticationProperties setQr(QRAuthenticationProperties qr) {
        this.qr = qr;
        return this;
    }

    @Generated
    public AuthenticationProperties setPasswordSync(PasswordSynchronizationProperties passwordSync) {
        this.passwordSync = passwordSync;
        return this;
    }

    @Generated
    public AuthenticationProperties setJson(JsonResourceAuthenticationProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public AuthenticationProperties setGroovy(GroovyAuthenticationProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public AuthenticationProperties setSyncope(SyncopeAuthenticationProperties syncope) {
        this.syncope = syncope;
        return this;
    }

    @Generated
    public AuthenticationProperties setAzureActiveDirectory(AzureActiveDirectoryAuthenticationProperties azureActiveDirectory) {
        this.azureActiveDirectory = azureActiveDirectory;
        return this;
    }

    @Generated
    public AuthenticationProperties setOkta(OktaAuthenticationProperties okta) {
        this.okta = okta;
        return this;
    }

    @Generated
    public AuthenticationProperties setCouchbase(CouchbaseAuthenticationProperties couchbase) {
        this.couchbase = couchbase;
        return this;
    }

    @Generated
    public AuthenticationProperties setRedis(RedisAuthenticationProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public AuthenticationProperties setCassandra(CassandraAuthenticationProperties cassandra) {
        this.cassandra = cassandra;
        return this;
    }

    @Generated
    public AuthenticationProperties setCloudDirectory(AmazonCloudDirectoryProperties cloudDirectory) {
        this.cloudDirectory = cloudDirectory;
        return this;
    }

    @Generated
    public AuthenticationProperties setCognito(AmazonCognitoAuthenticationProperties cognito) {
        this.cognito = cognito;
        return this;
    }

    @Generated
    public AuthenticationProperties setSoap(SoapAuthenticationProperties soap) {
        this.soap = soap;
        return this;
    }

    @Generated
    public AuthenticationProperties setSurrogate(SurrogateAuthenticationProperties surrogate) {
        this.surrogate = surrogate;
        return this;
    }

    @Generated
    public AuthenticationProperties setGua(GraphicalUserAuthenticationProperties gua) {
        this.gua = gua;
        return this;
    }

    @Generated
    public AuthenticationProperties setPm(PasswordManagementProperties pm) {
        this.pm = pm;
        return this;
    }

    @Generated
    public AuthenticationProperties setAdaptive(AdaptiveAuthenticationProperties adaptive) {
        this.adaptive = adaptive;
        return this;
    }

    @Generated
    public AuthenticationProperties setAttributeRepository(PrincipalAttributesProperties attributeRepository) {
        this.attributeRepository = attributeRepository;
        return this;
    }

    @Generated
    public AuthenticationProperties setDigest(DigestProperties digest) {
        this.digest = digest;
        return this;
    }

    @Generated
    public AuthenticationProperties setRest(RestAuthenticationProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public AuthenticationProperties setLdap(List<LdapAuthenticationProperties> ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public AuthenticationProperties setThrottle(ThrottleProperties throttle) {
        this.throttle = throttle;
        return this;
    }

    @Generated
    public AuthenticationProperties setSamlIdp(SamlIdPProperties samlIdp) {
        this.samlIdp = samlIdp;
        return this;
    }

    @Generated
    public AuthenticationProperties setErrors(AuthenticationExceptionsProperties errors) {
        this.errors = errors;
        return this;
    }

    @Generated
    public AuthenticationProperties setPolicy(AuthenticationPolicyProperties policy) {
        this.policy = policy;
        return this;
    }

    @Generated
    public AuthenticationProperties setAccept(AcceptAuthenticationProperties accept) {
        this.accept = accept;
        return this;
    }

    @Generated
    public AuthenticationProperties setFile(FileAuthenticationProperties file) {
        this.file = file;
        return this;
    }

    @Generated
    public AuthenticationProperties setReject(RejectAuthenticationProperties reject) {
        this.reject = reject;
        return this;
    }

    @Generated
    public AuthenticationProperties setRemoteAddress(RemoteAddressAuthenticationProperties remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    @Generated
    public AuthenticationProperties setShibIdp(ShibbolethIdPProperties shibIdp) {
        this.shibIdp = shibIdp;
        return this;
    }

    @Deprecated
    @Generated
    public AuthenticationProperties setShiro(ShiroAuthenticationProperties shiro) {
        this.shiro = shiro;
        return this;
    }

    @Generated
    public AuthenticationProperties setTrusted(TrustedAuthenticationProperties trusted) {
        this.trusted = trusted;
        return this;
    }

    @Generated
    public AuthenticationProperties setJaas(List<JaasAuthenticationProperties> jaas) {
        this.jaas = jaas;
        return this;
    }

    @Generated
    public AuthenticationProperties setJdbc(JdbcAuthenticationProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public AuthenticationProperties setMfa(MultifactorAuthenticationProperties mfa) {
        this.mfa = mfa;
        return this;
    }

    @Generated
    public AuthenticationProperties setMongo(MongoDbAuthenticationProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public AuthenticationProperties setCouchDb(CouchDbAuthenticationProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public AuthenticationProperties setNtlm(NtlmProperties ntlm) {
        this.ntlm = ntlm;
        return this;
    }

    @Generated
    public AuthenticationProperties setOauth(OAuthProperties oauth) {
        this.oauth = oauth;
        return this;
    }

    @Generated
    public AuthenticationProperties setOidc(OidcProperties oidc) {
        this.oidc = oidc;
        return this;
    }

    @Deprecated
    @Generated
    public AuthenticationProperties setOpenid(OpenIdProperties openid) {
        this.openid = openid;
        return this;
    }

    @Generated
    public AuthenticationProperties setPac4j(Pac4jDelegatedAuthenticationProperties pac4j) {
        this.pac4j = pac4j;
        return this;
    }

    @Generated
    public AuthenticationProperties setRadius(RadiusProperties radius) {
        this.radius = radius;
        return this;
    }

    @Generated
    public AuthenticationProperties setSpnego(SpnegoProperties spnego) {
        this.spnego = spnego;
        return this;
    }

    @Generated
    public AuthenticationProperties setWsfed(List<WsFederationDelegationProperties> wsfed) {
        this.wsfed = wsfed;
        return this;
    }

    @Generated
    public AuthenticationProperties setWsfedIdp(WsFederationProperties wsfedIdp) {
        this.wsfedIdp = wsfedIdp;
        return this;
    }

    @Generated
    public AuthenticationProperties setX509(X509Properties x509) {
        this.x509 = x509;
        return this;
    }

    @Generated
    public AuthenticationProperties setToken(TokenAuthenticationProperties token) {
        this.token = token;
        return this;
    }

    @Generated
    public AuthenticationProperties setFortress(FortressAuthenticationProperties fortress) {
        this.fortress = fortress;
        return this;
    }

    @Generated
    public AuthenticationProperties setAuthenticationAttributeRelease(AuthenticationAttributeReleaseProperties authenticationAttributeRelease) {
        this.authenticationAttributeRelease = authenticationAttributeRelease;
        return this;
    }
}

