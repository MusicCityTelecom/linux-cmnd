package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MealPrefType", propOrder = "value")
public class MealPrefType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "MealType")
   protected MealType mealType;
   @XmlAttribute(name = "FavoriteFood")
   protected String favoriteFood;
   @XmlAttribute(name = "Beverage")
   protected String beverage;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public MealType getMealType() {
      return this.mealType;
   }

   public void setMealType(MealType value) {
      this.mealType = value;
   }

   public String getFavoriteFood() {
      return this.favoriteFood;
   }

   public void setFavoriteFood(String value) {
      this.favoriteFood = value;
   }

   public String getBeverage() {
      return this.beverage;
   }

   public void setBeverage(String value) {
      this.beverage = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
   }
}
