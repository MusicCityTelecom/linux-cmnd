/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.style.ToStringCreator
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.util.Collection;
import org.springframework.boot.actuate.endpoint.AbstractExposableEndpoint;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.Assert;

public abstract class AbstractDiscoveredEndpoint<O extends Operation>
extends AbstractExposableEndpoint<O>
implements DiscoveredEndpoint<O> {
    private final EndpointDiscoverer<?, ?> discoverer;
    private final Object endpointBean;

    public AbstractDiscoveredEndpoint(EndpointDiscoverer<?, ?> discoverer, Object endpointBean, EndpointId id, boolean enabledByDefault, Collection<? extends O> operations) {
        super(id, enabledByDefault, operations);
        Assert.notNull(discoverer, (String)"Discoverer must not be null");
        Assert.notNull((Object)endpointBean, (String)"EndpointBean must not be null");
        this.discoverer = discoverer;
        this.endpointBean = endpointBean;
    }

    @Override
    public Object getEndpointBean() {
        return this.endpointBean;
    }

    @Override
    public boolean wasDiscoveredBy(Class<? extends EndpointDiscoverer<?, ?>> discoverer) {
        return discoverer.isInstance(this.discoverer);
    }

    public String toString() {
        ToStringCreator creator = new ToStringCreator((Object)this).append("discoverer", (Object)this.discoverer.getClass().getName()).append("endpointBean", (Object)this.endpointBean.getClass().getName());
        this.appendFields(creator);
        return creator.toString();
    }

    protected void appendFields(ToStringCreator creator) {
    }
}

