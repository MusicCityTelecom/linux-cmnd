/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.container;

import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public interface ContainerRequestContext {
    public Object getProperty(String var1);

    public Collection<String> getPropertyNames();

    public void setProperty(String var1, Object var2);

    public void removeProperty(String var1);

    public UriInfo getUriInfo();

    public void setRequestUri(URI var1);

    public void setRequestUri(URI var1, URI var2);

    public Request getRequest();

    public String getMethod();

    public void setMethod(String var1);

    public MultivaluedMap<String, String> getHeaders();

    public String getHeaderString(String var1);

    public Date getDate();

    public Locale getLanguage();

    public int getLength();

    public MediaType getMediaType();

    public List<MediaType> getAcceptableMediaTypes();

    public List<Locale> getAcceptableLanguages();

    public Map<String, Cookie> getCookies();

    public boolean hasEntity();

    public InputStream getEntityStream();

    public void setEntityStream(InputStream var1);

    public SecurityContext getSecurityContext();

    public void setSecurityContext(SecurityContext var1);

    public void abortWith(Response var1);
}

