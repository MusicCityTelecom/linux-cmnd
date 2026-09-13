/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.support.JdbcUtils
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jdbc;

import java.sql.DatabaseMetaData;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import javax.sql.DataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public enum DatabaseDriver {
    UNKNOWN(null, null),
    DERBY("Apache Derby", "org.apache.derby.jdbc.EmbeddedDriver", "org.apache.derby.jdbc.EmbeddedXADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1"),
    H2("H2", "org.h2.Driver", "org.h2.jdbcx.JdbcDataSource", "SELECT 1"),
    HSQLDB("HSQL Database Engine", "org.hsqldb.jdbc.JDBCDriver", "org.hsqldb.jdbc.pool.JDBCXADataSource", "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SYSTEM_USERS"),
    SQLITE("SQLite", "org.sqlite.JDBC"),
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlXADataSource", "/* ping */ SELECT 1"),
    MARIADB("MariaDB", "org.mariadb.jdbc.Driver", "org.mariadb.jdbc.MariaDbDataSource", "SELECT 1"),
    GAE(null, "com.google.appengine.api.rdbms.AppEngineDriver"),
    ORACLE("Oracle", "oracle.jdbc.OracleDriver", "oracle.jdbc.xa.client.OracleXADataSource", "SELECT 'Hello' from DUAL"),
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver", "org.postgresql.xa.PGXADataSource", "SELECT 1"),
    REDSHIFT("Redshift", "com.amazon.redshift.jdbc.Driver", null, "SELECT 1"),
    HANA("HDB", "com.sap.db.jdbc.Driver", "com.sap.db.jdbcext.XADataSourceSAP", "SELECT 1 FROM SYS.DUMMY"){

        @Override
        protected Collection<String> getUrlPrefixes() {
            return Collections.singleton("sap");
        }
    }
    ,
    JTDS(null, "net.sourceforge.jtds.jdbc.Driver"),
    SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "com.microsoft.sqlserver.jdbc.SQLServerXADataSource", "SELECT 1"){

        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || "SQL SERVER".equalsIgnoreCase(productName);
        }
    }
    ,
    FIREBIRD("Firebird", "org.firebirdsql.jdbc.FBDriver", "org.firebirdsql.ds.FBXADataSource", "SELECT 1 FROM RDB$DATABASE"){

        @Override
        protected Collection<String> getUrlPrefixes() {
            return Arrays.asList("firebirdsql", "firebird");
        }

        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || productName.toLowerCase(Locale.ENGLISH).startsWith("firebird");
        }
    }
    ,
    DB2("DB2", "com.ibm.db2.jcc.DB2Driver", "com.ibm.db2.jcc.DB2XADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1"){

        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || productName.toLowerCase(Locale.ENGLISH).startsWith("db2/");
        }
    }
    ,
    DB2_AS400("DB2 UDB for AS/400", "com.ibm.as400.access.AS400JDBCDriver", "com.ibm.as400.access.AS400JDBCXADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1"){

        @Override
        public String getId() {
            return "db2";
        }

        @Override
        protected Collection<String> getUrlPrefixes() {
            return Collections.singleton("as400");
        }

        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || productName.toLowerCase(Locale.ENGLISH).contains("as/400");
        }
    }
    ,
    TERADATA("Teradata", "com.teradata.jdbc.TeraDriver"),
    INFORMIX("Informix Dynamic Server", "com.informix.jdbc.IfxDriver", null, "select count(*) from systables"){

        @Override
        protected Collection<String> getUrlPrefixes() {
            return Arrays.asList("informix-sqli", "informix-direct");
        }
    }
    ,
    PHOENIX("Apache Phoenix", "org.apache.phoenix.jdbc.PhoenixDriver", null, "SELECT 1 FROM SYSTEM.CATALOG LIMIT 1"),
    TESTCONTAINERS(null, "org.testcontainers.jdbc.ContainerDatabaseDriver"){

        @Override
        protected Collection<String> getUrlPrefixes() {
            return Collections.singleton("tc");
        }
    };

    private final String productName;
    private final String driverClassName;
    private final String xaDataSourceClassName;
    private final String validationQuery;

    private DatabaseDriver(String productName, String driverClassName) {
        this(productName, driverClassName, (String)null);
    }

    private DatabaseDriver(String productName, String driverClassName, String xaDataSourceClassName) {
        this(productName, driverClassName, xaDataSourceClassName, null);
    }

    private DatabaseDriver(String productName, String driverClassName, String xaDataSourceClassName, String validationQuery) {
        this.productName = productName;
        this.driverClassName = driverClassName;
        this.xaDataSourceClassName = xaDataSourceClassName;
        this.validationQuery = validationQuery;
    }

    public String getId() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    protected boolean matchProductName(String productName) {
        return this.productName != null && this.productName.equalsIgnoreCase(productName);
    }

    protected Collection<String> getUrlPrefixes() {
        return Collections.singleton(this.name().toLowerCase(Locale.ENGLISH));
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public String getXaDataSourceClassName() {
        return this.xaDataSourceClassName;
    }

    public String getValidationQuery() {
        return this.validationQuery;
    }

    public static DatabaseDriver fromJdbcUrl(String url) {
        if (StringUtils.hasLength((String)url)) {
            Assert.isTrue((boolean)url.startsWith("jdbc"), (String)"URL must start with 'jdbc'");
            String urlWithoutPrefix = url.substring("jdbc".length()).toLowerCase(Locale.ENGLISH);
            for (DatabaseDriver driver : DatabaseDriver.values()) {
                for (String urlPrefix : driver.getUrlPrefixes()) {
                    String prefix = ":" + urlPrefix + ":";
                    if (driver == UNKNOWN || !urlWithoutPrefix.startsWith(prefix)) continue;
                    return driver;
                }
            }
        }
        return UNKNOWN;
    }

    public static DatabaseDriver fromProductName(String productName) {
        if (StringUtils.hasLength((String)productName)) {
            for (DatabaseDriver candidate : DatabaseDriver.values()) {
                if (!candidate.matchProductName(productName)) continue;
                return candidate;
            }
        }
        return UNKNOWN;
    }

    public static DatabaseDriver fromDataSource(DataSource dataSource) {
        try {
            String productName = JdbcUtils.commonDatabaseName((String)((String)JdbcUtils.extractDatabaseMetaData((DataSource)dataSource, DatabaseMetaData::getDatabaseProductName)));
            return DatabaseDriver.fromProductName(productName);
        }
        catch (Exception ex) {
            return UNKNOWN;
        }
    }
}

