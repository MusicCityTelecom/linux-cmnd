/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpSession
 */
package org.springframework.security.web.authentication;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebAuthenticationDetails
implements Serializable {
    private static final long serialVersionUID = 570L;
    private final String remoteAddress;
    private final String sessionId;

    public WebAuthenticationDetails(HttpServletRequest request) {
        this(request.getRemoteAddr(), WebAuthenticationDetails.extractSessionId(request));
    }

    public WebAuthenticationDetails(String remoteAddress, String sessionId) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
    }

    private static String extractSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? session.getId() : null;
    }

    public boolean equals(Object obj) {
        if (obj instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails other = (WebAuthenticationDetails)obj;
            if (this.remoteAddress == null && other.getRemoteAddress() != null) {
                return false;
            }
            if (this.remoteAddress != null && other.getRemoteAddress() == null) {
                return false;
            }
            if (this.remoteAddress != null && !this.remoteAddress.equals(other.getRemoteAddress())) {
                return false;
            }
            if (this.sessionId == null && other.getSessionId() != null) {
                return false;
            }
            if (this.sessionId != null && other.getSessionId() == null) {
                return false;
            }
            return this.sessionId == null || this.sessionId.equals(other.getSessionId());
        }
        return false;
    }

    public String getRemoteAddress() {
        return this.remoteAddress;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public int hashCode() {
        int code = 7654;
        if (this.remoteAddress != null) {
            code *= this.remoteAddress.hashCode() % 7;
        }
        if (this.sessionId != null) {
            code *= this.sessionId.hashCode() % 7;
        }
        return code;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(" [");
        sb.append("RemoteIpAddress=").append(this.getRemoteAddress()).append(", ");
        sb.append("SessionId=").append(this.getSessionId()).append("]");
        return sb.toString();
    }
}

