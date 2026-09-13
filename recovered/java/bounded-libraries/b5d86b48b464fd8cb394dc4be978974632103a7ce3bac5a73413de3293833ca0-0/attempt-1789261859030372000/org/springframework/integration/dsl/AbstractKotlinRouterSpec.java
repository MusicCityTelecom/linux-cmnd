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
import org.springframework.integration.dsl.AbstractRouterSpec;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0014\b\u0000\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0005B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u000bJ\u001f\u0010\u0013\u001a\u00020\u000b2\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u000b0\u0015\u00a2\u0006\u0002\b\u0017J\u000e\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\fR \u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0002X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0019"}, d2={"Lorg/springframework/integration/dsl/AbstractKotlinRouterSpec;", "S", "Lorg/springframework/integration/dsl/AbstractRouterSpec;", "R", "Lorg/springframework/integration/router/AbstractMessageRouter;", "Lorg/springframework/integration/dsl/ConsumerEndpointSpec;", "delegate", "(Lorg/springframework/integration/dsl/AbstractRouterSpec;)V", "getDelegate", "()Lorg/springframework/integration/dsl/AbstractRouterSpec;", "applySequence", "", "", "defaultOutputChannel", "channelName", "", "channel", "Lorg/springframework/messaging/MessageChannel;", "defaultOutputToParentFlow", "defaultSubFlowMapping", "subFlow", "Lkotlin/Function1;", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "Lkotlin/ExtensionFunctionType;", "ignoreSendFailures", "spring-integration-core"})
public abstract class AbstractKotlinRouterSpec<S extends AbstractRouterSpec<S, R>, R extends AbstractMessageRouter>
extends ConsumerEndpointSpec<S, R> {
    @NotNull
    private final AbstractRouterSpec<S, R> delegate;

    public AbstractKotlinRouterSpec(@NotNull AbstractRouterSpec<S, R> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, (String)"delegate");
        super((MessageHandler)delegate.handler);
        this.delegate = delegate;
    }

    @NotNull
    public AbstractRouterSpec<S, R> getDelegate() {
        return this.delegate;
    }

    public final void ignoreSendFailures(boolean ignoreSendFailures) {
        this.getDelegate().ignoreSendFailures(ignoreSendFailures);
    }

    public final void applySequence(boolean applySequence) {
        this.getDelegate().applySequence(applySequence);
    }

    public final void defaultOutputChannel(@NotNull String channelName) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        this.getDelegate().defaultOutputChannel(channelName);
    }

    public final void defaultOutputChannel(@NotNull MessageChannel channel) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        this.getDelegate().defaultOutputChannel(channel);
    }

    public final void defaultSubFlowMapping(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        this.getDelegate().defaultSubFlowMapping(arg_0 -> AbstractKotlinRouterSpec.defaultSubFlowMapping$lambda-0(subFlow, arg_0));
    }

    public final void defaultOutputToParentFlow() {
        this.getDelegate().defaultOutputToParentFlow();
    }

    private static final void defaultSubFlowMapping$lambda-0(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }
}

