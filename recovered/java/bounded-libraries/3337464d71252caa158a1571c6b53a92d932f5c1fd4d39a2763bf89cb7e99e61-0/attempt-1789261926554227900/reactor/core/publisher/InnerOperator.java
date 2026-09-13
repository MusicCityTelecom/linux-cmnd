/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.util.context.Context;

interface InnerOperator<I, O>
extends InnerConsumer<I>,
InnerProducer<O> {
    @Override
    default public Context currentContext() {
        return this.actual().currentContext();
    }
}

