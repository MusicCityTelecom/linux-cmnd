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
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PriorityChannelSpec;
import org.springframework.integration.dsl.PublishSubscribeChannelSpec;
import org.springframework.integration.dsl.QueueChannelSpec;
import org.springframework.integration.dsl.RendezvousChannelSpec;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.PriorityCapableChannelMessageStore;
import org.springframework.messaging.Message;

public final class Channels {
    static final Channels INSTANCE = new Channels();

    public DirectChannelSpec direct() {
        return MessageChannels.direct();
    }

    public DirectChannelSpec direct(String id) {
        return MessageChannels.direct(id);
    }

    public QueueChannelSpec queue() {
        return MessageChannels.queue();
    }

    public QueueChannelSpec queue(String id) {
        return MessageChannels.queue(id);
    }

    public QueueChannelSpec queue(Integer capacity) {
        return MessageChannels.queue(capacity);
    }

    public QueueChannelSpec queue(String id, Integer capacity) {
        return MessageChannels.queue(id, capacity);
    }

    public QueueChannelSpec queue(Queue<Message<?>> queue) {
        return MessageChannels.queue(queue);
    }

    public QueueChannelSpec queue(String id, Queue<Message<?>> queue) {
        return MessageChannels.queue(id, queue);
    }

    public QueueChannelSpec.MessageStoreSpec queue(ChannelMessageStore messageGroupStore, Object groupId) {
        return MessageChannels.queue(messageGroupStore, groupId);
    }

    public QueueChannelSpec.MessageStoreSpec queue(String id, ChannelMessageStore messageGroupStore, Object groupId) {
        return MessageChannels.queue(id, messageGroupStore, groupId);
    }

    public PriorityChannelSpec priority() {
        return MessageChannels.priority();
    }

    public PriorityChannelSpec priority(String id) {
        return MessageChannels.priority(id);
    }

    public PriorityChannelSpec priority(String id, PriorityCapableChannelMessageStore messageGroupStore, Object groupId) {
        return MessageChannels.priority(id, messageGroupStore, groupId);
    }

    public RendezvousChannelSpec rendezvous() {
        return MessageChannels.rendezvous();
    }

    public PriorityChannelSpec priority(PriorityCapableChannelMessageStore messageGroupStore, Object groupId) {
        return MessageChannels.priority(messageGroupStore, groupId);
    }

    public RendezvousChannelSpec rendezvous(String id) {
        return MessageChannels.rendezvous(id);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe() {
        return MessageChannels.publishSubscribe();
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(boolean requireSubscribers) {
        return MessageChannels.publishSubscribe(requireSubscribers);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(Executor executor) {
        return MessageChannels.publishSubscribe(executor);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(Executor executor, boolean requireSubscribers) {
        return MessageChannels.publishSubscribe(executor, requireSubscribers);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(String id, Executor executor) {
        return MessageChannels.publishSubscribe(id, executor);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(String id, Executor executor, boolean requireSubscribers) {
        return MessageChannels.publishSubscribe(id, executor, requireSubscribers);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(String id) {
        return MessageChannels.publishSubscribe(id);
    }

    public PublishSubscribeChannelSpec<?> publishSubscribe(String id, boolean requireSubscribers) {
        return MessageChannels.publishSubscribe(id, requireSubscribers);
    }

    public ExecutorChannelSpec executor(Executor executor) {
        return MessageChannels.executor(executor);
    }

    public ExecutorChannelSpec executor(String id, Executor executor) {
        return MessageChannels.executor(id, executor);
    }

    public FluxMessageChannelSpec flux() {
        return MessageChannels.flux();
    }

    public FluxMessageChannelSpec flux(String id) {
        return MessageChannels.flux(id);
    }

    private Channels() {
    }
}

