/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.env;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.invoke.LambdaMetafactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

class OriginTrackedPropertiesLoader {
    private final Resource resource;

    OriginTrackedPropertiesLoader(Resource resource) {
        Assert.notNull((Object)resource, (String)"Resource must not be null");
        this.resource = resource;
    }

    List<Document> load() throws IOException {
        return this.load(true);
    }

    List<Document> load(boolean expandLists) throws IOException {
        ArrayList<Document> documents = new ArrayList<Document>();
        Document document = new Document();
        StringBuilder buffer = new StringBuilder();
        try (CharacterReader reader = new CharacterReader(this.resource);){
            while (reader.read()) {
                if (reader.isPoundCharacter()) {
                    if (this.isNewDocument(reader)) {
                        if (!document.isEmpty()) {
                            documents.add(document);
                        }
                        document = new Document();
                        continue;
                    }
                    if (document.isEmpty() && !documents.isEmpty()) {
                        document = (Document)documents.remove(documents.size() - 1);
                    }
                    reader.setLastLineComment(true);
                    reader.skipComment();
                    continue;
                }
                reader.setLastLineComment(false);
                this.loadKeyAndValue(expandLists, document, reader, buffer);
            }
        }
        if (!document.isEmpty() && !documents.contains(document)) {
            documents.add(document);
        }
        return documents;
    }

    private void loadKeyAndValue(boolean expandLists, Document document, CharacterReader reader, StringBuilder buffer) throws IOException {
        String key = this.loadKey(buffer, reader).trim();
        if (expandLists && key.endsWith("[]")) {
            key = key.substring(0, key.length() - 2);
            int index = 0;
            do {
                OriginTrackedValue value = this.loadValue(buffer, reader, true);
                document.put(key + "[" + index++ + "]", value);
                if (reader.isEndOfLine()) continue;
                reader.read();
            } while (!reader.isEndOfLine());
        } else {
            OriginTrackedValue value = this.loadValue(buffer, reader, false);
            document.put(key, value);
        }
    }

    private String loadKey(StringBuilder buffer, CharacterReader reader) throws IOException {
        buffer.setLength(0);
        boolean previousWhitespace = false;
        while (!reader.isEndOfLine()) {
            if (reader.isPropertyDelimiter()) {
                reader.read();
                return buffer.toString();
            }
            if (!reader.isWhiteSpace() && previousWhitespace) {
                return buffer.toString();
            }
            previousWhitespace = reader.isWhiteSpace();
            buffer.append(reader.getCharacter());
            reader.read();
        }
        return buffer.toString();
    }

    private OriginTrackedValue loadValue(StringBuilder buffer, CharacterReader reader, boolean splitLists) throws IOException {
        buffer.setLength(0);
        while (reader.isWhiteSpace() && !reader.isEndOfLine()) {
            reader.read();
        }
        TextResourceOrigin.Location location = reader.getLocation();
        while (!(reader.isEndOfLine() || splitLists && reader.isListDelimiter())) {
            buffer.append(reader.getCharacter());
            reader.read();
        }
        TextResourceOrigin origin = new TextResourceOrigin(this.resource, location);
        return OriginTrackedValue.of(buffer.toString(), origin);
    }

    /*
     * Unable to fully structure code
     */
    private boolean isNewDocument(CharacterReader reader) throws IOException {
        if (CharacterReader.access$200(reader)) {
            return false;
        }
        v0 = result = reader.getLocation().getColumn() == 0 && reader.isPoundCharacter() != false;
        if (!result) ** GOTO lbl-1000
        if (this.readAndExpect(reader, (BooleanSupplier)LambdaMetafactory.metafactory(null, null, null, ()Z, isHyphenCharacter(), ()Z)((CharacterReader)reader))) {
            v1 = true;
        } else lbl-1000:
        // 2 sources

        {
            v1 = result = false;
        }
        if (!result) ** GOTO lbl-1000
        if (this.readAndExpect(reader, (BooleanSupplier)LambdaMetafactory.metafactory(null, null, null, ()Z, isHyphenCharacter(), ()Z)((CharacterReader)reader))) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = result = false;
        }
        if (!result) ** GOTO lbl-1000
        if (this.readAndExpect(reader, (BooleanSupplier)LambdaMetafactory.metafactory(null, null, null, ()Z, isHyphenCharacter(), ()Z)((CharacterReader)reader))) {
            v3 = true;
        } else lbl-1000:
        // 2 sources

        {
            v3 = result = false;
        }
        if (!reader.isEndOfLine()) {
            reader.read();
            CharacterReader.access$300(reader);
        }
        return result != false && reader.isEndOfLine() != false;
    }

    private boolean readAndExpect(CharacterReader reader, BooleanSupplier check) throws IOException {
        reader.read();
        return check.getAsBoolean();
    }

    static class Document {
        private final Map<String, OriginTrackedValue> values = new LinkedHashMap<String, OriginTrackedValue>();

        Document() {
        }

        void put(String key, OriginTrackedValue value) {
            if (!key.isEmpty()) {
                this.values.put(key, value);
            }
        }

        boolean isEmpty() {
            return this.values.isEmpty();
        }

        Map<String, OriginTrackedValue> asMap() {
            return this.values;
        }
    }

    private static class CharacterReader
    implements Closeable {
        private static final String[] ESCAPES = new String[]{"trnf", "\t\r\n\f"};
        private final LineNumberReader reader;
        private int columnNumber = -1;
        private boolean escaped;
        private int character;
        private boolean lastLineComment;

        CharacterReader(Resource resource) throws IOException {
            this.reader = new LineNumberReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.ISO_8859_1));
        }

        @Override
        public void close() throws IOException {
            this.reader.close();
        }

        boolean read() throws IOException {
            return this.read(false);
        }

        boolean read(boolean wrappedLine) throws IOException {
            this.escaped = false;
            this.character = this.reader.read();
            ++this.columnNumber;
            if (this.columnNumber == 0) {
                this.skipWhitespace();
                if (!wrappedLine && this.character == 33) {
                    this.skipComment();
                }
            }
            if (this.character == 92) {
                this.escaped = true;
                this.readEscaped();
            } else if (this.character == 10) {
                this.columnNumber = -1;
            }
            return !this.isEndOfFile();
        }

        private void skipWhitespace() throws IOException {
            while (this.isWhiteSpace()) {
                this.character = this.reader.read();
                ++this.columnNumber;
            }
        }

        private void setLastLineComment(boolean lastLineComment) {
            this.lastLineComment = lastLineComment;
        }

        private boolean isLastLineComment() {
            return this.lastLineComment;
        }

        private void skipComment() throws IOException {
            while (this.character != 10 && this.character != -1) {
                this.character = this.reader.read();
            }
            this.columnNumber = -1;
        }

        private void readEscaped() throws IOException {
            this.character = this.reader.read();
            int escapeIndex = ESCAPES[0].indexOf(this.character);
            if (escapeIndex != -1) {
                this.character = ESCAPES[1].charAt(escapeIndex);
            } else if (this.character == 10) {
                this.columnNumber = -1;
                this.read(true);
            } else if (this.character == 117) {
                this.readUnicode();
            }
        }

        private void readUnicode() throws IOException {
            this.character = 0;
            for (int i = 0; i < 4; ++i) {
                int digit = this.reader.read();
                if (digit >= 48 && digit <= 57) {
                    this.character = (this.character << 4) + digit - 48;
                    continue;
                }
                if (digit >= 97 && digit <= 102) {
                    this.character = (this.character << 4) + digit - 97 + 10;
                    continue;
                }
                if (digit >= 65 && digit <= 70) {
                    this.character = (this.character << 4) + digit - 65 + 10;
                    continue;
                }
                throw new IllegalStateException("Malformed \\uxxxx encoding.");
            }
        }

        boolean isWhiteSpace() {
            return !this.escaped && (this.character == 32 || this.character == 9 || this.character == 12);
        }

        boolean isEndOfFile() {
            return this.character == -1;
        }

        boolean isEndOfLine() {
            return this.character == -1 || !this.escaped && this.character == 10;
        }

        boolean isListDelimiter() {
            return !this.escaped && this.character == 44;
        }

        boolean isPropertyDelimiter() {
            return !this.escaped && (this.character == 61 || this.character == 58);
        }

        char getCharacter() {
            return (char)this.character;
        }

        TextResourceOrigin.Location getLocation() {
            return new TextResourceOrigin.Location(this.reader.getLineNumber(), this.columnNumber);
        }

        boolean isPoundCharacter() {
            return this.character == 35;
        }

        boolean isHyphenCharacter() {
            return this.character == 45;
        }

        static /* synthetic */ boolean access$200(CharacterReader x0) {
            return x0.isLastLineComment();
        }

        static /* synthetic */ void access$300(CharacterReader x0) throws IOException {
            x0.skipWhitespace();
        }
    }
}

