/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.reactivestreams.Subscription
 *  org.springframework.aop.Advisor
 *  org.springframework.aop.framework.Advised
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.aop.support.NameMatchMethodPointcutAdvisor
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.core.task.SyncTaskExecutor
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.TriggerContext
 *  org.springframework.scheduling.support.PeriodicTrigger
 *  org.springframework.scheduling.support.SimpleTriggerContext
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ErrorHandler
 *  org.springframework.util.ReflectionUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.integration.endpoint;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import org.aopalliance.aop.Advice;
import org.reactivestreams.Subscription;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.integration.aop.ReceiveMessageAdvice;
import org.springframework.integration.channel.ChannelUtils;
import org.springframework.integration.channel.MessagePublishingErrorHandler;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.support.MessagingExceptionWrapper;
import org.springframework.integration.transaction.IntegrationResourceHolder;
import org.springframework.integration.transaction.IntegrationResourceHolderSynchronization;
import org.springframework.integration.transaction.PassThroughTransactionSynchronizationFactory;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.integration.util.ErrorHandlingTaskExecutor;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ErrorHandler;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public abstract class AbstractPollingEndpoint
extends AbstractEndpoint
implements BeanClassLoaderAware {
    public static final long DEFAULT_POLLING_PERIOD = 10L;
    private final Collection<Advice> appliedAdvices = new HashSet<Advice>();
    private final Object initializationMonitor = new Object();
    private Executor taskExecutor = new SyncTaskExecutor();
    private boolean syncExecutor = true;
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private Trigger trigger = new PeriodicTrigger(10L);
    private ErrorHandler errorHandler;
    private boolean errorHandlerIsDefault;
    private List<Advice> adviceChain;
    private TransactionSynchronizationFactory transactionSynchronizationFactory;
    private volatile long maxMessagesPerPoll = -1L;
    private volatile Callable<Message<?>> pollingTask;
    private volatile Flux<Message<?>> pollingFlux;
    private volatile Subscription subscription;
    private volatile ScheduledFuture<?> runningTask;
    private volatile boolean initialized;

    public AbstractPollingEndpoint() {
        this.setPhase(0x3FFFFFFF);
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor != null ? taskExecutor : new SyncTaskExecutor();
        this.syncExecutor = this.taskExecutor instanceof SyncTaskExecutor || this.taskExecutor instanceof ErrorHandlingTaskExecutor && ((ErrorHandlingTaskExecutor)((Object)this.taskExecutor)).isSyncExecutor();
    }

    protected Executor getTaskExecutor() {
        return this.taskExecutor;
    }

    protected boolean isSyncExecutor() {
        return this.syncExecutor;
    }

    public void setTrigger(Trigger trigger2) {
        this.trigger = trigger2 != null ? trigger2 : new PeriodicTrigger(10L);
    }

    public void setAdviceChain(List<Advice> adviceChain) {
        this.adviceChain = adviceChain;
    }

    @ManagedAttribute
    public void setMaxMessagesPerPoll(long maxMessagesPerPoll) {
        this.maxMessagesPerPoll = maxMessagesPerPoll;
    }

    public long getMaxMessagesPerPoll() {
        return this.maxMessagesPerPoll;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public void setTransactionSynchronizationFactory(TransactionSynchronizationFactory transactionSynchronizationFactory) {
        this.transactionSynchronizationFactory = transactionSynchronizationFactory;
    }

    public MessageChannel getDefaultErrorChannel() {
        if (!this.errorHandlerIsDefault && this.errorHandler instanceof MessagePublishingErrorHandler) {
            return ((MessagePublishingErrorHandler)this.errorHandler).getDefaultErrorChannel();
        }
        return null;
    }

    protected ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    protected boolean isReceiveOnlyAdvice(Advice advice) {
        return advice instanceof ReceiveMessageAdvice;
    }

    protected void applyReceiveOnlyAdviceChain(Collection<Advice> chain) {
        Object source;
        if (!CollectionUtils.isEmpty(chain) && (source = this.getReceiveMessageSource()) != null) {
            if (AopUtils.isAopProxy((Object)source)) {
                Advised advised = (Advised)source;
                this.appliedAdvices.forEach(arg_0 -> ((Advised)advised).removeAdvice(arg_0));
                chain.forEach(advice -> advised.addAdvisor((Advisor)this.adviceToReceiveAdvisor((Advice)advice)));
            } else {
                ProxyFactory proxyFactory = new ProxyFactory(source);
                chain.forEach(advice -> proxyFactory.addAdvisor((Advisor)this.adviceToReceiveAdvisor((Advice)advice)));
                source = proxyFactory.getProxy(this.getBeanClassLoader());
            }
            this.appliedAdvices.clear();
            this.appliedAdvices.addAll(chain);
            if (!this.isSyncExecutor()) {
                this.logger.warn(() -> this.getComponentName() + ": A task executor is supplied and " + chain.size() + "ReceiveMessageAdvice(s) is/are provided. If an advice mutates the source, such mutations are not thread safe and could cause unexpected results, especially with high frequency pollers. Consider using a downstream ExecutorChannel instead of adding an executor to the poller");
            }
            this.setReceiveMessageSource(source);
        }
    }

    private NameMatchMethodPointcutAdvisor adviceToReceiveAdvisor(Advice advice) {
        NameMatchMethodPointcutAdvisor sourceAdvisor = new NameMatchMethodPointcutAdvisor(advice);
        sourceAdvisor.addMethodName("receive");
        return sourceAdvisor;
    }

    protected boolean isReactive() {
        return false;
    }

    protected Flux<Message<?>> getPollingFlux() {
        return this.pollingFlux;
    }

    protected Object getReceiveMessageSource() {
        return null;
    }

    protected void setReceiveMessageSource(Object source) {
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
            Assert.notNull((Object)this.trigger, (String)"Trigger is required");
            if (this.taskExecutor != null && !(this.taskExecutor instanceof ErrorHandlingTaskExecutor)) {
                if (this.errorHandler == null) {
                    this.errorHandler = ChannelUtils.getErrorHandler(this.getBeanFactory());
                    this.errorHandlerIsDefault = true;
                }
                this.taskExecutor = new ErrorHandlingTaskExecutor(this.taskExecutor, this.errorHandler);
            }
            if (this.transactionSynchronizationFactory == null && this.adviceChain != null) {
                if (this.adviceChain.stream().anyMatch(TransactionInterceptor.class::isInstance)) {
                    this.transactionSynchronizationFactory = new PassThroughTransactionSynchronizationFactory();
                }
            }
            this.initialized = true;
        }
        try {
            super.onInit();
        }
        catch (Exception ex) {
            throw new BeanInitializationException("Cannot initialize: " + this, (Throwable)ex);
        }
    }

    @Override
    protected void doStart() {
        if (!this.initialized) {
            this.onInit();
        }
        this.pollingTask = this.createPollingTask();
        if (this.isReactive()) {
            this.pollingFlux = this.createFluxGenerator();
        } else {
            TaskScheduler taskScheduler = this.getTaskScheduler();
            Assert.state((taskScheduler != null ? 1 : 0) != 0, (String)"unable to start polling, no taskScheduler available");
            this.runningTask = taskScheduler.schedule(this.createPoller(), this.trigger);
        }
    }

    private Callable<Message<?>> createPollingTask() {
        List<Advice> receiveOnlyAdviceChain = null;
        if (!CollectionUtils.isEmpty(this.adviceChain)) {
            receiveOnlyAdviceChain = this.adviceChain.stream().filter(this::isReceiveOnlyAdvice).collect(Collectors.toList());
        }
        Callable task = this::doPoll;
        List<Advice> advices = this.adviceChain;
        if (!CollectionUtils.isEmpty(advices)) {
            ProxyFactory proxyFactory = new ProxyFactory(task);
            if (!CollectionUtils.isEmpty(advices)) {
                advices.stream().filter(advice -> !this.isReceiveOnlyAdvice((Advice)advice)).forEach(arg_0 -> ((ProxyFactory)proxyFactory).addAdvice(arg_0));
            }
            task = (Callable)proxyFactory.getProxy(this.beanClassLoader);
        }
        if (!CollectionUtils.isEmpty(receiveOnlyAdviceChain)) {
            this.applyReceiveOnlyAdviceChain(receiveOnlyAdviceChain);
        }
        return task;
    }

    private Runnable createPoller() {
        return () -> this.taskExecutor.execute(() -> {
            int count = 0;
            while (this.initialized && (this.maxMessagesPerPoll <= 0L || (long)count < this.maxMessagesPerPoll)) {
                if (this.maxMessagesPerPoll == 0L) {
                    this.logger.info((CharSequence)"Polling disabled while 'maxMessagesPerPoll == 0'");
                    break;
                }
                if (this.pollForMessage() == null) break;
                ++count;
            }
        });
    }

    private Flux<Message<?>> createFluxGenerator() {
        SimpleTriggerContext triggerContext = new SimpleTriggerContext();
        return Flux.generate(sink -> {
            Date date = this.trigger.nextExecutionTime((TriggerContext)triggerContext);
            if (date != null) {
                triggerContext.update(date, null, null);
                long millis = date.getTime() - System.currentTimeMillis();
                sink.next((Object)Duration.ofMillis(millis));
            } else {
                sink.complete();
            }
        }).concatMap(duration -> Mono.delay((Duration)duration).doOnNext(l -> triggerContext.update(triggerContext.lastScheduledExecutionTime(), new Date(), null)).flatMapMany(l -> Flux.defer(() -> {
            if (this.maxMessagesPerPoll == 0L) {
                this.logger.info((CharSequence)"Polling disabled while 'maxMessagesPerPoll == 0'");
                return Mono.empty();
            }
            return Flux.generate(fluxSink -> {
                Message<?> message = this.pollForMessage();
                if (message != null) {
                    fluxSink.next(message);
                } else {
                    fluxSink.complete();
                }
            }).take(this.maxMessagesPerPoll < 0L ? Long.MAX_VALUE : this.maxMessagesPerPoll, true);
        }).subscribeOn(Schedulers.fromExecutor((Executor)this.taskExecutor)).doOnComplete(() -> triggerContext.update(triggerContext.lastScheduledExecutionTime(), triggerContext.lastActualExecutionTime(), new Date()))), 0).repeat(this::isActive).doOnSubscribe(subs -> {
            this.subscription = subs;
        });
    }

    private Message<?> pollForMessage() {
        try {
            Message<?> message = this.pollingTask.call();
            return message;
        }
        catch (Exception e) {
            Object resource;
            if (e instanceof MessagingException) {
                throw (MessagingException)((Object)e);
            }
            Message<?> failedMessage = null;
            if (this.transactionSynchronizationFactory != null && (resource = TransactionSynchronizationManager.getResource((Object)this.getResourceToBind())) instanceof IntegrationResourceHolder) {
                failedMessage = ((IntegrationResourceHolder)resource).getMessage();
            }
            throw new MessagingException(failedMessage, (Throwable)e);
        }
        finally {
            Object resource;
            if (this.transactionSynchronizationFactory != null && TransactionSynchronizationManager.hasResource((Object)(resource = this.getResourceToBind()))) {
                TransactionSynchronizationManager.unbindResource((Object)resource);
            }
        }
    }

    private Message<?> doPoll() {
        IntegrationResourceHolder holder = this.bindResourceHolderIfNecessary(this.getResourceKey(), this.getResourceToBind());
        Message<?> message = null;
        try {
            message = this.receiveMessage();
        }
        catch (Exception ex) {
            if (Thread.interrupted()) {
                this.logger.debug(() -> "Poll interrupted - during stop()? : " + ex.getMessage());
                return null;
            }
            ReflectionUtils.rethrowRuntimeException((Throwable)ex);
        }
        if (message == null) {
            this.logger.debug((CharSequence)"Received no Message during the poll, returning 'false'");
            return null;
        }
        this.messageReceived(holder, message);
        return message;
    }

    private void messageReceived(IntegrationResourceHolder holder, Message<?> message) {
        this.logger.debug(() -> "Poll resulted in Message: " + message);
        if (holder != null) {
            holder.setMessage(message);
        }
        if (!this.isReactive()) {
            try {
                this.handleMessage(message);
            }
            catch (MessagingException ex) {
                throw new MessagingExceptionWrapper(message, ex);
            }
            catch (Exception ex) {
                throw new MessagingException(message, (Throwable)ex);
            }
        }
    }

    @Override
    protected void doStop() {
        if (this.runningTask != null) {
            this.runningTask.cancel(true);
        }
        this.runningTask = null;
        if (this.subscription != null) {
            this.subscription.cancel();
        }
    }

    protected abstract Message<?> receiveMessage();

    protected abstract void handleMessage(Message<?> var1);

    protected Object getResourceToBind() {
        return null;
    }

    protected String getResourceKey() {
        return null;
    }

    private IntegrationResourceHolder bindResourceHolderIfNecessary(String key, Object resource) {
        if (this.transactionSynchronizationFactory != null && resource != null && TransactionSynchronizationManager.isActualTransactionActive()) {
            Object resourceHolder;
            TransactionSynchronization synchronization = this.transactionSynchronizationFactory.create(resource);
            if (synchronization != null) {
                TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)synchronization);
                if (synchronization instanceof IntegrationResourceHolderSynchronization) {
                    IntegrationResourceHolderSynchronization integrationSynchronization = (IntegrationResourceHolderSynchronization)synchronization;
                    integrationSynchronization.setShouldUnbindAtCompletion(false);
                    if (!TransactionSynchronizationManager.hasResource((Object)resource)) {
                        TransactionSynchronizationManager.bindResource((Object)resource, (Object)integrationSynchronization.getResourceHolder());
                    }
                }
            }
            if ((resourceHolder = TransactionSynchronizationManager.getResource((Object)resource)) instanceof IntegrationResourceHolder) {
                IntegrationResourceHolder integrationResourceHolder = (IntegrationResourceHolder)resourceHolder;
                if (key != null) {
                    integrationResourceHolder.addAttribute(key, resource);
                }
                return integrationResourceHolder;
            }
        }
        return null;
    }
}

