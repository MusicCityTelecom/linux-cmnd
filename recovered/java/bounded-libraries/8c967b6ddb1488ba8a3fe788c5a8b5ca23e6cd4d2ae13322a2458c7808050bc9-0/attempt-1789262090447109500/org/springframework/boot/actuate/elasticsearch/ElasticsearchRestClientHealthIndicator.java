/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.StatusLine
 *  org.elasticsearch.client.Request
 *  org.elasticsearch.client.Response
 *  org.elasticsearch.client.RestClient
 *  org.springframework.boot.json.JsonParser
 *  org.springframework.boot.json.JsonParserFactory
 *  org.springframework.util.StreamUtils
 */
package org.springframework.boot.actuate.elasticsearch;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.http.StatusLine;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.util.StreamUtils;

public class ElasticsearchRestClientHealthIndicator
extends AbstractHealthIndicator {
    private static final String RED_STATUS = "red";
    private final RestClient client;
    private final JsonParser jsonParser;

    public ElasticsearchRestClientHealthIndicator(RestClient client) {
        super("Elasticsearch health check failed");
        this.client = client;
        this.jsonParser = JsonParserFactory.getJsonParser();
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Response response = this.client.performRequest(new Request("GET", "/_cluster/health/"));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != 200) {
            builder.down();
            builder.withDetail("statusCode", statusLine.getStatusCode());
            builder.withDetail("reasonPhrase", statusLine.getReasonPhrase());
            return;
        }
        try (InputStream inputStream = response.getEntity().getContent();){
            this.doHealthCheck(builder, StreamUtils.copyToString((InputStream)inputStream, (Charset)StandardCharsets.UTF_8));
        }
    }

    private void doHealthCheck(Health.Builder builder, String json) {
        Map response = this.jsonParser.parseMap(json);
        String status = (String)response.get("status");
        if (RED_STATUS.equals(status)) {
            builder.outOfService();
        } else {
            builder.up();
        }
        builder.withDetails(response);
    }
}

