/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.apk;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkSigningBlockNotFoundException;
import com.android.apksig.apk.CodenameMinSdkVersionException;
import com.android.apksig.apk.MinSdkVersionException;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.android.apksig.internal.apk.v1.V1SchemeVerifier;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class ApkUtils {
    public static final String ANDROID_MANIFEST_ZIP_ENTRY_NAME = "AndroidManifest.xml";
    private static final long APK_SIG_BLOCK_MAGIC_HI = 3617552046287187010L;
    private static final long APK_SIG_BLOCK_MAGIC_LO = 2334950737559900225L;
    private static final int APK_SIG_BLOCK_MIN_SIZE = 32;
    private static final int MIN_SDK_VERSION_ATTR_ID = 16843276;
    private static final int DEBUGGABLE_ATTR_ID = 0x101000F;

    private ApkUtils() {
    }

    public static ZipSections findZipSections(DataSource apk) throws IOException, ZipFormatException {
        Pair<ByteBuffer, Long> eocdAndOffsetInFile = ZipUtils.findZipEndOfCentralDirectoryRecord(apk);
        if (eocdAndOffsetInFile == null) {
            throw new ZipFormatException("ZIP End of Central Directory record not found");
        }
        ByteBuffer eocdBuf = eocdAndOffsetInFile.getFirst();
        long eocdOffset = eocdAndOffsetInFile.getSecond();
        eocdBuf.order(ByteOrder.LITTLE_ENDIAN);
        long cdStartOffset = ZipUtils.getZipEocdCentralDirectoryOffset(eocdBuf);
        if (cdStartOffset > eocdOffset) {
            throw new ZipFormatException("ZIP Central Directory start offset out of range: " + cdStartOffset + ". ZIP End of Central Directory offset: " + eocdOffset);
        }
        long cdSizeBytes = ZipUtils.getZipEocdCentralDirectorySizeBytes(eocdBuf);
        long cdEndOffset = cdStartOffset + cdSizeBytes;
        if (cdEndOffset > eocdOffset) {
            throw new ZipFormatException("ZIP Central Directory overlaps with End of Central Directory. CD end: " + cdEndOffset + ", EoCD start: " + eocdOffset);
        }
        int cdRecordCount = ZipUtils.getZipEocdCentralDirectoryTotalRecordCount(eocdBuf);
        return new ZipSections(cdStartOffset, cdSizeBytes, cdRecordCount, eocdOffset, eocdBuf);
    }

    public static void setZipEocdCentralDirectoryOffset(ByteBuffer zipEndOfCentralDirectory, long offset) {
        ByteBuffer eocd = zipEndOfCentralDirectory.slice();
        eocd.order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.setZipEocdCentralDirectoryOffset(eocd, offset);
    }

    public static ApkSigningBlock findApkSigningBlock(DataSource apk, ZipSections zipSections) throws IOException, ApkSigningBlockNotFoundException {
        long eocdStartOffset;
        long centralDirStartOffset = zipSections.getZipCentralDirectoryOffset();
        long centralDirEndOffset = centralDirStartOffset + zipSections.getZipCentralDirectorySizeBytes();
        if (centralDirEndOffset != (eocdStartOffset = zipSections.getZipEndOfCentralDirectoryOffset())) {
            throw new ApkSigningBlockNotFoundException("ZIP Central Directory is not immediately followed by End of Central Directory. CD end: " + centralDirEndOffset + ", EoCD start: " + eocdStartOffset);
        }
        if (centralDirStartOffset < 32L) {
            throw new ApkSigningBlockNotFoundException("APK too small for APK Signing Block. ZIP Central Directory offset: " + centralDirStartOffset);
        }
        ByteBuffer footer = apk.getByteBuffer(centralDirStartOffset - 24L, 24);
        footer.order(ByteOrder.LITTLE_ENDIAN);
        if (footer.getLong(8) != 2334950737559900225L || footer.getLong(16) != 3617552046287187010L) {
            throw new ApkSigningBlockNotFoundException("No APK Signing Block before ZIP Central Directory");
        }
        long apkSigBlockSizeInFooter = footer.getLong(0);
        if (apkSigBlockSizeInFooter < (long)footer.capacity() || apkSigBlockSizeInFooter > 0x7FFFFFF7L) {
            throw new ApkSigningBlockNotFoundException("APK Signing Block size out of range: " + apkSigBlockSizeInFooter);
        }
        int totalSize = (int)(apkSigBlockSizeInFooter + 8L);
        long apkSigBlockOffset = centralDirStartOffset - (long)totalSize;
        if (apkSigBlockOffset < 0L) {
            throw new ApkSigningBlockNotFoundException("APK Signing Block offset out of range: " + apkSigBlockOffset);
        }
        ByteBuffer apkSigBlock = apk.getByteBuffer(apkSigBlockOffset, 8);
        apkSigBlock.order(ByteOrder.LITTLE_ENDIAN);
        long apkSigBlockSizeInHeader = apkSigBlock.getLong(0);
        if (apkSigBlockSizeInHeader != apkSigBlockSizeInFooter) {
            throw new ApkSigningBlockNotFoundException("APK Signing Block sizes in header and footer do not match: " + apkSigBlockSizeInHeader + " vs " + apkSigBlockSizeInFooter);
        }
        return new ApkSigningBlock(apkSigBlockOffset, apk.slice(apkSigBlockOffset, totalSize));
    }

    public static ByteBuffer getAndroidManifest(DataSource apk) throws IOException, ApkFormatException {
        ZipSections zipSections;
        try {
            zipSections = ApkUtils.findZipSections(apk);
        }
        catch (ZipFormatException e2) {
            throw new ApkFormatException("Not a valid ZIP archive", e2);
        }
        List<CentralDirectoryRecord> cdRecords = V1SchemeVerifier.parseZipCentralDirectory(apk, zipSections);
        CentralDirectoryRecord androidManifestCdRecord = null;
        for (CentralDirectoryRecord cdRecord : cdRecords) {
            if (!ANDROID_MANIFEST_ZIP_ENTRY_NAME.equals(cdRecord.getName())) continue;
            androidManifestCdRecord = cdRecord;
            break;
        }
        if (androidManifestCdRecord == null) {
            throw new ApkFormatException("Missing AndroidManifest.xml");
        }
        DataSource lfhSection = apk.slice(0L, zipSections.getZipCentralDirectoryOffset());
        try {
            return ByteBuffer.wrap(LocalFileRecord.getUncompressedData(lfhSection, androidManifestCdRecord, lfhSection.size()));
        }
        catch (ZipFormatException e3) {
            throw new ApkFormatException("Failed to read AndroidManifest.xml", e3);
        }
    }

    public static int getMinSdkVersionFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws MinSdkVersionException {
        try {
            int result = 1;
            AndroidBinXmlParser parser = new AndroidBinXmlParser(androidManifestContents);
            int eventType = parser.getEventType();
            while (eventType != 2) {
                if (eventType == 3 && parser.getDepth() == 2 && "uses-sdk".equals(parser.getName()) && parser.getNamespace().isEmpty()) {
                    int minSdkVersion = 1;
                    block7: for (int i2 = 0; i2 < parser.getAttributeCount(); ++i2) {
                        if (parser.getAttributeNameResourceId(i2) != 16843276) continue;
                        int valueType = parser.getAttributeValueType(i2);
                        switch (valueType) {
                            case 2: {
                                minSdkVersion = parser.getAttributeIntValue(i2);
                                break block7;
                            }
                            case 1: {
                                minSdkVersion = ApkUtils.getMinSdkVersionForCodename(parser.getAttributeStringValue(i2));
                                break block7;
                            }
                            default: {
                                throw new MinSdkVersionException("Unable to determine APK's minimum supported Android: unsupported value type in AndroidManifest.xml's minSdkVersion. Only integer values supported.");
                            }
                        }
                    }
                    result = Math.max(result, minSdkVersion);
                }
                eventType = parser.next();
            }
            return result;
        }
        catch (AndroidBinXmlParser.XmlParserException e2) {
            throw new MinSdkVersionException("Unable to determine APK's minimum supported Android platform version: malformed binary resource: AndroidManifest.xml", e2);
        }
    }

    static int getMinSdkVersionForCodename(String codename) throws CodenameMinSdkVersionException {
        char firstChar;
        char c2 = firstChar = codename.isEmpty() ? (char)' ' : (char)codename.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            Pair[] sortedCodenamesFirstCharToApiLevel = CodenamesLazyInitializer.SORTED_CODENAMES_FIRST_CHAR_TO_API_LEVEL;
            int searchResult = Arrays.binarySearch(sortedCodenamesFirstCharToApiLevel, Pair.of(Character.valueOf(firstChar), null), CodenamesLazyInitializer.CODENAME_FIRST_CHAR_COMPARATOR);
            if (searchResult >= 0) {
                return (Integer)sortedCodenamesFirstCharToApiLevel[searchResult].getSecond();
            }
            int insertionIndex = -1 - searchResult;
            if (insertionIndex == 0) {
                return 1;
            }
            Pair newestOlderCodenameMapping = sortedCodenamesFirstCharToApiLevel[insertionIndex - 1];
            char newestOlderCodenameFirstChar = ((Character)newestOlderCodenameMapping.getFirst()).charValue();
            int newestOlderCodenameApiLevel = (Integer)newestOlderCodenameMapping.getSecond();
            return newestOlderCodenameApiLevel + (firstChar - newestOlderCodenameFirstChar);
        }
        throw new CodenameMinSdkVersionException("Unable to determine APK's minimum supported Android platform version : Unsupported codename in AndroidManifest.xml's minSdkVersion: \"" + codename + "\"", codename);
    }

    public static boolean getDebuggableFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        try {
            AndroidBinXmlParser parser = new AndroidBinXmlParser(androidManifestContents);
            int eventType = parser.getEventType();
            while (eventType != 2) {
                if (eventType == 3 && parser.getDepth() == 2 && "application".equals(parser.getName()) && parser.getNamespace().isEmpty()) {
                    for (int i2 = 0; i2 < parser.getAttributeCount(); ++i2) {
                        if (parser.getAttributeNameResourceId(i2) != 0x101000F) continue;
                        int valueType = parser.getAttributeValueType(i2);
                        switch (valueType) {
                            case 1: 
                            case 2: 
                            case 4: {
                                String value = parser.getAttributeStringValue(i2);
                                return "true".equals(value) || "TRUE".equals(value) || "1".equals(value);
                            }
                            case 3: {
                                throw new ApkFormatException("Unable to determine whether APK is debuggable: AndroidManifest.xml's android:debuggable attribute references a resource. References are not supported for security reasons. Only constant boolean, string and int values are supported.");
                            }
                        }
                        throw new ApkFormatException("Unable to determine whether APK is debuggable: AndroidManifest.xml's android:debuggable attribute uses unsupported value type. Only boolean, string and int values are supported.");
                    }
                    return false;
                }
                eventType = parser.next();
            }
            return false;
        }
        catch (AndroidBinXmlParser.XmlParserException e2) {
            throw new ApkFormatException("Unable to determine whether APK is debuggable: malformed binary resource: AndroidManifest.xml", e2);
        }
    }

    public static String getPackageNameFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        try {
            AndroidBinXmlParser parser = new AndroidBinXmlParser(androidManifestContents);
            int eventType = parser.getEventType();
            while (eventType != 2) {
                if (eventType == 3 && parser.getDepth() == 1 && "manifest".equals(parser.getName()) && parser.getNamespace().isEmpty()) {
                    for (int i2 = 0; i2 < parser.getAttributeCount(); ++i2) {
                        if (!"package".equals(parser.getAttributeName(i2)) || !parser.getNamespace().isEmpty()) continue;
                        return parser.getAttributeStringValue(i2);
                    }
                    return null;
                }
                eventType = parser.next();
            }
            return null;
        }
        catch (AndroidBinXmlParser.XmlParserException e2) {
            throw new ApkFormatException("Unable to determine APK package name: malformed binary resource: AndroidManifest.xml", e2);
        }
    }

    private static class CodenamesLazyInitializer {
        private static final Pair<Character, Integer>[] SORTED_CODENAMES_FIRST_CHAR_TO_API_LEVEL = new Pair[]{Pair.of(Character.valueOf('C'), 2), Pair.of(Character.valueOf('D'), 3), Pair.of(Character.valueOf('E'), 4), Pair.of(Character.valueOf('F'), 7), Pair.of(Character.valueOf('G'), 8), Pair.of(Character.valueOf('H'), 10), Pair.of(Character.valueOf('I'), 13), Pair.of(Character.valueOf('J'), 15), Pair.of(Character.valueOf('K'), 18), Pair.of(Character.valueOf('L'), 20), Pair.of(Character.valueOf('M'), 22), Pair.of(Character.valueOf('N'), 23), Pair.of(Character.valueOf('O'), 25)};
        private static final Comparator<Pair<Character, Integer>> CODENAME_FIRST_CHAR_COMPARATOR = new ByFirstComparator();

        private CodenamesLazyInitializer() {
        }

        private static class ByFirstComparator
        implements Comparator<Pair<Character, Integer>> {
            private ByFirstComparator() {
            }

            @Override
            public int compare(Pair<Character, Integer> o12, Pair<Character, Integer> o22) {
                char c12 = o12.getFirst().charValue();
                char c2 = o22.getFirst().charValue();
                return c12 - c2;
            }
        }
    }

    public static class ApkSigningBlock {
        private final long mStartOffsetInApk;
        private final DataSource mContents;

        public ApkSigningBlock(long startOffsetInApk, DataSource contents) {
            this.mStartOffsetInApk = startOffsetInApk;
            this.mContents = contents;
        }

        public long getStartOffset() {
            return this.mStartOffsetInApk;
        }

        public DataSource getContents() {
            return this.mContents;
        }
    }

    public static class ZipSections {
        private final long mCentralDirectoryOffset;
        private final long mCentralDirectorySizeBytes;
        private final int mCentralDirectoryRecordCount;
        private final long mEocdOffset;
        private final ByteBuffer mEocd;

        public ZipSections(long centralDirectoryOffset, long centralDirectorySizeBytes, int centralDirectoryRecordCount, long eocdOffset, ByteBuffer eocd) {
            this.mCentralDirectoryOffset = centralDirectoryOffset;
            this.mCentralDirectorySizeBytes = centralDirectorySizeBytes;
            this.mCentralDirectoryRecordCount = centralDirectoryRecordCount;
            this.mEocdOffset = eocdOffset;
            this.mEocd = eocd;
        }

        public long getZipCentralDirectoryOffset() {
            return this.mCentralDirectoryOffset;
        }

        public long getZipCentralDirectorySizeBytes() {
            return this.mCentralDirectorySizeBytes;
        }

        public int getZipCentralDirectoryRecordCount() {
            return this.mCentralDirectoryRecordCount;
        }

        public long getZipEndOfCentralDirectoryOffset() {
            return this.mEocdOffset;
        }

        public ByteBuffer getZipEndOfCentralDirectory() {
            return this.mEocd;
        }
    }
}

