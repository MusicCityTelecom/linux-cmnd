/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.server.AbstractConnector
 *  org.eclipse.jetty.server.ConnectionFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.CustomRequestLog
 *  org.eclipse.jetty.server.Handler
 *  org.eclipse.jetty.server.HttpConfiguration$ConnectionFactory
 *  org.eclipse.jetty.server.RequestLog
 *  org.eclipse.jetty.server.RequestLog$Writer
 *  org.eclipse.jetty.server.RequestLogWriter
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.handler.ContextHandler
 *  org.eclipse.jetty.server.handler.HandlerCollection
 *  org.eclipse.jetty.server.handler.HandlerWrapper
 *  org.eclipse.jetty.util.BlockingArrayQueue
 *  org.eclipse.jetty.util.thread.QueuedThreadPool
 *  org.eclipse.jetty.util.thread.ThreadPool
 *  org.springframework.boot.cloud.CloudPlatform
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory
 *  org.springframework.boot.web.embedded.jetty.JettyServerCustomizer
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.autoconfigure.web.embedded;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.RequestLogWriter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.unit.DataSize;

public class JettyWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory>,
Ordered {
    private final Environment environment;
    private final ServerProperties serverProperties;

    public JettyWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
        this.environment = environment;
        this.serverProperties = serverProperties;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(ConfigurableJettyWebServerFactory factory) {
        ServerProperties properties = this.serverProperties;
        ServerProperties.Jetty jettyProperties = properties.getJetty();
        factory.setUseForwardHeaders(this.getOrDeduceUseForwardHeaders());
        ServerProperties.Jetty.Threads threadProperties = jettyProperties.getThreads();
        factory.setThreadPool(this.determineThreadPool(jettyProperties.getThreads()));
        PropertyMapper propertyMapper = PropertyMapper.get();
        propertyMapper.from(threadProperties::getAcceptors).whenNonNull().to(arg_0 -> ((ConfigurableJettyWebServerFactory)factory).setAcceptors(arg_0));
        propertyMapper.from(threadProperties::getSelectors).whenNonNull().to(arg_0 -> ((ConfigurableJettyWebServerFactory)factory).setSelectors(arg_0));
        propertyMapper.from(properties::getMaxHttpHeaderSize).whenNonNull().asInt(DataSize::toBytes).when(this::isPositive).to(maxHttpHeaderSize -> factory.addServerCustomizers(new JettyServerCustomizer[]{new MaxHttpHeaderSizeCustomizer((int)maxHttpHeaderSize)}));
        propertyMapper.from(jettyProperties::getMaxHttpFormPostSize).asInt(DataSize::toBytes).when(this::isPositive).to(maxHttpFormPostSize -> this.customizeMaxHttpFormPostSize(factory, (int)maxHttpFormPostSize));
        propertyMapper.from(jettyProperties::getConnectionIdleTimeout).whenNonNull().to(idleTimeout -> this.customizeIdleTimeout(factory, (Duration)idleTimeout));
        propertyMapper.from(jettyProperties::getAccesslog).when(ServerProperties.Jetty.Accesslog::isEnabled).to(accesslog -> this.customizeAccessLog(factory, (ServerProperties.Jetty.Accesslog)accesslog));
    }

    private boolean isPositive(Integer value) {
        return value > 0;
    }

    private boolean getOrDeduceUseForwardHeaders() {
        if (this.serverProperties.getForwardHeadersStrategy() == null) {
            CloudPlatform platform = CloudPlatform.getActive((Environment)this.environment);
            return platform != null && platform.isUsingForwardHeaders();
        }
        return this.serverProperties.getForwardHeadersStrategy().equals((Object)ServerProperties.ForwardHeadersStrategy.NATIVE);
    }

    private void customizeIdleTimeout(ConfigurableJettyWebServerFactory factory, Duration connectionTimeout) {
        factory.addServerCustomizers(new JettyServerCustomizer[]{server -> {
            for (Connector connector : server.getConnectors()) {
                if (!(connector instanceof AbstractConnector)) continue;
                ((AbstractConnector)connector).setIdleTimeout(connectionTimeout.toMillis());
            }
        }});
    }

    private void customizeMaxHttpFormPostSize(ConfigurableJettyWebServerFactory factory, final int maxHttpFormPostSize) {
        factory.addServerCustomizers(new JettyServerCustomizer[]{new JettyServerCustomizer(){

            public void customize(Server server) {
                this.setHandlerMaxHttpFormPostSize(server.getHandlers());
            }

            private void setHandlerMaxHttpFormPostSize(Handler ... handlers) {
                for (Handler handler : handlers) {
                    if (handler instanceof ContextHandler) {
                        ((ContextHandler)handler).setMaxFormContentSize(maxHttpFormPostSize);
                        continue;
                    }
                    if (handler instanceof HandlerWrapper) {
                        this.setHandlerMaxHttpFormPostSize(((HandlerWrapper)handler).getHandler());
                        continue;
                    }
                    if (!(handler instanceof HandlerCollection)) continue;
                    this.setHandlerMaxHttpFormPostSize(((HandlerCollection)handler).getHandlers());
                }
            }
        }});
    }

    private ThreadPool determineThreadPool(ServerProperties.Jetty.Threads properties) {
        BlockingQueue<Runnable> queue = this.determineBlockingQueue(properties.getMaxQueueCapacity());
        int maxThreadCount = properties.getMax() > 0 ? properties.getMax() : 200;
        int minThreadCount = properties.getMin() > 0 ? properties.getMin() : 8;
        int threadIdleTimeout = properties.getIdleTimeout() != null ? (int)properties.getIdleTimeout().toMillis() : 60000;
        return new QueuedThreadPool(maxThreadCount, minThreadCount, threadIdleTimeout, queue);
    }

    private BlockingQueue<Runnable> determineBlockingQueue(Integer maxQueueCapacity) {
        if (maxQueueCapacity == null) {
            return null;
        }
        if (maxQueueCapacity == 0) {
            return new SynchronousQueue<Runnable>();
        }
        return new BlockingArrayQueue(maxQueueCapacity.intValue());
    }

    private void customizeAccessLog(ConfigurableJettyWebServerFactory factory, ServerProperties.Jetty.Accesslog properties) {
        factory.addServerCustomizers(new JettyServerCustomizer[]{server -> {
            RequestLogWriter logWriter = new RequestLogWriter();
            String format = this.getLogFormat(properties);
            CustomRequestLog log = new CustomRequestLog((RequestLog.Writer)logWriter, format);
            if (!CollectionUtils.isEmpty(properties.getIgnorePaths())) {
                log.setIgnorePaths(properties.getIgnorePaths().toArray(new String[0]));
            }
            if (properties.getFilename() != null) {
                logWriter.setFilename(properties.getFilename());
            }
            if (properties.getFileDateFormat() != null) {
                logWriter.setFilenameDateFormat(properties.getFileDateFormat());
            }
            logWriter.setRetainDays(properties.getRetentionPeriod());
            logWriter.setAppend(properties.isAppend());
            server.setRequestLog((RequestLog)log);
        }});
    }

    private String getLogFormat(ServerProperties.Jetty.Accesslog properties) {
        if (properties.getCustomFormat() != null) {
            return properties.getCustomFormat();
        }
        if (ServerProperties.Jetty.Accesslog.FORMAT.EXTENDED_NCSA.equals((Object)properties.getFormat())) {
            return "%{client}a - %u %t \"%r\" %s %O \"%{Referer}i\" \"%{User-Agent}i\"";
        }
        return "%{client}a - %u %t \"%r\" %s %O";
    }

    private static class MaxHttpHeaderSizeCustomizer
    implements JettyServerCustomizer {
        private final int maxHttpHeaderSize;

        MaxHttpHeaderSizeCustomizer(int maxHttpHeaderSize) {
            this.maxHttpHeaderSize = maxHttpHeaderSize;
        }

        public void customize(Server server) {
            Arrays.stream(server.getConnectors()).forEach(this::customize);
        }

        private void customize(Connector connector) {
            connector.getConnectionFactories().forEach(this::customize);
        }

        private void customize(ConnectionFactory factory) {
            if (factory instanceof HttpConfiguration.ConnectionFactory) {
                ((HttpConfiguration.ConnectionFactory)factory).getHttpConfiguration().setRequestHeaderSize(this.maxHttpHeaderSize);
            }
        }
    }
}

