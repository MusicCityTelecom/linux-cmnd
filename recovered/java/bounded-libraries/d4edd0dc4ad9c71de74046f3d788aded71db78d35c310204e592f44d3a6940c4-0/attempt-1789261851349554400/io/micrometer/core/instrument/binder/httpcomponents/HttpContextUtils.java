/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpHost
 *  org.apache.http.conn.routing.HttpRoute
 *  org.apache.http.protocol.HttpContext
 */
package io.micrometer.core.instrument.binder.httpcomponents;

import io.micrometer.core.instrument.Tags;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

class HttpContextUtils {
    HttpContextUtils() {
    }

    static Tags generateTagsForRoute(HttpContext context) {
        String targetScheme = "UNKNOWN";
        String targetHost = "UNKNOWN";
        String targetPort = "UNKNOWN";
        Object routeAttribute = context.getAttribute("http.route");
        if (routeAttribute instanceof HttpRoute) {
            HttpHost host = ((HttpRoute)routeAttribute).getTargetHost();
            targetScheme = host.getSchemeName();
            targetHost = host.getHostName();
            targetPort = String.valueOf(host.getPort());
        }
        return Tags.of("target.scheme", targetScheme, "target.host", targetHost, "target.port", targetPort);
    }
}

