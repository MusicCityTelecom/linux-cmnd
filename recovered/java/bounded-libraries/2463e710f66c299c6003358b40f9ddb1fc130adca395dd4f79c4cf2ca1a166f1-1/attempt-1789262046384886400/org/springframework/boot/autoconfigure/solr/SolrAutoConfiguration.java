/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.solr.client.solrj.SolrClient
 *  org.apache.solr.client.solrj.impl.CloudSolrClient
 *  org.apache.solr.client.solrj.impl.CloudSolrClient$Builder
 *  org.apache.solr.client.solrj.impl.HttpSolrClient
 *  org.apache.solr.client.solrj.impl.HttpSolrClient$Builder
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.solr;

import java.util.Arrays;
import java.util.Optional;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.solr.SolrProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

@AutoConfiguration
@ConditionalOnClass(value={HttpSolrClient.class, CloudSolrClient.class})
@EnableConfigurationProperties(value={SolrProperties.class})
public class SolrAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SolrClient solrClient(SolrProperties properties) {
        if (StringUtils.hasText((String)properties.getZkHost())) {
            return new CloudSolrClient.Builder(Arrays.asList(properties.getZkHost()), Optional.empty()).build();
        }
        return new HttpSolrClient.Builder(properties.getHost()).build();
    }
}

