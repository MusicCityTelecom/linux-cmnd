/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.neo4j.driver.AccessMode
 *  org.neo4j.driver.Driver
 *  org.neo4j.driver.Record
 *  org.neo4j.driver.Result
 *  org.neo4j.driver.Session
 *  org.neo4j.driver.SessionConfig
 *  org.neo4j.driver.exceptions.SessionExpiredException
 *  org.neo4j.driver.summary.ResultSummary
 */
package org.springframework.boot.actuate.neo4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.exceptions.SessionExpiredException;
import org.neo4j.driver.summary.ResultSummary;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.neo4j.Neo4jHealthDetails;
import org.springframework.boot.actuate.neo4j.Neo4jHealthDetailsHandler;

public class Neo4jHealthIndicator
extends AbstractHealthIndicator {
    private static final Log logger = LogFactory.getLog(Neo4jHealthIndicator.class);
    static final String CYPHER = "CALL dbms.components() YIELD versions, name, edition WHERE name = 'Neo4j Kernel' RETURN edition, versions[0] as version";
    static final String MESSAGE_SESSION_EXPIRED = "Neo4j session has expired, retrying one single time to retrieve server health.";
    static final SessionConfig DEFAULT_SESSION_CONFIG = SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build();
    private final Driver driver;
    private final Neo4jHealthDetailsHandler healthDetailsHandler;

    public Neo4jHealthIndicator(Driver driver) {
        super("Neo4j health check failed");
        this.driver = driver;
        this.healthDetailsHandler = new Neo4jHealthDetailsHandler();
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            try {
                this.runHealthCheckQuery(builder);
            }
            catch (SessionExpiredException ex) {
                logger.warn((Object)MESSAGE_SESSION_EXPIRED);
                this.runHealthCheckQuery(builder);
            }
        }
        catch (Exception ex) {
            builder.down().withException(ex);
        }
    }

    private void runHealthCheckQuery(Health.Builder builder) {
        try (Session session = this.driver.session(DEFAULT_SESSION_CONFIG);){
            Result result = session.run(CYPHER);
            Record record = result.single();
            ResultSummary resultSummary = result.consume();
            this.healthDetailsHandler.addHealthDetails(builder, new Neo4jHealthDetails(record, resultSummary));
        }
    }
}

