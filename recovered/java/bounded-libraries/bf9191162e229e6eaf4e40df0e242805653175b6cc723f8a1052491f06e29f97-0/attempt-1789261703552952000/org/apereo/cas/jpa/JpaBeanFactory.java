/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManagerFactory
 *  javax.persistence.Query
 *  javax.persistence.spi.PersistenceProvider
 *  org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties
 *  org.apereo.cas.configuration.model.support.jpa.DatabaseProperties
 *  org.apereo.cas.configuration.model.support.jpa.JpaConfigurationContext
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.orm.jpa.JpaVendorAdapter
 */
package org.apereo.cas.jpa;

import java.io.Serializable;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.spi.PersistenceProvider;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.model.support.jpa.DatabaseProperties;
import org.apereo.cas.configuration.model.support.jpa.JpaConfigurationContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;

public interface JpaBeanFactory {
    public static final String DEFAULT_BEAN_NAME = "jpaBeanFactory";

    public JpaVendorAdapter newJpaVendorAdapter(DatabaseProperties var1);

    default public JpaVendorAdapter newJpaVendorAdapter() {
        DatabaseProperties properties = new DatabaseProperties();
        properties.setGenDdl(true);
        properties.setShowSql(true);
        return this.newJpaVendorAdapter(properties);
    }

    public FactoryBean<EntityManagerFactory> newEntityManagerFactoryBean(JpaConfigurationContext var1, AbstractJpaProperties var2);

    public PersistenceProvider newPersistenceProvider(AbstractJpaProperties var1);

    public Stream<? extends Serializable> streamQuery(Query var1);
}

