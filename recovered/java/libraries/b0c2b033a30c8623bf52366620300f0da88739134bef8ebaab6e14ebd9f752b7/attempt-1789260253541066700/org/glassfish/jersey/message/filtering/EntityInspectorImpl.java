/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.filtering.EntityGraphImpl;
import org.glassfish.jersey.message.filtering.EntityProcessorContextImpl;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.EntityGraphProvider;
import org.glassfish.jersey.message.filtering.spi.EntityInspector;
import org.glassfish.jersey.message.filtering.spi.EntityProcessor;
import org.glassfish.jersey.message.filtering.spi.EntityProcessorContext;
import org.glassfish.jersey.message.filtering.spi.FilteringHelper;
import org.glassfish.jersey.model.internal.RankedComparator;

@Singleton
final class EntityInspectorImpl
implements EntityInspector {
    private final List<EntityProcessor> entityProcessors;
    @Inject
    private EntityGraphProvider graphProvider;

    @Inject
    public EntityInspectorImpl(InjectionManager injectionManager) {
        Spliterator<EntityProcessor> entities = Providers.getAllProviders(injectionManager, EntityProcessor.class, new RankedComparator()).spliterator();
        this.entityProcessors = StreamSupport.stream(entities, false).collect(Collectors.toList());
    }

    @Override
    public void inspect(Class<?> entityClass, boolean forWriter) {
        if (!this.graphProvider.containsEntityGraph(entityClass, forWriter)) {
            EntityGraphImpl graph = new EntityGraphImpl(entityClass);
            HashSet inspect = new HashSet();
            if (!this.inspectEntityClass(entityClass, graph, forWriter)) {
                Map<String, Method> unmatchedAccessors = this.inspectEntityProperties(entityClass, graph, inspect, forWriter);
                this.inspectStandaloneAccessors(unmatchedAccessors, graph, forWriter);
                this.graphProvider.putIfAbsent(entityClass, graph, forWriter);
                for (Class clazz : inspect) {
                    this.inspect(clazz, forWriter);
                }
            } else {
                this.graphProvider.putIfAbsent(entityClass, graph, forWriter);
            }
        }
    }

    private boolean inspectEntityClass(Class<?> entityClass, EntityGraph graph, boolean forWriter) {
        EntityProcessorContextImpl context = new EntityProcessorContextImpl(forWriter ? EntityProcessorContext.Type.CLASS_WRITER : EntityProcessorContext.Type.CLASS_READER, entityClass, graph);
        for (EntityProcessor processor : this.entityProcessors) {
            EntityProcessor.Result result = processor.process(context);
            if (EntityProcessor.Result.ROLLBACK != result) continue;
            this.graphProvider.getOrCreateEmptyEntityGraph(entityClass, false);
            return true;
        }
        return false;
    }

    private Map<String, Method> inspectEntityProperties(Class<?> entityClass, EntityGraph graph, Set<Class<?>> inspect, boolean forWriter) {
        Field[] fields = AccessController.doPrivileged(ReflectionHelper.getAllFieldsPA(entityClass));
        Map<String, Method> methods = FilteringHelper.getPropertyMethods(entityClass, forWriter);
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            String name = field.getName();
            Class<?> clazz = FilteringHelper.getEntityClass(field.getGenericType());
            Method method = methods.remove(name);
            EntityProcessorContextImpl context = new EntityProcessorContextImpl(forWriter ? EntityProcessorContext.Type.PROPERTY_WRITER : EntityProcessorContext.Type.PROPERTY_READER, field, method, graph);
            boolean rollback = false;
            for (EntityProcessor processor : this.entityProcessors) {
                EntityProcessor.Result result = processor.process(context);
                if (EntityProcessor.Result.ROLLBACK != result) continue;
                rollback = true;
                graph.remove(name);
                break;
            }
            if (rollback || !FilteringHelper.filterableEntityClass(clazz)) continue;
            inspect.add(clazz);
        }
        return methods;
    }

    private void inspectStandaloneAccessors(Map<String, Method> unprocessedAccessors, EntityGraph graph, boolean forWriter) {
        block0: for (Map.Entry<String, Method> entry : unprocessedAccessors.entrySet()) {
            EntityProcessorContextImpl context = new EntityProcessorContextImpl(forWriter ? EntityProcessorContext.Type.METHOD_WRITER : EntityProcessorContext.Type.METHOD_READER, entry.getValue(), graph);
            for (EntityProcessor processor : this.entityProcessors) {
                EntityProcessor.Result result = processor.process(context);
                if (EntityProcessor.Result.ROLLBACK != result) continue;
                graph.remove(entry.getKey());
                continue block0;
            }
        }
    }
}

