/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  okhttp3.OkHttpClient$Builder
 *  org.influxdb.InfluxDB
 *  org.influxdb.impl.InfluxDBImpl
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 */
package org.springframework.boot.autoconfigure.influx;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.impl.InfluxDBImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.influx.InfluxDbCustomizer;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.boot.autoconfigure.influx.InfluxDbProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(value={InfluxDB.class})
@EnableConfigurationProperties(value={InfluxDbProperties.class})
public class InfluxDbAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value={"spring.influx.url"})
    public InfluxDB influxDb(InfluxDbProperties properties, ObjectProvider<InfluxDbOkHttpClientBuilderProvider> builder, ObjectProvider<InfluxDbCustomizer> customizers) {
        InfluxDBImpl influxDb = new InfluxDBImpl(properties.getUrl(), properties.getUser(), properties.getPassword(), InfluxDbAutoConfiguration.determineBuilder((InfluxDbOkHttpClientBuilderProvider)builder.getIfAvailable()));
        customizers.orderedStream().forEach(arg_0 -> InfluxDbAutoConfiguration.lambda$influxDb$0((InfluxDB)influxDb, arg_0));
        return influxDb;
    }

    private static OkHttpClient.Builder determineBuilder(InfluxDbOkHttpClientBuilderProvider builder) {
        if (builder != null) {
            return (OkHttpClient.Builder)builder.get();
        }
        return new OkHttpClient.Builder();
    }

    private static /* synthetic */ void lambda$influxDb$0(InfluxDB influxDb, InfluxDbCustomizer customizer) {
        customizer.customize(influxDb);
    }
}

