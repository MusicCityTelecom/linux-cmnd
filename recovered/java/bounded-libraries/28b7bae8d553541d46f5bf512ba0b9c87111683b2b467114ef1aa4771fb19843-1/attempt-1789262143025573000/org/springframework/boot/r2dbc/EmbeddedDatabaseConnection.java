/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  io.r2dbc.spi.ConnectionFactoryOptions
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import java.util.function.Predicate;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.boot.r2dbc.OptionsCapableConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public enum EmbeddedDatabaseConnection {
    NONE(null, null, options -> false),
    H2("io.r2dbc.h2.H2ConnectionFactoryProvider", "r2dbc:h2:mem:///%s?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", options -> options.getValue(ConnectionFactoryOptions.DRIVER).equals("h2") && options.getValue(ConnectionFactoryOptions.PROTOCOL).equals("mem"));

    private final String driverClassName;
    private final String url;
    private Predicate<ConnectionFactoryOptions> embedded;

    private EmbeddedDatabaseConnection(String driverClassName, String url, Predicate<ConnectionFactoryOptions> embedded) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.embedded = embedded;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public String getUrl(String databaseName) {
        Assert.hasText((String)databaseName, (String)"DatabaseName must not be empty");
        return this.url != null ? String.format(this.url, databaseName) : null;
    }

    public static EmbeddedDatabaseConnection get(ClassLoader classLoader) {
        for (EmbeddedDatabaseConnection candidate : EmbeddedDatabaseConnection.values()) {
            if (candidate == NONE || !ClassUtils.isPresent((String)candidate.getDriverClassName(), (ClassLoader)classLoader)) continue;
            return candidate;
        }
        return NONE;
    }

    public static boolean isEmbedded(ConnectionFactory connectionFactory) {
        OptionsCapableConnectionFactory optionsCapable = OptionsCapableConnectionFactory.unwrapFrom(connectionFactory);
        Assert.notNull((Object)optionsCapable, () -> "Cannot determine database's type as ConnectionFactory is not options-capable. To be options-capable, a ConnectionFactory should be created with " + ConnectionFactoryBuilder.class.getName());
        ConnectionFactoryOptions options = optionsCapable.getOptions();
        for (EmbeddedDatabaseConnection candidate : EmbeddedDatabaseConnection.values()) {
            if (!candidate.embedded.test(options)) continue;
            return true;
        }
        return false;
    }
}

