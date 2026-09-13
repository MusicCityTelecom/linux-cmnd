package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Tiling implements Convertibles {
   public static boolean TEMP_IS_ZERO_BEZEL_MODEL = false;
   public static final int MINIMUM_POSITION = 0;
   public static final int MAXIMUM_POSITION = 25;
   public static final int MAXIMUM_POSITION_ZERO_BEZEL = 150;
   public static final int MAXIMUM_NUMBER_OF_MONITORS = 5;
   public static final int MAXIMUM_NUMBER_OF_HMONITORS_ZERO_BEZEL = 15;
   public static final int MAXIMUM_NUMBER_OF_VMONITORS_ZERO_BEZEL = 10;
   private Tiling.Enable enable;
   private Tiling.FrameComp frameComp;
   private int position;
   private final int maximumPosition;
   private int numberOfHMonitors;
   private final int maximumNumberOfHMonitors;
   private int numberOfVMonitors;
   private final int maximumNumberOfVMonitors;

   public Tiling(
      final Tiling.Enable enable,
      final Tiling.FrameComp frameComp,
      final int position,
      final int numberOfHMonitors,
      final int numberOfVMonitors,
      final boolean isZeroBezelModel
   ) {
      this.enable = enable != null ? enable : Tiling.Enable.NO;
      this.frameComp = frameComp != null ? frameComp : Tiling.FrameComp.NO;
      if (isZeroBezelModel) {
         this.maximumPosition = 150;
         this.maximumNumberOfHMonitors = 15;
         this.maximumNumberOfVMonitors = 10;
      } else {
         this.maximumPosition = 25;
         this.maximumNumberOfHMonitors = this.maximumNumberOfVMonitors = 5;
      }

      this.position = this.getValue(position, this.maximumPosition);
      this.numberOfHMonitors = this.getValue(numberOfHMonitors, this.maximumNumberOfHMonitors);
      this.numberOfVMonitors = this.getValue(numberOfVMonitors, this.maximumNumberOfVMonitors);
   }

   public Tiling.Enable getEnable() {
      return this.enable;
   }

   public Tiling.FrameComp getFrameComp() {
      return this.frameComp;
   }

   public int getPosition() {
      return this.position;
   }

   public int getNumberOfHMonitors() {
      return this.numberOfHMonitors;
   }

   public int getNumberOfVMonitors() {
      return this.numberOfVMonitors;
   }

   private int getValue(final int value, final int maximum) {
      return ValueUtilities.getValue(value, 0, maximum);
   }

   @Override
   public byte[] convert() {
      Byte enableByte = this.enable.convert();
      Byte frameComp = this.frameComp.convert();
      Byte positionByte = (byte)this.position;
      int monitors = (this.numberOfVMonitors - 1) * this.maximumNumberOfHMonitors + this.numberOfHMonitors;
      Byte monitorsByte = (byte)monitors;
      return new byte[]{enableByte, frameComp, positionByte, monitorsByte};
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Tiling)) {
         return false;
      }

      Tiling tiling = (Tiling)object;
      return new EqualsBuilder()
         .append(this.position, tiling.position)
         .append(this.maximumPosition, tiling.maximumPosition)
         .append(this.numberOfHMonitors, tiling.numberOfHMonitors)
         .append(this.maximumNumberOfHMonitors, tiling.maximumNumberOfHMonitors)
         .append(this.numberOfVMonitors, tiling.numberOfVMonitors)
         .append(this.maximumNumberOfVMonitors, tiling.maximumNumberOfVMonitors)
         .append(this.enable, tiling.enable)
         .append(this.frameComp, tiling.frameComp)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.enable,
         this.frameComp,
         this.position,
         this.maximumPosition,
         this.numberOfHMonitors,
         this.maximumNumberOfHMonitors,
         this.numberOfVMonitors,
         this.maximumNumberOfVMonitors
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("enable", this.enable)
         .append("frameComp", this.frameComp)
         .append("position", this.position)
         .append("maximumPosition", this.maximumPosition)
         .append("numberOfHMonitors", this.numberOfHMonitors)
         .append("maximumNumberOfHMonitors", this.maximumNumberOfHMonitors)
         .append("numberOfVMonitors", this.numberOfVMonitors)
         .append("maximumNumberOfVMonitors", this.maximumNumberOfVMonitors)
         .toString();
   }

   public enum Enable implements Convertible {
      NO((byte)0),
      YES((byte)1);

      private byte data;

      Enable(final byte data) {
         this.data = data;
      }

      public byte getData() {
         return this.data;
      }

      @Override
      public byte convert() {
         return this.data;
      }
   }

   public enum FrameComp implements Convertible {
      NO((byte)0),
      YES((byte)1),
      DO_NOT_OVERWRITE((byte)2);

      private byte data;

      FrameComp(final byte data) {
         this.data = data;
      }

      public byte getData() {
         return this.data;
      }

      @Override
      public byte convert() {
         return this.data;
      }
   }
}
