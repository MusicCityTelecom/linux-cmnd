/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow$Builder
 *  io.undertow.server.HttpHandler
 *  io.undertow.server.HttpServerExchange
 *  io.undertow.server.handlers.Cookie
 *  io.undertow.server.handlers.resource.FileResourceManager
 *  io.undertow.server.handlers.resource.Resource
 *  io.undertow.server.handlers.resource.ResourceChangeListener
 *  io.undertow.server.handlers.resource.ResourceManager
 *  io.undertow.server.handlers.resource.URLResource
 *  io.undertow.server.session.SessionManager
 *  io.undertow.servlet.Servlets
 *  io.undertow.servlet.api.Deployment
 *  io.undertow.servlet.api.DeploymentInfo
 *  io.undertow.servlet.api.DeploymentManager
 *  io.undertow.servlet.api.ErrorPage
 *  io.undertow.servlet.api.InstanceFactory
 *  io.undertow.servlet.api.ListenerInfo
 *  io.undertow.servlet.api.MimeMapping
 *  io.undertow.servlet.api.ServletContainerInitializerInfo
 *  io.undertow.servlet.api.ServletStackTraces
 *  io.undertow.servlet.api.SessionPersistenceManager
 *  io.undertow.servlet.core.DeploymentImpl
 *  io.undertow.servlet.handlers.DefaultServlet
 *  io.undertow.servlet.util.ImmediateInstanceFactory
 *  javax.servlet.ServletContainerInitializer
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.http.Cookie
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceChangeListener;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.server.handlers.resource.URLResource;
import io.undertow.server.session.SessionManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.Deployment;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.MimeMapping;
import io.undertow.servlet.api.ServletContainerInitializerInfo;
import io.undertow.servlet.api.ServletStackTraces;
import io.undertow.servlet.api.SessionPersistenceManager;
import io.undertow.servlet.core.DeploymentImpl;
import io.undertow.servlet.handlers.DefaultServlet;
import io.undertow.servlet.util.ImmediateInstanceFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.undertow.CompositeResourceManager;
import org.springframework.boot.web.embedded.undertow.ConfigurableUndertowWebServerFactory;
import org.springframework.boot.web.embedded.undertow.DeploymentManagerHttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.FileSessionPersistence;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.JarResourceManager;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowWebServerFactoryDelegate;
import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class UndertowServletWebServerFactory
extends AbstractServletWebServerFactory
implements ConfigurableUndertowWebServerFactory,
ResourceLoaderAware {
    private static final Pattern ENCODED_SLASH = Pattern.compile("%2F", 16);
    private static final Set<Class<?>> NO_CLASSES = Collections.emptySet();
    private UndertowWebServerFactoryDelegate delegate = new UndertowWebServerFactoryDelegate();
    private Set<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers = new LinkedHashSet<UndertowDeploymentInfoCustomizer>();
    private ResourceLoader resourceLoader;
    private boolean eagerFilterInit = true;
    private boolean preservePathOnForward = false;

    public UndertowServletWebServerFactory() {
        this.getJsp().setRegistered(false);
    }

    public UndertowServletWebServerFactory(int port) {
        super(port);
        this.getJsp().setRegistered(false);
    }

    public UndertowServletWebServerFactory(String contextPath, int port) {
        super(contextPath, port);
        this.getJsp().setRegistered(false);
    }

    @Override
    public void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers) {
        this.delegate.setBuilderCustomizers(customizers);
    }

    @Override
    public void addBuilderCustomizers(UndertowBuilderCustomizer ... customizers) {
        this.delegate.addBuilderCustomizers(customizers);
    }

    public Collection<UndertowBuilderCustomizer> getBuilderCustomizers() {
        return this.delegate.getBuilderCustomizers();
    }

    @Override
    public void setBufferSize(Integer bufferSize) {
        this.delegate.setBufferSize(bufferSize);
    }

    @Override
    public void setIoThreads(Integer ioThreads) {
        this.delegate.setIoThreads(ioThreads);
    }

    @Override
    public void setWorkerThreads(Integer workerThreads) {
        this.delegate.setWorkerThreads(workerThreads);
    }

    @Override
    public void setUseDirectBuffers(Boolean directBuffers) {
        this.delegate.setUseDirectBuffers(directBuffers);
    }

    @Override
    public void setAccessLogDirectory(File accessLogDirectory) {
        this.delegate.setAccessLogDirectory(accessLogDirectory);
    }

    @Override
    public void setAccessLogPattern(String accessLogPattern) {
        this.delegate.setAccessLogPattern(accessLogPattern);
    }

    @Override
    public void setAccessLogPrefix(String accessLogPrefix) {
        this.delegate.setAccessLogPrefix(accessLogPrefix);
    }

    public String getAccessLogPrefix() {
        return this.delegate.getAccessLogPrefix();
    }

    @Override
    public void setAccessLogSuffix(String accessLogSuffix) {
        this.delegate.setAccessLogSuffix(accessLogSuffix);
    }

    @Override
    public void setAccessLogEnabled(boolean accessLogEnabled) {
        this.delegate.setAccessLogEnabled(accessLogEnabled);
    }

    public boolean isAccessLogEnabled() {
        return this.delegate.isAccessLogEnabled();
    }

    @Override
    public void setAccessLogRotate(boolean accessLogRotate) {
        this.delegate.setAccessLogRotate(accessLogRotate);
    }

    @Override
    public void setUseForwardHeaders(boolean useForwardHeaders) {
        this.delegate.setUseForwardHeaders(useForwardHeaders);
    }

    protected final boolean isUseForwardHeaders() {
        return this.delegate.isUseForwardHeaders();
    }

    public void setDeploymentInfoCustomizers(Collection<? extends UndertowDeploymentInfoCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        this.deploymentInfoCustomizers = new LinkedHashSet<UndertowDeploymentInfoCustomizer>(customizers);
    }

    public void addDeploymentInfoCustomizers(UndertowDeploymentInfoCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"UndertowDeploymentInfoCustomizers must not be null");
        this.deploymentInfoCustomizers.addAll(Arrays.asList(customizers));
    }

    public Collection<UndertowDeploymentInfoCustomizer> getDeploymentInfoCustomizers() {
        return this.deploymentInfoCustomizers;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public boolean isEagerFilterInit() {
        return this.eagerFilterInit;
    }

    public void setEagerFilterInit(boolean eagerFilterInit) {
        this.eagerFilterInit = eagerFilterInit;
    }

    public boolean isPreservePathOnForward() {
        return this.preservePathOnForward;
    }

    public void setPreservePathOnForward(boolean preservePathOnForward) {
        this.preservePathOnForward = preservePathOnForward;
    }

    @Override
    public WebServer getWebServer(ServletContextInitializer ... initializers) {
        Undertow.Builder builder = this.delegate.createBuilder(this);
        DeploymentManager manager = this.createManager(initializers);
        return this.getUndertowWebServer(builder, manager, this.getPort());
    }

    private DeploymentManager createManager(ServletContextInitializer ... initializers) {
        DeploymentInfo deployment = Servlets.deployment();
        this.registerServletContainerInitializerToDriveServletContextInitializers(deployment, initializers);
        deployment.setClassLoader(this.getServletClassLoader());
        deployment.setContextPath(this.getContextPath());
        deployment.setDisplayName(this.getDisplayName());
        deployment.setDeploymentName("spring-boot");
        if (this.isRegisterDefaultServlet()) {
            deployment.addServlet(Servlets.servlet((String)"default", DefaultServlet.class));
        }
        this.configureErrorPages(deployment);
        deployment.setServletStackTraces(ServletStackTraces.NONE);
        deployment.setResourceManager(this.getDocumentRootResourceManager());
        deployment.setTempDir(this.createTempDir("undertow"));
        deployment.setEagerFilterInit(this.eagerFilterInit);
        deployment.setPreservePathOnForward(this.preservePathOnForward);
        this.configureMimeMappings(deployment);
        this.configureWebListeners(deployment);
        for (UndertowDeploymentInfoCustomizer customizer : this.deploymentInfoCustomizers) {
            customizer.customize(deployment);
        }
        if (this.getSession().isPersistent()) {
            File dir = this.getValidSessionStoreDir();
            deployment.setSessionPersistenceManager((SessionPersistenceManager)new FileSessionPersistence(dir));
        }
        this.addLocaleMappings(deployment);
        DeploymentManager manager = Servlets.newContainer().addDeployment(deployment);
        manager.deploy();
        if (manager.getDeployment() instanceof DeploymentImpl) {
            this.removeSuperfluousMimeMappings((DeploymentImpl)manager.getDeployment(), deployment);
        }
        SessionManager sessionManager = manager.getDeployment().getSessionManager();
        Duration timeoutDuration = this.getSession().getTimeout();
        int sessionTimeout = this.isZeroOrLess(timeoutDuration) ? -1 : (int)timeoutDuration.getSeconds();
        sessionManager.setDefaultSessionTimeout(sessionTimeout);
        return manager;
    }

    private void configureWebListeners(DeploymentInfo deployment) {
        for (String className : this.getWebListenerClassNames()) {
            try {
                deployment.addListener(new ListenerInfo(this.loadWebListenerClass(className)));
            }
            catch (ClassNotFoundException ex) {
                throw new IllegalStateException("Failed to load web listener class '" + className + "'", ex);
            }
        }
    }

    private Class<? extends EventListener> loadWebListenerClass(String className) throws ClassNotFoundException {
        return this.getServletClassLoader().loadClass(className);
    }

    private boolean isZeroOrLess(Duration timeoutDuration) {
        return timeoutDuration == null || timeoutDuration.isZero() || timeoutDuration.isNegative();
    }

    private void addLocaleMappings(DeploymentInfo deployment) {
        this.getLocaleCharsetMappings().forEach((locale, charset) -> deployment.addLocaleCharsetMapping(locale.toString(), charset.toString()));
    }

    private void registerServletContainerInitializerToDriveServletContextInitializers(DeploymentInfo deployment, ServletContextInitializer ... initializers) {
        ServletContextInitializer[] mergedInitializers = this.mergeInitializers(initializers);
        Initializer initializer = new Initializer(mergedInitializers);
        deployment.addServletContainerInitializer(new ServletContainerInitializerInfo(Initializer.class, (InstanceFactory)new ImmediateInstanceFactory((Object)initializer), NO_CLASSES));
    }

    private ClassLoader getServletClassLoader() {
        if (this.resourceLoader != null) {
            return this.resourceLoader.getClassLoader();
        }
        return this.getClass().getClassLoader();
    }

    private ResourceManager getDocumentRootResourceManager() {
        Object rootManager;
        File root = this.getValidDocumentRoot();
        File docBase = this.getCanonicalDocumentRoot(root);
        List<URL> metaInfResourceUrls = this.getUrlsOfJarsWithMetaInfResources();
        ArrayList<URL> resourceJarUrls = new ArrayList<URL>();
        ArrayList<Object> managers = new ArrayList<Object>();
        Object object = rootManager = docBase.isDirectory() ? new FileResourceManager(docBase, 0L) : new JarResourceManager(docBase);
        if (root != null) {
            rootManager = new LoaderHidingResourceManager((ResourceManager)rootManager);
        }
        managers.add(rootManager);
        for (URL url : metaInfResourceUrls) {
            if ("file".equals(url.getProtocol())) {
                try {
                    File file = new File(url.toURI());
                    if (file.isFile()) {
                        resourceJarUrls.add(new URL("jar:" + url + "!/"));
                        continue;
                    }
                    managers.add(new FileResourceManager(new File(file, "META-INF/resources"), 0L));
                    continue;
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            resourceJarUrls.add(url);
        }
        managers.add(new MetaInfResourcesResourceManager(resourceJarUrls));
        return new CompositeResourceManager(managers.toArray(new ResourceManager[0]));
    }

    private File getCanonicalDocumentRoot(File docBase) {
        try {
            File root = docBase != null ? docBase : this.createTempDir("undertow-docbase");
            return root.getCanonicalFile();
        }
        catch (IOException ex) {
            throw new IllegalStateException("Cannot get canonical document root", ex);
        }
    }

    private void configureErrorPages(DeploymentInfo deployment) {
        for (ErrorPage errorPage : this.getErrorPages()) {
            deployment.addErrorPage(this.getUndertowErrorPage(errorPage));
        }
    }

    private io.undertow.servlet.api.ErrorPage getUndertowErrorPage(ErrorPage errorPage) {
        if (errorPage.getStatus() != null) {
            return new io.undertow.servlet.api.ErrorPage(errorPage.getPath(), errorPage.getStatusCode());
        }
        if (errorPage.getException() != null) {
            return new io.undertow.servlet.api.ErrorPage(errorPage.getPath(), errorPage.getException());
        }
        return new io.undertow.servlet.api.ErrorPage(errorPage.getPath());
    }

    private void configureMimeMappings(DeploymentInfo deployment) {
        for (MimeMappings.Mapping mimeMapping : this.getMimeMappings()) {
            deployment.addMimeMapping(new MimeMapping(mimeMapping.getExtension(), mimeMapping.getMimeType()));
        }
    }

    private void removeSuperfluousMimeMappings(DeploymentImpl deployment, DeploymentInfo deploymentInfo) {
        HashMap<String, String> mappings = new HashMap<String, String>();
        for (MimeMapping mapping : deploymentInfo.getMimeMappings()) {
            mappings.put(mapping.getExtension().toLowerCase(Locale.ENGLISH), mapping.getMimeType());
        }
        deployment.setMimeExtensionMappings(mappings);
    }

    protected UndertowServletWebServer getUndertowWebServer(Undertow.Builder builder, DeploymentManager manager, int port) {
        ArrayList<HttpHandlerFactory> initialHandlerFactories = new ArrayList<HttpHandlerFactory>();
        initialHandlerFactories.add(new DeploymentManagerHttpHandlerFactory(manager));
        HttpHandlerFactory cooHandlerFactory = this.getCookieHandlerFactory(manager.getDeployment());
        if (cooHandlerFactory != null) {
            initialHandlerFactories.add(cooHandlerFactory);
        }
        List<HttpHandlerFactory> httpHandlerFactories = this.delegate.createHttpHandlerFactories(this, initialHandlerFactories.toArray(new HttpHandlerFactory[0]));
        return new UndertowServletWebServer(builder, httpHandlerFactories, this.getContextPath(), port >= 0);
    }

    private HttpHandlerFactory getCookieHandlerFactory(Deployment deployment) {
        Cookie.SameSite sessionSameSite = this.getSession().getCookie().getSameSite();
        ArrayList<CookieSameSiteSupplier> suppliers = new ArrayList<CookieSameSiteSupplier>();
        if (sessionSameSite != null) {
            String sessionCookieName = deployment.getServletContext().getSessionCookieConfig().getName();
            suppliers.add(CookieSameSiteSupplier.of(sessionSameSite).whenHasName(sessionCookieName));
        }
        if (!CollectionUtils.isEmpty(this.getCookieSameSiteSuppliers())) {
            suppliers.addAll(this.getCookieSameSiteSuppliers());
        }
        return !suppliers.isEmpty() ? next -> new SuppliedSameSiteCookieHandler(next, suppliers) : null;
    }

    private static class SuppliedSameSiteCookieHandler
    implements HttpHandler {
        private final HttpHandler next;
        private final List<CookieSameSiteSupplier> suppliers;

        SuppliedSameSiteCookieHandler(HttpHandler next, List<CookieSameSiteSupplier> suppliers) {
            this.next = next;
            this.suppliers = suppliers;
        }

        public void handleRequest(HttpServerExchange exchange) throws Exception {
            exchange.addResponseCommitListener(this::beforeCommit);
            this.next.handleRequest(exchange);
        }

        private void beforeCommit(HttpServerExchange exchange) {
            for (io.undertow.server.handlers.Cookie cookie : exchange.responseCookies()) {
                Cookie.SameSite sameSite = this.getSameSite(this.asServletCookie(cookie));
                if (sameSite == null) continue;
                cookie.setSameSiteMode(sameSite.attributeValue());
            }
        }

        private Cookie asServletCookie(io.undertow.server.handlers.Cookie cookie) {
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            Cookie result = new Cookie(cookie.getName(), cookie.getValue());
            map.from(() -> ((io.undertow.server.handlers.Cookie)cookie).getComment()).to(arg_0 -> ((Cookie)result).setComment(arg_0));
            map.from(() -> ((io.undertow.server.handlers.Cookie)cookie).getDomain()).to(arg_0 -> ((Cookie)result).setDomain(arg_0));
            map.from(() -> ((io.undertow.server.handlers.Cookie)cookie).getMaxAge()).to(arg_0 -> ((Cookie)result).setMaxAge(arg_0));
            map.from(() -> ((io.undertow.server.handlers.Cookie)cookie).getPath()).to(arg_0 -> ((Cookie)result).setPath(arg_0));
            result.setSecure(cookie.isSecure());
            result.setVersion(cookie.getVersion());
            result.setHttpOnly(cookie.isHttpOnly());
            return result;
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

    private static final class LoaderHidingResourceManager
    implements ResourceManager {
        private final ResourceManager delegate;

        private LoaderHidingResourceManager(ResourceManager delegate) {
            this.delegate = delegate;
        }

        public Resource getResource(String path) throws IOException {
            if (path.startsWith("/org/springframework/boot")) {
                return null;
            }
            return this.delegate.getResource(path);
        }

        public boolean isResourceChangeListenerSupported() {
            return this.delegate.isResourceChangeListenerSupported();
        }

        public void registerResourceChangeListener(ResourceChangeListener listener) {
            this.delegate.registerResourceChangeListener(listener);
        }

        public void removeResourceChangeListener(ResourceChangeListener listener) {
            this.delegate.removeResourceChangeListener(listener);
        }

        public void close() throws IOException {
            this.delegate.close();
        }
    }

    private static final class MetaInfResourcesResourceManager
    implements ResourceManager {
        private final List<URL> metaInfResourceJarUrls;

        private MetaInfResourcesResourceManager(List<URL> metaInfResourceJarUrls) {
            this.metaInfResourceJarUrls = metaInfResourceJarUrls;
        }

        public void close() throws IOException {
        }

        public Resource getResource(String path) {
            for (URL url : this.metaInfResourceJarUrls) {
                URLResource resource = this.getMetaInfResource(url, path);
                if (resource == null) continue;
                return resource;
            }
            return null;
        }

        public boolean isResourceChangeListenerSupported() {
            return false;
        }

        public void registerResourceChangeListener(ResourceChangeListener listener) {
        }

        public void removeResourceChangeListener(ResourceChangeListener listener) {
        }

        private URLResource getMetaInfResource(URL resourceJar, String path) {
            try {
                String urlPath = URLEncoder.encode(ENCODED_SLASH.matcher(path).replaceAll("/"), "UTF-8");
                URL resourceUrl = new URL(resourceJar + "META-INF/resources" + urlPath);
                URLResource resource = new URLResource(resourceUrl, path);
                if (resource.getContentLength() < 0L) {
                    return null;
                }
                return resource;
            }
            catch (Exception ex) {
                return null;
            }
        }
    }

    private static class Initializer
    implements ServletContainerInitializer {
        private final ServletContextInitializer[] initializers;

        Initializer(ServletContextInitializer[] initializers) {
            this.initializers = initializers;
        }

        public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {
            for (ServletContextInitializer initializer : this.initializers) {
                initializer.onStartup(servletContext);
            }
        }
    }
}

