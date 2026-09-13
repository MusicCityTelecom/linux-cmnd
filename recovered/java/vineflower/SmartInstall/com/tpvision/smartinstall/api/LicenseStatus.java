package com.tpvision.smartinstall.api;

public enum LicenseStatus {
   NO_LICENSE("no license"),
   EXPIRE_LICENSE("license expired"),
   VALIDE_LICENSE("valid license"),
   DEVICE_LIMIT_REACHED("device limit reached");

   private String description;

   LicenseStatus(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }
}
