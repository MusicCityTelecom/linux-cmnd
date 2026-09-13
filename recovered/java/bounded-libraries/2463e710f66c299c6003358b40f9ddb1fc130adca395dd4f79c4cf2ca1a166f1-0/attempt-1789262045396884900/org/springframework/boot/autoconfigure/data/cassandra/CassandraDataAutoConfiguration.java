/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.CqlSession
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.env.Environment
 *  org.springframework.data.cassandra.SessionFactory
 *  org.springframework.data.cassandra.config.CassandraEntityClassScanner
 *  org.springframework.data.cassandra.config.SchemaAction
 *  org.springframework.data.cassandra.config.SessionFactoryFactoryBean
 *  org.springframework.data.cassandra.core.CassandraAdminOperations
 *  org.springframework.data.cassandra.core.CassandraOperations
 *  org.springframework.data.cassandra.core.CassandraTemplate
 *  org.springframework.data.cassandra.core.convert.CassandraConverter
 *  org.springframework.data.cassandra.core.convert.CassandraCustomConversions
 *  org.springframework.data.cassandra.core.convert.MappingCassandraConverter
 *  org.springframework.data.cassandra.core.mapping.CassandraMappingContext
 *  org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver
 *  org.springframework.data.cassandra.core.mapping.UserTypeResolver
 *  org.springframework.data.convert.CustomConversions
 */
package org.springframework.boot.autoconfigure.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.core.mapping.UserTypeResolver;
import org.springframework.data.convert.CustomConversions;

@AutoConfiguration(after={CassandraAutoConfiguration.class})
@ConditionalOnClass(value={CqlSession.class, CassandraAdminOperations.class})
@ConditionalOnBean(value={CqlSession.class})
public class CassandraDataAutoConfiguration {
    private final CqlSession session;

    public CassandraDataAutoConfiguration(CqlSession session) {
        this.session = session;
    }

    @Bean
    @ConditionalOnMissingBean
    public CassandraMappingContext cassandraMapping(BeanFactory beanFactory, CassandraCustomConversions conversions) throws ClassNotFoundException {
        CassandraMappingContext context = new CassandraMappingContext();
        List<String> packages = EntityScanPackages.get(beanFactory).getPackageNames();
        if (packages.isEmpty() && AutoConfigurationPackages.has(beanFactory)) {
            packages = AutoConfigurationPackages.get(beanFactory);
        }
        if (!packages.isEmpty()) {
            context.setInitialEntitySet(CassandraEntityClassScanner.scan(packages));
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }

    @Bean
    @ConditionalOnMissingBean
    public CassandraConverter cassandraConverter(CassandraMappingContext mapping, CassandraCustomConversions conversions) {
        MappingCassandraConverter converter = new MappingCassandraConverter(mapping);
        converter.setCodecRegistry(this.session.getContext().getCodecRegistry());
        converter.setCustomConversions((CustomConversions)conversions);
        converter.setUserTypeResolver((UserTypeResolver)new SimpleUserTypeResolver(this.session));
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean(value={SessionFactory.class})
    public SessionFactoryFactoryBean cassandraSessionFactory(Environment environment, CassandraConverter converter) {
        SessionFactoryFactoryBean session = new SessionFactoryFactoryBean();
        session.setSession(this.session);
        session.setConverter(converter);
        Binder binder = Binder.get((Environment)environment);
        binder.bind("spring.data.cassandra.schema-action", SchemaAction.class).ifBound(arg_0 -> ((SessionFactoryFactoryBean)session).setSchemaAction(arg_0));
        return session;
    }

    @Bean
    @ConditionalOnMissingBean(value={CassandraOperations.class})
    public CassandraTemplate cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);
    }

    @Bean
    @ConditionalOnMissingBean
    public CassandraCustomConversions cassandraCustomConversions() {
        return new CassandraCustomConversions(Collections.emptyList());
    }
}

