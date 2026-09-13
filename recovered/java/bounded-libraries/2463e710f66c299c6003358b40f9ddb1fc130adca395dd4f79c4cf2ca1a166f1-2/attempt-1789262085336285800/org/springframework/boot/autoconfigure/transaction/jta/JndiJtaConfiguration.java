/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.config.JtaTransactionManagerFactoryBean
 *  org.springframework.transaction.jta.JtaTransactionManager
 */
package org.springframework.boot.autoconfigure.transaction.jta;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.config.JtaTransactionManagerFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={JtaTransactionManager.class})
@ConditionalOnJndi(value={"java:comp/UserTransaction", "java:comp/TransactionManager", "java:appserver/TransactionManager", "java:pm/TransactionManager", "java:/TransactionManager"})
@ConditionalOnMissingBean(value={TransactionManager.class})
class JndiJtaConfiguration {
    JndiJtaConfiguration() {
    }

    @Bean
    JtaTransactionManager transactionManager(ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManagerFactoryBean().getObject();
        transactionManagerCustomizers.ifAvailable(customizers -> customizers.customize((PlatformTransactionManager)jtaTransactionManager));
        return jtaTransactionManager;
    }
}

