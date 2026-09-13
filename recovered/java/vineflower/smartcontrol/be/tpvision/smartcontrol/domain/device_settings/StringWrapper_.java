package be.tpvision.smartcontrol.domain.device_settings;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StringWrapper.class)
public abstract class StringWrapper_ {
   public static volatile SingularAttribute<StringWrapper, String> value;
   public static final String VALUE = "value";
}
