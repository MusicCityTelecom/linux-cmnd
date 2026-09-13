/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc;

import javax.sql.DataSource;
import javax.sql.XADataSource;

@FunctionalInterface
public interface XADataSourceWrapper {
    public DataSource wrapDataSource(XADataSource var1) throws Exception;
}

