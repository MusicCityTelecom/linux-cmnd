package com.tpvision.smartcms.dao;

import com.tpvision.smartcms.model.Publish;
import com.tpvision.smartcms.utils.Util;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class PublishDAOImpl implements PublishDAO {
   @Autowired
   private SessionFactory sessionFactory;

   private Session getCurrentSession() {
      return this.sessionFactory.getCurrentSession();
   }

   @Override
   public int addEntity(Publish publish) {
      Integer i = (Integer)this.getCurrentSession().save(publish);
      return i;
   }

   @Override
   public void updateEntity(Publish website) {
      this.getCurrentSession().saveOrUpdate(website);
   }

   @Override
   public Publish getEntityById(int id) {
      return this.getCurrentSession().get(Publish.class, id);
   }

   @Override
   public void deleteEntity(int id) {
      Publish website = this.getEntityById(id);
      if (website != null) {
         this.getCurrentSession().delete(website);
      }
   }

   @Transactional(readOnly = true)
   @Override
   public List<Publish> getEntityList(String orientation) {
      List l = null;
      if (Util.isNumeric(orientation)) {
         String queryString = "FROM Publish as p LEFT JOIN FETCH p.websites as w WHERE w.orientation = :orientation";
         l = this.getCurrentSession().createQuery(queryString).setParameter(orientation, orientation).list();
      } else {
         String queryString = "FROM Publish as p";
         l = this.getCurrentSession().createQuery(queryString).list();
      }

      return l;
   }
}
