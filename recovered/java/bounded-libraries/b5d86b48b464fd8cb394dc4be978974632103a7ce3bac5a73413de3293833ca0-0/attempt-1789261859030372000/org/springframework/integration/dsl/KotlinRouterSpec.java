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
 */
package org.springframework.integration.dsl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.integration.dsl.AbstractKotlinRouterSpec;
import org.springframework.integration.dsl.AbstractRouterSpec;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.messaging.MessageChannel;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000eJ\u000e\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0018J,\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u00002\u0017\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000b0\u001b\u00a2\u0006\u0002\b\u001d\u00a2\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000eR \u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006 "}, d2={"Lorg/springframework/integration/dsl/KotlinRouterSpec;", "K", "R", "Lorg/springframework/integration/router/AbstractMappingMessageRouter;", "Lorg/springframework/integration/dsl/AbstractKotlinRouterSpec;", "Lorg/springframework/integration/dsl/RouterSpec;", "delegate", "(Lorg/springframework/integration/dsl/RouterSpec;)V", "getDelegate", "()Lorg/springframework/integration/dsl/RouterSpec;", "channelMapping", "", "key", "channelName", "", "(Ljava/lang/Object;Ljava/lang/String;)V", "channel", "Lorg/springframework/messaging/MessageChannel;", "(Ljava/lang/Object;Lorg/springframework/messaging/MessageChannel;)V", "dynamicChannelLimit", "", "noChannelKeyFallback", "prefix", "resolutionRequired", "", "subFlowMapping", "subFlow", "Lkotlin/Function1;", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "suffix", "spring-integration-core"})
public final class KotlinRouterSpec<K, R extends AbstractMappingMessageRouter>
extends AbstractKotlinRouterSpec<RouterSpec<K, R>, R> {
    @NotNull
    private final RouterSpec<K, R> delegate;

    public KotlinRouterSpec(@NotNull RouterSpec<K, R> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, (String)"delegate");
        super((AbstractRouterSpec)delegate);
        this.delegate = delegate;
    }

    @Override
    @NotNull
    public RouterSpec<K, R> getDelegate() {
        return this.delegate;
    }

    public final void resolutionRequired(boolean resolutionRequired) {
        ((RouterSpec)this.getDelegate()).resolutionRequired(resolutionRequired);
    }

    public final void dynamicChannelLimit(int dynamicChannelLimit) {
        ((RouterSpec)this.getDelegate()).dynamicChannelLimit(dynamicChannelLimit);
    }

    public final void prefix(@NotNull String prefix) {
        Intrinsics.checkParameterIsNotNull((Object)prefix, (String)"prefix");
        ((RouterSpec)this.getDelegate()).prefix(prefix);
    }

    public final void suffix(@NotNull String suffix) {
        Intrinsics.checkParameterIsNotNull((Object)suffix, (String)"suffix");
        ((RouterSpec)this.getDelegate()).suffix(suffix);
    }

    public final void noChannelKeyFallback() {
        ((RouterSpec)this.getDelegate()).noChannelKeyFallback();
    }

    public final void channelMapping(K key, @NotNull String channelName) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        ((RouterSpec)this.getDelegate()).channelMapping(key, channelName);
    }

    public final void channelMapping(K key, @NotNull MessageChannel channel) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        ((RouterSpec)this.getDelegate()).channelMapping(key, channel);
    }

    public final void subFlowMapping(K key, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        ((RouterSpec)this.getDelegate()).subFlowMapping(key, arg_0 -> KotlinRouterSpec.subFlowMapping$lambda-0(subFlow, arg_0));
    }

    private static final void subFlowMapping$lambda-0(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }
}

