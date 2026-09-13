/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.codec.CodecCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.Order
 *  org.springframework.http.codec.multipart.DefaultPartHttpMessageReader
 *  org.springframework.util.unit.DataSize
 *  org.springframework.web.reactive.config.WebFluxConfigurer
 */
package org.springframework.boot.autoconfigure.web.reactive;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@AutoConfiguration
@ConditionalOnClass(value={DefaultPartHttpMessageReader.class, WebFluxConfigurer.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@EnableConfigurationProperties(value={ReactiveMultipartProperties.class})
public class ReactiveMultipartAutoConfiguration {
    @Bean
    @Order(value=0)
    CodecCustomizer defaultPartHttpMessageReaderCustomizer(ReactiveMultipartProperties multipartProperties) {
        return configurer -> configurer.defaultCodecs().configureDefaultCodec(codec -> {
            if (codec instanceof DefaultPartHttpMessageReader) {
                DefaultPartHttpMessageReader defaultPartHttpMessageReader = (DefaultPartHttpMessageReader)codec;
                PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
                map.from(multipartProperties::getMaxInMemorySize).asInt(DataSize::toBytes).to(arg_0 -> ((DefaultPartHttpMessageReader)defaultPartHttpMessageReader).setMaxInMemorySize(arg_0));
                map.from(multipartProperties::getMaxHeadersSize).asInt(DataSize::toBytes).to(arg_0 -> ((DefaultPartHttpMessageReader)defaultPartHttpMessageReader).setMaxHeadersSize(arg_0));
                map.from(multipartProperties::getMaxDiskUsagePerPart).asInt(DataSize::toBytes).to(arg_0 -> ((DefaultPartHttpMessageReader)defaultPartHttpMessageReader).setMaxDiskUsagePerPart(arg_0));
                map.from(multipartProperties::getMaxParts).to(arg_0 -> ((DefaultPartHttpMessageReader)defaultPartHttpMessageReader).setMaxParts(arg_0));
                map.from(multipartProperties::getStreaming).to(arg_0 -> ((DefaultPartHttpMessageReader)defaultPartHttpMessageReader).setStreaming(arg_0));
                map.from(multipartProperties::getFileStorageDirectory).as(x$0 -> Paths.get(x$0, new String[0])).to(dir -> this.configureFileStorageDirectory(defaultPartHttpMessageReader, (Path)dir));
                map.from(multipartProperties::getHeadersCharset).to(arg_0 -> ((DefaultPartHttpMessageReader)defaultPartHttpMessageReader).setHeadersCharset(arg_0));
            }
        });
    }

    private void configureFileStorageDirectory(DefaultPartHttpMessageReader defaultPartHttpMessageReader, Path fileStorageDirectory) {
        try {
            defaultPartHttpMessageReader.setFileStorageDirectory(fileStorageDirectory);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to configure multipart file storage directory", ex);
        }
    }
}

