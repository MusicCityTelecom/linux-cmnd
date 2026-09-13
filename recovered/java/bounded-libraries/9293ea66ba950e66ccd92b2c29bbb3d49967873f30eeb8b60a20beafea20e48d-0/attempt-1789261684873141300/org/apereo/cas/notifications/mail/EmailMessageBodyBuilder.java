/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  groovy.lang.Writable
 *  groovy.text.GStringTemplateEngine
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.FilenameUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringSubstitutor
 *  org.apereo.cas.configuration.model.support.email.EmailProperties
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.scripting.ScriptResourceCacheManager
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 */
package org.apereo.cas.notifications.mail;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import groovy.lang.Writable;
import groovy.text.GStringTemplateEngine;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.ScriptResourceCacheManager;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;

public class EmailMessageBodyBuilder
implements Supplier<String> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageBodyBuilder.class);
    @NonNull
    private final EmailProperties properties;
    private final Map<String, Object> parameters;
    private final Optional<Locale> locale;

    @CanIgnoreReturnValue
    public EmailMessageBodyBuilder addParameter(String key, Object object) {
        this.parameters.put(key, object);
        return this;
    }

    @Override
    public String get() {
        if (StringUtils.isBlank((CharSequence)this.properties.getText())) {
            LOGGER.warn("No email body is defined");
            return "";
        }
        try {
            if (ScriptingUtils.isExternalGroovyScript((String)this.properties.getText())) {
                ScriptResourceCacheManager cacheMgr = (ScriptResourceCacheManager)ApplicationContextProvider.getScriptResourceCacheManager().get();
                ExecutableCompiledGroovyScript script = cacheMgr.resolveScriptableResource(this.properties.getText(), new String[]{this.properties.getText()});
                Map args = CollectionUtils.wrap((String)"parameters", this.parameters, (String)"logger", (Object)LOGGER);
                this.locale.ifPresent(loc -> args.put("locale", loc));
                script.setBinding(args);
                return (String)script.execute(args.values().toArray(), String.class);
            }
            File templateFile = this.determineEmailTemplateFile();
            LOGGER.debug("Using email template file at [{}]", (Object)templateFile);
            String contents = FileUtils.readFileToString((File)templateFile, (Charset)StandardCharsets.UTF_8);
            if (templateFile.getName().endsWith(".gtemplate")) {
                GStringTemplateEngine engine = new GStringTemplateEngine();
                LinkedHashMap<String, Object> templateParams = new LinkedHashMap<String, Object>(this.parameters);
                this.locale.ifPresent(loc -> templateParams.put("locale", loc));
                Writable template = engine.createTemplate(contents).make(templateParams);
                return template.toString();
            }
            return this.formatEmailBody(contents);
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            return this.formatEmailBody(this.properties.getText());
        }
    }

    protected String formatEmailBody(String contents) {
        StringSubstitutor sub = new StringSubstitutor(this.parameters, "${", "}");
        return sub.replace(contents);
    }

    protected File determineEmailTemplateFile() {
        return (File)this.locale.map(Unchecked.function(loc -> {
            File originalFile = new File(this.properties.getText());
            String localizedName = String.format("%s_%s.%s", FilenameUtils.getBaseName((String)originalFile.getName()), loc.getLanguage(), FilenameUtils.getExtension((String)originalFile.getName()));
            File localizedFile = new File(originalFile.getParentFile(), localizedName);
            LOGGER.debug("Checking for localized email template file at [{}]", (Object)localizedFile.getPath());
            if (ResourceUtils.doesResourceExist((String)localizedFile.getPath())) {
                AbstractResource templateResource = ResourceUtils.getRawResourceFrom((String)localizedFile.getPath());
                return templateResource.getFile();
            }
            return this.getDefaultEmailTemplateFile();
        })).orElseGet(Unchecked.supplier(this::getDefaultEmailTemplateFile));
    }

    private File getDefaultEmailTemplateFile() throws IOException {
        AbstractResource templateResource = ResourceUtils.getResourceFrom((String)this.properties.getText());
        return templateResource.getFile();
    }

    @Generated
    private static Map<String, Object> $default$parameters() {
        return new LinkedHashMap<String, Object>();
    }

    @Generated
    private static Optional<Locale> $default$locale() {
        return Optional.empty();
    }

    @Generated
    protected EmailMessageBodyBuilder(EmailMessageBodyBuilderBuilder<?, ?> b) {
        this.properties = b.properties;
        if (this.properties == null) {
            throw new NullPointerException("properties is marked non-null but is null");
        }
        this.parameters = b.parameters$set ? b.parameters$value : EmailMessageBodyBuilder.$default$parameters();
        this.locale = b.locale$set ? b.locale$value : EmailMessageBodyBuilder.$default$locale();
    }

    @Generated
    public static EmailMessageBodyBuilderBuilder<?, ?> builder() {
        return new EmailMessageBodyBuilderBuilderImpl();
    }

    @Generated
    private static final class EmailMessageBodyBuilderBuilderImpl
    extends EmailMessageBodyBuilderBuilder<EmailMessageBodyBuilder, EmailMessageBodyBuilderBuilderImpl> {
        @Generated
        private EmailMessageBodyBuilderBuilderImpl() {
        }

        @Override
        @Generated
        protected EmailMessageBodyBuilderBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public EmailMessageBodyBuilder build() {
            return new EmailMessageBodyBuilder(this);
        }
    }

    @Generated
    public static abstract class EmailMessageBodyBuilderBuilder<C extends EmailMessageBodyBuilder, B extends EmailMessageBodyBuilderBuilder<C, B>> {
        @Generated
        private EmailProperties properties;
        @Generated
        private boolean parameters$set;
        @Generated
        private Map<String, Object> parameters$value;
        @Generated
        private boolean locale$set;
        @Generated
        private Optional<Locale> locale$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B properties(@NonNull EmailProperties properties) {
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
        public B locale(Optional<Locale> locale) {
            this.locale$value = locale;
            this.locale$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "EmailMessageBodyBuilder.EmailMessageBodyBuilderBuilder(properties=" + this.properties + ", parameters$value=" + this.parameters$value + ", locale$value=" + this.locale$value + ")";
        }
    }
}

