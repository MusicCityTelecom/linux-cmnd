/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.aop.Advisor
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.TargetSource
 *  org.springframework.aop.framework.Advised
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor
 *  org.springframework.aop.support.NameMatchMethodPointcut
 *  org.springframework.aop.support.NameMatchMethodPointcutAdvisor
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanDefinitionValidationException
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.GenericTypeResolver
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.core.task.TaskExecutor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.messaging.core.DestinationResolutionException
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.support.CronTrigger
 *  org.springframework.scheduling.support.PeriodicTrigger
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import org.aopalliance.aop.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.IdempotentReceiver;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Reactive;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.MessagePublishingErrorHandler;
import org.springframework.integration.config.annotation.MethodAnnotationPostProcessor;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.context.Orderable;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.AbstractPollingEndpoint;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.ReactiveStreamsConsumer;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.LambdaMessageProcessor;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.integration.handler.ReplyProducingMessageHandlerWrapper;
import org.springframework.integration.handler.advice.HandleMessageAdvice;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractMethodAnnotationPostProcessor<T extends Annotation>
implements MethodAnnotationPostProcessor<T> {
    private static final String UNCHECKED = "unchecked";
    private static final String INPUT_CHANNEL_ATTRIBUTE = "inputChannel";
    private static final String ADVICE_CHAIN_ATTRIBUTE = "adviceChain";
    protected static final String SEND_TIMEOUT_ATTRIBUTE = "sendTimeout";
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected final List<String> messageHandlerAttributes = new ArrayList<String>();
    protected final ConfigurableListableBeanFactory beanFactory;
    protected final BeanDefinitionRegistry definitionRegistry;
    protected final ConversionService conversionService;
    protected final DestinationResolver<MessageChannel> channelResolver;
    protected final Class<T> annotationType;

    public AbstractMethodAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"'beanFactory' must not be null");
        this.messageHandlerAttributes.add(SEND_TIMEOUT_ATTRIBUTE);
        this.beanFactory = beanFactory;
        this.definitionRegistry = (BeanDefinitionRegistry)beanFactory;
        this.conversionService = this.beanFactory.getConversionService() != null ? this.beanFactory.getConversionService() : DefaultConversionService.getSharedInstance();
        this.channelResolver = ChannelResolverUtils.getChannelResolver((BeanFactory)beanFactory);
        this.annotationType = GenericTypeResolver.resolveTypeArgument(this.getClass(), MethodAnnotationPostProcessor.class);
    }

    @Override
    public Object postProcess(Object bean, String beanName, Method method, List<Annotation> annotations) {
        AbstractEndpoint endpoint;
        MessageHandler handler;
        Object sourceHandler = null;
        if (this.beanAnnotationAware() && AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            if (!this.beanFactory.containsBeanDefinition(this.resolveTargetBeanName(method))) {
                this.logger.debug((Object)"Skipping endpoint creation; perhaps due to some '@Conditional' annotation.");
                return null;
            }
            sourceHandler = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
        }
        if (!((handler = this.createHandler(bean, method, annotations)) instanceof ReactiveMessageHandlerAdapter)) {
            AbstractMethodAnnotationPostProcessor.orderable(method, handler);
            this.producerOrRouter(annotations, handler);
            if (!handler.equals(sourceHandler)) {
                handler = this.registerHandlerBean(beanName, method, handler);
            }
            handler = this.annotated(method, handler);
            handler = this.adviceChain(beanName, annotations, handler);
        }
        if ((endpoint = this.createEndpoint(handler, method, annotations)) != null) {
            return endpoint;
        }
        return handler;
    }

    private MessageHandler registerHandlerBean(String beanName, Method method, MessageHandler handler) {
        String handlerBeanName = this.generateHandlerBeanName(beanName, method);
        if (handler instanceof ReplyProducingMessageHandlerWrapper && StringUtils.hasText((String)MessagingAnnotationUtils.endpointIdValue(method))) {
            handlerBeanName = handlerBeanName + ".wrapper";
        }
        if (handler instanceof IntegrationObjectSupport) {
            ((IntegrationObjectSupport)handler).setComponentName(handlerBeanName.substring(0, handlerBeanName.indexOf(".handler")));
        }
        this.definitionRegistry.registerBeanDefinition(handlerBeanName, (BeanDefinition)new RootBeanDefinition(MessageHandler.class, () -> handler));
        return (MessageHandler)this.beanFactory.getBean(handlerBeanName, MessageHandler.class);
    }

    private void producerOrRouter(List<Annotation> annotations, MessageHandler handler) {
        String resolvedValue;
        String sendTimeout;
        if ((handler instanceof AbstractMessageProducingHandler || handler instanceof AbstractMessageRouter) && (sendTimeout = MessagingAnnotationUtils.resolveAttribute(annotations, SEND_TIMEOUT_ATTRIBUTE, String.class)) != null && (resolvedValue = this.beanFactory.resolveEmbeddedValue(sendTimeout)) != null) {
            long value = Long.parseLong(resolvedValue);
            if (handler instanceof AbstractMessageProducingHandler) {
                ((AbstractMessageProducingHandler)handler).setSendTimeout(value);
            } else {
                ((AbstractMessageRouter)handler).setSendTimeout(value);
            }
        }
    }

    private MessageHandler annotated(Method method, MessageHandler handlerArg) {
        MessageHandler handler = handlerArg;
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)IdempotentReceiver.class.getName()) && !AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            String[] interceptors;
            for (String interceptor : interceptors = ((IdempotentReceiver)AnnotationUtils.getAnnotation((Method)method, IdempotentReceiver.class)).value()) {
                DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
                advisor.setAdviceBeanName(interceptor);
                NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
                pointcut.setMappedName("handleMessage");
                advisor.setPointcut((Pointcut)pointcut);
                advisor.setBeanFactory((BeanFactory)this.beanFactory);
                if (handler instanceof Advised) {
                    ((Advised)handler).addAdvisor((Advisor)advisor);
                    continue;
                }
                ProxyFactory proxyFactory = new ProxyFactory((Object)handler);
                proxyFactory.addAdvisor((Advisor)advisor);
                handler = (MessageHandler)proxyFactory.getProxy(this.beanFactory.getBeanClassLoader());
            }
        }
        return handler;
    }

    private MessageHandler adviceChain(String beanName, List<Annotation> annotations, MessageHandler handlerArg) {
        MessageHandler handler = handlerArg;
        List<Advice> adviceChain = this.extractAdviceChain(beanName, annotations);
        if (!CollectionUtils.isEmpty(adviceChain) && handler instanceof AbstractReplyProducingMessageHandler) {
            ((AbstractReplyProducingMessageHandler)handler).setAdviceChain(adviceChain);
        }
        if (!CollectionUtils.isEmpty(adviceChain)) {
            for (Advice advice : adviceChain) {
                if (!(advice instanceof HandleMessageAdvice)) continue;
                NameMatchMethodPointcutAdvisor handlerAdvice = new NameMatchMethodPointcutAdvisor(advice);
                handlerAdvice.addMethodName("handleMessage");
                if (handler instanceof Advised) {
                    ((Advised)handler).addAdvisor((Advisor)handlerAdvice);
                    continue;
                }
                ProxyFactory proxyFactory = new ProxyFactory((Object)handler);
                proxyFactory.addAdvisor((Advisor)handlerAdvice);
                handler = (MessageHandler)proxyFactory.getProxy(this.beanFactory.getBeanClassLoader());
            }
        }
        return handler;
    }

    @Override
    public boolean shouldCreateEndpoint(Method method, List<Annotation> annotations) {
        String inputChannel = MessagingAnnotationUtils.resolveAttribute(annotations, this.getInputChannelAttribute(), String.class);
        boolean createEndpoint = StringUtils.hasText((String)inputChannel);
        if (!createEndpoint && this.beanAnnotationAware()) {
            boolean isBean = AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName());
            Assert.isTrue((!isBean ? 1 : 0) != 0, () -> "A channel name in '" + this.getInputChannelAttribute() + "' is required when " + this.annotationType + " is used on '@Bean' methods.");
        }
        return createEndpoint;
    }

    protected String getInputChannelAttribute() {
        return INPUT_CHANNEL_ATTRIBUTE;
    }

    protected boolean beanAnnotationAware() {
        return true;
    }

    protected List<Advice> extractAdviceChain(String beanName, List<Annotation> annotations) {
        ArrayList<Advice> adviceChain = null;
        String[] adviceChainNames = MessagingAnnotationUtils.resolveAttribute(annotations, ADVICE_CHAIN_ATTRIBUTE, String[].class);
        if (adviceChainNames != null && adviceChainNames.length > 0) {
            adviceChain = new ArrayList<Advice>();
            for (String adviceChainName : adviceChainNames) {
                Object adviceChainBean = this.beanFactory.getBean(adviceChainName);
                if (adviceChainBean instanceof Advice) {
                    adviceChain.add((Advice)adviceChainBean);
                    continue;
                }
                if (adviceChainBean instanceof Advice[]) {
                    Collections.addAll(adviceChain, (Advice[])adviceChainBean);
                    continue;
                }
                if (adviceChainBean instanceof Collection) {
                    Collection adviceChainEntries = (Collection)adviceChainBean;
                    adviceChain.addAll(adviceChainEntries);
                    continue;
                }
                throw new IllegalArgumentException("Invalid advice chain type:" + adviceChainName.getClass().getName() + " for bean '" + beanName + "'");
            }
        }
        return adviceChain;
    }

    protected AbstractEndpoint createEndpoint(MessageHandler handler, Method method, List<Annotation> annotations) {
        AbstractEndpoint endpoint = null;
        String inputChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, this.getInputChannelAttribute(), String.class);
        if (StringUtils.hasText((String)inputChannelName)) {
            MessageChannel inputChannel;
            try {
                inputChannel = (MessageChannel)this.channelResolver.resolveDestination(inputChannelName);
            }
            catch (DestinationResolutionException e) {
                if (e.getCause() instanceof NoSuchBeanDefinitionException) {
                    this.definitionRegistry.registerBeanDefinition(inputChannelName, (BeanDefinition)new RootBeanDefinition(DirectChannel.class, DirectChannel::new));
                    inputChannel = (MessageChannel)this.beanFactory.getBean(inputChannelName, MessageChannel.class);
                }
                throw e;
            }
            Assert.notNull((Object)inputChannel, () -> "failed to resolve inputChannel '" + inputChannelName + "'");
            endpoint = this.doCreateEndpoint(handler, inputChannel, annotations);
        }
        return endpoint;
    }

    protected AbstractEndpoint doCreateEndpoint(MessageHandler handler, MessageChannel inputChannel, List<Annotation> annotations) {
        Object[] pollers = MessagingAnnotationUtils.resolveAttribute(annotations, "poller", Poller[].class);
        Reactive reactive = MessagingAnnotationUtils.resolveAttribute(annotations, "reactive", Reactive.class);
        boolean reactiveProvided = reactive != null && !"\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n".equals(reactive.value());
        Assert.state((!reactiveProvided || ObjectUtils.isEmpty((Object[])pollers) ? 1 : 0) != 0, (String)"The 'poller' and 'reactive' are mutually exclusive.");
        if (inputChannel instanceof Publisher || handler instanceof ReactiveMessageHandlerAdapter || reactiveProvided) {
            return this.reactiveStreamsConsumer(inputChannel, handler, reactiveProvided ? reactive : null);
        }
        if (inputChannel instanceof SubscribableChannel) {
            Assert.state((boolean)ObjectUtils.isEmpty((Object[])pollers), () -> "A '@Poller' should not be specified for Annotation-based endpoint, since '" + inputChannel + "' is a SubscribableChannel (not pollable).");
            return new EventDrivenConsumer((SubscribableChannel)inputChannel, handler);
        }
        if (inputChannel instanceof PollableChannel) {
            return this.pollingConsumer(inputChannel, handler, (Poller[])pollers);
        }
        throw new IllegalArgumentException("Unsupported 'inputChannel' type: '" + inputChannel.getClass().getName() + "'. Must be one of 'SubscribableChannel', 'PollableChannel' or 'ReactiveStreamsSubscribableChannel'");
    }

    private ReactiveStreamsConsumer reactiveStreamsConsumer(MessageChannel channel, MessageHandler handler, Reactive reactive) {
        String functionBeanName;
        ReactiveStreamsConsumer reactiveStreamsConsumer = handler instanceof ReactiveMessageHandlerAdapter ? new ReactiveStreamsConsumer(channel, ((ReactiveMessageHandlerAdapter)handler).getDelegate()) : new ReactiveStreamsConsumer(channel, handler);
        if (reactive != null && StringUtils.hasText((String)(functionBeanName = reactive.value()))) {
            Function reactiveCustomizer = (Function)this.beanFactory.getBean(functionBeanName, Function.class);
            reactiveStreamsConsumer.setReactiveCustomizer(reactiveCustomizer);
        }
        return reactiveStreamsConsumer;
    }

    private PollingConsumer pollingConsumer(MessageChannel inputChannel, MessageHandler handler, Poller[] pollers) {
        PollingConsumer pollingConsumer = new PollingConsumer((PollableChannel)inputChannel, handler);
        this.configurePollingEndpoint(pollingConsumer, pollers);
        return pollingConsumer;
    }

    protected void configurePollingEndpoint(AbstractPollingEndpoint pollingEndpoint, Poller[] pollers) {
        PollerMetadata pollerMetadata;
        if (!ObjectUtils.isEmpty((Object[])pollers)) {
            Assert.state((pollers.length == 1 ? 1 : 0) != 0, (String)"The 'poller' for an Annotation-based endpoint can have only one '@Poller'.");
            Poller poller = pollers[0];
            String ref = poller.value();
            String triggerRef = poller.trigger();
            String executorRef = poller.taskExecutor();
            String fixedDelayValue = this.beanFactory.resolveEmbeddedValue(poller.fixedDelay());
            String fixedRateValue = this.beanFactory.resolveEmbeddedValue(poller.fixedRate());
            String maxMessagesPerPollValue = this.beanFactory.resolveEmbeddedValue(poller.maxMessagesPerPoll());
            String cron = this.beanFactory.resolveEmbeddedValue(poller.cron());
            String errorChannel = this.beanFactory.resolveEmbeddedValue(poller.errorChannel());
            String receiveTimeout = this.beanFactory.resolveEmbeddedValue(poller.receiveTimeout());
            if (StringUtils.hasText((String)ref)) {
                Assert.state((!StringUtils.hasText((String)triggerRef) && !StringUtils.hasText((String)executorRef) && !StringUtils.hasText((String)cron) && !StringUtils.hasText((String)fixedDelayValue) && !StringUtils.hasText((String)fixedRateValue) && !StringUtils.hasText((String)maxMessagesPerPollValue) ? 1 : 0) != 0, (String)"The '@Poller' 'ref' attribute is mutually exclusive with other attributes.");
                pollerMetadata = (PollerMetadata)this.beanFactory.getBean(ref, PollerMetadata.class);
            } else {
                pollerMetadata = this.configurePoller(pollingEndpoint, triggerRef, executorRef, fixedDelayValue, fixedRateValue, maxMessagesPerPollValue, cron, errorChannel, receiveTimeout);
            }
        } else {
            pollerMetadata = PollerMetadata.getDefaultPollerMetadata((BeanFactory)this.beanFactory);
            Assert.notNull((Object)pollerMetadata, (String)"No poller has been defined for Annotation-based endpoint, and no default poller is available within the context.");
        }
        pollingEndpoint.setTaskExecutor(pollerMetadata.getTaskExecutor());
        pollingEndpoint.setTrigger(pollerMetadata.getTrigger());
        pollingEndpoint.setAdviceChain(pollerMetadata.getAdviceChain());
        pollingEndpoint.setMaxMessagesPerPoll(pollerMetadata.getMaxMessagesPerPoll());
        pollingEndpoint.setErrorHandler(pollerMetadata.getErrorHandler());
        if (pollingEndpoint instanceof PollingConsumer) {
            ((PollingConsumer)pollingEndpoint).setReceiveTimeout(pollerMetadata.getReceiveTimeout());
        }
        pollingEndpoint.setTransactionSynchronizationFactory(pollerMetadata.getTransactionSynchronizationFactory());
    }

    private PollerMetadata configurePoller(AbstractPollingEndpoint pollingEndpoint, String triggerRef, String executorRef, String fixedDelayValue, String fixedRateValue, String maxMessagesPerPollValue, String cron, String errorChannel, String receiveTimeout) {
        PollerMetadata pollerMetadata = new PollerMetadata();
        if (StringUtils.hasText((String)maxMessagesPerPollValue)) {
            pollerMetadata.setMaxMessagesPerPoll(Long.parseLong(maxMessagesPerPollValue));
        } else if (pollingEndpoint instanceof SourcePollingChannelAdapter) {
            pollerMetadata.setMaxMessagesPerPoll(1L);
        }
        if (StringUtils.hasText((String)executorRef)) {
            pollerMetadata.setTaskExecutor((Executor)this.beanFactory.getBean(executorRef, TaskExecutor.class));
        }
        this.trigger(triggerRef, fixedDelayValue, fixedRateValue, cron, pollerMetadata);
        if (StringUtils.hasText((String)errorChannel)) {
            MessagePublishingErrorHandler errorHandler = new MessagePublishingErrorHandler();
            errorHandler.setDefaultErrorChannelName(errorChannel);
            errorHandler.setBeanFactory((BeanFactory)this.beanFactory);
            pollerMetadata.setErrorHandler(errorHandler);
        }
        if (StringUtils.hasText((String)receiveTimeout)) {
            pollerMetadata.setReceiveTimeout(Long.parseLong(receiveTimeout));
        }
        return pollerMetadata;
    }

    private void trigger(String triggerRef, String fixedDelayValue, String fixedRateValue, String cron, PollerMetadata pollerMetadata) {
        Trigger trigger2 = null;
        if (StringUtils.hasText((String)triggerRef)) {
            Assert.state((!StringUtils.hasText((String)cron) && !StringUtils.hasText((String)fixedDelayValue) && !StringUtils.hasText((String)fixedRateValue) ? 1 : 0) != 0, (String)"The '@Poller' 'trigger' attribute is mutually exclusive with other attributes.");
            trigger2 = (Trigger)this.beanFactory.getBean(triggerRef, Trigger.class);
        } else if (StringUtils.hasText((String)cron)) {
            Assert.state((!StringUtils.hasText((String)fixedDelayValue) && !StringUtils.hasText((String)fixedRateValue) ? 1 : 0) != 0, (String)"The '@Poller' 'cron' attribute is mutually exclusive with other attributes.");
            trigger2 = new CronTrigger(cron);
        } else if (StringUtils.hasText((String)fixedDelayValue)) {
            Assert.state((!StringUtils.hasText((String)fixedRateValue) ? 1 : 0) != 0, (String)"The '@Poller' 'fixedDelay' attribute is mutually exclusive with other attributes.");
            trigger2 = new PeriodicTrigger(Long.parseLong(fixedDelayValue));
        } else if (StringUtils.hasText((String)fixedRateValue)) {
            trigger2 = new PeriodicTrigger(Long.parseLong(fixedRateValue));
            ((PeriodicTrigger)trigger2).setFixedRate(true);
        }
        pollerMetadata.setTrigger(trigger2);
    }

    protected String generateHandlerBeanName(String originalBeanName, Method method) {
        String name = MessagingAnnotationUtils.endpointIdValue(method);
        if (!StringUtils.hasText((String)name)) {
            String baseName;
            name = baseName = originalBeanName + "." + method.getName() + "." + ClassUtils.getShortNameAsProperty(this.annotationType);
            int count = 1;
            while (this.beanFactory.containsBean(name)) {
                name = baseName + '#' + ++count;
            }
        }
        return name + ".handler";
    }

    protected void setOutputChannelIfPresent(List<Annotation> annotations, AbstractReplyProducingMessageHandler handler) {
        String outputChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, "outputChannel", String.class);
        if (StringUtils.hasText((String)outputChannelName)) {
            handler.setOutputChannelName(outputChannelName);
        }
    }

    protected Object resolveTargetBeanFromMethodWithBeanAnnotation(Method method) {
        String id = this.resolveTargetBeanName(method);
        return this.beanFactory.getBean(id);
    }

    protected String resolveTargetBeanName(Method method) {
        Object id = method.getName();
        Object[] names = ((Bean)AnnotationUtils.getAnnotation((Method)method, Bean.class)).name();
        if (!ObjectUtils.isEmpty((Object[])names)) {
            id = names[0];
        }
        return id;
    }

    protected <H> H extractTypeIfPossible(@Nullable Object targetObject, Class<H> expectedType) {
        if (targetObject == null) {
            return null;
        }
        if (expectedType.isAssignableFrom(targetObject.getClass())) {
            return (H)targetObject;
        }
        if (targetObject instanceof Advised) {
            TargetSource targetSource = ((Advised)targetObject).getTargetSource();
            try {
                return this.extractTypeIfPossible(targetSource.getTarget(), expectedType);
            }
            catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

    protected void checkMessageHandlerAttributes(String handlerBeanName, List<Annotation> annotations) {
        for (String attribute : this.messageHandlerAttributes) {
            for (Annotation annotation : annotations) {
                Object value = AnnotationUtils.getValue((Annotation)annotation, (String)attribute);
                if (!MessagingAnnotationUtils.hasValue(value)) continue;
                throw new BeanDefinitionValidationException("The MessageHandler [" + handlerBeanName + "] can not be populated because of ambiguity with annotation attributes " + this.messageHandlerAttributes + " which are not allowed when an integration annotation is used with a @Bean definition for a MessageHandler.\nThe attribute causing the ambiguity is: [" + attribute + "].\nUse the appropriate setter on the MessageHandler directly when configuring an endpoint this way.");
            }
        }
    }

    protected boolean resolveAttributeToBoolean(String attribute) {
        return Boolean.parseBoolean(this.beanFactory.resolveEmbeddedValue(attribute));
    }

    @Nullable
    protected MessageProcessor<?> buildLambdaMessageProcessorForBeanMethod(Method method, Object target) {
        if ((target instanceof Function || target instanceof Consumer) && org.springframework.integration.util.ClassUtils.isLambda(target.getClass()) || org.springframework.integration.util.ClassUtils.isKotlinFunction1(target.getClass())) {
            ResolvableType methodReturnType = ResolvableType.forMethodReturnType((Method)method);
            Class expectedPayloadType = methodReturnType.getGeneric(new int[]{0}).toClass();
            return new LambdaMessageProcessor(target, expectedPayloadType);
        }
        return null;
    }

    private static void orderable(Method method, MessageHandler handler) {
        Order orderAnnotation;
        if (handler instanceof Orderable && (orderAnnotation = (Order)AnnotationUtils.findAnnotation((Method)method, Order.class)) != null) {
            ((Orderable)handler).setOrder(orderAnnotation.value());
        }
    }

    protected abstract MessageHandler createHandler(Object var1, Method var2, List<Annotation> var3);
}

