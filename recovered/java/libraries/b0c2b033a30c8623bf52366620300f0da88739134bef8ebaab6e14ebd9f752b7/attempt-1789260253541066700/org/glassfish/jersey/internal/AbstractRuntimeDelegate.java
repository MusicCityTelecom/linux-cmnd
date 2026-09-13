/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.message.internal.JerseyLink;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.message.internal.VariantListBuilder;
import org.glassfish.jersey.spi.HeaderDelegateProvider;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

public abstract class AbstractRuntimeDelegate
extends RuntimeDelegate {
    private final Set<HeaderDelegateProvider> hps;
    private final Map<Class<?>, RuntimeDelegate.HeaderDelegate<?>> map;

    protected AbstractRuntimeDelegate(Set<HeaderDelegateProvider> hps) {
        this.hps = hps;
        this.map = new WeakHashMap();
        this.map.put(EntityTag.class, this._createHeaderDelegate(EntityTag.class));
        this.map.put(MediaType.class, this._createHeaderDelegate(MediaType.class));
        this.map.put(CacheControl.class, this._createHeaderDelegate(CacheControl.class));
        this.map.put(NewCookie.class, this._createHeaderDelegate(NewCookie.class));
        this.map.put(Cookie.class, this._createHeaderDelegate(Cookie.class));
        this.map.put(URI.class, this._createHeaderDelegate(URI.class));
        this.map.put(Date.class, this._createHeaderDelegate(Date.class));
        this.map.put(String.class, this._createHeaderDelegate(String.class));
    }

    @Override
    public Variant.VariantListBuilder createVariantListBuilder() {
        return new VariantListBuilder();
    }

    @Override
    public Response.ResponseBuilder createResponseBuilder() {
        return new OutboundJaxrsResponse.Builder(new OutboundMessageContext((Configuration)null));
    }

    @Override
    public UriBuilder createUriBuilder() {
        return new JerseyUriBuilder();
    }

    @Override
    public Link.Builder createLinkBuilder() {
        return new JerseyLink.Builder();
    }

    @Override
    public <T> RuntimeDelegate.HeaderDelegate<T> createHeaderDelegate(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("type parameter cannot be null");
        }
        RuntimeDelegate.HeaderDelegate<?> delegate = this.map.get(type);
        if (delegate != null) {
            return delegate;
        }
        return this._createHeaderDelegate(type);
    }

    private <T> RuntimeDelegate.HeaderDelegate<T> _createHeaderDelegate(Class<T> type) {
        for (HeaderDelegateProvider hp : this.hps) {
            if (!hp.supports(type)) continue;
            return hp;
        }
        return null;
    }
}

