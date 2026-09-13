/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelOption
 *  org.springframework.boot.cloud.CloudPlatform
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
 *  org.springframework.boot.web.embedded.netty.NettyServerCustomizer
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 *  reactor.netty.http.server.HttpRequestDecoderSpec
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.autoconfigure.web.embedded;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import reactor.netty.http.server.HttpRequestDecoderSpec;
import reactor.netty.http.server.HttpServer;

public class NettyWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory>,
Ordered {
    private final Environment environment;
    private final ServerProperties serverProperties;

    public NettyWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
        this.environment = environment;
        this.serverProperties = serverProperties;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(NettyReactiveWebServerFactory factory) {
        factory.setUseForwardHeaders(this.getOrDeduceUseForwardHeaders());
        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        ServerProperties.Netty nettyProperties = this.serverProperties.getNetty();
        propertyMapper.from(nettyProperties::getConnectionTimeout).whenNonNull().to(connectionTimeout -> this.customizeConnectionTimeout(factory, (Duration)connectionTimeout));
        propertyMapper.from(nettyProperties::getIdleTimeout).whenNonNull().to(idleTimeout -> this.customizeIdleTimeout(factory, (Duration)idleTimeout));
        propertyMapper.from(nettyProperties::getMaxKeepAliveRequests).to(maxKeepAliveRequests -> this.customizeMaxKeepAliveRequests(factory, (int)maxKeepAliveRequests));
        this.customizeRequestDecoder(factory, propertyMapper);
    }

    private boolean getOrDeduceUseForwardHeaders() {
        if (this.serverProperties.getForwardHeadersStrategy() == null) {
            CloudPlatform platform = CloudPlatform.getActive((Environment)this.environment);
            return platform != null && platform.isUsingForwardHeaders();
        }
        return this.serverProperties.getForwardHeadersStrategy().equals((Object)ServerProperties.ForwardHeadersStrategy.NATIVE);
    }

    private void customizeConnectionTimeout(NettyReactiveWebServerFactory factory, Duration connectionTimeout) {
        factory.addServerCustomizers(new NettyServerCustomizer[]{httpServer -> (HttpServer)httpServer.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (Object)((int)connectionTimeout.toMillis()))});
    }

    private void customizeRequestDecoder(NettyReactiveWebServerFactory factory, PropertyMapper propertyMapper) {
        factory.addServerCustomizers(new NettyServerCustomizer[]{httpServer -> httpServer.httpRequestDecoder(httpRequestDecoderSpec -> {
            propertyMapper.from((Object)this.serverProperties.getMaxHttpHeaderSize()).whenNonNull().to(maxHttpRequestHeader -> {
                HttpRequestDecoderSpec cfr_ignored_0 = (HttpRequestDecoderSpec)httpRequestDecoderSpec.maxHeaderSize((int)maxHttpRequestHeader.toBytes());
            });
            ServerProperties.Netty nettyProperties = this.serverProperties.getNetty();
            propertyMapper.from((Object)nettyProperties.getMaxChunkSize()).whenNonNull().to(maxChunkSize -> {
                HttpRequestDecoderSpec cfr_ignored_0 = (HttpRequestDecoderSpec)httpRequestDecoderSpec.maxChunkSize((int)maxChunkSize.toBytes());
            });
            propertyMapper.from((Object)nettyProperties.getMaxInitialLineLength()).whenNonNull().to(maxInitialLineLength -> {
                HttpRequestDecoderSpec cfr_ignored_0 = (HttpRequestDecoderSpec)httpRequestDecoderSpec.maxInitialLineLength((int)maxInitialLineLength.toBytes());
            });
            propertyMapper.from((Object)nettyProperties.getH2cMaxContentLength()).whenNonNull().to(h2cMaxContentLength -> {
                HttpRequestDecoderSpec cfr_ignored_0 = (HttpRequestDecoderSpec)httpRequestDecoderSpec.h2cMaxContentLength((int)h2cMaxContentLength.toBytes());
            });
            propertyMapper.from((Object)nettyProperties.getInitialBufferSize()).whenNonNull().to(initialBufferSize -> {
                HttpRequestDecoderSpec cfr_ignored_0 = (HttpRequestDecoderSpec)httpRequestDecoderSpec.initialBufferSize((int)initialBufferSize.toBytes());
            });
            propertyMapper.from((Object)nettyProperties.isValidateHeaders()).whenNonNull().to(arg_0 -> ((HttpRequestDecoderSpec)httpRequestDecoderSpec).validateHeaders(arg_0));
            return httpRequestDecoderSpec;
        })});
    }

    private void customizeIdleTimeout(NettyReactiveWebServerFactory factory, Duration idleTimeout) {
        factory.addServerCustomizers(new NettyServerCustomizer[]{httpServer -> httpServer.idleTimeout(idleTimeout)});
    }

    private void customizeMaxKeepAliveRequests(NettyReactiveWebServerFactory factory, int maxKeepAliveRequests) {
        factory.addServerCustomizers(new NettyServerCustomizer[]{httpServer -> httpServer.maxKeepAliveRequests(maxKeepAliveRequests)});
    }
}

