/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.annotation.Priority;
import javax.inject.Singleton;
import org.glassfish.jersey.message.filtering.SecurityHelper;
import org.glassfish.jersey.message.filtering.spi.AbstractEntityProcessor;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.EntityProcessor;

@Singleton
@Priority(value=0x7FFFF447)
final class SecurityEntityProcessor
extends AbstractEntityProcessor {
    SecurityEntityProcessor() {
    }

    @Override
    protected EntityProcessor.Result process(String fieldName, Class<?> fieldClass, Annotation[] fieldAnnotations, Annotation[] annotations, EntityGraph graph) {
        if (annotations.length > 0) {
            Set<String> filteringScopes = SecurityHelper.getFilteringScopes(annotations);
            if (filteringScopes == null) {
                return EntityProcessor.Result.ROLLBACK;
            }
            if (!filteringScopes.isEmpty()) {
                if (fieldName != null) {
                    this.addFilteringScopes(fieldName, fieldClass, filteringScopes, graph);
                } else {
                    this.addGlobalScopes(filteringScopes, graph);
                }
            }
        }
        return EntityProcessor.Result.APPLY;
    }
}

