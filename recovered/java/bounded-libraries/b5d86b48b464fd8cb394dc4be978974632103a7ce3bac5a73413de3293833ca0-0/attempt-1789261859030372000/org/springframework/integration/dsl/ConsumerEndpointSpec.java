/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.util.function.Tuple2
 */
package org.springframework.integration.dsl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.reactivestreams.Publisher;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.dsl.EndpointSpec;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.advice.HandleMessageAdviceAdapter;
import org.springframework.integration.handler.advice.ReactiveRequestHandlerAdvice;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transaction.TransactionInterceptorBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public abstract class ConsumerEndpointSpec<S extends ConsumerEndpointSpec<S, H>, H extends MessageHandler>
extends EndpointSpec<S, ConsumerEndpointFactoryBean, H> {
    protected final List<Advice> adviceChain = new LinkedList<Advice>();

    protected ConsumerEndpointSpec(H messageHandler) {
        super(messageHandler, new ConsumerEndpointFactoryBean());
    }

    @Override
    public S phase(int phase) {
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setPhase(phase);
        return (S)((ConsumerEndpointSpec)this._this());
    }

    @Override
    public S autoStartup(boolean autoStartup) {
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setAutoStartup(autoStartup);
        return (S)((ConsumerEndpointSpec)this._this());
    }

    @Override
    public S poller(PollerMetadata pollerMetadata) {
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setPollerMetadata(pollerMetadata);
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S reactive() {
        return this.reactive(Function.identity());
    }

    public S reactive(Function<? super Flux<Message<?>>, ? extends Publisher<Message<?>>> reactiveCustomizer) {
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setReactiveCustomizer(reactiveCustomizer);
        return (S)((ConsumerEndpointSpec)this._this());
    }

    @Override
    public S role(String role) {
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setRole(role);
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S taskScheduler(TaskScheduler taskScheduler) {
        Assert.notNull((Object)taskScheduler, (String)"'taskScheduler' must not be null");
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setTaskScheduler(taskScheduler);
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S handleMessageAdvice(MethodInterceptor ... interceptors) {
        for (MethodInterceptor interceptor : interceptors) {
            this.advice(new Advice[]{new HandleMessageAdviceAdapter(interceptor)});
        }
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S advice(Advice ... advice) {
        this.adviceChain.addAll(Arrays.asList(advice));
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S transactional(TransactionManager transactionManager) {
        return this.transactional(transactionManager, false);
    }

    public S transactional(TransactionManager transactionManager, boolean handleMessageAdvice) {
        return this.transactional(new TransactionInterceptorBuilder(handleMessageAdvice).transactionManager(transactionManager).build());
    }

    public S transactional(TransactionInterceptor transactionInterceptor) {
        return this.advice(new Advice[]{transactionInterceptor});
    }

    public S transactional() {
        return this.transactional(false);
    }

    public S transactional(boolean handleMessageAdvice) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptorBuilder(handleMessageAdvice).build();
        this.componentsToRegister.put(transactionInterceptor, null);
        return this.transactional(transactionInterceptor);
    }

    public <T, V> S customizeMonoReply(BiFunction<Message<?>, Mono<T>, Publisher<V>> replyCustomizer) {
        return this.advice(new Advice[]{new ReactiveRequestHandlerAdvice(replyCustomizer)});
    }

    public S requiresReply(boolean requiresReply) {
        this.assertHandler();
        if (this.handler instanceof AbstractReplyProducingMessageHandler) {
            ((AbstractReplyProducingMessageHandler)this.handler).setRequiresReply(requiresReply);
        } else {
            this.logger.warn((Object)"'requiresReply' can be applied only for AbstractReplyProducingMessageHandler");
        }
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S sendTimeout(long sendTimeout) {
        this.assertHandler();
        if (this.handler instanceof AbstractMessageProducingHandler) {
            ((AbstractMessageProducingHandler)this.handler).setSendTimeout(sendTimeout);
        } else if (this.handler instanceof AbstractMessageRouter) {
            ((AbstractMessageRouter)this.handler).setSendTimeout(sendTimeout);
        } else {
            this.logger.warn((Object)"'sendTimeout' can be applied only for AbstractMessageProducingHandler");
        }
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S order(int order) {
        this.assertHandler();
        if (this.handler instanceof AbstractMessageHandler) {
            ((AbstractMessageHandler)this.handler).setOrder(order);
        } else {
            this.logger.warn((Object)"'order' can be applied only for AbstractMessageHandler");
        }
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S async(boolean async) {
        this.assertHandler();
        if (this.handler instanceof AbstractMessageProducingHandler) {
            ((AbstractMessageProducingHandler)this.handler).setAsync(async);
        } else {
            this.logger.warn((Object)"'async' can be applied only for AbstractMessageProducingHandler");
        }
        return (S)((ConsumerEndpointSpec)this._this());
    }

    public S notPropagatedHeaders(String ... headerPatterns) {
        this.assertHandler();
        if (this.handler instanceof AbstractMessageProducingHandler) {
            ((AbstractMessageProducingHandler)this.handler).setNotPropagatedHeaders(headerPatterns);
        } else {
            this.logger.warn((Object)"'headerPatterns' can be applied only for AbstractMessageProducingHandler");
        }
        return (S)((ConsumerEndpointSpec)this._this());
    }

    @Override
    protected Tuple2<ConsumerEndpointFactoryBean, H> doGet() {
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setAdviceChain(this.adviceChain);
        if (this.handler instanceof AbstractReplyProducingMessageHandler && !this.adviceChain.isEmpty()) {
            ((AbstractReplyProducingMessageHandler)this.handler).setAdviceChain(this.adviceChain);
        }
        ((ConsumerEndpointFactoryBean)this.endpointFactoryBean).setHandler(this.handler);
        return super.doGet();
    }
}

