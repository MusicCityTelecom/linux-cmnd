/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.aop.framework.Advised
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.GatewayProxySpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageProducerSpec;
import org.springframework.integration.dsl.MessageSourceSpec;
import org.springframework.integration.dsl.MessagingGatewaySpec;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.support.FixedSubscriberChannelPrototype;
import org.springframework.integration.dsl.support.MessageChannelReference;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public final class IntegrationFlows {
    public static IntegrationFlowBuilder from(String messageChannelName) {
        return IntegrationFlows.from(new MessageChannelReference(messageChannelName));
    }

    public static IntegrationFlowBuilder from(String messageChannelName, boolean fixedSubscriber) {
        return fixedSubscriber ? IntegrationFlows.from(new FixedSubscriberChannelPrototype(messageChannelName)) : IntegrationFlows.from(messageChannelName);
    }

    public static IntegrationFlowBuilder from(MessageChannelSpec<?, ?> messageChannelSpec) {
        Assert.notNull(messageChannelSpec, (String)"'messageChannelSpec' must not be null");
        return IntegrationFlows.from((MessageChannel)messageChannelSpec.get());
    }

    public static IntegrationFlowBuilder from(MessageChannel messageChannel) {
        return (IntegrationFlowBuilder)new IntegrationFlowBuilder().channel(messageChannel);
    }

    public static IntegrationFlowBuilder from(MessageSourceSpec<?, ? extends MessageSource<?>> messageSourceSpec) {
        return IntegrationFlows.from(messageSourceSpec, null);
    }

    public static IntegrationFlowBuilder from(MessageSourceSpec<?, ? extends MessageSource<?>> messageSourceSpec, Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
        Assert.notNull(messageSourceSpec, (String)"'messageSourceSpec' must not be null");
        return IntegrationFlows.from((MessageSource)messageSourceSpec.get(), endpointConfigurer, IntegrationFlows.registerComponents(messageSourceSpec));
    }

    public static <T> IntegrationFlowBuilder fromSupplier(Supplier<T> messageSource) {
        return IntegrationFlows.fromSupplier(messageSource, null);
    }

    public static <T> IntegrationFlowBuilder fromSupplier(final Supplier<T> messageSource, Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
        Assert.notNull(messageSource, (String)"'messageSource' must not be null");
        return IntegrationFlows.from(new AbstractMessageSource<Object>(){

            @Override
            protected Object doReceive() {
                return messageSource.get();
            }

            @Override
            public String getComponentType() {
                return "inbound-channel-adapter";
            }
        }, endpointConfigurer);
    }

    public static IntegrationFlowBuilder from(MessageSource<?> messageSource) {
        return IntegrationFlows.from(messageSource, null);
    }

    public static IntegrationFlowBuilder from(MessageSource<?> messageSource, @Nullable Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
        return IntegrationFlows.from(messageSource, endpointConfigurer, null);
    }

    private static IntegrationFlowBuilder from(MessageSource<?> messageSource, @Nullable Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer, @Nullable IntegrationFlowBuilder integrationFlowBuilderArg) {
        IntegrationFlowBuilder integrationFlowBuilder = integrationFlowBuilderArg;
        SourcePollingChannelAdapterSpec spec = new SourcePollingChannelAdapterSpec(messageSource);
        if (endpointConfigurer != null) {
            endpointConfigurer.accept(spec);
        }
        if (integrationFlowBuilder == null) {
            integrationFlowBuilder = new IntegrationFlowBuilder();
        }
        return (IntegrationFlowBuilder)((IntegrationFlowBuilder)integrationFlowBuilder.addComponent(spec)).currentComponent(spec);
    }

    public static IntegrationFlowBuilder from(MessageProducerSpec<?, ?> messageProducerSpec) {
        return IntegrationFlows.from((MessageProducerSupport)messageProducerSpec.get(), IntegrationFlows.registerComponents(messageProducerSpec));
    }

    public static IntegrationFlowBuilder from(MessageProducerSupport messageProducer) {
        return IntegrationFlows.from(messageProducer, null);
    }

    private static IntegrationFlowBuilder from(MessageProducerSupport messageProducer, @Nullable IntegrationFlowBuilder integrationFlowBuilderArg) {
        IntegrationFlowBuilder integrationFlowBuilder = integrationFlowBuilderArg;
        MessageChannel outputChannel = messageProducer.getOutputChannel();
        if (outputChannel == null) {
            outputChannel = new DirectChannel();
            messageProducer.setOutputChannel(outputChannel);
        }
        if (integrationFlowBuilder == null) {
            integrationFlowBuilder = IntegrationFlows.from(outputChannel);
        } else {
            integrationFlowBuilder.channel(outputChannel);
        }
        return (IntegrationFlowBuilder)integrationFlowBuilder.addComponent(messageProducer);
    }

    public static IntegrationFlowBuilder from(MessagingGatewaySpec<?, ?> inboundGatewaySpec) {
        return IntegrationFlows.from((MessagingGatewaySupport)inboundGatewaySpec.get(), IntegrationFlows.registerComponents(inboundGatewaySpec));
    }

    public static IntegrationFlowBuilder from(MessagingGatewaySupport inboundGateway) {
        return IntegrationFlows.from(inboundGateway, null);
    }

    public static IntegrationFlowBuilder from(Class<?> serviceInterface) {
        return IntegrationFlows.from(serviceInterface, null);
    }

    public static IntegrationFlowBuilder from(Class<?> serviceInterface, @Nullable Consumer<GatewayProxySpec> endpointConfigurer) {
        GatewayProxySpec gatewayProxySpec = new GatewayProxySpec(serviceInterface);
        if (endpointConfigurer != null) {
            endpointConfigurer.accept(gatewayProxySpec);
        }
        return (IntegrationFlowBuilder)IntegrationFlows.from(gatewayProxySpec.getGatewayRequestChannel()).addComponent(gatewayProxySpec.getGatewayProxyFactoryBean());
    }

    public static IntegrationFlowBuilder from(Publisher<? extends Message<?>> publisher) {
        FluxMessageChannel reactiveChannel = new FluxMessageChannel();
        reactiveChannel.subscribeTo(publisher);
        return IntegrationFlows.from(reactiveChannel);
    }

    public static IntegrationFlowBuilder from(IntegrationFlow other) {
        Map<Object, String> integrationComponents = other.getIntegrationComponents();
        Assert.notNull(integrationComponents, () -> "The provided integration flow to compose from '" + other + "' must be declared as a bean in the application context");
        MessageHandler lastIntegrationComponentFromOther = integrationComponents.keySet().stream().reduce((prev, next) -> next).orElse(null);
        if (lastIntegrationComponentFromOther instanceof MessageChannel) {
            return IntegrationFlows.from((MessageChannel)lastIntegrationComponentFromOther);
        }
        if (lastIntegrationComponentFromOther instanceof ConsumerEndpointFactoryBean) {
            MessageHandler handler = ((ConsumerEndpointFactoryBean)lastIntegrationComponentFromOther).getHandler();
            if ((handler = IntegrationFlows.extractProxyTarget(handler)) instanceof AbstractMessageProducingHandler) {
                return IntegrationFlows.buildFlowFromOutputChannel((AbstractMessageProducingHandler)handler);
            }
            lastIntegrationComponentFromOther = handler;
        }
        throw new BeanCreationException("The 'IntegrationFlow' to start from must end with a 'MessageChannel' or reply-producing endpoint to let the result from that flow to be processed in this instance. The provided flow ends with: " + lastIntegrationComponentFromOther);
    }

    private static IntegrationFlowBuilder buildFlowFromOutputChannel(AbstractMessageProducingHandler handler) {
        MessageChannel outputChannel = handler.getOutputChannel();
        if (outputChannel == null) {
            outputChannel = new PublishSubscribeChannel();
            handler.setOutputChannel(outputChannel);
        }
        return IntegrationFlows.from(outputChannel);
    }

    private static IntegrationFlowBuilder from(MessagingGatewaySupport inboundGateway, @Nullable IntegrationFlowBuilder integrationFlowBuilderArg) {
        IntegrationFlowBuilder integrationFlowBuilder = integrationFlowBuilderArg;
        MessageChannel outputChannel = inboundGateway.getRequestChannel();
        if (outputChannel == null) {
            outputChannel = new DirectChannel();
            inboundGateway.setRequestChannel(outputChannel);
        }
        if (integrationFlowBuilder == null) {
            integrationFlowBuilder = IntegrationFlows.from(outputChannel);
        } else {
            integrationFlowBuilder.channel(outputChannel);
        }
        return (IntegrationFlowBuilder)integrationFlowBuilder.addComponent(inboundGateway);
    }

    private static IntegrationFlowBuilder registerComponents(Object spec) {
        if (spec instanceof ComponentsRegistration) {
            return (IntegrationFlowBuilder)new IntegrationFlowBuilder().addComponents(((ComponentsRegistration)spec).getComponentsToRegister());
        }
        return null;
    }

    private static <T> T extractProxyTarget(T target) {
        if (!(target instanceof Advised)) {
            return target;
        }
        Advised advised = (Advised)target;
        try {
            return (T)IntegrationFlows.extractProxyTarget(advised.getTargetSource().getTarget());
        }
        catch (Exception e) {
            throw new BeanCreationException("Could not extract target", (Throwable)e);
        }
    }

    private IntegrationFlows() {
    }
}

