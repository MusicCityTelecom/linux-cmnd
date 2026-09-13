package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import java.util.Set;

public interface ContentServiceJdbc {
   Set<Content> getContent();

   Content getContent(String id);

   void addContent(Content content);

   void updateContent(Content content);

   void deleteContent(Content content);
}
