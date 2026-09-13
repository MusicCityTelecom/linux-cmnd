/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;

public interface IUsernameAttributeProvider {
    public String getUsernameAttribute();

    public String getUsernameFromQuery(Map<String, List<Object>> var1);
}

