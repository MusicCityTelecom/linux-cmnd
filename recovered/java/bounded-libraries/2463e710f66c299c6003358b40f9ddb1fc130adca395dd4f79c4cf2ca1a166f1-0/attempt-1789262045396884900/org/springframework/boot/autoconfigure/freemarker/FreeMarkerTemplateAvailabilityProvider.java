/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.freemarker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.autoconfigure.template.PathBasedTemplateAvailabilityProvider;

public class FreeMarkerTemplateAvailabilityProvider
extends PathBasedTemplateAvailabilityProvider {
    public FreeMarkerTemplateAvailabilityProvider() {
        super("freemarker.template.Configuration", FreeMarkerTemplateAvailabilityProperties.class, "spring.freemarker");
    }

    protected static final class FreeMarkerTemplateAvailabilityProperties
    extends PathBasedTemplateAvailabilityProvider.TemplateAvailabilityProperties {
        private List<String> templateLoaderPath = new ArrayList<String>(Arrays.asList("classpath:/templates/"));

        FreeMarkerTemplateAvailabilityProperties() {
            super("", ".ftlh");
        }

        @Override
        protected List<String> getLoaderPath() {
            return this.templateLoaderPath;
        }

        public List<String> getTemplateLoaderPath() {
            return this.templateLoaderPath;
        }

        public void setTemplateLoaderPath(List<String> templateLoaderPath) {
            this.templateLoaderPath = templateLoaderPath;
        }
    }
}

