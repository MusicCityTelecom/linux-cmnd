/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.audio;

import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.messages.mappers.audio.volume.ToVolumeMessages;
import be.tpvision.smartcontrol.messages.mappers.audio.volume.ToVolumeViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeViewModel;
import org.springframework.util.Assert;

public class VolumeMapper {
    private VolumeMapper() {
    }

    public static VolumeViewModel toVolumeViewModel(Volume volume) {
        Assert.notNull((Object)volume, ToVolumeViewModelMessages.VOLUME_CAN_NOT_BE_NULL);
        int speakerOut = volume.getSpeakerOut();
        int audioOut = volume.getAudioOut();
        return new VolumeViewModel(speakerOut, audioOut);
    }

    public static Volume toVolume(VolumeViewModel volumeViewModel) {
        Assert.notNull((Object)volumeViewModel, ToVolumeMessages.VOLUME_VIEW_MODEL_CAN_NOT_BE_NULL);
        int speakerOut = volumeViewModel.getSpeakerOut();
        int audioOut = volumeViewModel.getAudioOut();
        return new Volume(speakerOut, audioOut);
    }
}

