/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket;

public interface TicketDefinitionProperties {
    public void setExcludeFromCascade(boolean var1);

    public boolean isExcludeFromCascade();

    public boolean isCascadeRemovals();

    public void setCascadeRemovals(boolean var1);

    public String getStorageName();

    public void setStorageName(String var1);

    public long getStorageTimeout();

    public void setStorageTimeout(long var1);

    public String getStoragePassword();

    public void setStoragePassword(String var1);
}

