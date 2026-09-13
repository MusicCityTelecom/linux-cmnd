package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.domain.device_settings.general.WOL;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.HdmiOneWire;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LedStrips;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LockUsb;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OSDRotating;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateGet;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateSet;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PortStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.RS232Routing;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SicpSerialPortForwarding;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SwitchOnDelay;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TeamviewerStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Touch;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoAlignment;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;

public interface MiscellaneousService {
   Miscellaneous getMiscellaneous(Device device);

   SmartPower getSmartPower(Device device);

   void setSmartPower(Device device, SmartPower smartPower);

   void setVideoAlignment(Device device, VideoAlignment videoAlignment);

   TemperatureSensor getTemperatureSensor(Device device);

   StringWrapper getSerialCode(Device device);

   Tiling getTiling(Device device);

   void setTiling(Device device, Tiling tiling);

   IntWrapper getFrameCompensationHorizontal(Device device);

   void setFrameCompensationHorizontal(Device device, IntWrapper frameCompensationHorizontal);

   IntWrapper getFrameCompensationVertical(Device device);

   void setFrameCompensationVertical(Device device, IntWrapper frameCompensationVertical);

   LightSensor getLightSensor(Device device);

   void setLightSensor(Device device, LightSensor lightSensor);

   OSDRotating getOSDRotating(Device device);

   void setOSDRotating(Device device, OSDRotating osdRotating);

   IntWrapper getOSDInformation(Device device);

   void setOSDInformation(Device device, IntWrapper osdInformation);

   MEMCEffect getMEMCEffect(Device device);

   void setMEMCEffect(Device device, MEMCEffect memcEffect);

   Touch getTouch(Device device);

   void setTouch(Device device, Touch touch);

   NoiseReduction getNoiseReduction(Device device);

   void setNoiseReduction(Device device, NoiseReduction noiseReduction);

   ScanMode getScanMode(Device device);

   void setScanMode(Device device, ScanMode scanMode);

   ScanConversion getScanConversion(Device device);

   void setScanConversion(Device device, ScanConversion scanConversion);

   SwitchOnDelay getSwitchOnDelay(Device device);

   void setSwitchOnDelay(Device device, SwitchOnDelay switchOnDelay);

   void factoryReset(Device device);

   PowerOnLogo getPowerOnLogo(Device device);

   void setPowerOnLogo(Device device, PowerOnLogo powerOnLogo);

   FanSpeed getFanSpeed(Device device);

   void setFanSpeed(Device device, FanSpeed fanSpeed);

   APM getAPM(Device device);

   void setAPM(Device device, APM apm);

   PowerSavingMode getPowerSavingMode(Device device);

   void setPowerSavingMode(Device device, PowerSavingMode powerSavingMode);

   DisplayOrientation getDisplayOrientation(Device device);

   void setDisplayOrientation(Device device, DisplayOrientation displayOrientation);

   LedStrips getLedStrips(Device device);

   void setLedStrips(Device device, LedStrips ledStrips);

   PortStatus getPortStatus(Device device);

   void setPortStatus(Device device, PortStatus portStatus);

   IntWrapper getOffTimer(Device device);

   void setOffTimer(Device device, IntWrapper offTimer);

   LockUsb getLockUsb(Device device);

   void setLockUsb(Device device, LockUsb lockUsb);

   IntWrapper getHumanSensor(Device device);

   void setHumanSensor(Device device, IntWrapper humanSensor);

   PixelShift getPixelShift(Device device);

   void setPixelShift(Device device, PixelShift pixelShift);

   EcoMode getEcoMode(Device device);

   void setEcoMode(Device device, EcoMode ecoMode);

   void takeScreenshot(Device device);

   VideoPresent getVideoPresent(Device device);

   void openAndroidMenu(Device device);

   NavigationBar getNavigationBar(Device device);

   void setNavigationBar(Device device, NavigationBar navigationBar);

   BootOnSource getBootOnSource(Device device);

   void setBootOnSource(Device device, BootOnSource bootOnSource);

   void setOtaUpdateStatus(Device device, OtaUpdateSet otaUpdateSet);

   OtaUpdateGet getOtaUpdateStatus(Device device, OtaImageType otaImageType);

   StringWrapper getOtaFwVersion(Device device, OtaImageType otaImageType);

   TeamviewerStatus getTeamviewerStatus(Device device);

   void setTeamviewerStatus(Device device, TeamviewerStatus teamviewerStatus);

   RS232Routing getRS232Routing(Device device);

   void setRS232Routing(Device device, RS232Routing rS232Routing);

   SicpSerialPortForwarding getSicpSerialPortForwarding(Device device);

   void setSicpSerialPortForwarding(Device device, SicpSerialPortForwarding sicpSerialPortForwarding);

   WOL getWOL(Device device);

   void setWOL(Device device, WOL wOL);

   HdmiOneWire getHdmiOneWire(Device device);

   void setHdmiOneWire(Device device, HdmiOneWire hdmiOneWire);
}
