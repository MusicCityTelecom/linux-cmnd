/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.messages.mappers.video.color_parameters.ToColorParametersMessages;
import be.tpvision.smartcontrol.messages.mappers.video.color_parameters.ToColorParametersViewModelMessages;
import be.tpvision.smartcontrol.messages.mappers.video.color_parameters.color.ToColorMessages;
import be.tpvision.smartcontrol.messages.mappers.video.color_parameters.color.ToColorViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.video.ColorParametersViewModel;
import org.springframework.util.Assert;

public class ColorParametersMapper {
    private ColorParametersMapper() {
    }

    public static ColorParametersViewModel toColorParametersViewModel(ColorParameters colorParameters) {
        Assert.notNull((Object)colorParameters, ToColorParametersViewModelMessages.COLOR_PARAMETERS_CAN_NOT_BE_NULL);
        ColorParameters.Color domainRed = colorParameters.getRed();
        Assert.state(domainRed != null, ToColorParametersViewModelMessages.DOMAIN_RED_CAN_NOT_BE_NULL);
        ColorParametersViewModel.ColorViewModel viewModelRed = ColorMapper.toColorViewModel(domainRed);
        Assert.state(viewModelRed != null, ToColorParametersViewModelMessages.VIEW_MODEL_RED_CAN_NOT_BE_NULL);
        ColorParameters.Color domainGreen = colorParameters.getGreen();
        Assert.state(domainGreen != null, ToColorParametersViewModelMessages.DOMAIN_GREEN_CAN_NOT_BE_NULL);
        ColorParametersViewModel.ColorViewModel viewModelGreen = ColorMapper.toColorViewModel(domainGreen);
        Assert.state(viewModelGreen != null, ToColorParametersViewModelMessages.VIEW_MODEL_GREEN_CAN_NOT_BE_NULL);
        ColorParameters.Color domainBlue = colorParameters.getBlue();
        Assert.state(domainBlue != null, ToColorParametersViewModelMessages.DOMAIN_BLUE_CAN_NOT_BE_NULL);
        ColorParametersViewModel.ColorViewModel viewModelBlue = ColorMapper.toColorViewModel(domainBlue);
        Assert.state(viewModelBlue != null, ToColorParametersViewModelMessages.VIEW_MODEL_BLUE_CAN_NOT_BE_NULL);
        return new ColorParametersViewModel(viewModelRed, viewModelGreen, viewModelBlue);
    }

    public static ColorParameters toColorParameters(ColorParametersViewModel colorParametersViewModel) {
        Assert.notNull((Object)colorParametersViewModel, ToColorParametersMessages.COLOR_PARAMETERS_VIEW_MODEL_CAN_NOT_BE_NULL);
        ColorParametersViewModel.ColorViewModel viewModelRed = colorParametersViewModel.getRed();
        Assert.state(viewModelRed != null, ToColorParametersMessages.VIEW_MODEL_RED_CAN_NOT_BE_NULL);
        ColorParameters.Color domainRed = ColorMapper.toColor(viewModelRed);
        Assert.state(domainRed != null, ToColorParametersMessages.DOMAIN_RED_CAN_NOT_BE_NULL);
        ColorParametersViewModel.ColorViewModel viewModelGreen = colorParametersViewModel.getGreen();
        Assert.state(viewModelGreen != null, ToColorParametersMessages.VIEW_MODEL_GREEN_CAN_NOT_BE_NULL);
        ColorParameters.Color domainGreen = ColorMapper.toColor(viewModelGreen);
        Assert.state(domainGreen != null, ToColorParametersMessages.DOMAIN_GREEN_CAN_NOT_BE_NULL);
        ColorParametersViewModel.ColorViewModel viewModelBlue = colorParametersViewModel.getBlue();
        Assert.state(viewModelBlue != null, ToColorParametersMessages.VIEW_MODEL_BLUE_CAN_NOT_BE_NULL);
        ColorParameters.Color domainBlue = ColorMapper.toColor(viewModelBlue);
        Assert.state(domainBlue != null, ToColorParametersMessages.DOMAIN_BLUE_CAN_NOT_BE_NULL);
        return new ColorParameters(domainRed, domainGreen, domainBlue);
    }

    public static class ColorMapper {
        private ColorMapper() {
        }

        public static ColorParametersViewModel.ColorViewModel toColorViewModel(ColorParameters.Color color) {
            Assert.notNull((Object)color, ToColorViewModelMessages.COLOR_CAN_NOT_BE_NULL);
            int gain = color.getGain();
            int offset = color.getOffset();
            return new ColorParametersViewModel.ColorViewModel(gain, offset);
        }

        public static ColorParameters.Color toColor(ColorParametersViewModel.ColorViewModel colorViewModel) {
            Assert.notNull((Object)colorViewModel, ToColorMessages.COLOR_VIEW_MODEL_CAN_NOT_BE_NULL);
            int gain = colorViewModel.getGain();
            int offset = colorViewModel.getOffset();
            return new ColorParameters.Color(gain, offset);
        }
    }
}

