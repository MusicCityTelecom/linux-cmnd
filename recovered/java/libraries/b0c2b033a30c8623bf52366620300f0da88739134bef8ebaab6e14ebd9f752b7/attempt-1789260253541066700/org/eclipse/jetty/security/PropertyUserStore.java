/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.UserStore;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.PathWatcher;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Credential;

public class PropertyUserStore
extends UserStore
implements PathWatcher.Listener {
    private static final Logger LOG = Log.getLogger(PropertyUserStore.class);
    protected Path _configPath;
    protected PathWatcher _pathWatcher;
    protected boolean _hotReload = false;
    protected boolean _firstLoad = true;
    protected List<UserListener> _listeners;

    public String getConfig() {
        if (this._configPath != null) {
            return this._configPath.toString();
        }
        return null;
    }

    public void setConfig(String config) {
        block6: {
            if (config == null) {
                this._configPath = null;
                return;
            }
            try {
                Resource configResource = Resource.newResource(config);
                if (configResource instanceof JarFileResource) {
                    this._configPath = this.extractPackedFile((JarFileResource)configResource);
                    break block6;
                }
                if (configResource instanceof PathResource) {
                    this._configPath = ((PathResource)configResource).getPath();
                    break block6;
                }
                if (configResource.getFile() != null) {
                    this.setConfigFile(configResource.getFile());
                    break block6;
                }
                throw new IllegalArgumentException(config);
            }
            catch (Exception e) {
                this._configPath = null;
                throw new IllegalStateException(e);
            }
        }
    }

    public Path getConfigPath() {
        return this._configPath;
    }

    @Deprecated
    public void setConfigPath(String configFile) {
        this.setConfig(configFile);
    }

    private Path extractPackedFile(JarFileResource configResource) throws IOException {
        String uri = configResource.getURI().toASCIIString();
        int colon = uri.lastIndexOf(":");
        int bangSlash = uri.indexOf("!/");
        if (colon < 0 || bangSlash < 0 || colon > bangSlash) {
            throw new IllegalArgumentException("Not resolved JarFile resource: " + uri);
        }
        String entryPath = StringUtil.sanitizeFileSystemName(uri.substring(colon + 2));
        Path tmpDirectory = Files.createTempDirectory("users_store", new FileAttribute[0]);
        tmpDirectory.toFile().deleteOnExit();
        Path extractedPath = Paths.get(tmpDirectory.toString(), entryPath);
        Files.deleteIfExists(extractedPath);
        extractedPath.toFile().deleteOnExit();
        IO.copy(configResource.getInputStream(), new FileOutputStream(extractedPath.toFile()));
        if (this.isHotReload()) {
            LOG.warn("Cannot hot reload from packed configuration: {}", configResource);
            this.setHotReload(false);
        }
        return extractedPath;
    }

    @Deprecated
    public void setConfigPath(File configFile) {
        this.setConfigFile(configFile);
    }

    public void setConfigFile(File configFile) {
        this._configPath = configFile == null ? null : configFile.toPath();
    }

    public void setConfigPath(Path configPath) {
        this._configPath = configPath;
    }

    public Resource getConfigResource() {
        if (this._configPath == null) {
            return null;
        }
        return new PathResource(this._configPath);
    }

    public boolean isHotReload() {
        return this._hotReload;
    }

    public void setHotReload(boolean enable) {
        if (this.isRunning()) {
            throw new IllegalStateException("Cannot set hot reload while user store is running");
        }
        this._hotReload = enable;
    }

    @Override
    public String toString() {
        return String.format("%s@%x[users.count=%d,identityService=%s]", this.getClass().getSimpleName(), this.hashCode(), this.getKnownUserIdentities().size(), this.getIdentityService());
    }

    protected void loadUsers() throws IOException {
        Resource config;
        if (this._configPath == null) {
            throw new IllegalStateException("No config path set");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading {} from {}", this, this._configPath);
        }
        if (!(config = this.getConfigResource()).exists()) {
            throw new IllegalStateException("Config does not exist: " + config);
        }
        Properties properties = new Properties();
        properties.load(config.getInputStream());
        HashSet<String> known = new HashSet<String>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String username = ((String)entry.getKey()).trim();
            String credentials = ((String)entry.getValue()).trim();
            String roles = null;
            int c = credentials.indexOf(44);
            if (c >= 0) {
                roles = credentials.substring(c + 1).trim();
                credentials = credentials.substring(0, c).trim();
            }
            if (username.length() <= 0) continue;
            String[] roleArray = IdentityService.NO_ROLES;
            if (roles != null && roles.length() > 0) {
                roleArray = StringUtil.csvSplit(roles);
            }
            known.add(username);
            Credential credential = Credential.getCredential(credentials);
            this.addUser(username, credential, roleArray);
            this.notifyUpdate(username, credential, roleArray);
        }
        ArrayList<String> currentlyKnownUsers = new ArrayList<String>(this.getKnownUserIdentities().keySet());
        if (!this._firstLoad) {
            for (String user : currentlyKnownUsers) {
                if (known.contains(user)) continue;
                this.removeUser(user);
                this.notifyRemove(user);
            }
        }
        this._firstLoad = false;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded " + this + " from " + this._configPath, new Object[0]);
        }
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        this.loadUsers();
        if (this.isHotReload() && this._configPath != null) {
            this._pathWatcher = new PathWatcher();
            this._pathWatcher.watch(this._configPath);
            this._pathWatcher.addListener(this);
            this._pathWatcher.setNotifyExistingOnStart(false);
            this._pathWatcher.start();
        }
    }

    @Override
    public void onPathWatchEvent(PathWatcher.PathWatchEvent event) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Path watch event: {}", new Object[]{event.getType()});
            }
            this.loadUsers();
        }
        catch (IOException e) {
            LOG.warn(e);
        }
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        if (this._pathWatcher != null) {
            this._pathWatcher.stop();
        }
        this._pathWatcher = null;
    }

    private void notifyUpdate(String username, Credential credential, String[] roleArray) {
        if (this._listeners != null) {
            for (UserListener listener : this._listeners) {
                listener.update(username, credential, roleArray);
            }
        }
    }

    private void notifyRemove(String username) {
        if (this._listeners != null) {
            for (UserListener listener : this._listeners) {
                listener.remove(username);
            }
        }
    }

    public void registerUserListener(UserListener listener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList<UserListener>();
        }
        this._listeners.add(listener);
    }

    public static interface UserListener {
        public void update(String var1, Credential var2, String[] var3);

        public void remove(String var1);
    }
}

