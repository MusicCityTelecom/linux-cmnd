/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.tools.build.bundletool.io.AutoValue_ZipBuilder_Entry;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.ByteStreams;
import com.google.errorprone.annotations.Immutable;
import com.google.protobuf.MessageLite;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class ZipBuilder {
    private static final Long EPOCH = 0L;
    private final Map<ZipPath, Entry> entries = new LinkedHashMap<ZipPath, Entry>();

    public synchronized Path writeTo(Path target) throws IOException {
        Path tempFile = Files.createTempFile("ZipBuilder-", ".zip.tmp", new FileAttribute[0]);
        try {
            try (OutputStream out = BufferedIo.outputStream(tempFile);
                 ZipOutputStream outZip = new ZipOutputStream(out);){
                for (ZipPath path : this.entries.keySet()) {
                    ZipEntry zipEntry;
                    Entry entry = this.entries.get(path);
                    if (entry.getIsDirectory()) {
                        zipEntry = new ZipEntry(path + "/");
                        zipEntry.setTime(EPOCH);
                        outZip.putNextEntry(zipEntry);
                    } else {
                        Throwable throwable;
                        zipEntry = new ZipEntry(path.toString());
                        zipEntry.setTime(EPOCH);
                        if (entry.hasOption(EntryOption.UNCOMPRESSED)) {
                            zipEntry.setMethod(0);
                            throwable = null;
                            try (InputStream is = entry.getInputStreamSupplier().get().get();){
                                byte[] entryData = ByteStreams.toByteArray(is);
                                zipEntry.setSize(entryData.length);
                                zipEntry.setCompressedSize(entryData.length);
                                zipEntry.setCrc(ZipBuilder.computeCrc32(entryData));
                                outZip.putNextEntry(zipEntry);
                                outZip.write(entryData);
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                        }
                        outZip.putNextEntry(zipEntry);
                        throwable = null;
                        try (InputStream content = entry.getInputStreamSupplier().get().get();){
                            ByteStreams.copy(content, outZip);
                        }
                        catch (Throwable throwable3) {
                            throwable = throwable3;
                            throw throwable3;
                        }
                    }
                    outZip.closeEntry();
                }
            }
            Files.move(tempFile, target, new CopyOption[0]);
        }
        catch (IOException e2) {
            Files.deleteIfExists(tempFile);
            throw e2;
        }
        return target;
    }

    public ZipBuilder addFileWithContent(ZipPath toPath, byte[] content, EntryOption ... options) {
        return this.addFile(toPath, () -> new ByteArrayInputStream(content), options);
    }

    public ZipBuilder addFileFromDisk(ZipPath toPath, File file, EntryOption ... options) {
        Preconditions.checkArgument(file.isFile(), "Path '%s' does not denote a file.", (Object)file);
        return this.addFile(toPath, BufferedIo.inputStreamSupplier(file.toPath()), options);
    }

    public ZipBuilder addFileWithProtoContent(ZipPath toPath, MessageLite protoMsg, EntryOption ... options) {
        return this.addFile(toPath, () -> new ByteArrayInputStream(protoMsg.toByteArray()), options);
    }

    public ZipBuilder addFileFromZip(ZipPath toPath, ZipFile fromZipFile, ZipEntry zipEntry, EntryOption ... options) {
        return this.addFile(toPath, BufferedIo.inputStreamSupplier(fromZipFile, zipEntry), options);
    }

    public ZipBuilder addFileFromZipPreservingCompression(ZipPath toPath, ZipFile fromZipFile, ZipEntry zipEntry) {
        EntryOption[] entryOptionArray;
        if (zipEntry.getMethod() == 0) {
            EntryOption[] entryOptionArray2 = new EntryOption[1];
            entryOptionArray = entryOptionArray2;
            entryOptionArray2[0] = EntryOption.UNCOMPRESSED;
        } else {
            entryOptionArray = new EntryOption[]{};
        }
        EntryOption[] entryOption = entryOptionArray;
        return this.addFileFromZip(toPath, fromZipFile, zipEntry, entryOption);
    }

    public ZipBuilder addFile(ZipPath toPath, InputStreamSupplier inputStreamSupplier, EntryOption ... options) {
        return this.addEntryInternal(toPath, Entry.builder().setIsDirectory(false).setInputStreamSupplier(inputStreamSupplier).setOptions(ImmutableSet.copyOf(options)).build());
    }

    public ZipBuilder addDirectory(ZipPath dir) {
        return this.addEntryInternal(dir, Entry.builder().setIsDirectory(true).setOptions(ImmutableSet.of()).build());
    }

    private synchronized ZipBuilder addEntryInternal(ZipPath path, Entry entry) {
        Preconditions.checkArgument(this.entries.put(path, entry) == null, "Path '%s' is already taken.", (Object)path);
        return this;
    }

    public ZipBuilder copyAllContentsFromZip(ZipPath toDirectory, ZipFile srcZipFile, EntryOption ... entryOptions) {
        Enumeration<? extends ZipEntry> zipEntries = srcZipFile.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = zipEntries.nextElement();
            if (zipEntry.isDirectory()) continue;
            this.addFileFromZip(toDirectory.resolve(zipEntry.getName()), srcZipFile, zipEntry, entryOptions);
        }
        return this;
    }

    private static long computeCrc32(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static enum EntryOption {
        UNCOMPRESSED;

    }

    @Immutable
    @AutoValue
    @AutoValue.CopyAnnotations
    protected static abstract class Entry {
        protected Entry() {
        }

        public abstract Optional<InputStreamSupplier> getInputStreamSupplier();

        public abstract boolean getIsDirectory();

        public abstract ImmutableSet<EntryOption> getOptions();

        public static Builder builder() {
            return new AutoValue_ZipBuilder_Entry.Builder();
        }

        public boolean hasOption(EntryOption option) {
            return this.getOptions().contains((Object)option);
        }

        @AutoValue.Builder
        static abstract class Builder {
            Builder() {
            }

            public abstract Builder setInputStreamSupplier(InputStreamSupplier var1);

            public abstract Builder setIsDirectory(boolean var1);

            public abstract Builder setOptions(ImmutableSet<EntryOption> var1);

            public abstract Entry autoBuild();

            public Entry build() {
                Entry result = this.autoBuild();
                Preconditions.checkState(result.getInputStreamSupplier().isPresent() ^ result.getIsDirectory(), "Input stream supplier must be absent iff the entry is a directory.");
                return result;
            }
        }
    }
}

