package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Failovers implements Convertibles {
   private ArrayList<Failover> failoverList;

   public Failovers(final ArrayList<Failover> failoverList) {
      this.setFailoverList(failoverList);
   }

   public ArrayList<Failover> getFailoverList() {
      return this.failoverList;
   }

   public void setFailoverList(final ArrayList<Failover> failoverList) {
      Objects.requireNonNull(failoverList, MessageUtilities.FAILOVERS_LIST_NOT_NULL_MESSAGE);
      int numberOfFailovers = Failover.values().length;
      String failoverMessage = String.format("Failovers list has to contain exactly %d failovers.", numberOfFailovers);
      Validate.isTrue(failoverList.size() == numberOfFailovers, failoverMessage);
      Validate.noNullElements(failoverList, MessageUtilities.FAILOVERS_LIST_NO_NULL_ELEMENTS_MESSAGE);
      this.failoverList = failoverList;
   }

   @Override
   public byte[] convert() {
      int capacity = this.failoverList.size();
      ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);

      for (Failover failover : this.failoverList) {
         byte failoverData = failover.convert();
         byteBuffer.put(failoverData);
      }

      return byteBuffer.array();
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Failovers)) {
         return false;
      }

      Failovers failovers = (Failovers)object;
      return new EqualsBuilder().append(this.failoverList, failovers.failoverList).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.failoverList);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("failoverList", this.failoverList).toString();
   }
}
