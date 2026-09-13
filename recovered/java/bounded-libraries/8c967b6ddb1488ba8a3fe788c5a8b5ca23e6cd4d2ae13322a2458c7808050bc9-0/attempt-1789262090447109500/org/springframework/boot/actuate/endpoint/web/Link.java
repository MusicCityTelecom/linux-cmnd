/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.style.ToStringCreator
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.core.style.ToStringCreator;
import org.springframework.util.Assert;

public class Link {
    private final String href;
    private final boolean templated;

    public Link(String href) {
        Assert.notNull((Object)href, (String)"HREF must not be null");
        this.href = href;
        this.templated = href.contains("{");
    }

    public String getHref() {
        return this.href;
    }

    public boolean isTemplated() {
        return this.templated;
    }

    public String toString() {
        return new ToStringCreator((Object)this).append("href", (Object)this.href).toString();
    }
}

