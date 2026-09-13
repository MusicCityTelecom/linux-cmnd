package be.tpvision.smartcontrol.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FtpSettings.class)
public abstract class FtpSettings_ {
   public static volatile SingularAttribute<FtpSettings, String> password;
   public static volatile SingularAttribute<FtpSettings, Integer> port;
   public static volatile SingularAttribute<FtpSettings, Boolean> useDefault;
   public static volatile SingularAttribute<FtpSettings, String> username;
   public static final String PASSWORD = "password";
   public static final String PORT = "port";
   public static final String USE_DEFAULT = "useDefault";
   public static final String USERNAME = "username";
}
