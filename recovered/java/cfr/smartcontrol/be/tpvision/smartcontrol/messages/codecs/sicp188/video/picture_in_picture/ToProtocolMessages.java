/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.codecs.sicp188.video.picture_in_picture;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
    public static final String PICTURE_IN_PICTURE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Picture in picture");
    public static final String DOMAIN_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain status");
    public static final String PROTOCOL_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol status");
    public static final String DOMAIN_WINDOW_POSITION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain window position");
    public static final String PROTOCOL_WINDOW_POSITION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol window position");

    private ToProtocolMessages() {
    }
}

