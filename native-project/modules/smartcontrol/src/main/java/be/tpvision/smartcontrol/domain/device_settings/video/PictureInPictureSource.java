package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.picture_in_picture_source.SetPictureInPictureSourceSourceTypeMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class PictureInPictureSource implements DeviceSetting {
   private PictureInPictureSource.SourceType pictureInPictureSourceSourceType;
   private PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2;
   private PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3;
   private PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4;

   protected PictureInPictureSource() {
   }

   public PictureInPictureSource(
      final PictureInPictureSource.SourceType pictureInPictureSourceSourceType,
      final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2,
      final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3,
      final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4
   ) {
      this.setPictureInPictureSourceSourceType(pictureInPictureSourceSourceType);
      this.setInputSourceSourceTypeQ2(inputSourceSourceTypeQ2);
      this.setInputSourceSourceTypeQ3(inputSourceSourceTypeQ3);
      this.setInputSourceSourceTypeQ4(inputSourceSourceTypeQ4);
   }

   public PictureInPictureSource(
      final PictureInPictureSource.SourceType pictureInPictureSourceSourceType, final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2
   ) {
      this(pictureInPictureSourceSourceType, inputSourceSourceTypeQ2, null, null);
   }

   public PictureInPictureSource.SourceType getPictureInPictureSourceSourceType() {
      return this.pictureInPictureSourceSourceType;
   }

   public void setPictureInPictureSourceSourceType(final PictureInPictureSource.SourceType pictureInPictureSourceSourceType) {
      Assert.notNull(pictureInPictureSourceSourceType, SetPictureInPictureSourceSourceTypeMessages.PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_CAN_NOT_BE_NULL);
      this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
   }

   public PictureInPictureSource.InputSourceSourceType getInputSourceSourceTypeQ2() {
      return this.inputSourceSourceTypeQ2;
   }

   public void setInputSourceSourceTypeQ2(final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2) {
      this.inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2;
   }

   public PictureInPictureSource.InputSourceSourceType getInputSourceSourceTypeQ3() {
      return this.inputSourceSourceTypeQ3;
   }

   public void setInputSourceSourceTypeQ3(final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3) {
      this.inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3;
   }

   public PictureInPictureSource.InputSourceSourceType getInputSourceSourceTypeQ4() {
      return this.inputSourceSourceTypeQ4;
   }

   public void setInputSourceSourceTypeQ4(final PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4) {
      this.inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PictureInPictureSource)) {
         return false;
      }

      PictureInPictureSource that = (PictureInPictureSource)object;
      return new EqualsBuilder()
         .append(this.getPictureInPictureSourceSourceType(), that.getPictureInPictureSourceSourceType())
         .append(this.getInputSourceSourceTypeQ2(), that.getInputSourceSourceTypeQ2())
         .append(this.getInputSourceSourceTypeQ3(), that.getInputSourceSourceTypeQ3())
         .append(this.getInputSourceSourceTypeQ4(), that.getInputSourceSourceTypeQ4())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getPictureInPictureSourceSourceType(), this.getInputSourceSourceTypeQ2(), this.getInputSourceSourceTypeQ3(), this.getInputSourceSourceTypeQ4()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("pictureInPictureSourceSourceType", this.getPictureInPictureSourceSourceType())
         .append("inputSourceSourceQ2", this.getInputSourceSourceTypeQ2())
         .append("inputSourceSourceQ3", this.getInputSourceSourceTypeQ3())
         .append("inputSourceSourceQ4", this.getInputSourceSourceTypeQ4())
         .toString();
   }

   public enum InputSourceSourceType {
      VIDEO,
      S_VIDEO,
      COMPONENT,
      CVI_2,
      VGA,
      HDMI_2,
      DISPLAY_PORT_2,
      USB_2,
      CARD_DVI_D,
      DISPLAY_PORT_1,
      CARD_OPS,
      USB_1,
      HDMI_1,
      DVI_D,
      HDMI_3,
      BROWSER,
      SMART_CMS,
      DIGITAL_MEDIA_SERVER,
      INTERNAL_STORAGE,
      RESERVED_1,
      RESERVED_2,
      MEDIA_PLAYER,
      PDF_PLAYER,
      CUSTOM,
      HDMI_4,
      VGA_2,
      VGA_3,
      IWB,
      CMND_PLAY_WEB,
      USB_TYPEC,
      KIOSK,
      SMART_INFO,
      TUNER,
      GOOGLE_CAST;
   }

   public enum SourceType {
      INPUT_SOURCE,
      SMART_CARD;
   }
}
