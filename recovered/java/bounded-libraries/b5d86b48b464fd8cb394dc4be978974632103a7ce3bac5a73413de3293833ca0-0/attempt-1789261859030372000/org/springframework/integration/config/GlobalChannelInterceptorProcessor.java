/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.beans.factory.config.BeanPostProcessor
 *  org.springframework.core.OrderComparator
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.InterceptableChannel
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.OrderComparator;
import org.springframework.integration.channel.interceptor.GlobalChannelInterceptorWrapper;
import org.springframework.integration.channel.interceptor.VetoCapableInterceptor;
import org.springframework.integration.support.utils.PatternMatchUtils;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class GlobalChannelInterceptorProcessor
implements BeanFactoryAware,
SmartInitializingSingleton,
BeanPostProcessor {
    private static final Log LOGGER = LogFactory.getLog(GlobalChannelInterceptorProcessor.class);
    private final OrderComparator comparator = new OrderComparator();
    private final Set<GlobalChannelInterceptorWrapper> positiveOrderInterceptors = new LinkedHashSet<GlobalChannelInterceptorWrapper>();
    private final Set<GlobalChannelInterceptorWrapper> negativeOrderInterceptors = new LinkedHashSet<GlobalChannelInterceptorWrapper>();
    private ListableBeanFactory beanFactory;
    private volatile boolean singletonsInstantiated;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ListableBeanFactory.class, (Object)beanFactory);
        this.beanFactory = (ListableBeanFactory)beanFactory;
    }

    public void afterSingletonsInstantiated() {
        Collection interceptors = this.beanFactory.getBeansOfType(GlobalChannelInterceptorWrapper.class).values();
        if (CollectionUtils.isEmpty(interceptors)) {
            LOGGER.debug((Object)"No global channel interceptors.");
        } else {
            interceptors.forEach(interceptor -> {
                if (interceptor.getOrder() >= 0) {
                    this.positiveOrderInterceptors.add((GlobalChannelInterceptorWrapper)interceptor);
                } else {
                    this.negativeOrderInterceptors.add((GlobalChannelInterceptorWrapper)interceptor);
                }
            });
            this.beanFactory.getBeansOfType(InterceptableChannel.class).forEach((key, value) -> this.addMatchingInterceptors((InterceptableChannel)value, (String)key));
        }
        this.singletonsInstantiated = true;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.singletonsInstantiated && bean instanceof InterceptableChannel) {
            this.addMatchingInterceptors((InterceptableChannel)bean, beanName);
        }
        return bean;
    }

    public void addMatchingInterceptors(InterceptableChannel channel, String beanName) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("Applying global interceptors on channel '" + beanName + "'"));
        }
        ArrayList tempInterceptors = new ArrayList();
        this.positiveOrderInterceptors.forEach(interceptorWrapper -> GlobalChannelInterceptorProcessor.addMatchingInterceptors(beanName, tempInterceptors, interceptorWrapper));
        tempInterceptors.sort(this.comparator);
        tempInterceptors.stream().map(GlobalChannelInterceptorWrapper::getChannelInterceptor).filter(interceptor -> !(interceptor instanceof VetoCapableInterceptor) || ((VetoCapableInterceptor)interceptor).shouldIntercept(beanName, channel)).forEach(arg_0 -> ((InterceptableChannel)channel).addInterceptor(arg_0));
        tempInterceptors.clear();
        this.negativeOrderInterceptors.forEach(interceptorWrapper -> GlobalChannelInterceptorProcessor.addMatchingInterceptors(beanName, tempInterceptors, interceptorWrapper));
        tempInterceptors.sort(this.comparator);
        if (!tempInterceptors.isEmpty()) {
            for (int i = tempInterceptors.size() - 1; i >= 0; --i) {
                ChannelInterceptor channelInterceptor = ((GlobalChannelInterceptorWrapper)tempInterceptors.get(i)).getChannelInterceptor();
                if (channelInterceptor instanceof VetoCapableInterceptor && !((VetoCapableInterceptor)channelInterceptor).shouldIntercept(beanName, channel)) continue;
                channel.addInterceptor(0, channelInterceptor);
            }
        }
    }

    private static void addMatchingInterceptors(String beanName, List<GlobalChannelInterceptorWrapper> tempInterceptors, GlobalChannelInterceptorWrapper globalChannelInterceptorWrapper) {
        String[] patterns = globalChannelInterceptorWrapper.getPatterns();
        patterns = StringUtils.trimArrayElements((String[])patterns);
        if (beanName != null && Boolean.TRUE.equals(PatternMatchUtils.smartMatch(beanName, patterns))) {
            tempInterceptors.add(globalChannelInterceptorWrapper);
        }
    }
}

