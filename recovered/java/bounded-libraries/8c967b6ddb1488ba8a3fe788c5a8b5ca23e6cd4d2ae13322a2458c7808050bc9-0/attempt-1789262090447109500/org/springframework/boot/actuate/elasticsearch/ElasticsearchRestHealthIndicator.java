/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.elasticsearch.client.RestClient
 *  org.elasticsearch.client.RestHighLevelClient
 */
package org.springframework.boot.actuate.elasticsearch;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.actuate.elasticsearch.ElasticsearchRestClientHealthIndicator;

@Deprecated
public class ElasticsearchRestHealthIndicator
extends ElasticsearchRestClientHealthIndicator {
    public ElasticsearchRestHealthIndicator(RestHighLevelClient client) {
        this(client.getLowLevelClient());
    }

    public ElasticsearchRestHealthIndicator(RestClient client) {
        super(client);
    }
}

