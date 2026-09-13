/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactoryOptions
 *  io.r2dbc.spi.ConnectionFactoryOptions$Builder
 *  io.r2dbc.spi.Option
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.boot.r2dbc.EmbeddedDatabaseConnection
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.r2dbc.EmbeddedDatabaseConnection;
import org.springframework.util.StringUtils;

class ConnectionFactoryOptionsInitializer {
    ConnectionFactoryOptionsInitializer() {
    }

    ConnectionFactoryOptions.Builder initialize(R2dbcProperties properties, Supplier<EmbeddedDatabaseConnection> embeddedDatabaseConnection) {
        if (StringUtils.hasText((String)properties.getUrl())) {
            return this.initializeRegularOptions(properties);
        }
        EmbeddedDatabaseConnection embeddedConnection = embeddedDatabaseConnection.get();
        if (embeddedConnection != EmbeddedDatabaseConnection.NONE) {
            return this.initializeEmbeddedOptions(properties, embeddedConnection);
        }
        throw this.connectionFactoryBeanCreationException("Failed to determine a suitable R2DBC Connection URL", properties, embeddedConnection);
    }

    private ConnectionFactoryOptions.Builder initializeRegularOptions(R2dbcProperties properties) {
        ConnectionFactoryOptions urlOptions = ConnectionFactoryOptions.parse((CharSequence)properties.getUrl());
        ConnectionFactoryOptions.Builder optionsBuilder = urlOptions.mutate();
        this.configureIf(optionsBuilder, urlOptions, ConnectionFactoryOptions.USER, properties::getUsername, StringUtils::hasText);
        this.configureIf(optionsBuilder, urlOptions, ConnectionFactoryOptions.PASSWORD, properties::getPassword, StringUtils::hasText);
        this.configureIf(optionsBuilder, urlOptions, ConnectionFactoryOptions.DATABASE, () -> this.determineDatabaseName(properties), StringUtils::hasText);
        if (properties.getProperties() != null) {
            properties.getProperties().forEach((key, value) -> optionsBuilder.option(Option.valueOf((String)key), value));
        }
        return optionsBuilder;
    }

    private ConnectionFactoryOptions.Builder initializeEmbeddedOptions(R2dbcProperties properties, EmbeddedDatabaseConnection embeddedDatabaseConnection) {
        String url = embeddedDatabaseConnection.getUrl(this.determineEmbeddedDatabaseName(properties));
        if (url == null) {
            throw this.connectionFactoryBeanCreationException("Failed to determine a suitable R2DBC Connection URL", properties, embeddedDatabaseConnection);
        }
        ConnectionFactoryOptions.Builder builder = ConnectionFactoryOptions.parse((CharSequence)url).mutate();
        String username = this.determineEmbeddedUsername(properties);
        if (StringUtils.hasText((String)username)) {
            builder.option(ConnectionFactoryOptions.USER, (Object)username);
        }
        if (StringUtils.hasText((String)properties.getPassword())) {
            builder.option(ConnectionFactoryOptions.PASSWORD, (Object)properties.getPassword());
        }
        return builder;
    }

    private String determineDatabaseName(R2dbcProperties properties) {
        if (properties.isGenerateUniqueName()) {
            return properties.determineUniqueName();
        }
        if (StringUtils.hasLength((String)properties.getName())) {
            return properties.getName();
        }
        return null;
    }

    private String determineEmbeddedDatabaseName(R2dbcProperties properties) {
        String databaseName = this.determineDatabaseName(properties);
        return databaseName != null ? databaseName : "testdb";
    }

    private String determineEmbeddedUsername(R2dbcProperties properties) {
        String username = this.ifHasText(properties.getUsername());
        return username != null ? username : "sa";
    }

    private <T extends CharSequence> void configureIf(ConnectionFactoryOptions.Builder optionsBuilder, ConnectionFactoryOptions originalOptions, Option<T> option, Supplier<T> valueSupplier, Predicate<T> setIf) {
        if (originalOptions.hasOption(option)) {
            return;
        }
        CharSequence value = (CharSequence)valueSupplier.get();
        if (setIf.test(value)) {
            optionsBuilder.option(option, (Object)value);
        }
    }

    private ConnectionFactoryBeanCreationException connectionFactoryBeanCreationException(String message, R2dbcProperties properties, EmbeddedDatabaseConnection embeddedDatabaseConnection) {
        return new ConnectionFactoryBeanCreationException(message, properties, embeddedDatabaseConnection);
    }

    private String ifHasText(String candidate) {
        return StringUtils.hasText((String)candidate) ? candidate : null;
    }

    static class ConnectionFactoryBeanCreationException
    extends BeanCreationException {
        private final R2dbcProperties properties;
        private final EmbeddedDatabaseConnection embeddedDatabaseConnection;

        ConnectionFactoryBeanCreationException(String message, R2dbcProperties properties, EmbeddedDatabaseConnection embeddedDatabaseConnection) {
            super(message);
            this.properties = properties;
            this.embeddedDatabaseConnection = embeddedDatabaseConnection;
        }

        EmbeddedDatabaseConnection getEmbeddedDatabaseConnection() {
            return this.embeddedDatabaseConnection;
        }

        R2dbcProperties getProperties() {
            return this.properties;
        }
    }
}

