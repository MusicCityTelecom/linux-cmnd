/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import org.glassfish.jersey.internal.guava.Cache;
import org.glassfish.jersey.internal.guava.CacheBuilder;
import org.glassfish.jersey.message.filtering.spi.EntityGraphProvider;
import org.glassfish.jersey.message.filtering.spi.EntityInspector;
import org.glassfish.jersey.message.filtering.spi.FilteringHelper;
import org.glassfish.jersey.message.filtering.spi.ObjectGraphTransformer;
import org.glassfish.jersey.message.filtering.spi.ObjectProvider;
import org.glassfish.jersey.message.filtering.spi.ScopeProvider;

public abstract class AbstractObjectProvider<T>
implements ObjectProvider<T>,
ObjectGraphTransformer<T> {
    private static final int PROVIDER_CACHE_SIZE = 1000;
    private final Cache<EntityContext, T> filteringObjects = CacheBuilder.newBuilder().maximumSize(1000L).build();
    @Inject
    private ScopeProvider scopeProvider;
    @Inject
    private EntityInspector entityInspector;
    @Inject
    private EntityGraphProvider graphProvider;

    @Override
    public final T getFilteringObject(Type genericType, boolean forWriter, Annotation ... annotations) {
        return this.getFilteringObject(FilteringHelper.getEntityClass(genericType), forWriter, annotations);
    }

    private T getFilteringObject(Class<?> entityClass, boolean forWriter, Annotation ... annotations) {
        if (FilteringHelper.filterableEntityClass(entityClass)) {
            this.entityInspector.inspect(entityClass, forWriter);
            Set<String> filteringScope = this.scopeProvider.getFilteringScopes(this.getEntityAnnotations(annotations), true);
            EntityContext entityContext = new EntityContext(entityClass, filteringScope);
            T filteringObject = this.filteringObjects.getIfPresent(entityContext);
            if (filteringObject == null) {
                filteringObject = this.createFilteringObject(entityClass, filteringScope, forWriter);
                this.filteringObjects.put(entityContext, filteringObject);
            }
            return filteringObject;
        }
        return null;
    }

    private Annotation[] getEntityAnnotations(Annotation[] annotations) {
        ArrayList<Annotation> entityAnnotations = new ArrayList<Annotation>();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Proxy) continue;
            entityAnnotations.add(annotation);
        }
        return entityAnnotations.toArray(new Annotation[entityAnnotations.size()]);
    }

    private T createFilteringObject(Class<?> entityClass, Set<String> filteringScopes, boolean forWriter) {
        return this.transform(this.graphProvider.createObjectGraph(entityClass, filteringScopes, forWriter));
    }

    protected Set<String> immutableSetOf(Set<String> set, String item) {
        HashSet<String> duplicate = new HashSet<String>(set);
        duplicate.add(item);
        return Collections.unmodifiableSet(duplicate);
    }

    protected String subgraphIdentifier(Class<?> parent, String field, Class<?> fieldClass) {
        return parent.getName() + "_" + field + "_" + fieldClass.getName();
    }

    private static final class EntityContext {
        private final Class<?> entityClass;
        private final Set<String> filteringContext;

        private EntityContext(Class<?> entityClass, Set<String> filteringScopes) {
            this.entityClass = entityClass;
            this.filteringContext = filteringScopes;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof EntityContext)) {
                return false;
            }
            EntityContext that = (EntityContext)o;
            return this.entityClass.equals(that.entityClass) && this.filteringContext.equals(that.filteringContext);
        }

        public int hashCode() {
            int result = this.entityClass.hashCode();
            result = 47 * result + this.filteringContext.hashCode();
            return result;
        }
    }
}

