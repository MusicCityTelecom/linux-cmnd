package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Set;

public interface ContentService {
   Set<Content> getContent();

   Set<Content> getContentOrderedBy(OrderField<? super Content> orderField, OrderDirection orderDirection);

   Content getContent(String id);

   void addContent(Content content);

   void updateContent(Content content);

   void deleteContent(Content content);

   void deleteContent(String id);
}
