/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dsl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.FilterEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\fJ\u001f\u0010\r\u001a\u00020\t2\u0017\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\u0002\b\u0011J\u000e\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0013R\u0011\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0015"}, d2={"Lorg/springframework/integration/dsl/KotlinFilterEndpointSpec;", "Lorg/springframework/integration/dsl/ConsumerEndpointSpec;", "Lorg/springframework/integration/dsl/FilterEndpointSpec;", "Lorg/springframework/integration/filter/MessageFilter;", "delegate", "(Lorg/springframework/integration/dsl/FilterEndpointSpec;)V", "getDelegate", "()Lorg/springframework/integration/dsl/FilterEndpointSpec;", "discardChannel", "", "discardChannelName", "", "Lorg/springframework/messaging/MessageChannel;", "discardFlow", "subFlow", "Lkotlin/Function1;", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "Lkotlin/ExtensionFunctionType;", "discardWithinAdvice", "", "throwExceptionOnRejection", "spring-integration-core"})
public final class KotlinFilterEndpointSpec
extends ConsumerEndpointSpec<FilterEndpointSpec, MessageFilter> {
    @NotNull
    private final FilterEndpointSpec delegate;

    public KotlinFilterEndpointSpec(@NotNull FilterEndpointSpec delegate) {
        Intrinsics.checkParameterIsNotNull((Object)delegate, (String)"delegate");
        super((MessageHandler)delegate.handler);
        this.delegate = delegate;
    }

    @NotNull
    public final FilterEndpointSpec getDelegate() {
        return this.delegate;
    }

    public final void throwExceptionOnRejection(boolean throwExceptionOnRejection) {
        this.delegate.throwExceptionOnRejection(throwExceptionOnRejection);
    }

    public final void discardChannel(@NotNull MessageChannel discardChannel) {
        Intrinsics.checkParameterIsNotNull((Object)discardChannel, (String)"discardChannel");
        this.delegate.discardChannel(discardChannel);
    }

    public final void discardChannel(@NotNull String discardChannelName) {
        Intrinsics.checkParameterIsNotNull((Object)discardChannelName, (String)"discardChannelName");
        this.delegate.discardChannel(discardChannelName);
    }

    public final void discardWithinAdvice(boolean discardWithinAdvice) {
        this.delegate.discardWithinAdvice(discardWithinAdvice);
    }

    public final void discardFlow(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        this.delegate.discardFlow(arg_0 -> KotlinFilterEndpointSpec.discardFlow$lambda-0(subFlow, arg_0));
    }

    private static final void discardFlow$lambda-0(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }
}

