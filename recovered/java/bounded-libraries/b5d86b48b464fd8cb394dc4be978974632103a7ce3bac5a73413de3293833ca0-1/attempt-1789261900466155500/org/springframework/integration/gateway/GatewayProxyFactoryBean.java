/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.aopalliance.intercept.Interceptor
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.SimpleTypeConverter
 *  org.springframework.beans.TypeConverter
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.task.AsyncListenableTaskExecutor
 *  org.springframework.core.task.AsyncTaskExecutor
 *  org.springframework.core.task.SimpleAsyncTaskExecutor
 *  org.springframework.core.task.support.TaskExecutorAdapter
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.common.LiteralExpression
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.messaging.handler.annotation.Header
 *  org.springframework.messaging.handler.annotation.Payload
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.integration.gateway;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.JavaUtils;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.gateway.DefaultMethodInvokingMethodInterceptor;
import org.springframework.integration.gateway.GatewayMethodInboundMessageMapper;
import org.springframework.integration.gateway.GatewayMethodMetadata;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.gateway.MethodArgsMessageMapper;
import org.springframework.integration.gateway.RequestReplyExchanger;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class GatewayProxyFactoryBean
extends AbstractEndpoint
implements TrackableComponent,
FactoryBean<Object>,
MethodInterceptor,
BeanClassLoaderAware,
IntegrationManagement {
    private final Object initializationMonitor = new Object();
    private final Map<Method, MethodInvocationGateway> gatewayMap = new HashMap<Method, MethodInvocationGateway>();
    private final Class<?> serviceInterface;
    private final Set<Method> havePayloadExpressions = new HashSet<Method>();
    private MessageChannel defaultRequestChannel;
    private String defaultRequestChannelName;
    private MessageChannel defaultReplyChannel;
    private String defaultReplyChannelName;
    private MessageChannel errorChannel;
    private String errorChannelName;
    private Expression defaultRequestTimeout;
    private Expression defaultReplyTimeout;
    private DestinationResolver<MessageChannel> channelResolver;
    private boolean shouldTrack = false;
    private TypeConverter typeConverter = new SimpleTypeConverter();
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private Object serviceProxy;
    private AsyncTaskExecutor asyncExecutor = new SimpleAsyncTaskExecutor();
    private boolean asyncExecutorExplicitlySet;
    private Class<?> asyncSubmitType;
    private Class<?> asyncSubmitListenableType;
    private volatile boolean initialized;
    private Map<String, GatewayMethodMetadata> methodMetadataMap;
    private GatewayMethodMetadata globalMethodMetadata;
    private MethodArgsMessageMapper argsMapper;
    private boolean proxyDefaultMethods;
    private EvaluationContext evaluationContext = new StandardEvaluationContext();
    private MetricsCaptor metricsCaptor;

    public GatewayProxyFactoryBean() {
        this.serviceInterface = RequestReplyExchanger.class;
    }

    public GatewayProxyFactoryBean(Class<?> serviceInterface) {
        Assert.notNull(serviceInterface, (String)"'serviceInterface' must not be null");
        Assert.isTrue((boolean)serviceInterface.isInterface(), (String)"'serviceInterface' must be an interface");
        this.serviceInterface = serviceInterface;
    }

    public void setDefaultRequestChannel(MessageChannel defaultRequestChannel) {
        this.defaultRequestChannel = defaultRequestChannel;
    }

    public void setDefaultRequestChannelName(String defaultRequestChannelName) {
        this.defaultRequestChannelName = defaultRequestChannelName;
    }

    @Nullable
    protected MessageChannel getDefaultRequestChannel() {
        return this.defaultRequestChannel;
    }

    @Nullable
    protected String getDefaultRequestChannelName() {
        return this.defaultRequestChannelName;
    }

    public void setDefaultReplyChannel(MessageChannel defaultReplyChannel) {
        this.defaultReplyChannel = defaultReplyChannel;
    }

    public void setDefaultReplyChannelName(String defaultReplyChannelName) {
        this.defaultReplyChannelName = defaultReplyChannelName;
    }

    @Nullable
    protected MessageChannel getDefaultReplyChannel() {
        return this.defaultReplyChannel;
    }

    @Nullable
    protected String getDefaultReplyChannelName() {
        return this.defaultReplyChannelName;
    }

    public void setErrorChannel(MessageChannel errorChannel) {
        this.errorChannel = errorChannel;
    }

    public void setErrorChannelName(String errorChannelName) {
        this.errorChannelName = errorChannelName;
    }

    @Nullable
    protected MessageChannel getErrorChannel() {
        return this.errorChannel;
    }

    @Nullable
    protected String getErrorChannelName() {
        return this.errorChannelName;
    }

    public void setDefaultRequestTimeout(Long defaultRequestTimeout) {
        this.defaultRequestTimeout = new ValueExpression<Long>(defaultRequestTimeout);
    }

    public void setDefaultRequestTimeoutExpression(Expression defaultRequestTimeout) {
        this.defaultRequestTimeout = defaultRequestTimeout;
    }

    public void setDefaultRequestTimeoutExpressionString(String defaultRequestTimeout) {
        if (StringUtils.hasText((String)defaultRequestTimeout)) {
            this.defaultRequestTimeout = ExpressionUtils.longExpression(defaultRequestTimeout);
        }
    }

    @Nullable
    protected Expression getDefaultRequestTimeout() {
        return this.defaultRequestTimeout;
    }

    public void setDefaultReplyTimeout(Long defaultReplyTimeout) {
        this.defaultReplyTimeout = new ValueExpression<Long>(defaultReplyTimeout);
    }

    public void setDefaultReplyTimeoutExpression(Expression defaultReplyTimeout) {
        this.defaultReplyTimeout = defaultReplyTimeout;
    }

    public void setDefaultReplyTimeoutExpressionString(String defaultReplyTimeout) {
        if (StringUtils.hasText((String)defaultReplyTimeout)) {
            this.defaultReplyTimeout = ExpressionUtils.longExpression(defaultReplyTimeout);
        }
    }

    @Nullable
    protected Expression getDefaultReplyTimeout() {
        return this.defaultReplyTimeout;
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        this.shouldTrack = shouldTrack;
        if (!CollectionUtils.isEmpty(this.gatewayMap)) {
            for (MethodInvocationGateway gateway : this.gatewayMap.values()) {
                gateway.setShouldTrack(shouldTrack);
            }
        }
    }

    public void setAsyncExecutor(@Nullable Executor executor) {
        if (executor == null) {
            this.logger.info((CharSequence)"A null executor disables the async gateway; methods returning Future<?> will run on the calling thread");
        }
        this.asyncExecutor = executor instanceof AsyncTaskExecutor || executor == null ? (AsyncTaskExecutor)executor : new TaskExecutorAdapter(executor);
        this.asyncExecutorExplicitlySet = true;
    }

    public void setTypeConverter(TypeConverter typeConverter) {
        Assert.notNull((Object)typeConverter, (String)"typeConverter must not be null");
        this.typeConverter = typeConverter;
    }

    public void setMethodMetadataMap(Map<String, GatewayMethodMetadata> methodMetadataMap) {
        this.methodMetadataMap = methodMetadataMap;
    }

    public void setGlobalMethodMetadata(GatewayMethodMetadata globalMethodMetadata) {
        this.globalMethodMetadata = globalMethodMetadata;
    }

    @Nullable
    protected GatewayMethodMetadata getGlobalMethodMetadata() {
        return this.globalMethodMetadata;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public final void setMapper(MethodArgsMessageMapper mapper) {
        this.argsMapper = mapper;
    }

    @Nullable
    protected MethodArgsMessageMapper getMapper() {
        return this.argsMapper;
    }

    public void setProxyDefaultMethods(boolean proxyDefaultMethods) {
        this.proxyDefaultMethods = proxyDefaultMethods;
    }

    @Nullable
    protected AsyncTaskExecutor getAsyncExecutor() {
        return this.asyncExecutor;
    }

    protected boolean isAsyncExecutorExplicitlySet() {
        return this.asyncExecutorExplicitlySet;
    }

    public Map<Method, MessagingGatewaySupport> getGateways() {
        return Collections.unmodifiableMap(this.gatewayMap);
    }

    @Override
    public void registerMetricsCaptor(MetricsCaptor metricsCaptorToRegister) {
        this.metricsCaptor = metricsCaptorToRegister;
        this.gatewayMap.values().forEach(gw -> gw.registerMetricsCaptor(metricsCaptorToRegister));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void onInit() {
        Object object = this.initializationMonitor;
        synchronized (object) {
            if (this.initialized) {
                return;
            }
            BeanFactory beanFactory = this.getBeanFactory();
            if (this.channelResolver == null && beanFactory != null) {
                this.channelResolver = ChannelResolverUtils.getChannelResolver(beanFactory);
            }
            this.populateMethodInvocationGateways();
            ProxyFactory gatewayProxyFactory = new ProxyFactory(this.serviceInterface, (Interceptor)this);
            gatewayProxyFactory.addAdvice((Advice)new DefaultMethodInvokingMethodInterceptor());
            this.serviceProxy = gatewayProxyFactory.getProxy(this.beanClassLoader);
            if (this.asyncExecutor != null) {
                Callable<String> task = () -> null;
                Future submitType = this.asyncExecutor.submit(task);
                this.asyncSubmitType = submitType.getClass();
                if (this.asyncExecutor instanceof AsyncListenableTaskExecutor) {
                    submitType = ((AsyncListenableTaskExecutor)this.asyncExecutor).submitListenable(task);
                    this.asyncSubmitListenableType = submitType.getClass();
                }
            }
            this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(beanFactory);
            this.initialized = true;
        }
    }

    private void populateMethodInvocationGateways() {
        Method[] methods;
        for (Method method : methods = ReflectionUtils.getUniqueDeclaredMethods(this.serviceInterface)) {
            if (!Modifier.isAbstract(method.getModifiers()) && method.getAnnotation(Gateway.class) == null && (!method.isDefault() || !this.proxyDefaultMethods)) continue;
            MethodInvocationGateway gateway = this.createGatewayForMethod(method);
            this.gatewayMap.put(method, gateway);
        }
    }

    public Class<?> getObjectType() {
        return this.serviceInterface;
    }

    public Object getObject() {
        if (this.serviceProxy == null) {
            this.onInit();
            Assert.notNull((Object)this.serviceProxy, (String)"failed to initialize proxy");
        }
        return this.serviceProxy;
    }

    @Nullable
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationGateway gateway = this.gatewayMap.get(invocation.getMethod());
        Class<?> returnType = gateway != null ? gateway.returnType : invocation.getMethod().getReturnType();
        if (this.asyncExecutor != null && !Object.class.equals(returnType)) {
            Invoker invoker = new Invoker(invocation);
            if (returnType.isAssignableFrom(this.asyncSubmitType)) {
                return this.asyncExecutor.submit(invoker::get);
            }
            if (returnType.isAssignableFrom(this.asyncSubmitListenableType)) {
                return ((AsyncListenableTaskExecutor)this.asyncExecutor).submitListenable(invoker::get);
            }
            if (CompletableFuture.class.equals(returnType)) {
                return CompletableFuture.supplyAsync(invoker, (Executor)this.asyncExecutor);
            }
            if (Future.class.isAssignableFrom(returnType)) {
                this.logger.debug(() -> "AsyncTaskExecutor submit*() return types are incompatible with the method return type; running on calling thread; the downstream flow must return the required Future: " + returnType.getSimpleName());
            }
        }
        if (Mono.class.isAssignableFrom(returnType)) {
            return this.doInvoke(invocation, false);
        }
        return this.doInvoke(invocation, true);
    }

    @Nullable
    protected Object doInvoke(MethodInvocation invocation, boolean runningOnCallerThread) throws Throwable {
        Method method = invocation.getMethod();
        if (AopUtils.isToStringMethod((Method)method)) {
            return "gateway proxy for service interface [" + this.serviceInterface + "]";
        }
        try {
            return this.invokeGatewayMethod(invocation, runningOnCallerThread);
        }
        catch (Throwable e) {
            this.rethrowExceptionCauseIfPossible(e, invocation.getMethod());
            return null;
        }
    }

    @Nullable
    private Object invokeGatewayMethod(MethodInvocation invocation, boolean runningOnCallerThread) {
        Method method;
        MethodInvocationGateway gateway;
        if (!this.initialized) {
            this.afterPropertiesSet();
        }
        if ((gateway = this.gatewayMap.get(method = invocation.getMethod())) == null) {
            try {
                return invocation.proceed();
            }
            catch (Throwable throwable) {
                throw new IllegalStateException(throwable);
            }
        }
        boolean shouldReturnMessage = Message.class.isAssignableFrom(gateway.returnType) || !runningOnCallerThread && gateway.expectMessage;
        boolean shouldReply = gateway.returnType != Void.TYPE;
        int paramCount = method.getParameterTypes().length;
        boolean hasPayloadExpression = this.findPayloadExpression(method);
        Object response = paramCount == 0 && !hasPayloadExpression ? this.receive(gateway, method, shouldReply, shouldReturnMessage) : this.sendOrSendAndReceive(invocation, gateway, shouldReturnMessage, shouldReply);
        return this.response(gateway.returnType, shouldReturnMessage, response);
    }

    @Nullable
    private Object response(Class<?> returnType, boolean shouldReturnMessage, @Nullable Object response) {
        if (shouldReturnMessage) {
            return response;
        }
        return response != null ? this.convert(response, returnType) : null;
    }

    private boolean findPayloadExpression(Method method) {
        return method.isAnnotationPresent(Payload.class) || this.havePayloadExpressions.contains(method);
    }

    @Nullable
    private Object receive(MethodInvocationGateway gateway, Method method, boolean shouldReply, boolean shouldReturnMessage) {
        Long receiveTimeout = null;
        Expression receiveTimeoutExpression = gateway.getReceiveTimeoutExpression();
        if (receiveTimeoutExpression != null) {
            receiveTimeout = (Long)receiveTimeoutExpression.getValue(this.evaluationContext, Long.class);
        }
        if (shouldReply) {
            if (shouldReturnMessage) {
                if (receiveTimeout != null) {
                    return gateway.receiveMessage(receiveTimeout);
                }
                return gateway.receiveMessage();
            }
            if (receiveTimeout != null) {
                return gateway.receive(receiveTimeout);
            }
            return gateway.receive();
        }
        throw new IllegalArgumentException("The 'void' method without arguments '" + method + "' is not eligible for gateway invocation. Consider to use different signature or 'payloadExpression'.");
    }

    @Nullable
    private Object sendOrSendAndReceive(MethodInvocation invocation, MethodInvocationGateway gateway, boolean shouldReturnMessage, boolean shouldReply) {
        Object[] args = invocation.getArguments();
        if (shouldReply) {
            if (gateway.isMonoReturn) {
                Mono<Message<?>> messageMono = gateway.sendAndReceiveMessageReactive(args);
                if (!shouldReturnMessage) {
                    return messageMono.map(Message::getPayload);
                }
                return messageMono;
            }
            return shouldReturnMessage ? gateway.sendAndReceiveMessage(args) : gateway.sendAndReceive(args);
        }
        gateway.send(args);
        return null;
    }

    private void rethrowExceptionCauseIfPossible(Throwable originalException, Method method) throws Throwable {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        for (Throwable t = originalException; t != null; t = t.getCause()) {
            for (Class<?> exceptionType : exceptionTypes) {
                if (!exceptionType.isAssignableFrom(t.getClass())) continue;
                throw t;
            }
            if (!(t instanceof RuntimeException) || t instanceof MessagingException || t instanceof UndeclaredThrowableException || t instanceof IllegalStateException && "Unexpected exception thrown".equals(t.getMessage())) continue;
            throw t;
        }
        throw originalException;
    }

    private MethodInvocationGateway createGatewayForMethod(Method method) {
        Expression payloadExpression;
        Gateway gatewayAnnotation = method.getAnnotation(Gateway.class);
        GatewayMethodMetadata methodMetadata = null;
        if (!CollectionUtils.isEmpty(this.methodMetadataMap)) {
            methodMetadata = this.methodMetadataMap.get(method.getName());
        }
        if ((payloadExpression = this.extractPayloadExpressionFromAnnotationOrMetadata(gatewayAnnotation, methodMetadata)) != null) {
            this.havePayloadExpressions.add(method);
        }
        String requestChannelName = this.extractRequestChannelFromAnnotationOrMetadata(gatewayAnnotation, methodMetadata);
        String replyChannelName = this.extractReplyChannelFromAnnotationOrMetadata(gatewayAnnotation, methodMetadata);
        Expression requestTimeout = this.extractRequestTimeoutFromAnnotationOrMetadata(gatewayAnnotation, methodMetadata);
        Expression replyTimeout = this.extractReplyTimeoutFromAnnotationOrMetadata(gatewayAnnotation, methodMetadata);
        HashMap<String, Expression> headerExpressions = new HashMap<String, Expression>();
        if (gatewayAnnotation != null) {
            this.annotationHeaders(gatewayAnnotation, headerExpressions);
        } else if (methodMetadata != null && !CollectionUtils.isEmpty(methodMetadata.getHeaderExpressions())) {
            headerExpressions.putAll(methodMetadata.getHeaderExpressions());
        }
        return this.doCreateMethodInvocationGateway(method, payloadExpression, headerExpressions, requestChannelName, replyChannelName, requestTimeout, replyTimeout);
    }

    @Nullable
    private Expression extractPayloadExpressionFromAnnotationOrMetadata(@Nullable Gateway gatewayAnnotation, @Nullable GatewayMethodMetadata methodMetadata) {
        Expression payloadExpression;
        Expression expression = payloadExpression = this.globalMethodMetadata != null ? this.globalMethodMetadata.getPayloadExpression() : null;
        if (gatewayAnnotation != null) {
            if (payloadExpression == null && StringUtils.hasText((String)gatewayAnnotation.payloadExpression())) {
                payloadExpression = EXPRESSION_PARSER.parseExpression(gatewayAnnotation.payloadExpression());
            }
        } else if (methodMetadata != null && methodMetadata.getPayloadExpression() != null) {
            payloadExpression = methodMetadata.getPayloadExpression();
        }
        return payloadExpression;
    }

    @Nullable
    private String extractRequestChannelFromAnnotationOrMetadata(@Nullable Gateway gatewayAnnotation, @Nullable GatewayMethodMetadata methodMetadata) {
        if (gatewayAnnotation != null) {
            return gatewayAnnotation.requestChannel();
        }
        if (methodMetadata != null) {
            return methodMetadata.getRequestChannelName();
        }
        return null;
    }

    @Nullable
    private String extractReplyChannelFromAnnotationOrMetadata(@Nullable Gateway gatewayAnnotation, @Nullable GatewayMethodMetadata methodMetadata) {
        if (gatewayAnnotation != null) {
            return gatewayAnnotation.replyChannel();
        }
        if (methodMetadata != null) {
            return methodMetadata.getReplyChannelName();
        }
        return null;
    }

    @Nullable
    private Expression extractRequestTimeoutFromAnnotationOrMetadata(@Nullable Gateway gatewayAnnotation, @Nullable GatewayMethodMetadata methodMetadata) {
        String reqTimeout;
        Expression requestTimeout = this.defaultRequestTimeout;
        if (gatewayAnnotation != null) {
            if (requestTimeout == null || gatewayAnnotation.requestTimeout() != Long.MIN_VALUE) {
                requestTimeout = new ValueExpression(gatewayAnnotation.requestTimeout());
            }
            if (StringUtils.hasText((String)gatewayAnnotation.requestTimeoutExpression())) {
                requestTimeout = ExpressionUtils.longExpression(gatewayAnnotation.requestTimeoutExpression());
            }
        } else if (methodMetadata != null && StringUtils.hasText((String)(reqTimeout = methodMetadata.getRequestTimeout()))) {
            requestTimeout = ExpressionUtils.longExpression(reqTimeout);
        }
        return requestTimeout;
    }

    @Nullable
    private Expression extractReplyTimeoutFromAnnotationOrMetadata(@Nullable Gateway gatewayAnnotation, @Nullable GatewayMethodMetadata methodMetadata) {
        String repTimeout;
        Expression replyTimeout = this.defaultReplyTimeout;
        if (gatewayAnnotation != null) {
            if (replyTimeout == null || gatewayAnnotation.replyTimeout() != Long.MIN_VALUE) {
                replyTimeout = new ValueExpression(gatewayAnnotation.replyTimeout());
            }
            if (StringUtils.hasText((String)gatewayAnnotation.replyTimeoutExpression())) {
                replyTimeout = ExpressionUtils.longExpression(gatewayAnnotation.replyTimeoutExpression());
            }
        } else if (methodMetadata != null && StringUtils.hasText((String)(repTimeout = methodMetadata.getReplyTimeout()))) {
            replyTimeout = ExpressionUtils.longExpression(repTimeout);
        }
        return replyTimeout;
    }

    private void annotationHeaders(Gateway gatewayAnnotation, Map<String, Expression> headerExpressions) {
        if (!ObjectUtils.isEmpty((Object[])gatewayAnnotation.headers())) {
            for (GatewayHeader gatewayHeader : gatewayAnnotation.headers()) {
                String value = gatewayHeader.value();
                String expression = gatewayHeader.expression();
                String name = gatewayHeader.name();
                boolean hasValue = StringUtils.hasText((String)value);
                if (hasValue == StringUtils.hasText((String)expression)) {
                    throw new BeanDefinitionStoreException("exactly one of 'value' or 'expression' is required on a gateway's header.");
                }
                headerExpressions.put(name, (Expression)(hasValue ? new LiteralExpression(value) : EXPRESSION_PARSER.parseExpression(expression)));
            }
        }
    }

    private MethodInvocationGateway doCreateMethodInvocationGateway(Method method, @Nullable Expression payloadExpression, Map<String, Expression> headerExpressions, @Nullable String requestChannelName, @Nullable String replyChannelName, @Nullable Expression requestTimeout, @Nullable Expression replyTimeout) {
        GatewayMethodInboundMessageMapper messageMapper = this.createGatewayMessageMapper(method, headerExpressions);
        MethodInvocationGateway gateway = new MethodInvocationGateway(messageMapper);
        gateway.setupReturnType(this.serviceInterface, method);
        if (method.getParameterTypes().length == 0 && !this.findPayloadExpression(method)) {
            gateway.setPollable();
        }
        JavaUtils.INSTANCE.acceptIfNotNull(payloadExpression, messageMapper::setPayloadExpression).acceptIfNotNull(this.getTaskScheduler(), gateway::setTaskScheduler);
        this.channels(requestChannelName, replyChannelName, gateway);
        this.timeouts(requestTimeout, replyTimeout, messageMapper, gateway);
        String gatewayMethodBeanName = this.getComponentName() + '#' + method.getName() + '(' + Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(", ")) + ')';
        gateway.setBeanName(gatewayMethodBeanName);
        gateway.setBeanFactory(this.getBeanFactory());
        gateway.setShouldTrack(this.shouldTrack);
        gateway.registerMetricsCaptor(this.metricsCaptor);
        gateway.afterPropertiesSet();
        return gateway;
    }

    private GatewayMethodInboundMessageMapper createGatewayMessageMapper(Method method, Map<String, Expression> headerExpressions) {
        Map<String, Object> headers = this.headers(method, headerExpressions);
        return new GatewayMethodInboundMessageMapper(method, headerExpressions, this.globalMethodMetadata != null ? this.globalMethodMetadata.getHeaderExpressions() : null, headers, this.argsMapper, this.getMessageBuilderFactory());
    }

    @Nullable
    private Map<String, Object> headers(Method method, Map<String, Expression> headerExpressions) {
        HashMap<String, Object> headers = null;
        String errorChannelForVoidReturn = this.errorChannel == null ? this.errorChannelName : this.errorChannel;
        boolean isVoidReturnType = method.getReturnType().equals(Void.TYPE);
        if (errorChannelForVoidReturn != null && isVoidReturnType) {
            headers = new HashMap<String, Object>();
            headers.put("errorChannel", errorChannelForVoidReturn);
        }
        if (isVoidReturnType && (!headerExpressions.containsKey("replyChannel") || this.globalMethodMetadata != null && !this.globalMethodMetadata.getHeaderExpressions().containsKey("replyChannel"))) {
            if (headers == null) {
                headers = new HashMap();
            }
            headers.put("replyChannel", "nullChannel");
        }
        HashSet<String> headerNames = new HashSet<String>(headerExpressions.keySet());
        if (this.globalMethodMetadata != null) {
            headerNames.addAll(this.globalMethodMetadata.getHeaderExpressions().keySet());
        }
        List<MethodParameter> methodParameters = GatewayMethodInboundMessageMapper.getMethodParameterList(method);
        for (MethodParameter methodParameter : methodParameters) {
            Header header = (Header)methodParameter.getParameterAnnotation(Header.class);
            if (header == null) continue;
            String headerName = GatewayMethodInboundMessageMapper.determineHeaderName((Annotation)header, methodParameter);
            headerNames.add(headerName);
        }
        this.validateHeaders(headerNames);
        return headers;
    }

    private void validateHeaders(Set<String> headerNames) {
        for (String header : headerNames) {
            if (!"id".equals(header) && !"timestamp".equals(header)) continue;
            throw new BeanInitializationException("Messaging Gateway cannot override 'id' and 'timestamp' read-only headers.\nWrong headers configuration for " + this.getComponentName());
        }
    }

    private void channels(@Nullable String requestChannelName, @Nullable String replyChannelName, MethodInvocationGateway gateway) {
        this.setChannel(this.errorChannel, gateway::setErrorChannel, this.errorChannelName, gateway::setErrorChannelName);
        this.setChannel(requestChannelName, this.defaultRequestChannelName, gateway::setRequestChannelName, this.defaultRequestChannel, gateway::setRequestChannel);
        this.setChannel(replyChannelName, this.defaultReplyChannelName, gateway::setReplyChannelName, this.defaultReplyChannel, gateway::setReplyChannel);
    }

    private void timeouts(@Nullable Expression requestTimeout, @Nullable Expression replyTimeout, GatewayMethodInboundMessageMapper messageMapper, MethodInvocationGateway gateway) {
        Long timeout;
        if (requestTimeout == null) {
            gateway.setRequestTimeout(-1L);
        } else if (requestTimeout instanceof ValueExpression) {
            timeout = (Long)requestTimeout.getValue(Long.class);
            if (timeout != null) {
                gateway.setRequestTimeout(timeout);
            }
        } else {
            messageMapper.setSendTimeoutExpression(requestTimeout);
        }
        if (replyTimeout == null) {
            gateway.setReplyTimeout(-1L);
        } else if (replyTimeout instanceof ValueExpression) {
            timeout = (Long)replyTimeout.getValue(Long.class);
            if (timeout != null) {
                gateway.setReplyTimeout(timeout);
            }
        } else {
            messageMapper.setReplyTimeoutExpression(replyTimeout);
        }
        if (replyTimeout != null) {
            gateway.setReceiveTimeoutExpression(replyTimeout);
        }
    }

    private void setChannel(@Nullable MessageChannel channel, Consumer<MessageChannel> channelMethod, String channelName, Consumer<String> channelNameMethod) {
        if (channel != null) {
            channelMethod.accept(channel);
        } else if (StringUtils.hasText((String)channelName)) {
            channelNameMethod.accept(channelName);
        }
    }

    private void setChannel(@Nullable String channelName1, String channelName2, Consumer<String> channelNameMethod, MessageChannel channel, Consumer<MessageChannel> channelMethod) {
        if (StringUtils.hasText((String)channelName1)) {
            channelNameMethod.accept(channelName1);
        } else if (StringUtils.hasText((String)channelName2)) {
            channelNameMethod.accept(channelName2);
        } else {
            channelMethod.accept(channel);
        }
    }

    @Override
    protected void doStart() {
        this.gatewayMap.values().forEach(AbstractEndpoint::start);
    }

    @Override
    protected void doStop() {
        this.gatewayMap.values().forEach(AbstractEndpoint::stop);
    }

    @Nullable
    private <T> T convert(Object source, Class<T> expectedReturnType) {
        if (Future.class.isAssignableFrom(expectedReturnType)) {
            return (T)source;
        }
        if (Mono.class.isAssignableFrom(expectedReturnType)) {
            return (T)source;
        }
        if (this.getConversionService() != null) {
            return (T)this.getConversionService().convert(source, expectedReturnType);
        }
        return (T)this.typeConverter.convertIfNecessary(source, expectedReturnType);
    }

    @Override
    public void destroy() {
        super.destroy();
        this.gatewayMap.values().forEach(MessagingGatewaySupport::destroy);
    }

    private static final class MethodInvocationGateway
    extends MessagingGatewaySupport {
        private Expression receiveTimeoutExpression;
        private Class<?> returnType;
        private boolean expectMessage;
        private boolean isMonoReturn;
        private boolean isVoidReturn;
        private boolean pollable;

        MethodInvocationGateway(GatewayMethodInboundMessageMapper messageMapper) {
            this.setRequestMapper(messageMapper);
        }

        @Override
        public IntegrationPatternType getIntegrationPatternType() {
            return this.pollable ? IntegrationPatternType.outbound_channel_adapter : (this.isVoidReturn ? IntegrationPatternType.inbound_channel_adapter : IntegrationPatternType.inbound_gateway);
        }

        @Nullable
        Expression getReceiveTimeoutExpression() {
            return this.receiveTimeoutExpression;
        }

        void setReceiveTimeoutExpression(Expression receiveTimeoutExpression) {
            this.receiveTimeoutExpression = receiveTimeoutExpression;
        }

        void setupReturnType(Class<?> serviceInterface, Method method) {
            ResolvableType resolvableType = Function.class.isAssignableFrom(serviceInterface) && "apply".equals(method.getName()) ? ResolvableType.forClass(Function.class, serviceInterface).getGeneric(new int[]{1}) : ResolvableType.forMethodReturnType((Method)method);
            this.returnType = resolvableType.getRawClass();
            if (this.returnType == null) {
                this.returnType = Object.class;
            } else {
                this.isMonoReturn = Mono.class.isAssignableFrom(this.returnType);
                this.expectMessage = this.hasReturnParameterizedWithMessage(resolvableType);
            }
            this.isVoidReturn = this.isVoidReturnType(resolvableType);
        }

        private boolean hasReturnParameterizedWithMessage(ResolvableType resolvableType) {
            return (Future.class.isAssignableFrom(this.returnType) || Mono.class.isAssignableFrom(this.returnType)) && Message.class.isAssignableFrom(resolvableType.getGeneric(new int[]{0}).resolve(Object.class));
        }

        private boolean isVoidReturnType(ResolvableType resolvableType) {
            Class returnTypeToCheck = this.returnType;
            if (Future.class.isAssignableFrom(this.returnType) || Mono.class.isAssignableFrom(this.returnType)) {
                returnTypeToCheck = resolvableType.getGeneric(new int[]{0}).resolve(Object.class);
            }
            return Void.class.isAssignableFrom(returnTypeToCheck);
        }

        private void setPollable() {
            this.pollable = true;
        }
    }

    private final class Invoker
    implements Supplier<Object> {
        private final MethodInvocation invocation;

        Invoker(MethodInvocation methodInvocation) {
            this.invocation = methodInvocation;
        }

        @Override
        @Nullable
        public Object get() {
            try {
                return GatewayProxyFactoryBean.this.doInvoke(this.invocation, false);
            }
            catch (Error e) {
                throw e;
            }
            catch (Throwable t) {
                if (t instanceof RuntimeException) {
                    throw (RuntimeException)t;
                }
                throw new MessagingException("Asynchronous gateway invocation failed for: " + this.invocation, t);
            }
        }
    }
}

