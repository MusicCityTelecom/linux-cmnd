/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.MethodIntrospector
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.RouteMatcher$Route
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.MessagingAdviceBean;
import org.springframework.messaging.handler.invocation.AbstractExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.invocation.reactive.ArgumentResolverConfigurer;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolverComposite;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import org.springframework.messaging.handler.invocation.reactive.InvocableHelper;
import org.springframework.messaging.handler.invocation.reactive.ReturnValueHandlerConfigurer;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.RouteMatcher;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public abstract class AbstractMethodMessageHandler<T>
implements ReactiveMessageHandler,
ApplicationContextAware,
InitializingBean,
BeanNameAware {
    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private Predicate<Class<?>> handlerPredicate;
    @Nullable
    List<Object> handlers;
    private ArgumentResolverConfigurer argumentResolverConfigurer = new ArgumentResolverConfigurer();
    private ReturnValueHandlerConfigurer returnValueHandlerConfigurer = new ReturnValueHandlerConfigurer();
    private final InvocableHelper invocableHelper = new InvocableHelper(this::createExceptionMethodResolverFor);
    @Nullable
    private ApplicationContext applicationContext;
    @Nullable
    private String beanName;
    private final Map<T, HandlerMethod> handlerMethods = new ConcurrentHashMap<T, HandlerMethod>(64);
    private final Map<String, List<T>> destinationLookup = new ConcurrentHashMap<String, List<T>>(48);

    public void setHandlerPredicate(@Nullable Predicate<Class<?>> handlerPredicate) {
        this.handlerPredicate = handlerPredicate;
    }

    @Nullable
    public Predicate<Class<?>> getHandlerPredicate() {
        return this.handlerPredicate;
    }

    public void setHandlers(List<Object> handlers) {
        this.handlers = handlers;
        this.handlerPredicate = null;
    }

    public void setArgumentResolverConfigurer(ArgumentResolverConfigurer configurer) {
        Assert.notNull((Object)configurer, (String)"HandlerMethodArgumentResolver is required");
        this.argumentResolverConfigurer = configurer;
    }

    public ArgumentResolverConfigurer getArgumentResolverConfigurer() {
        return this.argumentResolverConfigurer;
    }

    public void setReturnValueHandlerConfigurer(ReturnValueHandlerConfigurer configurer) {
        Assert.notNull((Object)configurer, (String)"ReturnValueHandlerConfigurer is required");
        this.returnValueHandlerConfigurer = configurer;
    }

    public ReturnValueHandlerConfigurer getReturnValueHandlerConfigurer() {
        return this.returnValueHandlerConfigurer;
    }

    public void setReactiveAdapterRegistry(ReactiveAdapterRegistry registry) {
        this.invocableHelper.setReactiveAdapterRegistry(registry);
    }

    public ReactiveAdapterRegistry getReactiveAdapterRegistry() {
        return this.invocableHelper.getReactiveAdapterRegistry();
    }

    public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Nullable
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public String getBeanName() {
        return this.beanName != null ? this.beanName : this.getClass().getSimpleName() + "@" + ObjectUtils.getIdentityHexString((Object)this);
    }

    protected void registerExceptionHandlerAdvice(MessagingAdviceBean bean, AbstractExceptionHandlerMethodResolver resolver) {
        this.invocableHelper.registerExceptionHandlerAdvice(bean, resolver);
    }

    public Map<T, HandlerMethod> getHandlerMethods() {
        return Collections.unmodifiableMap(this.handlerMethods);
    }

    public MultiValueMap<String, T> getDestinationLookup() {
        return CollectionUtils.unmodifiableMultiValueMap((MultiValueMap)CollectionUtils.toMultiValueMap(this.destinationLookup));
    }

    protected HandlerMethodArgumentResolverComposite getArgumentResolvers() {
        return this.invocableHelper.getArgumentResolvers();
    }

    public void afterPropertiesSet() {
        List<HandlerMethodArgumentResolver> resolvers = this.initArgumentResolvers();
        if (resolvers.isEmpty()) {
            resolvers = new ArrayList<HandlerMethodArgumentResolver>(this.argumentResolverConfigurer.getCustomResolvers());
        }
        this.invocableHelper.addArgumentResolvers(resolvers);
        List<HandlerMethodReturnValueHandler> handlers = this.initReturnValueHandlers();
        if (handlers.isEmpty()) {
            handlers = new ArrayList<HandlerMethodReturnValueHandler>(this.returnValueHandlerConfigurer.getCustomHandlers());
        }
        this.invocableHelper.addReturnValueHandlers(handlers);
        this.initHandlerMethods();
    }

    protected abstract List<? extends HandlerMethodArgumentResolver> initArgumentResolvers();

    protected abstract List<? extends HandlerMethodReturnValueHandler> initReturnValueHandlers();

    private void initHandlerMethods() {
        Predicate<Class<?>> predicate;
        if (this.handlers != null) {
            for (Object handler : this.handlers) {
                this.detectHandlerMethods(handler);
            }
        }
        if ((predicate = this.handlerPredicate) == null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("[" + this.getBeanName() + "] Skip auto-detection of message handling methods"));
            }
            return;
        }
        if (this.applicationContext == null) {
            this.logger.warn((Object)"No ApplicationContext for auto-detection of beans with message handling methods.");
            return;
        }
        for (String beanName : this.applicationContext.getBeanNamesForType(Object.class)) {
            Class beanType;
            block8: {
                if (beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) continue;
                beanType = null;
                try {
                    beanType = this.applicationContext.getType(beanName);
                }
                catch (Throwable ex) {
                    if (!this.logger.isDebugEnabled()) break block8;
                    this.logger.debug((Object)("Could not resolve target class for bean with name '" + beanName + "'"), ex);
                }
            }
            if (beanType == null || !predicate.test(beanType)) continue;
            this.detectHandlerMethods(beanName);
        }
    }

    protected final void detectHandlerMethods(Object handler) {
        Class handlerType;
        if (handler instanceof String) {
            ApplicationContext context = this.getApplicationContext();
            Assert.state((context != null ? 1 : 0) != 0, (String)"ApplicationContext is required for resolving handler bean names");
            handlerType = context.getType((String)handler);
        } else {
            handlerType = handler.getClass();
        }
        if (handlerType != null) {
            Class userType = ClassUtils.getUserClass(handlerType);
            Map methods = MethodIntrospector.selectMethods((Class)userType, method -> this.getMappingForMethod(method, userType));
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)this.formatMappings(userType, methods));
            }
            methods.forEach((key, value) -> this.registerHandlerMethod(handler, (Method)key, (T)value));
        }
    }

    private String formatMappings(Class<?> userType, Map<Method, T> methods) {
        String packageName = ClassUtils.getPackageName(userType);
        String formattedType = StringUtils.hasText((String)packageName) ? Arrays.stream(packageName.split("\\.")).map(packageSegment -> packageSegment.substring(0, 1)).collect(Collectors.joining(".", "", "." + userType.getSimpleName())) : userType.getSimpleName();
        Function<Method, String> methodFormatter = method -> Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(",", "(", ")"));
        return methods.entrySet().stream().map(e -> {
            Method method = (Method)e.getKey();
            return e.getValue() + ": " + method.getName() + (String)methodFormatter.apply(method);
        }).collect(Collectors.joining("\n\t", "\n\t" + formattedType + ":\n\t", ""));
    }

    @Nullable
    protected abstract T getMappingForMethod(Method var1, Class<?> var2);

    public final void registerHandlerMethod(Object handler, Method method, T mapping) {
        Assert.notNull(mapping, (String)"Mapping must not be null");
        HandlerMethod newHandlerMethod = this.createHandlerMethod(handler, method);
        HandlerMethod oldHandlerMethod = this.handlerMethods.get(mapping);
        if (oldHandlerMethod != null && !oldHandlerMethod.equals(newHandlerMethod)) {
            throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + newHandlerMethod.getBean() + "' bean method \n" + newHandlerMethod + "\nto " + mapping + ": There is already '" + oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
        }
        mapping = this.extendMapping(mapping, newHandlerMethod);
        this.handlerMethods.put(mapping, newHandlerMethod);
        for (String pattern : this.getDirectLookupMappings(mapping)) {
            List values = this.destinationLookup.computeIfAbsent(pattern, p -> new CopyOnWriteArrayList());
            values.add(mapping);
        }
    }

    private HandlerMethod createHandlerMethod(Object handler, Method method) {
        HandlerMethod handlerMethod;
        if (handler instanceof String) {
            ApplicationContext context = this.getApplicationContext();
            Assert.state((context != null ? 1 : 0) != 0, (String)"ApplicationContext is required for resolving handler bean names");
            String beanName = (String)handler;
            handlerMethod = new HandlerMethod(beanName, (BeanFactory)context.getAutowireCapableBeanFactory(), method);
        } else {
            handlerMethod = new HandlerMethod(handler, method);
        }
        return handlerMethod;
    }

    protected T extendMapping(T mapping, HandlerMethod handlerMethod) {
        return mapping;
    }

    protected abstract Set<String> getDirectLookupMappings(T var1);

    @Override
    public Mono<Void> handleMessage(Message<?> message) throws MessagingException {
        Match<T> match = null;
        try {
            match = this.getHandlerMethod(message);
        }
        catch (Exception ex) {
            return Mono.error((Throwable)ex);
        }
        if (match == null) {
            return Mono.empty();
        }
        return this.handleMatch(((Match)match).mapping, ((Match)match).handlerMethod, message);
    }

    protected Mono<Void> handleMatch(T mapping, HandlerMethod handlerMethod, Message<?> message) {
        handlerMethod = handlerMethod.createWithResolvedBean();
        return this.invocableHelper.handleMessage(handlerMethod, message);
    }

    @Nullable
    private Match<T> getHandlerMethod(Message<?> message) {
        Match secondBestMatch;
        List<T> mappingsByUrl;
        ArrayList<Match<T>> matches = new ArrayList<Match<T>>();
        RouteMatcher.Route destination = this.getDestination(message);
        List<T> list = mappingsByUrl = destination != null ? this.destinationLookup.get(destination.value()) : null;
        if (mappingsByUrl != null) {
            this.addMatchesToCollection(mappingsByUrl, message, matches);
        }
        if (matches.isEmpty()) {
            Set<T> allMappings = this.handlerMethods.keySet();
            this.addMatchesToCollection(allMappings, message, matches);
        }
        if (matches.isEmpty()) {
            this.handleNoMatch(destination, message);
            return null;
        }
        MatchComparator comparator = new MatchComparator(this.getMappingComparator(message));
        matches.sort(comparator);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Found " + matches.size() + " handler methods: " + matches));
        }
        Match bestMatch = (Match)matches.get(0);
        if (matches.size() > 1 && comparator.compare(bestMatch, secondBestMatch = (Match)matches.get(1)) == 0) {
            HandlerMethod m1 = bestMatch.handlerMethod;
            HandlerMethod m2 = secondBestMatch.handlerMethod;
            throw new IllegalStateException("Ambiguous handler methods mapped for destination '" + (destination != null ? destination.value() : "") + "': {" + m1.getShortLogMessage() + ", " + m2.getShortLogMessage() + "}");
        }
        return bestMatch;
    }

    @Nullable
    protected abstract RouteMatcher.Route getDestination(Message<?> var1);

    private void addMatchesToCollection(Collection<T> mappingsToCheck, Message<?> message, List<Match<T>> matches) {
        for (T mapping : mappingsToCheck) {
            T match = this.getMatchingMapping(mapping, message);
            if (match == null) continue;
            matches.add(new Match<T>(match, this.handlerMethods.get(mapping)));
        }
    }

    @Nullable
    protected abstract T getMatchingMapping(T var1, Message<?> var2);

    protected abstract Comparator<T> getMappingComparator(Message<?> var1);

    protected void handleNoMatch(@Nullable RouteMatcher.Route destination, Message<?> message) {
        this.logger.debug((Object)("No handlers for destination '" + (destination != null ? destination.value() : "") + "'"));
    }

    protected abstract AbstractExceptionHandlerMethodResolver createExceptionMethodResolverFor(Class<?> var1);

    private class MatchComparator
    implements Comparator<Match<T>> {
        private final Comparator<T> comparator;

        MatchComparator(Comparator<T> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(Match<T> match1, Match<T> match2) {
            return this.comparator.compare(match1.mapping, match2.mapping);
        }
    }

    private static class Match<T> {
        private final T mapping;
        private final HandlerMethod handlerMethod;

        Match(T mapping, HandlerMethod handlerMethod) {
            this.mapping = mapping;
            this.handlerMethod = handlerMethod;
        }

        public String toString() {
            return this.mapping.toString();
        }
    }
}

