/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apereo.cas.monitor.Monitorable
 */
package org.apereo.cas.util.spring.beans;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.monitor.Monitorable;

@Monitorable
public interface BeanContainer<T> {
    public static <T> BeanContainer<T> of(T ... entries) {
        return new ListBeanContainer(Arrays.stream(entries).collect(Collectors.toList()));
    }

    public static <T> BeanContainer<T> of(List<T> entries) {
        return new ListBeanContainer<T>(new ArrayList<T>(entries));
    }

    public static <T> BeanContainer<T> of(Set<T> entries) {
        return new ListBeanContainer<T>(new ArrayList<T>(entries));
    }

    public static <T> BeanContainer<T> empty() {
        return BeanContainer.of(new Object[0]);
    }

    public List<T> toList();

    public Set<T> toSet();

    public BeanContainer<T> and(T var1);

    public int size();

    public T first();

    default public boolean isEmpty() {
        return this.size() == 0;
    }

    public static class ListBeanContainer<T>
    implements BeanContainer<T> {
        private final List<T> items;

        @Override
        public T first() {
            return this.items.get(0);
        }

        @Override
        public List<T> toList() {
            return this.items;
        }

        @Override
        @CanIgnoreReturnValue
        public BeanContainer<T> and(T entry) {
            this.items.add(entry);
            return this;
        }

        @Override
        public int size() {
            return this.items.size();
        }

        @Override
        public Set<T> toSet() {
            return new LinkedHashSet<T>(this.items);
        }

        @Generated
        public ListBeanContainer(List<T> items) {
            this.items = items;
        }
    }
}

