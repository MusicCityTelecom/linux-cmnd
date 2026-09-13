/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jta.atomikos;

import javax.sql.XADataSource;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;

public class AtomikosXADataSourceWrapper
implements XADataSourceWrapper {
    public AtomikosDataSourceBean wrapDataSource(XADataSource dataSource) throws Exception {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaDataSource(dataSource);
        return bean;
    }
}

