/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.info.JavaInfo
 */
package org.springframework.boot.actuate.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.JavaInfo;

public class JavaInfoContributor
implements InfoContributor {
    private final JavaInfo javaInfo = new JavaInfo();

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("java", this.javaInfo);
    }
}

