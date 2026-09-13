/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 */
package org.springframework.boot.actuate.web.mappings;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.context.ApplicationContext;

@Endpoint(id="mappings")
public class MappingsEndpoint {
    private final Collection<MappingDescriptionProvider> descriptionProviders;
    private final ApplicationContext context;

    public MappingsEndpoint(Collection<MappingDescriptionProvider> descriptionProviders, ApplicationContext context) {
        this.descriptionProviders = descriptionProviders;
        this.context = context;
    }

    @ReadOperation
    public ApplicationMappings mappings() {
        HashMap<String, ContextMappings> contextMappings = new HashMap<String, ContextMappings>();
        for (ApplicationContext target = this.context; target != null; target = target.getParent()) {
            contextMappings.put(target.getId(), this.mappingsForContext(target));
        }
        return new ApplicationMappings(contextMappings);
    }

    private ContextMappings mappingsForContext(ApplicationContext applicationContext) {
        HashMap mappings = new HashMap();
        this.descriptionProviders.forEach(provider -> mappings.put(provider.getMappingName(), provider.describeMappings(applicationContext)));
        return new ContextMappings(mappings, applicationContext.getParent() != null ? applicationContext.getId() : null);
    }

    public static final class ContextMappings {
        private final Map<String, Object> mappings;
        private final String parentId;

        private ContextMappings(Map<String, Object> mappings, String parentId) {
            this.mappings = mappings;
            this.parentId = parentId;
        }

        public String getParentId() {
            return this.parentId;
        }

        public Map<String, Object> getMappings() {
            return this.mappings;
        }
    }

    public static final class ApplicationMappings {
        private final Map<String, ContextMappings> contextMappings;

        private ApplicationMappings(Map<String, ContextMappings> contextMappings) {
            this.contextMappings = contextMappings;
        }

        public Map<String, ContextMappings> getContexts() {
            return this.contextMappings;
        }
    }
}

