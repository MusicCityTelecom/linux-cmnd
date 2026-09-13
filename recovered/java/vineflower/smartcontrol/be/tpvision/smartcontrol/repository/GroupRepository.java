package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.List;

public interface GroupRepository extends Repository<Long, Group> {
   Group getByIdWithDevicesOrderedBy(long id, OrderField<? super Device> devicesOrderField, OrderDirection devicesOrderDirection);

   List<Group> getAllOrderedByWithDevicesOrderedBy(
      OrderField<? super Group> groupOrderField,
      OrderDirection groupOrderDirection,
      OrderField<? super Device> devicesOrderField,
      OrderDirection devicesOrderDirection
   );
}
