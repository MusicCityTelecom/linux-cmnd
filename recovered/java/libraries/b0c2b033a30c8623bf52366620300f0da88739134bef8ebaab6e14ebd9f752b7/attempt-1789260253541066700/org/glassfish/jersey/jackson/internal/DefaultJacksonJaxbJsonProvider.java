/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import javax.inject.Singleton;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.Annotations;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JsonMapperConfigurator;

@Singleton
public class DefaultJacksonJaxbJsonProvider
extends JacksonJaxbJsonProvider {
    public DefaultJacksonJaxbJsonProvider() {
        this.findAndRegisterModules();
    }

    public DefaultJacksonJaxbJsonProvider(Annotations ... annotationsToUse) {
        super(annotationsToUse);
        this.findAndRegisterModules();
    }

    private void findAndRegisterModules() {
        ObjectMapper mapper;
        ObjectMapper defaultMapper = ((JsonMapperConfigurator)this._mapperConfig).getDefaultMapper();
        if (Objects.nonNull(defaultMapper)) {
            defaultMapper.findAndRegisterModules();
        }
        if (Objects.nonNull(mapper = ((JsonMapperConfigurator)this._mapperConfig).getConfiguredMapper())) {
            mapper.findAndRegisterModules();
        }
    }
}

