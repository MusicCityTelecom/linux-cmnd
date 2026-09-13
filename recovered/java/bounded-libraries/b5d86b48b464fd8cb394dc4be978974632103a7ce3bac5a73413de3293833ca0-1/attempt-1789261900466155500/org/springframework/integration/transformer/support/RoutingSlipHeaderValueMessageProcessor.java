/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.routingslip.ExpressionEvaluatingRoutingSlipRouteStrategy;
import org.springframework.integration.routingslip.RoutingSlipRouteStrategy;
import org.springframework.integration.transformer.support.AbstractHeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class RoutingSlipHeaderValueMessageProcessor
extends AbstractHeaderValueMessageProcessor<Map<List<Object>, Integer>>
implements BeanFactoryAware {
    private final List<Object> routingSlipPath;
    private volatile Map<List<Object>, Integer> routingSlip;
    private BeanFactory beanFactory;

    public RoutingSlipHeaderValueMessageProcessor(Object ... routingSlipPath) {
        Assert.notNull((Object)routingSlipPath, (String)"'routingSlipPath' must not be null");
        Assert.noNullElements((Object[])routingSlipPath, (String)"'routingSlipPath' must not contain null elements");
        for (Object entry : routingSlipPath) {
            if (entry instanceof String || entry instanceof MessageChannel || entry instanceof RoutingSlipRouteStrategy) continue;
            throw new IllegalArgumentException("The RoutingSlip can contain only bean names of MessageChannel or RoutingSlipRouteStrategy, or MessageChannel and RoutingSlipRouteStrategy instances: " + entry);
        }
        this.routingSlipPath = Arrays.asList(routingSlipPath);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.notNull((Object)beanFactory, (String)"beanFactory must not be null");
        this.beanFactory = beanFactory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<List<Object>, Integer> processMessage(Message<?> message) {
        Map<List<Object>, Integer> slip = this.routingSlip;
        if (slip == null) {
            RoutingSlipHeaderValueMessageProcessor routingSlipHeaderValueMessageProcessor = this;
            synchronized (routingSlipHeaderValueMessageProcessor) {
                slip = this.routingSlip;
                if (slip == null) {
                    List<Object> slipPath = this.routingSlipPath;
                    ArrayList<Object> routingSlipValues = new ArrayList<Object>(slipPath.size());
                    for (Object path : slipPath) {
                        if (path instanceof String) {
                            String entry = (String)path;
                            if (this.beanFactory.containsBean(entry)) {
                                Object bean = this.beanFactory.getBean(entry);
                                if (!(bean instanceof MessageChannel) && !(bean instanceof RoutingSlipRouteStrategy)) {
                                    throw new IllegalArgumentException("The RoutingSlip can contain only bean names of MessageChannel or RoutingSlipRouteStrategy: " + bean);
                                }
                                routingSlipValues.add(entry);
                                continue;
                            }
                            ExpressionEvaluatingRoutingSlipRouteStrategy strategy = new ExpressionEvaluatingRoutingSlipRouteStrategy(entry);
                            strategy.setBeanFactory(this.beanFactory);
                            try {
                                strategy.afterPropertiesSet();
                            }
                            catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                            routingSlipValues.add(strategy);
                            continue;
                        }
                        routingSlipValues.add(path);
                    }
                    this.routingSlip = slip = Collections.singletonMap(Collections.unmodifiableList(routingSlipValues), 0);
                }
            }
        }
        return slip;
    }
}

