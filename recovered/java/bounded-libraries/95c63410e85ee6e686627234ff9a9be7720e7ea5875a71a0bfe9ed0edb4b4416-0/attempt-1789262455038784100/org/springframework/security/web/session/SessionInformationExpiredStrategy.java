/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 */
package org.springframework.security.web.session;

import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;

public interface SessionInformationExpiredStrategy {
    public void onExpiredSessionDetected(SessionInformationExpiredEvent var1) throws IOException, ServletException;
}

