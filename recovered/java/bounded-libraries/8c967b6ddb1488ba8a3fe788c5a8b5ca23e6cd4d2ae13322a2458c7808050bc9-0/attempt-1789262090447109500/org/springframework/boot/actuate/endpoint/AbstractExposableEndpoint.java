/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.util.Assert;

public abstract class AbstractExposableEndpoint<O extends Operation>
implements ExposableEndpoint<O> {
    private final EndpointId id;
    private boolean enabledByDefault;
    private List<O> operations;

    public AbstractExposableEndpoint(EndpointId id, boolean enabledByDefault, Collection<? extends O> operations) {
        Assert.notNull((Object)id, (String)"ID must not be null");
        Assert.notNull(operations, (String)"Operations must not be null");
        this.id = id;
        this.enabledByDefault = enabledByDefault;
        this.operations = Collections.unmodifiableList(new ArrayList<O>(operations));
    }

    @Override
    public EndpointId getEndpointId() {
        return this.id;
    }

    @Override
    public boolean isEnableByDefault() {
        return this.enabledByDefault;
    }

    @Override
    public Collection<O> getOperations() {
        return this.operations;
    }
}

