/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.PublishedApi
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.reactivestreams.Publisher
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.ChannelInterceptor
 *  reactor.core.publisher.Flux
 */
package org.springframework.integration.dsl;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.springframework.expression.Expression;
import org.springframework.integration.channel.BroadcastCapableChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.AggregatorSpec;
import org.springframework.integration.dsl.BarrierSpec;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.BroadcastPublishSubscribeSpec;
import org.springframework.integration.dsl.Channels;
import org.springframework.integration.dsl.DelayerEndpointSpec;
import org.springframework.integration.dsl.EnricherSpec;
import org.springframework.integration.dsl.FilterEndpointSpec;
import org.springframework.integration.dsl.GatewayEndpointSpec;
import org.springframework.integration.dsl.GenericEndpointSpec;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationDsl;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinEnricherSpec;
import org.springframework.integration.dsl.KotlinFilterEndpointSpec;
import org.springframework.integration.dsl.KotlinIntegrationFlowDefinition;
import org.springframework.integration.dsl.KotlinRecipientListRouterSpec;
import org.springframework.integration.dsl.KotlinRouterSpec;
import org.springframework.integration.dsl.KotlinSplitterEndpointSpec;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageHandlerSpec;
import org.springframework.integration.dsl.MessageProcessorSpec;
import org.springframework.integration.dsl.RecipientListRouterSpec;
import org.springframework.integration.dsl.ResequencerSpec;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.dsl.ScatterGatherSpec;
import org.springframework.integration.dsl.SplitterEndpointSpec;
import org.springframework.integration.dsl.WireTapSpec;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.MessageTriggerAction;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.ErrorMessageExceptionTypeRouter;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.splitter.ExpressionEvaluatingSplitter;
import org.springframework.integration.splitter.MethodInvokingSplitter;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.support.MapBuilder;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.integration.transformer.HeaderFilter;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import reactor.core.publisher.Flux;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u00f8\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001J!\u0010\t\u001a\u00020\n2\u0019\b\u0002\u0010\u000b\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ)\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0019\b\u0002\u0010\u0012\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ'\u0010\u0014\u001a\u00020\n2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aJ'\u0010\u0018\u001a\u00020\n2\u001f\u0010\u001b\u001a\u001b\u0012\u0004\u0012\u00020\u001c\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001d0\f\u00a2\u0006\u0002\b\u000eJ\u0016\u0010\u0018\u001a\u00020\n2\u000e\u0010\u001e\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001dJ\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 J/\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020#2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u0018\u0010%\u001a\u00020\n2\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u0010&\u001a\u00020'J5\u0010%\u001a\u00020\n2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'2\u001d\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ'\u0010(\u001a\u00020\n2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ2\u0010*\u001a\u00020\n\"\u0006\b\u0000\u0010+\u0018\u00012\u001f\b\u0006\u0010,\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eH\u0086\bJ)\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020\u001a2\u0019\b\u0002\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001f\u00100\u001a\u00020\n2\u0017\u00101\u001a\u0013\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ;\u00103\u001a\u00020\n2\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0001052\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001f\u00103\u001a\u00020\n2\u0017\u00106\u001a\u0013\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ?\u00103\u001a\u00020\n2\u0016\u00104\u001a\u0012\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0001082\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ'\u00109\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020'0\fH\u0086\bJB\u00109\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020'0\f2\u0019\b\u0004\u0010<\u001a\u0013\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eH\u0086\bJ\u001a\u00109\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ1\u00109\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\b\u0010?\u001a\u0004\u0018\u00010\u001a2\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ)\u00109\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u001a2\u0019\b\u0002\u0010<\u001a\u0013\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ)\u00109\u001a\u00020\n2\u0006\u0010A\u001a\u00020B2\u0019\b\u0002\u0010<\u001a\u0013\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ-\u00109\u001a\u00020\n2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2\u0019\b\u0002\u0010<\u001a\u0013\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u0012\u0010E\u001a\u00020\n2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ8\u0010F\u001a\u00020\n\"\u0004\b\u0000\u0010G\"\u0004\b\u0001\u0010H2$\u0010I\u001a \u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HG0K0J\u0012\n\u0012\b\u0012\u0004\u0012\u0002HH0L0\fJ)\u0010M\u001a\u00020\n2\u0006\u0010N\u001a\u00020\u001a2\u0019\b\u0002\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ8\u0010M\u001a\u00020\n2\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e2\u0017\u0010P\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001f\u0010M\u001a\u00020\n2\u0017\u0010P\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ)\u0010M\u001a\u00020\n2\u0006\u0010N\u001a\u00020 2\u0019\b\u0002\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ-\u0010Q\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u001a\b\u0004\u0010R\u001a\u0014\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00010SH\u0086\bJN\u0010Q\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u001a\b\u0004\u0010R\u001a\u0014\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00010S2\u001f\b\u0004\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eH\u0086\bJ\u001e\u0010Q\u001a\u00020\n2\u0016\u0010U\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030K\u0012\u0004\u0012\u00020\n0\fJ=\u0010Q\u001a\u00020\n2\u0016\u0010U\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030K\u0012\u0004\u0012\u00020\n0\f2\u001d\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020V0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ>\u0010Q\u001a\u00020\n\"\b\b\u0000\u0010W*\u00020V2\u0006\u0010U\u001a\u0002HW2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002HW0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\u0010XJ\u001a\u0010Q\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ7\u0010Q\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\b\u0010?\u001a\u0004\u0018\u00010\u001a2\u001d\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001a\u0010Q\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u001a2\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ7\u0010Q\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u001a2\b\u0010?\u001a\u0004\u0018\u00010\u001a2\u001d\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ$\u0010Q\u001a\u00020\n\"\n\b\u0000\u0010W*\u0004\u0018\u00010V2\u0010\u0010Z\u001a\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002HW0[JC\u0010Q\u001a\u00020\n\"\b\b\u0000\u0010W*\u00020V2\u0010\u0010Z\u001a\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002HW0[2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002HW0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ3\u0010Q\u001a\u00020\n2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u000e\u0010Q\u001a\u00020\n2\u0006\u0010U\u001a\u00020VJ\u0018\u0010\\\u001a\u00020\n2\u0006\u0010]\u001a\u00020\u001a2\b\b\u0002\u0010^\u001a\u00020'J-\u0010\\\u001a\u00020\n2\u0006\u0010\\\u001a\u00020_2\u001d\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001f\u0010`\u001a\u00020\n2\u0012\u0010a\u001a\n\u0012\u0006\b\u0001\u0012\u00020c0b\"\u00020c\u00a2\u0006\u0002\u0010dJ\u0006\u0010e\u001a\u00020\nJ&\u0010e\u001a\u00020\n\"\u0004\b\u0000\u0010:2\u0018\u0010;\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H:0K\u0012\u0004\u0012\u00020\u00010\fJ\u000e\u0010e\u001a\u00020\n2\u0006\u0010f\u001a\u00020\u001aJ.\u0010e\u001a\u00020\n\"\u0004\b\u0000\u0010:2\u0006\u0010f\u001a\u00020\u001a2\u0018\u0010;\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H:0K\u0012\u0004\u0012\u00020\u00010\fJ\u0016\u0010e\u001a\u00020\n2\u0006\u0010f\u001a\u00020\u001a2\u0006\u0010g\u001a\u00020hJ\u000e\u0010e\u001a\u00020\n2\u0006\u0010g\u001a\u00020hJ.\u0010e\u001a\u00020\n\"\u0004\b\u0000\u0010:2\u0006\u0010i\u001a\u00020j2\u0018\u0010;\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H:0K\u0012\u0004\u0012\u00020\u00010\fJ6\u0010e\u001a\u00020\n\"\u0004\b\u0000\u0010:2\u0006\u0010i\u001a\u00020j2\u0006\u0010f\u001a\u00020\u001a2\u0018\u0010;\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H:0K\u0012\u0004\u0012\u00020\u00010\fJ\u001e\u0010e\u001a\u00020\n2\u0006\u0010i\u001a\u00020j2\u0006\u0010f\u001a\u00020\u001a2\u0006\u0010g\u001a\u00020\u001aJ\u001e\u0010e\u001a\u00020\n2\u0006\u0010i\u001a\u00020j2\u0006\u0010f\u001a\u00020\u001a2\u0006\u0010g\u001a\u00020hJ\u001a\u0010e\u001a\u00020\n2\u0006\u0010i\u001a\u00020j2\n\b\u0002\u0010f\u001a\u0004\u0018\u00010\u001aJ\u0016\u0010e\u001a\u00020\n2\u0006\u0010i\u001a\u00020j2\u0006\u0010g\u001a\u00020hJI\u0010k\u001a\u00020\n2\u0006\u0010l\u001a\u00020m24\u0010n\u001a\u001b\u0012\u0017\b\u0001\u0012\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e0b\"\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\u0010oJ!\u0010p\u001a\u00020\n2\u0019\b\u0002\u0010q\u001a\u0013\u0012\u0004\u0012\u00020r\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJT\u0010s\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u0001\"\u0004\b\u0001\u0010+2\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u0002H+0\f2%\b\u0004\u0010,\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u00020u0t\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eH\u0086\bJ)\u0010s\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0016\b\u0004\u0010;\u001a\u0010\u0012\u0004\u0012\u0002H:\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fH\u0086\bJ@\u0010s\u001a\u00020\n\"\n\b\u0000\u0010v*\u0004\u0018\u00010w2\u0006\u0010x\u001a\u0002Hv2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hv0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\u0010yJ\u001a\u0010s\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ=\u0010s\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\b\u0010?\u001a\u0004\u0018\u00010\u001a2#\u0010z\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020u0t\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001a\u0010s\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u001a2\n\b\u0002\u0010{\u001a\u0004\u0018\u00010\u001aJ=\u0010s\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u001a2\b\u0010{\u001a\u0004\u0018\u00010\u001a2#\u0010z\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020u0t\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ;\u0010s\u001a\u00020\n\"\u0004\b\u0000\u0010+2\u0006\u0010@\u001a\u00020\u001a2%\b\u0002\u0010z\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u00020|0t\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ9\u0010s\u001a\u00020\n2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2%\b\u0002\u0010z\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020u0t\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ4\u0010}\u001a\u00020\n2,\u0010z\u001a(\u0012\u0019\u0012\u0017\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u007f0~\u0012\u0005\u0012\u00030\u0080\u00010t\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ!\u0010\u0081\u0001\u001a\u00020\n2\u0018\u0010z\u001a\u0014\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\"\u0010\u0083\u0001\u001a\u00020\n2\u0019\u0010\u0084\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ<\u0010\u0083\u0001\u001a\u00020\n2\u0019\u0010\u0084\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e2\u0018\u0010\u0085\u0001\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJW\u0010\u0083\u0001\u001a\u00020\n2\u0019\u0010\u0084\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u0082\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e2\u0018\u0010\u0085\u0001\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e2\u0019\u0010\u0083\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u0086\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ,\u0010\u0083\u0001\u001a\u00020\n2\u0007\u0010\u0087\u0001\u001a\u00020 2\u001a\b\u0002\u0010\u0085\u0001\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJE\u0010\u0083\u0001\u001a\u00020\n2\u0007\u0010\u0087\u0001\u001a\u00020 2\u0018\u0010\u0085\u0001\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e2\u0019\u0010\u0083\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u0086\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u0007\u0010\u0088\u0001\u001a\u00020\nJ(\u0010\u0088\u0001\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020\u00010\fH\u0086\bJK\u0010\u0088\u0001\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020\u00010\f2!\b\u0004\u0010,\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u00030\u008a\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eH\u0086\bJG\u0010\u0088\u0001\u001a\u00020\n\"\n\b\u0000\u0010\u008b\u0001*\u00030\u008c\u00012\b\u0010\u008d\u0001\u001a\u0003H\u008b\u00012\"\b\u0002\u0010\u008e\u0001\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u0003H\u008b\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0003\u0010\u008f\u0001J\u001b\u0010\u0088\u0001\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ;\u0010\u0088\u0001\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\b\u0010?\u001a\u0004\u0018\u00010\u001a2 \u0010\u008e\u0001\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u00030\u008a\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u001b\u0010\u0088\u0001\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u001a2\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ;\u0010\u0088\u0001\u001a\u00020\n2\u0006\u0010Y\u001a\u00020\u001a2\b\u0010?\u001a\u0004\u0018\u00010\u001a2 \u0010\u008e\u0001\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u00030\u008a\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ2\u0010\u0088\u0001\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u001a2!\b\u0002\u0010\u0015\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u00030\u0090\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJK\u0010\u0088\u0001\u001a\u00020\n\"\n\b\u0000\u0010\u008b\u0001*\u00030\u008c\u00012\u0012\u0010\u0091\u0001\u001a\r\u0012\u0002\b\u0003\u0012\u0005\u0012\u0003H\u008b\u00010[2\"\b\u0002\u0010\u008e\u0001\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u0003H\u008b\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ7\u0010\u0088\u0001\u001a\u00020\n2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2\"\b\u0002\u0010\u008e\u0001\u001a\u001b\u0012\f\u0012\n\u0012\u0005\u0012\u00030\u008a\u00010\u0089\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ(\u0010\u0092\u0001\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020\u00010\fH\u0086\bJI\u0010\u0092\u0001\u001a\u00020\n\"\u0006\b\u0000\u0010:\u0018\u00012\u0014\b\u0004\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u00020\u00010\f2\u001f\b\u0004\u0010,\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eH\u0086\bJ\u001b\u0010\u0092\u0001\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u001aJ8\u0010\u0092\u0001\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00012\b\u0010?\u001a\u0004\u0018\u00010\u001a2\u001d\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ0\u0010\u0092\u0001\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u001a2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ4\u0010\u0092\u0001\u001a\u00020\n2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ2\u0010\u0092\u0001\u001a\u00020\n2\b\u0010\u0093\u0001\u001a\u00030\u0094\u00012\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ1\u0010\u0095\u0001\u001a\u00020\n2\u0007\u0010\u0096\u0001\u001a\u00020\u001a2\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ2\u0010\u0095\u0001\u001a\u00020\n2\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u001f\b\u0002\u0010\u0015\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u0016\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ-\u0010\u0099\u0001\u001a\u00020\n2\u0007\u0010\u009a\u0001\u001a\u00020\u001a2\u001b\b\u0002\u0010\u009b\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ \u0010\u0099\u0001\u001a\u00020\n2\u0017\u0010P\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ\u0011\u0010\u0099\u0001\u001a\u00020\n2\b\u0010\u009d\u0001\u001a\u00030\u009c\u0001J;\u0010\u0099\u0001\u001a\u00020\n2\u0019\u0010\u009b\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000e2\u0017\u0010P\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eJ-\u0010\u0099\u0001\u001a\u00020\n2\u0007\u0010\u009a\u0001\u001a\u00020 2\u001b\b\u0002\u0010\u009b\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u009c\u0001\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\u0002\b\u000eR \u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00038\u0000X\u0081\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u009e\u0001"}, d2={"Lorg/springframework/integration/dsl/KotlinIntegrationFlowDefinition;", "", "delegate", "Lorg/springframework/integration/dsl/IntegrationFlowDefinition;", "(Lorg/springframework/integration/dsl/IntegrationFlowDefinition;)V", "getDelegate$annotations", "()V", "getDelegate", "()Lorg/springframework/integration/dsl/IntegrationFlowDefinition;", "aggregate", "", "aggregator", "Lkotlin/Function1;", "Lorg/springframework/integration/dsl/AggregatorSpec;", "Lkotlin/ExtensionFunctionType;", "barrier", "timeout", "", "barrierConfigurer", "Lorg/springframework/integration/dsl/BarrierSpec;", "bridge", "endpointConfigurer", "Lorg/springframework/integration/dsl/GenericEndpointSpec;", "Lorg/springframework/integration/handler/BridgeHandler;", "channel", "messageChannelName", "", "channels", "Lorg/springframework/integration/dsl/Channels;", "Lorg/springframework/integration/dsl/MessageChannelSpec;", "messageChannelSpec", "messageChannel", "Lorg/springframework/messaging/MessageChannel;", "claimCheckIn", "messageStore", "Lorg/springframework/integration/store/MessageStore;", "Lorg/springframework/integration/transformer/MessageTransformingHandler;", "claimCheckOut", "removeMessage", "", "controlBus", "Lorg/springframework/integration/handler/ServiceActivatingHandler;", "convert", "T", "configurer", "delay", "groupId", "Lorg/springframework/integration/dsl/DelayerEndpointSpec;", "enrich", "enricherConfigurer", "Lorg/springframework/integration/dsl/KotlinEnricherSpec;", "enrichHeaders", "headers", "", "headerEnricherConfigurer", "Lorg/springframework/integration/dsl/HeaderEnricherSpec;", "Lorg/springframework/integration/support/MapBuilder;", "filter", "P", "function", "filterConfigurer", "Lorg/springframework/integration/dsl/KotlinFilterEndpointSpec;", "service", "methodName", "expression", "messageSelector", "Lorg/springframework/integration/core/MessageSelector;", "messageProcessorSpec", "Lorg/springframework/integration/dsl/MessageProcessorSpec;", "fixedSubscriberChannel", "fluxTransform", "I", "O", "fluxFunction", "Lreactor/core/publisher/Flux;", "Lorg/springframework/messaging/Message;", "Lorg/reactivestreams/Publisher;", "gateway", "requestChannel", "Lorg/springframework/integration/dsl/GatewayEndpointSpec;", "flow", "handle", "handler", "Lkotlin/Function2;", "Lorg/springframework/messaging/MessageHeaders;", "messageHandler", "Lorg/springframework/messaging/MessageHandler;", "H", "(Lorg/springframework/messaging/MessageHandler;Lkotlin/jvm/functions/Function1;)V", "beanName", "messageHandlerSpec", "Lorg/springframework/integration/dsl/MessageHandlerSpec;", "headerFilter", "headersToRemove", "patternMatch", "Lorg/springframework/integration/transformer/HeaderFilter;", "intercept", "interceptorArray", "", "Lorg/springframework/messaging/support/ChannelInterceptor;", "([Lorg/springframework/messaging/support/ChannelInterceptor;)V", "log", "category", "logExpression", "Lorg/springframework/expression/Expression;", "level", "Lorg/springframework/integration/handler/LoggingHandler$Level;", "publishSubscribe", "broadcastCapableChannel", "Lorg/springframework/integration/channel/BroadcastCapableChannel;", "subscribeSubFlows", "(Lorg/springframework/integration/channel/BroadcastCapableChannel;[Lkotlin/jvm/functions/Function1;)V", "resequence", "resequencer", "Lorg/springframework/integration/dsl/ResequencerSpec;", "route", "Lorg/springframework/integration/dsl/KotlinRouterSpec;", "Lorg/springframework/integration/router/MethodInvokingRouter;", "R", "Lorg/springframework/integration/router/AbstractMessageRouter;", "router", "(Lorg/springframework/integration/router/AbstractMessageRouter;Lkotlin/jvm/functions/Function1;)V", "routerConfigurer", "method", "Lorg/springframework/integration/router/ExpressionEvaluatingRouter;", "routeByException", "Ljava/lang/Class;", "", "Lorg/springframework/integration/router/ErrorMessageExceptionTypeRouter;", "routeToRecipients", "Lorg/springframework/integration/dsl/KotlinRecipientListRouterSpec;", "scatterGather", "scatterer", "gatherer", "Lorg/springframework/integration/dsl/ScatterGatherSpec;", "scatterChannel", "split", "Lorg/springframework/integration/dsl/KotlinSplitterEndpointSpec;", "Lorg/springframework/integration/splitter/MethodInvokingSplitter;", "S", "Lorg/springframework/integration/splitter/AbstractMessageSplitter;", "splitter", "splitterConfigurer", "(Lorg/springframework/integration/splitter/AbstractMessageSplitter;Lkotlin/jvm/functions/Function1;)V", "Lorg/springframework/integration/splitter/ExpressionEvaluatingSplitter;", "splitterMessageHandlerSpec", "transform", "transformer", "Lorg/springframework/integration/transformer/Transformer;", "trigger", "triggerActionId", "triggerAction", "Lorg/springframework/integration/handler/MessageTriggerAction;", "wireTap", "wireTapChannel", "wireTapConfigurer", "Lorg/springframework/integration/dsl/WireTapSpec;", "wireTapSpec", "spring-integration-core"})
@IntegrationDsl
public final class KotlinIntegrationFlowDefinition {
    @NotNull
    private final IntegrationFlowDefinition<?> delegate;

    public KotlinIntegrationFlowDefinition(@NotNull IntegrationFlowDefinition<?> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, (String)"delegate");
        this.delegate = delegate;
    }

    @NotNull
    public final IntegrationFlowDefinition<?> getDelegate() {
        return this.delegate;
    }

    @PublishedApi
    public static /* synthetic */ void getDelegate$annotations() {
    }

    public final /* synthetic */ <T> void convert(Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> configurer) {
        Intrinsics.checkParameterIsNotNull(configurer, (String)"configurer");
        boolean $i$f$convert = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        integrationFlowDefinition.convert(Object.class, new Consumer(configurer){
            final /* synthetic */ Function1<GenericEndpointSpec<MessageTransformingHandler>, Unit> $configurer;
            {
                this.$configurer = $configurer;
            }

            public final void accept(GenericEndpointSpec<MessageTransformingHandler> it) {
                Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                this.$configurer.invoke(it);
            }
        });
    }

    public static /* synthetic */ void convert$default(KotlinIntegrationFlowDefinition this_, Function1 configurer, int n, Object object) {
        if ((n & 1) != 0) {
            configurer = convert.1.INSTANCE;
        }
        Intrinsics.checkParameterIsNotNull((Object)configurer, (String)"configurer");
        boolean $i$f$convert = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this_.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        integrationFlowDefinition.convert(Object.class, new /* invalid duplicate definition of identical inner class */);
    }

    public final /* synthetic */ <P> void transform(Function1<? super P, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        boolean $i$f$transform = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.transform(Object.class, new GenericTransformer(function){
            final /* synthetic */ Function1<P, Object> $function;
            {
                this.$function = $function;
            }

            public final Object transform(P it) {
                return this.$function.invoke(it);
            }
        });
    }

    public final /* synthetic */ <P> void transform(Function1<? super P, ? extends Object> function, Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> configurer) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Intrinsics.checkParameterIsNotNull(configurer, (String)"configurer");
        boolean $i$f$transform = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.transform(Object.class, new GenericTransformer(function){
            final /* synthetic */ Function1<P, Object> $function;
            {
                this.$function = $function;
            }

            public final Object transform(P it) {
                return this.$function.invoke(it);
            }
        }, (Consumer<GenericEndpointSpec<MessageTransformingHandler>>)new Consumer(configurer){
            final /* synthetic */ Function1<GenericEndpointSpec<MessageTransformingHandler>, Unit> $configurer;
            {
                this.$configurer = $configurer;
            }

            public final void accept(GenericEndpointSpec<MessageTransformingHandler> it) {
                Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                this.$configurer.invoke(it);
            }
        });
    }

    public final /* synthetic */ <P> void split(Function1<? super P, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        boolean $i$f$split = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.split(Object.class, new Function(function){
            final /* synthetic */ Function1<P, Object> $function;
            {
                this.$function = $function;
            }

            public final Object apply(P it) {
                return this.$function.invoke(it);
            }
        });
    }

    public final /* synthetic */ <P> void split(Function1<? super P, ? extends Object> function, Function1<? super KotlinSplitterEndpointSpec<MethodInvokingSplitter>, Unit> configurer) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Intrinsics.checkParameterIsNotNull(configurer, (String)"configurer");
        boolean $i$f$split = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.split(Object.class, new Function(function){
            final /* synthetic */ Function1<P, Object> $function;
            {
                this.$function = $function;
            }

            public final Object apply(P it) {
                return this.$function.invoke(it);
            }
        }, (Consumer<SplitterEndpointSpec<MethodInvokingSplitter>>)new Consumer(configurer){
            final /* synthetic */ Function1<KotlinSplitterEndpointSpec<MethodInvokingSplitter>, Unit> $configurer;
            {
                this.$configurer = $configurer;
            }

            public final void accept(SplitterEndpointSpec<MethodInvokingSplitter> it) {
                Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                this.$configurer.invoke(new KotlinSplitterEndpointSpec<MethodInvokingSplitter>(it));
            }
        });
    }

    public final /* synthetic */ <P> void filter(Function1<? super P, Boolean> function) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        boolean $i$f$filter = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.filter(Object.class, new GenericSelector(function){
            final /* synthetic */ Function1<P, Boolean> $function;
            {
                this.$function = $function;
            }

            public final boolean accept(P it) {
                return (Boolean)this.$function.invoke(it);
            }
        });
    }

    public final /* synthetic */ <P> void filter(Function1<? super P, Boolean> function, Function1<? super KotlinFilterEndpointSpec, Unit> filterConfigurer) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Intrinsics.checkParameterIsNotNull(filterConfigurer, (String)"filterConfigurer");
        boolean $i$f$filter = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.filter(Object.class, new GenericSelector(function){
            final /* synthetic */ Function1<P, Boolean> $function;
            {
                this.$function = $function;
            }

            public final boolean accept(P it) {
                return (Boolean)this.$function.invoke(it);
            }
        }, (Consumer<FilterEndpointSpec>)new Consumer(filterConfigurer){
            final /* synthetic */ Function1<KotlinFilterEndpointSpec, Unit> $filterConfigurer;
            {
                this.$filterConfigurer = $filterConfigurer;
            }

            public final void accept(FilterEndpointSpec it) {
                Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
                this.$filterConfigurer.invoke((Object)new KotlinFilterEndpointSpec(it));
            }
        });
    }

    public final /* synthetic */ <P> void route(Function1<? super P, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        boolean $i$f$route = false;
        KotlinIntegrationFlowDefinition this_$iv = this;
        boolean $i$f$route2 = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this_$iv.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.route(Object.class, new Function(function){
            final /* synthetic */ Function1<P, T> $function;
            {
                this.$function = $function;
            }

            public final T apply(P it) {
                return (T)this.$function.invoke(it);
            }
        }, new Consumer(){

            public final void accept(RouterSpec<T, MethodInvokingRouter> it) {
                Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                KotlinRouterSpec<T, MethodInvokingRouter> $this$route_u24lambda_u2d0 = new KotlinRouterSpec<T, MethodInvokingRouter>(it);
                boolean bl = false;
            }
        });
    }

    public final /* synthetic */ <P, T> void route(Function1<? super P, ? extends T> function, Function1<? super KotlinRouterSpec<T, MethodInvokingRouter>, Unit> configurer) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Intrinsics.checkParameterIsNotNull(configurer, (String)"configurer");
        boolean $i$f$route = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.route(Object.class, new /* invalid duplicate definition of identical inner class */, new Consumer(configurer){
            final /* synthetic */ Function1<KotlinRouterSpec<T, MethodInvokingRouter>, Unit> $configurer;
            {
                this.$configurer = $configurer;
            }

            public final void accept(RouterSpec<T, MethodInvokingRouter> it) {
                Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                this.$configurer.invoke(new KotlinRouterSpec<T, MethodInvokingRouter>(it));
            }
        });
    }

    public final void fixedSubscriberChannel(@Nullable String messageChannelName) {
        this.delegate.fixedSubscriberChannel(messageChannelName);
    }

    public static /* synthetic */ void fixedSubscriberChannel$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.fixedSubscriberChannel(string);
    }

    public final void channel(@NotNull String messageChannelName) {
        Intrinsics.checkParameterIsNotNull((Object)messageChannelName, (String)"messageChannelName");
        this.delegate.channel(messageChannelName);
    }

    public final void channel(@NotNull MessageChannelSpec<?, ?> messageChannelSpec) {
        Intrinsics.checkParameterIsNotNull(messageChannelSpec, (String)"messageChannelSpec");
        this.delegate.channel(messageChannelSpec);
    }

    public final void channel(@NotNull MessageChannel messageChannel) {
        Intrinsics.checkParameterIsNotNull((Object)messageChannel, (String)"messageChannel");
        this.delegate.channel(messageChannel);
    }

    public final void channel(@NotNull Function1<? super Channels, ? extends MessageChannelSpec<?, ?>> channels) {
        Intrinsics.checkParameterIsNotNull(channels, (String)"channels");
        Function1<? super Channels, ? extends MessageChannelSpec<?, ?>> function1 = channels;
        this.delegate.channel(arg_0 -> KotlinIntegrationFlowDefinition.channel$lambda-1(function1, arg_0));
    }

    public final void publishSubscribe(@NotNull BroadcastCapableChannel broadcastCapableChannel, Function1<? super KotlinIntegrationFlowDefinition, Unit> ... subscribeSubFlows) {
        Intrinsics.checkParameterIsNotNull((Object)broadcastCapableChannel, (String)"broadcastCapableChannel");
        Intrinsics.checkParameterIsNotNull(subscribeSubFlows, (String)"subscribeSubFlows");
        Consumer<BroadcastPublishSubscribeSpec> publishSubscribeChannelConfigurer = arg_0 -> KotlinIntegrationFlowDefinition.publishSubscribe$lambda-4(subscribeSubFlows, arg_0);
        this.delegate.publishSubscribeChannel(broadcastCapableChannel, publishSubscribeChannelConfigurer);
    }

    public final void wireTap(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        this.delegate.wireTap(arg_0 -> KotlinIntegrationFlowDefinition.wireTap$lambda-5(flow, arg_0));
    }

    public final void wireTap(@NotNull Function1<? super WireTapSpec, Unit> wireTapConfigurer, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(wireTapConfigurer, (String)"wireTapConfigurer");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        Function1<? super WireTapSpec, Unit> function1 = wireTapConfigurer;
        this.delegate.wireTap(arg_0 -> KotlinIntegrationFlowDefinition.wireTap$lambda-6(flow, arg_0), arg_0 -> KotlinIntegrationFlowDefinition.wireTap$lambda-7(function1, arg_0));
    }

    public final void wireTap(@NotNull String wireTapChannel, @NotNull Function1<? super WireTapSpec, Unit> wireTapConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)wireTapChannel, (String)"wireTapChannel");
        Intrinsics.checkParameterIsNotNull(wireTapConfigurer, (String)"wireTapConfigurer");
        Function1<? super WireTapSpec, Unit> function1 = wireTapConfigurer;
        this.delegate.wireTap(wireTapChannel, arg_0 -> KotlinIntegrationFlowDefinition.wireTap$lambda-8(function1, arg_0));
    }

    public static /* synthetic */ void wireTap$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = wireTap.3.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.wireTap(string, (Function1<? super WireTapSpec, Unit>)function1);
    }

    public final void wireTap(@NotNull MessageChannel wireTapChannel, @NotNull Function1<? super WireTapSpec, Unit> wireTapConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)wireTapChannel, (String)"wireTapChannel");
        Intrinsics.checkParameterIsNotNull(wireTapConfigurer, (String)"wireTapConfigurer");
        Function1<? super WireTapSpec, Unit> function1 = wireTapConfigurer;
        this.delegate.wireTap(wireTapChannel, arg_0 -> KotlinIntegrationFlowDefinition.wireTap$lambda-9(function1, arg_0));
    }

    public static /* synthetic */ void wireTap$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageChannel messageChannel, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = wireTap.4.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.wireTap(messageChannel, (Function1<? super WireTapSpec, Unit>)function1);
    }

    public final void wireTap(@NotNull WireTapSpec wireTapSpec) {
        Intrinsics.checkParameterIsNotNull((Object)wireTapSpec, (String)"wireTapSpec");
        this.delegate.wireTap(wireTapSpec);
    }

    public final void controlBus(@NotNull Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.controlBus(arg_0 -> KotlinIntegrationFlowDefinition.controlBus$lambda-10(function1, arg_0));
    }

    public static /* synthetic */ void controlBus$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            function1 = controlBus.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.controlBus((Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit>)function1);
    }

    public final void transform(@NotNull Transformer transformer, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)transformer, (String)"transformer");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        this.delegate.transform(transformer, arg_0 -> KotlinIntegrationFlowDefinition.transform$lambda-11(endpointConfigurer, arg_0));
    }

    public static /* synthetic */ void transform$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Transformer transformer, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = transform.4.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.transform(transformer, (Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit>)function1);
    }

    public final void transform(@NotNull String expression, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.transform(expression, arg_0 -> KotlinIntegrationFlowDefinition.transform$lambda-12(function1, arg_0));
    }

    public static /* synthetic */ void transform$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = transform.6.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.transform(string, (Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit>)function1);
    }

    public final void transform(@NotNull Object service, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        this.delegate.transform(service, methodName);
    }

    public static /* synthetic */ void transform$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.transform(object, string);
    }

    public final void transform(@NotNull Object service, @Nullable String methodName, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.transform(service, methodName, arg_0 -> KotlinIntegrationFlowDefinition.transform$lambda-13(function1, arg_0));
    }

    public final void transform(@NotNull MessageProcessorSpec<?> messageProcessorSpec, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageProcessorSpec, (String)"messageProcessorSpec");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.transform(messageProcessorSpec, arg_0 -> KotlinIntegrationFlowDefinition.transform$lambda-14(function1, arg_0));
    }

    public static /* synthetic */ void transform$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageProcessorSpec messageProcessorSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = transform.7.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.transform(messageProcessorSpec, (Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit>)function1);
    }

    public final void filter(@NotNull String expression, @NotNull Function1<? super KotlinFilterEndpointSpec, Unit> filterConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        Intrinsics.checkParameterIsNotNull(filterConfigurer, (String)"filterConfigurer");
        this.delegate.filter(expression, arg_0 -> KotlinIntegrationFlowDefinition.filter$lambda-15(filterConfigurer, arg_0));
    }

    public static /* synthetic */ void filter$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = filter.4.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.filter(string, (Function1<? super KotlinFilterEndpointSpec, Unit>)function1);
    }

    public final void filter(@NotNull Object service, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        this.delegate.filter(service, methodName);
    }

    public static /* synthetic */ void filter$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.filter(object, string);
    }

    public final void filter(@NotNull Object service, @Nullable String methodName, @NotNull Function1<? super KotlinFilterEndpointSpec, Unit> filterConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        Intrinsics.checkParameterIsNotNull(filterConfigurer, (String)"filterConfigurer");
        this.delegate.filter(service, methodName, arg_0 -> KotlinIntegrationFlowDefinition.filter$lambda-16(filterConfigurer, arg_0));
    }

    public final void filter(@NotNull MessageProcessorSpec<?> messageProcessorSpec, @NotNull Function1<? super KotlinFilterEndpointSpec, Unit> filterConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageProcessorSpec, (String)"messageProcessorSpec");
        Intrinsics.checkParameterIsNotNull(filterConfigurer, (String)"filterConfigurer");
        ((BaseIntegrationFlowDefinition)this.delegate).filter(messageProcessorSpec, arg_0 -> KotlinIntegrationFlowDefinition.filter$lambda-17(filterConfigurer, arg_0));
    }

    public static /* synthetic */ void filter$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageProcessorSpec messageProcessorSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = filter.7.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.filter(messageProcessorSpec, (Function1<? super KotlinFilterEndpointSpec, Unit>)function1);
    }

    public final void filter(@NotNull MessageSelector messageSelector, @NotNull Function1<? super KotlinFilterEndpointSpec, Unit> filterConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)messageSelector, (String)"messageSelector");
        Intrinsics.checkParameterIsNotNull(filterConfigurer, (String)"filterConfigurer");
        this.delegate.filter(Message.class, messageSelector, arg_0 -> KotlinIntegrationFlowDefinition.filter$lambda-18(filterConfigurer, arg_0));
    }

    public static /* synthetic */ void filter$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageSelector messageSelector, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = filter.9.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.filter(messageSelector, (Function1<? super KotlinFilterEndpointSpec, Unit>)function1);
    }

    public final <H extends MessageHandler> void handle(@NotNull MessageHandlerSpec<?, H> messageHandlerSpec) {
        Intrinsics.checkParameterIsNotNull(messageHandlerSpec, (String)"messageHandlerSpec");
        this.delegate.handle(messageHandlerSpec);
    }

    public final void handle(@NotNull MessageHandler messageHandler) {
        Intrinsics.checkParameterIsNotNull((Object)messageHandler, (String)"messageHandler");
        this.delegate.handle(messageHandler);
    }

    public final void handle(@NotNull String beanName, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)beanName, (String)"beanName");
        this.delegate.handle(beanName, methodName);
    }

    public static /* synthetic */ void handle$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        kotlinIntegrationFlowDefinition.handle(string, string2);
    }

    public final void handle(@NotNull String beanName, @Nullable String methodName, @NotNull Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)beanName, (String)"beanName");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.handle(beanName, methodName, arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-19(function1, arg_0));
    }

    public final void handle(@NotNull Object service, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        this.delegate.handle(service, methodName);
    }

    public static /* synthetic */ void handle$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.handle(object, string);
    }

    public final void handle(@NotNull Object service, @Nullable String methodName, @NotNull Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.handle(service, methodName, arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-20(function1, arg_0));
    }

    public final /* synthetic */ <P> void handle(Function2<? super P, ? super MessageHeaders, ? extends Object> handler) {
        Intrinsics.checkParameterIsNotNull(handler, (String)"handler");
        boolean $i$f$handle = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.handle(Object.class, new GenericHandler(handler){
            final /* synthetic */ Function2<P, MessageHeaders, Object> $handler;
            {
                this.$handler = $handler;
            }

            public final Object handle(P p, MessageHeaders h) {
                Intrinsics.checkExpressionValueIsNotNull((Object)h, (String)"h");
                return this.$handler.invoke(p, (Object)h);
            }
        });
    }

    public final /* synthetic */ <P> void handle(Function2<? super P, ? super MessageHeaders, ? extends Object> handler, Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(handler, (String)"handler");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        boolean $i$f$handle = false;
        IntegrationFlowDefinition<?> integrationFlowDefinition = this.getDelegate();
        Intrinsics.reifiedOperationMarker((int)4, (String)"P");
        integrationFlowDefinition.handle(Object.class, new GenericHandler(handler){
            final /* synthetic */ Function2<P, MessageHeaders, Object> $handler;
            {
                this.$handler = $handler;
            }

            public final Object handle(P p, MessageHeaders h) {
                Intrinsics.checkExpressionValueIsNotNull((Object)h, (String)"h");
                return this.$handler.invoke(p, (Object)h);
            }
        }, (Consumer<GenericEndpointSpec<ServiceActivatingHandler>>)new Consumer(endpointConfigurer){
            final /* synthetic */ Function1<GenericEndpointSpec<ServiceActivatingHandler>, Unit> $endpointConfigurer;
            {
                this.$endpointConfigurer = $endpointConfigurer;
            }

            public final void accept(GenericEndpointSpec<ServiceActivatingHandler> it) {
                Intrinsics.checkExpressionValueIsNotNull(it, (String)"it");
                this.$endpointConfigurer.invoke(it);
            }
        });
    }

    public final void handle(@NotNull MessageProcessorSpec<?> messageProcessorSpec, @NotNull Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageProcessorSpec, (String)"messageProcessorSpec");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.handle(messageProcessorSpec, arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-21(function1, arg_0));
    }

    public static /* synthetic */ void handle$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageProcessorSpec messageProcessorSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = handle.4.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.handle((MessageHandler)messageProcessorSpec, (Function1)function1);
    }

    public final <H extends MessageHandler> void handle(@NotNull MessageHandlerSpec<?, H> messageHandlerSpec, @NotNull Function1<? super GenericEndpointSpec<H>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageHandlerSpec, (String)"messageHandlerSpec");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<H>, Unit> function1 = endpointConfigurer;
        this.delegate.handle(messageHandlerSpec, arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-22(function1, arg_0));
    }

    public static /* synthetic */ void handle$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageHandlerSpec messageHandlerSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = handle.5.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.handle((MessageHandler)messageHandlerSpec, (Function1)function1);
    }

    public final void handle(@NotNull Function1<? super Message<?>, Unit> messageHandler) {
        Intrinsics.checkParameterIsNotNull(messageHandler, (String)"messageHandler");
        this.delegate.handle(arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-23(messageHandler, arg_0));
    }

    public final void handle(@NotNull Function1<? super Message<?>, Unit> messageHandler, @NotNull Function1<? super GenericEndpointSpec<MessageHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageHandler, (String)"messageHandler");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.handle(arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-24(messageHandler, arg_0), arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-25(function1, arg_0));
    }

    public final <H extends MessageHandler> void handle(@NotNull H messageHandler, @NotNull Function1<? super GenericEndpointSpec<H>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageHandler, (String)"messageHandler");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<H>, Unit> function1 = endpointConfigurer;
        this.delegate.handle(messageHandler, arg_0 -> KotlinIntegrationFlowDefinition.handle$lambda-26(function1, arg_0));
    }

    public static /* synthetic */ void handle$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageHandler messageHandler, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = handle.8.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.handle(messageHandler, function1);
    }

    public final void bridge(@NotNull Function1<? super GenericEndpointSpec<BridgeHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<BridgeHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.bridge(arg_0 -> KotlinIntegrationFlowDefinition.bridge$lambda-27(function1, arg_0));
    }

    public static /* synthetic */ void bridge$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            function1 = bridge.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.bridge((Function1<? super GenericEndpointSpec<BridgeHandler>, Unit>)function1);
    }

    public final void delay(@NotNull String groupId, @NotNull Function1<? super DelayerEndpointSpec, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)groupId, (String)"groupId");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super DelayerEndpointSpec, Unit> function1 = endpointConfigurer;
        this.delegate.delay(groupId, arg_0 -> KotlinIntegrationFlowDefinition.delay$lambda-28(function1, arg_0));
    }

    public static /* synthetic */ void delay$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = delay.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.delay(string, (Function1<? super DelayerEndpointSpec, Unit>)function1);
    }

    public final void enrich(@NotNull Function1<? super KotlinEnricherSpec, Unit> enricherConfigurer) {
        Intrinsics.checkParameterIsNotNull(enricherConfigurer, (String)"enricherConfigurer");
        this.delegate.enrich(arg_0 -> KotlinIntegrationFlowDefinition.enrich$lambda-29(enricherConfigurer, arg_0));
    }

    public final void enrichHeaders(@NotNull MapBuilder<?, String, Object> headers, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(headers, (String)"headers");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.enrichHeaders(headers, arg_0 -> KotlinIntegrationFlowDefinition.enrichHeaders$lambda-30(function1, arg_0));
    }

    public static /* synthetic */ void enrichHeaders$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MapBuilder mapBuilder, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = enrichHeaders.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.enrichHeaders(mapBuilder, (Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit>)function1);
    }

    public final void enrichHeaders(@NotNull Map<String, ? extends Object> headers, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(headers, (String)"headers");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.enrichHeaders(headers, arg_0 -> KotlinIntegrationFlowDefinition.enrichHeaders$lambda-31(function1, arg_0));
    }

    public static /* synthetic */ void enrichHeaders$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Map map, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = enrichHeaders.2.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.enrichHeaders(map, (Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit>)function1);
    }

    public final void enrichHeaders(@NotNull Function1<? super HeaderEnricherSpec, Unit> headerEnricherConfigurer) {
        Intrinsics.checkParameterIsNotNull(headerEnricherConfigurer, (String)"headerEnricherConfigurer");
        Function1<? super HeaderEnricherSpec, Unit> function1 = headerEnricherConfigurer;
        this.delegate.enrichHeaders(arg_0 -> KotlinIntegrationFlowDefinition.enrichHeaders$lambda-32(function1, arg_0));
    }

    public final void split() {
        this.delegate.split();
    }

    public final void split(@NotNull String expression, @NotNull Function1<? super KotlinSplitterEndpointSpec<ExpressionEvaluatingSplitter>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        this.delegate.split(expression, arg_0 -> KotlinIntegrationFlowDefinition.split$lambda-33(endpointConfigurer, arg_0));
    }

    public static /* synthetic */ void split$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = split.4.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.split((AbstractMessageSplitter)((Object)string), (Function1)function1);
    }

    public final void split(@NotNull Object service, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        this.delegate.split(service, methodName);
    }

    public static /* synthetic */ void split$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.split(object, string);
    }

    public final void split(@NotNull Object service, @Nullable String methodName, @NotNull Function1<? super KotlinSplitterEndpointSpec<MethodInvokingSplitter>, Unit> splitterConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        Intrinsics.checkParameterIsNotNull(splitterConfigurer, (String)"splitterConfigurer");
        this.delegate.split(service, methodName, arg_0 -> KotlinIntegrationFlowDefinition.split$lambda-34(splitterConfigurer, arg_0));
    }

    public final void split(@NotNull String beanName, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)beanName, (String)"beanName");
        this.delegate.split(beanName, methodName);
    }

    public static /* synthetic */ void split$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        kotlinIntegrationFlowDefinition.split(string, string2);
    }

    public final void split(@NotNull String beanName, @Nullable String methodName, @NotNull Function1<? super KotlinSplitterEndpointSpec<MethodInvokingSplitter>, Unit> splitterConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)beanName, (String)"beanName");
        Intrinsics.checkParameterIsNotNull(splitterConfigurer, (String)"splitterConfigurer");
        this.delegate.split(beanName, methodName, arg_0 -> KotlinIntegrationFlowDefinition.split$lambda-35(splitterConfigurer, arg_0));
    }

    public final void split(@NotNull MessageProcessorSpec<?> messageProcessorSpec, @NotNull Function1<? super KotlinSplitterEndpointSpec<MethodInvokingSplitter>, Unit> splitterConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageProcessorSpec, (String)"messageProcessorSpec");
        Intrinsics.checkParameterIsNotNull(splitterConfigurer, (String)"splitterConfigurer");
        this.delegate.split(messageProcessorSpec, arg_0 -> KotlinIntegrationFlowDefinition.split$lambda-36(splitterConfigurer, arg_0));
    }

    public static /* synthetic */ void split$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageProcessorSpec messageProcessorSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = split.8.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.split((AbstractMessageSplitter)((Object)messageProcessorSpec), (Function1)function1);
    }

    public final <S extends AbstractMessageSplitter> void split(@NotNull MessageHandlerSpec<?, S> splitterMessageHandlerSpec, @NotNull Function1<? super KotlinSplitterEndpointSpec<S>, Unit> splitterConfigurer) {
        Intrinsics.checkParameterIsNotNull(splitterMessageHandlerSpec, (String)"splitterMessageHandlerSpec");
        Intrinsics.checkParameterIsNotNull(splitterConfigurer, (String)"splitterConfigurer");
        this.delegate.split(splitterMessageHandlerSpec, arg_0 -> KotlinIntegrationFlowDefinition.split$lambda-37(splitterConfigurer, arg_0));
    }

    public static /* synthetic */ void split$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageHandlerSpec messageHandlerSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = split.10.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.split((AbstractMessageSplitter)((Object)messageHandlerSpec), (Function1)function1);
    }

    public final <S extends AbstractMessageSplitter> void split(@NotNull S splitter, @NotNull Function1<? super KotlinSplitterEndpointSpec<S>, Unit> splitterConfigurer) {
        Intrinsics.checkParameterIsNotNull(splitter, (String)"splitter");
        Intrinsics.checkParameterIsNotNull(splitterConfigurer, (String)"splitterConfigurer");
        this.delegate.split(splitter, arg_0 -> KotlinIntegrationFlowDefinition.split$lambda-38(splitterConfigurer, arg_0));
    }

    public static /* synthetic */ void split$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, AbstractMessageSplitter abstractMessageSplitter, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = split.12.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.split(abstractMessageSplitter, function1);
    }

    public final void headerFilter(@NotNull String headersToRemove, boolean patternMatch) {
        Intrinsics.checkParameterIsNotNull((Object)headersToRemove, (String)"headersToRemove");
        this.delegate.headerFilter(headersToRemove, patternMatch);
    }

    public static /* synthetic */ void headerFilter$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        kotlinIntegrationFlowDefinition.headerFilter(string, bl);
    }

    public final void headerFilter(@NotNull HeaderFilter headerFilter, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)headerFilter, (String)"headerFilter");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.headerFilter(headerFilter, arg_0 -> KotlinIntegrationFlowDefinition.headerFilter$lambda-39(function1, arg_0));
    }

    public final void claimCheckIn(@NotNull MessageStore messageStore, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)messageStore, (String)"messageStore");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.claimCheckIn(messageStore, arg_0 -> KotlinIntegrationFlowDefinition.claimCheckIn$lambda-40(function1, arg_0));
    }

    public static /* synthetic */ void claimCheckIn$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageStore messageStore, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = claimCheckIn.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.claimCheckIn(messageStore, (Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit>)function1);
    }

    public final void claimCheckOut(@NotNull MessageStore messageStore, boolean removeMessage) {
        Intrinsics.checkParameterIsNotNull((Object)messageStore, (String)"messageStore");
        this.delegate.claimCheckOut(messageStore, removeMessage);
    }

    public static /* synthetic */ void claimCheckOut$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageStore messageStore, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        kotlinIntegrationFlowDefinition.claimCheckOut(messageStore, bl);
    }

    public final void claimCheckOut(@NotNull MessageStore messageStore, boolean removeMessage, @NotNull Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)messageStore, (String)"messageStore");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<MessageTransformingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.claimCheckOut(messageStore, removeMessage, arg_0 -> KotlinIntegrationFlowDefinition.claimCheckOut$lambda-41(function1, arg_0));
    }

    public final void resequence(@NotNull Function1<? super ResequencerSpec, Unit> resequencer) {
        Intrinsics.checkParameterIsNotNull(resequencer, (String)"resequencer");
        Function1<? super ResequencerSpec, Unit> function1 = resequencer;
        this.delegate.resequence(arg_0 -> KotlinIntegrationFlowDefinition.resequence$lambda-42(function1, arg_0));
    }

    public static /* synthetic */ void resequence$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            function1 = resequence.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.resequence((Function1<? super ResequencerSpec, Unit>)function1);
    }

    public final void aggregate(@NotNull Object aggregator) {
        Intrinsics.checkParameterIsNotNull((Object)aggregator, (String)"aggregator");
        this.delegate.aggregate(aggregator);
    }

    public final void aggregate(@NotNull Function1<? super AggregatorSpec, Unit> aggregator) {
        Intrinsics.checkParameterIsNotNull(aggregator, (String)"aggregator");
        Function1<? super AggregatorSpec, Unit> function1 = aggregator;
        this.delegate.aggregate(arg_0 -> KotlinIntegrationFlowDefinition.aggregate$lambda-43(function1, arg_0));
    }

    public static /* synthetic */ void aggregate$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            function1 = aggregate.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.aggregate((Function1<? super AggregatorSpec, Unit>)function1);
    }

    public final void route(@NotNull String beanName, @Nullable String method) {
        Intrinsics.checkParameterIsNotNull((Object)beanName, (String)"beanName");
        this.delegate.route(beanName, method);
    }

    public static /* synthetic */ void route$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        kotlinIntegrationFlowDefinition.route(string, string2);
    }

    public final void route(@NotNull String beanName, @Nullable String method, @NotNull Function1<? super KotlinRouterSpec<Object, MethodInvokingRouter>, Unit> routerConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)beanName, (String)"beanName");
        Intrinsics.checkParameterIsNotNull(routerConfigurer, (String)"routerConfigurer");
        this.delegate.route(beanName, method, arg_0 -> KotlinIntegrationFlowDefinition.route$lambda-44(routerConfigurer, arg_0));
    }

    public final void route(@NotNull Object service, @Nullable String methodName) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        this.delegate.route(service, methodName);
    }

    public static /* synthetic */ void route$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.route(object, string);
    }

    public final void route(@NotNull Object service, @Nullable String methodName, @NotNull Function1<? super KotlinRouterSpec<Object, MethodInvokingRouter>, Unit> routerConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)service, (String)"service");
        Intrinsics.checkParameterIsNotNull(routerConfigurer, (String)"routerConfigurer");
        this.delegate.route(service, methodName, arg_0 -> KotlinIntegrationFlowDefinition.route$lambda-45(routerConfigurer, arg_0));
    }

    public final <T> void route(@NotNull String expression, @NotNull Function1<? super KotlinRouterSpec<T, ExpressionEvaluatingRouter>, Unit> routerConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)expression, (String)"expression");
        Intrinsics.checkParameterIsNotNull(routerConfigurer, (String)"routerConfigurer");
        this.delegate.route(expression, arg_0 -> KotlinIntegrationFlowDefinition.route$lambda-46(routerConfigurer, arg_0));
    }

    public static /* synthetic */ void route$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = route.6.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.route((AbstractMessageRouter)((Object)string), (Function1)function1);
    }

    public final void route(@NotNull MessageProcessorSpec<?> messageProcessorSpec, @NotNull Function1<? super KotlinRouterSpec<Object, MethodInvokingRouter>, Unit> routerConfigurer) {
        Intrinsics.checkParameterIsNotNull(messageProcessorSpec, (String)"messageProcessorSpec");
        Intrinsics.checkParameterIsNotNull(routerConfigurer, (String)"routerConfigurer");
        this.delegate.route(messageProcessorSpec, arg_0 -> KotlinIntegrationFlowDefinition.route$lambda-47(routerConfigurer, arg_0));
    }

    public static /* synthetic */ void route$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageProcessorSpec messageProcessorSpec, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = route.8.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.route((AbstractMessageRouter)((Object)messageProcessorSpec), (Function1)function1);
    }

    public final void routeToRecipients(@NotNull Function1<? super KotlinRecipientListRouterSpec, Unit> routerConfigurer) {
        Intrinsics.checkParameterIsNotNull(routerConfigurer, (String)"routerConfigurer");
        this.delegate.routeToRecipients(arg_0 -> KotlinIntegrationFlowDefinition.routeToRecipients$lambda-48(routerConfigurer, arg_0));
    }

    public final void routeByException(@NotNull Function1<? super KotlinRouterSpec<Class<? extends Throwable>, ErrorMessageExceptionTypeRouter>, Unit> routerConfigurer) {
        Intrinsics.checkParameterIsNotNull(routerConfigurer, (String)"routerConfigurer");
        this.delegate.routeByException(arg_0 -> KotlinIntegrationFlowDefinition.routeByException$lambda-49(routerConfigurer, arg_0));
    }

    public final <R extends AbstractMessageRouter> void route(R router, @NotNull Function1<? super GenericEndpointSpec<R>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<R>, Unit> function1 = endpointConfigurer;
        this.delegate.route(router, arg_0 -> KotlinIntegrationFlowDefinition.route$lambda-50(function1, arg_0));
    }

    public static /* synthetic */ void route$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, AbstractMessageRouter abstractMessageRouter, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = route.10.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.route(abstractMessageRouter, function1);
    }

    public final void gateway(@NotNull String requestChannel, @NotNull Function1<? super GatewayEndpointSpec, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)requestChannel, (String)"requestChannel");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GatewayEndpointSpec, Unit> function1 = endpointConfigurer;
        this.delegate.gateway(requestChannel, arg_0 -> KotlinIntegrationFlowDefinition.gateway$lambda-51(function1, arg_0));
    }

    public static /* synthetic */ void gateway$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = gateway.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.gateway(string, (Function1<? super GatewayEndpointSpec, Unit>)function1);
    }

    public final void gateway(@NotNull MessageChannel requestChannel, @NotNull Function1<? super GatewayEndpointSpec, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)requestChannel, (String)"requestChannel");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GatewayEndpointSpec, Unit> function1 = endpointConfigurer;
        this.delegate.gateway(requestChannel, arg_0 -> KotlinIntegrationFlowDefinition.gateway$lambda-52(function1, arg_0));
    }

    public static /* synthetic */ void gateway$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageChannel messageChannel, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = gateway.2.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.gateway(messageChannel, (Function1<? super GatewayEndpointSpec, Unit>)function1);
    }

    public final void gateway(@NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        this.delegate.gateway(arg_0 -> KotlinIntegrationFlowDefinition.gateway$lambda-53(flow, arg_0));
    }

    public final void gateway(@NotNull Function1<? super GatewayEndpointSpec, Unit> endpointConfigurer, @NotNull Function1<? super KotlinIntegrationFlowDefinition, Unit> flow) {
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Intrinsics.checkParameterIsNotNull(flow, (String)"flow");
        Function1<? super GatewayEndpointSpec, Unit> function1 = endpointConfigurer;
        this.delegate.gateway(arg_0 -> KotlinIntegrationFlowDefinition.gateway$lambda-54(flow, arg_0), arg_0 -> KotlinIntegrationFlowDefinition.gateway$lambda-55(function1, arg_0));
    }

    public final void log() {
        this.delegate.log();
    }

    public final void log(@NotNull LoggingHandler.Level level, @Nullable String category) {
        Intrinsics.checkParameterIsNotNull((Object)((Object)level), (String)"level");
        this.delegate.log(level, category);
    }

    public static /* synthetic */ void log$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, LoggingHandler.Level level, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = null;
        }
        kotlinIntegrationFlowDefinition.log(level, string);
    }

    public final void log(@NotNull String category) {
        Intrinsics.checkParameterIsNotNull((Object)category, (String)"category");
        this.delegate.log(category);
    }

    public final void log(@NotNull LoggingHandler.Level level, @NotNull String category, @NotNull String logExpression) {
        Intrinsics.checkParameterIsNotNull((Object)((Object)level), (String)"level");
        Intrinsics.checkParameterIsNotNull((Object)category, (String)"category");
        Intrinsics.checkParameterIsNotNull((Object)logExpression, (String)"logExpression");
        this.delegate.log(level, category, logExpression);
    }

    public final <P> void log(@NotNull Function1<? super Message<P>, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Function1<? super Message<P>, ? extends Object> function1 = function;
        this.delegate.log(arg_0 -> KotlinIntegrationFlowDefinition.log$lambda-56(function1, arg_0));
    }

    public final void log(@NotNull Expression logExpression) {
        Intrinsics.checkParameterIsNotNull((Object)logExpression, (String)"logExpression");
        this.delegate.log(logExpression);
    }

    public final void log(@NotNull LoggingHandler.Level level, @NotNull Expression logExpression) {
        Intrinsics.checkParameterIsNotNull((Object)((Object)level), (String)"level");
        Intrinsics.checkParameterIsNotNull((Object)logExpression, (String)"logExpression");
        this.delegate.log(level, logExpression);
    }

    public final void log(@NotNull String category, @NotNull Expression logExpression) {
        Intrinsics.checkParameterIsNotNull((Object)category, (String)"category");
        Intrinsics.checkParameterIsNotNull((Object)logExpression, (String)"logExpression");
        this.delegate.log(category, logExpression);
    }

    public final <P> void log(@NotNull LoggingHandler.Level level, @NotNull Function1<? super Message<P>, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull((Object)((Object)level), (String)"level");
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Function1<? super Message<P>, ? extends Object> function1 = function;
        this.delegate.log(level, arg_0 -> KotlinIntegrationFlowDefinition.log$lambda-57(function1, arg_0));
    }

    public final <P> void log(@NotNull String category, @NotNull Function1<? super Message<P>, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull((Object)category, (String)"category");
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Function1<? super Message<P>, ? extends Object> function1 = function;
        this.delegate.log(category, arg_0 -> KotlinIntegrationFlowDefinition.log$lambda-58(function1, arg_0));
    }

    public final <P> void log(@NotNull LoggingHandler.Level level, @NotNull String category, @NotNull Function1<? super Message<P>, ? extends Object> function) {
        Intrinsics.checkParameterIsNotNull((Object)((Object)level), (String)"level");
        Intrinsics.checkParameterIsNotNull((Object)category, (String)"category");
        Intrinsics.checkParameterIsNotNull(function, (String)"function");
        Function1<? super Message<P>, ? extends Object> function1 = function;
        this.delegate.log(level, category, arg_0 -> KotlinIntegrationFlowDefinition.log$lambda-59(function1, arg_0));
    }

    public final void log(@NotNull LoggingHandler.Level level, @NotNull String category, @NotNull Expression logExpression) {
        Intrinsics.checkParameterIsNotNull((Object)((Object)level), (String)"level");
        Intrinsics.checkParameterIsNotNull((Object)category, (String)"category");
        Intrinsics.checkParameterIsNotNull((Object)logExpression, (String)"logExpression");
        this.delegate.log(level, category, logExpression);
    }

    public final void scatterGather(@NotNull MessageChannel scatterChannel, @NotNull Function1<? super AggregatorSpec, Unit> gatherer) {
        Intrinsics.checkParameterIsNotNull((Object)scatterChannel, (String)"scatterChannel");
        Intrinsics.checkParameterIsNotNull(gatherer, (String)"gatherer");
        Function1<? super AggregatorSpec, Unit> function1 = gatherer;
        this.delegate.scatterGather(scatterChannel, arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-60(function1, arg_0));
    }

    public static /* synthetic */ void scatterGather$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageChannel messageChannel, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = scatterGather.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.scatterGather(messageChannel, (Function1<? super AggregatorSpec, Unit>)function1);
    }

    public final void scatterGather(@NotNull MessageChannel scatterChannel, @NotNull Function1<? super AggregatorSpec, Unit> gatherer, @NotNull Function1<? super ScatterGatherSpec, Unit> scatterGather2) {
        Intrinsics.checkParameterIsNotNull((Object)scatterChannel, (String)"scatterChannel");
        Intrinsics.checkParameterIsNotNull(gatherer, (String)"gatherer");
        Intrinsics.checkParameterIsNotNull(scatterGather2, (String)"scatterGather");
        Object object = gatherer;
        Consumer<AggregatorSpec> consumer = arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-61(object, arg_0);
        object = scatterGather2;
        this.delegate.scatterGather(scatterChannel, consumer, arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-62(object, arg_0));
    }

    public final void scatterGather(@NotNull Function1<? super KotlinRecipientListRouterSpec, Unit> scatterer) {
        Intrinsics.checkParameterIsNotNull(scatterer, (String)"scatterer");
        this.delegate.scatterGather(arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-63(scatterer, arg_0));
    }

    public final void scatterGather(@NotNull Function1<? super KotlinRecipientListRouterSpec, Unit> scatterer, @NotNull Function1<? super AggregatorSpec, Unit> gatherer) {
        Intrinsics.checkParameterIsNotNull(scatterer, (String)"scatterer");
        Intrinsics.checkParameterIsNotNull(gatherer, (String)"gatherer");
        this.delegate.scatterGather(arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-64(scatterer, arg_0), arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-65(gatherer, arg_0));
    }

    public final void scatterGather(@NotNull Function1<? super KotlinRecipientListRouterSpec, Unit> scatterer, @NotNull Function1<? super AggregatorSpec, Unit> gatherer, @NotNull Function1<? super ScatterGatherSpec, Unit> scatterGather2) {
        Intrinsics.checkParameterIsNotNull(scatterer, (String)"scatterer");
        Intrinsics.checkParameterIsNotNull(gatherer, (String)"gatherer");
        Intrinsics.checkParameterIsNotNull(scatterGather2, (String)"scatterGather");
        this.delegate.scatterGather(arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-66(scatterer, arg_0), arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-67(gatherer, arg_0), arg_0 -> KotlinIntegrationFlowDefinition.scatterGather$lambda-68(scatterGather2, arg_0));
    }

    public final void barrier(long timeout, @NotNull Function1<? super BarrierSpec, Unit> barrierConfigurer) {
        Intrinsics.checkParameterIsNotNull(barrierConfigurer, (String)"barrierConfigurer");
        Function1<? super BarrierSpec, Unit> function1 = barrierConfigurer;
        this.delegate.barrier(timeout, arg_0 -> KotlinIntegrationFlowDefinition.barrier$lambda-69(function1, arg_0));
    }

    public static /* synthetic */ void barrier$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, long l, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = barrier.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.barrier(l, (Function1<? super BarrierSpec, Unit>)function1);
    }

    public final void trigger(@NotNull String triggerActionId, @NotNull Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)triggerActionId, (String)"triggerActionId");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.trigger(triggerActionId, arg_0 -> KotlinIntegrationFlowDefinition.trigger$lambda-70(function1, arg_0));
    }

    public static /* synthetic */ void trigger$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, String string, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = trigger.1.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.trigger(string, (Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit>)function1);
    }

    public final void trigger(@NotNull MessageTriggerAction triggerAction, @NotNull Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> endpointConfigurer) {
        Intrinsics.checkParameterIsNotNull((Object)triggerAction, (String)"triggerAction");
        Intrinsics.checkParameterIsNotNull(endpointConfigurer, (String)"endpointConfigurer");
        Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit> function1 = endpointConfigurer;
        this.delegate.trigger(triggerAction, arg_0 -> KotlinIntegrationFlowDefinition.trigger$lambda-71(function1, arg_0));
    }

    public static /* synthetic */ void trigger$default(KotlinIntegrationFlowDefinition kotlinIntegrationFlowDefinition, MessageTriggerAction messageTriggerAction, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = trigger.2.INSTANCE;
        }
        kotlinIntegrationFlowDefinition.trigger(messageTriggerAction, (Function1<? super GenericEndpointSpec<ServiceActivatingHandler>, Unit>)function1);
    }

    public final <I, O> void fluxTransform(@NotNull Function1<? super Flux<Message<I>>, ? extends Publisher<O>> fluxFunction) {
        Intrinsics.checkParameterIsNotNull(fluxFunction, (String)"fluxFunction");
        Function1<? super Flux<Message<I>>, ? extends Publisher<O>> function1 = fluxFunction;
        this.delegate.fluxTransform(arg_0 -> KotlinIntegrationFlowDefinition.fluxTransform$lambda-72(function1, arg_0));
    }

    public final void intercept(ChannelInterceptor ... interceptorArray) {
        Intrinsics.checkParameterIsNotNull((Object)interceptorArray, (String)"interceptorArray");
        this.delegate.intercept(Arrays.copyOf(interceptorArray, interceptorArray.length));
    }

    private static final MessageChannelSpec channel$lambda-1(Function1 $tmp0, Channels p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return (MessageChannelSpec)$tmp0.invoke((Object)p0);
    }

    private static final void publishSubscribe$lambda-4$lambda-3$lambda-2(Function1 $subFlow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$subFlow, (String)"$subFlow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $subFlow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void publishSubscribe$lambda-4(Function1[] $subscribeSubFlows, BroadcastPublishSubscribeSpec spec) {
        Intrinsics.checkParameterIsNotNull((Object)$subscribeSubFlows, (String)"$subscribeSubFlows");
        Function1[] $this$forEach$iv = $subscribeSubFlows;
        boolean $i$f$forEach = false;
        Function1[] function1Array = $this$forEach$iv;
        int n = function1Array.length;
        for (int i = 0; i < n; ++i) {
            Function1 element$iv;
            Function1 subFlow = element$iv = function1Array[i];
            boolean bl = false;
            spec.subscribe(arg_0 -> KotlinIntegrationFlowDefinition.publishSubscribe$lambda-4$lambda-3$lambda-2(subFlow, arg_0));
        }
    }

    private static final void wireTap$lambda-5(Function1 $flow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$flow, (String)"$flow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $flow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void wireTap$lambda-6(Function1 $flow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$flow, (String)"$flow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $flow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void wireTap$lambda-7(Function1 $tmp0, WireTapSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void wireTap$lambda-8(Function1 $tmp0, WireTapSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void wireTap$lambda-9(Function1 $tmp0, WireTapSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void controlBus$lambda-10(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void transform$lambda-11(Function1 $endpointConfigurer, GenericEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$endpointConfigurer, (String)"$endpointConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $endpointConfigurer.invoke((Object)it);
    }

    private static final void transform$lambda-12(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void transform$lambda-13(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void transform$lambda-14(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void filter$lambda-15(Function1 $filterConfigurer, FilterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$filterConfigurer, (String)"$filterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $filterConfigurer.invoke((Object)new KotlinFilterEndpointSpec(it));
    }

    private static final void filter$lambda-16(Function1 $filterConfigurer, FilterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$filterConfigurer, (String)"$filterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $filterConfigurer.invoke((Object)new KotlinFilterEndpointSpec(it));
    }

    private static final void filter$lambda-17(Function1 $filterConfigurer, FilterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$filterConfigurer, (String)"$filterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $filterConfigurer.invoke((Object)new KotlinFilterEndpointSpec(it));
    }

    private static final void filter$lambda-18(Function1 $filterConfigurer, FilterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$filterConfigurer, (String)"$filterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $filterConfigurer.invoke((Object)new KotlinFilterEndpointSpec(it));
    }

    private static final void handle$lambda-19(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void handle$lambda-20(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void handle$lambda-21(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void handle$lambda-22(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void handle$lambda-23(Function1 $messageHandler, Message it) {
        Intrinsics.checkParameterIsNotNull((Object)$messageHandler, (String)"$messageHandler");
        Intrinsics.checkParameterIsNotNull((Object)it, (String)"it");
        $messageHandler.invoke((Object)it);
    }

    private static final void handle$lambda-24(Function1 $messageHandler, Message it) {
        Intrinsics.checkParameterIsNotNull((Object)$messageHandler, (String)"$messageHandler");
        Intrinsics.checkParameterIsNotNull((Object)it, (String)"it");
        $messageHandler.invoke((Object)it);
    }

    private static final void handle$lambda-25(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void handle$lambda-26(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void bridge$lambda-27(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void delay$lambda-28(Function1 $tmp0, DelayerEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void enrich$lambda-29(Function1 $enricherConfigurer, EnricherSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$enricherConfigurer, (String)"$enricherConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $enricherConfigurer.invoke((Object)new KotlinEnricherSpec(it));
    }

    private static final void enrichHeaders$lambda-30(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void enrichHeaders$lambda-31(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void enrichHeaders$lambda-32(Function1 $tmp0, HeaderEnricherSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void split$lambda-33(Function1 $endpointConfigurer, SplitterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$endpointConfigurer, (String)"$endpointConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $endpointConfigurer.invoke(new KotlinSplitterEndpointSpec(it));
    }

    private static final void split$lambda-34(Function1 $splitterConfigurer, SplitterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$splitterConfigurer, (String)"$splitterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $splitterConfigurer.invoke(new KotlinSplitterEndpointSpec(it));
    }

    private static final void split$lambda-35(Function1 $splitterConfigurer, SplitterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$splitterConfigurer, (String)"$splitterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $splitterConfigurer.invoke(new KotlinSplitterEndpointSpec(it));
    }

    private static final void split$lambda-36(Function1 $splitterConfigurer, SplitterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$splitterConfigurer, (String)"$splitterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $splitterConfigurer.invoke(new KotlinSplitterEndpointSpec(it));
    }

    private static final void split$lambda-37(Function1 $splitterConfigurer, SplitterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$splitterConfigurer, (String)"$splitterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $splitterConfigurer.invoke(new KotlinSplitterEndpointSpec(it));
    }

    private static final void split$lambda-38(Function1 $splitterConfigurer, SplitterEndpointSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$splitterConfigurer, (String)"$splitterConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $splitterConfigurer.invoke(new KotlinSplitterEndpointSpec(it));
    }

    private static final void headerFilter$lambda-39(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void claimCheckIn$lambda-40(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void claimCheckOut$lambda-41(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void resequence$lambda-42(Function1 $tmp0, ResequencerSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void aggregate$lambda-43(Function1 $tmp0, AggregatorSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void route$lambda-44(Function1 $routerConfigurer, RouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$routerConfigurer, (String)"$routerConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $routerConfigurer.invoke(new KotlinRouterSpec(it));
    }

    private static final void route$lambda-45(Function1 $routerConfigurer, RouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$routerConfigurer, (String)"$routerConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $routerConfigurer.invoke(new KotlinRouterSpec(it));
    }

    private static final void route$lambda-46(Function1 $routerConfigurer, RouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$routerConfigurer, (String)"$routerConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $routerConfigurer.invoke(new KotlinRouterSpec(it));
    }

    private static final void route$lambda-47(Function1 $routerConfigurer, RouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$routerConfigurer, (String)"$routerConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $routerConfigurer.invoke(new KotlinRouterSpec(it));
    }

    private static final void routeToRecipients$lambda-48(Function1 $routerConfigurer, RecipientListRouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$routerConfigurer, (String)"$routerConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $routerConfigurer.invoke((Object)new KotlinRecipientListRouterSpec(it));
    }

    private static final void routeByException$lambda-49(Function1 $routerConfigurer, RouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$routerConfigurer, (String)"$routerConfigurer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $routerConfigurer.invoke(new KotlinRouterSpec(it));
    }

    private static final void route$lambda-50(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void gateway$lambda-51(Function1 $tmp0, GatewayEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void gateway$lambda-52(Function1 $tmp0, GatewayEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void gateway$lambda-53(Function1 $flow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$flow, (String)"$flow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $flow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void gateway$lambda-54(Function1 $flow, IntegrationFlowDefinition it) {
        Intrinsics.checkParameterIsNotNull((Object)$flow, (String)"$flow");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $flow.invoke((Object)new KotlinIntegrationFlowDefinition(it));
    }

    private static final void gateway$lambda-55(Function1 $tmp0, GatewayEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final Object log$lambda-56(Function1 $tmp0, Message p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke((Object)p0);
    }

    private static final Object log$lambda-57(Function1 $tmp0, Message p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke((Object)p0);
    }

    private static final Object log$lambda-58(Function1 $tmp0, Message p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke((Object)p0);
    }

    private static final Object log$lambda-59(Function1 $tmp0, Message p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return $tmp0.invoke((Object)p0);
    }

    private static final void scatterGather$lambda-60(Function1 $tmp0, AggregatorSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void scatterGather$lambda-61(Function1 $tmp0, AggregatorSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void scatterGather$lambda-62(Function1 $tmp0, ScatterGatherSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void scatterGather$lambda-63(Function1 $scatterer, RecipientListRouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$scatterer, (String)"$scatterer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $scatterer.invoke((Object)new KotlinRecipientListRouterSpec(it));
    }

    private static final void scatterGather$lambda-64(Function1 $scatterer, RecipientListRouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$scatterer, (String)"$scatterer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $scatterer.invoke((Object)new KotlinRecipientListRouterSpec(it));
    }

    private static final void scatterGather$lambda-65(Function1 $gatherer, AggregatorSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$gatherer, (String)"$gatherer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $gatherer.invoke((Object)it);
    }

    private static final void scatterGather$lambda-66(Function1 $scatterer, RecipientListRouterSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$scatterer, (String)"$scatterer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $scatterer.invoke((Object)new KotlinRecipientListRouterSpec(it));
    }

    private static final void scatterGather$lambda-67(Function1 $gatherer, AggregatorSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$gatherer, (String)"$gatherer");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $gatherer.invoke((Object)it);
    }

    private static final void scatterGather$lambda-68(Function1 $scatterGather, ScatterGatherSpec it) {
        Intrinsics.checkParameterIsNotNull((Object)$scatterGather, (String)"$scatterGather");
        Intrinsics.checkExpressionValueIsNotNull((Object)it, (String)"it");
        $scatterGather.invoke((Object)it);
    }

    private static final void barrier$lambda-69(Function1 $tmp0, BarrierSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void trigger$lambda-70(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final void trigger$lambda-71(Function1 $tmp0, GenericEndpointSpec p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke((Object)p0);
    }

    private static final Publisher fluxTransform$lambda-72(Function1 $tmp0, Flux p0) {
        Intrinsics.checkParameterIsNotNull((Object)$tmp0, (String)"$tmp0");
        return (Publisher)$tmp0.invoke((Object)p0);
    }
}

