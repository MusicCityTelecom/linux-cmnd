/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.domain.Content_;
import be.tpvision.smartcontrol.messages.repositories.content.GetSingularAttributeMessages;
import be.tpvision.smartcontrol.repository.AbstractRepository;
import be.tpvision.smartcontrol.repository.ContentRepository;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class ContentRepositoryImpl
extends AbstractRepository<String, Content>
implements ContentRepository {
    public ContentRepositoryImpl() {
        super(Content.class);
    }

    @Override
    public SingularAttribute<? super Content, ?> getSingularAttribute(OrderField<? super Content> orderField) {
        Assert.notNull(orderField, GetSingularAttributeMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
        be.tpvision.smartcontrol.repository.order_fields.content.OrderField contentOrderField = (be.tpvision.smartcontrol.repository.order_fields.content.OrderField)orderField;
        switch (contentOrderField) {
            case ID: {
                return Content_.id;
            }
            case TITLE: {
                return Content_.title;
            }
            case CREATED: {
                return Content_.created;
            }
            case CHANGED: {
                return Content_.changed;
            }
            case ORIENTATION: {
                return Content_.orientation;
            }
            case THUMBNAIL: {
                return Content_.thumbnail;
            }
            case LOCAL_CHANGED: {
                return Content_.localChanged;
            }
            case PUBLISH_DATE: {
                return Content_.publishDate;
            }
        }
        String orderFieldIsNotSupportedMessage = GetSingularAttributeMessages.getOrderFieldIsNotSupportedMessage(contentOrderField);
        throw new IllegalArgumentException(orderFieldIsNotSupportedMessage);
    }
}

