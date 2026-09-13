/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.common.web;

import org.apereo.inspektr.common.web.ClientInfo;

public class ClientInfoHolder {
    private static final ThreadLocal<ClientInfo> clientInfoHolder = new InheritableThreadLocal<ClientInfo>();

    public static void setClientInfo(ClientInfo clientInfo) {
        clientInfoHolder.set(clientInfo);
    }

    public static ClientInfo getClientInfo() {
        return clientInfoHolder.get();
    }

    public static void clear() {
        clientInfoHolder.remove();
    }
}

