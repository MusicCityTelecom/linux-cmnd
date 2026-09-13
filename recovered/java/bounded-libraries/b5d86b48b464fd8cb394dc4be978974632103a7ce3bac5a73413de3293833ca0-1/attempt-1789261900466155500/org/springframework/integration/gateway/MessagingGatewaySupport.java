/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.messaging.support.MessageBuilder
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 *  reactor.core.publisher.Sinks
 *  reactor.core.publisher.Sinks$EmitFailureHandler
 *  reactor.core.publisher.Sinks$One
 */
package org.springframework.integration.gateway;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.AttributeAccessor;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.MessageTimeoutException;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.ReactiveStreamsConsumer;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.history.HistoryWritingMessagePostProcessor;
import org.springframework.integration.mapping.InboundMessageMapper;
import org.springframework.integration.mapping.MessageMappingException;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.integration.support.DefaultErrorMessageStrategy;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageUtils;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.MutableMessageBuilder;
import org.springframework.integration.support.converter.SimpleMessageConverter;
import org.springframework.integration.support.management.IntegrationInboundManagement;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.support.management.metrics.SampleFacade;
import org.springframework.integration.support.management.metrics.TimerFacade;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@IntegrationManagedResource
public abstract class MessagingGatewaySupport
extends AbstractEndpoint
implements TrackableComponent,
IntegrationInboundManagement,
IntegrationPattern {
    private static final long DEFAULT_TIMEOUT = 1000L;
    protected final MessagingTemplate messagingTemplate;
    private final SimpleMessageConverter messageConverter = new SimpleMessageConverter();
    private final HistoryWritingMessagePostProcessor historyWritingPostProcessor = new HistoryWritingMessagePostProcessor();
    private final Object replyMessageCorrelatorMonitor = new Object();
    private final IntegrationManagement.ManagementOverrides managementOverrides = new IntegrationManagement.ManagementOverrides();
    private final Set<TimerFacade> timers = ConcurrentHashMap.newKeySet();
    private boolean errorOnTimeout;
    private ErrorMessageStrategy errorMessageStrategy = new DefaultErrorMessageStrategy();
    private MessageChannel requestChannel;
    private String requestChannelName;
    private MessageChannel replyChannel;
    private String replyChannelName;
    private MessageChannel errorChannel;
    private String errorChannelName;
    private long replyTimeout = 1000L;
    private InboundMessageMapper<Object> requestMapper = new DefaultRequestMapper();
    private boolean loggingEnabled = true;
    private String managedType;
    private String managedName;
    private MetricsCaptor metricsCaptor;
    private TimerFacade successTimer;
    private volatile AbstractEndpoint replyMessageCorrelator;
    private volatile boolean initialized;

    public MessagingGatewaySupport() {
        this(false);
    }

    public MessagingGatewaySupport(boolean errorOnTimeout) {
        MessagingTemplate template = new MessagingTemplate();
        template.setMessageConverter(this.messageConverter);
        template.setSendTimeout(1000L);
        template.setReceiveTimeout(this.replyTimeout);
        this.messagingTemplate = template;
        this.errorOnTimeout = errorOnTimeout;
    }

    public void setErrorOnTimeout(boolean errorOnTimeout) {
        this.errorOnTimeout = errorOnTimeout;
    }

    public void setRequestChannel(MessageChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    public void setRequestChannelName(String requestChannelName) {
        Assert.hasText((String)requestChannelName, (String)"'requestChannelName' must not be empty");
        this.requestChannelName = requestChannelName;
    }

    public void setReplyChannel(MessageChannel replyChannel) {
        this.replyChannel = replyChannel;
    }

    public void setReplyChannelName(String replyChannelName) {
        Assert.hasText((String)replyChannelName, (String)"'replyChannelName' must not be empty");
        this.replyChannelName = replyChannelName;
    }

    public void setErrorChannel(MessageChannel errorChannel) {
        this.errorChannel = errorChannel;
    }

    public void setErrorChannelName(String errorChannelName) {
        Assert.hasText((String)errorChannelName, (String)"'errorChannelName' must not be empty");
        this.errorChannelName = errorChannelName;
    }

    public void setRequestTimeout(long requestTimeout) {
        this.messagingTemplate.setSendTimeout(requestTimeout);
    }

    public void setReplyTimeout(long replyTimeout) {
        this.replyTimeout = replyTimeout;
        this.messagingTemplate.setReceiveTimeout(replyTimeout);
    }

    public void setRequestMapper(@Nullable InboundMessageMapper<?> requestMapper) {
        if (requestMapper != null) {
            this.requestMapper = requestMapper;
        }
        this.messageConverter.setInboundMessageMapper(this.requestMapper);
    }

    public void setReplyMapper(OutboundMessageMapper<?> replyMapper) {
        this.messageConverter.setOutboundMessageMapper(replyMapper);
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        this.historyWritingPostProcessor.setShouldTrack(shouldTrack);
    }

    @Override
    public String getComponentType() {
        return "gateway";
    }

    @Override
    public void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
        this.managementOverrides.loggingConfigured = true;
    }

    @Override
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public final void setErrorMessageStrategy(ErrorMessageStrategy errorMessageStrategy) {
        Assert.notNull((Object)errorMessageStrategy, (String)"'errorMessageStrategy' cannot be null");
        this.errorMessageStrategy = errorMessageStrategy;
    }

    @Override
    public IntegrationManagement.ManagementOverrides getOverrides() {
        return this.managementOverrides;
    }

    @Override
    public void setManagedType(String managedType) {
        this.managedType = managedType;
    }

    @Override
    public String getManagedType() {
        return this.managedType;
    }

    @Override
    public void setManagedName(String managedName) {
        this.managedName = managedName;
    }

    @Override
    public String getManagedName() {
        return this.managedName;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.inbound_gateway;
    }

    @Override
    public void registerMetricsCaptor(MetricsCaptor metricsCaptorToRegister) {
        this.metricsCaptor = metricsCaptorToRegister;
    }

    @Override
    protected void onInit() {
        Assert.state((this.requestChannelName == null || this.requestChannel == null ? 1 : 0) != 0, (String)"'requestChannelName' and 'requestChannel' are mutually exclusive.");
        Assert.state((this.replyChannelName == null || this.replyChannel == null ? 1 : 0) != 0, (String)"'replyChannelName' and 'replyChannel' are mutually exclusive.");
        Assert.state((this.errorChannelName == null || this.errorChannel == null ? 1 : 0) != 0, (String)"'errorChannelName' and 'errorChannel' are mutually exclusive.");
        this.historyWritingPostProcessor.setTrackableComponent(this);
        MessageBuilderFactory messageBuilderFactory = this.getMessageBuilderFactory();
        this.historyWritingPostProcessor.setMessageBuilderFactory(messageBuilderFactory);
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            this.messagingTemplate.setBeanFactory(beanFactory);
            if (this.requestMapper instanceof DefaultRequestMapper) {
                ((DefaultRequestMapper)this.requestMapper).setMessageBuilderFactory(messageBuilderFactory);
            }
            if (this.requestMapper instanceof BeanFactoryAware) {
                ((BeanFactoryAware)this.requestMapper).setBeanFactory(beanFactory);
            }
            this.messageConverter.setBeanFactory(beanFactory);
        }
        this.initialized = true;
    }

    private void initializeIfNecessary() {
        if (!this.initialized) {
            this.afterPropertiesSet();
        }
    }

    @Nullable
    public MessageChannel getRequestChannel() {
        if (this.requestChannel == null && this.requestChannelName != null) {
            this.requestChannel = (MessageChannel)this.getChannelResolver().resolveDestination(this.requestChannelName);
        }
        return this.requestChannel;
    }

    @Nullable
    public MessageChannel getReplyChannel() {
        if (this.replyChannel == null && this.replyChannelName != null) {
            this.replyChannel = (MessageChannel)this.getChannelResolver().resolveDestination(this.replyChannelName);
        }
        return this.replyChannel;
    }

    @Nullable
    public MessageChannel getErrorChannel() {
        if (this.errorChannel == null && this.errorChannelName != null) {
            this.errorChannel = (MessageChannel)this.getChannelResolver().resolveDestination(this.errorChannelName);
        }
        return this.errorChannel;
    }

    protected void send(Object object) {
        this.initializeIfNecessary();
        Assert.notNull((Object)object, (String)"request must not be null");
        MessageChannel channel = this.getRequestChannel();
        Assert.state((channel != null ? 1 : 0) != 0, (String)"send is not supported, because no request channel has been configured");
        SampleFacade sample = null;
        if (this.metricsCaptor != null) {
            sample = this.metricsCaptor.start();
        }
        try {
            this.messagingTemplate.convertAndSend(channel, object, this.historyWritingPostProcessor);
            if (sample != null) {
                sample.stop(this.sendTimer());
            }
        }
        catch (Exception e) {
            MessageChannel errorChan;
            if (sample != null) {
                sample.stop(this.buildSendTimer(false, e.getClass().getSimpleName()));
            }
            if ((errorChan = this.getErrorChannel()) != null) {
                this.messagingTemplate.send(errorChan, (Message)new ErrorMessage((Throwable)e));
            }
            this.rethrow(e, "failed to send message");
        }
    }

    @Nullable
    protected Object receive() {
        this.initializeIfNecessary();
        MessageChannel channel = this.getReplyChannel();
        this.assertPollableChannel(channel);
        return this.messagingTemplate.receiveAndConvert(channel, Object.class);
    }

    @Nullable
    protected Message<?> receiveMessage() {
        this.initializeIfNecessary();
        MessageChannel channel = this.getReplyChannel();
        this.assertPollableChannel(channel);
        return this.messagingTemplate.receive(channel);
    }

    @Nullable
    protected Object receive(long timeout) {
        this.initializeIfNecessary();
        MessageChannel channel = this.getReplyChannel();
        this.assertPollableChannel(channel);
        return this.messagingTemplate.receiveAndConvert(channel, timeout);
    }

    @Nullable
    protected Message<?> receiveMessage(long timeout) {
        this.initializeIfNecessary();
        MessageChannel channel = this.getReplyChannel();
        this.assertPollableChannel(channel);
        return this.messagingTemplate.receive(channel, timeout);
    }

    private void assertPollableChannel(@Nullable MessageChannel channel) {
        Assert.state((boolean)(channel instanceof PollableChannel), (String)"receive is not supported, because no pollable reply channel has been configured");
    }

    @Nullable
    protected Object sendAndReceive(Object object) {
        return this.doSendAndReceive(object, true);
    }

    @Nullable
    protected Message<?> sendAndReceiveMessage(Object object) {
        return (Message)this.doSendAndReceive(object, false);
    }

    @Nullable
    private Object doSendAndReceive(Object object, boolean shouldConvert) {
        Message<?> reply;
        Message<?> requestMessage;
        block9: {
            this.initializeIfNecessary();
            Assert.notNull((Object)object, (String)"request must not be null");
            MessageChannel channel = this.getRequestChannel();
            if (channel == null) {
                throw new MessagingException("No request channel available. Cannot send request message.");
            }
            this.registerReplyMessageCorrelatorIfNecessary();
            requestMessage = null;
            SampleFacade sample = null;
            try {
                if (this.metricsCaptor != null) {
                    sample = this.metricsCaptor.start();
                }
                if (shouldConvert) {
                    reply = this.messagingTemplate.convertSendAndReceive(channel, object, Object.class, this.historyWritingPostProcessor);
                } else {
                    requestMessage = object instanceof Message ? (Message<?>)object : this.requestMapper.toMessage(object);
                    Assert.state((requestMessage != null ? 1 : 0) != 0, () -> "request mapper resulted in no message for " + object);
                    requestMessage = this.historyWritingPostProcessor.postProcessMessage(requestMessage);
                    reply = this.messagingTemplate.sendAndReceive(channel, requestMessage);
                }
                if (reply == null && this.errorOnTimeout) {
                    this.throwMessageTimeoutException(object, "No reply received within timeout");
                }
                if (sample != null) {
                    sample.stop(this.sendTimer());
                }
            }
            catch (Throwable ex) {
                this.logger.debug(() -> "failure occurred in gateway sendAndReceive: " + ex.getMessage());
                reply = ex;
                if (sample == null) break block9;
                sample.stop(this.buildSendTimer(false, ex.getClass().getSimpleName()));
            }
        }
        if (reply instanceof Throwable || reply instanceof ErrorMessage) {
            Throwable error = reply instanceof ErrorMessage ? (Throwable)((ErrorMessage)reply).getPayload() : (Throwable)reply;
            return this.handleSendAndReceiveError(object, requestMessage, error, shouldConvert);
        }
        return reply;
    }

    @Nullable
    private Object handleSendAndReceiveError(Object object, @Nullable Message<?> requestMessage, Throwable error, boolean shouldConvert) {
        MessageChannel errorChan = this.getErrorChannel();
        if (errorChan != null) {
            ErrorMessage errorMessage = this.buildErrorMessage(requestMessage, error);
            Message<?> errorFlowReply = this.sendErrorMessageAndReceive(errorChan, errorMessage);
            if (errorFlowReply == null && this.errorOnTimeout) {
                this.throwMessageTimeoutException(object, "No reply received from error channel within timeout");
                return null;
            }
            return shouldConvert && errorFlowReply != null ? errorFlowReply.getPayload() : errorFlowReply;
        }
        if (error instanceof Error) {
            throw (Error)error;
        }
        Throwable errorToReThrow = error;
        if (error instanceof MessagingException && requestMessage != null && requestMessage.getHeaders().getErrorChannel() != null) {
            errorToReThrow = new MessageHandlingException(requestMessage, error);
        }
        this.rethrow(errorToReThrow, "gateway received checked Exception");
        return null;
    }

    @Nullable
    private Message<?> sendErrorMessageAndReceive(MessageChannel errorChan, ErrorMessage errorMessage) {
        Message<?> errorFlowReply;
        try {
            errorFlowReply = this.messagingTemplate.sendAndReceive(errorChan, (Message<?>)errorMessage);
        }
        catch (Exception errorFlowFailure) {
            throw new MessagingException((Message)errorMessage, "failure occurred in error-handling flow", (Throwable)errorFlowFailure);
        }
        if (errorFlowReply != null && errorFlowReply.getPayload() instanceof Throwable) {
            this.rethrow((Throwable)errorFlowReply.getPayload(), "error flow returned an Error Message");
        }
        return errorFlowReply;
    }

    private void throwMessageTimeoutException(Object object, String exceptionMessage) {
        if (object instanceof Message) {
            throw new MessageTimeoutException((Message)object, exceptionMessage);
        }
        throw new MessageTimeoutException(exceptionMessage);
    }

    protected Mono<Message<?>> sendAndReceiveMessageReactive(Object object) {
        this.initializeIfNecessary();
        Assert.notNull((Object)object, (String)"request must not be null");
        MessageChannel channel = this.getRequestChannel();
        if (channel == null) {
            throw new MessagingException("No request channel available. Cannot send request message.");
        }
        this.registerReplyMessageCorrelatorIfNecessary();
        return this.doSendAndReceiveMessageReactive(channel, object, false);
    }

    private Mono<Message<?>> doSendAndReceiveMessageReactive(MessageChannel requestChannel, Object object, boolean error) {
        Message<?> requestMessage;
        try {
            Message<?> message = object instanceof Message ? (Message<?>)object : this.requestMapper.toMessage(object);
            Assert.state((message != null ? 1 : 0) != 0, () -> "request mapper resulted in no message for " + object);
            requestMessage = message = this.historyWritingPostProcessor.postProcessMessage(message);
        }
        catch (Exception e) {
            throw new MessageMappingException("Cannot map to message: " + object, e);
        }
        return Mono.defer(() -> {
            Object originalReplyChannelHeader = requestMessage.getHeaders().getReplyChannel();
            Object originalErrorChannelHeader = requestMessage.getHeaders().getErrorChannel();
            MonoReplyChannel replyChan = new MonoReplyChannel();
            Message messageToSend = MutableMessageBuilder.fromMessage(requestMessage).setReplyChannel(replyChan).setHeader(this.messagingTemplate.getSendTimeoutHeader(), null).setHeader(this.messagingTemplate.getReceiveTimeoutHeader(), null).setErrorChannel(replyChan).build();
            this.sendMessageForReactiveFlow(requestChannel, messageToSend);
            return this.buildReplyMono(requestMessage, replyChan.replyMono.asMono(), error, originalReplyChannelHeader, originalErrorChannelHeader);
        }).onErrorResume(t -> error ? Mono.error((Throwable)t) : this.handleSendError(requestMessage, (Throwable)t));
    }

    private void sendMessageForReactiveFlow(MessageChannel requestChannel, Message<?> requestMessage) {
        if (requestChannel instanceof ReactiveStreamsSubscribableChannel) {
            ((ReactiveStreamsSubscribableChannel)requestChannel).subscribeTo((Publisher<? extends Message<?>>)Mono.just(requestMessage));
        } else {
            boolean sent;
            long sendTimeout = this.sendTimeout(requestMessage);
            boolean bl = sent = sendTimeout >= 0L ? requestChannel.send(requestMessage, sendTimeout) : requestChannel.send(requestMessage);
            if (!sent) {
                throw new MessageDeliveryException(requestMessage, "Failed to send message to channel '" + requestChannel + "' within timeout: " + sendTimeout);
            }
        }
    }

    private Mono<Message<?>> buildReplyMono(Message<?> requestMessage, Mono<Message<?>> reply, boolean error, @Nullable Object originalReplyChannelHeader, @Nullable Object originalErrorChannelHeader) {
        MetricsCaptor captor = this.metricsCaptor;
        return reply.doOnSubscribe(s -> {
            if (!error && captor != null) {
                captor.start().stop(this.sendTimer());
            }
        }).map(replyMessage -> {
            if (!error && replyMessage instanceof ErrorMessage) {
                ErrorMessage em = (ErrorMessage)replyMessage;
                if (em.getPayload() instanceof MessagingException) {
                    throw (MessagingException)((Object)((Object)em.getPayload()));
                }
                throw new MessagingException(requestMessage, (Throwable)em.getPayload());
            }
            return MessageBuilder.fromMessage((Message)replyMessage).setHeader("replyChannel", originalReplyChannelHeader).setHeader("errorChannel", originalErrorChannelHeader).build();
        });
    }

    private Mono<Message<?>> handleSendError(Message<?> requestMessage, Throwable exception) {
        this.logger.debug(() -> "failure occurred in gateway sendAndReceiveReactive: " + exception.getMessage());
        MessageChannel channel = this.getErrorChannel();
        if (channel != null) {
            ErrorMessage errorMessage = this.buildErrorMessage(requestMessage, exception);
            try {
                return this.doSendAndReceiveMessageReactive(channel, errorMessage, true);
            }
            catch (Exception errorFlowFailure) {
                throw new MessagingException((Message)errorMessage, "failure occurred in error-handling flow", (Throwable)errorFlowFailure);
            }
        }
        throw this.wrapExceptionIfNecessary(exception, "gateway received checked Exception");
    }

    protected TimerFacade sendTimer() {
        if (this.successTimer == null) {
            this.successTimer = this.buildSendTimer(true, "none");
        }
        return this.successTimer;
    }

    protected TimerFacade buildSendTimer(boolean success, String exception) {
        TimerFacade timer = this.metricsCaptor.timerBuilder("spring.integration.send").tag("type", "source").tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("result", success ? "success" : "failure").tag("exception", exception).description("Send processing time").build();
        this.timers.add(timer);
        return timer;
    }

    private long sendTimeout(Message<?> requestMessage) {
        Long sendTimeout = this.headerToLong(requestMessage.getHeaders().get((Object)this.messagingTemplate.getSendTimeoutHeader()));
        return sendTimeout != null ? sendTimeout.longValue() : this.messagingTemplate.getSendTimeout();
    }

    @Nullable
    private Long headerToLong(@Nullable Object headerValue) {
        if (headerValue instanceof Number) {
            return ((Number)headerValue).longValue();
        }
        if (headerValue instanceof String) {
            return Long.parseLong((String)headerValue);
        }
        return null;
    }

    protected final ErrorMessage buildErrorMessage(@Nullable Message<?> requestMessage, Throwable throwable) {
        return this.errorMessageStrategy.buildErrorMessage(throwable, this.getErrorMessageAttributes(requestMessage));
    }

    protected AttributeAccessor getErrorMessageAttributes(@Nullable Message<?> message) {
        return ErrorMessageUtils.getAttributeAccessor(message, null);
    }

    private void rethrow(Throwable t, String description) {
        throw this.wrapExceptionIfNecessary(t, description);
    }

    private RuntimeException wrapExceptionIfNecessary(Throwable t, String description) {
        if (t instanceof RuntimeException) {
            return (RuntimeException)t;
        }
        return new MessagingException(description, t);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void registerReplyMessageCorrelatorIfNecessary() {
        MessageChannel replyChan = this.getReplyChannel();
        if (replyChan != null && this.replyMessageCorrelator == null) {
            Object object = this.replyMessageCorrelatorMonitor;
            synchronized (object) {
                AbstractEndpoint correlator;
                if (this.replyMessageCorrelator != null) {
                    return;
                }
                BridgeHandler handler = new BridgeHandler();
                BeanFactory beanFactory = this.getBeanFactory();
                if (beanFactory != null) {
                    handler.setBeanFactory(beanFactory);
                }
                handler.afterPropertiesSet();
                if (replyChan instanceof SubscribableChannel) {
                    correlator = new EventDrivenConsumer((SubscribableChannel)replyChan, handler);
                } else if (replyChan instanceof PollableChannel) {
                    PollingConsumer endpoint = new PollingConsumer((PollableChannel)replyChan, handler);
                    endpoint.setReceiveTimeout(this.replyTimeout);
                    correlator = endpoint;
                } else if (replyChan instanceof ReactiveStreamsSubscribableChannel) {
                    correlator = new ReactiveStreamsConsumer(replyChan, (Subscriber<Message<?>>)handler);
                } else {
                    throw new MessagingException("Unsupported 'replyChannel' type [" + replyChan.getClass() + "].SubscribableChannel or PollableChannel type are supported.");
                }
                if (beanFactory != null) {
                    correlator.setBeanFactory(beanFactory);
                }
                correlator.afterPropertiesSet();
                this.replyMessageCorrelator = correlator;
            }
            if (this.isRunning()) {
                this.replyMessageCorrelator.start();
            }
        }
    }

    @Override
    protected void doStart() {
        if (this.replyMessageCorrelator != null) {
            this.replyMessageCorrelator.start();
        }
    }

    @Override
    protected void doStop() {
        if (this.replyMessageCorrelator != null) {
            this.replyMessageCorrelator.stop();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        this.timers.forEach(MeterFacade::remove);
        this.timers.clear();
    }

    private static class DefaultRequestMapper
    implements InboundMessageMapper<Object> {
        private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();

        DefaultRequestMapper() {
        }

        void setMessageBuilderFactory(MessageBuilderFactory messageBuilderFactory) {
            this.messageBuilderFactory = messageBuilderFactory;
        }

        @Override
        public Message<?> toMessage(Object object, @Nullable Map<String, Object> headers) {
            if (object instanceof Message) {
                return (Message)object;
            }
            return this.messageBuilderFactory.withPayload(object).copyHeadersIfAbsent(headers).build();
        }
    }

    private static class MonoReplyChannel
    implements MessageChannel,
    ReactiveStreamsSubscribableChannel {
        private final Sinks.One<Message<?>> replyMono = Sinks.one();

        MonoReplyChannel() {
        }

        public boolean send(Message<?> message, long timeout) {
            return this.replyMono.tryEmitValue(message).isSuccess();
        }

        @Override
        public void subscribeTo(Publisher<? extends Message<?>> publisher) {
            Mono.from(publisher).subscribe(value -> this.replyMono.emitValue(value, Sinks.EmitFailureHandler.FAIL_FAST), arg_0 -> this.replyMono.tryEmitError(arg_0), () -> this.replyMono.tryEmitEmpty());
        }
    }
}

