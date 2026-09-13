/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.elasticsearch.client.Client
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.elasticsearch.repository.ElasticsearchRepository
 *  org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.elasticsearch.client.Client;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;

@AutoConfiguration
@ConditionalOnClass(value={Client.class, ElasticsearchRepository.class})
@ConditionalOnProperty(prefix="spring.data.elasticsearch.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
@ConditionalOnMissingBean(value={ElasticsearchRepositoryFactoryBean.class})
@Import(value={ElasticsearchRepositoriesRegistrar.class})
public class ElasticsearchRepositoriesAutoConfiguration {
}

