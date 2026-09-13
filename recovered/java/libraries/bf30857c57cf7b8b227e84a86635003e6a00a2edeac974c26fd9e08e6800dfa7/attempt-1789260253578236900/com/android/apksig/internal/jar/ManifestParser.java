/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.jar;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;

public class ManifestParser {
    private final byte[] mManifest;
    private int mOffset;
    private int mEndOffset;
    private byte[] mBufferedLine;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public ManifestParser(byte[] data) {
        this(data, 0, data.length);
    }

    public ManifestParser(byte[] data, int offset, int length) {
        this.mManifest = data;
        this.mOffset = offset;
        this.mEndOffset = offset + length;
    }

    public List<Section> readAllSections() {
        Section section;
        ArrayList<Section> sections = new ArrayList<Section>();
        while ((section = this.readSection()) != null) {
            sections.add(section);
        }
        return sections;
    }

    public Section readSection() {
        int sectionStartOffset;
        String attr;
        do {
            sectionStartOffset = this.mOffset;
            attr = this.readAttribute();
            if (attr != null) continue;
            return null;
        } while (attr.length() == 0);
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(ManifestParser.parseAttr(attr));
        while ((attr = this.readAttribute()) != null && attr.length() != 0) {
            attrs.add(ManifestParser.parseAttr(attr));
        }
        int sectionEndOffset = this.mOffset;
        int sectionSizeBytes = sectionEndOffset - sectionStartOffset;
        return new Section(sectionStartOffset, sectionSizeBytes, attrs);
    }

    private static Attribute parseAttr(String attr) {
        int delimiterIndex = attr.indexOf(": ");
        if (delimiterIndex == -1) {
            return new Attribute(attr, "");
        }
        return new Attribute(attr.substring(0, delimiterIndex), attr.substring(delimiterIndex + ": ".length()));
    }

    private String readAttribute() {
        byte[] bytes = this.readAttributeBytes();
        if (bytes == null) {
            return null;
        }
        if (bytes.length == 0) {
            return "";
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private byte[] readAttributeBytes() {
        byte[] attrLine;
        if (this.mBufferedLine != null && this.mBufferedLine.length == 0) {
            this.mBufferedLine = null;
            return EMPTY_BYTE_ARRAY;
        }
        byte[] line = this.readLine();
        if (line == null) {
            if (this.mBufferedLine != null) {
                byte[] result = this.mBufferedLine;
                this.mBufferedLine = null;
                return result;
            }
            return null;
        }
        if (line.length == 0) {
            if (this.mBufferedLine != null) {
                byte[] result = this.mBufferedLine;
                this.mBufferedLine = EMPTY_BYTE_ARRAY;
                return result;
            }
            return EMPTY_BYTE_ARRAY;
        }
        if (this.mBufferedLine == null) {
            attrLine = line;
        } else {
            if (line.length == 0 || line[0] != 32) {
                byte[] result = this.mBufferedLine;
                this.mBufferedLine = line;
                return result;
            }
            attrLine = this.mBufferedLine;
            this.mBufferedLine = null;
            attrLine = ManifestParser.concat(attrLine, line, 1, line.length - 1);
        }
        while (true) {
            if ((line = this.readLine()) == null) {
                return attrLine;
            }
            if (line.length == 0) {
                this.mBufferedLine = EMPTY_BYTE_ARRAY;
                return attrLine;
            }
            if (line[0] != 32) break;
            attrLine = ManifestParser.concat(attrLine, line, 1, line.length - 1);
        }
        this.mBufferedLine = line;
        return attrLine;
    }

    private static byte[] concat(byte[] arr1, byte[] arr2, int offset2, int length2) {
        byte[] result = new byte[arr1.length + length2];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, offset2, result, arr1.length, length2);
        return result;
    }

    private byte[] readLine() {
        if (this.mOffset >= this.mEndOffset) {
            return null;
        }
        int startOffset = this.mOffset;
        int newlineStartOffset = -1;
        int newlineEndOffset = -1;
        for (int i2 = startOffset; i2 < this.mEndOffset; ++i2) {
            byte b2 = this.mManifest[i2];
            if (b2 == 13) {
                newlineStartOffset = i2;
                int nextIndex = i2 + 1;
                if (nextIndex < this.mEndOffset && this.mManifest[nextIndex] == 10) {
                    newlineEndOffset = nextIndex + 1;
                    break;
                }
                newlineEndOffset = nextIndex;
                break;
            }
            if (b2 != 10) continue;
            newlineStartOffset = i2;
            newlineEndOffset = i2 + 1;
            break;
        }
        if (newlineStartOffset == -1) {
            newlineStartOffset = this.mEndOffset;
            newlineEndOffset = this.mEndOffset;
        }
        this.mOffset = newlineEndOffset;
        if (newlineStartOffset == startOffset) {
            return EMPTY_BYTE_ARRAY;
        }
        return Arrays.copyOfRange(this.mManifest, startOffset, newlineStartOffset);
    }

    public static class Section {
        private final int mStartOffset;
        private final int mSizeBytes;
        private final String mName;
        private final List<Attribute> mAttributes;

        public Section(int startOffset, int sizeBytes, List<Attribute> attrs) {
            Attribute firstAttr;
            this.mStartOffset = startOffset;
            this.mSizeBytes = sizeBytes;
            String sectionName = null;
            if (!attrs.isEmpty() && "Name".equalsIgnoreCase((firstAttr = attrs.get(0)).getName())) {
                sectionName = firstAttr.getValue();
            }
            this.mName = sectionName;
            this.mAttributes = Collections.unmodifiableList(new ArrayList<Attribute>(attrs));
        }

        public String getName() {
            return this.mName;
        }

        public int getStartOffset() {
            return this.mStartOffset;
        }

        public int getSizeBytes() {
            return this.mSizeBytes;
        }

        public List<Attribute> getAttributes() {
            return this.mAttributes;
        }

        public String getAttributeValue(Attributes.Name name) {
            return this.getAttributeValue(name.toString());
        }

        public String getAttributeValue(String name) {
            for (Attribute attr : this.mAttributes) {
                if (!attr.getName().equalsIgnoreCase(name)) continue;
                return attr.getValue();
            }
            return null;
        }
    }

    public static class Attribute {
        private final String mName;
        private final String mValue;

        public Attribute(String name, String value) {
            this.mName = name;
            this.mValue = value;
        }

        public String getName() {
            return this.mName;
        }

        public String getValue() {
            return this.mValue;
        }
    }
}

