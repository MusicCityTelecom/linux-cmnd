/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.servlet.UndertowServletLogger
 *  io.undertow.servlet.api.SessionPersistenceManager
 *  io.undertow.servlet.api.SessionPersistenceManager$PersistentSession
 *  org.springframework.core.ConfigurableObjectInputStream
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.servlet.UndertowServletLogger;
import io.undertow.servlet.api.SessionPersistenceManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.core.ConfigurableObjectInputStream;

class FileSessionPersistence
implements SessionPersistenceManager {
    private final File dir;

    FileSessionPersistence(File dir) {
        this.dir = dir;
    }

    public void persistSessions(String deploymentName, Map<String, SessionPersistenceManager.PersistentSession> sessionData) {
        try {
            this.save(sessionData, this.getSessionFile(deploymentName));
        }
        catch (Exception ex) {
            UndertowServletLogger.ROOT_LOGGER.failedToPersistSessions(ex);
        }
    }

    private void save(Map<String, SessionPersistenceManager.PersistentSession> sessionData, File file) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));){
            this.save(sessionData, stream);
        }
    }

    private void save(Map<String, SessionPersistenceManager.PersistentSession> sessionData, ObjectOutputStream stream) throws IOException {
        LinkedHashMap session = new LinkedHashMap();
        sessionData.forEach((key, value) -> {
            Serializable cfr_ignored_0 = session.put(key, new SerializablePersistentSession((SessionPersistenceManager.PersistentSession)value));
        });
        stream.writeObject(session);
    }

    public Map<String, SessionPersistenceManager.PersistentSession> loadSessionAttributes(String deploymentName, ClassLoader classLoader) {
        try {
            File file = this.getSessionFile(deploymentName);
            if (file.exists()) {
                return this.load(file, classLoader);
            }
        }
        catch (Exception ex) {
            UndertowServletLogger.ROOT_LOGGER.failedtoLoadPersistentSessions(ex);
        }
        return null;
    }

    private Map<String, SessionPersistenceManager.PersistentSession> load(File file, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        try (ConfigurableObjectInputStream stream = new ConfigurableObjectInputStream((InputStream)new FileInputStream(file), classLoader);){
            Map<String, SessionPersistenceManager.PersistentSession> map = this.load((ObjectInputStream)stream);
            return map;
        }
    }

    private Map<String, SessionPersistenceManager.PersistentSession> load(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        Map<String, SerializablePersistentSession> session = this.readSession(stream);
        long time = System.currentTimeMillis();
        LinkedHashMap<String, SessionPersistenceManager.PersistentSession> result = new LinkedHashMap<String, SessionPersistenceManager.PersistentSession>();
        session.forEach((key, value) -> {
            SessionPersistenceManager.PersistentSession entrySession = value.getPersistentSession();
            if (entrySession.getExpiration().getTime() > time) {
                result.put((String)key, entrySession);
            }
        });
        return result;
    }

    private Map<String, SerializablePersistentSession> readSession(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        return (Map)stream.readObject();
    }

    private File getSessionFile(String deploymentName) {
        if (!this.dir.exists()) {
            this.dir.mkdirs();
        }
        return new File(this.dir, deploymentName + ".session");
    }

    public void clear(String deploymentName) {
        this.getSessionFile(deploymentName).delete();
    }

    static class SerializablePersistentSession
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Date expiration;
        private final Map<String, Object> sessionData;

        SerializablePersistentSession(SessionPersistenceManager.PersistentSession session) {
            this.expiration = session.getExpiration();
            this.sessionData = new LinkedHashMap<String, Object>(session.getSessionData());
        }

        SessionPersistenceManager.PersistentSession getPersistentSession() {
            return new SessionPersistenceManager.PersistentSession(this.expiration, this.sessionData);
        }
    }
}

