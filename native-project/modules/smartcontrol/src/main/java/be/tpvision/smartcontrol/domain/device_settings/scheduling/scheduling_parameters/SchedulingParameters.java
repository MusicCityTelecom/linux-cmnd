package be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.SetPagesMessages;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class SchedulingParameters implements DeviceSetting {
   private ArrayList<Page> pages;

   public ArrayList<Page> getPages() {
      return this.pages;
   }

   public void setPages(final ArrayList<Page> pages) {
      Assert.notNull(pages, SetPagesMessages.PAGES_CAN_NOT_BE_NULL);
      this.pages = pages;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof SchedulingParameters)) {
         return false;
      }

      SchedulingParameters that = (SchedulingParameters)object;
      return new EqualsBuilder().append(this.getPages(), that.getPages()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getPages());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("pages", this.getPages()).toString();
   }
}
