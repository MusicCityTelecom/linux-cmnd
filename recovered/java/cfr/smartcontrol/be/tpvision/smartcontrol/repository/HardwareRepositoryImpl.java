/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.domain.Hardware_;
import be.tpvision.smartcontrol.messages.repositories.hardware.GetSingularAttributeMessages;
import be.tpvision.smartcontrol.repository.AbstractRepository;
import be.tpvision.smartcontrol.repository.HardwareRepository;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
@Profile(value={"production"})
public class HardwareRepositoryImpl
extends AbstractRepository<String, Hardware>
implements HardwareRepository {
    public HardwareRepositoryImpl() {
        super(Hardware.class);
    }

    @Override
    public SingularAttribute<? super Hardware, ?> getSingularAttribute(OrderField<? super Hardware> orderField) {
        Assert.notNull(orderField, GetSingularAttributeMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
        be.tpvision.smartcontrol.repository.order_fields.hardware.OrderField hardwareOrderField = (be.tpvision.smartcontrol.repository.order_fields.hardware.OrderField)orderField;
        switch (hardwareOrderField) {
            case HARDWARE_KEY: {
                return Hardware_.hardwareKey;
            }
            case CONTENT_ID: {
                return Hardware_.contentId;
            }
        }
        String orderFieldIsNotSupportedMessage = GetSingularAttributeMessages.getOrderFieldIsNotSupportedMessage(hardwareOrderField);
        throw new IllegalArgumentException(orderFieldIsNotSupportedMessage);
    }
}

