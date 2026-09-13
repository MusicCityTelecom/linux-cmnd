package be.tpvision.smartcontrol.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class MatrixPosition {
   private int x;
   private int y;
   @Column(name = "size_x")
   private int sizeX;
   @Column(name = "size_y")
   private int sizeY;

   protected MatrixPosition() {
   }

   public MatrixPosition(final int x, final int y, final int sizeX, final int sizeY) {
      this.x = x;
      this.y = y;
      this.sizeX = sizeX;
      this.sizeY = sizeY;
   }

   public int getX() {
      return this.x;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public int getY() {
      return this.y;
   }

   public void setY(final int y) {
      this.y = y;
   }

   public int getSizeX() {
      return this.sizeX;
   }

   public void setSizeX(final int sizeX) {
      this.sizeX = sizeX;
   }

   public int getSizeY() {
      return this.sizeY;
   }

   public void setSizeY(final int sizeY) {
      this.sizeY = sizeY;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof MatrixPosition)) {
         return false;
      }

      MatrixPosition that = (MatrixPosition)object;
      return new EqualsBuilder().append(this.getX(), that.getX()).append(this.getY(), that.getY()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getX(), this.getY());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("x", this.getX())
         .append("y", this.getY())
         .append("sizeX", this.getSizeX())
         .append("sizeY", this.getSizeY())
         .toString();
   }
}
