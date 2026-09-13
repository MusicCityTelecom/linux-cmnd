/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.http.MediaType
 *  org.springframework.util.Assert
 *  org.springframework.web.HttpMediaTypeNotAcceptableException
 *  org.springframework.web.accept.ContentNegotiationStrategy
 *  org.springframework.web.accept.HeaderContentNegotiationStrategy
 *  org.springframework.web.context.request.NativeWebRequest
 *  org.springframework.web.context.request.ServletWebRequest
 */
package org.springframework.security.web.util.matcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

public final class MediaTypeRequestMatcher
implements RequestMatcher {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final ContentNegotiationStrategy contentNegotiationStrategy;
    private final Collection<MediaType> matchingMediaTypes;
    private boolean useEquals;
    private Set<MediaType> ignoredMediaTypes = Collections.emptySet();

    public MediaTypeRequestMatcher(MediaType ... matchingMediaTypes) {
        this((ContentNegotiationStrategy)new HeaderContentNegotiationStrategy(), Arrays.asList(matchingMediaTypes));
    }

    public MediaTypeRequestMatcher(Collection<MediaType> matchingMediaTypes) {
        this((ContentNegotiationStrategy)new HeaderContentNegotiationStrategy(), matchingMediaTypes);
    }

    public MediaTypeRequestMatcher(ContentNegotiationStrategy contentNegotiationStrategy, MediaType ... matchingMediaTypes) {
        this(contentNegotiationStrategy, Arrays.asList(matchingMediaTypes));
    }

    public MediaTypeRequestMatcher(ContentNegotiationStrategy contentNegotiationStrategy, Collection<MediaType> matchingMediaTypes) {
        Assert.notNull((Object)contentNegotiationStrategy, (String)"ContentNegotiationStrategy cannot be null");
        Assert.notEmpty(matchingMediaTypes, (String)"matchingMediaTypes cannot be null or empty");
        this.contentNegotiationStrategy = contentNegotiationStrategy;
        this.matchingMediaTypes = matchingMediaTypes;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        List httpRequestMediaTypes;
        try {
            httpRequestMediaTypes = this.contentNegotiationStrategy.resolveMediaTypes((NativeWebRequest)new ServletWebRequest(request));
        }
        catch (HttpMediaTypeNotAcceptableException ex) {
            this.logger.debug((Object)"Failed to match request since failed to parse MediaTypes", (Throwable)ex);
            return false;
        }
        for (MediaType httpRequestMediaType : httpRequestMediaTypes) {
            if (this.shouldIgnore(httpRequestMediaType)) continue;
            if (this.useEquals) {
                return this.matchingMediaTypes.contains(httpRequestMediaType);
            }
            for (MediaType matchingMediaType : this.matchingMediaTypes) {
                boolean isCompatibleWith = matchingMediaType.isCompatibleWith(httpRequestMediaType);
                if (!isCompatibleWith) continue;
                return true;
            }
        }
        return false;
    }

    private boolean shouldIgnore(MediaType httpRequestMediaType) {
        for (MediaType ignoredMediaType : this.ignoredMediaTypes) {
            if (!httpRequestMediaType.includes(ignoredMediaType)) continue;
            return true;
        }
        return false;
    }

    public void setUseEquals(boolean useEquals) {
        this.useEquals = useEquals;
    }

    public void setIgnoredMediaTypes(Set<MediaType> ignoredMediaTypes) {
        this.ignoredMediaTypes = ignoredMediaTypes;
    }

    public String toString() {
        return "MediaTypeRequestMatcher [contentNegotiationStrategy=" + this.contentNegotiationStrategy + ", matchingMediaTypes=" + this.matchingMediaTypes + ", useEquals=" + this.useEquals + ", ignoredMediaTypes=" + this.ignoredMediaTypes + "]";
    }
}

