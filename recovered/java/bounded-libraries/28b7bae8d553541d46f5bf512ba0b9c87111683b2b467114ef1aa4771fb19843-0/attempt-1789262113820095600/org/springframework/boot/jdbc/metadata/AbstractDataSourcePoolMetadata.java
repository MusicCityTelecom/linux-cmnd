/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc.metadata;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;

public abstract class AbstractDataSourcePoolMetadata<T extends DataSource>
implements DataSourcePoolMetadata {
    private final T dataSource;

    protected AbstractDataSourcePoolMetadata(T dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Float getUsage() {
        Integer maxSize = this.getMax();
        Integer currentSize = this.getActive();
        if (maxSize == null || currentSize == null) {
            return null;
        }
        if (maxSize < 0) {
            return Float.valueOf(-1.0f);
        }
        if (currentSize == 0) {
            return Float.valueOf(0.0f);
        }
        return Float.valueOf((float)currentSize.intValue() / (float)maxSize.intValue());
    }

    protected final T getDataSource() {
        return this.dataSource;
    }
}

