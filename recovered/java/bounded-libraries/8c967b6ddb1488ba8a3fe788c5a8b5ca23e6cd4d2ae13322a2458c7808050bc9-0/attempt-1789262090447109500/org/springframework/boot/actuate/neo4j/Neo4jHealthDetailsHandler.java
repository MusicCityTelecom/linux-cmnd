/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.summary.DatabaseInfo
 *  org.neo4j.driver.summary.ResultSummary
 *  org.neo4j.driver.summary.ServerInfo
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.neo4j;

import org.neo4j.driver.summary.DatabaseInfo;
import org.neo4j.driver.summary.ResultSummary;
import org.neo4j.driver.summary.ServerInfo;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.neo4j.Neo4jHealthDetails;
import org.springframework.util.StringUtils;

class Neo4jHealthDetailsHandler {
    Neo4jHealthDetailsHandler() {
    }

    void addHealthDetails(Health.Builder builder, Neo4jHealthDetails healthDetails) {
        ResultSummary summary = healthDetails.getSummary();
        ServerInfo serverInfo = summary.server();
        builder.up().withDetail("server", healthDetails.getVersion() + "@" + serverInfo.address()).withDetail("edition", healthDetails.getEdition());
        DatabaseInfo databaseInfo = summary.database();
        if (StringUtils.hasText((String)databaseInfo.name())) {
            builder.withDetail("database", databaseInfo.name());
        }
    }
}

