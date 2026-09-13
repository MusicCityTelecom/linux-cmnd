/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.DBSemaphore;
import org.quartz.impl.jdbcjobstore.LockException;
import org.quartz.impl.jdbcjobstore.Util;

public class UpdateLockRowSemaphore
extends DBSemaphore {
    public static final String UPDATE_FOR_LOCK = "UPDATE {0}LOCKS SET LOCK_NAME = LOCK_NAME WHERE SCHED_NAME = {1} AND LOCK_NAME = ? ";
    public static final String INSERT_LOCK = "INSERT INTO {0}LOCKS(SCHED_NAME, LOCK_NAME) VALUES ({1}, ?)";
    private static final int RETRY_COUNT = 2;

    public UpdateLockRowSemaphore() {
        super("QRTZ_", null, UPDATE_FOR_LOCK, INSERT_LOCK);
    }

    @Override
    protected void executeSQL(Connection conn, String lockName, String expandedSQL, String expandedInsertSQL) throws LockException {
        SQLException lastFailure = null;
        for (int i = 0; i < 2; ++i) {
            try {
                if (!this.lockViaUpdate(conn, lockName, expandedSQL)) {
                    this.lockViaInsert(conn, lockName, expandedInsertSQL);
                }
                return;
            }
            catch (SQLException e) {
                lastFailure = e;
                if (i + 1 == 2) {
                    this.getLog().debug("Lock '{}' was not obtained by: {}", (Object)lockName, (Object)Thread.currentThread().getName());
                } else {
                    this.getLog().debug("Lock '{}' was not obtained by: {} - will try again.", (Object)lockName, (Object)Thread.currentThread().getName());
                }
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException _) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
        }
        throw new LockException("Failure obtaining db row lock: " + lastFailure.getMessage(), lastFailure);
    }

    protected String getUpdateLockRowSQL() {
        return this.getSQL();
    }

    public void setUpdateLockRowSQL(String updateLockRowSQL) {
        this.setSQL(updateLockRowSQL);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean lockViaUpdate(Connection conn, String lockName, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, lockName);
            this.getLog().debug("Lock '" + lockName + "' is being obtained: " + Thread.currentThread().getName());
            boolean bl = ps.executeUpdate() >= 1;
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lockViaInsert(Connection conn, String lockName, String sql) throws SQLException {
        this.getLog().debug("Inserting new lock row for lock: '" + lockName + "' being obtained by thread: " + Thread.currentThread().getName());
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, lockName);
            if (ps.executeUpdate() != 1) {
                throw new SQLException(Util.rtp("No row exists, and one could not be inserted in table {0}LOCKS for lock named: " + lockName, this.getTablePrefix(), this.getSchedulerNameLiteral()));
            }
        }
    }
}

