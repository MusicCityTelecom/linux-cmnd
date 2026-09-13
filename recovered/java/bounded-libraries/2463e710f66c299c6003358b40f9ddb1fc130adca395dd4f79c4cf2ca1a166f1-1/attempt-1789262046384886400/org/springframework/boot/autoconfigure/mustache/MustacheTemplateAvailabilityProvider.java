/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.mustache;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

public class MustacheTemplateAvailabilityProvider
implements TemplateAvailabilityProvider {
    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (ClassUtils.isPresent((String)"com.samskivert.mustache.Template", (ClassLoader)classLoader)) {
            String prefix = environment.getProperty("spring.mustache.prefix", "classpath:/templates/");
            String suffix = environment.getProperty("spring.mustache.suffix", ".mustache");
            return resourceLoader.getResource(prefix + view + suffix).exists();
        }
        return false;
    }
}

