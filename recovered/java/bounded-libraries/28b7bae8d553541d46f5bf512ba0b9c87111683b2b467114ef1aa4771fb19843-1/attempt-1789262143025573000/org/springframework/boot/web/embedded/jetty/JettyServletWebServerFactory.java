/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpServletResponseWrapper
 *  org.eclipse.jetty.http.HttpCookie
 *  org.eclipse.jetty.http.HttpCookie$SameSite
 *  org.eclipse.jetty.http.MimeTypes
 *  org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory
 *  org.eclipse.jetty.server.AbstractConnector
 *  org.eclipse.jetty.server.ConnectionFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.Handler
 *  org.eclipse.jetty.server.HttpConfiguration
 *  org.eclipse.jetty.server.HttpConnectionFactory
 *  org.eclipse.jetty.server.Request
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.ServerConnector
 *  org.eclipse.jetty.server.handler.ErrorHandler
 *  org.eclipse.jetty.server.handler.HandlerWrapper
 *  org.eclipse.jetty.server.handler.StatisticsHandler
 *  org.eclipse.jetty.server.session.DefaultSessionCache
 *  org.eclipse.jetty.server.session.FileSessionDataStore
 *  org.eclipse.jetty.server.session.SessionCache
 *  org.eclipse.jetty.server.session.SessionDataStore
 *  org.eclipse.jetty.server.session.SessionHandler
 *  org.eclipse.jetty.servlet.ErrorPageErrorHandler
 *  org.eclipse.jetty.servlet.ListenerHolder
 *  org.eclipse.jetty.servlet.ServletHandler
 *  org.eclipse.jetty.servlet.ServletHolder
 *  org.eclipse.jetty.servlet.ServletMapping
 *  org.eclipse.jetty.servlet.Source
 *  org.eclipse.jetty.servlet.Source$Origin
 *  org.eclipse.jetty.util.resource.JarResource
 *  org.eclipse.jetty.util.resource.Resource
 *  org.eclipse.jetty.util.resource.ResourceCollection
 *  org.eclipse.jetty.util.thread.ThreadPool
 *  org.eclipse.jetty.webapp.AbstractConfiguration
 *  org.eclipse.jetty.webapp.Configuration
 *  org.eclipse.jetty.webapp.WebAppContext
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.jetty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventListener;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.FileSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionDataStore;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ListenerHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.resource.JarResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.embedded.jetty.ForwardHeadersCustomizer;
import org.springframework.boot.web.embedded.jetty.JasperInitializer;
import org.springframework.boot.web.embedded.jetty.JettyEmbeddedErrorHandler;
import org.springframework.boot.web.embedded.jetty.JettyEmbeddedWebAppContext;
import org.springframework.boot.web.embedded.jetty.JettyHandlerWrappers;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.embedded.jetty.ServletContextInitializerConfiguration;
import org.springframework.boot.web.embedded.jetty.SslServerCustomizer;
import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class JettyServletWebServerFactory
extends AbstractServletWebServerFactory
implements ConfigurableJettyWebServerFactory,
ResourceLoaderAware {
    private List<Configuration> configurations = new ArrayList<Configuration>();
    private boolean useForwardHeaders;
    private int acceptors = -1;
    private int selectors = -1;
    private Set<JettyServerCustomizer> jettyServerCustomizers = new LinkedHashSet<JettyServerCustomizer>();
    private ResourceLoader resourceLoader;
    private ThreadPool threadPool;

    public JettyServletWebServerFactory() {
    }

    public JettyServletWebServerFactory(int port) {
        super(port);
    }

    public JettyServletWebServerFactory(String contextPath, int port) {
        super(contextPath, port);
    }

    @Override
    public WebServer getWebServer(ServletContextInitializer ... initializers) {
        JettyEmbeddedWebAppContext context = new JettyEmbeddedWebAppContext();
        int port = Math.max(this.getPort(), 0);
        InetSocketAddress address = new InetSocketAddress(this.getAddress(), port);
        Server server = this.createServer(address);
        this.configureWebAppContext(context, initializers);
        server.setHandler(this.addHandlerWrappers((Handler)context));
        this.logger.info((Object)("Server initialized with port: " + port));
        if (this.getSsl() != null && this.getSsl().isEnabled()) {
            this.customizeSsl(server, address);
        }
        for (JettyServerCustomizer customizer : this.getServerCustomizers()) {
            customizer.customize(server);
        }
        if (this.useForwardHeaders) {
            new ForwardHeadersCustomizer().customize(server);
        }
        if (this.getShutdown() == Shutdown.GRACEFUL) {
            StatisticsHandler statisticsHandler = new StatisticsHandler();
            statisticsHandler.setHandler(server.getHandler());
            server.setHandler((Handler)statisticsHandler);
        }
        return this.getJettyWebServer(server);
    }

    private Server createServer(InetSocketAddress address) {
        Server server = new Server(this.getThreadPool());
        server.setConnectors(new Connector[]{this.createConnector(address, server)});
        server.setStopTimeout(0L);
        return server;
    }

    private AbstractConnector createConnector(InetSocketAddress address, Server server) {
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setSendServerVersion(false);
        ArrayList<Object> connectionFactories = new ArrayList<Object>();
        connectionFactories.add(new HttpConnectionFactory(httpConfiguration));
        if (this.getHttp2() != null && this.getHttp2().isEnabled()) {
            connectionFactories.add(new HTTP2CServerConnectionFactory(httpConfiguration));
        }
        ServerConnector connector = new ServerConnector(server, this.acceptors, this.selectors, connectionFactories.toArray(new ConnectionFactory[0]));
        connector.setHost(address.getHostString());
        connector.setPort(address.getPort());
        return connector;
    }

    private Handler addHandlerWrappers(Handler handler) {
        if (this.getCompression() != null && this.getCompression().getEnabled()) {
            handler = this.applyWrapper(handler, JettyHandlerWrappers.createGzipHandlerWrapper(this.getCompression()));
        }
        if (StringUtils.hasText((String)this.getServerHeader())) {
            handler = this.applyWrapper(handler, JettyHandlerWrappers.createServerHeaderHandlerWrapper(this.getServerHeader()));
        }
        if (!CollectionUtils.isEmpty(this.getCookieSameSiteSuppliers())) {
            handler = this.applyWrapper(handler, new SuppliedSameSiteCookieHandlerWrapper(this.getCookieSameSiteSuppliers()));
        }
        return handler;
    }

    private Handler applyWrapper(Handler handler, HandlerWrapper wrapper) {
        wrapper.setHandler(handler);
        return wrapper;
    }

    private void customizeSsl(Server server, InetSocketAddress address) {
        new SslServerCustomizer(address, this.getSsl(), this.getOrCreateSslStoreProvider(), this.getHttp2()).customize(server);
    }

    protected final void configureWebAppContext(WebAppContext context, ServletContextInitializer ... initializers) {
        String contextPath;
        Assert.notNull((Object)context, (String)"Context must not be null");
        context.clearAliasChecks();
        context.setTempDirectory(this.getTempDirectory());
        if (this.resourceLoader != null) {
            context.setClassLoader(this.resourceLoader.getClassLoader());
        }
        context.setContextPath(StringUtils.hasLength((String)(contextPath = this.getContextPath())) ? contextPath : "/");
        context.setDisplayName(this.getDisplayName());
        this.configureDocumentRoot(context);
        if (this.isRegisterDefaultServlet()) {
            this.addDefaultServlet(context);
        }
        if (this.shouldRegisterJspServlet()) {
            this.addJspServlet(context);
            context.addBean((Object)new JasperInitializer(context), true);
        }
        this.addLocaleMappings(context);
        ServletContextInitializer[] initializersToUse = this.mergeInitializers(initializers);
        Configuration[] configurations = this.getWebAppContextConfigurations(context, initializersToUse);
        context.setConfigurations(configurations);
        context.setThrowUnavailableOnStartupException(true);
        this.configureSession(context);
        this.postProcessWebAppContext(context);
    }

    private void configureSession(WebAppContext context) {
        Duration sessionTimeout;
        SessionHandler handler = context.getSessionHandler();
        Cookie.SameSite sessionSameSite = this.getSession().getCookie().getSameSite();
        if (sessionSameSite != null) {
            handler.setSameSite(HttpCookie.SameSite.valueOf((String)sessionSameSite.name()));
        }
        handler.setMaxInactiveInterval(this.isNegative(sessionTimeout = this.getSession().getTimeout()) ? -1 : (int)sessionTimeout.getSeconds());
        if (this.getSession().isPersistent()) {
            DefaultSessionCache cache = new DefaultSessionCache(handler);
            FileSessionDataStore store = new FileSessionDataStore();
            store.setStoreDir(this.getValidSessionStoreDir());
            cache.setSessionDataStore((SessionDataStore)store);
            handler.setSessionCache((SessionCache)cache);
        }
    }

    private boolean isNegative(Duration sessionTimeout) {
        return sessionTimeout == null || sessionTimeout.isNegative();
    }

    private void addLocaleMappings(WebAppContext context) {
        this.getLocaleCharsetMappings().forEach((locale, charset) -> context.addLocaleEncoding(locale.toString(), charset.toString()));
    }

    private File getTempDirectory() {
        String temp = System.getProperty("java.io.tmpdir");
        return temp != null ? new File(temp) : null;
    }

    private void configureDocumentRoot(WebAppContext handler) {
        File root = this.getValidDocumentRoot();
        File docBase = root != null ? root : this.createTempDir("jetty-docbase");
        try {
            ArrayList<Resource> resources = new ArrayList<Resource>();
            Resource rootResource = docBase.isDirectory() ? Resource.newResource((File)docBase.getCanonicalFile()) : JarResource.newJarResource((Resource)Resource.newResource((File)docBase));
            resources.add(root != null ? new LoaderHidingResource(rootResource) : rootResource);
            for (URL resourceJarUrl : this.getUrlsOfJarsWithMetaInfResources()) {
                Resource resource = this.createResource(resourceJarUrl);
                if (!resource.exists() || !resource.isDirectory()) continue;
                resources.add(resource);
            }
            handler.setBaseResource((Resource)new ResourceCollection(resources.toArray(new Resource[0])));
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private Resource createResource(URL url) throws Exception {
        File file;
        if ("file".equals(url.getProtocol()) && (file = new File(url.toURI())).isFile()) {
            return Resource.newResource((String)("jar:" + url + "!/META-INF/resources"));
        }
        return Resource.newResource((String)(url + "META-INF/resources"));
    }

    protected final void addDefaultServlet(WebAppContext context) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        ServletHolder holder = new ServletHolder();
        holder.setName("default");
        holder.setClassName("org.eclipse.jetty.servlet.DefaultServlet");
        holder.setInitParameter("dirAllowed", "false");
        holder.setInitOrder(1);
        context.getServletHandler().addServletWithMapping(holder, "/");
        ServletMapping servletMapping = context.getServletHandler().getServletMapping("/");
        try {
            servletMapping.setDefault(true);
        }
        catch (NoSuchMethodError ex) {
            Method setFromDefaultDescriptor = ReflectionUtils.findMethod(servletMapping.getClass(), (String)"setFromDefaultDescriptor", (Class[])new Class[]{Boolean.TYPE});
            ReflectionUtils.invokeMethod((Method)setFromDefaultDescriptor, (Object)servletMapping, (Object[])new Object[]{true});
        }
    }

    protected final void addJspServlet(WebAppContext context) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        ServletHolder holder = new ServletHolder();
        holder.setName("jsp");
        holder.setClassName(this.getJsp().getClassName());
        holder.setInitParameter("fork", "false");
        holder.setInitParameters(this.getJsp().getInitParameters());
        holder.setInitOrder(3);
        context.getServletHandler().addServlet(holder);
        ServletMapping mapping = new ServletMapping();
        mapping.setServletName("jsp");
        mapping.setPathSpecs(new String[]{"*.jsp", "*.jspx"});
        context.getServletHandler().addServletMapping(mapping);
    }

    protected Configuration[] getWebAppContextConfigurations(WebAppContext webAppContext, ServletContextInitializer ... initializers) {
        ArrayList<Object> configurations = new ArrayList<Object>();
        configurations.add(this.getServletContextInitializerConfiguration(webAppContext, initializers));
        configurations.add(this.getErrorPageConfiguration());
        configurations.add(this.getMimeTypeConfiguration());
        configurations.add((Object)new WebListenersConfiguration(this.getWebListenerClassNames()));
        configurations.addAll(this.getConfigurations());
        return configurations.toArray(new Configuration[0]);
    }

    private Configuration getErrorPageConfiguration() {
        return new AbstractConfiguration(){

            public void configure(WebAppContext context) throws Exception {
                JettyEmbeddedErrorHandler errorHandler = new JettyEmbeddedErrorHandler();
                context.setErrorHandler((ErrorHandler)errorHandler);
                JettyServletWebServerFactory.this.addJettyErrorPages((ErrorHandler)errorHandler, JettyServletWebServerFactory.this.getErrorPages());
            }
        };
    }

    private Configuration getMimeTypeConfiguration() {
        return new AbstractConfiguration(){

            public void configure(WebAppContext context) throws Exception {
                MimeTypes mimeTypes = context.getMimeTypes();
                for (MimeMappings.Mapping mapping : JettyServletWebServerFactory.this.getMimeMappings()) {
                    mimeTypes.addMimeMapping(mapping.getExtension(), mapping.getMimeType());
                }
            }
        };
    }

    protected Configuration getServletContextInitializerConfiguration(WebAppContext webAppContext, ServletContextInitializer ... initializers) {
        return new ServletContextInitializerConfiguration(initializers);
    }

    protected void postProcessWebAppContext(WebAppContext webAppContext) {
    }

    protected JettyWebServer getJettyWebServer(Server server) {
        return new JettyWebServer(server, this.getPort() >= 0);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setUseForwardHeaders(boolean useForwardHeaders) {
        this.useForwardHeaders = useForwardHeaders;
    }

    @Override
    public void setAcceptors(int acceptors) {
        this.acceptors = acceptors;
    }

    @Override
    public void setSelectors(int selectors) {
        this.selectors = selectors;
    }

    public void setServerCustomizers(Collection<? extends JettyServerCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        this.jettyServerCustomizers = new LinkedHashSet<JettyServerCustomizer>(customizers);
    }

    public Collection<JettyServerCustomizer> getServerCustomizers() {
        return this.jettyServerCustomizers;
    }

    @Override
    public void addServerCustomizers(JettyServerCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        this.jettyServerCustomizers.addAll(Arrays.asList(customizers));
    }

    public void setConfigurations(Collection<? extends Configuration> configurations) {
        Assert.notNull(configurations, (String)"Configurations must not be null");
        this.configurations = new ArrayList<Configuration>(configurations);
    }

    public Collection<Configuration> getConfigurations() {
        return this.configurations;
    }

    public void addConfigurations(Configuration ... configurations) {
        Assert.notNull((Object)configurations, (String)"Configurations must not be null");
        this.configurations.addAll(Arrays.asList(configurations));
    }

    public ThreadPool getThreadPool() {
        return this.threadPool;
    }

    @Override
    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    private void addJettyErrorPages(ErrorHandler errorHandler, Collection<ErrorPage> errorPages) {
        if (errorHandler instanceof ErrorPageErrorHandler) {
            ErrorPageErrorHandler handler = (ErrorPageErrorHandler)errorHandler;
            for (ErrorPage errorPage : errorPages) {
                if (errorPage.isGlobal()) {
                    handler.addErrorPage("org.eclipse.jetty.server.error_page.global", errorPage.getPath());
                    continue;
                }
                if (errorPage.getExceptionName() != null) {
                    handler.addErrorPage(errorPage.getExceptionName(), errorPage.getPath());
                    continue;
                }
                handler.addErrorPage(errorPage.getStatusCode(), errorPage.getPath());
            }
        }
    }

    private static class SuppliedSameSiteCookieHandlerWrapper
    extends HandlerWrapper {
        private final List<CookieSameSiteSupplier> suppliers;

        SuppliedSameSiteCookieHandlerWrapper(List<CookieSameSiteSupplier> suppliers) {
            this.suppliers = suppliers;
        }

        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            ResponseWrapper wrappedResponse = new ResponseWrapper(response);
            super.handle(target, baseRequest, request, (HttpServletResponse)wrappedResponse);
        }

        class ResponseWrapper
        extends HttpServletResponseWrapper {
            ResponseWrapper(HttpServletResponse response) {
                super(response);
            }

            public void addCookie(Cookie cookie) {
                Cookie.SameSite sameSite = this.getSameSite(cookie);
                if (sameSite != null) {
                    String comment = HttpCookie.getCommentWithoutAttributes((String)cookie.getComment());
                    String sameSiteComment = this.getSameSiteComment(sameSite);
                    cookie.setComment(comment != null ? comment + sameSiteComment : sameSiteComment);
                }
                super.addCookie(cookie);
            }

            private String getSameSiteComment(Cookie.SameSite sameSite) {
                switch (sameSite) {
                    case NONE: {
                        return "__SAME_SITE_NONE__";
                    }
                    case LAX: {
                        return "__SAME_SITE_LAX__";
                    }
                    case STRICT: {
                        return "__SAME_SITE_STRICT__";
                    }
                }
                throw new IllegalStateException("Unsupported SameSite value " + (Object)((Object)sameSite));
            }

            private Cookie.SameSite getSameSite(Cookie cookie) {
                for (CookieSameSiteSupplier supplier : SuppliedSameSiteCookieHandlerWrapper.this.suppliers) {
                    Cookie.SameSite sameSite = supplier.getSameSite(cookie);
                    if (sameSite == null) continue;
                    return sameSite;
                }
                return null;
            }
        }
    }

    private static class WebListenersConfiguration
    extends AbstractConfiguration {
        private final Set<String> classNames;

        WebListenersConfiguration(Set<String> webListenerClassNames) {
            this.classNames = webListenerClassNames;
        }

        public void configure(WebAppContext context) throws Exception {
            ServletHandler servletHandler = context.getServletHandler();
            for (String className : this.classNames) {
                this.configure(context, servletHandler, className);
            }
        }

        private void configure(WebAppContext context, ServletHandler servletHandler, String className) throws ClassNotFoundException {
            ListenerHolder holder = servletHandler.newListenerHolder(new Source(Source.Origin.ANNOTATION, className));
            holder.setHeldClass(this.loadClass(context, className));
            servletHandler.addListener(holder);
        }

        private Class<? extends EventListener> loadClass(WebAppContext context, String className) throws ClassNotFoundException {
            ClassLoader classLoader = context.getClassLoader();
            classLoader = classLoader != null ? classLoader : ((Object)((Object)this)).getClass().getClassLoader();
            return classLoader.loadClass(className);
        }
    }

    private static final class LoaderHidingResource
    extends Resource {
        private final Resource delegate;

        private LoaderHidingResource(Resource delegate) {
            this.delegate = delegate;
        }

        public Resource addPath(String path) throws IOException {
            if (path.startsWith("/org/springframework/boot")) {
                return null;
            }
            return this.delegate.addPath(path);
        }

        public boolean isContainedIn(Resource resource) throws MalformedURLException {
            return this.delegate.isContainedIn(resource);
        }

        public void close() {
            this.delegate.close();
        }

        public boolean exists() {
            return this.delegate.exists();
        }

        public boolean isDirectory() {
            return this.delegate.isDirectory();
        }

        public long lastModified() {
            return this.delegate.lastModified();
        }

        public long length() {
            return this.delegate.length();
        }

        @Deprecated
        public URL getURL() {
            return this.delegate.getURL();
        }

        public File getFile() throws IOException {
            return this.delegate.getFile();
        }

        public String getName() {
            return this.delegate.getName();
        }

        public InputStream getInputStream() throws IOException {
            return this.delegate.getInputStream();
        }

        public ReadableByteChannel getReadableByteChannel() throws IOException {
            return this.delegate.getReadableByteChannel();
        }

        public boolean delete() throws SecurityException {
            return this.delegate.delete();
        }

        public boolean renameTo(Resource dest) throws SecurityException {
            return this.delegate.renameTo(dest);
        }

        public String[] list() {
            return this.delegate.list();
        }
    }
}

