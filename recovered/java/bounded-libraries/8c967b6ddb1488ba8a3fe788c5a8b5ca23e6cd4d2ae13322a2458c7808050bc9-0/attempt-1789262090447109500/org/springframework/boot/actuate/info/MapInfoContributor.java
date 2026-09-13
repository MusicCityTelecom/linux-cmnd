/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.info;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

public class MapInfoContributor
implements InfoContributor {
    private final Map<String, Object> info;

    public MapInfoContributor(Map<String, Object> info) {
        this.info = new LinkedHashMap<String, Object>(info);
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetails(this.info);
    }
}

