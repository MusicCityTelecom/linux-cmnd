/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderFactoryBean
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent
 *  org.springframework.context.event.EventListener
 */
package org.apereo.cas.authentication;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderFactoryBean;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;

public class MultifactorAuthenticationProviderBean<T extends MultifactorAuthenticationProvider, P extends BaseMultifactorAuthenticationProviderProperties>
implements InitializingBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(MultifactorAuthenticationProviderBean.class);
    private final MultifactorAuthenticationProviderFactoryBean<T, P> providerFactory;
    private final DefaultListableBeanFactory beanFactory;
    private final List<P> properties;

    public void afterPropertiesSet() {
        this.properties.forEach(p -> {
            String name = this.providerFactory.beanName(p.getId());
            this.beanFactory.destroySingleton(name);
            this.beanFactory.registerSingleton(name, (Object)this.providerFactory.createProvider(p));
        });
    }

    public T getProvider(String id) {
        return (T)((MultifactorAuthenticationProvider)this.beanFactory.getBean(this.providerFactory.beanName(id)));
    }

    @EventListener
    public void onRefreshScopeRefreshed(RefreshScopeRefreshedEvent event) {
        LOGGER.trace("Refreshing MFA Providers...");
    }

    @Generated
    public MultifactorAuthenticationProviderBean(MultifactorAuthenticationProviderFactoryBean<T, P> providerFactory, DefaultListableBeanFactory beanFactory, List<P> properties) {
        this.providerFactory = providerFactory;
        this.beanFactory = beanFactory;
        this.properties = properties;
    }
}

