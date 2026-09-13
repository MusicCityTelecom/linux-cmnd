/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.services.content.AddContentMessages;
import be.tpvision.smartcontrol.messages.services.content.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.content.DeleteContentMessages;
import be.tpvision.smartcontrol.messages.services.content.GetContentMessages;
import be.tpvision.smartcontrol.messages.services.content.GetContentOrderedByMessages;
import be.tpvision.smartcontrol.messages.services.content.UpdateContentMessages;
import be.tpvision.smartcontrol.repository.ContentRepository;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import be.tpvision.smartcontrol.service.ContentService;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ContentServiceImpl
implements ContentService {
    private final ContentRepository contentRepository;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository) {
        Assert.notNull((Object)contentRepository, ConstructorMessages.CONTENT_REPOSITORY_CAN_NOT_BE_NULL);
        this.contentRepository = contentRepository;
    }

    @Override
    public Set<Content> getContent() {
        List contentList = this.contentRepository.getAll();
        return new HashSet<Content>(contentList);
    }

    @Override
    public Set<Content> getContentOrderedBy(OrderField<? super Content> orderField, OrderDirection orderDirection) {
        Assert.notNull(orderField, GetContentOrderedByMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
        Assert.notNull((Object)orderDirection, GetContentOrderedByMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
        List<? super Content> contentList = this.contentRepository.getAllOrderedBy(orderField, orderDirection);
        return new LinkedHashSet<Content>(contentList);
    }

    @Override
    public Content getContent(String id) {
        Assert.notNull((Object)id, GetContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        return (Content)this.contentRepository.getById(id);
    }

    @Override
    @Transactional
    public void addContent(Content content) {
        Assert.notNull((Object)content, AddContentMessages.CONTENT_CAN_NOT_BE_NULL);
        this.contentRepository.persist(content);
    }

    @Override
    @Transactional
    public void updateContent(Content content) {
        Assert.notNull((Object)content, UpdateContentMessages.CONTENT_CAN_NOT_BE_NULL);
        this.contentRepository.merge(content);
    }

    @Override
    @Transactional
    public void deleteContent(Content content) {
        Assert.notNull((Object)content, DeleteContentMessages.CONTENT_CAN_NOT_BE_NULL);
        this.contentRepository.delete(content);
    }

    @Override
    @Transactional
    public void deleteContent(String id) {
        Assert.notNull((Object)id, DeleteContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        this.contentRepository.deleteById(id);
    }
}

