/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.services.jdbc.content.AddContentMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.content.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.content.DeleteContentMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.content.GetContentMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.content.UpdateContentMessages;
import be.tpvision.smartcontrol.repository.ContentRepositoryJdbc;
import be.tpvision.smartcontrol.service.ContentServiceJdbc;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ContentServiceJdbcImpl
implements ContentServiceJdbc {
    private final ContentRepositoryJdbc contentRepositoryJdbc;

    @Autowired
    public ContentServiceJdbcImpl(ContentRepositoryJdbc contentRepositoryJdbc) {
        Assert.notNull((Object)contentRepositoryJdbc, ConstructorMessages.CONTENT_REPOSITORY_JDBC_CAN_NOT_BE_NULL);
        this.contentRepositoryJdbc = contentRepositoryJdbc;
    }

    @Override
    public Set<Content> getContent() {
        return this.contentRepositoryJdbc.getContent();
    }

    @Override
    public Content getContent(String id) {
        Assert.notNull((Object)id, GetContentMessages.ID_CAN_NOT_BE_NULL);
        Assert.isTrue(!id.isEmpty(), GetContentMessages.ID_CAN_NOT_BE_EMPTY);
        return this.contentRepositoryJdbc.getContent(id);
    }

    @Override
    public void addContent(Content content) {
        Assert.notNull((Object)content, AddContentMessages.CONTENT_CAN_NOT_BE_NULL);
        this.contentRepositoryJdbc.addContent(content);
    }

    @Override
    public void updateContent(Content content) {
        Assert.notNull((Object)content, UpdateContentMessages.CONTENT_CAN_NOT_BE_NULL);
        this.contentRepositoryJdbc.updateContent(content);
    }

    @Override
    public void deleteContent(Content content) {
        Assert.notNull((Object)content, DeleteContentMessages.CONTENT_CAN_NOT_BE_NULL);
        this.contentRepositoryJdbc.deleteContent(content);
    }
}

