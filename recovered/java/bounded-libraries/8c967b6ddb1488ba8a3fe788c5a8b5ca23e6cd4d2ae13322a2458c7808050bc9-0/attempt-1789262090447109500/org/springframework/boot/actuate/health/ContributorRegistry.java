/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.NamedContributors;

public interface ContributorRegistry<C>
extends NamedContributors<C> {
    public void registerContributor(String var1, C var2);

    public C unregisterContributor(String var1);
}

