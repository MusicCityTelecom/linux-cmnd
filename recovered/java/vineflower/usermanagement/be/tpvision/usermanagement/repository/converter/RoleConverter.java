package be.tpvision.usermanagement.repository.converter;

import be.tpvision.usermanagement.domain.Role;

public class RoleConverter extends EnumConverter<Role> {
   public RoleConverter() {
      super(Role.class);
   }
}
