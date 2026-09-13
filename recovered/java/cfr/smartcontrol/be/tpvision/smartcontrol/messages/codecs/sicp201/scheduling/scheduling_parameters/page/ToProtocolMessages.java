/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.codecs.sicp201.scheduling.scheduling_parameters.page;

import be.tpvision.smartcontrol.messages.Messages;

public class ToProtocolMessages {
    public static final String PAGE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Page");
    public static final String DOMAIN_NUMBER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain number");
    public static final String DOMAIN_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain status");
    public static final String PROTOCOL_STATUS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol status");
    public static final String DOMAIN_SOURCE_TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain source type");
    public static final String PROTOCOL_SOURCE_TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol source type");
    public static final String DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain working days");
    public static final String DOMAIN_WORKING_DAYS_MAP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Domain working days map");
    public static final String DOMAIN_WORKING_DAYS_COLLECTION_CAN_NOT_CONTAIN_NULL_VALUES = Messages.getCanNotContainNullValuesMessage("Domain working days collection");
    public static final String PROTOCOL_TAG_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Protocol tag");

    private ToProtocolMessages() {
    }
}

