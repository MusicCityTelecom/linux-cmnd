/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.function.FunctionUtils
 */
package org.apereo.cas.jpa;

import lombok.Generated;
import org.apereo.cas.util.function.FunctionUtils;

public abstract class AbstractJpaEntityFactory<T> {
    private final String dialect;

    public abstract Class<T> getType();

    public T newInstance() {
        return (T)FunctionUtils.doUnchecked(() -> this.getType().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
    }

    protected boolean isOracle() {
        return this.dialect.contains("Oracle");
    }

    protected boolean isMySql() {
        return this.dialect.contains("MySQL");
    }

    protected boolean isPostgres() {
        return this.dialect.contains("PostgreSQL");
    }

    protected boolean isMariaDb() {
        return this.dialect.contains("MariaDB");
    }

    @Generated
    protected AbstractJpaEntityFactory(String dialect) {
        this.dialect = dialect;
    }

    @Generated
    public String getDialect() {
        return this.dialect;
    }
}

