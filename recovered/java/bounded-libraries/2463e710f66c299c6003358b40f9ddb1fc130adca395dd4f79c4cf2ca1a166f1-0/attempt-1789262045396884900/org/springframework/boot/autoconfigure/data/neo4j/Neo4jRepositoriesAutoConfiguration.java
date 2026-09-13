/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.Driver
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.neo4j.repository.Neo4jRepository
 *  org.springframework.data.neo4j.repository.config.Neo4jRepositoryConfigurationExtension
 *  org.springframework.data.neo4j.repository.support.Neo4jRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.Neo4jRepositoryConfigurationExtension;
import org.springframework.data.neo4j.repository.support.Neo4jRepositoryFactoryBean;

@AutoConfiguration(after={Neo4jDataAutoConfiguration.class})
@ConditionalOnClass(value={Driver.class, Neo4jRepository.class})
@ConditionalOnMissingBean(value={Neo4jRepositoryFactoryBean.class, Neo4jRepositoryConfigurationExtension.class})
@ConditionalOnRepositoryType(store="neo4j", type=RepositoryType.IMPERATIVE)
@Import(value={Neo4jRepositoriesRegistrar.class})
public class Neo4jRepositoriesAutoConfiguration {
}

