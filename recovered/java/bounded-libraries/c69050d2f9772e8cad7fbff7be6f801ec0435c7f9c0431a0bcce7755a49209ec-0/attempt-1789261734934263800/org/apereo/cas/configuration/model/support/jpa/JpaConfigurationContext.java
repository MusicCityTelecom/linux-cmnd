/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.spi.PersistenceProvider
 *  lombok.Generated
 *  org.springframework.orm.jpa.JpaVendorAdapter
 */
package org.apereo.cas.configuration.model.support.jpa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;
import lombok.Generated;
import org.springframework.orm.jpa.JpaVendorAdapter;

public class JpaConfigurationContext {
    private final JpaVendorAdapter jpaVendorAdapter;
    private final String persistenceUnitName;
    private final DataSource dataSource;
    private final PersistenceProvider persistenceProvider;
    private final Map<String, Object> jpaProperties;
    private final Set<String> packagesToScan;

    @Generated
    private static Map<String, Object> $default$jpaProperties() {
        return new LinkedHashMap<String, Object>(0);
    }

    @Generated
    private static Set<String> $default$packagesToScan() {
        return new LinkedHashSet<String>(0);
    }

    @Generated
    protected JpaConfigurationContext(JpaConfigurationContextBuilder<?, ?> b) {
        this.jpaVendorAdapter = b.jpaVendorAdapter;
        this.persistenceUnitName = b.persistenceUnitName;
        this.dataSource = b.dataSource;
        this.persistenceProvider = b.persistenceProvider;
        this.jpaProperties = b.jpaProperties$set ? b.jpaProperties$value : JpaConfigurationContext.$default$jpaProperties();
        this.packagesToScan = b.packagesToScan$set ? b.packagesToScan$value : JpaConfigurationContext.$default$packagesToScan();
    }

    @Generated
    public static JpaConfigurationContextBuilder<?, ?> builder() {
        return new JpaConfigurationContextBuilderImpl();
    }

    @Generated
    public JpaVendorAdapter getJpaVendorAdapter() {
        return this.jpaVendorAdapter;
    }

    @Generated
    public String getPersistenceUnitName() {
        return this.persistenceUnitName;
    }

    @Generated
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Generated
    public PersistenceProvider getPersistenceProvider() {
        return this.persistenceProvider;
    }

    @Generated
    public Map<String, Object> getJpaProperties() {
        return this.jpaProperties;
    }

    @Generated
    public Set<String> getPackagesToScan() {
        return this.packagesToScan;
    }

    @Generated
    private static final class JpaConfigurationContextBuilderImpl
    extends JpaConfigurationContextBuilder<JpaConfigurationContext, JpaConfigurationContextBuilderImpl> {
        @Generated
        private JpaConfigurationContextBuilderImpl() {
        }

        @Override
        @Generated
        protected JpaConfigurationContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public JpaConfigurationContext build() {
            return new JpaConfigurationContext(this);
        }
    }

    @Generated
    public static abstract class JpaConfigurationContextBuilder<C extends JpaConfigurationContext, B extends JpaConfigurationContextBuilder<C, B>> {
        @Generated
        private JpaVendorAdapter jpaVendorAdapter;
        @Generated
        private String persistenceUnitName;
        @Generated
        private DataSource dataSource;
        @Generated
        private PersistenceProvider persistenceProvider;
        @Generated
        private boolean jpaProperties$set;
        @Generated
        private Map<String, Object> jpaProperties$value;
        @Generated
        private boolean packagesToScan$set;
        @Generated
        private Set<String> packagesToScan$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B jpaVendorAdapter(JpaVendorAdapter jpaVendorAdapter) {
            this.jpaVendorAdapter = jpaVendorAdapter;
            return this.self();
        }

        @Generated
        public B persistenceUnitName(String persistenceUnitName) {
            this.persistenceUnitName = persistenceUnitName;
            return this.self();
        }

        @Generated
        public B dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this.self();
        }

        @Generated
        public B persistenceProvider(PersistenceProvider persistenceProvider) {
            this.persistenceProvider = persistenceProvider;
            return this.self();
        }

        @Generated
        public B jpaProperties(Map<String, Object> jpaProperties) {
            this.jpaProperties$value = jpaProperties;
            this.jpaProperties$set = true;
            return this.self();
        }

        @Generated
        public B packagesToScan(Set<String> packagesToScan) {
            this.packagesToScan$value = packagesToScan;
            this.packagesToScan$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "JpaConfigurationContext.JpaConfigurationContextBuilder(jpaVendorAdapter=" + this.jpaVendorAdapter + ", persistenceUnitName=" + this.persistenceUnitName + ", dataSource=" + this.dataSource + ", persistenceProvider=" + this.persistenceProvider + ", jpaProperties$value=" + this.jpaProperties$value + ", packagesToScan$value=" + this.packagesToScan$value + ")";
        }
    }
}

