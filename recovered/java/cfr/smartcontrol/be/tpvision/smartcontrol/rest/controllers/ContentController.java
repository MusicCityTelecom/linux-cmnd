/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.controllers.content.ConstructorMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.content.OrderField;
import be.tpvision.smartcontrol.rest.mappers.content.ContentMapper;
import be.tpvision.smartcontrol.rest.view_models.content.ContentViewModel;
import be.tpvision.smartcontrol.service.ContentManagementService;
import be.tpvision.smartcontrol.service.ContentService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/content"})
public class ContentController {
    private final ContentService contentService;
    private final ContentManagementService contentManagementService;

    @Autowired
    public ContentController(ContentService contentService, ContentManagementService contentManagementService) {
        Assert.notNull((Object)contentService, ConstructorMessages.CONTENT_SERVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)contentManagementService, ConstructorMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
        this.contentService = contentService;
        this.contentManagementService = contentManagementService;
    }

    @GetMapping
    public List<ContentViewModel> getContentList() {
        this.contentManagementService.updateContent();
        Set<Content> contentSet = this.contentService.getContentOrderedBy(OrderField.TITLE, OrderDirection.ASC);
        return ContentMapper.toContentViewModelList(contentSet);
    }
}

