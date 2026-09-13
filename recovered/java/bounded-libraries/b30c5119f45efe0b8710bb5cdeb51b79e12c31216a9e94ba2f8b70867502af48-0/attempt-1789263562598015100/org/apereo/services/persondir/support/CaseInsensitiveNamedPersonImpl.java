/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apereo.services.persondir.support.NamedPersonImpl;

public class CaseInsensitiveNamedPersonImpl
extends NamedPersonImpl {
    private static final long serialVersionUID = 1L;

    public CaseInsensitiveNamedPersonImpl(String userName, Map<String, List<Object>> attributes) {
        super(userName, attributes);
    }

    @Override
    protected Map<String, List<Object>> createImmutableAttributeMap(int size) {
        return new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
    }
}

