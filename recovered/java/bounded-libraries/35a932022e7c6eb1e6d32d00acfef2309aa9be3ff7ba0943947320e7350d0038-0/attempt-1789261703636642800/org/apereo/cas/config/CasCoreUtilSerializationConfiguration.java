/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.apereo.cas.util.serialization.ComponentSerializationPlan
 *  org.apereo.cas.util.serialization.ComponentSerializationPlanConfigurer
 *  org.apereo.cas.util.serialization.DefaultComponentSerializationPlan
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.model.TriStateBoolean;
import org.apereo.cas.util.serialization.ComponentSerializationPlan;
import org.apereo.cas.util.serialization.ComponentSerializationPlanConfigurer;
import org.apereo.cas.util.serialization.DefaultComponentSerializationPlan;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Core)
@AutoConfiguration
public class CasCoreUtilSerializationConfiguration {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreUtilSerializationConfiguration.class);

    @ConditionalOnMissingBean(name={"componentSerializationPlan"})
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public ComponentSerializationPlan componentSerializationPlan(ObjectProvider<List<ComponentSerializationPlanConfigurer>> configurers) {
        DefaultComponentSerializationPlan plan = new DefaultComponentSerializationPlan();
        plan.registerSerializableClass(TriStateBoolean.class);
        configurers.ifAvailable(cfgs -> cfgs.forEach(c -> {
            LOGGER.trace("Configuring component serialization plan [{}]", (Object)c.getName());
            c.configureComponentSerializationPlan((ComponentSerializationPlan)plan);
        }));
        return plan;
    }
}

