/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.elasticsearch.client.RestClientBuilder
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 */
package org.springframework.boot.autoconfigure.elasticsearch;

import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.elasticsearch.DeprecatedElasticsearchRestClientProperties;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientConfigurations;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnClass(value={RestClientBuilder.class})
@EnableConfigurationProperties(value={ElasticsearchProperties.class, ElasticsearchRestClientProperties.class, DeprecatedElasticsearchRestClientProperties.class})
@Import(value={ElasticsearchRestClientConfigurations.RestClientBuilderConfiguration.class, ElasticsearchRestClientConfigurations.RestHighLevelClientConfiguration.class, ElasticsearchRestClientConfigurations.RestClientFromRestHighLevelClientConfiguration.class, ElasticsearchRestClientConfigurations.RestClientConfiguration.class, ElasticsearchRestClientConfigurations.RestClientSnifferConfiguration.class})
public class ElasticsearchRestClientAutoConfiguration {
}

