/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import org.glassfish.jersey.message.filtering.spi.AbstractObjectProvider;
import org.glassfish.jersey.message.filtering.spi.ObjectGraph;

final class ObjectGraphProvider
extends AbstractObjectProvider<ObjectGraph> {
    ObjectGraphProvider() {
    }

    @Override
    public ObjectGraph transform(ObjectGraph graph) {
        return graph;
    }
}

