/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.samskivert.mustache.Mustache$Compiler
 *  com.samskivert.mustache.Template
 *  org.reactivestreams.Publisher
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.buffer.DataBuffer
 *  org.springframework.core.io.buffer.DataBufferUtils
 *  org.springframework.http.MediaType
 *  org.springframework.web.reactive.result.view.AbstractUrlBasedView
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.web.reactive.result.view;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.reactivestreams.Publisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MustacheView
extends AbstractUrlBasedView {
    private Mustache.Compiler compiler;
    private String charset;

    public void setCompiler(Mustache.Compiler compiler) {
        this.compiler = compiler;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean checkResourceExists(Locale locale) throws Exception {
        return this.resolveResource() != null;
    }

    protected Mono<Void> renderInternal(Map<String, Object> model, MediaType contentType, ServerWebExchange exchange) {
        Resource resource = this.resolveResource();
        if (resource == null) {
            return Mono.error((Throwable)new IllegalStateException("Could not find Mustache template with URL [" + this.getUrl() + "]"));
        }
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().allocateBuffer();
        try (Reader reader = this.getReader(resource);){
            Template template = this.compiler.compile(reader);
            Charset charset = this.getCharset(contentType).orElseGet(() -> ((MustacheView)this).getDefaultCharset());
            try (OutputStreamWriter writer = new OutputStreamWriter(dataBuffer.asOutputStream(), charset);){
                template.execute(model, (Writer)writer);
                ((Writer)writer).flush();
            }
        }
        catch (Exception ex) {
            DataBufferUtils.release((DataBuffer)dataBuffer);
            return Mono.error((Throwable)ex);
        }
        return exchange.getResponse().writeWith((Publisher)Flux.just((Object)dataBuffer));
    }

    private Resource resolveResource() {
        Resource resource = this.getApplicationContext().getResource(this.getUrl());
        if (resource == null || !resource.exists()) {
            return null;
        }
        return resource;
    }

    private Reader getReader(Resource resource) throws IOException {
        if (this.charset != null) {
            return new InputStreamReader(resource.getInputStream(), this.charset);
        }
        return new InputStreamReader(resource.getInputStream());
    }

    private Optional<Charset> getCharset(MediaType mediaType) {
        return Optional.ofNullable(mediaType != null ? mediaType.getCharset() : null);
    }
}

