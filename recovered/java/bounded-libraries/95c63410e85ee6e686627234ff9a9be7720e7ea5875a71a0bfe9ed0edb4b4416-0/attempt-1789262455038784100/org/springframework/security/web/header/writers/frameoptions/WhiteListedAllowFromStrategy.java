/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers.frameoptions;

import java.util.Collection;
import org.springframework.security.web.header.writers.frameoptions.AbstractRequestParameterAllowFromStrategy;
import org.springframework.util.Assert;

@Deprecated
public final class WhiteListedAllowFromStrategy
extends AbstractRequestParameterAllowFromStrategy {
    private final Collection<String> allowed;

    public WhiteListedAllowFromStrategy(Collection<String> allowed) {
        Assert.notEmpty(allowed, (String)"Allowed origins cannot be empty.");
        this.allowed = allowed;
    }

    @Override
    protected boolean allowed(String allowFromOrigin) {
        return this.allowed.contains(allowFromOrigin);
    }
}

