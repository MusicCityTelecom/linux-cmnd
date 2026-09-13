/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dsl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.EnricherSpec;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.transformer.ContentEnricher;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\nJ\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u000bJ+\u0010\f\u001a\u00020\t\"\u0004\b\u0000\u0010\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u0002H\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012J\"\u0010\f\u001a\u00020\t\"\u0004\b\u0000\u0010\r2\u0006\u0010\u0013\u001a\u00020\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\r0\u0015J%\u0010\u0016\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0018J=\u0010\u0019\u001a\u00020\t\"\u0004\b\u0000\u0010\u001a2\u0006\u0010\u000e\u001a\u00020\n2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001a0\u001d\u0012\u0004\u0012\u00020\u001e0\u001c2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u001fJ!\u0010 \u001a\u00020\t\"\u0004\b\u0000\u0010\r2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\u000f\u001a\u0002H\r\u00a2\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020\t2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nJ.\u0010$\u001a\u00020\t\"\u0004\b\u0000\u0010\u001a2\u0006\u0010!\u001a\u00020\n2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001a0\u001d\u0012\u0004\u0012\u00020\u001e0\u001cJ\u000e\u0010%\u001a\u00020\t2\u0006\u0010%\u001a\u00020\nJ\u000e\u0010%\u001a\u00020\t2\u0006\u0010%\u001a\u00020\u000bJ\u000e\u0010&\u001a\u00020\t2\u0006\u0010&\u001a\u00020'J\u000e\u0010(\u001a\u00020\t2\u0006\u0010(\u001a\u00020\nJ\u000e\u0010(\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u000bJ&\u0010)\u001a\u00020\t\"\u0004\b\u0000\u0010\u001a2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001a0\u001d\u0012\u0004\u0012\u00020\u001e0\u001cJ\u000e\u0010*\u001a\u00020\t2\u0006\u0010*\u001a\u00020\nJ\u001f\u0010+\u001a\u00020\t2\u0017\u0010,\u001a\u0013\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\t0\u001c\u00a2\u0006\u0002\b.J\u000e\u0010/\u001a\u00020\t2\u0006\u0010/\u001a\u00020'J\u000e\u00100\u001a\u00020\t2\u0006\u00100\u001a\u00020\u0011R\u0011\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u00061"}, d2={"Lorg/springframework/integration/dsl/KotlinEnricherSpec;", "Lorg/springframework/integration/dsl/ConsumerEndpointSpec;", "Lorg/springframework/integration/dsl/EnricherSpec;", "Lorg/springframework/integration/transformer/ContentEnricher;", "delegate", "(Lorg/springframework/integration/dsl/EnricherSpec;)V", "getDelegate", "()Lorg/springframework/integration/dsl/EnricherSpec;", "errorChannel", "", "", "Lorg/springframework/messaging/MessageChannel;", "header", "V", "name", "value", "overwrite", "", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Boolean;)V", "headerName", "headerValueMessageProcessor", "Lorg/springframework/integration/transformer/support/HeaderValueMessageProcessor;", "headerExpression", "expression", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)V", "headerFunction", "P", "function", "Lkotlin/Function1;", "Lorg/springframework/messaging/Message;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Ljava/lang/Boolean;)V", "property", "key", "(Ljava/lang/String;Ljava/lang/Object;)V", "propertyExpression", "propertyFunction", "replyChannel", "replyTimeout", "", "requestChannel", "requestPayload", "requestPayloadExpression", "requestSubFlow", "subFlow", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "Lkotlin/ExtensionFunctionType;", "requestTimeout", "shouldClonePayload", "spring-integration-core"})
public final class KotlinEnricherSpec
extends ConsumerEndpointSpec<EnricherSpec, ContentEnricher> {
    @NotNull
    private final EnricherSpec delegate;

    public KotlinEnricherSpec(@NotNull EnricherSpec delegate) {
        Intrinsics.checkParameterIsNotNull((Object)delegate, (String)"delegate");
        super((MessageHandler)delegate.handler);
        this.delegate = delegate;
    }

    @NotNull
    public final EnricherSpec getDelegate() {
        return this.delegate;
    }

    public final void requestChannel(@NotNull MessageChannel requestChannel) {
        Intrinsics.checkParameterIsNotNull((Object)requestChannel, (String)"requestChannel");
        this.delegate.requestChannel(requestChannel);
    }

    public final void requestChannel(@NotNull String requestChannel) {
        Intrinsics.checkParameterIsNotNull((Object)requestChannel, (String)"requestChannel");
        this.delegate.requestChannel(requestChannel);
    }

    public final void replyChannel(@NotNull MessageChannel replyChannel) {
        Intrinsics.checkParameterIsNotNull((Object)replyChannel, (String)"replyChannel");
        this.delegate.replyChannel(replyChannel);
    }

    public final void replyChannel(@NotNull String replyChannel) {
        Intrinsics.checkParameterIsNotNull((Object)replyChannel, (String)"replyChannel");
        this.delegate.replyChannel(replyChannel);
    }

    public final void errorChannel(@NotNull MessageChannel errorChannel) {
        Intrinsics.checkParameterIsNotNull((Object)errorChannel, (String)"errorChannel");
        this.delegate.errorChannel(errorChannel);
    }

    public final void errorChannel(@NotNull String errorChannel) {
        Intrinsics.checkParameterIsNotNull((Object)errorChannel, (String)"errorChannel");
        this.delegate.errorChannel(errorChannel);
    }

    public final void requestTimeout(long requestTimeout) {
        this.delegate.requestTimeout(requestTimeout);
    }

    public final void replyTimeout(long replyTimeout) {
        this.delegate.replyTimeout(replyTimeout);
    }

    public final void requestPayloadExpression(@NotNull String requestPayloadExpression) {
        Intrinsics.checkParameterIsNotNull((Object)requestPayloadExpression, (String)"requestPayloadExpression");
        this.delegate.requestPayloadExpression(requestPayloadExpression);
    }

    public final <P> void requestPayload(@NotNull Function1<? super Message<P>, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Function1<? super Message<P>, ? extends Object> function1 = function;
        this.delegate.requestPayload(arg_0 -> KotlinEnricherSpec.requestPayload$lambda-0(function1, arg_0));
    }

    public final void requestSubFlow(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> subFlow) {
        Intrinsics.checkParameterIsNotNull(subFlow, (String)"subFlow");
        this.delegate.requestSubFlow(arg_0 -> KotlinEnricherSpec.requestSubFlow$lambda-1(subFlow, arg_0));
    }

    public final void shouldClonePayload(boolean shouldClonePayload) {
        this.delegate.shouldClonePayload(shouldClonePayload);
    }

    public final <V> void property(@NotNull String key, V value) {
        Intrinsics.checkParameterIsNotNull((Object)key, (String)"key");
        this.delegate.property(key, value);
    }

    public final void propertyExpression(@NotNull String key, @NotNull String expression) {
        Intrinsics.checkParameterIsNotNull((Object)key, (String)"key");
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        this.delegate.propertyExpression(key, expression);
    }

    public final <P> void propertyFunction(@NotNull String key, @NotNull Function1<? super Message<P>, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull((Object)key, (String)"key");
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Function1<? super Message<P>, ? extends Object> function1 = function;
        this.delegate.propertyFunction(key, arg_0 -> KotlinEnricherSpec.propertyFunction$lambda-2(function1, arg_0));
    }

    public final <V> void header(@NotNull String name, V value, @Nullable Boolean overwrite) {
        Intrinsics.checkParameterIsNotNull((Object)name, (String)"name");
        this.delegate.header(name, value, overwrite);
    }

    public final void headerExpression(@NotNull String name, @NotNull String expression, @Nullable Boolean overwrite) {
        Intrinsics.checkParameterIsNotNull((Object)name, (String)"name");
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        this.delegate.header(name, expression, overwrite);
    }

    public final <P> void headerFunction(@NotNull String name, @NotNull Function1<? super Message<P>, ? extends Object> function, @Nullable Boolean overwrite) {
        Intrinsics.checkParameterIsNotNull((Object)name, (String)"name");
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        this.delegate.header(name, function, overwrite);
    }

    public final <V> void header(@NotNull String headerName, @NotNull HeaderValueMessageProcessor<V> headerValueMessageProcessor) {
        Intrinsics.checkParameterIsNotNull((Object)headerName, (String)"headerName");
        Intrinsics.checkParameterIsNotNull(headerValueMessageProcessor, (String)"headerValueMessageProcessor");
        this.delegate.header(headerName, headerValueMessageProcessor);
    }

    private static final Object requestPayload$lambda-0(Function1 $tmp0, Message p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke((Object)p0);
    }

    private static final void requestSubFlow$lambda-1(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final Object propertyFunction$lambda-2(Function1 $tmp0, Message p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke((Object)p0);
    }
}

