/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.boot.actuate.metrics.web.servlet;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;

public class DefaultWebMvcTagsProvider
implements WebMvcTagsProvider {
    private final boolean ignoreTrailingSlash;
    private final List<WebMvcTagsContributor> contributors;

    public DefaultWebMvcTagsProvider() {
        this(false);
    }

    public DefaultWebMvcTagsProvider(List<WebMvcTagsContributor> contributors) {
        this(false, contributors);
    }

    public DefaultWebMvcTagsProvider(boolean ignoreTrailingSlash) {
        this(ignoreTrailingSlash, Collections.emptyList());
    }

    public DefaultWebMvcTagsProvider(boolean ignoreTrailingSlash, List<WebMvcTagsContributor> contributors) {
        this.ignoreTrailingSlash = ignoreTrailingSlash;
        this.contributors = contributors;
    }

    @Override
    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
        Tags tags = Tags.of((Tag[])new Tag[]{WebMvcTags.method(request), WebMvcTags.uri(request, response, this.ignoreTrailingSlash), WebMvcTags.exception(exception), WebMvcTags.status(response), WebMvcTags.outcome(response)});
        for (WebMvcTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.getTags(request, response, handler, exception));
        }
        return tags;
    }

    @Override
    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
        Tags tags = Tags.of((Tag[])new Tag[]{WebMvcTags.method(request), WebMvcTags.uri(request, null, this.ignoreTrailingSlash)});
        for (WebMvcTagsContributor contributor : this.contributors) {
            tags = tags.and(contributor.getLongRequestTags(request, handler));
        }
        return tags;
    }
}

