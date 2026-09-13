package com.tpvision.smartcms.dao;

import com.tpvision.smartcms.model.Publish;
import java.util.List;

public interface PublishDAO {
   int addEntity(Publish var1);

   void updateEntity(Publish var1);

   Publish getEntityById(int var1);

   void deleteEntity(int var1);

   List<Publish> getEntityList(String var1);
}
