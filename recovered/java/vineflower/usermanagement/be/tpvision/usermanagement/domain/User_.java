package be.tpvision.usermanagement.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ {
   public static volatile SingularAttribute<User, String> password;
   public static volatile SingularAttribute<User, Role> role;
   public static volatile SingularAttribute<User, Long> id;
   public static volatile SingularAttribute<User, String> username;
   public static final String PASSWORD = "password";
   public static final String ROLE = "role";
   public static final String ID = "id";
   public static final String USERNAME = "username";
}
