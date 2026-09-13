/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.EmbeddedValueResolverAware
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.format.support.DefaultFormattingConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.stereotype.Controller
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.RouteMatcher
 *  org.springframework.util.RouteMatcher$Route
 *  org.springframework.util.SimpleRouteMatcher
 *  org.springframework.util.StringValueResolver
 *  org.springframework.validation.Validator
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.annotation.reactive;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.KotlinDetector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.codec.Decoder;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.CompositeMessageCondition;
import org.springframework.messaging.handler.DestinationPatternsMessageCondition;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.MessagingAdviceBean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.reactive.ContinuationHandlerMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.reactive.DestinationVariableMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.reactive.HeaderMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.reactive.HeadersMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.reactive.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.AnnotationExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.invocation.AbstractExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.invocation.reactive.AbstractMethodMessageHandler;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.RouteMatcher;
import org.springframework.util.SimpleRouteMatcher;
import org.springframework.util.StringValueResolver;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

public class MessageMappingMessageHandler
extends AbstractMethodMessageHandler<CompositeMessageCondition>
implements EmbeddedValueResolverAware {
    private final List<Decoder<?>> decoders = new ArrayList();
    @Nullable
    private Validator validator;
    @Nullable
    private RouteMatcher routeMatcher;
    private ConversionService conversionService = new DefaultFormattingConversionService();
    @Nullable
    private StringValueResolver valueResolver;

    public MessageMappingMessageHandler() {
        this.setHandlerPredicate(type -> AnnotatedElementUtils.hasAnnotation((AnnotatedElement)type, Controller.class));
    }

    public void setDecoders(List<? extends Decoder<?>> decoders) {
        this.decoders.clear();
        this.decoders.addAll(decoders);
    }

    public List<? extends Decoder<?>> getDecoders() {
        return this.decoders;
    }

    public void setValidator(@Nullable Validator validator) {
        this.validator = validator;
    }

    @Nullable
    public Validator getValidator() {
        return this.validator;
    }

    public void setRouteMatcher(@Nullable RouteMatcher routeMatcher) {
        this.routeMatcher = routeMatcher;
    }

    @Nullable
    public RouteMatcher getRouteMatcher() {
        return this.routeMatcher;
    }

    protected RouteMatcher obtainRouteMatcher() {
        RouteMatcher routeMatcher = this.getRouteMatcher();
        Assert.state((routeMatcher != null ? 1 : 0) != 0, (String)"No RouteMatcher set");
        return routeMatcher;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public ConversionService getConversionService() {
        return this.conversionService;
    }

    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
    }

    public void registerMessagingAdvice(MessagingAdviceBean bean) {
        AnnotationExceptionHandlerMethodResolver resolver;
        Class<?> type = bean.getBeanType();
        if (type != null && (resolver = new AnnotationExceptionHandlerMethodResolver(type)).hasExceptionMappings()) {
            this.registerExceptionHandlerAdvice(bean, resolver);
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)("Detected @MessageExceptionHandler methods in " + bean));
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (this.routeMatcher == null) {
            AntPathMatcher pathMatcher = new AntPathMatcher();
            pathMatcher.setPathSeparator(".");
            this.routeMatcher = new SimpleRouteMatcher((PathMatcher)pathMatcher);
        }
        super.afterPropertiesSet();
    }

    @Override
    protected List<? extends HandlerMethodArgumentResolver> initArgumentResolvers() {
        ArrayList<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        ApplicationContext context = this.getApplicationContext();
        ConfigurableListableBeanFactory beanFactory = context instanceof ConfigurableApplicationContext ? ((ConfigurableApplicationContext)context).getBeanFactory() : null;
        resolvers.add(new HeaderMethodArgumentResolver(this.conversionService, (ConfigurableBeanFactory)beanFactory));
        resolvers.add(new HeadersMethodArgumentResolver());
        resolvers.add(new DestinationVariableMethodArgumentResolver(this.conversionService));
        if (KotlinDetector.isKotlinPresent()) {
            resolvers.add(new ContinuationHandlerMethodArgumentResolver());
        }
        resolvers.addAll(this.getArgumentResolverConfigurer().getCustomResolvers());
        resolvers.add(new PayloadMethodArgumentResolver(this.getDecoders(), this.validator, this.getReactiveAdapterRegistry(), true));
        return resolvers;
    }

    @Override
    protected List<? extends HandlerMethodReturnValueHandler> initReturnValueHandlers() {
        return Collections.emptyList();
    }

    @Override
    protected CompositeMessageCondition getMappingForMethod(Method method, Class<?> handlerType) {
        CompositeMessageCondition typeCondition;
        CompositeMessageCondition methodCondition = this.getCondition(method);
        if (methodCondition != null && (typeCondition = this.getCondition(handlerType)) != null) {
            return typeCondition.combine(methodCondition);
        }
        return methodCondition;
    }

    @Nullable
    protected CompositeMessageCondition getCondition(AnnotatedElement element) {
        MessageMapping ann = (MessageMapping)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)element, MessageMapping.class);
        if (ann == null || ann.value().length == 0) {
            return null;
        }
        String[] patterns = this.processDestinations(ann.value());
        return new CompositeMessageCondition(new DestinationPatternsMessageCondition(patterns, this.obtainRouteMatcher()));
    }

    protected String[] processDestinations(String[] destinations) {
        if (this.valueResolver != null) {
            destinations = (String[])Arrays.stream(destinations).map(s -> this.valueResolver.resolveStringValue(s)).toArray(String[]::new);
        }
        return destinations;
    }

    @Override
    protected Set<String> getDirectLookupMappings(CompositeMessageCondition mapping) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (String pattern : mapping.getCondition(DestinationPatternsMessageCondition.class).getPatterns()) {
            if (this.obtainRouteMatcher().isPattern(pattern)) continue;
            result.add(pattern);
        }
        return result;
    }

    @Override
    protected RouteMatcher.Route getDestination(Message<?> message) {
        return (RouteMatcher.Route)message.getHeaders().get("lookupDestination");
    }

    @Override
    protected CompositeMessageCondition getMatchingMapping(CompositeMessageCondition mapping, Message<?> message) {
        return mapping.getMatchingCondition((Message)message);
    }

    @Override
    protected Comparator<CompositeMessageCondition> getMappingComparator(Message<?> message) {
        return (info1, info2) -> info1.compareTo((CompositeMessageCondition)info2, message);
    }

    @Override
    protected AbstractExceptionHandlerMethodResolver createExceptionMethodResolverFor(Class<?> beanType) {
        return new AnnotationExceptionHandlerMethodResolver(beanType);
    }

    @Override
    protected Mono<Void> handleMatch(CompositeMessageCondition mapping, HandlerMethod handlerMethod, Message<?> message) {
        Set<String> patterns = mapping.getCondition(DestinationPatternsMessageCondition.class).getPatterns();
        if (!CollectionUtils.isEmpty(patterns)) {
            String pattern = patterns.iterator().next();
            RouteMatcher.Route destination = this.getDestination(message);
            Assert.state((destination != null ? 1 : 0) != 0, (String)"Missing destination header");
            Map vars = this.obtainRouteMatcher().matchAndExtract(pattern, destination);
            if (!CollectionUtils.isEmpty((Map)vars)) {
                MessageHeaderAccessor mha = MessageHeaderAccessor.getAccessor(message, MessageHeaderAccessor.class);
                Assert.state((mha != null && mha.isMutable() ? 1 : 0) != 0, (String)"Mutable MessageHeaderAccessor required");
                mha.setHeader(DestinationVariableMethodArgumentResolver.DESTINATION_TEMPLATE_VARIABLES_HEADER, vars);
            }
        }
        return super.handleMatch(mapping, handlerMethod, message);
    }
}

