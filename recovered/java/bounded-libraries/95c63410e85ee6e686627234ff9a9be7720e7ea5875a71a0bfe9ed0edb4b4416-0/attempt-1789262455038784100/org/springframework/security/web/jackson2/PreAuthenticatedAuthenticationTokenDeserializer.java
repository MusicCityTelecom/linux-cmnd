/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.MissingNode
 *  org.springframework.security.core.GrantedAuthority
 */
package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.io.IOException;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

class PreAuthenticatedAuthenticationTokenDeserializer
extends JsonDeserializer<PreAuthenticatedAuthenticationToken> {
    private static final TypeReference<List<GrantedAuthority>> GRANTED_AUTHORITY_LIST = new TypeReference<List<GrantedAuthority>>(){};

    PreAuthenticatedAuthenticationTokenDeserializer() {
    }

    public PreAuthenticatedAuthenticationToken deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)jp.getCodec();
        JsonNode jsonNode = (JsonNode)mapper.readTree(jp);
        Boolean authenticated = this.readJsonNode(jsonNode, "authenticated").asBoolean();
        JsonNode principalNode = this.readJsonNode(jsonNode, "principal");
        String principal = !principalNode.isObject() ? principalNode.asText() : mapper.readValue(principalNode.traverse((ObjectCodec)mapper), Object.class);
        String credentials = this.readJsonNode(jsonNode, "credentials").asText();
        List authorities = (List)mapper.readValue(this.readJsonNode(jsonNode, "authorities").traverse((ObjectCodec)mapper), GRANTED_AUTHORITY_LIST);
        PreAuthenticatedAuthenticationToken token = authenticated == false ? new PreAuthenticatedAuthenticationToken(principal, credentials) : new PreAuthenticatedAuthenticationToken(principal, credentials, authorities);
        token.setDetails(this.readJsonNode(jsonNode, "details"));
        return token;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}

