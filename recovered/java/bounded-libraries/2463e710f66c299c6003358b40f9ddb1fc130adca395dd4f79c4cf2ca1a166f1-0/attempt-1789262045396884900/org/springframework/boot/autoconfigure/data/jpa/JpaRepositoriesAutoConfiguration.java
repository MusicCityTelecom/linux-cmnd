/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.context.annotation.ImportSelector
 *  org.springframework.core.task.AsyncTaskExecutor
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension
 *  org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.data.jpa;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.EnversRevisionRepositoriesRegistrar;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesRegistrar;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.util.ClassUtils;

@AutoConfiguration(after={HibernateJpaAutoConfiguration.class, TaskExecutionAutoConfiguration.class})
@ConditionalOnBean(value={DataSource.class})
@ConditionalOnClass(value={JpaRepository.class})
@ConditionalOnMissingBean(value={JpaRepositoryFactoryBean.class, JpaRepositoryConfigExtension.class})
@ConditionalOnProperty(prefix="spring.data.jpa.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
@Import(value={JpaRepositoriesImportSelector.class})
public class JpaRepositoriesAutoConfiguration {
    @Bean
    @Conditional(value={BootstrapExecutorCondition.class})
    public EntityManagerFactoryBuilderCustomizer entityManagerFactoryBootstrapExecutorCustomizer(Map<String, AsyncTaskExecutor> taskExecutors) {
        return builder -> {
            AsyncTaskExecutor bootstrapExecutor = this.determineBootstrapExecutor(taskExecutors);
            if (bootstrapExecutor != null) {
                builder.setBootstrapExecutor(bootstrapExecutor);
            }
        };
    }

    private AsyncTaskExecutor determineBootstrapExecutor(Map<String, AsyncTaskExecutor> taskExecutors) {
        if (taskExecutors.size() == 1) {
            return taskExecutors.values().iterator().next();
        }
        return taskExecutors.get("applicationTaskExecutor");
    }

    static class JpaRepositoriesImportSelector
    implements ImportSelector {
        private static final boolean ENVERS_AVAILABLE = ClassUtils.isPresent((String)"org.springframework.data.envers.repository.config.EnableEnversRepositories", (ClassLoader)JpaRepositoriesImportSelector.class.getClassLoader());

        JpaRepositoriesImportSelector() {
        }

        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{this.determineImport()};
        }

        private String determineImport() {
            return ENVERS_AVAILABLE ? EnversRevisionRepositoriesRegistrar.class.getName() : JpaRepositoriesRegistrar.class.getName();
        }
    }

    private static final class BootstrapExecutorCondition
    extends AnyNestedCondition {
        BootstrapExecutorCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty(prefix="spring.data.jpa.repositories", name={"bootstrap-mode"}, havingValue="lazy")
        static class LazyBootstrapMode {
            LazyBootstrapMode() {
            }
        }

        @ConditionalOnProperty(prefix="spring.data.jpa.repositories", name={"bootstrap-mode"}, havingValue="deferred")
        static class DeferredBootstrapMode {
            DeferredBootstrapMode() {
            }
        }
    }
}

