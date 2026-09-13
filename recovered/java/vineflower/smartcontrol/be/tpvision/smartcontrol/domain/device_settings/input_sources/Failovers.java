package be.tpvision.smartcontrol.domain.device_settings.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.input_sources.failovers.SetFailoverListMessages;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Failovers implements DeviceSetting {
   private static final int numberOfFailovers = Failover.values().length;
   private ArrayList<Failover> failoverList;

   public Failovers(final ArrayList<Failover> failoverList) {
      this.setFailoverList(failoverList);
   }

   public ArrayList<Failover> getFailoverList() {
      return this.failoverList;
   }

   public void setFailoverList(final ArrayList<Failover> failoverList) {
      Assert.notNull(failoverList, SetFailoverListMessages.FAILOVER_LIST_CAN_NOT_BE_NULL);
      int failoverListSize = failoverList.size();
      String failoverMessage = SetFailoverListMessages.getMaximumNumberOfFailoversMessage(numberOfFailovers);
      Assert.state(failoverListSize <= numberOfFailovers, failoverMessage);
      boolean isAnyFailoverNull = failoverList.stream().anyMatch(Objects::isNull);
      Assert.state(!isAnyFailoverNull, SetFailoverListMessages.FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
      this.failoverList = failoverList;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Failovers)) {
         return false;
      }

      Failovers that = (Failovers)object;
      return Objects.equals(this.getFailoverList(), that.getFailoverList());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getFailoverList());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("failoverList", this.getFailoverList()).toString();
   }
}
