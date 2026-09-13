package com.tpvision.smartinstall;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class InQueryParameter {
   private List<Object> inValues;

   private InQueryParameter() {
   }

   public static InQueryParameter getInstance(String inValuesString) {
      return getInstance(inValuesString, ",");
   }

   private static InQueryParameter getInstance(String inValuesString, String split) {
      InQueryParameter p = new InQueryParameter();
      p.inValues = new ArrayList<>();

      for (Object obj : inValuesString.split(split)) {
         p.inValues.add(obj);
      }

      return p;
   }

   public List<Object> getParamList() {
      return this.inValues;
   }

   public String getPreparedInReplaceHolder() {
      StringJoiner holder = new StringJoiner(",");

      for (int i = 0; i < this.inValues.size(); i++) {
         holder.add("?");
      }

      return holder.toString();
   }
}
