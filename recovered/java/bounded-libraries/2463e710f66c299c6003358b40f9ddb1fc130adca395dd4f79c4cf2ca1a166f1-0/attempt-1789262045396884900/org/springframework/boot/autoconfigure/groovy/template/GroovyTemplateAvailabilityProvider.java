/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.groovy.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.autoconfigure.template.PathBasedTemplateAvailabilityProvider;

public class GroovyTemplateAvailabilityProvider
extends PathBasedTemplateAvailabilityProvider {
    public GroovyTemplateAvailabilityProvider() {
        super("groovy.text.TemplateEngine", GroovyTemplateAvailabilityProperties.class, "spring.groovy.template");
    }

    protected static final class GroovyTemplateAvailabilityProperties
    extends PathBasedTemplateAvailabilityProvider.TemplateAvailabilityProperties {
        private List<String> resourceLoaderPath = new ArrayList<String>(Arrays.asList("classpath:/templates/"));

        GroovyTemplateAvailabilityProperties() {
            super("", ".tpl");
        }

        @Override
        protected List<String> getLoaderPath() {
            return this.resourceLoaderPath;
        }

        public List<String> getResourceLoaderPath() {
            return this.resourceLoaderPath;
        }

        public void setResourceLoaderPath(List<String> resourceLoaderPath) {
            this.resourceLoaderPath = resourceLoaderPath;
        }
    }
}

