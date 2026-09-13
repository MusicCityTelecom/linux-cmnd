/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class JSONObjectConverter
extends AbstractHttpMessageConverter<JSONObject> {
    private static final Logger LOG = LoggerFactory.getLogger(JSONObjectConverter.class);

    public JSONObjectConverter() {
        super(new MediaType("application", "json", StandardCharsets.UTF_8), new MediaType("application", "*+json", StandardCharsets.UTF_8));
    }

    @Override
    protected JSONObject readInternal(Class<? extends JSONObject> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(JSONObject.class);
    }

    @Override
    protected void writeInternal(JSONObject t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = null;
        if (null != t) {
            text = t.toString();
            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            out.write(bytes);
        }
    }

    public static String mergeObjects(JSONObject target, JSONObject source) {
        for (String key : source.keySet()) {
            if (source.get(key) instanceof JSONObject) {
                if (target.has(key) && target.get(key) instanceof JSONObject) {
                    JSONObjectConverter.mergeObjects((JSONObject)target.get(key), (JSONObject)source.get(key));
                    continue;
                }
                target.put(key, source.getJSONObject(key));
                continue;
            }
            if (source.get(key) instanceof JSONArray) {
                target.put(key, source.getJSONArray(key));
                continue;
            }
            target.put(key, source.get(key));
        }
        return target.toString();
    }

    public static String mergeObjects(String origTarget, JSONObject source) {
        String jsonResult;
        JSONObject target = null;
        try {
            if (origTarget == null || "".equalsIgnoreCase(origTarget)) {
                origTarget = "{}";
            }
            target = new JSONObject(origTarget);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("origTarget:<{}> converted to json object error:{}", (Object)origTarget, (Object)ex);
        }
        if (target == null) {
            target = new JSONObject();
        }
        if (!TpvStringUtils.isJSONString(jsonResult = JSONObjectConverter.mergeObjects(target, source))) {
            LOG.error("origTarget:<{}>, source:<{}>, merged error json result:<{}>", origTarget, source, jsonResult);
            return "{}";
        }
        return jsonResult;
    }
}

