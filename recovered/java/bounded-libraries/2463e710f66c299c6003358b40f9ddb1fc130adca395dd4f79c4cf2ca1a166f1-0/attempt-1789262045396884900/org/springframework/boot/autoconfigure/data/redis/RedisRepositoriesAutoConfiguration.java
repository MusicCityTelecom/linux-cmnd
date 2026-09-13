/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.repository.configuration.EnableRedisRepositories
 *  org.springframework.data.redis.repository.support.RedisRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.repository.support.RedisRepositoryFactoryBean;

@AutoConfiguration(after={RedisAutoConfiguration.class})
@ConditionalOnClass(value={EnableRedisRepositories.class})
@ConditionalOnBean(value={RedisConnectionFactory.class})
@ConditionalOnProperty(prefix="spring.data.redis.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
@ConditionalOnMissingBean(value={RedisRepositoryFactoryBean.class})
@Import(value={RedisRepositoriesRegistrar.class})
public class RedisRepositoriesAutoConfiguration {
}

