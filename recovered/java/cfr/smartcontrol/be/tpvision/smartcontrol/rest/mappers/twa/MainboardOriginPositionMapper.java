/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.twa;

import be.tpvision.smartcontrol.domain.twa.MainboardOriginPosition;
import be.tpvision.smartcontrol.messages.mappers.twa.mainboard_origin_position.ToMainboardOriginPositionMessages;
import be.tpvision.smartcontrol.rest.view_models.twa.MainboardOriginPositionViewModel;
import org.springframework.util.Assert;

public class MainboardOriginPositionMapper {
    private MainboardOriginPositionMapper() {
    }

    public static MainboardOriginPosition toMainboardOriginPosition(MainboardOriginPositionViewModel mainboardOriginPositionViewModel) {
        Assert.notNull((Object)mainboardOriginPositionViewModel, ToMainboardOriginPositionMessages.MAINBOARD_ORIGIN_POSITION_VIEW_MODEL_CAN_NOT_BE_NULL);
        int x = mainboardOriginPositionViewModel.getX();
        int y = mainboardOriginPositionViewModel.getY();
        return new MainboardOriginPosition(x, y);
    }
}

