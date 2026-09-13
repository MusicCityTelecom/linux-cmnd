/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

final class ParallelFluxName<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<T> source;
    final String name;
    final Set<Tuple2<String, String>> tags;

    static <T> ParallelFlux<T> createOrAppend(ParallelFlux<T> source, String name) {
        Objects.requireNonNull(name, "name");
        if (source instanceof ParallelFluxName) {
            ParallelFluxName s = (ParallelFluxName)source;
            return new ParallelFluxName<T>(s.source, name, s.tags);
        }
        return new ParallelFluxName<T>(source, name, null);
    }

    static <T> ParallelFlux<T> createOrAppend(ParallelFlux<T> source, String tagName, String tagValue) {
        Objects.requireNonNull(tagName, "tagName");
        Objects.requireNonNull(tagValue, "tagValue");
        Set<Tuple2<String, String>> tags = Collections.singleton(Tuples.of(tagName, tagValue));
        if (source instanceof ParallelFluxName) {
            ParallelFluxName s = (ParallelFluxName)source;
            if (s.tags != null) {
                tags = new HashSet<Tuple2<String, String>>(tags);
                tags.addAll(s.tags);
            }
            return new ParallelFluxName<T>(s.source, s.name, tags);
        }
        return new ParallelFluxName<T>(source, null, tags);
    }

    ParallelFluxName(ParallelFlux<T> source, @Nullable String name, @Nullable Set<Tuple2<String, String>> tags) {
        this.source = source;
        this.name = name;
        this.tags = tags;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
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
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        this.source.subscribe(subscribers);
    }
}

