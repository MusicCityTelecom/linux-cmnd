/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.atomikos.icatch.config.UserTransactionService
 *  com.atomikos.icatch.config.UserTransactionServiceImp
 *  com.atomikos.icatch.jta.UserTransactionManager
 *  javax.jms.Message
 *  javax.transaction.TransactionManager
 *  javax.transaction.UserTransaction
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.jdbc.XADataSourceWrapper
 *  org.springframework.boot.jms.XAConnectionFactoryWrapper
 *  org.springframework.boot.jta.atomikos.AtomikosDependsOnBeanFactoryPostProcessor
 *  org.springframework.boot.jta.atomikos.AtomikosProperties
 *  org.springframework.boot.jta.atomikos.AtomikosXAConnectionFactoryWrapper
 *  org.springframework.boot.jta.atomikos.AtomikosXADataSourceWrapper
 *  org.springframework.boot.system.ApplicationHome
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.jta.JtaTransactionManager
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.transaction.jta;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import java.io.File;
import java.util.Map;
import java.util.Properties;
import javax.jms.Message;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.autoconfigure.transaction.jta.JtaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.boot.jms.XAConnectionFactoryWrapper;
import org.springframework.boot.jta.atomikos.AtomikosDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.jta.atomikos.AtomikosProperties;
import org.springframework.boot.jta.atomikos.AtomikosXAConnectionFactoryWrapper;
import org.springframework.boot.jta.atomikos.AtomikosXADataSourceWrapper;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(value={AtomikosProperties.class, JtaProperties.class})
@ConditionalOnClass(value={JtaTransactionManager.class, UserTransactionManager.class})
@ConditionalOnMissingBean(value={org.springframework.transaction.TransactionManager.class})
class AtomikosJtaConfiguration {
    AtomikosJtaConfiguration() {
    }

    @Bean(initMethod="init", destroyMethod="shutdownWait")
    @ConditionalOnMissingBean(value={UserTransactionService.class})
    UserTransactionServiceImp userTransactionService(AtomikosProperties atomikosProperties, JtaProperties jtaProperties) {
        Properties properties = new Properties();
        if (StringUtils.hasText((String)jtaProperties.getTransactionManagerId())) {
            properties.setProperty("com.atomikos.icatch.tm_unique_name", jtaProperties.getTransactionManagerId());
        }
        properties.setProperty("com.atomikos.icatch.log_base_dir", this.getLogBaseDir(jtaProperties));
        properties.putAll((Map<?, ?>)atomikosProperties.asProperties());
        return new UserTransactionServiceImp(properties);
    }

    private String getLogBaseDir(JtaProperties jtaProperties) {
        if (StringUtils.hasLength((String)jtaProperties.getLogDir())) {
            return jtaProperties.getLogDir();
        }
        File home = new ApplicationHome().getDir();
        return new File(home, "transaction-logs").getAbsolutePath();
    }

    @Bean(initMethod="init", destroyMethod="close")
    @ConditionalOnMissingBean(value={TransactionManager.class})
    UserTransactionManager atomikosTransactionManager(UserTransactionService userTransactionService) {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setStartupTransactionService(false);
        manager.setForceShutdown(true);
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean(value={XADataSourceWrapper.class})
    AtomikosXADataSourceWrapper xaDataSourceWrapper() {
        return new AtomikosXADataSourceWrapper();
    }

    @Bean
    @ConditionalOnMissingBean
    static AtomikosDependsOnBeanFactoryPostProcessor atomikosDependsOnBeanFactoryPostProcessor() {
        return new AtomikosDependsOnBeanFactoryPostProcessor();
    }

    @Bean
    JtaTransactionManager transactionManager(UserTransaction userTransaction, TransactionManager transactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, transactionManager);
        transactionManagerCustomizers.ifAvailable(customizers -> customizers.customize((PlatformTransactionManager)jtaTransactionManager));
        return jtaTransactionManager;
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Message.class})
    static class AtomikosJtaJmsConfiguration {
        AtomikosJtaJmsConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={XAConnectionFactoryWrapper.class})
        AtomikosXAConnectionFactoryWrapper xaConnectionFactoryWrapper() {
            return new AtomikosXAConnectionFactoryWrapper();
        }
    }
}

