/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Base64Variant
 *  com.fasterxml.jackson.core.Base64Variants
 *  com.fasterxml.jackson.core.FormatFeature
 *  com.fasterxml.jackson.core.JsonLocation
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonParser$Feature
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.StreamReadCapability
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.core.base.ParserBase
 *  com.fasterxml.jackson.core.io.IOContext
 *  com.fasterxml.jackson.core.util.BufferRecycler
 *  com.fasterxml.jackson.core.util.JacksonFeatureSet
 *  org.yaml.snakeyaml.error.Mark
 *  org.yaml.snakeyaml.error.MarkedYAMLException
 *  org.yaml.snakeyaml.error.YAMLException
 *  org.yaml.snakeyaml.events.AliasEvent
 *  org.yaml.snakeyaml.events.CollectionStartEvent
 *  org.yaml.snakeyaml.events.Event
 *  org.yaml.snakeyaml.events.Event$ID
 *  org.yaml.snakeyaml.events.MappingStartEvent
 *  org.yaml.snakeyaml.events.NodeEvent
 *  org.yaml.snakeyaml.events.ScalarEvent
 *  org.yaml.snakeyaml.nodes.NodeId
 *  org.yaml.snakeyaml.nodes.Tag
 *  org.yaml.snakeyaml.parser.ParserImpl
 *  org.yaml.snakeyaml.reader.StreamReader
 *  org.yaml.snakeyaml.resolver.Resolver
 */
package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.dataformat.yaml.JacksonYAMLParseException;
import com.fasterxml.jackson.dataformat.yaml.PackageVersion;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.error.MarkedYAMLException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;

public class YAMLParser
extends ParserBase {
    protected ObjectCodec _objectCodec;
    protected int _formatFeatures;
    protected boolean _cfgEmptyStringsToNull;
    protected final Reader _reader;
    protected final ParserImpl _yamlParser;
    protected final Resolver _yamlResolver = new Resolver();
    protected Event _lastEvent;
    protected Event _lastTagEvent;
    protected String _textValue;
    protected String _cleanedTextValue;
    protected String _currentFieldName;
    protected boolean _currentIsAlias;
    protected String _currentAnchor;

    public YAMLParser(IOContext ctxt, BufferRecycler br, int parserFeatures, int formatFeatures, ObjectCodec codec, Reader reader) {
        super(ctxt, parserFeatures);
        this._objectCodec = codec;
        this._formatFeatures = formatFeatures;
        this._reader = reader;
        this._yamlParser = new ParserImpl(new StreamReader(reader));
        this._cfgEmptyStringsToNull = Feature.EMPTY_STRING_AS_NULL.enabledIn(formatFeatures);
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public void setCodec(ObjectCodec c) {
        this._objectCodec = c;
    }

    public boolean isCurrentAlias() {
        return this._currentIsAlias;
    }

    @Deprecated
    public String getCurrentAnchor() {
        return this._currentAnchor;
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public boolean requiresCustomCodec() {
        return false;
    }

    public boolean canReadObjectId() {
        return true;
    }

    public boolean canReadTypeId() {
        return true;
    }

    public JacksonFeatureSet<StreamReadCapability> getReadCapabilities() {
        return DEFAULT_READ_CAPABILITIES;
    }

    protected void _closeInput() throws IOException {
        if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._reader.close();
        }
    }

    public int getFormatFeatures() {
        return this._formatFeatures;
    }

    public JsonParser overrideFormatFeatures(int values, int mask) {
        this._formatFeatures = this._formatFeatures & ~mask | values & mask;
        this._cfgEmptyStringsToNull = Feature.EMPTY_STRING_AS_NULL.enabledIn(this._formatFeatures);
        return this;
    }

    public JsonParser enable(Feature f) {
        this._formatFeatures |= f.getMask();
        this._cfgEmptyStringsToNull = Feature.EMPTY_STRING_AS_NULL.enabledIn(this._formatFeatures);
        return this;
    }

    public JsonParser disable(Feature f) {
        this._formatFeatures &= ~f.getMask();
        this._cfgEmptyStringsToNull = Feature.EMPTY_STRING_AS_NULL.enabledIn(this._formatFeatures);
        return this;
    }

    public JsonParser configure(Feature f, boolean state) {
        if (state) {
            this.enable(f);
        } else {
            this.disable(f);
        }
        return this;
    }

    public boolean isEnabled(Feature f) {
        return (this._formatFeatures & f.getMask()) != 0;
    }

    public JsonLocation getTokenLocation() {
        if (this._lastEvent == null) {
            return JsonLocation.NA;
        }
        return this._locationFor(this._lastEvent.getStartMark());
    }

    public JsonLocation getCurrentLocation() {
        if (this._lastEvent == null) {
            return JsonLocation.NA;
        }
        return this._locationFor(this._lastEvent.getEndMark());
    }

    protected JsonLocation _locationFor(Mark m) {
        if (m == null) {
            return new JsonLocation(this._ioContext.contentReference(), -1L, -1, -1);
        }
        return new JsonLocation(this._ioContext.contentReference(), (long)m.getIndex(), m.getLine() + 1, m.getColumn() + 1);
    }

    public JsonToken nextToken() throws IOException {
        this._currentIsAlias = false;
        this._binaryValue = null;
        if (this._closed) {
            return null;
        }
        while (true) {
            Event evt;
            try {
                evt = this._yamlParser.getEvent();
            }
            catch (YAMLException e) {
                if (e instanceof org.yaml.snakeyaml.error.MarkedYAMLException) {
                    throw MarkedYAMLException.from((JsonParser)this, (org.yaml.snakeyaml.error.MarkedYAMLException)e);
                }
                throw new JacksonYAMLParseException((JsonParser)this, e.getMessage(), (Exception)((Object)e));
            }
            if (evt == null) {
                this._currentAnchor = null;
                this._lastTagEvent = null;
                this._currToken = null;
                return null;
            }
            this._lastEvent = evt;
            if (this._parsingContext.inObject()) {
                if (this._currToken != JsonToken.FIELD_NAME) {
                    String name;
                    boolean firstEntry;
                    if (!evt.is(Event.ID.Scalar)) {
                        this._currentAnchor = null;
                        this._lastTagEvent = null;
                        if (evt.is(Event.ID.MappingEnd)) {
                            if (!this._parsingContext.inObject()) {
                                this._reportMismatchedEndMarker(125, ']');
                            }
                            this._parsingContext = this._parsingContext.getParent();
                            this._currToken = JsonToken.END_OBJECT;
                            return this._currToken;
                        }
                        this._reportError("Expected a field name (Scalar value in YAML), got this instead: " + evt);
                    }
                    ScalarEvent scalar = (ScalarEvent)evt;
                    String newAnchor = scalar.getAnchor();
                    boolean bl = firstEntry = this._currToken == JsonToken.START_OBJECT;
                    if (newAnchor != null || !firstEntry) {
                        this._currentAnchor = scalar.getAnchor();
                    }
                    if (!firstEntry) {
                        this._lastTagEvent = evt;
                    }
                    this._currentFieldName = name = scalar.getValue();
                    this._parsingContext.setCurrentName(name);
                    this._currToken = JsonToken.FIELD_NAME;
                    return this._currToken;
                }
            } else if (this._parsingContext.inArray()) {
                this._parsingContext.expectComma();
            }
            this._currentAnchor = null;
            this._lastTagEvent = evt;
            if (evt.is(Event.ID.Scalar)) {
                JsonToken t;
                this._currToken = t = this._decodeScalar((ScalarEvent)evt);
                return t;
            }
            if (evt.is(Event.ID.MappingStart)) {
                Mark m = evt.getStartMark();
                MappingStartEvent map = (MappingStartEvent)evt;
                this._currentAnchor = map.getAnchor();
                this._parsingContext = this._parsingContext.createChildObjectContext(m.getLine(), m.getColumn());
                this._currToken = JsonToken.START_OBJECT;
                return this._currToken;
            }
            if (evt.is(Event.ID.MappingEnd)) {
                this._reportError("Not expecting END_OBJECT but a value");
            }
            if (evt.is(Event.ID.SequenceStart)) {
                Mark m = evt.getStartMark();
                this._currentAnchor = ((NodeEvent)evt).getAnchor();
                this._parsingContext = this._parsingContext.createChildArrayContext(m.getLine(), m.getColumn());
                this._currToken = JsonToken.START_ARRAY;
                return this._currToken;
            }
            if (evt.is(Event.ID.SequenceEnd)) {
                if (!this._parsingContext.inArray()) {
                    this._reportMismatchedEndMarker(93, '}');
                }
                this._parsingContext = this._parsingContext.getParent();
                this._currToken = JsonToken.END_ARRAY;
                return this._currToken;
            }
            if (evt.is(Event.ID.DocumentEnd) || evt.is(Event.ID.DocumentStart)) continue;
            if (evt.is(Event.ID.Alias)) {
                AliasEvent alias = (AliasEvent)evt;
                this._currentIsAlias = true;
                this._textValue = alias.getAnchor();
                this._cleanedTextValue = null;
                this._currToken = JsonToken.VALUE_STRING;
                return this._currToken;
            }
            if (evt.is(Event.ID.StreamEnd)) {
                this.close();
                this._currToken = null;
                return null;
            }
            if (!evt.is(Event.ID.StreamStart)) continue;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonToken _decodeScalar(ScalarEvent scalar) throws IOException {
        String value;
        this._textValue = value = scalar.getValue();
        this._cleanedTextValue = null;
        if (!this._cfgEmptyStringsToNull && value.isEmpty()) {
            return JsonToken.VALUE_STRING;
        }
        String typeTag = scalar.getTag();
        int len = value.length();
        if (typeTag == null || typeTag.equals("!")) {
            Tag nodeTag = this._yamlResolver.resolve(NodeId.scalar, value, scalar.getImplicit().canOmitTagInPlainScalar());
            if (nodeTag == Tag.STR) {
                return JsonToken.VALUE_STRING;
            }
            if (nodeTag == Tag.INT) {
                return this._decodeNumberScalar(value, len);
            }
            if (nodeTag == Tag.FLOAT) {
                this._numTypesValid = 0;
                return this._cleanYamlFloat(value);
            }
            if (nodeTag == Tag.BOOL) {
                Boolean B = this._matchYAMLBoolean(value, len);
                if (B == null) return JsonToken.VALUE_STRING;
                return B != false ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
            }
            if (nodeTag != Tag.NULL) return JsonToken.VALUE_STRING;
            return JsonToken.VALUE_NULL;
        }
        if (typeTag.startsWith("tag:yaml.org,2002:") && (typeTag = typeTag.substring("tag:yaml.org,2002:".length())).contains(",")) {
            typeTag = typeTag.split(",")[0];
        }
        if ("binary".equals(typeTag)) {
            value = value.trim();
            try {
                this._binaryValue = Base64Variants.MIME.decode(value);
                return JsonToken.VALUE_EMBEDDED_OBJECT;
            }
            catch (IllegalArgumentException e) {
                this._reportError(e.getMessage());
            }
            return JsonToken.VALUE_EMBEDDED_OBJECT;
        }
        if ("bool".equals(typeTag)) {
            Boolean B = this._matchYAMLBoolean(value, len);
            if (B == null) return JsonToken.VALUE_STRING;
            return B != false ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
        }
        if ("int".equals(typeTag)) {
            return this._decodeNumberScalar(value, len);
        }
        if ("float".equals(typeTag)) {
            this._numTypesValid = 0;
            return this._cleanYamlFloat(value);
        }
        if (!"null".equals(typeTag)) return JsonToken.VALUE_STRING;
        return JsonToken.VALUE_NULL;
    }

    protected Boolean _matchYAMLBoolean(String value, int len) {
        switch (len) {
            case 1: {
                switch (value.charAt(0)) {
                    case 'Y': 
                    case 'y': {
                        return Boolean.TRUE;
                    }
                    case 'N': 
                    case 'n': {
                        return Boolean.FALSE;
                    }
                }
                break;
            }
            case 2: {
                if ("no".equalsIgnoreCase(value)) {
                    return Boolean.FALSE;
                }
                if (!"on".equalsIgnoreCase(value)) break;
                return Boolean.TRUE;
            }
            case 3: {
                if ("yes".equalsIgnoreCase(value)) {
                    return Boolean.TRUE;
                }
                if (!"off".equalsIgnoreCase(value)) break;
                return Boolean.FALSE;
            }
            case 4: {
                if (!"true".equalsIgnoreCase(value)) break;
                return Boolean.TRUE;
            }
            case 5: {
                if (!"false".equalsIgnoreCase(value)) break;
                return Boolean.FALSE;
            }
        }
        return null;
    }

    protected JsonToken _decodeNumberScalar(String value, int len) {
        block15: {
            int i;
            char ch = value.charAt(0);
            if (ch == '-') {
                this._numberNegative = true;
                i = 1;
            } else if (ch == '+') {
                this._numberNegative = false;
                if (len == 1) {
                    return null;
                }
                i = 1;
            } else {
                this._numberNegative = false;
                i = 0;
            }
            if (len == i) {
                return null;
            }
            if (value.charAt(i) == '0') {
                if (++i == len) {
                    this._numberInt = 0;
                    this._numTypesValid = 1;
                    return JsonToken.VALUE_NUMBER_INT;
                }
                ch = value.charAt(i);
                switch (ch) {
                    case 'B': 
                    case 'b': {
                        return this._decodeNumberIntBinary(value, i + 1, len, this._numberNegative);
                    }
                    case 'X': 
                    case 'x': {
                        return this._decodeNumberIntHex(value, i + 1, len, this._numberNegative);
                    }
                    case '0': 
                    case '1': 
                    case '2': 
                    case '3': 
                    case '4': 
                    case '5': 
                    case '6': 
                    case '7': 
                    case '8': 
                    case '9': 
                    case '_': {
                        return this._decodeNumberIntOctal(value, i, len, this._numberNegative);
                    }
                }
                return JsonToken.VALUE_STRING;
            }
            boolean underscores = false;
            do {
                char c;
                if ((c = value.charAt(i)) <= '9' && c >= '0') continue;
                if (c != '_') break block15;
                underscores = true;
            } while (++i != len);
            this._numTypesValid = 0;
            if (underscores) {
                return this._cleanYamlInt(value);
            }
            this._cleanedTextValue = this._textValue;
            return JsonToken.VALUE_NUMBER_INT;
        }
        return JsonToken.VALUE_STRING;
    }

    protected JsonToken _decodeNumberIntBinary(String value, int i, int origLen, boolean negative) {
        String cleansed = this._cleanUnderscores(value, i, origLen);
        int digitLen = cleansed.length();
        if (digitLen <= 31) {
            int v = Integer.parseInt(cleansed, 2);
            if (negative) {
                v = -v;
            }
            this._numberInt = v;
            this._numTypesValid = 1;
            return JsonToken.VALUE_NUMBER_INT;
        }
        if (digitLen <= 63) {
            return this._decodeFromLong(Long.parseLong(cleansed, 2), negative, digitLen == 32);
        }
        return this._decodeFromBigInteger(new BigInteger(cleansed, 2), negative);
    }

    protected JsonToken _decodeNumberIntOctal(String value, int i, int origLen, boolean negative) {
        String cleansed = this._cleanUnderscores(value, i, origLen);
        int digitLen = cleansed.length();
        if (digitLen <= 10) {
            int v = Integer.parseInt(cleansed, 8);
            if (negative) {
                v = -v;
            }
            this._numberInt = v;
            this._numTypesValid = 1;
            return JsonToken.VALUE_NUMBER_INT;
        }
        if (digitLen <= 21) {
            return this._decodeFromLong(Long.parseLong(cleansed, 8), negative, false);
        }
        return this._decodeFromBigInteger(new BigInteger(cleansed, 8), negative);
    }

    protected JsonToken _decodeNumberIntHex(String value, int i, int origLen, boolean negative) {
        String cleansed = this._cleanUnderscores(value, i, origLen);
        int digitLen = cleansed.length();
        if (digitLen <= 7) {
            int v = Integer.parseInt(cleansed, 16);
            if (negative) {
                v = -v;
            }
            this._numberInt = v;
            this._numTypesValid = 1;
            return JsonToken.VALUE_NUMBER_INT;
        }
        if (digitLen <= 15) {
            return this._decodeFromLong(Long.parseLong(cleansed, 16), negative, digitLen == 8);
        }
        return this._decodeFromBigInteger(new BigInteger(cleansed, 16), negative);
    }

    private JsonToken _decodeFromLong(long unsignedValue, boolean negative, boolean checkIfInt) {
        long actualValue;
        if (negative) {
            actualValue = -unsignedValue;
            if (checkIfInt && actualValue >= Integer.MIN_VALUE) {
                this._numberInt = (int)actualValue;
                this._numTypesValid = 1;
                return JsonToken.VALUE_NUMBER_INT;
            }
        } else {
            if (checkIfInt && unsignedValue < Integer.MAX_VALUE) {
                this._numberInt = (int)unsignedValue;
                this._numTypesValid = 1;
                return JsonToken.VALUE_NUMBER_INT;
            }
            actualValue = unsignedValue;
        }
        this._numberLong = actualValue;
        this._numTypesValid = 2;
        return JsonToken.VALUE_NUMBER_INT;
    }

    private JsonToken _decodeFromBigInteger(BigInteger unsignedValue, boolean negative) {
        this._numberBigInt = negative ? unsignedValue.negate() : unsignedValue;
        this._numTypesValid = 4;
        return JsonToken.VALUE_NUMBER_INT;
    }

    public boolean hasTextCharacters() {
        return false;
    }

    public String getText() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textValue;
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._currentFieldName;
        }
        if (this._currToken != null) {
            if (this._currToken.isScalarValue()) {
                return this._textValue;
            }
            return this._currToken.asString();
        }
        return null;
    }

    public String getCurrentName() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._currentFieldName;
        }
        return super.getCurrentName();
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

    public Object getEmbeddedObject() throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return this._binaryValue;
        }
        return null;
    }

    public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException {
        byte[] b = this.getBinaryValue(b64variant);
        out.write(b);
        return b.length;
    }

    protected void _parseNumericValue(int expType) throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            int len = this._cleanedTextValue.length();
            if (this._numberNegative) {
                --len;
            }
            if (len <= 9) {
                this._numberInt = Integer.parseInt(this._cleanedTextValue);
                this._numTypesValid = 1;
                return;
            }
            if (len <= 18) {
                long l = Long.parseLong(this._cleanedTextValue);
                if (len == 10) {
                    if (this._numberNegative) {
                        if (l >= Integer.MIN_VALUE) {
                            this._numberInt = (int)l;
                            this._numTypesValid = 1;
                            return;
                        }
                    } else if (l <= Integer.MAX_VALUE) {
                        this._numberInt = (int)l;
                        this._numTypesValid = 1;
                        return;
                    }
                }
                this._numberLong = l;
                this._numTypesValid = 2;
                return;
            }
            try {
                BigInteger n = new BigInteger(this._cleanedTextValue);
                if (len == 19 && n.bitLength() <= 63) {
                    this._numberLong = n.longValue();
                    this._numTypesValid = 2;
                    return;
                }
                this._numberBigInt = n;
                this._numTypesValid = 4;
                return;
            }
            catch (NumberFormatException nex) {
                this._wrapError("Malformed numeric value '" + this._textValue + "'", nex);
            }
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            String str = this._cleanedTextValue;
            try {
                if (expType == 16) {
                    this._numberBigDecimal = new BigDecimal(str);
                    this._numTypesValid = 16;
                } else {
                    this._numberDouble = Double.parseDouble(str);
                    this._numTypesValid = 8;
                }
            }
            catch (NumberFormatException nex) {
                this._wrapError("Malformed numeric value '" + this._textValue + "'", nex);
            }
            return;
        }
        this._reportError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
    }

    protected int _parseIntValue() throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            int len = this._cleanedTextValue.length();
            if (this._numberNegative) {
                --len;
            }
            if (len <= 9) {
                this._numTypesValid = 1;
                this._numberInt = Integer.parseInt(this._cleanedTextValue);
                return this._numberInt;
            }
        }
        this._parseNumericValue(1);
        if ((this._numTypesValid & 1) == 0) {
            this.convertNumberToInt();
        }
        return this._numberInt;
    }

    public String getObjectId() throws IOException {
        return this._currentAnchor;
    }

    public String getTypeId() throws IOException {
        String tag;
        if (this._lastTagEvent instanceof CollectionStartEvent) {
            tag = ((CollectionStartEvent)this._lastTagEvent).getTag();
        } else if (this._lastTagEvent instanceof ScalarEvent) {
            tag = ((ScalarEvent)this._lastTagEvent).getTag();
        } else {
            return null;
        }
        if (tag != null) {
            while (tag.startsWith("!")) {
                tag = tag.substring(1);
            }
            return tag;
        }
        return null;
    }

    private JsonToken _cleanYamlInt(String str) {
        int i;
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        int n = i = str.charAt(0) == '+' ? 1 : 0;
        while (i < len) {
            char c = str.charAt(i);
            if (c != '_') {
                sb.append(c);
            }
            ++i;
        }
        this._cleanedTextValue = sb.toString();
        return JsonToken.VALUE_NUMBER_INT;
    }

    private String _cleanUnderscores(String str, int i, int len) {
        StringBuilder sb = new StringBuilder(len);
        while (i < len) {
            char ch = str.charAt(i);
            if (ch != '_') {
                sb.append(ch);
            }
            ++i;
        }
        if (sb.length() == len) {
            return str;
        }
        return sb.toString();
    }

    private JsonToken _cleanYamlFloat(String str) {
        int i;
        int len = str.length();
        int ix = str.indexOf(95);
        if (ix < 0 || len == 0) {
            this._cleanedTextValue = str;
            return JsonToken.VALUE_NUMBER_FLOAT;
        }
        StringBuilder sb = new StringBuilder(len);
        int n = i = str.charAt(0) == '+' ? 1 : 0;
        while (i < len) {
            char c = str.charAt(i);
            if (c != '_') {
                sb.append(c);
            }
            ++i;
        }
        this._cleanedTextValue = sb.toString();
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    public static enum Feature implements FormatFeature
    {
        EMPTY_STRING_AS_NULL(true);

        final boolean _defaultState;
        final int _mask;

        public static int collectDefaults() {
            int flags = 0;
            for (Feature f : Feature.values()) {
                if (!f.enabledByDefault()) continue;
                flags |= f.getMask();
            }
            return flags;
        }

        private Feature(boolean defaultState) {
            this._defaultState = defaultState;
            this._mask = 1 << this.ordinal();
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public boolean enabledIn(int flags) {
            return (flags & this._mask) != 0;
        }

        public int getMask() {
            return this._mask;
        }
    }
}

