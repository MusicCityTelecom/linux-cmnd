package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.WakeupInfoRepository;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.WakeupInfo;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WakeupInfoManager {
   private static final Logger LOG = LoggerFactory.getLogger(WakeupInfoManager.class);
   @Autowired
   private WakeupInfoRepository wakeupInfoRepository;

   public WakeupInfo getNearestWakeupInfo(String roomId) {
      List<WakeupInfo> wakeupList = this.wakeupInfoRepository.findByRoomidOrderByWakeuptimeAsc(roomId);
      if (wakeupList.isEmpty()) {
         return null;
      }

      long timeDiff = 0L;

      for (int i = 0; i < wakeupList.size(); i++) {
         timeDiff = this.getTimeDifference(wakeupList.get(i).getWakeupTime());
         if (timeDiff > 0L && timeDiff < 86400000L && wakeupList.get(i).getStatus() == 0) {
            return wakeupList.get(i);
         }
      }

      return null;
   }

   public WakeupInfo getNearestOtWakeupInfo(String roomId) {
      List<WakeupInfo> wakeupList = this.wakeupInfoRepository.findByRoomidOrderByWakeuptimeAsc(roomId);
      if (wakeupList.isEmpty()) {
         return null;
      }

      for (int i = wakeupList.size() - 1; i >= 0; i--) {
         if (this.getTimeDifference(wakeupList.get(i).getWakeupTime()) < 0L && wakeupList.get(i).getStatus() == 0 || wakeupList.get(i).getStatus() == 1) {
            return wakeupList.get(i);
         }
      }

      return null;
   }

   public List<String> getRoomidList() {
      return this.wakeupInfoRepository.selectDistinctRoomId();
   }

   public List<WakeupInfo> findWakeupInfoByRoomIdAndWakeupId(String roomId, String wakeupId) {
      return this.wakeupInfoRepository.findByRoomidAndWakeupid(roomId, wakeupId);
   }

   public void insertWakeup(String roomId, String wakeupId, String wakeupTime) {
      if (this.wakeupInfoRepository.findByRoomidAndWakeupidAndWakeuptime(roomId, wakeupId, wakeupTime) == null) {
         this.save(roomId, wakeupId, wakeupTime);
      }
   }

   public void updateStatus(int id, int status) {
      this.wakeupInfoRepository.updateStatus(id, status);
   }

   public void deleteAllWakeups(String roomId) {
      List<WakeupInfo> wakeupList = this.wakeupInfoRepository.findByRoomidOrderByWakeuptimeAsc(roomId);
      if (!wakeupList.isEmpty()) {
         this.wakeupInfoRepository.deleteByRoomId(roomId);

         for (int i = 0; i < wakeupList.size(); i++) {
            if (wakeupList.get(i).getStatus() == 1 && this.getTimeDifference(wakeupList.get(i).getWakeupTime()) > 0L) {
               this.disableAlarm(roomId);
               break;
            }
         }
      }
   }

   public void deleteWakeup(String roomId, String wakeupTime) {
      WakeupInfo wakeupInfo = this.wakeupInfoRepository.findByRoomidAndWakeuptime(roomId, wakeupTime);
      if (wakeupInfo != null) {
         this.wakeupInfoRepository.deleteByRoomIdAndWakeupTime(roomId, wakeupTime);
         if (wakeupInfo.getStatus() == 1 && this.getTimeDifference(wakeupInfo.getWakeupTime()) > 0L) {
            this.disableAlarm(roomId);
         }
      }
   }

   public void deleteWakeupByRoomIdAndWakeupId(String roomId, String wakeupId) {
      List<WakeupInfo> wakeupList = this.wakeupInfoRepository.findByRoomidAndWakeupid(roomId, wakeupId);
      if (!wakeupList.isEmpty()) {
         this.wakeupInfoRepository.deleteByRoomIdAndWakeupid(roomId, wakeupId);

         for (int i = 0; i < wakeupList.size(); i++) {
            if (wakeupList.get(i).getStatus() == 1 && this.getTimeDifference(wakeupList.get(i).getWakeupTime()) > 0L) {
               this.disableAlarm(roomId);
               break;
            }
         }
      }
   }

   public long getTimeDifference(String wakeupTime) {
      Date d1 = TpvDateUtils.parseDateString(wakeupTime, "yyyy-MM-dd HH:mm:ss");
      Date d2 = new Date();
      return d1.getTime() - d2.getTime();
   }

   private void save(String roomId, String wakeupId, String wakeupTime) {
      WakeupInfo wakeupinfo = new WakeupInfo();
      wakeupinfo.setRoomId(roomId);
      wakeupinfo.setWakeupId(wakeupId);
      wakeupinfo.setWakeupTime(wakeupTime);
      wakeupinfo.setStatus(0);
      this.wakeupInfoRepository.save(wakeupinfo);
   }

   private void disableAlarm(String roomId) {
      List<Devices> devices = PmsUtils.getTVsForRoom(roomId);
      if (devices != null) {
         for (Devices device : devices) {
            PmsUtils.disablePmsAlarm(device);
         }
      }
   }
}
