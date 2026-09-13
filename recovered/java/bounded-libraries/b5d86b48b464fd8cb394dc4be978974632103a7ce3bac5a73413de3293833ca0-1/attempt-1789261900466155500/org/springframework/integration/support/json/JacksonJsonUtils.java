/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.databind.DatabindContext
 *  com.fasterxml.jackson.databind.DeserializationConfig
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.ObjectMapper$DefaultTypeResolverBuilder
 *  com.fasterxml.jackson.databind.ObjectMapper$DefaultTyping
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
 *  com.fasterxml.jackson.databind.jsontype.NamedType
 *  com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator
 *  com.fasterxml.jackson.databind.jsontype.TypeIdResolver
 *  com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.messaging.support.GenericMessage
 */
package org.springframework.integration.support.json;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.integration.message.AdviceMessage;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.support.json.AdviceMessageJacksonDeserializer;
import org.springframework.integration.support.json.ErrorMessageJacksonDeserializer;
import org.springframework.integration.support.json.GenericMessageJacksonDeserializer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JacksonPresent;
import org.springframework.integration.support.json.MessageHeadersJacksonSerializer;
import org.springframework.integration.support.json.MimeTypeSerializer;
import org.springframework.integration.support.json.MutableMessageJacksonDeserializer;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;

public final class JacksonJsonUtils {
    public static final List<String> DEFAULT_TRUSTED_PACKAGES = Arrays.asList("java.util", "java.lang", "org.springframework.messaging.support", "org.springframework.integration.support", "org.springframework.integration.message", "org.springframework.integration.store", "org.springframework.integration.history");

    private JacksonJsonUtils() {
    }

    public static ObjectMapper messagingAwareMapper(String ... trustedPackages) {
        if (JacksonPresent.isJackson2Present()) {
            ObjectMapper mapper = new Jackson2JsonObjectMapper().getObjectMapper();
            mapper.setDefaultTyping((TypeResolverBuilder)new AllowListTypeResolverBuilder(trustedPackages));
            GenericMessageJacksonDeserializer genericMessageDeserializer = new GenericMessageJacksonDeserializer();
            genericMessageDeserializer.setMapper(mapper);
            ErrorMessageJacksonDeserializer errorMessageDeserializer = new ErrorMessageJacksonDeserializer();
            errorMessageDeserializer.setMapper(mapper);
            AdviceMessageJacksonDeserializer adviceMessageDeserializer = new AdviceMessageJacksonDeserializer();
            adviceMessageDeserializer.setMapper(mapper);
            MutableMessageJacksonDeserializer mutableMessageDeserializer = new MutableMessageJacksonDeserializer();
            mutableMessageDeserializer.setMapper(mapper);
            SimpleModule simpleModule = new SimpleModule().addSerializer((JsonSerializer)new MessageHeadersJacksonSerializer()).addSerializer((JsonSerializer)new MimeTypeSerializer()).addDeserializer(GenericMessage.class, (JsonDeserializer)genericMessageDeserializer).addDeserializer(ErrorMessage.class, (JsonDeserializer)errorMessageDeserializer).addDeserializer(AdviceMessage.class, (JsonDeserializer)adviceMessageDeserializer).addDeserializer(MutableMessage.class, (JsonDeserializer)mutableMessageDeserializer);
            mapper.registerModule((Module)simpleModule);
            return mapper;
        }
        throw new IllegalStateException("No jackson-databind.jar is present in the classpath.");
    }

    private static final class AllowListTypeResolverBuilder
    extends ObjectMapper.DefaultTypeResolverBuilder {
        private static final long serialVersionUID = 1L;
        private final String[] trustedPackages;

        AllowListTypeResolverBuilder(String ... trustedPackages) {
            super(ObjectMapper.DefaultTyping.NON_FINAL, (PolymorphicTypeValidator)BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build());
            this.trustedPackages = trustedPackages != null ? Arrays.copyOf(trustedPackages, trustedPackages.length) : null;
            this.init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY);
        }

        protected TypeIdResolver idResolver(MapperConfig<?> config, JavaType baseType, PolymorphicTypeValidator subtypeValidator, Collection<NamedType> subtypes, boolean forSer, boolean forDeser) {
            TypeIdResolver result = super.idResolver(config, baseType, subtypeValidator, subtypes, forSer, forDeser);
            return new AllowListTypeIdResolver(result, this.trustedPackages);
        }
    }

    private static final class AllowListTypeIdResolver
    implements TypeIdResolver {
        private final TypeIdResolver delegate;
        private final Set<String> trustedPackages = new LinkedHashSet<String>(DEFAULT_TRUSTED_PACKAGES);

        AllowListTypeIdResolver(TypeIdResolver delegate, String ... trustedPackages) {
            this.delegate = delegate;
            if (trustedPackages != null) {
                for (String trustedPackage : trustedPackages) {
                    if ("*".equals(trustedPackage)) {
                        this.trustedPackages.clear();
                        break;
                    }
                    this.trustedPackages.add(trustedPackage);
                }
            }
        }

        public void init(JavaType baseType) {
            this.delegate.init(baseType);
        }

        public String idFromValue(Object value) {
            return this.delegate.idFromValue(value);
        }

        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return this.delegate.idFromValueAndType(value, suggestedType);
        }

        public String idFromBaseType() {
            return this.delegate.idFromBaseType();
        }

        public JavaType typeFromId(DatabindContext context, String id) throws IOException {
            boolean isExplicitMixin;
            DeserializationConfig config = (DeserializationConfig)context.getConfig();
            JavaType result = this.delegate.typeFromId(context, id);
            Package aPackage = result.getRawClass().getPackage();
            if (aPackage == null || this.isTrustedPackage(aPackage.getName())) {
                return result;
            }
            boolean bl = isExplicitMixin = config.findMixInClassFor(result.getRawClass()) != null;
            if (isExplicitMixin) {
                return result;
            }
            throw new IllegalArgumentException("The class with " + id + " and name of " + result.getRawClass().getName() + " is not in the trusted packages: " + this.trustedPackages + ". If you believe this class is safe to deserialize, please provide its name or an explicit Mixin. If the serialization is only done by a trusted source, you can also enable default typing.");
        }

        private boolean isTrustedPackage(String packageName) {
            if (!this.trustedPackages.isEmpty()) {
                for (String trustedPackage : this.trustedPackages) {
                    if (!packageName.equals(trustedPackage) && (packageName.equals("java.util.logging") || !packageName.startsWith(trustedPackage + "."))) continue;
                    return true;
                }
                return false;
            }
            return true;
        }

        public String getDescForKnownTypeIds() {
            return this.delegate.getDescForKnownTypeIds();
        }

        public JsonTypeInfo.Id getMechanism() {
            return this.delegate.getMechanism();
        }
    }
}

