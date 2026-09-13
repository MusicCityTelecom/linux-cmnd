/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.codecs.sicp188.video.video_parameters;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
    public static final String VIDEO_PARAMETERS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Video parameters");
    public static final String DOMAIN_GAMMA_SELECTION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain gamma selection");
    public static final String PROTOCOL_GAMMA_SELECTION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol gamma selection");

    private ToProtocolMessages() {
    }
}

