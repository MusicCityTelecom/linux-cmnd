/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.PropertySources
 *  org.springframework.core.env.PropertySourcesPropertyResolver
 *  org.springframework.core.io.Resource
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.StreamUtils
 */
package org.springframework.boot;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiPropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

public class ResourceBanner
implements Banner {
    private static final Log logger = LogFactory.getLog(ResourceBanner.class);
    private Resource resource;

    public ResourceBanner(Resource resource) {
        Assert.notNull((Object)resource, (String)"Resource must not be null");
        Assert.isTrue((boolean)resource.exists(), (String)"Resource must exist");
        this.resource = resource;
    }

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        try {
            String banner = StreamUtils.copyToString((InputStream)this.resource.getInputStream(), (Charset)((Charset)environment.getProperty("spring.banner.charset", Charset.class, (Object)StandardCharsets.UTF_8)));
            for (PropertyResolver resolver : this.getPropertyResolvers(environment, sourceClass)) {
                banner = resolver.resolvePlaceholders(banner);
            }
            out.println(banner);
        }
        catch (Exception ex) {
            logger.warn((Object)LogMessage.format((String)"Banner not printable: %s (%s: '%s')", (Object)this.resource, ex.getClass(), (Object)ex.getMessage()), (Throwable)ex);
        }
    }

    protected List<PropertyResolver> getPropertyResolvers(Environment environment, Class<?> sourceClass) {
        ArrayList<PropertyResolver> resolvers = new ArrayList<PropertyResolver>();
        resolvers.add((PropertyResolver)environment);
        resolvers.add(this.getVersionResolver(sourceClass));
        resolvers.add(this.getAnsiResolver());
        resolvers.add(this.getTitleResolver(sourceClass));
        return resolvers;
    }

    private PropertyResolver getVersionResolver(Class<?> sourceClass) {
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast((PropertySource)new MapPropertySource("version", this.getVersionsMap(sourceClass)));
        return new PropertySourcesPropertyResolver((PropertySources)propertySources);
    }

    private Map<String, Object> getVersionsMap(Class<?> sourceClass) {
        String appVersion = this.getApplicationVersion(sourceClass);
        String bootVersion = this.getBootVersion();
        HashMap<String, Object> versions = new HashMap<String, Object>();
        versions.put("application.version", this.getVersionString(appVersion, false));
        versions.put("spring-boot.version", this.getVersionString(bootVersion, false));
        versions.put("application.formatted-version", this.getVersionString(appVersion, true));
        versions.put("spring-boot.formatted-version", this.getVersionString(bootVersion, true));
        return versions;
    }

    protected String getApplicationVersion(Class<?> sourceClass) {
        Package sourcePackage = sourceClass != null ? sourceClass.getPackage() : null;
        return sourcePackage != null ? sourcePackage.getImplementationVersion() : null;
    }

    protected String getBootVersion() {
        return SpringBootVersion.getVersion();
    }

    private String getVersionString(String version, boolean format) {
        if (version == null) {
            return "";
        }
        return format ? " (v" + version + ")" : version;
    }

    private PropertyResolver getAnsiResolver() {
        MutablePropertySources sources = new MutablePropertySources();
        sources.addFirst((PropertySource)new AnsiPropertySource("ansi", true));
        return new PropertySourcesPropertyResolver((PropertySources)sources);
    }

    private PropertyResolver getTitleResolver(Class<?> sourceClass) {
        MutablePropertySources sources = new MutablePropertySources();
        String applicationTitle = this.getApplicationTitle(sourceClass);
        Map<String, String> titleMap = Collections.singletonMap("application.title", applicationTitle != null ? applicationTitle : "");
        sources.addFirst((PropertySource)new MapPropertySource("title", titleMap));
        return new PropertySourcesPropertyResolver((PropertySources)sources);
    }

    protected String getApplicationTitle(Class<?> sourceClass) {
        Package sourcePackage = sourceClass != null ? sourceClass.getPackage() : null;
        return sourcePackage != null ? sourcePackage.getImplementationTitle() : null;
    }
}

