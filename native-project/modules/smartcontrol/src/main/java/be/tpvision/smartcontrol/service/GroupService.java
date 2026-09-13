package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import be.tpvision.smartcontrol.service.exceptions.AddGroupResult;
import java.util.Set;

public interface GroupService {
   Set<Group> getGroups();

   Group getGroup(long id);

   Group getGroup(long id, OrderField<? super Device> deviceOrderField, OrderDirection deviceOrderDirection);

   AddGroupResult addGroup(Group group);

   void updateGroup(Group group);

   void deleteGroup(Group group);

   void deleteGroup(long id);
}
