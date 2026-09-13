/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.GatewayProxySpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlowDslKt;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.dsl.MessageProducerSpec;
import org.springframework.integration.dsl.MessageSourceSpec;
import org.springframework.integration.dsl.MessagingGatewaySpec;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006H\u0002\u001aP\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0019\b\u0002\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a9\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a;\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0012\u0010\u0014\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00160\u00152\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001aN\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0019\b\u0002\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001aE\u0010\t\u001a\u00020\u0019\"\u0006\b\u0000\u0010\u001a\u0018\u00012\u0019\b\u0006\u0010\u001b\u001a\u0013\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000fH\u0086\b\u001a/\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u001d\u001a\u00020\u00192\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a\u001f\u0010\t\u001a\u00020\u00192\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a7\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\u001e\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001f2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001aZ\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0016\u0010\u0017\u001a\u0012\u0012\u0002\b\u0003\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00180 2\u0019\b\u0002\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a7\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u000e\u0010!\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\"2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a/\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010#\u001a\u00020$2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a/\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u001b\u001a\u00020%2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u001a/\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010&\u001a\u00020'2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\u0002\b\u000f\u00a8\u0006("}, d2={"buildIntegrationFlow", "Lorg/springframework/integration/dsl/StandardIntegrationFlow;", "kotlin.jvm.PlatformType", "flowBuilder", "Lorg/springframework/integration/dsl/IntegrationFlowBuilder;", "flow", "Lkotlin/Function1;", "Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "", "integrationFlow", "source", "Lkotlin/Function0;", "", "options", "Lorg/springframework/integration/dsl/SourcePollingChannelAdapterSpec;", "Lkotlin/ExtensionFunctionType;", "channelName", "", "fixedSubscriber", "", "publisher", "Lorg/reactivestreams/Publisher;", "Lorg/springframework/messaging/Message;", "messageSource", "Lorg/springframework/integration/core/MessageSource;", "Lorg/springframework/integration/dsl/IntegrationFlow;", "T", "gateway", "Lorg/springframework/integration/dsl/GatewayProxySpec;", "sourceFlow", "producerSpec", "Lorg/springframework/integration/dsl/MessageProducerSpec;", "Lorg/springframework/integration/dsl/MessageSourceSpec;", "gatewaySpec", "Lorg/springframework/integration/dsl/MessagingGatewaySpec;", "producer", "Lorg/springframework/integration/endpoint/MessageProducerSupport;", "Lorg/springframework/integration/gateway/MessagingGatewaySupport;", "channel", "Lorg/springframework/messaging/MessageChannel;", "spring-integration-core"})
public final class IntegrationFlowDslKt {
    private static final StandardIntegrationFlow buildIntegrationFlow(IntegrationFlowBuilder flowBuilder2, Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition = new KotlinIntegrationFlowDefinition(flowBuilder2);
        boolean bl = false;
        boolean bl2 = false;
        flow.invoke((Object)kotlinIntegrationFlowDefinition);
        return kotlinIntegrationFlowDefinition.getDelegate().get();
    }

    @NotNull
    public static final IntegrationFlow integrationFlow(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        return arg_0 -> IntegrationFlowDslKt.integrationFlow$lambda-0(flow, arg_0);
    }

    public static final /* synthetic */ <T> IntegrationFlow integrationFlow(Function1<? super GatewayProxySpec, Unit> gateway2, Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(gateway2, (String)"gateway");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        boolean $i$f$integrationFlow = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        IntegrationFlowBuilder flowBuilder2 = IntegrationFlows.from(Object.class, new Consumer(gateway2){
            final /* synthetic */ Function1<GatewayProxySpec, Unit> $gateway;
            {
                this.$gateway = $gateway;
            }

            public final void accept(GatewayProxySpec it) {
                Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
                this.$gateway.invoke((Object)it);
            }
        });
        Intrinsics.checkExpressionValueIsNotNull((Object)flowBuilder2, (String)"flowBuilder");
        flow.invoke((Object)new KotlinIntegrationFlowDefinition(flowBuilder2));
        StandardIntegrationFlow standardIntegrationFlow = flowBuilder2.get();
        Intrinsics.checkExpressionValueIsNotNull((Object)standardIntegrationFlow, (String)"flowBuilder.get()");
        return standardIntegrationFlow;
    }

    public static /* synthetic */ IntegrationFlow integrationFlow$default(Function1 gateway2, Function1 flow, int n, Object object) {
        if ((n & 1) != 0) {
            gateway2 = integrationFlow.2.INSTANCE;
        }
        Intrinsics.checkParameterIsNotNull((Object)gateway2, (String)"gateway");
        Intrinsics.checkParameterIsNotNull((Object)flow, (String)"flow");
        boolean $i$f$integrationFlow = false;
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        IntegrationFlowBuilder flowBuilder2 = IntegrationFlows.from(Object.class, new /* invalid duplicate definition of identical inner class */);
        Intrinsics.checkExpressionValueIsNotNull((Object)flowBuilder2, (String)"flowBuilder");
        flow.invoke((Object)new KotlinIntegrationFlowDefinition(flowBuilder2));
        StandardIntegrationFlow standardIntegrationFlow = flowBuilder2.get();
        Intrinsics.checkExpressionValueIsNotNull((Object)standardIntegrationFlow, (String)"flowBuilder.get()");
        return standardIntegrationFlow;
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull String channelName, boolean fixedSubscriber, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull((Object)channelName, (String)"channelName");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(channelName, fixedSubscriber);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(channelName, fixedSubscriber)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static /* synthetic */ StandardIntegrationFlow integrationFlow$default(String string, boolean bl, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return IntegrationFlowDslKt.integrationFlow(string, bl, (Function1<? super KotlinIntegrationFlowDefinition, Unit>)function1);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessageChannel channel, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull((Object)channel, (String)"channel");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(channel);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(channel)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessageSource<?> messageSource, @NotNull Function1<? super SourcePollingChannelAdapterSpec, Unit> options, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(messageSource, (String)"messageSource");
        Intrinsics.checkParameterIsNotNull(options, (String)"options");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(messageSource, arg_0 -> IntegrationFlowDslKt.integrationFlow$lambda-1(options, arg_0));
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(messageSource) { options(it) }");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static /* synthetic */ StandardIntegrationFlow integrationFlow$default(MessageSource messageSource, Function1 function1, Function1 function12, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = integrationFlow.3.INSTANCE;
        }
        return IntegrationFlowDslKt.integrationFlow(messageSource, (Function1<? super SourcePollingChannelAdapterSpec, Unit>)function1, (Function1<? super KotlinIntegrationFlowDefinition, Unit>)function12);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessageSourceSpec<?, ? extends MessageSource<?>> messageSource, @NotNull Function1<? super SourcePollingChannelAdapterSpec, Unit> options, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(messageSource, (String)"messageSource");
        Intrinsics.checkParameterIsNotNull(options, (String)"options");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        Function1<? super SourcePollingChannelAdapterSpec, Unit> function1 = options;
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(messageSource, arg_0 -> IntegrationFlowDslKt.integrationFlow$lambda-2(function1, arg_0));
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(messageSource, options)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static /* synthetic */ StandardIntegrationFlow integrationFlow$default(MessageSourceSpec messageSourceSpec, Function1 function1, Function1 function12, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = integrationFlow.5.INSTANCE;
        }
        return IntegrationFlowDslKt.integrationFlow(messageSourceSpec, (Function1<? super SourcePollingChannelAdapterSpec, Unit>)function1, (Function1<? super KotlinIntegrationFlowDefinition, Unit>)function12);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull Function0<? extends Object> source, @NotNull Function1<? super SourcePollingChannelAdapterSpec, Unit> options, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(source, (String)"source");
        Intrinsics.checkParameterIsNotNull(options, (String)"options");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        Function0<? extends Object> function0 = source;
        Supplier<Object> supplier = () -> IntegrationFlowDslKt.integrationFlow$lambda-3(function0);
        function0 = options;
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.fromSupplier(supplier, arg_0 -> IntegrationFlowDslKt.integrationFlow$lambda-4(function0, arg_0));
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"fromSupplier(source, options)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static /* synthetic */ StandardIntegrationFlow integrationFlow$default(Function0 function0, Function1 function1, Function1 function12, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = integrationFlow.6.INSTANCE;
        }
        return IntegrationFlowDslKt.integrationFlow((Function0<? extends Object>)function0, (Function1<? super SourcePollingChannelAdapterSpec, Unit>)function1, (Function1<? super KotlinIntegrationFlowDefinition, Unit>)function12);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull Publisher<? extends Message<?>> publisher, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(publisher, (String)"publisher");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(publisher);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(publisher)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessagingGatewaySupport gateway2, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull((Object)gateway2, (String)"gateway");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(gateway2);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(gateway)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessagingGatewaySpec<?, ?> gatewaySpec, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(gatewaySpec, (String)"gatewaySpec");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(gatewaySpec);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(gatewaySpec)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessageProducerSupport producer, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull((Object)producer, (String)"producer");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(producer);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(producer)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull MessageProducerSpec<?, ?> producerSpec, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(producerSpec, (String)"producerSpec");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(producerSpec);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(producerSpec)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    public static final StandardIntegrationFlow integrationFlow(@NotNull IntegrationFlow sourceFlow, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull((Object)sourceFlow, (String)"sourceFlow");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        IntegrationFlowBuilder integrationFlowBuilder = IntegrationFlows.from(sourceFlow);
        Intrinsics.checkExpressionValueIsNotNull((Object)integrationFlowBuilder, (String)"from(sourceFlow)");
        return IntegrationFlowDslKt.buildIntegrationFlow(integrationFlowBuilder, flow);
    }

    private static final void integrationFlow$lambda-0(Function1 $flow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$flow, (String)"$flow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $flow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void integrationFlow$lambda-1(Function1 $options, SourcePollingChannelAdapterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$options, (String)"$options");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $options.invoke((Object)it);
    }

    private static final void integrationFlow$lambda-2(Function1 $tmp0, SourcePollingChannelAdapterSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final Object integrationFlow$lambda-3(Function0 $tmp0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke();
    }

    private static final void integrationFlow$lambda-4(Function1 $tmp0, SourcePollingChannelAdapterSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }
}

