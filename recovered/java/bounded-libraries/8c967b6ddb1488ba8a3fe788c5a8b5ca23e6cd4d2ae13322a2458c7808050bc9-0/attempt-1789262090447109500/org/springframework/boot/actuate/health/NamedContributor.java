/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import org.springframework.util.Assert;

public interface NamedContributor<C> {
    public String getName();

    public C getContributor();

    public static <C> NamedContributor<C> of(final String name, final C contributor) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        Assert.notNull(contributor, (String)"Contributor must not be null");
        return new NamedContributor<C>(){

            @Override
            public String getName() {
                return name;
            }

            @Override
            public C getContributor() {
                return contributor;
            }
        };
    }
}

