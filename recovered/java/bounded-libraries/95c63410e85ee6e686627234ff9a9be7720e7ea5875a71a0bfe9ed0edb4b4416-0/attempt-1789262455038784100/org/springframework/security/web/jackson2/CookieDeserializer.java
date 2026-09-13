/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.MissingNode
 *  com.fasterxml.jackson.databind.node.NullNode
 *  javax.servlet.http.Cookie
 */
package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import java.io.IOException;
import javax.servlet.http.Cookie;

class CookieDeserializer
extends JsonDeserializer<Cookie> {
    CookieDeserializer() {
    }

    public Cookie deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)jp.getCodec();
        JsonNode jsonNode = (JsonNode)mapper.readTree(jp);
        Cookie cookie = new Cookie(this.readJsonNode(jsonNode, "name").asText(), this.readJsonNode(jsonNode, "value").asText());
        cookie.setComment(this.readJsonNode(jsonNode, "comment").asText());
        cookie.setDomain(this.readJsonNode(jsonNode, "domain").asText());
        cookie.setMaxAge(this.readJsonNode(jsonNode, "maxAge").asInt(-1));
        cookie.setSecure(this.readJsonNode(jsonNode, "secure").asBoolean());
        cookie.setVersion(this.readJsonNode(jsonNode, "version").asInt());
        cookie.setPath(this.readJsonNode(jsonNode, "path").asText());
        cookie.setHttpOnly(this.readJsonNode(jsonNode, "httpOnly").asBoolean());
        return cookie;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return this.hasNonNullField(jsonNode, field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

    private boolean hasNonNullField(JsonNode jsonNode, String field) {
        return jsonNode.has(field) && !(jsonNode.get(field) instanceof NullNode);
    }
}

