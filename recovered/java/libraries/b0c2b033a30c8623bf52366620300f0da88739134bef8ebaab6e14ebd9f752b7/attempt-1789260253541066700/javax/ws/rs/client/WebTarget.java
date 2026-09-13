/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.net.URI;
import java.util.Map;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public interface WebTarget
extends Configurable<WebTarget> {
    public URI getUri();

    public UriBuilder getUriBuilder();

    public WebTarget path(String var1);

    public WebTarget resolveTemplate(String var1, Object var2);

    public WebTarget resolveTemplate(String var1, Object var2, boolean var3);

    public WebTarget resolveTemplateFromEncoded(String var1, Object var2);

    public WebTarget resolveTemplates(Map<String, Object> var1);

    public WebTarget resolveTemplates(Map<String, Object> var1, boolean var2);

    public WebTarget resolveTemplatesFromEncoded(Map<String, Object> var1);

    public WebTarget matrixParam(String var1, Object ... var2);

    public WebTarget queryParam(String var1, Object ... var2);

    public Invocation.Builder request();

    public Invocation.Builder request(String ... var1);

    public Invocation.Builder request(MediaType ... var1);
}

