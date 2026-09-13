/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import org.quartz.utils.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JNDIConnectionProvider
implements ConnectionProvider {
    private String url;
    private Properties props;
    private Object datasource;
    private boolean alwaysLookup = false;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JNDIConnectionProvider(String jndiUrl, boolean alwaysLookup) {
        this.url = jndiUrl;
        this.alwaysLookup = alwaysLookup;
        this.init();
    }

    public JNDIConnectionProvider(String jndiUrl, Properties jndiProps, boolean alwaysLookup) {
        this.url = jndiUrl;
        this.props = jndiProps;
        this.alwaysLookup = alwaysLookup;
        this.init();
    }

    protected Logger getLog() {
        return this.log;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        if (!this.isAlwaysLookup()) {
            InitialContext ctx = null;
            try {
                ctx = this.props != null ? new InitialContext(this.props) : new InitialContext();
                this.datasource = (DataSource)ctx.lookup(this.url);
            }
            catch (Exception e) {
                this.getLog().error("Error looking up datasource: " + e.getMessage(), (Throwable)e);
            }
            finally {
                if (ctx != null) {
                    try {
                        ctx.close();
                    }
                    catch (Exception exception) {}
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Connection getConnection() throws SQLException {
        Context ctx = null;
        try {
            Object ds = this.datasource;
            if (ds == null || this.isAlwaysLookup()) {
                ctx = this.props != null ? new InitialContext(this.props) : new InitialContext();
                ds = ctx.lookup(this.url);
                if (!this.isAlwaysLookup()) {
                    this.datasource = ds;
                }
            }
            if (ds == null) {
                throw new SQLException("There is no object at the JNDI URL '" + this.url + "'");
            }
            if (ds instanceof XADataSource) {
                Connection connection = ((XADataSource)ds).getXAConnection().getConnection();
                return connection;
            }
            if (ds instanceof DataSource) {
                Connection connection = ((DataSource)ds).getConnection();
                return connection;
            }
            throw new SQLException("Object at JNDI URL '" + this.url + "' is not a DataSource.");
        }
        catch (Exception e) {
            this.datasource = null;
            throw new SQLException("Could not retrieve datasource via JNDI url '" + this.url + "' " + e.getClass().getName() + ": " + e.getMessage());
        }
        finally {
            if (ctx != null) {
                try {
                    ctx.close();
                }
                catch (Exception exception) {}
            }
        }
    }

    public boolean isAlwaysLookup() {
        return this.alwaysLookup;
    }

    public void setAlwaysLookup(boolean b) {
        this.alwaysLookup = b;
    }

    @Override
    public void shutdown() throws SQLException {
    }

    @Override
    public void initialize() throws SQLException {
    }
}

