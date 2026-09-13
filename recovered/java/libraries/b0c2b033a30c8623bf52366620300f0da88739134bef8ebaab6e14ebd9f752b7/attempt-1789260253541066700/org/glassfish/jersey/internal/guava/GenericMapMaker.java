/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import org.glassfish.jersey.internal.guava.MapMaker;

@Deprecated
abstract class GenericMapMaker<K0, V0> {
    GenericMapMaker() {
    }

    <K extends K0, V extends V0> MapMaker.RemovalListener<K, V> getRemovalListener() {
        return NullListener.INSTANCE;
    }

    static enum NullListener implements MapMaker.RemovalListener<Object, Object>
    {
        INSTANCE;


        @Override
        public void onRemoval(MapMaker.RemovalNotification<Object, Object> notification) {
        }
    }
}

