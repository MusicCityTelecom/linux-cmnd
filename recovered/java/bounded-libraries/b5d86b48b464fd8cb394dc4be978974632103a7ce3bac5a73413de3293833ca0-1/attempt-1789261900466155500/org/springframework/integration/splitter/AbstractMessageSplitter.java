/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.TreeNode
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.integration.splitter;

import com.fasterxml.jackson.core.TreeNode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.DiscardingMessageHandler;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.json.JacksonPresent;
import org.springframework.integration.util.FunctionIterator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractMessageSplitter
extends AbstractReplyProducingMessageHandler
implements DiscardingMessageHandler {
    private boolean applySequence = true;
    private MessageChannel discardChannel;
    private String discardChannelName;

    public void setApplySequence(boolean applySequence) {
        this.applySequence = applySequence;
    }

    public void setDiscardChannel(MessageChannel discardChannel) {
        this.discardChannel = discardChannel;
    }

    public void setDiscardChannelName(String discardChannelName) {
        Assert.hasText((String)discardChannelName, (String)"'discardChannelName' must not be empty");
        this.discardChannelName = discardChannelName;
    }

    @Override
    public MessageChannel getDiscardChannel() {
        String channelName;
        if (this.discardChannel == null && (channelName = this.discardChannelName) != null) {
            this.discardChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            this.discardChannelName = null;
        }
        return this.discardChannel;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.splitter;
    }

    @Override
    protected void doInit() {
        Assert.state((this.discardChannelName == null || this.discardChannel == null ? 1 : 0) != 0, (String)"'discardChannelName' and 'discardChannel' are mutually exclusive.");
    }

    @Override
    protected final Object handleRequestMessage(Message<?> message) {
        Object result = this.splitMessage(message);
        if (result == null) {
            return null;
        }
        boolean reactive = this.getOutputChannel() instanceof ReactiveStreamsSubscribableChannel;
        this.setAsync(reactive);
        if (reactive) {
            return this.prepareFluxResult(message, result);
        }
        return this.prepareIteratorResult(message, result);
    }

    private Flux<?> prepareFluxResult(Message<?> message, Object result) {
        int sequenceSize = 1;
        Flux flux = Flux.just((Object)result);
        if (result instanceof Iterable) {
            Iterable iterable = (Iterable)result;
            sequenceSize = this.obtainSizeIfPossible(iterable);
            flux = Flux.fromIterable((Iterable)iterable);
        } else if (result.getClass().isArray()) {
            Object[] items = ObjectUtils.toObjectArray((Object)result);
            sequenceSize = items.length;
            flux = Flux.fromArray((Object[])items);
        } else if (result instanceof Iterator) {
            Iterator iter = (Iterator)result;
            sequenceSize = this.obtainSizeIfPossible(iter);
            flux = Flux.fromIterable(() -> iter);
        } else if (result instanceof Stream) {
            Stream stream = (Stream)result;
            sequenceSize = 0;
            flux = Flux.fromStream((Stream)stream);
        } else if (result instanceof Publisher) {
            Publisher publisher = (Publisher)result;
            sequenceSize = 0;
            flux = Flux.from((Publisher)publisher);
        }
        Function<Object, ?> messageBuilderFunction = this.prepareMessageBuilderFunction(message, sequenceSize);
        return flux.map(messageBuilderFunction).switchIfEmpty((Publisher)Mono.defer(() -> {
            MessageChannel discardingChannel = this.getDiscardChannel();
            if (discardingChannel != null) {
                this.messagingTemplate.send(discardingChannel, message);
            }
            return Mono.empty();
        }));
    }

    private Iterator<?> prepareIteratorResult(Message<?> message, Object result) {
        int sequenceSize = 1;
        Iterator<Object> iterator = Collections.singleton(result).iterator();
        if (result instanceof Iterable) {
            Iterable iterable = (Iterable)result;
            sequenceSize = this.obtainSizeIfPossible(iterable);
            iterator = iterable.iterator();
        } else if (result.getClass().isArray()) {
            Object[] items = ObjectUtils.toObjectArray((Object)result);
            sequenceSize = items.length;
            iterator = Arrays.asList(items).iterator();
        } else if (result instanceof Iterator) {
            Iterator iter = (Iterator)result;
            sequenceSize = this.obtainSizeIfPossible(iter);
            iterator = iter;
        } else if (result instanceof Stream) {
            Stream stream = (Stream)result;
            sequenceSize = 0;
            iterator = stream.iterator();
        } else if (result instanceof Publisher) {
            sequenceSize = 0;
            iterator = Flux.from((Publisher)((Publisher)result)).toIterable().iterator();
        }
        if (!iterator.hasNext()) {
            MessageChannel discardingChannel = this.getDiscardChannel();
            if (discardingChannel != null) {
                this.messagingTemplate.send(discardingChannel, message);
            }
            return null;
        }
        Function<Object, ?> messageBuilderFunction = this.prepareMessageBuilderFunction(message, sequenceSize);
        return new FunctionIterator(result instanceof AutoCloseable && !result.equals(iterator) ? (AutoCloseable)result : null, iterator, messageBuilderFunction);
    }

    private Function<Object, ?> prepareMessageBuilderFunction(Message<?> message, int sequenceSize) {
        Object messageHeaders = message.getHeaders();
        if (this.willAddHeaders(message)) {
            messageHeaders = new HashMap(messageHeaders);
            this.addHeaders(message, (Map<String, Object>)messageHeaders);
        }
        MessageHeaders headers = messageHeaders;
        UUID correlationId = message.getHeaders().getId();
        AtomicInteger sequenceNumber = new AtomicInteger(1);
        return arg_0 -> this.lambda$prepareMessageBuilderFunction$2((Map)headers, correlationId, sequenceNumber, sequenceSize, arg_0);
    }

    protected int obtainSizeIfPossible(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection)iterable).size();
        }
        if (JacksonPresent.isJackson2Present() && JacksonNodeHelper.isNode(iterable)) {
            return JacksonNodeHelper.nodeSize(iterable);
        }
        return 0;
    }

    protected int obtainSizeIfPossible(Iterator<?> iterator) {
        return 0;
    }

    private AbstractIntegrationMessageBuilder<?> createBuilder(Object item, Map<String, Object> headers, Object correlationId, int sequenceNumber, int sequenceSize) {
        AbstractIntegrationMessageBuilder<Object> builder = this.messageBuilderForReply(item);
        builder.copyHeadersIfAbsent(headers);
        if (this.applySequence) {
            builder.pushSequenceDetails(correlationId, sequenceNumber, sequenceSize);
        }
        return builder;
    }

    protected boolean willAddHeaders(Message<?> message) {
        return false;
    }

    protected void addHeaders(Message<?> message, Map<String, Object> headers) {
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void produceOutput(Object result, Message<?> requestMessage) {
        if (result instanceof Iterator) {
            Iterator iterator = (Iterator)result;
            try {
                while (iterator.hasNext()) {
                    super.produceOutput(iterator.next(), requestMessage);
                }
            }
            finally {
                if (iterator instanceof AutoCloseable) {
                    try {
                        ((AutoCloseable)((Object)iterator)).close();
                    }
                    catch (Exception exception) {}
                }
            }
        }
        super.produceOutput(result, requestMessage);
    }

    @Override
    public String getComponentType() {
        return "splitter";
    }

    protected abstract Object splitMessage(Message<?> var1);

    private /* synthetic */ Object lambda$prepareMessageBuilderFunction$2(Map headers, Object correlationId, AtomicInteger sequenceNumber, int sequenceSize, Object object) {
        return this.createBuilder(object, headers, correlationId, sequenceNumber.getAndIncrement(), sequenceSize);
    }

    private static class JacksonNodeHelper {
        private JacksonNodeHelper() {
        }

        private static boolean isNode(Object object) {
            return object instanceof TreeNode;
        }

        private static int nodeSize(Object node) {
            return ((TreeNode)node).size();
        }
    }
}

