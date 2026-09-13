/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.DBSemaphore;
import org.quartz.impl.jdbcjobstore.LockException;
import org.quartz.impl.jdbcjobstore.Util;

public class StdRowLockSemaphore
extends DBSemaphore {
    public static final String SELECT_FOR_LOCK = "SELECT * FROM {0}LOCKS WHERE SCHED_NAME = {1} AND LOCK_NAME = ? FOR UPDATE";
    public static final String INSERT_LOCK = "INSERT INTO {0}LOCKS(SCHED_NAME, LOCK_NAME) VALUES ({1}, ?)";
    private int maxRetry = 3;
    private long retryPeriod = 1000L;

    public StdRowLockSemaphore() {
        super("QRTZ_", null, SELECT_FOR_LOCK, INSERT_LOCK);
    }

    public StdRowLockSemaphore(String tablePrefix, String schedName, String selectWithLockSQL) {
        super(tablePrefix, schedName, selectWithLockSQL != null ? selectWithLockSQL : SELECT_FOR_LOCK, INSERT_LOCK);
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public void setRetryPeriod(long retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    public int getMaxRetry() {
        return this.maxRetry;
    }

    public long getRetryPeriod() {
        return this.retryPeriod;
    }

    @Override
    protected void executeSQL(Connection conn, String lockName, String expandedSQL, String expandedInsertSQL) throws LockException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        SQLException initCause = null;
        int count = 0;
        int maxRetryLocal = this.maxRetry;
        long retryPeriodLocal = this.retryPeriod;
        do {
            ++count;
            try {
                ps = conn.prepareStatement(expandedSQL);
                ps.setString(1, lockName);
                if (this.getLog().isDebugEnabled()) {
                    this.getLog().debug("Lock '" + lockName + "' is being obtained: " + Thread.currentThread().getName());
                }
                if (!(rs = ps.executeQuery()).next()) {
                    this.getLog().debug("Inserting new lock row for lock: '" + lockName + "' being obtained by thread: " + Thread.currentThread().getName());
                    rs.close();
                    rs = null;
                    ps.close();
                    ps = null;
                    ps = conn.prepareStatement(expandedInsertSQL);
                    ps.setString(1, lockName);
                    int res = ps.executeUpdate();
                    if (res != 1) {
                        if (count < maxRetryLocal) {
                            try {
                                Thread.sleep(retryPeriodLocal);
                            }
                            catch (InterruptedException ignore) {
                                Thread.currentThread().interrupt();
                            }
                            continue;
                        }
                        throw new SQLException(Util.rtp("No row exists, and one could not be inserted in table {0}LOCKS for lock named: " + lockName, this.getTablePrefix(), this.getSchedulerNameLiteral()));
                    }
                }
                return;
            }
            catch (SQLException sqle) {
                if (initCause == null) {
                    initCause = sqle;
                }
                if (this.getLog().isDebugEnabled()) {
                    this.getLog().debug("Lock '" + lockName + "' was not obtained by: " + Thread.currentThread().getName() + (count < maxRetryLocal ? " - will try again." : ""));
                }
                if (count < maxRetryLocal) {
                    try {
                        Thread.sleep(retryPeriodLocal);
                    }
                    catch (InterruptedException ignore) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
                throw new LockException("Failure obtaining db row lock: " + sqle.getMessage(), sqle);
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    }
                    catch (Exception exception) {}
                }
                if (ps != null) {
                    try {
                        ps.close();
                    }
                    catch (Exception exception) {}
                }
            }
        } while (count < maxRetryLocal + 1);
        throw new LockException("Failure obtaining db row lock, reached maximum number of attempts. Initial exception (if any) attached as root cause.", initCause);
    }

    protected String getSelectWithLockSQL() {
        return this.getSQL();
    }

    public void setSelectWithLockSQL(String selectWithLockSQL) {
        this.setSQL(selectWithLockSQL);
    }
}

