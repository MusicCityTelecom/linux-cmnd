package com.tpvision.smartinstall.japit;

import com.google.gson.Gson;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.util.JAPITUtils;
import java.util.ArrayList;
import java.util.List;

public class IPCloneService {
   public String Svc = "WebListeningServices";
   public String SvcVer = "3.0";
   public int Cookie = JAPITUtils.getJapitRandomCookieValue();
   public String CmdType = "Change";
   public String Fun = "IPCloneService";
   public IPCloneService.CommandDetail CommandDetails;

   public IPCloneService(JAPITUtils.WebServiceType webServiceType) {
      this.CommandDetails = new IPCloneService.CommandDetail(webServiceType);
      this.Svc = webServiceType.name();
   }

   public static IPCloneService fromJson(String json) {
      return new Gson().fromJson(json, IPCloneService.class);
   }

   public String toJson() {
      return new Gson().toJson(this);
   }

   public boolean isDownloadUrlEmpty() {
      return this.CommandDetails.IPCloneParameters.CloneItemDownloadDetails.isEmpty();
   }

   public static class CloneItemDetails {
      public String CloneItemName;
      public String CloneItemVersionNo;
   }

   public static class CloneItemDownloadDetail {
      public IPCloneService.CloneItemDetails CloneItemDetails = new IPCloneService.CloneItemDetails();
      public String URL;
   }

   public class CommandDetail {
      public IPCloneService.IPCloneParameters IPCloneParameters = IPCloneService.this.new IPCloneParameters();
      public IPCloneService.WebListeningServiceParameters WebListeningServiceParameters;
      public IPCloneService.WebServiceParameters WebServiceParameters;

      public CommandDetail(JAPITUtils.WebServiceType webServiceType) {
         if (webServiceType == JAPITUtils.WebServiceType.WebServices) {
            this.WebServiceParameters = IPCloneService.this.new WebServiceParameters();
            IPProfile lastConfig = IPProfile.loadIPProfile();
            if (lastConfig != null) {
               String pollingFrequency = lastConfig.getFastMode();
               String pollingFrequencyGreen = lastConfig.getGreenMode();
               this.WebServiceParameters.PollingFrequency = pollingFrequency;
               this.WebServiceParameters.PollingFrequencyGreen = pollingFrequencyGreen;
            }
         } else {
            this.WebListeningServiceParameters = IPCloneService.this.new WebListeningServiceParameters();
         }
      }
   }

   public class IPCloneParameters {
      public List<IPCloneService.CloneItemDownloadDetail> CloneItemDownloadDetails = new ArrayList<>();
   }

   public class WebListeningServiceParameters {
      public String TVUniqueID;
   }

   public class WebServiceParameters {
      public String PollingFrequency;
      public String PollingFrequencyGreen;
      public String TVUniqueID;
   }
}
