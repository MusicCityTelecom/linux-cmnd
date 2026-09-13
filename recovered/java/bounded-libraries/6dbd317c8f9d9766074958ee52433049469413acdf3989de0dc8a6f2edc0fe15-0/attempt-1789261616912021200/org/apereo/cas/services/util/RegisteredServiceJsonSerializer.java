/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apache.commons.io.FileUtils
 *  org.apereo.cas.services.RegisteredService
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.http.MediaType
 */
package org.apereo.cas.services.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.util.BaseRegisteredServiceSerializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;

public class RegisteredServiceJsonSerializer
extends BaseRegisteredServiceSerializer {
    private static final long serialVersionUID = 7645698151115635245L;

    public RegisteredServiceJsonSerializer(ConfigurableApplicationContext applicationContext) {
        super(applicationContext);
    }

    public boolean supports(File file) {
        try {
            String content = FileUtils.readFileToString((File)file, (String)StandardCharsets.UTF_8.name());
            return this.supports(content);
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean supports(String content) {
        return content.contains(JsonTypeInfo.Id.CLASS.getDefaultPropertyName());
    }

    public Class<RegisteredService> getTypeToSerialize() {
        return RegisteredService.class;
    }

    public List<MediaType> getContentTypes() {
        return List.of(MediaType.APPLICATION_JSON);
    }
}

