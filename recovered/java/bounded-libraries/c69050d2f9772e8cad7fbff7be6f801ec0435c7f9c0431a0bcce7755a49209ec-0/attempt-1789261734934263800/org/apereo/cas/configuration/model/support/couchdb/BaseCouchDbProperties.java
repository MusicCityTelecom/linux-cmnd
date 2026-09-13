/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.couchdb;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-couchdb-core")
public abstract class BaseCouchDbProperties
implements Serializable {
    private static final long serialVersionUID = 1323894615409106853L;
    @RequiredProperty
    private String url = "http://localhost:5984";
    @RequiredProperty
    private String username;
    @RequiredProperty
    private String password;
    private int socketTimeout = 10000;
    private int connectionTimeout = 1000;
    private int maxConnections = 20;
    private boolean enableSsl;
    private boolean relaxedSslSettings;
    private boolean caching;
    private int maxCacheEntries = 1000;
    private int maxObjectSizeBytes = 8192;
    private boolean useExpectContinue = true;
    private boolean cleanupIdleConnections = true;
    private boolean createIfNotExists = true;
    private int retries = 5;
    @RequiredProperty
    private String dbName;
    private String proxyHost;
    private int proxyPort = -1;

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    @Generated
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Generated
    public int getMaxConnections() {
        return this.maxConnections;
    }

    @Generated
    public boolean isEnableSsl() {
        return this.enableSsl;
    }

    @Generated
    public boolean isRelaxedSslSettings() {
        return this.relaxedSslSettings;
    }

    @Generated
    public boolean isCaching() {
        return this.caching;
    }

    @Generated
    public int getMaxCacheEntries() {
        return this.maxCacheEntries;
    }

    @Generated
    public int getMaxObjectSizeBytes() {
        return this.maxObjectSizeBytes;
    }

    @Generated
    public boolean isUseExpectContinue() {
        return this.useExpectContinue;
    }

    @Generated
    public boolean isCleanupIdleConnections() {
        return this.cleanupIdleConnections;
    }

    @Generated
    public boolean isCreateIfNotExists() {
        return this.createIfNotExists;
    }

    @Generated
    public int getRetries() {
        return this.retries;
    }

    @Generated
    public String getDbName() {
        return this.dbName;
    }

    @Generated
    public String getProxyHost() {
        return this.proxyHost;
    }

    @Generated
    public int getProxyPort() {
        return this.proxyPort;
    }

    @Generated
    public BaseCouchDbProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setEnableSsl(boolean enableSsl) {
        this.enableSsl = enableSsl;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setRelaxedSslSettings(boolean relaxedSslSettings) {
        this.relaxedSslSettings = relaxedSslSettings;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setCaching(boolean caching) {
        this.caching = caching;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setMaxCacheEntries(int maxCacheEntries) {
        this.maxCacheEntries = maxCacheEntries;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setMaxObjectSizeBytes(int maxObjectSizeBytes) {
        this.maxObjectSizeBytes = maxObjectSizeBytes;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setUseExpectContinue(boolean useExpectContinue) {
        this.useExpectContinue = useExpectContinue;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setCleanupIdleConnections(boolean cleanupIdleConnections) {
        this.cleanupIdleConnections = cleanupIdleConnections;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setCreateIfNotExists(boolean createIfNotExists) {
        this.createIfNotExists = createIfNotExists;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    @Generated
    public BaseCouchDbProperties setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }
}

