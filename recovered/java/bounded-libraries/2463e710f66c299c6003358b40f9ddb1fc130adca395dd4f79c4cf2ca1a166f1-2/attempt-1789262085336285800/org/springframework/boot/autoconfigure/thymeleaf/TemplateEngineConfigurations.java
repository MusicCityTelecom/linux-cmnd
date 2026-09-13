/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.thymeleaf.dialect.IDialect
 *  org.thymeleaf.spring5.ISpringTemplateEngine
 *  org.thymeleaf.spring5.ISpringWebFluxTemplateEngine
 *  org.thymeleaf.spring5.SpringTemplateEngine
 *  org.thymeleaf.spring5.SpringWebFluxTemplateEngine
 *  org.thymeleaf.templateresolver.ITemplateResolver
 */
package org.springframework.boot.autoconfigure.thymeleaf;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

class TemplateEngineConfigurations {
    TemplateEngineConfigurations() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnProperty(name={"spring.thymeleaf.enabled"}, matchIfMissing=true)
    static class ReactiveTemplateEngineConfiguration {
        ReactiveTemplateEngineConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={ISpringWebFluxTemplateEngine.class})
        SpringWebFluxTemplateEngine templateEngine(ThymeleafProperties properties, ObjectProvider<ITemplateResolver> templateResolvers, ObjectProvider<IDialect> dialects) {
            SpringWebFluxTemplateEngine engine = new SpringWebFluxTemplateEngine();
            engine.setEnableSpringELCompiler(properties.isEnableSpringElCompiler());
            engine.setRenderHiddenMarkersBeforeCheckboxes(properties.isRenderHiddenMarkersBeforeCheckboxes());
            templateResolvers.orderedStream().forEach(arg_0 -> ((SpringWebFluxTemplateEngine)engine).addTemplateResolver(arg_0));
            dialects.orderedStream().forEach(arg_0 -> ((SpringWebFluxTemplateEngine)engine).addDialect(arg_0));
            return engine;
        }
    }

    @Configuration(proxyBeanMethods=false)
    static class DefaultTemplateEngineConfiguration {
        DefaultTemplateEngineConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={ISpringTemplateEngine.class})
        SpringTemplateEngine templateEngine(ThymeleafProperties properties, ObjectProvider<ITemplateResolver> templateResolvers, ObjectProvider<IDialect> dialects) {
            SpringTemplateEngine engine = new SpringTemplateEngine();
            engine.setEnableSpringELCompiler(properties.isEnableSpringElCompiler());
            engine.setRenderHiddenMarkersBeforeCheckboxes(properties.isRenderHiddenMarkersBeforeCheckboxes());
            templateResolvers.orderedStream().forEach(arg_0 -> ((SpringTemplateEngine)engine).addTemplateResolver(arg_0));
            dialects.orderedStream().forEach(arg_0 -> ((SpringTemplateEngine)engine).addDialect(arg_0));
            return engine;
        }
    }
}

