/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.mappers.miscellaneous.pixel_shift;

import be.tpvision.smartcontrol.messages.Messages;

public class ToPixelShiftMessages {
    public static final String TILING_VIEW_MODEL_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("Pixel shift");
    public static final String VIEW_MODEL_STATE_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("model State");
    public static final String DOMAIN_STATE_CAN_NOT_BE_NULL = Messages.getViewModelCanNotBeNullMessage("domain State");

    private ToPixelShiftMessages() {
    }
}

