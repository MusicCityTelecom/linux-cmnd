/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.LinkedCaseInsensitiveMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.util.Map;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;

public class ObjectToJsonTransformer
extends AbstractTransformer {
    public static final String JSON_CONTENT_TYPE = "application/json";
    private final JsonObjectMapper<?, ?> jsonObjectMapper;
    private final ResultType resultType;
    private volatile String contentType = "application/json";
    private volatile boolean contentTypeExplicitlySet = false;

    public ObjectToJsonTransformer() {
        this(JsonObjectMapperProvider.newInstance());
    }

    public ObjectToJsonTransformer(JsonObjectMapper<?, ?> jsonObjectMapper) {
        this(jsonObjectMapper, ResultType.STRING);
    }

    public ObjectToJsonTransformer(ResultType resultType) {
        this(JsonObjectMapperProvider.newInstance(), resultType);
    }

    public ObjectToJsonTransformer(JsonObjectMapper<?, ?> jsonObjectMapper, ResultType resultType) {
        Assert.notNull(jsonObjectMapper, (String)"jsonObjectMapper must not be null");
        Assert.notNull((Object)((Object)resultType), (String)"'resultType' must not be null");
        this.jsonObjectMapper = jsonObjectMapper;
        this.resultType = resultType;
    }

    public void setContentType(String contentType) {
        Assert.notNull((Object)contentType, (String)"'contentType' must not be null");
        this.contentTypeExplicitlySet = true;
        this.contentType = contentType.trim();
    }

    @Override
    public String getComponentType() {
        return "object-to-json-transformer";
    }

    @Override
    protected Object doTransform(Message<?> message) {
        Object payload = this.buildJsonPayload(message.getPayload());
        LinkedCaseInsensitiveMap headers = new LinkedCaseInsensitiveMap();
        headers.putAll(message.getHeaders());
        if (headers.containsKey("contentType")) {
            if (this.contentTypeExplicitlySet && StringUtils.hasLength((String)this.contentType)) {
                headers.put("contentType", this.contentType);
            }
        } else if (StringUtils.hasLength((String)this.contentType)) {
            headers.put("contentType", this.contentType);
        }
        this.jsonObjectMapper.populateJavaTypes((Map<String, Object>)headers, message.getPayload());
        return this.getMessageBuilderFactory().withPayload(payload).copyHeaders((Map<String, ?>)headers).build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object buildJsonPayload(Object payload) {
        try {
            switch (this.resultType) {
                case STRING: {
                    return this.jsonObjectMapper.toJson(payload);
                }
                case NODE: {
                    return this.jsonObjectMapper.toJsonNode(payload);
                }
                case BYTES: {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
                        this.jsonObjectMapper.toJson(payload, new OutputStreamWriter(baos));
                        byte[] byArray = baos.toByteArray();
                        return byArray;
                    }
                }
            }
            throw new IllegalArgumentException("Unsupported ResultType provided: " + (Object)((Object)this.resultType));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static enum ResultType {
        STRING,
        NODE,
        BYTES;

    }
}

