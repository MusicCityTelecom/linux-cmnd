/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.message.internal.LinkProvider;
import org.glassfish.jersey.uri.UriTemplate;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

public final class JerseyLink
extends Link {
    private final URI uri;
    private final Map<String, String> params;

    private JerseyLink(URI uri, Map<String, String> params) {
        this.uri = uri;
        this.params = params;
    }

    @Override
    public URI getUri() {
        return this.uri;
    }

    @Override
    public UriBuilder getUriBuilder() {
        return new JerseyUriBuilder().uri(this.uri);
    }

    @Override
    public String getRel() {
        return this.params.get("rel");
    }

    @Override
    public List<String> getRels() {
        String rels = this.params.get("rel");
        return rels == null ? Collections.emptyList() : Arrays.asList(rels.split(" +"));
    }

    @Override
    public String getTitle() {
        return this.params.get("title");
    }

    @Override
    public String getType() {
        return this.params.get("type");
    }

    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    @Override
    public String toString() {
        return LinkProvider.stringfy(this);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Link) {
            Link otherLink = (Link)other;
            return this.uri.equals(otherLink.getUri()) && this.params.equals(otherLink.getParams());
        }
        return false;
    }

    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.uri != null ? this.uri.hashCode() : 0);
        hash = 89 * hash + (this.params != null ? this.params.hashCode() : 0);
        return hash;
    }

    public static class Builder
    implements Link.Builder {
        private UriBuilder uriBuilder = new JerseyUriBuilder();
        private URI baseUri = null;
        private Map<String, String> params = new HashMap<String, String>();

        @Override
        public Builder link(Link link) {
            this.uriBuilder.uri(link.getUri());
            this.params.clear();
            this.params.putAll(link.getParams());
            return this;
        }

        @Override
        public Builder link(String link) {
            LinkProvider.initBuilder(this, link);
            return this;
        }

        @Override
        public Builder uri(URI uri) {
            this.uriBuilder = UriBuilder.fromUri(uri);
            return this;
        }

        @Override
        public Builder uri(String uri) {
            this.uriBuilder = UriBuilder.fromUri(uri);
            return this;
        }

        @Override
        public Builder uriBuilder(UriBuilder uriBuilder) {
            this.uriBuilder = UriBuilder.fromUri(uriBuilder.toTemplate());
            return this;
        }

        @Override
        public Link.Builder baseUri(URI uri) {
            this.baseUri = uri;
            return this;
        }

        @Override
        public Link.Builder baseUri(String uri) {
            this.baseUri = URI.create(uri);
            return this;
        }

        @Override
        public Builder rel(String rel) {
            String rels = this.params.get("rel");
            this.param("rel", rels == null ? rel : rels + " " + rel);
            return this;
        }

        @Override
        public Builder title(String title) {
            this.param("title", title);
            return this;
        }

        @Override
        public Builder type(String type) {
            this.param("type", type);
            return this;
        }

        @Override
        public Builder param(String name, String value) {
            if (name == null || value == null) {
                throw new IllegalArgumentException("Link parameter name or value is null");
            }
            this.params.put(name, value);
            return this;
        }

        @Override
        public JerseyLink build(Object ... values) {
            URI linkUri = this.resolveLinkUri(values);
            return new JerseyLink(linkUri, Collections.unmodifiableMap(new HashMap<String, String>(this.params)));
        }

        @Override
        public Link buildRelativized(URI uri, Object ... values) {
            URI linkUri = UriTemplate.relativize(uri, this.resolveLinkUri(values));
            return new JerseyLink(linkUri, Collections.unmodifiableMap(new HashMap<String, String>(this.params)));
        }

        private URI resolveLinkUri(Object[] values) {
            URI linkUri = this.uriBuilder.build(values);
            if (this.baseUri == null || linkUri.isAbsolute()) {
                return UriTemplate.normalize(linkUri);
            }
            return UriTemplate.resolve(this.baseUri, linkUri);
        }
    }
}

