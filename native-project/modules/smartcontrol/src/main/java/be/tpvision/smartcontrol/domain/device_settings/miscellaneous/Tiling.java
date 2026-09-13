package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.repository.converters.tiling.EnableConverter;
import be.tpvision.smartcontrol.repository.converters.tiling.FrameCompConverter;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Tiling implements DeviceSetting {
   @Convert(converter = EnableConverter.class)
   private Tiling.Enable enable;
   @Convert(converter = FrameCompConverter.class)
   private Tiling.FrameComp frameComp;
   private int position;
   private int numberOfHorizontalMonitors;
   private int numberOfVerticalMonitors;

   protected Tiling() {
   }

   public Tiling(
      final Tiling.Enable enable,
      final Tiling.FrameComp frameComp,
      final int position,
      final int numberOfHorizontalMonitors,
      final int numberOfVerticalMonitors
   ) {
      this.setEnable(enable);
      this.setFrameComp(frameComp);
      this.setPosition(position);
      this.setNumberOfHorizontalMonitors(numberOfHorizontalMonitors);
      this.setNumberOfVerticalMonitors(numberOfVerticalMonitors);
   }

   public Tiling.Enable getEnable() {
      return this.enable;
   }

   public void setEnable(final Tiling.Enable enable) {
      this.enable = enable;
   }

   public Tiling.FrameComp getFrameComp() {
      return this.frameComp;
   }

   public void setFrameComp(final Tiling.FrameComp frameComp) {
      this.frameComp = frameComp;
   }

   public int getPosition() {
      return this.position;
   }

   public void setPosition(final int position) {
      this.position = position;
   }

   public int getNumberOfHorizontalMonitors() {
      return this.numberOfHorizontalMonitors;
   }

   public void setNumberOfHorizontalMonitors(final int numberOfHorizontalMonitors) {
      this.numberOfHorizontalMonitors = numberOfHorizontalMonitors;
   }

   public int getNumberOfVerticalMonitors() {
      return this.numberOfVerticalMonitors;
   }

   public void setNumberOfVerticalMonitors(final int numberOfVerticalMonitors) {
      this.numberOfVerticalMonitors = numberOfVerticalMonitors;
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
         .append(this.getEnable(), tiling.getEnable())
         .append(this.getFrameComp(), tiling.getFrameComp())
         .append(this.getPosition(), tiling.getPosition())
         .append(this.getNumberOfHorizontalMonitors(), tiling.getNumberOfHorizontalMonitors())
         .append(this.getNumberOfVerticalMonitors(), tiling.getNumberOfVerticalMonitors())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getEnable(), this.getFrameComp(), this.getPosition(), this.getNumberOfHorizontalMonitors(), this.getNumberOfVerticalMonitors());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("enable", this.getEnable())
         .append("frameComp", this.getFrameComp())
         .append("position", this.getPosition())
         .append("numberOfHorizontalMonitors", this.getNumberOfHorizontalMonitors())
         .append("numberOfVerticalMonitors", this.getNumberOfVerticalMonitors())
         .toString();
   }

   public enum Enable {
      NO,
      YES;
   }

   public enum FrameComp {
      NO,
      YES,
      DO_NOT_OVERWRITE;
   }
}
