package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.OnlineDevicesRepository;
import com.tpvision.smartinstall.dao.core.OnlineDevices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineDevicesManager {
   @Autowired
   private OnlineDevicesRepository onlineDevicesRepository;

   public void deleteAll() {
      this.onlineDevicesRepository.deleteAll();
   }

   public OnlineDevices findByTvId(String tvUnqiueId) {
      return this.onlineDevicesRepository.findById(tvUnqiueId).orElse(null);
   }

   public List<OnlineDevices> loadAll() {
      return this.onlineDevicesRepository.findAll();
   }

   public void saveOnlineDevices(OnlineDevices onlineDevices) {
      this.onlineDevicesRepository.save(onlineDevices);
   }
}
