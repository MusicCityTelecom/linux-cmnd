/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.dataformat.yaml.YAMLFactory
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
 */
package org.apereo.cas.web;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.nio.charset.StandardCharsets;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class CasYamlHttpMessageConverter
extends AbstractJackson2HttpMessageConverter {
    public CasYamlHttpMessageConverter() {
        super(JacksonObjectMapperFactory.builder().defaultTypingEnabled(true).jsonFactory((JsonFactory)new YAMLFactory()).build().toObjectMapper(), new MediaType("application", "vnd.cas.services+yaml"));
        this.setPrettyPrint(true);
        this.setDefaultCharset(StandardCharsets.UTF_8);
    }
}

