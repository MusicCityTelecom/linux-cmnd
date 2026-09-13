/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.util.DefaultPrettyPrinter
 *  com.fasterxml.jackson.core.util.MinimalPrettyPrinter
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.dataformat.yaml.YAMLFactory
 *  com.fasterxml.jackson.module.paramnames.ParameterNamesModule
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apereo.cas.util.serialization.StringSerializer
 *  org.hjson.JsonValue
 *  org.hjson.Stringify
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.serialization;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.List;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apereo.cas.util.DigestUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.apereo.cas.util.serialization.StringSerializer;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJacksonBackedStringSerializer<T>
implements StringSerializer<T> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJacksonBackedStringSerializer.class);
    @Generated
    private final Object $lock = new Object[0];
    protected static final PrettyPrinter MINIMAL_PRETTY_PRINTER = new MinimalPrettyPrinter();
    private static final long serialVersionUID = -8415599777321259365L;
    private final PrettyPrinter prettyPrinter;
    private ObjectMapper objectMapper;

    protected AbstractJacksonBackedStringSerializer() {
        this((PrettyPrinter)new DefaultPrettyPrinter());
    }

    private boolean isJsonFormat() {
        return !(this.getObjectMapper().getFactory() instanceof YAMLFactory);
    }

    public List<T> fromList(String json) {
        String jsonString = this.isJsonFormat() ? JsonValue.readHjson((String)json).toString() : json;
        return this.readObjectsFromString(jsonString);
    }

    public T from(String json) {
        String jsonString = this.isJsonFormat() ? JsonValue.readHjson((String)json).toString() : json;
        return this.readObjectFromString(jsonString);
    }

    public T from(File json) {
        return (T)FunctionUtils.doAndHandle(() -> {
            String data = this.isJsonFormat() ? JsonValue.readHjson((String)FileUtils.readFileToString((File)json, (Charset)StandardCharsets.UTF_8)).toString() : FileUtils.readFileToString((File)json, (Charset)StandardCharsets.UTF_8);
            return this.readObjectFromString(data);
        }, throwable -> null).get();
    }

    public T from(Reader json) {
        return (T)FunctionUtils.doAndHandle(() -> {
            String data = this.isJsonFormat() ? JsonValue.readHjson((Reader)json).toString() : String.join((CharSequence)"\n", IOUtils.readLines((Reader)json));
            return this.readObjectFromString(data);
        }, throwable -> null).get();
    }

    public T from(Writer writer) {
        return this.from(writer.toString());
    }

    public T from(InputStream json) {
        return (T)FunctionUtils.doAndHandle(() -> {
            String jsonString = this.readJsonFrom(json);
            return this.readObjectFromString(jsonString);
        }, throwable -> null).get();
    }

    protected String readJsonFrom(InputStream json) throws IOException {
        return this.isJsonFormat() ? JsonValue.readHjson((String)IOUtils.toString((InputStream)json, (Charset)StandardCharsets.UTF_8)).toString() : String.join((CharSequence)"\n", IOUtils.readLines((InputStream)json, (Charset)StandardCharsets.UTF_8));
    }

    public void to(OutputStream out, T object) {
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)unused -> {
            try (StringWriter writer = new StringWriter();){
                this.getObjectMapper().writer(this.prettyPrinter).writeValue((Writer)writer, object);
                String hjsonString = this.isJsonFormat() ? JsonValue.readHjson((String)writer.toString()).toString(Stringify.HJSON) : writer.toString();
                IOUtils.write((String)hjsonString, (OutputStream)out, (Charset)StandardCharsets.UTF_8);
            }
        }), new Object[0]);
    }

    public void to(Writer out, T object) {
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)unused -> {
            try (StringWriter writer = new StringWriter();){
                this.getObjectMapper().writer(this.prettyPrinter).writeValue((Writer)writer, object);
                if (this.isJsonFormat()) {
                    Stringify opt = this.prettyPrinter instanceof MinimalPrettyPrinter ? Stringify.PLAIN : Stringify.FORMATTED;
                    JsonValue.readHjson((String)writer.toString()).writeTo(out, opt);
                } else {
                    IOUtils.write((String)writer.toString(), (Writer)out);
                }
            }
        }), new Object[0]);
    }

    public void to(File out, T object) {
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)unused -> {
            block12: {
                try (StringWriter writer = new StringWriter();){
                    this.getObjectMapper().writer(this.prettyPrinter).writeValue((Writer)writer, object);
                    if (this.isJsonFormat()) {
                        try (BufferedWriter fileWriter = Files.newBufferedWriter(out.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);){
                            Stringify opt = this.prettyPrinter instanceof MinimalPrettyPrinter ? Stringify.PLAIN : Stringify.FORMATTED;
                            JsonValue.readHjson((String)writer.toString()).writeTo((Writer)fileWriter, opt);
                            fileWriter.flush();
                            break block12;
                        }
                    }
                    FileUtils.write((File)out, (CharSequence)writer.toString(), (Charset)StandardCharsets.UTF_8);
                }
            }
        }), new Object[0]);
    }

    public String toString(T object) {
        return (String)FunctionUtils.doUnchecked(() -> {
            try (StringWriter writer = new StringWriter();){
                this.to(writer, object);
                String string = writer.toString();
                return string;
            }
        });
    }

    protected void configureObjectMapper(ObjectMapper mapper) {
    }

    protected boolean isDefaultTypingEnabled() {
        return true;
    }

    protected JsonFactory getJsonFactory() {
        return null;
    }

    protected T readObjectFromString(String jsonString) {
        try {
            LOGGER.trace("Attempting to consume [{}]", (Object)jsonString);
            return (T)this.getObjectMapper().readValue(jsonString, this.getTypeToSerialize());
        }
        catch (Exception e) {
            LOGGER.error("Cannot read/parse [{}] to deserialize into type [{}]. This may be caused in the absence of a configuration/support module that knows how to interpret the fragment, specially if the fragment describes a CAS registered service definition. Internal parsing error is [{}]", new Object[]{DigestUtils.abbreviate(jsonString), this.getTypeToSerialize(), e.getMessage()});
            LOGGER.debug(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    protected List<T> readObjectsFromString(String jsonString) {
        try {
            LOGGER.trace("Attempting to consume [{}]", (Object)jsonString);
            JavaType expectedType = this.getObjectMapper().getTypeFactory().constructParametricType(List.class, new Class[]{this.getTypeToSerialize()});
            return (List)this.getObjectMapper().readValue(jsonString, expectedType);
        }
        catch (Exception e) {
            LOGGER.error("Cannot read/parse [{}] to deserialize into List of type [{}].Internal parsing error is [{}]", new Object[]{DigestUtils.abbreviate(jsonString), this.getTypeToSerialize(), e.getMessage()});
            LOGGER.debug(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ObjectMapper getObjectMapper() {
        Object object = this.$lock;
        synchronized (object) {
            if (this.objectMapper == null) {
                this.objectMapper = ((JacksonObjectMapperFactory)((JacksonObjectMapperFactory.JacksonObjectMapperFactoryBuilder)((JacksonObjectMapperFactory.JacksonObjectMapperFactoryBuilder)JacksonObjectMapperFactory.builder().defaultTypingEnabled(this.isDefaultTypingEnabled())).jsonFactory(this.getJsonFactory())).build()).toObjectMapper().registerModule((Module)new ParameterNamesModule());
                this.configureObjectMapper(this.objectMapper);
            }
            return this.objectMapper;
        }
    }

    @Generated
    public PrettyPrinter getPrettyPrinter() {
        return this.prettyPrinter;
    }

    @Generated
    protected AbstractJacksonBackedStringSerializer(PrettyPrinter prettyPrinter) {
        this.prettyPrinter = prettyPrinter;
    }
}

