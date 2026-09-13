/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.messages.Messages;

public class MessageUtilities {
    public static final String INPUT_SOURCE_SOURCE_TYPE_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Input source source type");
    public static final String INPUT_SOURCE_SOURCE_LABEL_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Input source source label");
    public static final String FAILOVERS_LIST_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Failovers list");
    public static final String FAILOVERS_LIST_NO_NULL_ELEMENTS_MESSAGE = MessageUtilities.getCantContainNullValuesMessage("Failovers list");
    public static final String VIDEO_ALIGNMENT_ITEM_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Video alignment item");
    public static final String PICTURE_IN_PICTURE_STATUS_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Picture in picture status");
    public static final String PICTURE_IN_PICTURE_WINDOW_POSITION_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Picture in picture window position");
    public static final String PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Picture in picture source source type");
    public static final String PICTURE_IN_PICTURE_SOURCE_QUADRANT_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Picture in picture source quadrant");
    public static final String VIDEO_PARAMETERS_GAMMA_SELECTION_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Video parameters gamma selection");
    public static final String GET_COMMAND_TYPE_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("GetCommand.Type");
    public static final String SET_COMMAND_TYPE_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("SetCommand.Type");
    public static final String SET_COMMAND_CONVERT_TYPE_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("SetCommand.ConvertType");
    public static final String CONVERTIBLE_NOT_NULL_MESSAGE = Messages.getCanNotBeNullMessage("Convertible");

    private MessageUtilities() {
    }

    private static String getCantContainNullValuesMessage(String input) {
        return input.concat(" can't contain null values.");
    }
}

