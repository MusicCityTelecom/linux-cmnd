/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.ContributorRegistry;
import org.springframework.boot.actuate.health.HealthContributorNameFactory;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.util.Assert;

class DefaultContributorRegistry<C>
implements ContributorRegistry<C> {
    private final Function<String, String> nameFactory;
    private final Object monitor = new Object();
    private volatile Map<String, C> contributors;

    DefaultContributorRegistry() {
        this(Collections.emptyMap());
    }

    DefaultContributorRegistry(Map<String, C> contributors) {
        this(contributors, HealthContributorNameFactory.INSTANCE);
    }

    DefaultContributorRegistry(Map<String, C> contributors, Function<String, String> nameFactory) {
        Assert.notNull(contributors, (String)"Contributors must not be null");
        Assert.notNull(nameFactory, (String)"NameFactory must not be null");
        this.nameFactory = nameFactory;
        LinkedHashMap namedContributors = new LinkedHashMap();
        contributors.forEach((? super K name, ? super V contributor) -> namedContributors.put(nameFactory.apply((String)name), contributor));
        this.contributors = Collections.unmodifiableMap(namedContributors);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void registerContributor(String name, C contributor) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        Assert.notNull(contributor, (String)"Contributor must not be null");
        String adaptedName = this.nameFactory.apply(name);
        Object object = this.monitor;
        synchronized (object) {
            Assert.state((!this.contributors.containsKey(adaptedName) ? 1 : 0) != 0, () -> "A contributor named \"" + adaptedName + "\" has already been registered");
            LinkedHashMap<String, C> contributors = new LinkedHashMap<String, C>(this.contributors);
            contributors.put(adaptedName, contributor);
            this.contributors = Collections.unmodifiableMap(contributors);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public C unregisterContributor(String name) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        String adaptedName = this.nameFactory.apply(name);
        Object object = this.monitor;
        synchronized (object) {
            C unregistered = this.contributors.get(adaptedName);
            if (unregistered != null) {
                LinkedHashMap<String, C> contributors = new LinkedHashMap<String, C>(this.contributors);
                contributors.remove(adaptedName);
                this.contributors = Collections.unmodifiableMap(contributors);
            }
            return unregistered;
        }
    }

    @Override
    public C getContributor(String name) {
        return this.contributors.get(name);
    }

    @Override
    public Iterator<NamedContributor<C>> iterator() {
        final Iterator<Map.Entry<String, C>> iterator = this.contributors.entrySet().iterator();
        return new Iterator<NamedContributor<C>>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public NamedContributor<C> next() {
                Map.Entry entry = (Map.Entry)iterator.next();
                return NamedContributor.of((String)entry.getKey(), entry.getValue());
            }
        };
    }
}

