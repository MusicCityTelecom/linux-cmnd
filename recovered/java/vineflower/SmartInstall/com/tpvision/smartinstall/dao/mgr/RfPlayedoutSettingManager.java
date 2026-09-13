package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.RfPlayedoutSettingRepository;
import com.tpvision.smartinstall.dao.core.RfPlayedoutSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RfPlayedoutSettingManager {
   @Autowired
   private RfPlayedoutSettingRepository rfPlayedoutSettingRepository;

   public RfPlayedoutSetting loadByKey(int id) {
      return this.rfPlayedoutSettingRepository.findById(id).orElse(null);
   }
}
