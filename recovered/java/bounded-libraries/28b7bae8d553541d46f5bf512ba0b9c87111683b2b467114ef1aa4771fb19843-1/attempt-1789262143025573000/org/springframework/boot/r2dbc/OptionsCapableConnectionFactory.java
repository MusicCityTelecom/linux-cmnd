/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.Connection
 *  io.r2dbc.spi.ConnectionFactory
 *  io.r2dbc.spi.ConnectionFactoryMetadata
 *  io.r2dbc.spi.ConnectionFactoryOptions
 *  io.r2dbc.spi.Wrapped
 *  org.reactivestreams.Publisher
 */
package org.springframework.boot.r2dbc;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Wrapped;
import org.reactivestreams.Publisher;

public class OptionsCapableConnectionFactory
implements Wrapped<ConnectionFactory>,
ConnectionFactory {
    private final ConnectionFactoryOptions options;
    private final ConnectionFactory delegate;

    public OptionsCapableConnectionFactory(ConnectionFactoryOptions options, ConnectionFactory delegate) {
        this.options = options;
        this.delegate = delegate;
    }

    public ConnectionFactoryOptions getOptions() {
        return this.options;
    }

    public Publisher<? extends Connection> create() {
        return this.delegate.create();
    }

    public ConnectionFactoryMetadata getMetadata() {
        return this.delegate.getMetadata();
    }

    public ConnectionFactory unwrap() {
        return this.delegate;
    }

    public static OptionsCapableConnectionFactory unwrapFrom(ConnectionFactory connectionFactory) {
        Object unwrapped;
        if (connectionFactory instanceof OptionsCapableConnectionFactory) {
            return (OptionsCapableConnectionFactory)connectionFactory;
        }
        if (connectionFactory instanceof Wrapped && (unwrapped = ((Wrapped)connectionFactory).unwrap()) instanceof ConnectionFactory) {
            return OptionsCapableConnectionFactory.unwrapFrom((ConnectionFactory)unwrapped);
        }
        return null;
    }
}

