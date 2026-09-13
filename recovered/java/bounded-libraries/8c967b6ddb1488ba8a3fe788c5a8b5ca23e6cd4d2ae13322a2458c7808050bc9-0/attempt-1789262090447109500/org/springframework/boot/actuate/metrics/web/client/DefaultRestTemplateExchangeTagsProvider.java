/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.http.HttpRequest
 *  org.springframework.http.client.ClientHttpResponse
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.metrics.web.client;

import io.micrometer.core.instrument.Tag;
import java.util.Arrays;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTags;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTagsProvider;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public class DefaultRestTemplateExchangeTagsProvider
implements RestTemplateExchangeTagsProvider {
    @Override
    public Iterable<Tag> getTags(String urlTemplate, HttpRequest request, ClientHttpResponse response) {
        Tag uriTag = StringUtils.hasText((String)urlTemplate) ? RestTemplateExchangeTags.uri(urlTemplate) : RestTemplateExchangeTags.uri(request);
        return Arrays.asList(RestTemplateExchangeTags.method(request), uriTag, RestTemplateExchangeTags.status(response), RestTemplateExchangeTags.clientName(request), RestTemplateExchangeTags.outcome(response));
    }
}

