/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.DefaultUriBuilderFactory
 *  org.springframework.web.util.UriTemplateHandler
 */
package org.springframework.boot.web.client;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

public class RootUriTemplateHandler
implements UriTemplateHandler {
    private final String rootUri;
    private final UriTemplateHandler handler;

    protected RootUriTemplateHandler(UriTemplateHandler handler) {
        Assert.notNull((Object)handler, (String)"Handler must not be null");
        this.rootUri = null;
        this.handler = handler;
    }

    public RootUriTemplateHandler(String rootUri) {
        this(rootUri, (UriTemplateHandler)new DefaultUriBuilderFactory());
    }

    public RootUriTemplateHandler(String rootUri, UriTemplateHandler handler) {
        Assert.notNull((Object)rootUri, (String)"RootUri must not be null");
        Assert.notNull((Object)handler, (String)"Handler must not be null");
        this.rootUri = rootUri;
        this.handler = handler;
    }

    public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
        return this.handler.expand(this.apply(uriTemplate), uriVariables);
    }

    public URI expand(String uriTemplate, Object ... uriVariables) {
        return this.handler.expand(this.apply(uriTemplate), uriVariables);
    }

    private String apply(String uriTemplate) {
        if (StringUtils.startsWithIgnoreCase((String)uriTemplate, (String)"/")) {
            return this.getRootUri() + uriTemplate;
        }
        return uriTemplate;
    }

    public String getRootUri() {
        return this.rootUri;
    }

    public RootUriTemplateHandler withHandlerWrapper(Function<UriTemplateHandler, UriTemplateHandler> wrapper) {
        return new RootUriTemplateHandler(this.getRootUri(), wrapper.apply(this.handler));
    }

    public static RootUriTemplateHandler addTo(RestTemplate restTemplate, String rootUri) {
        Assert.notNull((Object)restTemplate, (String)"RestTemplate must not be null");
        RootUriTemplateHandler handler = new RootUriTemplateHandler(rootUri, restTemplate.getUriTemplateHandler());
        restTemplate.setUriTemplateHandler((UriTemplateHandler)handler);
        return handler;
    }
}

