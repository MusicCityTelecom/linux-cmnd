/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.UrlResource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.annotation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;

public final class ImportCandidates
implements Iterable<String> {
    private static final String LOCATION = "META-INF/spring/%s.imports";
    private static final String COMMENT_START = "#";
    private final List<String> candidates;

    private ImportCandidates(List<String> candidates) {
        Assert.notNull(candidates, (String)"'candidates' must not be null");
        this.candidates = Collections.unmodifiableList(candidates);
    }

    @Override
    public Iterator<String> iterator() {
        return this.candidates.iterator();
    }

    public static ImportCandidates load(Class<?> annotation, ClassLoader classLoader) {
        Assert.notNull(annotation, (String)"'annotation' must not be null");
        ClassLoader classLoaderToUse = ImportCandidates.decideClassloader(classLoader);
        String location = String.format(LOCATION, annotation.getName());
        Enumeration<URL> urls = ImportCandidates.findUrlsInClasspath(classLoaderToUse, location);
        ArrayList<String> autoConfigurations = new ArrayList<String>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            autoConfigurations.addAll(ImportCandidates.readAutoConfigurations(url));
        }
        return new ImportCandidates(autoConfigurations);
    }

    private static ClassLoader decideClassloader(ClassLoader classLoader) {
        if (classLoader == null) {
            return ImportCandidates.class.getClassLoader();
        }
        return classLoader;
    }

    private static Enumeration<URL> findUrlsInClasspath(ClassLoader classLoader, String location) {
        try {
            return classLoader.getResources(location);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Failed to load autoconfigurations from location [" + location + "]", ex);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<String> readAutoConfigurations(URL url) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new UrlResource(url).getInputStream(), StandardCharsets.UTF_8));){
            String line;
            ArrayList<String> autoConfigurations = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                line = ImportCandidates.stripComment(line);
                if ((line = line.trim()).isEmpty()) continue;
                autoConfigurations.add(line);
            }
            ArrayList<String> arrayList = autoConfigurations;
            return arrayList;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load autoconfigurations from location [" + url + "]", ex);
        }
    }

    private static String stripComment(String line) {
        int commentStart = line.indexOf(COMMENT_START);
        if (commentStart == -1) {
            return line;
        }
        return line.substring(0, commentStart);
    }
}

