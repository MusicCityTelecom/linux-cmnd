/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.ReplaceWith
 *  kotlin.TypeCastException
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.JvmName
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.List;
import java.util.function.Function;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoBridges;

@Metadata(mv={1, 1, 18}, bv={1, 0, 3}, k=2, d1={"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010 \n\u0000\u001a/\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u001a\u0010\u0003\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004\"\u0006\u0012\u0002\b\u00030\u0005H\u0007\u00a2\u0006\u0002\u0010\u0006\u001aM\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\b2\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00010\u0004\"\u0006\u0012\u0002\b\u00030\u00012\u0016\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u0002H\b0\u000bH\u0007\u00a2\u0006\u0002\u0010\f\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\rH\u0007\u001aG\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\b*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00010\r2\u001a\b\u0004\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u000f\u0012\u0004\u0012\u0002H\b0\u000bH\u0087\b\u00a8\u0006\u0010"}, d2={"whenComplete", "Lreactor/core/publisher/Mono;", "Ljava/lang/Void;", "sources", "", "Lorg/reactivestreams/Publisher;", "([Lorg/reactivestreams/Publisher;)Lreactor/core/publisher/Mono;", "zip", "R", "monos", "combinator", "Lkotlin/Function1;", "([Lreactor/core/publisher/Mono;Lkotlin/jvm/functions/Function1;)Lreactor/core/publisher/Mono;", "", "T", "", "reactor-core"})
@JvmName(name="MonoWhenFunctionsKt")
public final class MonoWhenFunctionsKt {
    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.whenComplete"}, expression="whenComplete()"))
    @NotNull
    public static final Mono<Void> whenComplete(@NotNull Iterable<? extends Publisher<?>> $this$whenComplete) {
        Intrinsics.checkParameterIsNotNull($this$whenComplete, (String)"$this$whenComplete");
        Mono<Void> mono = Mono.when($this$whenComplete);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.`when`(this)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.zip"}, expression="zip(combinator)"))
    @NotNull
    public static final <T, R> Mono<R> zip(@NotNull Iterable<? extends Mono<T>> $this$zip, @NotNull Function1<? super List<? extends T>, ? extends R> combinator) {
        int $i$f$zip = 0;
        Intrinsics.checkParameterIsNotNull($this$zip, (String)"$this$zip");
        Intrinsics.checkParameterIsNotNull(combinator, (String)"combinator");
        Mono mono = Mono.zip($this$zip, new Function(combinator){
            final /* synthetic */ Function1 $combinator;

            public final R apply(Object[] it) {
                Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
                List list = ArraysKt.asList((Object[])it);
                if (list == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<T>");
                }
                return (R)this.$combinator.invoke((Object)list);
            }
            {
                this.$combinator = function1;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"Mono.zip(this) { combina\u2026it.asList() as List<T>) }");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.whenComplete"}, expression="whenComplete(*sources)"))
    @NotNull
    public static final Mono<Void> whenComplete(Publisher<?> ... sources) {
        Intrinsics.checkParameterIsNotNull(sources, (String)"sources");
        Mono<Void> mono = MonoBridges.when(sources);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"MonoBridges.`when`(sources)");
        return mono;
    }

    @Deprecated(message="To be removed in 3.3.0.RELEASE, replaced by module reactor-kotlin-extensions", replaceWith=@ReplaceWith(imports={"reactor.kotlin.core.publisher.zip"}, expression="zip(*monos, combinator)"))
    @NotNull
    public static final <R> Mono<R> zip(@NotNull Mono<?>[] monos, @NotNull Function1<? super Object[], ? extends R> combinator) {
        Intrinsics.checkParameterIsNotNull(monos, (String)"monos");
        Intrinsics.checkParameterIsNotNull(combinator, (String)"combinator");
        Function1<? super Object[], ? extends R> function1 = combinator;
        Mono mono = MonoBridges.zip(new Function(function1){
            private final /* synthetic */ Function1 function;
            {
                this.function = function1;
            }

            public final /* synthetic */ Object apply(Object p0) {
                return this.function.invoke(p0);
            }
        }, monos);
        Intrinsics.checkExpressionValueIsNotNull(mono, (String)"MonoBridges.zip(combinator, monos)");
        return mono;
    }
}

