/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public interface ClientRequestContext {
    public Object getProperty(String var1);

    public Collection<String> getPropertyNames();

    public void setProperty(String var1, Object var2);

    public void removeProperty(String var1);

    public URI getUri();

    public void setUri(URI var1);

    public String getMethod();

    public void setMethod(String var1);

    public MultivaluedMap<String, Object> getHeaders();

    public MultivaluedMap<String, String> getStringHeaders();

    public String getHeaderString(String var1);

    public Date getDate();

    public Locale getLanguage();

    public MediaType getMediaType();

    public List<MediaType> getAcceptableMediaTypes();

    public List<Locale> getAcceptableLanguages();

    public Map<String, Cookie> getCookies();

    public boolean hasEntity();

    public Object getEntity();

    public Class<?> getEntityClass();

    public Type getEntityType();

    public void setEntity(Object var1);

    public void setEntity(Object var1, Annotation[] var2, MediaType var3);

    public Annotation[] getEntityAnnotations();

    public OutputStream getEntityStream();

    public void setEntityStream(OutputStream var1);

    public Client getClient();

    public Configuration getConfiguration();

    public void abortWith(Response var1);
}

