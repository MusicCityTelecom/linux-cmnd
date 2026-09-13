/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.web.support.ThrottledSubmission
 */
package org.apereo.cas.throttle;

import java.util.concurrent.ConcurrentHashMap;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.throttle.BaseMappableThrottledSubmissionsStore;
import org.apereo.cas.web.support.ThrottledSubmission;

public class ConcurrentThrottledSubmissionsStore
extends BaseMappableThrottledSubmissionsStore<ThrottledSubmission> {
    public ConcurrentThrottledSubmissionsStore(CasConfigurationProperties casProperties) {
        super(new ConcurrentHashMap(), casProperties);
    }
}

