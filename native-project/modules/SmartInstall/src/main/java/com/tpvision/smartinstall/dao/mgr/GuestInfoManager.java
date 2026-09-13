package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.api.ReceptionSocket;
import com.tpvision.smartinstall.dao.GuestInfoRepository;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GuestInfoManager {
   private static final Logger LOG = LoggerFactory.getLogger(GuestInfoManager.class);
   @Autowired
   private GuestInfoRepository guestInfoRepository;

   public List<GuestInfo> findGuestInfosByRoomids(String[] roomids) {
      List<GuestInfo> guestInfoList = new ArrayList<>();

      for (Integer roomid : Arrays.asList(roomids).stream().map(Integer::parseInt).distinct().collect(Collectors.toList())) {
         List<GuestInfo> guestInfo = this.guestInfoRepository.findByRoomid(roomid.toString());
         if (!guestInfo.isEmpty()) {
            guestInfoList.addAll(guestInfo);
         }
      }

      return guestInfoList;
   }

   public Page<GuestInfo> findGuestInfoByKeywordAndCheckInStatus(String keyword, Pageable pageable) {
      Sort sort = pageable.getSort();
      if (sort.isSorted()) {
         Order order = sort.iterator().next();
         if (StringUtils.equalsIgnoreCase(order.getProperty(), "roomid")) {
            return this.findByKeywordAndCheckInStatusOrderByRoomid(keyword, order.getDirection(), pageable);
         }
      }

      Specification<GuestInfo> specs = this.getQuerySpecification(keyword);
      return this.guestInfoRepository.findAll(specs, pageable);
   }

   private Specification<GuestInfo> getQuerySpecification(String keyword) {
      return (root, query, cb) -> {
         Predicate predicateResult = cb.equal(root.get("checkin"), "Y");
         return this.addKeywordLikePridicate(keyword, root, cb, predicateResult);
      };
   }

   private Predicate addKeywordLikePridicate(String keyword, Root<GuestInfo> root, CriteriaBuilder cb, Predicate predicateResult) {
      if (StringUtils.isNotBlank(keyword)) {
         String matchKeyword = "%" + keyword + "%";
         Predicate roomidLike = cb.like(root.<String>get("roomid").as(String.class), matchKeyword);
         Predicate guestNameLike = cb.like(root.<String>get("guestName").as(String.class), matchKeyword);
         Predicate checkoutTimeLike = cb.like(root.<String>get("checkoutTime").as(String.class), matchKeyword);
         Predicate allOrLike = cb.or(roomidLike, guestNameLike, checkoutTimeLike);
         predicateResult = cb.and(predicateResult, allOrLike);
      }

      return predicateResult;
   }

   private Page<GuestInfo> findByKeywordAndCheckInStatusOrderByRoomid(String keyword, Direction sortDirection, Pageable pageable) {
      long totalCount = this.guestInfoRepository.count(this.getQuerySpecification(keyword));
      List<GuestInfo> guestInfoList = Collections.emptyList();
      if (totalCount > 0L) {
         int startIndex = (int)pageable.getOffset();
         int pageSize = pageable.getPageSize();
         if (sortDirection == Direction.ASC) {
            guestInfoList = this.guestInfoRepository.findByKeywordAndCheckInStatusOrderByRoomidAsc(keyword, startIndex, pageSize);
         } else {
            guestInfoList = this.guestInfoRepository.findByKeywordAndCheckInStatusOrderByRoomidDesc(keyword, startIndex, pageSize);
         }
      }

      return new PageImpl<>(guestInfoList, pageable, totalCount);
   }

   public List<GuestInfo> findGuestInfoByCheckoutTimeAndCheckInStatus(String checkoutTime, String checkInStatus) {
      return this.guestInfoRepository.findGuestInfoByCheckoutTimeAndCheckInStatus(checkoutTime, checkInStatus);
   }

   public void deleteByKey(String guestId) {
      this.guestInfoRepository.deleteById(guestId);
      ReceptionSocket.notifyPmsGuestInfoUpdate();
   }

   public GuestInfo loadByKey(String guestId) {
      return this.guestInfoRepository.findById(guestId).orElse(null);
   }

   public void save(GuestInfo guestInfo) {
      this.guestInfoRepository.save(guestInfo);
      ReceptionSocket.notifyPmsGuestInfoUpdate();
   }

   public GuestInfo loadByOrderId(String orderId) {
      List<GuestInfo> gis = this.guestInfoRepository.findByOrderid(orderId);
      return gis.isEmpty() ? null : gis.get(0);
   }

   public List<GuestInfo> loadAll() {
      return this.guestInfoRepository.findAll();
   }

   public List<GuestInfo> findGuestInfosByRoomid(String roomId) {
      return this.guestInfoRepository.findByRoomid(roomId);
   }

   public List<GuestInfo> findGuestInfosByCheckin(String checkin) {
      return this.guestInfoRepository.findByCheckin(checkin);
   }

   public JSONArray findAllGroupName() throws SQLException {
      String sql = "select distinct(groupName) as groupName from guestinfo";
      JSONArray groupNames = new JSONArray();
      JSONArray groupNames1 = JpaManager.getResultsetAsArray(sql);

      for (int i = 0; i < groupNames1.length(); i++) {
         JSONObject obj = groupNames1.getJSONObject(i);
         groupNames.put(obj.get("groupName"));
      }

      return groupNames;
   }

   public JSONArray findAllRoomids() throws SQLException {
      String sql = "select distinct(roomid) as roomid from guestinfo";
      return JpaManager.getResultsetAsArray(sql);
   }

   public JSONArray findAllPmsDataFromGuestInfoUnionFutureCheckIn(String checkoutTime, String keyword, Pageable pageable) {
      String queryString = "select * from (select guestId as id,roomid, guestName, guestLanguage as language, checkin_time as checkinTime ,checkout_time as checkoutTime,'Y' as checkIn from guestinfo where checkin='Y' union all select id,room_id as roomid,guest_name as guestName,guest_language as language, checkin_time as checkinTime,checkout_time as checkoutTime,'N' as checkIn from future_check_in) as temp ";
      boolean isHaveWhere = false;
      if (StringUtils.isNotBlank(keyword)) {
         isHaveWhere = true;
         queryString = queryString
            + "where (roomid like '%"
            + keyword
            + "%' or guestName like '%"
            + keyword
            + "%' or checkinTime like '%"
            + keyword
            + "%' or checkoutTime like '%"
            + keyword
            + "%' ) ";
      }

      if (StringUtils.isNotBlank(checkoutTime)) {
         if (isHaveWhere) {
            queryString = queryString + "and ";
         } else {
            queryString = queryString + "where ";
         }

         queryString = queryString + "(checkoutTime like '" + checkoutTime + "%') ";
      }

      Sort sort = pageable.getSort();
      if (sort.isSorted()) {
         Order order = sort.iterator().next();
         if (StringUtils.equalsIgnoreCase(order.getProperty(), "roomid")) {
            queryString = queryString + "order by cast(roomid as unsigned) " + order.getDirection();
         } else {
            queryString = queryString + "order by " + order.getProperty() + " " + order.getDirection();
         }
      }

      try {
         return JpaManager.getResultsetAsArray(queryString);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return new JSONArray();
      }
   }

   public void deleteAll() {
      this.guestInfoRepository.deleteAll();
      ReceptionSocket.notifyPmsGuestInfoUpdate();
   }

   public List<GuestInfo> findByGuestidLike(String guestId) {
      return this.guestInfoRepository.findByGuestIdLike("%" + guestId);
   }

   public List<GuestInfo> findByBeginGuestid(String guestId) {
      return this.guestInfoRepository.findByGuestIdLike(guestId + "%");
   }
}
