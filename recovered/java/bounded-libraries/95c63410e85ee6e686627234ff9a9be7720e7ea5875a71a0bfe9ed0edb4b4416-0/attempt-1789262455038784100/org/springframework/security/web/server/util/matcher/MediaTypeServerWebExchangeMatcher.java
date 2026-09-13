/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.InvalidMediaTypeException
 *  org.springframework.http.MediaType
 *  org.springframework.util.Assert
 *  org.springframework.web.server.NotAcceptableStatusException
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class MediaTypeServerWebExchangeMatcher
implements ServerWebExchangeMatcher {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final Collection<MediaType> matchingMediaTypes;
    private boolean useEquals;
    private Set<MediaType> ignoredMediaTypes = Collections.emptySet();

    public MediaTypeServerWebExchangeMatcher(MediaType ... matchingMediaTypes) {
        Assert.notEmpty((Object[])matchingMediaTypes, (String)"matchingMediaTypes cannot be null");
        Assert.noNullElements((Object[])matchingMediaTypes, (String)"matchingMediaTypes cannot contain null");
        this.matchingMediaTypes = Arrays.asList(matchingMediaTypes);
    }

    public MediaTypeServerWebExchangeMatcher(Collection<MediaType> matchingMediaTypes) {
        Assert.notEmpty(matchingMediaTypes, (String)"matchingMediaTypes cannot be null");
        Assert.noNullElements(matchingMediaTypes, () -> "matchingMediaTypes cannot contain null. Got " + matchingMediaTypes);
        this.matchingMediaTypes = matchingMediaTypes;
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        List<MediaType> httpRequestMediaTypes;
        try {
            httpRequestMediaTypes = this.resolveMediaTypes(exchange);
        }
        catch (NotAcceptableStatusException ex) {
            this.logger.debug((Object)"Failed to parse MediaTypes, returning false", (Throwable)ex);
            return ServerWebExchangeMatcher.MatchResult.notMatch();
        }
        this.logger.debug((Object)LogMessage.format((String)"httpRequestMediaTypes=%s", httpRequestMediaTypes));
        for (MediaType httpRequestMediaType : httpRequestMediaTypes) {
            this.logger.debug((Object)LogMessage.format((String)"Processing %s", (Object)httpRequestMediaType));
            if (this.shouldIgnore(httpRequestMediaType)) {
                this.logger.debug((Object)"Ignoring");
                continue;
            }
            if (this.useEquals) {
                boolean isEqualTo = this.matchingMediaTypes.contains(httpRequestMediaType);
                this.logger.debug((Object)("isEqualTo " + isEqualTo));
                return isEqualTo ? ServerWebExchangeMatcher.MatchResult.match() : ServerWebExchangeMatcher.MatchResult.notMatch();
            }
            for (MediaType matchingMediaType : this.matchingMediaTypes) {
                boolean isCompatibleWith = matchingMediaType.isCompatibleWith(httpRequestMediaType);
                this.logger.debug((Object)LogMessage.format((String)"%s .isCompatibleWith %s = %s", (Object)matchingMediaType, (Object)httpRequestMediaType, (Object)isCompatibleWith));
                if (!isCompatibleWith) continue;
                return ServerWebExchangeMatcher.MatchResult.match();
            }
        }
        this.logger.debug((Object)"Did not match any media types");
        return ServerWebExchangeMatcher.MatchResult.notMatch();
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

    private List<MediaType> resolveMediaTypes(ServerWebExchange exchange) throws NotAcceptableStatusException {
        try {
            List mediaTypes = exchange.getRequest().getHeaders().getAccept();
            MediaType.sortBySpecificityAndQuality((List)mediaTypes);
            return mediaTypes;
        }
        catch (InvalidMediaTypeException ex) {
            String value = exchange.getRequest().getHeaders().getFirst("Accept");
            throw new NotAcceptableStatusException("Could not parse 'Accept' header [" + value + "]: " + ex.getMessage());
        }
    }

    public String toString() {
        return "MediaTypeRequestMatcher [matchingMediaTypes=" + this.matchingMediaTypes + ", useEquals=" + this.useEquals + ", ignoredMediaTypes=" + this.ignoredMediaTypes + "]";
    }
}

