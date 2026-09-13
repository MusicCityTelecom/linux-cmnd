/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.support;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.apereo.inspektr.audit.support.AbstractWhereClauseMatchCriteria;

public class MaxAgeWhereClauseMatchCriteria
extends AbstractWhereClauseMatchCriteria {
    private static final String DATE_COLUMN = "AUD_DATE";
    protected int maxAge;

    public MaxAgeWhereClauseMatchCriteria(int maxAgeDays) {
        this.maxAge = maxAgeDays;
        this.addCriteria(DATE_COLUMN, "<");
    }

    @Override
    public List<?> getParameterValues() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -this.maxAge);
        return Collections.singletonList(cal.getTime());
    }
}

