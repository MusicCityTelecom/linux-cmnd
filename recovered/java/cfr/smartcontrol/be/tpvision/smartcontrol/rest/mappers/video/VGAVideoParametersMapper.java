/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.messages.mappers.video.vga_video_parameters.ToVgaVideoParametersMessages;
import be.tpvision.smartcontrol.messages.mappers.video.vga_video_parameters.ToVgaVideoParametersViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.video.VGAVideoParametersViewModel;
import org.springframework.util.Assert;

public class VGAVideoParametersMapper {
    private VGAVideoParametersMapper() {
    }

    public static VGAVideoParametersViewModel toVGAVideoParametersViewModel(VGAVideoParameters vgaVideoParameters) {
        Assert.notNull((Object)vgaVideoParameters, ToVgaVideoParametersViewModelMessages.VGA_VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
        int clock = vgaVideoParameters.getClock();
        int clockPhase = vgaVideoParameters.getClockPhase();
        int horizontalPosition = vgaVideoParameters.getHorizontalPosition();
        int verticalPosition = vgaVideoParameters.getVerticalPosition();
        return new VGAVideoParametersViewModel(clock, clockPhase, horizontalPosition, verticalPosition);
    }

    public static VGAVideoParameters toVGAVideoParameters(VGAVideoParametersViewModel vgaVideoParametersViewModel) {
        Assert.notNull((Object)vgaVideoParametersViewModel, ToVgaVideoParametersMessages.VGA_VIDEO_PARAMETERS_VIEW_MODEL_CAN_NOT_BE_NULL);
        int clock = vgaVideoParametersViewModel.getClock();
        int clockPhase = vgaVideoParametersViewModel.getClockPhase();
        int horizontalPosition = vgaVideoParametersViewModel.getHorizontalPosition();
        int verticalPosition = vgaVideoParametersViewModel.getVerticalPosition();
        return new VGAVideoParameters(clock, clockPhase, horizontalPosition, verticalPosition);
    }
}

