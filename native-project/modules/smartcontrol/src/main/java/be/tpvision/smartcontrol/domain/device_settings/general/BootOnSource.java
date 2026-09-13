package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BootOnSource implements DeviceSetting {
   private BootOnSource.Tag tag;
   private BootOnSource.VideoSourceType videoSourceType;

   public BootOnSource() {
   }

   public BootOnSource(BootOnSource.VideoSourceType videoSourceType, BootOnSource.Tag tag) {
      this.setVideoSourceType(videoSourceType);
      this.setTag(tag);
   }

   public BootOnSource.Tag getTag() {
      return this.tag;
   }

   public void setTag(BootOnSource.Tag tag) {
      this.tag = tag;
   }

   public BootOnSource.VideoSourceType getVideoSourceType() {
      return this.videoSourceType;
   }

   public void setVideoSourceType(BootOnSource.VideoSourceType videoSourceType) {
      this.videoSourceType = videoSourceType;
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("tag", this.getTag()).append("videoSourceType", this.getVideoSourceType()).toString();
   }

   public enum Tag {
      TAG_ZERO,
      TAG_ONE,
      TAG_TWO,
      TAG_THREE,
      TAG_FOUR,
      TAG_FIVE,
      TAG_SIX,
      TAG_SEVEN,
      USB_AUTOPLAY;
   }

   public enum VideoSourceType {
      LAST_INPUT,
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
      HOME_LAUNCHER,
      USB_TYPEC,
      KIOSK,
      SMART_INFO,
      TUNER,
      GOOGLE_CAST;
   }
}
