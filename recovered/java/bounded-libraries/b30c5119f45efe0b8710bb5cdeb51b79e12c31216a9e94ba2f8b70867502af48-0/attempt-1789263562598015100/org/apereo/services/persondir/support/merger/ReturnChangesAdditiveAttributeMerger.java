/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.support.merger;

import java.util.List;
import java.util.Map;
import org.apereo.services.persondir.support.merger.BaseAdditiveAttributeMerger;

public class ReturnChangesAdditiveAttributeMerger
extends BaseAdditiveAttributeMerger {
    @Override
    protected Map<String, List<Object>> mergePersonAttributes(Map<String, List<Object>> toModify, Map<String, List<Object>> toConsider) {
        return toConsider;
    }
}

