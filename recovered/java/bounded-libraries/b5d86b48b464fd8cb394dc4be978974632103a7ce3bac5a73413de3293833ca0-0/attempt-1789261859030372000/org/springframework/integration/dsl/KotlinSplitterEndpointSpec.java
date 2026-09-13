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
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.dsl.SplitterEndpointSpec;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0000\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u0010J\u001f\u0010\u0011\u001a\u00020\n2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\n0\u0012\u00a2\u0006\u0002\b\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0015"}, d2={"Lorg/springframework/integration/dsl/KotlinSplitterEndpointSpec;", "H", "Lorg/springframework/integration/splitter/AbstractMessageSplitter;", "Lorg/springframework/integration/dsl/ConsumerEndpointSpec;", "delegate", "Lorg/springframework/integration/dsl/SplitterEndpointSpec;", "(Lorg/springframework/integration/dsl/SplitterEndpointSpec;)V", "getDelegate", "()Lorg/springframework/integration/dsl/SplitterEndpointSpec;", "applySequence", "", "", "delimiters", "", "discardChannel", "discardChannelName", "Lorg/springframework/messaging/MessageChannel;", "discardFlow", "Lkotlin/Function1;", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "Lkotlin/ExtensionFunctionType;", "spring-integration-core"})
public final class KotlinSplitterEndpointSpec<H extends AbstractMessageSplitter>
extends ConsumerEndpointSpec<KotlinSplitterEndpointSpec<H>, H> {
    @NotNull
    private final SplitterEndpointSpec<H> delegate;

    public KotlinSplitterEndpointSpec(@NotNull SplitterEndpointSpec<H> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, (String)"delegate");
        super((MessageHandler)delegate.handler);
        this.delegate = delegate;
    }

    @NotNull
    public final SplitterEndpointSpec<H> getDelegate() {
        return this.delegate;
    }

    public final void applySequence(boolean applySequence) {
        this.delegate.applySequence(applySequence);
    }

    public final void delimiters(@NotNull String delimiters) {
        Intrinsics.checkParameterIsNotNull((Object)delimiters, (String)"delimiters");
        this.delegate.delimiters(delimiters);
    }

    public final void discardChannel(@NotNull MessageChannel discardChannel) {
        Intrinsics.checkParameterIsNotNull((Object)discardChannel, (String)"discardChannel");
        this.delegate.discardChannel(discardChannel);
    }

    public final void discardChannel(@NotNull String discardChannelName) {
        Intrinsics.checkParameterIsNotNull((Object)discardChannelName, (String)"discardChannelName");
        this.delegate.discardChannel(discardChannelName);
    }

    public final void discardFlow(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> discardFlow) {
        Intrinsics.checkParameterIsNotNull(discardFlow, (String)"discardFlow");
        this.delegate.discardFlow(arg_0 -> KotlinSplitterEndpointSpec.discardFlow$lambda-0(discardFlow, arg_0));
    }

    private static final void discardFlow$lambda-0(Function1 $discardFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$discardFlow, (String)"$discardFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $discardFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }
}

