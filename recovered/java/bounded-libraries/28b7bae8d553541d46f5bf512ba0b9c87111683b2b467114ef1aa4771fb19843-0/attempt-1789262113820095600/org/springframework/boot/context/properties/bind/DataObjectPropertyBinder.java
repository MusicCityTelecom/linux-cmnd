/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

import org.springframework.boot.context.properties.bind.Bindable;

interface DataObjectPropertyBinder {
    public Object bindProperty(String var1, Bindable<?> var2);
}

