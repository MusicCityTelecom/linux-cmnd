/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartcms.dao;

import com.tpvision.smartcms.model.Website;
import java.util.List;

public interface WebsiteDAO {
    public int addEntity(Website var1);

    public void updateEntity(Website var1);

    public Website getEntityById(int var1);

    public Website getEntityByIdList(int var1);

    public void deleteEntity(int var1);

    public List<Website> getEntityList(String var1);
}

