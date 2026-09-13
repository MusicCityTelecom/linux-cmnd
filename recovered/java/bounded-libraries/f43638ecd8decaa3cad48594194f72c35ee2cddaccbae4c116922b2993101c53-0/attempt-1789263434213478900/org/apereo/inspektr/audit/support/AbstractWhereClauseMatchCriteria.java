/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.support;

import org.apereo.inspektr.audit.support.WhereClauseMatchCriteria;

public abstract class AbstractWhereClauseMatchCriteria
implements WhereClauseMatchCriteria {
    protected StringBuilder sbClause = new StringBuilder();

    @Override
    public String toString() {
        return this.sbClause.toString();
    }

    protected void addCriteria(String column) {
        this.addCriteria(column, "=");
    }

    protected void addCriteria(String column, String operator) {
        if (this.sbClause.length() == 0) {
            this.sbClause.append("WHERE");
        } else {
            this.sbClause.append(" AND");
        }
        this.sbClause.append(' ');
        this.sbClause.append(column);
        this.sbClause.append(' ');
        this.sbClause.append(operator);
        this.sbClause.append(" ?");
    }
}

