/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidBinXmlParser {
    public static final int EVENT_START_DOCUMENT = 1;
    public static final int EVENT_END_DOCUMENT = 2;
    public static final int EVENT_START_ELEMENT = 3;
    public static final int EVENT_END_ELEMENT = 4;
    public static final int VALUE_TYPE_UNSUPPORTED = 0;
    public static final int VALUE_TYPE_STRING = 1;
    public static final int VALUE_TYPE_INT = 2;
    public static final int VALUE_TYPE_REFERENCE = 3;
    public static final int VALUE_TYPE_BOOLEAN = 4;
    private static final long NO_NAMESPACE = 0xFFFFFFFFL;
    private final ByteBuffer mXml;
    private StringPool mStringPool;
    private ResourceMap mResourceMap;
    private int mDepth;
    private int mCurrentEvent = 1;
    private String mCurrentElementName;
    private String mCurrentElementNamespace;
    private int mCurrentElementAttributeCount;
    private List<Attribute> mCurrentElementAttributes;
    private ByteBuffer mCurrentElementAttributesContents;
    private int mCurrentElementAttrSizeBytes;

    public AndroidBinXmlParser(ByteBuffer xml) throws XmlParserException {
        Chunk chunk;
        xml.order(ByteOrder.LITTLE_ENDIAN);
        Chunk resXmlChunk = null;
        while (xml.hasRemaining() && (chunk = Chunk.get(xml)) != null) {
            if (chunk.getType() != 3) continue;
            resXmlChunk = chunk;
            break;
        }
        if (resXmlChunk == null) {
            throw new XmlParserException("No XML chunk in file");
        }
        this.mXml = resXmlChunk.getContents();
    }

    public int getDepth() {
        return this.mDepth;
    }

    public int getEventType() {
        return this.mCurrentEvent;
    }

    public String getName() {
        if (this.mCurrentEvent != 3 && this.mCurrentEvent != 4) {
            return null;
        }
        return this.mCurrentElementName;
    }

    public String getNamespace() {
        if (this.mCurrentEvent != 3 && this.mCurrentEvent != 4) {
            return null;
        }
        return this.mCurrentElementNamespace;
    }

    public int getAttributeCount() {
        if (this.mCurrentEvent != 3) {
            return -1;
        }
        return this.mCurrentElementAttributeCount;
    }

    public int getAttributeNameResourceId(int index) throws XmlParserException {
        return this.getAttribute(index).getNameResourceId();
    }

    public String getAttributeName(int index) throws XmlParserException {
        return this.getAttribute(index).getName();
    }

    public String getAttributeNamespace(int index) throws XmlParserException {
        return this.getAttribute(index).getNamespace();
    }

    public int getAttributeValueType(int index) throws XmlParserException {
        int type = this.getAttribute(index).getValueType();
        switch (type) {
            case 3: {
                return 1;
            }
            case 16: 
            case 17: {
                return 2;
            }
            case 1: {
                return 3;
            }
            case 18: {
                return 4;
            }
        }
        return 0;
    }

    public int getAttributeIntValue(int index) throws XmlParserException {
        return this.getAttribute(index).getIntValue();
    }

    public boolean getAttributeBooleanValue(int index) throws XmlParserException {
        return this.getAttribute(index).getBooleanValue();
    }

    public String getAttributeStringValue(int index) throws XmlParserException {
        return this.getAttribute(index).getStringValue();
    }

    private Attribute getAttribute(int index) {
        if (this.mCurrentEvent != 3) {
            throw new IndexOutOfBoundsException("Current event not a START_ELEMENT");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("index must be >= 0");
        }
        if (index >= this.mCurrentElementAttributeCount) {
            throw new IndexOutOfBoundsException("index must be <= attr count (" + this.mCurrentElementAttributeCount + ")");
        }
        this.parseCurrentElementAttributesIfNotParsed();
        return this.mCurrentElementAttributes.get(index);
    }

    public int next() throws XmlParserException {
        Chunk chunk;
        if (this.mCurrentEvent == 4) {
            --this.mDepth;
        }
        while (this.mXml.hasRemaining() && (chunk = Chunk.get(this.mXml)) != null) {
            switch (chunk.getType()) {
                case 1: {
                    if (this.mStringPool != null) {
                        throw new XmlParserException("Multiple string pools not supported");
                    }
                    this.mStringPool = new StringPool(chunk);
                    break;
                }
                case 258: {
                    if (this.mStringPool == null) {
                        throw new XmlParserException("Named element encountered before string pool");
                    }
                    ByteBuffer contents = chunk.getContents();
                    if (contents.remaining() < 20) {
                        throw new XmlParserException("Start element chunk too short. Need at least 20 bytes. Available: " + contents.remaining() + " bytes");
                    }
                    long nsId = AndroidBinXmlParser.getUnsignedInt32(contents);
                    long nameId = AndroidBinXmlParser.getUnsignedInt32(contents);
                    int attrStartOffset = AndroidBinXmlParser.getUnsignedInt16(contents);
                    int attrSizeBytes = AndroidBinXmlParser.getUnsignedInt16(contents);
                    int attrCount = AndroidBinXmlParser.getUnsignedInt16(contents);
                    long attrEndOffset = (long)attrStartOffset + (long)attrCount * (long)attrSizeBytes;
                    contents.position(0);
                    if (attrStartOffset > contents.remaining()) {
                        throw new XmlParserException("Attributes start offset out of bounds: " + attrStartOffset + ", max: " + contents.remaining());
                    }
                    if (attrEndOffset > (long)contents.remaining()) {
                        throw new XmlParserException("Attributes end offset out of bounds: " + attrEndOffset + ", max: " + contents.remaining());
                    }
                    this.mCurrentElementName = this.mStringPool.getString(nameId);
                    this.mCurrentElementNamespace = nsId == 0xFFFFFFFFL ? "" : this.mStringPool.getString(nsId);
                    this.mCurrentElementAttributeCount = attrCount;
                    this.mCurrentElementAttributes = null;
                    this.mCurrentElementAttrSizeBytes = attrSizeBytes;
                    this.mCurrentElementAttributesContents = AndroidBinXmlParser.sliceFromTo(contents, (long)attrStartOffset, attrEndOffset);
                    ++this.mDepth;
                    this.mCurrentEvent = 3;
                    return this.mCurrentEvent;
                }
                case 259: {
                    if (this.mStringPool == null) {
                        throw new XmlParserException("Named element encountered before string pool");
                    }
                    ByteBuffer contents = chunk.getContents();
                    if (contents.remaining() < 8) {
                        throw new XmlParserException("End element chunk too short. Need at least 8 bytes. Available: " + contents.remaining() + " bytes");
                    }
                    long nsId = AndroidBinXmlParser.getUnsignedInt32(contents);
                    long nameId = AndroidBinXmlParser.getUnsignedInt32(contents);
                    this.mCurrentElementName = this.mStringPool.getString(nameId);
                    this.mCurrentElementNamespace = nsId == 0xFFFFFFFFL ? "" : this.mStringPool.getString(nsId);
                    this.mCurrentEvent = 4;
                    this.mCurrentElementAttributes = null;
                    this.mCurrentElementAttributesContents = null;
                    return this.mCurrentEvent;
                }
                case 384: {
                    if (this.mResourceMap != null) {
                        throw new XmlParserException("Multiple resource maps not supported");
                    }
                    this.mResourceMap = new ResourceMap(chunk);
                    break;
                }
            }
        }
        this.mCurrentEvent = 2;
        return this.mCurrentEvent;
    }

    private void parseCurrentElementAttributesIfNotParsed() {
        if (this.mCurrentElementAttributes != null) {
            return;
        }
        this.mCurrentElementAttributes = new ArrayList<Attribute>(this.mCurrentElementAttributeCount);
        for (int i2 = 0; i2 < this.mCurrentElementAttributeCount; ++i2) {
            int startPosition = i2 * this.mCurrentElementAttrSizeBytes;
            ByteBuffer attr = AndroidBinXmlParser.sliceFromTo(this.mCurrentElementAttributesContents, startPosition, startPosition + this.mCurrentElementAttrSizeBytes);
            long nsId = AndroidBinXmlParser.getUnsignedInt32(attr);
            long nameId = AndroidBinXmlParser.getUnsignedInt32(attr);
            attr.position(attr.position() + 7);
            int valueType = AndroidBinXmlParser.getUnsignedInt8(attr);
            long valueData = AndroidBinXmlParser.getUnsignedInt32(attr);
            this.mCurrentElementAttributes.add(new Attribute(nsId, nameId, valueType, (int)valueData, this.mStringPool, this.mResourceMap));
        }
    }

    private static ByteBuffer sliceFromTo(ByteBuffer source, long start, long end) {
        if (start < 0L) {
            throw new IllegalArgumentException("start: " + start);
        }
        if (end < start) {
            throw new IllegalArgumentException("end < start: " + end + " < " + start);
        }
        int capacity = source.capacity();
        if (end > (long)source.capacity()) {
            throw new IllegalArgumentException("end > capacity: " + end + " > " + capacity);
        }
        return AndroidBinXmlParser.sliceFromTo(source, (int)start, (int)end);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ByteBuffer sliceFromTo(ByteBuffer source, int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("start: " + start);
        }
        if (end < start) {
            throw new IllegalArgumentException("end < start: " + end + " < " + start);
        }
        int capacity = source.capacity();
        if (end > source.capacity()) {
            throw new IllegalArgumentException("end > capacity: " + end + " > " + capacity);
        }
        int originalLimit = source.limit();
        int originalPosition = source.position();
        try {
            source.position(0);
            source.limit(end);
            source.position(start);
            ByteBuffer result = source.slice();
            result.order(source.order());
            ByteBuffer byteBuffer = result;
            return byteBuffer;
        }
        finally {
            source.position(0);
            source.limit(originalLimit);
            source.position(originalPosition);
        }
    }

    private static int getUnsignedInt8(ByteBuffer buffer) {
        return buffer.get() & 0xFF;
    }

    private static int getUnsignedInt16(ByteBuffer buffer) {
        return buffer.getShort() & 0xFFFF;
    }

    private static long getUnsignedInt32(ByteBuffer buffer) {
        return (long)buffer.getInt() & 0xFFFFFFFFL;
    }

    private static long getUnsignedInt32(ByteBuffer buffer, int position) {
        return (long)buffer.getInt(position) & 0xFFFFFFFFL;
    }

    public static class XmlParserException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public XmlParserException(String message) {
            super(message);
        }

        public XmlParserException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class ResourceMap {
        private final ByteBuffer mChunkContents;
        private final int mEntryCount;

        public ResourceMap(Chunk chunk) throws XmlParserException {
            this.mChunkContents = chunk.getContents().slice();
            this.mChunkContents.order(chunk.getContents().order());
            this.mEntryCount = this.mChunkContents.remaining() / 4;
        }

        public int getResourceId(long index) {
            if (index < 0L || index >= (long)this.mEntryCount) {
                return 0;
            }
            int idx = (int)index;
            return this.mChunkContents.getInt(idx * 4);
        }
    }

    private static class StringPool {
        private static final int FLAG_UTF8 = 256;
        private final ByteBuffer mChunkContents;
        private final ByteBuffer mStringsSection;
        private final int mStringCount;
        private final boolean mUtf8Encoded;
        private final Map<Integer, String> mCachedStrings = new HashMap<Integer, String>();

        public StringPool(Chunk chunk) throws XmlParserException {
            ByteBuffer header = chunk.getHeader();
            int headerSizeBytes = header.remaining();
            header.position(8);
            if (header.remaining() < 20) {
                throw new XmlParserException("XML chunk's header too short. Required at least 20 bytes. Available: " + header.remaining() + " bytes");
            }
            long stringCount = AndroidBinXmlParser.getUnsignedInt32(header);
            if (stringCount > Integer.MAX_VALUE) {
                throw new XmlParserException("Too many strings: " + stringCount);
            }
            this.mStringCount = (int)stringCount;
            long styleCount = AndroidBinXmlParser.getUnsignedInt32(header);
            if (styleCount > Integer.MAX_VALUE) {
                throw new XmlParserException("Too many styles: " + styleCount);
            }
            long flags = AndroidBinXmlParser.getUnsignedInt32(header);
            long stringsStartOffset = AndroidBinXmlParser.getUnsignedInt32(header);
            long stylesStartOffset = AndroidBinXmlParser.getUnsignedInt32(header);
            ByteBuffer contents = chunk.getContents();
            if (this.mStringCount > 0) {
                int stringsSectionEndOffsetInContents;
                int stringsSectionStartOffsetInContents = (int)(stringsStartOffset - (long)headerSizeBytes);
                if (styleCount > 0L) {
                    if (stylesStartOffset < stringsStartOffset) {
                        throw new XmlParserException("Styles offset (" + stylesStartOffset + ") < strings offset (" + stringsStartOffset + ")");
                    }
                    stringsSectionEndOffsetInContents = (int)(stylesStartOffset - (long)headerSizeBytes);
                } else {
                    stringsSectionEndOffsetInContents = contents.remaining();
                }
                this.mStringsSection = AndroidBinXmlParser.sliceFromTo(contents, stringsSectionStartOffsetInContents, stringsSectionEndOffsetInContents);
            } else {
                this.mStringsSection = ByteBuffer.allocate(0);
            }
            this.mUtf8Encoded = (flags & 0x100L) != 0L;
            this.mChunkContents = contents;
        }

        public String getString(long index) throws XmlParserException {
            if (index < 0L) {
                throw new XmlParserException("Unsuported string index: " + index);
            }
            if (index >= (long)this.mStringCount) {
                throw new XmlParserException("Unsuported string index: " + index + ", max: " + (this.mStringCount - 1));
            }
            int idx = (int)index;
            String result = this.mCachedStrings.get(idx);
            if (result != null) {
                return result;
            }
            long offsetInStringsSection = AndroidBinXmlParser.getUnsignedInt32(this.mChunkContents, idx * 4);
            if (offsetInStringsSection >= (long)this.mStringsSection.capacity()) {
                throw new XmlParserException("Offset of string idx " + idx + " out of bounds: " + offsetInStringsSection + ", max: " + (this.mStringsSection.capacity() - 1));
            }
            this.mStringsSection.position((int)offsetInStringsSection);
            result = this.mUtf8Encoded ? StringPool.getLengthPrefixedUtf8EncodedString(this.mStringsSection) : StringPool.getLengthPrefixedUtf16EncodedString(this.mStringsSection);
            this.mCachedStrings.put(idx, result);
            return result;
        }

        private static String getLengthPrefixedUtf16EncodedString(ByteBuffer encoded) throws XmlParserException {
            int arrOffset;
            byte[] arr;
            int lengthChars = AndroidBinXmlParser.getUnsignedInt16(encoded);
            if ((lengthChars & 0x8000) != 0) {
                lengthChars = (lengthChars & Short.MAX_VALUE) << 16 | AndroidBinXmlParser.getUnsignedInt16(encoded);
            }
            if (lengthChars > 0x3FFFFFFF) {
                throw new XmlParserException("String too long: " + lengthChars + " uint16s");
            }
            int lengthBytes = lengthChars * 2;
            if (encoded.hasArray()) {
                arr = encoded.array();
                arrOffset = encoded.arrayOffset() + encoded.position();
                encoded.position(encoded.position() + lengthBytes);
            } else {
                arr = new byte[lengthBytes];
                arrOffset = 0;
                encoded.get(arr);
            }
            if (arr[arrOffset + lengthBytes] != 0 || arr[arrOffset + lengthBytes + 1] != 0) {
                throw new XmlParserException("UTF-16 encoded form of string not NULL terminated");
            }
            try {
                return new String(arr, arrOffset, lengthBytes, "UTF-16LE");
            }
            catch (UnsupportedEncodingException e2) {
                throw new RuntimeException("UTF-16LE character encoding not supported", e2);
            }
        }

        private static String getLengthPrefixedUtf8EncodedString(ByteBuffer encoded) throws XmlParserException {
            int arrOffset;
            byte[] arr;
            int lengthBytes = AndroidBinXmlParser.getUnsignedInt8(encoded);
            if ((lengthBytes & 0x80) != 0) {
                lengthBytes = (lengthBytes & 0x7F) << 8 | AndroidBinXmlParser.getUnsignedInt8(encoded);
            }
            if (((lengthBytes = AndroidBinXmlParser.getUnsignedInt8(encoded)) & 0x80) != 0) {
                lengthBytes = (lengthBytes & 0x7F) << 8 | AndroidBinXmlParser.getUnsignedInt8(encoded);
            }
            if (encoded.hasArray()) {
                arr = encoded.array();
                arrOffset = encoded.arrayOffset() + encoded.position();
                encoded.position(encoded.position() + lengthBytes);
            } else {
                arr = new byte[lengthBytes];
                arrOffset = 0;
                encoded.get(arr);
            }
            if (arr[arrOffset + lengthBytes] != 0) {
                throw new XmlParserException("UTF-8 encoded form of string not NULL terminated");
            }
            try {
                return new String(arr, arrOffset, lengthBytes, "UTF-8");
            }
            catch (UnsupportedEncodingException e2) {
                throw new RuntimeException("UTF-8 character encoding not supported", e2);
            }
        }
    }

    private static class Chunk {
        public static final int TYPE_STRING_POOL = 1;
        public static final int TYPE_RES_XML = 3;
        public static final int RES_XML_TYPE_START_ELEMENT = 258;
        public static final int RES_XML_TYPE_END_ELEMENT = 259;
        public static final int RES_XML_TYPE_RESOURCE_MAP = 384;
        static final int HEADER_MIN_SIZE_BYTES = 8;
        private final int mType;
        private final ByteBuffer mHeader;
        private final ByteBuffer mContents;

        public Chunk(int type, ByteBuffer header, ByteBuffer contents) {
            this.mType = type;
            this.mHeader = header;
            this.mContents = contents;
        }

        public ByteBuffer getContents() {
            ByteBuffer result = this.mContents.slice();
            result.order(this.mContents.order());
            return result;
        }

        public ByteBuffer getHeader() {
            ByteBuffer result = this.mHeader.slice();
            result.order(this.mHeader.order());
            return result;
        }

        public int getType() {
            return this.mType;
        }

        public static Chunk get(ByteBuffer input) throws XmlParserException {
            if (input.remaining() < 8) {
                input.position(input.limit());
                return null;
            }
            int originalPosition = input.position();
            int type = AndroidBinXmlParser.getUnsignedInt16(input);
            int headerSize = AndroidBinXmlParser.getUnsignedInt16(input);
            long chunkSize = AndroidBinXmlParser.getUnsignedInt32(input);
            long chunkRemaining = chunkSize - 8L;
            if (chunkRemaining > (long)input.remaining()) {
                input.position(input.limit());
                return null;
            }
            if (headerSize < 8) {
                throw new XmlParserException("Malformed chunk: header too short: " + headerSize + " bytes");
            }
            if ((long)headerSize > chunkSize) {
                throw new XmlParserException("Malformed chunk: header too long: " + headerSize + " bytes. Chunk size: " + chunkSize + " bytes");
            }
            int contentStartPosition = originalPosition + headerSize;
            long chunkEndPosition = (long)originalPosition + chunkSize;
            Chunk chunk = new Chunk(type, AndroidBinXmlParser.sliceFromTo(input, originalPosition, contentStartPosition), AndroidBinXmlParser.sliceFromTo(input, contentStartPosition, chunkEndPosition));
            input.position((int)chunkEndPosition);
            return chunk;
        }
    }

    private static class Attribute {
        private static final int TYPE_REFERENCE = 1;
        private static final int TYPE_STRING = 3;
        private static final int TYPE_INT_DEC = 16;
        private static final int TYPE_INT_HEX = 17;
        private static final int TYPE_INT_BOOLEAN = 18;
        private final long mNsId;
        private final long mNameId;
        private final int mValueType;
        private final int mValueData;
        private final StringPool mStringPool;
        private final ResourceMap mResourceMap;

        private Attribute(long nsId, long nameId, int valueType, int valueData, StringPool stringPool, ResourceMap resourceMap) {
            this.mNsId = nsId;
            this.mNameId = nameId;
            this.mValueType = valueType;
            this.mValueData = valueData;
            this.mStringPool = stringPool;
            this.mResourceMap = resourceMap;
        }

        public int getNameResourceId() {
            return this.mResourceMap != null ? this.mResourceMap.getResourceId(this.mNameId) : 0;
        }

        public String getName() throws XmlParserException {
            return this.mStringPool.getString(this.mNameId);
        }

        public String getNamespace() throws XmlParserException {
            return this.mNsId != 0xFFFFFFFFL ? this.mStringPool.getString(this.mNsId) : "";
        }

        public int getValueType() {
            return this.mValueType;
        }

        public int getIntValue() throws XmlParserException {
            switch (this.mValueType) {
                case 1: 
                case 16: 
                case 17: 
                case 18: {
                    return this.mValueData;
                }
            }
            throw new XmlParserException("Cannot coerce to int: value type " + this.mValueType);
        }

        public boolean getBooleanValue() throws XmlParserException {
            switch (this.mValueType) {
                case 18: {
                    return this.mValueData != 0;
                }
            }
            throw new XmlParserException("Cannot coerce to boolean: value type " + this.mValueType);
        }

        public String getStringValue() throws XmlParserException {
            switch (this.mValueType) {
                case 3: {
                    return this.mStringPool.getString((long)this.mValueData & 0xFFFFFFFFL);
                }
                case 16: {
                    return Integer.toString(this.mValueData);
                }
                case 17: {
                    return "0x" + Integer.toHexString(this.mValueData);
                }
                case 18: {
                    return Boolean.toString(this.mValueData != 0);
                }
                case 1: {
                    return "@" + Integer.toHexString(this.mValueData);
                }
            }
            throw new XmlParserException("Cannot coerce to string: value type " + this.mValueType);
        }
    }
}

