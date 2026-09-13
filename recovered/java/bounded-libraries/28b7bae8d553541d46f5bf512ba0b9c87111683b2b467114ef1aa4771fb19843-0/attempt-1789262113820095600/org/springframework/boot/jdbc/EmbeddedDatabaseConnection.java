/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ConnectionCallback
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public enum EmbeddedDatabaseConnection {
    NONE(null, null, null, url -> false),
    H2(EmbeddedDatabaseType.H2, DatabaseDriver.H2.getDriverClassName(), "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", url -> url.contains(":h2:mem")),
    DERBY(EmbeddedDatabaseType.DERBY, DatabaseDriver.DERBY.getDriverClassName(), "jdbc:derby:memory:%s;create=true", url -> true),
    HSQLDB(EmbeddedDatabaseType.HSQL, DatabaseDriver.HSQLDB.getDriverClassName(), "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:%s", url -> url.contains(":hsqldb:mem:"));

    private final EmbeddedDatabaseType type;
    private final String driverClass;
    private final String alternativeDriverClass;
    private final String url;
    private final Predicate<String> embeddedUrl;

    private EmbeddedDatabaseConnection(EmbeddedDatabaseType type, String driverClass, String url, Predicate<String> embeddedUrl) {
        this(type, driverClass, null, url, embeddedUrl);
    }

    private EmbeddedDatabaseConnection(EmbeddedDatabaseType type, String driverClass, String fallbackDriverClass, String url, Predicate<String> embeddedUrl) {
        this.type = type;
        this.driverClass = driverClass;
        this.alternativeDriverClass = fallbackDriverClass;
        this.url = url;
        this.embeddedUrl = embeddedUrl;
    }

    public String getDriverClassName() {
        return this.driverClass;
    }

    public EmbeddedDatabaseType getType() {
        return this.type;
    }

    public String getUrl(String databaseName) {
        Assert.hasText((String)databaseName, (String)"DatabaseName must not be empty");
        return this.url != null ? String.format(this.url, databaseName) : null;
    }

    boolean isEmbeddedUrl(String url) {
        return this.embeddedUrl.test(url);
    }

    boolean isDriverCompatible(String driverClass) {
        return driverClass != null && (driverClass.equals(this.driverClass) || driverClass.equals(this.alternativeDriverClass));
    }

    public static boolean isEmbedded(String driverClass, String url) {
        if (driverClass == null) {
            return false;
        }
        EmbeddedDatabaseConnection connection = EmbeddedDatabaseConnection.getEmbeddedDatabaseConnection(driverClass);
        if (connection == NONE) {
            return false;
        }
        return url == null || connection.isEmbeddedUrl(url);
    }

    private static EmbeddedDatabaseConnection getEmbeddedDatabaseConnection(String driverClass) {
        return Stream.of(H2, HSQLDB, DERBY).filter(connection -> connection.isDriverCompatible(driverClass)).findFirst().orElse(NONE);
    }

    public static boolean isEmbedded(DataSource dataSource) {
        try {
            return (Boolean)new JdbcTemplate(dataSource).execute((ConnectionCallback)new IsEmbedded());
        }
        catch (DataAccessException ex) {
            return false;
        }
    }

    public static EmbeddedDatabaseConnection get(ClassLoader classLoader) {
        for (EmbeddedDatabaseConnection candidate : EmbeddedDatabaseConnection.values()) {
            if (candidate == NONE || !ClassUtils.isPresent((String)candidate.getDriverClassName(), (ClassLoader)classLoader)) continue;
            return candidate;
        }
        return NONE;
    }

    private static class IsEmbedded
    implements ConnectionCallback<Boolean> {
        private IsEmbedded() {
        }

        public Boolean doInConnection(Connection connection) throws SQLException, DataAccessException {
            EmbeddedDatabaseConnection[] candidates;
            DatabaseMetaData metaData = connection.getMetaData();
            String productName = metaData.getDatabaseProductName();
            if (productName == null) {
                return false;
            }
            productName = productName.toUpperCase(Locale.ENGLISH);
            for (EmbeddedDatabaseConnection candidate : candidates = EmbeddedDatabaseConnection.values()) {
                if (candidate == NONE || !productName.contains(candidate.getType().name())) continue;
                String url = metaData.getURL();
                return url == null || candidate.isEmbeddedUrl(url);
            }
            return false;
        }
    }
}

