/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.Record
 *  org.neo4j.driver.summary.ResultSummary
 */
package org.springframework.boot.actuate.neo4j;

import org.neo4j.driver.Record;
import org.neo4j.driver.summary.ResultSummary;

class Neo4jHealthDetails {
    private final String version;
    private final String edition;
    private final ResultSummary summary;

    Neo4jHealthDetails(Record record, ResultSummary summary) {
        this.version = record.get("version").asString();
        this.edition = record.get("edition").asString();
        this.summary = summary;
    }

    String getVersion() {
        return this.version;
    }

    String getEdition() {
        return this.edition;
    }

    ResultSummary getSummary() {
        return this.summary;
    }
}

