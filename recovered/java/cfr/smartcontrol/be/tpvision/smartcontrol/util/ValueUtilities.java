/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.messages.util.value_utilities.EnumToStringListMessages;
import be.tpvision.smartcontrol.messages.util.value_utilities.GetEnumValueMessages;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

public class ValueUtilities {
    private ValueUtilities() {
    }

    public static int getValue(int value, int minimum, int maximum) {
        if (value < minimum) {
            return minimum;
        }
        if (value > maximum) {
            return maximum;
        }
        return value;
    }

    public static byte getByteValue(int value) {
        return (byte)ValueUtilities.getValue(value, -128, 127);
    }

    public static byte getByteValueFromUnsigned(int value) {
        return (byte)ValueUtilities.getValue(value, 0, 255);
    }

    public static String toHexString(byte value) {
        return String.format("0x%02X", value);
    }

    public static <T extends Enum> T getEnumValue(Class<T> enumClass, String enumValue) {
        Assert.notNull(enumClass, GetEnumValueMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
        Assert.notNull((Object)enumValue, GetEnumValueMessages.ENUM_VALUE_CAN_NOT_BE_NULL);
        String enumValueUpperCase = enumValue.toUpperCase();
        return Enum.valueOf(enumClass, enumValueUpperCase);
    }

    public static <T extends Enum> List<String> enumToStringList(Class<T> enumClass) {
        Assert.notNull(enumClass, EnumToStringListMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
        ArrayList<String> values = new ArrayList<String>();
        for (Enum enumValue : (Enum[])enumClass.getEnumConstants()) {
            String stringValue = String.valueOf(enumValue);
            values.add(stringValue);
        }
        return values;
    }

    public static boolean[] toBooleanArray(byte byteValue) {
        boolean[] booleanArray = new boolean[]{(byteValue & 0x80) != 0, (byteValue & 0x40) != 0, (byteValue & 0x20) != 0, (byteValue & 0x10) != 0, (byteValue & 8) != 0, (byteValue & 4) != 0, (byteValue & 2) != 0, (byteValue & 1) != 0};
        return booleanArray;
    }

    public static byte toByte(boolean[] booleanArray) {
        byte byteValue = 0;
        for (boolean booleanValue : booleanArray) {
            byteValue = (byte)(byteValue << 1);
            if (!booleanValue) continue;
            byteValue = (byte)(byteValue | 1);
        }
        return byteValue;
    }

    public static boolean isWithinByteRange(int value) {
        return value >= -128 && value <= 127;
    }
}

