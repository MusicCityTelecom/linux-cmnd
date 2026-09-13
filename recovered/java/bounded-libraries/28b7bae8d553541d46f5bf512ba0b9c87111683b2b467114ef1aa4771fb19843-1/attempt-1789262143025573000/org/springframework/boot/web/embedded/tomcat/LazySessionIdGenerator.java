/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.LifecycleException
 *  org.apache.catalina.LifecycleState
 *  org.apache.catalina.util.StandardSessionIdGenerator
 */
package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.util.StandardSessionIdGenerator;

class LazySessionIdGenerator
extends StandardSessionIdGenerator {
    LazySessionIdGenerator() {
    }

    protected void startInternal() throws LifecycleException {
        this.setState(LifecycleState.STARTING);
    }
}

