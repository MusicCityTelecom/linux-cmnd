/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.web.reactive.function.client.ClientRequest
 *  org.springframework.web.reactive.function.client.ClientResponse
 */
package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.Tag;
import java.util.Arrays;
import org.springframework.boot.actuate.metrics.web.reactive.client.WebClientExchangeTags;
import org.springframework.boot.actuate.metrics.web.reactive.client.WebClientExchangeTagsProvider;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

public class DefaultWebClientExchangeTagsProvider
implements WebClientExchangeTagsProvider {
    @Override
    public Iterable<Tag> tags(ClientRequest request, ClientResponse response, Throwable throwable) {
        Tag method = WebClientExchangeTags.method(request);
        Tag uri = WebClientExchangeTags.uri(request);
        Tag clientName = WebClientExchangeTags.clientName(request);
        Tag status = WebClientExchangeTags.status(response, throwable);
        Tag outcome = WebClientExchangeTags.outcome(response);
        return Arrays.asList(method, uri, clientName, status, outcome);
    }
}

