/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioSync;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.SpeakersStatus;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;

public interface AudioService {
    public Volume getVolume(Device var1);

    public void setVolume(Device var1, Volume var2);

    public void setVolume(Device var1, VolumeUpDown var2);

    public VolumeLimits getVolumeLimitsSpeakerOut(Device var1);

    public void setVolumeLimitsSpeakerOut(Device var1, VolumeLimits var2);

    public VolumeLimits getVolumeLimitsAudioOut(Device var1);

    public void setVolumeLimitsAudioOut(Device var1, VolumeLimits var2);

    public AudioParameters getAudioParameters(Device var1);

    public void setAudioParameters(Device var1, AudioParameters var2);

    public Mute getMute(Device var1);

    public void setMute(Device var1, Mute var2);

    public AudioSync getAudioSync(Device var1);

    public void setAudioSync(Device var1, AudioSync var2);

    public SpeakersStatus getSpeakersStatus(Device var1);

    public void setSpeakersStatus(Device var1, SpeakersStatus var2);
}

