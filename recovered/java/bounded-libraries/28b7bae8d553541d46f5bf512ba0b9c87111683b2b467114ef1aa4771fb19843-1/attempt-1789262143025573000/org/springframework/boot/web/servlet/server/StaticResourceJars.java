/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;

class StaticResourceJars {
    StaticResourceJars() {
    }

    List<URL> getUrls() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            return this.getUrlsFrom(((URLClassLoader)classLoader).getURLs());
        }
        return this.getUrlsFrom((URL[])Stream.of(ManagementFactory.getRuntimeMXBean().getClassPath().split(File.pathSeparator)).map(this::toUrl).toArray(URL[]::new));
    }

    List<URL> getUrlsFrom(URL ... urls) {
        ArrayList<URL> resourceJarUrls = new ArrayList<URL>();
        for (URL url : urls) {
            this.addUrl(resourceJarUrls, url);
        }
        return resourceJarUrls;
    }

    private URL toUrl(String classPathEntry) {
        try {
            return new File(classPathEntry).toURI().toURL();
        }
        catch (MalformedURLException ex) {
            throw new IllegalArgumentException("URL could not be created from '" + classPathEntry + "'", ex);
        }
    }

    private File toFile(URL url) {
        try {
            return new File(url.toURI());
        }
        catch (URISyntaxException ex) {
            throw new IllegalStateException("Failed to create File from URL '" + url + "'");
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private void addUrl(List<URL> urls, URL url) {
        try {
            if (!"file".equals(url.getProtocol())) {
                this.addUrlConnection(urls, url, url.openConnection());
            } else {
                File file = this.toFile(url);
                if (file != null) {
                    this.addUrlFile(urls, url, file);
                } else {
                    this.addUrlConnection(urls, url, url.openConnection());
                }
            }
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private void addUrlFile(List<URL> urls, URL url, File file) {
        if (file.isDirectory() && new File(file, "META-INF/resources").isDirectory() || this.isResourcesJar(file)) {
            urls.add(url);
        }
    }

    private void addUrlConnection(List<URL> urls, URL url, URLConnection connection) {
        if (connection instanceof JarURLConnection && this.isResourcesJar((JarURLConnection)connection)) {
            urls.add(url);
        }
    }

    private boolean isResourcesJar(JarURLConnection connection) {
        try {
            return this.isResourcesJar(connection.getJarFile());
        }
        catch (IOException ex) {
            return false;
        }
    }

    private boolean isResourcesJar(File file) {
        try {
            return this.isResourcesJar(new JarFile(file));
        }
        catch (IOException | InvalidPathException ex) {
            return false;
        }
    }

    private boolean isResourcesJar(JarFile jar) throws IOException {
        try {
            boolean bl = jar.getName().endsWith(".jar") && jar.getJarEntry("META-INF/resources") != null;
            return bl;
        }
        finally {
            jar.close();
        }
    }
}

