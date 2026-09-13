package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.SicpMessage;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.text.WordUtils;

public class SetCommand extends SicpMessage {
   private SetCommand.Type commandType;
   private Convertible convertible;
   private Convertibles convertibles;

   private void validate(final SetCommand.Type commandType, final Object convertible, final SetCommand.ConvertType convertType) {
      Objects.requireNonNull(commandType, MessageUtilities.SET_COMMAND_TYPE_NOT_NULL_MESSAGE);
      Objects.requireNonNull(convertType, MessageUtilities.SET_COMMAND_CONVERT_TYPE_NOT_NULL_MESSAGE);
      String convertTypeString = String.valueOf(convertType).toLowerCase();
      String capitalizedConvertType = WordUtils.capitalize(convertTypeString);
      String convertibleNotNullMessage = String.format("%s of SetCommand can't be null.", capitalizedConvertType);
      Objects.requireNonNull(convertible, convertibleNotNullMessage);
      Class<? extends Object> convertibleClass = (Class<? extends Object>)convertible.getClass();
      Class<?> commandTypeClass = commandType.typeClass;
      String notTheRightConvertibleMessage = String.format("Not the right %s! Expected: %s and found %s", convertTypeString, commandTypeClass, convertibleClass);
      Validate.isTrue(convertibleClass.equals(commandTypeClass), notTheRightConvertibleMessage);
   }

   public SetCommand(final int controlId, final int groupId, final SetCommand.Type commandType) {
      super(controlId, groupId);
      Objects.requireNonNull(commandType, MessageUtilities.SET_COMMAND_TYPE_NOT_NULL_MESSAGE);
      this.commandType = commandType;
   }

   public SetCommand(final int control, final int group, final SetCommand.Type commandType, final Convertible convertible) {
      super(control, group);
      this.validate(commandType, convertible, SetCommand.ConvertType.CONVERTIBLE);
      this.commandType = commandType;
      this.convertible = convertible;
   }

   public SetCommand(final int control, final int group, SetCommand.Type commandType, final Convertibles convertibles) {
      super(control, group);
      this.validate(commandType, convertibles, SetCommand.ConvertType.CONVERTIBLES);
      this.commandType = commandType;
      this.convertibles = convertibles;
   }

   @Override
   protected byte[] getData() {
      byte commandTypeData = this.commandType.convert();
      byte[] data;
      if (this.convertible != null) {
         data = new byte[]{commandTypeData, this.convertible.convert()};
      } else if (this.convertibles != null) {
         byte[] convertiblesData = this.convertibles.convert();
         int capacity = 1 + convertiblesData.length;
         ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
         byteBuffer.put(commandTypeData);
         byteBuffer.put(convertiblesData);
         data = byteBuffer.array();
      } else {
         data = new byte[]{commandTypeData};
      }

      return data;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof SetCommand)) {
         return false;
      }

      SetCommand that = (SetCommand)object;
      return new EqualsBuilder()
         .append(this.commandType, that.commandType)
         .append(this.convertible, that.convertible)
         .append(this.convertibles, that.convertibles)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.commandType, this.convertible, this.convertibles);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("commandType", this.commandType)
         .append("convertible", this.convertible)
         .append("convertibles", this.convertibles)
         .toString();
   }

   private enum ConvertType {
      CONVERTIBLE,
      CONVERTIBLES;
   }

   public enum Type implements Convertible {
      POWER_STATE((byte)24, PowerState.class),
      REMOTE_CONTROL_LOCK_STATE((byte)28, RemoteControlLockState.class),
      KEYPAD_LOCK_STATE((byte)26, KeypadLockState.class),
      POWER_STATE_AT_COLD_START((byte)-93, PowerStateAtColdStart.class),
      INPUT_SOURCE((byte)-84, InputSource.class),
      AUTO_SIGNAL_DETECTING((byte)-82, AutoSignalDetecting.class),
      FAILOVERS((byte)-91, Failovers.class),
      VIDEO_PARAMETERS((byte)50, VideoParameters.class),
      COLOR_TEMPERATURE((byte)52, ColorTemperature.class),
      COLOR_PARAMETERS((byte)54, ColorParameters.class),
      COLOR_TEMPERATURE_100K((byte)17, ColorTemperature100K.class),
      PICTURE_FORMAT((byte)58, PictureFormat.class),
      VGA_VIDEO_PARAMETERS((byte)56, VGAVideoParameters.class),
      PICTURE_IN_PICTURE((byte)60, PictureInPicture.class),
      PICTURE_IN_PICTURE_SOURCE((byte)-124, PictureInPictureSource.class),
      VOLUME((byte)68, Volume.class),
      VOLUME_UP_DOWN((byte)65, VolumeUpDown.class),
      VOLUME_LIMITS((byte)-72, VolumeLimits.class),
      AUDIO_PARAMETERS((byte)66, AudioParameters.class),
      SMART_POWER((byte)-35, SmartPower.class),
      VIDEO_ALIGNMENT((byte)112, VideoAlignment.class),
      TILING((byte)34, Tiling.class),
      LIGHT_SENSOR((byte)36, LightSensor.class),
      OSD_ROTATING((byte)38, OSDRotating.class),
      OSD_INFORMATION((byte)44, OSDInformation.class),
      MEMC_EFFECT((byte)40, MEMCEffect.class),
      TOUCH((byte)30, Touch.class),
      NOISE_REDUCTION((byte)42, NoiseReduction.class),
      SCAN_MODE((byte)80, ScanMode.class),
      SCAN_CONVERSION((byte)82, ScanConversion.class),
      SWITCH_ON_DELAY((byte)84, SwitchOnDelay.class),
      FACTORY_RESET((byte)86),
      POWER_ON_LOGO((byte)62, PowerOnLogo.class),
      FAN_SPEED((byte)97, FanSpeed.class),
      APM((byte)-48, APM.class),
      POWER_SAVING_MODE((byte)-46, PowerSavingMode.class),
      SCHEDULING_PARAMETERS((byte)90, SchedulingParameters.class),
      GROUP_ID((byte)92, GroupId.class);

      private byte data;
      private Class<?> typeClass;

      Type(final byte data) {
         this.data = data;
      }

      Type(final byte data, final Class<?> typeClass) {
         this(data);
         this.typeClass = typeClass;
      }

      public byte getData() {
         return this.data;
      }

      public Class<?> getTypeClass() {
         return this.typeClass;
      }

      @Override
      public byte convert() {
         return this.data;
      }
   }
}
