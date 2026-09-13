package be.tpvision.smartcontrol.codecs.sicp207.miscellaneous;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TeamviewerStatus;
import java.util.EnumMap;
import java.util.Map;

public class TeamviewerStatusCodec extends SingleValueCodec<TeamviewerStatus> {
   static final byte OFF_BYTE = 0;
   static final byte ON_BYTE = 1;
   private static TeamviewerStatusCodec teamviewerStatusCodec;

   private TeamviewerStatusCodec() {
      super(TeamviewerStatus.class);
   }

   public static synchronized TeamviewerStatusCodec getInstance() {
      if (teamviewerStatusCodec == null) {
         teamviewerStatusCodec = new TeamviewerStatusCodec();
      }

      return teamviewerStatusCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<TeamviewerStatus, Byte> domainTouch = new EnumMap<>(TeamviewerStatus.class);
      domainTouch.put(TeamviewerStatus.OFF, (byte)0);
      domainTouch.put(TeamviewerStatus.ON, (byte)1);
      super.setDeviceSettings(domainTouch);
   }
}
