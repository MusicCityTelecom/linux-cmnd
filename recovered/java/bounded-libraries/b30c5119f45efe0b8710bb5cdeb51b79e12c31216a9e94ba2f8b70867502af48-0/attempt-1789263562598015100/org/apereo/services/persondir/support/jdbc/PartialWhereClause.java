/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.services.persondir.support.jdbc;

import java.util.LinkedList;
import java.util.List;

class PartialWhereClause {
    public final StringBuilder sql = new StringBuilder();
    public final List<String> arguments = new LinkedList<String>();

    PartialWhereClause() {
    }

    public String toString() {
        return "sql=[" + this.sql + "] args=" + this.arguments;
    }
}

