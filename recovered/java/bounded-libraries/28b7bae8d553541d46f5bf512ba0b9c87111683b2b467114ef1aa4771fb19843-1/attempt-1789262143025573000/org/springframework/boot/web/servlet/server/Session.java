/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.web.servlet.server.SessionStoreDirectory;

public class Session {
    @DurationUnit(value=ChronoUnit.SECONDS)
    private Duration timeout = Duration.ofMinutes(30L);
    private Set<SessionTrackingMode> trackingModes;
    private boolean persistent;
    private File storeDir;
    private final Cookie cookie = new Cookie();
    private final SessionStoreDirectory sessionStoreDirectory = new SessionStoreDirectory();

    public Duration getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Set<SessionTrackingMode> getTrackingModes() {
        return this.trackingModes;
    }

    public void setTrackingModes(Set<SessionTrackingMode> trackingModes) {
        this.trackingModes = trackingModes;
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public File getStoreDir() {
        return this.storeDir;
    }

    public void setStoreDir(File storeDir) {
        this.sessionStoreDirectory.setDirectory(storeDir);
        this.storeDir = storeDir;
    }

    public Cookie getCookie() {
        return this.cookie;
    }

    SessionStoreDirectory getSessionStoreDirectory() {
        return this.sessionStoreDirectory;
    }

    public static enum SessionTrackingMode {
        COOKIE,
        URL,
        SSL;

    }

    public static class Cookie
    extends org.springframework.boot.web.server.Cookie {
        private String comment;

        public String getComment() {
            return this.comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}

