/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Base64Variant
 *  com.fasterxml.jackson.core.FormatSchema
 *  com.fasterxml.jackson.core.JsonGenerationException
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonGenerator$Feature
 *  com.fasterxml.jackson.core.JsonStreamContext
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.SerializableString
 *  com.fasterxml.jackson.core.StreamWriteCapability
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.core.base.GeneratorBase
 *  com.fasterxml.jackson.core.io.IOContext
 *  com.fasterxml.jackson.core.json.JsonWriteContext
 *  com.fasterxml.jackson.core.util.JacksonFeatureSet
 */
package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.StreamWriteCapability;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.PackageVersion;
import com.fasterxml.jackson.dataformat.javaprop.io.JPropWriteContext;
import com.fasterxml.jackson.dataformat.javaprop.util.Markers;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public abstract class JavaPropsGenerator
extends GeneratorBase {
    protected static final int SHORT_WRITE = 100;
    protected static final JsonWriteContext BOGUS_WRITE_CONTEXT = JsonWriteContext.createRootContext(null);
    private static final JavaPropsSchema EMPTY_SCHEMA = JavaPropsSchema.emptySchema();
    protected final IOContext _ioContext;
    protected JavaPropsSchema _schema = EMPTY_SCHEMA;
    protected JPropWriteContext _jpropContext;
    protected final StringBuilder _basePath = new StringBuilder(50);
    protected boolean _headerChecked;
    protected int _indentLength;

    public JavaPropsGenerator(IOContext ctxt, int stdFeatures, ObjectCodec codec) {
        super(stdFeatures, codec, BOGUS_WRITE_CONTEXT);
        this._ioContext = ctxt;
        this._jpropContext = JPropWriteContext.createRootContext();
    }

    public Object currentValue() {
        return this._jpropContext.getCurrentValue();
    }

    public Object getCurrentValue() {
        return this._jpropContext.getCurrentValue();
    }

    public void assignCurrentValue(Object v) {
        this._jpropContext.setCurrentValue(v);
    }

    public void setCurrentValue(Object v) {
        this._jpropContext.setCurrentValue(v);
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public JsonGenerator useDefaultPrettyPrinter() {
        return this;
    }

    public JsonGenerator setPrettyPrinter(PrettyPrinter pp) {
        return this;
    }

    public void setSchema(FormatSchema schema) {
        if (schema instanceof JavaPropsSchema) {
            this._schema = (JavaPropsSchema)schema;
            if (this._jpropContext.inRoot()) {
                String prefix;
                String indent = this._schema.lineIndentation();
                int n = this._indentLength = indent == null ? 0 : indent.length();
                if (this._indentLength > 0) {
                    this._basePath.setLength(0);
                    this._basePath.append(indent);
                    this._jpropContext = JPropWriteContext.createRootContext(this._indentLength);
                }
                if ((prefix = this._schema.prefix()) != null) {
                    this._basePath.append(prefix);
                }
            }
            return;
        }
        super.setSchema(schema);
    }

    public FormatSchema getSchema() {
        return this._schema;
    }

    public boolean canUseSchema(FormatSchema schema) {
        return schema instanceof JavaPropsSchema;
    }

    public boolean canWriteObjectId() {
        return false;
    }

    public boolean canWriteTypeId() {
        return false;
    }

    public boolean canWriteBinaryNatively() {
        return false;
    }

    public boolean canOmitFields() {
        return true;
    }

    public boolean canWriteFormattedNumbers() {
        return true;
    }

    public JacksonFeatureSet<StreamWriteCapability> getWriteCapabilities() {
        return DEFAULT_TEXTUAL_WRITE_CAPABILITIES;
    }

    public JsonStreamContext getOutputContext() {
        return this._jpropContext;
    }

    public void writeFieldName(String name) throws IOException {
        String sep;
        if (!this._jpropContext.writeFieldName(name)) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (!this._headerChecked) {
            this._headerChecked = true;
            String header = this._schema.header();
            if (header != null && !header.isEmpty()) {
                this._writeRaw(header);
            }
        }
        this._jpropContext.truncatePath(this._basePath);
        if (this._basePath.length() > this._indentLength && !(sep = this._schema.pathSeparator()).isEmpty()) {
            this._basePath.append(sep);
        }
        this._appendFieldName(this._basePath, name);
    }

    protected abstract void _appendFieldName(StringBuilder var1, String var2);

    public void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._jpropContext = this._jpropContext.createChildArrayContext(this._basePath.length());
    }

    public void writeEndArray() throws IOException {
        if (!this._jpropContext.inArray()) {
            this._reportError("Current context not an Array but " + this._jpropContext.typeDesc());
        }
        this._jpropContext = this._jpropContext.getParent();
    }

    public void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._jpropContext = this._jpropContext.createChildObjectContext(this._basePath.length());
    }

    public void writeEndObject() throws IOException {
        if (!this._jpropContext.inObject()) {
            this._reportError("Current context not an Ibject but " + this._jpropContext.typeDesc());
        }
        this._jpropContext = this._jpropContext.getParent();
    }

    public void writeString(String text) throws IOException {
        if (text == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write String value");
        this._writeEscapedEntry(text);
    }

    public void writeString(char[] text, int offset, int len) throws IOException {
        this._verifyValueWrite("write String value");
        this._writeEscapedEntry(text, offset, len);
    }

    public void writeRawUTF8String(byte[] text, int offset, int len) throws IOException {
        this._reportUnsupportedOperation();
    }

    public void writeUTF8String(byte[] text, int offset, int len) throws IOException {
        this.writeString(new String(text, offset, len, "UTF-8"));
    }

    public void writeRaw(String text) throws IOException {
        this._writeRaw(text);
    }

    public void writeRaw(String text, int offset, int len) throws IOException {
        this._writeRaw(text.substring(offset, offset + len));
    }

    public void writeRaw(char[] text, int offset, int len) throws IOException {
        this._writeRaw(text, offset, len);
    }

    public void writeRaw(char c) throws IOException {
        this._writeRaw(c);
    }

    public void writeRaw(SerializableString text) throws IOException, JsonGenerationException {
        this.writeRaw(text.toString());
    }

    public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException {
        if (data == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write Binary value");
        if (offset > 0 || offset + len != data.length) {
            data = Arrays.copyOfRange(data, offset, offset + len);
        }
        String encoded = b64variant.encode(data);
        this._writeEscapedEntry(encoded);
    }

    public void writeBoolean(boolean state) throws IOException {
        this._verifyValueWrite("write boolean value");
        this._writeUnescapedEntry(state ? "true" : "false");
    }

    public void writeNumber(int i) throws IOException {
        this._verifyValueWrite("write number");
        this._writeUnescapedEntry(String.valueOf(i));
    }

    public void writeNumber(long l) throws IOException {
        this._verifyValueWrite("write number");
        this._writeUnescapedEntry(String.valueOf(l));
    }

    public void writeNumber(BigInteger v) throws IOException {
        if (v == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write number");
        this._writeUnescapedEntry(String.valueOf(v));
    }

    public void writeNumber(double d) throws IOException {
        this._verifyValueWrite("write number");
        this._writeUnescapedEntry(String.valueOf(d));
    }

    public void writeNumber(float f) throws IOException {
        this._verifyValueWrite("write number");
        this._writeUnescapedEntry(String.valueOf(f));
    }

    public void writeNumber(BigDecimal dec) throws IOException {
        if (dec == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write number");
        String str = this.isEnabled(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) ? dec.toPlainString() : dec.toString();
        this._writeUnescapedEntry(str);
    }

    public void writeNumber(String encodedValue) throws IOException {
        if (encodedValue == null) {
            this.writeNull();
            return;
        }
        this._verifyValueWrite("write number");
        this._writeUnescapedEntry(encodedValue);
    }

    public void writeNull() throws IOException {
        this._verifyValueWrite("write null value");
        this._writeUnescapedEntry("");
    }

    protected void _verifyValueWrite(String typeMsg) throws IOException {
        if (!this._jpropContext.writeValue()) {
            this._reportError("Can not " + typeMsg + ", expecting field name");
        }
        if (this._jpropContext.inArray()) {
            this._jpropContext.truncatePath(this._basePath);
            int ix = this._jpropContext.getCurrentIndex() + this._schema.firstArrayOffset();
            if (this._schema.writeIndexUsingMarkers()) {
                Markers m = this._schema.indexMarker();
                this._basePath.append(m.getStart());
                this._basePath.append(ix);
                this._basePath.append(m.getEnd());
            } else {
                String sep;
                if (this._basePath.length() > 0 && !(sep = this._schema.pathSeparator()).isEmpty()) {
                    this._basePath.append(sep);
                }
                this._basePath.append(ix);
            }
        }
    }

    protected abstract void _writeEscapedEntry(String var1) throws IOException;

    protected abstract void _writeEscapedEntry(char[] var1, int var2, int var3) throws IOException;

    protected abstract void _writeUnescapedEntry(String var1) throws IOException;

    protected abstract void _writeRaw(char var1) throws IOException;

    protected abstract void _writeRaw(String var1) throws IOException;

    protected abstract void _writeRaw(StringBuilder var1) throws IOException;

    protected abstract void _writeRaw(char[] var1, int var2, int var3) throws IOException;
}

