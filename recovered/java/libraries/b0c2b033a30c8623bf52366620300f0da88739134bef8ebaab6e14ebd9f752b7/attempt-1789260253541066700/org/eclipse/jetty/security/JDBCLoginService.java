/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Credential;

public class JDBCLoginService
extends AbstractLoginService {
    private static final Logger LOG = Log.getLogger(JDBCLoginService.class);
    protected String _config;
    protected String _jdbcDriver;
    protected String _url;
    protected String _userName;
    protected String _password;
    protected String _userTableKey;
    protected String _userTablePasswordField;
    protected String _roleTableRoleField;
    protected Connection _con;
    protected String _userSql;
    protected String _roleSql;

    public JDBCLoginService() throws IOException {
    }

    public JDBCLoginService(String name) throws IOException {
        this.setName(name);
    }

    public JDBCLoginService(String name, String config) throws IOException {
        this.setName(name);
        this.setConfig(config);
    }

    public JDBCLoginService(String name, IdentityService identityService, String config) throws IOException {
        this.setName(name);
        this.setIdentityService(identityService);
        this.setConfig(config);
    }

    @Override
    protected void doStart() throws Exception {
        Properties properties = new Properties();
        Resource resource = Resource.newResource(this._config);
        try (InputStream in = resource.getInputStream();){
            properties.load(in);
        }
        this._jdbcDriver = properties.getProperty("jdbcdriver");
        this._url = properties.getProperty("url");
        this._userName = properties.getProperty("username");
        this._password = properties.getProperty("password");
        String userTable = properties.getProperty("usertable");
        this._userTableKey = properties.getProperty("usertablekey");
        String userTableUserField = properties.getProperty("usertableuserfield");
        this._userTablePasswordField = properties.getProperty("usertablepasswordfield");
        String roleTable = properties.getProperty("roletable");
        String roleTableKey = properties.getProperty("roletablekey");
        this._roleTableRoleField = properties.getProperty("roletablerolefield");
        String userRoleTable = properties.getProperty("userroletable");
        String userRoleTableUserKey = properties.getProperty("userroletableuserkey");
        String userRoleTableRoleKey = properties.getProperty("userroletablerolekey");
        if (this._jdbcDriver == null || this._jdbcDriver.equals("") || this._url == null || this._url.equals("") || this._userName == null || this._userName.equals("") || this._password == null) {
            LOG.warn("UserRealm " + this.getName() + " has not been properly configured", new Object[0]);
        }
        this._userSql = "select " + this._userTableKey + "," + this._userTablePasswordField + " from " + userTable + " where " + userTableUserField + " = ?";
        this._roleSql = "select r." + this._roleTableRoleField + " from " + roleTable + " r, " + userRoleTable + " u where u." + userRoleTableUserKey + " = ? and r." + roleTableKey + " = u." + userRoleTableRoleKey;
        Loader.loadClass(this._jdbcDriver).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        super.doStart();
    }

    public String getConfig() {
        return this._config;
    }

    public void setConfig(String config) {
        if (this.isRunning()) {
            throw new IllegalStateException("Running");
        }
        this._config = config;
    }

    public void connectDatabase() {
        try {
            Class.forName(this._jdbcDriver);
            this._con = DriverManager.getConnection(this._url, this._userName, this._password);
        }
        catch (SQLException e) {
            LOG.warn("UserRealm " + this.getName() + " could not connect to database; will try later", e);
        }
        catch (ClassNotFoundException e) {
            LOG.warn("UserRealm " + this.getName() + " could not connect to database; will try later", e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AbstractLoginService.UserPrincipal loadUserInfo(String username) {
        try {
            if (null == this._con) {
                this.connectDatabase();
            }
            if (null == this._con) {
                throw new SQLException("Can't connect to database");
            }
            try (PreparedStatement stat1 = this._con.prepareStatement(this._userSql);){
                stat1.setObject(1, username);
                try (ResultSet rs1 = stat1.executeQuery();){
                    if (!rs1.next()) return null;
                    int key = rs1.getInt(this._userTableKey);
                    String credentials = rs1.getString(this._userTablePasswordField);
                    JDBCUserPrincipal jDBCUserPrincipal = new JDBCUserPrincipal(username, Credential.getCredential(credentials), key);
                    return jDBCUserPrincipal;
                }
            }
        }
        catch (SQLException e) {
            LOG.warn("UserRealm " + this.getName() + " could not load user information from database", e);
            this.closeConnection();
        }
        return null;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    public String[] loadRoleInfo(AbstractLoginService.UserPrincipal user) {
        JDBCUserPrincipal jdbcUser = (JDBCUserPrincipal)user;
        try {
            if (null == this._con) {
                this.connectDatabase();
            }
            if (null == this._con) {
                throw new SQLException("Can't connect to database");
            }
            ArrayList<String> roles = new ArrayList<String>();
            try (PreparedStatement stat2 = this._con.prepareStatement(this._roleSql);){
                String[] stringArray;
                block17: {
                    stat2.setInt(1, jdbcUser.getUserKey());
                    ResultSet rs2 = stat2.executeQuery();
                    try {
                        while (rs2.next()) {
                            roles.add(rs2.getString(this._roleTableRoleField));
                        }
                        stringArray = roles.toArray(new String[roles.size()]);
                        if (rs2 == null) break block17;
                    }
                    catch (Throwable throwable) {
                        if (rs2 != null) {
                            try {
                                rs2.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    rs2.close();
                }
                return stringArray;
            }
        }
        catch (SQLException e) {
            LOG.warn("UserRealm " + this.getName() + " could not load user information from database", e);
            this.closeConnection();
            return null;
        }
    }

    @Override
    protected void doStop() throws Exception {
        this.closeConnection();
        super.doStop();
    }

    private void closeConnection() {
        if (this._con != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Closing db connection for JDBCUserRealm", new Object[0]);
            }
            try {
                this._con.close();
            }
            catch (Exception e) {
                LOG.ignore(e);
            }
        }
        this._con = null;
    }

    public class JDBCUserPrincipal
    extends AbstractLoginService.UserPrincipal {
        int _userKey;

        public JDBCUserPrincipal(String name, Credential credential, int key) {
            super(name, credential);
            this._userKey = key;
        }

        public int getUserKey() {
            return this._userKey;
        }
    }
}

