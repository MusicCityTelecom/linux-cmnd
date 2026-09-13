/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp201.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.codecs.sicp201.input_sources.InputSourceCodec;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.messages.codecs.sicp201.scheduling.scheduling_parameters.page.PageCodecMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp201.scheduling.scheduling_parameters.page.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp201.scheduling.scheduling_parameters.page.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

public class PageCodec
extends Codec<Page> {
    static final byte STATUS_DISABLED_BYTE = 0;
    static final byte STATUS_ENABLED_BYTE = 1;
    private static final Map<Page.Status, Byte> domainStatuses = new EnumMap<Page.Status, Byte>(Page.Status.class);
    private static final Map<Byte, Page.Status> protocolStatuses;
    private static final Map<InputSource.SourceType, Byte> domainSourceTypes;
    private static final Map<Byte, InputSource.SourceType> protocolSourceTypes;
    private static final Map<InputSource.Tag, Byte> domainTags;
    private static final Map<Byte, InputSource.Tag> protocolTags;
    private static PageCodec pageCodec;

    private PageCodec() {
        super(Page.class);
    }

    public static Map<Page.Status, Byte> getDomainStatuses() {
        return domainStatuses;
    }

    public static Map<Byte, Page.Status> getProtocolStatuses() {
        return protocolStatuses;
    }

    public static Map<InputSource.SourceType, Byte> getDomainSourceTypes() {
        return domainSourceTypes;
    }

    public static Map<Byte, InputSource.SourceType> getProtocolSourceTypes() {
        return protocolSourceTypes;
    }

    public static Map<InputSource.Tag, Byte> getDomainTags() {
        return domainTags;
    }

    public static Map<Byte, InputSource.Tag> getProtocolTags() {
        return protocolTags;
    }

    public static synchronized PageCodec getInstance() {
        if (pageCodec == null) {
            pageCodec = new PageCodec();
        }
        return pageCodec;
    }

    @Override
    public byte[] toProtocol(Page page) {
        Assert.notNull((Object)page, ToProtocolMessages.PAGE_CAN_NOT_BE_NULL);
        Integer domainNumber = page.getNumber();
        Assert.state(domainNumber != null, ToProtocolMessages.DOMAIN_NUMBER_CAN_NOT_BE_NULL);
        byte protocolNumber = ValueUtilities.getByteValueFromUnsigned(domainNumber);
        boolean[] numberBooleanByte = ValueUtilities.toBooleanArray(protocolNumber);
        boolean[] numberBits = Arrays.copyOfRange(numberBooleanByte, 4, 8);
        Page.Status domainStatus = page.getStatus();
        Assert.state(domainStatus != null, ToProtocolMessages.DOMAIN_STATUS_CAN_NOT_BE_NULL);
        Byte protocolStatus = domainStatuses.get((Object)domainStatus);
        Assert.state(protocolStatus != null, ToProtocolMessages.PROTOCOL_STATUS_CAN_NOT_BE_NULL);
        boolean[] statusBooleanByte = ValueUtilities.toBooleanArray(protocolStatus);
        boolean[] statusBits = Arrays.copyOfRange(statusBooleanByte, 4, 8);
        boolean[] pageBits = ArrayUtils.addAll(numberBits, statusBits);
        byte protocolPage = ValueUtilities.toByte(pageBits);
        LocalTime domainStart = page.getStart();
        int domainStartHour = domainStart != null ? domainStart.getHour() : 24;
        byte protocolStartHour = ValueUtilities.getByteValueFromUnsigned(domainStartHour);
        int domainStartMinute = domainStart != null ? domainStart.getMinute() : 60;
        byte protocolStartMinute = ValueUtilities.getByteValueFromUnsigned(domainStartMinute);
        LocalTime domainEnd = page.getEnd();
        int domainEndHour = domainEnd != null ? domainEnd.getHour() : 24;
        byte protocolEndHour = ValueUtilities.getByteValueFromUnsigned(domainEndHour);
        int domainEndMinute = domainEnd != null ? domainEnd.getMinute() : 60;
        byte protocolEndMinute = ValueUtilities.getByteValueFromUnsigned(domainEndMinute);
        InputSource.SourceType domainSourceType = page.getSourceType();
        Byte protocolSourceType = domainSourceType != null ? domainSourceTypes.get((Object)domainSourceType) : domainSourceTypes.get((Object)InputSource.SourceType.HDMI_1);
        Page.WorkingDays domainWorkingDays = page.getWorkingDays();
        Assert.state(domainWorkingDays != null, ToProtocolMessages.DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL);
        LinkedHashMap<Page.WorkingDay, Boolean> domainWorkingDaysMap = domainWorkingDays.getWorkingDays();
        Assert.state(domainWorkingDaysMap != null, ToProtocolMessages.DOMAIN_WORKING_DAYS_MAP_CAN_NOT_BE_NULL);
        Collection<Object> domainWorkingDaysCollection = domainWorkingDaysMap.values();
        boolean isAnyDomainWorkingDayNull = domainWorkingDaysCollection.stream().anyMatch(Objects::isNull);
        Assert.state(!isAnyDomainWorkingDayNull, ToProtocolMessages.DOMAIN_WORKING_DAYS_COLLECTION_CAN_NOT_CONTAIN_NULL_VALUES);
        boolean isAllFalse = domainWorkingDaysCollection.stream().allMatch(e -> e == false);
        if (isAllFalse) {
            domainWorkingDays.getWorkingDays().put(Page.WorkingDay.MONDAY, true);
            domainWorkingDaysCollection = domainWorkingDays.getWorkingDays().values();
        }
        Boolean[] protocolWorkingDaysArray = (Boolean[])domainWorkingDaysCollection.stream().toArray(Boolean[]::new);
        boolean[] protocolWorkingDaysPrimitiveArray = ArrayUtils.toPrimitive(protocolWorkingDaysArray);
        ArrayUtils.reverse(protocolWorkingDaysPrimitiveArray);
        byte protocolWorkingDays = ValueUtilities.toByte(protocolWorkingDaysPrimitiveArray);
        byte[] protocolSchedulingParameters = new byte[]{protocolPage, protocolStartHour, protocolStartMinute, protocolEndHour, protocolEndMinute, protocolSourceType, protocolWorkingDays};
        InputSource.Tag domainTag = page.getTag();
        int newLength = protocolSchedulingParameters.length + 1;
        protocolSchedulingParameters = Arrays.copyOf(protocolSchedulingParameters, newLength);
        int tagIndex = newLength - 1;
        if (domainTag != null) {
            Byte protocolTag = domainTags.get((Object)domainTag);
            Assert.state(protocolTag != null, ToProtocolMessages.PROTOCOL_TAG_CAN_NOT_BE_NULL);
            protocolSchedulingParameters[tagIndex] = protocolTag;
        } else {
            protocolSchedulingParameters[tagIndex] = 0;
        }
        return protocolSchedulingParameters;
    }

    private static boolean isBitSet(byte byteValue, int bitPosition) {
        return (byteValue & 0xFF & 1 << bitPosition) != 0;
    }

    @Override
    public Page toDomain(byte[] bytes) {
        InputSource.Tag domainTag;
        InputSource.SourceType domainSourceType;
        if (bytes == null || bytes.length < 7) {
            return null;
        }
        Integer number = null;
        byte protocolStatus = bytes[0];
        Page.Status domainStatus = protocolStatuses.get(protocolStatus);
        Assert.state(domainStatus != null, ToDomainMessages.DOMAIN_STATUS_CAN_NOT_BE_NULL);
        byte protocolStartHour = bytes[1];
        int domainStartHour = Byte.toUnsignedInt(protocolStartHour);
        byte protocolStartMinute = bytes[2];
        int domainStartMinute = Byte.toUnsignedInt(protocolStartMinute);
        LocalTime domainStart = domainStartHour != 24 && domainStartMinute != 60 ? LocalTime.of(domainStartHour, domainStartMinute) : null;
        byte protocolEndHour = bytes[3];
        int domainEndHour = Byte.toUnsignedInt(protocolEndHour);
        byte protocolEndMinute = bytes[4];
        int domainEndMinute = Byte.toUnsignedInt(protocolEndMinute);
        LocalTime domainEnd = domainEndHour != 24 && domainEndMinute != 60 ? LocalTime.of(domainEndHour, domainEndMinute) : null;
        byte protocolSourceType = bytes[5];
        if (protocolSourceType == 0) {
            domainSourceType = null;
        } else {
            domainSourceType = protocolSourceTypes.get(protocolSourceType);
            Assert.state(domainSourceType != null, ToDomainMessages.DOMAIN_SOURCE_TYPE_CAN_NOT_BE_NULL);
        }
        byte workingDaysByte = bytes[6];
        boolean[] protocolWorkingDaysWorkingDays = new boolean[8];
        for (int i = 0; i < 8; ++i) {
            protocolWorkingDaysWorkingDays[i] = PageCodec.isBitSet(workingDaysByte, i);
        }
        Page.WorkingDays domainWorkingDays = new Page.WorkingDays();
        Page.WorkingDay[] domainWorkingDayValues = Page.WorkingDay.values();
        for (int i = 0; i < domainWorkingDayValues.length; ++i) {
            Page.WorkingDay domainWorkingDay = domainWorkingDayValues[i];
            boolean domainWorkingDayValue = protocolWorkingDaysWorkingDays[i];
            domainWorkingDays.setValue(domainWorkingDay, domainWorkingDayValue);
        }
        if (bytes.length >= 8) {
            byte protocolTag = bytes[7];
            if (protocolTag == 0) {
                domainTag = null;
            } else {
                domainTag = protocolTags.get(protocolTag);
                Assert.state(domainTag != null, ToDomainMessages.DOMAIN_TAG_CAN_NOT_BE_NULL);
            }
        } else {
            domainTag = null;
        }
        return new Page(number, domainStatus, domainStart, domainEnd, domainSourceType, domainWorkingDays, domainTag);
    }

    static {
        domainStatuses.put(Page.Status.DISABLED, (byte)0);
        domainStatuses.put(Page.Status.ENABLED, (byte)1);
        protocolStatuses = MapUtilities.inverse(domainStatuses);
        domainSourceTypes = InputSourceCodec.getDomainSourceTypes();
        Assert.state(domainSourceTypes != null, PageCodecMessages.DOMAIN_SOURCE_TYPES_CAN_NOT_BE_NULL);
        protocolSourceTypes = InputSourceCodec.getProtocolSourceTypes();
        Assert.state(protocolSourceTypes != null, PageCodecMessages.PROTOCOL_SOURCE_TYPES_CAN_NOT_BE_NULL);
        domainTags = InputSourceCodec.getDomainTags();
        Assert.state(domainTags != null, PageCodecMessages.DOMAIN_SOURCE_TYPES_CAN_NOT_BE_NULL);
        protocolTags = InputSourceCodec.getProtocolTags();
        Assert.state(protocolTags != null, PageCodecMessages.PROTOCOL_SOURCE_TYPES_CAN_NOT_BE_NULL);
    }
}

