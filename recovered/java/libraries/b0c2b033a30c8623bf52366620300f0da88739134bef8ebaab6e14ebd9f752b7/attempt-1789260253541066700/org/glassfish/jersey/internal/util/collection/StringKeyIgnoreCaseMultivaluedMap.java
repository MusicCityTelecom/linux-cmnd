/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import javax.ws.rs.core.AbstractMultivaluedMap;
import org.glassfish.jersey.internal.util.collection.KeyComparatorLinkedHashMap;
import org.glassfish.jersey.internal.util.collection.StringIgnoreCaseKeyComparator;

public class StringKeyIgnoreCaseMultivaluedMap<V>
extends AbstractMultivaluedMap<String, V> {
    public StringKeyIgnoreCaseMultivaluedMap() {
        super(new KeyComparatorLinkedHashMap(StringIgnoreCaseKeyComparator.SINGLETON));
    }
}

