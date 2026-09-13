/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.domain.Group_;
import be.tpvision.smartcontrol.messages.repositories.group.GetAllOrderedByWithDevicesOrderedByMessages;
import be.tpvision.smartcontrol.messages.repositories.group.GetByIdWithDevicesOrderedByMessages;
import be.tpvision.smartcontrol.messages.repositories.group.GetSingularAttributeMessages;
import be.tpvision.smartcontrol.repository.AbstractRepository;
import be.tpvision.smartcontrol.repository.GroupRepository;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.group.OrderField;
import be.tpvision.smartcontrol.util.DeviceUtilities;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
@Profile(value={"production"})
public class GroupRepositoryImpl
extends AbstractRepository<Long, Group>
implements GroupRepository {
    private final DeviceUtilities deviceUtilities;

    @Autowired
    public GroupRepositoryImpl(DeviceUtilities deviceUtilities) {
        super(Group.class);
        this.deviceUtilities = deviceUtilities;
    }

    @Override
    public SingularAttribute<? super Group, ?> getSingularAttribute(be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Group> orderField) {
        Assert.notNull(orderField, GetSingularAttributeMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
        OrderField groupOrderField = (OrderField)orderField;
        switch (groupOrderField) {
            case ID: {
                return Group_.id;
            }
            case NAME: {
                return Group_.name;
            }
        }
        String orderFieldNotSupportedMessage = GetSingularAttributeMessages.getOrderFieldIsNotSupportedMessage(groupOrderField);
        throw new IllegalArgumentException(orderFieldNotSupportedMessage);
    }

    @Override
    public Group getByIdWithDevicesOrderedBy(long id, be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Device> devicesOrderField, OrderDirection devicesOrderDirection) {
        Assert.notNull(devicesOrderField, GetByIdWithDevicesOrderedByMessages.DEVICES_ORDER_FIELD_CAN_NOT_BE_NULL);
        Assert.notNull((Object)devicesOrderDirection, GetByIdWithDevicesOrderedByMessages.DEVICES_ORDER_DIRECTION_CAN_NOT_BE_NULL);
        Group group = (Group)super.getById(id);
        if (group == null) {
            return null;
        }
        Set<Device> deviceSet = group.getDevices();
        List<Device> orderedDeviceList = this.deviceUtilities.orderDevices(deviceSet, devicesOrderField, devicesOrderDirection);
        deviceSet = new LinkedHashSet<Device>(orderedDeviceList);
        group.setDevices(deviceSet);
        return group;
    }

    @Override
    public List<Group> getAllOrderedByWithDevicesOrderedBy(be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Group> groupOrderField, OrderDirection groupOrderDirection, be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Device> devicesOrderField, OrderDirection devicesOrderDirection) {
        Assert.notNull(groupOrderField, GetAllOrderedByWithDevicesOrderedByMessages.GROUP_ORDER_FIELD_CAN_NOT_BE_NULL);
        Assert.notNull((Object)groupOrderDirection, GetAllOrderedByWithDevicesOrderedByMessages.GROUP_ORDER_DIRECTION_CAN_NOT_BE_NULL);
        SingularAttribute<? super Group, ?> singularAttribute = this.getSingularAttribute(groupOrderField);
        Assert.state(singularAttribute != null, GetAllOrderedByWithDevicesOrderedByMessages.SINGULAR_ATTRIBUTE_CAN_NOT_BE_NULL);
        EntityManager entityManager = super.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Assert.state(criteriaBuilder != null, GetAllOrderedByWithDevicesOrderedByMessages.CRITERIA_BUILDER_CAN_NOT_BE_NULL);
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Assert.state(criteriaQuery != null, GetAllOrderedByWithDevicesOrderedByMessages.CRITERIA_QUERY_CAN_NOT_BE_NULL);
        Root<Group> root = criteriaQuery.from(Group.class);
        Assert.state(root != null, GetAllOrderedByWithDevicesOrderedByMessages.ROOT_CAN_NOT_BE_NULL);
        Path<?> expression = root.get(singularAttribute);
        Assert.state(expression != null, GetAllOrderedByWithDevicesOrderedByMessages.EXPRESSION_CAN_NOT_BE_NULL);
        boolean isOrderDirectionDesc = groupOrderDirection.equals((Object)OrderDirection.DESC);
        Order order = isOrderDirectionDesc ? criteriaBuilder.desc(expression) : criteriaBuilder.asc(expression);
        Assert.state(order != null, GetAllOrderedByWithDevicesOrderedByMessages.ORDER_CAN_NOT_BE_NULL);
        criteriaQuery.orderBy(order);
        TypedQuery<Group> query = entityManager.createQuery(criteriaQuery);
        Assert.state(query != null, GetAllOrderedByWithDevicesOrderedByMessages.QUERY_CAN_NOT_BE_NULL);
        List groups = query.getResultList();
        groups.forEach(group -> {
            Set<Device> deviceSet = group.getDevices();
            List<Device> orderedDeviceList = this.deviceUtilities.orderDevices(deviceSet, devicesOrderField, devicesOrderDirection);
            deviceSet = new LinkedHashSet<Device>(orderedDeviceList);
            group.setDevices(deviceSet);
        });
        return groups;
    }
}

