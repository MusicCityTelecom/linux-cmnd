/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import java.sql.SQLException;
import org.quartz.JobPersistenceException;
import org.quartz.SchedulerConfigException;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.utils.DBConnectionManager;

public class JobStoreCMT
extends JobStoreSupport {
    protected String nonManagedTxDsName;
    protected boolean dontSetNonManagedTXConnectionAutoCommitFalse = false;
    protected boolean setTxIsolationLevelReadCommitted = false;

    public void setNonManagedTXDataSource(String nonManagedTxDsName) {
        this.nonManagedTxDsName = nonManagedTxDsName;
    }

    public String getNonManagedTXDataSource() {
        return this.nonManagedTxDsName;
    }

    public boolean isDontSetNonManagedTXConnectionAutoCommitFalse() {
        return this.dontSetNonManagedTXConnectionAutoCommitFalse;
    }

    public void setDontSetNonManagedTXConnectionAutoCommitFalse(boolean b) {
        this.dontSetNonManagedTXConnectionAutoCommitFalse = b;
    }

    public boolean isTxIsolationLevelReadCommitted() {
        return this.setTxIsolationLevelReadCommitted;
    }

    public void setTxIsolationLevelReadCommitted(boolean b) {
        this.setTxIsolationLevelReadCommitted = b;
    }

    @Override
    public void initialize(ClassLoadHelper loadHelper, SchedulerSignaler signaler) throws SchedulerConfigException {
        if (this.nonManagedTxDsName == null) {
            throw new SchedulerConfigException("Non-ManagedTX DataSource name not set!  If your 'org.quartz.jobStore.dataSource' is XA, then set 'org.quartz.jobStore.nonManagedTXDataSource' to a non-XA datasource (for the same DB).  Otherwise, you can set them to be the same.");
        }
        if (this.getLockHandler() == null) {
            this.setUseDBLocks(true);
        }
        super.initialize(loadHelper, signaler);
        this.getLog().info("JobStoreCMT initialized.");
    }

    @Override
    public void shutdown() {
        super.shutdown();
        try {
            DBConnectionManager.getInstance().shutdown(this.getNonManagedTXDataSource());
        }
        catch (SQLException sqle) {
            this.getLog().warn("Database connection shutdown unsuccessful.", (Throwable)sqle);
        }
    }

    @Override
    protected Connection getNonManagedTXConnection() throws JobPersistenceException {
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection(this.getNonManagedTXDataSource());
        }
        catch (SQLException sqle) {
            throw new JobPersistenceException("Failed to obtain DB connection from data source '" + this.getNonManagedTXDataSource() + "': " + sqle.toString(), sqle);
        }
        catch (Throwable e) {
            throw new JobPersistenceException("Failed to obtain DB connection from data source '" + this.getNonManagedTXDataSource() + "': " + e.toString(), e);
        }
        if (conn == null) {
            throw new JobPersistenceException("Could not get connection from DataSource '" + this.getNonManagedTXDataSource() + "'");
        }
        conn = this.getAttributeRestoringConnection(conn);
        try {
            if (!this.isDontSetNonManagedTXConnectionAutoCommitFalse()) {
                conn.setAutoCommit(false);
            }
            if (this.isTxIsolationLevelReadCommitted()) {
                conn.setTransactionIsolation(2);
            }
        }
        catch (SQLException sqle) {
            this.getLog().warn("Failed to override connection auto commit/transaction isolation.", (Throwable)sqle);
        }
        catch (Throwable e) {
            try {
                conn.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            throw new JobPersistenceException("Failure setting up connection.", e);
        }
        return conn;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object executeInLock(String lockName, JobStoreSupport.TransactionCallback txCallback) throws JobPersistenceException {
        boolean transOwner = false;
        Connection conn = null;
        try {
            if (lockName != null) {
                if (this.getLockHandler().requiresConnection()) {
                    conn = this.getConnection();
                }
                transOwner = this.getLockHandler().obtainLock(conn, lockName);
            }
            if (conn == null) {
                conn = this.getConnection();
            }
            Object t = txCallback.execute(conn);
            return t;
        }
        finally {
            try {
                this.releaseLock(lockName, transOwner);
            }
            finally {
                this.cleanupConnection(conn);
            }
        }
    }
}

