/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.groovy.plugin.DefaultRunners;
import org.apache.groovy.plugin.GroovyRunner;

public class GroovyRunnerRegistry
implements Map<String, GroovyRunner>,
Iterable<GroovyRunner> {
    private static final GroovyRunnerRegistry INSTANCE = new GroovyRunnerRegistry();
    private static final Logger LOG = Logger.getLogger(GroovyRunnerRegistry.class.getName());
    private volatile Map<String, GroovyRunner> runnerMap;
    private volatile List<GroovyRunner> cachedValues;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = this.rwLock.readLock();
    private final Lock writeLock = this.rwLock.writeLock();

    public static GroovyRunnerRegistry getInstance() {
        return INSTANCE;
    }

    GroovyRunnerRegistry(Map<? extends String, ? extends GroovyRunner> runners) {
        this.runnerMap = new LinkedHashMap<String, GroovyRunner>();
        this.putAll(runners);
    }

    private GroovyRunnerRegistry() {
    }

    private Map<String, GroovyRunner> getMap() {
        Map<String, GroovyRunner> map = this.runnerMap;
        if (map == null) {
            this.writeLock.lock();
            try {
                map = this.runnerMap;
                if (map == null) {
                    this.runnerMap = map = new LinkedHashMap<String, GroovyRunner>();
                    this.load(null);
                }
            }
            finally {
                this.writeLock.unlock();
            }
        }
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load(ClassLoader classLoader) {
        Map<String, GroovyRunner> map = this.runnerMap;
        if (map == null) {
            map = this.getMap();
            if (classLoader == null) {
                return;
            }
        }
        this.writeLock.lock();
        try {
            if (classLoader == null) {
                classLoader = Thread.currentThread().getContextClassLoader();
            }
            this.cachedValues = null;
            this.loadDefaultRunners();
            this.loadWithLock(classLoader);
        }
        catch (SecurityException se) {
            LOG.log(Level.WARNING, "Failed to get the context ClassLoader", se);
        }
        catch (ServiceConfigurationError sce) {
            LOG.log(Level.WARNING, "Failed to load GroovyRunner services from ClassLoader " + classLoader, sce);
        }
        finally {
            this.writeLock.unlock();
        }
    }

    private void loadDefaultRunners() {
        this.register(DefaultRunners.junit3TestRunner());
        this.register(DefaultRunners.junit3SuiteRunner());
        this.register(DefaultRunners.junit4TestRunner());
    }

    private void loadWithLock(ClassLoader classLoader) {
        ServiceLoader<GroovyRunner> serviceLoader = ServiceLoader.load(GroovyRunner.class, classLoader);
        for (GroovyRunner runner : serviceLoader) {
            this.register(runner);
        }
    }

    private void register(GroovyRunner runner) {
        this.put(runner.getClass().getName(), runner);
    }

    @Override
    public Iterator<GroovyRunner> iterator() {
        return this.values().iterator();
    }

    @Override
    public int size() {
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            int n = map.size();
            return n;
        }
        finally {
            this.readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            boolean bl = map.isEmpty();
            return bl;
        }
        finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            boolean bl = map.containsKey(key);
            return bl;
        }
        finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean containsValue(Object runner) {
        if (runner == null) {
            return false;
        }
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            boolean bl = map.containsValue(runner);
            return bl;
        }
        finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public GroovyRunner get(Object key) {
        if (key == null) {
            return null;
        }
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            GroovyRunner groovyRunner = map.get(key);
            return groovyRunner;
        }
        finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public GroovyRunner put(String key, GroovyRunner runner) {
        if (key == null || runner == null) {
            return null;
        }
        Map<String, GroovyRunner> map = this.getMap();
        this.writeLock.lock();
        try {
            this.cachedValues = null;
            GroovyRunner groovyRunner = map.put(key, runner);
            return groovyRunner;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public GroovyRunner remove(Object key) {
        if (key == null) {
            return null;
        }
        Map<String, GroovyRunner> map = this.getMap();
        this.writeLock.lock();
        try {
            this.cachedValues = null;
            GroovyRunner groovyRunner = map.remove(key);
            return groovyRunner;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void putAll(Map<? extends String, ? extends GroovyRunner> m) {
        Map<String, GroovyRunner> map = this.getMap();
        this.writeLock.lock();
        try {
            this.cachedValues = null;
            for (Map.Entry<? extends String, ? extends GroovyRunner> entry : m.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) continue;
                map.put(entry.getKey(), entry.getValue());
            }
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        Map<String, GroovyRunner> map = this.getMap();
        this.writeLock.lock();
        try {
            this.cachedValues = null;
            map.clear();
            this.loadDefaultRunners();
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public Set<String> keySet() {
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            if (map.isEmpty()) {
                Set<String> set = Collections.emptySet();
                return set;
            }
            Set<String> set = Collections.unmodifiableSet(new LinkedHashSet<String>(map.keySet()));
            return set;
        }
        finally {
            this.readLock.unlock();
        }
    }

    @Override
    public Collection<GroovyRunner> values() {
        List<GroovyRunner> values = this.cachedValues;
        if (values == null) {
            Map<String, GroovyRunner> map = this.getMap();
            this.readLock.lock();
            try {
                values = this.cachedValues;
                if (values == null) {
                    this.cachedValues = values = Collections.unmodifiableList(new ArrayList<GroovyRunner>(map.values()));
                }
            }
            finally {
                this.readLock.unlock();
            }
        }
        return values;
    }

    @Override
    public Set<Map.Entry<String, GroovyRunner>> entrySet() {
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            if (map.isEmpty()) {
                Set<Map.Entry<String, GroovyRunner>> set = Collections.emptySet();
                return set;
            }
            Set<Map.Entry<String, GroovyRunner>> set = Collections.unmodifiableSet(new LinkedHashSet<Map.Entry<String, GroovyRunner>>(map.entrySet()));
            return set;
        }
        finally {
            this.readLock.unlock();
        }
    }

    public String toString() {
        Map<String, GroovyRunner> map = this.getMap();
        this.readLock.lock();
        try {
            String string = map.toString();
            return string;
        }
        finally {
            this.readLock.unlock();
        }
    }
}

