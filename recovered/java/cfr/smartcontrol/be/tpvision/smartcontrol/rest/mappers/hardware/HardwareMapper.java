/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.hardware;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.mappers.hardware.hardware.ToHardwareViewModelListMessages;
import be.tpvision.smartcontrol.messages.mappers.hardware.hardware.ToHardwareViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.hardware.HardwareViewModel;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class HardwareMapper {
    private HardwareMapper() {
    }

    public static HardwareViewModel toHardwareViewModel(Hardware hardware) {
        Assert.notNull((Object)hardware, ToHardwareViewModelMessages.HARDWARE_CAN_NOT_BE_NULL);
        HardwareViewModel hardwareViewModel = new HardwareViewModel();
        String hardwareKey = hardware.getHardwareKey();
        hardwareViewModel.setHardwareKey(hardwareKey);
        String contentId = hardware.getContentId();
        hardwareViewModel.setContentId(contentId);
        return hardwareViewModel;
    }

    public static List<HardwareViewModel> toHardwareViewModelList(Collection<Hardware> hardwareCollection) {
        Assert.notNull(hardwareCollection, ToHardwareViewModelListMessages.HARDWARE_COLLECTION_CAN_NOT_BE_NULL);
        return hardwareCollection.stream().filter(Objects::nonNull).map(HardwareMapper::toHardwareViewModel).filter(Objects::nonNull).collect(Collectors.toList());
    }
}

