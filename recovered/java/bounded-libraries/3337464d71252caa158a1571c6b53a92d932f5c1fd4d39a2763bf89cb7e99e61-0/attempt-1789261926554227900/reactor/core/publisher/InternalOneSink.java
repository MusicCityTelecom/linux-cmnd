/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.Exceptions;
import reactor.core.publisher.InternalEmptySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;

interface InternalOneSink<T>
extends Sinks.One<T>,
InternalEmptySink<T> {
    @Override
    default public void emitValue(@Nullable T value, Sinks.EmitFailureHandler failureHandler) {
        Sinks.EmitResult emitResult;
        boolean shouldRetry;
        if (value == null) {
            this.emitEmpty(failureHandler);
            return;
        }
        do {
            if (!(emitResult = this.tryEmitValue(value)).isSuccess()) continue;
            return;
        } while (shouldRetry = failureHandler.onEmitFailure(SignalType.ON_NEXT, emitResult));
        switch (emitResult) {
            case FAIL_ZERO_SUBSCRIBER: {
                return;
            }
            case FAIL_OVERFLOW: {
                Operators.onDiscard(value, this.currentContext());
                this.emitError(Exceptions.failWithOverflow("Backpressure overflow during Sinks.One#emitValue"), failureHandler);
                return;
            }
            case FAIL_CANCELLED: {
                Operators.onDiscard(value, this.currentContext());
                return;
            }
            case FAIL_TERMINATED: {
                Operators.onNextDropped(value, this.currentContext());
                return;
            }
            case FAIL_NON_SERIALIZED: {
                throw new Sinks.EmissionException(emitResult, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
        }
        throw new Sinks.EmissionException(emitResult, "Unknown emitResult value");
    }
}

