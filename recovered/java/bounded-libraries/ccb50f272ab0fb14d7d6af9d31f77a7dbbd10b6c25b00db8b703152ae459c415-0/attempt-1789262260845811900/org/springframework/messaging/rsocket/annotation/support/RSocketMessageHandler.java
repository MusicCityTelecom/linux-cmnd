/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.ConnectionSetupPayload
 *  io.rsocket.RSocket
 *  io.rsocket.SocketAcceptor
 *  io.rsocket.frame.FrameType
 *  io.rsocket.metadata.WellKnownMimeType
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.RouteMatcher
 *  org.springframework.util.RouteMatcher$Route
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.rsocket.annotation.support;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.frame.FrameType;
import io.rsocket.metadata.WellKnownMimeType;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.CompositeMessageCondition;
import org.springframework.messaging.handler.DestinationPatternsMessageCondition;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.MessageCondition;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.reactive.MessageMappingMessageHandler;
import org.springframework.messaging.handler.annotation.reactive.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.messaging.rsocket.annotation.support.MessagingRSocket;
import org.springframework.messaging.rsocket.annotation.support.RSocketFrameTypeMessageCondition;
import org.springframework.messaging.rsocket.annotation.support.RSocketPayloadReturnValueHandler;
import org.springframework.messaging.rsocket.annotation.support.RSocketRequesterMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.RouteMatcher;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class RSocketMessageHandler
extends MessageMappingMessageHandler {
    private final List<Encoder<?>> encoders = new ArrayList();
    private RSocketStrategies strategies = RSocketStrategies.create();
    @Nullable
    private MimeType defaultDataMimeType;
    private MimeType defaultMetadataMimeType = MimeTypeUtils.parseMimeType((String)WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString());

    public RSocketMessageHandler() {
        this.setRSocketStrategies(this.strategies);
    }

    public void setEncoders(List<? extends Encoder<?>> encoders) {
        this.encoders.clear();
        this.encoders.addAll(encoders);
        this.strategies = this.strategies.mutate().encoders(list -> {
            list.clear();
            list.addAll(encoders);
        }).build();
    }

    public List<? extends Encoder<?>> getEncoders() {
        return this.encoders;
    }

    @Override
    public void setDecoders(List<? extends Decoder<?>> decoders) {
        super.setDecoders(decoders);
        this.strategies = this.strategies.mutate().decoders(list -> {
            list.clear();
            list.addAll(decoders);
        }).build();
    }

    @Override
    public void setRouteMatcher(@Nullable RouteMatcher routeMatcher) {
        super.setRouteMatcher(routeMatcher);
        this.strategies = this.strategies.mutate().routeMatcher(routeMatcher).build();
    }

    @Override
    public void setReactiveAdapterRegistry(ReactiveAdapterRegistry registry) {
        super.setReactiveAdapterRegistry(registry);
        this.strategies = this.strategies.mutate().reactiveAdapterStrategy(registry).build();
    }

    public void setMetadataExtractor(MetadataExtractor extractor) {
        this.strategies = this.strategies.mutate().metadataExtractor(extractor).build();
    }

    public MetadataExtractor getMetadataExtractor() {
        return this.strategies.metadataExtractor();
    }

    public void setRSocketStrategies(RSocketStrategies rsocketStrategies) {
        this.strategies = rsocketStrategies;
        this.encoders.clear();
        this.encoders.addAll(this.strategies.encoders());
        super.setDecoders(this.strategies.decoders());
        super.setRouteMatcher(this.strategies.routeMatcher());
        super.setReactiveAdapterRegistry(this.strategies.reactiveAdapterRegistry());
    }

    public RSocketStrategies getRSocketStrategies() {
        return this.strategies;
    }

    public void setDefaultDataMimeType(@Nullable MimeType mimeType) {
        this.defaultDataMimeType = mimeType;
    }

    @Nullable
    public MimeType getDefaultDataMimeType() {
        return this.defaultDataMimeType;
    }

    public void setDefaultMetadataMimeType(MimeType mimeType) {
        Assert.notNull((Object)mimeType, (String)"'metadataMimeType' is required");
        this.defaultMetadataMimeType = mimeType;
    }

    public MimeType getDefaultMetadataMimeType() {
        return this.defaultMetadataMimeType;
    }

    @Override
    public void afterPropertiesSet() {
        this.getArgumentResolverConfigurer().addCustomResolver(new RSocketRequesterMethodArgumentResolver());
        super.afterPropertiesSet();
        this.getHandlerMethods().forEach((composite, handler) -> {
            MethodParameter returnType;
            if (composite.getMessageConditions().contains(RSocketFrameTypeMessageCondition.CONNECT_CONDITION) && this.getCardinality(returnType = handler.getReturnType()) > 0) {
                throw new IllegalStateException("Invalid @ConnectMapping method. Return type must be void or a void async type: " + handler);
            }
        });
    }

    @Override
    protected List<? extends HandlerMethodReturnValueHandler> initReturnValueHandlers() {
        ArrayList<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>();
        handlers.add(new RSocketPayloadReturnValueHandler(this.encoders, this.getReactiveAdapterRegistry()));
        handlers.addAll(this.getReturnValueHandlerConfigurer().getCustomHandlers());
        return handlers;
    }

    @Override
    @Nullable
    protected CompositeMessageCondition getCondition(AnnotatedElement element) {
        MessageMapping ann1 = (MessageMapping)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)element, MessageMapping.class);
        if (ann1 != null && ann1.value().length > 0) {
            return new CompositeMessageCondition(RSocketFrameTypeMessageCondition.EMPTY_CONDITION, new DestinationPatternsMessageCondition(this.processDestinations(ann1.value()), this.obtainRouteMatcher()));
        }
        ConnectMapping ann2 = (ConnectMapping)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)element, ConnectMapping.class);
        if (ann2 != null) {
            String[] patterns = this.processDestinations(ann2.value());
            return new CompositeMessageCondition(RSocketFrameTypeMessageCondition.CONNECT_CONDITION, new DestinationPatternsMessageCondition(patterns, this.obtainRouteMatcher()));
        }
        return null;
    }

    @Override
    protected CompositeMessageCondition extendMapping(CompositeMessageCondition composite, HandlerMethod handler) {
        List<MessageCondition<?>> conditions = composite.getMessageConditions();
        Assert.isTrue((conditions.size() == 2 && conditions.get(0) instanceof RSocketFrameTypeMessageCondition && conditions.get(1) instanceof DestinationPatternsMessageCondition ? 1 : 0) != 0, (String)"Unexpected message condition types");
        if (conditions.get(0) != RSocketFrameTypeMessageCondition.EMPTY_CONDITION) {
            return composite;
        }
        int responseCardinality = this.getCardinality(handler.getReturnType());
        int requestCardinality = 0;
        for (MethodParameter parameter : handler.getMethodParameters()) {
            if (!(this.getArgumentResolvers().getArgumentResolver(parameter) instanceof PayloadMethodArgumentResolver)) continue;
            requestCardinality = this.getCardinality(parameter);
        }
        return new CompositeMessageCondition(RSocketFrameTypeMessageCondition.getCondition(requestCardinality, responseCardinality), conditions.get(1));
    }

    private int getCardinality(MethodParameter parameter) {
        Class clazz = parameter.getParameterType();
        ReactiveAdapter adapter = this.getReactiveAdapterRegistry().getAdapter(clazz);
        if (adapter == null) {
            return clazz.equals(Void.TYPE) ? 0 : 1;
        }
        if (parameter.nested().getNestedParameterType().equals(Void.class)) {
            return 0;
        }
        return adapter.isMultiValue() ? 2 : 1;
    }

    @Override
    protected void handleNoMatch(@Nullable RouteMatcher.Route destination, Message<?> message) {
        FrameType frameType = RSocketFrameTypeMessageCondition.getFrameType(message);
        if (frameType == FrameType.SETUP || frameType == FrameType.METADATA_PUSH) {
            return;
        }
        if (frameType == FrameType.REQUEST_FNF) {
            this.logger.warn((Object)("No handler for fireAndForget to '" + destination + "'"));
            return;
        }
        Set frameTypes = this.getHandlerMethods().keySet().stream().map(CompositeMessageCondition::getMessageConditions).filter(conditions -> ((MessageCondition)conditions.get(1)).getMatchingCondition(message) != null).map(conditions -> (RSocketFrameTypeMessageCondition)conditions.get(0)).flatMap(condition -> condition.getFrameTypes().stream()).collect(Collectors.toSet());
        throw new MessageDeliveryException(frameTypes.isEmpty() ? "No handler for destination '" + destination + "'" : "Destination '" + destination + "' does not support " + frameType + ". Supported interaction(s): " + frameTypes);
    }

    public SocketAcceptor responder() {
        return (setupPayload, sendingRSocket) -> {
            MessagingRSocket responder;
            try {
                responder = this.createResponder(setupPayload, sendingRSocket);
            }
            catch (Throwable ex) {
                return Mono.error((Throwable)ex);
            }
            return responder.handleConnectionSetupPayload(setupPayload).then(Mono.just((Object)responder));
        };
    }

    private MessagingRSocket createResponder(ConnectionSetupPayload setupPayload, RSocket rsocket) {
        String str = setupPayload.dataMimeType();
        MimeType dataMimeType = StringUtils.hasText((String)str) ? MimeTypeUtils.parseMimeType((String)str) : this.defaultDataMimeType;
        Assert.notNull((Object)dataMimeType, (String)"No `dataMimeType` in ConnectionSetupPayload and no default value");
        Assert.isTrue((boolean)this.isDataMimeTypeSupported(dataMimeType), (String)("Data MimeType '" + dataMimeType + "' not supported"));
        str = setupPayload.metadataMimeType();
        MimeType metaMimeType = StringUtils.hasText((String)str) ? MimeTypeUtils.parseMimeType((String)str) : this.defaultMetadataMimeType;
        Assert.notNull((Object)metaMimeType, (String)"No `metadataMimeType` in ConnectionSetupPayload and no default value");
        RSocketRequester requester = RSocketRequester.wrap(rsocket, dataMimeType, metaMimeType, this.strategies);
        return new MessagingRSocket(dataMimeType, metaMimeType, this.getMetadataExtractor(), requester, this, this.obtainRouteMatcher(), this.strategies);
    }

    private boolean isDataMimeTypeSupported(MimeType dataMimeType) {
        for (Encoder<?> encoder : this.getEncoders()) {
            for (MimeType encodable : encoder.getEncodableMimeTypes()) {
                if (!encodable.isCompatibleWith(dataMimeType)) continue;
                return true;
            }
        }
        return false;
    }

    public static SocketAcceptor responder(RSocketStrategies strategies, Object ... candidateHandlers) {
        Assert.notEmpty((Object[])candidateHandlers, (String)"No handlers");
        ArrayList<Object> handlers = new ArrayList<Object>(candidateHandlers.length);
        for (Object obj : candidateHandlers) {
            handlers.add(obj instanceof Class ? BeanUtils.instantiateClass((Class)((Class)obj)) : obj);
        }
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setHandlers(handlers);
        handler.setRSocketStrategies(strategies);
        handler.afterPropertiesSet();
        return handler.responder();
    }
}

