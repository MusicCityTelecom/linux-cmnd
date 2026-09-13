/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.net.URI;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

public interface Client
extends Configurable<Client> {
    public void close();

    public WebTarget target(String var1);

    public WebTarget target(URI var1);

    public WebTarget target(UriBuilder var1);

    public WebTarget target(Link var1);

    public Invocation.Builder invocation(Link var1);

    public SSLContext getSslContext();

    public HostnameVerifier getHostnameVerifier();
}

