package com.tpvision.smartinstall.androidapp;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class AndroidApplications {
   private String availablePackages;
   private List<AndroidApplications.AndroidApp> clonePackages;

   public String getAvailablePackages() {
      return this.availablePackages;
   }

   public void setAvailablePackages(String availablePackages) {
      this.availablePackages = availablePackages;
   }

   public List<AndroidApplications.AndroidApp> getClonePackages() {
      return this.clonePackages;
   }

   public void setClonePackages(List<AndroidApplications.AndroidApp> clonePackages) {
      this.clonePackages = clonePackages;
   }

   public static class AndroidApp {
      public static final String PACKAGE_TYPE_LOCAL = "LOCAL";
      public static final String PACKAGE_TYPE_PLAYSTORE = "PlayStore";
      private String packageURI;
      private String silentAction;
      private String packageType;
      private String packagePosition;
      private String packageShowHide;
      private String[] packageCountry;
      private String[] packageCategory;
      private String packageName;
      private AndroidApplications.PackageDetails packageDetails;
      private String applicationName;

      public String getPackageURI() {
         if (this.packageURI != null) {
            return this.packageURI;
         } else {
            return this.packageDetails != null && !this.packageDetails.getBasePackages().isEmpty()
               ? this.packageDetails.getBasePackages().get(0).getPackageURI()
               : null;
         }
      }

      public void setPackageURI(String packageURI) {
         this.packageURI = packageURI;
      }

      public String getSilentAction() {
         return this.silentAction;
      }

      public void setSilentAction(String silentAction) {
         this.silentAction = silentAction;
      }

      public String getPackageType() {
         return this.packageType;
      }

      public void setPackageType(String packageType) {
         this.packageType = packageType;
      }

      public String getPackagePosition() {
         return this.packagePosition;
      }

      public void setPackagePosition(String packagePosition) {
         this.packagePosition = packagePosition;
      }

      public String getPackageShowHide() {
         return this.packageShowHide;
      }

      public void setPackageShowHide(String packageShowHide) {
         this.packageShowHide = packageShowHide;
      }

      public String[] getPackageCountry() {
         return this.packageCountry;
      }

      public void setPackageCountry(String[] packageCountry) {
         this.packageCountry = packageCountry;
      }

      public String[] getPackageCategory() {
         return this.packageCategory;
      }

      public void setPackageCategory(String[] packageCategory) {
         this.packageCategory = packageCategory;
      }

      public String getPackageName() {
         if (this.packageName != null) {
            return this.packageName;
         } else {
            return this.packageDetails != null && StringUtils.isNotBlank(this.packageDetails.getPackageName()) ? this.packageDetails.getPackageName() : null;
         }
      }

      public void setPackageName(String packageName) {
         this.packageName = packageName;
      }

      public String getAllInfoString() {
         return this.packageURI + " " + String.join(",", this.getPackageCategory()) + " " + String.join(",", this.getPackageCountry());
      }

      public AndroidApplications.PackageDetails getPackageDetails() {
         return this.packageDetails;
      }

      public void setPackageDetails(AndroidApplications.PackageDetails packageDetails) {
         this.packageDetails = packageDetails;
      }

      public String getApplicationName() {
         return this.applicationName;
      }

      public void setApplicationName(String applicationName) {
         this.applicationName = applicationName;
      }

      public boolean containsCategory(String categoryName) {
         boolean isContains = false;

         for (String category : this.packageCategory) {
            if (category.contains(categoryName)) {
               isContains = true;
               break;
            }
         }

         return isContains;
      }
   }

   public static class PackageDetails {
      private String packageName;
      private List<AndroidApplications.PackageDetails.BasePackage> basePackages = new ArrayList<>();
      private List<AndroidApplications.PackageDetails.SplitPackage> splitPackages = new ArrayList<>();

      public String getPackageName() {
         return this.packageName;
      }

      public void setPackageName(String packageName) {
         this.packageName = packageName;
      }

      public List<AndroidApplications.PackageDetails.BasePackage> getBasePackages() {
         return this.basePackages;
      }

      public void setBasePackages(List<AndroidApplications.PackageDetails.BasePackage> basePackages) {
         this.basePackages = basePackages;
      }

      public List<AndroidApplications.PackageDetails.SplitPackage> getSplitPackages() {
         return this.splitPackages;
      }

      public void setSplitPackages(List<AndroidApplications.PackageDetails.SplitPackage> splitPackages) {
         this.splitPackages = splitPackages;
      }

      public static class BasePackage {
         private String packageURI;

         public String getPackageURI() {
            return this.packageURI;
         }

         public void setPackageURI(String packageURI) {
            this.packageURI = packageURI;
         }
      }

      public static class SplitPackage {
         private String packageURI;
         private String splitName;

         public String getSplitName() {
            return this.splitName;
         }

         public void setSplitName(String splitName) {
            this.splitName = splitName;
         }

         public String getPackageURI() {
            return this.packageURI;
         }

         public void setPackageURI(String packageURI) {
            this.packageURI = packageURI;
         }
      }
   }
}
