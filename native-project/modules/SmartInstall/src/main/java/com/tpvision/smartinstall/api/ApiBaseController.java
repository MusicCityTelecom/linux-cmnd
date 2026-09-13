package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class ApiBaseController {
   protected String getRecpetionInfo() {
      HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
      String clientInfo = request.getHeader("x-client-info");
      return StringUtils.isEmpty(clientInfo) ? "Reception" : clientInfo;
   }

   protected void writePMSInfoToMetricsLog(Devices tv, String action) {
      CmndMetricsTask.setPmsTypeName(this.getRecpetionInfo());
      CmndMetricsTask.writePMSInfoToMetricsLog(tv, action);
      CmndMetricsTask.resetPmsTypeName();
   }
}
