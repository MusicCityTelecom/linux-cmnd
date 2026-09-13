/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import org.terracotta.management.resource.AbstractEntityV2;

public class QueryResultsEntityV2
extends AbstractEntityV2 {
    private String name;
    private Object[][] data;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public Object[][] getData() {
        return this.data;
    }
}

