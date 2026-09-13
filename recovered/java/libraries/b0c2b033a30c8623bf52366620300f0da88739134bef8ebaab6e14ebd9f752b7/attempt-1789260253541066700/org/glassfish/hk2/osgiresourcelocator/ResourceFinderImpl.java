/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleReference
 */
package org.glassfish.hk2.osgiresourcelocator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.osgiresourcelocator.ResourceFinder;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

public class ResourceFinderImpl
extends ResourceFinder {
    private BundleContext bundleContext;

    public ResourceFinderImpl() {
        ClassLoader cl = this.getClass().getClassLoader();
        if (cl instanceof BundleReference) {
            this.bundleContext = ((BundleReference)BundleReference.class.cast(cl)).getBundle().getBundleContext();
        }
        if (this.bundleContext == null) {
            throw new RuntimeException("There is no bundle context available yet. Instatiate this class in STARTING or ACTIVE state only");
        }
    }

    @Override
    URL findEntry1(String path) {
        for (Bundle bundle : this.bundleContext.getBundles()) {
            URL url = bundle.getEntry(path);
            if (url == null) continue;
            return url;
        }
        return null;
    }

    @Override
    List<URL> findEntries1(String path) {
        ArrayList<URL> urls = new ArrayList<URL>();
        for (Bundle bundle : this.bundleContext.getBundles()) {
            URL url = bundle.getEntry(path);
            if (url == null) continue;
            urls.add(url);
        }
        return urls;
    }
}

