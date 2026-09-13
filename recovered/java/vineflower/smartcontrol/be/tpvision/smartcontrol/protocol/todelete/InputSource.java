package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.repository.converters.input_source.SourceLabelConverter;
import be.tpvision.smartcontrol.repository.converters.input_source.SourceTypeConverter;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class InputSource implements Convertibles {
   @Convert(converter = SourceTypeConverter.class)
   private InputSource.SourceType sourceType;
   @Convert(converter = SourceLabelConverter.class)
   private InputSource.SourceLabel sourceLabel;

   protected InputSource() {
      this(InputSource.SourceType.HDMI_1, InputSource.SourceLabel.ON);
   }

   public InputSource(final InputSource.SourceType sourceType, final InputSource.SourceLabel sourceLabel) {
      this.setSourceType(sourceType);
      this.setSourceLabel(sourceLabel);
   }

   public InputSource.SourceType getSourceType() {
      return this.sourceType;
   }

   public void setSourceType(final InputSource.SourceType sourceType) {
      Objects.requireNonNull(sourceType, MessageUtilities.INPUT_SOURCE_SOURCE_TYPE_NOT_NULL_MESSAGE);
      this.sourceType = sourceType;
   }

   public InputSource.SourceLabel getSourceLabel() {
      return this.sourceLabel;
   }

   public void setSourceLabel(final InputSource.SourceLabel sourceLabel) {
      Objects.requireNonNull(sourceLabel, MessageUtilities.INPUT_SOURCE_SOURCE_LABEL_NOT_NULL_MESSAGE);
      this.sourceLabel = sourceLabel;
   }

   @Override
   public byte[] convert() {
      byte sourceTypeByte = this.sourceType.convert();
      byte reservedByte = 0;
      byte sourceLabelByte = this.sourceLabel.convert();
      return new byte[]{sourceTypeByte, reservedByte, sourceLabelByte, reservedByte};
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
      return new EqualsBuilder().append(this.sourceType, that.sourceType).append(this.sourceLabel, that.sourceLabel).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.sourceType, this.sourceLabel);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("sourceType", this.sourceType).append("sourceLabel", this.sourceLabel).toString();
   }

   public enum SourceLabel implements Convertible {
      OFF((byte)0),
      ON((byte)1);

      private byte data;

      SourceLabel(final byte data) {
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

   public enum SourceType implements Convertible {
      VIDEO((byte)1),
      S_VIDEO((byte)2),
      COMPONENT((byte)3),
      CVI_2((byte)4),
      VGA((byte)5),
      HDMI_2((byte)6),
      DISPLAY_PORT_2((byte)7),
      USB_2((byte)8),
      CARD_DVI_D((byte)9),
      DISPLAY_PORT_1((byte)10),
      CARD_OPS((byte)11),
      USB_1((byte)12),
      HDMI_1((byte)13),
      DVI_D((byte)14),
      HDMI_3((byte)15),
      BROWSER((byte)16),
      SMART_CMS((byte)17),
      DIGITAL_MEDIA_SERVER((byte)18),
      INTERNAL_STORAGE((byte)19),
      RESERVED_1((byte)20),
      RESERVED_2((byte)21);

      private byte data;

      SourceType(final byte data) {
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
