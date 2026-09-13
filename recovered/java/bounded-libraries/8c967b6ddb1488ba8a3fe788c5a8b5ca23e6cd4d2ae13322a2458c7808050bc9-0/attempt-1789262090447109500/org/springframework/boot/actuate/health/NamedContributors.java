/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.boot.actuate.health.NamedContributor;

public interface NamedContributors<C>
extends Iterable<NamedContributor<C>> {
    public C getContributor(String var1);

    default public Stream<NamedContributor<C>> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}

