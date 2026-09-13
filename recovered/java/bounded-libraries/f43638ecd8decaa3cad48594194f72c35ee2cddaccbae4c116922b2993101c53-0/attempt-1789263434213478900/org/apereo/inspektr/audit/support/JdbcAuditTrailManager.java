/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Min
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 *  org.apereo.inspektr.audit.AuditActionContext
 *  org.apereo.inspektr.audit.AuditTrailManager
 *  org.apereo.inspektr.audit.AuditTrailManager$WhereClauseFields
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.TransactionCallback
 *  org.springframework.transaction.support.TransactionCallbackWithoutResult
 *  org.springframework.transaction.support.TransactionOperations
 *  org.springframework.util.Assert
 */
package org.apereo.inspektr.audit.support;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.support.NoMatchWhereClauseMatchCriteria;
import org.apereo.inspektr.audit.support.WhereClauseMatchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.Assert;

public class JdbcAuditTrailManager
extends NamedParameterJdbcDaoSupport
implements AuditTrailManager,
DisposableBean {
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE, AUD_USERAGENT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s %s";
    private static final int DEFAULT_COLUMN_LENGTH = 100;
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @NotNull
    private final TransactionOperations transactionTemplate;
    @NotNull
    @Size(min=1)
    private @NotNull @Size(min=1) String tableName = "COM_AUDIT_TRAIL";
    @Min(value=50L)
    private @Min(value=50L) int columnLength = 100;
    private String selectByDateSqlTemplate = "SELECT * FROM %s WHERE %s ORDER BY AUD_DATE DESC";
    private String dateFormatterPattern = "yyyy-MM-dd 00:00:00.000000";
    @NotNull
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean defaultExecutorService = true;
    private boolean asynchronous = true;
    private WhereClauseMatchCriteria cleanupCriteria = new NoMatchWhereClauseMatchCriteria();

    public JdbcAuditTrailManager(TransactionOperations transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    public void record(AuditActionContext auditActionContext) {
        LoggingTask command = new LoggingTask(auditActionContext, this.transactionTemplate, this.columnLength);
        if (this.asynchronous) {
            this.executorService.execute(command);
        } else {
            command.run();
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setCleanupCriteria(WhereClauseMatchCriteria criteria) {
        this.cleanupCriteria = criteria;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        this.defaultExecutorService = false;
    }

    public void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }

    public void destroy() {
        if (this.defaultExecutorService) {
            this.executorService.shutdown();
        }
    }

    public void clean() {
        this.transactionTemplate.execute((TransactionCallback)new TransactionCallbackWithoutResult(){

            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                String sql = String.format(JdbcAuditTrailManager.DELETE_SQL_TEMPLATE, JdbcAuditTrailManager.this.tableName, JdbcAuditTrailManager.this.cleanupCriteria);
                List<?> params = JdbcAuditTrailManager.this.cleanupCriteria.getParameterValues();
                JdbcAuditTrailManager.this.logger.info("Cleaning audit records with query " + sql);
                JdbcAuditTrailManager.this.logger.debug("Query parameters: " + params);
                int count = JdbcAuditTrailManager.this.getJdbcTemplate().update(sql, params.toArray());
                JdbcAuditTrailManager.this.logger.info(count + " records deleted.");
            }
        });
    }

    public void removeAll() {
        this.transactionTemplate.execute((TransactionCallback)new TransactionCallbackWithoutResult(){

            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                String sql = String.format(JdbcAuditTrailManager.DELETE_SQL_TEMPLATE, JdbcAuditTrailManager.this.tableName, "");
                int count = JdbcAuditTrailManager.this.getJdbcTemplate().update(sql);
                JdbcAuditTrailManager.this.logger.info(count + " records deleted.");
            }
        });
    }

    public Set<? extends AuditActionContext> getAuditRecords(Map<AuditTrailManager.WhereClauseFields, Object> whereClause) {
        StringBuilder builder = new StringBuilder("1=1 ");
        if (whereClause.containsKey(AuditTrailManager.WhereClauseFields.DATE)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.dateFormatterPattern);
            LocalDate sinceDate = (LocalDate)whereClause.get(AuditTrailManager.WhereClauseFields.DATE);
            builder.append(String.format("AND AUD_DATE>='%s' ", sinceDate.format(formatter)));
        }
        if (whereClause.containsKey(AuditTrailManager.WhereClauseFields.PRINCIPAL)) {
            String principal = whereClause.get(AuditTrailManager.WhereClauseFields.PRINCIPAL).toString();
            builder.append(String.format("AND AUD_USER='%s' ", principal));
        }
        return this.getAuditRecordsSince(builder);
    }

    public void setDateFormatterPattern(String dateFormatterPattern) {
        this.dateFormatterPattern = dateFormatterPattern;
    }

    public void setSelectByDateSqlTemplate(String selectByDateSqlTemplate) {
        this.selectByDateSqlTemplate = selectByDateSqlTemplate;
    }

    private Set<? extends AuditActionContext> getAuditRecordsSince(StringBuilder where) {
        return (Set)this.transactionTemplate.execute(transactionStatus -> {
            String sql = String.format(this.selectByDateSqlTemplate, this.tableName, where);
            LinkedHashSet results = new LinkedHashSet();
            this.getJdbcTemplate().query(sql, resultSet -> results.add(this.getAuditActionContext(resultSet)));
            return results;
        });
    }

    private AuditActionContext getAuditActionContext(ResultSet resultSet) throws SQLException {
        String principal = resultSet.getString("AUD_USER");
        String resource = resultSet.getString("AUD_RESOURCE");
        String clientIp = resultSet.getString("AUD_CLIENT_IP");
        String serverIp = resultSet.getString("AUD_SERVER_IP");
        Date audDate = resultSet.getDate("AUD_DATE");
        String appCode = resultSet.getString("APPLIC_CD");
        String action = resultSet.getString("AUD_ACTION");
        String userAgent = resultSet.getString("AUD_USERAGENT");
        Assert.notNull((Object)principal, (String)"AUD_USER cannot be null");
        Assert.notNull((Object)resource, (String)"AUD_RESOURCE cannot be null");
        Assert.notNull((Object)clientIp, (String)"AUD_CLIENT_IP cannot be null");
        Assert.notNull((Object)serverIp, (String)"AUD_SERVER_IP cannot be null");
        Assert.notNull((Object)audDate, (String)"AUD_DATE cannot be null");
        Assert.notNull((Object)appCode, (String)"APPLIC_CD cannot be null");
        Assert.notNull((Object)action, (String)"AUD_ACTION cannot be null");
        AuditActionContext audit = new AuditActionContext(principal, resource, action, appCode, (java.util.Date)audDate, clientIp, serverIp, userAgent);
        return audit;
    }

    protected class LoggingTask
    implements Runnable {
        private final AuditActionContext auditActionContext;
        private final TransactionOperations transactionTemplate;
        private final int columnLength;

        public LoggingTask(AuditActionContext auditActionContext, TransactionOperations transactionTemplate, int columnLength) {
            this.auditActionContext = auditActionContext;
            this.transactionTemplate = transactionTemplate;
            this.columnLength = columnLength;
        }

        @Override
        public void run() {
            this.transactionTemplate.execute((TransactionCallback)new TransactionCallbackWithoutResult(){

                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    String userId = LoggingTask.this.auditActionContext.getPrincipal().length() <= LoggingTask.this.columnLength ? LoggingTask.this.auditActionContext.getPrincipal() : LoggingTask.this.auditActionContext.getPrincipal().substring(0, LoggingTask.this.columnLength);
                    String resource = LoggingTask.this.auditActionContext.getResourceOperatedUpon().length() <= LoggingTask.this.columnLength ? LoggingTask.this.auditActionContext.getResourceOperatedUpon() : LoggingTask.this.auditActionContext.getResourceOperatedUpon().substring(0, LoggingTask.this.columnLength);
                    String action = LoggingTask.this.auditActionContext.getActionPerformed().length() <= LoggingTask.this.columnLength ? LoggingTask.this.auditActionContext.getActionPerformed() : LoggingTask.this.auditActionContext.getActionPerformed().substring(0, LoggingTask.this.columnLength);
                    JdbcAuditTrailManager.this.getJdbcTemplate().update(String.format(JdbcAuditTrailManager.INSERT_SQL_TEMPLATE, JdbcAuditTrailManager.this.tableName), new Object[]{userId, LoggingTask.this.auditActionContext.getClientIpAddress(), LoggingTask.this.auditActionContext.getServerIpAddress(), resource, action, LoggingTask.this.auditActionContext.getApplicationCode(), LoggingTask.this.auditActionContext.getWhenActionWasPerformed(), LoggingTask.this.auditActionContext.getUserAgent()});
                }
            });
        }
    }
}

