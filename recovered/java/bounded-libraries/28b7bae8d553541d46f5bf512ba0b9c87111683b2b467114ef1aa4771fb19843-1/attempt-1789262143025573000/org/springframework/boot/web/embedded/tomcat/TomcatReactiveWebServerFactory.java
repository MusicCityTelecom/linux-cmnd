/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.apache.catalina.Container
 *  org.apache.catalina.Context
 *  org.apache.catalina.Engine
 *  org.apache.catalina.Host
 *  org.apache.catalina.LifecycleListener
 *  org.apache.catalina.Loader
 *  org.apache.catalina.Valve
 *  org.apache.catalina.connector.Connector
 *  org.apache.catalina.core.AprLifecycleListener
 *  org.apache.catalina.loader.WebappLoader
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.catalina.startup.Tomcat$FixContextListener
 *  org.apache.coyote.AbstractProtocol
 *  org.apache.coyote.ProtocolHandler
 *  org.apache.coyote.UpgradeProtocol
 *  org.apache.coyote.http2.Http2Protocol
 *  org.apache.tomcat.JarScanFilter
 *  org.apache.tomcat.util.modeler.Registry
 *  org.apache.tomcat.util.scan.StandardJarScanFilter
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.http.server.reactive.TomcatHttpHandlerAdapter
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.tomcat;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.Servlet;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.JarScanFilter;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.boot.web.embedded.tomcat.CompressionConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.DisableReferenceClearingContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.SslConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedContext;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.reactive.server.AbstractReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class TomcatReactiveWebServerFactory
extends AbstractReactiveWebServerFactory
implements ConfigurableTomcatWebServerFactory {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
    private File baseDirectory;
    private final List<Valve> engineValves = new ArrayList<Valve>();
    private List<LifecycleListener> contextLifecycleListeners = new ArrayList<LifecycleListener>();
    private List<LifecycleListener> serverLifecycleListeners = TomcatReactiveWebServerFactory.getDefaultServerLifecycleListeners();
    private Set<TomcatContextCustomizer> tomcatContextCustomizers = new LinkedHashSet<TomcatContextCustomizer>();
    private Set<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new LinkedHashSet<TomcatConnectorCustomizer>();
    private Set<TomcatProtocolHandlerCustomizer<?>> tomcatProtocolHandlerCustomizers = new LinkedHashSet();
    private final List<Connector> additionalTomcatConnectors = new ArrayList<Connector>();
    private String protocol = "org.apache.coyote.http11.Http11NioProtocol";
    private Charset uriEncoding = DEFAULT_CHARSET;
    private int backgroundProcessorDelay;
    private boolean disableMBeanRegistry = true;

    public TomcatReactiveWebServerFactory() {
    }

    public TomcatReactiveWebServerFactory(int port) {
        super(port);
    }

    private static List<LifecycleListener> getDefaultServerLifecycleListeners() {
        AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
        return AprLifecycleListener.isAprAvailable() ? new ArrayList<AprLifecycleListener>(Arrays.asList(aprLifecycleListener)) : new ArrayList();
    }

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        if (this.disableMBeanRegistry) {
            Registry.disableRegistry();
        }
        Tomcat tomcat = new Tomcat();
        File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        for (LifecycleListener lifecycleListener : this.serverLifecycleListeners) {
            tomcat.getServer().addLifecycleListener(lifecycleListener);
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
        TomcatHttpHandlerAdapter tomcatHttpHandlerAdapter = new TomcatHttpHandlerAdapter(httpHandler);
        this.prepareContext(tomcat.getHost(), tomcatHttpHandlerAdapter);
        return this.getTomcatWebServer(tomcat);
    }

    private void configureEngine(Engine engine) {
        engine.setBackgroundProcessorDelay(this.backgroundProcessorDelay);
        for (Valve valve : this.engineValves) {
            engine.getPipeline().addValve(valve);
        }
    }

    protected void prepareContext(Host host, TomcatHttpHandlerAdapter servlet) {
        File docBase = this.createTempDir("tomcat-docbase");
        TomcatEmbeddedContext context = new TomcatEmbeddedContext();
        context.setPath("");
        context.setDocBase(docBase.getAbsolutePath());
        context.addLifecycleListener((LifecycleListener)new Tomcat.FixContextListener());
        context.setParentClassLoader(ClassUtils.getDefaultClassLoader());
        this.skipAllTldScanning(context);
        WebappLoader loader = new WebappLoader();
        loader.setLoaderClass(TomcatEmbeddedWebappClassLoader.class.getName());
        loader.setDelegate(true);
        context.setLoader((Loader)loader);
        Tomcat.addServlet((Context)context, (String)"httpHandlerServlet", (Servlet)servlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/", "httpHandlerServlet");
        host.addChild((Container)context);
        this.configureContext((Context)context);
    }

    private void skipAllTldScanning(TomcatEmbeddedContext context) {
        StandardJarScanFilter filter = new StandardJarScanFilter();
        filter.setTldSkip("*.jar");
        context.getJarScanner().setJarScanFilter((JarScanFilter)filter);
    }

    protected void configureContext(Context context) {
        this.contextLifecycleListeners.forEach(arg_0 -> ((Context)context).addLifecycleListener(arg_0));
        new DisableReferenceClearingContextCustomizer().customize(context);
        this.tomcatContextCustomizers.forEach(customizer -> customizer.customize(context));
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

    private void invokeProtocolHandlerCustomizers(ProtocolHandler protocolHandler) {
        LambdaSafe.callbacks(TomcatProtocolHandlerCustomizer.class, this.tomcatProtocolHandlerCustomizers, protocolHandler, new Object[0]).invoke(customizer -> customizer.customize(protocolHandler));
    }

    private void customizeProtocol(AbstractProtocol<?> protocol) {
        if (this.getAddress() != null) {
            protocol.setAddress(this.getAddress());
        }
    }

    private void customizeSsl(Connector connector) {
        new SslConnectorCustomizer(this.getSsl(), this.getOrCreateSslStoreProvider()).customize(connector);
    }

    @Override
    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void setBackgroundProcessorDelay(int delay) {
        this.backgroundProcessorDelay = delay;
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

    public void setTomcatProtocolHandlerCustomizers(Collection<? extends TomcatProtocolHandlerCustomizer<?>> tomcatProtocolHandlerCustomizers) {
        Assert.notNull(tomcatProtocolHandlerCustomizers, (String)"TomcatProtocolHandlerCustomizers must not be null");
        this.tomcatProtocolHandlerCustomizers = new LinkedHashSet(tomcatProtocolHandlerCustomizers);
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
    public void addEngineValves(Valve ... engineValves) {
        Assert.notNull((Object)engineValves, (String)"Valves must not be null");
        this.engineValves.addAll(Arrays.asList(engineValves));
    }

    public List<Valve> getEngineValves() {
        return this.engineValves;
    }

    @Override
    public void setUriEncoding(Charset uriEncoding) {
        this.uriEncoding = uriEncoding;
    }

    public Charset getUriEncoding() {
        return this.uriEncoding;
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

    protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
        return new TomcatWebServer(tomcat, this.getPort() >= 0, this.getShutdown());
    }

    public void setProtocol(String protocol) {
        Assert.hasLength((String)protocol, (String)"Protocol must not be empty");
        this.protocol = protocol;
    }

    public void setDisableMBeanRegistry(boolean disableMBeanRegistry) {
        this.disableMBeanRegistry = disableMBeanRegistry;
    }
}

