package be.tpvision.smartcontrol.codecs.sicp188.system;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.system.ModelNumberFirmwareVersionBuildDateInfo;
import java.util.EnumMap;
import java.util.Map;

public class ModelNumberFirmwareVersionBuildDateInfoCodec extends SingleValueCodec<ModelNumberFirmwareVersionBuildDateInfo> {
   static final byte MODEL_NUMBER_BYTE = 0;
   static final byte FIRMWARE_VERSION_BYTE = 1;
   static final byte BUILD_DATE_BYTE = 2;
   private static ModelNumberFirmwareVersionBuildDateInfoCodec modelNumberFirmwareVersionBuildDateInfoCodec;

   private ModelNumberFirmwareVersionBuildDateInfoCodec() {
      super(ModelNumberFirmwareVersionBuildDateInfo.class);
   }

   public static synchronized ModelNumberFirmwareVersionBuildDateInfoCodec getInstance() {
      if (modelNumberFirmwareVersionBuildDateInfoCodec == null) {
         modelNumberFirmwareVersionBuildDateInfoCodec = new ModelNumberFirmwareVersionBuildDateInfoCodec();
      }

      return modelNumberFirmwareVersionBuildDateInfoCodec;
   }

   @Override
   protected void initializeDeviceSettings() {
      Map<ModelNumberFirmwareVersionBuildDateInfo, Byte> domainInfo = new EnumMap<>(ModelNumberFirmwareVersionBuildDateInfo.class);
      domainInfo.put(ModelNumberFirmwareVersionBuildDateInfo.MODEL_NUMBER, (byte)0);
      domainInfo.put(ModelNumberFirmwareVersionBuildDateInfo.FIRMWARE_VERSION, (byte)1);
      domainInfo.put(ModelNumberFirmwareVersionBuildDateInfo.BUILD_DATE, (byte)2);
      super.setDeviceSettings(domainInfo);
   }

   public byte[] toProtocol(ModelNumberFirmwareVersionBuildDateInfo deviceSetting) {
      if (ModelNumberFirmwareVersionBuildDateInfo.FIRMWARE_VERSION_ANDROID.equals(deviceSetting)) {
         throw new UnsupportedOperationException("Firmware version (Android) is not supported in this SICP version.");
      } else {
         return super.toProtocol(deviceSetting);
      }
   }
}
