/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.util.Assert
 *  org.springframework.util.DefaultPropertiesPersister
 */
package org.springframework.integration.metadata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.metadata.ConcurrentMetadataStore;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.util.Assert;
import org.springframework.util.DefaultPropertiesPersister;

public class PropertiesPersistingMetadataStore
implements ConcurrentMetadataStore,
InitializingBean,
DisposableBean,
Closeable,
Flushable {
    private static final String KEY_CANNOT_BE_NULL = "'key' cannot be null";
    private final Log logger = LogFactory.getLog(this.getClass());
    private final Properties metadata = new Properties();
    private final DefaultPropertiesPersister persister = new DefaultPropertiesPersister();
    private final LockRegistry lockRegistry = new DefaultLockRegistry();
    private String baseDirectory = System.getProperty("java.io.tmpdir") + "/spring-integration/";
    private String fileName = "metadata-store.properties";
    private File file;
    private volatile boolean dirty;

    public void setBaseDirectory(String baseDirectory) {
        Assert.hasText((String)baseDirectory, (String)"'baseDirectory' must be non-empty");
        this.baseDirectory = baseDirectory;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void afterPropertiesSet() {
        File baseDir = new File(this.baseDirectory);
        if (!baseDir.mkdirs() && this.logger.isWarnEnabled()) {
            this.logger.warn((Object)("Failed to create directories for " + baseDir));
        }
        this.file = new File(baseDir, this.fileName);
        try {
            if (!this.file.exists() && !this.file.createNewFile() && this.logger.isWarnEnabled()) {
                this.logger.warn((Object)("Failed to create file " + this.file));
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Failed to create metadata-store file '" + this.file.getAbsolutePath() + "'", e);
        }
        this.loadMetadata();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void put(String key, String value) {
        Assert.notNull((Object)key, (String)KEY_CANNOT_BE_NULL);
        Assert.notNull((Object)value, (String)"'value' cannot be null");
        Lock lock = this.lockRegistry.obtain(key);
        lock.lock();
        try {
            this.metadata.setProperty(key, value);
        }
        finally {
            this.dirty = true;
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String get(String key) {
        Assert.notNull((Object)key, (String)KEY_CANNOT_BE_NULL);
        Lock lock = this.lockRegistry.obtain(key);
        lock.lock();
        try {
            String string = this.metadata.getProperty(key);
            return string;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String remove(String key) {
        Assert.notNull((Object)key, (String)KEY_CANNOT_BE_NULL);
        Lock lock = this.lockRegistry.obtain(key);
        lock.lock();
        try {
            String string = (String)this.metadata.remove(key);
            return string;
        }
        finally {
            this.dirty = true;
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String putIfAbsent(String key, String value) {
        Assert.notNull((Object)key, (String)KEY_CANNOT_BE_NULL);
        Assert.notNull((Object)value, (String)"'value' cannot be null");
        Lock lock = this.lockRegistry.obtain(key);
        lock.lock();
        try {
            String property = this.metadata.getProperty(key);
            if (property == null) {
                this.metadata.setProperty(key, value);
                this.dirty = true;
                String string = null;
                return string;
            }
            String string = property;
            return string;
        }
        finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        Assert.notNull((Object)key, (String)KEY_CANNOT_BE_NULL);
        Assert.notNull((Object)oldValue, (String)"'oldValue' cannot be null");
        Assert.notNull((Object)newValue, (String)"'newValue' cannot be null");
        Lock lock = this.lockRegistry.obtain(key);
        lock.lock();
        try {
            String property = this.metadata.getProperty(key);
            if (oldValue.equals(property)) {
                this.metadata.setProperty(key, newValue);
                this.dirty = true;
                boolean bl = true;
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        this.flush();
    }

    @Override
    public void flush() {
        this.saveMetadata();
    }

    public void destroy() {
        this.flush();
    }

    private void saveMetadata() {
        if (this.file == null || !this.dirty) {
            return;
        }
        this.dirty = false;
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(this.file));
            this.persister.store(this.metadata, outputStream, "Last entry");
        }
        catch (IOException e) {
            this.logger.warn((Object)"Failed to persist entry. This may result in a duplicate entry after this component is restarted.", (Throwable)e);
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            catch (IOException e) {
                this.logger.warn((Object)("Failed to close OutputStream to " + this.file.getAbsolutePath()), (Throwable)e);
            }
        }
    }

    private void loadMetadata() {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(this.file));
            this.persister.load(this.metadata, inputStream);
        }
        catch (Exception e) {
            this.logger.warn((Object)"Failed to load entry from the persistent store. This may result in a duplicate entry after this component is restarted", (Throwable)e);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (Exception e2) {
                this.logger.warn((Object)("Failed to close InputStream for: " + this.file.getAbsolutePath()));
            }
        }
    }
}

