/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support.merger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributes;

public interface IAttributeMerger {
    public Set<IPersonAttributes> mergeResults(Set<IPersonAttributes> var1, Set<IPersonAttributes> var2);

    public Set<String> mergePossibleUserAttributeNames(Set<String> var1, Set<String> var2);

    public Set<String> mergeAvailableQueryAttributes(Set<String> var1, Set<String> var2);

    public Map<String, List<Object>> mergeAttributes(Map<String, List<Object>> var1, Map<String, List<Object>> var2);
}

