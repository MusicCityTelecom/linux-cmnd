/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import be.tpvision.smartcontrol.service.exceptions.AddGroupResult;
import java.util.Set;

public interface GroupService {
    public Set<Group> getGroups();

    public Group getGroup(long var1);

    public Group getGroup(long var1, OrderField<? super Device> var3, OrderDirection var4);

    public AddGroupResult addGroup(Group var1);

    public void updateGroup(Group var1);

    public void deleteGroup(Group var1);

    public void deleteGroup(long var1);
}

