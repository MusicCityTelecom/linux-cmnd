/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support.rule;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributes;

public interface AttributeRule {
    public boolean appliesTo(Map<String, List<Object>> var1);

    public Set<IPersonAttributes> evaluate(Map<String, List<Object>> var1);

    public Set<String> getPossibleUserAttributeNames();

    public Set<String> getAvailableQueryAttributes();
}

