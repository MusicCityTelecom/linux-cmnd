/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.concurrent;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import reactor.util.annotation.Nullable;

final class MpscLinkedQueue<E>
extends AbstractQueue<E>
implements BiPredicate<E, E> {
    private volatile LinkedQueueNode<E> producerNode;
    private static final AtomicReferenceFieldUpdater<MpscLinkedQueue, LinkedQueueNode> PRODUCER_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueue.class, LinkedQueueNode.class, "producerNode");
    private volatile LinkedQueueNode<E> consumerNode;
    private static final AtomicReferenceFieldUpdater<MpscLinkedQueue, LinkedQueueNode> CONSUMER_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueue.class, LinkedQueueNode.class, "consumerNode");

    public MpscLinkedQueue() {
        LinkedQueueNode node = new LinkedQueueNode();
        CONSUMER_NODE_UPDATER.lazySet(this, node);
        PRODUCER_NODE_UPDATER.getAndSet(this, node);
    }

    @Override
    public final boolean offer(E e) {
        Objects.requireNonNull(e, "The offered value 'e' must be non-null");
        LinkedQueueNode<E> nextNode = new LinkedQueueNode<E>(e);
        LinkedQueueNode<E> prevProducerNode = PRODUCER_NODE_UPDATER.getAndSet(this, nextNode);
        prevProducerNode.soNext(nextNode);
        return true;
    }

    @Override
    public boolean test(E e1, E e2) {
        Objects.requireNonNull(e1, "The offered value 'e1' must be non-null");
        Objects.requireNonNull(e2, "The offered value 'e2' must be non-null");
        LinkedQueueNode<E> nextNode = new LinkedQueueNode<E>(e1);
        LinkedQueueNode<E> nextNextNode = new LinkedQueueNode<E>(e2);
        LinkedQueueNode<E> prevProducerNode = PRODUCER_NODE_UPDATER.getAndSet(this, nextNextNode);
        nextNode.soNext(nextNextNode);
        prevProducerNode.soNext(nextNode);
        return true;
    }

    @Override
    @Nullable
    public E poll() {
        LinkedQueueNode<E> currConsumerNode = this.consumerNode;
        LinkedQueueNode<E> nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            E nextValue = nextNode.getAndNullValue();
            currConsumerNode.soNext(currConsumerNode);
            CONSUMER_NODE_UPDATER.lazySet(this, nextNode);
            return nextValue;
        }
        if (currConsumerNode != this.producerNode) {
            while ((nextNode = currConsumerNode.lvNext()) == null) {
            }
            E nextValue = nextNode.getAndNullValue();
            currConsumerNode.soNext(currConsumerNode);
            CONSUMER_NODE_UPDATER.lazySet(this, nextNode);
            return nextValue;
        }
        return null;
    }

    @Override
    @Nullable
    public E peek() {
        LinkedQueueNode<E> currConsumerNode = this.consumerNode;
        LinkedQueueNode<E> nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            return nextNode.lpValue();
        }
        if (currConsumerNode != this.producerNode) {
            while ((nextNode = currConsumerNode.lvNext()) == null) {
            }
            return nextNode.lpValue();
        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        while (this.poll() != null && !this.isEmpty()) {
        }
    }

    @Override
    public int size() {
        int size;
        LinkedQueueNode<E> chaserNode = this.consumerNode;
        LinkedQueueNode<E> producerNode = this.producerNode;
        for (size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; ++size) {
            LinkedQueueNode<E> next = chaserNode.lvNext();
            if (next == chaserNode) {
                return size;
            }
            chaserNode = next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.consumerNode == this.producerNode;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    static final class LinkedQueueNode<E> {
        private volatile LinkedQueueNode<E> next;
        private static final AtomicReferenceFieldUpdater<LinkedQueueNode, LinkedQueueNode> NEXT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(LinkedQueueNode.class, LinkedQueueNode.class, "next");
        private E value;

        LinkedQueueNode() {
            this(null);
        }

        LinkedQueueNode(@Nullable E val) {
            this.spValue(val);
        }

        @Nullable
        public E getAndNullValue() {
            E temp = this.lpValue();
            this.spValue(null);
            return temp;
        }

        @Nullable
        public E lpValue() {
            return this.value;
        }

        public void spValue(@Nullable E newValue) {
            this.value = newValue;
        }

        public void soNext(@Nullable LinkedQueueNode<E> n) {
            NEXT_UPDATER.lazySet(this, n);
        }

        @Nullable
        public LinkedQueueNode<E> lvNext() {
            return this.next;
        }
    }
}

