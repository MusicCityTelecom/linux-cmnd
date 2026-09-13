/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.util.context.Context;

interface ContextHolder {
    public Context currentContext();
}

