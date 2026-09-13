package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationSchedulesPlusChargeType", propOrder = "operationSchedule")
public class OperationSchedulesPlusChargeType {
   @XmlElement(name = "OperationSchedule")
   protected List<OperationSchedulesPlusChargeType.OperationSchedule> operationSchedule;

   public List<OperationSchedulesPlusChargeType.OperationSchedule> getOperationSchedule() {
      if (this.operationSchedule == null) {
         this.operationSchedule = new ArrayList<>();
      }

      return this.operationSchedule;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class OperationSchedule extends OperationSchedulePlusChargeType {
      @XmlAttribute(name = "Name")
      protected String name;

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }
   }
}
