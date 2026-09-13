/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import javax.ws.rs.core.MultivaluedMap;

public interface PathSegment {
    public String getPath();

    public MultivaluedMap<String, String> getMatrixParameters();
}

