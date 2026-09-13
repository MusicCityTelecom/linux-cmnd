/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.SessionCookieConfig
 *  javax.servlet.SessionTrackingMode
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.server.AbstractConfigurableWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.boot.web.servlet.server.DocumentRoot;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.boot.web.servlet.server.StaticResourceJars;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public abstract class AbstractServletWebServerFactory
extends AbstractConfigurableWebServerFactory
implements ConfigurableServletWebServerFactory {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String contextPath = "";
    private String displayName;
    private Session session = new Session();
    private boolean registerDefaultServlet = false;
    private MimeMappings mimeMappings = new MimeMappings(MimeMappings.DEFAULT);
    private List<ServletContextInitializer> initializers = new ArrayList<ServletContextInitializer>();
    private Jsp jsp = new Jsp();
    private Map<Locale, Charset> localeCharsetMappings = new HashMap<Locale, Charset>();
    private Map<String, String> initParameters = Collections.emptyMap();
    private List<CookieSameSiteSupplier> cookieSameSiteSuppliers = new ArrayList<CookieSameSiteSupplier>();
    private final DocumentRoot documentRoot = new DocumentRoot(this.logger);
    private final StaticResourceJars staticResourceJars = new StaticResourceJars();
    private final Set<String> webListenerClassNames = new HashSet<String>();

    public AbstractServletWebServerFactory() {
    }

    public AbstractServletWebServerFactory(int port) {
        super(port);
    }

    public AbstractServletWebServerFactory(String contextPath, int port) {
        super(port);
        this.checkContextPath(contextPath);
        this.contextPath = contextPath;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    @Override
    public void setContextPath(String contextPath) {
        this.checkContextPath(contextPath);
        this.contextPath = contextPath;
    }

    private void checkContextPath(String contextPath) {
        Assert.notNull((Object)contextPath, (String)"ContextPath must not be null");
        if (!contextPath.isEmpty()) {
            if ("/".equals(contextPath)) {
                throw new IllegalArgumentException("Root ContextPath must be specified using an empty string");
            }
            if (!contextPath.startsWith("/") || contextPath.endsWith("/")) {
                throw new IllegalArgumentException("ContextPath must start with '/' and not end with '/'");
            }
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isRegisterDefaultServlet() {
        return this.registerDefaultServlet;
    }

    @Override
    public void setRegisterDefaultServlet(boolean registerDefaultServlet) {
        this.registerDefaultServlet = registerDefaultServlet;
    }

    public MimeMappings getMimeMappings() {
        return this.mimeMappings;
    }

    @Override
    public void setMimeMappings(MimeMappings mimeMappings) {
        this.mimeMappings = new MimeMappings(mimeMappings);
    }

    public File getDocumentRoot() {
        return this.documentRoot.getDirectory();
    }

    @Override
    public void setDocumentRoot(File documentRoot) {
        this.documentRoot.setDirectory(documentRoot);
    }

    @Override
    public void setInitializers(List<? extends ServletContextInitializer> initializers) {
        Assert.notNull(initializers, (String)"Initializers must not be null");
        this.initializers = new ArrayList<ServletContextInitializer>(initializers);
    }

    @Override
    public void addInitializers(ServletContextInitializer ... initializers) {
        Assert.notNull((Object)initializers, (String)"Initializers must not be null");
        this.initializers.addAll(Arrays.asList(initializers));
    }

    public Jsp getJsp() {
        return this.jsp;
    }

    @Override
    public void setJsp(Jsp jsp) {
        this.jsp = jsp;
    }

    public Session getSession() {
        return this.session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public Map<Locale, Charset> getLocaleCharsetMappings() {
        return this.localeCharsetMappings;
    }

    @Override
    public void setLocaleCharsetMappings(Map<Locale, Charset> localeCharsetMappings) {
        Assert.notNull(localeCharsetMappings, (String)"localeCharsetMappings must not be null");
        this.localeCharsetMappings = localeCharsetMappings;
    }

    @Override
    public void setInitParameters(Map<String, String> initParameters) {
        this.initParameters = initParameters;
    }

    public Map<String, String> getInitParameters() {
        return this.initParameters;
    }

    @Override
    public void setCookieSameSiteSuppliers(List<? extends CookieSameSiteSupplier> cookieSameSiteSuppliers) {
        Assert.notNull(cookieSameSiteSuppliers, (String)"CookieSameSiteSuppliers must not be null");
        this.cookieSameSiteSuppliers = new ArrayList<CookieSameSiteSupplier>(cookieSameSiteSuppliers);
    }

    @Override
    public void addCookieSameSiteSuppliers(CookieSameSiteSupplier ... cookieSameSiteSuppliers) {
        Assert.notNull((Object)cookieSameSiteSuppliers, (String)"CookieSameSiteSuppliers must not be null");
        this.cookieSameSiteSuppliers.addAll(Arrays.asList(cookieSameSiteSuppliers));
    }

    public List<CookieSameSiteSupplier> getCookieSameSiteSuppliers() {
        return this.cookieSameSiteSuppliers;
    }

    protected final ServletContextInitializer[] mergeInitializers(ServletContextInitializer ... initializers) {
        ArrayList<ServletContextInitializer> mergedInitializers = new ArrayList<ServletContextInitializer>();
        mergedInitializers.add(servletContext -> this.initParameters.forEach((arg_0, arg_1) -> ((ServletContext)servletContext).setInitParameter(arg_0, arg_1)));
        mergedInitializers.add(new SessionConfiguringInitializer(this.session));
        mergedInitializers.addAll(Arrays.asList(initializers));
        mergedInitializers.addAll(this.initializers);
        return mergedInitializers.toArray(new ServletContextInitializer[0]);
    }

    protected boolean shouldRegisterJspServlet() {
        return this.jsp != null && this.jsp.getRegistered() && ClassUtils.isPresent((String)this.jsp.getClassName(), (ClassLoader)this.getClass().getClassLoader());
    }

    protected final File getValidDocumentRoot() {
        return this.documentRoot.getValidDirectory();
    }

    protected final List<URL> getUrlsOfJarsWithMetaInfResources() {
        return this.staticResourceJars.getUrls();
    }

    protected final File getValidSessionStoreDir() {
        return this.getValidSessionStoreDir(true);
    }

    protected final File getValidSessionStoreDir(boolean mkdirs) {
        return this.session.getSessionStoreDirectory().getValidDirectory(mkdirs);
    }

    @Override
    public void addWebListeners(String ... webListenerClassNames) {
        this.webListenerClassNames.addAll(Arrays.asList(webListenerClassNames));
    }

    protected final Set<String> getWebListenerClassNames() {
        return this.webListenerClassNames;
    }

    private static class SessionConfiguringInitializer
    implements ServletContextInitializer {
        private final Session session;

        SessionConfiguringInitializer(Session session) {
            this.session = session;
        }

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            if (this.session.getTrackingModes() != null) {
                servletContext.setSessionTrackingModes(this.unwrap(this.session.getTrackingModes()));
            }
            this.configureSessionCookie(servletContext.getSessionCookieConfig());
        }

        private void configureSessionCookie(SessionCookieConfig config) {
            Session.Cookie cookie = this.session.getCookie();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(cookie::getName).to(arg_0 -> ((SessionCookieConfig)config).setName(arg_0));
            map.from(cookie::getDomain).to(arg_0 -> ((SessionCookieConfig)config).setDomain(arg_0));
            map.from(cookie::getPath).to(arg_0 -> ((SessionCookieConfig)config).setPath(arg_0));
            map.from(cookie::getComment).to(arg_0 -> ((SessionCookieConfig)config).setComment(arg_0));
            map.from(cookie::getHttpOnly).to(arg_0 -> ((SessionCookieConfig)config).setHttpOnly(arg_0));
            map.from(cookie::getSecure).to(arg_0 -> ((SessionCookieConfig)config).setSecure(arg_0));
            map.from(cookie::getMaxAge).asInt(Duration::getSeconds).to(arg_0 -> ((SessionCookieConfig)config).setMaxAge(arg_0));
        }

        private Set<SessionTrackingMode> unwrap(Set<Session.SessionTrackingMode> modes) {
            if (modes == null) {
                return null;
            }
            LinkedHashSet<SessionTrackingMode> result = new LinkedHashSet<SessionTrackingMode>();
            for (Session.SessionTrackingMode mode : modes) {
                result.add(SessionTrackingMode.valueOf((String)mode.name()));
            }
            return result;
        }
    }
}

