/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.jooq.ExecuteContext
 *  org.jooq.SQLDialect
 *  org.jooq.impl.DefaultExecuteListener
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
 *  org.springframework.jdbc.support.SQLExceptionTranslator
 *  org.springframework.jdbc.support.SQLStateSQLExceptionTranslator
 */
package org.springframework.boot.autoconfigure.jooq;

import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class JooqExceptionTranslator
extends DefaultExecuteListener {
    private static final Log logger = LogFactory.getLog(JooqExceptionTranslator.class);

    public void exception(ExecuteContext context) {
        SQLExceptionTranslator translator = this.getTranslator(context);
        for (SQLException exception = context.sqlException(); exception != null; exception = exception.getNextException()) {
            this.handle(context, translator, exception);
        }
    }

    private SQLExceptionTranslator getTranslator(ExecuteContext context) {
        String dbName;
        SQLDialect dialect = context.configuration().dialect();
        if (dialect != null && dialect.thirdParty() != null && (dbName = dialect.thirdParty().springDbName()) != null) {
            return new SQLErrorCodeSQLExceptionTranslator(dbName);
        }
        return new SQLStateSQLExceptionTranslator();
    }

    private void handle(ExecuteContext context, SQLExceptionTranslator translator, SQLException exception) {
        DataAccessException translated = this.translate(context, translator, exception);
        if (exception.getNextException() == null) {
            if (translated != null) {
                context.exception((RuntimeException)translated);
            }
        } else {
            logger.error((Object)"Execution of SQL statement failed.", (Throwable)(translated != null ? translated : exception));
        }
    }

    private DataAccessException translate(ExecuteContext context, SQLExceptionTranslator translator, SQLException exception) {
        return translator.translate("jOOQ", context.sql(), exception);
    }
}

