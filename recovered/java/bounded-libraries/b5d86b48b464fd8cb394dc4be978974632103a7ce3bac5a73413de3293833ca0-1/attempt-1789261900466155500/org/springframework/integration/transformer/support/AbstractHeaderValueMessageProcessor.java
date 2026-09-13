/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.transformer.support;

import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;

public abstract class AbstractHeaderValueMessageProcessor<T>
implements HeaderValueMessageProcessor<T> {
    private Boolean overwrite = null;

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    @Override
    public Boolean isOverwrite() {
        return this.overwrite;
    }
}

