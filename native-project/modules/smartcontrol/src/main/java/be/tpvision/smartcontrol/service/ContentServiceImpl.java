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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ContentServiceImpl implements ContentService {
   private final ContentRepository contentRepository;

   @Autowired
   public ContentServiceImpl(final ContentRepository contentRepository) {
      Assert.notNull(contentRepository, ConstructorMessages.CONTENT_REPOSITORY_CAN_NOT_BE_NULL);
      this.contentRepository = contentRepository;
   }

   @Override
   public Set<Content> getContent() {
      List<Content> contentList = this.contentRepository.getAll();
      return new HashSet<>(contentList);
   }

   @Override
   public Set<Content> getContentOrderedBy(final OrderField<? super Content> orderField, final OrderDirection orderDirection) {
      Assert.notNull(orderField, GetContentOrderedByMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
      Assert.notNull(orderDirection, GetContentOrderedByMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
      List<Content> contentList = this.contentRepository.getAllOrderedBy(orderField, orderDirection);
      return new LinkedHashSet<>(contentList);
   }

   @Override
   public Content getContent(final String id) {
      Assert.notNull(id, GetContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
      return this.contentRepository.getById(id);
   }

   @Transactional
   @Override
   public void addContent(final Content content) {
      Assert.notNull(content, AddContentMessages.CONTENT_CAN_NOT_BE_NULL);
      this.contentRepository.persist(content);
   }

   @Transactional
   @Override
   public void updateContent(final Content content) {
      Assert.notNull(content, UpdateContentMessages.CONTENT_CAN_NOT_BE_NULL);
      this.contentRepository.merge(content);
   }

   @Transactional
   @Override
   public void deleteContent(final Content content) {
      Assert.notNull(content, DeleteContentMessages.CONTENT_CAN_NOT_BE_NULL);
      this.contentRepository.delete(content);
   }

   @Transactional
   @Override
   public void deleteContent(final String id) {
      Assert.notNull(id, DeleteContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
      this.contentRepository.deleteById(id);
   }
}
