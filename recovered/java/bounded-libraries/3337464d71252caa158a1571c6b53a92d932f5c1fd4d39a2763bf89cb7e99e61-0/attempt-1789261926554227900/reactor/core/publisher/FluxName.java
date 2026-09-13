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
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxNameFuseable;
import reactor.core.publisher.InternalFluxOperator;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

final class FluxName<T>
extends InternalFluxOperator<T, T> {
    final String name;
    final Set<Tuple2<String, String>> tags;

    static <T> Flux<T> createOrAppend(Flux<T> source, String name) {
        Objects.requireNonNull(name, "name");
        if (source instanceof FluxName) {
            FluxName s = (FluxName)source;
            return new FluxName<T>(s.source, name, s.tags);
        }
        if (source instanceof FluxNameFuseable) {
            FluxNameFuseable s = (FluxNameFuseable)source;
            return new FluxNameFuseable(s.source, name, s.tags);
        }
        if (source instanceof Fuseable) {
            return new FluxNameFuseable<T>(source, name, null);
        }
        return new FluxName<T>(source, name, null);
    }

    static <T> Flux<T> createOrAppend(Flux<T> source, String tagName, String tagValue) {
        Objects.requireNonNull(tagName, "tagName");
        Objects.requireNonNull(tagValue, "tagValue");
        Set<Tuple2<String, String>> tags = Collections.singleton(Tuples.of(tagName, tagValue));
        if (source instanceof FluxName) {
            FluxName s = (FluxName)source;
            if (s.tags != null) {
                tags = new HashSet<Tuple2<String, String>>(tags);
                tags.addAll(s.tags);
            }
            return new FluxName<T>(s.source, s.name, tags);
        }
        if (source instanceof FluxNameFuseable) {
            FluxNameFuseable s = (FluxNameFuseable)source;
            if (s.tags != null) {
                tags = new HashSet<Tuple2<String, String>>(tags);
                tags.addAll(s.tags);
            }
            return new FluxNameFuseable(s.source, s.name, tags);
        }
        if (source instanceof Fuseable) {
            return new FluxNameFuseable<T>(source, null, tags);
        }
        return new FluxName<T>(source, null, tags);
    }

    FluxName(Flux<? extends T> source, @Nullable String name, @Nullable Set<Tuple2<String, String>> tags) {
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

