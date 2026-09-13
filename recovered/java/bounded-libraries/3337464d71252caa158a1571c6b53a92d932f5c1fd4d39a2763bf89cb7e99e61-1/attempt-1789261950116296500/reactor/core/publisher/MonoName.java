/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoNameFuseable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

final class MonoName<T>
extends InternalMonoOperator<T, T> {
    final String name;
    final Set<Tuple2<String, String>> tags;

    static <T> Mono<T> createOrAppend(Mono<T> source, String name) {
        Objects.requireNonNull(name, "name");
        if (source instanceof MonoName) {
            MonoName s = (MonoName)source;
            return new MonoName<T>(s.source, name, s.tags);
        }
        if (source instanceof MonoNameFuseable) {
            MonoNameFuseable s = (MonoNameFuseable)source;
            return new MonoNameFuseable(s.source, name, s.tags);
        }
        if (source instanceof Fuseable) {
            return new MonoNameFuseable<T>(source, name, null);
        }
        return new MonoName<T>(source, name, null);
    }

    static <T> Mono<T> createOrAppend(Mono<T> source, String tagName, String tagValue) {
        Objects.requireNonNull(tagName, "tagName");
        Objects.requireNonNull(tagValue, "tagValue");
        Set<Tuple2<String, String>> tags = Collections.singleton(Tuples.of(tagName, tagValue));
        if (source instanceof MonoName) {
            MonoName s = (MonoName)source;
            if (s.tags != null) {
                tags = new HashSet<Tuple2<String, String>>(tags);
                tags.addAll(s.tags);
            }
            return new MonoName<T>(s.source, s.name, tags);
        }
        if (source instanceof MonoNameFuseable) {
            MonoNameFuseable s = (MonoNameFuseable)source;
            if (s.tags != null) {
                tags = new HashSet<Tuple2<String, String>>(tags);
                tags.addAll(s.tags);
            }
            return new MonoNameFuseable(s.source, s.name, tags);
        }
        if (source instanceof Fuseable) {
            return new MonoNameFuseable<T>(source, null, tags);
        }
        return new MonoName<T>(source, null, tags);
    }

    MonoName(Mono<? extends T> source, @Nullable String name, @Nullable Set<Tuple2<String, String>> tags) {
        super(source);
        this.name = name;
        this.tags = tags;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return actual;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.NAME) {
            return this.name;
        }
        if (key == Scannable.Attr.TAGS && this.tags != null) {
            return this.tags.stream();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

