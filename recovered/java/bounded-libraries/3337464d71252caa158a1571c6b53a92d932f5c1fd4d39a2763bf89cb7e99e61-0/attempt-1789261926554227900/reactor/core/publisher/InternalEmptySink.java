/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;

interface InternalEmptySink<T>
extends Sinks.Empty<T>,
ContextHolder {
    @Override
    default public void emitEmpty(Sinks.EmitFailureHandler failureHandler) {
        Sinks.EmitResult emitResult;
        boolean shouldRetry;
        do {
            if (!(emitResult = this.tryEmitEmpty()).isSuccess()) continue;
            return;
        } while (shouldRetry = failureHandler.onEmitFailure(SignalType.ON_COMPLETE, emitResult));
        switch (emitResult) {
            case FAIL_ZERO_SUBSCRIBER: 
            case FAIL_OVERFLOW: 
            case FAIL_CANCELLED: 
            case FAIL_TERMINATED: {
                return;
            }
            case FAIL_NON_SERIALIZED: {
                throw new Sinks.EmissionException(emitResult, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
        }
        throw new Sinks.EmissionException(emitResult, "Unknown emitResult value");
    }

    @Override
    default public void emitError(Throwable error, Sinks.EmitFailureHandler failureHandler) {
        Sinks.EmitResult emitResult;
        boolean shouldRetry;
        do {
            if (!(emitResult = this.tryEmitError(error)).isSuccess()) continue;
            return;
        } while (shouldRetry = failureHandler.onEmitFailure(SignalType.ON_ERROR, emitResult));
        switch (emitResult) {
            case FAIL_ZERO_SUBSCRIBER: 
            case FAIL_OVERFLOW: 
            case FAIL_CANCELLED: {
                return;
            }
            case FAIL_TERMINATED: {
                Operators.onErrorDropped(error, this.currentContext());
                return;
            }
            case FAIL_NON_SERIALIZED: {
                throw new Sinks.EmissionException(emitResult, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
        }
        throw new Sinks.EmissionException(emitResult, "Unknown emitResult value");
    }
}

