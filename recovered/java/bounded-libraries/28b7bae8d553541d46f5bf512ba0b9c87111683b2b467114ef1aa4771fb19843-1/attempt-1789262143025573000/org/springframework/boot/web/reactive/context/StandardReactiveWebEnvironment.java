/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.StandardEnvironment
 */
package org.springframework.boot.web.reactive.context;

import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

public class StandardReactiveWebEnvironment
extends StandardEnvironment
implements ConfigurableReactiveWebEnvironment {
    public StandardReactiveWebEnvironment() {
    }

    protected StandardReactiveWebEnvironment(MutablePropertySources propertySources) {
        super(propertySources);
    }
}

