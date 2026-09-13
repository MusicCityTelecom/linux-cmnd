/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.FormatSchema
 *  com.fasterxml.jackson.core.JsonEncoding
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.StreamReadFeature
 *  com.fasterxml.jackson.core.TSFBuilder
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.core.format.InputAccessor
 *  com.fasterxml.jackson.core.format.MatchStrength
 *  com.fasterxml.jackson.core.io.IOContext
 */
package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactoryBuilder;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsGenerator;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsParser;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.PackageVersion;
import com.fasterxml.jackson.dataformat.javaprop.impl.PropertiesBackedGenerator;
import com.fasterxml.jackson.dataformat.javaprop.impl.WriterBackedGenerator;
import com.fasterxml.jackson.dataformat.javaprop.io.Latin1Reader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class JavaPropsFactory
extends JsonFactory {
    private static final long serialVersionUID = 1L;
    public static final String FORMAT_NAME_JAVA_PROPERTIES = "java_properties";
    protected static final String CHARSET_ID_LATIN1 = "ISO-8859-1";

    public JavaPropsFactory() {
    }

    public JavaPropsFactory(ObjectCodec codec) {
        super(codec);
    }

    protected JavaPropsFactory(JavaPropsFactory src, ObjectCodec oc) {
        super((JsonFactory)src, oc);
    }

    protected JavaPropsFactory(JavaPropsFactoryBuilder b) {
        super((TSFBuilder)b, false);
    }

    public JavaPropsFactoryBuilder rebuild() {
        return new JavaPropsFactoryBuilder(this);
    }

    public static JavaPropsFactoryBuilder builder() {
        return new JavaPropsFactoryBuilder();
    }

    public JavaPropsFactory copy() {
        this._checkInvalidCopy(JavaPropsFactory.class);
        return new JavaPropsFactory(this, null);
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public String getFormatName() {
        return FORMAT_NAME_JAVA_PROPERTIES;
    }

    public MatchStrength hasFormat(InputAccessor acc) throws IOException {
        return MatchStrength.INCONCLUSIVE;
    }

    public boolean requiresPropertyOrdering() {
        return false;
    }

    public boolean canHandleBinaryNatively() {
        return false;
    }

    public boolean canUseCharArrays() {
        return false;
    }

    public boolean canUseSchema(FormatSchema schema) {
        return schema instanceof JavaPropsSchema;
    }

    public JavaPropsParser createParser(Map<?, ?> content) {
        return new JavaPropsParser(this._createContext(this._createContentReference(content), true), this._parserFeatures, content, this._objectCodec, content);
    }

    @Deprecated
    public JavaPropsParser createParser(Properties props) {
        return new JavaPropsParser(this._createContext(this._createContentReference(props), true), this._parserFeatures, props, this._objectCodec, props);
    }

    @Deprecated
    public JavaPropsGenerator createGenerator(Properties props) {
        IOContext ctxt = this._createContext(this._createContentReference(props), true);
        return new PropertiesBackedGenerator(ctxt, props, this._generatorFeatures, this._objectCodec);
    }

    public JavaPropsGenerator createGenerator(Map<?, ?> target, JavaPropsSchema schema) {
        IOContext ctxt = this._createContext(this._createContentReference(target), true);
        return new PropertiesBackedGenerator(ctxt, target, this._generatorFeatures, this._objectCodec);
    }

    public JsonParser createParser(File f) throws IOException {
        IOContext ctxt = this._createContext(this._createContentReference(f), true);
        return this._createParser(this._decorate(new FileInputStream(f), ctxt), ctxt);
    }

    public JsonParser createParser(URL url) throws IOException {
        IOContext ctxt = this._createContext(this._createContentReference(url), true);
        return this._createParser(this._decorate(this._optimizedStreamFromURL(url), ctxt), ctxt);
    }

    public JsonParser createParser(InputStream in) throws IOException {
        IOContext ctxt = this._createContext(this._createContentReference(in), false);
        return this._createParser(this._decorate(in, ctxt), ctxt);
    }

    public JsonParser createParser(byte[] data) throws IOException {
        InputStream in;
        IOContext ctxt = this._createContext(this._createContentReference(data), true);
        if (this._inputDecorator != null && (in = this._inputDecorator.decorate(ctxt, data, 0, data.length)) != null) {
            return this._createParser(in, ctxt);
        }
        return this._createParser(data, 0, data.length, ctxt);
    }

    public JsonParser createParser(byte[] data, int offset, int len) throws IOException {
        InputStream in;
        IOContext ctxt = this._createContext(this._createContentReference(data, offset, len), true);
        if (this._inputDecorator != null && (in = this._inputDecorator.decorate(ctxt, data, offset, len)) != null) {
            return this._createParser(in, ctxt);
        }
        return this._createParser(data, offset, len, ctxt);
    }

    public JsonGenerator createGenerator(OutputStream out, JsonEncoding enc) throws IOException {
        IOContext ctxt = this._createContext(this._createContentReference(out), false);
        ctxt.setEncoding(enc);
        return this._createJavaPropsGenerator(ctxt, this._generatorFeatures, this._objectCodec, this._decorate(out, ctxt));
    }

    public JsonGenerator createGenerator(OutputStream out) throws IOException {
        IOContext ctxt = this._createContext(this._createContentReference(out), false);
        return this._createJavaPropsGenerator(ctxt, this._generatorFeatures, this._objectCodec, this._decorate(out, ctxt));
    }

    protected JsonParser _createParser(InputStream in, IOContext ctxt) throws IOException {
        Properties props = this._loadProperties(in, ctxt);
        return new JavaPropsParser(ctxt, this._parserFeatures, in, this._objectCodec, props);
    }

    protected JsonParser _createParser(Reader r, IOContext ctxt) throws IOException {
        Properties props = this._loadProperties(r, ctxt);
        return new JavaPropsParser(ctxt, this._parserFeatures, r, this._objectCodec, props);
    }

    protected JsonParser _createParser(char[] data, int offset, int len, IOContext ctxt, boolean recyclable) throws IOException {
        return this._createParser(new CharArrayReader(data, offset, len), ctxt);
    }

    protected JsonParser _createParser(byte[] data, int offset, int len, IOContext ctxt) throws IOException {
        return this._createParser(new Latin1Reader(data, offset, len), ctxt);
    }

    protected JsonGenerator _createGenerator(Writer out, IOContext ctxt) throws IOException {
        return new WriterBackedGenerator(ctxt, out, this._generatorFeatures, this._objectCodec);
    }

    protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt) throws IOException {
        return this._createJavaPropsGenerator(ctxt, this._generatorFeatures, this._objectCodec, out);
    }

    protected Writer _createWriter(OutputStream out, JsonEncoding enc, IOContext ctxt) throws IOException {
        return new OutputStreamWriter(out, CHARSET_ID_LATIN1);
    }

    protected Properties _loadProperties(InputStream in, IOContext ctxt) throws IOException {
        return this._loadProperties(new Latin1Reader(ctxt, in), ctxt);
    }

    protected Properties _loadProperties(Reader r0, IOContext ctxt) throws IOException {
        Properties props = new Properties();
        if (ctxt.isResourceManaged() || this.isEnabled(StreamReadFeature.AUTO_CLOSE_SOURCE)) {
            try (Reader r = r0;){
                props.load(r);
            }
        } else {
            props.load(r0);
        }
        return props;
    }

    private final JsonGenerator _createJavaPropsGenerator(IOContext ctxt, int stdFeat, ObjectCodec codec, OutputStream out) throws IOException {
        return new WriterBackedGenerator(ctxt, this._createWriter(out, null, ctxt), stdFeat, this._objectCodec);
    }
}

