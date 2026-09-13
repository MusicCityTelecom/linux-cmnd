/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache$Compiler
 *  org.springframework.boot.web.servlet.view.MustacheViewResolver
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={MustacheViewResolver.class})
class MustacheServletWebConfiguration {
    MustacheServletWebConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="spring.mustache", name={"enabled"}, matchIfMissing=true)
    MustacheViewResolver mustacheViewResolver(Mustache.Compiler mustacheCompiler, MustacheProperties mustache) {
        MustacheViewResolver resolver = new MustacheViewResolver(mustacheCompiler);
        resolver.setPrefix(mustache.getPrefix());
        resolver.setSuffix(mustache.getSuffix());
        resolver.setCache(mustache.getServlet().isCache());
        if (mustache.getServlet().getContentType() != null) {
            resolver.setContentType(mustache.getServlet().getContentType().toString());
        }
        resolver.setViewNames(mustache.getViewNames());
        resolver.setExposeRequestAttributes(mustache.getServlet().isExposeRequestAttributes());
        resolver.setAllowRequestOverride(mustache.getServlet().isAllowRequestOverride());
        resolver.setAllowSessionOverride(mustache.getServlet().isAllowSessionOverride());
        resolver.setExposeSessionAttributes(mustache.getServlet().isExposeSessionAttributes());
        resolver.setExposeSpringMacroHelpers(mustache.getServlet().isExposeSpringMacroHelpers());
        resolver.setRequestContextAttribute(mustache.getRequestContextAttribute());
        resolver.setCharset(mustache.getCharsetName());
        resolver.setOrder(0x7FFFFFF5);
        return resolver;
    }
}

