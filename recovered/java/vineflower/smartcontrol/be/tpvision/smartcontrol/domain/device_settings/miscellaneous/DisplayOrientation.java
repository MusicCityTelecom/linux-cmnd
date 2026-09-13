package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DisplayOrientation implements DeviceSetting {
   private DisplayOrientation.AutoRotate autoRotate;
   private DisplayOrientation.OsdRotation osdRotation;
   private DisplayOrientation.ImageAll imageAll;
   private DisplayOrientation.DisplayWindow1 displayWindow1;
   private DisplayOrientation.DisplayWindow2 displayWindow2;
   private DisplayOrientation.DisplayWindow3 displayWindow3;
   private DisplayOrientation.DisplayWindow4 displayWindow4;

   protected DisplayOrientation() {
   }

   public DisplayOrientation(
      final DisplayOrientation.AutoRotate autoRotate,
      final DisplayOrientation.OsdRotation osdRotation,
      final DisplayOrientation.ImageAll imageAll,
      final DisplayOrientation.DisplayWindow1 displayWindow1,
      final DisplayOrientation.DisplayWindow2 displayWindow2,
      final DisplayOrientation.DisplayWindow3 displayWindow3,
      final DisplayOrientation.DisplayWindow4 displayWindow4
   ) {
      this.autoRotate = autoRotate;
      this.osdRotation = osdRotation;
      this.imageAll = imageAll;
      this.displayWindow1 = displayWindow1;
      this.displayWindow2 = displayWindow2;
      this.displayWindow3 = displayWindow3;
      this.displayWindow4 = displayWindow4;
   }

   public DisplayOrientation.AutoRotate getAutoRotate() {
      return this.autoRotate;
   }

   public void setAutoRotate(final DisplayOrientation.AutoRotate autoRotate) {
      this.autoRotate = autoRotate;
   }

   public DisplayOrientation.OsdRotation getOsdRotation() {
      return this.osdRotation;
   }

   public void setOsdRotation(final DisplayOrientation.OsdRotation osdRotation) {
      this.osdRotation = osdRotation;
   }

   public DisplayOrientation.ImageAll getImageAll() {
      return this.imageAll;
   }

   public void setImageAll(final DisplayOrientation.ImageAll imageAll) {
      this.imageAll = imageAll;
   }

   public DisplayOrientation.DisplayWindow1 getDisplayWindow1() {
      return this.displayWindow1;
   }

   public void setDisplayWindow1(final DisplayOrientation.DisplayWindow1 displayWindow1) {
      this.displayWindow1 = displayWindow1;
   }

   public DisplayOrientation.DisplayWindow2 getDisplayWindow2() {
      return this.displayWindow2;
   }

   public void setDisplayWindow2(final DisplayOrientation.DisplayWindow2 displayWindow2) {
      this.displayWindow2 = displayWindow2;
   }

   public DisplayOrientation.DisplayWindow3 getDisplayWindow3() {
      return this.displayWindow3;
   }

   public void setDisplayWindow3(final DisplayOrientation.DisplayWindow3 displayWindow3) {
      this.displayWindow3 = displayWindow3;
   }

   public DisplayOrientation.DisplayWindow4 getDisplayWindow4() {
      return this.displayWindow4;
   }

   public void setDisplayWindow4(final DisplayOrientation.DisplayWindow4 displayWindow4) {
      this.displayWindow4 = displayWindow4;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DisplayOrientation)) {
         return false;
      }

      DisplayOrientation that = (DisplayOrientation)object;
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

   public enum AutoRotate {
      OFF,
      ON;
   }

   public enum DisplayWindow1 {
      OFF,
      ON;
   }

   public enum DisplayWindow2 {
      OFF,
      ON;
   }

   public enum DisplayWindow3 {
      OFF,
      ON;
   }

   public enum DisplayWindow4 {
      OFF,
      ON;
   }

   public enum ImageAll {
      OFF,
      ON,
      ON_CLOCKWISE,
      ON_COUNTERCLOCKWISE;
   }

   public enum OsdRotation {
      LANDSCAPE,
      PORTRAIT;
   }
}
