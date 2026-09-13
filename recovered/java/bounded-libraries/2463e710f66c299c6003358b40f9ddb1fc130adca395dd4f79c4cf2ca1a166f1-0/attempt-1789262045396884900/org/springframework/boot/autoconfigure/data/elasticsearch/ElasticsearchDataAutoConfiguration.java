/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
 */
package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@AutoConfiguration(after={ElasticsearchRestClientAutoConfiguration.class, ReactiveElasticsearchRestClientAutoConfiguration.class})
@ConditionalOnClass(value={ElasticsearchRestTemplate.class})
@Import(value={ElasticsearchDataConfiguration.BaseConfiguration.class, ElasticsearchDataConfiguration.RestClientConfiguration.class, ElasticsearchDataConfiguration.ReactiveRestClientConfiguration.class})
public class ElasticsearchDataAutoConfiguration {
}

