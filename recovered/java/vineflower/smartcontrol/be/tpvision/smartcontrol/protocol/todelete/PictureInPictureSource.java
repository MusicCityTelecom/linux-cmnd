package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureSource implements Convertibles {
   private PictureInPictureSource.SourceType pictureInPictureSourceSourceType;
   private InputSource.SourceType inputSourceSourceType;
   private PictureInPictureSource.Quadrant quadrant;

   public PictureInPictureSource(
      final PictureInPictureSource.SourceType pictureInPictureSourceSourceType,
      final InputSource.SourceType inputSourceSourceType,
      final PictureInPictureSource.Quadrant quadrant
   ) {
      this.setPictureInPictureSourceSourceType(pictureInPictureSourceSourceType);
      this.setInputSourceSourceType(inputSourceSourceType);
      this.setQuadrant(quadrant);
   }

   public PictureInPictureSource.SourceType getPictureInPictureSourceSourceType() {
      return this.pictureInPictureSourceSourceType;
   }

   public void setPictureInPictureSourceSourceType(final PictureInPictureSource.SourceType pictureInPictureSourceSourceType) {
      Objects.requireNonNull(pictureInPictureSourceSourceType, MessageUtilities.PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_NOT_NULL_MESSAGE);
      this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
   }

   public InputSource.SourceType getInputSourceSourceType() {
      return this.inputSourceSourceType;
   }

   public void setInputSourceSourceType(final InputSource.SourceType inputSourceSourceType) {
      this.inputSourceSourceType = inputSourceSourceType;
   }

   public PictureInPictureSource.Quadrant getQuadrant() {
      return this.quadrant;
   }

   public void setQuadrant(final PictureInPictureSource.Quadrant quadrant) {
      Objects.requireNonNull(quadrant, MessageUtilities.PICTURE_IN_PICTURE_SOURCE_QUADRANT_NOT_NULL_MESSAGE);
      this.quadrant = quadrant;
   }

   @Override
   public byte[] convert() {
      byte sourceTypeByte = this.pictureInPictureSourceSourceType.convert();
      byte inputSourceByte = this.inputSourceSourceType != null ? this.inputSourceSourceType.convert() : 0;
      byte quadrantByte = this.quadrant.convert();
      return new byte[]{sourceTypeByte, inputSourceByte, quadrantByte};
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
         .append(this.pictureInPictureSourceSourceType, that.pictureInPictureSourceSourceType)
         .append(this.inputSourceSourceType, that.inputSourceSourceType)
         .append(this.quadrant, that.quadrant)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.pictureInPictureSourceSourceType, this.inputSourceSourceType, this.quadrant);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("pictureInPictureSourceSourceType", this.pictureInPictureSourceSourceType)
         .append("inputSourceSourceType", this.inputSourceSourceType)
         .append("quadrant", this.quadrant)
         .toString();
   }

   public enum Quadrant implements Convertible {
      Q2((byte)0),
      Q3((byte)1),
      Q4((byte)2);

      private byte data;

      Quadrant(final byte data) {
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
      INPUT_SOURCE((byte)-3),
      SMART_CARD((byte)-2);

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
