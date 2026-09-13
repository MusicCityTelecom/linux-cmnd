/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.PollableChannel
 */
package org.springframework.integration.graph;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.IntegrationConsumer;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.graph.CompositeMessageHandlerNode;
import org.springframework.integration.graph.DiscardingMessageHandlerNode;
import org.springframework.integration.graph.EndpointNode;
import org.springframework.integration.graph.ErrorCapableCompositeMessageHandlerNode;
import org.springframework.integration.graph.ErrorCapableDiscardingMessageHandlerNode;
import org.springframework.integration.graph.ErrorCapableMessageHandlerNode;
import org.springframework.integration.graph.ErrorCapableNode;
import org.springframework.integration.graph.ErrorCapableRoutingNode;
import org.springframework.integration.graph.Graph;
import org.springframework.integration.graph.IntegrationNode;
import org.springframework.integration.graph.LinkNode;
import org.springframework.integration.graph.MessageChannelNode;
import org.springframework.integration.graph.MessageGatewayNode;
import org.springframework.integration.graph.MessageHandlerNode;
import org.springframework.integration.graph.MessageProducerNode;
import org.springframework.integration.graph.MessageSourceNode;
import org.springframework.integration.graph.MicrometerNodeEnhancer;
import org.springframework.integration.graph.PollableChannelNode;
import org.springframework.integration.graph.RoutingMessageHandlerNode;
import org.springframework.integration.handler.CompositeMessageHandler;
import org.springframework.integration.handler.DiscardingMessageHandler;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.router.RecipientListRouterManagement;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.MappingMessageRouterManagement;
import org.springframework.integration.support.management.micrometer.MicrometerMetricsCaptorConfiguration;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;

public class IntegrationGraphServer
implements ApplicationContextAware,
ApplicationListener<ContextRefreshedEvent> {
    private static final float GRAPH_VERSION = 1.2f;
    private static MicrometerNodeEnhancer micrometerEnhancer;
    private final NodeFactory nodeFactory = new NodeFactory();
    private ApplicationContext applicationContext;
    private Graph graph;
    private String applicationName;
    private Function<NamedComponent, Map<String, Object>> additionalPropertiesCallback;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setAdditionalPropertiesCallback(@Nullable Function<NamedComponent, Map<String, Object>> additionalPropertiesCallback) {
        this.additionalPropertiesCallback = additionalPropertiesCallback;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().equals(this.applicationContext)) {
            this.buildGraph();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Graph getGraph() {
        if (this.graph == null) {
            IntegrationGraphServer integrationGraphServer = this;
            synchronized (integrationGraphServer) {
                if (this.graph == null) {
                    this.buildGraph();
                }
            }
        }
        return this.graph;
    }

    public Graph rebuild() {
        return this.buildGraph();
    }

    protected <T> Map<String, T> getBeansOfType(Class<T> type) {
        return this.applicationContext.getBeansOfType(type, true, false);
    }

    private synchronized Graph buildGraph() {
        String implementationVersion;
        if (micrometerEnhancer == null && MicrometerMetricsCaptorConfiguration.METER_REGISTRY_PRESENT) {
            micrometerEnhancer = new MicrometerNodeEnhancer(this.applicationContext);
        }
        if ((implementationVersion = IntegrationGraphServer.class.getPackage().getImplementationVersion()) == null) {
            implementationVersion = "unknown - is Spring Integration running from the distribution jar?";
        }
        HashMap<String, Object> descriptor = new HashMap<String, Object>();
        descriptor.put("provider", "spring-integration");
        descriptor.put("providerVersion", implementationVersion);
        descriptor.put("providerFormatVersion", Float.valueOf(1.2f));
        String name = this.applicationName;
        if (name == null) {
            name = this.applicationContext.getEnvironment().getProperty("spring.application.name");
        }
        if (name != null) {
            descriptor.put("name", name);
        }
        this.nodeFactory.reset();
        ArrayList<IntegrationNode> nodes = new ArrayList<IntegrationNode>();
        ArrayList<LinkNode> links = new ArrayList<LinkNode>();
        Map<String, MessageChannelNode> channelNodes = this.channels(nodes);
        this.pollingAdapters(nodes, links, channelNodes);
        this.gateways(nodes, links, channelNodes);
        this.producers(nodes, links, channelNodes);
        this.consumers(nodes, links, channelNodes);
        this.graph = new Graph(descriptor, nodes, links);
        return this.graph;
    }

    private Map<String, MessageChannelNode> channels(Collection<IntegrationNode> nodes) {
        return this.getBeansOfType(MessageChannel.class).entrySet().stream().map(e -> {
            MessageChannel messageChannel = (MessageChannel)e.getValue();
            MessageChannelNode messageChannelNode = this.nodeFactory.channelNode((String)e.getKey(), messageChannel);
            if (messageChannel instanceof NamedComponent) {
                messageChannelNode.addProperties(this.getAdditionalPropertiesIfAny((NamedComponent)messageChannel));
            }
            return messageChannelNode;
        }).peek(nodes::add).collect(Collectors.toMap(IntegrationNode::getName, Function.identity()));
    }

    private void pollingAdapters(Collection<IntegrationNode> nodes, Collection<LinkNode> links, Map<String, MessageChannelNode> channelNodes) {
        this.getBeansOfType(SourcePollingChannelAdapter.class).entrySet().stream().map(e -> {
            SourcePollingChannelAdapter sourceAdapter = (SourcePollingChannelAdapter)e.getValue();
            MessageSourceNode sourceNode = this.nodeFactory.sourceNode((String)e.getKey(), sourceAdapter);
            sourceNode.addProperties(this.getAdditionalPropertiesIfAny(sourceAdapter));
            return sourceNode;
        }).peek(nodes::add).forEach(sourceNode -> this.producerLink(links, channelNodes, (EndpointNode)sourceNode));
    }

    private void gateways(Collection<IntegrationNode> nodes, Collection<LinkNode> links, Map<String, MessageChannelNode> channelNodes) {
        this.getBeansOfType(MessagingGatewaySupport.class).entrySet().stream().map(e -> {
            MessagingGatewaySupport gateway = (MessagingGatewaySupport)e.getValue();
            MessageGatewayNode gatewayNode = this.nodeFactory.gatewayNode((String)e.getKey(), gateway);
            gatewayNode.addProperties(this.getAdditionalPropertiesIfAny(gateway));
            return gatewayNode;
        }).peek(nodes::add).forEach(gatewayNode -> this.producerLink(links, channelNodes, (EndpointNode)gatewayNode));
        Map<String, GatewayProxyFactoryBean> gpfbs = this.getBeansOfType(GatewayProxyFactoryBean.class);
        for (Map.Entry<String, GatewayProxyFactoryBean> entry : gpfbs.entrySet()) {
            entry.getValue().getGateways().entrySet().stream().map(e -> {
                MessagingGatewaySupport gateway = (MessagingGatewaySupport)e.getValue();
                Method method = (Method)e.getKey();
                String nodeName = ((String)entry.getKey()).substring(1) + "." + method.getName() + "(" + Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(",")) + ")";
                MessageGatewayNode gatewayNode = this.nodeFactory.gatewayNode(nodeName, gateway);
                gatewayNode.addProperties(this.getAdditionalPropertiesIfAny(gateway));
                return gatewayNode;
            }).peek(nodes::add).forEach(gatewayNode -> this.producerLink(links, channelNodes, (EndpointNode)gatewayNode));
        }
    }

    private void producers(Collection<IntegrationNode> nodes, Collection<LinkNode> links, Map<String, MessageChannelNode> channelNodes) {
        this.getBeansOfType(MessageProducerSupport.class).entrySet().stream().map(e -> {
            MessageProducerSupport producer = (MessageProducerSupport)e.getValue();
            MessageProducerNode producerNode = this.nodeFactory.producerNode((String)e.getKey(), producer);
            producerNode.addProperties(this.getAdditionalPropertiesIfAny(producer));
            return producerNode;
        }).peek(nodes::add).forEach(producerNode -> this.producerLink(links, channelNodes, (EndpointNode)producerNode));
    }

    private void consumers(Collection<IntegrationNode> nodes, Collection<LinkNode> links, Map<String, MessageChannelNode> channelNodes) {
        this.getBeansOfType(IntegrationConsumer.class).entrySet().stream().map(e -> {
            IntegrationConsumer consumer = (IntegrationConsumer)e.getValue();
            MessageHandlerNode handlerNode = consumer instanceof PollingConsumer ? this.nodeFactory.polledHandlerNode((String)e.getKey(), (PollingConsumer)consumer) : this.nodeFactory.handlerNode((String)e.getKey(), consumer);
            handlerNode.addProperties(this.getAdditionalPropertiesIfAny(consumer));
            return handlerNode;
        }).peek(nodes::add).forEach(handlerNode -> {
            MessageChannelNode channelNode = (MessageChannelNode)channelNodes.get(handlerNode.getInput());
            if (channelNode != null) {
                links.add(new LinkNode(channelNode.getNodeId(), handlerNode.getNodeId(), LinkNode.Type.input));
            }
            this.producerLink(links, channelNodes, (EndpointNode)handlerNode);
        });
    }

    @Nullable
    private Map<String, Object> getAdditionalPropertiesIfAny(NamedComponent namedComponent) {
        if (this.additionalPropertiesCallback != null) {
            return this.additionalPropertiesCallback.apply(namedComponent);
        }
        return null;
    }

    private void producerLink(Collection<LinkNode> links, Map<String, MessageChannelNode> channelNodes, EndpointNode endpointNode) {
        MessageChannelNode channelNode;
        if (endpointNode.getOutput() != null && (channelNode = channelNodes.get(endpointNode.getOutput())) != null) {
            links.add(new LinkNode(endpointNode.getNodeId(), channelNode.getNodeId(), LinkNode.Type.output));
        }
        if (endpointNode instanceof ErrorCapableNode && (channelNode = channelNodes.get(((ErrorCapableNode)((Object)endpointNode)).getErrors())) != null) {
            links.add(new LinkNode(endpointNode.getNodeId(), channelNode.getNodeId(), LinkNode.Type.error));
        }
        if (endpointNode instanceof DiscardingMessageHandlerNode && (channelNode = channelNodes.get(((DiscardingMessageHandlerNode)endpointNode).getDiscards())) != null) {
            links.add(new LinkNode(endpointNode.getNodeId(), channelNode.getNodeId(), LinkNode.Type.discard));
        }
        if (endpointNode instanceof RoutingMessageHandlerNode) {
            Collection<String> routes = ((RoutingMessageHandlerNode)endpointNode).getRoutes();
            for (String route : routes) {
                channelNode = channelNodes.get(route);
                if (channelNode == null) continue;
                links.add(new LinkNode(endpointNode.getNodeId(), channelNode.getNodeId(), LinkNode.Type.route));
            }
        }
    }

    private static final class NodeFactory {
        private final AtomicInteger nodeId = new AtomicInteger();

        NodeFactory() {
        }

        MessageChannelNode channelNode(String name, MessageChannel channel) {
            MessageChannelNode node = channel instanceof PollableChannel ? new PollableChannelNode(this.nodeId.incrementAndGet(), name, channel) : new MessageChannelNode(this.nodeId.incrementAndGet(), name, channel);
            if (micrometerEnhancer != null) {
                node = micrometerEnhancer.enhance(node);
            }
            return node;
        }

        MessageGatewayNode gatewayNode(String name, MessagingGatewaySupport gateway) {
            String errorChannel = this.channelToBeanName(gateway.getErrorChannel());
            String requestChannel = this.channelToBeanName(gateway.getRequestChannel());
            return new MessageGatewayNode(this.nodeId.incrementAndGet(), name, gateway, requestChannel, errorChannel);
        }

        @Nullable
        private String channelToBeanName(MessageChannel messageChannel) {
            return messageChannel instanceof NamedComponent ? ((NamedComponent)messageChannel).getBeanName() : Objects.toString(messageChannel, null);
        }

        MessageProducerNode producerNode(String name, MessageProducerSupport producer) {
            String errorChannel = this.channelToBeanName(producer.getErrorChannel());
            String outputChannel = this.channelToBeanName(producer.getOutputChannel());
            return new MessageProducerNode(this.nodeId.incrementAndGet(), name, producer, outputChannel, errorChannel);
        }

        MessageSourceNode sourceNode(String name, SourcePollingChannelAdapter adapter) {
            String errorChannel = this.channelToBeanName(adapter.getDefaultErrorChannel());
            String outputChannel = this.channelToBeanName(adapter.getOutputChannel());
            String nameToUse = name;
            MessageSource<?> source = adapter.getMessageSource();
            if (source instanceof NamedComponent) {
                nameToUse = IntegrationUtils.obtainComponentName((NamedComponent)((Object)source));
            }
            MessageSourceNode node = new MessageSourceNode(this.nodeId.incrementAndGet(), nameToUse, source, outputChannel, errorChannel);
            if (micrometerEnhancer != null) {
                node = micrometerEnhancer.enhance(node);
            }
            return node;
        }

        MessageHandlerNode handlerNode(String nameArg, IntegrationConsumer consumer) {
            MessageHandlerNode node;
            String outputChannelName = this.channelToBeanName(consumer.getOutputChannel());
            MessageHandler handler = consumer.getHandler();
            String name = nameArg;
            if (handler instanceof NamedComponent) {
                name = IntegrationUtils.obtainComponentName((NamedComponent)handler);
            }
            if (handler instanceof CompositeMessageHandler) {
                node = this.compositeHandler(name, consumer, (CompositeMessageHandler)handler, outputChannelName, null, false);
            } else if (handler instanceof DiscardingMessageHandler) {
                node = this.discardingHandler(name, consumer, (DiscardingMessageHandler)handler, outputChannelName, null, false);
            } else if (handler instanceof MappingMessageRouterManagement) {
                node = this.routingHandler(name, consumer, handler, (MappingMessageRouterManagement)handler, outputChannelName, null, false);
            } else if (handler instanceof RecipientListRouterManagement) {
                node = this.recipientListRoutingHandler(name, consumer, handler, (RecipientListRouterManagement)handler, outputChannelName, null, false);
            } else {
                String inputChannel = this.channelToBeanName(consumer.getInputChannel());
                node = new MessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, outputChannelName);
            }
            if (micrometerEnhancer != null) {
                node = micrometerEnhancer.enhance(node);
            }
            return node;
        }

        MessageHandlerNode polledHandlerNode(String nameArg, PollingConsumer consumer) {
            MessageHandlerNode node;
            String outputChannelName = this.channelToBeanName(consumer.getOutputChannel());
            String errorChannel = this.channelToBeanName(consumer.getDefaultErrorChannel());
            MessageHandler handler = consumer.getHandler();
            String name = nameArg;
            if (handler instanceof NamedComponent) {
                name = IntegrationUtils.obtainComponentName((NamedComponent)handler);
            }
            if (handler instanceof CompositeMessageHandler) {
                node = this.compositeHandler(name, consumer, (CompositeMessageHandler)handler, outputChannelName, errorChannel, true);
            } else if (handler instanceof DiscardingMessageHandler) {
                node = this.discardingHandler(name, consumer, (DiscardingMessageHandler)handler, outputChannelName, errorChannel, true);
            } else if (handler instanceof MappingMessageRouterManagement) {
                node = this.routingHandler(name, consumer, handler, (MappingMessageRouterManagement)handler, outputChannelName, errorChannel, true);
            } else if (handler instanceof RecipientListRouterManagement) {
                node = this.recipientListRoutingHandler(name, consumer, handler, (RecipientListRouterManagement)handler, outputChannelName, errorChannel, true);
            } else {
                String inputChannel = this.channelToBeanName(consumer.getInputChannel());
                node = new ErrorCapableMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, outputChannelName, errorChannel);
            }
            if (micrometerEnhancer != null) {
                node = micrometerEnhancer.enhance(node);
            }
            return node;
        }

        MessageHandlerNode compositeHandler(String name, IntegrationConsumer consumer, CompositeMessageHandler handler, String output, String errors, boolean polled) {
            List<CompositeMessageHandlerNode.InnerHandler> innerHandlers = handler.getHandlers().stream().filter(NamedComponent.class::isInstance).map(NamedComponent.class::cast).map(named -> new CompositeMessageHandlerNode.InnerHandler(IntegrationUtils.obtainComponentName(named), named.getComponentType())).collect(Collectors.toList());
            String inputChannel = this.channelToBeanName(consumer.getInputChannel());
            return polled ? new ErrorCapableCompositeMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, errors, innerHandlers) : new CompositeMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, innerHandlers);
        }

        MessageHandlerNode discardingHandler(String name, IntegrationConsumer consumer, DiscardingMessageHandler handler, String output, String errors, boolean polled) {
            String discards = this.channelToBeanName(handler.getDiscardChannel());
            String inputChannel = this.channelToBeanName(consumer.getInputChannel());
            return polled ? new ErrorCapableDiscardingMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, discards, errors) : new DiscardingMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, discards);
        }

        MessageHandlerNode routingHandler(String name, IntegrationConsumer consumer, MessageHandler handler, MappingMessageRouterManagement router, String output, String errors, boolean polled) {
            Collection routes = Stream.concat(router.getChannelMappings().values().stream(), router.getDynamicChannelNames().stream()).collect(Collectors.toList());
            String inputChannel = this.channelToBeanName(consumer.getInputChannel());
            return polled ? new ErrorCapableRoutingNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, errors, routes) : new RoutingMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, routes);
        }

        MessageHandlerNode recipientListRoutingHandler(String name, IntegrationConsumer consumer, MessageHandler handler, RecipientListRouterManagement router, String output, String errors, boolean polled) {
            List<String> routes = router.getRecipients().stream().map(recipient -> this.channelToBeanName(((RecipientListRouter.Recipient)recipient).getChannel())).collect(Collectors.toList());
            String inputChannel = this.channelToBeanName(consumer.getInputChannel());
            return polled ? new ErrorCapableRoutingNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, errors, routes) : new RoutingMessageHandlerNode(this.nodeId.incrementAndGet(), name, handler, inputChannel, output, routes);
        }

        void reset() {
            this.nodeId.set(0);
        }
    }
}

