/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.boot.archive.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import org.hibernate.boot.archive.spi.AbstractArchiveDescriptor;
import org.hibernate.boot.archive.spi.ArchiveContext;
import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;
import org.hibernate.boot.archive.spi.ArchiveEntry;
import org.hibernate.boot.archive.spi.ArchiveEntryHandler;
import org.hibernate.boot.archive.spi.ArchiveException;
import org.hibernate.boot.archive.spi.InputStreamAccess;
import org.hibernate.internal.log.UrlMessageBundle;

public class JarFileBasedArchiveDescriptor
extends AbstractArchiveDescriptor {
    public JarFileBasedArchiveDescriptor(ArchiveDescriptorFactory archiveDescriptorFactory, URL archiveUrl, String entry) {
        super(archiveDescriptorFactory, archiveUrl, entry);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void visitArchive(ArchiveContext context) {
        JarFile jarFile = this.resolveJarFileReference();
        if (jarFile == null) {
            return;
        }
        try {
            Enumeration<JarEntry> zipEntries = jarFile.entries();
            while (zipEntries.hasMoreElements()) {
                InputStreamAccess inputStreamAccess;
                Object subZipEntry2;
                ZipEntry zipEntry = zipEntries.nextElement();
                String entryName = this.extractName(zipEntry);
                if (this.getEntryBasePrefix() != null && !entryName.startsWith(this.getEntryBasePrefix()) || zipEntry.isDirectory()) continue;
                if (entryName.equals(this.getEntryBasePrefix())) {
                    try {
                        BufferedInputStream is = new BufferedInputStream(jarFile.getInputStream(zipEntry));
                        Throwable throwable = null;
                        try {
                            JarInputStream jarInputStream = new JarInputStream(is);
                            Throwable throwable2 = null;
                            try {
                                subZipEntry2 = jarInputStream.getNextEntry();
                                while (subZipEntry2 != null) {
                                    if (!((ZipEntry)subZipEntry2).isDirectory()) {
                                        final String name = this.extractName((ZipEntry)subZipEntry2);
                                        final String relativeName = this.extractRelativeName((ZipEntry)subZipEntry2);
                                        final InputStreamAccess inputStreamAccess2 = this.buildByteBasedInputStreamAccess(name, jarInputStream);
                                        ArchiveEntry entry = new ArchiveEntry(){

                                            @Override
                                            public String getName() {
                                                return name;
                                            }

                                            @Override
                                            public String getNameWithinArchive() {
                                                return relativeName;
                                            }

                                            @Override
                                            public InputStreamAccess getStreamAccess() {
                                                return inputStreamAccess2;
                                            }
                                        };
                                        ArchiveEntryHandler entryHandler = context.obtainArchiveEntryHandler(entry);
                                        entryHandler.handleEntry(entry, context);
                                    }
                                    subZipEntry2 = jarInputStream.getNextEntry();
                                }
                                continue;
                            }
                            catch (Throwable subZipEntry2) {
                                throwable2 = subZipEntry2;
                                throw subZipEntry2;
                            }
                            finally {
                                if (jarInputStream == null) continue;
                                if (throwable2 != null) {
                                    try {
                                        jarInputStream.close();
                                    }
                                    catch (Throwable subZipEntry2) {
                                        throwable2.addSuppressed(subZipEntry2);
                                    }
                                    continue;
                                }
                                jarInputStream.close();
                                continue;
                            }
                        }
                        catch (Throwable jarInputStream) {
                            throwable = jarInputStream;
                            throw jarInputStream;
                        }
                        finally {
                            if (is == null) continue;
                            if (throwable != null) {
                                try {
                                    ((InputStream)is).close();
                                }
                                catch (Throwable jarInputStream) {
                                    throwable.addSuppressed(jarInputStream);
                                }
                                continue;
                            }
                            ((InputStream)is).close();
                            continue;
                        }
                    }
                    catch (Exception e) {
                        throw new ArchiveException("Error accessing JarFile entry [" + zipEntry.getName() + "]", e);
                    }
                }
                final String name = this.extractName(zipEntry);
                final String relativeName = this.extractRelativeName(zipEntry);
                try {
                    InputStream is = jarFile.getInputStream(zipEntry);
                    subZipEntry2 = null;
                    try {
                        inputStreamAccess = this.buildByteBasedInputStreamAccess(name, is);
                    }
                    catch (Throwable throwable) {
                        subZipEntry2 = throwable;
                        throw throwable;
                    }
                    finally {
                        if (is != null) {
                            if (subZipEntry2 != null) {
                                try {
                                    is.close();
                                }
                                catch (Throwable throwable) {
                                    ((Throwable)subZipEntry2).addSuppressed(throwable);
                                }
                            } else {
                                is.close();
                            }
                        }
                    }
                }
                catch (IOException e) {
                    throw new ArchiveException(String.format("Unable to access stream from jar file [%s] for entry [%s]", jarFile.getName(), zipEntry.getName()));
                }
                ArchiveEntry entry = new ArchiveEntry(){

                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public String getNameWithinArchive() {
                        return relativeName;
                    }

                    @Override
                    public InputStreamAccess getStreamAccess() {
                        return inputStreamAccess;
                    }
                };
                ArchiveEntryHandler entryHandler = context.obtainArchiveEntryHandler(entry);
                entryHandler.handleEntry(entry, context);
            }
        }
        finally {
            try {
                jarFile.close();
            }
            catch (Exception exception) {}
        }
    }

    private JarFile resolveJarFileReference() {
        try {
            String filePart = this.getArchiveUrl().getFile();
            if (filePart != null && filePart.indexOf(32) != -1) {
                return new JarFile(this.getArchiveUrl().getFile());
            }
            return new JarFile(this.getArchiveUrl().toURI().getSchemeSpecificPart());
        }
        catch (IOException e) {
            UrlMessageBundle.URL_LOGGER.logUnableToFindFileByUrl(this.getArchiveUrl(), e);
        }
        catch (URISyntaxException e) {
            UrlMessageBundle.URL_LOGGER.logMalformedUrl(this.getArchiveUrl(), e);
        }
        return null;
    }
}

