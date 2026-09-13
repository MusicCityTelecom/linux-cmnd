/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.CqlSession
 *  com.datastax.oss.driver.api.core.metadata.Node
 *  com.datastax.oss.driver.api.core.metadata.NodeState
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.metadata.NodeState;
import java.util.Collection;
import java.util.Optional;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.Assert;

public class CassandraDriverHealthIndicator
extends AbstractHealthIndicator {
    private final CqlSession session;

    public CassandraDriverHealthIndicator(CqlSession session) {
        super("Cassandra health check failed");
        Assert.notNull((Object)session, (String)"session must not be null");
        this.session = session;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Collection nodes = this.session.getMetadata().getNodes().values();
        Optional<Node> nodeUp = nodes.stream().filter(node -> node.getState() == NodeState.UP).findAny();
        builder.status(nodeUp.isPresent() ? Status.UP : Status.DOWN);
        nodeUp.map(Node::getCassandraVersion).ifPresent(version -> builder.withDetail("version", version));
    }
}

