/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.display_orientation.ToDisplayOrientationMessages;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.display_orientation.ToDisplayOrientationViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.DisplayOrientationViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class DisplayOrientationMapper {
    private DisplayOrientationMapper() {
    }

    public static DisplayOrientationViewModel toDisplayOrientationViewModel(DisplayOrientation displayOrientation) {
        Assert.notNull((Object)displayOrientation, ToDisplayOrientationViewModelMessages.DISPLAY_ORIENTATION_CAN_NOT_BE_NULL);
        DisplayOrientation.AutoRotate domainAutoRotate = displayOrientation.getAutoRotate();
        String viewModelAutoRotate = String.valueOf((Object)domainAutoRotate);
        DisplayOrientation.OsdRotation domainOsdRotation = displayOrientation.getOsdRotation();
        String viewModelOsdRotation = String.valueOf((Object)domainOsdRotation);
        DisplayOrientation.ImageAll domainImageAll = displayOrientation.getImageAll();
        String viewModelImageAll = String.valueOf((Object)domainImageAll);
        DisplayOrientation.DisplayWindow1 domainDisplayWindow1 = displayOrientation.getDisplayWindow1();
        String viewModelDisplayWindow1 = String.valueOf((Object)domainDisplayWindow1);
        DisplayOrientation.DisplayWindow2 domainDisplayWindow2 = displayOrientation.getDisplayWindow2();
        String viewModelDisplayWindow2 = String.valueOf((Object)domainDisplayWindow2);
        DisplayOrientation.DisplayWindow3 domainDisplayWindow3 = displayOrientation.getDisplayWindow3();
        String viewModelDisplayWindow3 = String.valueOf((Object)domainDisplayWindow3);
        DisplayOrientation.DisplayWindow4 domainDisplayWindow4 = displayOrientation.getDisplayWindow4();
        String viewModelDisplayWindow4 = String.valueOf((Object)domainDisplayWindow4);
        return new DisplayOrientationViewModel(viewModelAutoRotate, viewModelOsdRotation, viewModelImageAll, viewModelDisplayWindow1, viewModelDisplayWindow2, viewModelDisplayWindow3, viewModelDisplayWindow4);
    }

    public static DisplayOrientation toDisplayOrientation(DisplayOrientationViewModel displayOrientationViewModel) {
        Assert.notNull((Object)displayOrientationViewModel, ToDisplayOrientationMessages.DISPLAY_ORIENTATION_VIEW_MODEL_CAN_NOT_BE_NULL);
        String viewModelAutoRotate = displayOrientationViewModel.getAutoRotate();
        DisplayOrientation.AutoRotate domainAutoRotate = ValueUtilities.getEnumValue(DisplayOrientation.AutoRotate.class, viewModelAutoRotate);
        String viewModelOsdRotation = displayOrientationViewModel.getOsdRotation();
        DisplayOrientation.OsdRotation domainOsdRotation = ValueUtilities.getEnumValue(DisplayOrientation.OsdRotation.class, viewModelOsdRotation);
        String viewModelImageAll = displayOrientationViewModel.getImageAll();
        DisplayOrientation.ImageAll domainImageAll = ValueUtilities.getEnumValue(DisplayOrientation.ImageAll.class, viewModelImageAll);
        String viewModelDisplayWindow1 = displayOrientationViewModel.getDisplayWindow1();
        DisplayOrientation.DisplayWindow1 domainDisplayWindow1 = ValueUtilities.getEnumValue(DisplayOrientation.DisplayWindow1.class, viewModelDisplayWindow1);
        String viewModelDisplayWindow2 = displayOrientationViewModel.getDisplayWindow2();
        DisplayOrientation.DisplayWindow2 domainDisplayWindow2 = ValueUtilities.getEnumValue(DisplayOrientation.DisplayWindow2.class, viewModelDisplayWindow2);
        String viewModelDisplayWindow3 = displayOrientationViewModel.getDisplayWindow3();
        DisplayOrientation.DisplayWindow3 domainDisplayWindow3 = ValueUtilities.getEnumValue(DisplayOrientation.DisplayWindow3.class, viewModelDisplayWindow3);
        String viewModelDisplayWindow4 = displayOrientationViewModel.getDisplayWindow4();
        DisplayOrientation.DisplayWindow4 domainDisplayWindow4 = ValueUtilities.getEnumValue(DisplayOrientation.DisplayWindow4.class, viewModelDisplayWindow4);
        return new DisplayOrientation(domainAutoRotate, domainOsdRotation, domainImageAll, domainDisplayWindow1, domainDisplayWindow2, domainDisplayWindow3, domainDisplayWindow4);
    }
}

