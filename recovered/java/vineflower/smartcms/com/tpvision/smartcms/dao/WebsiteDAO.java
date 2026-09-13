package com.tpvision.smartcms.dao;

import com.tpvision.smartcms.model.Website;
import java.util.List;

public interface WebsiteDAO {
   int addEntity(Website var1);

   void updateEntity(Website var1);

   Website getEntityById(int var1);

   Website getEntityByIdList(int var1);

   void deleteEntity(int var1);

   List<Website> getEntityList(String var1);
}
