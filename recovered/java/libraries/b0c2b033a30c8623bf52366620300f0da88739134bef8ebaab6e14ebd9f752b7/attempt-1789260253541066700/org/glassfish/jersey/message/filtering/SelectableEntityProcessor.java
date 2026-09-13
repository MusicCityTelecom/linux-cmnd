/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import javax.annotation.Priority;
import javax.inject.Singleton;
import org.glassfish.jersey.message.filtering.SelectableScopeResolver;
import org.glassfish.jersey.message.filtering.spi.AbstractEntityProcessor;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.EntityProcessor;

@Singleton
@Priority(value=2147478647)
public class SelectableEntityProcessor
extends AbstractEntityProcessor {
    @Override
    protected EntityProcessor.Result process(String fieldName, Class<?> fieldClass, Annotation[] fieldAnnotations, Annotation[] annotations, EntityGraph graph) {
        if (fieldName != null) {
            HashSet<String> scopes = new HashSet<String>();
            scopes.add(SelectableScopeResolver.DEFAULT_SCOPE);
            scopes.add(SelectableScopeResolver.PREFIX + fieldName);
            this.addFilteringScopes(fieldName, fieldClass, scopes, graph);
        }
        return EntityProcessor.Result.APPLY;
    }
}

