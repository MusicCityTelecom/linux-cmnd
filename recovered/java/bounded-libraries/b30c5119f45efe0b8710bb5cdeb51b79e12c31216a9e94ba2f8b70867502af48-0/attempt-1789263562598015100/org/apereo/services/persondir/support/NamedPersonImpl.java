/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import org.apereo.services.persondir.support.BasePersonImpl;

public class NamedPersonImpl
extends BasePersonImpl {
    private static final long serialVersionUID = 1L;
    private final String userName;

    public NamedPersonImpl(String userName, Map<String, List<Object>> attributes) {
        super(attributes);
        this.userName = userName;
    }

    public String getName() {
        return this.userName;
    }
}

