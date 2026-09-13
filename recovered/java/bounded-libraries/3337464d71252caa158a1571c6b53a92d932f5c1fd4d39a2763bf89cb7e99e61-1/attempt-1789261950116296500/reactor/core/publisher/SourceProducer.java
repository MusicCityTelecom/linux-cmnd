/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

interface SourceProducer<O>
extends Scannable,
Publisher<O> {
    @Override
    @Nullable
    default public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return Scannable.from(null);
        }
        if (key == Scannable.Attr.ACTUAL) {
            return Scannable.from(null);
        }
        return null;
    }

    @Override
    default public String stepName() {
        return "source(" + this.getClass().getSimpleName() + ")";
    }
}

