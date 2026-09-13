/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.IdGenerator
 */
package org.springframework.messaging.support;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.IdGenerator;

public class IdTimestampMessageHeaderInitializer
implements MessageHeaderInitializer {
    private static final IdGenerator ID_VALUE_NONE_GENERATOR = () -> MessageHeaders.ID_VALUE_NONE;
    @Nullable
    private IdGenerator idGenerator;
    private boolean enableTimestamp;

    public void setIdGenerator(@Nullable IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Nullable
    public IdGenerator getIdGenerator() {
        return this.idGenerator;
    }

    public void setDisableIdGeneration() {
        this.idGenerator = ID_VALUE_NONE_GENERATOR;
    }

    public void setEnableTimestamp(boolean enableTimestamp) {
        this.enableTimestamp = enableTimestamp;
    }

    public boolean isEnableTimestamp() {
        return this.enableTimestamp;
    }

    @Override
    public void initHeaders(MessageHeaderAccessor headerAccessor) {
        IdGenerator idGenerator = this.getIdGenerator();
        if (idGenerator != null) {
            headerAccessor.setIdGenerator(idGenerator);
        }
        headerAccessor.setEnableTimestamp(this.isEnableTimestamp());
    }
}

