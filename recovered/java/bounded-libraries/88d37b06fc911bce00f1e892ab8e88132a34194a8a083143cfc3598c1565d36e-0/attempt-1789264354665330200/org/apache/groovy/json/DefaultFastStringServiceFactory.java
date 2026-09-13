/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json;

import org.apache.groovy.json.DefaultFastStringService;
import org.apache.groovy.json.FastStringService;
import org.apache.groovy.json.FastStringServiceFactory;

public class DefaultFastStringServiceFactory
implements FastStringServiceFactory {
    @Override
    public FastStringService getService() {
        return new DefaultFastStringService();
    }
}

