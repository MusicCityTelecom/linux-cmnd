package com.tpvision.smartinstall.xml.channel.v6;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"schemaVersion", "channelMap", "applicationMap", "themeTV"})
@XmlRootElement(name = "TvContents")
public class TvContents {
   @XmlElement(name = "SchemaVersion", required = true)
   protected TvContents.SchemaVersion schemaVersion;
   @XmlElement(name = "ChannelMap", required = true)
   protected TvContents.ChannelMap channelMap;
   @XmlElement(name = "ApplicationMap", required = true)
   protected TvContents.ApplicationMap applicationMap;
   @XmlElement(name = "ThemeTV", required = true)
   protected TvContents.ThemeTV themeTV;

   public TvContents.SchemaVersion getSchemaVersion() {
      return this.schemaVersion;
   }

   public void setSchemaVersion(TvContents.SchemaVersion value) {
      this.schemaVersion = value;
   }

   public TvContents.ChannelMap getChannelMap() {
      return this.channelMap;
   }

   public void setChannelMap(TvContents.ChannelMap value) {
      this.channelMap = value;
   }

   public TvContents.ApplicationMap getApplicationMap() {
      return this.applicationMap;
   }

   public void setApplicationMap(TvContents.ApplicationMap value) {
      this.applicationMap = value;
   }

   public TvContents.ThemeTV getThemeTV() {
      return this.themeTV;
   }

   public void setThemeTV(TvContents.ThemeTV value) {
      this.themeTV = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "application")
   public static class ApplicationMap {
      @XmlElement(name = "Application")
      protected List<TvContents.ApplicationMap.Application> application;

      public List<TvContents.ApplicationMap.Application> getApplication() {
         if (this.application == null) {
            this.application = new ArrayList<>();
         }

         return this.application;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"app", "setup"})
      public static class Application {
         @XmlElement(name = "App", required = true)
         protected TvContents.ApplicationMap.Application.App app;
         @XmlElement(name = "Setup", required = true)
         protected TvContents.ApplicationMap.Application.Setup setup;

         public TvContents.ApplicationMap.Application.App getApp() {
            return this.app;
         }

         public void setApp(TvContents.ApplicationMap.Application.App value) {
            this.app = value;
         }

         public TvContents.ApplicationMap.Application.Setup getSetup() {
            return this.setup;
         }

         public void setSetup(TvContents.ApplicationMap.Application.Setup value) {
            this.setup = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class App {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "Type")
            protected String type;
            @XmlAttribute(name = "AppName")
            protected String appName;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getType() {
               return this.type;
            }

            public void setType(String value) {
               this.type = value;
            }

            public String getAppName() {
               return this.appName;
            }

            public void setAppName(String value) {
               this.appName = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class Setup {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "name")
            protected String name;
            @XmlAttribute(name = "FreePKG")
            protected Byte freePKG;
            @XmlAttribute(name = "PayPKG1")
            protected Byte payPKG1;
            @XmlAttribute(name = "PayPKG2")
            protected Byte payPKG2;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getName() {
               return this.name;
            }

            public void setName(String value) {
               this.name = value;
            }

            public Byte getFreePKG() {
               return this.freePKG;
            }

            public void setFreePKG(Byte value) {
               this.freePKG = value;
            }

            public Byte getPayPKG1() {
               return this.payPKG1;
            }

            public void setPayPKG1(Byte value) {
               this.payPKG1 = value;
            }

            public Byte getPayPKG2() {
               return this.payPKG2;
            }

            public void setPayPKG2(Byte value) {
               this.payPKG2 = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "channel")
   public static class ChannelMap {
      @XmlElement(name = "Channel")
      protected List<TvContents.ChannelMap.Channel> channel;

      public List<TvContents.ChannelMap.Channel> getChannel() {
         if (this.channel == null) {
            this.channel = new ArrayList<>();
         }

         return this.channel;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {"broadcast", "multicast", "source", "media", "setup"})
      public static class Channel {
         @XmlElement(name = "Broadcast")
         protected TvContents.ChannelMap.Channel.Broadcast broadcast;
         @XmlElement(name = "Multicast")
         protected TvContents.ChannelMap.Channel.Multicast multicast;
         @XmlElement(name = "Source")
         protected TvContents.ChannelMap.Channel.Source source;
         @XmlElement(name = "Media")
         protected TvContents.ChannelMap.Channel.Media media;
         @XmlElement(name = "Setup", required = true)
         protected TvContents.ChannelMap.Channel.Setup setup;

         public TvContents.ChannelMap.Channel.Broadcast getBroadcast() {
            return this.broadcast;
         }

         public void setBroadcast(TvContents.ChannelMap.Channel.Broadcast value) {
            this.broadcast = value;
         }

         public TvContents.ChannelMap.Channel.Multicast getMulticast() {
            return this.multicast;
         }

         public void setMulticast(TvContents.ChannelMap.Channel.Multicast value) {
            this.multicast = value;
         }

         public TvContents.ChannelMap.Channel.Source getSource() {
            return this.source;
         }

         public void setSource(TvContents.ChannelMap.Channel.Source value) {
            this.source = value;
         }

         public TvContents.ChannelMap.Channel.Media getMedia() {
            return this.media;
         }

         public void setMedia(TvContents.ChannelMap.Channel.Media value) {
            this.media = value;
         }

         public TvContents.ChannelMap.Channel.Setup getSetup() {
            return this.setup;
         }

         public void setSetup(TvContents.ChannelMap.Channel.Setup value) {
            this.setup = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class Broadcast {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "medium")
            protected String medium;
            @XmlAttribute(name = "PhysicalChannel")
            protected Byte physicalChannel;
            @XmlAttribute(name = "frequency")
            protected Integer frequency;
            @XmlAttribute(name = "ProgramNumber")
            protected Byte programNumber;
            @XmlAttribute(name = "TSID")
            protected Byte tsid;
            @XmlAttribute(name = "modulation")
            protected String modulation;
            @XmlAttribute(name = "bandwidth")
            protected String bandwidth;
            @XmlAttribute(name = "servicetype")
            protected String servicetype;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getMedium() {
               return this.medium;
            }

            public void setMedium(String value) {
               this.medium = value;
            }

            public Byte getPhysicalChannel() {
               return this.physicalChannel;
            }

            public void setPhysicalChannel(Byte value) {
               this.physicalChannel = value;
            }

            public Integer getFrequency() {
               return this.frequency;
            }

            public void setFrequency(Integer value) {
               this.frequency = value;
            }

            public Byte getProgramNumber() {
               return this.programNumber;
            }

            public void setProgramNumber(Byte value) {
               this.programNumber = value;
            }

            public Byte getTSID() {
               return this.tsid;
            }

            public void setTSID(Byte value) {
               this.tsid = value;
            }

            public String getModulation() {
               return this.modulation;
            }

            public void setModulation(String value) {
               this.modulation = value;
            }

            public String getBandwidth() {
               return this.bandwidth;
            }

            public void setBandwidth(String value) {
               this.bandwidth = value;
            }

            public String getServicetype() {
               return this.servicetype;
            }

            public void setServicetype(String value) {
               this.servicetype = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class Media {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "url")
            protected String url;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getUrl() {
               return this.url;
            }

            public void setUrl(String value) {
               this.url = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class Multicast {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "url")
            protected String url;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getUrl() {
               return this.url;
            }

            public void setUrl(String value) {
               this.url = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class Setup {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "presetnumber")
            protected Short presetnumber;
            @XmlAttribute(name = "name")
            protected String name;
            @XmlAttribute(name = "blank")
            protected Byte blank;
            @XmlAttribute(name = "skip")
            protected Byte skip;
            @XmlAttribute(name = "FreePKG")
            protected Byte freePKG;
            @XmlAttribute(name = "PayPKG1")
            protected Byte payPKG1;
            @XmlAttribute(name = "PayPKG2")
            protected Byte payPKG2;
            @XmlAttribute(name = "logo")
            protected String logo;
            @XmlAttribute(name = "TTV1")
            protected Byte ttv1;
            @XmlAttribute(name = "TTV2")
            protected Byte ttv2;
            @XmlAttribute(name = "TTV3")
            protected Byte ttv3;
            @XmlAttribute(name = "TTV4")
            protected Byte ttv4;
            @XmlAttribute(name = "TTV5")
            protected Byte ttv5;
            @XmlAttribute(name = "TTV6")
            protected Byte ttv6;
            @XmlAttribute(name = "TTV7")
            protected Byte ttv7;
            @XmlAttribute(name = "TTV8")
            protected Byte ttv8;
            @XmlAttribute(name = "TTV9")
            protected Byte ttv9;
            @XmlAttribute(name = "TTV10")
            protected Byte ttv10;
            @XmlAttribute(name = "hide")
            protected Byte hide;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public Short getPresetnumber() {
               return this.presetnumber;
            }

            public void setPresetnumber(Short value) {
               this.presetnumber = value;
            }

            public String getName() {
               return this.name;
            }

            public void setName(String value) {
               this.name = value;
            }

            public Byte getBlank() {
               return this.blank;
            }

            public void setBlank(Byte value) {
               this.blank = value;
            }

            public Byte getSkip() {
               return this.skip;
            }

            public void setSkip(Byte value) {
               this.skip = value;
            }

            public Byte getFreePKG() {
               return this.freePKG;
            }

            public void setFreePKG(Byte value) {
               this.freePKG = value;
            }

            public Byte getPayPKG1() {
               return this.payPKG1;
            }

            public void setPayPKG1(Byte value) {
               this.payPKG1 = value;
            }

            public Byte getPayPKG2() {
               return this.payPKG2;
            }

            public void setPayPKG2(Byte value) {
               this.payPKG2 = value;
            }

            public String getLogo() {
               return this.logo;
            }

            public void setLogo(String value) {
               this.logo = value;
            }

            public Byte getTTV1() {
               return this.ttv1;
            }

            public void setTTV1(Byte value) {
               this.ttv1 = value;
            }

            public Byte getTTV2() {
               return this.ttv2;
            }

            public void setTTV2(Byte value) {
               this.ttv2 = value;
            }

            public Byte getTTV3() {
               return this.ttv3;
            }

            public void setTTV3(Byte value) {
               this.ttv3 = value;
            }

            public Byte getTTV4() {
               return this.ttv4;
            }

            public void setTTV4(Byte value) {
               this.ttv4 = value;
            }

            public Byte getTTV5() {
               return this.ttv5;
            }

            public void setTTV5(Byte value) {
               this.ttv5 = value;
            }

            public Byte getTTV6() {
               return this.ttv6;
            }

            public void setTTV6(Byte value) {
               this.ttv6 = value;
            }

            public Byte getTTV7() {
               return this.ttv7;
            }

            public void setTTV7(Byte value) {
               this.ttv7 = value;
            }

            public Byte getTTV8() {
               return this.ttv8;
            }

            public void setTTV8(Byte value) {
               this.ttv8 = value;
            }

            public Byte getTTV9() {
               return this.ttv9;
            }

            public void setTTV9(Byte value) {
               this.ttv9 = value;
            }

            public Byte getTTV10() {
               return this.ttv10;
            }

            public void setTTV10(Byte value) {
               this.ttv10 = value;
            }

            public Byte getHide() {
               return this.hide;
            }

            public void setHide(Byte value) {
               this.hide = value;
            }
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class Source {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "Type")
            protected String type;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getType() {
               return this.type;
            }

            public void setType(String value) {
               this.type = value;
            }
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class SchemaVersion {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "MajorVerNo")
      protected Byte majorVerNo;
      @XmlAttribute(name = "MinorVerNo")
      protected Byte minorVerNo;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public Byte getMajorVerNo() {
         return this.majorVerNo;
      }

      public void setMajorVerNo(Byte value) {
         this.majorVerNo = value;
      }

      public Byte getMinorVerNo() {
         return this.minorVerNo;
      }

      public void setMinorVerNo(Byte value) {
         this.minorVerNo = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"ttv1", "ttv2", "ttv3", "ttv4", "ttv5", "ttv6", "ttv7", "ttv8", "ttv9", "ttv10"})
   public static class ThemeTV {
      @XmlElement(name = "TTV1", required = true)
      protected TvContents.ThemeTV.TTV1 ttv1;
      @XmlElement(name = "TTV2", required = true)
      protected TvContents.ThemeTV.TTV2 ttv2;
      @XmlElement(name = "TTV3", required = true)
      protected TvContents.ThemeTV.TTV3 ttv3;
      @XmlElement(name = "TTV4", required = true)
      protected TvContents.ThemeTV.TTV4 ttv4;
      @XmlElement(name = "TTV5", required = true)
      protected TvContents.ThemeTV.TTV5 ttv5;
      @XmlElement(name = "TTV6", required = true)
      protected TvContents.ThemeTV.TTV6 ttv6;
      @XmlElement(name = "TTV7", required = true)
      protected TvContents.ThemeTV.TTV7 ttv7;
      @XmlElement(name = "TTV8", required = true)
      protected TvContents.ThemeTV.TTV8 ttv8;
      @XmlElement(name = "TTV9", required = true)
      protected TvContents.ThemeTV.TTV9 ttv9;
      @XmlElement(name = "TTV10", required = true)
      protected TvContents.ThemeTV.TTV10 ttv10;

      public TvContents.ThemeTV.TTV1 getTTV1() {
         return this.ttv1;
      }

      public void setTTV1(TvContents.ThemeTV.TTV1 value) {
         this.ttv1 = value;
      }

      public TvContents.ThemeTV.TTV2 getTTV2() {
         return this.ttv2;
      }

      public void setTTV2(TvContents.ThemeTV.TTV2 value) {
         this.ttv2 = value;
      }

      public TvContents.ThemeTV.TTV3 getTTV3() {
         return this.ttv3;
      }

      public void setTTV3(TvContents.ThemeTV.TTV3 value) {
         this.ttv3 = value;
      }

      public TvContents.ThemeTV.TTV4 getTTV4() {
         return this.ttv4;
      }

      public void setTTV4(TvContents.ThemeTV.TTV4 value) {
         this.ttv4 = value;
      }

      public TvContents.ThemeTV.TTV5 getTTV5() {
         return this.ttv5;
      }

      public void setTTV5(TvContents.ThemeTV.TTV5 value) {
         this.ttv5 = value;
      }

      public TvContents.ThemeTV.TTV6 getTTV6() {
         return this.ttv6;
      }

      public void setTTV6(TvContents.ThemeTV.TTV6 value) {
         this.ttv6 = value;
      }

      public TvContents.ThemeTV.TTV7 getTTV7() {
         return this.ttv7;
      }

      public void setTTV7(TvContents.ThemeTV.TTV7 value) {
         this.ttv7 = value;
      }

      public TvContents.ThemeTV.TTV8 getTTV8() {
         return this.ttv8;
      }

      public void setTTV8(TvContents.ThemeTV.TTV8 value) {
         this.ttv8 = value;
      }

      public TvContents.ThemeTV.TTV9 getTTV9() {
         return this.ttv9;
      }

      public void setTTV9(TvContents.ThemeTV.TTV9 value) {
         this.ttv9 = value;
      }

      public TvContents.ThemeTV.TTV10 getTTV10() {
         return this.ttv10;
      }

      public void setTTV10(TvContents.ThemeTV.TTV10 value) {
         this.ttv10 = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV1 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV10 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV2 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV3 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV4 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV5 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV6 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV7 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV8 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class TTV9 {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "icon")
         protected String icon;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

         public String getIcon() {
            return this.icon;
         }

         public void setIcon(String value) {
            this.icon = value;
         }
      }
   }
}
