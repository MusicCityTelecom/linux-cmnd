/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolutionException
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.ListenableFutureCallback
 *  org.springframework.util.concurrent.SettableListenableFuture
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.integration.handler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.handler.HeaderPropagationAware;
import org.springframework.integration.routingslip.RoutingSlipRouteStrategy;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public abstract class AbstractMessageProducingHandler
extends AbstractMessageHandler
implements MessageProducer,
HeaderPropagationAware {
    protected final MessagingTemplate messagingTemplate = new MessagingTemplate();
    private boolean async;
    @Nullable
    private String outputChannelName;
    @Nullable
    private MessageChannel outputChannel;
    private String[] notPropagatedHeaders;
    private boolean selectiveHeaderPropagation;
    private boolean noHeadersPropagation;

    public void setSendTimeout(long sendTimeout) {
        this.messagingTemplate.setSendTimeout(sendTimeout);
    }

    @Override
    public void setOutputChannel(MessageChannel outputChannel) {
        this.outputChannel = outputChannel;
    }

    @Override
    public void setOutputChannelName(String outputChannelName) {
        Assert.hasText((String)outputChannelName, (String)"'outputChannelName' must not be empty");
        this.outputChannelName = outputChannelName;
    }

    public final void setAsync(boolean async) {
        this.async = async;
    }

    protected boolean isAsync() {
        return this.async;
    }

    @Override
    public void setNotPropagatedHeaders(String ... headers) {
        this.updateNotPropagatedHeaders(headers, false);
    }

    protected final void updateNotPropagatedHeaders(String[] headers, boolean merge) {
        HashSet<String> headerPatterns = new HashSet<String>();
        if (merge && this.notPropagatedHeaders != null) {
            headerPatterns.addAll(Arrays.asList(this.notPropagatedHeaders));
        }
        if (!ObjectUtils.isEmpty((Object[])headers)) {
            Assert.noNullElements((Object[])headers, (String)"null elements are not allowed in 'headers'");
            headerPatterns.addAll(Arrays.asList(headers));
            this.notPropagatedHeaders = headerPatterns.toArray(new String[0]);
        }
        if (headerPatterns.contains("*")) {
            this.notPropagatedHeaders = new String[]{"*"};
            this.noHeadersPropagation = true;
        }
        this.selectiveHeaderPropagation = !ObjectUtils.isEmpty((Object[])this.notPropagatedHeaders);
    }

    @Override
    public Collection<String> getNotPropagatedHeaders() {
        return this.notPropagatedHeaders != null ? Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(this.notPropagatedHeaders))) : Collections.emptyList();
    }

    @Override
    public void addNotPropagatedHeaders(String ... headers) {
        this.updateNotPropagatedHeaders(headers, true);
    }

    @Override
    protected void onInit() {
        super.onInit();
        Assert.state((this.outputChannelName == null || this.outputChannel == null ? 1 : 0) != 0, (String)"'outputChannelName' and 'outputChannel' are mutually exclusive.");
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            this.messagingTemplate.setBeanFactory(beanFactory);
        }
        this.messagingTemplate.setDestinationResolver(this.getChannelResolver());
    }

    @Override
    @Nullable
    public MessageChannel getOutputChannel() {
        String channelName = this.outputChannelName;
        if (channelName != null) {
            this.outputChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            this.outputChannelName = null;
        }
        return this.outputChannel;
    }

    protected void sendOutputs(Object result, Message<?> requestMessage) {
        if (result instanceof Iterable && this.shouldSplitOutput((Iterable)result)) {
            for (Object o : (Iterable)result) {
                this.produceOutput(o, requestMessage);
            }
        } else if (result != null) {
            this.produceOutput(result, requestMessage);
        }
    }

    protected boolean shouldSplitOutput(Iterable<?> reply) {
        for (Object next : reply) {
            if (!(next instanceof Message) && !(next instanceof AbstractIntegrationMessageBuilder)) continue;
            return true;
        }
        return false;
    }

    protected void produceOutput(Object replyArg, Message<?> requestMessage) {
        MessageHeaders requestHeaders = requestMessage.getHeaders();
        AbstractIntegrationMessageBuilder<?> reply = replyArg;
        Object replyChannel = null;
        if (this.getOutputChannel() == null) {
            Map<?, ?> routingSlipHeader = this.obtainRoutingSlipHeader(requestHeaders, reply);
            if (routingSlipHeader != null) {
                Assert.isTrue((routingSlipHeader.size() == 1 ? 1 : 0) != 0, (String)"The RoutingSlip header value must be a SingletonMap");
                Object key = routingSlipHeader.keySet().iterator().next();
                Object value = routingSlipHeader.values().iterator().next();
                Assert.isInstanceOf(List.class, key, (String)"The RoutingSlip key must be List");
                Assert.isInstanceOf(Integer.class, value, (String)"The RoutingSlip value must be Integer");
                List routingSlip = (List)key;
                AtomicInteger routingSlipIndex = new AtomicInteger((Integer)value);
                replyChannel = this.getOutputChannelFromRoutingSlip(reply, requestMessage, routingSlip, routingSlipIndex);
                if (replyChannel != null) {
                    reply = this.addRoutingSlipHeader(reply, routingSlip, routingSlipIndex);
                }
            }
            if (replyChannel == null) {
                replyChannel = this.obtainReplyChannel(requestHeaders, reply);
            }
        }
        this.doProduceOutput(requestMessage, requestHeaders, reply, replyChannel);
    }

    @Nullable
    private Map<?, ?> obtainRoutingSlipHeader(MessageHeaders requestHeaders, Object reply) {
        Map routingSlipHeader = (Map)requestHeaders.get((Object)"routingSlip", Map.class);
        if (routingSlipHeader == null) {
            if (reply instanceof Message) {
                routingSlipHeader = (Map)((Message)reply).getHeaders().get((Object)"routingSlip", Map.class);
            } else if (reply instanceof AbstractIntegrationMessageBuilder) {
                routingSlipHeader = ((AbstractIntegrationMessageBuilder)reply).getHeader("routingSlip", Map.class);
            }
        }
        return routingSlipHeader;
    }

    @Nullable
    private Object obtainReplyChannel(MessageHeaders requestHeaders, Object reply) {
        Object replyChannel = requestHeaders.getReplyChannel();
        if (replyChannel == null) {
            if (reply instanceof Message) {
                replyChannel = ((Message)reply).getHeaders().getReplyChannel();
            } else if (reply instanceof AbstractIntegrationMessageBuilder) {
                replyChannel = ((AbstractIntegrationMessageBuilder)reply).getHeader("replyChannel", Object.class);
            }
        }
        return replyChannel;
    }

    private void doProduceOutput(Message<?> requestMessage, MessageHeaders requestHeaders, Object reply, @Nullable Object replyChannelArg) {
        Object replyChannel = replyChannelArg;
        if (replyChannel == null) {
            replyChannel = this.getOutputChannel();
        }
        if (this.async && (reply instanceof ListenableFuture || reply instanceof Publisher)) {
            if (reply instanceof Publisher && replyChannel instanceof ReactiveStreamsSubscribableChannel) {
                ((ReactiveStreamsSubscribableChannel)replyChannel).subscribeTo((Publisher<? extends Message<?>>)Flux.from((Publisher)((Publisher)reply)).doOnError(ex -> this.sendErrorMessage(requestMessage, (Throwable)ex)).map(result -> this.createOutputMessage(result, requestHeaders)));
            } else {
                this.asyncNonReactiveReply(requestMessage, reply, replyChannel);
            }
        } else {
            this.sendOutput(this.createOutputMessage(reply, requestHeaders), replyChannel, false);
        }
    }

    private AbstractIntegrationMessageBuilder<?> addRoutingSlipHeader(Object reply, List<?> routingSlip, AtomicInteger routingSlipIndex) {
        return this.messageBuilderForReply(reply).setHeader("routingSlip", Collections.singletonMap(routingSlip, routingSlipIndex.get()));
    }

    protected AbstractIntegrationMessageBuilder<?> messageBuilderForReply(Object reply) {
        AbstractIntegrationMessageBuilder<Object> builder = reply instanceof Message ? this.getMessageBuilderFactory().fromMessage((Message)reply) : (reply instanceof AbstractIntegrationMessageBuilder ? (AbstractIntegrationMessageBuilder<Object>)reply : this.getMessageBuilderFactory().withPayload(reply));
        return builder;
    }

    private void asyncNonReactiveReply(Message<?> requestMessage, Object reply, @Nullable Object replyChannel) {
        ListenableFuture future;
        if (reply instanceof ListenableFuture) {
            future = (ListenableFuture)reply;
        } else {
            SettableListenableFuture settableListenableFuture = new SettableListenableFuture();
            ReactiveAdapter adapter = ReactiveAdapterRegistry.getSharedInstance().getAdapter(null, reply);
            Mono reactiveReply = adapter != null && adapter.isMultiValue() ? Mono.just((Object)reply) : Mono.from((Publisher)((Publisher)reply));
            reactiveReply.publishOn(Schedulers.boundedElastic()).subscribe(arg_0 -> ((SettableListenableFuture)settableListenableFuture).set(arg_0), arg_0 -> ((SettableListenableFuture)settableListenableFuture).setException(arg_0));
            future = settableListenableFuture;
        }
        future.addCallback((ListenableFutureCallback)new ReplyFutureCallback(requestMessage, replyChannel));
    }

    private Object getOutputChannelFromRoutingSlip(Object reply, Message<?> requestMessage, List<?> routingSlip, AtomicInteger routingSlipIndex) {
        if (routingSlipIndex.get() >= routingSlip.size()) {
            return null;
        }
        Object path = routingSlip.get(routingSlipIndex.get());
        Object routingSlipPathValue = null;
        if (path instanceof String) {
            routingSlipPathValue = this.getBeanFactory().getBean((String)path);
        } else if (path instanceof RoutingSlipRouteStrategy) {
            routingSlipPathValue = path;
        } else {
            throw new IllegalArgumentException("The RoutingSlip 'path' can be of String or RoutingSlipRouteStrategy type, but got: " + path.getClass());
        }
        if (routingSlipPathValue instanceof MessageChannel) {
            routingSlipIndex.incrementAndGet();
            return routingSlipPathValue;
        }
        Object nextPath = ((RoutingSlipRouteStrategy)routingSlipPathValue).getNextPath(requestMessage, reply);
        if (nextPath != null && (!(nextPath instanceof String) || StringUtils.hasText((String)((String)nextPath)))) {
            return nextPath;
        }
        routingSlipIndex.incrementAndGet();
        return this.getOutputChannelFromRoutingSlip(reply, requestMessage, routingSlip, routingSlipIndex);
    }

    protected Message<?> createOutputMessage(Object output, MessageHeaders requestHeaders) {
        AbstractIntegrationMessageBuilder<Object> builder;
        if (output instanceof Message) {
            if (this.noHeadersPropagation || !this.shouldCopyRequestHeaders()) {
                return (Message)output;
            }
            builder = this.getMessageBuilderFactory().fromMessage((Message)output);
        } else {
            builder = output instanceof AbstractIntegrationMessageBuilder ? (AbstractIntegrationMessageBuilder<Object>)output : this.getMessageBuilderFactory().withPayload(output);
        }
        if (!this.noHeadersPropagation && (this.shouldCopyRequestHeaders() || !(output instanceof Message) && !(output instanceof AbstractIntegrationMessageBuilder))) {
            builder.filterAndCopyHeadersIfAbsent((Map<String, ?>)requestHeaders, this.selectiveHeaderPropagation ? this.notPropagatedHeaders : null);
        }
        return builder.build();
    }

    protected void sendOutput(Object output, @Nullable Object replyChannelArg, boolean useArgChannel) {
        Object replyChannel = replyChannelArg;
        MessageChannel outChannel = this.getOutputChannel();
        if (!useArgChannel && outChannel != null) {
            replyChannel = outChannel;
        }
        if (replyChannel == null) {
            throw new DestinationResolutionException("no output-channel or replyChannel header available");
        }
        if (replyChannel instanceof MessageChannel) {
            if (output instanceof Message) {
                this.messagingTemplate.send((MessageChannel)replyChannel, (Message)output);
            } else {
                this.messagingTemplate.convertAndSend((MessageChannel)replyChannel, output);
            }
        } else if (replyChannel instanceof String) {
            if (output instanceof Message) {
                this.messagingTemplate.send((String)replyChannel, (Message)output);
            } else {
                this.messagingTemplate.convertAndSend((String)replyChannel, output);
            }
        } else {
            throw new MessagingException("replyChannel must be a MessageChannel or String");
        }
    }

    protected boolean shouldCopyRequestHeaders() {
        return true;
    }

    protected void sendErrorMessage(Message<?> requestMessage, Throwable ex) {
        Object errorChannel = this.resolveErrorChannel(requestMessage.getHeaders());
        Throwable result = ex;
        if (!(ex instanceof MessagingException)) {
            result = new MessageHandlingException(requestMessage, ex);
        }
        if (errorChannel == null) {
            this.logger.error(result, (CharSequence)"Async exception received and no 'errorChannel' header exists and no default 'errorChannel' found");
        } else {
            try {
                this.sendOutput(new ErrorMessage(result), errorChannel, true);
            }
            catch (Exception e) {
                RuntimeException exceptionToLog = IntegrationUtils.wrapInHandlingExceptionIfNecessary(requestMessage, () -> "failed to send error message in the [" + this + ']', e);
                this.logger.error((Throwable)exceptionToLog, (CharSequence)"Failed to send async reply");
            }
        }
    }

    protected Object resolveErrorChannel(MessageHeaders requestHeaders) {
        Object errorChannel = requestHeaders.getErrorChannel();
        if (errorChannel == null) {
            try {
                errorChannel = this.getChannelResolver().resolveDestination("errorChannel");
            }
            catch (DestinationResolutionException destinationResolutionException) {
                // empty catch block
            }
        }
        return errorChannel;
    }

    private final class ReplyFutureCallback
    implements ListenableFutureCallback<Object> {
        private final Message<?> requestMessage;
        @Nullable
        private final Object replyChannel;

        ReplyFutureCallback(@Nullable Message<?> requestMessage, Object replyChannel) {
            this.requestMessage = requestMessage;
            this.replyChannel = replyChannel;
        }

        public void onSuccess(Object result) {
            Message<?> replyMessage = null;
            try {
                replyMessage = AbstractMessageProducingHandler.this.createOutputMessage(result, this.requestMessage.getHeaders());
                AbstractMessageProducingHandler.this.sendOutput(replyMessage, this.replyChannel, false);
            }
            catch (Exception ex) {
                Throwable exceptionToLogAndSend = ex;
                if (!(ex instanceof MessagingException)) {
                    exceptionToLogAndSend = new MessageHandlingException(this.requestMessage, (Throwable)ex);
                    if (replyMessage != null) {
                        exceptionToLogAndSend = new MessagingException(replyMessage, exceptionToLogAndSend);
                    }
                }
                AbstractMessageProducingHandler.this.logger.error(exceptionToLogAndSend, () -> "Failed to send async reply: " + result.toString());
                this.onFailure(exceptionToLogAndSend);
            }
        }

        public void onFailure(Throwable ex) {
            AbstractMessageProducingHandler.this.sendErrorMessage(this.requestMessage, ex);
        }
    }
}

