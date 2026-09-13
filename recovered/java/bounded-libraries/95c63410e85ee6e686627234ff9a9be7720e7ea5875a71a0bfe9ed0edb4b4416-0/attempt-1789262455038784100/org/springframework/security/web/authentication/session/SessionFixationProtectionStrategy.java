/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpSession
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.authentication.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.session.AbstractSessionFixationProtectionStrategy;

public class SessionFixationProtectionStrategy
extends AbstractSessionFixationProtectionStrategy {
    boolean migrateSessionAttributes = true;

    protected Map<String, Object> extractAttributes(HttpSession session) {
        return this.createMigratedAttributeMap(session);
    }

    @Override
    final HttpSession applySessionFixation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String originalSessionId = session.getId();
        this.logger.debug((Object)LogMessage.of(() -> "Invalidating session with Id '" + originalSessionId + "' " + (this.migrateSessionAttributes ? "and" : "without") + " migrating attributes."));
        Map<String, Object> attributesToMigrate = this.extractAttributes(session);
        int maxInactiveIntervalToMigrate = session.getMaxInactiveInterval();
        session.invalidate();
        session = request.getSession(true);
        this.logger.debug((Object)LogMessage.format((String)"Started new session: %s", (Object)session.getId()));
        this.transferAttributes(attributesToMigrate, session);
        if (this.migrateSessionAttributes) {
            session.setMaxInactiveInterval(maxInactiveIntervalToMigrate);
        }
        return session;
    }

    void transferAttributes(Map<String, Object> attributes, HttpSession newSession) {
        if (attributes != null) {
            attributes.forEach((arg_0, arg_1) -> ((HttpSession)newSession).setAttribute(arg_0, arg_1));
        }
    }

    private HashMap<String, Object> createMigratedAttributeMap(HttpSession session) {
        HashMap<String, Object> attributesToMigrate = new HashMap<String, Object>();
        Enumeration enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = (String)enumeration.nextElement();
            if (!this.migrateSessionAttributes && !key.startsWith("SPRING_SECURITY_")) continue;
            attributesToMigrate.put(key, session.getAttribute(key));
        }
        return attributesToMigrate;
    }

    public void setMigrateSessionAttributes(boolean migrateSessionAttributes) {
        this.migrateSessionAttributes = migrateSessionAttributes;
    }
}

