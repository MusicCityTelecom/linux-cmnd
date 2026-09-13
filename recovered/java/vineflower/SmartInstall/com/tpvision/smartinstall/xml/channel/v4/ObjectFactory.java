package com.tpvision.smartinstall.xml.channel.v4;

import com.tpvision.smartinstall.xml.remotediagnose.DIAGNOSTICANALYTIC;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
   public TvContents createTvContents() {
      return new TvContents();
   }

   public SchemaVersion createSchemaVersion() {
      return new SchemaVersion();
   }

   public ChannelMap createChannelMap() {
      return new ChannelMap();
   }

   public Channel createChannel() {
      return new Channel();
   }

   public Broadcast createBroadcast() {
      return new Broadcast();
   }

   public Multicast createMulticast() {
      return new Multicast();
   }

   public Setup createSetup() {
      return new Setup();
   }

   public ApplicationMap createApplicationMap() {
      return new ApplicationMap();
   }

   public Application createApplication() {
      return new Application();
   }

   public DIAGNOSTICANALYTIC createDIAGNOSTICANALYTIC() {
      return new DIAGNOSTICANALYTIC();
   }
}
