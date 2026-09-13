/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.impl.jdbcjobstore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeRestoringConnectionInvocationHandler
implements InvocationHandler {
    private Connection conn;
    private boolean overwroteOriginalAutoCommitValue;
    private boolean overwroteOriginalTxIsolationValue;
    private boolean originalAutoCommitValue;
    private int originalTxIsolationValue;

    public AttributeRestoringConnectionInvocationHandler(Connection conn) {
        this.conn = conn;
    }

    protected Logger getLog() {
        return LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("setAutoCommit")) {
            this.setAutoCommit((Boolean)args[0]);
        } else if (method.getName().equals("setTransactionIsolation")) {
            this.setTransactionIsolation((Integer)args[0]);
        } else if (method.getName().equals("close")) {
            this.close();
        } else {
            try {
                return method.invoke(this.conn, args);
            }
            catch (InvocationTargetException ite) {
                throw ite.getCause() != null ? ite.getCause() : ite;
            }
        }
        return null;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        boolean currentAutoCommitValue = this.conn.getAutoCommit();
        if (autoCommit != currentAutoCommitValue) {
            if (!this.overwroteOriginalAutoCommitValue) {
                this.overwroteOriginalAutoCommitValue = true;
                this.originalAutoCommitValue = currentAutoCommitValue;
            }
            this.conn.setAutoCommit(autoCommit);
        }
    }

    public void setTransactionIsolation(int level) throws SQLException {
        int currentLevel = this.conn.getTransactionIsolation();
        if (level != currentLevel) {
            if (!this.overwroteOriginalTxIsolationValue) {
                this.overwroteOriginalTxIsolationValue = true;
                this.originalTxIsolationValue = currentLevel;
            }
            this.conn.setTransactionIsolation(level);
        }
    }

    public Connection getWrappedConnection() {
        return this.conn;
    }

    public void restoreOriginalAtributes() {
        try {
            if (this.overwroteOriginalAutoCommitValue) {
                this.conn.setAutoCommit(this.originalAutoCommitValue);
            }
        }
        catch (Throwable t) {
            this.getLog().warn("Failed restore connection's original auto commit setting.", t);
        }
        try {
            if (this.overwroteOriginalTxIsolationValue) {
                this.conn.setTransactionIsolation(this.originalTxIsolationValue);
            }
        }
        catch (Throwable t) {
            this.getLog().warn("Failed restore connection's original transaction isolation setting.", t);
        }
    }

    public void close() throws SQLException {
        this.restoreOriginalAtributes();
        this.conn.close();
    }
}

