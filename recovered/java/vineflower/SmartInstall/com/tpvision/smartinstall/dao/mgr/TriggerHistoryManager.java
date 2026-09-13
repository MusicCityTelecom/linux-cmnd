package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.TriggerHistoryRepository;
import com.tpvision.smartinstall.dao.core.TriggerHistoryInfo;
import java.sql.SQLException;
import java.util.List;
import javax.transaction.Transactional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TriggerHistoryManager {
   private static final Logger LOG = LoggerFactory.getLogger(TriggerHistoryManager.class);
   @Autowired
   private TriggerHistoryRepository triggerHistoryRepository;

   public List<TriggerHistoryInfo> findAll() {
      return this.triggerHistoryRepository.findAll();
   }

   public JSONObject findTriggerHistoryPageBySearchParam(SearchParam sp) {
      try {
         return JpaManager.findSimpleLikeDataPageBySearchParam("trigger_history", sp);
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public TriggerHistoryInfo save(TriggerHistoryInfo triggerHistory) {
      return this.triggerHistoryRepository.save(triggerHistory);
   }

   public void updateResult(int id, String result) {
      this.triggerHistoryRepository.updateResult(id, result);
   }

   public void deleteAll() {
      this.triggerHistoryRepository.truncateTable();
   }
}
