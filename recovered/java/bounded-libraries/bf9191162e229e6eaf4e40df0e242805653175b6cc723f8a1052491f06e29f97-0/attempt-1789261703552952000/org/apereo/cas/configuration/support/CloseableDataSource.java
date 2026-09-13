/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.DisposableBean
 */
package org.apereo.cas.configuration.support;

import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.beans.factory.DisposableBean;

public interface CloseableDataSource
extends DataSource,
DisposableBean {
    public void close() throws IOException;

    public DataSource getTargetDataSource();

    default public void destroy() throws Exception {
        this.close();
    }
}

