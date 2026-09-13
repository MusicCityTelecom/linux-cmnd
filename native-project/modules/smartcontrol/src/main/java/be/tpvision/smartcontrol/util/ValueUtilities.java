package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.messages.util.value_utilities.EnumToStringListMessages;
import be.tpvision.smartcontrol.messages.util.value_utilities.GetEnumValueMessages;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

public class ValueUtilities {
   private ValueUtilities() {
   }

   public static int getValue(final int value, final int minimum, final int maximum) {
      if (value < minimum) {
         return minimum;
      } else {
         return value > maximum ? maximum : value;
      }
   }

   public static byte getByteValue(final int value) {
      return (byte)getValue(value, -128, 127);
   }

   public static byte getByteValueFromUnsigned(final int value) {
      return (byte)getValue(value, 0, 255);
   }

   public static String toHexString(final byte value) {
      return String.format("0x%02X", value);
   }

   public static <T extends Enum> T getEnumValue(final Class<T> enumClass, final String enumValue) {
      Assert.notNull(enumClass, GetEnumValueMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
      Assert.notNull(enumValue, GetEnumValueMessages.ENUM_VALUE_CAN_NOT_BE_NULL);
      String enumValueUpperCase = enumValue.toUpperCase();
      return (T) Enum.valueOf(enumClass, enumValueUpperCase);
   }

   public static <T extends Enum> List<String> enumToStringList(final Class<T> enumClass) {
      Assert.notNull(enumClass, EnumToStringListMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
      List<String> values = new ArrayList<>();

      for (T enumValue : enumClass.getEnumConstants()) {
         String stringValue = String.valueOf(enumValue);
         values.add(stringValue);
      }

      return values;
   }

   public static boolean[] toBooleanArray(final byte byteValue) {
      return new boolean[]{
         (byteValue & 128) != 0,
         (byteValue & 64) != 0,
         (byteValue & 32) != 0,
         (byteValue & 16) != 0,
         (byteValue & 8) != 0,
         (byteValue & 4) != 0,
         (byteValue & 2) != 0,
         (byteValue & 1) != 0
      };
   }

   public static byte toByte(final boolean[] booleanArray) {
      byte byteValue = 0;

      for (boolean booleanValue : booleanArray) {
         byteValue = (byte)(byteValue << 1);
         if (booleanValue) {
            byteValue = (byte)(byteValue | 1);
         }
      }

      return byteValue;
   }

   public static boolean isWithinByteRange(final int value) {
      return value >= -128 && value <= 127;
   }
}
