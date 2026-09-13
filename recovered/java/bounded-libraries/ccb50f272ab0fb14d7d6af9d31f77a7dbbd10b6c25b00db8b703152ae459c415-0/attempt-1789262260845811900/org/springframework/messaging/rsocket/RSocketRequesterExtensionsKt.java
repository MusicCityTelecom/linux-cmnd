/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.transport.ClientTransport
 *  kotlin.Metadata
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.coroutines.Continuation
 *  kotlin.coroutines.intrinsics.IntrinsicsKt
 *  kotlin.coroutines.jvm.internal.ContinuationImpl
 *  kotlin.jvm.internal.InlineMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlinx.coroutines.flow.Flow
 *  kotlinx.coroutines.reactive.AwaitKt
 *  kotlinx.coroutines.reactive.ReactiveFlowKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.reactivestreams.Publisher
 *  org.springframework.core.ParameterizedTypeReference
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket;

import io.rsocket.transport.ClientTransport;
import java.net.URI;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.reactive.AwaitKt;
import kotlinx.coroutines.reactive.ReactiveFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketRequesterExtensionsKt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Metadata(mv={1, 1, 18}, bv={1, 0, 3}, k=2, d1={"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005\u001a%\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u001a\u001d\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000f\u001a!\u0010\u0010\u001a\u00020\u0011\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0013H\u0086\b\u001a'\u0010\u0010\u001a\u00020\u0011\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0017H\u0086\b\u001a'\u0010\u0010\u001a\u00020\u0011\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u00142\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0019H\u0086\b\u001a!\u0010\u001a\u001a\u0002H\u0012\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u0011H\u0086H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u001a#\u0010\u001c\u001a\u0004\u0018\u0001H\u0012\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u0011H\u0086H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u001a\u001f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0017\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u0011H\u0086\b\u001a\u001f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00120\u001f\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u0011H\u0086\b\u001a\u001f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00120!\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u0013*\u00020\u0011H\u0086\b\u001a\u0015\u0010\"\u001a\u00020#*\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006$"}, d2={"connectAndAwait", "Lorg/springframework/messaging/rsocket/RSocketRequester;", "Lorg/springframework/messaging/rsocket/RSocketRequester$Builder;", "transport", "Lio/rsocket/transport/ClientTransport;", "(Lorg/springframework/messaging/rsocket/RSocketRequester$Builder;Lio/rsocket/transport/ClientTransport;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectTcpAndAwait", "host", "", "port", "", "(Lorg/springframework/messaging/rsocket/RSocketRequester$Builder;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectWebSocketAndAwait", "uri", "Ljava/net/URI;", "(Lorg/springframework/messaging/rsocket/RSocketRequester$Builder;Ljava/net/URI;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dataWithType", "Lorg/springframework/messaging/rsocket/RSocketRequester$RetrieveSpec;", "T", "", "Lorg/springframework/messaging/rsocket/RSocketRequester$RequestSpec;", "producer", "flow", "Lkotlinx/coroutines/flow/Flow;", "publisher", "Lorg/reactivestreams/Publisher;", "retrieveAndAwait", "(Lorg/springframework/messaging/rsocket/RSocketRequester$RetrieveSpec;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "retrieveAndAwaitOrNull", "retrieveFlow", "retrieveFlux", "Lreactor/core/publisher/Flux;", "retrieveMono", "Lreactor/core/publisher/Mono;", "sendAndAwait", "", "spring-messaging"})
public final class RSocketRequesterExtensionsKt {
    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final Object connectAndAwait(@NotNull RSocketRequester.Builder var0, @NotNull ClientTransport var1_1, @NotNull Continuation<? super RSocketRequester> var2_2) {
        if (!(var2_2 instanceof connectAndAwait.1)) ** GOTO lbl-1000
        var4_3 = var2_2;
        if ((var4_3.label & -2147483648) != 0) {
            var4_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(var2_2){
                /* synthetic */ Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return RSocketRequesterExtensionsKt.connectAndAwait(null, null, (Continuation<? super RSocketRequester>)this);
                }
            };
        }
        $result = $continuation.result;
        var5_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                v0 = $this$connectAndAwait.connect((ClientTransport)transport);
                Intrinsics.checkExpressionValueIsNotNull(v0, (String)"connect(transport)");
                $continuation.label = 1;
                v1 = AwaitKt.awaitSingle((Publisher)((Publisher)v0), (Continuation)$continuation);
                if (v1 == var5_5) {
                    return var5_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl22:
                // 2 sources

                Intrinsics.checkExpressionValueIsNotNull((Object)v1, (String)"connect(transport).awaitSingle()");
                return v1;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final Object connectTcpAndAwait(@NotNull RSocketRequester.Builder var0, @NotNull String var1_1, int var2_2, @NotNull Continuation<? super RSocketRequester> var3_3) {
        if (!(var3_3 instanceof connectTcpAndAwait.1)) ** GOTO lbl-1000
        var5_4 = var3_3;
        if ((var5_4.label & -2147483648) != 0) {
            var5_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(var3_3){
                /* synthetic */ Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return RSocketRequesterExtensionsKt.connectTcpAndAwait(null, null, 0, (Continuation<? super RSocketRequester>)this);
                }
            };
        }
        $result = $continuation.result;
        var6_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                v0 = $this$connectTcpAndAwait.connectTcp((String)host, (int)port);
                Intrinsics.checkExpressionValueIsNotNull(v0, (String)"connectTcp(host, port)");
                $continuation.label = 1;
                v1 = AwaitKt.awaitSingle((Publisher)((Publisher)v0), (Continuation)$continuation);
                if (v1 == var6_6) {
                    return var6_6;
                }
                ** GOTO lbl22
            }
            case 1: {
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl22:
                // 2 sources

                Intrinsics.checkExpressionValueIsNotNull((Object)v1, (String)"connectTcp(host, port).awaitSingle()");
                return v1;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final Object connectWebSocketAndAwait(@NotNull RSocketRequester.Builder var0, @NotNull URI var1_1, @NotNull Continuation<? super RSocketRequester> var2_2) {
        if (!(var2_2 instanceof connectWebSocketAndAwait.1)) ** GOTO lbl-1000
        var4_3 = var2_2;
        if ((var4_3.label & -2147483648) != 0) {
            var4_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(var2_2){
                /* synthetic */ Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return RSocketRequesterExtensionsKt.connectWebSocketAndAwait(null, null, (Continuation<? super RSocketRequester>)this);
                }
            };
        }
        $result = $continuation.result;
        var5_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure((Object)$result);
                v0 = $this$connectWebSocketAndAwait.connectWebSocket((URI)uri);
                Intrinsics.checkExpressionValueIsNotNull(v0, (String)"connectWebSocket(uri)");
                $continuation.label = 1;
                v1 = AwaitKt.awaitSingle((Publisher)((Publisher)v0), (Continuation)$continuation);
                if (v1 == var5_5) {
                    return var5_5;
                }
                ** GOTO lbl22
            }
            case 1: {
                ResultKt.throwOnFailure((Object)$result);
                v1 = $result;
lbl22:
                // 2 sources

                Intrinsics.checkExpressionValueIsNotNull((Object)v1, (String)"connectWebSocket(uri).awaitSingle()");
                return v1;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static final /* synthetic */ <T> RSocketRequester.RetrieveSpec dataWithType(RSocketRequester.RequestSpec $this$dataWithType, Object producer) {
        int $i$f$dataWithType = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$dataWithType, (String)"$this$dataWithType");
        Intrinsics.checkParameterIsNotNull((Object)producer, (String)"producer");
        Intrinsics.needClassReification();
        RSocketRequester.RetrieveSpec retrieveSpec = $this$dataWithType.data(producer, (ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull((Object)retrieveSpec, (String)"data(producer, object : \u2026zedTypeReference<T>() {})");
        return retrieveSpec;
    }

    public static final /* synthetic */ <T> RSocketRequester.RetrieveSpec dataWithType(RSocketRequester.RequestSpec $this$dataWithType, Publisher<T> publisher) {
        int $i$f$dataWithType = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$dataWithType, (String)"$this$dataWithType");
        Intrinsics.checkParameterIsNotNull(publisher, (String)"publisher");
        Intrinsics.needClassReification();
        RSocketRequester.RetrieveSpec retrieveSpec = $this$dataWithType.data(publisher, (ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull((Object)retrieveSpec, (String)"data(publisher, object :\u2026zedTypeReference<T>() {})");
        return retrieveSpec;
    }

    public static final /* synthetic */ <T> RSocketRequester.RetrieveSpec dataWithType(RSocketRequester.RequestSpec $this$dataWithType, Flow<? extends T> flow) {
        int $i$f$dataWithType = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$dataWithType, (String)"$this$dataWithType");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        Intrinsics.needClassReification();
        RSocketRequester.RetrieveSpec retrieveSpec = $this$dataWithType.data(flow, (ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull((Object)retrieveSpec, (String)"data(flow, object : Para\u2026zedTypeReference<T>() {})");
        return retrieveSpec;
    }

    @Nullable
    public static final Object sendAndAwait(@NotNull RSocketRequester.RetrieveSpec $this$sendAndAwait, @NotNull Continuation<? super Unit> $completion) {
        Mono<Void> mono = $this$sendAndAwait.send();
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"send()");
        Object object = AwaitKt.awaitSingleOrNull((Publisher)((Publisher)mono), $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    public static final /* synthetic */ <T> Object retrieveAndAwait(RSocketRequester.RetrieveSpec $this$retrieveAndAwait, Continuation<? super T> continuation) {
        int $i$f$retrieveAndAwait = 0;
        Intrinsics.needClassReification();
        Mono mono = $this$retrieveAndAwait.retrieveMono((ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"retrieveMono(object : Pa\u2026zedTypeReference<T>() {})");
        Publisher publisher = (Publisher)mono;
        InlineMarker.mark((int)0);
        Object object = AwaitKt.awaitSingle((Publisher)publisher, continuation);
        InlineMarker.mark((int)1);
        Intrinsics.checkExpressionValueIsNotNull((Object)object, (String)"retrieveMono(object : Pa\u2026ce<T>() {}).awaitSingle()");
        return object;
    }

    public static final /* synthetic */ <T> Object retrieveAndAwaitOrNull(RSocketRequester.RetrieveSpec $this$retrieveAndAwaitOrNull, Continuation<? super T> continuation) {
        int $i$f$retrieveAndAwaitOrNull = 0;
        Intrinsics.needClassReification();
        Mono mono = $this$retrieveAndAwaitOrNull.retrieveMono((ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"retrieveMono(object : Pa\u2026zedTypeReference<T>() {})");
        Publisher publisher = (Publisher)mono;
        InlineMarker.mark((int)0);
        Object object = AwaitKt.awaitSingleOrNull((Publisher)publisher, continuation);
        InlineMarker.mark((int)1);
        return object;
    }

    public static final /* synthetic */ <T> Flow<T> retrieveFlow(RSocketRequester.RetrieveSpec $this$retrieveFlow) {
        int $i$f$retrieveFlow = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$retrieveFlow, (String)"$this$retrieveFlow");
        Intrinsics.needClassReification();
        Flux flux = $this$retrieveFlow.retrieveFlux((ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull(flux, (String)"retrieveFlux(object : Pa\u2026zedTypeReference<T>() {})");
        return ReactiveFlowKt.asFlow((Publisher)((Publisher)flux));
    }

    public static final /* synthetic */ <T> Mono<T> retrieveMono(RSocketRequester.RetrieveSpec $this$retrieveMono) {
        int $i$f$retrieveMono = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$retrieveMono, (String)"$this$retrieveMono");
        Intrinsics.needClassReification();
        Mono mono = $this$retrieveMono.retrieveMono((ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"retrieveMono(object : Pa\u2026zedTypeReference<T>() {})");
        return mono;
    }

    public static final /* synthetic */ <T> Flux<T> retrieveFlux(RSocketRequester.RetrieveSpec $this$retrieveFlux) {
        int $i$f$retrieveFlux = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$retrieveFlux, (String)"$this$retrieveFlux");
        Intrinsics.needClassReification();
        Flux flux = $this$retrieveFlux.retrieveFlux((ParameterizedTypeReference)new ParameterizedTypeReference<T>(){});
        Intrinsics.checkExpressionValueIsNotNull(flux, (String)"retrieveFlux(object : Pa\u2026zedTypeReference<T>() {})");
        return flux;
    }
}

