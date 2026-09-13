package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.domain.Hardware_;
import be.tpvision.smartcontrol.messages.repositories.hardware.GetSingularAttributeMessages;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

@org.springframework.stereotype.Repository
@Profile("production")
public class HardwareRepositoryImpl extends AbstractRepository<String, Hardware> implements HardwareRepository {
   public HardwareRepositoryImpl() {
      super(Hardware.class);
   }

   @Override
   public SingularAttribute<? super Hardware, ?> getSingularAttribute(OrderField<? super Hardware> orderField) {
      Assert.notNull(orderField, GetSingularAttributeMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
      be.tpvision.smartcontrol.repository.order_fields.hardware.OrderField hardwareOrderField = (be.tpvision.smartcontrol.repository.order_fields.hardware.OrderField)orderField;
      switch (hardwareOrderField) {
         case HARDWARE_KEY:
            return Hardware_.hardwareKey;
         case CONTENT_ID:
            return Hardware_.contentId;
         default:
            String orderFieldIsNotSupportedMessage = GetSingularAttributeMessages.getOrderFieldIsNotSupportedMessage(hardwareOrderField);
            throw new IllegalArgumentException(orderFieldIsNotSupportedMessage);
      }
   }
}
