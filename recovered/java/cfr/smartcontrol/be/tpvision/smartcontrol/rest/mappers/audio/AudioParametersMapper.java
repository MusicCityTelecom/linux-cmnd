/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.audio;

import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.messages.mappers.audio.audio_parameters.ToAudioParametersMessages;
import be.tpvision.smartcontrol.messages.mappers.audio.audio_parameters.ToAudioParametersViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.audio.AudioParametersViewModel;
import org.springframework.util.Assert;

public class AudioParametersMapper {
    private AudioParametersMapper() {
    }

    public static AudioParametersViewModel toAudioParametersViewModel(AudioParameters audioParameters) {
        Assert.notNull((Object)audioParameters, ToAudioParametersViewModelMessages.AUDIO_PARAMETERS_CAN_NOT_BE_NULL);
        int treble = audioParameters.getTreble();
        int bass = audioParameters.getBass();
        return new AudioParametersViewModel(treble, bass);
    }

    public static AudioParameters toAudioParameters(AudioParametersViewModel audioParametersViewModel) {
        Assert.notNull((Object)audioParametersViewModel, ToAudioParametersMessages.AUDIO_PARAMETERS_VIEW_MODEL_CAN_NOT_BE_NULL);
        int treble = audioParametersViewModel.getTreble();
        int bass = audioParametersViewModel.getBass();
        return new AudioParameters(treble, bass);
    }
}

