/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.jooq.lambda.Unchecked
 *  org.springframework.http.HttpOutputMessage
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 *  org.springframework.http.server.ServletServerHttpResponse
 */
package org.apereo.cas.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.jooq.lambda.Unchecked;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

public final class JsonUtils {
    private static final ObjectMapper MAPPER = ((JacksonObjectMapperFactory)JacksonObjectMapperFactory.builder().build()).toObjectMapper();

    public static void render(Object model, HttpServletResponse response) {
        Unchecked.consumer(o -> {
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            jsonConverter.setPrettyPrint(true);
            MediaType jsonMimeType = MediaType.APPLICATION_JSON;
            jsonConverter.write(model, jsonMimeType, (HttpOutputMessage)new ServletServerHttpResponse(response));
        }).accept(model);
    }

    public static void render(HttpServletResponse response) {
        HashMap map = new HashMap();
        response.setStatus(200);
        JsonUtils.render(map, response);
    }

    public static void renderException(Exception ex, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("error", ex.getMessage());
        map.put("stacktrace", Arrays.deepToString(ex.getStackTrace()));
        JsonUtils.renderException(map, response);
    }

    private static void renderException(Map<String, Object> model, HttpServletResponse response) {
        response.setStatus(400);
        model.put("status", 400);
        JsonUtils.render(model, response);
    }

    public static boolean isValidJson(String json) {
        return (Boolean)FunctionUtils.doAndHandle(() -> !MAPPER.readTree(json).isEmpty(), t -> false).get();
    }

    @Generated
    private JsonUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

