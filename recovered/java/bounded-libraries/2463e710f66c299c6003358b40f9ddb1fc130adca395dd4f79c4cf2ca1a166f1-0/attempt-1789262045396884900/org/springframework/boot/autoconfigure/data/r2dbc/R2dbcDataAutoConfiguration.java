/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.convert.CustomConversions
 *  org.springframework.data.convert.CustomConversions$StoreConversions
 *  org.springframework.data.mapping.context.MappingContext
 *  org.springframework.data.mapping.model.SimpleTypeHolder
 *  org.springframework.data.r2dbc.convert.MappingR2dbcConverter
 *  org.springframework.data.r2dbc.convert.R2dbcConverter
 *  org.springframework.data.r2dbc.convert.R2dbcCustomConversions
 *  org.springframework.data.r2dbc.core.R2dbcEntityTemplate
 *  org.springframework.data.r2dbc.dialect.DialectResolver
 *  org.springframework.data.r2dbc.dialect.R2dbcDialect
 *  org.springframework.data.r2dbc.mapping.R2dbcMappingContext
 *  org.springframework.data.relational.core.mapping.NamingStrategy
 *  org.springframework.r2dbc.core.DatabaseClient
 */
package org.springframework.boot.autoconfigure.data.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.r2dbc.core.DatabaseClient;

@AutoConfiguration(after={R2dbcAutoConfiguration.class})
@ConditionalOnClass(value={DatabaseClient.class, R2dbcEntityTemplate.class})
@ConditionalOnSingleCandidate(value=DatabaseClient.class)
public class R2dbcDataAutoConfiguration {
    private final DatabaseClient databaseClient;
    private final R2dbcDialect dialect;

    public R2dbcDataAutoConfiguration(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
        this.dialect = DialectResolver.getDialect((ConnectionFactory)this.databaseClient.getConnectionFactory());
    }

    @Bean
    @ConditionalOnMissingBean
    public R2dbcEntityTemplate r2dbcEntityTemplate(R2dbcConverter r2dbcConverter) {
        return new R2dbcEntityTemplate(this.databaseClient, this.dialect, r2dbcConverter);
    }

    @Bean
    @ConditionalOnMissingBean
    public R2dbcMappingContext r2dbcMappingContext(ObjectProvider<NamingStrategy> namingStrategy, R2dbcCustomConversions r2dbcCustomConversions) {
        R2dbcMappingContext relationalMappingContext = new R2dbcMappingContext((NamingStrategy)namingStrategy.getIfAvailable(() -> NamingStrategy.INSTANCE));
        relationalMappingContext.setSimpleTypeHolder(r2dbcCustomConversions.getSimpleTypeHolder());
        return relationalMappingContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public MappingR2dbcConverter r2dbcConverter(R2dbcMappingContext mappingContext, R2dbcCustomConversions r2dbcCustomConversions) {
        return new MappingR2dbcConverter((MappingContext)mappingContext, (CustomConversions)r2dbcCustomConversions);
    }

    @Bean
    @ConditionalOnMissingBean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        ArrayList converters = new ArrayList(this.dialect.getConverters());
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS);
        return new R2dbcCustomConversions(CustomConversions.StoreConversions.of((SimpleTypeHolder)this.dialect.getSimpleTypeHolder(), converters), Collections.emptyList());
    }
}

