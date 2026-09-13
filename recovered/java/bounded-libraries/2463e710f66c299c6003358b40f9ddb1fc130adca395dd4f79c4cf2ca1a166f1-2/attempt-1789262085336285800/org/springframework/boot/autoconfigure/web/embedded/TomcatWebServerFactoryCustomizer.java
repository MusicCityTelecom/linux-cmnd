/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.Valve
 *  org.apache.catalina.valves.AccessLogValve
 *  org.apache.catalina.valves.ErrorReportValve
 *  org.apache.catalina.valves.RemoteIpValve
 *  org.apache.coyote.AbstractProtocol
 *  org.apache.coyote.ProtocolHandler
 *  org.apache.coyote.UpgradeProtocol
 *  org.apache.coyote.http11.AbstractHttp11Protocol
 *  org.apache.coyote.http2.Http2Protocol
 *  org.springframework.boot.cloud.CloudPlatform
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory
 *  org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 *  org.springframework.util.StringUtils
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.autoconfigure.web.embedded;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.catalina.Valve;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.catalina.valves.RemoteIpValve;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.UpgradeProtocol;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;

public class TomcatWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory>,
Ordered {
    private final Environment environment;
    private final ServerProperties serverProperties;

    public TomcatWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
        this.environment = environment;
        this.serverProperties = serverProperties;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(ConfigurableTomcatWebServerFactory factory) {
        ServerProperties properties = this.serverProperties;
        ServerProperties.Tomcat tomcatProperties = properties.getTomcat();
        PropertyMapper propertyMapper = PropertyMapper.get();
        propertyMapper.from(tomcatProperties::getBasedir).whenNonNull().to(arg_0 -> ((ConfigurableTomcatWebServerFactory)factory).setBaseDirectory(arg_0));
        propertyMapper.from(tomcatProperties::getBackgroundProcessorDelay).whenNonNull().as(Duration::getSeconds).as(Long::intValue).to(arg_0 -> ((ConfigurableTomcatWebServerFactory)factory).setBackgroundProcessorDelay(arg_0));
        this.customizeRemoteIpValve(factory);
        ServerProperties.Tomcat.Threads threadProperties = tomcatProperties.getThreads();
        propertyMapper.from(threadProperties::getMax).when(this::isPositive).to(maxThreads -> this.customizeMaxThreads(factory, threadProperties.getMax()));
        propertyMapper.from(threadProperties::getMinSpare).when(this::isPositive).to(minSpareThreads -> this.customizeMinThreads(factory, (int)minSpareThreads));
        propertyMapper.from((Object)this.serverProperties.getMaxHttpHeaderSize()).whenNonNull().asInt(DataSize::toBytes).when(this::isPositive).to(maxHttpHeaderSize -> this.customizeMaxHttpHeaderSize(factory, (int)maxHttpHeaderSize));
        propertyMapper.from(tomcatProperties::getMaxSwallowSize).whenNonNull().asInt(DataSize::toBytes).to(maxSwallowSize -> this.customizeMaxSwallowSize(factory, (int)maxSwallowSize));
        propertyMapper.from(tomcatProperties::getMaxHttpFormPostSize).asInt(DataSize::toBytes).when(maxHttpFormPostSize -> maxHttpFormPostSize != 0).to(maxHttpFormPostSize -> this.customizeMaxHttpFormPostSize(factory, (int)maxHttpFormPostSize));
        propertyMapper.from(tomcatProperties::getAccesslog).when(ServerProperties.Tomcat.Accesslog::isEnabled).to(enabled -> this.customizeAccessLog(factory));
        propertyMapper.from(tomcatProperties::getUriEncoding).whenNonNull().to(arg_0 -> ((ConfigurableTomcatWebServerFactory)factory).setUriEncoding(arg_0));
        propertyMapper.from(tomcatProperties::getConnectionTimeout).whenNonNull().to(connectionTimeout -> this.customizeConnectionTimeout(factory, (Duration)connectionTimeout));
        propertyMapper.from(tomcatProperties::getMaxConnections).when(this::isPositive).to(maxConnections -> this.customizeMaxConnections(factory, (int)maxConnections));
        propertyMapper.from(tomcatProperties::getAcceptCount).when(this::isPositive).to(acceptCount -> this.customizeAcceptCount(factory, (int)acceptCount));
        propertyMapper.from(tomcatProperties::getProcessorCache).to(processorCache -> this.customizeProcessorCache(factory, (int)processorCache));
        propertyMapper.from(tomcatProperties::getKeepAliveTimeout).whenNonNull().to(keepAliveTimeout -> this.customizeKeepAliveTimeout(factory, (Duration)keepAliveTimeout));
        propertyMapper.from(tomcatProperties::getMaxKeepAliveRequests).to(maxKeepAliveRequests -> this.customizeMaxKeepAliveRequests(factory, (int)maxKeepAliveRequests));
        propertyMapper.from(tomcatProperties::getRelaxedPathChars).as(this::joinCharacters).whenHasText().to(relaxedChars -> this.customizeRelaxedPathChars(factory, (String)relaxedChars));
        propertyMapper.from(tomcatProperties::getRelaxedQueryChars).as(this::joinCharacters).whenHasText().to(relaxedChars -> this.customizeRelaxedQueryChars(factory, (String)relaxedChars));
        propertyMapper.from(tomcatProperties::isRejectIllegalHeader).to(rejectIllegalHeader -> this.customizeRejectIllegalHeader(factory, (boolean)rejectIllegalHeader));
        this.customizeStaticResources(factory);
        this.customizeErrorReportValve(properties.getError(), factory);
    }

    private boolean isPositive(int value) {
        return value > 0;
    }

    private void customizeAcceptCount(ConfigurableTomcatWebServerFactory factory, int acceptCount) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractProtocol) {
                AbstractProtocol protocol = (AbstractProtocol)handler;
                protocol.setAcceptCount(acceptCount);
            }
        }});
    }

    private void customizeProcessorCache(ConfigurableTomcatWebServerFactory factory, int processorCache) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractProtocol) {
                ((AbstractProtocol)handler).setProcessorCache(processorCache);
            }
        }});
    }

    private void customizeKeepAliveTimeout(ConfigurableTomcatWebServerFactory factory, Duration keepAliveTimeout) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            for (UpgradeProtocol upgradeProtocol : handler.findUpgradeProtocols()) {
                if (!(upgradeProtocol instanceof Http2Protocol)) continue;
                ((Http2Protocol)upgradeProtocol).setKeepAliveTimeout(keepAliveTimeout.toMillis());
            }
            if (handler instanceof AbstractProtocol) {
                AbstractProtocol protocol = (AbstractProtocol)handler;
                protocol.setKeepAliveTimeout((int)keepAliveTimeout.toMillis());
            }
        }});
    }

    private void customizeMaxKeepAliveRequests(ConfigurableTomcatWebServerFactory factory, int maxKeepAliveRequests) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractHttp11Protocol) {
                AbstractHttp11Protocol protocol = (AbstractHttp11Protocol)handler;
                protocol.setMaxKeepAliveRequests(maxKeepAliveRequests);
            }
        }});
    }

    private void customizeMaxConnections(ConfigurableTomcatWebServerFactory factory, int maxConnections) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractProtocol) {
                AbstractProtocol protocol = (AbstractProtocol)handler;
                protocol.setMaxConnections(maxConnections);
            }
        }});
    }

    private void customizeConnectionTimeout(ConfigurableTomcatWebServerFactory factory, Duration connectionTimeout) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractProtocol) {
                AbstractProtocol protocol = (AbstractProtocol)handler;
                protocol.setConnectionTimeout((int)connectionTimeout.toMillis());
            }
        }});
    }

    private void customizeRelaxedPathChars(ConfigurableTomcatWebServerFactory factory, String relaxedChars) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> connector.setProperty("relaxedPathChars", relaxedChars)});
    }

    private void customizeRelaxedQueryChars(ConfigurableTomcatWebServerFactory factory, String relaxedChars) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> connector.setProperty("relaxedQueryChars", relaxedChars)});
    }

    private void customizeRejectIllegalHeader(ConfigurableTomcatWebServerFactory factory, boolean rejectIllegalHeader) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractHttp11Protocol) {
                AbstractHttp11Protocol protocol = (AbstractHttp11Protocol)handler;
                protocol.setRejectIllegalHeader(rejectIllegalHeader);
            }
        }});
    }

    private String joinCharacters(List<Character> content) {
        return content.stream().map(String::valueOf).collect(Collectors.joining());
    }

    private void customizeRemoteIpValve(ConfigurableTomcatWebServerFactory factory) {
        ServerProperties.Tomcat.Remoteip remoteIpProperties = this.serverProperties.getTomcat().getRemoteip();
        String protocolHeader = remoteIpProperties.getProtocolHeader();
        String remoteIpHeader = remoteIpProperties.getRemoteIpHeader();
        if (StringUtils.hasText((String)protocolHeader) || StringUtils.hasText((String)remoteIpHeader) || this.getOrDeduceUseForwardHeaders()) {
            RemoteIpValve valve = new RemoteIpValve();
            valve.setProtocolHeader(StringUtils.hasLength((String)protocolHeader) ? protocolHeader : "X-Forwarded-Proto");
            if (StringUtils.hasLength((String)remoteIpHeader)) {
                valve.setRemoteIpHeader(remoteIpHeader);
            }
            valve.setInternalProxies(remoteIpProperties.getInternalProxies());
            try {
                valve.setHostHeader(remoteIpProperties.getHostHeader());
            }
            catch (NoSuchMethodError noSuchMethodError) {
                // empty catch block
            }
            valve.setPortHeader(remoteIpProperties.getPortHeader());
            valve.setProtocolHeaderHttpsValue(remoteIpProperties.getProtocolHeaderHttpsValue());
            factory.addEngineValves(new Valve[]{valve});
        }
    }

    private boolean getOrDeduceUseForwardHeaders() {
        if (this.serverProperties.getForwardHeadersStrategy() == null) {
            CloudPlatform platform = CloudPlatform.getActive((Environment)this.environment);
            return platform != null && platform.isUsingForwardHeaders();
        }
        return this.serverProperties.getForwardHeadersStrategy().equals((Object)ServerProperties.ForwardHeadersStrategy.NATIVE);
    }

    private void customizeMaxThreads(ConfigurableTomcatWebServerFactory factory, int maxThreads) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractProtocol) {
                AbstractProtocol protocol = (AbstractProtocol)handler;
                protocol.setMaxThreads(maxThreads);
            }
        }});
    }

    private void customizeMinThreads(ConfigurableTomcatWebServerFactory factory, int minSpareThreads) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractProtocol) {
                AbstractProtocol protocol = (AbstractProtocol)handler;
                protocol.setMinSpareThreads(minSpareThreads);
            }
        }});
    }

    private void customizeMaxHttpHeaderSize(ConfigurableTomcatWebServerFactory factory, int maxHttpHeaderSize) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractHttp11Protocol) {
                AbstractHttp11Protocol protocol = (AbstractHttp11Protocol)handler;
                protocol.setMaxHttpHeaderSize(maxHttpHeaderSize);
                for (UpgradeProtocol upgradeProtocol : protocol.findUpgradeProtocols()) {
                    if (!(upgradeProtocol instanceof Http2Protocol)) continue;
                    ((Http2Protocol)upgradeProtocol).setMaxHeaderSize(maxHttpHeaderSize);
                }
            }
        }});
    }

    private void customizeMaxSwallowSize(ConfigurableTomcatWebServerFactory factory, int maxSwallowSize) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof AbstractHttp11Protocol) {
                AbstractHttp11Protocol protocol = (AbstractHttp11Protocol)handler;
                protocol.setMaxSwallowSize(maxSwallowSize);
            }
        }});
    }

    private void customizeMaxHttpFormPostSize(ConfigurableTomcatWebServerFactory factory, int maxHttpFormPostSize) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer[]{connector -> connector.setMaxPostSize(maxHttpFormPostSize)});
    }

    private void customizeAccessLog(ConfigurableTomcatWebServerFactory factory) {
        ServerProperties.Tomcat tomcatProperties = this.serverProperties.getTomcat();
        AccessLogValve valve = new AccessLogValve();
        PropertyMapper map = PropertyMapper.get();
        ServerProperties.Tomcat.Accesslog accessLogConfig = tomcatProperties.getAccesslog();
        map.from((Object)accessLogConfig.getConditionIf()).to(arg_0 -> ((AccessLogValve)valve).setConditionIf(arg_0));
        map.from((Object)accessLogConfig.getConditionUnless()).to(arg_0 -> ((AccessLogValve)valve).setConditionUnless(arg_0));
        map.from((Object)accessLogConfig.getPattern()).to(arg_0 -> ((AccessLogValve)valve).setPattern(arg_0));
        map.from((Object)accessLogConfig.getDirectory()).to(arg_0 -> ((AccessLogValve)valve).setDirectory(arg_0));
        map.from((Object)accessLogConfig.getPrefix()).to(arg_0 -> ((AccessLogValve)valve).setPrefix(arg_0));
        map.from((Object)accessLogConfig.getSuffix()).to(arg_0 -> ((AccessLogValve)valve).setSuffix(arg_0));
        map.from((Object)accessLogConfig.getEncoding()).whenHasText().to(arg_0 -> ((AccessLogValve)valve).setEncoding(arg_0));
        map.from((Object)accessLogConfig.getLocale()).whenHasText().to(arg_0 -> ((AccessLogValve)valve).setLocale(arg_0));
        map.from((Object)accessLogConfig.isCheckExists()).to(arg_0 -> ((AccessLogValve)valve).setCheckExists(arg_0));
        map.from((Object)accessLogConfig.isRotate()).to(arg_0 -> ((AccessLogValve)valve).setRotatable(arg_0));
        map.from((Object)accessLogConfig.isRenameOnRotate()).to(arg_0 -> ((AccessLogValve)valve).setRenameOnRotate(arg_0));
        map.from((Object)accessLogConfig.getMaxDays()).to(arg_0 -> ((AccessLogValve)valve).setMaxDays(arg_0));
        map.from((Object)accessLogConfig.getFileDateFormat()).to(arg_0 -> ((AccessLogValve)valve).setFileDateFormat(arg_0));
        map.from((Object)accessLogConfig.isIpv6Canonical()).to(arg_0 -> ((AccessLogValve)valve).setIpv6Canonical(arg_0));
        map.from((Object)accessLogConfig.isRequestAttributesEnabled()).to(arg_0 -> ((AccessLogValve)valve).setRequestAttributesEnabled(arg_0));
        map.from((Object)accessLogConfig.isBuffered()).to(arg_0 -> ((AccessLogValve)valve).setBuffered(arg_0));
        factory.addEngineValves(new Valve[]{valve});
    }

    private void customizeStaticResources(ConfigurableTomcatWebServerFactory factory) {
        ServerProperties.Tomcat.Resource resource = this.serverProperties.getTomcat().getResource();
        factory.addContextCustomizers(new TomcatContextCustomizer[]{context -> context.addLifecycleListener(event -> {
            if (event.getType().equals("configure_start")) {
                context.getResources().setCachingAllowed(resource.isAllowCaching());
                if (resource.getCacheTtl() != null) {
                    long ttl = resource.getCacheTtl().toMillis();
                    context.getResources().setCacheTtl(ttl);
                }
            }
        })});
    }

    private void customizeErrorReportValve(ErrorProperties error, ConfigurableTomcatWebServerFactory factory) {
        if (error.getIncludeStacktrace() == ErrorProperties.IncludeAttribute.NEVER) {
            factory.addContextCustomizers(new TomcatContextCustomizer[]{context -> {
                ErrorReportValve valve = new ErrorReportValve();
                valve.setShowServerInfo(false);
                valve.setShowReport(false);
                context.getParent().getPipeline().addValve((Valve)valve);
            }});
        }
    }
}

