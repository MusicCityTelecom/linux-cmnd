/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 */
package org.glassfish.hk2.osgiresourcelocator;

import org.glassfish.hk2.osgiresourcelocator.ResourceFinder;
import org.glassfish.hk2.osgiresourcelocator.ResourceFinderImpl;
import org.glassfish.hk2.osgiresourcelocator.ServiceLoader;
import org.glassfish.hk2.osgiresourcelocator.ServiceLoaderImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator
implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        ServiceLoaderImpl serviceLoader = new ServiceLoaderImpl();
        serviceLoader.trackBundles();
        ServiceLoader.initialize(serviceLoader);
        ResourceFinderImpl resourceFinder = new ResourceFinderImpl();
        ResourceFinder.initialize(resourceFinder);
    }

    public void stop(BundleContext context) throws Exception {
        ServiceLoader.reset();
    }
}

