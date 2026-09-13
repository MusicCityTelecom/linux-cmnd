package com.tpvision.smartcms.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import javax.annotation.Resource;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Util {
   public static String SERVER;
   public static String PORT;
   private static String serverName = null;
   @Resource
   private Environment env;

   @Value("${server.name}")
   public void setName(String name) {
      SERVER = this.env.getRequiredProperty("server.name");
   }

   @Value("${server.port}")
   public void setPort(String port) {
      PORT = this.env.getRequiredProperty("server.port");
   }

   public static boolean isNumeric(String str) {
      return str.matches("-?\\d+(.\\d+)?");
   }

   public static String getCurrentUrl() {
      if (serverName == null || serverName.equalsIgnoreCase(null)) {
         MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();

         try {
            Set<ObjectName> objectNames = beanServer.queryNames(
               new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"))
            );
            String host = InetAddress.getLocalHost().getHostName();
            String port = objectNames.iterator().next().getKeyProperty("port");
            serverName = "http://" + host + ":" + port + "/smartcms/";
         } catch (MalformedObjectNameException | UnknownHostException e) {
            e.printStackTrace();
         }
      }

      return "http://" + SERVER + ":" + PORT + "/smartcms/";
   }
}
