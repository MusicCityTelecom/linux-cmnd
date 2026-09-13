/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.reactive.DispatcherHandler
 *  org.springframework.web.reactive.HandlerMapping
 *  org.springframework.web.reactive.function.server.HandlerFunction
 *  org.springframework.web.reactive.function.server.RequestPredicate
 *  org.springframework.web.reactive.function.server.RouterFunction
 *  org.springframework.web.reactive.function.server.RouterFunctions$Visitor
 *  org.springframework.web.reactive.function.server.ServerRequest
 *  org.springframework.web.reactive.function.server.support.RouterFunctionMapping
 *  org.springframework.web.reactive.handler.AbstractUrlHandlerMapping
 *  org.springframework.web.reactive.result.method.RequestMappingInfo
 *  org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping
 *  org.springframework.web.util.pattern.PathPattern
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.web.mappings.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.actuate.web.mappings.HandlerMethodDescription;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.boot.actuate.web.mappings.reactive.DispatcherHandlerMappingDescription;
import org.springframework.boot.actuate.web.mappings.reactive.DispatcherHandlerMappingDetails;
import org.springframework.boot.actuate.web.mappings.reactive.HandlerFunctionDescription;
import org.springframework.boot.actuate.web.mappings.reactive.RequestMappingConditionsDescription;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping;
import org.springframework.web.reactive.handler.AbstractUrlHandlerMapping;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import reactor.core.publisher.Mono;

public class DispatcherHandlersMappingDescriptionProvider
implements MappingDescriptionProvider {
    private static final List<HandlerMappingDescriptionProvider<? extends HandlerMapping>> descriptionProviders = Arrays.asList(new RequestMappingInfoHandlerMappingDescriptionProvider(), new UrlHandlerMappingDescriptionProvider(), new RouterFunctionMappingDescriptionProvider());

    @Override
    public String getMappingName() {
        return "dispatcherHandlers";
    }

    @Override
    public Map<String, List<DispatcherHandlerMappingDescription>> describeMappings(ApplicationContext context) {
        HashMap<String, List<DispatcherHandlerMappingDescription>> mappings = new HashMap<String, List<DispatcherHandlerMappingDescription>>();
        context.getBeansOfType(DispatcherHandler.class).forEach((name, handler) -> mappings.put((String)name, this.describeMappings((DispatcherHandler)handler)));
        return mappings;
    }

    private List<DispatcherHandlerMappingDescription> describeMappings(DispatcherHandler dispatcherHandler) {
        return dispatcherHandler.getHandlerMappings().stream().flatMap(this::describe).collect(Collectors.toList());
    }

    private <T extends HandlerMapping> Stream<DispatcherHandlerMappingDescription> describe(T handlerMapping) {
        for (HandlerMappingDescriptionProvider<? extends HandlerMapping> descriptionProvider : descriptionProviders) {
            if (!descriptionProvider.getMappingClass().isInstance(handlerMapping)) continue;
            return descriptionProvider.describe(handlerMapping).stream();
        }
        return Stream.empty();
    }

    private static final class MappingDescriptionVisitor
    implements RouterFunctions.Visitor {
        private final List<DispatcherHandlerMappingDescription> descriptions = new ArrayList<DispatcherHandlerMappingDescription>();

        private MappingDescriptionVisitor() {
        }

        public void startNested(RequestPredicate predicate) {
        }

        public void endNested(RequestPredicate predicate) {
        }

        public void route(RequestPredicate predicate, HandlerFunction<?> handlerFunction) {
            DispatcherHandlerMappingDetails details = new DispatcherHandlerMappingDetails();
            details.setHandlerFunction(new HandlerFunctionDescription(handlerFunction));
            this.descriptions.add(new DispatcherHandlerMappingDescription(predicate.toString(), handlerFunction.toString(), details));
        }

        public void resources(Function<ServerRequest, Mono<Resource>> lookupFunction) {
        }

        public void attributes(Map<String, Object> attributes) {
        }

        public void unknown(RouterFunction<?> routerFunction) {
        }
    }

    private static final class RouterFunctionMappingDescriptionProvider
    implements HandlerMappingDescriptionProvider<RouterFunctionMapping> {
        private RouterFunctionMappingDescriptionProvider() {
        }

        @Override
        public Class<RouterFunctionMapping> getMappingClass() {
            return RouterFunctionMapping.class;
        }

        @Override
        public List<DispatcherHandlerMappingDescription> describe(RouterFunctionMapping handlerMapping) {
            MappingDescriptionVisitor visitor = new MappingDescriptionVisitor();
            RouterFunction routerFunction = handlerMapping.getRouterFunction();
            if (routerFunction != null) {
                routerFunction.accept((RouterFunctions.Visitor)visitor);
            }
            return visitor.descriptions;
        }
    }

    private static final class UrlHandlerMappingDescriptionProvider
    implements HandlerMappingDescriptionProvider<AbstractUrlHandlerMapping> {
        private UrlHandlerMappingDescriptionProvider() {
        }

        @Override
        public Class<AbstractUrlHandlerMapping> getMappingClass() {
            return AbstractUrlHandlerMapping.class;
        }

        @Override
        public List<DispatcherHandlerMappingDescription> describe(AbstractUrlHandlerMapping handlerMapping) {
            return handlerMapping.getHandlerMap().entrySet().stream().map(this::describe).collect(Collectors.toList());
        }

        private DispatcherHandlerMappingDescription describe(Map.Entry<PathPattern, Object> mapping) {
            return new DispatcherHandlerMappingDescription(mapping.getKey().getPatternString(), mapping.getValue().toString(), null);
        }
    }

    private static final class RequestMappingInfoHandlerMappingDescriptionProvider
    implements HandlerMappingDescriptionProvider<RequestMappingInfoHandlerMapping> {
        private RequestMappingInfoHandlerMappingDescriptionProvider() {
        }

        @Override
        public Class<RequestMappingInfoHandlerMapping> getMappingClass() {
            return RequestMappingInfoHandlerMapping.class;
        }

        @Override
        public List<DispatcherHandlerMappingDescription> describe(RequestMappingInfoHandlerMapping handlerMapping) {
            Map handlerMethods = handlerMapping.getHandlerMethods();
            return handlerMethods.entrySet().stream().map(this::describe).collect(Collectors.toList());
        }

        private DispatcherHandlerMappingDescription describe(Map.Entry<RequestMappingInfo, HandlerMethod> mapping) {
            DispatcherHandlerMappingDetails handlerMapping = new DispatcherHandlerMappingDetails();
            handlerMapping.setHandlerMethod(new HandlerMethodDescription(mapping.getValue()));
            handlerMapping.setRequestMappingConditions(new RequestMappingConditionsDescription(mapping.getKey()));
            return new DispatcherHandlerMappingDescription(mapping.getKey().toString(), mapping.getValue().toString(), handlerMapping);
        }
    }

    private static interface HandlerMappingDescriptionProvider<T extends HandlerMapping> {
        public Class<T> getMappingClass();

        public List<DispatcherHandlerMappingDescription> describe(T var1);
    }
}

