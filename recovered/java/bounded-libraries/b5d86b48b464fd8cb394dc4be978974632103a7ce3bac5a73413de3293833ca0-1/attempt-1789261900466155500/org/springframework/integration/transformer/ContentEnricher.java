/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.SpelParserConfiguration
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.transformer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.JavaUtils;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class ContentEnricher
extends AbstractReplyProducingMessageHandler
implements ManageableLifecycle {
    private static final SpelExpressionParser SPEL_PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    private Map<Expression, Expression> nullResultPropertyExpressions = new HashMap<Expression, Expression>();
    private Map<String, HeaderValueMessageProcessor<?>> nullResultHeaderExpressions = new HashMap();
    private Map<Expression, Expression> propertyExpressions = new HashMap<Expression, Expression>();
    private Map<String, HeaderValueMessageProcessor<?>> headerExpressions = new HashMap();
    private EvaluationContext sourceEvaluationContext;
    private EvaluationContext targetEvaluationContext;
    private boolean shouldClonePayload = false;
    private Expression requestPayloadExpression;
    private MessageChannel requestChannel;
    private String requestChannelName;
    private MessageChannel replyChannel;
    private String replyChannelName;
    private MessageChannel errorChannel;
    private String errorChannelName;
    private Gateway gateway;
    private Long requestTimeout;
    private Long replyTimeout;

    public void setNullResultPropertyExpressions(Map<String, Expression> nullResultPropertyExpressions) {
        this.nullResultPropertyExpressions = ContentEnricher.convertExpressions(nullResultPropertyExpressions);
    }

    public void setNullResultHeaderExpressions(Map<String, HeaderValueMessageProcessor<?>> nullResultHeaderExpressions) {
        this.nullResultHeaderExpressions = new HashMap(nullResultHeaderExpressions);
    }

    public void setPropertyExpressions(Map<String, Expression> propertyExpressions) {
        Assert.notEmpty(propertyExpressions, (String)"propertyExpressions must not be empty");
        Assert.noNullElements((Object[])propertyExpressions.keySet().toArray(), (String)"propertyExpressions keys must not be empty");
        Assert.noNullElements((Object[])propertyExpressions.values().toArray(), (String)"propertyExpressions values must not be empty");
        this.propertyExpressions = ContentEnricher.convertExpressions(propertyExpressions);
    }

    public void setHeaderExpressions(Map<String, HeaderValueMessageProcessor<?>> headerExpressions) {
        Assert.notEmpty(headerExpressions, (String)"headerExpressions must not be empty");
        Assert.noNullElements((Object[])headerExpressions.keySet().toArray(), (String)"headerExpressions keys must not be empty");
        Assert.noNullElements((Object[])headerExpressions.values().toArray(), (String)"headerExpressions values must not be empty");
        this.headerExpressions = new HashMap(headerExpressions);
    }

    public void setRequestChannel(MessageChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    public void setRequestChannelName(String requestChannelName) {
        Assert.hasText((String)requestChannelName, (String)"'requestChannelName' must not be empty");
        this.requestChannelName = requestChannelName;
    }

    public void setReplyChannel(MessageChannel replyChannel) {
        this.replyChannel = replyChannel;
    }

    public void setReplyChannelName(String replyChannelName) {
        Assert.hasText((String)replyChannelName, (String)"'replyChannelName' must not be empty");
        this.replyChannelName = replyChannelName;
    }

    public void setErrorChannel(MessageChannel errorChannel) {
        this.errorChannel = errorChannel;
    }

    public void setErrorChannelName(String errorChannelName) {
        Assert.hasText((String)errorChannelName, (String)"'errorChannelName' must not be empty");
        this.errorChannelName = errorChannelName;
    }

    public void setRequestTimeout(Long requestTimeout) {
        Assert.notNull((Object)requestTimeout, (String)"requestTimeout must not be null");
        this.requestTimeout = requestTimeout;
    }

    public void setReplyTimeout(Long replyTimeout) {
        Assert.notNull((Object)replyTimeout, (String)"replyTimeout must not be null");
        this.replyTimeout = replyTimeout;
    }

    public void setRequestPayloadExpression(Expression requestPayloadExpression) {
        this.requestPayloadExpression = requestPayloadExpression;
    }

    public void setShouldClonePayload(boolean shouldClonePayload) {
        this.shouldClonePayload = shouldClonePayload;
    }

    public void setIntegrationEvaluationContext(EvaluationContext evaluationContext) {
        this.sourceEvaluationContext = evaluationContext;
    }

    @Override
    public String getComponentType() {
        return "enricher";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.content_enricher;
    }

    @Override
    protected void doInit() {
        this.validateConfiguration();
        BeanFactory beanFactory = this.getBeanFactory();
        if (this.requestChannel != null || this.requestChannelName != null) {
            this.gateway = new Gateway();
            JavaUtils.INSTANCE.acceptIfNotNull(this.requestChannel, this.gateway::setRequestChannel).acceptIfNotNull(this.requestChannelName, this.gateway::setRequestChannelName).acceptIfNotNull(this.requestTimeout, this.gateway::setRequestTimeout).acceptIfNotNull(this.replyTimeout, this.gateway::setReplyTimeout).acceptIfNotNull(this.replyChannel, this.gateway::setReplyChannel).acceptIfNotNull(this.replyChannelName, this.gateway::setReplyChannelName).acceptIfNotNull(this.errorChannel, this.gateway::setErrorChannel).acceptIfNotNull(this.errorChannelName, this.gateway::setErrorChannelName).acceptIfNotNull(beanFactory, this.gateway::setBeanFactory);
            this.gateway.afterPropertiesSet();
        }
        if (this.sourceEvaluationContext == null) {
            this.sourceEvaluationContext = ExpressionUtils.createStandardEvaluationContext(beanFactory);
        }
        StandardEvaluationContext targetContext = ExpressionUtils.createStandardEvaluationContext(beanFactory);
        targetContext.setBeanResolver(null);
        this.targetEvaluationContext = targetContext;
        if (beanFactory != null) {
            this.configureHeaderExpressions(beanFactory, this.headerExpressions);
            this.configureHeaderExpressions(beanFactory, this.nullResultHeaderExpressions);
        }
    }

    private void validateConfiguration() {
        Assert.state((this.requestChannelName == null || this.requestChannel == null ? 1 : 0) != 0, (String)"'requestChannelName' and 'requestChannel' are mutually exclusive.");
        Assert.state((this.replyChannelName == null || this.replyChannel == null ? 1 : 0) != 0, (String)"'replyChannelName' and 'replyChannel' are mutually exclusive.");
        Assert.state((this.errorChannelName == null || this.errorChannel == null ? 1 : 0) != 0, (String)"'errorChannelName' and 'errorChannel' are mutually exclusive.");
        if (this.replyChannel != null || this.replyChannelName != null) {
            Assert.state((this.requestChannel != null || this.requestChannelName != null ? 1 : 0) != 0, (String)"If the replyChannel is set, then the requestChannel must not be null");
        }
        if (this.errorChannel != null || this.errorChannelName != null) {
            Assert.state((this.requestChannel != null || this.requestChannelName != null ? 1 : 0) != 0, (String)"If the errorChannel is set, then the requestChannel must not be null");
        }
    }

    private void configureHeaderExpressions(BeanFactory beanFactory, Map<String, HeaderValueMessageProcessor<?>> headerExpressions) {
        boolean checkReadOnlyHeaders = this.getMessageBuilderFactory() instanceof DefaultMessageBuilderFactory;
        for (Map.Entry<String, HeaderValueMessageProcessor<?>> entry : headerExpressions.entrySet()) {
            if (checkReadOnlyHeaders && ("id".equals(entry.getKey()) || "timestamp".equals(entry.getKey()))) {
                throw new BeanInitializationException("ContentEnricher cannot override 'id' and 'timestamp' read-only headers.\nWrong 'headerExpressions' [" + headerExpressions + "] configuration for " + this.getComponentName());
            }
            if (!(entry.getValue() instanceof BeanFactoryAware)) continue;
            ((BeanFactoryAware)entry.getValue()).setBeanFactory(beanFactory);
        }
    }

    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        Message<?> replyMessage;
        Object targetPayload = this.prepareTargetPayload(requestMessage);
        Message<?> actualRequestMessage = this.prepareActualRequestMessage(requestMessage);
        if (this.gateway == null) {
            replyMessage = actualRequestMessage;
        } else {
            replyMessage = this.gateway.sendAndReceiveMessage(actualRequestMessage);
            if (replyMessage == null) {
                return this.processNullReply(requestMessage, targetPayload);
            }
        }
        for (Map.Entry<Expression, Expression> entry : this.propertyExpressions.entrySet()) {
            Expression propertyExpression = entry.getKey();
            Expression valueExpression = entry.getValue();
            Object value = valueExpression.getValue(this.sourceEvaluationContext, replyMessage);
            propertyExpression.setValue(this.targetEvaluationContext, targetPayload, value);
        }
        if (this.headerExpressions.isEmpty()) {
            return targetPayload;
        }
        return this.buildReplyWithHeaders(replyMessage, targetPayload, this.headerExpressions);
    }

    private Object prepareTargetPayload(Message<?> requestMessage) {
        Object requestPayload = requestMessage.getPayload();
        if (requestPayload instanceof Cloneable && this.shouldClonePayload) {
            try {
                Method cloneMethod = requestPayload.getClass().getMethod("clone", new Class[0]);
                Object result = ReflectionUtils.invokeMethod((Method)cloneMethod, (Object)requestPayload);
                Assert.notNull((Object)result, (String)"The 'clone()' method cannot return null for content enricher");
                return result;
            }
            catch (Exception ex) {
                throw new MessageHandlingException(requestMessage, "Failed to clone payload object in the [" + this + ']', (Throwable)ex);
            }
        }
        return requestPayload;
    }

    private Message<?> prepareActualRequestMessage(Message<?> requestMessage) {
        if (this.requestPayloadExpression != null) {
            Object requestMessagePayload = this.requestPayloadExpression.getValue(this.sourceEvaluationContext, requestMessage);
            Assert.state((requestMessagePayload != null ? 1 : 0) != 0, () -> "Request payload expression produced null for " + requestMessage);
            return this.getMessageBuilderFactory().withPayload(requestMessagePayload).copyHeaders((Map<String, ?>)requestMessage.getHeaders()).build();
        }
        return requestMessage;
    }

    @Nullable
    private Object processNullReply(Message<?> requestMessage, Object targetPayload) {
        if (this.nullResultPropertyExpressions.isEmpty() && this.nullResultHeaderExpressions.isEmpty()) {
            return null;
        }
        for (Map.Entry<Expression, Expression> entry : this.nullResultPropertyExpressions.entrySet()) {
            Expression propertyExpression = entry.getKey();
            Expression valueExpression = entry.getValue();
            Object value = valueExpression.getValue(this.sourceEvaluationContext, requestMessage);
            propertyExpression.setValue(this.targetEvaluationContext, targetPayload, value);
        }
        if (this.nullResultHeaderExpressions.isEmpty()) {
            return targetPayload;
        }
        return this.buildReplyWithHeaders(requestMessage, targetPayload, this.nullResultHeaderExpressions);
    }

    private AbstractIntegrationMessageBuilder<?> buildReplyWithHeaders(Message<?> message, Object targetPayload, Map<String, HeaderValueMessageProcessor<?>> headerExpressions) {
        HashMap targetHeaders = new HashMap(headerExpressions.size());
        for (Map.Entry<String, HeaderValueMessageProcessor<?>> entry : headerExpressions.entrySet()) {
            String header = entry.getKey();
            HeaderValueMessageProcessor<?> valueProcessor = entry.getValue();
            Boolean overwrite = valueProcessor.isOverwrite();
            overwrite = overwrite != null ? overwrite : true;
            if (!overwrite.booleanValue() && message.getHeaders().containsKey((Object)header)) continue;
            Object value = valueProcessor.processMessage(message);
            targetHeaders.put(header, value);
        }
        return this.getMessageBuilderFactory().withPayload(targetPayload).copyHeaders(targetHeaders);
    }

    @Override
    public void start() {
        if (this.gateway != null) {
            this.gateway.start();
        }
    }

    @Override
    public void stop() {
        if (this.gateway != null) {
            this.gateway.stop();
        }
    }

    @Override
    public boolean isRunning() {
        return this.gateway == null || this.gateway.isRunning();
    }

    private static Map<Expression, Expression> convertExpressions(Map<String, Expression> expressions) {
        HashMap<Expression, Expression> localMap = new HashMap<Expression, Expression>(expressions.size());
        for (Map.Entry<String, Expression> entry : expressions.entrySet()) {
            String key = entry.getKey();
            Expression value = entry.getValue();
            localMap.put(SPEL_PARSER.parseExpression(key), value);
        }
        return localMap;
    }

    private static final class Gateway
    extends MessagingGatewaySupport {
        Gateway() {
        }

        @Override
        protected Message<?> sendAndReceiveMessage(Object object) {
            return super.sendAndReceiveMessage(object);
        }

        @Override
        public String getComponentType() {
            return "enricher$gateway";
        }
    }
}

