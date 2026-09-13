/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.ReplaceWith
 *  kotlin.Unit
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.reflect.KClass
 *  kotlin.reflect.KDeclarationContainer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Metadata(mv={1, 1, 18}, bv={1, 0, 3}, k=2, d1={"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u0006\u0012\u0002\b\u00030\u0001H\u0087\b\u001aJ\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0005*\u00020\u0006*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u000b0\nH\u0007\u001a#\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u0006\u0012\u0002\b\u00030\u0001H\u0087\b\u001aJ\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0005*\u00020\u0006*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00060\nH\u0007\u001aT\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0005*\u00020\u0006*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\nH\u0007\u001aG\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0005*\u00020\u0006*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b2\u0006\u0010\u0012\u001a\u0002H\u0002H\u0007\u00a2\u0006\u0002\u0010\u0013\u001a2\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0016H\u0007\u001a \u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0016H\u0007\u001a!\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u0002H\u0007\u00a2\u0006\u0002\u0010\u0018\u001a \u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0019H\u0007\u001a\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00020\u001aH\u0007\u001a\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0006H\u0007\u001a\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u001bH\u0007\u00a8\u0006\u001c"}, d2={"cast", "Lreactor/core/publisher/Mono;", "T", "", "doOnError", "E", "", "exceptionType", "Lkotlin/reflect/KClass;", "onError", "Lkotlin/Function1;", "", "ofType", "onErrorMap", "mapper", "onErrorResume", "fallback", "onErrorReturn", "value", "(Lreactor/core/publisher/Mono;Lkotlin/reflect/KClass;Ljava/lang/Object;)Lreactor/core/publisher/Mono;", "switchIfEmpty", "s", "Lkotlin/Function0;", "toMono", "(Ljava/lang/Object;)Lreactor/core/publisher/Mono;", "Ljava/util/concurrent/Callable;", "Ljava/util/concurrent/CompletableFuture;", "Lorg/reactivestreams/Publisher;", "reactor-core"})
public final class MonoExtensionsKt {
    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.toMono"}, expression="toMono()"))
    @NotNull
    public static final <T> Mono<T> toMono(@NotNull Publisher<T> $this$toMono) {
        Intrinsics.checkParameterIsNotNull($this$toMono, (String)"$this$toMono");
        Mono<T> mono = Mono.from($this$toMono);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.from(this)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.toMono"}, expression="toMono()"))
    @NotNull
    public static final <T> Mono<T> toMono(@NotNull Function0<? extends T> $this$toMono) {
        Intrinsics.checkParameterIsNotNull($this$toMono, (String)"$this$toMono");
        Function0<? extends T> function0 = $this$toMono;
        Mono mono = Mono.fromSupplier(new Supplier(function0){
            private final /* synthetic */ Function0 function;
            {
                this.function = function0;
            }

            public final /* synthetic */ Object get() {
                return this.function.invoke();
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.fromSupplier(this)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.toMono"}, expression="toMono()"))
    @NotNull
    public static final <T> Mono<T> toMono(@NotNull T $this$toMono) {
        Intrinsics.checkParameterIsNotNull($this$toMono, (String)"$this$toMono");
        Mono<T> mono = Mono.just($this$toMono);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.just(this)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.toMono"}, expression="toMono()"))
    @NotNull
    public static final <T> Mono<T> toMono(@NotNull CompletableFuture<? extends T> $this$toMono) {
        Intrinsics.checkParameterIsNotNull($this$toMono, (String)"$this$toMono");
        Mono<? extends T> mono = Mono.fromFuture($this$toMono);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.fromFuture(this)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.toMono"}, expression="toMono()"))
    @NotNull
    public static final <T> Mono<T> toMono(@NotNull Callable<T> $this$toMono) {
        Intrinsics.checkParameterIsNotNull($this$toMono, (String)"$this$toMono");
        Function0 function0 = new Function0<T>($this$toMono){

            @Nullable
            public final T invoke() {
                return (T)((Callable)this.receiver).call();
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(Callable.class);
            }

            public final String getName() {
                return "call";
            }

            public final String getSignature() {
                return "call()Ljava/lang/Object;";
            }
        };
        Mono mono = Mono.fromCallable(new Callable(function0){
            private final /* synthetic */ Function0 function;
            {
                this.function = function0;
            }

            public final /* synthetic */ Object call() {
                return this.function.invoke();
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.fromCallable(this::call)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.toMono"}, expression="toMono<T>()"))
    @NotNull
    public static final <T> Mono<T> toMono(@NotNull Throwable $this$toMono) {
        Intrinsics.checkParameterIsNotNull((Object)$this$toMono, (String)"$this$toMono");
        Mono mono = Mono.error($this$toMono);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.error(this)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.cast"}, expression="cast<T>()"))
    public static final /* synthetic */ <T> Mono<T> cast(Mono<?> $this$cast) {
        int $i$f$cast = 0;
        Intrinsics.checkParameterIsNotNull($this$cast, (String)"$this$cast");
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        Mono<Object> mono = $this$cast.cast(Object.class);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"cast(T::class.java)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.doOnError"}, expression="doOnError(exceptionType, onError)"))
    @NotNull
    public static final <T, E extends Throwable> Mono<T> doOnError(@NotNull Mono<T> $this$doOnError, @NotNull KClass<E> exceptionType, @NotNull Function1<? super E, Unit> onError) {
        Intrinsics.checkParameterIsNotNull($this$doOnError, (String)"$this$doOnError");
        Intrinsics.checkParameterIsNotNull(exceptionType, (String)"exceptionType");
        Intrinsics.checkParameterIsNotNull(onError, (String)"onError");
        Mono<T> mono = $this$doOnError.doOnError(JvmClassMappingKt.getJavaClass(exceptionType), new Consumer(onError){
            final /* synthetic */ Function1 $onError;

            public final void accept(E it) {
                E e = it;
                Intrinsics.checkExpressionValueIsNotNull(e, (String)"it");
                this.$onError.invoke(e);
            }
            {
                this.$onError = function1;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"doOnError(exceptionType.java) { onError(it) }");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.onErrorMap"}, expression="onErrorMap(exceptionType, mapper)"))
    @NotNull
    public static final <T, E extends Throwable> Mono<T> onErrorMap(@NotNull Mono<T> $this$onErrorMap, @NotNull KClass<E> exceptionType, @NotNull Function1<? super E, ? extends Throwable> mapper) {
        Intrinsics.checkParameterIsNotNull($this$onErrorMap, (String)"$this$onErrorMap");
        Intrinsics.checkParameterIsNotNull(exceptionType, (String)"exceptionType");
        Intrinsics.checkParameterIsNotNull(mapper, (String)"mapper");
        Mono<T> mono = $this$onErrorMap.onErrorMap(JvmClassMappingKt.getJavaClass(exceptionType), new Function(mapper){
            final /* synthetic */ Function1 $mapper;

            @NotNull
            public final Throwable apply(E it) {
                E e = it;
                Intrinsics.checkExpressionValueIsNotNull(e, (String)"it");
                return (Throwable)this.$mapper.invoke(e);
            }
            {
                this.$mapper = function1;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"onErrorMap(exceptionType.java) { mapper(it) }");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.ofType"}, expression="ofType<T>()"))
    public static final /* synthetic */ <T> Mono<T> ofType(Mono<?> $this$ofType) {
        int $i$f$ofType = 0;
        Intrinsics.checkParameterIsNotNull($this$ofType, (String)"$this$ofType");
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        Mono<Object> mono = $this$ofType.ofType(Object.class);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"ofType(T::class.java)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.onErrorResume"}, expression="onErrorResume(exceptionType, fallback)"))
    @NotNull
    public static final <T, E extends Throwable> Mono<T> onErrorResume(@NotNull Mono<T> $this$onErrorResume, @NotNull KClass<E> exceptionType, @NotNull Function1<? super E, ? extends Mono<T>> fallback) {
        Intrinsics.checkParameterIsNotNull($this$onErrorResume, (String)"$this$onErrorResume");
        Intrinsics.checkParameterIsNotNull(exceptionType, (String)"exceptionType");
        Intrinsics.checkParameterIsNotNull(fallback, (String)"fallback");
        Mono<T> mono = $this$onErrorResume.onErrorResume(JvmClassMappingKt.getJavaClass(exceptionType), new Function(fallback){
            final /* synthetic */ Function1 $fallback;

            @NotNull
            public final Mono<T> apply(E it) {
                E e = it;
                Intrinsics.checkExpressionValueIsNotNull(e, (String)"it");
                return (Mono)this.$fallback.invoke(e);
            }
            {
                this.$fallback = function1;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"onErrorResume(exceptionType.java) { fallback(it) }");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.onErrorReturn"}, expression="onErrorReturn(exceptionType, value)"))
    @NotNull
    public static final <T, E extends Throwable> Mono<T> onErrorReturn(@NotNull Mono<T> $this$onErrorReturn, @NotNull KClass<E> exceptionType, @NotNull T value) {
        Intrinsics.checkParameterIsNotNull($this$onErrorReturn, (String)"$this$onErrorReturn");
        Intrinsics.checkParameterIsNotNull(exceptionType, (String)"exceptionType");
        Intrinsics.checkParameterIsNotNull(value, (String)"value");
        Mono<T> mono = $this$onErrorReturn.onErrorReturn(JvmClassMappingKt.getJavaClass(exceptionType), value);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"onErrorReturn(exceptionType.java, value)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.switchIfEmpty"}, expression="switchIfEmpty(s)"))
    @NotNull
    public static final <T> Mono<T> switchIfEmpty(@NotNull Mono<T> $this$switchIfEmpty, @NotNull Function0<? extends Mono<T>> s) {
        Intrinsics.checkParameterIsNotNull($this$switchIfEmpty, (String)"$this$switchIfEmpty");
        Intrinsics.checkParameterIsNotNull(s, (String)"s");
        Mono<T> mono = $this$switchIfEmpty.switchIfEmpty(Mono.defer(new Supplier(s){
            final /* synthetic */ Function0 $s;

            @NotNull
            public final Mono<T> get() {
                return (Mono)this.$s.invoke();
            }
            {
                this.$s = function0;
            }
        }));
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"this.switchIfEmpty(Mono.defer { s() })");
        return mono;
    }
}

