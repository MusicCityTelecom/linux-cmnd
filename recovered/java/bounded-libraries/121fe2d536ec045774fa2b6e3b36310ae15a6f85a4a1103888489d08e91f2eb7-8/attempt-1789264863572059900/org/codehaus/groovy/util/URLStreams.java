/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLStreams {
    private URLStreams() {
    }

    public static InputStream openUncachedStream(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.setUseCaches(false);
        return urlConnection.getInputStream();
    }
}

