/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.thymeleaf;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

public class ThymeleafTemplateAvailabilityProvider
implements TemplateAvailabilityProvider {
    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (ClassUtils.isPresent((String)"org.thymeleaf.spring5.SpringTemplateEngine", (ClassLoader)classLoader)) {
            String prefix = environment.getProperty("spring.thymeleaf.prefix", "classpath:/templates/");
            String suffix = environment.getProperty("spring.thymeleaf.suffix", ".html");
            return resourceLoader.getResource(prefix + view + suffix).exists();
        }
        return false;
    }
}

