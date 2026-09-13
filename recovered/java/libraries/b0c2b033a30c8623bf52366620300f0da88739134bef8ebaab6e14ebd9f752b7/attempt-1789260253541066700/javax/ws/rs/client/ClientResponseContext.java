/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public interface ClientResponseContext {
    public int getStatus();

    public void setStatus(int var1);

    public Response.StatusType getStatusInfo();

    public void setStatusInfo(Response.StatusType var1);

    public MultivaluedMap<String, String> getHeaders();

    public String getHeaderString(String var1);

    public Set<String> getAllowedMethods();

    public Date getDate();

    public Locale getLanguage();

    public int getLength();

    public MediaType getMediaType();

    public Map<String, NewCookie> getCookies();

    public EntityTag getEntityTag();

    public Date getLastModified();

    public URI getLocation();

    public Set<Link> getLinks();

    public boolean hasLink(String var1);

    public Link getLink(String var1);

    public Link.Builder getLinkBuilder(String var1);

    public boolean hasEntity();

    public InputStream getEntityStream();

    public void setEntityStream(InputStream var1);
}

