package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.SicpMessage;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetCommand extends SicpMessage {
   private GetCommand.Type commandType;
   private Convertible convertible;

   public GetCommand(final int controlId, final int groupId, final GetCommand.Type commandType) {
      super(controlId, groupId);
      Objects.requireNonNull(commandType, MessageUtilities.GET_COMMAND_TYPE_NOT_NULL_MESSAGE);
      this.commandType = commandType;
   }

   public GetCommand(final int controlId, final int groupId, final GetCommand.Type commandType, final Convertible convertible) {
      this(controlId, groupId, commandType);
      Objects.requireNonNull(convertible, MessageUtilities.CONVERTIBLE_NOT_NULL_MESSAGE);
      Class<? extends Convertible> convertibleClass = (Class<? extends Convertible>)convertible.getClass();
      Class<?> commandTypeClass = commandType.typeClass;
      String notTheRightConvertibleMessage = String.format("Not the right convertible! Expected: %s and found %s", commandTypeClass, convertibleClass);
      Validate.isTrue(convertibleClass.equals(commandTypeClass), notTheRightConvertibleMessage);
      this.convertible = convertible;
   }

   @Override
   protected byte[] getData() {
      byte commandTypeData = this.commandType.getData();
      byte[] data;
      if (this.convertible != null) {
         byte convertibleData = this.convertible.convert();
         data = new byte[]{commandTypeData, convertibleData};
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

      if (!(object instanceof GetCommand)) {
         return false;
      }

      GetCommand that = (GetCommand)object;
      return new EqualsBuilder().append(this.commandType, that.commandType).append(this.convertible, that.convertible).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.commandType, this.convertible);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("commandType", this.commandType).append("convertible", this.convertible).toString();
   }

   public enum Type implements Convertible {
      SICP_VERSION_AND_PLATFORM_INFO((byte)-94, SicpAndPlatformInfo.class),
      MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE((byte)-95, ModelNumberFirmwareVersionBuildDateInfo.class),
      POWER_STATE((byte)25),
      REMOTE_CONTROL_LOCK_STATE((byte)29),
      KEYPAD_LOCK_STATE((byte)27),
      POWER_STATE_AT_COLD_START((byte)-92),
      INPUT_SOURCE((byte)-83),
      AUTO_SIGNAL_DETECTING((byte)-81),
      FAILOVERS((byte)-90),
      VIDEO_PARAMETERS((byte)51),
      COLOR_TEMPERATURE((byte)53),
      COLOR_PARAMETERS((byte)55),
      COLOR_TEMPERATURE_100K((byte)18),
      PICTURE_FORMAT((byte)59),
      VGA_VIDEO_PARAMETERS((byte)57),
      PICTURE_IN_PICTURE((byte)61),
      PICTURE_IN_PICTURE_SOURCE((byte)-123),
      VOLUME((byte)69),
      AUDIO_PARAMETERS((byte)67),
      MISCELLANEOUS((byte)15, Miscellaneous.Info.class),
      SMART_POWER((byte)-34),
      TEMPERATURE_SENSOR((byte)47),
      SERIAL_CODE((byte)21),
      TILING((byte)35),
      LIGHT_SENSOR((byte)37),
      OSD_ROTATING((byte)39),
      OSD_INFORMATION((byte)45),
      MEMC_EFFECT((byte)41),
      TOUCH((byte)31),
      NOISE_REDUCTION((byte)43),
      SCAN_MODE((byte)81),
      SCAN_CONVERSION((byte)83),
      SWITCH_ON_DELAY((byte)85),
      POWER_ON_LOGO((byte)63),
      FAN_SPEED((byte)98),
      APM((byte)-47),
      POWER_SAVING_MODE((byte)-45),
      SCHEDULING_PARAMETERS((byte)91, SchedulingParameters.Page.class),
      GROUP_ID((byte)93);

      private byte data;
      private Class<? extends Convertible> typeClass;

      Type(final byte data) {
         this.data = data;
      }

      Type(final byte data, final Class<? extends Convertible> typeClass) {
         this(data);
         this.typeClass = typeClass;
      }

      public byte getData() {
         return this.data;
      }

      public Class<? extends Convertible> getTypeClass() {
         return this.typeClass;
      }

      @Override
      public byte convert() {
         return this.data;
      }
   }
}
