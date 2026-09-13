package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Device_;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.repositories.device.GetByIpDestinationMessages;
import be.tpvision.smartcontrol.messages.repositories.device.GetBySerialCodeMessages;
import be.tpvision.smartcontrol.messages.repositories.device.GetSingularAttributeMessages;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import be.tpvision.smartcontrol.util.DeviceUtilities;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

@org.springframework.stereotype.Repository
@Profile("production")
public class DeviceRepositoryImpl extends AbstractRepository<Long, Device> implements DeviceRepository {
   private final DeviceUtilities deviceUtilities;

   @Autowired
   public DeviceRepositoryImpl(final DeviceUtilities deviceUtilities) {
      super(Device.class);
      this.deviceUtilities = deviceUtilities;
   }

   @Override
   public SingularAttribute<? super Device, ?> getSingularAttribute(final OrderField<? super Device> orderField) {
      Assert.notNull(orderField, GetSingularAttributeMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
      be.tpvision.smartcontrol.repository.order_fields.device.OrderField deviceOrderField = (be.tpvision.smartcontrol.repository.order_fields.device.OrderField)orderField;
      switch (deviceOrderField) {
         case ID:
            return Device_.id;
         case NAME:
            return Device_.name;
         case MODEL_NUMBER:
            return Device_.modelNumber;
         case IP_DESTINATION:
            return Device_.address;
         case TEMPERATURE:
            return Device_.temperature;
         case POWER_STATE:
            return Device_.powerState;
         case INPUT_SOURCE:
            return Device_.inputSource;
         default:
            String orderFieldNotSupportedMessage = GetSingularAttributeMessages.getOrderFieldIsNotSupportedMessage(deviceOrderField);
            throw new IllegalArgumentException(orderFieldNotSupportedMessage);
      }
   }

   @Override
   public List<Device> getAllOrderedBy(final OrderField<? super Device> orderField, final OrderDirection orderDirection) {
      List<Device> deviceList = super.getAll();
      return this.deviceUtilities.orderDevices(deviceList, orderField, orderDirection);
   }

   @Override
   public Device getByIpDestination(final IpDestination ipDestination) {
      Assert.notNull(ipDestination, GetByIpDestinationMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
      EntityManager entityManager = super.getEntityManager();
      Assert.state(entityManager != null, GetByIpDestinationMessages.ENTITY_MANAGER_CAN_NOT_BE_NULL);
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      Assert.state(criteriaBuilder != null, GetByIpDestinationMessages.CRITERIA_BUILDER_CAN_NOT_BE_NULL);
      CriteriaQuery<Device> criteriaQuery = criteriaBuilder.createQuery(Device.class);
      Assert.state(criteriaQuery != null, GetByIpDestinationMessages.CRITERIA_QUERY_CAN_NOT_BE_NULL);
      Predicate predicate = criteriaBuilder.equal(criteriaQuery.from(Device.class).get(Device_.address), ipDestination);
      criteriaQuery.where(predicate);
      Query query = entityManager.createQuery(criteriaQuery);
      Assert.state(query != null, GetByIpDestinationMessages.QUERY_CAN_NOT_BE_NULL);

      try {
         return (Device)query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   public Device getBySerialCode(final String serialCode) {
      Assert.notNull(serialCode, GetBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_NULL);
      Assert.hasLength(serialCode, GetBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_EMPTY);
      StringWrapper serialCodeStringWrapper = new StringWrapper(serialCode);
      EntityManager entityManager = super.getEntityManager();
      Assert.state(entityManager != null, GetBySerialCodeMessages.ENTITY_MANAGER_CAN_NOT_BE_NULL);
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      Assert.state(criteriaBuilder != null, GetBySerialCodeMessages.CRITERIA_BUILDER_CAN_NOT_BE_NULL);
      CriteriaQuery<Device> criteriaQuery = criteriaBuilder.createQuery(Device.class);
      Assert.state(criteriaQuery != null, GetBySerialCodeMessages.CRITERIA_QUERY_CAN_NOT_BE_NULL);
      Predicate predicate = criteriaBuilder.equal(criteriaQuery.from(Device.class).get(Device_.serialCode), serialCodeStringWrapper);
      criteriaQuery.where(predicate);
      Query query = entityManager.createQuery(criteriaQuery);
      Assert.state(query != null, GetBySerialCodeMessages.QUERY_CAN_NOT_BE_NULL);

      try {
         return (Device)query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }
}
