/*
 * Decompiled with CFR 0.152.
 */
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
    public Miscellaneous getMiscellaneous(Device var1);

    public SmartPower getSmartPower(Device var1);

    public void setSmartPower(Device var1, SmartPower var2);

    public void setVideoAlignment(Device var1, VideoAlignment var2);

    public TemperatureSensor getTemperatureSensor(Device var1);

    public StringWrapper getSerialCode(Device var1);

    public Tiling getTiling(Device var1);

    public void setTiling(Device var1, Tiling var2);

    public IntWrapper getFrameCompensationHorizontal(Device var1);

    public void setFrameCompensationHorizontal(Device var1, IntWrapper var2);

    public IntWrapper getFrameCompensationVertical(Device var1);

    public void setFrameCompensationVertical(Device var1, IntWrapper var2);

    public LightSensor getLightSensor(Device var1);

    public void setLightSensor(Device var1, LightSensor var2);

    public OSDRotating getOSDRotating(Device var1);

    public void setOSDRotating(Device var1, OSDRotating var2);

    public IntWrapper getOSDInformation(Device var1);

    public void setOSDInformation(Device var1, IntWrapper var2);

    public MEMCEffect getMEMCEffect(Device var1);

    public void setMEMCEffect(Device var1, MEMCEffect var2);

    public Touch getTouch(Device var1);

    public void setTouch(Device var1, Touch var2);

    public NoiseReduction getNoiseReduction(Device var1);

    public void setNoiseReduction(Device var1, NoiseReduction var2);

    public ScanMode getScanMode(Device var1);

    public void setScanMode(Device var1, ScanMode var2);

    public ScanConversion getScanConversion(Device var1);

    public void setScanConversion(Device var1, ScanConversion var2);

    public SwitchOnDelay getSwitchOnDelay(Device var1);

    public void setSwitchOnDelay(Device var1, SwitchOnDelay var2);

    public void factoryReset(Device var1);

    public PowerOnLogo getPowerOnLogo(Device var1);

    public void setPowerOnLogo(Device var1, PowerOnLogo var2);

    public FanSpeed getFanSpeed(Device var1);

    public void setFanSpeed(Device var1, FanSpeed var2);

    public APM getAPM(Device var1);

    public void setAPM(Device var1, APM var2);

    public PowerSavingMode getPowerSavingMode(Device var1);

    public void setPowerSavingMode(Device var1, PowerSavingMode var2);

    public DisplayOrientation getDisplayOrientation(Device var1);

    public void setDisplayOrientation(Device var1, DisplayOrientation var2);

    public LedStrips getLedStrips(Device var1);

    public void setLedStrips(Device var1, LedStrips var2);

    public PortStatus getPortStatus(Device var1);

    public void setPortStatus(Device var1, PortStatus var2);

    public IntWrapper getOffTimer(Device var1);

    public void setOffTimer(Device var1, IntWrapper var2);

    public LockUsb getLockUsb(Device var1);

    public void setLockUsb(Device var1, LockUsb var2);

    public IntWrapper getHumanSensor(Device var1);

    public void setHumanSensor(Device var1, IntWrapper var2);

    public PixelShift getPixelShift(Device var1);

    public void setPixelShift(Device var1, PixelShift var2);

    public EcoMode getEcoMode(Device var1);

    public void setEcoMode(Device var1, EcoMode var2);

    public void takeScreenshot(Device var1);

    public VideoPresent getVideoPresent(Device var1);

    public void openAndroidMenu(Device var1);

    public NavigationBar getNavigationBar(Device var1);

    public void setNavigationBar(Device var1, NavigationBar var2);

    public BootOnSource getBootOnSource(Device var1);

    public void setBootOnSource(Device var1, BootOnSource var2);

    public void setOtaUpdateStatus(Device var1, OtaUpdateSet var2);

    public OtaUpdateGet getOtaUpdateStatus(Device var1, OtaImageType var2);

    public StringWrapper getOtaFwVersion(Device var1, OtaImageType var2);

    public TeamviewerStatus getTeamviewerStatus(Device var1);

    public void setTeamviewerStatus(Device var1, TeamviewerStatus var2);

    public RS232Routing getRS232Routing(Device var1);

    public void setRS232Routing(Device var1, RS232Routing var2);

    public SicpSerialPortForwarding getSicpSerialPortForwarding(Device var1);

    public void setSicpSerialPortForwarding(Device var1, SicpSerialPortForwarding var2);

    public WOL getWOL(Device var1);

    public void setWOL(Device var1, WOL var2);

    public HdmiOneWire getHdmiOneWire(Device var1);

    public void setHdmiOneWire(Device var1, HdmiOneWire var2);
}

