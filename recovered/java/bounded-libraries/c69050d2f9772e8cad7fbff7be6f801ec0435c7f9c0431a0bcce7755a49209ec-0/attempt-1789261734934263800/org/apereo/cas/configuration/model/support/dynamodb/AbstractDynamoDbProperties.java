/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.dynamodb;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-dynamodb-core")
public abstract class AbstractDynamoDbProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = -8349917272283787550L;
    private boolean dropTablesOnStartup;
    private boolean preventTableCreationOnStartup;
    private int timeOffset;
    private long readCapacity = 10L;
    private long writeCapacity = 10L;
    private BillingMode billingMode = BillingMode.PROVISIONED;
    private boolean localInstance;

    @Generated
    public boolean isDropTablesOnStartup() {
        return this.dropTablesOnStartup;
    }

    @Generated
    public boolean isPreventTableCreationOnStartup() {
        return this.preventTableCreationOnStartup;
    }

    @Generated
    public int getTimeOffset() {
        return this.timeOffset;
    }

    @Generated
    public long getReadCapacity() {
        return this.readCapacity;
    }

    @Generated
    public long getWriteCapacity() {
        return this.writeCapacity;
    }

    @Generated
    public BillingMode getBillingMode() {
        return this.billingMode;
    }

    @Generated
    public boolean isLocalInstance() {
        return this.localInstance;
    }

    @Generated
    public AbstractDynamoDbProperties setDropTablesOnStartup(boolean dropTablesOnStartup) {
        this.dropTablesOnStartup = dropTablesOnStartup;
        return this;
    }

    @Generated
    public AbstractDynamoDbProperties setPreventTableCreationOnStartup(boolean preventTableCreationOnStartup) {
        this.preventTableCreationOnStartup = preventTableCreationOnStartup;
        return this;
    }

    @Generated
    public AbstractDynamoDbProperties setTimeOffset(int timeOffset) {
        this.timeOffset = timeOffset;
        return this;
    }

    @Generated
    public AbstractDynamoDbProperties setReadCapacity(long readCapacity) {
        this.readCapacity = readCapacity;
        return this;
    }

    @Generated
    public AbstractDynamoDbProperties setWriteCapacity(long writeCapacity) {
        this.writeCapacity = writeCapacity;
        return this;
    }

    @Generated
    public AbstractDynamoDbProperties setBillingMode(BillingMode billingMode) {
        this.billingMode = billingMode;
        return this;
    }

    @Generated
    public AbstractDynamoDbProperties setLocalInstance(boolean localInstance) {
        this.localInstance = localInstance;
        return this;
    }

    public static enum BillingMode {
        PROVISIONED,
        PAY_PER_REQUEST;

    }
}

