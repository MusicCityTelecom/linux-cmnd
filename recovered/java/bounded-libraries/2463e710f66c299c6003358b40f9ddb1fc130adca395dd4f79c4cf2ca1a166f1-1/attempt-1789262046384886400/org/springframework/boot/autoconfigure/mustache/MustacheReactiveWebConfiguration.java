/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache$Compiler
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.reactive.result.view.MustacheViewResolver
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.reactive.result.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
class MustacheReactiveWebConfiguration {
    MustacheReactiveWebConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="spring.mustache", name={"enabled"}, matchIfMissing=true)
    MustacheViewResolver mustacheViewResolver(Mustache.Compiler mustacheCompiler, MustacheProperties mustache) {
        MustacheViewResolver resolver = new MustacheViewResolver(mustacheCompiler);
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(mustache::getPrefix).to(arg_0 -> ((MustacheViewResolver)resolver).setPrefix(arg_0));
        map.from(mustache::getSuffix).to(arg_0 -> ((MustacheViewResolver)resolver).setSuffix(arg_0));
        map.from(mustache::getViewNames).to(arg_0 -> ((MustacheViewResolver)resolver).setViewNames(arg_0));
        map.from(mustache::getRequestContextAttribute).to(arg_0 -> ((MustacheViewResolver)resolver).setRequestContextAttribute(arg_0));
        map.from(mustache::getCharsetName).to(arg_0 -> ((MustacheViewResolver)resolver).setCharset(arg_0));
        map.from(mustache.getReactive()::getMediaTypes).to(arg_0 -> ((MustacheViewResolver)resolver).setSupportedMediaTypes(arg_0));
        resolver.setOrder(0x7FFFFFF5);
        return resolver;
    }
}

