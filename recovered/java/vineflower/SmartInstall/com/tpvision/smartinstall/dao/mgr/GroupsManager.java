package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.GroupsRepository;
import com.tpvision.smartinstall.dao.core.Groups;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupsManager {
   private static final Logger LOG = LoggerFactory.getLogger(GroupsManager.class);
   @Autowired
   private GroupsRepository groupsRepository;

   public JSONObject findGroupInfoViewPageBySearchParam(SearchParam sp) {
      try {
         JSONObject data = JpaManager.findSimpleLikeDataPageBySearchParam("groupinfo_view", sp);
         JSONArray rows = data.optJSONArray("rows");

         for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            row.put("supportRemoteControl", PlatformUtils.isSupportRemoteControl(row.optString("type")));
         }

         return data;
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public List<Groups> findGroupsByTvid(String tvId) {
      return this.groupsRepository.findByTvid(tvId);
   }

   public List<Groups> findGroupsByTvidAndOrderByGroupName(String tvId) {
      return this.groupsRepository.findByTvidOrderByGroupnameAsc(tvId);
   }

   public List<Groups> findGroupsByTvidAndPowerstatusOrderByGroupName(String tvId, String powerstatus) {
      return this.groupsRepository.findByTvidAndPowerstatusOrderByGroupnameAsc(tvId, powerstatus);
   }

   public List<Groups> findGroupsByGroupName(String groupName) {
      return this.groupsRepository.findByGroupname(groupName);
   }

   public List<String> findAllGroupNames() {
      return this.groupsRepository.findAllGroupNames();
   }

   public List<Groups> loadAll() {
      return this.groupsRepository.findAll();
   }

   public Groups findGroupsByTvIdAndGroupName(String tvid, String groupName) {
      return this.groupsRepository.findByTvidAndGroupname(tvid, groupName);
   }

   public Groups loadByKey(int id) {
      return this.groupsRepository.findById(id).orElse(null);
   }

   public void save(Groups groups) {
      this.groupsRepository.save(groups);
   }

   public void deleteByKey(int id) {
      this.groupsRepository.deleteById(id);
   }

   public void deleteByGroupName(String groupName) {
      this.groupsRepository.deleteByGroupname(groupName);
   }
}
