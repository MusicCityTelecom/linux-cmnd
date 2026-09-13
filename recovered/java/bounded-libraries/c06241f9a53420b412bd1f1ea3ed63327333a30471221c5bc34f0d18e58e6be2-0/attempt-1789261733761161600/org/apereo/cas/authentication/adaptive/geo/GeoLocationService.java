/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.adaptive.geo;

import java.net.InetAddress;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationResponse;

public interface GeoLocationService {
    public static final String BEAN_NAME = "geoLocationService";

    public GeoLocationResponse locate(InetAddress var1);

    public GeoLocationResponse locate(String var1);

    public GeoLocationResponse locate(Double var1, Double var2);

    public GeoLocationResponse locate(String var1, GeoLocationRequest var2);

    public GeoLocationResponse locate(GeoLocationRequest var1);
}

