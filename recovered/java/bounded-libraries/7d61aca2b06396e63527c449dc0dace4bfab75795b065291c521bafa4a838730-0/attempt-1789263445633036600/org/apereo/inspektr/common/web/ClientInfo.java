/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.text.StringEscapeUtils
 */
package org.apereo.inspektr.common.web;

import java.net.Inet4Address;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.text.StringEscapeUtils;

public class ClientInfo {
    public static ClientInfo EMPTY_CLIENT_INFO = new ClientInfo();
    private final String serverIpAddress;
    private final String clientIpAddress;
    private final String geoLocation;
    private final String userAgent;

    private ClientInfo() {
        this(null);
    }

    public ClientInfo(HttpServletRequest request) {
        this(request, null, null, false);
    }

    public ClientInfo(HttpServletRequest request, String alternateServerAddrHeaderName, String alternateLocalAddrHeaderName, boolean useServerHostAddress) {
        try {
            String clientIpAddress;
            String serverIpAddress = request != null ? request.getLocalAddr() : null;
            String string = clientIpAddress = request != null ? request.getRemoteAddr() : null;
            if (request == null) {
                this.geoLocation = "unknown";
                this.userAgent = "unknown";
            } else {
                String header;
                if (useServerHostAddress) {
                    serverIpAddress = Inet4Address.getLocalHost().getHostAddress();
                } else if (alternateServerAddrHeaderName != null && !alternateServerAddrHeaderName.isEmpty()) {
                    String string2 = serverIpAddress = request.getHeader(alternateServerAddrHeaderName) != null ? request.getHeader(alternateServerAddrHeaderName) : request.getLocalAddr();
                }
                if (alternateLocalAddrHeaderName != null && !alternateLocalAddrHeaderName.isEmpty()) {
                    clientIpAddress = request.getHeader(alternateLocalAddrHeaderName) != null ? request.getHeader(alternateLocalAddrHeaderName) : request.getRemoteAddr();
                }
                this.userAgent = (header = request.getHeader("user-agent")) == null ? "unknown" : StringEscapeUtils.escapeHtml4((String)header);
                String geo = request.getParameter("geolocation");
                if (geo == null) {
                    geo = request.getHeader("geolocation");
                }
                this.geoLocation = geo == null ? "unknown" : StringEscapeUtils.escapeHtml4((String)geo);
            }
            this.serverIpAddress = serverIpAddress == null ? "unknown" : serverIpAddress;
            this.clientIpAddress = clientIpAddress == null ? "unknown" : clientIpAddress;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getServerIpAddress() {
        return this.serverIpAddress;
    }

    public String getClientIpAddress() {
        return this.clientIpAddress;
    }

    public String getGeoLocation() {
        return this.geoLocation;
    }

    public String getUserAgent() {
        return this.userAgent;
    }
}

