package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.xml.psg.configuration.Config;
import java.io.File;
import java.util.Date;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationGenerator {
   private static final Logger LOG = LoggerFactory.getLogger(ConfigurationGenerator.class);

   public static void generate(Map<String, String> parameters) {
      String freq = parameters.get("freq") != null && !parameters.get("freq").trim().equals("") ? parameters.get("freq") : "706";
      String modulation = parameters.get("mod") != null && !parameters.get("mod").trim().equals("") ? parameters.get("mod") : "QPSK";
      String outlevel = parameters.get("outlevel") != null && !parameters.get("outlevel").trim().equals("") ? parameters.get("outlevel") : "-27.5";
      String band = parameters.get("band") != null && !parameters.get("band").trim().equals("") ? parameters.get("band") : "8";
      Config conf = loadConfig();
      freq = Float.toString(Float.parseFloat(freq) * 10.0F).replace(".0", "");
      String hasES2K12 = parameters.get("ES2K12_PRESENT") != null ? parameters.get("ES2K12_PRESENT") : null;
      String platformId = parameters.get("PLATFROM_ID_PRESENT") != null ? parameters.get("PLATFROM_ID_PRESENT") : null;
      LOG.info("processTSCreation-> cpCatalog -> 0001 ConfigurationGenerator platformId = {}", platformId);
      String starRooomID = parameters.get("roomIdString") != null ? parameters.get("roomIdString") : "0000 ";
      String roomIdString = parameters.get("roomIdString");
      String upgradeAllRoom = parameters.get("upgradeAllRoom");
      if (null != platformId && null != upgradeAllRoom && upgradeAllRoom.equalsIgnoreCase("true")) {
         if (platformId.equalsIgnoreCase("TPN141HE_CloneData") || platformId.equalsIgnoreCase("TPN142HE_CloneData")) {
            roomIdString = "0000";
         } else if (platformId.equals("TPM1532HE_CloneData") || platformId.equals("TPM1531HE_CloneData") || platformId.equals("TPN161HE_CloneData")) {
            roomIdString = "00000";
         }
      }

      if (null != conf) {
         conf.setModulationFrequency(freq);
         conf.setConstellation(modulation);
         conf.setOutputLevel(outlevel);
         conf.setBandwidth(band);
         conf.setTransmissionMode("2k");
         conf.setRoomNumber(starRooomID);
         if (null != platformId && null != hasES2K12) {
            conf.setPid("1FF");
            conf.setProgNum("1F8");
            if (platformId.equalsIgnoreCase("TPN141HE_CloneData") || platformId.equalsIgnoreCase("TPN142HE_CloneData")) {
               String RoomIDString = null != roomIdString && !roomIdString.equalsIgnoreCase("null") ? roomIdString : "0000";
               conf.setRoomNumber(RoomIDString);
            }

            if (platformId.equals("TPM1532HE_CloneData") || platformId.equals("TPM1531HE_CloneData") || platformId.equals("TPN161HE_CloneData")) {
               String roomIDString = null != roomIdString && !roomIdString.equalsIgnoreCase("null") ? roomIdString : "00000";
               conf.setRoomNumber(roomIDString);
            }
         }

         String identifier = TpvDateUtils.getIdentifierFormatTime(new Date());
         conf.setChannelTableIdentifier(identifier);
         conf.setSsbIdentifier(identifier);
         generate(conf);
      }
   }

   public static void generate4KTransmission(String platformId) {
      Config conf = loadConfig();
      String countryCode = "20D0";
      LastRFConfig lastRfConfig = LastRFConfig.loadLastConfig();
      if (null != conf) {
         if (platformId.endsWith("_CloneData")) {
            conf.setTransmissionMode("4k");
            countryCode = lastRfConfig.getONID().split("-")[1];
         }

         if (platformId.endsWith("_CloneData")) {
            conf.setTransmissionMode("2k");
            conf.setGuardInterval("1/4");
            conf.setCodeRate("1/2");
            conf.setRoomNumber(conf.getRoomNumber());
            String dateFormat = conf.getChannelTableIdentifier();
            conf.setChannelTableIdentifier(dateFormat + "" + dateFormat + "" + dateFormat + "" + dateFormat + "" + dateFormat);
         } else {
            conf.setGuardInterval("1/32");
            conf.setCodeRate("7/8");
         }

         conf.setOriginalNetworkId(countryCode);
         generate(conf);
      }
   }

   private static void generate(Config conf) {
      try {
         JAXBContext context = JAXBContext.newInstance(Config.class);
         Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
         String configFileLocation = getConfigFile();
         File output = new File(configFileLocation);
         marshaller.marshal(conf, output);
      } catch (JAXBException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private static Config loadConfig() {
      Config conf = null;

      try {
         JAXBContext context = JAXBContext.newInstance(Config.class);
         Unmarshaller unmarshaller = context.createUnmarshaller();
         File file = new File(getConfigFile());
         conf = (Config)unmarshaller.unmarshal(file);
      } catch (JAXBException e) {
         LOG.error(e.getMessage(), e);
         conf = new Config();
         conf.setBuildRootFolder(CommonConstants.CONFIG_BUILD_ROOT);
         conf.setWorkingRootFolder(CommonConstants.CONFIG_WORK_ROOT);
         conf.setTransmissionMode("2k");
         conf.setGuardInterval("1/32");
         conf.setConstellation("QPSK");
         conf.setCodeRate("7/8");
         conf.setModulationType("DVB-T");
         conf.setDeviceCapabilityFlags("DVB-T | UHF");
         conf.setDeviceStreamType("MODULATED");
         conf.setTxMode("188");
         conf.setStuffMode("OFF");
         conf.setBandwidth("8");
         conf.setOutputChannel("68");
         conf.setModulationFrequency("7060");
         conf.setOutputLevel("-27.5");
         conf.setOriginalNetworkId("20D0");
         conf.setProgNum("1F8");
         conf.setPid("1FF");
         conf.setOui("00903E");
         conf.setTxnId("98C90001");
         conf.setDwnldId("98C90003");
         conf.setNetworkId("1");
         conf.setTransportStreamId("1");
         conf.setPcrPid("1FFF");
         conf.setRoomNumber("0000");
         conf.setSsbIdentifier("2011/08/02-TPVSW");
         conf.setChannelTableIdentifier("2011/08/02-TPVSW");
      }

      return conf;
   }

   private static String getConfigFile() {
      return CommonConstants.CONFIG_FILE;
   }
}
