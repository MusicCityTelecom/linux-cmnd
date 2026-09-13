/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.integration.config.IntegrationConfigUtils;
import org.springframework.util.Assert;

public final class ChannelInitializer
implements BeanFactoryAware,
InitializingBean {
    private static final Log LOGGER = LogFactory.getLog(ChannelInitializer.class);
    private volatile BeanFactory beanFactory;
    private volatile boolean autoCreate = true;

    ChannelInitializer() {
    }

    public void setAutoCreate(boolean autoCreate) {
        this.autoCreate = autoCreate;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.beanFactory, (String)"'beanFactory' must not be null");
        if (!this.autoCreate) {
            return;
        }
        AutoCreateCandidatesCollector channelCandidatesCollector = (AutoCreateCandidatesCollector)this.beanFactory.getBean("$autoCreateChannelCandidates", AutoCreateCandidatesCollector.class);
        Collection<String> channelNames = channelCandidatesCollector.getChannelNames();
        if (channelNames != null) {
            for (String channelName : channelNames) {
                if (this.beanFactory.containsBean(channelName)) continue;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Auto-creating channel '" + channelName + "' as DirectChannel"));
                }
                IntegrationConfigUtils.autoCreateDirectChannel(channelName, (BeanDefinitionRegistry)this.beanFactory);
            }
        }
    }

    public static class AutoCreateCandidatesCollector {
        private final Collection<String> channelNames;

        AutoCreateCandidatesCollector(Collection<String> channelNames) {
            this.channelNames = channelNames;
        }

        public Collection<String> getChannelNames() {
            return this.channelNames;
        }
    }
}

