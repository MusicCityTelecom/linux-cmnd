/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.osgiresourcelocator;

import java.net.URL;
import java.util.List;

public abstract class ResourceFinder {
    private static ResourceFinder _me;

    public static void initialize(ResourceFinder singleton) {
        if (singleton == null) {
            throw new NullPointerException("Did you intend to call reset()?");
        }
        if (_me != null) {
            throw new IllegalStateException("Already initialzed with [" + _me + "]");
        }
        _me = singleton;
    }

    public static synchronized void reset() {
        if (_me == null) {
            throw new IllegalStateException("Not yet initialized");
        }
        _me = null;
    }

    public static URL findEntry(String path) {
        if (_me == null) {
            return null;
        }
        return _me.findEntry1(path);
    }

    public static List<URL> findEntries(String path) {
        if (_me == null) {
            return null;
        }
        return _me.findEntries1(path);
    }

    abstract URL findEntry1(String var1);

    abstract List<URL> findEntries1(String var1);
}

