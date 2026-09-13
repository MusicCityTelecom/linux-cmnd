/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.Beta;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.CLIENT)
@Beta
public interface InvocationBuilderListener {
    public void onNewBuilder(InvocationBuilderContext var1);

    public static interface InvocationBuilderContext {
        public InvocationBuilderContext accept(String ... var1);

        public InvocationBuilderContext accept(MediaType ... var1);

        public InvocationBuilderContext acceptLanguage(Locale ... var1);

        public InvocationBuilderContext acceptLanguage(String ... var1);

        public InvocationBuilderContext acceptEncoding(String ... var1);

        public InvocationBuilderContext cookie(Cookie var1);

        public InvocationBuilderContext cookie(String var1, String var2);

        public InvocationBuilderContext cacheControl(CacheControl var1);

        public List<String> getAccepted();

        public List<String> getAcceptedLanguages();

        public List<CacheControl> getCacheControls();

        public Configuration getConfiguration();

        public Map<String, Cookie> getCookies();

        public List<String> getEncodings();

        public List<String> getHeader(String var1);

        public MultivaluedMap<String, Object> getHeaders();

        public Object getProperty(String var1);

        public Collection<String> getPropertyNames();

        public URI getUri();

        public InvocationBuilderContext header(String var1, Object var2);

        public InvocationBuilderContext headers(MultivaluedMap<String, Object> var1);

        public InvocationBuilderContext property(String var1, Object var2);

        public void removeProperty(String var1);
    }
}

