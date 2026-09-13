/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import org.glassfish.hk2.api.DescriptorFileFinder;
import org.glassfish.hk2.api.DescriptorFileFinderInformation;
import org.glassfish.hk2.utilities.reflection.Logger;

public class ClasspathDescriptorFileFinder
implements DescriptorFileFinder,
DescriptorFileFinderInformation {
    private static final String DEBUG_DESCRIPTOR_FINDER_PROPERTY = "org.jvnet.hk2.properties.debug.descriptor.file.finder";
    private static final boolean DEBUG_DESCRIPTOR_FINDER = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

        @Override
        public Boolean run() {
            return Boolean.parseBoolean(System.getProperty(ClasspathDescriptorFileFinder.DEBUG_DESCRIPTOR_FINDER_PROPERTY, "false"));
        }
    });
    private static final String DEFAULT_NAME = "default";
    private final ClassLoader classLoader;
    private final String[] names;
    private final ArrayList<String> identifiers = new ArrayList();

    public ClasspathDescriptorFileFinder() {
        this(ClasspathDescriptorFileFinder.class.getClassLoader(), DEFAULT_NAME);
    }

    public ClasspathDescriptorFileFinder(ClassLoader cl) {
        this(cl, DEFAULT_NAME);
    }

    public ClasspathDescriptorFileFinder(ClassLoader cl, String ... names) {
        this.classLoader = cl;
        this.names = names;
    }

    @Override
    public List<InputStream> findDescriptorFiles() throws IOException {
        this.identifiers.clear();
        ArrayList<InputStream> returnList = new ArrayList<InputStream>();
        for (String name : this.names) {
            Enumeration<URL> e = this.classLoader.getResources("META-INF/hk2-locator/" + name);
            while (e.hasMoreElements()) {
                InputStream inputStream;
                URL url = e.nextElement();
                if (DEBUG_DESCRIPTOR_FINDER) {
                    Logger.getLogger().debug("Adding in URL to set being parsed: " + url + " from " + "META-INF/hk2-locator/" + name);
                }
                try {
                    this.identifiers.add(url.toURI().toString());
                }
                catch (URISyntaxException e1) {
                    throw new IOException(e1);
                }
                try {
                    inputStream = url.openStream();
                }
                catch (IOException ioe) {
                    if (DEBUG_DESCRIPTOR_FINDER) {
                        Logger.getLogger().debug("IOException for url " + url, ioe);
                    }
                    throw ioe;
                }
                catch (Throwable th) {
                    if (DEBUG_DESCRIPTOR_FINDER) {
                        Logger.getLogger().debug("Unexpected exception for url " + url, th);
                    }
                    throw new IOException(th);
                }
                if (DEBUG_DESCRIPTOR_FINDER) {
                    Logger.getLogger().debug("Input stream for: " + url + " from " + "META-INF/hk2-locator/" + name + " has succesfully been opened");
                }
                returnList.add(inputStream);
            }
        }
        return returnList;
    }

    @Override
    public List<String> getDescriptorFileInformation() {
        return this.identifiers;
    }

    public String toString() {
        return "ClasspathDescriptorFileFinder(" + this.classLoader + "," + Arrays.toString(this.names) + "," + System.identityHashCode(this) + ")";
    }
}

