/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  liquibase.exception.LiquibaseException
 *  liquibase.integration.spring.SpringLiquibase
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.autoconfigure.liquibase;

import java.lang.reflect.Method;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.ReflectionUtils;

public class DataSourceClosingSpringLiquibase
extends SpringLiquibase
implements DisposableBean {
    private volatile boolean closeDataSourceOnceMigrated = true;

    public void setCloseDataSourceOnceMigrated(boolean closeDataSourceOnceMigrated) {
        this.closeDataSourceOnceMigrated = closeDataSourceOnceMigrated;
    }

    public void afterPropertiesSet() throws LiquibaseException {
        super.afterPropertiesSet();
        if (this.closeDataSourceOnceMigrated) {
            this.closeDataSource();
        }
    }

    private void closeDataSource() {
        Class<?> dataSourceClass = this.getDataSource().getClass();
        Method closeMethod = ReflectionUtils.findMethod(dataSourceClass, (String)"close");
        if (closeMethod != null) {
            ReflectionUtils.invokeMethod((Method)closeMethod, (Object)this.getDataSource());
        }
    }

    public void destroy() throws Exception {
        if (!this.closeDataSourceOnceMigrated) {
            this.closeDataSource();
        }
    }
}

