/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.io.File;
import java.security.AccessControlException;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

public class JspTemplateAvailabilityProvider
implements TemplateAvailabilityProvider {
    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (ClassUtils.isPresent((String)"org.apache.jasper.compiler.JspConfig", (ClassLoader)classLoader)) {
            String resourceName = this.getResourceName(view, environment);
            if (resourceLoader.getResource(resourceName).exists()) {
                return true;
            }
            try {
                return new File("src/main/webapp", resourceName).exists();
            }
            catch (AccessControlException accessControlException) {
                // empty catch block
            }
        }
        return false;
    }

    private String getResourceName(String view, Environment environment) {
        String prefix = environment.getProperty("spring.mvc.view.prefix", "");
        String suffix = environment.getProperty("spring.mvc.view.suffix", "");
        return prefix + view + suffix;
    }
}

