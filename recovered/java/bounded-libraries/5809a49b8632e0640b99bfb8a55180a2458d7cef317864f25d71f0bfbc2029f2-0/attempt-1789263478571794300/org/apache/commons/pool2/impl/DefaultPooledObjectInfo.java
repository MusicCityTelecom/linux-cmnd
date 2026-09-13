/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObjectInfoMBean;

public class DefaultPooledObjectInfo
implements DefaultPooledObjectInfoMBean {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss Z";
    private final PooledObject<?> pooledObject;

    public DefaultPooledObjectInfo(PooledObject<?> pooledObject) {
        this.pooledObject = pooledObject;
    }

    @Override
    public long getBorrowedCount() {
        return this.pooledObject.getBorrowedCount();
    }

    @Override
    public long getCreateTime() {
        return this.pooledObject.getCreateInstant().toEpochMilli();
    }

    @Override
    public String getCreateTimeFormatted() {
        return this.getTimeFormatted(this.getCreateTime());
    }

    @Override
    public long getLastBorrowTime() {
        return this.pooledObject.getLastBorrowInstant().toEpochMilli();
    }

    @Override
    public String getLastBorrowTimeFormatted() {
        return this.getTimeFormatted(this.getLastBorrowTime());
    }

    @Override
    public String getLastBorrowTrace() {
        StringWriter sw = new StringWriter();
        this.pooledObject.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    @Override
    public long getLastReturnTime() {
        return this.pooledObject.getLastReturnInstant().toEpochMilli();
    }

    @Override
    public String getLastReturnTimeFormatted() {
        return this.getTimeFormatted(this.getLastReturnTime());
    }

    @Override
    public String getPooledObjectToString() {
        return this.pooledObject.getObject().toString();
    }

    @Override
    public String getPooledObjectType() {
        return this.pooledObject.getObject().getClass().getName();
    }

    private String getTimeFormatted(long millis) {
        return new SimpleDateFormat(PATTERN).format(millis);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DefaultPooledObjectInfo [pooledObject=");
        builder.append(this.pooledObject);
        builder.append("]");
        return builder.toString();
    }
}

