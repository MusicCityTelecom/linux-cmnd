/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.handler.codec.http.HttpHeaderNames
 *  io.netty.handler.codec.http.HttpHeaders
 *  org.springframework.util.InvalidMimeTypeException
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  reactor.netty.http.server.HttpServer
 *  reactor.netty.http.server.HttpServerRequest
 *  reactor.netty.http.server.HttpServerResponse
 */
package org.springframework.boot.web.embedded.netty;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.Compression;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

final class CompressionCustomizer
implements NettyServerCustomizer {
    private static final CompressionPredicate ALWAYS_COMPRESS = (request, response) -> true;
    private final Compression compression;

    CompressionCustomizer(Compression compression) {
        this.compression = compression;
    }

    @Override
    public HttpServer apply(HttpServer server) {
        if (!this.compression.getMinResponseSize().isNegative()) {
            server = server.compress((int)this.compression.getMinResponseSize().toBytes());
        }
        CompressionPredicate mimeTypes = this.getMimeTypesPredicate(this.compression.getMimeTypes());
        CompressionPredicate excludedUserAgents = this.getExcludedUserAgentsPredicate(this.compression.getExcludedUserAgents());
        server = server.compress(mimeTypes.and(excludedUserAgents));
        return server;
    }

    private CompressionPredicate getMimeTypesPredicate(String[] mimeTypeValues) {
        if (ObjectUtils.isEmpty((Object[])mimeTypeValues)) {
            return ALWAYS_COMPRESS;
        }
        List mimeTypes = Arrays.stream(mimeTypeValues).map(MimeTypeUtils::parseMimeType).collect(Collectors.toList());
        return (request, response) -> {
            String contentType = response.responseHeaders().get((CharSequence)HttpHeaderNames.CONTENT_TYPE);
            if (!StringUtils.hasLength((String)contentType)) {
                return false;
            }
            try {
                MimeType contentMimeType = MimeTypeUtils.parseMimeType((String)contentType);
                return mimeTypes.stream().anyMatch(candidate -> candidate.isCompatibleWith(contentMimeType));
            }
            catch (InvalidMimeTypeException ex) {
                return false;
            }
        };
    }

    private CompressionPredicate getExcludedUserAgentsPredicate(String[] excludedUserAgents) {
        if (ObjectUtils.isEmpty((Object[])excludedUserAgents)) {
            return ALWAYS_COMPRESS;
        }
        return (request, response) -> {
            HttpHeaders headers = request.requestHeaders();
            return Arrays.stream(excludedUserAgents).noneMatch(candidate -> headers.contains((CharSequence)HttpHeaderNames.USER_AGENT, (CharSequence)candidate, true));
        };
    }

    private static interface CompressionPredicate
    extends BiPredicate<HttpServerRequest, HttpServerResponse> {
    }
}

