package be.tpvision.smartcontrol.codecs.sicp199.input_sources;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failover;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.messages.codecs.sicp197.input_sources.failovers.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp197.input_sources.failovers.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

public class FailoversCodec extends Codec<Failovers> {
   static final byte HDMI_1_BYTE = 0;
   static final byte COMPONENT_BYTE = 1;
   static final byte COMPOSITE_BYTE = 2;
   static final byte DISPLAY_PORT_BYTE = 3;
   static final byte DVI_D_BYTE = 4;
   static final byte VGA_BYTE = 5;
   static final byte OPS_BYTE = 6;
   static final byte USB_BYTE = 7;
   static final byte BROWSER_BYTE = 8;
   static final byte SMART_CMS_BYTE = 9;
   static final byte INTERNAL_STORAGE_BYTE = 10;
   static final byte DIGITAL_MEDIA_SERVER_BYTE = 11;
   static final byte HDMI_2_BYTE = 12;
   static final byte HDMI_3_BYTE = 13;
   static final byte USB_PLAYLIST_BYTE = 14;
   static final byte USB_AUTOPLAY_BYTE = 15;
   static final byte MEDIA_PLAYER_BYTE = 16;
   static final byte PDF_PLAYER_BYTE = 17;
   static final byte CUSTOM_BYTE = 18;
   static final byte HDMI_4_BYTE = 19;
   static final byte VGA_2_BYTE = 20;
   static final byte VGA_3_BYTE = 21;
   static final byte IWB_BYTE = 22;
   static final byte CMND_PLAY_WEB_BYTE = 23;
   static final byte HOME_LAUNCHER_BYTE = 24;
   static final byte USB_TYPEC_BYTE = 25;
   static final byte KIOSK_BYTE = 26;
   static final byte SMART_INFO_BYTE = 27;
   static final byte TUNER_BYTE = 28;
   static final byte GOOGLE_CAST_BYTE = 29;
   private static final int NUMBER_OF_FAILOVERS = Failover.values().length;
   private static final Map<Failover, Byte> domainFailovers = new EnumMap<>(Failover.class);
   private static final Map<Byte, Failover> protocolFailovers = MapUtilities.inverse(domainFailovers);
   private static FailoversCodec failoversCodec;

   private FailoversCodec() {
      super(Failovers.class);
   }

   public static synchronized FailoversCodec getInstance() {
      if (failoversCodec == null) {
         failoversCodec = new FailoversCodec();
      }

      return failoversCodec;
   }

   public byte[] toProtocol(final Failovers failovers) {
      Assert.notNull(failovers, ToProtocolMessages.FAILOVERS_CAN_NOT_BE_NULL);
      List<Failover> domainFailoverList = failovers.getFailoverList();
      Assert.state(domainFailoverList != null, ToProtocolMessages.DOMAIN_FAILOVER_LIST_CAN_NOT_BE_NULL);
      boolean isAnyDomainFailoverNull = domainFailoverList.stream().anyMatch(Objects::isNull);
      Assert.state(!isAnyDomainFailoverNull, ToProtocolMessages.DOMAIN_FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
      Byte[] protocolFailoverArray = domainFailoverList.stream().map(domainFailovers::get).toArray(Byte[]::new);
      boolean isAnyProtocolFailoverNull = Arrays.stream(protocolFailoverArray).anyMatch(Objects::isNull);
      Assert.state(!isAnyProtocolFailoverNull, ToProtocolMessages.PROTOCOL_FAILOVER_ARRAY_CAN_NOT_CONTAIN_NULL_VALUES);
      return ArrayUtils.toPrimitive(protocolFailoverArray);
   }

   public Failovers toDomain(final byte[] bytes) {
      if (bytes != null && bytes.length <= NUMBER_OF_FAILOVERS) {
         Byte[] protocolFailoverArray = ArrayUtils.toObject(bytes);
         boolean isAnyProtocolFailoverNull = Arrays.stream(protocolFailoverArray).anyMatch(Objects::isNull);
         Assert.state(!isAnyProtocolFailoverNull, ToDomainMessages.PROTOCOL_FAILOVER_ARRAY_CAN_NOT_CONTAIN_NULL_VALUES);
         ArrayList<Failover> domainFailoverList = Arrays.stream(protocolFailoverArray)
            .map(protocolFailovers::get)
            .collect(Collectors.toCollection(ArrayList::new));
         boolean isAnyDomainFailoverNull = domainFailoverList.stream().anyMatch(Objects::isNull);
         Assert.state(!isAnyDomainFailoverNull, ToDomainMessages.DOMAIN_FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
         return new Failovers(domainFailoverList);
      } else {
         return null;
      }
   }

   static {
      domainFailovers.put(Failover.HDMI_1, (byte)0);
      domainFailovers.put(Failover.COMPONENT, (byte)1);
      domainFailovers.put(Failover.COMPOSITE, (byte)2);
      domainFailovers.put(Failover.DISPLAY_PORT_1, (byte)3);
      domainFailovers.put(Failover.DVI_D, (byte)4);
      domainFailovers.put(Failover.VGA, (byte)5);
      domainFailovers.put(Failover.OPS, (byte)6);
      domainFailovers.put(Failover.USB, (byte)7);
      domainFailovers.put(Failover.BROWSER, (byte)8);
      domainFailovers.put(Failover.SMART_CMS, (byte)9);
      domainFailovers.put(Failover.INTERNAL_STORAGE, (byte)10);
      domainFailovers.put(Failover.DIGITAL_MEDIA_SERVER, (byte)11);
      domainFailovers.put(Failover.HDMI_2, (byte)12);
      domainFailovers.put(Failover.HDMI_3, (byte)13);
      domainFailovers.put(Failover.USB_PLAYLIST, (byte)14);
      domainFailovers.put(Failover.USB_AUTOPLAY, (byte)15);
      domainFailovers.put(Failover.MEDIA_PLAYER, (byte)16);
      domainFailovers.put(Failover.PDF_PLAYER, (byte)17);
      domainFailovers.put(Failover.CUSTOM, (byte)18);
      domainFailovers.put(Failover.HDMI_4, (byte)19);
      domainFailovers.put(Failover.VGA_2, (byte)20);
      domainFailovers.put(Failover.VGA_3, (byte)21);
      domainFailovers.put(Failover.IWB, (byte)22);
      domainFailovers.put(Failover.CMND_PLAY_WEB, (byte)23);
      domainFailovers.put(Failover.HOME_LAUNCHER, (byte)24);
      domainFailovers.put(Failover.USB_TYPEC, (byte)25);
      domainFailovers.put(Failover.KIOSK, (byte)26);
      domainFailovers.put(Failover.SMART_INFO, (byte)27);
      domainFailovers.put(Failover.TUNER, (byte)28);
      domainFailovers.put(Failover.GOOGLE_CAST, (byte)29);
   }
}
