/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.EmbeddedValueResolverAware
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.format.support.DefaultFormattingConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.stereotype.Controller
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.StringValueResolver
 *  org.springframework.validation.Validator
 */
package org.springframework.messaging.simp.annotation.support;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.messaging.handler.DestinationPatternsMessageCondition;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.support.AnnotationExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.annotation.support.DestinationVariableMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.HeaderMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.HeadersMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.MessageMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.AbstractExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.invocation.AbstractMethodMessageHandler;
import org.springframework.messaging.handler.invocation.CompletableFutureReturnValueHandler;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandlerComposite;
import org.springframework.messaging.handler.invocation.ListenableFutureReturnValueHandler;
import org.springframework.messaging.handler.invocation.ReactiveReturnValueHandler;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageMappingInfo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageTypeMessageCondition;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.annotation.support.PrincipalMethodArgumentResolver;
import org.springframework.messaging.simp.annotation.support.SendToMethodReturnValueHandler;
import org.springframework.messaging.simp.annotation.support.SubscriptionMethodReturnValueHandler;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringValueResolver;
import org.springframework.validation.Validator;

public class SimpAnnotationMethodMessageHandler
extends AbstractMethodMessageHandler<SimpMessageMappingInfo>
implements EmbeddedValueResolverAware,
SmartLifecycle {
    private static final boolean reactorPresent = ClassUtils.isPresent((String)"reactor.core.publisher.Flux", (ClassLoader)SimpAnnotationMethodMessageHandler.class.getClassLoader());
    private final SubscribableChannel clientInboundChannel;
    private final SimpMessageSendingOperations clientMessagingTemplate;
    private final SimpMessageSendingOperations brokerTemplate;
    private MessageConverter messageConverter;
    private ConversionService conversionService = new DefaultFormattingConversionService();
    private PathMatcher pathMatcher = new AntPathMatcher();
    private boolean slashPathSeparator = true;
    @Nullable
    private Validator validator;
    @Nullable
    private StringValueResolver valueResolver;
    @Nullable
    private MessageHeaderInitializer headerInitializer;
    private volatile boolean running;
    private final Object lifecycleMonitor = new Object();

    public SimpAnnotationMethodMessageHandler(SubscribableChannel clientInboundChannel, MessageChannel clientOutboundChannel, SimpMessageSendingOperations brokerTemplate) {
        Assert.notNull((Object)clientInboundChannel, (String)"clientInboundChannel must not be null");
        Assert.notNull((Object)clientOutboundChannel, (String)"clientOutboundChannel must not be null");
        Assert.notNull((Object)brokerTemplate, (String)"brokerTemplate must not be null");
        this.clientInboundChannel = clientInboundChannel;
        this.clientMessagingTemplate = new SimpMessagingTemplate(clientOutboundChannel);
        this.brokerTemplate = brokerTemplate;
        ArrayList<MessageConverter> converters = new ArrayList<MessageConverter>();
        converters.add(new StringMessageConverter());
        converters.add(new ByteArrayMessageConverter());
        this.messageConverter = new CompositeMessageConverter(converters);
    }

    @Override
    public void setDestinationPrefixes(@Nullable Collection<String> prefixes) {
        super.setDestinationPrefixes(SimpAnnotationMethodMessageHandler.appendSlashes(prefixes));
    }

    @Nullable
    private static Collection<String> appendSlashes(@Nullable Collection<String> prefixes) {
        if (CollectionUtils.isEmpty(prefixes)) {
            return prefixes;
        }
        ArrayList<String> result = new ArrayList<String>(prefixes.size());
        for (String prefix : prefixes) {
            if (!prefix.endsWith("/")) {
                prefix = prefix + "/";
            }
            result.add(prefix);
        }
        return result;
    }

    public void setMessageConverter(MessageConverter converter) {
        this.messageConverter = converter;
        ((AbstractMessageSendingTemplate)((Object)this.clientMessagingTemplate)).setMessageConverter(converter);
    }

    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public ConversionService getConversionService() {
        return this.conversionService;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        Assert.notNull((Object)pathMatcher, (String)"PathMatcher must not be null");
        this.pathMatcher = pathMatcher;
        this.slashPathSeparator = this.pathMatcher.combine("a", "a").equals("a/a");
    }

    public PathMatcher getPathMatcher() {
        return this.pathMatcher;
    }

    @Nullable
    public Validator getValidator() {
        return this.validator;
    }

    public void setValidator(@Nullable Validator validator) {
        this.validator = validator;
    }

    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void start() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.clientInboundChannel.subscribe(this);
            this.running = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void stop() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.running = false;
            this.clientInboundChannel.unsubscribe(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void stop(Runnable callback) {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.stop();
            callback.run();
        }
    }

    public final boolean isRunning() {
        return this.running;
    }

    @Override
    protected List<HandlerMethodArgumentResolver> initArgumentResolvers() {
        ApplicationContext context = this.getApplicationContext();
        ConfigurableListableBeanFactory beanFactory = context instanceof ConfigurableApplicationContext ? ((ConfigurableApplicationContext)context).getBeanFactory() : null;
        ArrayList<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        resolvers.add(new HeaderMethodArgumentResolver(this.conversionService, (ConfigurableBeanFactory)beanFactory));
        resolvers.add(new HeadersMethodArgumentResolver());
        resolvers.add(new DestinationVariableMethodArgumentResolver(this.conversionService));
        resolvers.add(new PrincipalMethodArgumentResolver());
        resolvers.add(new MessageMethodArgumentResolver(this.messageConverter));
        resolvers.addAll(this.getCustomArgumentResolvers());
        resolvers.add(new PayloadMethodArgumentResolver(this.messageConverter, this.validator));
        return resolvers;
    }

    @Override
    protected List<? extends HandlerMethodReturnValueHandler> initReturnValueHandlers() {
        ArrayList<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>();
        handlers.add(new ListenableFutureReturnValueHandler());
        handlers.add(new CompletableFutureReturnValueHandler());
        if (reactorPresent) {
            handlers.add(new ReactiveReturnValueHandler());
        }
        SendToMethodReturnValueHandler sendToHandler = new SendToMethodReturnValueHandler(this.brokerTemplate, true);
        sendToHandler.setHeaderInitializer(this.headerInitializer);
        handlers.add(sendToHandler);
        SubscriptionMethodReturnValueHandler subscriptionHandler = new SubscriptionMethodReturnValueHandler(this.clientMessagingTemplate);
        subscriptionHandler.setHeaderInitializer(this.headerInitializer);
        handlers.add(subscriptionHandler);
        handlers.addAll(this.getCustomReturnValueHandlers());
        sendToHandler = new SendToMethodReturnValueHandler(this.brokerTemplate, false);
        sendToHandler.setHeaderInitializer(this.headerInitializer);
        handlers.add(sendToHandler);
        return handlers;
    }

    @Override
    protected Log getReturnValueHandlerLogger() {
        return SimpLogging.forLog(HandlerMethodReturnValueHandlerComposite.defaultLogger);
    }

    @Override
    protected Log getHandlerMethodLogger() {
        return SimpLogging.forLog(HandlerMethod.defaultLogger);
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    @Override
    @Nullable
    protected SimpMessageMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        SubscribeMapping subscribeAnn;
        MessageMapping messageAnn = (MessageMapping)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)method, MessageMapping.class);
        if (messageAnn != null) {
            MessageMapping typeAnn = (MessageMapping)AnnotatedElementUtils.findMergedAnnotation(handlerType, MessageMapping.class);
            if (messageAnn.value().length > 0 || typeAnn != null && typeAnn.value().length > 0) {
                SimpMessageMappingInfo result = this.createMessageMappingCondition(messageAnn.value());
                if (typeAnn != null) {
                    result = this.createMessageMappingCondition(typeAnn.value()).combine(result);
                }
                return result;
            }
        }
        if ((subscribeAnn = (SubscribeMapping)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)method, SubscribeMapping.class)) != null) {
            MessageMapping typeAnn = (MessageMapping)AnnotatedElementUtils.findMergedAnnotation(handlerType, MessageMapping.class);
            if (subscribeAnn.value().length > 0 || typeAnn != null && typeAnn.value().length > 0) {
                SimpMessageMappingInfo result = this.createSubscribeMappingCondition(subscribeAnn.value());
                if (typeAnn != null) {
                    result = this.createMessageMappingCondition(typeAnn.value()).combine(result);
                }
                return result;
            }
        }
        return null;
    }

    private SimpMessageMappingInfo createMessageMappingCondition(String[] destinations) {
        String[] resolvedDestinations = this.resolveEmbeddedValuesInDestinations(destinations);
        return new SimpMessageMappingInfo(SimpMessageTypeMessageCondition.MESSAGE, new DestinationPatternsMessageCondition(resolvedDestinations, this.pathMatcher));
    }

    private SimpMessageMappingInfo createSubscribeMappingCondition(String[] destinations) {
        String[] resolvedDestinations = this.resolveEmbeddedValuesInDestinations(destinations);
        return new SimpMessageMappingInfo(SimpMessageTypeMessageCondition.SUBSCRIBE, new DestinationPatternsMessageCondition(resolvedDestinations, this.pathMatcher));
    }

    protected String[] resolveEmbeddedValuesInDestinations(String[] destinations) {
        if (this.valueResolver == null) {
            return destinations;
        }
        String[] result = new String[destinations.length];
        for (int i2 = 0; i2 < destinations.length; ++i2) {
            result[i2] = this.valueResolver.resolveStringValue(destinations[i2]);
        }
        return result;
    }

    @Override
    protected Set<String> getDirectLookupDestinations(SimpMessageMappingInfo mapping) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (String pattern : mapping.getDestinationConditions().getPatterns()) {
            if (this.pathMatcher.isPattern(pattern)) continue;
            result.add(pattern);
        }
        return result;
    }

    @Override
    @Nullable
    protected String getDestination(Message<?> message) {
        return SimpMessageHeaderAccessor.getDestination(message.getHeaders());
    }

    @Override
    protected String getLookupDestination(@Nullable String destination) {
        if (destination == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(this.getDestinationPrefixes())) {
            return destination;
        }
        for (String prefix : this.getDestinationPrefixes()) {
            if (!destination.startsWith(prefix)) continue;
            if (this.slashPathSeparator) {
                return destination.substring(prefix.length() - 1);
            }
            return destination.substring(prefix.length());
        }
        return null;
    }

    @Override
    @Nullable
    protected SimpMessageMappingInfo getMatchingMapping(SimpMessageMappingInfo mapping, Message<?> message) {
        return mapping.getMatchingCondition((Message)message);
    }

    @Override
    protected Comparator<SimpMessageMappingInfo> getMappingComparator(Message<?> message) {
        return (info1, info2) -> info1.compareTo((SimpMessageMappingInfo)info2, message);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void handleMatch(SimpMessageMappingInfo mapping, HandlerMethod handlerMethod, String lookupDestination, Message<?> message) {
        Set<String> patterns = mapping.getDestinationConditions().getPatterns();
        if (!CollectionUtils.isEmpty(patterns)) {
            String pattern = patterns.iterator().next();
            Map vars = this.getPathMatcher().extractUriTemplateVariables(pattern, lookupDestination);
            if (!CollectionUtils.isEmpty((Map)vars)) {
                MessageHeaderAccessor mha = MessageHeaderAccessor.getAccessor(message, MessageHeaderAccessor.class);
                Assert.state((mha != null && mha.isMutable() ? 1 : 0) != 0, (String)"Mutable MessageHeaderAccessor required");
                mha.setHeader(DestinationVariableMethodArgumentResolver.DESTINATION_TEMPLATE_VARIABLES_HEADER, vars);
            }
        }
        try {
            SimpAttributesContextHolder.setAttributesFromMessage(message);
            super.handleMatch(mapping, handlerMethod, lookupDestination, message);
        }
        finally {
            SimpAttributesContextHolder.resetAttributes();
        }
    }

    @Override
    protected AbstractExceptionHandlerMethodResolver createExceptionHandlerMethodResolverFor(Class<?> beanType) {
        return new AnnotationExceptionHandlerMethodResolver(beanType);
    }
}

