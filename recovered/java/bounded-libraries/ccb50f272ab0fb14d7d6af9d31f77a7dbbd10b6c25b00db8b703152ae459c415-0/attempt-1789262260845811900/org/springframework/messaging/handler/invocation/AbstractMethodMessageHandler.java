/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.MethodIntrospector
 *  org.springframework.core.MethodParameter
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.ListenableFutureCallback
 */
package org.springframework.messaging.handler.invocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.MessagingAdviceBean;
import org.springframework.messaging.handler.invocation.AbstractExceptionHandlerMethodResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolverComposite;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandlerComposite;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public abstract class AbstractMethodMessageHandler<T>
implements MessageHandler,
ApplicationContextAware,
InitializingBean {
    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private Log handlerMethodLogger;
    private final List<String> destinationPrefixes = new ArrayList<String>();
    private final List<HandlerMethodArgumentResolver> customArgumentResolvers = new ArrayList<HandlerMethodArgumentResolver>(4);
    private final List<HandlerMethodReturnValueHandler> customReturnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>(4);
    private final HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();
    private final HandlerMethodReturnValueHandlerComposite returnValueHandlers = new HandlerMethodReturnValueHandlerComposite();
    @Nullable
    private ApplicationContext applicationContext;
    private final Map<T, HandlerMethod> handlerMethods = new LinkedHashMap<T, HandlerMethod>(64);
    private final MultiValueMap<String, T> destinationLookup = new LinkedMultiValueMap(48);
    private final Map<Class<?>, AbstractExceptionHandlerMethodResolver> exceptionHandlerCache = new ConcurrentHashMap(64);
    private final Map<MessagingAdviceBean, AbstractExceptionHandlerMethodResolver> exceptionHandlerAdviceCache = new LinkedHashMap<MessagingAdviceBean, AbstractExceptionHandlerMethodResolver>(64);

    public void setDestinationPrefixes(@Nullable Collection<String> prefixes) {
        this.destinationPrefixes.clear();
        if (prefixes != null) {
            for (String prefix : prefixes) {
                prefix = prefix.trim();
                this.destinationPrefixes.add(prefix);
            }
        }
    }

    public Collection<String> getDestinationPrefixes() {
        return this.destinationPrefixes;
    }

    public void setCustomArgumentResolvers(@Nullable List<HandlerMethodArgumentResolver> customArgumentResolvers) {
        this.customArgumentResolvers.clear();
        if (customArgumentResolvers != null) {
            this.customArgumentResolvers.addAll(customArgumentResolvers);
        }
    }

    public List<HandlerMethodArgumentResolver> getCustomArgumentResolvers() {
        return this.customArgumentResolvers;
    }

    public void setCustomReturnValueHandlers(@Nullable List<HandlerMethodReturnValueHandler> customReturnValueHandlers) {
        this.customReturnValueHandlers.clear();
        if (customReturnValueHandlers != null) {
            this.customReturnValueHandlers.addAll(customReturnValueHandlers);
        }
    }

    public List<HandlerMethodReturnValueHandler> getCustomReturnValueHandlers() {
        return this.customReturnValueHandlers;
    }

    public void setArgumentResolvers(@Nullable List<HandlerMethodArgumentResolver> argumentResolvers) {
        if (argumentResolvers == null) {
            this.argumentResolvers.clear();
            return;
        }
        this.argumentResolvers.addResolvers(argumentResolvers);
    }

    public List<HandlerMethodArgumentResolver> getArgumentResolvers() {
        return this.argumentResolvers.getResolvers();
    }

    public void setReturnValueHandlers(@Nullable List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        if (returnValueHandlers == null) {
            this.returnValueHandlers.clear();
            return;
        }
        this.returnValueHandlers.addHandlers(returnValueHandlers);
    }

    public List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
        return this.returnValueHandlers.getReturnValueHandlers();
    }

    public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Nullable
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void afterPropertiesSet() {
        Log returnValueLogger;
        if (this.argumentResolvers.getResolvers().isEmpty()) {
            this.argumentResolvers.addResolvers(this.initArgumentResolvers());
        }
        if (this.returnValueHandlers.getReturnValueHandlers().isEmpty()) {
            this.returnValueHandlers.addHandlers(this.initReturnValueHandlers());
        }
        if ((returnValueLogger = this.getReturnValueHandlerLogger()) != null) {
            this.returnValueHandlers.setLogger(returnValueLogger);
        }
        this.handlerMethodLogger = this.getHandlerMethodLogger();
        ApplicationContext context = this.getApplicationContext();
        if (context == null) {
            return;
        }
        for (String beanName : context.getBeanNamesForType(Object.class)) {
            Class beanType;
            block7: {
                if (beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) continue;
                beanType = null;
                try {
                    beanType = context.getType(beanName);
                }
                catch (Throwable ex) {
                    if (!this.logger.isDebugEnabled()) break block7;
                    this.logger.debug((Object)("Could not resolve target class for bean with name '" + beanName + "'"), ex);
                }
            }
            if (beanType == null || !this.isHandler(beanType)) continue;
            this.detectHandlerMethods(beanName);
        }
    }

    protected abstract List<? extends HandlerMethodArgumentResolver> initArgumentResolvers();

    protected abstract List<? extends HandlerMethodReturnValueHandler> initReturnValueHandlers();

    protected abstract boolean isHandler(Class<?> var1);

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

    protected void registerHandlerMethod(Object handler, Method method, T mapping) {
        Assert.notNull(mapping, (String)"Mapping must not be null");
        HandlerMethod newHandlerMethod = this.createHandlerMethod(handler, method);
        HandlerMethod oldHandlerMethod = this.handlerMethods.get(mapping);
        if (oldHandlerMethod != null && !oldHandlerMethod.equals(newHandlerMethod)) {
            throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + newHandlerMethod.getBean() + "' bean method \n" + newHandlerMethod + "\nto " + mapping + ": There is already '" + oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
        }
        this.handlerMethods.put(mapping, newHandlerMethod);
        for (String pattern : this.getDirectLookupDestinations(mapping)) {
            this.destinationLookup.add((Object)pattern, mapping);
        }
    }

    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
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

    protected abstract Set<String> getDirectLookupDestinations(T var1);

    @Nullable
    protected Log getReturnValueHandlerLogger() {
        return null;
    }

    @Nullable
    protected Log getHandlerMethodLogger() {
        return null;
    }

    protected void registerExceptionHandlerAdvice(MessagingAdviceBean bean, AbstractExceptionHandlerMethodResolver resolver) {
        this.exceptionHandlerAdviceCache.put(bean, resolver);
    }

    public Map<T, HandlerMethod> getHandlerMethods() {
        return Collections.unmodifiableMap(this.handlerMethods);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String destination = this.getDestination(message);
        if (destination == null) {
            return;
        }
        String lookupDestination = this.getLookupDestination(destination);
        if (lookupDestination == null) {
            return;
        }
        MessageHeaderAccessor headerAccessor = MessageHeaderAccessor.getMutableAccessor(message);
        headerAccessor.setHeader("lookupDestination", lookupDestination);
        headerAccessor.setLeaveMutable(true);
        message = MessageBuilder.createMessage(message.getPayload(), headerAccessor.getMessageHeaders());
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Searching methods to handle " + headerAccessor.getShortLogMessage(message.getPayload()) + ", lookupDestination='" + lookupDestination + "'"));
        }
        this.handleMessageInternal(message, lookupDestination);
        headerAccessor.setImmutable();
    }

    @Nullable
    protected abstract String getDestination(Message<?> var1);

    @Nullable
    protected String getLookupDestination(@Nullable String destination) {
        if (destination == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(this.destinationPrefixes)) {
            return destination;
        }
        for (int i2 = 0; i2 < this.destinationPrefixes.size(); ++i2) {
            String prefix = this.destinationPrefixes.get(i2);
            if (!destination.startsWith(prefix)) continue;
            return destination.substring(prefix.length());
        }
        return null;
    }

    protected void handleMessageInternal(Message<?> message, String lookupDestination) {
        Match secondBestMatch;
        ArrayList<Match> matches = new ArrayList<Match>();
        List mappingsByUrl = (List)this.destinationLookup.get((Object)lookupDestination);
        if (mappingsByUrl != null) {
            this.addMatchesToCollection(mappingsByUrl, message, matches);
        }
        if (matches.isEmpty()) {
            Set<T> allMappings = this.handlerMethods.keySet();
            this.addMatchesToCollection(allMappings, message, matches);
        }
        if (matches.isEmpty()) {
            this.handleNoMatch(this.handlerMethods.keySet(), lookupDestination, message);
            return;
        }
        MatchComparator comparator = new MatchComparator(this.getMappingComparator(message));
        matches.sort(comparator);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)("Found " + matches.size() + " handler methods: " + matches));
        }
        Match bestMatch = (Match)matches.get(0);
        if (matches.size() > 1 && comparator.compare(bestMatch, secondBestMatch = (Match)matches.get(1)) == 0) {
            Method m1 = bestMatch.handlerMethod.getMethod();
            Method m2 = secondBestMatch.handlerMethod.getMethod();
            throw new IllegalStateException("Ambiguous handler methods mapped for destination '" + lookupDestination + "': {" + m1 + ", " + m2 + "}");
        }
        this.handleMatch(bestMatch.mapping, bestMatch.handlerMethod, lookupDestination, message);
    }

    private void addMatchesToCollection(Collection<T> mappingsToCheck, Message<?> message, List<Match> matches) {
        for (T mapping : mappingsToCheck) {
            T match = this.getMatchingMapping(mapping, message);
            if (match == null) continue;
            matches.add(new Match(match, this.handlerMethods.get(mapping)));
        }
    }

    @Nullable
    protected abstract T getMatchingMapping(T var1, Message<?> var2);

    protected void handleNoMatch(Set<T> ts, String lookupDestination, Message<?> message) {
        this.logger.debug((Object)"No matching message handler methods.");
    }

    protected abstract Comparator<T> getMappingComparator(Message<?> var1);

    protected void handleMatch(T mapping, HandlerMethod handlerMethod, String lookupDestination, Message<?> message) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Invoking " + handlerMethod.getShortLogMessage()));
        }
        handlerMethod = handlerMethod.createWithResolvedBean();
        InvocableHandlerMethod invocable = new InvocableHandlerMethod(handlerMethod);
        if (this.handlerMethodLogger != null) {
            invocable.setLogger(this.handlerMethodLogger);
        }
        invocable.setMessageMethodArgumentResolvers(this.argumentResolvers);
        try {
            Object returnValue = invocable.invoke(message, new Object[0]);
            MethodParameter returnType = handlerMethod.getReturnType();
            if (Void.TYPE == returnType.getParameterType()) {
                return;
            }
            if (returnValue != null && this.returnValueHandlers.isAsyncReturnValue(returnValue, returnType)) {
                ListenableFuture<?> future = this.returnValueHandlers.toListenableFuture(returnValue, returnType);
                if (future != null) {
                    future.addCallback((ListenableFutureCallback)new ReturnValueListenableFutureCallback(invocable, message));
                }
            } else {
                this.returnValueHandlers.handleReturnValue(returnValue, returnType, message);
            }
        }
        catch (Exception ex) {
            this.processHandlerMethodException(handlerMethod, ex, message);
        }
        catch (Throwable ex) {
            MessageHandlingException handlingException = new MessageHandlingException(message, "Unexpected handler method invocation error", ex);
            this.processHandlerMethodException(handlerMethod, (Exception)((Object)handlingException), message);
        }
    }

    protected void processHandlerMethodException(HandlerMethod handlerMethod, Exception exception, Message<?> message) {
        InvocableHandlerMethod invocable = this.getExceptionHandlerMethod(handlerMethod, exception);
        if (invocable == null) {
            this.logger.error((Object)"Unhandled exception from message handler method", (Throwable)exception);
            return;
        }
        invocable.setMessageMethodArgumentResolvers(this.argumentResolvers);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Invoking " + invocable.getShortLogMessage()));
        }
        try {
            Throwable cause = exception.getCause();
            Object returnValue = cause != null ? invocable.invoke(message, exception, cause, handlerMethod) : invocable.invoke(message, exception, handlerMethod);
            MethodParameter returnType = invocable.getReturnType();
            if (Void.TYPE == returnType.getParameterType()) {
                return;
            }
            this.returnValueHandlers.handleReturnValue(returnValue, returnType, message);
        }
        catch (Throwable ex2) {
            this.logger.error((Object)"Error while processing handler method exception", ex2);
        }
    }

    @Nullable
    protected InvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        Method method;
        Class<?> beanType;
        AbstractExceptionHandlerMethodResolver resolver;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Searching methods to handle " + exception.getClass().getSimpleName()));
        }
        if ((resolver = this.exceptionHandlerCache.get(beanType = handlerMethod.getBeanType())) == null) {
            resolver = this.createExceptionHandlerMethodResolverFor(beanType);
            this.exceptionHandlerCache.put(beanType, resolver);
        }
        if ((method = resolver.resolveMethod(exception)) != null) {
            return new InvocableHandlerMethod(handlerMethod.getBean(), method);
        }
        for (Map.Entry<MessagingAdviceBean, AbstractExceptionHandlerMethodResolver> entry : this.exceptionHandlerAdviceCache.entrySet()) {
            MessagingAdviceBean advice = entry.getKey();
            if (!advice.isApplicableToBeanType(beanType) || (method = (resolver = entry.getValue()).resolveMethod(exception)) == null) continue;
            return new InvocableHandlerMethod(advice.resolveBean(), method);
        }
        return null;
    }

    protected abstract AbstractExceptionHandlerMethodResolver createExceptionHandlerMethodResolverFor(Class<?> var1);

    public String toString() {
        return this.getClass().getSimpleName() + "[prefixes=" + this.getDestinationPrefixes() + "]";
    }

    private class ReturnValueListenableFutureCallback
    implements ListenableFutureCallback<Object> {
        private final InvocableHandlerMethod handlerMethod;
        private final Message<?> message;

        public ReturnValueListenableFutureCallback(InvocableHandlerMethod handlerMethod, Message<?> message) {
            this.handlerMethod = handlerMethod;
            this.message = message;
        }

        public void onSuccess(@Nullable Object result) {
            try {
                MethodParameter returnType = this.handlerMethod.getAsyncReturnValueType(result);
                AbstractMethodMessageHandler.this.returnValueHandlers.handleReturnValue(result, returnType, this.message);
            }
            catch (Throwable ex) {
                this.handleFailure(ex);
            }
        }

        public void onFailure(Throwable ex) {
            this.handleFailure(ex);
        }

        private void handleFailure(Throwable ex) {
            Exception cause = ex instanceof Exception ? (Exception)ex : new IllegalStateException(ex);
            AbstractMethodMessageHandler.this.processHandlerMethodException(this.handlerMethod, cause, this.message);
        }
    }

    private class MatchComparator
    implements Comparator<Match> {
        private final Comparator<T> comparator;

        public MatchComparator(Comparator<T> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(Match match1, Match match2) {
            return this.comparator.compare(match1.mapping, match2.mapping);
        }
    }

    private class Match {
        private final T mapping;
        private final HandlerMethod handlerMethod;

        public Match(T mapping, HandlerMethod handlerMethod) {
            this.mapping = mapping;
            this.handlerMethod = handlerMethod;
        }

        public String toString() {
            return this.mapping.toString();
        }
    }
}

