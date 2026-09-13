package com.tpvision.smartcms.dao;

import com.tpvision.smartcms.model.Website;
import com.tpvision.smartcms.utils.Util;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class WebsiteDAOImpl implements WebsiteDAO {
   @Autowired
   private SessionFactory sessionFactory;

   private Session getCurrentSession() {
      return this.sessionFactory.getCurrentSession();
   }

   @Override
   public int addEntity(Website website) {
      Integer i = (Integer)this.getCurrentSession().save(website);
      return i;
   }

   @Override
   public void updateEntity(Website website) {
      this.getCurrentSession().saveOrUpdate(website);
   }

   @Override
   public Website getEntityById(int id) {
      return this.getCurrentSession().get(Website.class, id);
   }

   @Override
   public void deleteEntity(int id) {
      Website website = this.getEntityById(id);
      if (website != null) {
         this.getCurrentSession().delete(website);
      }
   }

   @Override
   public Website getEntityByIdList(int id) {
      Query<Website> query = this.getCurrentSession().createQuery("from Website w where w.id = :id && w.success = '1'", Website.class);
      query.setParameter("id", id);
      List<Website> marks = query.list();
      return marks != null && !marks.isEmpty() ? marks.get(0) : null;
   }

   @Transactional(readOnly = true)
   @Override
   public List<Website> getEntityList(String orientation) {
      if (Util.isNumeric(orientation)) {
         Query<Website> query = this.getCurrentSession()
            .createQuery("from Website w where w.orientation = :orientation && w.success = '1' ORDER BY w.id ASC", Website.class)
            .setParameter("orientation", orientation);
         return query.list();
      } else {
         Query<Website> query = this.getCurrentSession().createQuery("from Website w where w.success = '1' ORDER BY w.id ASC", Website.class);
         return query.list();
      }
   }
}
