/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.logging.Log;

class DocumentRoot {
    private static final String[] COMMON_DOC_ROOTS = new String[]{"src/main/webapp", "public", "static"};
    private final Log logger;
    private File directory;

    DocumentRoot(Log logger) {
        this.logger = logger;
    }

    File getDirectory() {
        return this.directory;
    }

    void setDirectory(File directory) {
        this.directory = directory;
    }

    final File getValidDirectory() {
        File file = this.directory;
        file = file != null ? file : this.getWarFileDocumentRoot();
        file = file != null ? file : this.getExplodedWarFileDocumentRoot();
        File file2 = file = file != null ? file : this.getCommonDocumentRoot();
        if (file == null && this.logger.isDebugEnabled()) {
            this.logNoDocumentRoots();
        } else if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Document root: " + file));
        }
        return file;
    }

    private File getWarFileDocumentRoot() {
        return this.getArchiveFileDocumentRoot(".war");
    }

    private File getArchiveFileDocumentRoot(String extension) {
        File file = this.getCodeSourceArchive();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Code archive: " + file));
        }
        if (file != null && file.exists() && !file.isDirectory() && file.getName().toLowerCase(Locale.ENGLISH).endsWith(extension)) {
            return file.getAbsoluteFile();
        }
        return null;
    }

    private File getExplodedWarFileDocumentRoot() {
        return this.getExplodedWarFileDocumentRoot(this.getCodeSourceArchive());
    }

    private File getCodeSourceArchive() {
        return this.getCodeSourceArchive(this.getClass().getProtectionDomain().getCodeSource());
    }

    File getCodeSourceArchive(CodeSource codeSource) {
        try {
            URL location;
            URL uRL = location = codeSource != null ? codeSource.getLocation() : null;
            if (location == null) {
                return null;
            }
            URLConnection connection = location.openConnection();
            String path = connection instanceof JarURLConnection ? ((JarURLConnection)connection).getJarFile().getName() : location.toURI().getPath();
            int index = path.indexOf("!/");
            if (index != -1) {
                path = path.substring(0, index);
            }
            return new File(path);
        }
        catch (Exception ex) {
            return null;
        }
    }

    final File getExplodedWarFileDocumentRoot(File codeSourceFile) {
        String path;
        int webInfPathIndex;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Code archive: " + codeSourceFile));
        }
        if (codeSourceFile != null && codeSourceFile.exists() && (webInfPathIndex = (path = codeSourceFile.getAbsolutePath()).indexOf(File.separatorChar + "WEB-INF" + File.separatorChar)) >= 0) {
            path = path.substring(0, webInfPathIndex);
            return new File(path);
        }
        return null;
    }

    private File getCommonDocumentRoot() {
        for (String commonDocRoot : COMMON_DOC_ROOTS) {
            File root = new File(commonDocRoot);
            if (!root.exists() || !root.isDirectory()) continue;
            return root.getAbsoluteFile();
        }
        return null;
    }

    private void logNoDocumentRoots() {
        this.logger.debug((Object)("None of the document roots " + Arrays.asList(COMMON_DOC_ROOTS) + " point to a directory and will be ignored."));
    }
}

