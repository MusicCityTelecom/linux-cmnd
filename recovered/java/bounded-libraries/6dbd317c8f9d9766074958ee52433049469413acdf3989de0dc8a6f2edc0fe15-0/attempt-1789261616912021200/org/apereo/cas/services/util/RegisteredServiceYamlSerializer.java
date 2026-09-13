/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.dataformat.yaml.YAMLFactory
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.http.MediaType
 */
package org.apereo.cas.services.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apereo.cas.services.util.RegisteredServiceJsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;

public class RegisteredServiceYamlSerializer
extends RegisteredServiceJsonSerializer {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceYamlSerializer.class);
    private static final long serialVersionUID = -6026921045861422473L;

    public RegisteredServiceYamlSerializer(ConfigurableApplicationContext applicationContext) {
        super(applicationContext);
    }

    protected JsonFactory getJsonFactory() {
        return new YAMLFactory();
    }

    @Override
    public boolean supports(File file) {
        try {
            String contents = FileUtils.readFileToString((File)file, (String)StandardCharsets.UTF_8.name()).trim();
            return this.supports(contents);
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            return false;
        }
    }

    @Override
    public boolean supports(String content) {
        return content.startsWith("--- !<");
    }

    @Override
    public List<MediaType> getContentTypes() {
        return List.of(MediaType.valueOf((String)"application/yaml"), MediaType.valueOf((String)"application/yml"));
    }
}

