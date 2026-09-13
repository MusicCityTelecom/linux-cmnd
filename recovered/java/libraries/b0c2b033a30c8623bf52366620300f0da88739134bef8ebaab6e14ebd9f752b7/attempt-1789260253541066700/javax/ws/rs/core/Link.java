/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public abstract class Link {
    public static final String TITLE = "title";
    public static final String REL = "rel";
    public static final String TYPE = "type";

    public abstract URI getUri();

    public abstract UriBuilder getUriBuilder();

    public abstract String getRel();

    public abstract List<String> getRels();

    public abstract String getTitle();

    public abstract String getType();

    public abstract Map<String, String> getParams();

    public abstract String toString();

    public static Link valueOf(String value) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.link(value);
        return b.build(new Object[0]);
    }

    public static Builder fromUri(URI uri) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.uri(uri);
        return b;
    }

    public static Builder fromUri(String uri) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.uri(uri);
        return b;
    }

    public static Builder fromUriBuilder(UriBuilder uriBuilder) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.uriBuilder(uriBuilder);
        return b;
    }

    public static Builder fromLink(Link link) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.link(link);
        return b;
    }

    public static Builder fromPath(String path) {
        return Link.fromUriBuilder(UriBuilder.fromPath(path));
    }

    public static Builder fromResource(Class<?> resource) {
        return Link.fromUriBuilder(UriBuilder.fromResource(resource));
    }

    public static Builder fromMethod(Class<?> resource, String method) {
        return Link.fromUriBuilder(UriBuilder.fromMethod(resource, method));
    }

    public static class JaxbAdapter
    extends XmlAdapter<JaxbLink, Link> {
        @Override
        public Link unmarshal(JaxbLink v) {
            Builder lb = Link.fromUri(v.getUri());
            for (Map.Entry<QName, Object> e : v.getParams().entrySet()) {
                lb.param(e.getKey().getLocalPart(), e.getValue().toString());
            }
            return lb.build(new Object[0]);
        }

        @Override
        public JaxbLink marshal(Link v) {
            JaxbLink jl = new JaxbLink(v.getUri());
            for (Map.Entry<String, String> e : v.getParams().entrySet()) {
                String name = e.getKey();
                jl.getParams().put(new QName("", name), e.getValue());
            }
            return jl;
        }
    }

    public static class JaxbLink {
        private URI uri;
        private Map<QName, Object> params;

        public JaxbLink() {
        }

        public JaxbLink(URI uri) {
            this.uri = uri;
        }

        public JaxbLink(URI uri, Map<QName, Object> params) {
            this.uri = uri;
            this.params = params;
        }

        @XmlAttribute(name="href")
        public URI getUri() {
            return this.uri;
        }

        @XmlAnyAttribute
        public Map<QName, Object> getParams() {
            if (this.params == null) {
                this.params = new HashMap<QName, Object>();
            }
            return this.params;
        }

        void setUri(URI uri) {
            this.uri = uri;
        }

        void setParams(Map<QName, Object> params) {
            this.params = params;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof JaxbLink)) {
                return false;
            }
            JaxbLink jaxbLink = (JaxbLink)o;
            if (this.uri != null ? !this.uri.equals(jaxbLink.uri) : jaxbLink.uri != null) {
                return false;
            }
            if (this.params == jaxbLink.params) {
                return true;
            }
            if (this.params == null) {
                return jaxbLink.params.isEmpty();
            }
            if (jaxbLink.params == null) {
                return this.params.isEmpty();
            }
            return this.params.equals(jaxbLink.params);
        }

        public int hashCode() {
            int result = this.uri != null ? this.uri.hashCode() : 0;
            result = 31 * result + (this.params != null && !this.params.isEmpty() ? this.params.hashCode() : 0);
            return result;
        }
    }

    public static interface Builder {
        public Builder link(Link var1);

        public Builder link(String var1);

        public Builder uri(URI var1);

        public Builder uri(String var1);

        public Builder baseUri(URI var1);

        public Builder baseUri(String var1);

        public Builder uriBuilder(UriBuilder var1);

        public Builder rel(String var1);

        public Builder title(String var1);

        public Builder type(String var1);

        public Builder param(String var1, String var2);

        public Link build(Object ... var1);

        public Link buildRelativized(URI var1, Object ... var2);
    }
}

