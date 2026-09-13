package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Content;
import java.util.Set;

public interface ContentRepositoryJdbc {
   Set<Content> getContent();

   Content getContent(String id);

   void addContent(Content content);

   void updateContent(Content content);

   void deleteContent(Content content);
}
