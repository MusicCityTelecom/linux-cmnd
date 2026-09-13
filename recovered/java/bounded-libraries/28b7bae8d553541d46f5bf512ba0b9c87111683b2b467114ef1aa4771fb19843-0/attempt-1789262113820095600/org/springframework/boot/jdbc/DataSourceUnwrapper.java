/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.framework.AopProxyUtils
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.jdbc.datasource.DelegatingDataSource
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.jdbc;

import java.sql.Wrapper;
import javax.sql.DataSource;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.util.ClassUtils;

public final class DataSourceUnwrapper {
    private static final boolean DELEGATING_DATA_SOURCE_PRESENT = ClassUtils.isPresent((String)"org.springframework.jdbc.datasource.DelegatingDataSource", (ClassLoader)DataSourceUnwrapper.class.getClassLoader());

    private DataSourceUnwrapper() {
    }

    public static <I, T extends I> T unwrap(DataSource dataSource, Class<I> unwrapInterface, Class<T> target) {
        Object proxyTarget;
        DataSource targetDataSource;
        if (target.isInstance(dataSource)) {
            return target.cast(dataSource);
        }
        I unwrapped = DataSourceUnwrapper.safeUnwrap(dataSource, unwrapInterface);
        if (unwrapped != null && unwrapInterface.isAssignableFrom(target)) {
            return target.cast(unwrapped);
        }
        if (DELEGATING_DATA_SOURCE_PRESENT && (targetDataSource = DelegatingDataSourceUnwrapper.getTargetDataSource(dataSource)) != null) {
            return DataSourceUnwrapper.unwrap(targetDataSource, unwrapInterface, target);
        }
        if (AopUtils.isAopProxy((Object)dataSource) && (proxyTarget = AopProxyUtils.getSingletonTarget((Object)dataSource)) instanceof DataSource) {
            return DataSourceUnwrapper.unwrap((DataSource)proxyTarget, unwrapInterface, target);
        }
        return null;
    }

    public static <T> T unwrap(DataSource dataSource, Class<T> target) {
        return DataSourceUnwrapper.unwrap(dataSource, target, target);
    }

    private static <S> S safeUnwrap(Wrapper wrapper, Class<S> target) {
        try {
            if (target.isInterface() && wrapper.isWrapperFor(target)) {
                return wrapper.unwrap(target);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private static class DelegatingDataSourceUnwrapper {
        private DelegatingDataSourceUnwrapper() {
        }

        private static DataSource getTargetDataSource(DataSource dataSource) {
            if (dataSource instanceof DelegatingDataSource) {
                return ((DelegatingDataSource)dataSource).getTargetDataSource();
            }
            return null;
        }
    }
}

