/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.core.HazelcastInstance
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.orm.jpa.AbstractEntityManagerFactoryBean
 *  org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 */
package org.springframework.boot.autoconfigure.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryDependsOnPostProcessor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@AutoConfiguration(after={HazelcastAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ConditionalOnClass(value={HazelcastInstance.class, LocalContainerEntityManagerFactoryBean.class})
@Import(value={HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor.class})
public class HazelcastJpaDependencyAutoConfiguration {

    static class OnHazelcastAndJpaCondition
    extends AllNestedConditions {
        OnHazelcastAndJpaCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnBean(value={AbstractEntityManagerFactoryBean.class})
        static class HasJpa {
            HasJpa() {
            }
        }

        @ConditionalOnBean(name={"hazelcastInstance"})
        static class HasHazelcastInstance {
            HasHazelcastInstance() {
            }
        }
    }

    @Conditional(value={OnHazelcastAndJpaCondition.class})
    static class HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor
    extends EntityManagerFactoryDependsOnPostProcessor {
        HazelcastInstanceEntityManagerFactoryDependsOnPostProcessor() {
            super("hazelcastInstance");
        }
    }
}

