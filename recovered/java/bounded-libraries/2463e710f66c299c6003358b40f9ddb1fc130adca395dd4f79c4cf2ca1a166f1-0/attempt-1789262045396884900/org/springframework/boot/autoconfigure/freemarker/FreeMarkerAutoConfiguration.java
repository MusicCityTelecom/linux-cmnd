/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  freemarker.template.Configuration
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
 */
package org.springframework.boot.autoconfigure.freemarker;

import freemarker.template.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerNonWebConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerReactiveWebConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerServletWebConfiguration;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

@AutoConfiguration
@ConditionalOnClass(value={Configuration.class, FreeMarkerConfigurationFactory.class})
@EnableConfigurationProperties(value={FreeMarkerProperties.class})
@Import(value={FreeMarkerServletWebConfiguration.class, FreeMarkerReactiveWebConfiguration.class, FreeMarkerNonWebConfiguration.class})
public class FreeMarkerAutoConfiguration {
    private static final Log logger = LogFactory.getLog(FreeMarkerAutoConfiguration.class);
    private final ApplicationContext applicationContext;
    private final FreeMarkerProperties properties;

    public FreeMarkerAutoConfiguration(ApplicationContext applicationContext, FreeMarkerProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
        this.checkTemplateLocationExists();
    }

    public void checkTemplateLocationExists() {
        List<TemplateLocation> locations;
        if (logger.isWarnEnabled() && this.properties.isCheckTemplateLocation() && (locations = this.getLocations()).stream().noneMatch(this::locationExists)) {
            logger.warn((Object)("Cannot find template location(s): " + locations + " (please add some templates, check your FreeMarker configuration, or set spring.freemarker.checkTemplateLocation=false)"));
        }
    }

    private List<TemplateLocation> getLocations() {
        ArrayList<TemplateLocation> locations = new ArrayList<TemplateLocation>();
        for (String templateLoaderPath : this.properties.getTemplateLoaderPath()) {
            TemplateLocation location = new TemplateLocation(templateLoaderPath);
            locations.add(location);
        }
        return locations;
    }

    private boolean locationExists(TemplateLocation location) {
        return location.exists((ResourcePatternResolver)this.applicationContext);
    }
}

