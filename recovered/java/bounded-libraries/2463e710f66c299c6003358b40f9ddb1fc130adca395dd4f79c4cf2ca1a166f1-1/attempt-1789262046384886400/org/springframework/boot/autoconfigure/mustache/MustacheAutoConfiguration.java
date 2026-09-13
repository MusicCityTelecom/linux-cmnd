/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache
 *  com.samskivert.mustache.Mustache$Compiler
 *  com.samskivert.mustache.Mustache$TemplateLoader
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.io.support.ResourcePatternResolver
 */
package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.Mustache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.boot.autoconfigure.mustache.MustacheReactiveWebConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.boot.autoconfigure.mustache.MustacheServletWebConfiguration;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;

@AutoConfiguration
@ConditionalOnClass(value={Mustache.class})
@EnableConfigurationProperties(value={MustacheProperties.class})
@Import(value={MustacheServletWebConfiguration.class, MustacheReactiveWebConfiguration.class})
public class MustacheAutoConfiguration {
    private static final Log logger = LogFactory.getLog(MustacheAutoConfiguration.class);
    private final MustacheProperties mustache;
    private final ApplicationContext applicationContext;

    public MustacheAutoConfiguration(MustacheProperties mustache, ApplicationContext applicationContext) {
        this.mustache = mustache;
        this.applicationContext = applicationContext;
        this.checkTemplateLocationExists();
    }

    public void checkTemplateLocationExists() {
        TemplateLocation location;
        if (this.mustache.isCheckTemplateLocation() && !(location = new TemplateLocation(this.mustache.getPrefix())).exists((ResourcePatternResolver)this.applicationContext) && logger.isWarnEnabled()) {
            logger.warn((Object)("Cannot find template location: " + location + " (please add some templates, check your Mustache configuration, or set spring.mustache.check-template-location=false)"));
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader mustacheTemplateLoader) {
        return Mustache.compiler().withLoader(mustacheTemplateLoader);
    }

    @Bean
    @ConditionalOnMissingBean(value={Mustache.TemplateLoader.class})
    public MustacheResourceTemplateLoader mustacheTemplateLoader() {
        MustacheResourceTemplateLoader loader = new MustacheResourceTemplateLoader(this.mustache.getPrefix(), this.mustache.getSuffix());
        loader.setCharset(this.mustache.getCharsetName());
        return loader;
    }
}

