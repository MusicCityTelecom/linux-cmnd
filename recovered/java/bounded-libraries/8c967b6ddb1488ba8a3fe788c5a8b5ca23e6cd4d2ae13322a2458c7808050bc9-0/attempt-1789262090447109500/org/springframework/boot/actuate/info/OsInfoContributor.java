/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.info.OsInfo
 */
package org.springframework.boot.actuate.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.OsInfo;

public class OsInfoContributor
implements InfoContributor {
    private final OsInfo osInfo = new OsInfo();

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("os", this.osInfo);
    }
}

