/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.twa;

import be.tpvision.smartcontrol.domain.twa.ModuleAddress;
import be.tpvision.smartcontrol.messages.mappers.twa.module_address.ToModuleAddressMessages;
import be.tpvision.smartcontrol.rest.view_models.twa.ModuleAddressViewModel;
import org.springframework.util.Assert;

public class ModuleAddressMapper {
    private ModuleAddressMapper() {
    }

    public static ModuleAddress toModuleAddress(ModuleAddressViewModel moduleAddressViewModel) {
        Assert.notNull((Object)moduleAddressViewModel, ToModuleAddressMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
        int x = moduleAddressViewModel.getX();
        int y = moduleAddressViewModel.getY();
        return new ModuleAddress(x, y);
    }
}

