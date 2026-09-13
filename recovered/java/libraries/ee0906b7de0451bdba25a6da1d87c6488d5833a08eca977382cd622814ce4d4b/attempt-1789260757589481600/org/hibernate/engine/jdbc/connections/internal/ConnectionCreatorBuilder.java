/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.engine.jdbc.connections.internal;

import java.sql.Driver;
import java.util.Properties;
import org.hibernate.engine.jdbc.connections.internal.ConnectionCreator;
import org.hibernate.engine.jdbc.connections.internal.DriverConnectionCreator;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionCreator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class ConnectionCreatorBuilder {
    private final ServiceRegistryImplementor serviceRegistry;
    private Driver driver;
    private String url;
    private Properties connectionProps;
    private boolean autoCommit;
    private Integer isolation;

    public ConnectionCreatorBuilder(ServiceRegistryImplementor serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setConnectionProps(Properties connectionProps) {
        this.connectionProps = connectionProps;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void setIsolation(Integer isolation) {
        this.isolation = isolation;
    }

    public ConnectionCreator build() {
        if (this.driver == null) {
            return new DriverManagerConnectionCreator(this.serviceRegistry, this.url, this.connectionProps, (Boolean)this.autoCommit, this.isolation);
        }
        return new DriverConnectionCreator(this.driver, this.serviceRegistry, this.url, this.connectionProps, this.autoCommit, this.isolation);
    }
}

