/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package org.apereo.services.persondir.support.merger;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.support.merger.BaseAdditiveAttributeMerger;

public class ReplacingAttributeAdder
extends BaseAdditiveAttributeMerger {
    @Override
    protected Map<String, List<Object>> mergePersonAttributes(Map<String, List<Object>> toModify, Map<String, List<Object>> toConsider) {
        Validate.notNull(toModify, (String)"toModify cannot be null", (Object[])new Object[0]);
        Validate.notNull(toConsider, (String)"toConsider cannot be null", (Object[])new Object[0]);
        toModify.putAll(toConsider);
        return toModify;
    }
}

