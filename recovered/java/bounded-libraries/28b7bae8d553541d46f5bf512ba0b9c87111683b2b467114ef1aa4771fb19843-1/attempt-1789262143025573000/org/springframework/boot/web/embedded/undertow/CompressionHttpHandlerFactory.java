/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.attribute.ExchangeAttribute
 *  io.undertow.attribute.RequestHeaderAttribute
 *  io.undertow.predicate.Predicate
 *  io.undertow.predicate.Predicates
 *  io.undertow.server.HttpHandler
 *  io.undertow.server.HttpServerExchange
 *  io.undertow.server.handlers.encoding.ContentEncodingProvider
 *  io.undertow.server.handlers.encoding.ContentEncodingRepository
 *  io.undertow.server.handlers.encoding.EncodingHandler
 *  io.undertow.server.handlers.encoding.GzipEncodingProvider
 *  io.undertow.util.Headers
 *  io.undertow.util.HttpString
 *  org.springframework.util.InvalidMimeTypeException
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.attribute.ExchangeAttribute;
import io.undertow.attribute.RequestHeaderAttribute;
import io.undertow.predicate.Predicate;
import io.undertow.predicate.Predicates;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.encoding.ContentEncodingProvider;
import io.undertow.server.handlers.encoding.ContentEncodingRepository;
import io.undertow.server.handlers.encoding.EncodingHandler;
import io.undertow.server.handlers.encoding.GzipEncodingProvider;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.boot.web.server.Compression;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

class CompressionHttpHandlerFactory
implements HttpHandlerFactory {
    private final Compression compression;

    CompressionHttpHandlerFactory(Compression compression) {
        this.compression = compression;
    }

    @Override
    public HttpHandler getHandler(HttpHandler next) {
        if (!this.compression.getEnabled()) {
            return next;
        }
        ContentEncodingRepository repository = new ContentEncodingRepository();
        repository.addEncodingHandler("gzip", (ContentEncodingProvider)new GzipEncodingProvider(), 50, Predicates.and((Predicate[])CompressionHttpHandlerFactory.getCompressionPredicates(this.compression)));
        return new EncodingHandler(repository).setNext(next);
    }

    private static Predicate[] getCompressionPredicates(Compression compression) {
        ArrayList<Object> predicates = new ArrayList<Object>();
        predicates.add(new MaxSizePredicate((int)compression.getMinResponseSize().toBytes()));
        predicates.add(new CompressibleMimeTypePredicate(compression.getMimeTypes()));
        if (compression.getExcludedUserAgents() != null) {
            for (String agent : compression.getExcludedUserAgents()) {
                RequestHeaderAttribute agentHeader = new RequestHeaderAttribute(new HttpString("User-Agent"));
                predicates.add(Predicates.not((Predicate)Predicates.regex((ExchangeAttribute)agentHeader, (String)agent)));
            }
        }
        return predicates.toArray(new Predicate[0]);
    }

    private static class MaxSizePredicate
    implements Predicate {
        private final Predicate maxContentSize;

        MaxSizePredicate(int size) {
            this.maxContentSize = Predicates.requestLargerThan((long)size);
        }

        public boolean resolve(HttpServerExchange value) {
            if (value.getResponseHeaders().contains(Headers.CONTENT_LENGTH)) {
                return this.maxContentSize.resolve(value);
            }
            return true;
        }
    }

    private static class CompressibleMimeTypePredicate
    implements Predicate {
        private final List<MimeType> mimeTypes;

        CompressibleMimeTypePredicate(String[] mimeTypes) {
            this.mimeTypes = new ArrayList<MimeType>(mimeTypes.length);
            for (String mimeTypeString : mimeTypes) {
                this.mimeTypes.add(MimeTypeUtils.parseMimeType((String)mimeTypeString));
            }
        }

        public boolean resolve(HttpServerExchange value) {
            String contentType = value.getResponseHeaders().getFirst("Content-Type");
            if (contentType != null) {
                try {
                    MimeType parsed = MimeTypeUtils.parseMimeType((String)contentType);
                    for (MimeType mimeType : this.mimeTypes) {
                        if (!mimeType.isCompatibleWith(parsed)) continue;
                        return true;
                    }
                }
                catch (InvalidMimeTypeException ex) {
                    return false;
                }
            }
            return false;
        }
    }
}

