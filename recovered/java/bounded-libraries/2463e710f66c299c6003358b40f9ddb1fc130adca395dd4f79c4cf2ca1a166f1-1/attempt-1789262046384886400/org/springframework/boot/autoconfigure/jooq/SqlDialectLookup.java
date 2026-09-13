/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.jooq.SQLDialect
 *  org.jooq.tools.jdbc.JDBCUtils
 *  org.springframework.jdbc.support.JdbcUtils
 *  org.springframework.jdbc.support.MetaDataAccessException
 */
package org.springframework.boot.autoconfigure.jooq;

import java.sql.DatabaseMetaData;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.SQLDialect;
import org.jooq.tools.jdbc.JDBCUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

final class SqlDialectLookup {
    private static final Log logger = LogFactory.getLog(SqlDialectLookup.class);

    private SqlDialectLookup() {
    }

    static SQLDialect getDialect(DataSource dataSource) {
        if (dataSource == null) {
            return SQLDialect.DEFAULT;
        }
        try {
            String url = (String)JdbcUtils.extractDatabaseMetaData((DataSource)dataSource, DatabaseMetaData::getURL);
            SQLDialect sqlDialect = JDBCUtils.dialect((String)url);
            if (sqlDialect != null) {
                return sqlDialect;
            }
        }
        catch (MetaDataAccessException ex) {
            logger.warn((Object)"Unable to determine jdbc url from datasource", (Throwable)ex);
        }
        return SQLDialect.DEFAULT;
    }
}

