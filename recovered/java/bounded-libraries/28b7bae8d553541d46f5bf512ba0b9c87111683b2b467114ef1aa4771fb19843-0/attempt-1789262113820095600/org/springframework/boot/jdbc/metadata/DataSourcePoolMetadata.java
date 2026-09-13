/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc.metadata;

public interface DataSourcePoolMetadata {
    public Float getUsage();

    public Integer getActive();

    default public Integer getIdle() {
        return null;
    }

    public Integer getMax();

    public Integer getMin();

    public String getValidationQuery();

    public Boolean getDefaultAutoCommit();
}

