package be.tpvision.smartcontrol.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Hardware.class)
public abstract class Hardware_ {
   public static volatile SingularAttribute<Hardware, String> contentId;
   public static volatile SingularAttribute<Hardware, String> hardwareKey;
   public static final String CONTENT_ID = "contentId";
   public static final String HARDWARE_KEY = "hardwareKey";
}
