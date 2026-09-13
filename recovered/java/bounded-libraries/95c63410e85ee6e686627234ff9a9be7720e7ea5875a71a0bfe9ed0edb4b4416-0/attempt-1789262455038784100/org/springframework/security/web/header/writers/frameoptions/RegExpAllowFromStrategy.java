/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers.frameoptions;

import java.util.regex.Pattern;
import org.springframework.security.web.header.writers.frameoptions.AbstractRequestParameterAllowFromStrategy;
import org.springframework.util.Assert;

@Deprecated
public final class RegExpAllowFromStrategy
extends AbstractRequestParameterAllowFromStrategy {
    private final Pattern pattern;

    public RegExpAllowFromStrategy(String pattern) {
        Assert.hasText((String)pattern, (String)"Pattern cannot be empty.");
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    protected boolean allowed(String allowFromOrigin) {
        return this.pattern.matcher(allowFromOrigin).matches();
    }
}

