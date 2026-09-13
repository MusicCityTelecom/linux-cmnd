/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.support.rest.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class RestResourceUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResourceUtils.class);
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(true).build().toObjectMapper();

    public static ResponseEntity<String> createResponseEntityForAuthnFailure(AuthenticationException e, HttpServletRequest request, ApplicationContext applicationContext) {
        try {
            List authnExceptions = e.getHandlerErrors().values().stream().map(ex -> RestResourceUtils.mapExceptionToMessage(e, request, applicationContext, ex)).collect(Collectors.toList());
            if (authnExceptions.isEmpty()) {
                authnExceptions.add(RestResourceUtils.mapExceptionToMessage(e, request, applicationContext, (Throwable)e));
            }
            HashMap errorsMap = new HashMap(1);
            errorsMap.put("authentication_exceptions", authnExceptions);
            LOGGER.warn("[{}] Caused by: [{}]", (Object)e.getMessage(), authnExceptions);
            return new ResponseEntity((Object)MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(errorsMap), HttpStatus.UNAUTHORIZED);
        }
        catch (JsonProcessingException exception) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return new ResponseEntity((Object)StringEscapeUtils.escapeHtml4((String)e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static String mapExceptionToMessage(AuthenticationException authnhandlerErrors, HttpServletRequest request, ApplicationContext applicationContext, Throwable ex) {
        String authnMsg = (String)StringUtils.defaultIfBlank((CharSequence)StringEscapeUtils.escapeHtml4((String)ex.getMessage()), (CharSequence)("Authentication Failure: " + authnhandlerErrors.getMessage()));
        String authnBundleMsg = RestResourceUtils.getTranslatedMessageForExceptionClass(ex.getClass().getSimpleName(), request, applicationContext);
        return String.format("%s:%s", authnMsg, authnBundleMsg);
    }

    private static String getTranslatedMessageForExceptionClass(String className, HttpServletRequest request, ApplicationContext applicationContext) {
        try {
            String msgKey = "authenticationFailure." + className;
            return applicationContext.getMessage(msgKey, null, request.getLocale());
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            return "";
        }
    }

    @Generated
    private RestResourceUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

