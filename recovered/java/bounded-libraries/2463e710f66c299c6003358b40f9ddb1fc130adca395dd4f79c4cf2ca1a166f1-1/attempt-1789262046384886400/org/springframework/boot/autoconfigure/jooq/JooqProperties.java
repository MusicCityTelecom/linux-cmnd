/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.SQLDialect
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.jooq;

import javax.sql.DataSource;
import org.jooq.SQLDialect;
import org.springframework.boot.autoconfigure.jooq.SqlDialectLookup;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.jooq")
public class JooqProperties {
    private SQLDialect sqlDialect;

    public SQLDialect getSqlDialect() {
        return this.sqlDialect;
    }

    public void setSqlDialect(SQLDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    public SQLDialect determineSqlDialect(DataSource dataSource) {
        if (this.sqlDialect != null) {
            return this.sqlDialect;
        }
        return SqlDialectLookup.getDialect(dataSource);
    }
}

