/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContainerInitializer
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.catalina.Container
 *  org.apache.catalina.Context
 *  org.apache.catalina.Engine
 *  org.apache.catalina.Host
 *  org.apache.catalina.LifecycleEvent
 *  org.apache.catalina.LifecycleException
 *  org.apache.catalina.LifecycleListener
 *  org.apache.catalina.Loader
 *  org.apache.catalina.Manager
 *  org.apache.catalina.Valve
 *  org.apache.catalina.WebResource
 *  org.apache.catalina.WebResourceRoot
 *  org.apache.catalina.WebResourceRoot$ResourceSetType
 *  org.apache.catalina.WebResourceSet
 *  org.apache.catalina.Wrapper
 *  org.apache.catalina.connector.Connector
 *  org.apache.catalina.core.AprLifecycleListener
 *  org.apache.catalina.loader.WebappLoader
 *  org.apache.catalina.session.StandardManager
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.catalina.startup.Tomcat$FixContextListener
 *  org.apache.catalina.util.LifecycleBase
 *  org.apache.catalina.util.SessionConfig
 *  org.apache.catalina.webresources.AbstractResourceSet
 *  org.apache.catalina.webresources.EmptyResource
 *  org.apache.catalina.webresources.StandardRoot
 *  org.apache.coyote.AbstractProtocol
 *  org.apache.coyote.ProtocolHandler
 *  org.apache.coyote.UpgradeProtocol
 *  org.apache.coyote.http2.Http2Protocol
 *  org.apache.tomcat.JarScanFilter
 *  org.apache.tomcat.util.descriptor.web.ErrorPage
 *  org.apache.tomcat.util.http.CookieProcessor
 *  org.apache.tomcat.util.http.Rfc6265CookieProcessor
 *  org.apache.tomcat.util.modeler.Registry
 *  org.apache.tomcat.util.scan.StandardJarScanFilter
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.NativeDetector
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.tomcat;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.Valve;
import org.apache.catalina.WebResource;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.LifecycleBase;
import org.apache.catalina.util.SessionConfig;
import org.apache.catalina.webresources.AbstractResourceSet;
import org.apache.catalina.webresources.EmptyResource;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.JarScanFilter;
import org.apache.tomcat.util.http.CookieProcessor;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.boot.web.embedded.tomcat.CompressionConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.DisableReferenceClearingContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.SslConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TldPatterns;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedContext;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatStarter;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.NativeDetector;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class TomcatServletWebServerFactory
extends AbstractServletWebServerFactory
implements ConfigurableTomcatWebServerFactory,
ResourceLoaderAware {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final Set<Class<?>> NO_CLASSES = Collections.emptySet();
    public static final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
    private File baseDirectory;
    private List<Valve> engineValves = new ArrayList<Valve>();
    private List<Valve> contextValves = new ArrayList<Valve>();
    private List<LifecycleListener> contextLifecycleListeners = new ArrayList<LifecycleListener>();
    private List<LifecycleListener> serverLifecycleListeners = TomcatServletWebServerFactory.getDefaultServerLifecycleListeners();
    private Set<TomcatContextCustomizer> tomcatContextCustomizers = new LinkedHashSet<TomcatContextCustomizer>();
    private Set<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new LinkedHashSet<TomcatConnectorCustomizer>();
    private Set<TomcatProtocolHandlerCustomizer<?>> tomcatProtocolHandlerCustomizers = new LinkedHashSet();
    private final List<Connector> additionalTomcatConnectors = new ArrayList<Connector>();
    private ResourceLoader resourceLoader;
    private String protocol = "org.apache.coyote.http11.Http11NioProtocol";
    private Set<String> tldSkipPatterns = new LinkedHashSet<String>(TldPatterns.DEFAULT_SKIP);
    private Set<String> tldScanPatterns = new LinkedHashSet<String>(TldPatterns.DEFAULT_SCAN);
    private Charset uriEncoding = DEFAULT_CHARSET;
    private int backgroundProcessorDelay;
    private boolean disableMBeanRegistry = true;

    public TomcatServletWebServerFactory() {
    }

    public TomcatServletWebServerFactory(int port) {
        super(port);
    }

    public TomcatServletWebServerFactory(String contextPath, int port) {
        super(contextPath, port);
    }

    private static List<LifecycleListener> getDefaultServerLifecycleListeners() {
        ArrayList<LifecycleListener> lifecycleListeners = new ArrayList<LifecycleListener>();
        if (!NativeDetector.inNativeImage()) {
            AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
            if (AprLifecycleListener.isAprAvailable()) {
                lifecycleListeners.add((LifecycleListener)aprLifecycleListener);
            }
        }
        return lifecycleListeners;
    }

    @Override
    public WebServer getWebServer(ServletContextInitializer ... initializers) {
        if (this.disableMBeanRegistry) {
            Registry.disableRegistry();
        }
        Tomcat tomcat = new Tomcat();
        File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        for (LifecycleListener listener : this.serverLifecycleListeners) {
            tomcat.getServer().addLifecycleListener(listener);
        }
        Connector connector = new Connector(this.protocol);
        connector.setThrowOnFailure(true);
        tomcat.getService().addConnector(connector);
        this.customizeConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
        this.configureEngine(tomcat.getEngine());
        for (Connector additionalConnector : this.additionalTomcatConnectors) {
            tomcat.getService().addConnector(additionalConnector);
        }
        this.prepareContext(tomcat.getHost(), initializers);
        return this.getTomcatWebServer(tomcat);
    }

    private void configureEngine(Engine engine) {
        engine.setBackgroundProcessorDelay(this.backgroundProcessorDelay);
        for (Valve valve : this.engineValves) {
            engine.getPipeline().addValve(valve);
        }
    }

    protected void prepareContext(Host host, ServletContextInitializer[] initializers) {
        File documentRoot = this.getValidDocumentRoot();
        TomcatEmbeddedContext context = new TomcatEmbeddedContext();
        if (documentRoot != null) {
            context.setResources((WebResourceRoot)new LoaderHidingResourceRoot(context));
        }
        context.setName(this.getContextPath());
        context.setDisplayName(this.getDisplayName());
        context.setPath(this.getContextPath());
        File docBase = documentRoot != null ? documentRoot : this.createTempDir("tomcat-docbase");
        context.setDocBase(docBase.getAbsolutePath());
        context.addLifecycleListener((LifecycleListener)new Tomcat.FixContextListener());
        context.setParentClassLoader(this.resourceLoader != null ? this.resourceLoader.getClassLoader() : ClassUtils.getDefaultClassLoader());
        this.resetDefaultLocaleMapping(context);
        this.addLocaleMappings(context);
        try {
            context.setCreateUploadTargets(true);
        }
        catch (NoSuchMethodError noSuchMethodError) {
            // empty catch block
        }
        this.configureTldPatterns(context);
        WebappLoader loader = new WebappLoader();
        loader.setLoaderClass(TomcatEmbeddedWebappClassLoader.class.getName());
        loader.setDelegate(true);
        context.setLoader((Loader)loader);
        if (this.isRegisterDefaultServlet()) {
            this.addDefaultServlet((Context)context);
        }
        if (this.shouldRegisterJspServlet()) {
            this.addJspServlet((Context)context);
            this.addJasperInitializer(context);
        }
        context.addLifecycleListener(new StaticResourceConfigurer((Context)context));
        ServletContextInitializer[] initializersToUse = this.mergeInitializers(initializers);
        host.addChild((Container)context);
        this.configureContext((Context)context, initializersToUse);
        this.postProcessContext((Context)context);
    }

    private void resetDefaultLocaleMapping(TomcatEmbeddedContext context) {
        context.addLocaleEncodingMappingParameter(Locale.ENGLISH.toString(), DEFAULT_CHARSET.displayName());
        context.addLocaleEncodingMappingParameter(Locale.FRENCH.toString(), DEFAULT_CHARSET.displayName());
        context.addLocaleEncodingMappingParameter(Locale.JAPANESE.toString(), DEFAULT_CHARSET.displayName());
    }

    private void addLocaleMappings(TomcatEmbeddedContext context) {
        this.getLocaleCharsetMappings().forEach((locale, charset) -> context.addLocaleEncodingMappingParameter(locale.toString(), charset.toString()));
    }

    private void configureTldPatterns(TomcatEmbeddedContext context) {
        StandardJarScanFilter filter = new StandardJarScanFilter();
        filter.setTldSkip(StringUtils.collectionToCommaDelimitedString(this.tldSkipPatterns));
        filter.setTldScan(StringUtils.collectionToCommaDelimitedString(this.tldScanPatterns));
        context.getJarScanner().setJarScanFilter((JarScanFilter)filter);
    }

    private void addDefaultServlet(Context context) {
        Wrapper defaultServlet = context.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);
        defaultServlet.setOverridable(true);
        context.addChild((Container)defaultServlet);
        context.addServletMappingDecoded("/", "default");
    }

    private void addJspServlet(Context context) {
        Wrapper jspServlet = context.createWrapper();
        jspServlet.setName("jsp");
        jspServlet.setServletClass(this.getJsp().getClassName());
        jspServlet.addInitParameter("fork", "false");
        this.getJsp().getInitParameters().forEach((arg_0, arg_1) -> ((Wrapper)jspServlet).addInitParameter(arg_0, arg_1));
        jspServlet.setLoadOnStartup(3);
        context.addChild((Container)jspServlet);
        context.addServletMappingDecoded("*.jsp", "jsp");
        context.addServletMappingDecoded("*.jspx", "jsp");
    }

    private void addJasperInitializer(TomcatEmbeddedContext context) {
        try {
            ServletContainerInitializer initializer = (ServletContainerInitializer)ClassUtils.forName((String)"org.apache.jasper.servlet.JasperInitializer", null).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            context.addServletContainerInitializer(initializer, null);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void customizeConnector(Connector connector) {
        int port = Math.max(this.getPort(), 0);
        connector.setPort(port);
        if (StringUtils.hasText((String)this.getServerHeader())) {
            connector.setProperty("server", this.getServerHeader());
        }
        if (connector.getProtocolHandler() instanceof AbstractProtocol) {
            this.customizeProtocol((AbstractProtocol)connector.getProtocolHandler());
        }
        this.invokeProtocolHandlerCustomizers(connector.getProtocolHandler());
        if (this.getUriEncoding() != null) {
            connector.setURIEncoding(this.getUriEncoding().name());
        }
        connector.setProperty("bindOnInit", "false");
        if (this.getHttp2() != null && this.getHttp2().isEnabled()) {
            connector.addUpgradeProtocol((UpgradeProtocol)new Http2Protocol());
        }
        if (this.getSsl() != null && this.getSsl().isEnabled()) {
            this.customizeSsl(connector);
        }
        CompressionConnectorCustomizer compression = new CompressionConnectorCustomizer(this.getCompression());
        compression.customize(connector);
        for (TomcatConnectorCustomizer customizer : this.tomcatConnectorCustomizers) {
            customizer.customize(connector);
        }
    }

    private void customizeProtocol(AbstractProtocol<?> protocol) {
        if (this.getAddress() != null) {
            protocol.setAddress(this.getAddress());
        }
    }

    private void invokeProtocolHandlerCustomizers(ProtocolHandler protocolHandler) {
        LambdaSafe.callbacks(TomcatProtocolHandlerCustomizer.class, this.tomcatProtocolHandlerCustomizers, protocolHandler, new Object[0]).invoke(customizer -> customizer.customize(protocolHandler));
    }

    private void customizeSsl(Connector connector) {
        new SslConnectorCustomizer(this.getSsl(), this.getOrCreateSslStoreProvider()).customize(connector);
    }

    protected void configureContext(Context context, ServletContextInitializer[] initializers) {
        TomcatStarter starter = new TomcatStarter(initializers);
        if (context instanceof TomcatEmbeddedContext) {
            TomcatEmbeddedContext embeddedContext = (TomcatEmbeddedContext)context;
            embeddedContext.setStarter(starter);
            embeddedContext.setFailCtxIfServletStartFails(true);
        }
        context.addServletContainerInitializer((ServletContainerInitializer)starter, NO_CLASSES);
        for (LifecycleListener lifecycleListener : this.contextLifecycleListeners) {
            context.addLifecycleListener(lifecycleListener);
        }
        for (Valve valve : this.contextValves) {
            context.getPipeline().addValve(valve);
        }
        for (ErrorPage errorPage : this.getErrorPages()) {
            org.apache.tomcat.util.descriptor.web.ErrorPage tomcatErrorPage = new org.apache.tomcat.util.descriptor.web.ErrorPage();
            tomcatErrorPage.setLocation(errorPage.getPath());
            tomcatErrorPage.setErrorCode(errorPage.getStatusCode());
            tomcatErrorPage.setExceptionType(errorPage.getExceptionName());
            context.addErrorPage(tomcatErrorPage);
        }
        for (MimeMappings.Mapping mapping : this.getMimeMappings()) {
            context.addMimeMapping(mapping.getExtension(), mapping.getMimeType());
        }
        this.configureSession(context);
        this.configureCookieProcessor(context);
        new DisableReferenceClearingContextCustomizer().customize(context);
        for (String webListenerClassName : this.getWebListenerClassNames()) {
            context.addApplicationListener(webListenerClassName);
        }
        for (TomcatContextCustomizer customizer : this.tomcatContextCustomizers) {
            customizer.customize(context);
        }
    }

    private void configureSession(Context context) {
        long sessionTimeout = this.getSessionTimeoutInMinutes();
        context.setSessionTimeout((int)sessionTimeout);
        Boolean httpOnly = this.getSession().getCookie().getHttpOnly();
        if (httpOnly != null) {
            context.setUseHttpOnly(httpOnly.booleanValue());
        }
        if (this.getSession().isPersistent()) {
            Manager manager = context.getManager();
            if (manager == null) {
                manager = new StandardManager();
                context.setManager(manager);
            }
            this.configurePersistSession(manager);
        } else {
            context.addLifecycleListener((LifecycleListener)new DisablePersistSessionListener());
        }
    }

    private void configureCookieProcessor(Context context) {
        Cookie.SameSite sessionSameSite = this.getSession().getCookie().getSameSite();
        ArrayList<CookieSameSiteSupplier> suppliers = new ArrayList<CookieSameSiteSupplier>();
        if (sessionSameSite != null) {
            suppliers.add(CookieSameSiteSupplier.of(sessionSameSite).whenHasName(() -> SessionConfig.getSessionCookieName((Context)context)));
        }
        if (!CollectionUtils.isEmpty(this.getCookieSameSiteSuppliers())) {
            suppliers.addAll(this.getCookieSameSiteSuppliers());
        }
        if (!suppliers.isEmpty()) {
            context.setCookieProcessor((CookieProcessor)new SuppliedSameSiteCookieProcessor(suppliers));
        }
    }

    private void configurePersistSession(Manager manager) {
        Assert.state((boolean)(manager instanceof StandardManager), () -> "Unable to persist HTTP session state using manager type " + manager.getClass().getName());
        File dir = this.getValidSessionStoreDir();
        File file = new File(dir, "SESSIONS.ser");
        ((StandardManager)manager).setPathname(file.getAbsolutePath());
    }

    private long getSessionTimeoutInMinutes() {
        Duration sessionTimeout = this.getSession().getTimeout();
        if (this.isZeroOrLess(sessionTimeout)) {
            return 0L;
        }
        return Math.max(sessionTimeout.toMinutes(), 1L);
    }

    private boolean isZeroOrLess(Duration sessionTimeout) {
        return sessionTimeout == null || sessionTimeout.isNegative() || sessionTimeout.isZero();
    }

    protected void postProcessContext(Context context) {
    }

    protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
        return new TomcatWebServer(tomcat, this.getPort() >= 0, this.getShutdown());
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public Set<String> getTldSkipPatterns() {
        return this.tldSkipPatterns;
    }

    public void setTldSkipPatterns(Collection<String> patterns) {
        Assert.notNull(patterns, (String)"Patterns must not be null");
        this.tldSkipPatterns = new LinkedHashSet<String>(patterns);
    }

    public void addTldSkipPatterns(String ... patterns) {
        Assert.notNull((Object)patterns, (String)"Patterns must not be null");
        this.tldSkipPatterns.addAll(Arrays.asList(patterns));
    }

    public void setProtocol(String protocol) {
        Assert.hasLength((String)protocol, (String)"Protocol must not be empty");
        this.protocol = protocol;
    }

    public void setEngineValves(Collection<? extends Valve> engineValves) {
        Assert.notNull(engineValves, (String)"Valves must not be null");
        this.engineValves = new ArrayList<Valve>(engineValves);
    }

    public Collection<Valve> getEngineValves() {
        return this.engineValves;
    }

    @Override
    public void addEngineValves(Valve ... engineValves) {
        Assert.notNull((Object)engineValves, (String)"Valves must not be null");
        this.engineValves.addAll(Arrays.asList(engineValves));
    }

    public void setContextValves(Collection<? extends Valve> contextValves) {
        Assert.notNull(contextValves, (String)"Valves must not be null");
        this.contextValves = new ArrayList<Valve>(contextValves);
    }

    public Collection<Valve> getContextValves() {
        return this.contextValves;
    }

    public void addContextValves(Valve ... contextValves) {
        Assert.notNull((Object)contextValves, (String)"Valves must not be null");
        this.contextValves.addAll(Arrays.asList(contextValves));
    }

    public void setContextLifecycleListeners(Collection<? extends LifecycleListener> contextLifecycleListeners) {
        Assert.notNull(contextLifecycleListeners, (String)"ContextLifecycleListeners must not be null");
        this.contextLifecycleListeners = new ArrayList<LifecycleListener>(contextLifecycleListeners);
    }

    public Collection<LifecycleListener> getContextLifecycleListeners() {
        return this.contextLifecycleListeners;
    }

    public void addContextLifecycleListeners(LifecycleListener ... contextLifecycleListeners) {
        Assert.notNull((Object)contextLifecycleListeners, (String)"ContextLifecycleListeners must not be null");
        this.contextLifecycleListeners.addAll(Arrays.asList(contextLifecycleListeners));
    }

    public void setTomcatContextCustomizers(Collection<? extends TomcatContextCustomizer> tomcatContextCustomizers) {
        Assert.notNull(tomcatContextCustomizers, (String)"TomcatContextCustomizers must not be null");
        this.tomcatContextCustomizers = new LinkedHashSet<TomcatContextCustomizer>(tomcatContextCustomizers);
    }

    public Collection<TomcatContextCustomizer> getTomcatContextCustomizers() {
        return this.tomcatContextCustomizers;
    }

    @Override
    public void addContextCustomizers(TomcatContextCustomizer ... tomcatContextCustomizers) {
        Assert.notNull((Object)tomcatContextCustomizers, (String)"TomcatContextCustomizers must not be null");
        this.tomcatContextCustomizers.addAll(Arrays.asList(tomcatContextCustomizers));
    }

    public void setTomcatConnectorCustomizers(Collection<? extends TomcatConnectorCustomizer> tomcatConnectorCustomizers) {
        Assert.notNull(tomcatConnectorCustomizers, (String)"TomcatConnectorCustomizers must not be null");
        this.tomcatConnectorCustomizers = new LinkedHashSet<TomcatConnectorCustomizer>(tomcatConnectorCustomizers);
    }

    @Override
    public void addConnectorCustomizers(TomcatConnectorCustomizer ... tomcatConnectorCustomizers) {
        Assert.notNull((Object)tomcatConnectorCustomizers, (String)"TomcatConnectorCustomizers must not be null");
        this.tomcatConnectorCustomizers.addAll(Arrays.asList(tomcatConnectorCustomizers));
    }

    public Collection<TomcatConnectorCustomizer> getTomcatConnectorCustomizers() {
        return this.tomcatConnectorCustomizers;
    }

    public void setTomcatProtocolHandlerCustomizers(Collection<? extends TomcatProtocolHandlerCustomizer<?>> tomcatProtocolHandlerCustomizer) {
        Assert.notNull(tomcatProtocolHandlerCustomizer, (String)"TomcatProtocolHandlerCustomizers must not be null");
        this.tomcatProtocolHandlerCustomizers = new LinkedHashSet(tomcatProtocolHandlerCustomizer);
    }

    @Override
    public void addProtocolHandlerCustomizers(TomcatProtocolHandlerCustomizer<?> ... tomcatProtocolHandlerCustomizers) {
        Assert.notNull(tomcatProtocolHandlerCustomizers, (String)"TomcatProtocolHandlerCustomizers must not be null");
        this.tomcatProtocolHandlerCustomizers.addAll(Arrays.asList(tomcatProtocolHandlerCustomizers));
    }

    public Collection<TomcatProtocolHandlerCustomizer<?>> getTomcatProtocolHandlerCustomizers() {
        return this.tomcatProtocolHandlerCustomizers;
    }

    public void addAdditionalTomcatConnectors(Connector ... connectors) {
        Assert.notNull((Object)connectors, (String)"Connectors must not be null");
        this.additionalTomcatConnectors.addAll(Arrays.asList(connectors));
    }

    public List<Connector> getAdditionalTomcatConnectors() {
        return this.additionalTomcatConnectors;
    }

    @Override
    public void setUriEncoding(Charset uriEncoding) {
        this.uriEncoding = uriEncoding;
    }

    public Charset getUriEncoding() {
        return this.uriEncoding;
    }

    @Override
    public void setBackgroundProcessorDelay(int delay) {
        this.backgroundProcessorDelay = delay;
    }

    public void setDisableMBeanRegistry(boolean disableMBeanRegistry) {
        this.disableMBeanRegistry = disableMBeanRegistry;
    }

    private static class SuppliedSameSiteCookieProcessor
    extends Rfc6265CookieProcessor {
        private final List<CookieSameSiteSupplier> suppliers;

        SuppliedSameSiteCookieProcessor(List<CookieSameSiteSupplier> suppliers) {
            this.suppliers = suppliers;
        }

        public String generateHeader(Cookie cookie, HttpServletRequest request) {
            Cookie.SameSite sameSite = this.getSameSite(cookie);
            if (sameSite == null) {
                return super.generateHeader(cookie, request);
            }
            Rfc6265CookieProcessor delegate = new Rfc6265CookieProcessor();
            delegate.setSameSiteCookies(sameSite.attributeValue());
            return delegate.generateHeader(cookie, request);
        }

        private Cookie.SameSite getSameSite(Cookie cookie) {
            for (CookieSameSiteSupplier supplier : this.suppliers) {
                Cookie.SameSite sameSite = supplier.getSameSite(cookie);
                if (sameSite == null) continue;
                return sameSite;
            }
            return null;
        }
    }

    private static final class LoaderHidingWebResourceSet
    extends AbstractResourceSet {
        private final WebResourceSet delegate;
        private final Method initInternal;

        private LoaderHidingWebResourceSet(WebResourceSet delegate) {
            this.delegate = delegate;
            try {
                this.initInternal = LifecycleBase.class.getDeclaredMethod("initInternal", new Class[0]);
                this.initInternal.setAccessible(true);
            }
            catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }

        public WebResource getResource(String path) {
            if (path.startsWith("/org/springframework/boot")) {
                return new EmptyResource(this.getRoot(), path);
            }
            return this.delegate.getResource(path);
        }

        public String[] list(String path) {
            return this.delegate.list(path);
        }

        public Set<String> listWebAppPaths(String path) {
            return this.delegate.listWebAppPaths(path).stream().filter(webAppPath -> !webAppPath.startsWith("/org/springframework/boot")).collect(Collectors.toSet());
        }

        public boolean mkdir(String path) {
            return this.delegate.mkdir(path);
        }

        public boolean write(String path, InputStream is, boolean overwrite) {
            return this.delegate.write(path, is, overwrite);
        }

        public URL getBaseUrl() {
            return this.delegate.getBaseUrl();
        }

        public void setReadOnly(boolean readOnly) {
            this.delegate.setReadOnly(readOnly);
        }

        public boolean isReadOnly() {
            return this.delegate.isReadOnly();
        }

        public void gc() {
            this.delegate.gc();
        }

        protected void initInternal() throws LifecycleException {
            if (this.delegate instanceof LifecycleBase) {
                try {
                    ReflectionUtils.invokeMethod((Method)this.initInternal, (Object)this.delegate);
                }
                catch (Exception ex) {
                    throw new LifecycleException((Throwable)ex);
                }
            }
        }
    }

    private static final class LoaderHidingResourceRoot
    extends StandardRoot {
        private LoaderHidingResourceRoot(TomcatEmbeddedContext context) {
            super((Context)context);
        }

        protected WebResourceSet createMainResourceSet() {
            return new LoaderHidingWebResourceSet(super.createMainResourceSet());
        }
    }

    private final class StaticResourceConfigurer
    implements LifecycleListener {
        private final Context context;

        private StaticResourceConfigurer(Context context) {
            this.context = context;
        }

        public void lifecycleEvent(LifecycleEvent event) {
            if (event.getType().equals("configure_start")) {
                this.addResourceJars(TomcatServletWebServerFactory.this.getUrlsOfJarsWithMetaInfResources());
            }
        }

        private void addResourceJars(List<URL> resourceJarUrls) {
            for (URL url : resourceJarUrls) {
                String path = url.getPath();
                if (path.endsWith(".jar") || path.endsWith(".jar!/")) {
                    String jar = url.toString();
                    if (!jar.startsWith("jar:")) {
                        jar = "jar:" + jar + "!/";
                    }
                    this.addResourceSet(jar);
                    continue;
                }
                this.addResourceSet(url.toString());
            }
        }

        private void addResourceSet(String resource) {
            try {
                if (this.isInsideNestedJar(resource)) {
                    resource = resource.substring(0, resource.length() - 2);
                }
                URL url = new URL(resource);
                String path = "/META-INF/resources";
                this.context.getResources().createWebResourceSet(WebResourceRoot.ResourceSetType.RESOURCE_JAR, "/", url, path);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        private boolean isInsideNestedJar(String dir) {
            return dir.indexOf("!/") < dir.lastIndexOf("!/");
        }
    }

    private static class DisablePersistSessionListener
    implements LifecycleListener {
        private DisablePersistSessionListener() {
        }

        public void lifecycleEvent(LifecycleEvent event) {
            Context context;
            Manager manager;
            if (event.getType().equals("start") && (manager = (context = (Context)event.getLifecycle()).getManager()) instanceof StandardManager) {
                ((StandardManager)manager).setPathname(null);
            }
        }
    }
}

