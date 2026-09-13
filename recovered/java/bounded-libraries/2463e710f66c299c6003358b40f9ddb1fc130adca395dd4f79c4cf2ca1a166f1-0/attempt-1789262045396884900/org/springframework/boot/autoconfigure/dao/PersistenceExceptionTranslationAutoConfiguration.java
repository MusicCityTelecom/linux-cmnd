/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.env.Environment
 *  org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
 */
package org.springframework.boot.autoconfigure.dao;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@AutoConfiguration
@ConditionalOnClass(value={PersistenceExceptionTranslationPostProcessor.class})
public class PersistenceExceptionTranslationAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="spring.dao.exceptiontranslation", name={"enabled"}, matchIfMissing=true)
    public static PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(Environment environment) {
        PersistenceExceptionTranslationPostProcessor postProcessor = new PersistenceExceptionTranslationPostProcessor();
        boolean proxyTargetClass = (Boolean)environment.getProperty("spring.aop.proxy-target-class", Boolean.class, (Object)Boolean.TRUE);
        postProcessor.setProxyTargetClass(proxyTargetClass);
        return postProcessor;
    }
}

