/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support.merger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.support.merger.IAttributeMerger;

public abstract class BaseAdditiveAttributeMerger
implements IAttributeMerger {
    @Override
    public Set<String> mergeAvailableQueryAttributes(Set<String> toModify, Set<String> toConsider) {
        toModify.addAll(toConsider);
        return toModify;
    }

    @Override
    public Set<String> mergePossibleUserAttributeNames(Set<String> toModify, Set<String> toConsider) {
        toModify.addAll(toConsider);
        return toModify;
    }

    @Override
    public final Set<IPersonAttributes> mergeResults(Set<IPersonAttributes> toModify, Set<IPersonAttributes> toConsider) {
        Validate.notNull(toModify, (String)"toModify cannot be null", (Object[])new Object[0]);
        Validate.notNull(toConsider, (String)"toConsider cannot be null", (Object[])new Object[0]);
        LinkedHashMap<String, IPersonAttributes> toModfyPeople = new LinkedHashMap<String, IPersonAttributes>();
        for (IPersonAttributes toModifyPerson : toModify) {
            toModfyPeople.put(toModifyPerson.getName(), toModifyPerson);
        }
        for (IPersonAttributes toConsiderPerson : toConsider) {
            String toConsiderName = toConsiderPerson.getName();
            IPersonAttributes toModifyPerson = (IPersonAttributes)toModfyPeople.get(toConsiderName);
            if (toModifyPerson == null) {
                toModify.add(toConsiderPerson);
                continue;
            }
            Map<String, List<Object>> toModifyAttributes = this.buildMutableAttributeMap(toModifyPerson.getAttributes());
            Map<String, List<Object>> mergedAttributes = this.mergePersonAttributes(toModifyAttributes, toConsiderPerson.getAttributes());
            NamedPersonImpl mergedPerson = new NamedPersonImpl(toConsiderName, mergedAttributes);
            toModify.remove(mergedPerson);
            toModify.add(mergedPerson);
        }
        return toModify;
    }

    protected Map<String, List<Object>> buildMutableAttributeMap(Map<String, List<Object>> attributes) {
        Map<String, List<Object>> mutableValuesBuilder = this.createMutableAttributeMap(attributes.size());
        for (Map.Entry<String, List<Object>> attrEntry : attributes.entrySet()) {
            String key = attrEntry.getKey();
            List<Object> value = attrEntry.getValue();
            if (value != null) {
                value = new ArrayList<Object>(value);
            }
            mutableValuesBuilder.put(key, value);
        }
        return mutableValuesBuilder;
    }

    protected Map<String, List<Object>> createMutableAttributeMap(int size) {
        return new LinkedHashMap<String, List<Object>>(size > 0 ? size : 1);
    }

    protected abstract Map<String, List<Object>> mergePersonAttributes(Map<String, List<Object>> var1, Map<String, List<Object>> var2);

    @Override
    public Map<String, List<Object>> mergeAttributes(Map<String, List<Object>> toModify, Map<String, List<Object>> toConsider) {
        return this.mergePersonAttributes(toModify, toConsider);
    }
}

