/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.http.HttpMessage
 */
package org.apereo.cas.logout;

import java.net.URL;
import org.apereo.cas.util.http.HttpMessage;

public class LogoutHttpMessage
extends HttpMessage {
    public static final String LOGOUT_REQUEST_PARAMETER = "logoutRequest";
    private static final long serialVersionUID = 399581521957873727L;

    public LogoutHttpMessage(URL url, String message, boolean asynchronous) {
        super(url, message, asynchronous);
        this.setContentType("application/x-www-form-urlencoded");
    }

    protected String formatOutputMessageInternal(String message) {
        return "logoutRequest=" + super.formatOutputMessageInternal(message);
    }
}

