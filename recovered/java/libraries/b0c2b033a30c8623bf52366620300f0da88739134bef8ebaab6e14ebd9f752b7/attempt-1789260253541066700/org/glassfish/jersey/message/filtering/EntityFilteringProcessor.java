/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import javax.annotation.Priority;
import javax.inject.Singleton;
import org.glassfish.jersey.message.filtering.EntityFilteringHelper;
import org.glassfish.jersey.message.filtering.spi.AbstractEntityProcessor;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.EntityProcessor;
import org.glassfish.jersey.message.filtering.spi.EntityProcessorContext;

@Singleton
@Priority(value=2147481647)
final class EntityFilteringProcessor
extends AbstractEntityProcessor {
    EntityFilteringProcessor() {
    }

    @Override
    public EntityProcessor.Result process(EntityProcessorContext context) {
        switch (context.getType()) {
            case CLASS_READER: 
            case CLASS_WRITER: {
                this.addGlobalScopes(EntityFilteringHelper.getFilteringScopes(context.getEntityClass().getDeclaredAnnotations()), context.getEntityGraph());
                break;
            }
        }
        return super.process(context);
    }

    @Override
    protected EntityProcessor.Result process(String field, Class<?> fieldClass, Annotation[] fieldAnnotations, Annotation[] annotations, EntityGraph graph) {
        HashSet<String> filteringScopes = new HashSet<String>();
        if (fieldAnnotations.length > 0) {
            filteringScopes.addAll(EntityFilteringHelper.getFilteringScopes(fieldAnnotations));
        }
        if (annotations.length > 0) {
            filteringScopes.addAll(EntityFilteringHelper.getFilteringScopes(annotations));
        }
        if (!filteringScopes.isEmpty()) {
            if (field != null) {
                this.addFilteringScopes(field, fieldClass, filteringScopes, graph);
            } else {
                this.addGlobalScopes(filteringScopes, graph);
            }
            return EntityProcessor.Result.APPLY;
        }
        return EntityProcessor.Result.SKIP;
    }
}

