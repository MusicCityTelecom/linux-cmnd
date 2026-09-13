/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.client.spi.ConnectorProvider;

public class HttpUrlConnectorProvider
implements ConnectorProvider {
    public static final String USE_FIXED_LENGTH_STREAMING = "jersey.config.client.httpUrlConnector.useFixedLengthStreaming";
    public static final String SET_METHOD_WORKAROUND = "jersey.config.client.httpUrlConnection.setMethodWorkaround";
    private static final ConnectionFactory DEFAULT_CONNECTION_FACTORY = new DefaultConnectionFactory();
    private static final Logger LOGGER = Logger.getLogger(HttpUrlConnectorProvider.class.getName());
    private ConnectionFactory connectionFactory = DEFAULT_CONNECTION_FACTORY;
    private int chunkSize = 4096;
    private boolean useFixedLengthStreaming = false;
    private boolean useSetMethodWorkaround = false;

    public HttpUrlConnectorProvider connectionFactory(ConnectionFactory connectionFactory) {
        if (connectionFactory == null) {
            throw new NullPointerException(LocalizationMessages.NULL_INPUT_PARAMETER("connectionFactory"));
        }
        this.connectionFactory = connectionFactory;
        return this;
    }

    public HttpUrlConnectorProvider chunkSize(int chunkSize) {
        if (chunkSize < 0) {
            throw new IllegalArgumentException(LocalizationMessages.NEGATIVE_INPUT_PARAMETER("chunkSize"));
        }
        this.chunkSize = chunkSize;
        return this;
    }

    public HttpUrlConnectorProvider useFixedLengthStreaming() {
        this.useFixedLengthStreaming = true;
        return this;
    }

    public HttpUrlConnectorProvider useSetMethodWorkaround() {
        this.useSetMethodWorkaround = true;
        return this;
    }

    @Override
    public Connector getConnector(Client client, Configuration config) {
        Map<String, Object> properties = config.getProperties();
        int computedChunkSize = ClientProperties.getValue(properties, "jersey.config.client.chunkedEncodingSize", this.chunkSize, Integer.class);
        if (computedChunkSize < 0) {
            LOGGER.warning(LocalizationMessages.NEGATIVE_CHUNK_SIZE(computedChunkSize, this.chunkSize));
            computedChunkSize = this.chunkSize;
        }
        boolean computedUseFixedLengthStreaming = ClientProperties.getValue(properties, USE_FIXED_LENGTH_STREAMING, this.useFixedLengthStreaming, Boolean.class);
        boolean computedUseSetMethodWorkaround = ClientProperties.getValue(properties, SET_METHOD_WORKAROUND, this.useSetMethodWorkaround, Boolean.class);
        return this.createHttpUrlConnector(client, this.connectionFactory, computedChunkSize, computedUseFixedLengthStreaming, computedUseSetMethodWorkaround);
    }

    protected Connector createHttpUrlConnector(Client client, ConnectionFactory connectionFactory, int chunkSize, boolean fixLengthStreaming, boolean setMethodWorkaround) {
        return new HttpUrlConnector(client, connectionFactory, chunkSize, fixLengthStreaming, setMethodWorkaround);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        HttpUrlConnectorProvider that = (HttpUrlConnectorProvider)o;
        if (this.chunkSize != that.chunkSize) {
            return false;
        }
        if (this.useFixedLengthStreaming != that.useFixedLengthStreaming) {
            return false;
        }
        return this.connectionFactory.equals(that.connectionFactory);
    }

    public int hashCode() {
        int result = this.connectionFactory.hashCode();
        result = 31 * result + this.chunkSize;
        result = 31 * result + (this.useFixedLengthStreaming ? 1 : 0);
        return result;
    }

    private static class DefaultConnectionFactory
    implements ConnectionFactory {
        private DefaultConnectionFactory() {
        }

        @Override
        public HttpURLConnection getConnection(URL url) throws IOException {
            return (HttpURLConnection)url.openConnection();
        }
    }

    public static interface ConnectionFactory {
        public HttpURLConnection getConnection(URL var1) throws IOException;
    }
}

