/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.web.support;

import org.apereo.cas.web.support.ThrottledSubmission;

@FunctionalInterface
public interface ThrottledSubmissionReceiver<T extends ThrottledSubmission> {
    public void receive(T var1) throws Exception;
}

