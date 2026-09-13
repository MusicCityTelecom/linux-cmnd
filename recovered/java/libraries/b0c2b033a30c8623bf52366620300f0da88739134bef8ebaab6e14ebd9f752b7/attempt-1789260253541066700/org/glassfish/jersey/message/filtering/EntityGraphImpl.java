/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.glassfish.jersey.internal.guava.HashBasedTable;
import org.glassfish.jersey.internal.guava.HashMultimap;
import org.glassfish.jersey.internal.guava.Table;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.ScopeProvider;

final class EntityGraphImpl
implements EntityGraph {
    private final Class<?> entityClass;
    private final Set<String> globalScopes;
    private final Set<String> localScopes;
    private final HashMultimap<String, String> fields;
    private final Table<String, String, Class<?>> subgraphs;

    public EntityGraphImpl(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.fields = HashMultimap.create();
        this.subgraphs = HashBasedTable.create();
        this.globalScopes = new HashSet<String>();
        this.localScopes = new HashSet<String>();
    }

    @Override
    public EntityGraphImpl addField(String fieldName) {
        return this.addField(fieldName, (Set)this.globalScopes);
    }

    @Override
    public EntityGraphImpl addField(String fieldName, String ... filteringScopes) {
        return this.addField(fieldName, Arrays.stream(filteringScopes).collect(Collectors.toSet()));
    }

    @Override
    public EntityGraphImpl addField(String fieldName, Set<String> filteringScopes) {
        for (String filteringScope : filteringScopes) {
            this.createFilteringScope(filteringScope);
            this.fields.get((Object)filteringScope).add(fieldName);
        }
        return this;
    }

    @Override
    public EntityGraphImpl addFilteringScopes(Set<String> filteringScopes) {
        this.globalScopes.addAll(filteringScopes);
        return this;
    }

    @Override
    public EntityGraphImpl addSubgraph(String fieldName, Class<?> fieldClass) {
        return this.addSubgraph(fieldName, (Class)fieldClass, (Set)this.globalScopes);
    }

    @Override
    public EntityGraphImpl addSubgraph(String fieldName, Class<?> fieldClass, String ... filteringScopes) {
        return this.addSubgraph(fieldName, (Class)fieldClass, Arrays.stream(filteringScopes).collect(Collectors.toSet()));
    }

    @Override
    public EntityGraphImpl addSubgraph(String fieldName, Class<?> fieldClass, Set<String> filteringScopes) {
        for (String filteringScope : filteringScopes) {
            this.createFilteringScope(filteringScope);
            this.subgraphs.put(filteringScope, fieldName, fieldClass);
        }
        return this;
    }

    @Override
    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public Set<String> getFields(String filteringScope) {
        return this.fields.containsKey(filteringScope) ? Collections.unmodifiableSet(this.fields.get((Object)filteringScope)) : Collections.emptySet();
    }

    @Override
    public Set<String> getFields(String ... filteringScopes) {
        return filteringScopes.length == 0 ? Collections.emptySet() : (filteringScopes.length == 1 ? this.getFields(filteringScopes[0]) : this.getFields(Arrays.stream(filteringScopes).collect(Collectors.toSet())));
    }

    @Override
    public Set<String> getFields(Set<String> filteringScopes) {
        HashSet<String> matched = new HashSet<String>();
        for (String filteringContext : filteringScopes) {
            matched.addAll(this.fields.get((Object)filteringContext));
        }
        return matched;
    }

    @Override
    public Set<String> getFilteringScopes() {
        HashSet<String> strings = new HashSet<String>(this.globalScopes);
        strings.addAll(this.localScopes);
        return Collections.unmodifiableSet(strings);
    }

    @Override
    public Set<String> getClassFilteringScopes() {
        return Collections.unmodifiableSet(this.globalScopes);
    }

    @Override
    public Map<String, Class<?>> getSubgraphs(String filteringScope) {
        return this.subgraphs.containsRow(filteringScope) ? Collections.unmodifiableMap(this.subgraphs.row(filteringScope)) : Collections.emptyMap();
    }

    @Override
    public Map<String, Class<?>> getSubgraphs(String ... filteringScopes) {
        return filteringScopes.length == 0 ? Collections.emptyMap() : (filteringScopes.length == 1 ? this.getSubgraphs(filteringScopes[0]) : this.getSubgraphs(Arrays.stream(filteringScopes).collect(Collectors.toSet())));
    }

    @Override
    public Map<String, Class<?>> getSubgraphs(Set<String> filteringScopes) {
        HashMap matched = new HashMap();
        for (String filteringContext : filteringScopes) {
            matched.putAll(this.subgraphs.row(filteringContext));
        }
        return matched;
    }

    @Override
    public boolean presentInScopes(String name) {
        return this.fields.containsValue(name) || this.subgraphs.containsColumn(name);
    }

    @Override
    public boolean presentInScope(String field, String filteringScope) {
        return this.fields.containsEntry(filteringScope, field) || this.subgraphs.contains(filteringScope, field);
    }

    @Override
    public EntityGraphImpl remove(String fieldName) {
        for (String scope : this.getFilteringScopes()) {
            if (this.fields.containsEntry(scope, fieldName)) {
                this.fields.remove(scope, fieldName);
            }
            if (!this.subgraphs.containsColumn(fieldName)) continue;
            this.subgraphs.remove(scope, fieldName);
        }
        return this;
    }

    private void createFilteringScope(String filteringScope) {
        if (!this.getFilteringScopes().contains(filteringScope)) {
            if (this.localScopes.contains(ScopeProvider.DEFAULT_SCOPE)) {
                this.fields.putAll((Object)filteringScope, (Iterable)this.fields.get((Object)ScopeProvider.DEFAULT_SCOPE));
                Map<String, Class<?>> row = this.subgraphs.row(ScopeProvider.DEFAULT_SCOPE);
                for (Map.Entry<String, Class<?>> entry : row.entrySet()) {
                    this.subgraphs.put(filteringScope, entry.getKey(), entry.getValue());
                }
            }
            this.localScopes.add(filteringScope);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EntityGraphImpl that = (EntityGraphImpl)o;
        return this.entityClass.equals(that.entityClass) && this.fields.equals(that.fields) && this.globalScopes.equals(that.globalScopes) && this.localScopes.equals(that.localScopes) && this.subgraphs.equals(that.subgraphs);
    }

    public int hashCode() {
        int result = this.entityClass.hashCode();
        result = 53 * result + this.globalScopes.hashCode();
        result = 53 * result + this.localScopes.hashCode();
        result = 53 * result + this.fields.hashCode();
        result = 53 * result + this.subgraphs.hashCode();
        return result;
    }
}

