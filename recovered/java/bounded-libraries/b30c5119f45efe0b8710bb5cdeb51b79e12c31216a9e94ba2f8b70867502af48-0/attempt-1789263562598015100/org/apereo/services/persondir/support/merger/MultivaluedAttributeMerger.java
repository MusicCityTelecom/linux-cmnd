/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package org.apereo.services.persondir.support.merger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.support.merger.BaseAdditiveAttributeMerger;

public class MultivaluedAttributeMerger
extends BaseAdditiveAttributeMerger {
    private boolean distinctValues;

    public void setDistinctValues(boolean distinctValues) {
        this.distinctValues = distinctValues;
    }

    @Override
    protected Map<String, List<Object>> mergePersonAttributes(Map<String, List<Object>> toModify, Map<String, List<Object>> toConsider) {
        Validate.notNull(toModify, (String)"toModify cannot be null", (Object[])new Object[0]);
        Validate.notNull(toConsider, (String)"toConsider cannot be null", (Object[])new Object[0]);
        for (Map.Entry<String, List<Object>> sourceEntry : toConsider.entrySet()) {
            String sourceKey = sourceEntry.getKey();
            List values = toModify.computeIfAbsent(sourceKey, k -> new LinkedList());
            List<Object> sourceValue = sourceEntry.getValue();
            if (this.distinctValues) {
                TreeSet<Object> temp = new TreeSet<Object>((o1, o2) -> {
                    if (o1 instanceof String && o2 instanceof String && o1.toString().equalsIgnoreCase(o2.toString())) {
                        return 0;
                    }
                    if (o1 instanceof Comparable && o2 instanceof Comparable && o1.getClass().isAssignableFrom(o2.getClass())) {
                        return ((Comparable)o1).compareTo(o2);
                    }
                    return -1;
                });
                temp.addAll(values);
                temp.addAll(sourceValue);
                toModify.put(sourceKey, new ArrayList(temp));
                continue;
            }
            values.addAll(sourceValue);
        }
        return toModify;
    }
}

