/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.expression.Expression;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.AbstractKotlinRouterSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.dsl.RecipientListRouterSpec;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ/\u0010\b\u001a\u00020\t\"\u0006\b\u0000\u0010\f\u0018\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0014\b\u0004\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u000f0\u000eH\u0086\bJ\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000bJ\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013J/\u0010\b\u001a\u00020\t\"\u0006\b\u0000\u0010\f\u0018\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0014\b\u0004\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u000f0\u000eH\u0086\bJ\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u000bJ\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011JB\u0010\u0014\u001a\u00020\t\"\u0006\b\u0000\u0010\f\u0018\u00012\u0014\b\u0004\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u000f0\u000e2\u0019\b\u0004\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\u0002\b\u0017H\u0086\bJ'\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000b2\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\u0002\b\u0017J'\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\u0002\b\u0017J\u001f\u0010\u0014\u001a\u00020\t2\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\u0002\b\u0017R\u0014\u0010\u0004\u001a\u00020\u0002X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0018"}, d2={"Lorg/springframework/integration/dsl/KotlinRecipientListRouterSpec;", "Lorg/springframework/integration/dsl/AbstractKotlinRouterSpec;", "Lorg/springframework/integration/dsl/RecipientListRouterSpec;", "Lorg/springframework/integration/router/RecipientListRouter;", "delegate", "(Lorg/springframework/integration/dsl/RecipientListRouterSpec;)V", "getDelegate", "()Lorg/springframework/integration/dsl/RecipientListRouterSpec;", "recipient", "", "channelName", "", "P", "selector", "Lkotlin/Function1;", "", "expression", "Lorg/springframework/expression/Expression;", "channel", "Lorg/springframework/messaging/MessageChannel;", "recipientFlow", "subFlow", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "Lkotlin/ExtensionFunctionType;", "spring-integration-core"})
public final class KotlinRecipientListRouterSpec
extends AbstractKotlinRouterSpec<RecipientListRouterSpec, RecipientListRouter> {
    @NotNull
    private final RecipientListRouterSpec delegate;

    public KotlinRecipientListRouterSpec(@NotNull RecipientListRouterSpec delegate) {
        Intrinsics.checkParameterIsNotNull((Object)delegate, (String)"delegate");
        super(delegate);
        this.delegate = delegate;
    }

    @NotNull
    public RecipientListRouterSpec getDelegate() {
        return this.delegate;
    }

    public final void recipient(@NotNull String channelName) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        this.getDelegate().recipient(channelName);
    }

    public final void recipient(@NotNull String channelName, @NotNull String expression) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        this.getDelegate().recipient(channelName, expression);
    }

    public final void recipient(@NotNull String channelName, @NotNull Expression expression) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        this.getDelegate().recipient(channelName, expression);
    }

    public final /* synthetic */ <P> void recipient(String channelName, Function1<? super P, Boolean> selector) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        Intrinsics.checkParameterIsNotNull(selector, (String)"selector");
        boolean $i$f$recipient = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        if (Message.class.isAssignableFrom(Object.class)) {
            RecipientListRouterSpec recipientListRouterSpec = this.getDelegate();
            Intrinsics.needClassReification();
            recipientListRouterSpec.recipientMessageSelector(channelName, new MessageSelector(selector){
                final /* synthetic */ Function1<P, Boolean> $selector;
                {
                    this.$selector = $selector;
                }

                public final boolean accept(Message<?> it) {
                    Intrinsics.reifiedOperationMarker((int)1, (String)"P");
                    return (Boolean)this.$selector.invoke((Object)it);
                }
            });
        } else {
            this.getDelegate().recipient(channelName, (GenericSelector)new GenericSelector(selector){
                final /* synthetic */ Function1<P, Boolean> $selector;
                {
                    this.$selector = $selector;
                }

                public final boolean accept(P it) {
                    return (Boolean)this.$selector.invoke(it);
                }
            });
        }
    }

    public final void recipient(@NotNull MessageChannel channel) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        this.getDelegate().recipient(channel);
    }

    public final void recipient(@NotNull MessageChannel channel, @NotNull String expression) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        this.getDelegate().recipient(channel, expression);
    }

    public final void recipient(@NotNull MessageChannel channel, @NotNull Expression expression) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        this.getDelegate().recipient(channel, expression);
    }

    public final /* synthetic */ <P> void recipient(MessageChannel channel, Function1<? super P, Boolean> selector) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        Intrinsics.checkParameterIsNotNull(selector, (String)"selector");
        boolean $i$f$recipient = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        if (Message.class.isAssignableFrom(Object.class)) {
            RecipientListRouterSpec recipientListRouterSpec = this.getDelegate();
            Intrinsics.needClassReification();
            recipientListRouterSpec.recipientMessageSelector(channel, new MessageSelector(selector){
                final /* synthetic */ Function1<P, Boolean> $selector;
                {
                    this.$selector = $selector;
                }

                public final boolean accept(Message<?> it) {
                    Intrinsics.reifiedOperationMarker((int)1, (String)"P");
                    return (Boolean)this.$selector.invoke((Object)it);
                }
            });
        } else {
            this.getDelegate().recipient(channel, (GenericSelector)new GenericSelector(selector){
                final /* synthetic */ Function1<P, Boolean> $selector;
                {
                    this.$selector = $selector;
                }

                public final boolean accept(P it) {
                    return (Boolean)this.$selector.invoke(it);
                }
            });
        }
    }

    public final /* synthetic */ <P> void recipientFlow(Function1<? super P, Boolean> selector, Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull(selector, (String)"selector");
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        boolean $i$f$recipientFlow = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        if (Message.class.isAssignableFrom(Object.class)) {
            RecipientListRouterSpec recipientListRouterSpec = this.getDelegate();
            Intrinsics.needClassReification();
            recipientListRouterSpec.recipientMessageSelectorFlow(new MessageSelector(selector){
                final /* synthetic */ Function1<P, Boolean> $selector;
                {
                    this.$selector = $selector;
                }

                public final boolean accept(Message<?> it) {
                    Intrinsics.reifiedOperationMarker((int)1, (String)"P");
                    return (Boolean)this.$selector.invoke((Object)it);
                }
            }, new IntegrationFlow(subFlow){
                final /* synthetic */ Function1<KotlinIntegrationFlowDefinition, Unit> $subFlow;
                {
                    this.$subFlow = $subFlow;
                }

                public final void configure(IntegrationFlowDefinition<?> it) {
                    Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                    this.$subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
                }
            });
        } else {
            this.getDelegate().recipientFlow(new GenericSelector(selector){
                final /* synthetic */ Function1<P, Boolean> $selector;
                {
                    this.$selector = $selector;
                }

                public final boolean accept(P it) {
                    return (Boolean)this.$selector.invoke(it);
                }
            }, new IntegrationFlow(subFlow){
                final /* synthetic */ Function1<KotlinIntegrationFlowDefinition, Unit> $subFlow;
                {
                    this.$subFlow = $subFlow;
                }

                public final void configure(IntegrationFlowDefinition<?> it) {
                    Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                    this.$subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
                }
            });
        }
    }

    public final void recipientFlow(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        this.getDelegate().recipientFlow(arg_0 -> KotlinRecipientListRouterSpec.recipientFlow$lambda-0(subFlow, arg_0));
    }

    public final void recipientFlow(@NotNull String expression, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        this.getDelegate().recipientFlow(expression, arg_0 -> KotlinRecipientListRouterSpec.recipientFlow$lambda-1(subFlow, arg_0));
    }

    public final void recipientFlow(@NotNull Expression expression, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        this.getDelegate().recipientFlow(expression, arg_0 -> KotlinRecipientListRouterSpec.recipientFlow$lambda-2(subFlow, arg_0));
    }

    private static final void recipientFlow$lambda-0(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void recipientFlow$lambda-1(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void recipientFlow$lambda-2(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }
}

