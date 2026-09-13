/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.id.insert;

import org.hibernate.dialect.Dialect;
import org.hibernate.id.insert.IdentifierGeneratingInsert;

public class InsertSelectIdentityInsert
extends IdentifierGeneratingInsert {
    public InsertSelectIdentityInsert(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String toStatementString() {
        return this.getDialect().getIdentityColumnSupport().appendIdentitySelectToInsert(super.toStatementString());
    }
}

