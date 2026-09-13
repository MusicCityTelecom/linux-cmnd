package org.htng._2011b;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

public final class WakeupSchedulingManagementClient {
   private static final QName SERVICE_NAME = new QName("http://htng.org/2011B", "HTNG_GuestAndRoomStatusService");

   private WakeupSchedulingManagementClient() {
   }

   public static void main(String[] args) throws Exception {
      URL wsdlURL = HTNGGuestAndRoomStatusService.WSDL_LOCATION;
      if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
         File wsdlFile = new File(args[0]);

         try {
            if (wsdlFile.exists()) {
               wsdlURL = wsdlFile.toURI().toURL();
            } else {
               wsdlURL = new URL(args[0]);
            }
         } catch (MalformedURLException e) {
            e.printStackTrace();
         }
      }

      HTNGGuestAndRoomStatusService ss = new HTNGGuestAndRoomStatusService(wsdlURL, SERVICE_NAME);
      WakeupSchedulingManagement port = ss.getWakeupSchedulingManagement();
      System.out.println("Invoking scheduleWakeup...");
      HTNGWakeupSchedulingNotifRQ _scheduleWakeup_htngWakeupSchedulingNotifRQ = null;
      HTNGWakeupSchedulingNotifRS _scheduleWakeup__return = port.scheduleWakeup(_scheduleWakeup_htngWakeupSchedulingNotifRQ);
      System.out.println("scheduleWakeup.result=" + _scheduleWakeup__return);
      System.exit(0);
   }
}
