package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.CastAnalyticalDataRepository;
import com.tpvision.smartinstall.dao.core.CastAnalyticalData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CastAnalyticalDataManager {
   @Autowired
   private CastAnalyticalDataRepository castAnalyticalDataRepository;

   public void save(CastAnalyticalData castAnalyticalData) {
      this.castAnalyticalDataRepository.save(castAnalyticalData);
   }

   public Long findMaxIndexId() {
      return this.castAnalyticalDataRepository.findMaxIndexId();
   }

   public List<CastAnalyticalData> findOverLappedCastAnalyticalDataByTimeFrame(Date startTime, Date endTime, String applicatioName) {
      SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      spf.setTimeZone(TimeZone.getTimeZone("UTC"));
      String startTimeStr = spf.format(startTime);
      String endTimeStr = spf.format(endTime);
      return this.castAnalyticalDataRepository.findOverLappedCastAnalyticalDataByTimeFrame(startTimeStr, endTimeStr, applicatioName);
   }
}
