/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoTimeSync;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import be.tpvision.smartcontrol.domain.device_settings.general.ClockParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.DateParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.ForceRestartCustomApp;
import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.LanguageOSD;
import be.tpvision.smartcontrol.domain.device_settings.general.MonitorSystem;
import be.tpvision.smartcontrol.domain.device_settings.general.OpsSdmSettings;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerLED;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.TimeZone;

public interface GeneralService {
    public PowerState getPowerState(Device var1);

    public void setPowerState(Device var1, PowerState var2);

    public RemoteControlLockState getRemoteControlLockState(Device var1);

    public void setRemoteControlLockState(Device var1, RemoteControlLockState var2);

    public KeypadLockState getKeypadLockState(Device var1);

    public void setKeypadLockState(Device var1, KeypadLockState var2);

    public PowerStateAtColdStart getPowerStateAtColdStart(Device var1);

    public void setPowerStateAtColdStart(Device var1, PowerStateAtColdStart var2);

    public void monitorRestart(Device var1, MonitorSystem var2);

    public Backlight getBacklight(Device var1);

    public void setBacklight(Device var1, Backlight var2);

    public AutoRestartParameter getAutoRestartParameter(Device var1);

    public void setAutoRestartParameter(Device var1, AutoRestartParameter var2);

    public LanguageOSD getLanguageOSD(Device var1);

    public void setLanguageOSD(Device var1, LanguageOSD var2);

    public OpsSdmSettings getOpsSdmSettings(Device var1);

    public void setOpsSdmSettings(Device var1, OpsSdmSettings var2);

    public PowerLED getPowerLED(Device var1);

    public void setPowerLED(Device var1, PowerLED var2);

    public ForceRestartCustomApp getForceRestartCustomApp(Device var1);

    public void setForceRestartCustomApp(Device var1, ForceRestartCustomApp var2);

    public ClockParameter getClockParameter(Device var1);

    public void setClockParameter(Device var1, ClockParameter var2);

    public DateParameter getDateParameter(Device var1);

    public void setDateParameter(Device var1, DateParameter var2);

    public AutoTimeSync getAutoTimeSync(Device var1);

    public void setAutoTimeSync(Device var1, AutoTimeSync var2);

    public TimeZone getTimeZone(Device var1);

    public void setTimeZone(Device var1, TimeZone var2);

    public StringWrapper getNumberOfInputSources(Device var1);
}

