package com.tpvision.smartinstall.dao.core;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

public class ProfileRolePK implements Serializable {
   private static final long serialVersionUID = 1L;
   private String profileId;
   private int roleIdrole;

   public String getProfileId() {
      return this.profileId;
   }

   public void setProfileId(String profileId) {
      this.profileId = profileId;
   }

   public int getRoleIdrole() {
      return this.roleIdrole;
   }

   public void setRoleIdrole(int roleIdrole) {
      this.roleIdrole = roleIdrole;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (!(obj instanceof ProfileRolePK)) {
         return false;
      }

      ProfileRolePK profileRolePK = (ProfileRolePK)obj;
      return StringUtils.equalsIgnoreCase(this.profileId, profileRolePK.getProfileId()) && this.roleIdrole == profileRolePK.roleIdrole;
   }

   @Override
   public int hashCode() {
      return (this.profileId + this.roleIdrole).hashCode();
   }
}
