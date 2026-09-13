/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.json.bind.Jsonb
 *  javax.json.bind.JsonbBuilder
 *  org.springframework.context.annotation.Bean
 */
package org.springframework.boot.autoconfigure.jsonb;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(value={Jsonb.class})
@ConditionalOnResource(resources={"classpath:META-INF/services/javax.json.bind.spi.JsonbProvider", "classpath:META-INF/services/javax.json.spi.JsonProvider"})
public class JsonbAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Jsonb jsonb() {
        return JsonbBuilder.create();
    }
}

