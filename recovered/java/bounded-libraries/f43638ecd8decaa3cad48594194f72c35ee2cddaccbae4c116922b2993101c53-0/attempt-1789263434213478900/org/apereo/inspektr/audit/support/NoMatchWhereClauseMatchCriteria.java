/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.support;

import java.util.Collections;
import java.util.List;
import org.apereo.inspektr.audit.support.AbstractWhereClauseMatchCriteria;

public class NoMatchWhereClauseMatchCriteria
extends AbstractWhereClauseMatchCriteria {
    public NoMatchWhereClauseMatchCriteria() {
        this.sbClause.append("WHERE 0=1");
    }

    @Override
    public List<?> getParameterValues() {
        return Collections.emptyList();
    }
}

