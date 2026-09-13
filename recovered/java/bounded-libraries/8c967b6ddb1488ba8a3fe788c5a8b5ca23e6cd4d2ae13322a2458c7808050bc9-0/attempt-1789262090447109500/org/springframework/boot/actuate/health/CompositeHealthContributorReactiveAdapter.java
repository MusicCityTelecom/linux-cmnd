/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import java.util.Iterator;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.util.Assert;

class CompositeHealthContributorReactiveAdapter
implements CompositeReactiveHealthContributor {
    private final CompositeHealthContributor delegate;

    CompositeHealthContributorReactiveAdapter(CompositeHealthContributor delegate) {
        Assert.notNull((Object)delegate, (String)"Delegate must not be null");
        this.delegate = delegate;
    }

    @Override
    public Iterator<NamedContributor<ReactiveHealthContributor>> iterator() {
        final Iterator iterator = this.delegate.iterator();
        return new Iterator<NamedContributor<ReactiveHealthContributor>>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public NamedContributor<ReactiveHealthContributor> next() {
                NamedContributor namedContributor = (NamedContributor)iterator.next();
                return NamedContributor.of(namedContributor.getName(), ReactiveHealthContributor.adapt((HealthContributor)namedContributor.getContributor()));
            }
        };
    }

    @Override
    public ReactiveHealthContributor getContributor(String name) {
        HealthContributor contributor = (HealthContributor)this.delegate.getContributor(name);
        return contributor != null ? ReactiveHealthContributor.adapt(contributor) : null;
    }
}

