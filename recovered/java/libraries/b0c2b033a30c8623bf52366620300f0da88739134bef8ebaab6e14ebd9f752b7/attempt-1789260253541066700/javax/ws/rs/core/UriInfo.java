/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.net.URI;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;

public interface UriInfo {
    public String getPath();

    public String getPath(boolean var1);

    public List<PathSegment> getPathSegments();

    public List<PathSegment> getPathSegments(boolean var1);

    public URI getRequestUri();

    public UriBuilder getRequestUriBuilder();

    public URI getAbsolutePath();

    public UriBuilder getAbsolutePathBuilder();

    public URI getBaseUri();

    public UriBuilder getBaseUriBuilder();

    public MultivaluedMap<String, String> getPathParameters();

    public MultivaluedMap<String, String> getPathParameters(boolean var1);

    public MultivaluedMap<String, String> getQueryParameters();

    public MultivaluedMap<String, String> getQueryParameters(boolean var1);

    public List<String> getMatchedURIs();

    public List<String> getMatchedURIs(boolean var1);

    public List<Object> getMatchedResources();

    public URI resolve(URI var1);

    public URI relativize(URI var1);
}

