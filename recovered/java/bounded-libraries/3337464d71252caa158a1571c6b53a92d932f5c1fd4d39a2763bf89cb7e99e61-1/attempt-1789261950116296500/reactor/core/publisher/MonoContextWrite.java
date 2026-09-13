/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxContextWrite;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

final class MonoContextWrite<T>
extends InternalMonoOperator<T, T>
implements Fuseable {
    final Function<Context, Context> doOnContext;

    MonoContextWrite(Mono<? extends T> source, Function<Context, Context> doOnContext) {
        super(source);
        this.doOnContext = Objects.requireNonNull(doOnContext, "doOnContext");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Context c = this.doOnContext.apply(actual.currentContext());
        return new FluxContextWrite.ContextWriteSubscriber<T>(actual, c);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

