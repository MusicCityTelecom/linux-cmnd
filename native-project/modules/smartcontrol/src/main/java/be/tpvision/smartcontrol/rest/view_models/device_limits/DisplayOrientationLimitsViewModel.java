package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DisplayOrientationLimitsViewModel {
   private EnumLimitsViewModel<DisplayOrientation.AutoRotate> autoRotate;
   private EnumLimitsViewModel<DisplayOrientation.OsdRotation> osdRotation;
   private EnumLimitsViewModel<DisplayOrientation.ImageAll> imageAll;
   private EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> displayWindow1;
   private EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> displayWindow2;
   private EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> displayWindow3;
   private EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> displayWindow4;

   public DisplayOrientationLimitsViewModel() {
      this(
         new EnumLimitsViewModel<>(DisplayOrientation.AutoRotate.class),
         new EnumLimitsViewModel<>(DisplayOrientation.OsdRotation.class),
         new EnumLimitsViewModel<>(DisplayOrientation.ImageAll.class),
         new EnumLimitsViewModel<>(DisplayOrientation.DisplayWindow1.class),
         new EnumLimitsViewModel<>(DisplayOrientation.DisplayWindow2.class),
         new EnumLimitsViewModel<>(DisplayOrientation.DisplayWindow3.class),
         new EnumLimitsViewModel<>(DisplayOrientation.DisplayWindow4.class)
      );
   }

   public DisplayOrientationLimitsViewModel(
      final EnumLimitsViewModel<DisplayOrientation.AutoRotate> autoRotate,
      final EnumLimitsViewModel<DisplayOrientation.OsdRotation> osdRotation,
      final EnumLimitsViewModel<DisplayOrientation.ImageAll> imageAll,
      final EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> displayWindow1,
      final EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> displayWindow2,
      final EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> displayWindow3,
      final EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> displayWindow4
   ) {
      this.autoRotate = autoRotate;
      this.osdRotation = osdRotation;
      this.imageAll = imageAll;
      this.displayWindow1 = displayWindow1;
      this.displayWindow2 = displayWindow2;
      this.displayWindow3 = displayWindow3;
      this.displayWindow4 = displayWindow4;
   }

   public EnumLimitsViewModel<DisplayOrientation.AutoRotate> getAutoRotate() {
      return this.autoRotate;
   }

   public void setAutoRotate(final EnumLimitsViewModel<DisplayOrientation.AutoRotate> autoRotate) {
      this.autoRotate = autoRotate;
   }

   public EnumLimitsViewModel<DisplayOrientation.OsdRotation> getOsdRotation() {
      return this.osdRotation;
   }

   public void setOsdRotation(final EnumLimitsViewModel<DisplayOrientation.OsdRotation> osdRotation) {
      this.osdRotation = osdRotation;
   }

   public EnumLimitsViewModel<DisplayOrientation.ImageAll> getImageAll() {
      return this.imageAll;
   }

   public void setImageAll(final EnumLimitsViewModel<DisplayOrientation.ImageAll> imageAll) {
      this.imageAll = imageAll;
   }

   public EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> getDisplayWindow1() {
      return this.displayWindow1;
   }

   public void setDisplayWindow1(final EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> displayWindow1) {
      this.displayWindow1 = displayWindow1;
   }

   public EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> getDisplayWindow2() {
      return this.displayWindow2;
   }

   public void setDisplayWindow2(final EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> displayWindow2) {
      this.displayWindow2 = displayWindow2;
   }

   public EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> getDisplayWindow3() {
      return this.displayWindow3;
   }

   public void setDisplayWindow3(final EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> displayWindow3) {
      this.displayWindow3 = displayWindow3;
   }

   public EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> getDisplayWindow4() {
      return this.displayWindow4;
   }

   public void setDisplayWindow4(final EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> displayWindow4) {
      this.displayWindow4 = displayWindow4;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DisplayOrientationLimitsViewModel)) {
         return false;
      }

      DisplayOrientationLimitsViewModel that = (DisplayOrientationLimitsViewModel)object;
      return new EqualsBuilder()
         .append(this.getAutoRotate(), that.getAutoRotate())
         .append(this.getOsdRotation(), that.getOsdRotation())
         .append(this.getImageAll(), that.getImageAll())
         .append(this.getDisplayWindow1(), that.getDisplayWindow1())
         .append(this.getDisplayWindow2(), that.getDisplayWindow2())
         .append(this.getDisplayWindow3(), that.getDisplayWindow3())
         .append(this.getDisplayWindow4(), that.getDisplayWindow4())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getAutoRotate(),
         this.getOsdRotation(),
         this.getImageAll(),
         this.getDisplayWindow1(),
         this.getDisplayWindow2(),
         this.getDisplayWindow3(),
         this.getDisplayWindow4()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("autoRotate", this.getAutoRotate())
         .append("osdRotation", this.getOsdRotation())
         .append("imageAll", this.getImageAll())
         .append("displayWindow1", this.getDisplayWindow1())
         .append("displayWindow2", this.getDisplayWindow2())
         .append("displayWindow3", this.getDisplayWindow3())
         .append("displayWindow4", this.getDisplayWindow4())
         .toString();
   }
}
