package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RevenueCategoriesType", propOrder = "revenueCategory")
public class RevenueCategoriesType {
   @XmlElement(name = "RevenueCategory", required = true)
   protected List<RevenueCategoryType> revenueCategory;

   public List<RevenueCategoryType> getRevenueCategory() {
      if (this.revenueCategory == null) {
         this.revenueCategory = new ArrayList<>();
      }

      return this.revenueCategory;
   }
}
