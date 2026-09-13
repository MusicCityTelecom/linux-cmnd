/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Base64Variant
 *  com.fasterxml.jackson.core.FormatSchema
 *  com.fasterxml.jackson.core.JsonLocation
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.core.JsonParser$NumberType
 *  com.fasterxml.jackson.core.JsonStreamContext
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.StreamReadCapability
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.core.base.ParserMinimalBase
 *  com.fasterxml.jackson.core.io.IOContext
 *  com.fasterxml.jackson.core.util.ByteArrayBuilder
 *  com.fasterxml.jackson.core.util.JacksonFeature
 *  com.fasterxml.jackson.core.util.JacksonFeatureSet
 */
package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.JacksonFeature;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.PackageVersion;
import com.fasterxml.jackson.dataformat.javaprop.io.JPropReadContext;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNode;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNodeBuilder;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

public class JavaPropsParser
extends ParserMinimalBase {
    protected static final JavaPropsSchema DEFAULT_SCHEMA = new JavaPropsSchema();
    protected static final JacksonFeatureSet<StreamReadCapability> STREAM_READ_CAPABILITIES = DEFAULT_READ_CAPABILITIES.with((JacksonFeature)StreamReadCapability.UNTYPED_SCALARS);
    protected ObjectCodec _objectCodec;
    protected final Object _inputSource;
    protected final Map<?, ?> _sourceContent;
    protected JavaPropsSchema _schema = DEFAULT_SCHEMA;
    protected JPropReadContext _readContext;
    protected boolean _closed;
    protected ByteArrayBuilder _byteArrayBuilder;
    protected byte[] _binaryValue;

    @Deprecated
    public JavaPropsParser(IOContext ctxt, Object inputSource, int parserFeatures, ObjectCodec codec, Properties sourceProps) {
        this(ctxt, parserFeatures, inputSource, codec, sourceProps);
    }

    public JavaPropsParser(IOContext ctxt, int parserFeatures, Object inputSource, ObjectCodec codec, Map<?, ?> sourceMap) {
        super(parserFeatures);
        this._objectCodec = codec;
        this._inputSource = inputSource;
        this._sourceContent = sourceMap;
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public void setSchema(FormatSchema schema) {
        if (schema instanceof JavaPropsSchema) {
            this._schema = (JavaPropsSchema)schema;
        } else {
            super.setSchema(schema);
        }
    }

    public JavaPropsSchema getSchema() {
        return this._schema;
    }

    public void close() throws IOException {
        this._closed = true;
        this._readContext = null;
    }

    public boolean isClosed() {
        return this._closed;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public void setCodec(ObjectCodec c) {
        this._objectCodec = c;
    }

    public Object getInputSource() {
        return this._inputSource;
    }

    public boolean canUseSchema(FormatSchema schema) {
        return schema instanceof JavaPropsSchema;
    }

    public boolean requiresCustomCodec() {
        return false;
    }

    public boolean canReadObjectId() {
        return false;
    }

    public boolean canReadTypeId() {
        return false;
    }

    public JacksonFeatureSet<StreamReadCapability> getReadCapabilities() {
        return STREAM_READ_CAPABILITIES;
    }

    public JsonStreamContext getParsingContext() {
        return this._readContext;
    }

    public void overrideCurrentName(String name) {
        this._readContext.overrideCurrentName(name);
    }

    public String getCurrentName() throws IOException {
        JPropReadContext parent;
        if (this._readContext == null) {
            return null;
        }
        if ((this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) && (parent = this._readContext.getParent()) != null) {
            return parent.getCurrentName();
        }
        return this._readContext.getCurrentName();
    }

    public JsonToken nextToken() throws IOException {
        this._binaryValue = null;
        if (this._readContext == null) {
            if (this._closed) {
                return null;
            }
            this._closed = true;
            JPropNode root = JPropNodeBuilder.build(this._sourceContent, this._schema);
            this._readContext = JPropReadContext.create(root);
        }
        while ((this._currToken = this._readContext.nextToken()) == null) {
            this._readContext = this._readContext.nextContext();
            if (this._readContext != null) continue;
            return null;
        }
        return this._currToken;
    }

    public String getText() throws IOException {
        JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_STRING) {
            return this._readContext.getCurrentText();
        }
        if (t == JsonToken.FIELD_NAME) {
            return this._readContext.getCurrentName();
        }
        return t == null ? null : t.asString();
    }

    public boolean hasTextCharacters() {
        return false;
    }

    public char[] getTextCharacters() throws IOException {
        String text = this.getText();
        return text == null ? null : text.toCharArray();
    }

    public int getTextLength() throws IOException {
        String text = this.getText();
        return text == null ? 0 : text.length();
    }

    public int getTextOffset() throws IOException {
        return 0;
    }

    public int getText(Writer writer) throws IOException {
        String str = this.getText();
        if (str == null) {
            return 0;
        }
        writer.write(str);
        return str.length();
    }

    public byte[] getBinaryValue(Base64Variant variant) throws IOException {
        if (this._binaryValue == null) {
            if (this._currToken != JsonToken.VALUE_STRING) {
                this._reportError("Current token (" + this._currToken + ") not VALUE_STRING, can not access as binary");
            }
            ByteArrayBuilder builder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), builder, variant);
            this._binaryValue = builder.toByteArray();
        }
        return this._binaryValue;
    }

    public ByteArrayBuilder _getByteArrayBuilder() {
        if (this._byteArrayBuilder == null) {
            this._byteArrayBuilder = new ByteArrayBuilder();
        } else {
            this._byteArrayBuilder.reset();
        }
        return this._byteArrayBuilder;
    }

    public Object getEmbeddedObject() throws IOException {
        return null;
    }

    public JsonLocation getTokenLocation() {
        return JsonLocation.NA;
    }

    public JsonLocation getCurrentLocation() {
        return JsonLocation.NA;
    }

    public Number getNumberValue() throws IOException {
        return (Number)this._noNumbers();
    }

    public JsonParser.NumberType getNumberType() throws IOException {
        return (JsonParser.NumberType)this._noNumbers();
    }

    public int getIntValue() throws IOException {
        return (Integer)this._noNumbers();
    }

    public long getLongValue() throws IOException {
        return (Long)this._noNumbers();
    }

    public BigInteger getBigIntegerValue() throws IOException {
        return (BigInteger)this._noNumbers();
    }

    public float getFloatValue() throws IOException {
        return ((Float)this._noNumbers()).floatValue();
    }

    public double getDoubleValue() throws IOException {
        return (Double)this._noNumbers();
    }

    public BigDecimal getDecimalValue() throws IOException {
        return (BigDecimal)this._noNumbers();
    }

    protected <T> T _noNumbers() throws IOException {
        this._reportError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
        return null;
    }

    protected void _handleEOF() throws JsonParseException {
        if (this._readContext != null && !this._readContext.inRoot()) {
            this._reportInvalidEOF(": expected close marker for " + this._readContext.typeDesc(), null);
        }
    }
}

