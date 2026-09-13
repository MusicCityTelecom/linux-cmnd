/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
 *  org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
 *  org.springframework.data.elasticsearch.repository.support.ReactiveElasticsearchRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ReactiveElasticsearchRepositoryFactoryBean;

@AutoConfiguration
@ConditionalOnClass(value={ReactiveElasticsearchClient.class, ReactiveElasticsearchRepository.class})
@ConditionalOnProperty(prefix="spring.data.elasticsearch.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
@ConditionalOnMissingBean(value={ReactiveElasticsearchRepositoryFactoryBean.class})
@Import(value={ReactiveElasticsearchRepositoriesRegistrar.class})
public class ReactiveElasticsearchRepositoriesAutoConfiguration {
}

