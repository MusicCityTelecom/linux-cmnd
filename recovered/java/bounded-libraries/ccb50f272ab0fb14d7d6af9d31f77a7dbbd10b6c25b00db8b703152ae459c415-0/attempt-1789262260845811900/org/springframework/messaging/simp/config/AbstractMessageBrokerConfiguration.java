/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.task.TaskExecutor
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.StringUtils
 *  org.springframework.validation.Errors
 *  org.springframework.validation.Validator
 */
package org.springframework.messaging.simp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.converter.JsonbMessageConverter;
import org.springframework.messaging.converter.KotlinSerializationJsonMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.TaskExecutorRegistration;
import org.springframework.messaging.simp.stomp.StompBrokerRelayMessageHandler;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.MultiServerUserRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationMessageHandler;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.simp.user.UserRegistryMessageHandler;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.messaging.support.ImmutableMessageChannelInterceptor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractMessageBrokerConfiguration
implements ApplicationContextAware {
    private static final String MVC_VALIDATOR_NAME = "mvcValidator";
    private static final boolean jackson2Present;
    private static final boolean gsonPresent;
    private static final boolean jsonbPresent;
    private static final boolean kotlinSerializationJsonPresent;
    @Nullable
    private ApplicationContext applicationContext;
    @Nullable
    private ChannelRegistration clientInboundChannelRegistration;
    @Nullable
    private ChannelRegistration clientOutboundChannelRegistration;
    @Nullable
    private MessageBrokerRegistry brokerRegistry;

    protected AbstractMessageBrokerConfiguration() {
    }

    public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Nullable
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Bean
    public AbstractSubscribableChannel clientInboundChannel(@Qualifier(value="clientInboundChannelExecutor") TaskExecutor executor) {
        ExecutorSubscribableChannel channel = new ExecutorSubscribableChannel((Executor)executor);
        channel.setLogger(SimpLogging.forLog(channel.getLogger()));
        ChannelRegistration reg = this.getClientInboundChannelRegistration();
        if (reg.hasInterceptors()) {
            channel.setInterceptors(reg.getInterceptors());
        }
        return channel;
    }

    @Bean
    public TaskExecutor clientInboundChannelExecutor() {
        TaskExecutorRegistration reg = this.getClientInboundChannelRegistration().taskExecutor();
        ThreadPoolTaskExecutor executor = reg.getTaskExecutor();
        executor.setThreadNamePrefix("clientInboundChannel-");
        return executor;
    }

    protected final ChannelRegistration getClientInboundChannelRegistration() {
        if (this.clientInboundChannelRegistration == null) {
            ChannelRegistration registration = new ChannelRegistration();
            this.configureClientInboundChannel(registration);
            registration.interceptors(new ImmutableMessageChannelInterceptor());
            this.clientInboundChannelRegistration = registration;
        }
        return this.clientInboundChannelRegistration;
    }

    protected void configureClientInboundChannel(ChannelRegistration registration) {
    }

    @Bean
    public AbstractSubscribableChannel clientOutboundChannel(@Qualifier(value="clientOutboundChannelExecutor") TaskExecutor executor) {
        ExecutorSubscribableChannel channel = new ExecutorSubscribableChannel((Executor)executor);
        channel.setLogger(SimpLogging.forLog(channel.getLogger()));
        ChannelRegistration reg = this.getClientOutboundChannelRegistration();
        if (reg.hasInterceptors()) {
            channel.setInterceptors(reg.getInterceptors());
        }
        return channel;
    }

    @Bean
    public TaskExecutor clientOutboundChannelExecutor() {
        TaskExecutorRegistration reg = this.getClientOutboundChannelRegistration().taskExecutor();
        ThreadPoolTaskExecutor executor = reg.getTaskExecutor();
        executor.setThreadNamePrefix("clientOutboundChannel-");
        return executor;
    }

    protected final ChannelRegistration getClientOutboundChannelRegistration() {
        if (this.clientOutboundChannelRegistration == null) {
            ChannelRegistration registration = new ChannelRegistration();
            this.configureClientOutboundChannel(registration);
            registration.interceptors(new ImmutableMessageChannelInterceptor());
            this.clientOutboundChannelRegistration = registration;
        }
        return this.clientOutboundChannelRegistration;
    }

    protected void configureClientOutboundChannel(ChannelRegistration registration) {
    }

    @Bean
    public AbstractSubscribableChannel brokerChannel(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, @Qualifier(value="brokerChannelExecutor") TaskExecutor executor) {
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        ChannelRegistration registration = registry.getBrokerChannelRegistration();
        ExecutorSubscribableChannel channel = registration.hasTaskExecutor() ? new ExecutorSubscribableChannel((Executor)executor) : new ExecutorSubscribableChannel();
        registration.interceptors(new ImmutableMessageChannelInterceptor());
        channel.setLogger(SimpLogging.forLog(channel.getLogger()));
        channel.setInterceptors(registration.getInterceptors());
        return channel;
    }

    @Bean
    public TaskExecutor brokerChannelExecutor(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel) {
        ThreadPoolTaskExecutor executor;
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        ChannelRegistration registration = registry.getBrokerChannelRegistration();
        if (registration.hasTaskExecutor()) {
            executor = registration.taskExecutor().getTaskExecutor();
        } else {
            executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(0);
            executor.setMaxPoolSize(1);
            executor.setQueueCapacity(0);
        }
        executor.setThreadNamePrefix("brokerChannel-");
        return executor;
    }

    protected final MessageBrokerRegistry getBrokerRegistry(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel) {
        if (this.brokerRegistry == null) {
            MessageBrokerRegistry registry = new MessageBrokerRegistry(clientInboundChannel, clientOutboundChannel);
            this.configureMessageBroker(registry);
            this.brokerRegistry = registry;
        }
        return this.brokerRegistry;
    }

    protected void configureMessageBroker(MessageBrokerRegistry registry) {
    }

    @Nullable
    public final PathMatcher getPathMatcher(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel) {
        return this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel).getPathMatcher();
    }

    @Bean
    public SimpAnnotationMethodMessageHandler simpAnnotationMethodMessageHandler(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, SimpMessagingTemplate brokerMessagingTemplate, CompositeMessageConverter brokerMessageConverter) {
        SimpAnnotationMethodMessageHandler handler = this.createAnnotationMethodMessageHandler(clientInboundChannel, clientOutboundChannel, brokerMessagingTemplate);
        MessageBrokerRegistry brokerRegistry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        handler.setDestinationPrefixes(brokerRegistry.getApplicationDestinationPrefixes());
        handler.setMessageConverter(brokerMessageConverter);
        handler.setValidator(this.simpValidator());
        ArrayList<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        this.addArgumentResolvers(argumentResolvers);
        handler.setCustomArgumentResolvers(argumentResolvers);
        ArrayList<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();
        this.addReturnValueHandlers(returnValueHandlers);
        handler.setCustomReturnValueHandlers(returnValueHandlers);
        PathMatcher pathMatcher = brokerRegistry.getPathMatcher();
        if (pathMatcher != null) {
            handler.setPathMatcher(pathMatcher);
        }
        return handler;
    }

    protected SimpAnnotationMethodMessageHandler createAnnotationMethodMessageHandler(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, SimpMessagingTemplate brokerMessagingTemplate) {
        return new SimpAnnotationMethodMessageHandler(clientInboundChannel, clientOutboundChannel, brokerMessagingTemplate);
    }

    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    }

    @Bean
    @Nullable
    public AbstractBrokerMessageHandler simpleBrokerMessageHandler(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, AbstractSubscribableChannel brokerChannel, UserDestinationResolver userDestinationResolver) {
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        SimpleBrokerMessageHandler handler = registry.getSimpleBroker(brokerChannel);
        if (handler == null) {
            return null;
        }
        this.updateUserDestinationResolver(handler, userDestinationResolver, registry.getUserDestinationPrefix());
        return handler;
    }

    private void updateUserDestinationResolver(AbstractBrokerMessageHandler handler, UserDestinationResolver userDestinationResolver, @Nullable String userDestinationPrefix) {
        Collection<String> prefixes = handler.getDestinationPrefixes();
        if (!prefixes.isEmpty() && !prefixes.iterator().next().startsWith("/")) {
            ((DefaultUserDestinationResolver)userDestinationResolver).setRemoveLeadingSlash(true);
        }
        if (StringUtils.hasText((String)userDestinationPrefix)) {
            handler.setUserDestinationPredicate(destination -> destination.startsWith(userDestinationPrefix));
        }
    }

    @Bean
    @Nullable
    public AbstractBrokerMessageHandler stompBrokerRelayMessageHandler(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, AbstractSubscribableChannel brokerChannel, UserDestinationMessageHandler userDestinationMessageHandler, @Nullable MessageHandler userRegistryMessageHandler, UserDestinationResolver userDestinationResolver) {
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        StompBrokerRelayMessageHandler handler = registry.getStompBrokerRelay(brokerChannel);
        if (handler == null) {
            return null;
        }
        HashMap<String, MessageHandler> subscriptions = new HashMap<String, MessageHandler>(4);
        String destination = registry.getUserDestinationBroadcast();
        if (destination != null) {
            subscriptions.put(destination, userDestinationMessageHandler);
        }
        if ((destination = registry.getUserRegistryBroadcast()) != null) {
            subscriptions.put(destination, userRegistryMessageHandler);
        }
        handler.setSystemSubscriptions(subscriptions);
        this.updateUserDestinationResolver(handler, userDestinationResolver, registry.getUserDestinationPrefix());
        return handler;
    }

    @Bean
    public UserDestinationMessageHandler userDestinationMessageHandler(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, AbstractSubscribableChannel brokerChannel, UserDestinationResolver userDestinationResolver) {
        UserDestinationMessageHandler handler = new UserDestinationMessageHandler(clientInboundChannel, brokerChannel, userDestinationResolver);
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        String destination = registry.getUserDestinationBroadcast();
        if (destination != null) {
            handler.setBroadcastDestination(destination);
        }
        return handler;
    }

    @Bean
    @Nullable
    public MessageHandler userRegistryMessageHandler(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, SimpUserRegistry userRegistry, SimpMessagingTemplate brokerMessagingTemplate, @Qualifier(value="messageBrokerTaskScheduler") TaskScheduler scheduler) {
        MessageBrokerRegistry brokerRegistry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        if (brokerRegistry.getUserRegistryBroadcast() == null) {
            return null;
        }
        Assert.isInstanceOf(MultiServerUserRegistry.class, (Object)userRegistry, (String)"MultiServerUserRegistry required");
        return new UserRegistryMessageHandler((MultiServerUserRegistry)userRegistry, brokerMessagingTemplate, brokerRegistry.getUserRegistryBroadcast(), scheduler);
    }

    @Bean(name={"messageBrokerTaskScheduler", "messageBrokerSockJsTaskScheduler"})
    public TaskScheduler messageBrokerTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("MessageBroker-");
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setRemoveOnCancelPolicy(true);
        return scheduler;
    }

    @Bean
    public SimpMessagingTemplate brokerMessagingTemplate(AbstractSubscribableChannel brokerChannel, AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel, CompositeMessageConverter brokerMessageConverter) {
        SimpMessagingTemplate template = new SimpMessagingTemplate(brokerChannel);
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        String prefix = registry.getUserDestinationPrefix();
        if (prefix != null) {
            template.setUserDestinationPrefix(prefix);
        }
        template.setMessageConverter(brokerMessageConverter);
        return template;
    }

    @Bean
    public CompositeMessageConverter brokerMessageConverter() {
        ArrayList<MessageConverter> converters = new ArrayList<MessageConverter>();
        boolean registerDefaults = this.configureMessageConverters(converters);
        if (registerDefaults) {
            converters.add(new StringMessageConverter());
            converters.add(new ByteArrayMessageConverter());
            if (jackson2Present) {
                converters.add(this.createJacksonConverter());
            } else if (gsonPresent) {
                converters.add(new GsonMessageConverter());
            } else if (jsonbPresent) {
                converters.add(new JsonbMessageConverter());
            } else if (kotlinSerializationJsonPresent) {
                converters.add(new KotlinSerializationJsonMessageConverter());
            }
        }
        return new CompositeMessageConverter(converters);
    }

    protected MappingJackson2MessageConverter createJacksonConverter() {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setContentTypeResolver(resolver);
        return converter;
    }

    protected boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }

    @Bean
    public UserDestinationResolver userDestinationResolver(SimpUserRegistry userRegistry, AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel) {
        DefaultUserDestinationResolver resolver = new DefaultUserDestinationResolver(userRegistry);
        MessageBrokerRegistry registry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        String prefix = registry.getUserDestinationPrefix();
        if (prefix != null) {
            resolver.setUserDestinationPrefix(prefix);
        }
        return resolver;
    }

    @Bean
    public SimpUserRegistry userRegistry(AbstractSubscribableChannel clientInboundChannel, AbstractSubscribableChannel clientOutboundChannel) {
        SimpUserRegistry userRegistry = this.createLocalUserRegistry();
        MessageBrokerRegistry brokerRegistry = this.getBrokerRegistry(clientInboundChannel, clientOutboundChannel);
        if (userRegistry == null) {
            userRegistry = this.createLocalUserRegistry(brokerRegistry.getUserRegistryOrder());
        }
        boolean broadcast = brokerRegistry.getUserRegistryBroadcast() != null;
        return broadcast ? new MultiServerUserRegistry(userRegistry) : userRegistry;
    }

    @Deprecated
    @Nullable
    protected SimpUserRegistry createLocalUserRegistry() {
        return null;
    }

    protected abstract SimpUserRegistry createLocalUserRegistry(@Nullable Integer var1);

    protected Validator simpValidator() {
        Validator validator = this.getValidator();
        if (validator == null) {
            if (this.applicationContext != null && this.applicationContext.containsBean(MVC_VALIDATOR_NAME)) {
                validator = (Validator)this.applicationContext.getBean(MVC_VALIDATOR_NAME, Validator.class);
            } else if (ClassUtils.isPresent((String)"javax.validation.Validator", (ClassLoader)this.getClass().getClassLoader())) {
                Class clazz;
                try {
                    String className = "org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean";
                    clazz = ClassUtils.forName((String)className, (ClassLoader)AbstractMessageBrokerConfiguration.class.getClassLoader());
                }
                catch (Throwable ex) {
                    throw new BeanInitializationException("Could not find default validator class", ex);
                }
                validator = (Validator)BeanUtils.instantiateClass((Class)clazz);
            } else {
                validator = new Validator(){

                    public boolean supports(Class<?> clazz) {
                        return false;
                    }

                    public void validate(@Nullable Object target, Errors errors) {
                    }
                };
            }
        }
        return validator;
    }

    @Nullable
    public Validator getValidator() {
        return null;
    }

    static {
        ClassLoader classLoader = AbstractMessageBrokerConfiguration.class.getClassLoader();
        jackson2Present = ClassUtils.isPresent((String)"com.fasterxml.jackson.databind.ObjectMapper", (ClassLoader)classLoader) && ClassUtils.isPresent((String)"com.fasterxml.jackson.core.JsonGenerator", (ClassLoader)classLoader);
        gsonPresent = ClassUtils.isPresent((String)"com.google.gson.Gson", (ClassLoader)classLoader);
        jsonbPresent = ClassUtils.isPresent((String)"javax.json.bind.Jsonb", (ClassLoader)classLoader);
        kotlinSerializationJsonPresent = ClassUtils.isPresent((String)"kotlinx.serialization.json.Json", (ClassLoader)classLoader);
    }
}

