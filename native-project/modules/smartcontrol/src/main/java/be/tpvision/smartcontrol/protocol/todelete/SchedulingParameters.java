package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SchedulingParameters implements Convertibles {
   private SchedulingParameters.Page page;
   private LocalTime start;
   private LocalTime end;
   private InputSource.SourceType sourceType;
   private SchedulingParameters.WorkingDays workingDays;

   public SchedulingParameters.Page getPage() {
      return this.page;
   }

   public void setPage(final SchedulingParameters.Page page) {
      this.page = page;
   }

   public LocalTime getStart() {
      return this.start;
   }

   public void setStart(final LocalTime start) {
      this.start = start;
   }

   public LocalTime getEnd() {
      return this.end;
   }

   public void setEnd(final LocalTime end) {
      this.end = end;
   }

   public InputSource.SourceType getSourceType() {
      return this.sourceType;
   }

   public void setSourceType(final InputSource.SourceType sourceType) {
      this.sourceType = sourceType;
   }

   public SchedulingParameters.WorkingDays getWorkingDays() {
      return this.workingDays;
   }

   public void setWorkingDays(final SchedulingParameters.WorkingDays workingDays) {
      this.workingDays = workingDays;
   }

   @Override
   public byte[] convert() {
      byte pageByte = this.page.convert();
      int startHour = this.start.getHour();
      byte startHourByte = (byte)startHour;
      int startMinute = this.start.getMinute();
      byte startMinuteByte = (byte)startMinute;
      int endHour = this.end.getHour();
      byte endHourByte = (byte)endHour;
      int endMinute = this.end.getMinute();
      byte endMinuteByte = (byte)endMinute;
      byte sourceTypeByte = this.sourceType.convert();
      byte workingDaysByte = this.workingDays.convert();
      return new byte[]{pageByte, startHourByte, startMinuteByte, endHourByte, endMinuteByte, sourceTypeByte, workingDaysByte};
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
      return new EqualsBuilder()
         .append(this.page, that.page)
         .append(this.start, that.start)
         .append(this.end, that.end)
         .append(this.sourceType, that.sourceType)
         .append(this.workingDays, that.workingDays)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.page, this.start, this.end, this.sourceType, this.workingDays);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("page", this.page)
         .append("start", this.start)
         .append("end", this.end)
         .append("sourceType", this.sourceType)
         .append("workingDays", this.workingDays)
         .toString();
   }

   public static class Page implements Convertible {
      private SchedulingParameters.Page.PageNumber pageNumber;
      private SchedulingParameters.Page.Status status;

      public Page(final SchedulingParameters.Page.PageNumber pageNumber) {
         this(pageNumber, null);
      }

      public Page(final SchedulingParameters.Page.PageNumber pageNumber, final SchedulingParameters.Page.Status status) {
         this.pageNumber = pageNumber;
         this.status = status;
      }

      public SchedulingParameters.Page.PageNumber getPageNumber() {
         return this.pageNumber;
      }

      public void setPageNumber(final SchedulingParameters.Page.PageNumber pageNumber) {
         this.pageNumber = pageNumber;
      }

      public SchedulingParameters.Page.Status getStatus() {
         return this.status;
      }

      public void setStatus(final SchedulingParameters.Page.Status status) {
         this.status = status;
      }

      @Override
      public byte convert() {
         byte statusByte = this.status != null ? this.status.convert() : 0;
         boolean[] statusBooleanByte = ValueUtilities.toBooleanArray(statusByte);
         boolean[] statusBits = Arrays.copyOfRange(statusBooleanByte, 4, 8);
         byte pageNumberByte = this.pageNumber != null ? this.pageNumber.convert() : 0;
         boolean[] pageNumberBooleanByte = ValueUtilities.toBooleanArray(pageNumberByte);
         boolean[] pageNumberBits = Arrays.copyOfRange(pageNumberBooleanByte, 4, 8);
         boolean[] pageBits = ArrayUtils.addAll(statusBits, pageNumberBits);
         return ValueUtilities.toByte(pageBits);
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof SchedulingParameters.Page)) {
            return false;
         }

         SchedulingParameters.Page page = (SchedulingParameters.Page)object;
         return new EqualsBuilder().append(this.pageNumber, page.pageNumber).append(this.status, page.status).isEquals();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.pageNumber, this.status);
      }

      @Override
      public String toString() {
         return new ToStringBuilder(this).append("pageNumber", this.pageNumber).append("status", this.status).toString();
      }

      public static class PageNumber implements Convertible {
         public static final int MINIMUM = 1;
         public static final int MAXIMUM = 7;
         private int number;

         public PageNumber() {
            this(1);
         }

         public PageNumber(final int number) {
            this.number = number;
         }

         public int getNumber() {
            return this.number;
         }

         public void setNumber(final int number) {
            this.number = ValueUtilities.getValue(number, 1, 7);
         }

         @Override
         public byte convert() {
            return (byte)this.number;
         }

         @Override
         public boolean equals(final Object object) {
            if (this == object) {
               return true;
            }

            if (!(object instanceof SchedulingParameters.Page.PageNumber)) {
               return false;
            }

            SchedulingParameters.Page.PageNumber that = (SchedulingParameters.Page.PageNumber)object;
            return new EqualsBuilder().append(this.number, that.number).isEquals();
         }

         @Override
         public int hashCode() {
            return Objects.hash(this.number);
         }

         @Override
         public String toString() {
            return new ToStringBuilder(this).append("number", this.number).toString();
         }
      }

      public enum Status implements Convertible {
         DISABLED((byte)0),
         ENABLED((byte)1);

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
   }

   public enum WorkingDay {
      EVERY_WEEK,
      MONDAY,
      TUESDAY,
      WEDNESDAY,
      THURSDAY,
      FRIDAY,
      SATURDAY,
      SUNDAY;
   }

   public static class WorkingDays implements Convertible {
      private LinkedHashMap<SchedulingParameters.WorkingDay, Boolean> workingDays = new LinkedHashMap<>();

      public WorkingDays() {
         for (SchedulingParameters.WorkingDay workingDay : SchedulingParameters.WorkingDay.values()) {
            this.workingDays.put(workingDay, false);
         }
      }

      public boolean getValue(final SchedulingParameters.WorkingDay workingDay) {
         return this.workingDays.get(workingDay);
      }

      public void setValue(final SchedulingParameters.WorkingDay workingDay, final boolean value) {
         this.workingDays.put(workingDay, value);
      }

      @Override
      public byte convert() {
         Boolean[] workingDayValues = this.workingDays.values().toArray(new Boolean[0]);
         boolean[] workingDayBits = ArrayUtils.toPrimitive(workingDayValues);
         return ValueUtilities.toByte(workingDayBits);
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof SchedulingParameters.WorkingDays)) {
            return false;
         }

         SchedulingParameters.WorkingDays that = (SchedulingParameters.WorkingDays)object;
         return new EqualsBuilder().append(this.workingDays, that.workingDays).isEquals();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.workingDays);
      }

      @Override
      public String toString() {
         return new ToStringBuilder(this).append("workingDays", this.workingDays).toString();
      }
   }
}
