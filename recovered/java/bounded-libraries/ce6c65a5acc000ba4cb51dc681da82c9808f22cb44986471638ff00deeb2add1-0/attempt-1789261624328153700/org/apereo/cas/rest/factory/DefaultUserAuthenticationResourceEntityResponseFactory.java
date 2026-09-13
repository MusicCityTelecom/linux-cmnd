/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.rest.factory.UserAuthenticationResourceEntityResponseFactory;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultUserAuthenticationResourceEntityResponseFactory
implements UserAuthenticationResourceEntityResponseFactory {
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(false).build().toObjectMapper();

    @Override
    public ResponseEntity<String> build(AuthenticationResult result, HttpServletRequest request) throws Exception {
        return new ResponseEntity((Object)MAPPER.writeValueAsString((Object)result), HttpStatus.OK);
    }
}

