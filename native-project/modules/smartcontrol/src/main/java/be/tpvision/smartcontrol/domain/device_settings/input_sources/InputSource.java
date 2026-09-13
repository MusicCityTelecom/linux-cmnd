package be.tpvision.smartcontrol.domain.device_settings.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.input_sources.input_source.SetSourceLabelMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.input_sources.input_source.SetSourceTypeMessages;
import be.tpvision.smartcontrol.repository.converters.input_source.SourceLabelConverter;
import be.tpvision.smartcontrol.repository.converters.input_source.SourceTypeConverter;
import be.tpvision.smartcontrol.repository.converters.input_source.TagConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

@Embeddable
public class InputSource implements DeviceSetting {
   @Convert(converter = SourceTypeConverter.class)
   private InputSource.SourceType sourceType;
   @Convert(converter = TagConverter.class)
   private InputSource.Tag tag;
   @Convert(converter = SourceLabelConverter.class)
   private InputSource.SourceLabel sourceLabel;

   protected InputSource() {
      this(InputSource.SourceType.HDMI_1, InputSource.SourceLabel.ON);
   }

   public InputSource(final InputSource.SourceType sourceType, final InputSource.SourceLabel sourceLabel) {
      this.setSourceType(sourceType);
      this.setSourceLabel(sourceLabel);
   }

   public InputSource(InputSource.SourceType sourceType, InputSource.Tag tag, InputSource.SourceLabel sourceLabel) {
      this.setSourceType(sourceType);
      this.setTag(tag);
      this.setSourceLabel(sourceLabel);
   }

   public InputSource.SourceType getSourceType() {
      return this.sourceType;
   }

   public void setSourceType(final InputSource.SourceType sourceType) {
      Assert.notNull(sourceType, SetSourceTypeMessages.SOURCE_TYPE_CAN_NOT_BE_NULL);
      this.sourceType = sourceType;
   }

   public InputSource.Tag getTag() {
      return this.tag;
   }

   public void setTag(InputSource.Tag tag) {
      if (tag == null && this.isTagSourceType(this.sourceType)) {
         this.tag = InputSource.Tag.TAG_ONE;
      } else {
         this.tag = tag;
      }
   }

   private boolean isTagSourceType(InputSource.SourceType sourceType) {
      List<InputSource.SourceType> tagSourceTypesList = Arrays.asList(
         InputSource.SourceType.BROWSER, InputSource.SourceType.MEDIA_PLAYER, InputSource.SourceType.PDF_PLAYER
      );
      return tagSourceTypesList.contains(sourceType);
   }

   public InputSource.SourceLabel getSourceLabel() {
      return this.sourceLabel;
   }

   public void setSourceLabel(final InputSource.SourceLabel sourceLabel) {
      Assert.notNull(sourceLabel, SetSourceLabelMessages.SOURCE_LABEL_CAN_NOT_BE_NULL);
      this.sourceLabel = sourceLabel;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof InputSource)) {
         return false;
      }

      InputSource that = (InputSource)object;
      return new EqualsBuilder()
         .append(this.getSourceType(), that.getSourceType())
         .append(this.getTag(), that.getTag())
         .append(this.getSourceLabel(), that.getSourceLabel())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSourceType(), this.getTag(), this.getSourceLabel());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("sourceType", this.getSourceType())
         .append("tag", this.getTag())
         .append("sourceLabel", this.getSourceLabel())
         .toString();
   }

   public enum SourceLabel {
      OFF,
      ON;
   }

   public enum SourceType {
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

   public enum Tag {
      TAG_ONE,
      TAG_TWO,
      TAG_THREE,
      TAG_FOUR,
      TAG_FIVE,
      TAG_SIX,
      TAG_SEVEN,
      USB_AUTOPLAY;
   }
}
