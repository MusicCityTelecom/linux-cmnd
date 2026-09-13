/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.LocalVariableTableParameterNameDiscoverer
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.annotation.SynthesizingMethodParameter
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.handler.annotation.Header
 *  org.springframework.messaging.handler.annotation.Headers
 *  org.springframework.messaging.handler.annotation.Payload
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.gateway;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.gateway.MethodArgsHolder;
import org.springframework.integration.gateway.MethodArgsMessageMapper;
import org.springframework.integration.mapping.InboundMessageMapper;
import org.springframework.integration.mapping.MessageMappingException;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

class GatewayMethodInboundMessageMapper
implements InboundMessageMapper<Object[]>,
BeanFactoryAware {
    private static final Log LOGGER = LogFactory.getLog(GatewayMethodInboundMessageMapper.class);
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private final Map<String, Expression> parameterPayloadExpressions = new HashMap<String, Expression>();
    private final Method method;
    private final Map<String, Expression> headerExpressions;
    private final Map<String, Expression> globalHeaderExpressions;
    private final Map<String, Object> headers;
    private final List<MethodParameter> parameterList;
    private final MethodArgsMessageMapper argsMapper;
    private final MessageBuilderFactory messageBuilderFactory;
    private Expression payloadExpression;
    private EvaluationContext payloadExpressionEvaluationContext;
    private BeanFactory beanFactory;
    @Nullable
    private Expression sendTimeoutExpression;
    @Nullable
    private Expression replyTimeoutExpression;

    GatewayMethodInboundMessageMapper(Method method) {
        this(method, null);
    }

    GatewayMethodInboundMessageMapper(Method method, @Nullable Map<String, Expression> headerExpressions) {
        this(method, headerExpressions, null, null, null);
    }

    GatewayMethodInboundMessageMapper(Method method, @Nullable Map<String, Expression> headerExpressions, @Nullable Map<String, Expression> globalHeaderExpressions, @Nullable MethodArgsMessageMapper mapper, @Nullable MessageBuilderFactory messageBuilderFactory) {
        this(method, headerExpressions, globalHeaderExpressions, null, mapper, messageBuilderFactory);
    }

    GatewayMethodInboundMessageMapper(Method method, @Nullable Map<String, Expression> headerExpressions, @Nullable Map<String, Expression> globalHeaderExpressions, @Nullable Map<String, Object> headers, @Nullable MethodArgsMessageMapper mapper, @Nullable MessageBuilderFactory messageBuilderFactory) {
        Assert.notNull((Object)method, (String)"method must not be null");
        this.method = method;
        this.headerExpressions = headerExpressions;
        this.headers = headers;
        this.globalHeaderExpressions = globalHeaderExpressions;
        this.parameterList = GatewayMethodInboundMessageMapper.getMethodParameterList(method);
        this.payloadExpression = GatewayMethodInboundMessageMapper.parsePayloadExpression(method);
        this.messageBuilderFactory = messageBuilderFactory == null ? new DefaultMessageBuilderFactory() : messageBuilderFactory;
        this.argsMapper = mapper == null ? new DefaultMethodArgsMessageMapper() : mapper;
    }

    public void setPayloadExpression(Expression expressionString) {
        this.payloadExpression = expressionString;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.payloadExpressionEvaluationContext = ExpressionUtils.createStandardEvaluationContext(beanFactory);
    }

    public void setSendTimeoutExpression(Expression sendTimeoutExpression) {
        this.sendTimeoutExpression = sendTimeoutExpression;
    }

    public void setReplyTimeoutExpression(Expression replyTimeoutExpression) {
        this.replyTimeoutExpression = replyTimeoutExpression;
    }

    @Override
    public Message<?> toMessage(Object[] arguments, @Nullable Map<String, Object> headers) {
        Assert.notNull((Object)arguments, (String)"cannot map null arguments to Message");
        if (arguments.length != this.parameterList.size()) {
            String prefix = arguments.length < this.parameterList.size() ? "Not enough" : "Too many";
            throw new IllegalArgumentException(prefix + " parameters provided for method [" + this.method + "], expected " + this.parameterList.size() + " but received " + arguments.length + ".");
        }
        return this.mapArgumentsToMessage(arguments, headers);
    }

    @Nullable
    private Message<?> mapArgumentsToMessage(Object[] arguments, @Nullable Map<String, Object> headers) {
        try {
            return this.argsMapper.toMessage(new MethodArgsHolder(this.method, arguments), headers);
        }
        catch (MessagingException e) {
            throw e;
        }
        catch (Exception e) {
            throw new MessageMappingException("Failed to map arguments: " + Arrays.toString(arguments), e);
        }
    }

    private Map<String, Object> evaluateHeaders(EvaluationContext methodInvocationEvaluationContext, MethodArgsHolder methodArgsHolder, Map<String, Expression> headerExpressions) {
        HashMap<String, Object> evaluatedHeaders = new HashMap<String, Object>();
        for (Map.Entry<String, Expression> entry : headerExpressions.entrySet()) {
            Object value = entry.getValue().getValue(methodInvocationEvaluationContext, (Object)methodArgsHolder);
            evaluatedHeaders.put(entry.getKey(), value);
        }
        return evaluatedHeaders;
    }

    private StandardEvaluationContext createMethodInvocationEvaluationContext(Object[] arguments) {
        StandardEvaluationContext context = ExpressionUtils.createStandardEvaluationContext(this.beanFactory);
        context.setVariable("args", (Object)arguments);
        context.setVariable("gatewayMethod", (Object)this.method);
        return context;
    }

    @Nullable
    private Object evaluatePayloadExpression(String expressionString, Object argumentValue) {
        Expression expression = this.parameterPayloadExpressions.computeIfAbsent(expressionString, arg_0 -> ((SpelExpressionParser)PARSER).parseExpression(arg_0));
        return expression.getValue(this.payloadExpressionEvaluationContext, argumentValue);
    }

    private void copyHeaders(Map<?, ?> argumentValue, Map<String, Object> headers) {
        for (Map.Entry<?, ?> entry : argumentValue.entrySet()) {
            Object key = entry.getKey();
            if (!(key instanceof String)) {
                if (!LOGGER.isWarnEnabled()) continue;
                LOGGER.warn((Object)("Invalid header name [" + key + "], name type must be String. Skipping mapping of this header to MessageHeaders."));
                continue;
            }
            headers.put((String)key, entry.getValue());
        }
    }

    private void throwExceptionForMultipleMessageOrPayloadParameters(MethodParameter methodParameter) {
        throw new MessagingException("At most one parameter (or expression via method-level @Payload) may be mapped to the payload or Message. Found more than one on method [" + methodParameter.getMethod() + "]");
    }

    static String determineHeaderName(Annotation headerAnnotation, MethodParameter methodParameter) {
        String valueAttribute = (String)AnnotationUtils.getValue((Annotation)headerAnnotation);
        String headerName = StringUtils.hasText((String)valueAttribute) ? valueAttribute : methodParameter.getParameterName();
        Assert.notNull((Object)headerName, (String)"Cannot determine header name. Possible reasons: -debug is disabled or header name is not explicitly provided via @Header annotation.");
        return headerName;
    }

    static List<MethodParameter> getMethodParameterList(Method method) {
        LinkedList<MethodParameter> parameterList = new LinkedList<MethodParameter>();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        for (int i = 0; i < method.getParameterCount(); ++i) {
            SynthesizingMethodParameter methodParameter = new SynthesizingMethodParameter(method, i);
            methodParameter.initParameterNameDiscovery((ParameterNameDiscoverer)parameterNameDiscoverer);
            parameterList.add((MethodParameter)methodParameter);
        }
        return parameterList;
    }

    @Nullable
    private static Expression parsePayloadExpression(Method method) {
        Expression expression = null;
        Payload payload = method.getAnnotation(Payload.class);
        if (payload != null) {
            String expressionString = (String)AnnotationUtils.getValue((Annotation)payload);
            Assert.hasText((String)expressionString, (String)"@Payload at method-level on a Gateway must provide a non-empty Expression.");
            expression = PARSER.parseExpression(expressionString);
        }
        return expression;
    }

    public class DefaultMethodArgsMessageMapper
    implements MethodArgsMessageMapper {
        private final MessageBuilderFactory msgBuilderFactory;

        public DefaultMethodArgsMessageMapper() {
            this.msgBuilderFactory = GatewayMethodInboundMessageMapper.this.messageBuilderFactory;
        }

        @Override
        public Message<?> toMessage(MethodArgsHolder holder, @Nullable Map<String, Object> headersToMap) {
            HashMap<String, Object> headersToPopulate;
            Object messageOrPayload = null;
            boolean foundPayloadAnnotation = false;
            Object[] arguments = holder.getArgs();
            StandardEvaluationContext methodInvocationEvaluationContext = GatewayMethodInboundMessageMapper.this.createMethodInvocationEvaluationContext(arguments);
            HashMap<String, Object> hashMap = headersToPopulate = headersToMap != null ? new HashMap<String, Object>(headersToMap) : new HashMap();
            if (GatewayMethodInboundMessageMapper.this.payloadExpression != null) {
                messageOrPayload = GatewayMethodInboundMessageMapper.this.payloadExpression.getValue((EvaluationContext)methodInvocationEvaluationContext, (Object)holder);
            }
            for (int i = 0; i < GatewayMethodInboundMessageMapper.this.parameterList.size(); ++i) {
                Object argumentValue = arguments[i];
                MethodParameter methodParameter = (MethodParameter)GatewayMethodInboundMessageMapper.this.parameterList.get(i);
                Annotation annotation = MessagingAnnotationUtils.findMessagePartAnnotation(methodParameter.getParameterAnnotations(), false);
                if (annotation != null) {
                    if (annotation.annotationType().equals(Payload.class)) {
                        messageOrPayload = this.processPayloadAnnotation(messageOrPayload, argumentValue, methodParameter, annotation);
                        foundPayloadAnnotation = true;
                        continue;
                    }
                    this.headerOrHeaders(headersToPopulate, argumentValue, methodParameter, annotation);
                    continue;
                }
                if (messageOrPayload == null) {
                    messageOrPayload = argumentValue;
                    continue;
                }
                if (Map.class.isAssignableFrom(methodParameter.getParameterType())) {
                    this.processMapArgument(messageOrPayload, foundPayloadAnnotation, headersToPopulate, (Map)argumentValue);
                    continue;
                }
                if (GatewayMethodInboundMessageMapper.this.payloadExpression != null) continue;
                GatewayMethodInboundMessageMapper.this.throwExceptionForMultipleMessageOrPayloadParameters(methodParameter);
            }
            Assert.isTrue((messageOrPayload != null ? 1 : 0) != 0, () -> "The 'payload' (or 'Message') for gateway [" + GatewayMethodInboundMessageMapper.this.method + "] method call cannot be determined (must not be 'null') from the provided arguments: " + Arrays.toString(arguments));
            this.populateSendAndReplyTimeoutHeaders((EvaluationContext)methodInvocationEvaluationContext, holder, headersToPopulate);
            return this.buildMessage(holder, headersToPopulate, messageOrPayload, (EvaluationContext)methodInvocationEvaluationContext);
        }

        private void headerOrHeaders(Map<String, Object> headersToPopulate, Object argumentValue, MethodParameter methodParameter, Annotation annotation) {
            if (annotation.annotationType().equals(Header.class)) {
                this.processHeaderAnnotation(headersToPopulate, argumentValue, methodParameter, annotation);
            } else if (annotation.annotationType().equals(Headers.class)) {
                this.processHeadersAnnotation(headersToPopulate, argumentValue);
            }
        }

        @Nullable
        private Object processPayloadAnnotation(@Nullable Object messageOrPayload, Object argumentValue, MethodParameter methodParameter, Annotation annotation) {
            String expression;
            if (messageOrPayload != null) {
                GatewayMethodInboundMessageMapper.this.throwExceptionForMultipleMessageOrPayloadParameters(methodParameter);
            }
            if (!StringUtils.hasText((String)(expression = (String)AnnotationUtils.getValue((Annotation)annotation)))) {
                return argumentValue;
            }
            return GatewayMethodInboundMessageMapper.this.evaluatePayloadExpression(expression, argumentValue);
        }

        private void processHeaderAnnotation(Map<String, Object> headersToPopulate, @Nullable Object argumentValue, MethodParameter methodParameter, Annotation annotation) {
            String headerName = GatewayMethodInboundMessageMapper.determineHeaderName(annotation, methodParameter);
            if (((Boolean)AnnotationUtils.getValue((Annotation)annotation, (String)"required")).booleanValue() && argumentValue == null) {
                throw new IllegalArgumentException("Received null argument value for required header: '" + headerName + "'");
            }
            headersToPopulate.put(headerName, argumentValue);
        }

        private void processHeadersAnnotation(Map<String, Object> headersToPopulate, @Nullable Object argumentValue) {
            if (argumentValue != null) {
                if (!(argumentValue instanceof Map)) {
                    throw new IllegalArgumentException("@Headers annotation is only valid for Map-typed parameters");
                }
                for (Object key : ((Map)argumentValue).keySet()) {
                    Assert.isInstanceOf(String.class, key, (String)("Invalid header name [" + key + "], name type must be String."));
                    Object value = ((Map)argumentValue).get(key);
                    headersToPopulate.put((String)key, value);
                }
            }
        }

        private void processMapArgument(Object messageOrPayload, boolean foundPayloadAnnotation, Map<String, Object> headersToPopulate, Map<?, ?> argumentValue) {
            if (messageOrPayload instanceof Map && !foundPayloadAnnotation && GatewayMethodInboundMessageMapper.this.payloadExpression == null) {
                throw new MessagingException("Ambiguous method parameters; found more than one Map-typed parameter and neither one contains a @Payload annotation");
            }
            GatewayMethodInboundMessageMapper.this.copyHeaders(argumentValue, headersToPopulate);
        }

        private void populateSendAndReplyTimeoutHeaders(EvaluationContext methodInvocationEvaluationContext, MethodArgsHolder methodArgsHolder, Map<String, Object> headersToPopulate) {
            if (GatewayMethodInboundMessageMapper.this.sendTimeoutExpression != null) {
                headersToPopulate.computeIfAbsent("sendTimeout", v -> GatewayMethodInboundMessageMapper.this.sendTimeoutExpression.getValue(methodInvocationEvaluationContext, (Object)methodArgsHolder, Long.class));
            }
            if (GatewayMethodInboundMessageMapper.this.replyTimeoutExpression != null) {
                headersToPopulate.computeIfAbsent("receiveTimeout", v -> GatewayMethodInboundMessageMapper.this.replyTimeoutExpression.getValue(methodInvocationEvaluationContext, (Object)methodArgsHolder, Long.class));
            }
        }

        private Message<?> buildMessage(MethodArgsHolder methodArgsHolder, Map<String, Object> headers, Object messageOrPayload, EvaluationContext methodInvocationEvaluationContext) {
            Map evaluatedHeaders;
            AbstractIntegrationMessageBuilder<Object> builder = messageOrPayload instanceof Message ? this.msgBuilderFactory.fromMessage((Message)messageOrPayload) : this.msgBuilderFactory.withPayload(messageOrPayload);
            builder.copyHeadersIfAbsent(headers);
            if (!CollectionUtils.isEmpty((Map)GatewayMethodInboundMessageMapper.this.headerExpressions)) {
                evaluatedHeaders = GatewayMethodInboundMessageMapper.this.evaluateHeaders(methodInvocationEvaluationContext, methodArgsHolder, GatewayMethodInboundMessageMapper.this.headerExpressions);
                builder.copyHeaders(evaluatedHeaders);
            }
            if (!CollectionUtils.isEmpty((Map)GatewayMethodInboundMessageMapper.this.globalHeaderExpressions)) {
                evaluatedHeaders = GatewayMethodInboundMessageMapper.this.evaluateHeaders(methodInvocationEvaluationContext, methodArgsHolder, GatewayMethodInboundMessageMapper.this.globalHeaderExpressions);
                builder.copyHeadersIfAbsent(evaluatedHeaders);
            }
            if (GatewayMethodInboundMessageMapper.this.headers != null) {
                builder.copyHeadersIfAbsent(GatewayMethodInboundMessageMapper.this.headers);
            }
            return builder.build();
        }
    }
}

