package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPicture implements Convertibles {
   private PictureInPicture.Status status;
   private PictureInPicture.WindowPosition windowPosition;

   public PictureInPicture(final PictureInPicture.Status status, final PictureInPicture.WindowPosition windowPosition) {
      this.setStatus(status);
      this.setWindowPosition(windowPosition);
   }

   public PictureInPicture.Status getStatus() {
      return this.status;
   }

   public void setStatus(final PictureInPicture.Status status) {
      Objects.requireNonNull(status, MessageUtilities.PICTURE_IN_PICTURE_STATUS_NOT_NULL_MESSAGE);
      this.status = status;
   }

   public PictureInPicture.WindowPosition getWindowPosition() {
      return this.windowPosition;
   }

   public void setWindowPosition(final PictureInPicture.WindowPosition windowPosition) {
      Objects.requireNonNull(windowPosition, MessageUtilities.PICTURE_IN_PICTURE_WINDOW_POSITION_NOT_NULL_MESSAGE);
      this.windowPosition = windowPosition;
   }

   @Override
   public byte[] convert() {
      byte statusByte = this.status.convert();
      byte windowPositionByte = this.windowPosition.convert();
      byte reserved = 0;
      return new byte[]{statusByte, windowPositionByte, 0, 0};
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PictureInPicture)) {
         return false;
      }

      PictureInPicture that = (PictureInPicture)object;
      return new EqualsBuilder().append(this.status, that.status).append(this.windowPosition, that.windowPosition).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.status, this.windowPosition);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("status", this.status).append("windowPosition", this.windowPosition).toString();
   }

   public enum Status implements Convertible {
      OFF((byte)0),
      ON((byte)1);

      private byte data;

      Status(final byte data) {
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

   public enum WindowPosition implements Convertible {
      BOTTOM_LEFT((byte)0),
      TOP_LEFT((byte)1),
      TOP_RIGHT((byte)2),
      BOTTOM_RIGHT((byte)3),
      OTHER((byte)4);

      private byte data;

      WindowPosition(final byte data) {
         this.data = data;
      }

      public int getData() {
         return this.data;
      }

      @Override
      public byte convert() {
         return this.data;
      }
   }
}
