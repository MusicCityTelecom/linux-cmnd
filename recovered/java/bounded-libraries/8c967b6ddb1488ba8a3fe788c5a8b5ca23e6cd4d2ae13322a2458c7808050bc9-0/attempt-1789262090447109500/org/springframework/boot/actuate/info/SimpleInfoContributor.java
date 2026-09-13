/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.util.Assert;

public class SimpleInfoContributor
implements InfoContributor {
    private final String prefix;
    private final Object detail;

    public SimpleInfoContributor(String prefix, Object detail) {
        Assert.notNull((Object)prefix, (String)"Prefix must not be null");
        this.prefix = prefix;
        this.detail = detail;
    }

    @Override
    public void contribute(Info.Builder builder) {
        if (this.detail != null) {
            builder.withDetail(this.prefix, this.detail);
        }
    }
}

