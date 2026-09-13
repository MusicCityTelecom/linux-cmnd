/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.gateway.AnnotationGatewayProxyFactoryBean;
import org.springframework.integration.gateway.GatewayMethodMetadata;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.gateway.MethodArgsHolder;
import org.springframework.integration.gateway.MethodArgsMessageMapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;

public class GatewayProxySpec
implements ComponentsRegistration {
    protected static final SpelExpressionParser PARSER = new SpelExpressionParser();
    protected final MessageChannel gatewayRequestChannel = new DirectChannel();
    protected final GatewayProxyFactoryBean gatewayProxyFactoryBean;
    protected final GatewayMethodMetadata gatewayMethodMetadata = new GatewayMethodMetadata();
    protected final Map<String, Expression> headerExpressions = new HashMap<String, Expression>();
    private boolean populateGatewayMethodMetadata;

    protected GatewayProxySpec(Class<?> serviceInterface) {
        this.gatewayProxyFactoryBean = new AnnotationGatewayProxyFactoryBean(serviceInterface);
        this.gatewayProxyFactoryBean.setDefaultRequestChannel(this.gatewayRequestChannel);
    }

    public GatewayProxySpec beanName(@Nullable String beanName) {
        if (beanName != null) {
            this.gatewayProxyFactoryBean.setBeanName(beanName);
        }
        return this;
    }

    public GatewayProxySpec replyChannel(String channelName) {
        this.gatewayProxyFactoryBean.setDefaultReplyChannelName(channelName);
        return this;
    }

    public GatewayProxySpec replyChannel(MessageChannel replyChannel) {
        this.gatewayProxyFactoryBean.setDefaultReplyChannel(replyChannel);
        return this;
    }

    public GatewayProxySpec errorChannel(String errorChannelName) {
        this.gatewayProxyFactoryBean.setErrorChannelName(errorChannelName);
        return this;
    }

    public GatewayProxySpec errorChannel(MessageChannel errorChannel) {
        this.gatewayProxyFactoryBean.setErrorChannel(errorChannel);
        return this;
    }

    public GatewayProxySpec requestTimeout(long requestTimeout) {
        this.gatewayProxyFactoryBean.setDefaultRequestTimeout(requestTimeout);
        return this;
    }

    public GatewayProxySpec replyTimeout(long replyTimeout) {
        this.gatewayProxyFactoryBean.setDefaultReplyTimeout(replyTimeout);
        return this;
    }

    public GatewayProxySpec asyncExecutor(@Nullable Executor executor) {
        this.gatewayProxyFactoryBean.setAsyncExecutor(executor);
        return this;
    }

    public GatewayProxySpec payloadExpression(String expression) {
        return this.payloadExpression(PARSER.parseExpression(expression));
    }

    public GatewayProxySpec payloadFunction(Function<MethodArgsHolder, ?> defaultPayloadFunction) {
        return this.payloadExpression(new FunctionExpression<MethodArgsHolder>(defaultPayloadFunction));
    }

    public GatewayProxySpec payloadExpression(Expression expression) {
        this.gatewayMethodMetadata.setPayloadExpression(expression);
        this.populateGatewayMethodMetadata = true;
        return this;
    }

    public GatewayProxySpec header(String headerName, Object value) {
        return this.header(headerName, new ValueExpression<Object>(value));
    }

    public GatewayProxySpec header(String headerName, Function<MethodArgsHolder, ?> valueFunction) {
        return this.header(headerName, new FunctionExpression<MethodArgsHolder>(valueFunction));
    }

    public GatewayProxySpec header(String headerName, Expression valueExpression) {
        this.headerExpressions.put(headerName, valueExpression);
        this.populateGatewayMethodMetadata = true;
        return this;
    }

    public GatewayProxySpec mapper(MethodArgsMessageMapper mapper) {
        this.gatewayProxyFactoryBean.setMapper(mapper);
        return this;
    }

    public GatewayProxySpec proxyDefaultMethods(boolean proxyDefaultMethods) {
        this.gatewayProxyFactoryBean.setProxyDefaultMethods(proxyDefaultMethods);
        return this;
    }

    MessageChannel getGatewayRequestChannel() {
        return this.gatewayRequestChannel;
    }

    GatewayProxyFactoryBean getGatewayProxyFactoryBean() {
        if (this.populateGatewayMethodMetadata) {
            this.gatewayMethodMetadata.setHeaderExpressions(this.headerExpressions);
            this.gatewayProxyFactoryBean.setGlobalMethodMetadata(this.gatewayMethodMetadata);
        }
        return this.gatewayProxyFactoryBean;
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        return Collections.singletonMap(this.gatewayProxyFactoryBean, this.gatewayProxyFactoryBean.getBeanName() + ".gateway");
    }
}

