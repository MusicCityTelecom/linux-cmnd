/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManager
 *  org.hibernate.engine.spi.SessionImplementor
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 *  org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import javax.persistence.EntityManager;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@AutoConfiguration(after={DataSourceAutoConfiguration.class})
@ConditionalOnClass(value={LocalContainerEntityManagerFactoryBean.class, EntityManager.class, SessionImplementor.class})
@EnableConfigurationProperties(value={JpaProperties.class})
@Import(value={HibernateJpaConfiguration.class})
public class HibernateJpaAutoConfiguration {
}

