/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jooq.lambda.Seq;

final class SeqBuffer<T> {
    private final Spliterator<T> source;
    private final List<T> buffer = new ArrayList<T>();
    private volatile boolean buffering = true;

    static <T> SeqBuffer<T> of(Stream<? extends T> stream) {
        return SeqBuffer.of(stream.spliterator());
    }

    static <T> SeqBuffer<T> of(Spliterator<T> spliterator) {
        if (spliterator instanceof BufferSpliterator) {
            return ((BufferSpliterator)spliterator).parentSeqBuffer();
        }
        return new SeqBuffer<T>(spliterator);
    }

    private SeqBuffer(Spliterator<T> source) {
        this.source = Objects.requireNonNull(source);
    }

    Seq<T> seq() {
        return Seq.seq(new BufferSpliterator());
    }

    private class BufferSpliterator
    implements Spliterator<T> {
        private int nextIndex = 0;

        private BufferSpliterator() {
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            return SeqBuffer.this.buffering ? this.tryAdvanceThisWithBuffering(action) : this.tryAdvanceThisAtOnce(action);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean tryAdvanceThisWithBuffering(Consumer<? super T> action) {
            Object next;
            List list = SeqBuffer.this.buffer;
            synchronized (list) {
                if (!this.canAdvanceThisWithBuffering()) {
                    return false;
                }
                next = this.advanceThis();
            }
            action.accept(next);
            return true;
        }

        private boolean canAdvanceThisWithBuffering() {
            return this.canAdvanceThisAtOnce() || this.tryAdvanceSource() && this.canAdvanceThisAtOnce();
        }

        private boolean canAdvanceThisAtOnce() {
            return this.nextIndex < SeqBuffer.this.buffer.size();
        }

        private boolean tryAdvanceSource() {
            boolean canAdvanceSource = SeqBuffer.this.buffering;
            if (!canAdvanceSource) {
                return false;
            }
            while ((canAdvanceSource = SeqBuffer.this.source.tryAdvance(SeqBuffer.this.buffer::add)) && !this.canAdvanceThisAtOnce()) {
            }
            SeqBuffer.this.buffering = canAdvanceSource;
            return true;
        }

        private T advanceThis() {
            return SeqBuffer.this.buffer.get(this.nextIndex++);
        }

        private boolean tryAdvanceThisAtOnce(Consumer<? super T> action) {
            if (!this.canAdvanceThisAtOnce()) {
                return false;
            }
            action.accept(this.advanceThis());
            return true;
        }

        @Override
        public long estimateSize() {
            return SeqBuffer.this.buffering ? this.estimateSizeDuringBuffering() : (long)this.numberOfElementsLeftInBuffer();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private long estimateSizeDuringBuffering() {
            List list = SeqBuffer.this.buffer;
            synchronized (list) {
                int leftInBuffer = this.numberOfElementsLeftInBuffer();
                if (!SeqBuffer.this.buffering) {
                    return leftInBuffer;
                }
                long estimateSize = (long)leftInBuffer + SeqBuffer.this.source.estimateSize();
                long l = estimateSize >= 0L ? estimateSize : Long.MAX_VALUE;
                return l;
            }
        }

        private int numberOfElementsLeftInBuffer() {
            return SeqBuffer.this.buffer.size() - this.nextIndex;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public int characteristics() {
            return SeqBuffer.this.source.characteristics() & 0xFFFFEFFF | 0x10 | (SeqBuffer.this.buffering ? 0 : 64);
        }

        @Override
        public Comparator<? super T> getComparator() {
            return SeqBuffer.this.source.getComparator();
        }

        SeqBuffer<T> parentSeqBuffer() {
            return SeqBuffer.this;
        }
    }
}

