/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.glassfish.jersey.message.filtering.spi.AbstractObjectProvider;
import org.glassfish.jersey.message.filtering.spi.ObjectGraph;

final class JacksonObjectProvider
extends AbstractObjectProvider<FilterProvider> {
    JacksonObjectProvider() {
    }

    @Override
    public FilterProvider transform(ObjectGraph graph) {
        FilteringPropertyFilter root = new FilteringPropertyFilter(graph.getEntityClass(), graph.getFields(), this.createSubfilters(graph.getEntityClass(), graph.getSubgraphs()));
        return new FilteringFilterProvider(root);
    }

    private Map<String, FilteringPropertyFilter> createSubfilters(Class<?> entityClass, Map<String, ObjectGraph> entitySubgraphs) {
        HashMap<String, FilteringPropertyFilter> subfilters = new HashMap<String, FilteringPropertyFilter>();
        for (Map.Entry<String, ObjectGraph> entry : entitySubgraphs.entrySet()) {
            String fieldName = entry.getKey();
            ObjectGraph graph = entry.getValue();
            Map<String, ObjectGraph> subgraphs = graph.getSubgraphs(fieldName);
            Map<Object, Object> subSubfilters = new HashMap();
            if (!subgraphs.isEmpty()) {
                Class<?> subEntityClass = graph.getEntityClass();
                Set<String> processed = Collections.singleton(this.subgraphIdentifier(entityClass, fieldName, subEntityClass));
                subSubfilters = this.createSubfilters(fieldName, subEntityClass, subgraphs, processed);
            }
            FilteringPropertyFilter filter = new FilteringPropertyFilter(graph.getEntityClass(), graph.getFields(fieldName), subSubfilters);
            subfilters.put(fieldName, filter);
        }
        return subfilters;
    }

    private Map<String, FilteringPropertyFilter> createSubfilters(String parent, Class<?> entityClass, Map<String, ObjectGraph> entitySubgraphs, Set<String> processed) {
        HashMap<String, FilteringPropertyFilter> subfilters = new HashMap<String, FilteringPropertyFilter>();
        for (Map.Entry<String, ObjectGraph> entry : entitySubgraphs.entrySet()) {
            String fieldName = entry.getKey();
            ObjectGraph graph = entry.getValue();
            String path = parent + "." + fieldName;
            Map<String, ObjectGraph> subgraphs = graph.getSubgraphs(path);
            Class<?> subEntityClass = graph.getEntityClass();
            String processedSubgraph = this.subgraphIdentifier(entityClass, fieldName, subEntityClass);
            Map<Object, Object> subSubfilters = new HashMap();
            if (!subgraphs.isEmpty() && !processed.contains(processedSubgraph)) {
                Set<String> subProcessed = this.immutableSetOf(processed, processedSubgraph);
                subSubfilters = this.createSubfilters(path, subEntityClass, subgraphs, subProcessed);
            }
            subfilters.put(fieldName, new FilteringPropertyFilter(graph.getEntityClass(), graph.getFields(path), subSubfilters));
        }
        return subfilters;
    }

    private static final class FilteringPropertyFilter
    implements PropertyFilter {
        private final Class<?> entityClass;
        private final Set<String> fields;
        private final Map<String, FilteringPropertyFilter> subfilters;

        private FilteringPropertyFilter(Class<?> entityClass, Set<String> fields, Map<String, FilteringPropertyFilter> subfilters) {
            this.entityClass = entityClass;
            this.fields = fields;
            this.subfilters = subfilters;
        }

        private boolean include(String fieldName) {
            return this.fields.contains(fieldName) || this.subfilters.containsKey(fieldName);
        }

        @Override
        public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer) throws Exception {
            if (this.include(writer.getName())) {
                writer.serializeAsField(pojo, jgen, prov);
            }
        }

        @Override
        public void serializeAsElement(Object elementValue, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer) throws Exception {
            if (this.include(writer.getName())) {
                writer.serializeAsElement(elementValue, jgen, prov);
            }
        }

        @Override
        public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
            if (this.include(writer.getName())) {
                writer.depositSchemaProperty(propertiesNode, provider);
            }
        }

        @Override
        public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
            if (this.include(writer.getName())) {
                writer.depositSchemaProperty(objectVisitor, provider);
            }
        }

        public FilteringPropertyFilter findSubfilter(String fieldName) {
            return this.subfilters.get(fieldName);
        }

        public Class<?> getEntityClass() {
            return this.entityClass;
        }
    }

    private static class FilteringFilterProvider
    extends FilterProvider {
        private final FilteringPropertyFilter root;
        private final Stack<FilteringPropertyFilter> stack = new Stack();

        public FilteringFilterProvider(FilteringPropertyFilter root) {
            this.root = root;
        }

        @Override
        public BeanPropertyFilter findFilter(Object filterId) {
            throw new UnsupportedOperationException("Access to deprecated filters not supported");
        }

        @Override
        public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
            if (filterId instanceof String) {
                String id = (String)filterId;
                if (id.equals(this.root.getEntityClass().getName())) {
                    this.stack.clear();
                    return this.stack.push(this.root);
                }
                while (!this.stack.isEmpty()) {
                    FilteringPropertyFilter peek = this.stack.peek();
                    FilteringPropertyFilter subfilter = peek.findSubfilter(id);
                    if (subfilter != null) {
                        this.stack.push(subfilter);
                        if (valueToFilter instanceof Map) {
                            Map map = (Map)valueToFilter;
                            return new FilteringPropertyFilter(Map.class, map.keySet(), Collections.emptyMap());
                        }
                        return subfilter;
                    }
                    this.stack.pop();
                }
            }
            return SimpleBeanPropertyFilter.filterOutAllExcept(new String[0]);
        }
    }
}

