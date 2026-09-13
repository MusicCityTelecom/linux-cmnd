/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.util.Assert;

public class EndpointMediaTypes {
    public static final EndpointMediaTypes DEFAULT = new EndpointMediaTypes(ApiVersion.V3.getProducedMimeType().toString(), ApiVersion.V2.getProducedMimeType().toString(), "application/json");
    private final List<String> produced;
    private final List<String> consumed;

    public EndpointMediaTypes(String ... producedAndConsumed) {
        this(producedAndConsumed != null ? Arrays.asList(producedAndConsumed) : null);
    }

    public EndpointMediaTypes(List<String> producedAndConsumed) {
        this(producedAndConsumed, producedAndConsumed);
    }

    public EndpointMediaTypes(List<String> produced, List<String> consumed) {
        Assert.notNull(produced, (String)"Produced must not be null");
        Assert.notNull(consumed, (String)"Consumed must not be null");
        this.produced = Collections.unmodifiableList(produced);
        this.consumed = Collections.unmodifiableList(consumed);
    }

    public List<String> getProduced() {
        return this.produced;
    }

    public List<String> getConsumed() {
        return this.consumed;
    }
}

