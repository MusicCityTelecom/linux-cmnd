/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.r2dbc.connection.R2dbcTransactionManager
 *  org.springframework.transaction.ReactiveTransactionManager
 */
package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

@AutoConfiguration(before={TransactionAutoConfiguration.class})
@ConditionalOnClass(value={R2dbcTransactionManager.class, ReactiveTransactionManager.class})
@ConditionalOnSingleCandidate(value=ConnectionFactory.class)
@AutoConfigureOrder(value=0x7FFFFFFF)
public class R2dbcTransactionManagerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={ReactiveTransactionManager.class})
    public R2dbcTransactionManager connectionFactoryTransactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}

