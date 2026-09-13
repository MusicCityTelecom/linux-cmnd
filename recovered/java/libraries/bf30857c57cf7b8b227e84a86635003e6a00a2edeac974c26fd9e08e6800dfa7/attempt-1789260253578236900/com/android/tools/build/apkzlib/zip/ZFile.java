/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.utils.CachedFileContents;
import com.android.tools.build.apkzlib.utils.IOExceptionFunction;
import com.android.tools.build.apkzlib.utils.IOExceptionRunnable;
import com.android.tools.build.apkzlib.zip.AlignmentRule;
import com.android.tools.build.apkzlib.zip.CentralDirectory;
import com.android.tools.build.apkzlib.zip.CentralDirectoryHeader;
import com.android.tools.build.apkzlib.zip.CentralDirectoryHeaderCompressInfo;
import com.android.tools.build.apkzlib.zip.CompressionMethod;
import com.android.tools.build.apkzlib.zip.CompressionResult;
import com.android.tools.build.apkzlib.zip.Compressor;
import com.android.tools.build.apkzlib.zip.DataDescriptorType;
import com.android.tools.build.apkzlib.zip.EncodeUtils;
import com.android.tools.build.apkzlib.zip.Eocd;
import com.android.tools.build.apkzlib.zip.ExtraField;
import com.android.tools.build.apkzlib.zip.FileUseMap;
import com.android.tools.build.apkzlib.zip.FileUseMapEntry;
import com.android.tools.build.apkzlib.zip.GPFlags;
import com.android.tools.build.apkzlib.zip.InflaterByteSource;
import com.android.tools.build.apkzlib.zip.LazyDelegateByteSource;
import com.android.tools.build.apkzlib.zip.ProcessedAndRawByteSources;
import com.android.tools.build.apkzlib.zip.StoredEntry;
import com.android.tools.build.apkzlib.zip.VerifyLog;
import com.android.tools.build.apkzlib.zip.ZFileExtension;
import com.android.tools.build.apkzlib.zip.ZFileOptions;
import com.android.tools.build.apkzlib.zip.ZipFileState;
import com.android.tools.build.apkzlib.zip.compress.Zip64NotSupportedException;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.android.tools.build.apkzlib.zip.utils.LittleEndianUtils;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;
import com.google.common.io.Closer;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Nullable;

public class ZFile
implements Closeable {
    public static final char SEPARATOR = '/';
    private static final int MIN_EOCD_SIZE = 22;
    private static final int ZIP64_EOCD_LOCATOR_SIZE = 20;
    private static final int MAX_EOCD_COMMENT_SIZE = 65535;
    private static final int LAST_BYTES_TO_READ = 65557;
    private static final int ZIP64_EOCD_LOCATOR_SIGNATURE = 117853008;
    private static final byte[] EOCD_SIGNATURE = new byte[]{6, 5, 75, 80};
    private static final int IO_BUFFER_SIZE = 0x100000;
    private static final int MAXIMUM_EXTENSION_CYCLE_COUNT = 10;
    private static final int MINIMUM_EXTRA_FIELD_SIZE = 6;
    private static final int MAX_LOCAL_EXTRA_FIELD_CONTENTS_SIZE = Short.MAX_VALUE;
    private final File file;
    @Nullable
    private RandomAccessFile raf;
    private final FileUseMap map;
    @Nullable
    private FileUseMapEntry<Eocd> eocdEntry;
    @Nullable
    private FileUseMapEntry<CentralDirectory> directoryEntry;
    private final Map<String, FileUseMapEntry<StoredEntry>> entries;
    private final List<StoredEntry> uncompressedEntries;
    private ZipFileState state;
    private boolean dirty;
    @Nullable
    private CachedFileContents<Object> closedControl;
    private final AlignmentRule alignmentRule;
    private final List<ZFileExtension> extensions;
    private final List<IOExceptionRunnable> toRun;
    private boolean isNotifying;
    private long extraDirectoryOffset;
    private boolean noTimestamps;
    private final Compressor compressor;
    private final ByteStorage storage;
    private boolean coverEmptySpaceUsingExtraField;
    private boolean autoSortFiles;
    private final Supplier<VerifyLog> verifyLogFactory;
    private final VerifyLog verifyLog;
    @Nullable
    private byte[] eocdComment;
    private boolean readOnly;

    @Deprecated
    public ZFile(File file) throws IOException {
        this(file, new ZFileOptions());
    }

    @Deprecated
    public ZFile(File file, ZFileOptions options) throws IOException {
        this(file, options, false);
    }

    @Deprecated
    public ZFile(File file, ZFileOptions options, boolean readOnly) throws IOException {
        this.file = file;
        this.map = new FileUseMap(0L, options.getCoverEmptySpaceUsingExtraField() ? 6 : 0);
        this.readOnly = readOnly;
        this.dirty = false;
        this.closedControl = null;
        this.alignmentRule = options.getAlignmentRule();
        this.extensions = Lists.newArrayList();
        this.toRun = Lists.newArrayList();
        this.noTimestamps = options.getNoTimestamps();
        this.storage = options.getStorageFactory().create();
        this.compressor = options.getCompressor();
        this.coverEmptySpaceUsingExtraField = options.getCoverEmptySpaceUsingExtraField();
        this.autoSortFiles = options.getAutoSortFiles();
        this.verifyLogFactory = options.getVerifyLogFactory();
        this.verifyLog = this.verifyLogFactory.get();
        this.state = ZipFileState.CLOSED;
        this.raf = null;
        if (file.exists()) {
            this.openReadOnlyIfClosed();
        } else {
            if (readOnly) {
                throw new IOException("File does not exist but read-only mode requested");
            }
            this.dirty = true;
        }
        this.entries = Maps.newHashMap();
        this.uncompressedEntries = Lists.newArrayList();
        this.extraDirectoryOffset = 0L;
        try {
            if (this.state != ZipFileState.CLOSED) {
                long MAX_ENTRY_SIZE = 0xFFFFFFFFL;
                long rafSize = this.raf.length();
                if (rafSize > 0xFFFFFFFFL) {
                    throw new IOException("File exceeds size limit of 4294967295.");
                }
                this.map.extend(this.raf.length());
                this.readData();
            }
            if (this.eocdEntry == null) {
                this.eocdComment = new byte[0];
            }
            if (this.state != ZipFileState.CLOSED) {
                this.notify(ZFileExtension::open);
            }
        }
        catch (Zip64NotSupportedException e2) {
            throw e2;
        }
        catch (IOException e3) {
            throw new IOException("Failed to read zip file '" + file.getAbsolutePath() + "'.", e3);
        }
        catch (VerifyException | IllegalArgumentException | IllegalStateException e4) {
            throw new RuntimeException("Internal error when trying to read zip file '" + file.getAbsolutePath() + "'.", e4);
        }
    }

    @Deprecated
    public void openReadOnly() throws IOException {
        this.openReadOnlyIfClosed();
    }

    public static ZFile openReadOnly(File file) throws IOException {
        return ZFile.openReadOnly(file, new ZFileOptions());
    }

    public static ZFile openReadOnly(File file, ZFileOptions options) throws IOException {
        return new ZFile(file, options, true);
    }

    public static ZFile openReadWrite(File file) throws IOException {
        return ZFile.openReadWrite(file, new ZFileOptions());
    }

    public static ZFile openReadWrite(File file, ZFileOptions options) throws IOException {
        return new ZFile(file, options, false);
    }

    public Set<StoredEntry> entries() {
        HashMap<String, StoredEntry> entries = Maps.newHashMap();
        for (FileUseMapEntry<StoredEntry> mapEntry : this.entries.values()) {
            StoredEntry entry = mapEntry.getStore();
            assert (entry != null);
            entries.put(entry.getCentralDirectoryHeader().getName(), entry);
        }
        for (StoredEntry uncompressed : this.uncompressedEntries) {
            entries.put(uncompressed.getCentralDirectoryHeader().getName(), uncompressed);
        }
        return Sets.newHashSet(entries.values());
    }

    @Nullable
    public StoredEntry get(String path) {
        for (StoredEntry stillUncompressed : Lists.reverse(this.uncompressedEntries)) {
            if (!stillUncompressed.getCentralDirectoryHeader().getName().equals(path)) continue;
            return stillUncompressed;
        }
        FileUseMapEntry<StoredEntry> found = this.entries.get(path);
        if (found == null) {
            return null;
        }
        return found.getStore();
    }

    private void readData() throws IOException {
        long directoryStartOffset;
        long entryEndOffset;
        Preconditions.checkState(this.state != ZipFileState.CLOSED, "state == ZipFileState.CLOSED");
        Preconditions.checkState(this.raf != null, "raf == null");
        this.readEocd();
        this.readCentralDirectory();
        if (this.directoryEntry != null) {
            CentralDirectory directory = this.directoryEntry.getStore();
            assert (directory != null);
            entryEndOffset = 0L;
            for (StoredEntry entry : directory.getEntries().values()) {
                long start = entry.getCentralDirectoryHeader().getOffset();
                long end = start + entry.getInFileSize();
                Verify.verify(start >= 0L, "start < 0", new Object[0]);
                Verify.verify(end < this.map.size(), "end >= map.size()", new Object[0]);
                FileUseMapEntry<?> found = this.map.at(start);
                Verify.verifyNotNull(found);
                if (!found.isFree() || found.getEnd() < end) {
                    String overlappingEntryDescription;
                    Object foundEntry;
                    if (found.isFree()) {
                        Verify.verify((found = this.map.after(found)) != null && !found.isFree());
                    }
                    Verify.verify((foundEntry = found.getStore()) != null);
                    IOExceptionFunction<StoredEntry, String> describe = e2 -> String.format("'%s' (offset: %d, size: %d)", e2.getCentralDirectoryHeader().getName(), e2.getCentralDirectoryHeader().getOffset(), e2.getInFileSize());
                    if (foundEntry instanceof StoredEntry) {
                        StoredEntry foundStored = (StoredEntry)foundEntry;
                        overlappingEntryDescription = describe.apply(foundStored);
                    } else {
                        overlappingEntryDescription = "Central Directory / EOCD: " + found.getStart() + " - " + found.getEnd();
                    }
                    throw new IOException("Cannot read entry " + describe.apply(entry) + " because it overlaps with " + overlappingEntryDescription);
                }
                FileUseMapEntry<StoredEntry> mapEntry = this.map.add(start, end, entry);
                this.entries.put(entry.getCentralDirectoryHeader().getName(), mapEntry);
                if (end <= entryEndOffset) continue;
                entryEndOffset = end;
            }
            directoryStartOffset = this.directoryEntry.getStart();
        } else {
            Verify.verifyNotNull(this.eocdEntry);
            assert (this.eocdEntry != null);
            directoryStartOffset = this.eocdEntry.getStart();
            entryEndOffset = 0L;
        }
        long extraOffset = directoryStartOffset - entryEndOffset;
        Verify.verify(extraOffset >= 0L, "extraOffset (%s) < 0", extraOffset);
        this.extraDirectoryOffset = extraOffset;
    }

    private void readEocd() throws IOException {
        Preconditions.checkState(this.state != ZipFileState.CLOSED, "state == ZipFileState.CLOSED");
        Preconditions.checkState(this.raf != null, "raf == null");
        int lastToRead = 65557;
        if ((long)lastToRead > this.raf.length()) {
            lastToRead = Ints.checkedCast(this.raf.length());
        }
        byte[] last = new byte[lastToRead];
        this.directFullyRead(this.raf.length() - (long)lastToRead, last);
        Eocd eocd = null;
        int foundEocdSignature = -1;
        IOException errorFindingSignature = null;
        long eocdStart = -1L;
        for (int endIdx = last.length - 22; endIdx >= 0 && foundEocdSignature == -1; --endIdx) {
            if (last[endIdx] != EOCD_SIGNATURE[3] || last[endIdx + 1] != EOCD_SIGNATURE[2] || last[endIdx + 2] != EOCD_SIGNATURE[1] || last[endIdx + 3] != EOCD_SIGNATURE[0]) continue;
            foundEocdSignature = endIdx;
            ByteBuffer eocdBytes = ByteBuffer.wrap(last, foundEocdSignature, last.length - foundEocdSignature);
            try {
                eocd = new Eocd(eocdBytes);
                eocdStart = this.raf.length() - (long)lastToRead + (long)foundEocdSignature;
                if (eocdStart + eocd.getEocdSize() == this.raf.length()) continue;
                this.verifyLog.log("EOCD starts at " + eocdStart + " and has " + eocd.getEocdSize() + " bytes, but file ends at " + this.raf.length() + ".");
                continue;
            }
            catch (IOException e2) {
                if (errorFindingSignature != null) {
                    e2.addSuppressed(errorFindingSignature);
                }
                errorFindingSignature = e2;
                foundEocdSignature = -1;
                eocd = null;
            }
        }
        if (foundEocdSignature == -1) {
            throw new IOException("EOCD signature not found in the last " + lastToRead + " bytes of the file.", errorFindingSignature);
        }
        Verify.verify(eocdStart >= 0L);
        long zip64LocatorStart = eocdStart - 20L;
        if (zip64LocatorStart >= 0L) {
            byte[] possibleZip64Locator = new byte[4];
            this.directFullyRead(zip64LocatorStart, possibleZip64Locator);
            if (LittleEndianUtils.readUnsigned4Le(ByteBuffer.wrap(possibleZip64Locator)) == 117853008L) {
                throw new Zip64NotSupportedException("Zip64 EOCD locator found but Zip64 format is not supported.");
            }
        }
        this.eocdEntry = this.map.add(eocdStart, eocdStart + eocd.getEocdSize(), eocd);
    }

    private void readCentralDirectory() throws IOException {
        Preconditions.checkNotNull(this.eocdEntry, "eocdEntry == null");
        Preconditions.checkNotNull(this.eocdEntry.getStore(), "eocdEntry.getStore() == null");
        Preconditions.checkState(this.state != ZipFileState.CLOSED, "state == ZipFileState.CLOSED");
        Preconditions.checkState(this.raf != null, "raf == null");
        Preconditions.checkState(this.directoryEntry == null, "directoryEntry != null");
        Eocd eocd = this.eocdEntry.getStore();
        long dirSize = eocd.getDirectorySize();
        if (dirSize > Integer.MAX_VALUE) {
            throw new IOException("Cannot read central directory with size " + dirSize + ".");
        }
        long centralDirectoryEnd = eocd.getDirectoryOffset() + dirSize;
        if (centralDirectoryEnd != this.eocdEntry.getStart()) {
            String msg = "Central directory is stored in [" + eocd.getDirectoryOffset() + " - " + (centralDirectoryEnd - 1L) + "] and EOCD starts at " + this.eocdEntry.getStart() + ".";
            if (centralDirectoryEnd > this.eocdEntry.getSize()) {
                throw new IOException(msg);
            }
            this.verifyLog.log(msg);
        }
        byte[] directoryData = new byte[Ints.checkedCast(dirSize)];
        this.directFullyRead(eocd.getDirectoryOffset(), directoryData);
        CentralDirectory directory = CentralDirectory.makeFromData(ByteBuffer.wrap(directoryData), eocd.getTotalRecords(), this, this.storage);
        if (eocd.getDirectorySize() > 0L) {
            this.directoryEntry = this.map.add(eocd.getDirectoryOffset(), eocd.getDirectoryOffset() + eocd.getDirectorySize(), directory);
        }
    }

    public InputStream directOpen(final long start, final long end) throws IOException {
        Preconditions.checkState(this.state != ZipFileState.CLOSED, "state == ZipFileState.CLOSED");
        Preconditions.checkState(this.raf != null, "raf == null");
        Preconditions.checkArgument(start >= 0L, "start < 0");
        Preconditions.checkArgument(end >= start, "end < start");
        Preconditions.checkArgument(end <= this.raf.length(), "end > raf.length()");
        return new InputStream(){
            private long mCurr;
            {
                this.mCurr = start;
            }

            @Override
            public int read() throws IOException {
                if (this.mCurr == end) {
                    return -1;
                }
                byte[] b2 = new byte[1];
                int r3 = ZFile.this.directRead(this.mCurr, b2);
                if (r3 > 0) {
                    ++this.mCurr;
                    return b2[0];
                }
                return -1;
            }

            @Override
            public int read(byte[] b2, int off, int len) throws IOException {
                Preconditions.checkNotNull(b2, "b == null");
                Preconditions.checkArgument(off >= 0, "off < 0");
                Preconditions.checkArgument(off <= b2.length, "off > b.length");
                Preconditions.checkArgument(len >= 0, "len < 0");
                Preconditions.checkArgument(off + len <= b2.length, "off + len > b.length");
                long availableToRead = end - this.mCurr;
                long toRead = Math.min((long)len, availableToRead);
                if (toRead == 0L) {
                    return -1;
                }
                if (toRead > Integer.MAX_VALUE) {
                    throw new IOException("Cannot read " + toRead + " bytes.");
                }
                int r3 = ZFile.this.directRead(this.mCurr, b2, off, Ints.checkedCast(toRead));
                if (r3 > 0) {
                    this.mCurr += (long)r3;
                }
                return r3;
            }
        };
    }

    void delete(StoredEntry entry, boolean notify) throws IOException {
        this.checkNotInReadOnlyMode();
        String path = entry.getCentralDirectoryHeader().getName();
        FileUseMapEntry<StoredEntry> mapEntry = this.entries.get(path);
        Preconditions.checkNotNull(mapEntry, "mapEntry == null");
        Preconditions.checkArgument(entry == mapEntry.getStore(), "entry != mapEntry.getStore()");
        this.dirty = true;
        this.map.remove(mapEntry);
        this.entries.remove(path);
        if (notify) {
            this.notify(ext -> ext.removed(entry));
        }
    }

    private void checkNotInReadOnlyMode() {
        if (this.readOnly) {
            throw new IllegalStateException("Illegal operation in read only model");
        }
    }

    public void update() throws IOException {
        this.checkNotInReadOnlyMode();
        this.processAllReadyEntriesWithWait();
        this.notify(ZFileExtension::beforeUpdate);
        this.processAllReadyEntriesWithWait();
        if (this.dirty) {
            this.writeAllFilesToZip();
        }
        this.recomputeAndWriteCentralDirectoryAndEocd();
        if (this.raf != null && this.raf.length() != this.map.size()) {
            this.raf.setLength(this.map.size());
        }
        this.dirty = false;
        this.notify(ext -> {
            ext.updated();
            return null;
        });
    }

    private void writeAllFilesToZip() throws IOException {
        this.reopenRw();
        if (this.autoSortFiles) {
            this.sortZipContents();
        } else {
            this.packIfNecessary();
        }
        this.deleteDirectoryAndEocd();
        this.map.truncate();
        if (this.coverEmptySpaceUsingExtraField) {
            for (FileUseMapEntry<StoredEntry> fileUseMapEntry : new HashSet<FileUseMapEntry<StoredEntry>>(this.entries.values())) {
                ImmutableList<ExtraField.Segment> currentSegments;
                StoredEntry storedEntry = fileUseMapEntry.getStore();
                assert (storedEntry != null);
                FileUseMapEntry<?> before = this.map.before(fileUseMapEntry);
                if (before == null || !before.isFree()) continue;
                int localExtraSize = storedEntry.getLocalExtra().size() + Ints.checkedCast(before.getSize());
                Verify.verify(localExtraSize <= Short.MAX_VALUE);
                storedEntry.loadSourceIntoMemory();
                long newStart = before.getStart();
                long newSize = fileUseMapEntry.getSize() + before.getSize();
                String name = storedEntry.getCentralDirectoryHeader().getName();
                this.map.remove(fileUseMapEntry);
                Verify.verify(fileUseMapEntry == this.entries.remove(name));
                try {
                    currentSegments = storedEntry.getLocalExtra().getSegments();
                }
                catch (IOException e2) {
                    currentSegments = ImmutableList.of();
                }
                ArrayList<ExtraField.Segment> extraFieldSegments = new ArrayList<ExtraField.Segment>();
                int newExtraFieldSize = 0;
                for (ExtraField.Segment segment : currentSegments) {
                    if (segment.getHeaderId() == 55605) continue;
                    extraFieldSegments.add(segment);
                    newExtraFieldSize += segment.size();
                }
                int spaceToFill = Ints.checkedCast(before.getSize() + (long)storedEntry.getLocalExtra().size() - (long)newExtraFieldSize);
                extraFieldSegments.add(new ExtraField.AlignmentSegment(this.chooseAlignment(storedEntry), spaceToFill));
                storedEntry.setLocalExtraNoNotify(new ExtraField(ImmutableList.copyOf(extraFieldSegments)));
                this.entries.put(name, this.map.add(newStart, newStart + newSize, storedEntry));
                storedEntry.getCentralDirectoryHeader().setOffset(-1L);
            }
        }
        TreeMap toWriteToStore = new TreeMap(FileUseMapEntry.COMPARE_BY_START);
        for (FileUseMapEntry<StoredEntry> entry : this.entries.values()) {
            StoredEntry entryStore = entry.getStore();
            assert (entryStore != null);
            if (entryStore.getCentralDirectoryHeader().getOffset() != -1L) continue;
            toWriteToStore.put(entry, entryStore);
        }
        for (FileUseMapEntry<?> freeArea : this.map.getFreeAreas()) {
            toWriteToStore.put(freeArea, null);
        }
        byte[] byArray = new byte[0x100000];
        for (FileUseMapEntry<?> fileUseMapEntry : toWriteToStore.keySet()) {
            StoredEntry entry = (StoredEntry)toWriteToStore.get(fileUseMapEntry);
            if (entry == null) {
                int size = Ints.checkedCast(fileUseMapEntry.getSize());
                this.directWrite(fileUseMapEntry.getStart(), new byte[size]);
                continue;
            }
            this.writeEntry(entry, fileUseMapEntry.getStart(), byArray);
        }
    }

    private void recomputeAndWriteCentralDirectoryAndEocd() throws IOException {
        boolean hasCentralDirectory;
        boolean changedAnything = false;
        int extensionBugDetector = 10;
        do {
            if (this.directoryEntry == null) {
                this.reopenRw();
                changedAnything = true;
                this.computeCentralDirectory();
            }
            if (this.eocdEntry == null) {
                this.reopenRw();
                changedAnything = true;
                this.computeEocd();
            }
            hasCentralDirectory = this.directoryEntry != null;
            this.notify(ext -> {
                ext.entriesWritten();
                return null;
            });
            if (--extensionBugDetector != 0) continue;
            throw new IOException("Extensions keep resetting the central directory. This is probably a bug.");
        } while (hasCentralDirectory && this.directoryEntry == null);
        if (changedAnything) {
            this.reopenRw();
            this.appendCentralDirectory();
            this.appendEocd();
        }
    }

    private void packIfNecessary() throws IOException {
        if (!this.coverEmptySpaceUsingExtraField) {
            return;
        }
        TreeSet entriesByLocation = new TreeSet(FileUseMapEntry.COMPARE_BY_START);
        entriesByLocation.addAll(this.entries.values());
        for (FileUseMapEntry fileUseMapEntry : entriesByLocation) {
            int localExtraSize;
            StoredEntry storedEntry = (StoredEntry)fileUseMapEntry.getStore();
            assert (storedEntry != null);
            FileUseMapEntry<?> before = this.map.before(fileUseMapEntry);
            if (before == null || !before.isFree() || (localExtraSize = storedEntry.getLocalExtra().size() + Ints.checkedCast(before.getSize())) <= Short.MAX_VALUE) continue;
            this.reAdd(storedEntry, PositionHint.LOWEST_OFFSET);
        }
    }

    private void reAdd(StoredEntry entry, PositionHint positionHint) throws IOException {
        String name = entry.getCentralDirectoryHeader().getName();
        FileUseMapEntry<StoredEntry> mapEntry = this.entries.get(name);
        Preconditions.checkNotNull(mapEntry);
        Preconditions.checkState(mapEntry.getStore() == entry);
        entry.loadSourceIntoMemory();
        this.map.remove(mapEntry);
        this.entries.remove(name);
        FileUseMapEntry<StoredEntry> positioned = this.positionInFile(entry, positionHint);
        this.entries.put(name, positioned);
        this.dirty = true;
    }

    void localHeaderChanged(StoredEntry entry, boolean resized) throws IOException {
        this.dirty = true;
        if (resized) {
            this.reAdd(entry, PositionHint.ANYWHERE);
        }
    }

    void centralDirectoryChanged() {
        this.dirty = true;
        this.deleteDirectoryAndEocd();
    }

    @Override
    public void close() throws IOException {
        try (Closeable ignored = this::innerClose;){
            if (!this.readOnly) {
                this.update();
            }
            this.storage.close();
        }
        this.notify(ext -> {
            ext.closed();
            return null;
        });
    }

    private void deleteDirectoryAndEocd() {
        if (this.directoryEntry != null) {
            this.map.remove(this.directoryEntry);
            this.directoryEntry = null;
        }
        if (this.eocdEntry != null) {
            this.map.remove(this.eocdEntry);
            Eocd eocd = this.eocdEntry.getStore();
            Verify.verify(eocd != null);
            this.eocdComment = eocd.getComment();
            this.eocdEntry = null;
        }
    }

    private void writeEntry(StoredEntry entry, long offset, byte[] chunk) throws IOException {
        Preconditions.checkArgument(entry.getDataDescriptorType() == DataDescriptorType.NO_DATA_DESCRIPTOR, "Cannot write entries with a data descriptor.");
        Preconditions.checkNotNull(this.raf, "raf == null");
        Preconditions.checkState(this.state == ZipFileState.OPEN_RW, "state != ZipFileState.OPEN_RW");
        int readOffset = entry.toHeaderData(chunk);
        long writeOffset = offset;
        try (InputStream is = entry.getSource().getRawByteSource().openStream();){
            int r3;
            while ((r3 = is.read(chunk, readOffset, chunk.length - readOffset)) >= 0 || readOffset > 0) {
                int toWrite = (r3 == -1 ? 0 : r3) + readOffset;
                this.directWrite(writeOffset, chunk, 0, toWrite);
                writeOffset += (long)toWrite;
                readOffset = 0;
            }
        }
        entry.replaceSourceFromZip(offset);
    }

    private void computeCentralDirectory() throws IOException {
        Preconditions.checkState(this.state == ZipFileState.OPEN_RW, "state != ZipFileState.OPEN_RW");
        Preconditions.checkNotNull(this.raf, "raf == null");
        Preconditions.checkState(this.directoryEntry == null, "directoryEntry == null");
        HashSet<StoredEntry> newStored = Sets.newHashSet();
        for (FileUseMapEntry<StoredEntry> mapEntry : this.entries.values()) {
            newStored.add(mapEntry.getStore());
        }
        this.map.truncate();
        CentralDirectory newDirectory = CentralDirectory.makeFromEntries(newStored, this);
        byte[] newDirectoryBytes = newDirectory.toBytes();
        long directoryOffset = this.map.size() + this.extraDirectoryOffset;
        this.map.extend(directoryOffset + (long)newDirectoryBytes.length);
        if (newDirectoryBytes.length > 0) {
            this.directoryEntry = this.map.add(directoryOffset, directoryOffset + (long)newDirectoryBytes.length, newDirectory);
        }
    }

    private void appendCentralDirectory() throws IOException {
        Preconditions.checkState(this.state == ZipFileState.OPEN_RW, "state != ZipFileState.OPEN_RW");
        Preconditions.checkNotNull(this.raf, "raf == null");
        if (this.entries.isEmpty()) {
            Preconditions.checkState(this.directoryEntry == null, "directoryEntry != null");
            return;
        }
        Preconditions.checkNotNull(this.directoryEntry, "directoryEntry != null");
        CentralDirectory newDirectory = this.directoryEntry.getStore();
        Preconditions.checkNotNull(newDirectory, "newDirectory != null");
        byte[] newDirectoryBytes = newDirectory.toBytes();
        long directoryOffset = this.directoryEntry.getStart();
        this.directWrite(directoryOffset, newDirectoryBytes);
    }

    public byte[] getCentralDirectoryBytes() throws IOException {
        if (this.entries.isEmpty()) {
            Preconditions.checkState(this.directoryEntry == null, "directoryEntry != null");
            return new byte[0];
        }
        Preconditions.checkNotNull(this.directoryEntry, "directoryEntry == null");
        CentralDirectory cd2 = this.directoryEntry.getStore();
        Preconditions.checkNotNull(cd2, "cd == null");
        return cd2.toBytes();
    }

    private void computeEocd() throws IOException {
        long dirStart;
        Preconditions.checkState(this.state == ZipFileState.OPEN_RW, "state != ZipFileState.OPEN_RW");
        Preconditions.checkNotNull(this.raf, "raf == null");
        if (this.directoryEntry == null) {
            Preconditions.checkState(this.entries.isEmpty(), "directoryEntry == null && !entries.isEmpty()");
        }
        long dirSize = 0L;
        if (this.directoryEntry != null) {
            CentralDirectory directory = this.directoryEntry.getStore();
            assert (directory != null);
            dirStart = this.directoryEntry.getStart();
            dirSize = this.directoryEntry.getSize();
            Verify.verify(directory.getEntries().size() == this.entries.size());
        } else {
            dirStart = this.extraDirectoryOffset;
        }
        Verify.verify(this.eocdComment != null);
        Eocd eocd = new Eocd(this.entries.size(), dirStart, dirSize, this.eocdComment);
        this.eocdComment = null;
        byte[] eocdBytes = eocd.toBytes();
        long eocdOffset = this.map.size();
        this.map.extend(eocdOffset + (long)eocdBytes.length);
        this.eocdEntry = this.map.add(eocdOffset, eocdOffset + (long)eocdBytes.length, eocd);
    }

    private void appendEocd() throws IOException {
        Preconditions.checkState(this.state == ZipFileState.OPEN_RW, "state != ZipFileState.OPEN_RW");
        Preconditions.checkNotNull(this.raf, "raf == null");
        Preconditions.checkNotNull(this.eocdEntry, "eocdEntry == null");
        Eocd eocd = this.eocdEntry.getStore();
        Preconditions.checkNotNull(eocd, "eocd == null");
        byte[] eocdBytes = eocd.toBytes();
        long eocdOffset = this.eocdEntry.getStart();
        this.directWrite(eocdOffset, eocdBytes);
    }

    public byte[] getEocdBytes() throws IOException {
        Preconditions.checkNotNull(this.eocdEntry, "eocdEntry == null");
        Eocd eocd = this.eocdEntry.getStore();
        Preconditions.checkNotNull(eocd, "eocd == null");
        return eocd.toBytes();
    }

    private void innerClose() throws IOException {
        if (this.state == ZipFileState.CLOSED) {
            return;
        }
        Verify.verifyNotNull(this.raf, "raf == null", new Object[0]);
        this.raf.close();
        this.raf = null;
        this.state = ZipFileState.CLOSED;
        if (this.closedControl == null) {
            this.closedControl = new CachedFileContents(this.file);
        }
        this.closedControl.closed(null);
    }

    public void openReadOnlyIfClosed() throws IOException {
        if (this.state != ZipFileState.CLOSED) {
            return;
        }
        this.state = ZipFileState.OPEN_RO;
        this.raf = new RandomAccessFile(this.file, "r");
    }

    private void reopenRw() throws IOException {
        boolean wasClosed;
        Verify.verify(!this.readOnly);
        if (this.state == ZipFileState.OPEN_RW) {
            return;
        }
        if (this.state == ZipFileState.OPEN_RO) {
            this.innerClose();
            wasClosed = false;
        } else {
            wasClosed = true;
        }
        Verify.verify(this.state == ZipFileState.CLOSED, "state != ZpiFileState.CLOSED", new Object[0]);
        Verify.verify(this.raf == null, "raf != null", new Object[0]);
        if (this.closedControl != null && !this.closedControl.isValid()) {
            throw new IOException("File '" + this.file.getAbsolutePath() + "' has been modified by an external application.");
        }
        this.raf = new RandomAccessFile(this.file, "rw");
        this.state = ZipFileState.OPEN_RW;
        for (StoredEntry entry : this.entries()) {
            this.dirty |= entry.removeDataDescriptor();
        }
        if (wasClosed) {
            this.notify(ZFileExtension::open);
        }
    }

    public void add(String name, InputStream stream) throws IOException {
        this.checkNotInReadOnlyMode();
        this.add(name, stream, true);
    }

    public void add(String name, InputStream stream, boolean mayCompress) throws IOException {
        this.checkNotInReadOnlyMode();
        this.processAllReadyEntries();
        this.add(this.makeStoredEntry(name, stream, mayCompress));
    }

    private void add(StoredEntry newEntry) throws IOException {
        this.uncompressedEntries.add(newEntry);
        this.processAllReadyEntries();
    }

    private StoredEntry makeStoredEntry(String name, InputStream stream, boolean mayCompress) throws IOException {
        CloseableByteSource source = this.storage.fromStream(stream);
        long crc32 = source.hash(Hashing.crc32()).padToLong();
        boolean encodeWithUtf8 = !EncodeUtils.canAsciiEncode(name);
        SettableFuture<CentralDirectoryHeaderCompressInfo> compressInfo = SettableFuture.create();
        GPFlags flags = GPFlags.make(encodeWithUtf8);
        CentralDirectoryHeader newFileData = new CentralDirectoryHeader(name, EncodeUtils.encode(name, flags), source.size(), compressInfo, flags, this);
        newFileData.setCrc32(crc32);
        Verify.verify(newFileData.getOffset() == -1L);
        return new StoredEntry(newFileData, this, this.createSources(mayCompress, source, compressInfo, newFileData), this.storage);
    }

    private ProcessedAndRawByteSources createSources(boolean mayCompress, CloseableByteSource source, final SettableFuture<CentralDirectoryHeaderCompressInfo> compressInfo, final CentralDirectoryHeader newFileData) throws IOException {
        if (mayCompress) {
            ListenableFuture<CompressionResult> result = this.compressor.compress(source, this.storage);
            Futures.addCallback(result, new FutureCallback<CompressionResult>(){

                @Override
                public void onSuccess(CompressionResult result) {
                    compressInfo.set(new CentralDirectoryHeaderCompressInfo(newFileData, result.getCompressionMethod(), result.getSize()));
                }

                @Override
                public void onFailure(Throwable t3) {
                    compressInfo.setException(t3);
                }
            }, MoreExecutors.directExecutor());
            ListenableFuture<CloseableByteSource> compressedByteSourceFuture = Futures.transform(result, CompressionResult::getSource, MoreExecutors.directExecutor());
            LazyDelegateByteSource compressedByteSource = new LazyDelegateByteSource(compressedByteSourceFuture);
            return new ProcessedAndRawByteSources(source, compressedByteSource);
        }
        compressInfo.set(new CentralDirectoryHeaderCompressInfo(newFileData, CompressionMethod.STORE, source.size()));
        return new ProcessedAndRawByteSources(source, source);
    }

    private void processAllReadyEntries() throws IOException {
        while (!this.uncompressedEntries.isEmpty()) {
            StoredEntry next = this.uncompressedEntries.get(0);
            CentralDirectoryHeader cdh = next.getCentralDirectoryHeader();
            Future<CentralDirectoryHeaderCompressInfo> compressionInfo = cdh.getCompressionInfo();
            if (!compressionInfo.isDone()) {
                return;
            }
            this.uncompressedEntries.remove(0);
            try {
                compressionInfo.get();
            }
            catch (InterruptedException e2) {
                throw new IOException("Impossible I/O exception: get for already computed future throws InterruptedException", e2);
            }
            catch (ExecutionException e3) {
                throw new IOException("Failed to obtain compression information for entry", e3);
            }
            this.addToEntries(next);
        }
    }

    private void processAllReadyEntriesWithWait() throws IOException {
        this.processAllReadyEntries();
        while (!this.uncompressedEntries.isEmpty()) {
            StoredEntry first = this.uncompressedEntries.get(0);
            CentralDirectoryHeader cdh = first.getCentralDirectoryHeader();
            cdh.getCompressionInfoWithWait();
            this.processAllReadyEntries();
        }
    }

    private void addToEntries(StoredEntry newEntry) throws IOException {
        StoredEntry replaceStore;
        Preconditions.checkArgument(newEntry.getDataDescriptorType() == DataDescriptorType.NO_DATA_DESCRIPTOR, "newEntry has data descriptor");
        FileUseMapEntry<StoredEntry> toReplace = this.entries.get(newEntry.getCentralDirectoryHeader().getName());
        if (toReplace != null) {
            replaceStore = toReplace.getStore();
            assert (replaceStore != null);
            replaceStore.delete(false);
        } else {
            replaceStore = null;
        }
        FileUseMapEntry<StoredEntry> fileUseMapEntry = this.positionInFile(newEntry, PositionHint.ANYWHERE);
        this.entries.put(newEntry.getCentralDirectoryHeader().getName(), fileUseMapEntry);
        this.dirty = true;
        this.notify(ext -> ext.added(newEntry, replaceStore));
    }

    private FileUseMapEntry<StoredEntry> positionInFile(StoredEntry entry, PositionHint positionHint) throws IOException {
        FileUseMap.PositionAlgorithm algorithm;
        this.deleteDirectoryAndEocd();
        long size = entry.getInFileSize();
        int localHeaderSize = entry.getLocalHeaderSize();
        int alignment = this.chooseAlignment(entry);
        switch (positionHint) {
            case LOWEST_OFFSET: {
                algorithm = FileUseMap.PositionAlgorithm.FIRST_FIT;
                break;
            }
            case ANYWHERE: {
                algorithm = FileUseMap.PositionAlgorithm.BEST_FIT;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        long newOffset = this.map.locateFree(size, localHeaderSize, alignment, algorithm);
        long newEnd = newOffset + entry.getInFileSize();
        if (newEnd > this.map.size()) {
            this.map.extend(newEnd);
        }
        return this.map.add(newOffset, newEnd, entry);
    }

    private int chooseAlignment(StoredEntry entry) throws IOException {
        boolean isCompressed;
        CentralDirectoryHeader cdh = entry.getCentralDirectoryHeader();
        CentralDirectoryHeaderCompressInfo compressionInfo = cdh.getCompressionInfoWithWait();
        boolean bl = isCompressed = compressionInfo.getMethod() != CompressionMethod.STORE;
        if (isCompressed) {
            return 1;
        }
        return this.alignmentRule.alignment(cdh.getName());
    }

    public void mergeFrom(ZFile src, Predicate<String> ignoreFilter) throws IOException {
        this.checkNotInReadOnlyMode();
        for (StoredEntry fromEntry : src.entries()) {
            int r3;
            CentralDirectoryHeader newFileData;
            if (ignoreFilter.apply(fromEntry.getCentralDirectoryHeader().getName())) continue;
            boolean replaceCurrent = true;
            String path = fromEntry.getCentralDirectoryHeader().getName();
            FileUseMapEntry<StoredEntry> currentEntry = this.entries.get(path);
            if (currentEntry != null) {
                long fromSize = fromEntry.getCentralDirectoryHeader().getUncompressedSize();
                long fromCrc = fromEntry.getCentralDirectoryHeader().getCrc32();
                StoredEntry currentStore = currentEntry.getStore();
                assert (currentStore != null);
                long currentSize = currentStore.getCentralDirectoryHeader().getUncompressedSize();
                long currentCrc = currentStore.getCentralDirectoryHeader().getCrc32();
                if (fromSize == currentSize && fromCrc == currentCrc) {
                    replaceCurrent = false;
                }
            }
            if (!replaceCurrent) continue;
            CentralDirectoryHeader fromCdr = fromEntry.getCentralDirectoryHeader();
            CentralDirectoryHeaderCompressInfo fromCompressInfo = fromCdr.getCompressionInfoWithWait();
            try {
                newFileData = fromCdr.clone();
                newFileData.setOffset(-1L);
                newFileData.resetDeferredCrc();
            }
            catch (CloneNotSupportedException e2) {
                throw new IOException("Failed to clone CDR.", e2);
            }
            ProcessedAndRawByteSources fromSource = fromEntry.getSource();
            InputStream fromInput = fromSource.getRawByteSource().openStream();
            long sourceSize = fromSource.getRawByteSource().size();
            if (sourceSize > Integer.MAX_VALUE) {
                throw new IOException("Cannot read source with " + sourceSize + " bytes.");
            }
            byte[] data = new byte[Ints.checkedCast(sourceSize)];
            for (int read = 0; read < data.length; read += r3) {
                r3 = fromInput.read(data, read, data.length - read);
                Verify.verify(r3 >= 0, "There should be at least 'size' bytes in the stream.", new Object[0]);
            }
            CloseableByteSource rawContents = this.storage.fromSource(fromSource.getRawByteSource());
            CloseableByteSource processedContents = fromCompressInfo.getMethod() == CompressionMethod.DEFLATE ? new InflaterByteSource(rawContents) : rawContents;
            ProcessedAndRawByteSources newSource = new ProcessedAndRawByteSources(processedContents, rawContents);
            StoredEntry newEntry = new StoredEntry(newFileData, this, newSource, this.storage);
            this.add(newEntry);
        }
    }

    public void touch() {
        this.checkNotInReadOnlyMode();
        this.dirty = true;
    }

    public void finishAllBackgroundTasks() throws IOException {
        this.processAllReadyEntriesWithWait();
    }

    public boolean realign() throws IOException {
        this.checkNotInReadOnlyMode();
        boolean anyChanges = false;
        for (StoredEntry entry : this.entries()) {
            anyChanges |= entry.realign();
        }
        if (anyChanges) {
            this.dirty = true;
        }
        return anyChanges;
    }

    boolean realign(StoredEntry entry) throws IOException {
        CentralDirectoryHeader clonedCdh;
        FileUseMapEntry<StoredEntry> mapEntry = this.entries.get(entry.getCentralDirectoryHeader().getName());
        Verify.verify(entry == mapEntry.getStore());
        long currentDataOffset = mapEntry.getStart() + (long)entry.getLocalHeaderSize();
        int expectedAlignment = this.chooseAlignment(entry);
        long misalignment = currentDataOffset % (long)expectedAlignment;
        if (misalignment == 0L) {
            return false;
        }
        if (entry.getCentralDirectoryHeader().getOffset() == -1L) {
            this.map.remove(mapEntry);
            long newStart = this.map.locateFree(mapEntry.getSize(), entry.getLocalHeaderSize(), expectedAlignment, FileUseMap.PositionAlgorithm.BEST_FIT);
            mapEntry = this.map.add(newStart, newStart + entry.getInFileSize(), entry);
            this.entries.put(entry.getCentralDirectoryHeader().getName(), mapEntry);
            Verify.verify(this.dirty);
            return false;
        }
        CentralDirectoryHeaderCompressInfo compressInfo = entry.getCentralDirectoryHeader().getCompressionInfoWithWait();
        ProcessedAndRawByteSources source = entry.getSource();
        try {
            clonedCdh = entry.getCentralDirectoryHeader().clone();
        }
        catch (CloneNotSupportedException e2) {
            Verify.verify(false);
            return false;
        }
        clonedCdh.setOffset(-1L);
        clonedCdh.resetDeferredCrc();
        CloseableByteSource rawContents = this.storage.fromSource(source.getRawByteSource());
        CloseableByteSource processedContents = compressInfo.getMethod() == CompressionMethod.DEFLATE ? new InflaterByteSource(rawContents) : rawContents;
        ProcessedAndRawByteSources newSource = new ProcessedAndRawByteSources(processedContents, rawContents);
        StoredEntry newEntry = new StoredEntry(clonedCdh, this, newSource, this.storage);
        this.add(newEntry);
        return true;
    }

    public void addZFileExtension(ZFileExtension extension) {
        this.checkNotInReadOnlyMode();
        this.extensions.add(extension);
    }

    public void removeZFileExtension(ZFileExtension extension) {
        this.checkNotInReadOnlyMode();
        this.extensions.remove(extension);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void notify(IOExceptionFunction<ZFileExtension, IOExceptionRunnable> function) throws IOException {
        for (ZFileExtension fl : Lists.newArrayList(this.extensions)) {
            IOExceptionRunnable r3 = function.apply(fl);
            if (r3 == null) continue;
            this.toRun.add(r3);
        }
        if (!this.isNotifying) {
            this.isNotifying = true;
            try {
                while (!this.toRun.isEmpty()) {
                    IOExceptionRunnable r4 = this.toRun.remove(0);
                    r4.run();
                }
            }
            finally {
                this.isNotifying = false;
            }
        }
    }

    public void directWrite(long offset, byte[] data, int start, int count) throws IOException {
        this.checkNotInReadOnlyMode();
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        Preconditions.checkArgument(start >= 0, "start >= 0");
        Preconditions.checkArgument(count >= 0, "count >= 0");
        if (data.length == 0) {
            return;
        }
        Preconditions.checkArgument(start <= data.length, "start > data.length");
        Preconditions.checkArgument(start + count <= data.length, "start + count > data.length");
        this.reopenRw();
        assert (this.raf != null);
        this.raf.seek(offset);
        this.raf.write(data, start, count);
    }

    public void directWrite(long offset, byte[] data) throws IOException {
        this.directWrite(offset, data, 0, data.length);
    }

    public long directSize() throws IOException {
        if (this.raf == null) {
            this.reopenRw();
            assert (this.raf != null);
        }
        return this.raf.length();
    }

    public int directRead(long offset, byte[] data, int start, int count) throws IOException {
        Preconditions.checkArgument(start >= 0, "start >= 0");
        Preconditions.checkArgument(count >= 0, "count >= 0");
        Preconditions.checkArgument(start <= data.length, "start > data.length");
        Preconditions.checkArgument(start + count <= data.length, "start + count > data.length");
        return this.directRead(offset, ByteBuffer.wrap(data, start, count));
    }

    public int directRead(long offset, ByteBuffer dest) throws IOException {
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        if (!dest.hasRemaining()) {
            return 0;
        }
        if (this.raf == null) {
            this.reopenRw();
            assert (this.raf != null);
        }
        this.raf.seek(offset);
        return this.raf.getChannel().read(dest);
    }

    public int directRead(long offset, byte[] data) throws IOException {
        return this.directRead(offset, data, 0, data.length);
    }

    public void directFullyRead(long offset, byte[] data) throws IOException {
        this.directFullyRead(offset, ByteBuffer.wrap(data));
    }

    public void directFullyRead(long offset, ByteBuffer dest) throws IOException {
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        if (!dest.hasRemaining()) {
            return;
        }
        if (this.raf == null) {
            this.reopenRw();
            assert (this.raf != null);
        }
        FileChannel fileChannel = this.raf.getChannel();
        while (dest.hasRemaining()) {
            fileChannel.position(offset);
            int chunkSize = fileChannel.read(dest);
            if (chunkSize == -1) {
                throw new EOFException("Failed to read " + dest.remaining() + " more bytes: premature EOF");
            }
            offset += (long)chunkSize;
        }
    }

    public void addAllRecursively(File file) throws IOException {
        this.checkNotInReadOnlyMode();
        this.addAllRecursively(file, f2 -> true);
    }

    public void addAllRecursively(File file, Predicate<? super File> mayCompress) throws IOException {
        this.checkNotInReadOnlyMode();
        this.addAllRecursively(file, file, mayCompress);
    }

    private void addAllRecursively(File file, File base, Predicate<? super File> mayCompress) throws IOException {
        String path;
        String string = path = Objects.equal(file, base) ? file.getName() : base.toURI().relativize(file.toURI()).getPath();
        if (file.isFile()) {
            boolean mayCompressFile = Verify.verifyNotNull(mayCompress.apply(file), "mayCompress.apply() returned null", new Object[0]);
            try (Closer closer = Closer.create();){
                FileInputStream fileInput = closer.register(new FileInputStream(file));
                this.add(path, fileInput, mayCompressFile);
            }
            return;
        }
        if (file.isDirectory()) {
            File[] directoryContents;
            if (!file.equals(base)) {
                File[] fileArray = null;
                try (Closer closer = Closer.create();){
                    InputStream stream = closer.register(new ByteArrayInputStream(new byte[0]));
                    this.add(path, stream, false);
                }
                catch (Throwable object) {
                    fileArray = object;
                    throw object;
                }
            }
            if ((directoryContents = file.listFiles()) != null) {
                Arrays.sort(directoryContents, (f02, f12) -> f02.getName().compareTo(f12.getName()));
                for (File subFile : directoryContents) {
                    this.addAllRecursively(subFile, base, mayCompress);
                }
            }
        }
    }

    public long getCentralDirectoryOffset() {
        if (this.directoryEntry != null) {
            return this.directoryEntry.getStart();
        }
        if (this.entries.isEmpty()) {
            return this.extraDirectoryOffset;
        }
        return this.map.usedSize() + this.extraDirectoryOffset;
    }

    public long getCentralDirectorySize() {
        if (this.directoryEntry != null) {
            return this.directoryEntry.getSize();
        }
        if (this.entries.isEmpty()) {
            return 0L;
        }
        return 1L;
    }

    public long getEocdOffset() {
        if (this.eocdEntry == null) {
            return -1L;
        }
        return this.eocdEntry.getStart();
    }

    public long getEocdSize() {
        if (this.eocdEntry == null) {
            return -1L;
        }
        return this.eocdEntry.getSize();
    }

    public byte[] getEocdComment() {
        if (this.eocdEntry == null) {
            Verify.verify(this.eocdComment != null);
            byte[] eocdCommentCopy = new byte[this.eocdComment.length];
            System.arraycopy(this.eocdComment, 0, eocdCommentCopy, 0, this.eocdComment.length);
            return eocdCommentCopy;
        }
        Eocd eocd = this.eocdEntry.getStore();
        Verify.verify(eocd != null);
        return eocd.getComment();
    }

    public void setEocdComment(byte[] comment) {
        this.checkNotInReadOnlyMode();
        if (comment.length > 65535) {
            throw new IllegalArgumentException("EOCD comment size (" + comment.length + ") is larger than the maximum allowed (" + 65535 + ")");
        }
        for (int i2 = 0; i2 < comment.length - 22; ++i2) {
            if (comment[i2] != EOCD_SIGNATURE[3] || comment[i2 + 1] != EOCD_SIGNATURE[2] || comment[i2 + 2] != EOCD_SIGNATURE[1] || comment[i2 + 3] != EOCD_SIGNATURE[0]) continue;
            ByteBuffer bytes = ByteBuffer.wrap(comment, i2, comment.length - i2);
            try {
                new Eocd(bytes);
                throw new IllegalArgumentException("Position " + i2 + " of the comment contains a valid EOCD record.");
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.deleteDirectoryAndEocd();
        this.eocdComment = new byte[comment.length];
        System.arraycopy(comment, 0, this.eocdComment, 0, comment.length);
        this.dirty = true;
    }

    public void setExtraDirectoryOffset(long offset) {
        this.checkNotInReadOnlyMode();
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        if (this.extraDirectoryOffset != offset) {
            this.extraDirectoryOffset = offset;
            this.deleteDirectoryAndEocd();
            this.dirty = true;
        }
    }

    public long getExtraDirectoryOffset() {
        return this.extraDirectoryOffset;
    }

    public boolean areTimestampsIgnored() {
        return this.noTimestamps;
    }

    public void sortZipContents() throws IOException {
        this.checkNotInReadOnlyMode();
        this.reopenRw();
        this.processAllReadyEntriesWithWait();
        Verify.verify(this.uncompressedEntries.isEmpty());
        TreeSet<StoredEntry> sortedEntries = Sets.newTreeSet(StoredEntry.COMPARE_BY_NAME);
        for (FileUseMapEntry<StoredEntry> fmEntry : this.entries.values()) {
            StoredEntry entry = fmEntry.getStore();
            Preconditions.checkNotNull(entry);
            sortedEntries.add(entry);
            entry.loadSourceIntoMemory();
            this.map.remove(fmEntry);
        }
        this.entries.clear();
        for (StoredEntry entry : sortedEntries) {
            String name = entry.getCentralDirectoryHeader().getName();
            FileUseMapEntry<StoredEntry> positioned = this.positionInFile(entry, PositionHint.LOWEST_OFFSET);
            this.entries.put(name, positioned);
        }
        this.dirty = true;
    }

    public File getFile() {
        return this.file;
    }

    VerifyLog makeVerifyLog() {
        VerifyLog log = this.verifyLogFactory.get();
        assert (log != null);
        return log;
    }

    VerifyLog getVerifyLog() {
        return this.verifyLog;
    }

    public boolean hasPendingChangesWithWait() throws IOException {
        this.processAllReadyEntriesWithWait();
        return this.dirty;
    }

    public ByteStorage getStorage() {
        return this.storage;
    }

    static enum PositionHint {
        ANYWHERE,
        LOWEST_OFFSET;

    }
}

