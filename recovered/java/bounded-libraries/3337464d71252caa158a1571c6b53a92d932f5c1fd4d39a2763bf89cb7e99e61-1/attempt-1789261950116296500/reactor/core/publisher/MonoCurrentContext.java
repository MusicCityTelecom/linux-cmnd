/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

@Deprecated
final class MonoCurrentContext
extends Mono<Context>
implements Fuseable,
Scannable {
    static final MonoCurrentContext INSTANCE = new MonoCurrentContext();

    MonoCurrentContext() {
    }

    @Override
    public void subscribe(CoreSubscriber<? super Context> actual) {
        Context ctx = actual.currentContext();
        actual.onSubscribe(Operators.scalarSubscription(actual, ctx));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

