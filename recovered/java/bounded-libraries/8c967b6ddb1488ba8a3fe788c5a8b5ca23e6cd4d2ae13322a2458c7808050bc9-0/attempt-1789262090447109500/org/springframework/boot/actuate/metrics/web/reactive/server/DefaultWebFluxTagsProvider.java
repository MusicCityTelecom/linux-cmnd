/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  org.springframework.web.server.ServerWebExchange
 */
package org.springframework.boot.actuate.metrics.web.reactive.server;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTags;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsContributor;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsProvider;
import org.springframework.web.server.ServerWebExchange;

public class DefaultWebFluxTagsProvider
implements WebFluxTagsProvider {
    private final boolean ignoreTrailingSlash;
    private final List<WebFluxTagsContributor> contributors;

    public DefaultWebFluxTagsProvider() {
        this(false);
    }

    public DefaultWebFluxTagsProvider(List<WebFluxTagsContributor> contributors) {
        this(false, contributors);
    }

    public DefaultWebFluxTagsProvider(boolean ignoreTrailingSlash) {
        this(ignoreTrailingSlash, Collections.emptyList());
    }

    public DefaultWebFluxTagsProvider(boolean ignoreTrailingSlash, List<WebFluxTagsContributor> contributors) {
        this.ignoreTrailingSlash = ignoreTrailingSlash;
        this.contributors = contributors;
    }

    @Override
    public Iterable<Tag> httpRequestTags(ServerWebExchange exchange, Throwable exception) {
        Tags tags = Tags.empty();
        tags = tags.and(new Tag[]{WebFluxTags.method(exchange)});
        tags = tags.and(new Tag[]{WebFluxTags.uri(exchange, this.ignoreTrailingSlash)});
        tags = tags.and(new Tag[]{WebFluxTags.exception(exception)});
        tags = tags.and(new Tag[]{WebFluxTags.status(exchange)});
        tags = tags.and(new Tag[]{WebFluxTags.outcome(exchange, exception)});
        for (WebFluxTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.httpRequestTags(exchange, exception));
        }
        return tags;
    }
}

