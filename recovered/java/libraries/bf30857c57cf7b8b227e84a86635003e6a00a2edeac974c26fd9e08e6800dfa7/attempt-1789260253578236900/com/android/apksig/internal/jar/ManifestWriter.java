/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.jar;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.jar.Attributes;

public abstract class ManifestWriter {
    private static final byte[] CRLF = new byte[]{13, 10};
    private static final int MAX_LINE_LENGTH = 70;

    private ManifestWriter() {
    }

    public static void writeMainSection(OutputStream out, Attributes attributes) throws IOException {
        String manifestVersion = attributes.getValue(Attributes.Name.MANIFEST_VERSION);
        if (manifestVersion == null) {
            throw new IllegalArgumentException("Mandatory " + Attributes.Name.MANIFEST_VERSION + " attribute missing");
        }
        ManifestWriter.writeAttribute(out, Attributes.Name.MANIFEST_VERSION, manifestVersion);
        if (attributes.size() > 1) {
            SortedMap<String, String> namedAttributes = ManifestWriter.getAttributesSortedByName(attributes);
            namedAttributes.remove(Attributes.Name.MANIFEST_VERSION.toString());
            ManifestWriter.writeAttributes(out, namedAttributes);
        }
        ManifestWriter.writeSectionDelimiter(out);
    }

    public static void writeIndividualSection(OutputStream out, String name, Attributes attributes) throws IOException {
        ManifestWriter.writeAttribute(out, "Name", name);
        if (!attributes.isEmpty()) {
            ManifestWriter.writeAttributes(out, ManifestWriter.getAttributesSortedByName(attributes));
        }
        ManifestWriter.writeSectionDelimiter(out);
    }

    static void writeSectionDelimiter(OutputStream out) throws IOException {
        out.write(CRLF);
    }

    static void writeAttribute(OutputStream out, Attributes.Name name, String value) throws IOException {
        ManifestWriter.writeAttribute(out, name.toString(), value);
    }

    private static void writeAttribute(OutputStream out, String name, String value) throws IOException {
        ManifestWriter.writeLine(out, name + ": " + value);
    }

    private static void writeLine(OutputStream out, String line) throws IOException {
        int chunkLength;
        byte[] lineBytes = line.getBytes(StandardCharsets.UTF_8);
        int offset = 0;
        boolean firstLine = true;
        for (int remaining = lineBytes.length; remaining > 0; remaining -= chunkLength) {
            if (firstLine) {
                chunkLength = Math.min(remaining, 70);
            } else {
                out.write(CRLF);
                out.write(32);
                chunkLength = Math.min(remaining, 69);
            }
            out.write(lineBytes, offset, chunkLength);
            offset += chunkLength;
            firstLine = false;
        }
        out.write(CRLF);
    }

    static SortedMap<String, String> getAttributesSortedByName(Attributes attributes) {
        Set<Map.Entry<Object, Object>> attributesEntries = attributes.entrySet();
        TreeMap<String, String> namedAttributes = new TreeMap<String, String>();
        for (Map.Entry<Object, Object> attribute : attributesEntries) {
            String attrName = attribute.getKey().toString();
            String attrValue = attribute.getValue().toString();
            namedAttributes.put(attrName, attrValue);
        }
        return namedAttributes;
    }

    static void writeAttributes(OutputStream out, SortedMap<String, String> attributesSortedByName) throws IOException {
        for (Map.Entry<String, String> attribute : attributesSortedByName.entrySet()) {
            String attrName = attribute.getKey();
            String attrValue = attribute.getValue();
            ManifestWriter.writeAttribute(out, attrName, attrValue);
        }
    }
}

