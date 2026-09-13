package com.tpvision.smartinstall.core;

import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.xml.Channel;
import com.tpvision.smartinstall.xml.channel.v4.TvContents;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingChannelLoader {
   private static final Logger LOG = LoggerFactory.getLogger(SettingChannelLoader.class);

   public static TvContents loadChannelV4(String processCloneFilePath, String platformId) throws IOException, JAXBException {
      TvContents ret = new TvContents();
      String path = "";
      if ("TPN161HE_CloneData".equalsIgnoreCase(platformId)) {
         path = processCloneFilePath + "/MasterCloneData/ChannelList/ChannelList.xml";
      } else {
         Channel channel = findChanelXmlFile(new File(processCloneFilePath));
         path = processCloneFilePath + "/" + channel.getFileName();
      }

      File chanelXml = new File(path);
      if (!chanelXml.exists()) {
         return new TvContents();
      }

      try (
         BOMInputStream stream = new BOMInputStream(new FileInputStream(chanelXml));
         Reader freader = new InputStreamReader(stream, StandardCharsets.UTF_8);
      ) {
         if (chanelXml.exists()) {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v4");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ret = (TvContents)unmarshaller.unmarshal(freader);
         } else {
            ret = new TvContents();
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   public static com.tpvision.smartinstall.xml.channel.v5.TvContents loadChannelV5(String processCloneFilePath) throws IOException, JAXBException {
      com.tpvision.smartinstall.xml.channel.v5.TvContents ret = new com.tpvision.smartinstall.xml.channel.v5.TvContents();
      String path = processCloneFilePath + "/MasterCloneData/ChannelList/ChannelList.xml";
      File chanelXml = new File(path);
      if (chanelXml.exists()) {
         try (
            BOMInputStream stream = new BOMInputStream(new FileInputStream(chanelXml));
            Reader freader = new InputStreamReader(stream, "UTF-8");
         ) {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v5");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ret = (com.tpvision.smartinstall.xml.channel.v5.TvContents)unmarshaller.unmarshal(freader);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      } else {
         LOG.warn("ChannelList.xml not exists");
      }

      return ret;
   }

   public static com.tpvision.smartinstall.xml.channel.v6.TvContents loadChannelV6(String processCloneFilePath) throws IOException, JAXBException {
      com.tpvision.smartinstall.xml.channel.v6.TvContents ret = new com.tpvision.smartinstall.xml.channel.v6.TvContents();
      String path = processCloneFilePath + "/MasterCloneData/ChannelList/ChannelList.xml";
      File chanelXml = new File(path);

      try (
         BOMInputStream stream = new BOMInputStream(new FileInputStream(chanelXml));
         Reader freader = new InputStreamReader(stream, StandardCharsets.UTF_8);
      ) {
         if (chanelXml.exists()) {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v6");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ret = (com.tpvision.smartinstall.xml.channel.v6.TvContents)unmarshaller.unmarshal(freader);
         } else {
            LOG.error("channellist.xml not exist," + path);
         }
      }

      return ret;
   }

   private static final Channel findChanelXmlFile(File file) {
      List<File> xmlFiles = TpvFileUtils.findFiles(file, "xml");
      return loadChannel(xmlFiles);
   }

   private static Channel loadChannel(List<File> xmlFiles) {
      Channel ret = new Channel();

      for (File f : xmlFiles) {
         if (f.getName().indexOf("CH") > -1) {
            ret.setFileName(f.getName());
            ret.setFolderName("/");
            ret.setXsdVer("v1");
         }
      }

      return ret;
   }
}
