/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartcms.dao;

import com.tpvision.smartcms.model.Publish;
import java.util.List;

public interface PublishDAO {
    public int addEntity(Publish var1);

    public void updateEntity(Publish var1);

    public Publish getEntityById(int var1);

    public void deleteEntity(int var1);

    public List<Publish> getEntityList(String var1);
}

