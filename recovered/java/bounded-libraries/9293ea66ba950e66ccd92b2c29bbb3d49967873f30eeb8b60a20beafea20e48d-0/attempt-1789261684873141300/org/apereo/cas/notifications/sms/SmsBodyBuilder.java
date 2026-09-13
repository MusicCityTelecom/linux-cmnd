/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringSubstitutor
 *  org.apereo.cas.configuration.model.support.sms.SmsProperties
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.ResourceUtils
 */
package org.apereo.cas.notifications.sms;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class SmsBodyBuilder
implements Supplier<String> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsBodyBuilder.class);
    @NonNull
    private final SmsProperties properties;
    private final Map<String, Object> parameters;

    @Override
    public String get() {
        if (StringUtils.isBlank((CharSequence)this.properties.getText())) {
            LOGGER.warn("No SMS text is defined");
            return "";
        }
        try {
            File templateFile = ResourceUtils.getFile((String)this.properties.getText());
            String contents = FileUtils.readFileToString((File)templateFile, (Charset)StandardCharsets.UTF_8);
            return this.formatSmsBody(contents);
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            return this.formatSmsBody(this.properties.getText());
        }
    }

    protected String formatSmsBody(String contents) {
        StringSubstitutor sub = new StringSubstitutor(this.parameters, "${", "}");
        return sub.replace(contents);
    }

    @Generated
    private static Map<String, Object> $default$parameters() {
        return new LinkedHashMap<String, Object>();
    }

    @Generated
    protected SmsBodyBuilder(SmsBodyBuilderBuilder<?, ?> b) {
        this.properties = b.properties;
        if (this.properties == null) {
            throw new NullPointerException("properties is marked non-null but is null");
        }
        this.parameters = b.parameters$set ? b.parameters$value : SmsBodyBuilder.$default$parameters();
    }

    @Generated
    public static SmsBodyBuilderBuilder<?, ?> builder() {
        return new SmsBodyBuilderBuilderImpl();
    }

    @Generated
    private static final class SmsBodyBuilderBuilderImpl
    extends SmsBodyBuilderBuilder<SmsBodyBuilder, SmsBodyBuilderBuilderImpl> {
        @Generated
        private SmsBodyBuilderBuilderImpl() {
        }

        @Override
        @Generated
        protected SmsBodyBuilderBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public SmsBodyBuilder build() {
            return new SmsBodyBuilder(this);
        }
    }

    @Generated
    public static abstract class SmsBodyBuilderBuilder<C extends SmsBodyBuilder, B extends SmsBodyBuilderBuilder<C, B>> {
        @Generated
        private SmsProperties properties;
        @Generated
        private boolean parameters$set;
        @Generated
        private Map<String, Object> parameters$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B properties(@NonNull SmsProperties properties) {
            if (properties == null) {
                throw new NullPointerException("properties is marked non-null but is null");
            }
            this.properties = properties;
            return this.self();
        }

        @Generated
        public B parameters(Map<String, Object> parameters) {
            this.parameters$value = parameters;
            this.parameters$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "SmsBodyBuilder.SmsBodyBuilderBuilder(properties=" + this.properties + ", parameters$value=" + this.parameters$value + ")";
        }
    }
}

