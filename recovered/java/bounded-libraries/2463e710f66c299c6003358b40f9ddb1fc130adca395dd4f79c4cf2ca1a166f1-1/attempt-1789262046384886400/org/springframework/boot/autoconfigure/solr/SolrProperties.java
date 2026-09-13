/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.solr;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.data.solr")
public class SolrProperties {
    private String host = "http://127.0.0.1:8983/solr";
    private String zkHost;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getZkHost() {
        return this.zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }
}

