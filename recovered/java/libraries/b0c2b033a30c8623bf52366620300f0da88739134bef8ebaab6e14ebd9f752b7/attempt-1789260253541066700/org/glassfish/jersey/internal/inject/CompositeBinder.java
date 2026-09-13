/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;

public class CompositeBinder
extends AbstractBinder {
    private Collection<Binder> installed = new ArrayList<Binder>();

    private CompositeBinder(Collection<Binder> installed) {
        this.installed = installed;
    }

    public static AbstractBinder wrap(Collection<Binder> binders) {
        return new CompositeBinder(binders);
    }

    public static AbstractBinder wrap(Binder ... binders) {
        return new CompositeBinder(Arrays.asList(binders));
    }

    @Override
    public void configure() {
        this.install(this.installed.toArray(new AbstractBinder[0]));
    }
}

