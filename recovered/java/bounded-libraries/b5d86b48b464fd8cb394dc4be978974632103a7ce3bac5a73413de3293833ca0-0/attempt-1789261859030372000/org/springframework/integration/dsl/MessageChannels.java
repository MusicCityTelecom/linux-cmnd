/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.dsl;

import java.util.Queue;
import java.util.concurrent.Executor;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.ExecutorChannelSpec;
import org.springframework.integration.dsl.FluxMessageChannelSpec;
import org.springframework.integration.dsl.PriorityChannelSpec;
import org.springframework.integration.dsl.PublishSubscribeChannelSpec;
import org.springframework.integration.dsl.QueueChannelSpec;
import org.springframework.integration.dsl.RendezvousChannelSpec;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.PriorityCapableChannelMessageStore;
import org.springframework.messaging.Message;

public final class MessageChannels {
    public static DirectChannelSpec direct() {
        return new DirectChannelSpec();
    }

    public static DirectChannelSpec direct(String id) {
        return (DirectChannelSpec)MessageChannels.direct().id(id);
    }

    public static QueueChannelSpec queue() {
        return new QueueChannelSpec();
    }

    public static QueueChannelSpec queue(String id) {
        return (QueueChannelSpec)MessageChannels.queue().id(id);
    }

    public static QueueChannelSpec queue(Queue<Message<?>> queue) {
        return new QueueChannelSpec(queue);
    }

    public static QueueChannelSpec queue(String id, Queue<Message<?>> queue) {
        return (QueueChannelSpec)MessageChannels.queue(queue).id(id);
    }

    public static QueueChannelSpec queue(Integer capacity) {
        return new QueueChannelSpec(capacity);
    }

    public static QueueChannelSpec queue(String id, Integer capacity) {
        return (QueueChannelSpec)MessageChannels.queue(capacity).id(id);
    }

    public static QueueChannelSpec.MessageStoreSpec queue(ChannelMessageStore messageGroupStore, Object groupId) {
        return new QueueChannelSpec.MessageStoreSpec(messageGroupStore, groupId);
    }

    public static QueueChannelSpec.MessageStoreSpec queue(String id, ChannelMessageStore messageGroupStore, Object groupId) {
        return MessageChannels.queue(messageGroupStore, groupId).id(id);
    }

    public static ExecutorChannelSpec executor(Executor executor) {
        return new ExecutorChannelSpec(executor);
    }

    public static ExecutorChannelSpec executor(String id, Executor executor) {
        return (ExecutorChannelSpec)MessageChannels.executor(executor).id(id);
    }

    public static RendezvousChannelSpec rendezvous() {
        return new RendezvousChannelSpec();
    }

    public static RendezvousChannelSpec rendezvous(String id) {
        return (RendezvousChannelSpec)MessageChannels.rendezvous().id(id);
    }

    public static PriorityChannelSpec priority() {
        return new PriorityChannelSpec();
    }

    public static PriorityChannelSpec priority(String id) {
        return (PriorityChannelSpec)MessageChannels.priority().id(id);
    }

    public static PriorityChannelSpec priority(PriorityCapableChannelMessageStore messageGroupStore, Object groupId) {
        return MessageChannels.priority().messageStore(messageGroupStore, groupId);
    }

    public static PriorityChannelSpec priority(String id, PriorityCapableChannelMessageStore messageGroupStore, Object groupId) {
        return (PriorityChannelSpec)MessageChannels.priority(messageGroupStore, groupId).id(id);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe() {
        return MessageChannels.publishSubscribe(false);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(boolean requireSubscribers) {
        return new PublishSubscribeChannelSpec(requireSubscribers);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(String id) {
        return MessageChannels.publishSubscribe(id, false);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(String id, boolean requireSubscribers) {
        return (PublishSubscribeChannelSpec)MessageChannels.publishSubscribe(requireSubscribers).id(id);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(Executor executor) {
        return MessageChannels.publishSubscribe(executor, false);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(Executor executor, boolean requireSubscribers) {
        return new PublishSubscribeChannelSpec(executor, requireSubscribers);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(String id, Executor executor) {
        return MessageChannels.publishSubscribe(id, executor, false);
    }

    public static PublishSubscribeChannelSpec<?> publishSubscribe(String id, Executor executor, boolean requireSubscribers) {
        return (PublishSubscribeChannelSpec)MessageChannels.publishSubscribe(executor, requireSubscribers).id(id);
    }

    public static FluxMessageChannelSpec flux() {
        return new FluxMessageChannelSpec();
    }

    public static FluxMessageChannelSpec flux(String id) {
        return (FluxMessageChannelSpec)MessageChannels.flux().id(id);
    }

    private MessageChannels() {
    }
}

